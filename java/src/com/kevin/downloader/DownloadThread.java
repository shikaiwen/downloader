package com.kevin.downloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLConnection;

public class DownloadThread extends Thread{

	
	private long startByte;
	private long endBytes;
	private URL url;
	
	private int threadNum;
	
	private RandomAccessFile randomFile;

	public DownloadThread(long startByte, long endBytes, URL url, int threadNum, RandomAccessFile randomFile ) {
		super();
		this.startByte = startByte;
		this.endBytes = endBytes;
		this.url = url;
		this.threadNum = threadNum;
		this.randomFile = randomFile;
	}


	public static void main(String[] args) throws Exception {
		URL url = new URL("http://apache.fayea.com/httpcomponents/httpclient/binary/httpcomponents-client-4.5-bin.tar.gz");
	
		File f = new File("file.zip") ;
		RandomAccessFile randomFile = new RandomAccessFile(f,"rw");
		
		new DownloadThread(4000000, 6000000 , url, 2,randomFile).start();
	}
	
	@Override
	public void run() {
	System.out.println("Thread"+threadNum+" started ..");
		try {
			URLConnection conn = url.openConnection();
			String headStr =  "bytes="+startByte+"-"+endBytes;
			System.out.println("Thread"+threadNum+ ":"+headStr);
			conn.setRequestProperty("Range",headStr);
			InputStream is = conn.getInputStream();

			byte buf []  = new byte[(int)endBytes/20];
			
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
			String msg = threadNum +e.getMessage() + "";
//			e.printStackTrace();
			System.out.println("ERROR:"+ msg);
		}
		
		
	}



	public long getStartByte() {
		return startByte;
	}



	public void setStartByte(long startByte) {
		this.startByte = startByte;
	}



	public long getEndBytes() {
		return endBytes;
	}



	public void setEndBytes(long endBytes) {
		this.endBytes = endBytes;
	}



	
	
}
