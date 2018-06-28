package com.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.List;


/**
 * 文件工具
 * 
 * @author fengyoutian
 * 
 */
public class FileUtil {
	
	/*
	 * @author yanglihang
	 */
	public static String getProjectRootPath() {
		try {
			String jarWholePath = FileUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			jarWholePath = URLDecoder.decode(jarWholePath, "UTF-8");
			String jarPath = new File(jarWholePath).getParentFile().getAbsolutePath();
			return jarPath;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
     * 递归删除目录下的�?有文件及子目录下�?有文�?
     * 
     * @param dir 将要删除的文件目�?
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    public static boolean clearDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = clearDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删�?
        return dir.delete();
    }
    
    /**
     * 遍历目录及其子目录下的所有文�?
     * 
     * @param path 目录全路�?
     * @param list 列表：保存文件对�?
     */
    public static void list(File path, List<File> list) {
        if (!path.exists()){
            return;
        }
        
        if (path.isFile()){
            list.add(path);
        } else{
            File[] files = path.listFiles();
            for (File file : files){
            	list(file, list);
            }
        }
    }
    
    /**
     * 将is流写到descFile文件�?
     * 
     * @param is
     * @param descFile
     * 
     */
    public static void replaceFile(InputStream is, File descFile) {
    	OutputStream os = null;
		
    	try {
			os = new FileOutputStream(descFile);
			write(is, os);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
	    	if (os != null) {
	            try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }   
	        if (is != null) {   
	            try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
		}
    }
    
    /**
     * 将srcFile文件写到descFile文件�?
     * 
     * @param srcFile
     * @param descFile
     * 
     */
    public static void replaceFile(File srcFile, File descFile) {
    	OutputStream os = null;
		InputStream is = null;
		
    	try {
			os = new FileOutputStream(descFile);
			is = new FileInputStream(srcFile);
			write(is, os);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
	    	if (os != null) {
	            try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }   
	        if (is != null) {   
	            try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
		}
    }
    
    public static String readFile(File file) throws IOException {
    	if (file.exists() && file.isFile()) {
    		return new String(getByte(file));
    	} else {
    		return null;
    	}
    }
    
    public static void write(InputStream input, OutputStream output) throws IOException {
		byte[] buff = new byte[1024];
		int bytesRead = 0;
		while ((bytesRead = input.read(buff)) != -1) {
			output.write(buff, 0, bytesRead);
		}
		input.close();
	}
    
    public static void writeToFile(String content, File file) throws IOException {
    	File parentDir = file.getParentFile();
    	if (!parentDir.exists()) {
    		parentDir.mkdirs();
    	}
    	if (!file.exists()) {
    		file.createNewFile();
    	}
    	FileWriter fw = new FileWriter(file, false);
    	BufferedWriter bw = new BufferedWriter(fw);
    	bw.write(content);
    	
    	bw.close();
    	fw.close();
	}
    
    /**
     * 将文本文件中的内容读入到buffer�?
     * 
     * @param buffer 		buffer
     * @param filePath 		文件路径
     * @throws IOException
     */
    public static void readToBuffer(StringBuffer buffer, File file) throws IOException {
        InputStream is = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line = null;
        while ((line = reader.readLine()) != null) {
            buffer.append(line).append("\n");
        }
        reader.close();
        is.close();
    }
    
    /**
     * 把一个文件转化为字节
     *
     * @param file
     * @return byte[]
     * @throws IOException 
     */
    public static byte[] getByte(File file) throws IOException {
        byte[] bytes = null;
        if (null != file && file.exists()) {
            InputStream is = new FileInputStream(file);
            int length = (int) file.length();
            // 当文件的长度超过了int的最大�??
            if (length > Integer.MAX_VALUE) {
                is.close();
                return null;
            }
            bytes = new byte[length];
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }
            // 如果得到的字节长度和file实际的长度不�?致就可能出错�?
            if (offset < bytes.length) {
                is.close();
                return null;
            }
            is.close();
        }
        return bytes;
    }
    
    
    public static boolean existFile(String path, String fileName){
		String filePath = path + File.separator + fileName;
		File file = new File(filePath);
		if(file.exists()){
			return true;
		}
		return false;
	}
    /**
     * 复制单个文件
     * 
     * @param srcFileName
     *            待复制的文件名
     * @param destFileName
     *            目标文件名
     * @param overlay
     *            如果目标文件存在，是否覆盖
     * @return 如果复制成功，则返回true，否则返回false
     */
    public static boolean copyFile(String srcFileName, String destFileName,
    		boolean overlay) {
    	// 判断原文件是否存在
    	File srcFile = new File(srcFileName);
    	if (!srcFile.exists()) {
    		System.out.println("复制文件失败：原文件" + srcFileName + "不存在！");
    		return false;
    	} else if (!srcFile.isFile()) {
    		System.out.println("复制文件失败：" + srcFileName + "不是一个文件！");
    		return false;
    	}
    	// 判断目标文件是否存在
    	File destFile = new File(destFileName);
    	if (destFile.exists()) {
    		// 如果目标文件存在，而且复制时允许覆盖。
    		if (overlay) {
    			// 删除已存在的目标文件，无论目标文件是目录还是单个文件
    			System.out.println("目标文件已存在，准备删除它！");
    			if (!FileUtil.clearDir(new File(destFileName))) {
    				System.out.println("复制文件失败：删除目标文件" + destFileName + "失败！");
    				return false;
    			}
    		} else {
    			System.out.println("复制文件失败：目标文件" + destFileName + "已存在！");
    			return false;
    		}
    	} else {
    		if (!destFile.getParentFile().exists()) {
    			// 如果目标文件所在的目录不存在，则创建目录
    			System.out.println("目标文件所在的目录不存在，准备创建它！");
    			if (!destFile.getParentFile().mkdirs()) {
    				System.out.println("复制文件失败：创建目标文件所在的目录失败！");
    				return false;
    			}
    		}
    	}
    	// 准备复制文件
    	int byteread = 0;// 读取的位数
    	InputStream in = null;
    	OutputStream out = null;
    	try {
    		// 打开原文件
    		in = new FileInputStream(srcFile);
    		// 打开连接到目标文件的输出流
    		out = new FileOutputStream(destFile);
    		byte[] buffer = new byte[1024];
    		// 一次读取1024个字节，当byteread为-1时表示文件已经读完
    		while ((byteread = in.read(buffer)) != -1) {
    			// 将读取的字节写入输出流
    			out.write(buffer, 0, byteread);
    		}
    		System.out.println("复制单个文件" + srcFileName + "至" + destFileName
    				+ "成功！");
    		return true;
    	} catch (Exception e) {
    		System.out.println("复制文件失败：" + e.getMessage());
    		return false;
    	} finally {
    		// 关闭输入输出流，注意先关闭输出流，再关闭输入流
    		if (out != null) {
    			try {
    				out.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    		if (in != null) {
    			try {
    				in.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}
    }
    
    
}
