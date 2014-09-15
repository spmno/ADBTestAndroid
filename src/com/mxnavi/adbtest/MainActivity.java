package com.mxnavi.adbtest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.mxnavi.adbtest.SendState.SendStateResult;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	//private static final String HOST = "127.0.0.1";  
    private static final int PORT = 9999;  
    SendStateContext sendStateContext;
    String buffer = ""; 
    
    EditText filesEditText;
    
    public Handler myHandler = new Handler() {  
        @Override  
        public void handleMessage(Message msg) {  
            if (msg.what == 0x11) {  
            	for (String fileName : FileContainer.filelist) {
            		filesEditText.append(fileName);
            		filesEditText.append("\n");
            	}
            	
            } else if (msg.what == 0x12) {
        		Dialog alertDialog = new AlertDialog.Builder(MainActivity.this). 
		                setTitle("提示"). 
		                setMessage("文件传输完成"). 
		                setIcon(R.drawable.ic_launcher). 
		                create(); 
		        alertDialog.show(); 
            }
        }  
  
    };  
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SocketHolder.activity = this;
		TextView pathTextView = (TextView)findViewById(R.id.pathTextView);
		filesEditText = (EditText)findViewById(R.id.filesEditText);
		filesEditText.setCursorVisible(false);      
		filesEditText.setFocusable(false);         
		filesEditText.setFocusableInTouchMode(false);    
		//getSDPath();
		pathTextView.setText(getSDPath());
		try {
			SocketHolder.ss = new ServerSocket(PORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Dialog alertDialog = new AlertDialog.Builder(this). 
		                setTitle("错误"). 
		                setMessage("建立服务器失败"). 
		                setIcon(R.drawable.ic_launcher). 
		                create(); 
		        alertDialog.show(); 
		}
		sendStateContext = new SendStateContext(new AcceptState());
		MyThread myThread = new MyThread();
		myThread.start();
	}
	
	public String getSDPath(){
		//return "/storage/sd_external";
		//return "/storage/sdcard1";
		
		String extSDPath = "";
		System.getenv(); // 返回的是一个map
		Map<String, String> map = System.getenv();

    	
		Collection<String> col = map.values();
		Iterator<String> val = col.iterator();
		while(val.hasNext()) {
			extSDPath = val.next();
			//Log.d("123", val.next());
		}
			
		return extSDPath;
		//不同的机型获得的会有所不同，先试试！！
		
	 }
	 class MyThread extends Thread {  
		  

	    @Override  
	    public void run() {  
	        //定义消息 
	     	String edbPath = getSDPath() + "/MXNavi/edb";
	       	FileContainer.getFiles(edbPath);
	        myHandler.sendEmptyMessage(0x11);
	        while (true) {
	        	SendStateResult result = sendStateContext.request();
	        	if (result == SendStateResult.CONTINUE) {
	        		continue;
	        	} else if (result == SendStateResult.COMPLETED) {

			        myHandler.sendEmptyMessage(0x12);
			        continue;
	        	} else if (result == SendStateResult.FAILED) {
	        		Dialog alertDialog = new AlertDialog.Builder(SocketHolder.activity). 
			                setTitle("错误"). 
			                setMessage("文件传输失败"). 
			                setIcon(R.drawable.ic_launcher). 
			                create(); 
			        alertDialog.show(); 
			        break;
	        	}
	        }
	        
	    }
	 }  
	 
}
