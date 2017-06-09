package com.fullsail.i_rosario_ce05;

// Isaias Rosario
// MDF3 - Term 1603
// Music Service Class

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;

import java.io.IOException;

public class MusicService extends Service {

    public static final String ACTION_BACK = "com.fullsail.android.ACTION_BACK";
    public static final String ACTION_NEXT = "com.fullsail.android.ACTION_NEXT";

    MediaPlayer mPlayer;


    public class MusicServiceBinder extends Binder {
        public MusicService getService(){
            return MusicService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPlayer = new MediaPlayer();

        System.out.println("on create");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        System.out.println("on start command");
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("on bind");

        return new MusicServiceBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("on unbind");
        mPlayer.release();
        mPlayer = null;
        stopForeground(true);
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("on destroy");
    }

    public void play(String _track){

        try {
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            Uri uri = Uri.parse(_track);
            mPlayer.setDataSource(this, uri);
            mPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mPlayer.start();

    }

    public void pause(){
        mPlayer.pause();
        stopForeground(true);
        System.out.println("pause");

    }
    public void back(){
        System.out.println("back");
        mPlayer.reset();
        stopForeground(true);
    }
    public void forward(){
        System.out.println("forward");
        mPlayer.reset();
        stopForeground(true);

    }

    // Create notification method for the current cong playing
    public void sendNotification(String _title, String _text, String _uri) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(_title)
                .setContentText(_text)
                .setWhen(System.currentTimeMillis())
                .setOngoing(true);
        Uri imageUri = Uri.parse(_uri);
        Bitmap b = null;
        try {
            b = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        builder.setLargeIcon(b);
        NotificationCompat.BigTextStyle bigStyle = new NotificationCompat.BigTextStyle();
        bigStyle.bigText(_text);
        bigStyle.setBigContentTitle(_title);
        bigStyle.setSummaryText("Music Player CE05");


        Intent nextIntent = new Intent(MainActivity.ACTION_NEXT);
        PendingIntent pendingNext = PendingIntent.getBroadcast(this, 0, nextIntent, 0);

        Intent backIntent = new Intent(MainActivity.ACTION_BACK);
        PendingIntent pendinBack = PendingIntent.getBroadcast(this, 0, backIntent, 0);

        builder.setStyle(bigStyle).
                addAction(android.R.drawable.ic_media_previous,null,pendinBack).
                addAction(android.R.drawable.ic_media_next, null, pendingNext);

        Intent startIntent = new Intent(this, MainActivity.class);
        startIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, startIntent, 0);
        builder.setContentIntent(contentIntent);
        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        startForeground(1, notification);
        notificationManager.notify(1, notification);
    }

}
