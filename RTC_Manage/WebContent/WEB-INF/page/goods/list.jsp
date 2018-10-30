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
	<!--条件查询-->
	<div class="condition">
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">商品名称</label>
				<div class="layui-input-inline">
					<input type="text" name="username" id="goods_name" lay-verify="required|phone"
						autocomplete="off" class="layui-input" id="goods_name" placeholder='请输入商品名称(模糊查询)'>
				</div>
			</div>
			<button class="layui-btn" onclick="reload()">确定</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<span id="opetion_btn">
				 <button class="layui-btn layui-btn-sm" id="user_add" onclick="add('{{d.id}}')"><i class="layui-icon">&#xe654;</i>新增</button>
			 </span>
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
function reload(){
	tableInstance.reload({
		  where: {
              goods_name: $("#goods_name").val()
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
		area:["800px","800px"],
		maxmin:true,
		offset: '0%',
		content:"../goods/toAddMainInfo.do"  
	});
}
function del(id){
	$.post("../goods/del.do?id="+id,function(data){
		reload()
	})
}
function edit(id){
	layer.open({
		type:2,
		title:"详情",
		area:["800px","800px"],
		maxmin:true,
		offset: '0%',
		content:"../goods/toGoodsEdit.do?id="+id
	});
}

function detail(id) {
	layer.open({
		type:2,
		title:"详情",
		area:["800px","800px"],
		maxmin:true,
		offset: '0%',
		content:"../goods/toGoodsDetail.do?id="+id
	});
}

	layui.use([ 'table','layer'], function() {
		table = layui.table;
		layer = layui.layer;		
		
  		var oprateBtn = "<div><button class='layui-btn layui-btn-xs' id='user_update' onclick=edit('{{d.id}}')>修改</button>"
    			+"<button class='layui-btn layui-btn-xs'  id='user_delete' onclick=del('{{d.id}}')>删除</button>"
    			+"<button class='layui-btn layui-btn-xs'  id='user_delete' onclick=detail('{{d.id}}')>详情</button>";
		tableInstance = table.render({
			limits:[10,20,50],
		    elem: '#demo'
		    ,height: $(window).height()*0.8
		    ,url: '../goods/queryData.do' //数据接口
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
		      ,{field: 'typeName', title: '商品类型', width:"10%"}
		      ,{field: 'goods_name', title: '商品名称', width:"10%"}
		      ,{field: 'goods_original_price', title: '原始价格(元)', width:"10%"}
		      ,{field: 'goods_discount_price', title: '打折价格(元)', width:"10%"}
		      ,{field: 'goods_count', title: '商品总数(件)', width:"10%"}
		      ,{field: 'goods_remain_count', title: '剩余个数(件)', width:"10%"}
		      ,{field: 'createUser', title: '发布用户', width:"10%"}
		      ,{field: 'create_time', title: '发布时间', width:"10%"}
		      ,{ width:"15%",title:"操作",
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