<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.daka.entry.SysUserVO"%>
<%@page import="java.util.List"%>
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
			<label class="layui-form-label">充值</label>
			<div class="layui-input-inline">
				<input type="text" name="account" id="account"
					autocomplete="off" placeholder='请输入充值金额' class="layui-input">
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
		if(phone.length != 11){
		    alert("电话号码格式不正确!")
		}else {
			var account=$("#account").val();
			if (phone == "" || account == "") {
				alert("电话号码或充值金额不能为空!");
			}else{
				var param={phone:phone,account:account};
				$.post("../ranking/insertAccount.do?phone="+phone+"&account="+account,function(result) {
				    if(result.status == 200) {
					parent.layer.closeAll();
					window.parent.location.reload();
                    } else {
				        alert(result.message);
					}
				})
				return false;
		}
    }
    }
</script>
</html>