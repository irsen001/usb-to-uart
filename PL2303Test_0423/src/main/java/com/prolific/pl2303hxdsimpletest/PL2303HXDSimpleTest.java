package com.prolific.pl2303hxdsimpletest;


import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import tw.com.prolific.driver.pl2303.PL2303Driver;
import tw.com.prolific.driver.pl2303.PL2303Driver.DataBits;
import tw.com.prolific.driver.pl2303.PL2303Driver.FlowControl;
import tw.com.prolific.driver.pl2303.PL2303Driver.Parity;
import tw.com.prolific.driver.pl2303.PL2303Driver.StopBits;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lmy.logcatch.LogCather;

public class PL2303HXDSimpleTest extends Activity {

	// debug settings
	// private static final boolean SHOW_DEBUG = false;

	private static final boolean SHOW_DEBUG = true;

	// Defines of Display Settings
	private static final int DISP_CHAR = 0;

	// Linefeed Code Settings
	//    private static final int LINEFEED_CODE_CR = 0;
	private static final int LINEFEED_CODE_CRLF = 1;
	private static final int LINEFEED_CODE_LF = 2;

	PL2303Driver mSerial;

	//    private ScrollView mSvText;
	//   private StringBuilder mText = new StringBuilder();

	String TAG = "PL2303HXD_APLog";

	private Button btWrite;
	private EditText etWrite;

	private Button btRead;
	private EditText etRead;

	private Button btLoopBack;
	private ProgressBar pbLoopBack;    
	private TextView tvLoopBack;

	private Button btGetSN;  
	private TextView tvShowSN;

	private int mDisplayType = DISP_CHAR;
	private int mReadLinefeedCode = LINEFEED_CODE_LF;
	private int mWriteLinefeedCode = LINEFEED_CODE_LF;

	//BaudRate.B4800, DataBits.D8, StopBits.S1, Parity.NONE, FlowControl.RTSCTS
	//private PL2303Driver.BaudRate mBaudrate = PL2303Driver.BaudRate.B9600;
	private PL2303Driver.BaudRate mBaudrate = PL2303Driver.BaudRate.B115200;
	private PL2303Driver.DataBits mDataBits = PL2303Driver.DataBits.D8;
	private PL2303Driver.Parity mParity = PL2303Driver.Parity.NONE;
	private PL2303Driver.StopBits mStopBits = PL2303Driver.StopBits.S1;
	private PL2303Driver.FlowControl mFlowControl = PL2303Driver.FlowControl.OFF;
	private Button finish;
	private TextView test_value;
	private TextView pef,pefp,fev,fevp,fvc,fevfvc;
	private boolean opened = false, reading = false, isFinished = false, gotData = false;

	private static final String ACTION_USB_PERMISSION = "com.prolific.pl2303hxdsimpletest.USB_PERMISSION";

	private static final String NULL = null;   

	// Linefeed
	//    private final static String BR = System.getProperty("line.separator");

	public Spinner PL2303HXD_BaudRate_spinner;
	public int PL2303HXD_BaudRate;
	public String PL2303HXD_BaudRate_str="B4800";

	private String strStr;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "Enter onCreate xx");

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.received);
		new LogCather(this).start("pl2303");

