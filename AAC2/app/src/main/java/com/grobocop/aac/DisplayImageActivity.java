package com.grobocop.aac;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DisplayImageActivity extends AppCompatActivity {

    public String Url = "http://192.168.0.132";
    private String imagePath;
    private SubsamplingScaleImageView imgView;
    private Response imageResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);


        setContentView(R.layout.activity_display_image);

        imagePath = getIntent().getStringExtra(MainActivity.EXTRA_IMAGE_PATH);
        Bitmap image = BitmapFactory.decodeFile(imagePath);
        int nh = (int) (image.getHeight() * (512.0 / image.getWidth()));
        imgView = (SubsamplingScaleImageView) findViewById(R.id.imageView);
        Bitmap scaled = Bitmap.createScaledBitmap(image, 512, nh, true);
        imgView.setImage(ImageSource.bitmap(image));


        Button convertButton = (Button) findViewById(R.id.ConvertImageButton);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MediaType MEDIA_TYPE_MARKDOWN
                        = MediaType.parse("image/png");

                final OkHttpClient client = new OkHttpClient();
                File file = new File(imagePath);
                String content_type = getMimeType(imagePath);
                RequestBody fileBody = RequestBody.create(MediaType.parse(content_type),file);

                RequestBody body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("type",content_type)
                        .addFormDataPart("uploaded_file","image",fileBody)
                        .build();


                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(Url)
                        .post(body)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        imageResponse = response;
                        DisplayImageActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    String responseData = imageResponse.body().string();
                                    JSONObject jsonResponse = new JSONObject(responseData);
                                    String imageUrl;
                                    imageUrl = jsonResponse.getString("url");
                                    imgView.setImage(ImageSource.resource(R.color.transparent));
                                    Glide.with(imgView.getContext())
                                            .asBitmap()
                                            .load(imageUrl)
                                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                                            .into(new SimpleTarget<Bitmap>() {
                                                @Override
                                                public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                                                    imgView.setImage(ImageSource.bitmap(bitmap)); //For SubsampleImage
                                                }
                                            });




                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });


                    }
                });


            }
        });


    }

    private  String  getMimeType(String filePath){
        String extension = MimeTypeMap.getFileExtensionFromUrl(filePath);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }
}
