package com.kevin.downloader;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MainMultiThread {

	public static void main(String[] args)throws Throwable {
		new MainMultiThread().go();
	}
	
	Object monitor = new Object();
	int threadCount = 5;
	List<DownloadThread> threadsList = new ArrayList<DownloadThread>();
	
	void go() throws Throwable {
		ExecutorService service = Executors.newFixedThreadPool(threadCount);
		
		URL url = new URL("http://httpd.apache.org/images/httpd_logo_wide_new.png");
		
		String filename = url.toString().substring(url.toString().lastIndexOf("/")+1);
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
			t.setMonitor(this.monitor);
			threadsList.add(t);
			service.execute(t);
		}

		while (true) {
			boolean terminated = false;

			synchronized (monitor) {
				for (int i = 0; i < threadsList.size(); i++) {
					if (threadsList.get(i).isOver()) {
						terminated = true;
					} else {
						terminated = false;
						break;
					}

				}
				
				if (terminated) {
					service.shutdownNow();
					break;
				}else{
					monitor.wait();
				}
			}

		}
		
	}
}

