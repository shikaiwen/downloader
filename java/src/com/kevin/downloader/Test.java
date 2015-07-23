package com.kevin.downloader;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLConnection;

public class Test {

	static void initProxy(){
		 System.setProperty("http.proxySet", "true");
		 System.setProperty("http.proxyHost", "127.0.0.1");
		 System.setProperty("http.proxyPort", "8888");
	}
	
	public static void main(String[] args) throws IOException {
//		initProxy();
//		new Test().go();
//		File f = new File("/12.jpg");

		FileOutputStream fos = new FileOutputStream("/12.png");
		
		System.out.println(fos.getFD());
//		fos.write(100);
//		fos.flush();fos.close();
	}
	
	int threadCount = 5;
	
	void go() throws IOException{
		
		
		
//		URL url = new URL("http://mirror.bit.edu.cn/apache//ant/binaries/apache-ant-1.9.6-bin.zip");
		
//		URL url = new URL("http://localhost/tt.rar");
		
//			URL url = new URL("http://www.winpcap.org/install/bin/WinPcap_4_1_3.exe");
		URL url = new URL("http://httpd.apache.org/images/httpd_logo_wide_new.png");
		
		String filename = url.toString().substring(url.toString().lastIndexOf("/"));
		File f = new File(filename) ;
		RandomAccessFile randomFile = new RandomAccessFile(f,"rw");
		
		long len = url.openConnection().getContentLengthLong();
		
		
		
		long lenPerThread = len / threadCount;
		
		for(int i =0 ;i < threadCount;i++){
			Thread t;
			
			if(i == (threadCount -1)){
				t = new DownloadThread(i * lenPerThread - 1, len-1 , url, i,randomFile);
			}else{
				t = new DownloadThread(i * lenPerThread , lenPerThread *(i+1) - 1 , url, i,randomFile);
			}
			
			t.start();
			
		}
		
//		while(true){}
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
