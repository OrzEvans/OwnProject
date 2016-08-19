package com.main.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.zxing.WriterException;
import com.main.servicei.FileServiceI;
import com.main.util.Util;
@Service("FileService")
public class FileServiceImpl implements FileServiceI {

	public Map<String, Object> upload(MultipartFile file, HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException, WriterException {
		req.setCharacterEncoding("UTF-8");
		String fileOriginalName=file.getOriginalFilename();
		Map<String, Object> map = new HashMap<String, Object>();
		boolean isMultipart = ServletFileUpload.isMultipartContent(req);
		if (isMultipart) {
			if (!file.isEmpty()) {
				String realPath = req.getSession().getServletContext().getRealPath("/FileUpload");
				File uploadFile = new File(realPath);
				if (!uploadFile.exists()) {
					uploadFile.mkdirs();
				}
				String path = req.getContextPath();
				String basePath = req.getScheme()+"://"+ req.getServerName() + ":" + req.getServerPort()+path;
				String fileName=UUID.randomUUID()+fileOriginalName;
				String filePath=realPath+File.separator+fileName;
				FileUtils.copyInputStreamToFile(file.getInputStream(),new File(filePath));
				String str=Util.createQRCode(basePath, realPath, fileName);
				
				map.put("data", str);
			}
			map.put("status", 0);
			map.put("msg", "上传成功");
		}
		return map;
	}

	public void download(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String storeName = req.getParameter("fileName"); 
		String realName=null;
		if(storeName.startsWith("Q")){
			realName = storeName.substring(38);
		}else{
			realName = storeName.substring(36);
		}
        byte[] bytes = storeName.getBytes("ISO8859-1");
        storeName= new String(bytes,"UTF-8");
        String contentType = "application/octet-stream";  
        Util.download(req, resp, storeName, contentType,realName); 
	}

}
