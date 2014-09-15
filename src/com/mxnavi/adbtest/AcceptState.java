package com.mxnavi.adbtest;

import android.app.AlertDialog;
import android.app.Dialog;

public class AcceptState extends SendState {

	@Override
	public SendStateResult Handle(SendStateContext context) {
		// TODO Auto-generated method stub
        try {  
          	
        	SocketHolder.socket = SocketHolder.ss.accept();
        	
           } catch(Exception e) {
        	   Dialog alertDialog = new AlertDialog.Builder(SocketHolder.activity). 
		                setTitle("¥ÌŒÛ"). 
		                setMessage("accept ß∞‹"). 
		                setIcon(R.drawable.ic_launcher). 
		                create(); 
		        alertDialog.show(); 
        	   e.printStackTrace();
        	   return SendStateResult.FAILED;
           }
        context.setState(new SendFileState());
        return SendStateResult.CONTINUE;
	}
}
