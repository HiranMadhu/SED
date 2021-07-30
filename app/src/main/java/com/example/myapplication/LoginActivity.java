package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for changing status bar icon colors
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);
    }

    public void onSignUp(View View){
        startActivity(new Intent(this,RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }

    public void onLoginClick(View view){
        EditText editName=findViewById(R.id.editTextEmail);
        String name=editName.getText().toString();
        EditText editPassword=findViewById(R.id.editTextPassword);
        String password=editPassword.getText().toString();
        if (name.equals("") && password.equals("")){
            startActivity(new Intent(this,MainInterface.class));
        }
        else{
            Toast.makeText(this,"Wrong User name or Password",Toast.LENGTH_SHORT).show();
            editName.setText("");
            editPassword.setText("");
        }
    }



}
