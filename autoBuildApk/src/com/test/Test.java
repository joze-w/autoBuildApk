package com.test;

import java.io.File;
import java.io.IOException;



public class Test {

	
	public static void main(String[] args) throws InterruptedException {
		
		// TODO Auto-generated method stub
		System.out.print("������棺");
		
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
		System.out.println("\n������������");
		
		System.out.println("\n���Ǵ��޸ĵ��ļ���");
		coerFile();
	
        try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("cmd.exe /c .\\runApk.bat");
			System.out.println("��ʼ���apk��" + buffer);
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
		    	
			System.out.println("\n���apk�ļ�������");
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	
	/**
	 * �ر������
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
	  *�ƶ�������Դ�ļ� 
	  */
	 public static void coerFile(){
		 FileUtil.copyFile(FileUtil.getProjectRootPath() + File.separator + "TestDemo"+ File.separator +"app" + File.separator + "src" + File.separator + "main" + File.separator +"res" + File.separator +"values" + File.separator +"activity_main.xml",
				 FileUtil.getProjectRootPath() + File.separator + "TestDemo"+ File.separator +"app" + File.separator + "src" + File.separator + "main" + File.separator +"res" + File.separator +"layout" + File.separator +"activity_main.xml",
				 true);
		
		 FileUtil.clearDir(new File(FileUtil.getProjectRootPath() + File.separator + "TestDemo"+ File.separator +"app" + File.separator + "src" + File.separator + "main" + File.separator +"res" + File.separator +"values" + File.separator +"activity_main.xml"));
	 }
	 
	 
	
}
