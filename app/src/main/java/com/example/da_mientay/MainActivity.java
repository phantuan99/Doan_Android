package com.example.da_mientay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Khởi báo control
    Button btnSignIn, btnSignUp;
    TextView txtSlogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AddControl();
        AddEvent();
    }

    void AddControl()
    {
        btnSignIn = (Button)findViewById(R.id.btnSignin);
        btnSignUp = (Button)findViewById(R.id.btnSignup);
        txtSlogan = (TextView)findViewById(R.id.txtSlogan);
    }

    void AddEvent()
    {

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I_signIn = new Intent(MainActivity.this,Signin.class);
                startActivity(I_signIn);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I_signUp = new Intent(MainActivity.this,SignUp.class);
                startActivity(I_signUp);
            }
        });


    }


}