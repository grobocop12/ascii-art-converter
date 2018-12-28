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

        String imagePath = getIntent().getStringExtra(ExploreFilesActivity.EXTRA_IMAGE_PATH);
        Bitmap image = BitmapFactory.decodeFile(imagePath);
        ImageView imgView = (ImageView) findViewById(R.id.imageView);
        imgView.setImageBitmap(image);
    }
}
