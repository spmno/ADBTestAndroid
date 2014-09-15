package com.mxnavi.adbtest;

import java.io.File;
import java.util.ArrayList;

public class FileContainer {
	public static ArrayList<String> filelist = new ArrayList<String>();
	//public static ArrayList<Integer> fileSizeList = new ArrayList<Integer>();
	static void getFiles(String filePath){
		File root = new File(filePath);
	    File[] files = root.listFiles();
		for(File file:files){     
			if(file.isDirectory()){
				getFiles(file.getAbsolutePath());
				
				//System.out.println("显示"+filePath+"下所有子目录及其文件"+file.getAbsolutePath());
			}else{
				filelist.add(file.getAbsolutePath());
				//fileSizeList.add(file.getTotalSpace())file.getUsableSpace()
				//System.out.println("显示"+filePath+"下所有子目录"+file.getAbsolutePath());
			}     
		}
	}
}
