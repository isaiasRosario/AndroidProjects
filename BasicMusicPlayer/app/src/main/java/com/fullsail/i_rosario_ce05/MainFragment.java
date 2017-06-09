package com.fullsail.i_rosario_ce05;

// Isaias Rosario
// MDF3 - Term 1603
// Main Fragment Class


import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;


public class MainFragment extends Fragment {

    public static final String ACTION_UPDATE_UI = "com.fullsail.android.ACTION_UPDATE_UI";
    public static final String ACTION_UPDATE_SEEK = "com.fullsail.android.ACTION_UPDATE_SEEK";
    TextView trackText;
    ImageView image;
    BroadcastReceiver mReceiver;
    SeekBar seekBar;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);

        // View set up
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        trackText = (TextView)v.findViewById(R.id.trackTextView);
        image = (ImageView)v.findViewById(R.id.imageView);
        seekBar = (SeekBar)v.findViewById(R.id.seekBar);


        Switch loopSwitch = (Switch)v.findViewById(R.id.loopSwitch);
        loopSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    System.out.println("loop switch on");
                    Intent loopIntent = new Intent(MainActivity.ACTION_LOOP);
                    loopIntent.putExtra("isChecked", true);
                    getActivity().sendBroadcast(loopIntent);
                } else {
                    System.out.println("loop switch off");
                    Intent loopIntent = new Intent(MainActivity.ACTION_LOOP);
                    loopIntent.putExtra("isChecked", false);
                    getActivity().sendBroadcast(loopIntent);
                }
            }
        });

        Switch shuffleSwitch = (Switch)v.findViewById(R.id.shuffleSwitch);
        shuffleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (isChecked) {
                    System.out.println("shuffle switch on");
                    Intent shuffleIntent = new Intent(MainActivity.ACTION_SHUFFLE);
                    shuffleIntent.putExtra("isChecked", true);
                    getActivity().sendBroadcast(shuffleIntent);
                } else {
                    System.out.println("shuffle switch off");
                    Intent shuffleIntent = new Intent(MainActivity.ACTION_SHUFFLE);
                    shuffleIntent.putExtra("isChecked", false);
                    getActivity().sendBroadcast(shuffleIntent);
                }


            }
        });

        return v;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState == null){
            trackText.setText("Mike Jackson: Intro Song");
            Drawable artwork = getResources().getDrawable(R.drawable.album1);
            image.setImageDrawable(artwork);
        }

    }


    @Override
    public void onResume() {
        super.onResume();

        mReceiver = new UpdateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_UPDATE_UI);
        filter.addAction(ACTION_UPDATE_SEEK);
        getActivity().registerReceiver(mReceiver, filter);
    }


    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(mReceiver);
    }


    private class UpdateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent){

            if (intent.getAction().equals(ACTION_UPDATE_UI)){

                trackText.setText(intent.getStringExtra("trackText"));
                image.setImageURI(Uri.parse("android.resource://" + getActivity().getPackageName() + "/drawable/" + intent.getStringExtra("artWork")));
                seekBar.refreshDrawableState();
                seekBar.setMax(intent.getIntExtra("duration", 0));

                System.out.println("update ui receiver " + intent.getIntExtra("duration", 0));
            }else if (intent.getAction().equals(ACTION_UPDATE_SEEK)){
                seekBar.setProgress(intent.getIntExtra("position", 0));
                System.out.println("update seek receiver " + intent.getIntExtra("position", 0));
            }

        }
    }

}
