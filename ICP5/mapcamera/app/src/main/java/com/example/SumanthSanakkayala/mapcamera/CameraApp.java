package com.example.SumanthSanakkayala.mapcamera;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class CameraApp extends AppCompatActivity {
ImageView image;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        Button takepic=(Button)findViewById(R.id.button);
        Button home=(Button)findViewById(R.id.button2);
        image=(ImageView)findViewById(R.id.imageView2);
        Toast.makeText(this,"In camera activity",Toast.LENGTH_SHORT).show();
        if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.CAMERA}, 100);
        }
        takepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pic=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(pic,0);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redirect= new Intent(CameraApp.this,MainPage.class);
                startActivity(redirect);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bit=(Bitmap)data.getExtras().get("data");
        image.setImageBitmap(bit);
    }

}
