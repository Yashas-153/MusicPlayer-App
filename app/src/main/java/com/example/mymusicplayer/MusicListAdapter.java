package com.example.mymusicplayer;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.io.File;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.util.ArrayList;

public class    MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.MusicViewHolder> {
    Context ctx;
    ArrayList<String> songTitles = new ArrayList<>();
    private OnNoteListner onNoteListner;
    public MusicListAdapter(Context context, ArrayList<String> songtitles,OnNoteListner onNoteListner){
        songTitles = songtitles;
        ctx = context;
        this.onNoteListner = onNoteListner;
       ;
    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.musiclistview,parent,false);
        return new MusicViewHolder(view,onNoteListner);
    }

    @Override
    public void onBindViewHolder(@NonNull  MusicListAdapter.MusicViewHolder holder, int position) {
        holder.songTitle.setText(songTitles.get(position));
        //holder.artistName.setText(artistNames.get(position));
    }

    @Override
    public int getItemCount() {
        return songTitles.size();
    }

    public class MusicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView songTitle;
        //TextView artistName;
        OnNoteListner onNoteListner;

        public MusicViewHolder(@NonNull View itemView, OnNoteListner onNoteListner) {
            super(itemView);
            songTitle = itemView.findViewById(R.id.songTitle);
            songTitle.setTextColor(Color.parseColor("#FFFFFF"));
            //artistName = itemView.findViewById(R.id.artistName);
            itemView.setOnClickListener(this);
            this.onNoteListner = onNoteListner;
        }

        @Override
        public void onClick(View v){
            onNoteListner.onClick(getAdapterPosition());
        }
    }
    public interface OnNoteListner{
        void onClick(int position);
    }
}

