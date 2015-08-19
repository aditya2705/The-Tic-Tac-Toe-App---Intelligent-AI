package com.adityarathi.tictactoe;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class BoxClickBluetooth implements OnClickListener {

	private BluetoothRound _parentactivity = null;
	private ImageView _imageView = null;
	private int _xresource=-1, _oresource=-1;
	private int _rows = -1, _cols = -1;
	private char current_value = ' ';
	private boolean turnX;
	private int s;
	public Activity getParentActivity() { return _parentactivity;}
	public int getRow() {return _rows;}
	public int getCol() {return _cols;}
	public ImageView getImgView() {return _imageView;}
	public int getresourcex() {return _xresource;}
	public int getresourceo() {return _oresource;}
	public char getCurrentValue() {return current_value;}
	
	public BoxClickBluetooth (BluetoothRound parentActivity, ImageView imgv, int row, int col,boolean turn,int sound)
	{
	_parentactivity = parentActivity;
	_imageView = imgv;
	_rows = row;
	_cols = col;
	turnX=turn;
	s=sound;
	
	
	}
	
	public void onClick(View v)
	{
		
		MediaPlayer mp1 = MediaPlayer.create(_parentactivity, R.raw.dripsound);
		
		if(_parentactivity._otherplayerThinking()) return;
		{
			try
			{
					
					
					if(turnX&&_parentactivity.getttt().getValue(_rows, _cols)==' '&&_parentactivity.getNowChance())
					{
						if(s==1)
						mp1.start();
						if(_parentactivity.getttt().whoseTurnfirstX() == 'X')
						{
							_imageView.setImageResource(R.animator.animationx);
							AnimationDrawable anim = (AnimationDrawable) _imageView.getDrawable();
							anim.start();		
						}
						else
						{	
							_imageView.setImageResource(R.animator.animationo);
							AnimationDrawable anim = (AnimationDrawable) _imageView.getDrawable();
							anim.start();
						}
					
						current_value = _parentactivity.getttt().whoseTurnfirstX();
						_parentactivity.getttt().playMoveFirstX(_rows, _cols);
					}
					if(_parentactivity.getNowChance()&&_parentactivity.getttt().getValue(_rows, _cols)==' ')
					{
						_parentactivity.sendOrdinates(_rows,_cols);
						_parentactivity.boxNowSelected(this);
					}
			}
				catch(Exception ex){}
			}
		}
		
		
	}
