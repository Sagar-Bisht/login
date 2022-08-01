package com.example.loginactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DashBoard extends AppCompatActivity {

    TextView textView ;
    Button logout ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);


        textView = findViewById(R.id.textView);
        logout = findViewById(R.id.logout);

        checkUserExistent();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("credential",MODE_PRIVATE);
                sp.edit().remove("username").commit();
                sp.edit().remove("password").commit();
                sp.edit().apply();
                startActivity(new Intent(DashBoard.this,MainActivity.class));
                finish();
            }
        });


    }

    private void checkUserExistent(){
        SharedPreferences sp = getSharedPreferences("credential",MODE_PRIVATE);
        if(sp.contains("username")){
            textView.setText(sp.getString("username",""));
        }else{
            startActivity(new Intent(DashBoard.this,MainActivity.class));
        }

    }
}