package com.adityarathi.tictactoe;

import java.util.Random;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class SinglePlayer extends Activity implements AccelerometerListener{

	String WinDesc[]={"There was a an error actually. Nice advantage taken.","I did it on purpose to make you happy.. You own me after all. ",
		"Lets try one more time...I'll definately get you..", "Ok you win.. Flukes do happen.","Alrite you win! But you have to give me another chance!"};
	String LoseDesc[]={"Dont cry now, accept I am the master.","In your face. HueHueHue!","I am so awesome!!","One word for you..... SUCKER!","Why do I have to play against losers?!","I thought the TV was my only idiot friend.","Decrease the difficulty, your skills are going 'unmatched'."};
	String DrawDesc[]={"Would please play something new?!","I am getting pretty bored now...","Please don't force me to shutdown...fed up","Same'ol story."};
	String playerOne="Player 1",playerTwo="Computer";
	private ImageView[][] _boxes = new ImageView[3][3];
	private AlertDialog _alertDialogMessage = null;
	private AlertDialog _alertDialogPlayer = null;
	private TicTacToe _ttt= new TicTacToe();
	private boolean firstChance=true,secondChance=false,alt;
	private int _cpuRow = -1, _cpuCol = -1;
	private int difficulty=0;
	private int oneScore = 0;
	private int twoScore = 0;
	private int draws = 0;
	private int count=0;
	private boolean specialcase = true;
	private TextView endTitle,description;
	private View layout1;
	private ImageView iv;
	int exw=0,eow=0,edr=0;
	int mxw=0,mow=0,mdr=0;
	int hxw=0,how=0,hdr=0;
	private String easyxwins,easyowins,easydraws,mediumxwins,mediumowins,
	mediumdraws,hardxwins,hardowins,harddraws,soundstring,shakestring;
	private SharedPreferences Speasyxwins,Speasyowins,Speasydraws,Spmediumxwins,Spmediumowins,
	Spmediumdraws,Sphardxwins,Sphardowins,Spharddraws,SoundSet,ShakeSet;
	private int soundCall,shakeCall;
	private int turn;
	private int shake=1,shakesupport=0,shakepop=0;
	private AnimationDrawable anim;
	
	
	private int[] evenpts={0,2};
	private int[] oddpts={1,1};
	private int[][] pts={evenpts,oddpts};
	private int[][][] nonCor = {
								{{0,1},{1,2}},
								{{0,1},{1,0}},
								{{2,1},{1,0}},
								{{2,1},{1,2}}
								};
	Random rd=new Random();


	
	boolean comp_thinking = false;
	final Handler _cpuHandler = new Handler();
	
	
	boolean  _compThinking(){ return comp_thinking;}
	public TicTacToe getttt(){ return _ttt;}

	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.layout_grid);
	        
	        
	        
	        Speasyxwins = getSharedPreferences("EASYXWINS", 0);
	        Speasyowins = getSharedPreferences("EASYOWINS", 0);
	        Speasydraws = getSharedPreferences("EASYDRAWS", 0);
	        Spmediumxwins = getSharedPreferences("MEDIUMXWINS", 0);
	        Spmediumowins = getSharedPreferences("MEDIUMOWINS", 0);
	        Spmediumdraws = getSharedPreferences("MEDIUMDRAWS", 0);
	        Sphardxwins = getSharedPreferences("HARDXWINS", 0);
	        Sphardowins = getSharedPreferences("HARDOWINS", 0);
	        Spharddraws = getSharedPreferences("HARDDRAWS", 0);
	        
	        
	        
	     	exw=Speasyxwins.getInt(easyxwins, 0);
	        eow=Speasyowins.getInt(easyowins, 0);
	        edr=Speasydraws.getInt(easydraws, 0);
	        mxw=Spmediumxwins.getInt(mediumxwins, 0);
	        mow=Spmediumowins.getInt(mediumowins, 0);
	        mdr=Spmediumdraws.getInt(mediumdraws, 0);
	        hxw=Sphardxwins.getInt(hardxwins, 0);
	        how=Sphardowins.getInt(hardowins, 0);
	        hdr=Spharddraws.getInt(harddraws, 0);
	        
	        SoundSet = getSharedPreferences("SOUNDSWITCH",0);
	    	soundCall = SoundSet.getInt(soundstring, 1);
	    	
	    	ShakeSet = getSharedPreferences("SHAKESWITCH",0);
	    	shakeCall = ShakeSet.getInt(shakestring, 1);
	      

	        
	        _alertDialogPlayer = new AlertDialog.Builder(this).create();
	        LayoutInflater inflater1 = this.getLayoutInflater();
	        _alertDialogPlayer.setView(inflater1.inflate(R.layout.dialog_singleplayer, null));
	        
	        _alertDialogPlayer.setButton("OK", new DialogInterface.OnClickListener() {
	        	
	        		
	        	
	                    @Override
	                    public void onClick(DialogInterface dialog, int which)
	                    {
	                    	EditText et1 = (EditText) _alertDialogPlayer.findViewById(R.id.playerOne);
	                    	
	                    	RadioButton firstX = (RadioButton) _alertDialogPlayer.findViewById(R.id.turnCompX);
	                    	RadioButton firstO = (RadioButton) _alertDialogPlayer.findViewById(R.id.turnCompO);
	                    	RadioButton alternate = (RadioButton) _alertDialogPlayer.findViewById(R.id.alternate);
	                    	
	                    	RadioButton easy = (RadioButton) _alertDialogPlayer.findViewById(R.id.easy);
	                    	RadioButton medium = (RadioButton) _alertDialogPlayer.findViewById(R.id.medium);
	                    	RadioButton hard = (RadioButton) _alertDialogPlayer.findViewById(R.id.hard);
	                    	
	                    	if(firstX.isChecked())
	                    	{
	                    		turn=1;
	                    		alt=false;
	                    	}
	                    	else if(firstO.isChecked())
	                    	{
	                    		turn=2;
	                    		alt=false;
	                    	}
	                    	else if(alternate.isChecked())
	                    	{
	                    		turn=1;
	                    		alt=true;
	                    	}
	                    	
	                    	
	                    	if(easy.isChecked())
	                    	{
	                    		difficulty=0;
	                    	}
	                    	else 
	                    	{
	                    		if(medium.isChecked())
	                    		{
	                    			difficulty=1;
	                    		}
	                    		else if(hard.isChecked())
		                    	{
		                    		difficulty=2;
		                    	}
	                    	}
	                    	playerOne= et1.getText().toString();
	                    	
	                    	if(playerOne.isEmpty())
	                    	playerOne="You";
	                    	
	                    	
	                    	GraphicInterface();
	        		        try {
								startNewGame();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	                    }
	                });
	        
	        _alertDialogPlayer.setOnCancelListener(new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface arg0) {
					// TODO Auto-generated method stub
					_alertDialogPlayer.dismiss();
					if(shakepop==0)
					{
						Intent originalIntent = new Intent().setClass(SinglePlayer.this, MenuActivity.class);
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

                    	TextView text1 = (TextView) layout.findViewById(R.id.score);
                    	TextView text2 = (TextView) layout.findViewById(R.id.draws);
                    	text1.setText(oneScore+" - "+twoScore);
                    	text2.setText("Draws: "+draws);

                    	Toast toast = new Toast(getApplicationContext());
                    	toast.setGravity(Gravity.TOP, 0, 0);
                    	toast.setDuration(Toast.LENGTH_SHORT);
                    	toast.setView(layout);
                    	toast.show();
                        try {
                        	if(alt)
                        	{
                        		if(turn==1)
                        		turn=2;
                        		else
                        		turn=1;
                        	}
							resetGui();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    }
                });
        
        _alertDialogMessage.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface arg0) {
				// TODO Auto-generated method stub
			try {
				resetGui();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			}
		});
}
    
    public void startNewGame() throws Exception
    {
    	if(shake==1&&shakesupport==1)
        {
        	shake=0;
        	if(shakeCall==1)
        	Toast.makeText(getApplicationContext(), "SHAKE device to start a new game", Toast.LENGTH_SHORT).show();
        }
        resetGui();

    }
    
    public void resetGui() throws Exception
    {   
    	
    	comp_thinking=false;
    	firstChance=true;
    	secondChance=false;
    	specialcase = true;
    	count=0;
    	
    	
    	
    	
    	
    	if(turn==2)
    	{
    		new Thread(new Runnable() {
             	public void run()
             	{
                 	try {
                 	 Thread.sleep(100);
                	 cpuSelectMoveCompFirst();
                 } catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                 	_cpuHandler.post(new Runnable() {
                     	public void run()
                     	{
                         	new BoxClickSingle(SinglePlayer.this, _cpuRow, _cpuCol).onClick(null);
                     	}
                 	});
             	}
         	}).start();
    	}
    	
    	for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
            {
            	
            		_boxes[i][j].setOnClickListener(new BoxClickSingle(this,i,j));
            		_boxes[i][j].setImageResource(R.drawable.blank);
            		_boxes[i][j].setTag(new Integer(R.drawable.blank));
            }	 
        _ttt.reset();
  
    }
    
    
    
    public void boxNowSelected(BoxClickSingle clk)
    {
    	MediaPlayer mp1 = MediaPlayer.create(SinglePlayer.this, R.raw.dripsound);
    	final MediaPlayer mp2 = MediaPlayer.create(SinglePlayer.this, R.raw.whistle);
    	
    	
    	if(comp_thinking)return;
    	
    	 try
         {
             if(_ttt.getValue(clk.getRow(), clk.getCol()) != ' ')
             {
                 return;
             }
         }
         catch ( Exception ex ) { return; }
    	 
    	 
    	 if(turn==1)
    	 {	
    		if(_ttt.whoseTurnfirstX() == 'X')
         	{
    		 	if(soundCall==1)
    			 mp1.start();
    		 
    		 	_boxes[clk.getRow()][clk.getCol()].setImageResource(R.animator.animationx);
    		 	anim = (AnimationDrawable) _boxes[clk.getRow()][clk.getCol()].getDrawable();
    		 	anim.start();
         	}
         	else
         	{
        	 	if(soundCall==1)
        		 mp1.start();
        	 
        	 	_boxes[clk.getRow()][clk.getCol()].setImageResource(R.animator.animationo);
        	 	anim = (AnimationDrawable) _boxes[clk.getRow()][clk.getCol()].getDrawable();
        	 	anim.start();
         	}
         
         	try { _ttt.playMoveFirstX(clk.getRow(), clk.getCol()); }
         	catch (Exception ex) { finish(); }
    	 }
    	 else if(turn==2)
    	 {
    		if(_ttt.whoseTurnfirstO() == 'X')
          	{
     		 	if(soundCall==1)
     			 mp1.start();
     		 
     		 	_boxes[clk.getRow()][clk.getCol()].setImageResource(R.animator.animationx);
     		 	anim = (AnimationDrawable) _boxes[clk.getRow()][clk.getCol()].getDrawable();
     		 	anim.start();
          	}
          	else
          	{
         	 	if(soundCall==1)
         		 mp1.start();
         	 
         	 	_boxes[clk.getRow()][clk.getCol()].setImageResource(R.animator.animationo);
         	 	anim = (AnimationDrawable) _boxes[clk.getRow()][clk.getCol()].getDrawable();
         	 	anim.start();
          	}
          
          	try { _ttt.playMoveFirstO(clk.getRow(), clk.getCol()); }
          	catch (Exception ex) { finish(); }
    	 }
         
    	
    	
         if(_ttt.theWinner() != ' ')
         {
         	 
             
             if(_ttt.theWinner() == 'X')
             {
            	 oneScore++;
                 endTitle.setText("WINNER: "+playerOne.toUpperCase());
                 description.setText(WinDesc[rd.nextInt(5)]);
                 iv.setImageResource(R.drawable.tup);
             }
             else if(_ttt.theWinner() == 'O')
             {
            	 twoScore++;
            	 endTitle.setText("WINNER: "+playerTwo.toUpperCase());
            	 description.setText(LoseDesc[rd.nextInt(7)]);
            	 iv.setImageResource(R.drawable.tdown);
             }
             else
             {
            	 draws++;
            	 endTitle.setText("LOSER: ALMOST YOU");
            	 description.setText(DrawDesc[rd.nextInt(4)]);
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
    	 
    	 if(turn==1&&_ttt.theWinner() == ' ')
    	 {	
    		if(_ttt.whoseTurnfirstX() == 'X')
         	{
             
         	}
         	else
         	{
                 	new Thread(new Runnable() {
                     	public void run()
                     	{
                         	try {
                        	 cpuSelectMovePlayerFirst();
                         } catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                         	_cpuHandler.post(new Runnable() {
                             	public void run()
                             	{
                                 	new BoxClickSingle(SinglePlayer.this, _cpuRow, _cpuCol).onClick(null);
                             	}
                         	});
                     	}
                 	}).start();
             	
         	}
    	 }
    	 else if(turn==2&&_ttt.theWinner() == ' ')
    	 {	
    		if(_ttt.whoseTurnfirstO() == 'X')
         	{
             
         	}
         	else
         	{
                 	new Thread(new Runnable() {
                     	public void run()
                     	{
                         	try {
                        	 cpuSelectMoveCompFirst();
                         } catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                         	_cpuHandler.post(new Runnable() {
                             	public void run()
                             	{
                                 	new BoxClickSingle(SinglePlayer.this, _cpuRow, _cpuCol).onClick(null);
                             	}
                         	});
                     	}
                 	}).start();
             	
         	}
    	 }
    	 
    	
    }
    
    
	public void cpuSelectMovePlayerFirst() throws Exception
    {
        comp_thinking = true;
        try { Thread.sleep(rd.nextInt(600) + 500); }
        catch ( Exception ex ) { }

        	switch(difficulty)
        	{
        		case 0: compEasy(); break;
        		case 1: compMedium1(); break;
        		case 2: compHard1(); break;
        	}
        comp_thinking = false;
    }
	
	public void cpuSelectMoveCompFirst() throws Exception
    {
        comp_thinking = true;
        try { Thread.sleep(rd.nextInt(600) + 500); }
        catch ( Exception ex ) { }

        	switch(difficulty)
        	{
        		case 0: compEasy(); break;
        		case 1: compMedium2(); break;
        		case 2: compHard2(); break;
        	}
        comp_thinking = false;
    }
    
    public void compEasy()
    {
        _cpuRow = rd.nextInt(3);
        _cpuCol = rd.nextInt(3);
        
        try
        {
            while(_ttt.getValue(_cpuRow, _cpuCol) != ' ')
            {
                _cpuRow = rd.nextInt(3);
                _cpuCol = rd.nextInt(3);
            }
        }
        catch ( Exception ex ) { finish(); }
    }
    
    public void compMedium1() throws Exception
    {
    	count=0;
    	//checking for comp win
    	//row check comp
    	for(int i=0;i<3;i++)
    	{
    		for(int j=0;j<3;j++)
    		{
    			if(_ttt.getValue(i, j)=='O')
    			{
    				count++;
    			}
    			if(count==2)
    			{
    				for(int k=0;k<3;k++)
    				{
    					if(_ttt.getValue(i,k)==' ')
    					{
    						_cpuRow=i;
    						_cpuCol=k;
    						count=0;
    						return;
    					}
    				}	
    			}	
    		}
    		count=0;
    		
    	}
    	count=0;
    	
    	//col check comp
    	for(int i=0;i<3;i++)
    	{
    		for(int j=0;j<3;j++)
    		{
    			if(_ttt.getValue(j, i)=='O')
    			{
    				count++;
    			}
    			if(count==2)
    			{
    				for(int k=0;k<3;k++)
    				{
    					if(_ttt.getValue(k,i)==' ')
    					{
    						_cpuRow=k;
    						_cpuCol=i;
    						count=0;
    						return;
    					}
    				}	
    			}	
    		}
    		count=0;
    		
    	}
    	count=0;
    	
    	//left diagonal check comp
    	for(int i=0;i<3;i++)
    	{
    		if(_ttt.getValue(i, i)=='O')
    		{
    			count++;
   			}
    	}
  		if(count==2)
    	{
    		for(int k=0;k<3;k++)
    		{
   				if(_ttt.getValue(k,k)==' ')
 				{
    				_cpuRow=k;
    				_cpuCol=k;
    				count=0;
    				return;
    			}
    		}	
   		}	
    	count=0;
    	
    	//right diagonal check comp
    	for(int i=0,j=2;j>=0;j--)
        {
    		if(_ttt.getValue(i, j)=='O')
			{
				count++;
			}
            i++;
        }
        if(count==2)
        {
            for(int k=0,l=2;l>=0;l--)
            {
            	if(_ttt.getValue(k,l)==' ')
				{
					_cpuRow=k;
					_cpuCol=l;
					count=0;
					return;
				}
                k++;
            }
            count=0;
            
        }
        count=0;
        
        //else checking if player will win in next move
        
        //row check player
    	for(int i=0;i<3;i++)
    	{
    		for(int j=0;j<3;j++)
    		{
    			if(_ttt.getValue(i, j)=='X')
    			{
    				count++;
    			}
    			if(count==2)
    			{
    				for(int k=0;k<3;k++)
    				{
    					if(_ttt.getValue(i,k)==' ')
    					{
    						_cpuRow=i;
    						_cpuCol=k;
    						count=0;
    						return;
    					}
    				}	
    			}	
    		}
    		count=0;
    		
    	}
    	count=0;

        //col check player
        for(int i=0;i<3;i++)
    	{
    		for(int j=0;j<3;j++)
    		{
    			if(_ttt.getValue(j, i)=='X')
    			{
    				count++;
    			}
    			if(count==2)
    			{
    				for(int k=0;k<3;k++)
    				{
    					if(_ttt.getValue(k,i)==' ')
    					{
    						_cpuRow=k;
    						_cpuCol=i;
    						count=0;
    						return;
    					}
    				}	
    			}	
    		}
    		count=0;
    		
    	}
    	count=0;
    	
    	//left diagonal check player
    	for(int i=0;i<3;i++)
    	{
    		if(_ttt.getValue(i, i)=='X')
    		{
    			count++;
   			}
    	}
  		if(count==2)
    	{
    		for(int k=0;k<3;k++)
    		{
   				if(_ttt.getValue(k,k)==' ')
 				{
    				_cpuRow=k;
    				_cpuCol=k;
    				count=0;
    				return;
    			}
    		}	
   		}	
    	count=0;
    	
    	//right diagonal check player
    	for(int i=0,j=2;j>=0;j--)
        {
    		if(_ttt.getValue(i, j)=='X')
			{
				count++;
			}
            i++;
        }
        if(count==2)
        {
            for(int k=0,l=2;l>=0;l--)
            {
            	if(_ttt.getValue(k,l)==' ')
				{
					_cpuRow=k;
					_cpuCol=l;
					count=0;
					return;
				}
                k++;
            }
            count=0;
            
        }
        count=0;
        
        compEasy();
    }
    
    
    public void compMedium2() throws Exception
    {
    	count=0;
    	//checking for comp win
    	//row check comp
    	for(int i=0;i<3;i++)
    	{
    		for(int j=0;j<3;j++)
    		{
    			if(_ttt.getValue(i, j)=='O')
    			{
    				count++;
    			}
    			if(count==2)
    			{
    				for(int k=0;k<3;k++)
    				{
    					if(_ttt.getValue(i,k)==' ')
    					{
    						_cpuRow=i;
    						_cpuCol=k;
    						count=0;
    						return;
    					}
    				}	
    			}	
    		}
    		count=0;
    		
    	}
    	count=0;
    	
    	//col check comp
    	for(int i=0;i<3;i++)
    	{
    		for(int j=0;j<3;j++)
    		{
    			if(_ttt.getValue(j, i)=='O')
    			{
    				count++;
    			}
    			if(count==2)
    			{
    				for(int k=0;k<3;k++)
    				{
    					if(_ttt.getValue(k,i)==' ')
    					{
    						_cpuRow=k;
    						_cpuCol=i;
    						count=0;
    						return;
    					}
    				}	
    			}	
    		}
    		count=0;
    		
    	}
    	count=0;
    	
    	//left diagonal check comp
    	for(int i=0;i<3;i++)
    	{
    		if(_ttt.getValue(i, i)=='O')
    		{
    			count++;
   			}
    	}
  		if(count==2)
    	{
    		for(int k=0;k<3;k++)
    		{
   				if(_ttt.getValue(k,k)==' ')
 				{
    				_cpuRow=k;
    				_cpuCol=k;
    				count=0;
    				return;
    			}
    		}	
   		}	
    	count=0;
    	
    	//right diagonal check comp
    	for(int i=0,j=2;j>=0;j--)
        {
    		if(_ttt.getValue(i, j)=='O')
			{
				count++;
			}
            i++;
        }
        if(count==2)
        {
            for(int k=0,l=2;l>=0;l--)
            {
            	if(_ttt.getValue(k,l)==' ')
				{
					_cpuRow=k;
					_cpuCol=l;
					count=0;
					return;
				}
                k++;
            }
            count=0;
            
        }
        count=0;
        
        //else checking if player will win in next move
        
        //row check player
    	for(int i=0;i<3;i++)
    	{
    		for(int j=0;j<3;j++)
    		{
    			if(_ttt.getValue(i, j)=='X')
    			{
    				count++;
    			}
    			if(count==2)
    			{
    				for(int k=0;k<3;k++)
    				{
    					if(_ttt.getValue(i,k)==' ')
    					{
    						_cpuRow=i;
    						_cpuCol=k;
    						count=0;
    						return;
    					}
    				}	
    			}	
    		}
    		count=0;
    		
    	}
    	count=0;

        //col check player
        for(int i=0;i<3;i++)
    	{
    		for(int j=0;j<3;j++)
    		{
    			if(_ttt.getValue(j, i)=='X')
    			{
    				count++;
    			}
    			if(count==2)
    			{
    				for(int k=0;k<3;k++)
    				{
    					if(_ttt.getValue(k,i)==' ')
    					{
    						_cpuRow=k;
    						_cpuCol=i;
    						count=0;
    						return;
    					}
    				}	
    			}	
    		}
    		count=0;
    		
    	}
    	count=0;
    	
    	//left diagonal check player
    	for(int i=0;i<3;i++)
    	{
    		if(_ttt.getValue(i, i)=='X')
    		{
    			count++;
   			}
    	}
  		if(count==2)
    	{
    		for(int k=0;k<3;k++)
    		{
   				if(_ttt.getValue(k,k)==' ')
 				{
    				_cpuRow=k;
    				_cpuCol=k;
    				count=0;
    				return;
    			}
    		}	
   		}	
    	count=0;
    	
    	//right diagonal check player
    	for(int i=0,j=2;j>=0;j--)
        {
    		if(_ttt.getValue(i, j)=='X')
			{
				count++;
			}
            i++;
        }
        if(count==2)
        {
            for(int k=0,l=2;l>=0;l--)
            {
            	if(_ttt.getValue(k,l)==' ')
				{
					_cpuRow=k;
					_cpuCol=l;
					count=0;
					return;
				}
                k++;
            }
            count=0;
            
        }
        count=0;
        
        compEasy();
    }
    
    public void compHard1() throws Exception
    {
    	
    	if(_ttt.getValue(1, 1)==' ')
    	{
    		if(firstChance)
    		{
    			_cpuRow=1;
    			_cpuCol=1;
    			firstChance=false;
    			return;
    		}
    	}
    	else
    	{
    		if(firstChance)
    		{
    			firstChance=false;
    			_cpuRow=evenpts[rd.nextInt(2)];
        		_cpuCol=evenpts[rd.nextInt(2)];
        		while(_ttt.getValue(_cpuRow, _cpuCol)!=' ')
        		{
        			_cpuRow=evenpts[rd.nextInt(2)];
            		_cpuCol=evenpts[rd.nextInt(2)];
        		}
        		return;
    		}
    	}
	
    	count=0;
    	//checking for comp win
    	//row check comp
    	for(int i=0;i<3;i++)
    	{
    		for(int j=0;j<3;j++)
    		{
    			if(_ttt.getValue(i, j)=='O')
    			{
    				count++;
    			}
    			if(count==2)
    			{
    				for(int k=0;k<3;k++)
    				{
    					if(_ttt.getValue(i,k)==' ')
    					{
    						_cpuRow=i;
    						_cpuCol=k;
    						count=0;
    						return;
    					}
    				}	
    			}	
    		}
    		count=0;
    		
    	}
    	count=0;
    	
    	//col check comp
    	for(int i=0;i<3;i++)
    	{
    		for(int j=0;j<3;j++)
    		{
    			if(_ttt.getValue(j, i)=='O')
    			{
    				count++;
    			}
    			if(count==2)
    			{
    				for(int k=0;k<3;k++)
    				{
    					if(_ttt.getValue(k,i)==' ')
    					{
    						_cpuRow=k;
    						_cpuCol=i;
    						count=0;
    						return;
    					}
    				}	
    			}	
    		}
    		count=0;
    		
    	}
    	count=0;
    	
    	//left diagonal check comp
    	for(int i=0;i<3;i++)
    	{
    		if(_ttt.getValue(i, i)=='O')
    		{
    			count++;
   			}
    	}
  		if(count==2)
    	{
    		for(int k=0;k<3;k++)
    		{
   				if(_ttt.getValue(k,k)==' ')
 				{
    				_cpuRow=k;
    				_cpuCol=k;
    				count=0;
    				return;
    			}
    		}	
   		}	
    	count=0;
    	
    	//right diagonal check comp
    	for(int i=0,j=2;j>=0;j--)
        {
    		if(_ttt.getValue(i, j)=='O')
			{
				count++;
			}
            i++;
        }
        if(count==2)
        {
            for(int k=0,l=2;l>=0;l--)
            {
            	if(_ttt.getValue(k,l)==' ')
				{
					_cpuRow=k;
					_cpuCol=l;
					count=0;
					return;
				}
                k++;
            }
            count=0;
            
        }
        count=0;
        
        //else checking if player will win in next move
        
        //row check player
    	for(int i=0;i<3;i++)
    	{
    		for(int j=0;j<3;j++)
    		{
    			if(_ttt.getValue(i, j)=='X')
    			{
    				count++;
    			}
    			if(count==2)
    			{
    				for(int k=0;k<3;k++)
    				{
    					if(_ttt.getValue(i,k)==' ')
    					{
    						_cpuRow=i;
    						_cpuCol=k;
    						count=0;
    						return;
    					}
    				}	
    			}	
    		}
    		count=0;
    		
    	}
    	count=0;

        //col check player
        for(int i=0;i<3;i++)
    	{
    		for(int j=0;j<3;j++)
    		{
    			if(_ttt.getValue(j, i)=='X')
    			{
    				count++;
    			}
    			if(count==2)
    			{
    				for(int k=0;k<3;k++)
    				{
    					if(_ttt.getValue(k,i)==' ')
    					{
    						_cpuRow=k;
    						_cpuCol=i;
    						count=0;
    						return;
    					}
    				}	
    			}	
    		}
    		count=0;
    		
    	}
    	count=0;
    	
    	//left diagonal check player
    	for(int i=0;i<3;i++)
    	{
    		if(_ttt.getValue(i, i)=='X')
    		{
    			count++;
   			}
    	}
  		if(count==2)
    	{
    		for(int k=0;k<3;k++)
    		{
   				if(_ttt.getValue(k,k)==' ')
 				{
    				_cpuRow=k;
    				_cpuCol=k;
    				count=0;
    				return;
    			}
    		}	
   		}	
    	count=0;
    	
    	//right diagonal check player
    	for(int i=0,j=2;j>=0;j--)
        {
    		if(_ttt.getValue(i, j)=='X')
			{
				count++;
			}
            i++;
        }
        if(count==2)
        {
            for(int k=0,l=2;l>=0;l--)
            {
            	if(_ttt.getValue(k,l)==' ')
				{
					_cpuRow=k;
					_cpuCol=l;
					count=0;
					return;
				}
                k++;
            }
            count=0;
            
        }
        count=0;
      
        

       if((_ttt.getValue(0, 0)=='X'&&_ttt.getValue(2, 2)=='X')||(_ttt.getValue(2, 0)=='X'&&_ttt.getValue(0, 2)=='X'))
        {
    	   	int i=rd.nextInt(2);
    	   	_cpuRow=pts[i][rd.nextInt(2)];
        	if(i==0)
        	_cpuCol=pts[1][rd.nextInt(2)];
        	else
        	_cpuCol=pts[0][rd.nextInt(2)];
        	return;

        }
       
       if(_ttt.getValue(0, 0)!=' '&&_ttt.getValue(0, 2)!=' '&&_ttt.getValue(2, 0)!=' '&&_ttt.getValue(2, 2)!=' ')
    	count=-1;   
       
       if(_ttt.getValue(1, 1)=='X'&&count!=-1&&(_ttt.getValue(0, 0)=='X'||_ttt.getValue(0, 2)=='X'||_ttt.getValue(2, 0)=='X'||_ttt.getValue(2, 2)=='X'))
       {
			_cpuRow=evenpts[rd.nextInt(2)];
			_cpuCol=evenpts[rd.nextInt(2)];
			while(_ttt.getValue(_cpuRow, _cpuCol)!=' ')
			{
				_cpuRow=evenpts[rd.nextInt(2)];
				_cpuCol=evenpts[rd.nextInt(2)];
			}
			return;
       }

       if(_ttt.getValue(1, 1)=='O')
       {
    	   for(int k=0;k<3;k++)
    	   {
    		   if(_ttt.getValue(k, k)=='X')
  			   {
    			   for(int l=0;l<3;l++)
    			   {
    				   if(_ttt.getValue(l, l)==' ')
    				   {
    					   _cpuRow=l;
    					   _cpuCol=l;
    					   return;
    				   }
    			   }
    		   }
    	   }
    	   for(int i=0,j=2;j>=0;j--)
           {
    		   if(_ttt.getValue(i, j)=='X')
  			   {
    			   for(int l=0,m=2;m>=0;m--)
    			   {
    				   if(_ttt.getValue(l, m)==' ')
    				   {
    					   _cpuRow=l;
    					   _cpuCol=m;
    					   return;
    				   }
    				   l++;
    			   }
    		   }
               i++;
           }
       }
       
       if(specialcase)
       for(int k=0;k<4;k++)
       {
    	   if((_ttt.getValue(nonCor[k][0][0], nonCor[k][0][1])=='X')&&(_ttt.getValue(nonCor[k][1][0], nonCor[k][1][1])=='X'))
    	   {
    		   specialcase=false;
    		   if(_ttt.getValue(nonCor[k][0][0], nonCor[k][1][1])==' ')
    		   {
    			   _cpuRow=nonCor[k][0][0];
    			   _cpuCol=nonCor[k][1][1];
    			   return;
    		   }
    		   
    	   }
       }
       
       compEasy();
      
        
    }
    
    
    public void compHard2() throws Exception
    {
    	
    	if(firstChance)
    	{
    		firstChance=false;
    		secondChance=true;
    		if(rd.nextInt(3)==2)
    		{
    			_cpuRow=1;
    			_cpuCol=1;
    			return;
    		}
    		else
    		{
    			_cpuRow=evenpts[rd.nextInt(2)];
    			_cpuCol=evenpts[rd.nextInt(2)];
    			return;
    		}
    	}
    	
    	if(secondChance)
    	{
    		secondChance=false;
    		if(_ttt.getValue(1, 1)==' ')
    		{
    			_cpuRow=1;
    			_cpuCol=1;
    			return;
    		}
    		else
    		{
    			_cpuRow=evenpts[rd.nextInt(2)];
    			_cpuCol=evenpts[rd.nextInt(2)];
    			while(_ttt.getValue(_cpuRow, _cpuCol)!=' ')
    			{
    				_cpuRow=evenpts[rd.nextInt(2)];
        			_cpuCol=evenpts[rd.nextInt(2)];
    			}
    			return;
    		}
    	}
    	
    	
	
    	count=0;
    	//checking for comp win
    	//row check comp
    	for(int i=0;i<3;i++)
    	{
    		for(int j=0;j<3;j++)
    		{
    			if(_ttt.getValue(i, j)=='O')
    			{
    				count++;
    			}
    			if(count==2)
    			{
    				for(int k=0;k<3;k++)
    				{
    					if(_ttt.getValue(i,k)==' ')
    					{
    						_cpuRow=i;
    						_cpuCol=k;
    						count=0;
    						return;
    					}
    				}	
    			}	
    		}
    		count=0;
    		
    	}
    	count=0;
    	
    	//col check comp
    	for(int i=0;i<3;i++)
    	{
    		for(int j=0;j<3;j++)
    		{
    			if(_ttt.getValue(j, i)=='O')
    			{
    				count++;
    			}
    			if(count==2)
    			{
    				for(int k=0;k<3;k++)
    				{
    					if(_ttt.getValue(k,i)==' ')
    					{
    						_cpuRow=k;
    						_cpuCol=i;
    						count=0;
    						return;
    					}
    				}	
    			}	
    		}
    		count=0;
    		
    	}
    	count=0;
    	
    	//left diagonal check comp
    	for(int i=0;i<3;i++)
    	{
    		if(_ttt.getValue(i, i)=='O')
    		{
    			count++;
   			}
    	}
  		if(count==2)
    	{
    		for(int k=0;k<3;k++)
    		{
   				if(_ttt.getValue(k,k)==' ')
 				{
    				_cpuRow=k;
    				_cpuCol=k;
    				count=0;
    				return;
    			}
    		}	
   		}	
    	count=0;
    	
    	//right diagonal check comp
    	for(int i=0,j=2;j>=0;j--)
        {
    		if(_ttt.getValue(i, j)=='O')
			{
				count++;
			}
            i++;
        }
        if(count==2)
        {
            for(int k=0,l=2;l>=0;l--)
            {
            	if(_ttt.getValue(k,l)==' ')
				{
					_cpuRow=k;
					_cpuCol=l;
					count=0;
					return;
				}
                k++;
            }
            count=0;
            
        }
        count=0;
        
        //else checking if player will win in next move
        
        //row check player
    	for(int i=0;i<3;i++)
    	{
    		for(int j=0;j<3;j++)
    		{
    			if(_ttt.getValue(i, j)=='X')
    			{
    				count++;
    			}
    			if(count==2)
    			{
    				for(int k=0;k<3;k++)
    				{
    					if(_ttt.getValue(i,k)==' ')
    					{
    						_cpuRow=i;
    						_cpuCol=k;
    						count=0;
    						return;
    					}
    				}	
    			}	
    		}
    		count=0;
    		
    	}
    	count=0;

        //col check player
        for(int i=0;i<3;i++)
    	{
    		for(int j=0;j<3;j++)
    		{
    			if(_ttt.getValue(j, i)=='X')
    			{
    				count++;
    			}
    			if(count==2)
    			{
    				for(int k=0;k<3;k++)
    				{
    					if(_ttt.getValue(k,i)==' ')
    					{
    						_cpuRow=k;
    						_cpuCol=i;
    						count=0;
    						return;
    					}
    				}	
    			}	
    		}
    		count=0;
    		
    	}
    	count=0;
    	
    	//left diagonal check player
    	for(int i=0;i<3;i++)
    	{
    		if(_ttt.getValue(i, i)=='X')
    		{
    			count++;
   			}
    	}
  		if(count==2)
    	{
    		for(int k=0;k<3;k++)
    		{
   				if(_ttt.getValue(k,k)==' ')
 				{
    				_cpuRow=k;
    				_cpuCol=k;
    				count=0;
    				return;
    			}
    		}	
   		}	
    	count=0;
    	
    	//right diagonal check player
    	for(int i=0,j=2;j>=0;j--)
        {
    		if(_ttt.getValue(i, j)=='X')
			{
				count++;
			}
            i++;
        }
        if(count==2)
        {
            for(int k=0,l=2;l>=0;l--)
            {
            	if(_ttt.getValue(k,l)==' ')
				{
					_cpuRow=k;
					_cpuCol=l;
					count=0;
					return;
				}
                k++;
            }
            count=0;
            
        }
        count=0;
      
       
       
       compEasy();
      
        
    }
    
    private void roundEnds() {
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
    	
    	if(_ttt.theWinner()=='X')
    	{
    		if(difficulty==0)
    		{
    			exw++;
        		editorexw.putInt(easyxwins, exw);
    		}
    		else if(difficulty==1)
    		{
    			mxw++;
        		editormxw.putInt(mediumxwins, mxw);
    		}
    		else if(difficulty==2)
    		{
    			hxw++;
        		editorhxw.putInt(hardxwins, hxw);
    		}
    	}
    	else if(_ttt.theWinner()=='O')
    	{
    		if(difficulty==0)
    		{
    			eow++;
        		editoreow.putInt(easyowins, eow);
    		}
    		else if(difficulty==1)
    		{
    			mow++;
        		editormow.putInt(mediumowins, mow);
    		}
    		else if(difficulty==2)
    		{
    			how++;
        		editorhow.putInt(hardowins, how);
    		}
    	}
    	else if(_ttt.theWinner()!=' ')
    	{
    		if(difficulty==0)
    		{
    			edr++;
        		editoredr.putInt(easydraws, edr);
    		}
    		else if(difficulty==1)
    		{
    			mdr++;
        		editormdr.putInt(mediumdraws, mdr);
    		}
    		else if(difficulty==2)
    		{
    			hdr++;
        		editorhdr.putInt(harddraws, hdr);
    		}
    	}
    	
    	
    	
    	
    	
    	editorexw.commit();
    	editoreow.commit();
    	editoredr.commit();
    	editormxw.commit();
    	editormow.commit();
    	editormdr.commit();
    	editorhxw.commit();
    	editorhow.commit();
    	editorhdr.commit();
    	
		
	}
    
    @Override
    public void onBackPressed()
    {
    	Intent originalIntent = new Intent().setClass(SinglePlayer.this, MenuActivity.class);
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
