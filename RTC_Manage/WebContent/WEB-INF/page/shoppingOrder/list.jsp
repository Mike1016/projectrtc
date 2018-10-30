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
				<label class="layui-form-label">昵称</label>
				<div class="layui-input-inline">
					<input type="text" name="nick_name" id="nick_name" lay-verify="required|phone"
						autocomplete="off" class="layui-input" id="nick_name" placeholder='请输入用户昵称(模糊查询)'>
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
	<span style='color:pink'>待付款</span>

	{{# } else if(d.state== 1) { }}
	<span style='color:red'>已取消</span>

	{{# } else if(d.state== 2) { }}
	<span style='color:green'>待发货</span>

	{{# } else if(d.state== 3) { }}
	<span style='color: #660000'>待收货</span>

	{{# } else if(d.state== 4) { }}
	<span style='color:blue'>已完成</span>

	{{# } else if(d.state== 5) { }}
	<span style='color:grey'>申请退款</span>

	{{# } else if(d.state== 6) { }}
	<span style='color:blue'>退款审核通过</span>

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
    var url = "../shoppingOrder/toReview.do";
    layer.open({
        type:2,
        title:"审核",
        area:["350px","350px"],
        maxmin:true,
        offset: '20%',
        content:url+"?id="+id
    });

}

function update(id,url){
    $.ajax({
        url:"../shoppingOrder/updateState.do",
        type:"get",
        data:"id="+id,
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
    			+"<button class='layui-btn layui-btn-xs'  id='update' onclick=update('{{d.id}}')>发货</button>";
		tableInstance = table.render({
			limits:[10,20,50],
		    elem: '#demo'
		    ,height: $(window).height()*0.8
		    ,url: '../shoppingOrder/queryData.do' //数据接口
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
		      ,{field: 'nick_name', title: '用户昵称', width:"6%"}
		      ,{field: 'phone', title: '电话号码', width:"6%"}
		      ,{field: 'order_no', title: '订单号', width:"6%"}
		      ,{field: 'goods_name', title: '商品名称', width:"6%"}
		      ,{field: 'num', title: '购买数量', width:"6%"}
		      ,{field: 'price', title: '价格', width:"6%"}
		      ,{field: 'type_name', title: '商品类型', width:"10%"}
		      ,{field: 'receiving_address', title: '地址', width:"10%"}
		      ,{field: 'which_located', title: '详细地址', width:"10%"}
		      ,{field: 'create_time', title: '购买日期', width:"10%"}
		      ,{field: 'state', title: '状态', width:"7%",templet:"#state"}
		      ,{ width:"10%",title:"操作",
		    	  templet: oprateBtn
		       }
		      ]]
			 ,even: false //开启隔行背景
			 ,done:function(res){
                for(var i=0;i<res.data.length;i++){
                    if(res.data[i].state!=5){
                        $("[data-index='"+i+"'] [data-field='10'] #edit").attr('disabled','disabled').css("background-color","cadetblue");
                    }
                    if(res.data[i].state!=2){
                        $("[data-index='"+i+"'] [data-field='10'] #update").attr('disabled','disabled').css("background-color","cadetblue");
                    }
                }
			    }
		  });
	});
</script>
</html>