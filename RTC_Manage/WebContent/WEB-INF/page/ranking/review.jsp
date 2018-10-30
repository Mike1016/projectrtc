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
	<input type="hidden" name="id" id='id' value='${result.id}'>
		<div class="layui-form-item">
			<label class="layui-form-label">电话号码</label>
			<div class="layui-input-inline">
				<input type="text" name="phone" id="phone"
					autocomplete="off" placeholder="${result.phone}" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">充值金额</label>
			<div class="layui-input-inline">
				<input type="text" name="account" id="account"
					autocomplete="off" placeholder="${result.account}" class="layui-input">
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
		var id = $("#id").val();
		var phone=$("#phone").val();
		var account=$("#account").val();
		var param={id:id,phone:phone,account:account};
        $.post("../ranking/updateRanking.do?id="+id+"&phone="+phone+"&account="+account,function(data) {
            parent.layer.closeAll();
            window.parent.location.reload();
        })
        return false;
	}
</script>
</html>