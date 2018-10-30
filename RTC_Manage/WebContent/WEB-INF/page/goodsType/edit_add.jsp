
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="com.daka.entry.GoodsTypeVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../static/layui/css/layui.css" />
<title></title>
</head> 
<body style="padding: 2%">

	<form class="layui-form" enctype="multipart/form-data">
	<input type="hidden" id="id" name="id" value="${result.id }"/>
		<div class="layui-form-item">
			<label class="layui-form-label">商品类型</label>
			<div class="layui-input-inline">
			<input type="text" name="name" id="taskType" placeholder=""
					autocomplete="off" value='${result.typeName}' class="layui-input">
			</div>
		</div>
		
		<div class="layui-form-item">
			<label class="layui-form-label">分类图标</label>
			<div class="layui-input-inline">
			<input type="file" class="layui-input" name="file" id="logoFile2" onchange="setImg2(this);" class="selectedLogoImgId"
                         accept="image/jpg,image/jpeg,image/png,image/bmp" />
			</div>
		</div>
                  <div class="cp-img" id="div_detail">
                    <ul id="div_detail_imgs" style="overflow: hidden;">
                        <li style="block;" id="P_detai0">                                       
                    </ul>
                     <span id="hiddenss1"></span>
                  </div>
		<hr />
		<a class="layui-btn layui-btn-sm" style="float: right;" onclick="submit()" >提交</a>
	</form>
</body>
<script type="text/javascript" src="../static/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="../static/layui/layui.js"></script>
<script type="text/javascript" src="../static/js/ajaxfileupload.js"></script>

<script type="text/javascript">
function submit(){
	var id=$("#id").val();
	debugger;
	var imgsUrl = $($($("#div_detail_imgs li")[1]).find("input")[0]).val()
	var name = $("#taskType").val()
	if(name == ''){
		alert("类型名称不能为空")
		return false;
	}
	$.post("../goodsType/validateName.do?name="+name+"&id="+id,function(data){
		if(data){
			var url = "";
			if(id==""){
				url = "../goodsType/save.do?typeName="+name+"&typeImg="+imgsUrl
			}
			else{
				url = "../goodsType/update.do?typeName="+name+"&typeImg="+imgsUrl+"&id="+id;
			}
			$.post(url,function(){
				parent.layer.closeAll();
				parent.reload();
			})
		}
		else{
			alert("名称重复")
		}
	})
	
}


<%
GoodsTypeVO goods= (GoodsTypeVO)request.getAttribute("result");
String details = JSONObject.fromObject(goods).toString();
%>

$(document).ready(function(){
	init();
})

function init(){
	var jsonObj = JSON.parse('<%=details %>');
		var data = jsonObj
			debugger;
			var url = data.typeImg;
        //拼接图片列表 
          var id = $('#div_detail li:last').attr('id');
              id = id.substr(7);
              id = parseInt(id) + 1;
              var ids=id;
              id = 'P_Detai'+id;                                                    
          var a_hidden="<li id='"+ id +"' style='float:left'><input type='hidden' value='"+url+"' name='imgs'>";
          var img_html="<img style='height:100px;width:100px'  src='"+url+"'  onclick='showOriginal(this)' original='"+url+"'>";
          var a_html="<a href='javascript:void(0);' onclick='delespan2("+ids+")'>删除</a>";
          var li_html="</li>";
         $('#div_detail_imgs').append(a_hidden+img_html+"<br>"+a_html+li_html);
}


function delespan2(id){
	   $('#P_Detai'+id).remove();
	}

function setImg2(obj){//用于进行图片上传，返回地址
    var f=$(obj).val();
    if(f == null || f ==undefined || f == ''){
        return false;
    }else if(!/\.(?:png|jpg|bmp|gif|PNG|JPG|BMP|GIF)$/.test(f))
    {
        alertLayel("类型必须是图片(.png|jpg|bmp|gif|PNG|JPG|BMP|GIF)");
        $(obj).val('');
        return false;
    }else{
        //批量上传图片
        $.ajaxFileUpload({
             url:"../goods/upload.do",//需要链接到服务器地址   
              secureuri:false,  
              fileElementId:"logoFile2",//文件选择框的id属性  ,//文件选择框的id属性  
              dataType: 'json',   //json 
              contentType: false,    //不可缺
             processData: false,    //不可缺
             success: function (data, status)  //服务器成功响应处理函数
             {
                    	 debugger;
                 if(data!=null){
                     $.each(data,function(i,url){
                        //拼接图片列表 
                          var id = $('#div_detail li:last').attr('id');
                              id = id.substr(7);
                              id = parseInt(id) + 1;
                              var ids=id;
                              id = 'P_Detai'+id;                                                    
                          var a_hidden="<li id='"+ id +"' style='float:left'><input type='hidden' value='"+url+"' name='imgs'>";
                          var img_html="<img style='height:100px;width:100px'  src='"+url+"'  onclick='showOriginal(this)' original='"+url+"'>";
                          var a_html="<a href='javascript:void(0);' onclick='delespan2("+ids+")'>删除</a>";
                          var li_html="</li>";
                         $('#div_detail_imgs').append(a_hidden+img_html+"<br>"+a_html+li_html);
                     });
                 }else{
                	 debugger;
                     alertLayel("上传失败");
                     $("#url").val("");
                     $(obj).val('');
                 }   
             },
            error:function(XMLHttpRequest, textStatus, errorThrown){  
            	debugger;
                $("#url").val("");
                $(obj).val('');
           } 
        });
    }
}

</script>
</html>