package com.pangaeaapps.ayumu;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuActivity extends Activity {

	private Button btnPlay;
	private Button btnWatch;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnWatch = (Button) findViewById(R.id.btnWatch);
        
        btnPlay.setOnClickListener(new OnClickListener() { 
        	public void onClick(View v) {
        		Intent game = new Intent(getBaseContext(), AyumuActivity.class);
        		game.putExtra("startTime", System.currentTimeMillis());
        		startActivity(game);
        		finish();
			}
		});
        
        btnWatch.setOnClickListener(new OnClickListener() { 
        	public void onClick(View v) {
        		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=zJAH4ZJBiN8")));
			}
		});
        
	}
	
	@Override
	public void onBackPressed() {
		this.finish();
	}
	
}
