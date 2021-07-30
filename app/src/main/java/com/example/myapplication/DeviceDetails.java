package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DeviceDetails extends AppCompatActivity {
    String junk;
    private static final int PICK_IMAGE=100;
    Uri imageURI;
    public String localHostString;
    String deviceType;
    String devicesTemp = "CFL Bulb.Incandescent.LED Bulb.Fan.Router.New Device";
    public static final String EXTRA_MESSAGE1="Extra.Message";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_details);
        if(getIntent().hasExtra(MainInterface.EXTRA_MESSAGE)){
            Intent intent=getIntent();
            String device=intent.getStringExtra(MainInterface.EXTRA_MESSAGE);
            updateClick(device);
        }
        ImageView clickImage=findViewById(R.id.deviceImage);
        clickImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                openGallery();
                return false;
            }
        });


    }


    public void showDevice(View view){
        TextView deviceAlready=findViewById(R.id.nameEdit);
        String nameOfDevice=deviceAlready.getText().toString();

        String[] devices = devicesTemp.split("\\."); //spliting the string from space
        System.out.println(devices[4]);
        int itemIndex= Arrays.asList(devices).indexOf(nameOfDevice);

        AlertDialog.Builder builder=new AlertDialog.Builder(DeviceDetails.this);
        builder.setTitle("Choose device");
        builder.setSingleChoiceItems(devices, itemIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deviceType=devices[which];
            }
        });
        builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(deviceType.equals("New Device")){
                    devicesTemp=devicesTemp.replace("New Device","TV.New Device");
                    openDialog();
                }else{
                    TextView textView=findViewById(R.id.nameEdit);
                    textView.setText("Incandescent");
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void openDialog(){
        DeviceAddDialog deviceAddDialog=new DeviceAddDialog();
        deviceAddDialog.show(getSupportFragmentManager(),"New device");
    }

    public void openGallery(){
        Intent galleryPic=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galleryPic,PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView setImage=findViewById(R.id.deviceImage);
        if (resultCode==RESULT_OK&& requestCode==PICK_IMAGE){
            imageURI=data.getData();
            setImage.setImageURI(imageURI);
        }
    }

    public void updateClick(String device){
        ImageView preview=findViewById(R.id.deviceImage);
        TextView deviceName=findViewById(R.id.nameEdit);
        TextView wattage=findViewById(R.id.wattageEdit);
        TextView current=findViewById(R.id.currentEdit);
        switch (device){
            case "CFL":
                deviceName.setText("CFL Bulb");
                wattage.setText("11 W");
                current.setText("0.26 A");
                preview.setImageResource(R.drawable.cfl);
                break;
            case "INC":
                deviceName.setText("Incandescent");
                wattage.setText("60 W");
                current.setText("0.7 A");
                preview.setImageResource(R.drawable.inc3);
                break;
            case "LED":
                deviceName.setText("LED Bulb");
                wattage.setText("7 W");
                current.setText("0.1 A");
                preview.setImageResource(R.drawable.led2);
                break;
            case "FAN":
                deviceName.setText("Fan");
                wattage.setText("65 W");
                current.setText("0.62 A");
                preview.setImageResource(R.drawable.fan1);
                break;
            case "ROU":
                deviceName.setText("Router");
                wattage.setText("15 W");
                current.setText("0.3 A");
                preview.setImageResource(R.drawable.router1);
                break;
            case "TV":
                devicesTemp=devicesTemp.replace("New Device","TV.New Device");
                deviceName.setText("TV");
                wattage.setText("47 W");
                current.setText("0.57 A");
                preview.setImageResource(R.drawable.tv);
                break;
            case "OTHER":
                Toast.makeText(this,"Give a name to your device",Toast.LENGTH_LONG).show();
                deviceName.setText("Set name");
                wattage.setText("47 W");
                current.setText("0.57 A");
                preview.setImageResource(R.drawable.addwhite);
                break;
            case "OTHER2":
                devicesTemp=devicesTemp.replace("New Device","TV.New Device");
                Toast.makeText(this,"Give a name to your device",Toast.LENGTH_LONG).show();
                deviceName.setText("Set name");
                wattage.setText("47 W");
                current.setText("0.57 A");
                preview.setImageResource(R.drawable.addwhite);
                break;

        }

    }


    public void sendFlask(View view){
        TextView deviceName=findViewById(R.id.nameEdit);
        String newDevice=deviceName.getText().toString();
        RequestBody formbody=new FormBody.Builder().add("value",newDevice).build();
        try {
            OkHttpClient okHttpClient=new OkHttpClient();
            Request request=new Request.Builder().url("http://192.168.1.2:5000/update").post(formbody).build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();
                }
                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    junk=response.body().string();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        Intent intent=new Intent(this,MainInterface.class);
        startActivity(intent);
    }


}