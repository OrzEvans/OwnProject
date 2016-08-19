<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="Util.jsp" %>
<title>欢迎使用文件上传功能</title>
<script type="text/javascript" src="<%=path%>/js/index.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<h1 class="text-center text-primary">欢迎使用</h1>
		</div>
		<hr>
		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-4">
				<form id="myform" name="myform" class="form-inline" method="post" action="upload.htm" enctype="multipart/form-data" >
				<input id="file" name="file" type="file" style="display:none;">
				<div class="input-append">
				<input id="choose" name="choose" class="form-control" type="text" placeholder="请选择文件" >
				<button id="chooseBtn" class="btn btn-info" type="button">请选择</button>
				</div>
				<br>
				<button  class="btn btn-primary" type="submit" id="submitBtn">上传</button>
				</form>
				<br>
				<div id="warn">
					<c:if test="${map.status==0}" >
						<span class="text-success">${map.msg}</span>
					</c:if>
					<c:if test="${map.status==1}" >
						<span class="text-danger">${map.msg}</span>
					</c:if>
				</div>
				<div id="url">
					<h3 class="text-primary">二维码访问地址：</h3>
					<textarea class="form-control" rows="5" style="resize: none;" readonly >${map.data }</textarea>
				</div>
				<script type="text/javascript"> $("#url").hide()</script>
				<c:if test="${map.data!=null}"><script type="text/javascript"> $("#url").show()</script></c:if>
				</div>
			<div class="col-md-4"></div>
		</div>
	</div>
</body>
</html>