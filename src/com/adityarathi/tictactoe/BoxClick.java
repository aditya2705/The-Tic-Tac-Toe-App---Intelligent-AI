package com.adityarathi.tictactoe;

import com.adityarathi.tictactoe.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;


public class BoxClick implements OnClickListener {
	
	private MultiplayerActivity _parentactivity = null;
	private ImageView _imageView = null;
	private AlertDialog alert_dialog = null;
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
	public AlertDialog getAlertDialog() {return alert_dialog;}
	public char getCurrentValue() {return current_value;}
	
	@SuppressWarnings("deprecation")
	public BoxClick (MultiplayerActivity parentActivity, ImageView imgv, int row, int col,boolean turn,int sound)
	{
	_parentactivity = parentActivity;
	_imageView = imgv;
	_rows = row;
	_cols = col;
	turnX=turn;
	s=sound;
	alert_dialog = new AlertDialog.Builder(_parentactivity).create();
	alert_dialog.setButton("OK", new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}
	});
	
	
	}
	
	public void onClick(View v)
	{
		
		MediaPlayer mp1 = MediaPlayer.create(_parentactivity, R.raw.dripsound);
		
		if(_parentactivity._otherplayerThinking()) return;
		{
			try
			{
				if(_parentactivity.getttt().getValue(_rows, _cols) != ' ')
				{
					alert_dialog.setTitle("Message");
					alert_dialog.setMessage("Don't embarass yourself");
					alert_dialog.show();
				}
				else
				{
					if(s==1)
					mp1.start();
					if(turnX)
					{
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
					else
					{
						if(_parentactivity.getttt().whoseTurnfirstO() == 'X')
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
					
						current_value = _parentactivity.getttt().whoseTurnfirstO();
						_parentactivity.getttt().playMoveFirstO(_rows, _cols);
					}
					
					
					_parentactivity.boxNowSelected(this);
					
					
				}
			}
				catch(Exception ex)
				{
					
				}
			}
		}
		
		
	}
	
	


