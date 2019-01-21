package com.example.grobocop.ascii_art_converter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ConvertImageActivity extends AppCompatActivity {

    private TextView asciiTextView;

    private static final String TAG = "Convert";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_image);

        String imagePath = getIntent().getStringExtra(MainActivity.EXTRA_IMAGE_PATH);

        ImageView loadImage = (ImageView) findViewById(R.id.loadImageView);

        Glide
                .with(this)
                .load("http://157.158.200.33:80/AArtConverter/")
                .into(loadImage);



       // Bitmap image = BitmapFactory.decodeFile(imagePath);



      //  int nh = (int) ( image.getHeight() * (512.0 / image.getWidth()) );

      //  Bitmap scaled = Bitmap.createScaledBitmap(image, 512, nh, true);

       // String yourAsciiArtData = ""; // from assets or network or...

       /* AsciiArtView asciiArtView = (AsciiArtView) findViewById(R.id.asciiart);
        asciiArtView.setAsciiArt(yourAsciiArtData);
        asciiArtView.setTypeface(Typeface.create( "Sans-serif", Typeface.NORMAL));
*/



    }
}
