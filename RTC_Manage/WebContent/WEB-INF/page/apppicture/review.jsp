<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.daka.entry.SysUserVO" %>
<%@page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="../static/layui/css/layui.css"/>
    <script type="text/javascript" src="../static/js/jquery-1.11.3.min.js"></script>
    <title></title>
</head>

<body style="">
<form id="form">
    <input type="hidden" name="id" id="id" value="${result.data.id}">
    <div class="layui-form-item">
        <label class="layui-form-label">图片路径</label>
        <div class="layui-input-inline">
            <input type="text" name="pictureImg" id="picture_img"
                   autocomplete="off" value="${result.data.pictureImg}" class="layui-input"
                   disabled="disabled">
        </div>
    </div>
    <div class="layui-form-item">
        <img src='<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()%>${result.data.pictureImg}' width="190px" hspace="110px">
        <div align="right">
            <div style="padding-right:30px">
            <button type="button" class="layui-btn" id="file">
                <i class="layui-icon">&#xe67c;</i>上传图片
            </button>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">图片类型</label>
        <div class="layui-input-block">
            <select id="type" lay-verify="required" class="layui-input" style="width: 190px">
                <option value="1" <c:if test="${1==result.data.type}">selected="selected"</c:if>>群聊图片</option>
                <option value="2" <c:if test="${2==result.data.type}">selected="selected"</c:if>>新手指引</option>
                <option value="3" <c:if test="${3==result.data.type}">selected="selected"</c:if>>LOGO</option>
                <option value="4" <c:if test="${4==result.data.type}">selected="selected"</c:if>>轮播图</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">图片说明</label>
        <div class="layui-input-inline">
            <input type="text" name="picExplain" id="pic_explain"
                   autocomplete="off" value="${result.data.picExplain}" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">维护时间</label>
        <div class="layui-input-inline">
            <input type="text" name="maintenanceTime" id="maintenance_time"
                   autocomplete="off" value="${result.data.maintenanceTime}" class="layui-input"
                   disabled="disabled">
        </div>
    </div>
    <hr/>
    <div style="padding-right:30px">
        <input type="button" onclick="modifyPicture()" value="提交"
               class="layui-btn layui-btn-sm" style="float: right;">
    </div>

</form>
</body>

<script type="text/javascript" src="../static/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="../static/layui/layui.js"></script>
<script>
    layui.use('upload', function () {
        var upload = layui.upload;

        //执行实例
        var uploadInst = upload.render({
            elem: '#file' //绑定元素
            , url: '../apppicture/upload.do' //上传接口
            , done: function (data) {
                //上传完毕回调
                var v1 = data.src;
                $("#picture_img").val(v1);
            }
            , error: function () {
                //请求异常回调
                alert("上传失败");
            }
        });
    });
</script>
<script type="text/javascript">
    function modifyPicture() {
        debugger;
        var id = $("#id").val();
        var pictureImg = $("#picture_img").val();
        var type = $("#type option:selected").val();
        var picExplain = $("#pic_explain").val();
        var param = {
            id: id,
            pictureImg: pictureImg,
            type: type,
            picExplain: picExplain,
        };
        var url = "${pageContext.request.contextPath }/apppicture/modifyAppPicture.do";
        $.post(url, param, function (result) {
            debugger;
            parent.layer.closeAll();
            window.parent.location.reload();
        });
        return false;
    }
</script>
</html>