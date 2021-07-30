package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainInterface extends AppCompatActivity implements RecyclerViewAdapter.OnNoteListener{

    public static final String EXTRA_MESSAGE="Extra.Message";
    private Handler mHandler=new Handler();
    public String localHostString;
    public String flakJason;
    public List<String> list=new ArrayList<String>();
    List<String> list2=new ArrayList<String>(){{
        add("ROU");
        add("INC");
        add("LED");
        add("FAN");
    }};
    List<String> list3=new ArrayList<String>(){{
        add("ROU");
        add("INC");
        add("LED");
        add("FAN");
        add("CFL");
    }};
    float wattageVal =0;
    double currentVal=0;
    DecimalFormat df = new DecimalFormat("#.###");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_interface);
//        setHost();
//        start();
        if(getIntent().hasExtra(SettingsActivity.EXTRA_MESSAGE)){
            setList(list2);
        }else {
            setList(list3);
        }
        double val=wattageVal/1000;
        TextView current=findViewById(R.id.currentShow);
        TextView unit=findViewById(R.id.unitShow);
        unit.setText(String.valueOf(df.format(val)));
        current.setText(String.valueOf(df.format(currentVal)));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.usageHistory:
                Intent intent=new Intent(MainInterface.this,BarShow.class);
                startActivity(intent);
                break;
            case R.id.action_settings:
                Intent intent2=new Intent(MainInterface.this,SettingsActivity.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void setHost(){
        DBHelper host;
        host=new DBHelper(this);
        Cursor res = host.getHost();
        if (res.getCount() == 0) {
            Toast.makeText(MainInterface.this, "Error in Host address\ncheck in Settings", Toast.LENGTH_LONG).show();
            Intent intent=new Intent(this,SettingsActivity.class);
            startActivity(intent);
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append(res.getString(0));
        }
        localHostString=buffer.toString();
    }


    public void setPicture(int picArray[], String orderArray[]){
        RecyclerView recyclerView;
        RecyclerView.LayoutManager layoutManager;
        RecyclerViewAdapter recyclerViewAdapter1;
        recyclerView=findViewById(R.id.recyclerView);
        layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter1=new RecyclerViewAdapter(picArray,orderArray,this);
        recyclerView.setAdapter(recyclerViewAdapter1);
        recyclerView.setHasFixedSize(true);
    }



    public void setList(List list){
        wattageVal=0;
        currentVal=0;
        String arr2[]={};
        int arr3[]={};
        for (Object temp : list) {
            String item=String.valueOf(temp);
            switch (item){
                case "CFL":
                    arr3=addInt(arr3.length,arr3,R.drawable.cfl);
                    arr2=addString(arr2.length,arr2,"CFL Bulb");
                    wattageVal=wattageVal+11;
                    currentVal=currentVal+0.26;
                    break;
                case "INC":
                    arr3=addInt(arr3.length,arr3,R.drawable.inc3);
                    arr2=addString(arr2.length,arr2,"Incandescent");
                    wattageVal=wattageVal+60;
                    currentVal=currentVal+0.7;
                    break;
                case "LED":
                    arr3=addInt(arr3.length,arr3,R.drawable.led2);
                    arr2=addString(arr2.length,arr2,"LED Bulb");
                    wattageVal=wattageVal+7;
                    currentVal=currentVal+0.1;
                    break;
                case "FAN":
                    arr3=addInt(arr3.length,arr3,R.drawable.fan1);
                    arr2=addString(arr2.length,arr2,"Fan");
                    wattageVal=wattageVal+65;
                    currentVal=currentVal+0.62;
                    break;
                case "ROU":
                    arr3=addInt(arr3.length,arr3,R.drawable.router1);
                    arr2=addString(arr2.length,arr2,"Router");
                    wattageVal=wattageVal+15;
                    currentVal=currentVal+0.3;
                    break;
                case "TV":
                    arr3=addInt(arr3.length,arr3,R.drawable.tv);
                    arr2=addString(arr2.length,arr2,"TV");
                    wattageVal=wattageVal+47;
                    currentVal=currentVal+0.57;
                    break;
                case "OTHER":
                case "OTHER2":
                    arr3=addInt(arr3.length,arr3,R.drawable.question1);
                    arr2=addString(arr2.length,arr2,"Unknown");
                    wattageVal=wattageVal+47;
                    currentVal=currentVal+0.57;
                    break;
            }
        }
        setPicture(arr3,arr2);
    }



    public static int[] addInt(int n, int arr[], int x)
    {
        int i;
        int newarr[] = new int[n + 1];
        for (i = 0; i < n; i++)
            newarr[i] = arr[i];
        newarr[n] = x;        return newarr;
    }



    public static String[] addString(int n, String arr[], String x)
    {
        int i;
        String newarr[] = new String[n + 1];
        for (i = 0; i < n; i++)
            newarr[i] = arr[i];
        newarr[n] = x;        return newarr;
    }



    public void flaskCheck() {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder().url("http://192.168.1.2:5000/").build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Toast.makeText(MainInterface.this, "Error in Host address\ncheck in Settings", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    flakJason = response.body().string();
                }
            });
    }


    public boolean listCheckForUpdates(){
        List<String> temp=new ArrayList<String>();
        List<String> temp2=new ArrayList<String>();
        for(String lang:list){
            temp.add(lang);
        }
        try{
            String[] separatedTemp = flakJason.split("\\.");
            for(String lang:separatedTemp){
                temp2.add(lang);
                if(flakJason==""){
                    temp2.clear();
                }
            }

            if (temp.size()!=temp2.size()){
                list.clear();
                for(String lang:temp2){
                    list.add(lang);
                }
                return true;
            }else{
                return false;
            }
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }

    public void start(){
        showList.run();
    }

    public void stop(){
        mHandler.removeCallbacks(showList);
    }

    private Runnable showList=new Runnable() {
        @Override
        public void run() {
            flaskCheck();
            if(listCheckForUpdates()){
                TextView current=findViewById(R.id.currentShow);
                TextView unit=findViewById(R.id.unitShow);
                setList(list);
                float val=(wattageVal/1000);
                unit.setText(String.valueOf(df.format(val)));
                current.setText(String.valueOf(df.format(currentVal)));
            }
            mHandler.postDelayed(this,3000);
        }
    };


    @Override
    public void onNoteClick(int position) {
        stop();
        Intent intent=new Intent(this, DeviceDetails.class);
        intent.putExtra( EXTRA_MESSAGE,list2.get(position));
        startActivity(intent);
    }
}