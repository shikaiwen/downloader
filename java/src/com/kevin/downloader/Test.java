package com.kevin.downloader;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLConnection;

public class Test {

	public static void main(String[] args) throws IOException {
		new Test().go();
	}
	
	int threadCount = 5;
	
	void go() throws IOException{
		
		
		File f = new File("file.zip") ;
		RandomAccessFile randomFile = new RandomAccessFile(f,"rw");
		
		
		URL url = new URL("http://apache.fayea.com//httpcomponents/httpclient/binary/httpcomponents-client-4.5-bin.tar.gz");
		long len = getContentLen();
		
		
		
		long lenPerThread = len / threadCount;
		
		for(int i =0 ;i < threadCount;i++){
			Thread t;
			if(i == (threadCount -1)){
				t = new DownloadThread(i * lenPerThread - 1, len - (i* lenPerThread) , url, i,randomFile);
			}else{
				t = new DownloadThread(i * lenPerThread - 1, lenPerThread, url , i,randomFile);
			}
			
			t.start();
			
		}
	}
	
	long getContentLen() throws IOException{
		String path = "http://archive.apache.org/dist/axis/axis2/java/core/1.6.2/axis2-1.6.2-bin.zip";
		URL url = new URL(path);
		URLConnection conn = url.openConnection();
		long len = conn.getContentLengthLong();
		return len;
	}

	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}
	
}
