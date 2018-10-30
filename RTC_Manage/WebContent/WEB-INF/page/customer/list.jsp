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
			注册时间:
			<div class="layui-inline"> <!-- 注意：这一层元素并不是必须的 -->
				<input type="text" class="layui-input" id="registerFrom">
				<input type="hidden" class="layui-input" id="rfrom">
			</div>
			至&nbsp;&nbsp;
			<div class="layui-inline"> <!-- 注意：这一层元素并不是必须的 -->
			<input type="text" class="layui-input" id="registerTo">
			<input type="hidden" class="layui-input" id="rto">
		</div>
			&nbsp;&nbsp;&nbsp;&nbsp;激活时间:
			<div class="layui-inline"> <!-- 注意：这一层元素并不是必须的 -->
				<input type="text" class="layui-input" id="activationFrom">
				<input type="hidden" class="layui-input" id="afrom">
			</div>
			至&nbsp;&nbsp;
			<div class="layui-inline"> <!-- 注意：这一层元素并不是必须的 -->
				<input type="text" class="layui-input" id="activationTo">
				<input type="hidden" class="layui-input" id="ato">
			</div>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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

<script>
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //执行一个laydate实例
        laydate.render({
            elem: '#registerFrom' //指定元素
            ,type: 'datetime'
            ,done:function (value, date, endDate) {
				debugger;
                $("#rfrom").val(value);
                tableInstance.reload({
                    where: {
                        registerTimeFrom: value,
                        registerTimeTo: $("#rto").val(),
						activationTimeFrom: $("#afrom").val(),
                        activationTimeTo: $("#ato").val()
                    }
                    ,page: {
                        curr: 1
                    }
                });
            }
        });

        //执行一个laydate实例
        laydate.render({
            elem: '#registerTo' //指定元素
            ,type: 'datetime'
            ,done:function (value, date, endDate) {
                $("#rto").val(value);
                tableInstance.reload({
                    where: {
                        registerTimeTo: value,
                        registerTimeFrom: $("#rfrom").val(),
                        activationTimeFrom: $("#afrom").val(),
                        activationTimeTo: $("#ato").val()
                    }
                    ,page: {
                        curr: 1
                    }
                });
            }
        });

        //执行一个laydate实例
        laydate.render({
            elem: '#activationFrom' //指定元素
            ,type: 'datetime'
            ,done:function (value, date, endDate) {
                $("#afrom").val(value);
                tableInstance.reload({
                    where: {
                        activationTimeFrom: value,
                        activationTimeTo: $("#ato").val(),
						registerTimeTo: $("#rto").val(),
                        registerTimeFrom: $("#rfrom").val(),
                    }
                    ,page: {
                        curr: 1
                    }
                });
            }
        });

        //执行一个laydate实例
        laydate.render({
            elem: '#activationTo' //指定元素
            ,type: 'datetime'
            ,done:function (value, date, endDate) {
                $("#ato").val(value);
                tableInstance.reload({
					where: {
                        activationTimeTo: value,
                        activationTimeFrom: $("#afrom").val(),
                        registerTimeTo: $("#rto").val(),
                        registerTimeFrom: $("#rfrom").val(),
					}
					,page: {
						curr: 1
					}
                });
            }
        });
    });
</script>

