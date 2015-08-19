package com.adityarathi.tictactoe;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
public class BluetoothRound extends Activity {
	
	private ImageView[][] _boxes = new ImageView[3][3];
	private BoxClickBluetooth[][] _boxonclicks = new BoxClickBluetooth[3][3];
	private TicTacToe _ttt= new TicTacToe();
	boolean player2_thinking = false;
	private boolean turnX=true;
	private int soundCall;
	boolean  _otherplayerThinking(){ return player2_thinking;}
	public TicTacToe getttt(){ return _ttt;}
	private int oneScore = 0;
	private int twoScore = 0;
	private int draws = 0;
	private boolean NowChance;
	public boolean getNowChance(){return NowChance;}
	private SharedPreferences SoundSet;
	private String soundstring;
	
	
    // Debugging
    private static final String TAG = "BluetoothTicTacToe";
    private static final boolean D = true;

    // Message types sent from the BluetoothGameframe Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Key names received from the BluetoothGameframe Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;

    // Layout Views

    // Name of the connected device
    private String mConnectedDeviceName = null;
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    // Member object for the gameFrame
    private BluetoothGameframe mGameService = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(D) Log.e(TAG, "+++ ON CREATE +++");

        // Set up the window layout
        setContentView(R.layout.layout_grid);
        
        
        
