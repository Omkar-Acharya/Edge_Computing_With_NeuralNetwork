package com.omkaracharya.edgecomputingwithneuralnetworks;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView image;
    Bitmap imgBitMap;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageView) findViewById(R.id.selectedImage);
        final Button selectImage = (Button) findViewById(R.id.selectImage);

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectphoto = new Intent(Intent.ACTION_PICK);
                selectphoto.setType("image/*");
                startActivityForResult(selectphoto, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            Uri imgUri = data.getData();
            if(imgUri != null){
                try{
                    imgBitMap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
                    image.setImageBitmap(imgBitMap);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void onStop(){
        super.onStop();
        if(imgBitMap != null){
            imgBitMap.recycle();
            imgBitMap = null;
            System.gc();
        }
    }
}
