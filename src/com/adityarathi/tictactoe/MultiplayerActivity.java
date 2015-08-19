package com.adityarathi.tictactoe;



import java.util.Random;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MultiplayerActivity extends Activity implements AccelerometerListener{
	
	String WinDesc[]={"Thats a good win, ","You were pretty good, ","Awesome win! ","Nice played! ","Good win really, "};
	String LoseDesc[]={"Too bad.. ","Better luck next time, ","And you..seriously??! Huh?! ","Its not rocket science! C'mon ","Even luck can't save you, "};
	String DrawDesc[]={"You both suck!!","I hate such rounds!","Try something new..Somebody?!","Try playing with me noobs.","Thats the best you people got?","I expect more from humans"};
	String playerOne="Player 1",playerTwo="Player 2";
	private ImageView[][] _boxes = new ImageView[3][3];
	private BoxClick[][] _boxonclicks = new BoxClick[3][3];
	private AlertDialog _alertDialogMessage = null;
	private AlertDialog _alertDialogPlayer = null;
	private TicTacToe _ttt= new TicTacToe();
	private int oneScore = 0;
	private int twoScore = 0;
	private int draws = 0;
	private TextView endTitle,description;
	private View layout1;
	private ImageView iv;
	private Random rd = new Random();
	private int mxw=0,mow=0,mdr=0;
	private String mxwins,mowins,mdraws,soundstring,shakestring;
	private SharedPreferences Spmxwins,Spmowins,Spmdraws,SoundSet,ShakeSet;
	private SharedPreferences.Editor editormxwins,editormowins,editormdraws;
	private boolean turnX,alt;
	private int soundCall,shakeCall;
	private int shake=1,shakesupport=1,shakepop=0;
	
	boolean player2_thinking = false;
	final Handler _comHandler = new Handler();
	
	boolean  _otherplayerThinking(){ return player2_thinking;}
	public TicTacToe getttt(){ return _ttt;}
	
	
	
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_grid);  
        
        
        
        Spmxwins = getSharedPreferences("MXWINS", 0);
        Spmowins = getSharedPreferences("MOWINS", 0);
        Spmdraws = getSharedPreferences("MDRAWS", 0);
        
        editormxwins = Spmxwins.edit();
    	editormowins = Spmowins.edit();
    	editormdraws = Spmdraws.edit();
        
        mxw = Spmxwins.getInt(mxwins, 0);
        mow = Spmowins.getInt(mowins, 0);
        mdr = Spmdraws.getInt(mdraws, 0);
        
        SoundSet = getSharedPreferences("SOUNDSWITCH",0);
     	soundCall = SoundSet.getInt(soundstring, 1);
     	
     	ShakeSet = getSharedPreferences("SHAKESWITCH",0);
     	shakeCall = ShakeSet.getInt(shakestring, 1);
        
        _alertDialogPlayer = new AlertDialog.Builder(this).create();
        LayoutInflater inflater1 = this.getLayoutInflater();
        _alertDialogPlayer.setView(inflater1.inflate(R.layout.dialog_multiplayer, null));
        _alertDialogPlayer.setButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                    	EditText et1 = (EditText) _alertDialogPlayer.findViewById(R.id.playerOne);
                    	EditText et2 = (EditText) _alertDialogPlayer.findViewById(R.id.playerTwo);
                    	RadioButton firstX = (RadioButton) _alertDialogPlayer.findViewById(R.id.firstX);
                    	RadioButton firstO = (RadioButton) _alertDialogPlayer.findViewById(R.id.firstO);
                    	RadioButton alternate = (RadioButton) _alertDialogPlayer.findViewById(R.id.alternate2); 
                    	playerOne= et1.getText().toString();
                    	playerTwo= et2.getText().toString();
                    	if(firstX.isChecked())
                    	{
                        	turnX=true;
                        	alt=false;
                    	}
                    	else if(firstO.isChecked())
                    	{
                        	turnX=false;
                        	alt=false;
                    	}
                    	else if(alternate.isChecked())
                    	{
                    		alt=true;
                    		turnX=true;
                    	}
                    	
                    	if(playerOne.isEmpty())
	                    playerOne="Player 1";
	                    	
	                    if(playerTwo.isEmpty())
	                    playerTwo="Player 2";
                    	GraphicInterface();
        		        startNewGame();
                    }
                });
        
        _alertDialogPlayer.setOnCancelListener(new OnCancelListener() {
    		
    		@Override
    		public void onCancel(DialogInterface arg0) {
    			// TODO Auto-generated method stub
    			_alertDialogPlayer.dismiss();
    			
    			if(shakepop==0)
    			{
    				Intent originalIntent = new Intent().setClass(MultiplayerActivity.this, MenuActivity.class);
    				startActivityForResult(originalIntent, 1);
    				finish();
    			}
    			else
    			{
    				shakepop=0;
    			}
    		}
    	});
        
        _alertDialogPlayer.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = _alertDialogPlayer.getWindow();
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        
        
    }
    
    
    
    @SuppressWarnings("deprecation")
	public void GraphicInterface()
    {
    
        
    
    	
    	_boxes[0][0] = (ImageView)findViewById(R.id.otile11);
    	_boxes[0][1] = (ImageView)findViewById(R.id.otile12);
    	_boxes[0][2] = (ImageView)findViewById(R.id.otile13);
    	_boxes[1][0] = (ImageView)findViewById(R.id.otile21);
    	_boxes[1][1] = (ImageView)findViewById(R.id.otile22);
    	_boxes[1][2] = (ImageView)findViewById(R.id.otile23);
    	_boxes[2][0] = (ImageView)findViewById(R.id.otile31);
    	_boxes[2][1] = (ImageView)findViewById(R.id.otile32);
    	_boxes[2][2] = (ImageView)findViewById(R.id.otile33);
    	
    	
        
        
        
     
        
    
    _alertDialogMessage = new AlertDialog.Builder(this).create();
    LayoutInflater inflater3 = this.getLayoutInflater();
    layout1 = inflater3.inflate(R.layout.custom_dialog,
            (ViewGroup) findViewById(R.id.dialog_root));
    endTitle = (TextView)layout1.findViewById(R.id.endTitle);
    description = (TextView)layout1.findViewById(R.id.description);
    iv = (ImageView)layout1.findViewById(R.id.thumb);
    
    _alertDialogMessage.setButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                	LayoutInflater inflater2 = getLayoutInflater();
                	View layout = inflater2.inflate(R.layout.custom_toast,
                	                               (ViewGroup) findViewById(R.id.toast_layout_root));
                	ImageView player1 = (ImageView) layout.findViewById(R.id.playerFirst);
                	ImageView player2 = (ImageView) layout.findViewById(R.id.playerSecond);
                	TextView text1 = (TextView) layout.findViewById(R.id.score);
                	TextView text2 = (TextView) layout.findViewById(R.id.draws);
                	
                	player1.setImageResource(R.drawable.xanim0040);
                	player2.setImageResource(R.drawable.oanim0040);
                	
                	text1.setText(oneScore+" - "+twoScore);
                	text2.setText("Draws: "+draws);
                	
                	

                	Toast toast = new Toast(getApplicationContext());
                	toast.setGravity(Gravity.TOP, 0, 0);
                	toast.setDuration(Toast.LENGTH_SHORT-400);
                	toast.setView(layout);
                	toast.show();
                	
                	
                	if(alt)
                	{
                		if(turnX)
                		turnX=false;
                		else
                		turnX=true;
                			
                	}
                	
                    resetGui();
                }
            });
    
    _alertDialogMessage.setOnCancelListener(new OnCancelListener() {
		
		@Override
		public void onCancel(DialogInterface arg0) {
			// TODO Auto-generated method stub
			resetGui();
		}
	});
    
   
    
   
}

    
    public void startNewGame()
    {
        if(shake==1&&shakesupport==1)
        {
        	shake=0;
        	if(shakeCall==1)
        	Toast.makeText(getApplicationContext(), "SHAKE device to start a new game", Toast.LENGTH_SHORT).show();
        }
        resetGui();
    }
    
    public void resetGui()
    {   
    	
    	player2_thinking = false;
    	
    	
                
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
            {
                _boxonclicks[i][j] = new BoxClick(this, _boxes[i][j], i, j,turnX,soundCall);
                _boxes[i][j].setOnClickListener(_boxonclicks[i][j]);
                _boxes[i][j].setImageResource(R.drawable.blank);
            }

        _ttt.reset();
       
    }
    
    
    
    public void boxNowSelected(BoxClick clk)
    {
    	
    	final MediaPlayer mp2 = MediaPlayer.create(MultiplayerActivity.this, R.raw.whistle);
    	
    	 if(_ttt.theWinner() != ' ')
         {
    		 
             
             if(_ttt.theWinner() == 'X')
             {
            	 oneScore++;	 
                 
            	 endTitle.setText("WINNER: "+playerOne.toUpperCase());
                 description.setText(WinDesc[rd.nextInt(5)]+playerOne+"... .... "+LoseDesc[rd.nextInt(5)]+playerTwo+".");
                 iv.setImageResource(R.drawable.tup);
             }
             else if(_ttt.theWinner() == 'O')
             {
            	 twoScore++;
            	 
            	 endTitle.setText("WINNER: "+playerTwo.toUpperCase());
            	 description.setText(WinDesc[rd.nextInt(5)]+playerTwo+"... .... "+LoseDesc[rd.nextInt(5)]+playerOne+".");
            	 iv.setImageResource(R.drawable.tup);
             }
             else
             {
            	 draws++;
            	 endTitle.setText("LOSERS: BOTH");
            	 description.setText(DrawDesc[rd.nextInt(6)]);
            	 iv.setImageResource(R.drawable.tdown);
                 
             }
             roundEnds();
             

             final Handler dialogHandler = new Handler(){
            	 @Override
            	public void handleMessage(Message msg) {
            		// TODO Auto-generated method stub
            		super.handleMessage(msg);
            		
            		if(soundCall==1)
               		 mp2.start();
            		
            		_alertDialogMessage.setView(layout1);
               	 	_alertDialogMessage.show();
               	 	WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
               	 	Window window = _alertDialogMessage.getWindow();
               	 	lp.copyFrom(window.getAttributes());
               	 	//This makes the dialog take up the full width
               	 	lp.width = WindowManager.LayoutParams.FILL_PARENT;
               	 	lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
               	 	window.setAttributes(lp);
               	 	return;
            	}
             };
             
             new Thread(){
            	 @Override
            	public void run() {
            		// TODO Auto-generated method stub
            		super.run();
            		
            		try {
						Thread.sleep(650);
						dialogHandler.sendEmptyMessage(0);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            		
            	}
             }.start();
            
             
         }
    	 
    	
    }
	private void roundEnds() {
		// TODO Auto-generated method stub
		
		
		if(_ttt.theWinner()=='X')
		{
			mxw++;
			editormxwins.putInt(mxwins, mxw);
		}
		else if(_ttt.theWinner()=='O')
		{
			mow++;
			editormowins.putInt(mowins, mow);
		}
		else if(_ttt.theWinner()!=' ')
		{
			mdr++;
			editormdraws.putInt(mdraws, mdr);
		}
		editormxwins.commit();
		editormowins.commit();
		editormdraws.commit();
		
		
	}
	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent originalIntent = new Intent().setClass(MultiplayerActivity.this, MenuActivity.class);
		startActivityForResult(originalIntent, 1);
		finish();
	}
    
	
	
	
	
	
	public void onAccelerationChanged(float x, float y, float z) {
        // TODO Auto-generated method stub
         
    }
 
    public void onShake(float force) {
         
        // Do your stuff here
    	shakepop=1;
    	if(shakeCall==1)
        _alertDialogPlayer.show();
        // Called when Motion Detected
    //    Toast.makeText(getBaseContext(), "Motion detected", 
     //           Toast.LENGTH_SHORT).show();
         
    }
 
    @Override
    public void onResume() {
            super.onResume();
       //     Toast.makeText(getBaseContext(), "onResume Accelerometer Started", 
       //             Toast.LENGTH_SHORT).show();
             
            //Check device supported Accelerometer senssor or not
            if (AccelerometerManager.isSupported(this)) {
                 shakesupport=1;
                //Start Accelerometer Listening
                AccelerometerManager.startListening(this);
            }
    }
     
    @Override
    public void onStop() {
            super.onStop();
             
            //Check device supported Accelerometer senssor or not
            if (AccelerometerManager.isListening()) {
                 shakesupport=1;
                //Start Accelerometer Listening
                AccelerometerManager.stopListening();
                 
          //      Toast.makeText(getBaseContext(), "onStop Accelerometer Stoped", 
          //               Toast.LENGTH_SHORT).show();
            }
            
    }
     
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Sensor", "Service  distroy");
         
        //Check device supported Accelerometer senssor or not
        if (AccelerometerManager.isListening()) {
             shakesupport=1;
            //Start Accelerometer Listening
            AccelerometerManager.stopListening();
             
         //   Toast.makeText(getBaseContext(), "onDestroy Accelerometer Stoped", 
         //          Toast.LENGTH_SHORT).show();
        }
             
    }
    
    

    

}
