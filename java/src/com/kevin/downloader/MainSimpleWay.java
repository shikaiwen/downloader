package com.kevin.downloader;


import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainSimpleWay {

	static void initProxy(){
		 System.setProperty("http.proxySet", "true");
		 System.setProperty("http.proxyHost", "127.0.0.1");
		 System.setProperty("http.proxyPort", "8888");
	}
	

	
	static void threadT(){
		
		ScheduledExecutorService exe =  Executors.newScheduledThreadPool(2);
		exe.scheduleAtFixedRate(new Runnable(){
			@Override
			public void run() {
				System.out.println(1);
				try {
					Thread.sleep(20000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, 1, 2, TimeUnit.SECONDS);
		
		exe.scheduleAtFixedRate(new Runnable(){
			@Override
			public void run() {
				System.out.println(2);
			}
		}, 1, 3, TimeUnit.SECONDS);
	}
	
	
	public static void main(String[] args) throws IOException {
//		initProxy();
		
//		threadT();
		new MainSimpleWay().go();
//		fos.write(100);
//		fos.flush();fos.close();
	}
	
	
	int threadCount = 5;
	
	
	void go() throws IOException{
		
//		URL url = new URL("http://www.winpcap.org/install/bin/WinPcap_4_1_3.exe");
		URL url = new URL("http://httpd.apache.org/images/httpd_logo_wide_new.png");
		
		String filename = url.toString().substring(url.toString().lastIndexOf("/"));
		File f = new File(filename) ;
		RandomAccessFile randomFile = new RandomAccessFile(f,"rw");
		
		long len = url.openConnection().getContentLengthLong();
		
		long lenPerThread = len / threadCount;
		
		for(int i =0 ;i < threadCount;i++){
			DownloadThread t;
			
			if(i == (threadCount -1)){
				t = new DownloadThread(i * lenPerThread - 1, len-1 , url, i,randomFile);
			}else{
				t = new DownloadThread(i * lenPerThread , lenPerThread *(i+1) - 1 , url, i,randomFile);
			}
			
			t.start();
			
		}
		
		
		
	}
	

	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}
	
}
