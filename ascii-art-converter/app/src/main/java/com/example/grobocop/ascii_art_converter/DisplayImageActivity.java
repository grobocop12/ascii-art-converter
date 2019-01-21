package com.example.grobocop.ascii_art_converter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

//import uk.co.senab.photoview.PhotoViewAttacher;



public class DisplayImageActivity extends AppCompatActivity {

    public static final String EXTRA_IMAGE_PATH = "com.example.grobocop.ascii_art_converter.EXTRA_IMAGE";

    public String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_display_image);

        imagePath = getIntent().getStringExtra(MainActivity.EXTRA_IMAGE_PATH);
        Bitmap image = BitmapFactory.decodeFile(imagePath);
        int nh = (int) ( image.getHeight() * (512.0 / image.getWidth()) );
        ImageView imgView = (ImageView) findViewById(R.id.imageView);
        Bitmap scaled = Bitmap.createScaledBitmap(image, 512, nh, true);
        imgView.setImageBitmap(scaled);
        //PhotoViewAttacher photoView =  new PhotoViewAttacher(imgView);
        //photoView.update();

        Button convertButton = (Button) findViewById(R.id.ConvertImageButton);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ConvertImageActivity.class);
                intent.putExtra(EXTRA_IMAGE_PATH, imagePath);
                startActivity(intent);
            }
        });



    }
}
