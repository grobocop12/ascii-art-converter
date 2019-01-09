package com.example.grobocop.ascii_art_converter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class DisplayImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);

        String imagePath = getIntent().getStringExtra(MainActivity.EXTRA_IMAGE_PATH);
        Bitmap image = BitmapFactory.decodeFile(imagePath);

        int nh = (int) ( image.getHeight() * (512.0 / image.getWidth()) );
        Bitmap scaled = Bitmap.createScaledBitmap(image, 512, nh, true);

        ImageView imgView = (ImageView) findViewById(R.id.imageView);
        imgView.setImageBitmap(scaled);
    }
}
