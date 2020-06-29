package com.example.kamdhenupashuahar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
Button logbut;
TextView username,password;
String un,pw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=findViewById(R.id.editText);
        password=findViewById(R.id.editText2);
        logbut=findViewById(R.id.loginbutton);
        logbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                un=username.getText().toString();
                pw=password.getText().toString();
                if(un.equals("admin")&&pw.equals("admin123")){
                    Intent intent=new Intent(Login.this,MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(Login.this, "Invalid Username or Password!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
