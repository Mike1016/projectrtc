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
					<input type="text" name="username" id="nick_name" lay-verify="required|phone"
						autocomplete="off" class="layui-input" id="nick_name" placeholder='请输入用户名称(模糊查询)'>
				</div>
			</div>
			创建时间:
			<div class="layui-inline"> <!-- 注意：这一层元素并不是必须的 -->
				<input type="text" class="layui-input" id="createFrom">
				<input type="hidden" class="layui-input" id="cfrom">
			</div>
			至&nbsp;&nbsp;
			<div class="layui-inline"> <!-- 注意：这一层元素并不是必须的 -->
				<input type="text" class="layui-input" id="createTo">
				<input type="hidden" class="layui-input" id="cto">
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
<script>
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //执行一个laydate实例
        laydate.render({
            elem: '#createFrom' //指定元素
            ,type: 'datetime'
            ,done:function (value, date, endDate) {
                debugger;
                $("#cfrom").val(value);
                tableInstance.reload({
                    where: {
                        createTimeFrom: value,
                        createTimeTo: $("#cto").val(),
                    }
                    ,page: {
                        curr: 1
                    }
                });
            }
        });

        //执行一个laydate实例
        laydate.render({
            elem: '#createTo' //指定元素
            ,type: 'datetime'
            ,done:function (value, date, endDate) {
                $("#cto").val(value);
                tableInstance.reload({
                    where: {
                        createTimeTo: value,
                        createTimeFrom: $("#cfrom").val(),
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
	<span style='color:blue'>每天返利</span>

	{{# } else if(d.type== 2) { }}
	<span style='color:green'>佣金复投</span>

	{{# } else if(d.type== 3) { }}
	<span style='color:#660000'>团队</span>

	{{# } else if(d.type== 4) { }}
	<span style='color:pink'>提现</span>

	{{# } else if(d.type== 5) { }}
	<span style='color:purple'>余额复投</span>


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

	layui.use([ 'table','layer'], function() {
		table = layui.table;
		layer = layui.layer;		
		
//  		var oprateBtn = "<div><button class='layui-btn layui-btn-xs' id='user_update' onclick=edit('{{d.id}}')>修改</button>"
//    			+"<button class='layui-btn layui-btn-xs'  id='user_delete' onclick=del('{{d.id}}')>删除</button>";
		tableInstance = table.render({
			limits:[10,20,50],
		    elem: '#demo'
		    ,height: $(window).height()*0.8
		    ,url: '../fiveInvestLog/queryData.do' //数据接口
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
		      ,{field: 'nickName', title: '用户名称', width:"15%"}
		      ,{field: 'limitAccount', title: '五倍复投总额度', width:"20%"}
		      ,{field: 'capital', title: '本金', width:"15%"}
		      ,{field: 'rate', title: '比率', width:"15%"}
		      ,{field: 'current_return_account', title: '本次返回金额', width:"15%"}
		      ,{field: 'create_time', title: '创建时间', width:"15%", sort: true}
		      /*,{ width:"10%",title:"操作",
                    templet: oprateBtn
                }*/
		      ]]
			 ,even: false //开启隔行背景
			 ,done:function(){
			    }
		  });
	});
</script>
</html>