package com.kevin.downloader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLConnection;

public class DownloadThread extends Thread{

	
	private long startByte;
	private long len;
	private URL url;
	
	private int threadNum;
	
	private RandomAccessFile randomFile;

	public DownloadThread(long startByte, long len, URL url, int threadNum, RandomAccessFile randomFile ) {
		super();
		this.startByte = startByte;
		this.len = len;
		this.url = url;
		this.threadNum = threadNum;
		this.randomFile = randomFile;
	}



	@Override
	public void run() {
		
		try {
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("Range", " bytes="+startByte+"-"+(startByte+len));
			InputStream is = conn.getInputStream();

			byte buf []  = new byte[(int)len/20];
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			int hasRead = 0;
			while((hasRead = is.read( buf)) > 0){
				baos.write(buf, 0, hasRead);
			}
			
			
			byte [] bytes = baos.toByteArray();
			randomFile.seek(startByte);
			randomFile.write(bytes);
			
			System.out.println("thread " + threadNum +" over ..");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}



	public long getStartByte() {
		return startByte;
	}



	public void setStartByte(long startByte) {
		this.startByte = startByte;
	}



	public long getLen() {
		return len;
	}



	public void setLen(long len) {
		this.len = len;
	}
	
	
}
