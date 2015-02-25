package com.acadgild.asynctaskassignment3;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends Activity {

    private ProgressBar progressBar;
    private EditText editText;
    private ImageView imageView;
    private Button button;
    private Bitmap bitmap = null;
    private int file_length = 0;
    private int downloadedsize = 0;
    private String sdCardPath = Environment.getExternalStorageDirectory().getAbsolutePath();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        editText = (EditText) findViewById(R.id.editText);
        imageView = (ImageView) findViewById(R.id.imageView);
        button = (Button) findViewById(R.id.button);

        editText.setText("http://upload.wikimedia.org/wikipedia/commons/6/66/Android_robot.png");

        final String s = editText.getText().toString();
        final String text = s.substring(s.lastIndexOf("."));
        final String imageName = s.substring(s.lastIndexOf("/"));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (text.equals(".png") || text.equals(".jpeg") || text.equals(".jpg") || text.equals(".gif")) {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(0);
                    new Thread() {
                        public void run() {
                            try {
                                String address = s;
                                URL url = new URL(address);
                                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                connection.setDoInput(true);
                                connection.connect();
                                InputStream is = connection.getInputStream();
                                file_length = connection.getContentLength();
                                if (file_length <= 0) throw new RuntimeException("No inputstream");
                                if (is == null) throw new RuntimeException("null");
                                FileOutputStream output = new FileOutputStream(sdCardPath + imageName);
                                byte[] data = new byte[4096];
                                int count = 0;
                                sendMessage(0);
                                do {
                                    count = is.read(data);
                                    if (count == -1) {
                                        break;
                                    }
                                    output.write(data,0,count);
                                    downloadedsize += count;
                                    sendMessage(1);
                                } while (true);
                                sendMessage(2);
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();


                }
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg){
        switch (msg.what) {
            case 0:
                progressBar.setMax(file_length);
                break;
            case 1:
                progressBar.setProgress(downloadedsize);
                break;
            case 2:
                String s = editText.getText().toString();
                String imageName = s.substring(s.lastIndexOf("/"));
                String my_image_path = sdCardPath + imageName;
                BitmapFactory.Options options = new BitmapFactory.Options();
                bitmap = BitmapFactory.decodeFile(my_image_path, options);
                imageView.setImageBitmap(bitmap);
                progressBar.setVisibility(View.GONE);
                break;
        }
        super.handleMessage(msg);
        }
    };

    private void sendMessage(int i) {
        Message msg = new Message();
        msg.what = i;
        handler.sendMessage(msg);
    }
}