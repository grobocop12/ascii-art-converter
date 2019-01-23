package com.grobocop.aac;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DisplayImageActivity extends AppCompatActivity {

    public String Url = "http://192.168.0.132";
    private String imagePath;
    private SubsamplingScaleImageView imgView;
    private Response imageResponse;
    private Bitmap convertedBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);


        setContentView(R.layout.activity_display_image);

        imagePath = getIntent().getStringExtra(MainActivity.EXTRA_IMAGE_PATH);
        Bitmap image = BitmapFactory.decodeFile(imagePath);

        imgView = (SubsamplingScaleImageView) findViewById(R.id.imageView);

        imgView.setImage(ImageSource.bitmap(image));


        Button convertButton = (Button) findViewById(R.id.ConvertImageButton);
        final Button saveButton = (Button) findViewById(R.id.SaveImageButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date currentTime = Calendar.getInstance().getTime();
                File f = new File(DisplayImageActivity.this.getCacheDir(), currentTime.toString());
                try {
                    f.createNewFile();

                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    convertedBitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
                    byte[] bitmapdata = bos.toByteArray();

                    FileOutputStream fos = new FileOutputStream(f);
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();
                    MediaStore.Images.Media.insertImage(getContentResolver(),f.getAbsolutePath(),f.getName(),f.getName());
                    Toast.makeText(DisplayImageActivity.this,"Image saved at: " + f.getAbsolutePath(),Toast.LENGTH_LONG).show();


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final OkHttpClient client = new OkHttpClient();
                File file = new File(imagePath);
                String content_type = getMimeType(imagePath);
                RequestBody fileBody = RequestBody.create(MediaType.parse(content_type), file);

                RequestBody body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("type", content_type)
                        .addFormDataPart("uploaded_file", "image", fileBody)
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

                                    Glide.with(imgView.getContext())
                                            .asBitmap()
                                            .load(Url + imageUrl)
                                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                                            .into(new SimpleTarget<Bitmap>() {
                                                @Override
                                                public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                                                    saveButton.setEnabled(true);
                                                    convertedBitmap = bitmap;
                                                    imgView.setImage(ImageSource.bitmap(convertedBitmap)); //For SubsampleImage
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

    private String getMimeType(String filePath) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(filePath);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }
}
