package com.example.grobocop.ascii_art_converter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.widget.TextView;

import com.bachors.img2ascii.Img2Ascii;

import org.w3c.dom.Text;

public class ConvertImageActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_image);

        String imagePath = getIntent().getStringExtra(MainActivity.EXTRA_IMAGE_PATH);
        Bitmap image = BitmapFactory.decodeFile(imagePath);

        textView = (TextView) findViewById(R.id.asciiText);

        int quality = 1; // 1 - 3

// convert image(bitmap) to ascii(string)
        new Img2Ascii()
                .bitmap(image)
                .quality(3) // 1 - 5
                //.color(true)
                .convert(new Img2Ascii.Listener() {
                    @Override
                    public void onProgress(int percentage) {
                        textView.setText(String.valueOf(percentage) + " %");
                    }
                    @Override
                    public void onResponse(Spannable text) {
                        textView.setText(text);
                    }
                });


    }
}
