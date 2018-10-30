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
	<input type="hidden" name="customerId" id='customerId' value='${result.customerId}'>
		<div class="layui-form-item">
			<label class="layui-form-label">用户名称</label>
			<div class="layui-input-inline">
				<input type="text" name="nick_name" id="nick_name"
					autocomplete="off" value='${result.nick_name}' class="layui-input" disabled="disabled">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">商品名称</label>
			<div class="layui-input-inline">
				<input type="text" name="goods_name" id="goods_name"
					autocomplete="off" value='${result.goods_name}' class="layui-input" disabled="disabled">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">购买数量</label>
			<div class="layui-input-inline">
				<input type="text" name="num" id="num"
					autocomplete="off" value='${result.num}' class="layui-input" disabled="disabled">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">价格</label>
			<div class="layui-input-inline">
				<input type="text" name="price" id="price"
					autocomplete="off" value='${result.price}' class="layui-input" disabled="disabled">
			</div>
		</div>

	<div class="layui-form-item">
		<label class="layui-form-label">商品类型</label>
		<div class="layui-input-inline">
			<input type="text" name="type_name" id="type_name"
				   autocomplete="off" value='${result.type_name}' class="layui-input" disabled="disabled">
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
		<label class="layui-form-label">详细地址</label>
		<div class="layui-input-inline">
			<input type="text" name="which_located" id="which_located"
				   autocomplete="off" value='${result.which_located}' class="layui-input" disabled="disabled">
		</div>
	</div>

	<div class="layui-form-item">
		<label class="layui-form-label">购买日期</label>
		<div class="layui-input-inline">
			<input type="text" name="create_time" id="create_time"
				   autocomplete="off" value='${result.create_time}' class="layui-input" disabled="disabled">
		</div>
	</div>

	<div class="layui-form-item">
		<label class="layui-form-label">审核</label>
		<div class="layui-input-inline" style="line-height: 50px">
			<select lay-verify="required" lay-search="" style="height: 38px" id="state" name="state">
				<option value="6" <c:if test="${6 == result.state }">selected="selected"</c:if>>审核通过</option>
				<option value="2" <c:if test="${2 == result.state }">selected="selected"</c:if>>审核不通过</option>
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
		var customerId = $("#customerId").val();
		var state=$("#state").val();
		var param={id:id,state:state};
		$.post("../shoppingOrder/toUpdate.do?state="+state+"&id="+id+"&customerId="+customerId,function(data) {
			parent.layer.closeAll();
			window.parent.location.reload();
		});
		return false;
	}
</script>
</html>