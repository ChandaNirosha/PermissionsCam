package com.example.sys9.security;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;

public class MainActivity extends AppCompatActivity {
    Button b1;
    Button b2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button) findViewById(R.id.call);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermission()) {
                    Intent i = new Intent(Intent.ACTION_CALL);
                    i.setData(Uri.parse("tel:8125224669"));
                    startActivity(i);

                } else {
                    requestPermission();
                    {
                        Toast.makeText(MainActivity.this, "not given", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        b2=(Button)findViewById(R.id.cam);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermission()) {

                    Intent in = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                  //startActivity(in);
                startActivityForResult(in,200);
                } else {
                    requestPermission();
                    {
                        Toast.makeText(MainActivity.this, "not given", Toast.LENGTH_SHORT).show();
                    }
                }            }

        });

    }

    private boolean checkPermission() {
        int FIRSTPERMISSIONRESULT = ContextCompat.checkSelfPermission(MainActivity.this, CALL_PHONE);
        int sec = ContextCompat.checkSelfPermission(MainActivity.this, CAMERA);
        if (FIRSTPERMISSIONRESULT == PackageManager.PERMISSION_GRANTED && sec == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA}, 100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {


        switch (requestCode) {
            case 100:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }
        protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data)
        {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == 200) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                ImageView image = (ImageView) findViewById(R.id.image);
                image.setImageBitmap(bitmap);
            }
        }

    }







