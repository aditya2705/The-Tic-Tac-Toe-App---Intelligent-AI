package com.adityarathi.tictactoe;



import android.app.Activity;

import android.view.View;
import android.view.View.OnClickListener;


public class BoxClickSingle implements OnClickListener {
	
	
	private SinglePlayer _parentactivity = null;
	private int _rows = -1, _cols = -1;
	public Activity getParentActivity() { return _parentactivity;}
	public int getRow() {return _rows;}
	public int getCol() {return _cols;}
	
	public BoxClickSingle (SinglePlayer parentActivity, int row, int col)
	{
	_parentactivity = parentActivity;
	_rows = row;
	_cols = col;
	}
	public void onClick(View v)
	{
		try {
			_parentactivity.boxNowSelected(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

