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

<script type="text/html" id="imgs">
<img src="{{d.type_img}}" width=50px height=50px/>
</script>
<body>
	
	<div class="condition">
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">商品类型</label>
				<div class="layui-input-inline">
					<input type="text" name="typeName" id="typeName" lay-verify="required|phone"
						autocomplete="off" class="layui-input" id="taskDoName" placeholder='请输入商品类型(模糊查询)'>
				</div>
			</div>
			<button class="layui-btn" onclick="reload()">确定</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<button class="layui-btn" onclick="add()">新增</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<span id="opetion_btn"></span>
		</div>
	</div>
	<hr>
	<div>
	    <table id="demo"   lay-filter="test"></table>
	</div>
	<div id="pages"></div>
</body>


<script type="text/javascript">
var table;
var tableInstance;
var layer;
var curr_menu_id = window.parent.document.getElementById("curr_menu_id").value;

function reload(){
	tableInstance.reload({
		  where: { 
			  type_name: $("#typeName").val()
		  }
		  ,page: {
		    curr: 1
		  }
		});

}

function add(){
	layer.open({
		type:2,
		title:"新增",
		area:["500px","400px"],
		maxmin:true,
		offset: '20%',
		content:"../goodsType/toAdd.do"  
	});
}
function edit(id){
	layer.open({
		type:2,
		title:"新增",
		area:["500px","400px"],
		maxmin:true,
		offset: '20%',
		content:"../goodsType/toEdit.do?id="+id  
	});
}
function del(id){
	
	$.post("../goodsType/validateReference.do?id="+id,function(data){
		if(data){
			$.post("../goodsType/del.do?id="+id,function(){
				reload();
			})
		}
		else{
			alert("该类型被引用，不可删除")
		}
	})
	
}

	layui.use([ 'table','layer'], function() {
		debugger;
		table = layui.table;
		layer = layui.layer;		
		var oprateBtn = "<div><button class='layui-btn layui-btn-xs' id='user_update' onclick=edit('{{d.id}}')>修改</button>"
			+"<button class='layui-btn layui-btn-xs'  id='user_delete' onclick=del('{{d.id}}')>删除</button>"
		tableInstance = table.render({
			limits:[10,20,50],
		    elem: '#demo'
		    ,height: $(window).height()*0.8
		    ,url: '../goodsType/queryData.do' //数据接口
		    ,response: {
		    		  statusName: 'status' //数据状态的字段名称，默认：code
		    		  ,statusCode: 200 //成功的状态码，默认：0
		    		  ,msgName: 'hint' //状态信息的字段名称，默认：msg
		    		  ,countName: 'count' //数据总数的字段名称，默认：count
		    		  ,dataName: 'data' //数据列表的字段名称，默认：data
		    		} 
		    ,page: true //开启分页
		    ,cols: [[//表头
		      {field: 'id',type:"checkbox", width:"15%", sort: true, fixed: 'left'}
		      ,{field: 'type_name', title: '类型名', width:"30%"}
		      ,{field: 'type_img', title: '类型图标', width:"30%",templet: "#imgs"}
		      ,{ width:"25%",title:"操作",
		    	  templet: oprateBtn
		       }
		      ]]
			 ,even: false //开启隔行背景
			 ,done:function(){
			    }
		  });
	});
</script>
</html>