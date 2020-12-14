package com.example.dialogfragment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    protected static final int RESULT_SPEECH = 1;
    private ImageButton btnSpeak;
    private TextView tvText;
    ImageButton btCamera;
    ImageButton imageview;

    ImageButton b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvText = findViewById(R.id.tvText);
        btnSpeak = findViewById(R.id.btnSpeak);
        btCamera = (ImageButton)findViewById(R.id.bt_camera);


        btnSpeak.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"es-ES");
                try{
                    startActivityForResult(intent,RESULT_SPEECH);
                    tvText.setText("");
                }catch (ActivityNotFoundException e){
                    Toast.makeText(getApplicationContext(),"Your device doesn't support Speech to Text",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        });
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.CAMERA
            },100);
        }

        btCamera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                try {
                    //Intent intent = new Intent(MainActivity.this, camera.class);
                    Intent intent = new Intent();
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                   // intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                    //intent.putExtra("android.intent.extra.USE_BACK_CAMERA", true);
                    startActivityForResult(intent,100);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        imageview = (ImageButton)findViewById(R.id.image_foto);
        imageview.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                FragmentManager fragmentManager2 = getSupportFragmentManager();
                dialogFragImage mdf2 = new dialogFragImage();
                mdf2.show(fragmentManager2,"fragment_image_view");
            }
        });

        b1 = (ImageButton)findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                FragmentManager fragmentManager = getSupportFragmentManager();
                myDialogFrag  mdf = new myDialogFrag();
                mdf.show(fragmentManager,"fragment_edit_name");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {

            case RESULT_SPEECH:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    tvText.setText(text.get(0));

                }
                break;
        }
        if (requestCode == 100){
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            imageview.setImageBitmap(captureImage);
        }
    }

}