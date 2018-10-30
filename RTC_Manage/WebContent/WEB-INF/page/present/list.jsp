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
				<label class="layui-form-label">用户名</label>
				<div class="layui-input-inline">
					<input type="text" name="username" id="nick_name" lay-verify="required|phone"
						autocomplete="off" class="layui-input" id="nick_name" placeholder='请输入用户名(模糊查询)'>
				</div>
			</div>
			<button class="layui-btn" onclick="reload()">确定</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</div>
	</div>
	<hr>
	<div>
<table id="demo"   lay-filter="test"></table>
	</div>
	<div id="pages"></div>
</body>

<script type="text/html" id="state">
	{{# if (d.state== 0) { }}
	<span style='color:blue'>等待审核</span>

	{{# } else if(d.state== 1) { }}
	<span style='color:pink'>待发货</span>

	{{# } else if(d.state== 2) { }}
	<span style='color:orange'>待收货</span>

	{{# } else if(d.state== 3) { }}
	<span style='color:green'>已完成</span>

	{{# }
	}}
</script>

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

function edit(id,url){
	var url = "../present/toReview.do";
	layer.open({
		type:2,
		title:"修改",
		area:["350px","350px"],
		maxmin:true,
		offset: '20%',
		content:url+"?id="+id
	});
}

function update(id) {
    $.ajax({
        url:"../present/toUpdate.do",
        type:"get",
        data:"id="+id+"&state=2",
        dataType:"json",
        success:function(data) {
            location.reload();
        }
    });
}

	layui.use([ 'table','layer'], function() {
		table = layui.table;
		layer = layui.layer;		
		
  		var oprateBtn = "<div><button class='layui-btn layui-btn-xs' id='edit' onclick=edit('{{d.id}}')>审核</button>"
		+"<button class='layui-btn layui-btn-xs' id='update' onclick=update('{{d.id}}')>确认发货</button>";
		tableInstance = table.render({
			limits:[10,20,50],
		    elem: '#demo'
		    ,height: $(window).height()*0.8
		    ,url: '../present/queryData.do' //数据接口
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
		      ,{field: 'nick_name', title: '姓名', width:"10%"}
		      ,{field: 'phone', title: '电话号码', width:"15%"}
		      ,{field: 'level', title: '等级', width:"5%"}
		      ,{field: 'order_no', title: '订单编号', width:"15%"}
		      ,{field: 'receiving_address', title: '收货地址', width:"15%"}
		      ,{field: 'create_time', title: '创建时间', width:"15%"}
		      ,{field: 'state', title: '状态', width:"10%",templet: "#state"}
		      ,{ width:"10%",title:"操作",
		    	  templet: oprateBtn
		       }
		      ]]
			 ,even: false //开启隔行背景
			 ,done:function(res){
			    for(var i=0;i<res.data.length;i++) {
			        if(res.data[i].state != 0) {
                        $("[data-index='"+i+"'] [data-field='8'] #edit").attr('disabled','disabled').css("background-color","cadetblue");
                    }
                    if(res.data[i].state != 1) {
                        $("[data-index='"+i+"'] [data-field='8'] #update").attr('disabled','disabled').css("background-color","cadetblue");
                    }
				}
			    }
		  });
	});
</script>
</html>