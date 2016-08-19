package com.main.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.google.zxing.WriterException;
import com.main.servicei.FileServiceI;
import com.main.util.Util;

@Controller
public class FileController {
	@Resource(name = "FileService")
	private FileServiceI fileService;

	/**
	 * 首页跳转
	 * @return
	 */
	@RequestMapping("/index")
	public String toIndex() {
		return "index";
	}

	/**
	 * 上传文件Controller
	 * @param file 上传的文件
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 * @throws WriterException
	 */
	@RequestMapping("/upload")
	public void upload(@RequestParam("file") MultipartFile file, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, WriterException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		Map<String, Object> map = fileService.upload(file, req, resp);
		req.setAttribute("map", map);
		req.getRequestDispatcher("WEB-INF/Jsp/index.jsp").forward(req, resp);
	}

	/**
	 * 下载原文件
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/downloadFile")
	public String downloadFile(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Util.UPLOADDIR = "FileUpload\\";
		fileService.download(req, resp);
		return null;
	}

	/**
	 * 下载二维码文件
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/downloadQR")
	public String downloadQR(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Util.UPLOADDIR = "QRCode\\";
		fileService.download(req, resp);
		return null;
	}
}