        SoundSet = getSharedPreferences("SOUNDSWITCH",0);
     	soundCall = SoundSet.getInt(soundstring, 1);
       

        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(D) Log.e(TAG, "++ ON START ++");

        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        } else {
            if (mGameService == null) setupStart();
        }
    }

    @Override
    public synchronized void onResume() {
        super.onResume();
        if(D) Log.e(TAG, "+ ON RESUME +");
        

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mGameService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mGameService.getState() == BluetoothGameframe.STATE_NONE) {
              // Start the Bluetooth Game
              mGameService.start();
            }
            
        }
        
        
        
    }

    private void setupStart() {
        Log.d(TAG, "setupStart()");

        GraphicInterface();

        
        // Initialize the BluetoothGameframe to perform bluetooth connections
        mGameService = new BluetoothGameframe(this, mHandler);
        
        connectDialog();
        

    }

    private void connectDialog() {
		// TODO Auto-generated method stub
    	AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setPositiveButton("CONNECT TO A DEVICE", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				
				Intent serverIntent = new Intent(BluetoothRound.this, DeviceListActivity.class);
	            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_INSECURE);
			}
		});
        builder.setNegativeButton("MAKE DISCOVERABLE", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				if(mBluetoothAdapter.isDiscovering())
				{
					mBluetoothAdapter.cancelDiscovery();
				}
				ensureDiscoverable();
				
			}
		});
        AlertDialog _alertDialogConnect = builder.create();
        _alertDialogConnect.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
				Intent mainIntent = new Intent(BluetoothRound.this, MenuActivity.class);
				BluetoothRound.this.startActivity(mainIntent);
				BluetoothRound.this.finish();
			}
		});
        _alertDialogConnect.show();
	}
    
    
	private void GraphicInterface() {
		// TODO Auto-generated method stub
    	_boxes[0][0] = (ImageView)findViewById(R.id.otile11);
    	_boxes[0][1] = (ImageView)findViewById(R.id.otile12);
    	_boxes[0][2] = (ImageView)findViewById(R.id.otile13);
    	_boxes[1][0] = (ImageView)findViewById(R.id.otile21);
    	_boxes[1][1] = (ImageView)findViewById(R.id.otile22);
    	_boxes[1][2] = (ImageView)findViewById(R.id.otile23);
    	_boxes[2][0] = (ImageView)findViewById(R.id.otile31);
    	_boxes[2][1] = (ImageView)findViewById(R.id.otile32);
    	_boxes[2][2] = (ImageView)findViewById(R.id.otile33);
    	
    	
    	resetGui();
    	
	}

	private void resetGui() {
		// TODO Auto-generated method stub
		NowChance=true;
		player2_thinking = false;
		
		for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
            {
            	_boxonclicks[i][j] = new BoxClickBluetooth(BluetoothRound.this, _boxes[i][j], i, j,turnX,soundCall);
                _boxes[i][j].setOnClickListener(_boxonclicks[i][j]);
                _boxes[i][j].setImageResource(R.drawable.blank);
            }

        _ttt.reset();
	}

	@Override
    public synchronized void onPause() {
        super.onPause();
        if(D) Log.e(TAG, "- ON PAUSE -");
    }

    @Override
    public void onStop() {
        super.onStop();
        if(D) Log.e(TAG, "-- ON STOP --");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop the Bluetooth chat services
        if (mGameService != null) mGameService.stop();
        if(D) Log.e(TAG, "--- ON DESTROY ---");
    }

    private void ensureDiscoverable() {
        if(D) Log.d(TAG, "ensure discoverable");
        if (mBluetoothAdapter.getScanMode() !=
            BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
            
        }
        
    }

    /**
     * Sends a message.
     * @param message  A string of text to send.
     */
    void sendOrdinates(int i,int j) {
        // Check that we're actually connected before trying anything
        if (mGameService.getState() != BluetoothGameframe.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }
        String ordinates = i+"/"+j;
        
            // Get the ordinates bytes and tell the BluetoothGameframe to write
            byte[] send = ordinates.getBytes();
            mGameService.write(send);

           
    }

  
    // The Handler that gets information back from the BluetoothGameframe
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MESSAGE_STATE_CHANGE:
                if(D) Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                switch (msg.arg1) {
                case BluetoothGameframe.STATE_CONNECTED:
                	
                    break;
                case BluetoothGameframe.STATE_CONNECTING:
                    Toast.makeText(getApplicationContext(), "Connecting...", Toast.LENGTH_SHORT).show();
                    break;
                case BluetoothGameframe.STATE_LISTEN:
                case BluetoothGameframe.STATE_NONE:
                    break;
                }
                break;
            case MESSAGE_WRITE:
            	NowChance=false;
                break;
				
            case MESSAGE_READ:
            	NowChance=true;
            	MediaPlayer mp1 = MediaPlayer.create(BluetoothRound.this, R.raw.dripsound);
                byte[] readBuf = (byte[]) msg.obj;
                // construct a string from the valid bytes in the buffer
                String readMessage = new String(readBuf, 0, msg.arg1);
                int r =Integer.parseInt(readMessage.charAt(0)+"");
                int s =Integer.parseInt(readMessage.charAt(2)+"");
                try {
                	
					if(_ttt.whoseTurnfirstX() == 'X'&&_ttt.getValue(r, s)==' ')
					{
						if(soundCall==1)
		                mp1.start();
						_boxes[r][s].setImageResource(R.animator.animationx);
						AnimationDrawable anim = (AnimationDrawable) _boxes[r][s].getDrawable();
						anim.start();		
					}
					else if(_ttt.getValue(r, s)==' ')
					{	
						if(soundCall==1)
			            mp1.start();
						_boxes[r][s].setImageResource(R.animator.animationo);
						AnimationDrawable anim = (AnimationDrawable) _boxes[r][s].getDrawable();
						anim.start();
					}
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					_ttt.playMoveFirstX(r, s);
					BluetoothRound.this.boxNowSelected(null);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
                break;
                
            case MESSAGE_DEVICE_NAME:
                // save the connected device's name
                mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                Toast.makeText(getApplicationContext(), "Connected to "
                               + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                break;
            case MESSAGE_TOAST:
                Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                               Toast.LENGTH_SHORT).show();
                break;
            }
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(D) Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
        case REQUEST_CONNECT_DEVICE_SECURE:
            // When DeviceListActivity returns with a device to connect
            if (resultCode == Activity.RESULT_OK) {
                connectDevice(data, true);
            }
            break;
        case REQUEST_CONNECT_DEVICE_INSECURE:
            // When DeviceListActivity returns with a device to connect
            if (resultCode == Activity.RESULT_OK) {
                connectDevice(data, false);
            }
            break;
        case REQUEST_ENABLE_BT:
            // When the request to enable Bluetooth returns
            if (resultCode == Activity.RESULT_OK) {
                // Bluetooth is now enabled, so set up a chat session
                setupStart();
            } else {
                // User did not enable Bluetooth or an error occured
                Log.d(TAG, "BT not enabled");
                Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                Intent mainIntent = new Intent(BluetoothRound.this, MenuActivity.class);
                BluetoothRound.this.startActivity(mainIntent);

                BluetoothRound.this.finish();
            }
        }
    }

    private void connectDevice(Intent data, boolean secure) {
        // Get the device MAC address
        String address = data.getExtras()
            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BLuetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        mGameService.connect(device, secure);
    }

    
    
	public void boxNowSelected(BoxClickBluetooth boxClickBluetooth) {
		// TODO Auto-generated method stub
		final MediaPlayer mp2 = MediaPlayer.create(BluetoothRound.this, R.raw.whistle);
    	
   	 if(_ttt.theWinner() != ' ')
        {
   		 	
            
            if(_ttt.theWinner() == 'X')
            {
           	 oneScore++;
            }
            else if(_ttt.theWinner() == 'O')
            {
           	 twoScore++;
            }
            else
            {
           	 draws++;
            }
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
        	
        	
        	final Toast toast = new Toast(getApplicationContext());
        	toast.setGravity(Gravity.TOP, 0, 0);
        	toast.setDuration(Toast.LENGTH_SHORT-500);
        	toast.setView(layout);
        	if(soundCall==1)
         	mp2.start();
        	
        	
        	final Handler scoreHandler = new Handler(){
        		@Override
        		public void handleMessage(Message msg) {
        			// TODO Auto-generated method stub
        			super.handleMessage(msg);
        			toast.show();
        			resetGui();
        		}
        	};
        	
        	new Thread(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					super.run();
					try {
						Thread.sleep(850);
						scoreHandler.sendEmptyMessage(0);
						return;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
        		
        	}.start();
        	
            
        }
		
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent originalIntent = new Intent().setClass(BluetoothRound.this, MenuActivity.class);
		startActivityForResult(originalIntent, 1);
		BluetoothRound.this.finish();
	}

  

}
