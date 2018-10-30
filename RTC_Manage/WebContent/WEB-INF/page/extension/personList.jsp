<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主页</title>
<link rel="stylesheet" href="../static/layui/css/layui.css" />
<link rel="stylesheet" href="../static/css/common.css" />
<script type="text/javascript" src="../static/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="../static/layui/layui.js"></script>
</head>
<body>
	<hr>
	<div>
<table id="demo"   lay-filter="test"></table>
	</div>
	<div id="pages"></div>
</body>

<script type="text/javascript">
var table;
var tableInstance;
function reload(){
	tableInstance.reload({
		  where: { 
			  nick_name: $("#nick_name").val()
		  }
		  ,page: {
		    curr: 1
		  }
		});
}

	layui.use([ 'table','layer'], function() {
		table = layui.table;
		layer = layui.layer;		
		
		var id = window.location.href;
		
		tableInstance = table.render({
			limits:[10,20,50],
		    elem: '#demo'
		    ,height: $(window).height()*0.8
		    ,url: '../extension/toReviewList.do?id='+id //数据接口
		    ,response: {
		    		  statusName: 'status' //数据状态的字段名称，默认：code
		    		  ,statusCode: 200 //成功的状态码，默认：0
		    		  ,msgName: 'hint' //状态信息的字段名称，默认：msg
		    		  ,countName: 'count' //数据总数的字段名称，默认：count
		    		  ,dataName: 'data' //数据列表的字段名称，默认：data
		    		} 
		    ,page: true //开启分页
		    ,cols: [[//表头
		      {field: 'id',type:"checkbox", width:"5%", sort: true, fixed: 'left'}
		      ,{field: 'nickName', title: '用户名', width:"25%"}
		      ,{field: 'level', title: '等级', width:"20%"}
		      ,{field: 'phone', title: '电话号码', width:"25%"}
		      ,{field: 'regesterTime', title: '注册时间', width:"25%"}
		      
		      ]]
			 ,even: false //开启隔行背景
			 ,done:function(){
			    }
		  });
	});
</script>
</html>