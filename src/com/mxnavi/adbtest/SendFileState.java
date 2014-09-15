package com.mxnavi.adbtest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SendFileState extends SendState {

	@Override
	public SendStateResult Handle(SendStateContext context) {
		// TODO Auto-generated method stub
       	try {
			SocketHolder.in = new BufferedReader(new InputStreamReader(SocketHolder.socket.getInputStream()));
	       	PrintWriter out = new PrintWriter(SocketHolder.socket.getOutputStream());

	       	JSONObject object = new JSONObject();
	       	JSONObject jsonObj = new JSONObject();
	       	jsonObj.put("command", "FILE");
	       	JSONArray jsonarray = new JSONArray();
	        for (String fileName : FileContainer.filelist) {
	        	JSONObject fileNameObject = new JSONObject();
	        	fileNameObject.put("name", fileName);
	        	jsonarray.put(fileNameObject);
	        }
	        object.put("filelist", jsonarray);
	        out.print(object.toString());
	        out.flush();
	        
	       	String str;
	       	str = SocketHolder.in.readLine();
	       	if (str == null) {
	           	context.setState(new AcceptState());
	    		return SendStateResult.CONTINUE;
	       	}
	       	if (str.equals("transfaner ok")) {
	       		return SendStateResult.COMPLETED;
	       	}
	       	//SocketHolder.ss.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       	context.setState(new AcceptState());
		return SendStateResult.CONTINUE;

	}

}
