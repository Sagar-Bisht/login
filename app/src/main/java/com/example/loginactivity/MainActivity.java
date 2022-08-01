package com.example.loginactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    EditText mEmail;
    EditText mPassword;
    Button mLogin;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        checkUserExistent() ;

        init() ;

        listeners();
    }

    private void listeners(){
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processData();
            }
        });

    }
    private void  init(){
        mEmail = findViewById(R.id.mEmail);
        mPassword = findViewById(R.id.mPassword);
        mLogin = findViewById(R.id.mLogin);
        mTextView = findViewById(R.id.mTextView);
    }


    private void checkUserExistent() {
        SharedPreferences sp = getSharedPreferences("credential" ,MODE_PRIVATE);
        if(sp.contains("username")){
            startActivity(new Intent(MainActivity.this,DashBoard.class));
        }else{
            mTextView.setText("please login");
            mTextView.setTextColor(Color.GREEN);
        }

    }

    private void processData() {
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        Call<ResponseModel> call = APIcontroller.getInstance().getAPI().verifyUser(email,password);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                ResponseModel model = response.body();

                String output=model.getMessage();
                if(output.equals("failed")){
                    mEmail.setText("");
                    mPassword.setText("");
                    mTextView.setText("invalid");
                    mTextView.setTextColor(Color.RED);
                }if(output.equals("exist")){
                    SharedPreferences sharedPreferences = getSharedPreferences("credential",MODE_PRIVATE);
                    SharedPreferences.Editor  editor = sharedPreferences.edit();
                    editor.putString("username",mEmail.getText().toString());
                    editor.putString("password",mPassword.getText().toString());
                    editor.commit();
                    editor.apply();


                    startActivity(new Intent(MainActivity.this,DashBoard.class));
                    finish();
                }
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

                mTextView.setText(t.toString());
            }
        });
    }
}