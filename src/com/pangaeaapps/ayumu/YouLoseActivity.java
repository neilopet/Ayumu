package com.pangaeaapps.ayumu;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

public class YouLoseActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.loser);
        
        MediaPlayer mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.lose);
        mediaPlayer.start(); 
	}
	
	@Override
	public void onBackPressed() {
		//super.onBackPressed();
		startActivity(new Intent(getBaseContext(), MenuActivity.class));
		finish();
	}
	
}
