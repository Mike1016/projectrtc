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
			<label class="layui-form-label">用户名称</label>
			<div class="layui-input-inline">
				<input type="text" name="nick_name" id="nick_name"
					autocomplete="off" value='${result.nick_name}' class="layui-input" disabled="disabled">
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
			<label class="layui-form-label">电话号码</label>
			<div class="layui-input-inline">
				<input type="text" name="phone" id="phone"
					autocomplete="off" value='${result.phone}' class="layui-input" disabled="disabled">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">提现金额</label>
			<div class="layui-input-inline">
				<input type="text" name="account" id="account"
					autocomplete="off" value='${result.account}' class="layui-input" disabled="disabled">
			</div>
		</div>

	<div class="layui-form-item">
		<label class="layui-form-label">审核</label>
		<div class="layui-input-inline" style="line-height: 50px">
			<select lay-verify="required" lay-search="" style="height: 38px" id="state" name="state">
				<option value="1" <c:if test="${1 == result.state }">selected="selected"</c:if>>审核通过</option>
				<option value="2" <c:if test="${2 == result.state }">selected="selected"</c:if>>审核不通过</option>
			</select>
		</div>
	</div>

	<div class="layui-form-item">
		<label class="layui-form-label">备注</label>
		<div class="layui-input-inline">
			<input type="text" name="remark" id="remark"
				   autocomplete="off" class="layui-input" placeholder="请输入审核备注">
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
		var remark=$("#remark").val();
		var param={id:id,state:state,remark:remark};
		$.post("../putforward/updateState.do?state="+state+"&remark="+remark+"&id="+id,function(data) {
			parent.layer.closeAll();
			window.parent.location.reload();
		});
		return false;
	}
</script>
</html>