/*		PL2303HXD_BaudRate_spinner = (Spinner)findViewById(R.id.spinner1);

		ArrayAdapter<CharSequence> adapter = 
				ArrayAdapter.createFromResource(this, R.array.BaudRate_Var, android.R.layout.simple_spinner_item);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		PL2303HXD_BaudRate_spinner.setAdapter(adapter);		
		PL2303HXD_BaudRate_spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
*/
		//Button start_btn1 = (Button)findViewById(R.id.start_btn1);
		//start_btn1.setOnClickListener(mOnClickListener);
		Button re_get = (Button)findViewById(R.id.re_get);
		re_get.setOnClickListener(mOnClickListener);
		re_get.setVisibility(View.GONE);
		finish = (Button)findViewById(R.id.finish);
		finish.setOnClickListener(mOnClickListener);
		test_value = (TextView) findViewById(R.id.test_value);
		test_value.setVisibility(View.GONE);
		pef = (TextView) findViewById(R.id.pef_line);
		pefp = (TextView) findViewById(R.id.pef_tip_value);//pef_tip_value);
		fev = (TextView) findViewById(R.id.fev1_line);
		fevp = (TextView) findViewById(R.id.fev1_tip_value);
		fvc = (TextView) findViewById(R.id.fvc_line);
		fevfvc = (TextView) findViewById(R.id.fevfvc);
		/*start_btn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				openUsbSerial();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				writeDataToSerial();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Log.d(TAG, ".......after sleep start inflate");
				LayoutInflater inflater = getLayoutInflater();
				View layout = inflater.inflate(R.layout.received, null);
				setContentView(layout);
				Log.d(TAG, "inflater.inflate(R.layout.received");
			}
		});*/

		/*btWrite = (Button) findViewById(R.id.button2);        
		btWrite.setOnClickListener(new Button.OnClickListener() {		
			public void onClick(View v) {
				etWrite = (EditText) findViewById(R.id.editText1);
				writeDataToSerial();
			}
		});*/

	/*	btRead = (Button) findViewById(R.id.button3);        
		btRead.setOnClickListener(new Button.OnClickListener() {		
			public void onClick(View v) {				
				etRead = (EditText) findViewById(R.id.editText2);
				readDataFromSerial();
			}
		});*/

	/*	btLoopBack = (Button) findViewById(R.id.button4);        
		btLoopBack.setOnClickListener(new Button.OnClickListener() {		
			public void onClick(View v) {				
				pbLoopBack = (ProgressBar) findViewById(R.id.ProgressBar1);
				setProgressBarVisibility(true);
				pbLoopBack.setIndeterminate(false);
				pbLoopBack.setVisibility(View.VISIBLE);
				pbLoopBack.setProgress(0);
				tvLoopBack = (TextView) findViewById(R.id.textView2);
				new Thread(tLoop).start();
			}
		});        

		
		btGetSN = (Button) findViewById(R.id.btn_GetSN);       
		tvShowSN = (TextView) findViewById(R.id.text_ShowSN);
		tvShowSN.setText("");
		btGetSN.setOnClickListener(new Button.OnClickListener() {		
			public void onClick(View v) {				
		
			
				ShowPL2303HXD_SerialNmber();
				
			}
		});  */
		// get service
		mSerial = new PL2303Driver((UsbManager) getSystemService(Context.USB_SERVICE),
				this, ACTION_USB_PERMISSION);
				//this, ACTION_USB_PERMISSION); 

		// check USB host function.
		if (!mSerial.PL2303USBFeatureSupported()) {

			Toast.makeText(this, "No Support USB host API", Toast.LENGTH_SHORT)
			.show();

			Log.d(TAG, "No Support USB host API");

			mSerial = null;

		}else {
			new Handler().postDelayed(new Runnable() {
				public void run() {
					openUsbSerial();
					writeReadDate();
				}
			}, 500);
			//checkTime();
			Log.d(TAG, "Leave onCreate");
		}
	}//onCreate
	private void writeReadDate()
	{
		new Handler().postDelayed(new Runnable() {
			public void run() {
				writeDataToSerial();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//new Thread(readLoop).start();
				//readDataFromSerial();
				//readThread();
			}
		}, 1500);
		new Thread(readLoop).start();
	}

	protected void onStop() {
		Log.d(TAG, "Enter onStop");
		super.onStop();        
		Log.d(TAG, "Leave onStop");
	}    

	@Override
	protected void onDestroy() {
		Log.d(TAG, "Enter onDestroy");   

		isFinished = true;
		if(mSerial!=null) {
			mSerial.end();
			mSerial = null;
		}    	

		super.onDestroy();        
		Log.d(TAG, "Leave onDestroy");
	}    

	public void onStart() {
		Log.d(TAG, "Enter onStart");
		super.onStart();
		Log.d(TAG, "Leave onStart");
	}

	public void onResume() {
		Log.d(TAG, "Enter onResume"); 
		super.onResume();
		String action =  getIntent().getAction();
		Log.d(TAG, "onResume:"+action);

		//if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action))        
		if(!mSerial.isConnected()) {
			if (SHOW_DEBUG) {
				Log.d(TAG, "New instance : " + mSerial);
			}

			if( !mSerial.enumerate() ) {

				Toast.makeText(this, "no more devices found", Toast.LENGTH_SHORT).show();     
				return;
			} else {
				Log.d(TAG, "onResume:enumerate succeeded!");
			}    		 
		}//if isConnected  
		//Toast.makeText(this, "attached", Toast.LENGTH_SHORT).show();

		Log.d(TAG, "Leave onResume"); 
	}
	private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(final View v) {
			switch (v.getId()) {
			//	case R.id.start_btn1:
				//	openUsbSerial();
					/*try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					writeDataToSerial();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Log.d(TAG, ".......after sleep start inflate");
					LayoutInflater inflater = getLayoutInflater();
					View layout = inflater.inflate(R.layout.received, null);
					setContentView(layout);

					Button re_get = (Button)layout.findViewById(R.id.re_get);
					re_get.setOnClickListener(mOnClickListener);
					Log.d(TAG, "inflater.inflate(R.layout.received");*/
					//break;
				case R.id.re_get:
					/*if(opened == false) openUsbSerial();
					pef.setText("_ _ _ _");
					pef.setTextColor(getResources().getColor(R.color.WHITE));
					pefp.setText("_ _ _");
					pefp.setTextColor(getResources().getColor(R.color.WHITE));
					fev.setText("_ _ _ _");
					fev.setTextColor(getResources().getColor(R.color.WHITE));
					fevp.setText("_ _ _");
					fevp.setTextColor(getResources().getColor(R.color.WHITE));
					fvc.setText("_ _ _ _");
					fvc.setTextColor(getResources().getColor(R.color.WHITE));
					fevfvc.setText("_ _ _");
					fevfvc.setTextColor(getResources().getColor(R.color.WHITE));

					Log.d(TAG, "xxx re-write writeDataToSerial");
					writeDataToSerial();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					readDataFromSerial();*/
					openUsbSerial();
					writeReadDate();
					break;
				case R.id.finish:
					isFinished = true;
					finish();
					break;
			}
		}
	};
	/*
    public void SetNewVIDPID(){

    	 Log.d(TAG, "Enter SetNewVIDPID"); 
    	 String strVIDPID = etNewVIDPID.getText().toString();    	 
         Log.d(TAG, "new VID_PID : " + strVIDPID);

       	 if(!mSerial.isConnected()) {
             if (SHOW_DEBUG) {
              	  Log.d(TAG, "New instance : " + mSerial);
             }

    		 if( !mSerial.Set_NewVID_PID(strVIDPID) ) {
    			 Log.d(TAG, "SetNewVIDPID_2 : " + mSerial);
               	 return;
              } else {
                 Log.d(TAG, "onResume:enumerate succeeded!");
              }    		 
          }//if isConnected  
		  Toast.makeText(this, "attached", Toast.LENGTH_SHORT).show();
		  Log.d(TAG, "attached!");		
		  openUsbSerial();       	
          Log.d(TAG, "Leave SetNewVIDPID");     	
    }
	 */
	private void openUsbSerial() {
		Log.d(TAG, "Enter  openUsbSerial");

		opened = true;
		if(null==mSerial)
			return;   	 

		if (mSerial.isConnected()) {
			if (SHOW_DEBUG) {
				Log.d(TAG, "openUsbSerial : isConnected ");
			}
			//String str = PL2303HXD_BaudRate_spinner.getSelectedItem().toString();
			int baudRate= 115200;//Integer.parseInt(str);
			switch (baudRate) {
			case 9600:
				mBaudrate = PL2303Driver.BaudRate.B9600;
				break;
			case 19200:
				mBaudrate =PL2303Driver.BaudRate.B19200;
				break;
			case 115200:
				mBaudrate =PL2303Driver.BaudRate.B115200;
				break;
			default:
				mBaudrate =PL2303Driver.BaudRate.B9600;
				break;
			}   		            
			Log.d(TAG, "baudRate:"+baudRate);
			// if (!mSerial.InitByBaudRate(mBaudrate)) {
				if (!mSerial.InitByBaudRate(mBaudrate,700)) {
					if(!mSerial.PL2303Device_IsHasPermission()) {
						Toast.makeText(this, "cannot open, maybe no permission", Toast.LENGTH_SHORT).show();		
					}

					if(mSerial.PL2303Device_IsHasPermission() && (!mSerial.PL2303Device_IsSupportChip())) {
						Toast.makeText(this, "cannot open, maybe this chip has no support, please use PL2303HXD / RA / EA chip.", Toast.LENGTH_SHORT).show();
					}
				} else {        	
					
						Toast.makeText(this, "connected : " , Toast.LENGTH_LONG).show();
					
				}
		}//isConnected

		Log.d(TAG, "Leave openUsbSerial");
	}//openUsbSerial

	private int andData(byte bbb){
		byte tmp = (byte)0x000000FF;
		return bbb & tmp;
	}
	private Runnable readLoop = new Runnable() {
		public void run() {
			Log.d(TAG, "xxxxx Start readDataFromSerial");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			readDataFromSerial();
		}
	};


	private void readDataFromSerial() {

		int len = 0, change,change1,times = 0;
		char c;
		// byte[] rbuf = new byte[4096];
		byte[] rbuf = new byte[100];
		StringBuffer sbHex=new StringBuffer();
		String pef_s,pefp_s,fev_s,fevp_s,fvc_s,fevfvc_s;
		boolean dataError = false;

		Log.d(TAG, "Enter readDataFromSerial");

		if(null==mSerial)
			return;

		if(!mSerial.isConnected()) 
			return;

		len = mSerial.read(rbuf);
		while(!(len > 0) && (isFinished == false)){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			len = mSerial.read(rbuf);
			times++;
			Log.d(TAG, "always looper times = " + times);
		}
		if(len<0) {
			Log.d(TAG, "Fail to bulkTraxxxxxxxxxnsfer(read data)");
			return;
		}

		if (len > 0) {
			//Toast.makeText(this, "Read Len: "+len, Toast.LENGTH_LONG).show();
			if (SHOW_DEBUG) {
				Log.d(TAG, "read len : " + len);
			}                
			//rbuf[len] = 0;
			gotData = true;
			for (int j = 0; j < len; j++) {     //
				sbHex.append(Integer.toHexString(rbuf[j]&0x000000FF));
				sbHex.append(" ");
				if((rbuf[j]&0x000000FF) == 0xc5){
					Log.d(TAG, "Find C5 : " +  Integer.toHexString(rbuf[j+1]&0x000000FF));
					if(j < (len - 1) && ((rbuf[j+1]&0x000000FF) == 0x5c)){//����֡
						change = rbuf[j+6] & 0xff;
						change = change << 8;
						change1 = rbuf[j+7] & 0xff;
						change = change + change1;

						if(rbuf[j+2] == 0x08 && 
							rbuf[j+3] == 0x00 &&
							rbuf[j+4] == 0x08){//stop data
							Log.d(TAG, "Stop data");
							break;//stop_read();
						}
						if(rbuf[j+2] == 0x09 && 
							rbuf[j+3] == 0x01 &&
							rbuf[j+4] == 0x01){//error data
							//dialog
							Log.d(TAG, "Error data");
							dataError = true;
							Message m= new Message();
							m.what = GOT_ERROR;
							handleDataHandler.sendMessage(m);
							//return;//return_error();
						}
						if(rbuf[j+2] == 0x02 && 
							rbuf[j+3] == 0x04 &&
							rbuf[j+4] == 0x00){//PEF data
							pef_s = Integer.toString(change);
							Message m= new Message();
							m.what = GOT_PEF;
							//m.arg1 = pef_s;
							m.arg1 = change;
							handleDataHandler.sendMessage(m);
							continue;
						}
						if(rbuf[j+2] == 0x03 && 
							rbuf[j+3] == 0x04 &&
							rbuf[j+4] == 0x00){//PEF% data
							pefp_s = Integer.toString(change);
							Message m= new Message();
							m.what = GOT_PEFP;
							//m.arg1 = pefp_s;
							m.arg1 = change;
							handleDataHandler.sendMessage(m);
							Log.d(TAG, "PEF % data");
							continue;
						}
						if(rbuf[j+2] == 0x04 && 
							rbuf[j+3] == 0x04 &&
							rbuf[j+4] == 0x00){//FEV data
							fev_s = Integer.toString(change);
							Log.d(TAG, "fev data " + change + " fev_s = " + fev_s + " rbuf[j+7] = " + rbuf[j+7] + " change1 " + change1);
							String tmp = fev_s.substring(0,fev_s.length()-2) + "." + fev_s.charAt(fev_s.length()-2) + fev_s.charAt(fev_s.length()-1);
							Message m= new Message();
							m.what = GOT_FEV;
							//m.arg1 = tmp;
							m.arg1 = change;
							handleDataHandler.sendMessage(m);
							Log.d(TAG, "fev data");
							continue;
						}
						if(rbuf[j+2] == 0x05 && 
							rbuf[j+3] == 0x04 &&
							rbuf[j+4] == 0x00){//FEV1% data
							fevp_s = Integer.toString(change);
							Message m= new Message();
							m.what = GOT_FEVP;
							//m.arg1 = fevp_s;
							m.arg1 = change;
							handleDataHandler.sendMessage(m);
							Log.d(TAG, "fev % data");
							continue;
						}
						if(rbuf[j+2] == 0x06 && 
							rbuf[j+3] == 0x04 &&
							rbuf[j+4] == 0x00){//FVC data
							fvc_s = Integer.toString(change);
							String tmp = fvc_s.substring(0,fvc_s.length()-2) + "." + fvc_s.charAt(fvc_s.length()-2) + fvc_s.charAt(fvc_s.length()-1);
							Message m= new Message();
							m.what = GOT_FVC;
							//m.arg1 = tmp;
							m.arg1 = change;
							handleDataHandler.sendMessage(m);
							Log.d(TAG, "fvc data");
							continue;
						}
						if(rbuf[j+2] == 0x07 && 
							rbuf[j+3] == 0x04 &&
							rbuf[j+4] == 0x00){//fve/fvc data
							fevfvc_s = Integer.toString(change);
							Message m= new Message();
							m.what = GOT_FEVFVC;
							//m.arg1 = fevfvc_s;
							m.arg1 = change;
							handleDataHandler.sendMessage(m);
							Log.d(TAG, "fvc / fev data");
							continue;
						}
					}
				}
				//sbHex.append(Integer.toHexString(rbuf[j]&0x000000FF));
				//sbHex.append(" ");
			}
			test_value.setText(sbHex.toString());
			Log.d(TAG, "test_value  : " + test_value);
			if(dataError == true){
				Looper.prepare();
				new AlertDialog.Builder(this)//should use activity, not context
						.setTitle("错误提示")
						.setMessage("长时间未检测到流量信号，请重新启动测量")
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,int which) {
								isFinished = true;finish();}}).show();
				Looper.loop();
			}
		}
		else {     	
			if (SHOW_DEBUG) {
				Log.d(TAG, "read len : 0 ");
			}
			test_value.setText("empty");
			return;
		}

		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Log.d(TAG, "Leave readDataFromSerial");
	}//readDataFromSerial

	private void writeDataToSerial() {

		Log.d(TAG, "Enter writeDataToSerial");

		if(null==mSerial)
			return;

		if(!mSerial.isConnected()) 
			return;

		/*String strWrite = etWrite.getText().toString();

        //strWrite="012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789";
       // strWrite = changeLinefeedcode(strWrite);
         strWrite="012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789";
         if (SHOW_DEBUG) {
            Log.d(TAG, "PL2303Driver Write(" + strWrite.length() + ") : " + strWrite);
        }
        int res = mSerial.write(strWrite.getBytes(), strWrite.length());
		if( res<0 ) {
			Log.d(TAG, "setup: fail to controlTransfer: "+ res);
			return;
		} 

		Toast.makeText(this, "Write length: "+strWrite.length()+" bytes", Toast.LENGTH_SHORT).show();  
		 */
		// test data: 600 byte
		//strWrite="AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
		/*if (SHOW_DEBUG) {
			Log.d(TAG, "PL2303Driver Write 2(" + strWrite.length() + ") : " + strWrite);
		}
		Log.d(TAG, "lmy  strWrite.getBytes(): "+ strWrite.getBytes());*/
		short ddt = 0xC5;
		short FF = 0xff;
		byte[] bbt = new byte[5];
		bbt[0] = (byte)(ddt & FF);
		bbt[1] = (byte)(0x5C & 0xff);
		bbt[2] = (byte)(0x01 & 0xff);
		bbt[3] = (byte)(0x00 & 0xff);
		bbt[4] = (byte)(0x01 & 0xff);
		//int res = mSerial.write(strWrite.getBytes(), strWrite.length());
		int res = mSerial.write(bbt, bbt.length);
		Log.d(TAG, "mSerial.write res = "  + bbt[0]+" "+bbt[1]+" "+bbt[2]+" "+bbt[3]+" " +bbt[4]+" len = " + bbt.length);
		if( res<0 ) {
			Log.d(TAG, "setup2: fail to controlTransfer: "+ res);
			return;
		} 

		//Toast.makeText(this, "Write length: "+bbt.length+" bytes", Toast.LENGTH_SHORT).show();

		Log.d(TAG, "Leave writeDataToSerial");
	}//writeDataToSerial
	
	
	private void ShowPL2303HXD_SerialNmber() {



		Log.d(TAG, "Enter ShowPL2303HXD_SerialNmber");

		if(null==mSerial)
			return;        

		if(!mSerial.isConnected()) 
			return;

		if(mSerial.PL2303Device_GetSerialNumber()!=NULL) {
			tvShowSN.setText(mSerial.PL2303Device_GetSerialNumber());
			
		}
		else{
			tvShowSN.setText("No SN");
			
		}

		Log.d(TAG, "Leave ShowPL2303HXD_SerialNmber");	
	}//ShowPL2303HXD_SerialNmber

	private static final int GOT_PEF = 0x200;
	private static final int GOT_PEFP = 0x201;
	private static final int GOT_FEV = 0x202;
	private static final int GOT_FEVP = 0x203;
	private static final int GOT_FVC = 0x204;
	private static final int GOT_FEVFVC = 0x205;
	private static final int GOT_ERROR = 0x206;

	Handler handleDataHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch(msg.what){
				case GOT_PEF:
					pef.setText( Integer.toString(msg.arg1));
					pef.setTextColor(getResources().getColor(R.color.RED));
					break;
				case GOT_PEFP:
					pefp.setText( Integer.toString(msg.arg1));
					pefp.setTextColor(getResources().getColor(R.color.RED));
					break;
				case GOT_FEV:
					String commer = Integer.toString(msg.arg1);
					String tmp = commer.substring(0,commer.length()-2) + "." + commer.charAt(commer.length()-2) + commer.charAt(commer.length()-1);
					fev.setText(tmp);
					fev.setTextColor(getResources().getColor(R.color.RED));
					break;
				case GOT_FEVP:
					fevp.setText( Integer.toString(msg.arg1));
					fevp.setTextColor(getResources().getColor(R.color.RED));
					break;
				case GOT_FVC:
					String commer1 = Integer.toString(msg.arg1);
					String tmp1 = commer1.substring(0,commer1.length()-2) + "." + commer1.charAt(commer1.length()-2) + commer1.charAt(commer1.length()-1);
					fvc.setText( tmp1);
					fvc.setTextColor(getResources().getColor(R.color.RED));
					break;
				case GOT_FEVFVC:
					fevfvc.setText( Integer.toString(msg.arg1));
					fevfvc.setTextColor(getResources().getColor(R.color.RED));
					break;
				case GOT_ERROR:
/*					new AlertDialog.Builder(getApplicationContext())//should use activity, not context
							.setTitle("错误提示")
							.setMessage("长时间未检测到流量信号，请重新启动测量")
							.setPositiveButton("确定", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,int which) {
									isFinished = true;finish();}}).show();*/
					break;	
			}
			super.handleMessage(msg);
		}
	};

	//------------------------------------------------------------------------------------------------//
	//--------------------------------------LoopBack function-----------------------------------------//    
	//------------------------------------------------------------------------------------------------//    
	private static final int START_NOTIFIER = 0x100;
	private static final int STOP_NOTIFIER = 0x101;
	private static final int PROG_NOTIFIER_SMALL = 0x102;
	private static final int PROG_NOTIFIER_LARGE = 0x103;
	private static final int ERROR_BAUDRATE_SETUP = 0x8000;
	private static final int ERROR_WRITE_DATA = 0x8001;
	private static final int ERROR_WRITE_LEN = 0x8002;
	private static final int ERROR_READ_DATA = 0x8003;
	private static final int ERROR_READ_LEN = 0x8004;
	private static final int ERROR_COMPARE_DATA = 0x8005;

	Handler myMessageHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch(msg.what){
			case START_NOTIFIER:
				pbLoopBack.setProgress(0);
				tvLoopBack.setText("LoopBack Test start...");
				btWrite.setEnabled(false);
				btRead.setEnabled(false);
				break;
			case STOP_NOTIFIER:
				pbLoopBack.setProgress(pbLoopBack.getMax());
				tvLoopBack.setText("LoopBack Test successfully!");
				btWrite.setEnabled(true);
				btRead.setEnabled(true);
				break;
			case PROG_NOTIFIER_SMALL:
				pbLoopBack.incrementProgressBy(5);
				break;
			case PROG_NOTIFIER_LARGE:
				pbLoopBack.incrementProgressBy(10);					
				break;	
			case ERROR_BAUDRATE_SETUP:					
				tvLoopBack.setText("Fail to setup:baudrate "+msg.arg1);
				break;
			case ERROR_WRITE_DATA:
				tvLoopBack.setText("Fail to write:"+ msg.arg1);				
				break;
			case ERROR_WRITE_LEN:
				tvLoopBack.setText("Fail to write len:"+msg.arg2+";"+ msg.arg1);
				break;
			case ERROR_READ_DATA:
				tvLoopBack.setText("Fail to read:"+ msg.arg1);					
				break;
			case ERROR_READ_LEN:
				tvLoopBack.setText("Length("+msg.arg2+") is wrong! "+ msg.arg1);
				break;
			case ERROR_COMPARE_DATA:
				tvLoopBack.setText("wrong:"+ 
						String.format("rbuf=%02X,byteArray1=%02X", msg.arg1, msg.arg2));
				break;

			}	
			super.handleMessage(msg);
		}//handleMessage
	};

	private void Send_Notifier_Message(int mmsg) {
		Message m= new Message();
		m.what = mmsg;
		myMessageHandler.sendMessage(m);
		Log.d(TAG, String.format("Msg index: %04x", mmsg));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void Send_ERROR_Message(int mmsg, int value1, int value2) {
		Message m= new Message();
		m.what = mmsg;
		m.arg1 = value1;
		m.arg2 = value2;
		myMessageHandler.sendMessage(m);
		Log.d(TAG, String.format("Msg index: %04x", mmsg));
	}	

	private Runnable tLoop = new Runnable() {
		public void run() {	

			int res = 0, len, i;
			Time t = new Time();
			byte[] rbuf = new byte[4096];    	
			final int mBRateValue[] = {9600, 19200, 115200};    	
			PL2303Driver.BaudRate mBRate[] = {PL2303Driver.BaudRate.B9600, PL2303Driver.BaudRate.B19200, PL2303Driver.BaudRate.B115200};

			if(null==mSerial)
				return;            	    	

			if(!mSerial.isConnected()) 
				return;		

			t.setToNow();
			Random mRandom = new Random(t.toMillis(false));

			byte[] byteArray1 = new byte[256]; //test pattern-1    	
			mRandom.nextBytes(byteArray1);//fill buf with random bytes
			Send_Notifier_Message(START_NOTIFIER);    	

			for(int WhichBR=0;WhichBR<mBRate.length;WhichBR++) {

				try {
					res = mSerial.setup(mBRate[WhichBR], mDataBits, mStopBits, mParity, mFlowControl);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if( res<0 ) {
					Send_Notifier_Message(START_NOTIFIER);
					Send_ERROR_Message(ERROR_BAUDRATE_SETUP, mBRateValue[WhichBR], 0);					
					Log.d(TAG, "Fail to setup="+res);
					return;
				} 
				Send_Notifier_Message(PROG_NOTIFIER_LARGE);

				for(int times=0;times<2;times++) {

					len = mSerial.write(byteArray1, byteArray1.length);
					if( len<0 ) {
						Send_ERROR_Message(ERROR_WRITE_DATA, mBRateValue[WhichBR], 0);		       			
						Log.d(TAG, "Fail to write="+len);
						return;
					}

					if( len!=byteArray1.length ) {		       			
						Send_ERROR_Message(ERROR_WRITE_LEN, mBRateValue[WhichBR], len);		       			
						return;
					} 
					Send_Notifier_Message(PROG_NOTIFIER_SMALL);

					len = mSerial.read(rbuf);
					if(len<0) {
						Send_ERROR_Message(ERROR_READ_DATA, mBRateValue[WhichBR], 0);
						return;
					}
					Log.d(TAG, "read length="+len+";byteArray1 length="+byteArray1.length);

					if(len!=byteArray1.length) {		    	    	
						Send_ERROR_Message(ERROR_READ_LEN, mBRateValue[WhichBR], len);
						return;    	    	
					}  		
					Send_Notifier_Message(PROG_NOTIFIER_SMALL);

					for(i=0;i<len;i++) {
						if(rbuf[i]!=byteArray1[i]) {	
							Send_ERROR_Message(ERROR_COMPARE_DATA, rbuf[i], byteArray1[i]);		    	    		
							Log.d(TAG, "Data is wrong at "+ 
									String.format("rbuf[%d]=%02X,byteArray1[%d]=%02X", i, rbuf[i], i, byteArray1[i]));
							return;    	    	    	    		
						}//if
					}//for
					Send_Notifier_Message(PROG_NOTIFIER_LARGE);

				}//for(times)    	    

			}//for(WhichBR)

			try {
				res = mSerial.setup(mBaudrate, mDataBits, mStopBits, mParity, mFlowControl);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if( res<0 ) {
				Send_ERROR_Message(ERROR_BAUDRATE_SETUP, 0, 0);				
				return;
			}   		    		    			
			Send_Notifier_Message(STOP_NOTIFIER);    	

		}//run()
	};//Runnable tLoop

	public class MyOnItemSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

			if(null==mSerial)
				return;

			if(!mSerial.isConnected()) 
				return;

			int baudRate=0;
			String newBaudRate;
			Toast.makeText(parent.getContext(), "newBaudRate is-" + parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
			newBaudRate= parent.getItemAtPosition(position).toString();

			try	{
				baudRate= Integer.parseInt(newBaudRate);
			}
			catch (NumberFormatException e)	{
				System.out.println(" parse int error!!  " + e);
			}

			switch (baudRate) {
			case 9600:
				mBaudrate =PL2303Driver.BaudRate.B9600;
				break;
			case 19200:
				mBaudrate =PL2303Driver.BaudRate.B19200;
				break;
			case 115200:
				mBaudrate =PL2303Driver.BaudRate.B115200;
				break;
			default:
				mBaudrate =PL2303Driver.BaudRate.B9600;
				break;
			}   			 

			int res = 0;
			try {
				res = mSerial.setup(mBaudrate, mDataBits, mStopBits, mParity, mFlowControl);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if( res<0 ) {
				Log.d(TAG, "fail to setup");
				return;
			}
		}
		public void onNothingSelected(AdapterView<?> parent) {
			// Do nothing.    
		}
	}//MyOnItemSelectedListener
	private void checkTime(){
		new Thread(new Runnable() {
			public void run() {
				checkReadData();
			}
		}).start();
	}
	private void checkReadData(){
		long nowTime;
		long startTime = SystemClock.uptimeMillis();
		while(isFinished == false){//reading
			if(gotData == true){
				gotData = false;
				startTime = SystemClock.uptimeMillis();
			}else {
				nowTime = SystemClock.uptimeMillis();
				Log.d(TAG, "checkReadData " + (nowTime - startTime > 60*1000) + " isfinish " + isFinished);
				if(nowTime - startTime > 60*1000){
					Looper.prepare();
					new AlertDialog.Builder(this)//should use activity, not context
							.setTitle("错误提示")
							.setMessage("长时间未检测到流量信号，请重新启动测量")
							.setPositiveButton("确定", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,int which) {
									finish();}}).show();
					Looper.loop();
				}
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
