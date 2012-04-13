package com.pangaeaapps.ayumu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class WinActivity extends Activity {

	private TextView winText;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.winner);
        
        winText = (TextView) findViewById(R.id.txtWinner);
        winText.setText("Completed in " + Math.round(getIntent().getLongExtra("completionTime", 0) / 1000) + " seconds.");
        
	}
	
	@Override
	public void onBackPressed() {
		//super.onBackPressed();
		startActivity(new Intent(getBaseContext(), MenuActivity.class));
		finish();
	}
	
}
