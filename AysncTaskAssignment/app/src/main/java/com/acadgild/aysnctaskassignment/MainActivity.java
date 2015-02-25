package com.acadgild.aysnctaskassignment;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.widget.Button;
import android.widget.ProgressBar;


public class MainActivity extends Activity {

    public Button button;
    public ProgressBar progressBar, progressBar2, progressBar3, progressBar4;
    public ParallelDownload task, task2, task3, task4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar3 = (ProgressBar) findViewById(R.id.progressBar3);
        progressBar4 = (ProgressBar) findViewById(R.id.progressBar4);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                task = new ParallelDownload(progressBar);
                RunInParallel(task);
                task2 = new ParallelDownload(progressBar2);
                RunInParallel(task2);
                task3 = new ParallelDownload(progressBar3);
                RunInParallel(task3);
                task4 = new ParallelDownload(progressBar4);
                RunInParallel(task4);
            }
        });
    }

    public class ParallelDownload extends AsyncTask<Void, Integer, Void> {

        ProgressBar myProgressBar;

        public ParallelDownload(ProgressBar bar) {
            myProgressBar = bar;
        }

        @Override
        protected Void doInBackground(Void... params) {

            for (int i=0; i<101; i++) {
                publishProgress(i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            myProgressBar.setProgress(values[0]);
        }
    }

    public void RunInParallel(ParallelDownload task) {
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

}
