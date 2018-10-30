<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../static/layui/css/layui.css" />
<script type="text/javascript" src="../static/js/jquery-1.11.3.min.js"></script>
<title></title>
</head>

<body style="">
<form id="form">
		<div class="layui-form-item">
			<label class="layui-form-label">电话号码</label>
			<div class="layui-input-inline">
				<input type="text" name="phone" id="phone"
					autocomplete="off" placeholder='请输入电话号码' class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">密码</label>
			<div class="layui-input-inline">
				<input type="text" name="password" id="password"
					autocomplete="off" placeholder='请输入密码' class="layui-input">
			</div>
		</div>
		
		<div class="layui-form-item">
			<label class="layui-form-label">推荐人手机号码</label>
			<div class="layui-input-inline">
				<input type="text" name="extension_code" id="extension_code"
					autocomplete="off" placeholder='请输入推荐人手机号码' class="layui-input">
			</div>
		</div>
		
		<hr />
		<input type="button" onclick="add_edit_task()" value="提交"
			class="layui-btn layui-btn-sm" style="float: right;">
	</form>
</body>
<script type="text/javascript" src="../static/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="../static/layui/layui.js"></script>
<script type="text/javascript">
	function add_edit_task() {
		debugger;
		var phone=$("#phone").val();
		var password=$("#password").val();
		var extensionCode=$("#extension_code").val();
		var param={phone:phone,password:password,extensionCode:extensionCode};
		$.post("../customer/save.do?phone="+phone+"&password="+password+"&extensionCode="+extensionCode,function(data) {
			parent.layer.closeAll();
			window.parent.location.reload();
		})
		return false;
	}
</script>
</html>