<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>主页</title>
    <link rel="stylesheet" href="../static/layui/css/layui.css"/>
    <link rel="stylesheet" href="../static/css/common.css"/>
    <script type="text/javascript" src="../static/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="../static/layui/layui.js"></script>
</head>
<body>
<!--条件查询-->
<div class="condition">
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">图片类型</label>
            <div class=" layui-input-inline">
                <select id="types" lay-verify="required" class="layui-input">
                    <option value="">请选择类型</option>
                    <option value="1">群聊图片</option>
                    <option value="2">新手指引</option>
                    <option value="3">LOGO</option>
                    <option value="4">轮播图</option>
                </select>
            </div>
        </div>
        <button class="layui-btn" onclick="reload()">确定</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <button class="layui-btn " id="Picture_add" onclick="addPicture()">新增</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    </div>
</div>
<hr>
<div>
    <table id="demo" lay-filter="test"></table>
</div>
<div id="pages"></div>
</body>

<script type="text/html" id="type">
    {{# if (d.type== 1) { }}
    <span>群聊图片</span>

    {{# } else if(d.type== 2) { }}
    <span>新手指引</span>

    {{# } else if(d.type== 3) { }}
    <span>LOGO</span>

    {{# } else if(d.type== 4) { }}
    <span>轮播图</span>

    {{# }
    }}
</script>


<script type="text/javascript">
    var table;
    var tableInstance;

    function reload() {
        tableInstance.reload({
            where: {
                type: $("#types option:selected").val()
            }
            , page: {
                curr: 1
            }
        });
    }


    function addPicture() {
        layer.open({
            type: 2,
            title: "新增",
            area: ["450px", "450px"],
            maxmin: true,
            offset: '20%',
            content: "../apppicture/toAdd.do"
        });
    }

    function edit(id, url) {
        var url = "../apppicture/queryAppPictureById.do";
        layer.open({
            type: 2,
            title: "修改",
            area: ["570px", "570px"],
            maxmin: true,
            offset: '20%',
            content: url + "?id=" + id
        });
    }


    function del(id) {
        layer.confirm('确认要删除吗，删除后不能恢复', {title: '删除确认'}, function (index) {
            $.post("../apppicture/removeAppPicture.do", {id: id}, function (data) {
                reload()
            })
            layer.close(index);
        });

    }


    layui.use(['table', 'layer'], function () {
        table = layui.table;
        layer = layui.layer;

        var oprateBtn = "<div><button class='layui-btn layui-btn-xs' id='Picture_update' onclick=edit('{{d.id}}')>修改</button>"
            + "<button class='layui-btn layui-btn-xs'  id='Picture_delete' onclick=del('{{d.id}}')>删除</button>";
        tableInstance = table.render({
            limits: [10, 20, 50],
            elem: '#demo'
            , height: $(window).height() * 0.8
            , url: '../apppicture/findAppPicturelistPage.do' //数据接口
            , response: {
                statusName: 'status' //数据状态的字段名称，默认：code
                , statusCode: 200 //成功的状态码，默认：0
                , msgName: 'hint' //状态信息的字段名称，默认：msg
                , countName: 'count' //数据总数的字段名称，默认：count
                , dataName: 'data' //数据列表的字段名称，默认：data
            }
            , page: true //开启分页
            , cols: [[//表头
                {field: 'id', type: "checkbox", width: "5%", sort: true, fixed: 'left'}
                , {field: 'type', title: '图片类型', width: "24%", templet: "#type"}
                , {field: 'pic_explain', title: '图片说明', width: "24%"}
                , {field: 'maintenance_time', title: '维护时间', width: "24%"}
                , {
                    width: "23%", title: "操作",
                    templet: oprateBtn
                }
            ]]
            , even: false //开启隔行背景
            , done: function () {
            }
        });
    });
</script>
</html>