package com.adityarathi.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle sumeet) {
		super.onCreate(sumeet);
		setContentView(R.layout.splash_screen);
		
		try {
			Thread.sleep(800);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
				new Handler().postDelayed(new Runnable() {
					  @Override
					  public void run() {

					    Intent mainIntent = new Intent(SplashActivity.this, MenuActivity.class);
					    SplashActivity.this.startActivity(mainIntent);

					    SplashActivity.this.finish();

					    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
					  }
					}, 600);
				
			
		}	
	}
