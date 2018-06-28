package com.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * CMD进程工具
 * 
 * @author fengyoutian
 *
 */
public class ProcessUtil {
	
	public static boolean print(Process process) throws InterruptedException {
		// 打印程序输出
        readProcessOutput(process);
        // 等待程序执行结束并输出状�?
        if (process.waitFor() == 0) {
            return true;
        } else {
            return false;
        }
	}
	
	private static void readProcessOutput(final Process process) {
        // 将进程的正常输出�? System.out 中打印，进程的错误输出在 System.err 中打�?
        read(process.getInputStream(), System.out);
        read(process.getErrorStream(), System.err);
    }
	
	private static void read(final InputStream inputStream, final PrintStream out) {
    	new Thread() {
    		public void run() {
		        try {
		            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "gbk"));
		            String line;
		            while ((line = reader.readLine()) != null) {
//		            	if (!AAPTLogger.info(line)) {
		            		out.println(line);
//		            	}
		            }
		        } catch (IOException e) {
		        	e.printStackTrace();
		        } finally {
		            try {
		                inputStream.close();
		            } catch (IOException e) {
		            	e.printStackTrace();
		            }
		        }
    		}
    	}.start();
    }
}
