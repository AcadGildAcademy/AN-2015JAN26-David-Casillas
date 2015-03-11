package com.acadgild.musicplayer;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class PlayMusic extends Service {

    MediaPlayer mp;

    @Override
    public void onCreate() {
        super.onCreate();

        mp = MediaPlayer.create(this, R.raw.donttellem);
        mp.start();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.stop();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
