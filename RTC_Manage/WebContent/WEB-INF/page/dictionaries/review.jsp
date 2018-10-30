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
			<label class="layui-form-label">编号</label>
			<div class="layui-input-inline">
				<input type="text" name="type" id="type"
					autocomplete="off" value="${result.type}" class="layui-input" disabled="disabled">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">参数值</label>
			<div class="layui-input-inline">
				<input type="text" name="parameter" id="parameter"
					autocomplete="off" value="${result.parameter}" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">描述</label>
			<div class="layui-input-inline">
				<input type="text" name="remarks" id="remarks"
					autocomplete="off" value="${result.remarks}" class="layui-input" disabled="disabled">
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
        var parameter=$("#parameter").val();
        var param={id:id,parameter:parameter};
        if(window.confirm("你确定要修改吗？")) {
        $.post("../dictionaries/toUpdate.do?id="+id+"&parameter="+parameter,function(data) {
            parent.layer.closeAll();
            window.parent.location.reload();
        })
        } else {
            window.parent.location.reload();
		}
        return false;
	}
</script>
</html>