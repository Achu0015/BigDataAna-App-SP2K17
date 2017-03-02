package com.example.achu.spark_android;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;
import com.google.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by ACHU on 3/1/2017.
 */

public class ImageClassificationActivity extends AppCompatActivity {

    TextView outputTextView;
    ImageView image;
    String Clarifai_API_ID = "h8omgfTgL52U60aXqhDvwgEz1UuXLTNWGZDjbIgP";
    String Clarifai_API_KEY = "WgytSd-O0bVCdfyZZSuzP9a2AB5F_D5tYKIPE2s1";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bitmap photoImage;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_classification);
        outputTextView = (TextView) findViewById(R.id.text_output);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2 = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        image = (ImageView) findViewById(R.id.image_to_analyze);

        Intent intent = getIntent();
        photoImage = intent.getParcelableExtra("Image");
        if (photoImage != null) {
            image.setImageBitmap(photoImage);
        } else {
            image.setImageResource(R.drawable.buddha);
        }
    }

    // For Clarifai API
    public void onClickClarifai(View v) {
        String getURL = "https://developer.clarifai.com/account/application?id=30987" +
                "key=h8omgfTgL52U60aXqhDvwgEz1UuXLTNWGZDjbIgP" +
                "WgytSd-O0bVCdfyZZSuzP9a2AB5F_D5tYKIPE2s1";//The API service URL

        BitmapDrawable bitmapDrawable = ((BitmapDrawable) image.getDrawable());
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageInByte = stream.toByteArray();
        ByteArrayInputStream bis = new ByteArrayInputStream(imageInByte);

        byte[] bytes;
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        try {
            while ((bytesRead = bis.read(buffer)) != -1) {
                bytearrayoutputstream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        bytes = bytearrayoutputstream.toByteArray();
        String encodedString = Base64.encodeToString(bytes, Base64.DEFAULT);

        OkHttpClient client = new OkHttpClient.Builder().retryOnConnectionFailure(true).build();
        try {
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/x-markdown"), encodedString);
            Request request = new Request.Builder().url(url).post(requestBody).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println(e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final JSONObject jsonResult;
                    final String result = response.body().string();

                    Log.d("okHttp", result);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            outputTextView.setText(result);
                        }
                    });

                }
            });
        } catch (Exception ex) {
            outputTextView.setText(ex.getMessage());
        }
    }

}

    public void onClickPhoto(View v) {
        Intent redirect = new Intent(ImageClassificationActivity.this, PhotoActivity.class);
        startActivity(redirect);

    }

    // For Spark API
    public void onClickSpark(View v) {
        String url = "http://10.205.0.1:8080/get_custom";
        //ImageView image = (ImageView) findViewById(R.id.image_to_analyze);

        BitmapDrawable bitmapDrawable = ((BitmapDrawable) image.getDrawable());
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageInByte = stream.toByteArray();
        ByteArrayInputStream bis = new ByteArrayInputStream(imageInByte);

        byte[] bytes;
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        try {
            while ((bytesRead = bis.read(buffer)) != -1) {
                bytearrayoutputstream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        bytes = bytearrayoutputstream.toByteArray();
        String encodedString = Base64.encodeToString(bytes, Base64.DEFAULT);

        OkHttpClient client = new OkHttpClient.Builder().retryOnConnectionFailure(true).build();
        try {
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/x-markdown"), encodedString);
            Request request = new Request.Builder().url(url).post(requestBody).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println(e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final JSONObject jsonResult;
                    final String result = response.body().string();

                    Log.d("okHttp", result);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            outputTextView.setText(result);
                        }
                    });

                }
            });
        } catch (Exception ex) {
            outputTextView.setText(ex.getMessage());
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ImageClassification Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2.connect();
        AppIndex.AppIndexApi.start(client2, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client2, getIndexApiAction());
        client2.disconnect();
    }


}

