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
	<input type="hidden" name="customerId" id='customerId' value='${result.customer_id}'>
		<div class="layui-form-item">
			<label class="layui-form-label">用户名称</label>
			<div class="layui-input-inline">
				<input type="text" name="nick_name" id="nick_name"
					autocomplete="off" value='${result.nick_name}' class="layui-input" disabled="disabled">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">电话号码</label>
			<div class="layui-input-inline">
				<input type="text" name="phone" id="phone"
					autocomplete="off" value='${result.phone}' class="layui-input" disabled="disabled">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">等级</label>
			<div class="layui-input-inline">
				<input type="text" name="level" id="level"
					autocomplete="off" value='${result.level}' class="layui-input" disabled="disabled">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">订单编号</label>
			<div class="layui-input-inline">
				<input type="text" name="order_no" id="order_no"
					autocomplete="off" value='${result.order_no}' class="layui-input" disabled="disabled">
			</div>
		</div>

	<div class="layui-form-item">
		<label class="layui-form-label">收货地址</label>
		<div class="layui-input-inline">
			<input type="text" name="receiving_address" id="receiving_address"
				   autocomplete="off" value='${result.receiving_address}' class="layui-input" disabled="disabled">
		</div>
	</div>

	<div class="layui-form-item">
		<label class="layui-form-label">创建日期</label>
		<div class="layui-input-inline">
			<input type="text" name="create_time" id="create_time"
				   autocomplete="off" value='${result.create_time}' class="layui-input" disabled="disabled">
		</div>
	</div>

	<div class="layui-form-item">
		<label class="layui-form-label">审核</label>
		<div class="layui-input-inline" style="line-height: 50px">
			<select lay-verify="required" lay-search="" style="height: 38px" id="state" name="state">
				<option value="1" <c:if test="${1 == result.state }">selected="selected"</c:if>>审核通过</option>
			</select>
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
		var state=$("#state").val();
		var param={id:id,state:state};
		$.post("../present/toUpdate.do?state="+state+"&id="+id,function(data) {
			parent.layer.closeAll();
			window.parent.location.reload();
		});
		return false;
	}
</script>
</html>