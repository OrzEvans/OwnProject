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
		//设置request编码
		req.setCharacterEncoding("UTF-8");
		//获取文件原始名称
		String fileOriginalName=file.getOriginalFilename();
		//定义结果集
		Map<String, Object> map = new HashMap<String, Object>();
		//判断请求是否问文件请求（是否是上传请求）
		boolean isMultipart = ServletFileUpload.isMultipartContent(req);
		if (isMultipart) {
			//如果文件不为空
			if (!file.isEmpty()) {
				//得到上传文件存放路径
				String realPath = req.getSession().getServletContext().getRealPath("/FileUpload");
				//定义上传文件
				File uploadFile = new File(realPath);
				if (!uploadFile.exists()) {
					uploadFile.mkdirs();
				}
				//获取项目名称
				String path = req.getContextPath();
				//拼接访问路径  http://ip:port/项目路径
				String basePath = req.getScheme()+"://"+ req.getServerName() + ":" + req.getServerPort()+path;
				//使用UUID重命名文件名称
				String fileName=UUID.randomUUID()+fileOriginalName;
				//拼接文件上传路径
				String filePath=realPath+File.separator+fileName;
				//使用fileupload包中的FileUtils复制文件到文件存放路径
				FileUtils.copyInputStreamToFile(file.getInputStream(),new File(filePath));
				//将文件下载路径生成二维码图，并返回二维码访问路径
				String str=Util.createQRCode(basePath, realPath, fileName);
				//将路径存放在结果集中返回
				map.put("data", str);
				//定义结果集状态
				map.put("status", 0);
				map.put("msg", "上传成功");
			}else{
				//如果文件为空
				map.put("data", "错误：文件不存在！");
				//定义结果集状态
				map.put("status", 1);
				map.put("msg", "上传失败");
			}
			
		}else{
			//如果文件不是文件格式
			map.put("data", "错误：请选择文件");
			map.put("status", 1);
			map.put("msg", "请选择文件");
		}
		return map;
	}

	public void download(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取文件下载的文件名称参数
		String storeName = req.getParameter("fileName"); 
		//定义文件实际名称
		String realName=null;
		//判断文件名称为二维码文件（Q开头）/还是真实文件名称
		if(storeName.startsWith("Q")){
			realName = storeName.substring(38);
		}else{
			realName = storeName.substring(36);
		}
		//由于名称参数是浏览器直接访问，故编码为ISO8859-1，需要转码进行下载，否则乱码，无法找到文件
        byte[] bytes = storeName.getBytes("ISO8859-1");
        storeName= new String(bytes,"UTF-8");
        //定义头信息格式
        String contentType = "application/octet-stream";
        Util.download(req, resp, storeName, contentType,realName); 
	}

}
