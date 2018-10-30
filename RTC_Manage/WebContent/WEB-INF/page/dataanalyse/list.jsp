<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>主页</title>
    <link rel="stylesheet" href="../static/layui/css/layui.css" media="all"/>
    <link rel="stylesheet" href="../static/css/common.css"/>
    <script type="text/javascript" src="../static/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="../static/layui/layui.js"></script>
    <script type="text/javascript" src="../static/js/echarts.min.js"></script>

</head>
<body>
<!--条件查询-->
<div class="condition">
    <div class="layui-form-item">
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;选择日期:
        <div class="layui-inline">
            <input type="text" class="layui-input" id="chooseFrom">
        </div>
        至&nbsp;&nbsp;
        <div class="layui-inline">
            <input type="text" class="layui-input" id="chooseTo">
        </div>
        <%--<div class="layui-inline">
            <select id="types" lay-verify="required" class="layui-input">
                <option value="">请选择类型</option>
                <option value="1">一级</option>
                <option value="2">二级</option>
                <option value="3">三级</option>
            </select>
        </div>--%>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <button class="layui-btn" onclick="reload()">确定</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    </div>
</div>
<div id='main' style="width: 600px; height: 400px;border: 1px;"></div>
<hr>
</body>

<script>
    layui.use('laydate', function () {
        var laydate = layui.laydate;

        //执行一个laydate实例
        laydate.render({
            elem: '#chooseFrom' //指定元素
        });
        //执行一个laydate实例
        laydate.render({
            elem: '#chooseTo' //指定元素
        });
    });
</script>
<script type="text/javascript">
    var myChart = echarts.init(document.getElementById('main'));
    var names = [];     //类别数组（实际用来盛放X轴坐标值）
    var nums = [];       //销量数组（实际用来盛放Y坐标值）
    myChart.setOption({
        title: {
            text: '各等级返利统计'
        },
        tooltip: {
            trigger: 'axis', //坐标轴触发提示框，多用于柱状、折线图中
        },
        legend: {
            data: ['返利总和']
        },
        toolbox: {
            feature: {
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        xAxis: {
            type: 'category',
            data: ['一级返利','二级返利','三级返利']
        },
        yAxis: {},
        series: [{
            name: '返利总和',
            type: 'bar',
            data: []
        }]
    });
    myChart.showLoading(); //数据加载完之前先显示一段简单的loading动画

    function reload() {
        nums=[];
        var beginTime = $("#chooseFrom").val();
        var endTime = $("#chooseTo").val();
        // var type = $("#types option:selected").val();
        var param = {beginTime: beginTime, endTime: endTime};
        if (beginTime != null && endTime != null) {
            debugger;
            var url = "../rebate/queryDataByType.do";
            $.post(url, param, function (res) {
                if (res.status == 200) {
                    var obj = eval(res.data);
                    for (var i = 0; i < obj.length; i++) {
                        nums.push(obj[i].account);
                    }

                    myChart.hideLoading(); //隐藏加载动画
                    myChart.setOption({ //加载数据图表
                        series: [{
                            // 根据名字对应到相应的系列
                            name: '返利总和',
                            data: nums
                        }]
                    });
                } else {
                    alert("后台数据获取失败!");
                    myChart.hideLoading(); //隐藏加载动画
                }
                // parent.layer.closeAll();
                //window.parent.location.reload();
            });
        }
    }


</script>
</html>