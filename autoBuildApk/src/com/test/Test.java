package com.test;

import java.io.File;
import java.io.IOException;



public class Test {

	
	public static void main(String[] args) throws InterruptedException {
		
		// TODO Auto-generated method stub
		System.out.print("清除缓存：");
		
		 FileUtil.clearDir(new File(FileUtil.getProjectRootPath() + File.separator + "TestDemo"+ File.separator +"app" + File.separator + "build" + File.separator + "outputs" + File.separator +"apk"));
        
		int dexTime = 0;
		while(FileUtil.existFile(FileUtil.getProjectRootPath() + File.separator + "TestDemo"+ File.separator +"app" + File.separator + "build" + File.separator + "outputs" + File.separator +"apk", "app-debug.apk")){
			Thread.sleep(1000);
			dexTime++;
			if(dexTime%10==0){
				System.out.println(".");
			}
			else
		    	System.out.print(".");
		}
		System.out.println("\n清除缓存结束：");
		
		System.out.println("\n覆盖待修改的文件：");
		coerFile();
	
        try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("cmd.exe /c .\\runApk.bat");
			System.out.println("开始打包apk：" + buffer);
			  Runtime rt = Runtime.getRuntime();  
			  
			  Runtime.getRuntime().exec(buffer.toString(), null, new File(FileUtil.getProjectRootPath() + File.separator + "TestDemo"));

		        
				int dexTime1 = 0;
				while(!FileUtil.existFile(FileUtil.getProjectRootPath() + File.separator + "TestDemo"+ File.separator +"app" + File.separator + "build" + File.separator + "outputs" + File.separator +"apk", "app-debug.apk")){
					Thread.sleep(1000);
					dexTime1++;
					if(dexTime1%10==0){
						System.out.println(".");
					}
					else
				    	System.out.print(".");
				}
		        
			
		   	killProcess();
		    	
			System.out.println("\n打包apk文件结束！");
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	
	/**
	 * 关闭命令窗口
	 */
	 public static void killProcess(){
		  Runtime rt = Runtime.getRuntime();
		  Process p = null;  
		  try {
		   rt.exec("cmd.exe /C start wmic process where name='cmd.exe' call terminate");
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
		 }
	 
	 
	 /**
	  *移动覆盖资源文件 
	  */
	 public static void coerFile(){
		 FileUtil.copyFile(FileUtil.getProjectRootPath() + File.separator + "TestDemo"+ File.separator +"app" + File.separator + "src" + File.separator + "main" + File.separator +"res" + File.separator +"values" + File.separator +"activity_main.xml",
				 FileUtil.getProjectRootPath() + File.separator + "TestDemo"+ File.separator +"app" + File.separator + "src" + File.separator + "main" + File.separator +"res" + File.separator +"layout" + File.separator +"activity_main.xml",
				 true);
		
		 FileUtil.clearDir(new File(FileUtil.getProjectRootPath() + File.separator + "TestDemo"+ File.separator +"app" + File.separator + "src" + File.separator + "main" + File.separator +"res" + File.separator +"values" + File.separator +"activity_main.xml"));
	 }
	 
	 
	
}
