<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主页</title>
<link rel="stylesheet" href="../static/layui/css/layui.css" />
<link rel="stylesheet" href="../static/css/common.css" />
<style type="text/css">

</style>
</head>
<body>
	<form class="layui-form" action="">
			<div class="layui-form-item">
		    <label class="layui-form-label">生成的个数:</label>
		    <div class="layui-input-block">
		      <input type="radio" name="num" id="num" title="100条" value="100">
		      <input type="radio" name="num" id="num" title="200条" value="200">
		      <input type="radio" name="num" id="num" title="500条" value="500">
		    <button class="layui-btn layui-btn-primary" onclick="show()">完成</button>
		    </div>
		  </div>
	</form>
	
	<!--条件查询-->
	<div class="condition">
		<div class="layui-form-item">

			<div class="layui-inline">
			
				<label class="layui-form-label">激活码</label>
				<div class="layui-input-inline">
					<input type="text" name="code" lay-verify="required|phone"
						autocomplete="off" class="layui-input" id="code" placeholder='请输入激活码(模糊查询)'>
				</div>
				
			</div>
			<button class="layui-btn" onclick="reload()">确定</button>   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
			<span id="opetion_btn"></span>
		</div>
	</div>
	<hr>
	<div>
<table id="demo"   lay-filter="test"></table>
	</div>
	<div id="pages"></div>
</body>

<script type="text/javascript" src="../static/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="../static/layui/layui.js"></script>
<script type="text/javascript">
layui.use(['form', 'layedit', 'laydate'], function(){
	  var form = layui.formlayui.use(['form', 'layedit', 'laydate'], function(){
		  var form = layui.form;
	  });
});
</script>
<script type="text/html" id="state">
	{{# if (d.state== 0) { }}
	<span style='color:blue'>可用</span>

	{{# } else if(d.state== 1) { }}
	<span style='color:red'>不可用</span>

	{{# }
	}}
</script>
<script type="text/javascript">
	function show() {
		debugger;
		var length = $("input[name='num']:checked").val();
		var param = {length:length};
		$.ajax({
			url:"../activation/save.do",
			type:"get",
			data:"length="+length,
			dataType:"json",
			success:function(data) {
				alert(data);
			}
			
		});
	}
</script>
<script type="text/javascript">
var table;
var tableInstance;
var curr_menu_id = window.parent.document.getElementById("curr_menu_id").value;

function reload(){
	tableInstance.reload({
		  where: { 
			  code: $("#code").val()
		  }
		  ,page: {
		    curr: 1
		  }
		});

}

 function review(id){
	 $.ajax({
			url:"../activation/delete.do",
			type:"get",
			data:"id="+id,
			dataType:"json",
			success:function(data) {
				location.reload();
			}
			
		});
} 


  var oprateBtn = "<div><button type='button' class='layui-btn layui-btn-xs' id='review' onclick=review('{{d.id}}')>删除</button>";
	layui.use([ 'table','layer'], function() {
		table = layui.table;
		layer = layui.layer;		
		tableInstance = table.render({
			limits:[10,20,50],
		    elem: '#demo'
		    ,height: $(window).height()*0.7
		    ,url: '../activation/queryData.do' //数据接口
		    ,response: {
		    		  statusName: 'status' //数据状态的字段名称，默认：code
		    		  ,statusCode: 200 //成功的状态码，默认：0
		    		  ,msgName: 'hint' //状态信息的字段名称，默认：msg
		    		  ,countName: 'count' //数据总数的字段名称，默认：count
		    		  ,dataName: 'data' //数据列表的字段名称，默认：data
		    		} 
		    ,page: true //开启分页
		    ,cols: [[//表头
		      {field: 'id',type:"checkbox", width:"10%", sort: true, fixed: 'left'}		      
		      ,{field: 'code', title: '激活码编号', width:"25%", sort: true}
		      ,{field: 'create_time', title: '创建时间', width:"25%"}
		      ,{field: 'state', title: '状态', width:"25%", sort: true,
                    templet: "#state"
				}
		      ,{ width:"15%",title:"操作",
		    	  templet: oprateBtn
		      } 
		      ]]
			 ,even: true //开启隔行背景
			 ,done:function(){
			    }
		  });
	});

	$(document).ready(function(){
		$("#opetion_btn").html(parent.window.initPermissionBtn(curr_menu_id));
	});
</script>
</html>