<script type="text/html" id="state">
	{{# if (d.state== 0) { }}
	<span style='color:blue'>未激活</span>

	{{# } else if(d.state== 1) { }}
	<span style='color:green'>激活</span>

	{{# } else if(d.state== 2) { }}
	<span style='color:red'>封号</span>

	{{# }
	}}
</script>

<script type="text/javascript">
var table;
var tableInstance;
function reload(){
	tableInstance.reload({
		  where: {
              nick_name: $("#nick_name").val(), 
              activationTimeTo: $("#ato").val(),
              activationTimeFrom: $("#afrom").val(),
              registerTimeTo: $("#rto").val(),
              registerTimeFrom: $("#rfrom").val()
		  }
		  ,page: {
		    curr: 1
		  }
		});
}

function add() {
	layer.open({
        type:2,
        title:"新增",
        area:["450px","580px"],
        maxmin:true,
        offset: '20%',
		content: "../customer/toAdd.do"
	});
}

function prohibition(id,url) {
    $.ajax({
        url:"../customer/updateState1.do",
        type:"get",
        data:"id="+id,
        dataType:"json",
        success:function(data) {
            location.reload();
        }
    });
}

function activation(id,url) {
    $.ajax({
        url:"../customer/activation.do",
        type:"get",
        data:"id="+id,
        dataType:"json",
        success:function(data) {
            location.reload();
        }
    });
}

function relieve(id,url) {
    $.ajax({
        url:"../customer/updateState2.do",
        type:"get",
        data:"id="+id,
        dataType:"json",
        success:function(data) {
            location.reload();
        }
    });
}

function edit(id,url){
	var url = "../customer/toReview.do";
	layer.open({
		type:2,
		title:"修改",
		area:["600px","600px"],
		maxmin:true,
		offset: '20%',
		content:url+"?id="+id
	});
}

function del(id) {
	$.ajax({
		url:"../customer/delete.do",
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

  		var oprateBtn = "<div><button class='layui-btn layui-btn-xs' id='prohibition' onclick=prohibition('{{d.id}}')>封号</button>"
			+"<button class='layui-btn layui-btn-xs'  id='activation' onclick=activation('{{d.id}}')>激活</button>"
			+"<button class='layui-btn layui-btn-xs'  id='relieve' onclick=relieve('{{d.id}}')>解封</button>"
            +"<button class='layui-btn layui-btn-xs'  id='user_update_customer' onclick=edit('{{d.id}}')>修改</button>"
            +"<button class='layui-btn layui-btn-xs'  id='user_delete' onclick=del('{{d.id}}')>删除</button>";
		tableInstance = table.render({
			limits:[10,20,50],
		    elem: '#demo'
		    ,height: $(window).height()*0.8
		    ,url: '../customer/queryData.do' //数据接口
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
		      ,{field: 'nick_name', title: '用户名', width:"5%"}
		      ,{field: 'level', title: '等级', width:"3%"}
		      ,{field: 'phone', title: '电话号码', width:"7%"}
		      ,{field: 'regester_time', title: '注册时间', width:"10%"}
		      ,{field: 'activation_time', title: '激活时间', width:"10%"}
		      ,{field: 'team_achievement', title: '团队业绩', width:"6%"}
		      ,{field: 'waiting_dividends_wallet', title: '待分红', width:"5%"}
		      ,{field: 'dividends_wallet', title: '分红钱包', width:"7%"}
		      ,{field: 'rebate_wallet', title: '返利钱包', width:"7%"}
		      ,{field: 'shopping_coin', title: '商城币', width:"5%"}
		      ,{field: 'original_wallet', title: '原始余额', width:"7%"}
		      ,{field: 'state', title: '账号状态', width:"6%",templet: "#state"}
		      ,{ width:"15%",title:"操作",
		    	  templet: oprateBtn
		       }
		      ]]
			 ,even: false //开启隔行背景
			 ,done:function(res){
                for (var i=0;i<res.data.length;i++) {
                    if (res.data[i].state==1||res.data[i].state==0) {
                        $("[data-index='"+i+"'] [data-field='13'] #relieve").attr('disabled','disabled').css("background-color","cadetblue");
                    }
                    if (res.data[i].state==2) {
                        $("[data-index='"+i+"'] [data-field='13'] #prohibition").attr('disabled','disabled').css("background-color","cadetblue");
                    }
                    if (res.data[i].state==1||res.data[i].state==2) {
                        $("[data-index='"+i+"'] [data-field='13'] #activation").attr('disabled','disabled').css("background-color","cadetblue");
                    }
                }
			    }
		  });
	});
</script>
</html>