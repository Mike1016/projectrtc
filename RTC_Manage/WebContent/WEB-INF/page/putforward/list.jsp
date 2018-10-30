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
				<label class="layui-form-label">用户名称</label>
				<div class="layui-input-inline">
					<input type="text" name="nick_name" id="nick_name" lay-verify="required|phone"
						autocomplete="off" class="layui-input" id="nick_name" placeholder='请输入用户名称(模糊查询)'>
				</div>
			</div>
			<button class="layui-btn" onclick="reload()">确定</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

			&nbsp;&nbsp;&nbsp;&nbsp;提现时间:
			<div class="layui-inline"> <!-- 注意：这一层元素并不是必须的 -->
				<input type="text" class="layui-input" id="putForwardTimeFrom">
				<input type="hidden" class="layui-input" id="pfrom">
			</div>
			至&nbsp;&nbsp;
			<div class="layui-inline"> <!-- 注意：这一层元素并不是必须的 -->
				<input type="text" class="layui-input" id="putForwardTimeTo">
				<input type="hidden" class="layui-input" id="pto">
			</div>
		</div>
	</div>
	<hr>
	<div>
<table id="demo"   lay-filter="test"></table>
	</div>
	<div id="pages"></div>
</body>

<script>
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //执行一个laydate实例
        laydate.render({
            elem: '#putForwardTimeFrom' //指定元素
            ,type: 'datetime'
            ,done:function (value, date, endDate) {
                debugger;
                $("#pfrom").val(value);
                tableInstance.reload({
                    where: {
                        putForwardTimeFrom: value,
                        putForwardTimeTo: $("#pto").val(),
                    }
                    ,page: {
                        curr: 1
                    }
                });
            }
        });

        //执行一个laydate实例
        laydate.render({
            elem: '#putForwardTimeTo' //指定元素
            ,type: 'datetime'
            ,done:function (value, date, endDate) {
                $("#pto").val(value);
                tableInstance.reload({
                    where: {
                        putForwardTimeTo: value,
                        putForwardTimeFrom: $("#pfrom").val(),
                    }
                    ,page: {
                        curr: 1
                    }
                });
            }
        });

    });
</script>

<script type="text/html" id="type">
	{{# if (d.type== 1) { }}
	<span style='color:blue'>余额</span>

	{{# } else if(d.type== 2) { }}
	<span style='color:green'>佣金</span>

	{{# }
	}}
</script>

<script type="text/html" id="state">
	{{# if (d.state== 1) { }}
	<span style='color:blue'>提现成功</span>

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


function review(cId,url){
	var url = "../putforward/toReview.do";
	layer.open({
		type:2,
		title:"审核",
		area:["350px","350px"],
		maxmin:true,
		offset: '20%',
		content:url+"?cId="+cId
	});
}


	layui.use([ 'table','layer'], function() {
		table = layui.table;
		layer = layui.layer;		
		
  		var oprateBtn = "<div><button class='layui-btn layui-btn-xs' id='review' onclick=review('{{d.cId}}')>审核</button>";
		tableInstance = table.render({
			limits:[10,20,50],
		    elem: '#demo'
		    ,height: $(window).height()*0.8
		    ,url: '../putforward/queryData.do' //数据接口
		    ,response: {
		    		  statusName: 'status' //数据状态的字段名称，默认：code
		    		  ,statusCode: 200 //成功的状态码，默认：0
		    		  ,msgName: 'hint' //状态信息的字段名称，默认：msg
		    		  ,countName: 'count' //数据总数的字段名称，默认：count
		    		  ,dataName: 'data' //数据列表的字段名称，默认：data
		    		} 
		    ,page: true //开启分页
		    ,cols: [[//表头
		      {field: 'cId',type:"checkbox", width:"5%", sort: true, fixed: 'left'}
		      ,{field: 'nick_name', title: '用户名', width:"10%"}
		      ,{field: 'level', title: '等级', width:"10%"}
		      ,{field: 'phone', title: '电话号码', width:"10%"}
		      ,{field: 'account', title: '提现金额', width:"10%"}
		      ,{field: 'alipay', title: '支付宝账号', width:"10%"}
		      ,{field: 'type', title: '类型', width:"10%",templet:"#type"}
		      ,{field: 'create_time', title: '创建时间', width:"10%"}
		      ,{field: 'update_time', title: '审核时间', width:"10%"}
		      ,{field: 'state', title: '状态', width:"15%",templet:"#state"}
		      ]]
			 ,even: false //开启隔行背景
			 ,done:function(res){
                for(var i=0;i<res.data.length;i++){
                    if(res.data[i].state!=0){
                        $("[data-index='"+i+"'] [data-field='9'] #review").attr('disabled','disabled').css("background-color","cadetblue");
                    }
                }
			    }
		  });
	});
</script>
</html>