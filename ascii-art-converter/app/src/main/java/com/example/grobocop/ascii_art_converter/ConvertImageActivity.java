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

//import com.bachors.img2ascii.Img2Ascii;

import com.bachors.img2ascii.Img2Ascii;

public class ConvertImageActivity extends AppCompatActivity {

    private TextView asciiTextView;

    private static final String TAG = "Convert";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_image);

        String imagePath = getIntent().getStringExtra(MainActivity.EXTRA_IMAGE_PATH);

        Bitmap image = BitmapFactory.decodeFile(imagePath);

        asciiTextView = (TextView) findViewById(R.id.asciiart);

       // String yourAsciiArtData = ""; // from assets or network or...

       /* AsciiArtView asciiArtView = (AsciiArtView) findViewById(R.id.asciiart);
        asciiArtView.setAsciiArt(yourAsciiArtData);
        asciiArtView.setTypeface(Typeface.create( "Sans-serif", Typeface.NORMAL));
*/

       new Img2Ascii()
                .bitmap(image)
                .quality(2) // 1 - 5
                .color(true)
                .convert(new Img2Ascii.Listener() {
                    @Override
                    public void onProgress(int percentage) {
                        asciiTextView.setText(String.valueOf(percentage) + " %");
                        Log.d(TAG, String.valueOf(percentage) + " %");
                    }
                    @Override
                    public void onResponse(Spannable text) {
                        asciiTextView.setText(text);
                    }
                });


    }
}
