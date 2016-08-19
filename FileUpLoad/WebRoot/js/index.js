/**
 * 页面加载完成执行
 */
$(function() {
	//初始化
	Index.init();
});
var Index = {
	init : function() {
		//事件初始化
		Index.event.init();
	},
	//事件层
	event : {
		//初始化
		init : function() {
			//绑定选择文件按钮单击事件
			$("#chooseBtn").on("click", function() {
				Index.event.chooseFile();
			});
			//绑定原输入框值改变事件
			$("#file").on("change", function() {
				Index.event.getFile(this);
			});
			//绑定上传按钮单击事件
			$("#submitBtn").on("click", function() {
				Index.event.fileSubmit();
			});
		},
		/**
		 * 选择文件
		 */
		chooseFile : function() {
			$("#file").click();
		},
		/**
		 * 获取文件信息
		 */
		getFile : function(object) {
			$("#choose").val("");
			$("#choose").val($(object).val());
		},
		/**
		 * 文件上传
		 */
		fileSubmit : function() {
			if ("" != $("#choose").val() && $("#choose").val() != null) {
				$("#warn").empty();
				$("#url").empty();
				$("#myform").submit();
			}
			return false;
		}

	}
}