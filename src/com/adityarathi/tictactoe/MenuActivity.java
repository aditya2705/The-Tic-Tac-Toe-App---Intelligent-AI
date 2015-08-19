package com.adityarathi.tictactoe;



import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MenuActivity extends Activity{
	
	private int soundint=1,shakeint=1;
	
	private AlertDialog _alertDialogTwo=null;
	private AlertDialog _alertDialogStats=null;
	private AlertDialog _alertDialogOptions=null;
	private AlertDialog _alertDialogExit=null;
	private AlertDialog _alertDialogAbout=null;
	private View layout1,layout2,layout3,layout4,layout5;
	private String easyxwins,easyowins,easydraws,mediumxwins,mediumowins,
	mediumdraws,hardxwins,hardowins,harddraws,soundstring,shakestring;
	
	private SharedPreferences Speasyxwins,Speasyowins,Speasydraws,Spmediumxwins,Spmediumowins,
	Spmediumdraws,Sphardxwins,Sphardowins,Spharddraws,SoundSet,ShakeSet;
	
	
	private String mxwins,mowins,mdraws;
	SharedPreferences Spmxwins,Spmowins,Spmdraws;
	protected TextView easyxwinsone;
	protected TextView easyowinsone;
	protected TextView easydrawsone;
	protected TextView mediumxwinsone;
	protected TextView mediumowinsone;
	protected TextView mediumdrawsone;
	protected TextView hardxwinsone;
	protected TextView hardowinsone;
	protected TextView harddrawsone;
	protected TextView mxwinstwo;
	protected TextView mowinstwo;
	protected TextView mdrawstwo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		

		
		 	Speasyxwins = getSharedPreferences("EASYXWINS", 0);
	        Speasyowins = getSharedPreferences("EASYOWINS", 0);
	        Speasydraws = getSharedPreferences("EASYDRAWS", 0);
	        Spmediumxwins = getSharedPreferences("MEDIUMXWINS", 0);
	        Spmediumowins = getSharedPreferences("MEDIUMOWINS", 0);
	        Spmediumdraws = getSharedPreferences("MEDIUMDRAWS", 0);
	        Sphardxwins = getSharedPreferences("HARDXWINS", 0);
	        Sphardowins = getSharedPreferences("HARDOWINS", 0);
	        Spharddraws = getSharedPreferences("HARDDRAWS", 0);
	        Spmxwins = getSharedPreferences("MXWINS", 0);
	        Spmowins = getSharedPreferences("MOWINS", 0);
	        Spmdraws = getSharedPreferences("MDRAWS", 0);
	        
	        SoundSet = getSharedPreferences("SOUNDSWITCH", 0);    
	        soundint = SoundSet.getInt(soundstring,1);
	        
	        ShakeSet = getSharedPreferences("SHAKESWITCH", 0);
	        shakeint = ShakeSet.getInt(shakestring,1);
	        
			Button stats = (Button)findViewById(R.id.statsbutton);
			Button twoPlayer = (Button) findViewById(R.id.twoplayer);
			Button onePlayer = (Button) findViewById(R.id.oneplayer);
			Button menuOptions = (Button)findViewById(R.id.options);
			
			_alertDialogAbout = new AlertDialog.Builder(this).create();
	        LayoutInflater inflater4 = this.getLayoutInflater();
	        layout4=inflater4.inflate(R.layout.aboutus, (ViewGroup)findViewById(R.id.dialog_about));
			_alertDialogAbout.setView(layout4);
			
			_alertDialogOptions = new AlertDialog.Builder(this).create();
	        LayoutInflater inflater2 = this.getLayoutInflater();
	        layout2=inflater2.inflate(R.layout.custom_options, (ViewGroup)findViewById(R.id.dialog_options));
	        
	        Button about = (Button) layout2.findViewById(R.id.AboutUs);
	        about.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					_alertDialogAbout.show();
					WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
			        Window window = _alertDialogAbout.getWindow();
			        lp.copyFrom(window.getAttributes());
			        //This makes the dialog take up the full width
			        lp.width = WindowManager.LayoutParams.FILL_PARENT;
			        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
			        window.setAttributes(lp);
				}
			});
	        
	        final Button reset = (Button)layout2.findViewById(R.id.Reset);
	        reset.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					SharedPreferences.Editor editorexw = Speasyxwins.edit();
			        SharedPreferences.Editor editoreow = Speasyowins.edit();
			        SharedPreferences.Editor editoredr = Speasydraws.edit();
			        SharedPreferences.Editor editormxw = Spmediumxwins.edit();
			        SharedPreferences.Editor editormow = Spmediumowins.edit();
			        SharedPreferences.Editor editormdr = Spmediumdraws.edit();
			        SharedPreferences.Editor editorhxw = Sphardxwins.edit();
			        SharedPreferences.Editor editorhow = Sphardowins.edit();
			        SharedPreferences.Editor editorhdr = Spharddraws.edit();
			    	//MUL
			        SharedPreferences.Editor editormxwins = Spmxwins.edit();
			        SharedPreferences.Editor editormowins = Spmowins.edit();
			        SharedPreferences.Editor editormdraws = Spmdraws.edit();
			    	//
			    	editorexw.putInt(easyxwins, 0);
			    	editoreow.putInt(easyowins, 0);
			    	editoredr.putInt(easydraws, 0);
			    	editormxw.putInt(mediumxwins, 0);
			    	editormow.putInt(mediumowins, 0);
			    	editormdr.putInt(mediumdraws, 0);
			    	editorhxw.putInt(hardxwins, 0);
			    	editorhow.putInt(hardowins, 0);
			    	editorhdr.putInt(harddraws, 0);
			    	//MUL
			    	editormxwins.putInt(mxwins, 0);
			    	editormowins.putInt(mowins, 0);
			    	editormdraws.putInt(mdraws, 0);
			    	//
			    	editorexw.commit();
			    	editoreow.commit();
			    	editoredr.commit();
			    	editormxw.commit();
			    	editormow.commit();
			    	editormdr.commit();
			    	editorhxw.commit();
			    	editorhow.commit();
			    	editorhdr.commit();
			    	
			    	//MUL
			    	editormxwins.commit();
					editormowins.commit();
					editormdraws.commit();
			   	//
			    	Toast.makeText(getApplicationContext(), "RESET SUCCESSFUL", Toast.LENGTH_SHORT).show();
					
				}
			});
	        final ToggleButton sound = (ToggleButton)layout2.findViewById(R.id.soundToggle);
	        sound.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					SharedPreferences.Editor editsound = SoundSet.edit();
					
					if(sound.isChecked())
					{
						soundint=1;
					}
					else
					{
						soundint=0;
					}
					editsound.putInt(soundstring, soundint);
					editsound.commit();
					
				}
			});
	        
	        final ToggleButton shake = (ToggleButton)layout2.findViewById(R.id.shakeToggle);
	        shake.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					SharedPreferences.Editor editshake = ShakeSet.edit();
					
					if(shake.isChecked())
					{
						shakeint=1;
					}
					else
					{
						shakeint=0;
					}
					editshake.putInt(shakestring, shakeint);
					editshake.commit();
				}
			});
	        
	        
	        
	        
	        
	        AlertDialog.Builder builder=new AlertDialog.Builder(this);
	        builder.setPositiveButton("Yeah Bored.", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					SharedPreferences.Editor editsound = SoundSet.edit();
					editsound.putInt(soundstring, soundint);
					SharedPreferences.Editor shakesound = ShakeSet.edit();
					editsound.putInt(shakestring, shakeint);
					
					SharedPreferences.Editor editorexw = Speasyxwins.edit();
			        SharedPreferences.Editor editoreow = Speasyowins.edit();
			        SharedPreferences.Editor editoredr = Speasydraws.edit();
			        SharedPreferences.Editor editormxw = Spmediumxwins.edit();
			        SharedPreferences.Editor editormow = Spmediumowins.edit();
			        SharedPreferences.Editor editormdr = Spmediumdraws.edit();
			        SharedPreferences.Editor editorhxw = Sphardxwins.edit();
			        SharedPreferences.Editor editorhow = Sphardowins.edit();
			        SharedPreferences.Editor editorhdr = Spharddraws.edit();
			    	//MUL
			        SharedPreferences.Editor editormxwins = Spmxwins.edit();
			        SharedPreferences.Editor editormowins = Spmowins.edit();
			        SharedPreferences.Editor editormdraws = Spmdraws.edit();
			    	//
			    	editorexw.putInt(easyxwins, Speasyxwins.getInt(easyxwins, 0));
			    	editoreow.putInt(easyowins, Speasyowins.getInt(easyowins, 0));
			    	editoredr.putInt(easydraws, Speasydraws.getInt(easydraws, 0));
			    	editormxw.putInt(mediumxwins, Spmediumxwins.getInt(mediumxwins, 0));
			    	editormow.putInt(mediumowins, Spmediumowins.getInt(mediumowins, 0));
			    	editormdr.putInt(mediumdraws, Spmediumdraws.getInt(mediumdraws, 0));
			    	editorhxw.putInt(hardxwins, Sphardxwins.getInt(hardxwins, 0));
			    	editorhow.putInt(hardowins, Sphardowins.getInt(hardowins, 0));
			    	editorhdr.putInt(harddraws, Spharddraws.getInt(harddraws, 0));
			    	//MUL
			    	editormxwins.putInt(mxwins, Spmxwins.getInt(mxwins, 0));
			    	editormowins.putInt(mowins, Spmowins.getInt(mowins, 0));
			    	editormdraws.putInt(mdraws, Spmdraws.getInt(mdraws, 0));
			    	//
			    	editorexw.commit();
			    	editoreow.commit();
			    	editoredr.commit();
			    	editormxw.commit();
			    	editormow.commit();
			    	editormdr.commit();
			    	editorhxw.commit();
			    	editorhow.commit();
			    	editorhdr.commit();
			    	
			    	//MUL
			    	editormxwins.commit();
					editormowins.commit();
					editormdraws.commit();
					editsound.commit();
					shakesound.commit();
					
					MenuActivity.this.finish();
					
					
				}
			});
	        builder.setNegativeButton("Nope!", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					arg0.cancel();
					
				}
			});
	        _alertDialogExit = builder.create();
	        LayoutInflater inflater3 = this.getLayoutInflater();
	        layout3=inflater3.inflate(R.layout.custom_exit, (ViewGroup)findViewById(R.id.dialog_exit));
	        
	       _alertDialogStats = new AlertDialog.Builder(this).create();
	        LayoutInflater inflater1 = this.getLayoutInflater();
	        layout1=inflater1.inflate(R.layout.dialog_stats, (ViewGroup)findViewById(R.id.statsid));
	        
	        _alertDialogTwo = new AlertDialog.Builder(this).create();
	        LayoutInflater inflater5= this.getLayoutInflater();
	        layout5=inflater5.inflate(R.layout.dialog_two, (ViewGroup)findViewById(R.id.twoid));
	        
	        _alertDialogTwo.setOnCancelListener(new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface arg0) {
					// TODO Auto-generated method stub
					arg0.dismiss();
				}
			});
	        
	        	twoPlayer.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					_alertDialogTwo.setView(layout5);
					_alertDialogTwo.show();
					WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
			        Window window = _alertDialogTwo.getWindow();
			        lp.copyFrom(window.getAttributes());
			        //This makes the dialog take up the full width
			        lp.width = WindowManager.LayoutParams.FILL_PARENT;
			        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
			        window.setAttributes(lp);
				}
			});
	        
	        
	        Button bluetoothPlay = (Button) layout5.findViewById(R.id.bluetoothPlay);
	        
	        bluetoothPlay.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					new Handler().postDelayed(new Runnable() {
						  @Override
						  public void run() {

							  Intent mainIntent = new Intent(MenuActivity.this, BluetoothRound.class);
							  MenuActivity.this.startActivity(mainIntent);

							  MenuActivity.this.finish();

						    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
						  }
						}, 600);
				}
			});
	        Button passPlay = (Button) layout5.findViewById(R.id.passPlay);
	        
	        passPlay.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					new Handler().postDelayed(new Runnable() {
						  @Override
						  public void run() {

							  Intent mainIntent = new Intent(MenuActivity.this, MultiplayerActivity.class);
							  MenuActivity.this.startActivity(mainIntent);

							  MenuActivity.this.finish();

						    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
						  }
						}, 600);
				}
			});
			
			
			
			

			onePlayer.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					new Handler().postDelayed(new Runnable() {
						  @Override
						  public void run() {

						    Intent mainIntent = new Intent(MenuActivity.this, SinglePlayer.class);
						    MenuActivity.this.startActivity(mainIntent);

						    MenuActivity.this.finish();

						    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
						  }
						}, 600);
				}
			});
			
			menuOptions.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					_alertDialogOptions.setView(layout2);
					if(soundint==1)
						sound.setChecked(true);
					else
						sound.setChecked(false);
					
					if(shakeint==1)
						shake.setChecked(true);
					else
						shake.setChecked(false);
					
					_alertDialogOptions.show();
					WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
			        Window window = _alertDialogOptions.getWindow();
			        lp.copyFrom(window.getAttributes());
			        //This makes the dialog take up the full width
			        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
			        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
			        window.setAttributes(lp);
					
				}
			});
			
			_alertDialogOptions.setOnCancelListener(new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface arg0) {
					// TODO Auto-generated method stub
					SharedPreferences.Editor editsound = SoundSet.edit();
					editsound.putInt(soundstring, soundint);
					SharedPreferences.Editor shakesound = ShakeSet.edit();
					editsound.putInt(shakestring, shakeint);
					editsound.commit();
					shakesound.commit();
					arg0.dismiss();
				}
			});
			
			
			stats.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					easyxwinsone = (TextView)layout1.findViewById(R.id.easyxwinsone);
			        easyowinsone = (TextView)layout1.findViewById(R.id.easyowinsone);
			        easydrawsone = (TextView)layout1.findViewById(R.id.easydrawsone);
			        mediumxwinsone = (TextView)layout1.findViewById(R.id.mediumxwinsone);
			        mediumowinsone = (TextView)layout1.findViewById(R.id.mediumowinsone);
			        mediumdrawsone = (TextView)layout1.findViewById(R.id.mediumdrawsone);
			        hardxwinsone = (TextView)layout1.findViewById(R.id.hardxwinsone);
			        hardowinsone = (TextView)layout1.findViewById(R.id.hardowinsone);
			        harddrawsone = (TextView)layout1.findViewById(R.id.harddrawsone);
			        
			        mxwinstwo = (TextView)layout1.findViewById(R.id.mxwinstwo);
			        mowinstwo = (TextView)layout1.findViewById(R.id.mowinstwo);
			        mdrawstwo = (TextView)layout1.findViewById(R.id.mdrawstwo);
					
					easyxwinsone.setText(Speasyxwins.getInt(easyxwins, 0)+"");
			        easyowinsone.setText(Speasyowins.getInt(easyowins, 0)+"");
			        easydrawsone.setText(Speasydraws.getInt(easydraws, 0)+"");
			        mediumxwinsone.setText(Spmediumxwins.getInt(mediumxwins, 0)+"");
			        mediumowinsone.setText(Spmediumowins.getInt(mediumowins, 0)+"");
			        mediumdrawsone.setText(Spmediumdraws.getInt(mediumdraws, 0)+"");
			        hardxwinsone.setText(Sphardxwins.getInt(hardxwins, 0)+"");
			        hardowinsone.setText(Sphardowins.getInt(hardowins, 0)+"");
			        harddrawsone.setText(Spharddraws.getInt(harddraws, 0)+"");
			        
			        mxwinstwo.setText(Spmxwins.getInt(mxwins, 0)+"");
			        mowinstwo.setText(Spmowins.getInt(mowins, 0)+"");
			        mdrawstwo.setText(Spmdraws.getInt(mdraws, 0)+"");
			        
					_alertDialogStats.setView(layout1);
					_alertDialogStats.show();
					WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
			        Window window = _alertDialogStats.getWindow();
			        lp.copyFrom(window.getAttributes());
			        //This makes the dialog take up the full width
			        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
			        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
			        window.setAttributes(lp);
				}
			});
			
				
	
		}

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
	@Override
	public void onBackPressed() {
			_alertDialogExit.setView(layout3);
			_alertDialogExit.show();
			WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
	        Window window = _alertDialogExit.getWindow();
	        lp.copyFrom(window.getAttributes());
	        //This makes the dialog take up the full width
	        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
	        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
	        window.setAttributes(lp);
	}
	
	





}
