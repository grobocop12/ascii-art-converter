package com.example.grobocop.ascii_art_converter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickTakePhoto(View v) {

    }

    public void onClickExploreFiles(View v) {
        Intent intent = new Intent(this, ExploreFilesActivity.class);
        startActivity(intent);
    }
}
