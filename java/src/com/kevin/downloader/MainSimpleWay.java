package com.kevin.downloader;


import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;

public class MainSimpleWay {

	public static void main(String[] args) throws IOException {
		new MainSimpleWay().go();
	}
	
	int threadCount = 5;
	
	void go() throws IOException{
		
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
