<%@page import="com.daka.entry.SysUserVO" %>
<%@page import="com.daka.constants.SystemConstant" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>GCR</title>
    <link rel="stylesheet" href="../static/layui/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="../static/css/common.css"/>
</head>
<body>
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo">RTC管理系统</div>
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="javascript:;">
                    <img src="http://t.cn/RCzsdCq" class="layui-nav-img">
                    <%=((SysUserVO) session.getAttribute(SystemConstant.SYS_USER)).getUsername() %>
                </a>
                <!--  <dl class="layui-nav-child">
                  <dd><a href="">基本资料</a></dd>
                  <dd><a href="">安全设置</a></dd>
                </dl>-->
            </li>
            <li class="layui-nav-item"><a href="../user/logout.do">退出</a></li>
        </ul>
    </div>

    <div class="layui-side layui-bg-black" id="">
        <div class="layui-side-scroll">
            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
            <ul class="layui-nav layui-nav-tree site-demo-nav" lay-filter="test" id="nav-main">
                <li class="layui-nav-item">
                    <a href="javascript:;" target="item">管理员管理</a><span class="layui-nav-more"></span>
                    <dl class="layui-nav-child">
                        <dd class=""><a href="../user/toList.do" target="item">管理员管理</a></dd>
                    </dl>
                </li>

                <li class="layui-nav-item">
                    <a href="javascript:;" target="item">用户管理</a><span class="layui-nav-more"></span>
                    <dl class="layui-nav-child">
                        <dd class=""><a href="../customer/toList.do" target="item">用户管理</a></dd>
                    </dl>
                </li>

                <li class="layui-nav-item">
                    <a href="javascript:;" target="item">商城管理</a><span class="layui-nav-more"></span>
                    <dl class="layui-nav-child">
                        <dd class=""><a href="../goods/toList.do" target="item">商品管理</a></dd>
                        <dd class=""><a href="../goodsType/toList.do" target="item">商品类型管理</a></dd>
                    </dl>
                </li>

                <li class="layui-nav-item">
                    <a href="javascript:;" target="item">订单管理</a><span class="layui-nav-more"></span>
                    <dl class="layui-nav-child">
                        <dd class=""><a href="../shoppingOrder/toList.do" target="item">订单管理</a></dd>
                    </dl>
                </li>

                <li class="layui-nav-item">
                    <a href="javascript:;" target="item">激活码管理</a><span class="layui-nav-more"></span>
                    <dl class="layui-nav-child">
                        <dd class=""><a href="../activation/toList.do" target="item">激活码管理</a></dd>
                    </dl>
                </li>

                <li class="layui-nav-item">
                    <a href="javascript:;" target="item">推广管理</a><span class="layui-nav-more"></span>
                    <dl class="layui-nav-child">
                        <dd class=""><a href="../extension/toList.do" target="item">推广管理</a></dd>
                    </dl>
                </li>

                <li class="layui-nav-item">
                    <a href="javascript:;" target="item">推广奖金管理</a><span class="layui-nav-more"></span>
                    <dl class="layui-nav-child">
                        <dd class=""><a href="../promotionBonus/toList.do" target="item">推广奖金管理</a></dd>
                    </dl>
                </li>

                <li class="layui-nav-item">
                    <a href="javascript:;" target="item">待分红管理</a><span class="layui-nav-more"></span>
                    <dl class="layui-nav-child">
                        <dd class=""><a href="../recharge/toList.do" target="item">待分红管理</a></dd>
                    </dl>
                </li>

                <li class="layui-nav-item">
                    <a href="javascript:;" target="item">分红管理</a><span class="layui-nav-more"></span>
                    <dl class="layui-nav-child">
                        <dd class=""><a href="../bonus/toList.do" target="item">分红管理</a></dd>
                    </dl>
                </li>

                <li class="layui-nav-item">
                    <a href="javascript:;" target="item">五倍复投管理</a><span class="layui-nav-more"></span>
                    <dl class="layui-nav-child">
                        <dd class=""><a href="../fiveInvest/toList.do" target="item">五倍复投管理</a></dd>
                        <dd class=""><a href="../fiveInvestLog/toList.do" target="item">五倍复投日志</a></dd>
                    </dl>
                </li>

                <li class="layui-nav-item">
                    <a href="javascript:;" target="item">图片管理</a><span class="layui-nav-more"></span>
                    <dl class="layui-nav-child">
                        <dd class=""><a href="../apppicture/toList.do" target="item">图片管理</a></dd>
                    </dl>
                </li>

                <li class="layui-nav-item">
                    <a href="javascript:;" target="item">排行榜管理</a><span class="layui-nav-more"></span>
                    <dl class="layui-nav-child">
                        <dd class=""><a href="../ranking/toList.do" target="item">排行榜管理</a></dd>
                    </dl>
                </li>

                <li class="layui-nav-item">
                    <a href="javascript:;" target="item">充值提现记录查询</a><span class="layui-nav-more"></span>
                    <dl class="layui-nav-child">
                        <dd class=""><a href="../rechargeWithdrawal/toList.do" target="item">充值提现记录查询</a></dd>
                    </dl>
                </li>

                <li class="layui-nav-item">
                    <a href="javascript:;" target="item">其他管理</a><span class="layui-nav-more"></span>
                    <dl class="layui-nav-child">
                        <dd class=""><a href="../dictionaries/toList.do" target="item">其他管理</a></dd>
                    </dl>
                </li>

                <li class="layui-nav-item">
                    <a href="javascript:;" target="item">中秋礼品管理</a><span class="layui-nav-more"></span>
                    <dl class="layui-nav-child">
                        <dd class=""><a href="../present/toList.do" target="item">中秋礼品管理</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;" target="item">返利数据分析</a><span class="layui-nav-more"></span>
                    <dl class="layui-nav-child">
                        <dd class=""><a href="../rebate/toList.do" target="item">返利数据分析</a></dd>
                    </dl>
                </li>

            </ul>

        </div>
    </div>

    <div class="layui-body left_body" style="bottom:0;overflow:hidden ;">
        <!--面包屑导航-->

        <div class="nav_bar">
            <a href="javascript:;" class="nav_b"></a><span class="divider">></span>
            <a href="javascript:;" class="nav_c"> </a>
            <input type="hidden" id="curr_menu_id">
        </div>


        <!-- 内容主体区域 -->
        <iframe src="/RTC_Manage/daka/bg.do" name="item" id="item"
                style="width:100%;height:100%;border: none;">
        </iframe>

        <div id="testInterface" style="padding: 5%">
            <input type="text" id="testURL" style="width: 70%" placeholder="请输入URL">
            <input type="button" value="test" onclick="test()">
            <br><br>
            <textarea rows="5" cols="8" style="width: 60%;height: 30%" id="test_result" readonly="readonly">
		
  		</textarea>
        </div>

    </div>


    <div class="layui-footer" style="background: #282B33;color:#fff;text-align: center;">
        <!-- 底部固定区域 -->
        © 西安大咖实业有限公司
    </div>
</div>

</body>
<script type="text/javascript" src="../static/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="../static/layui/layui.js"></script>

<script type="text/javascript">
    function test() {
        $.post($("#testURL").val(), function (data) {
            debugger;
            $("#test_result").val(data);
        });
    }

    $(document).ready(function () {
        $(".nav_bar,.condition").css("display", "none");
        $(".layui-nav-child").click(function () {
            $(".nav_bar,.condition").css("display", "block");
        })
        $(".layui-nav-child a").click(function () {
            var cont = $(this).text();
            var curr_menu_id = $(this).attr("curr_menu_id");
            $("#curr_menu_id").val(curr_menu_id);
            var par = $(this).parent().parent().siblings().text();
            $(".nav_bar .nav_c").text(cont);
            $(".nav_bar .nav_b").text(par);

        })

        //绑定layui事件
        layui.use('element', function () {
            var element = layui.element;
        });
    });
</script>
</html>
