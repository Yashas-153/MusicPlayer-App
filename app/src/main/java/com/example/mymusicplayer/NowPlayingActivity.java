package com.example.mymusicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.progressindicator.CircularProgressIndicator;


import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class NowPlayingActivity extends AppCompatActivity {
    int position;
    static MediaPlayer player;
    TextView nowplayingtext,displayName,overalltime,runningtime;
    ImageView albumPhoto ;
    ImageButton play;
    ImageButton next;
    ImageButton previous;
    SeekBar seekbar;


    ArrayList<File> files = new ArrayList<>();
    boolean isplaying = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing_activity);

        nowplayingtext = findViewById(R.id.nowPlayingText);
        play = findViewById(R.id.playpauseBtn);
        next = findViewById(R.id.nextBtn);
        previous = findViewById(R.id.previouBtn);
        displayName = findViewById(R.id.songName);
        overalltime = findViewById(R.id.overalltime);
        runningtime = findViewById(R.id.runningTime);
        Intent intent = getIntent();
        albumPhoto = findViewById(R.id.albumPhoto);

        Bundle bundle = intent.getExtras();
        files = (ArrayList) bundle.getParcelableArrayList("files");


        if (player != null) {
            player.release();
        }

        position = bundle.getInt("pos");
        String songName = files.get(position).getName().toString();
        Uri uri = Uri.parse(files.get(position).toString());
        //album art retrival
        getAlbumPhoto(uri);


        player = MediaPlayer.create(getApplicationContext(), uri);
        player.start();
        play.setImageResource(R.drawable.ic_baseline_pause_24);


        displayName.setText(songName);
        overalltime.setText(getEndTime(player.getDuration()));

        seekbar = findViewById(R.id.seekBar4);
        seekbar.setMax(player.getDuration());

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seekbar.setProgress(player.getCurrentPosition());

            }
        }, 0, 900);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                player.seekTo(progress);
                int min = player.getCurrentPosition() / 1000 / 60;
                int secs = (player.getCurrentPosition() / 1000) % 60;
                String time = "";
                time = time + min + ":";
                if (secs < 10) {
                    time = time + "0" + secs;
                    runningtime.setText(time);
                } else {
                    time = time + secs;
                    runningtime.setText(time);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //seekBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                /*if (player != null && player.isPlaying()) {
                    player.seekTo(seekBar.getProgress());
                }*/
            }
        });

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                next.performClick();
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (player.isPlaying()) {
                    play.setImageResource(R.drawable.play_btn);
                    player.pause();
                } else if (!player.isPlaying()) {
                    play.setImageResource(R.drawable.ic_baseline_pause_24);
                    player.start();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
                player.reset();

                position = (position + 1) % files.size();
                Uri uri = Uri.parse(files.get(position).toString());
                getAlbumPhoto(uri);
                player = MediaPlayer.create(getApplicationContext(), uri);
                player.start();
                String songName = files.get(position).getName().toString();
                displayName.setText(songName);
                overalltime.setText(getEndTime(player.getDuration()));
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
                player.reset();

                position = (position  - 1) % files.size();
                Uri uri = Uri.parse(files.get(position).toString());
                getAlbumPhoto(uri);
                player = MediaPlayer.create(getApplicationContext(), uri);
                String songName = files.get(position).getName().toString();
                player.start();
                displayName.setText(songName);
                overalltime.setText(getEndTime(player.getDuration()));
            }
        });
    }

    static String getEndTime(int duration){
        int min  = duration/1000/60;
        int secs = (duration/1000)% 60;
        String time = "";
        time = time + min + ":";
        if(secs < 10){
            time += "0";
        }
        time = time + secs;
        return time;

    }

    void getAlbumPhoto(Uri uri){
        MediaMetadataRetriever metaRetriver = new MediaMetadataRetriever();
        metaRetriver.setDataSource(String.valueOf(uri));
        try {
            byte[] art = metaRetriver.getEmbeddedPicture();
            Bitmap songImage = BitmapFactory.decodeByteArray(art, 0, art.length);
            albumPhoto.setImageBitmap(songImage);
        } catch (Exception e) {
            albumPhoto.setBackgroundColor(Color.GRAY);

        }

    }
    private void clearMediaPlayer() {
        player.stop();
        player.release();
        player = null;
    }



}