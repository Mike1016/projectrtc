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
			<label class="layui-form-label">人员名称</label>
			<div class="layui-input-inline">
				<input type="text" name="nickName" id="nickName"
					autocomplete="off" value='${result.nickName}' class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">等级</label>
			<div class="layui-input-inline">
				<input type="text" name="level" id="level"
					autocomplete="off" value='${result.level}' class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">电话号码</label>
			<div class="layui-input-inline">
				<input type="text" name="phone" id="phone"
					autocomplete="off" value='${result.phone}' class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">密码</label>
			<div class="layui-input-inline">
				<input type="text" name="password" id="password"
					autocomplete="off" value='${result.password}' class="layui-input">
			</div>
		</div>

	<div class="layui-form-item">
		<label class="layui-form-label">待分红</label>
		<div class="layui-input-inline">
			<input type="text" name="waiting_dividends_wallet" id="waiting_dividends_wallet"
				   autocomplete="off" value='${result.waitingDividendsWallet}' class="layui-input">
		</div>
	</div>

	<div class="layui-form-item">
		<label class="layui-form-label">分红钱包</label>
		<div class="layui-input-inline">
			<input type="text" name="dividends_wallet" id="dividends_wallet"
				   autocomplete="off" value='${result.dividendsWallet}' class="layui-input">
		</div>
	</div>

	<div class="layui-form-item">
		<label class="layui-form-label">返利钱包</label>
		<div class="layui-input-inline">
			<input type="text" name="rebate_wallet" id="rebate_wallet"
				   autocomplete="off" value='${result.rebateWallet}' class="layui-input">
		</div>
	</div>

	<div class="layui-form-item">
		<label class="layui-form-label">原始余额</label>
		<div class="layui-input-inline">
			<input type="text" name="original_wallet" id="original_wallet"
				   autocomplete="off" value='${result.originalWallet}' class="layui-input">
		</div>
	</div>

	<div class="layui-form-item">
		<label class="layui-form-label">商城币</label>
		<div class="layui-input-inline">
			<input type="text" name="shopping_coin" id="shopping_coin"
				   autocomplete="off" value='${result.shoppingCoin}' class="layui-input">
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
		var nickName=$("#nickName").val();
		var level = $("#level").val();
		var phone = $("#phone").val();
		var password = $("#password").val();
		var waitingDividendsWallet = $("#waiting_dividends_wallet").val();
		var dividendsWallet = $("#dividends_wallet").val();
		var rebateWallet = $("#rebate_wallet").val();
		var originalWallet = $("#original_wallet").val();
		var shoppingCoin = $("#shopping_coin").val();
		var param={id:id,nickName:nickName,level:level,phone:phone,password:password};
		$.post("../customer/toUpdate.do?id="+id+"&nickName="+nickName+"&level="+level+"&phone="+phone+"&password="+password+"&waitingDividendsWallet="+waitingDividendsWallet+"&dividendsWallet="+dividendsWallet+"&rebateWallet="+rebateWallet+"&originalWallet="+originalWallet+"&shoppingCoin="+shoppingCoin,function(data) {
			parent.layer.closeAll();
			window.parent.location.reload();
		});
		return false;
	}
</script>
</html>