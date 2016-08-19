package com.main.util;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;  
public class Util {

	 public static  String UPLOADDIR = "FileUpload/";  
	
	/**
	 * 将链接生成访问二维码
	 * @param basePath 下载请求路径即项目访问路径
	 * @param filePath 文件物理路径
	 * @param fileName 文件名称
	 * @throws IOException IO异常
	 * @throws WriterException Writer异常
	 * @return 返回二维码下载地址（访问地址）
	 */
	public static String createQRCode(String basePath,String filePath,String fileName) throws IOException, WriterException{
		//文件下载访问路径
		String str = basePath+"/downloadFile.htm?fileName="+fileName;
		// 图像宽度
		int width = 200; 
		// 图像高度
        int height = 200;  
        // 图像类型  
        String format = "png";
        //使用google的zxing包生成二维码图片
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();  
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");  
        // 生成矩阵  
        BitMatrix bitMatrix = new MultiFormatWriter().encode(str,BarcodeFormat.QR_CODE, width, height, hints);
        //拼接二维码图片保存路径
        String path = filePath.substring(0,filePath.lastIndexOf("\\"))+"\\QRCode\\QR"+fileName.substring(0, fileName.indexOf("."))+"."+format;
        // 输出图像 
        MatrixToImageWriter.writeToFile(bitMatrix, format, new File(path));
        //返回二维码下载地址（访问地址）
        return basePath+"/downloadQR.htm?fileName="+"QR"+fileName.substring(0, fileName.indexOf("."))+"."+format;
	}
	/**
	 * 下载文件
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param storeName 请求文件名称
	 * @param contentType  头信息格式
	 * @param realName 原名称
	 * @throws Exception
	 */
	 public static void download(HttpServletRequest request,  
	            HttpServletResponse response, String storeName, String contentType,  
	            String realName) throws Exception {  
	        response.setContentType("text/html;charset=UTF-8");  
	        request.setCharacterEncoding("UTF-8");  
	        BufferedInputStream bis = null;  
	        BufferedOutputStream bos = null;  
	        String ctxPath = request.getSession().getServletContext().getRealPath("\\")+ Util.UPLOADDIR;  
	        String downLoadPath = ctxPath + storeName;  
	        long fileLength = new File(downLoadPath).length();  
	        response.setContentType(contentType);  
	        response.setHeader("Content-disposition", "attachment; filename="  
	                + new String(realName.getBytes("utf-8"), "utf-8"));  
	        response.setHeader("Content-Length", String.valueOf(fileLength));  
	        bis = new BufferedInputStream(new FileInputStream(downLoadPath));  
	        bos = new BufferedOutputStream(response.getOutputStream());  
	        byte[] buff = new byte[2048];  
	        int bytesRead;  
	        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
	            bos.write(buff, 0, bytesRead);  
	        }  
	        bis.close();  
	        bos.close();  
	    }  
	
}
