package com.main.servicei;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.google.zxing.WriterException;

public interface FileServiceI {

	/**
	 * 文件上传服务层接口
	 * @param file 上传的文件
	 * @param req HttpServletRequest
	 * @param resp HttpServletResponse
	 * @return Map集合
	 */
	public Map<String,Object> upload(MultipartFile file, HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException, WriterException ;
	/**
	 * 文件下载
	 * @param request 
	 * @param response
	 * @throws Exception
	 */
	public void download(HttpServletRequest request,HttpServletResponse response) throws Exception ;
}
