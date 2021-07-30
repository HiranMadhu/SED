package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;

public class SettingsActivity extends AppCompatActivity {
    String devicesTemp = "Incandescent.CFL Bulb.LED Bulb.Fan.Router.New Device";
    String deviceType;
    public static final String EXTRA_MESSAGE="Extra.Message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

    }

    public void showDevice(View view){

        String[] devices = devicesTemp.split("\\."); //spliting the string from space
        System.out.println(devices[4]);
        int itemIndex= 0;

        AlertDialog.Builder builder=new AlertDialog.Builder(SettingsActivity.this);
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
                    TextView textView=findViewById(R.id.LocalHostEdit);
                    textView.setText("CFL Bulb");
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

    public void onUpdatePress(View view){
//        TextView textView=findViewById(R.id.LocalHostEdit);
//        String newHost=textView.getText().toString();
//        DBHelper updateHost;
//        updateHost=new DBHelper(this);
//        updateHost.updateHostAddress(newHost);
        Intent intent=new Intent(this,MainInterface.class);
        intent.putExtra( EXTRA_MESSAGE,"hello");
        startActivity(intent);
    }
}