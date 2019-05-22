package com.omkaracharya.edgecomputingwithneuralnetworks;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private static final String MODEL_PATH = "inceptionv3_slim_2016.tflite";
    private static final String LABEL_PATH = "imagenet_slim_labels.txt";
    private static final int INPUT_SIZE = 299;

    Classifier classifier;

    Executor executor = Executors.newSingleThreadExecutor();

    ImageView image;
    Bitmap imgBitMap;
    TextView resultsTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageView) findViewById(R.id.selectedImage);
        //Button selectImage = (Button) findViewById(R.id.selectImage);
        Button detectImage = (Button) findViewById(R.id.recognizeImage);
        resultsTextView = findViewById(R.id.resultsTextView);
        resultsTextView.setMovementMethod(new ScrollingMovementMethod());

        //Call to init() to load the classifier class
        init();

//        selectImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent selectphoto = new Intent(Intent.ACTION_PICK);
//                selectphoto.setType("image/*");
//                startActivityForResult(selectphoto, 1);
//            }
//        });

//        detectImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                List<Classifier.Recognition> results = classifier.recognizeImage(imgBitMap);
//                resultsTextView.setText(results.toString());
//            }
//        });

        detectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AssetManager assetManager = getAssets();
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //InputStream inputStream = assetManager.open("images");
                            String[] images = getAssets().list("images");
                            //Drawable[] drawables = new Drawable[images.length];
                            //System.out.print("Total images in assets: "+images.length);
                            Log.d("Total images in Assets", " " + images.length);
//                            InputStream inputStream = getAssets().open("images/"+images[9]);
//                            imgBitMap = BitmapFactory.decodeStream(inputStream);
//                            image.setImageBitmap(imgBitMap);
//                            List<Classifier.Recognition> results = classifier.recognizeImage(imgBitMap);
//                            resultsTextView.setText(results.toString());

                            for (int i = 0; i < images.length; i++) {
                                InputStream inputStream = getAssets().open("images/" + images[i]);
                                imgBitMap = BitmapFactory.decodeStream(inputStream);
                                final List<Classifier.Recognition> results = classifier.recognizeImage(imgBitMap);
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        image.setImageBitmap(imgBitMap);
                                        resultsTextView.setText(results.toString());
                                    }
                                });
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == RESULT_OK) {
//            Uri imgUri = data.getData();
//            if (imgUri != null) {
//                try {
//                    imgBitMap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
//                    image.setImageBitmap(imgBitMap);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//    }

        @Override
        public void onStop () {
            super.onStop();
            if (imgBitMap != null) {
                imgBitMap.recycle();
                imgBitMap = null;
                System.gc();
            }
        }

        void init () {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        classifier = TensorFlowImageClassifier.create(
                                getAssets(),
                                MODEL_PATH,
                                LABEL_PATH,
                                INPUT_SIZE);
                    } catch (final Exception e) {
                        throw new RuntimeException("Error initializing TensorFlow!", e);
                    }
                }
            });
        }
}
