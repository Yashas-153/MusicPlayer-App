package com.example.mymusicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.view.View;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class MusicListActivity extends AppCompatActivity implements MusicListAdapter.OnNoteListner {
    MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
    ArrayList<String> songTitles;
    ArrayList<File> files;
    RecyclerView recyclerView;
    MediaPlayer player = new MediaPlayer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_list_acitivity);

        files = getAllFiles(Environment.getExternalStorageDirectory());
        songTitles = new ArrayList<>();

        for(File song: files){
            songTitles.add(song.getName().replace("mp3" , "").replace("wav", ""))   ;
            //thought of adding the Artistnames here.

        }

        recyclerView = findViewById(R.id.recyclerView);
        MusicListAdapter musicListAdapter = new MusicListAdapter(this , songTitles,this);
        recyclerView.setAdapter(musicListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }
    public ArrayList<File> getAllFiles(File file){
        ArrayList<File> songs = new ArrayList<>();
        File [] files = file.listFiles();
        for(File singlefile :files ){
            if(singlefile.isDirectory() && !singlefile.isHidden()){
                songs.addAll(getAllFiles(singlefile));
            }else{
                if(singlefile.getName().endsWith("mp3") || singlefile.getName().endsWith("wav")){
                    songs.add(singlefile);
                }
            }
        }
        return songs;
    }

    @Override
    public void onClick(int position) {
        songTitles.get(position);
        //player.reset();
        //Uri uri = Uri.parse(files.get(position).toString());
        //player = MediaPlayer.create(getApplicationContext(), uri);
        //player.start();
        startActivity(new Intent(getApplicationContext(),NowPlayingActivity.class).putExtra("files", files).putExtra("pos",position));

}
}