package com.example.dialogfragment;

import androidx.annotation.NonNull;
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
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

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
    SearchView searchView;
    ImageButton b1;

    final private int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvText = findViewById(R.id.tvText);
        btnSpeak = findViewById(R.id.btnSpeak);
        btCamera = (ImageButton)findViewById(R.id.bt_camera);

        searchView = findViewById(R.id.search);
        //String love = "Busqueda...";





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

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.CAMERA
                    },REQUEST_CODE);
        }else{
            // Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            //startActivityForResult(cameraIntent, REQUEST_CODE);
        }
        btCamera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                try {
                    Toast.makeText(MainActivity.this,"Camara",Toast.LENGTH_SHORT).show();
                    // Intent intent = new Intent(MainActivity.this, camera.class);
                    Intent intent = new Intent();
                    // Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,REQUEST_CODE);
                    //startActivity(intent);
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
                Toast.makeText(MainActivity.this,"Abriendo...",Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager2 = getSupportFragmentManager();
                dialogFragImage mdf2 = new dialogFragImage();
                mdf2.show(fragmentManager2,"fragment_image_view");
            }
        });

        b1 = (ImageButton)findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                Toast.makeText(MainActivity.this,"Abriendo...",Toast.LENGTH_SHORT).show();
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
                    SpannableString content = new SpannableString(text.get(0));
                    content.setSpan(new UnderlineSpan(), 0, text.get(0).length(), 0);
                    tvText.setText(content);
                    searchView.setQuery(content, true);
                }
                break;
        }
        if (requestCode == 100){
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            imageview.setImageBitmap(captureImage);
        }
    }

}