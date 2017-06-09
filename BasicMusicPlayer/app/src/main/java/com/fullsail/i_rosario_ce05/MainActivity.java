package com.fullsail.i_rosario_ce05;

// Isaias Rosario
// MDF3 - Term 1603
// Main Activity Class

import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection{

    public static final String ACTION_BACK = "com.fullsail.android.ACTION_BACK";
    public static final String ACTION_NEXT = "com.fullsail.android.ACTION_NEXT";
    public static final String ACTION_LOOP = "com.fullsail.android.ACTION_LOOP";
    public static final String ACTION_SHUFFLE = "com.fullsail.android.ACTION_SHUFFLE";

    MusicService mService;
    boolean mBound;
    boolean mShuffle;
    boolean mLoop;
    ArrayList<MusicTrack> trackInfo = new ArrayList<>();
    ArrayList<Integer>  countArr = new ArrayList<Integer>();
    int count = 0;
    String path;
    BroadcastReceiver mReceiver;
    Timer timer;
    TimerTask timerTask = new TimerTask(){

        @Override
        public void run() {

            runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    if (mService != null && mBound) {
                        int currentPosition = mService.mPlayer.getCurrentPosition() / 1000;
                        Intent updateSeek = new Intent(MainFragment.ACTION_UPDATE_SEEK);
                        updateSeek.putExtra("position", currentPosition);
                        sendBroadcast(updateSeek);
                        System.out.println(currentPosition);
                    }
                }
            });
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trackInfo.add(new MusicTrack("Mike Jackson", "Intro Song", "intro", "album1"));
        trackInfo.add(new MusicTrack("R-Pain", "Outro Song", "outro", "album2"));
        trackInfo.add(new MusicTrack("The Artist", "Middle Song", "middle", "album3"));

        path = "android.resource://" + this.getPackageName() + "/raw/";

        timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 1000);

        countArr.add(0);
        countArr.add(1);
        countArr.add(2);

        if (savedInstanceState == null){
            FragmentManager fragManager = getFragmentManager();
            fragManager.beginTransaction().replace(R.id.mainFrame, new MainFragment()).commit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        mReceiver = new ActionReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_BACK);
        filter.addAction(ACTION_NEXT);
        filter.addAction(ACTION_LOOP);
        filter.addAction(ACTION_SHUFFLE);
        registerReceiver(mReceiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    private class ActionReceiver extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent){
            boolean isChecked = intent.getBooleanExtra("isChecked",true);

            System.out.println("action received "+ isChecked);


            if (intent.getAction().equals(ACTION_NEXT)){
                forward();

                System.out.println("action next");

            }else if (intent.getAction().equals(ACTION_BACK)){
                back();

                System.out.println("action back");

            }else if (intent.getAction().equals(ACTION_LOOP)){
                mLoop = isChecked;

                System.out.println("action loop");

            }else if (intent.getAction().equals(ACTION_SHUFFLE)){
                mShuffle = isChecked;

                System.out.println("action shuffle");

            }

        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = new Intent(this, MusicService.class);

        if (id == R.id.playButton){

            if (!mBound){
                bindService(intent, this, BIND_AUTO_CREATE);

            }else {
                mService.mPlayer.start();
                mService.sendNotification(trackInfo.get(count).getArtist(),trackInfo.get(count).getArtist(),
                        "android.resource://" + getPackageName() + "/drawable/" + trackInfo.get(count).getArtwork());
            }

        }else if (id == R.id.pauseButton){
            if (mBound){
                mService.pause();
            }

        }else if (id == R.id.stopButton){

            if (mBound){
                unbindService(this);
                mBound = false;
            }

        }else if (id == R.id.backButton){
            back();

        } else if (id == R.id.forwardButton){
            forward();
        }
    }

    public void updateUI() {
        Intent intent = new Intent(MainFragment.ACTION_UPDATE_UI);
        intent.putExtra("trackText", trackInfo.get(count).getArtist() + ": "+trackInfo.get(count).getName());
        intent.putExtra("artWork", trackInfo.get(count).getArtwork());
        intent.putExtra("duration", mService.mPlayer.getDuration());
        sendBroadcast(intent);
        System.out.println("update ui");
    }


    public void back(){
        if (mBound && !mLoop){
            if (!mShuffle){
                if (count==0){
                    count = 2;
                    mService.back();
                    mService.play(path + trackInfo.get(count).getUri());
                    mService.sendNotification(trackInfo.get(count).getArtist(), trackInfo.get(count).getArtist(),
                            "android.resource://" + getPackageName() + "/drawable/" + trackInfo.get(count).getArtwork());

                } else {
                    count--;
                    mService.back();
                    mService.play(path + trackInfo.get(count).getUri());
                    mService.sendNotification(trackInfo.get(count).getArtist(), trackInfo.get(count).getArtist(),
                            "android.resource://" + getPackageName() + "/drawable/" + trackInfo.get(count).getArtwork());
                }

            }else {
                Random r = new Random();
                count = countArr.get(r.nextInt(countArr.size()));
                mService.back();
                mService.play(path + trackInfo.get(count).getUri());
                mService.sendNotification(trackInfo.get(count).getArtist(), trackInfo.get(count).getArtist(),
                        "android.resource://" + getPackageName() + "/drawable/" + trackInfo.get(count).getArtwork());
            }

            updateUI();

        }
    }

    public void forward(){
        if (mBound && !mLoop) {
            if (!mShuffle) {
                if (count == 2) {
                    count = 0;
                    mService.forward();
                    mService.play(path + trackInfo.get(count).getUri());
                    mService.sendNotification(trackInfo.get(count).getArtist(), trackInfo.get(count).getArtist(),
                            "android.resource://" + getPackageName() + "/drawable/" + trackInfo.get(count).getArtwork());
                } else {
                    count++;
                    mService.forward();
                    mService.play(path + trackInfo.get(count).getUri());
                    mService.sendNotification(trackInfo.get(count).getArtist(), trackInfo.get(count).getArtist(),
                            "android.resource://" + getPackageName() + "/drawable/" + trackInfo.get(count).getArtwork());
                }
            }else {

                Random r = new Random();
                count = countArr.get(r.nextInt(countArr.size()));
                mService.forward();
                mService.play(path + trackInfo.get(count).getUri());
                mService.sendNotification(trackInfo.get(count).getArtist(), trackInfo.get(count).getArtist(),
                        "android.resource://" + getPackageName() + "/drawable/" + trackInfo.get(count).getArtwork());

            }

            updateUI();
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder iBinder) {
        System.out.println("on service connected");
        MusicService.MusicServiceBinder binder = (MusicService.MusicServiceBinder)iBinder;
        mService = binder.getService();
        mBound = true;

        mService.mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                if (mLoop) {
                    mp.start();
                } else if (mShuffle) {
                    forward();
                } else {
                    forward();
                }

            }
        });

        mService.play("android.resource://" + this.getPackageName() + "/raw/" + trackInfo.get(count).getUri());
        mService.sendNotification(trackInfo.get(count).getArtist(), trackInfo.get(count).getArtist(),
                "android.resource://" + getPackageName() + "/drawable/" + trackInfo.get(count).getArtwork());

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        System.out.println("on service disconnected");
        timerTask.cancel();
        mBound = false;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);


        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){

            setContentView(R.layout.activity_main);
            FragmentManager fragManager = getFragmentManager();
            fragManager.beginTransaction().replace(R.id.mainFrame, new MainFragment()).commit();
            updateUI();


        }else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){

            setContentView(R.layout.activity_main);
            FragmentManager fragManager = getFragmentManager();
            fragManager.beginTransaction().replace(R.id.mainFrame, new MainFragment()).commit();
            updateUI();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBound){
            unbindService(this);
        }

    }
}
