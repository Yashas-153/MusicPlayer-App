package com.example.mymusicplayer;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.Manifest.permission.PACKAGE_USAGE_STATS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    TextView recplayed;
    ImageButton nextbtn;
    ImageButton playbtn;
    Button mymusicsbtn;
    Button playlistbtn;


    public static final int permission = 1;
    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recplayed = (TextView) findViewById(R.id.RecentlyPlayedText);
        playbtn = (ImageButton) findViewById(R.id.RecentPlayButton);
        mymusicsbtn = (Button) findViewById(R.id.ViewAllSongsButton);
        playlistbtn = (Button) findViewById(R.id.ViewPlaylistButton);

        getPermission();
        mymusicsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MusicListActivity.class);
                startActivity(intent);
            }
        });

    }




    private void getPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle("Storage Permission Required");
                    alert.setMessage("External storage permission is required to access media files");
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                                    READ_EXTERNAL_STORAGE}, permission);
                        }
                    });

                    alert.setNegativeButton("Cancel", null);
                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                            READ_EXTERNAL_STORAGE}, permission);

                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case permission: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(getApplicationContext(), "Pemission Granted", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(getApplicationContext(), "Pemission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}