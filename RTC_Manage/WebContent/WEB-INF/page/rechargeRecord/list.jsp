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
				<label class="layui-form-label">手机号码</label>
				<div class="layui-input-inline">
					<input type="text" name="phones" id="phones" lay-verify="required|phones"
						autocomplete="off" class="layui-input" id="phones" placeholder='请输入手机号码(模糊查询)'>
				</div>
			</div>
			<div class=" layui-input-inline">
                    <select id="type" lay-verify="required" class="layui-input">
                        <option value="">请选择记录类型</option>
                        <option value="1">充值</option>
                        <option value="2">提现</option>
                    </select>
            </div>	
			完成时间:
			<div class="layui-inline"> <!-- 注意：这一层元素并不是必须的 -->
				<input type="text" class="layui-input" id="completeFrom">
				<input type="hidden" class="layui-input" id="rfrom">
			</div>
			至&nbsp;&nbsp;
			<div class="layui-inline"> <!-- 注意：这一层元素并不是必须的 -->
				<input type="text" class="layui-input" id="completeTo">
				<input type="hidden" class="layui-input" id="rto">
			</div>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
            elem: '#completeFrom' //指定元素
            ,type: 'datetime'
            ,done:function (value, date, endDate) {
				debugger;
                $("#rfrom").val(value);
                tableInstance.reload({
                    where: {
                        completeFrom: value,
                        completeTimeTo: $("#rto").val(),
                    }
                    ,page: {
                        curr: 1
                    }
                });
            }
        });
        //执行一个laydate实例
        laydate.render({
            elem: '#completeTo' //指定元素
            ,type: 'datetime'
            ,done:function (value, date, endDate) {
                $("#rto").val(value);
                tableInstance.reload({
                    where: {
                        completeTo: value,
                        completeFrom: $("#rfrom").val(),
                    }
                    ,page: {
                        curr: 1
                    }
                });
            }
        });
    });
</script>

<script type="text/html" id="pay_type">
	{{# if (d.pay_type== 1) { }}
	<span style='color:blue'>充值</span>

	{{# } else if(d.pay_type== 2) { }}
	<span style='color:green'>提现</span>

	{{# }
	}}
</script>


<script type="text/html" id="pay_state">
	{{# if (d.pay_state== 2) { }}
	<span style='color:blue'>已完成</span>

	{{# }
	}}
</script>

<script type="text/javascript">
var table;
var tableInstance;
function reload(){
	tableInstance.reload({
		  where: {
              phones: $("#phones").val(),
              completeTo: $("#ato").val(),
              completeFrom: $("#afrom").val(),
              pay_type:$("#type").val()
		  }
		  ,page: {
		    curr: 1
		  }
		});
}

	layui.use([ 'table','layer'], function() {
		table = layui.table;
		layer = layui.layer;

		tableInstance = table.render({
			limits:[10,20,50],
		    elem: '#demo'
		    ,height: $(window).height()*0.8
		    ,url: '../rechargeWithdrawal/queryData.do' //数据接口
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
		      ,{field: 'phones', title: '用户电话号码', width:"7%"}
		      ,{field: 'pay_out_order_no', title: '支付宝订单号', width:"15%"}
		      ,{field: 'pay_reality_total', title: '金额', width:"7%"}
		      ,{field: 'pay_state', title: '状态', width:"10%",templet: "#pay_state"}
		      ,{field: 'pay_complete_time', title: '完成时间', width:"10%"}
		      ,{field: 'pay_type', title: '类型', width:"6%",templet: "#pay_type"}
		      ]]
			 ,even: false //开启隔行背景
			 ,done:function(res){
			    }
		  });
	});
</script>
</html>