package com.example.karim.movieapp;

import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class video extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    String VideoURL = "http://www.sweet-cures.com/index.htm";
    YouTubePlayerView youTubePlayerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video);
        youTubePlayerView=(YouTubePlayerView)findViewById(R.id.view);
        youTubePlayerView.initialize("AIzaSyAljYbAk_rFrvvIe358unq1euZeU5aAhJQ",this);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
            youTubePlayer.loadVideo(getIntent().getStringExtra("site"));
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
}
