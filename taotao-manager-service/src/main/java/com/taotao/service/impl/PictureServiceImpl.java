package com.taotao.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.utils.FtpUtil;
import com.taotao.common.utils.IDUtils;
import com.taotao.service.PictureService;
@Service
public class PictureServiceImpl implements PictureService {

	@Value("${FTP_ADDRESS}")
	private String host;
	
	@Value("${FTP_PORT}")
	private Integer port;
	
	@Value("${FTP_USERNAME}")
	private String username;
	
	@Value("${FTP_PASSWORD}")
	private String password;
	
	@Value("${FTP_BASE_PATH}")
	private String basePath;
	
	@Value("${IMAGE_BASE_URL}")
	private String baseUrl;
	
	
	@Override
	public Map uploadPicture(MultipartFile file) {
		Map resultMap = new HashMap();
		try{
			//获取图片的原名称
			String oldFileName = file.getOriginalFilename();
			//获取文件的扩展名
			String ext = FilenameUtils.getExtension(oldFileName);
			//生成图片的新名称
			String newName = IDUtils.genImageName() + "." + ext;
			String imgPath = new DateTime().toString("yyyy-MM-dd");
			boolean result = FtpUtil.uploadFile(host, port, username, password, 
					basePath, imgPath, newName, file.getInputStream());
			//返回结果
			if (!result) {
				resultMap.put("error", 1);
				resultMap.put("message", "文件上传失败！");
				return resultMap;
			}
			resultMap.put("error", 0);
			resultMap.put("url", baseUrl + imgPath + "/" + newName);
			return resultMap;
		}catch (Exception e) {
			resultMap.put("error", 1);
			resultMap.put("message", "文件上传发生异常！");
			return resultMap;
		}
	}
	
	//删除图片
	public boolean deletePic(String imgName, String imgPath) {
		boolean result = false;
		try {
			result = FtpUtil.deleteFile(host, port, username, password, basePath, imgPath, imgName);
			if (result) {
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}

}
