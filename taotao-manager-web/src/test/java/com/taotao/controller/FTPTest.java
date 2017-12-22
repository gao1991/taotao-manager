package com.taotao.controller;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import com.taotao.common.utils.FtpUtil;

public class FTPTest {

	@Test
	public void testFtpClient() throws Exception {
		//创建一个FtpClient对象
		FTPClient ftpClient = new FTPClient();
		//创建ftp连接。默认是21端口
		ftpClient.connect("192.168.133.128", 21);
		//登录ftp服务器，使用用户名和密码
		ftpClient.login("ftpuser", "ftpuser");
		//上传文件。
		//读取本地文件
		FileInputStream inputStream = new FileInputStream(new File("E:\\360Downloads\\Koala.jpg"));
		//设置上传的路径
		ftpClient.changeWorkingDirectory("/home/ftpuser/www/images");
		//修改上传文件的格式
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		//第一个参数：服务器端文档名
		//第二个参数：上传文档的inputStream
		ftpClient.storeFile("hello1.jpg", inputStream);
		//关闭连接
		ftpClient.logout();
		
	}
	
	@Test
	public void testFtpUtil() throws Exception {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	    String dateNowStr = sdf.format(d); 
		FileInputStream inputStream = new FileInputStream(new File("E:\\360Downloads\\Koala.jpg"));
		FtpUtil.uploadFile("192.168.133.128", 21, "ftpuser", "ftpuser", 
				"/home/ftpuser/www/images", dateNowStr, "Koala11.jpg", inputStream);
		
	}
	
	@Test
	public void dataTest(){
		Date d = new Date();  
        System.out.println(d);  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        String dateNowStr = sdf.format(d);  
        System.out.println("格式化后的日期：" + dateNowStr);
	}
}
