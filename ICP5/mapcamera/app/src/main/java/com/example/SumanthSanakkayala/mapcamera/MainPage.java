package com.example.SumanthSanakkayala.mapcamera;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void OnCamera(View v){
        Intent toCamera= new Intent(MainPage.this,CameraApp.class);
        startActivity(toCamera);
    }
    public void Location(View v){
        Intent toLocation=new Intent(MainPage.this,maplocation.class);
        startActivity(toLocation);
    }
}
