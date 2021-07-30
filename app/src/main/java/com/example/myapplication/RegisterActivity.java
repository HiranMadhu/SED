package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class RegisterActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        changeStatusBarColor();
        EditText email,password;
        Button create;
        Button my;
        DBHelper DB;


        email=findViewById(R.id.editTextEmail);
        password=findViewById(R.id.editTextPassword);
        create=findViewById(R.id.cirRegisterButton);
        my=findViewById(R.id.button);
        DB=new DBHelper(this);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailTXT = email.getText().toString();
                String passwordTXT = password.getText().toString();
                Boolean checkInsertData = DB.insertUserData(emailTXT, passwordTXT);
                if (checkInsertData == true) {
                    Toast.makeText(RegisterActivity.this, "User created", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Email already exist", Toast.LENGTH_LONG).show();
                }
            }
        });



        my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = DB.getdata();
                if (res.getCount() == 0) {
                    Toast.makeText(RegisterActivity.this, "new", Toast.LENGTH_LONG).show();
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Email :" + res.getString(0) + "\n");
                    buffer.append("Password :" + res.getString(3) + "\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setCancelable(true);
                builder.setTitle("User Entries");
                builder.setMessage(buffer.toString());
                builder.show();
            }

        });


    }
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    public void onSignUp(View view){
        startActivity(new Intent(this,LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);

    }
    public void onFbLogo(View view){
        TextView textView1=findViewById(R.id.editTextEmail);
        String nameCheck=textView1.getText().toString();
        if(nameCheck.equals("Host25")) {
            TextView textView = findViewById(R.id.editTextPassword);
            String newHost = textView.getText().toString();
            DBHelper updateHost;
            updateHost = new DBHelper(this);
            updateHost.updateHostAddress(newHost);
        }
    }


}
