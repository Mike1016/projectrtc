<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主页</title>
<link rel="stylesheet" href="../static/layui/css/layui.css" />
<link rel="stylesheet" href="../static/css/common.css" />
<script type="text/javascript" src="../static/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="../static/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="../static/layui/layui.js"></script>
<style type="text/css">
 table
        {
            border-collapse: collapse;
            margin: 0 auto;
            text-align: center;
        }
        table td, table th
        {
            border: 1px solid #cad9ea;
            color: #666;
            height: 30px;
        }
        table thead th
        {
            background-color: #CCE8EB;
            width: 100px;
        }
        table tr:nth-child(odd)
        {
            background: #fff;
        }
        table tr:nth-child(even)
        {
            background: #F5FAFA;
        }

</style>
</head>
<body style="padding: 5%">

<form id="form" class="layui-form">
		<div class="layui-form-item">
			<label class="layui-form-label">商品类型</label>
			<div class="layui-input-inline">
				<select name="goodsTypeId" id="goodsTypeId" lay-verify="required">
					<c:forEach items="${goodsType}" var="item">
						<option value="${item.id }">${item.typeName }</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">商品名称</label>
			<div class="layui-input-inline">
				<input type="text" name="goodsName" id="goodsName" required  lay-verify="required"
					autocomplete="off" maxlength="64" placeholder='请输入商品名称' class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">商品价格</label>
			<div class="layui-input-inline">
				<input type="number" name="goodsOriginalPrice" id="goodsOriginalPrice"
					autocomplete="off" required  lay-verify="required" placeholder='请输入商品价格' class="layui-input">
			</div>
		</div> 
		<div class="layui-form-item">
			<label class="layui-form-label">折扣</label>
			<div class="layui-input-inline">
				<input type="number" required  lay-verify="required" name="goodsDiscountRate" id="goodsDiscountRate"
					autocomplete="off" placeholder='请输入折扣，如8折，输入8' class="layui-input" value="10">
			</div>
		</div>
		
		<div class="layui-form-item">
			<label class="layui-form-label">商品数量</label>
			<div class="layui-input-inline"> 
			<input type="number" name="goodsCount" id="goodsCount"
					autocomplete="off" required  lay-verify="required" placeholder='请输入商品数量' class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">商品运费</label> 
			<div class="layui-input-inline">
			<input type="number" name="freight" id="freight"
					autocomplete="off" required  lay-verify="required" placeholder='请输商品运费' class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">备注</label>
			<div class="layui-input-inline">
				<input type="text" name="remarks" id="remarks"
					   autocomplete="off" required  lay-verify="required" placeholder='请输入商品备注' class="layui-input">
			</div>
		</div>
		<hr>
	</form>
	<table id="attrTable">
		<tr>
			<td width="30%">属性名</td>
			<td width="30%">属性值</td>
			<td width="30%">
			<a class="layui-btn" onclick="addRow()">&nbsp;&nbsp;+&nbsp;&nbsp;</a>
			&nbsp;&nbsp;&nbsp;
			<a  class="layui-btn" onclick="delRow()">&nbsp;&nbsp;-&nbsp;&nbsp;</a>
			</td>
		</tr>
		</table>
		<script type="text/javascript">
		var attrName = 0;
		var attrValue = 0;
		function addRow(){
			$("#attrTable").append('<tr><td style="padding: 5px" width="30%"><input class="layui-input"' +
					'placeholder="请输入属性名" /></td><td style="padding: 5px" width="30%"><input class="layui-input" placeholder="请输入属性值"/>'
					+'</td><td width="300px"></td></tr>')
		}
		function delRow(){
			if($("#attrTable tr").length==1){
				alert("请最少保留一行亲");
				return;
			}
			$("#attrTable tr:last").remove()
		}
		</script>
		<hr>
		<br>
					<div class="layui-form-item">
			<label class="layui-form-label">封面图片</label>
			<div class="layui-input-inline">
			<input type="file" class="layui-input" name="file" id="logoFile1" onchange="setImg1(this);" class="selectedLogoImgId"
                         accept="image/jpg,image/jpeg,image/png,image/bmp" multiple/>
			</div>
		</div>
                  <div class="cp-img" id="div_cover">
                    <ul id="div_cover_imgs" style="overflow: hidden;">
                        <li style="block;" id="P_cover0">                                       
                    </ul>
                     <span id="hiddenss1"></span>
                  </div>
                  <hr>
                         <div class="layui-form-item">
			<label class="layui-form-label">详情图片</label>
			<div class="layui-input-inline">
			<input type="file" class="layui-input" name="file" id="logoFile2" onchange="setImg2(this);" class="selectedLogoImgId"
                         accept="image/jpg,image/jpeg,image/png,image/bmp" multiple/>
			</div>
		</div>
                  <div class="cp-img" id="div_detail">
                    <ul id="div_detail_imgs" style="overflow: hidden;">
                        <li style="block;" id="P_detai0">                                       
                    </ul>
                     <span id="hiddenss1"></span>
                  </div>
                  
                  <input type="button" onclick="addGoods()" value="提交"
			class="layui-btn layui-btn-sm" style="float: right;">
</body>


<script type="text/javascript">

function validate(){
	
	var goodsName = $("#goodsName").val();
	var goodsOriginalPrice = $("#goodsOriginalPrice").val();
	var goodsDiscountRate = $("#goodsDiscountRate").val();
	var goodsCount = $("#goodsCount").val();
	var freight = $("#freight").val();
    var remarks = $("#remarks").val();
	if(goodsName == '')
	{
		alert("商品名称不能为空")
		return false;
	}
	else if(goodsOriginalPrice == '')
	{
		alert("商品价格不能为空")
		return false;
	}
	else if(goodsDiscountRate == '')
	{
		alert("折率不能为空")
		return false;
	}
	else if(goodsCount == '')
	{
		alert("商品数量不能为空")
		return false;
	}
	else if(freight == '')
	{
		alert("商品运费不能为空")
		return false;
	}
	return true;
}


function addGoods(){
	debugger;
	var data = $("#form").serialize();
	var attrN="";
	var attrV="";
	var cover_Imgs="";
	var deital_Imgs="";
	
	var flag= true;
	
	for(var i=0;i<$("#attrTable tr").length;i++)
	{
		if(i == 0){
			continue;
		}
		var inputs = $($("#attrTable tr")[i]).find("input");
		if($(inputs[0]).val() == '' || $(inputs[1]).val()==''){
			flag = false;
			alert("属性值/属性名存在空值")
		}
		attrN+=$(inputs[0]).val()+",";
		attrV+=$(inputs[1]).val()+",";
	}
	
	
	if(!flag || !validate()){
		return false;
	}
	data+="&attrN="+attrN+"&attrV="+attrV;
	for(var i=0;i<$("#div_cover_imgs li").length;i++){
		if(i == 0){
			continue;
		}
		cover_Imgs+=$($($("#div_cover_imgs li")[i]).find("input")[0]).val()+","
	}
	for(var i=0;i<$("#div_detail_imgs li").length;i++){
		if(i == 0){
			continue;
		}
		deital_Imgs+=$($($("#div_detail_imgs li")[i]).find("input")[0]).val()+","
	}
	data+="&coverImgs="+cover_Imgs+"&deitalImgs="+deital_Imgs;
	$.post("../goods/validateGoodsName.do?name="+$("#goodsName").val(),function(res){
		if(res == true){
			$.post("../goods/addMainInfo.do?"+data,function(result){
				if(result == true){
				alert("操作成功");
					
				}
				else{
					alert("操作失败");					
				}
				parent.layer.closeAll();
				parent.reload();
			});
		}
		else{
			alert("商品名称重复")
		}
	})
}

function setImg1(obj){//用于进行图片上传，返回地址
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
              fileElementId:"logoFile1",//文件选择框的id属性  ,//文件选择框的id属性  
              dataType: 'json',   //json 
              contentType: false,    //不可缺
             processData: false,    //不可缺
             success: function (data, status)  //服务器成功响应处理函数
             {
            	 debugger;
                 if(data!=null){
                     $.each(data,function(i,url){
                        //拼接图片列表 
                          var id = $('#div_cover li:last').attr('id');
                              id = id.substr(7);
                              id = parseInt(id) + 1;
                              var ids=id;
                              id = 'P_Cover'+id;                                                    
                          var a_hidden="<li id='"+ id +"' style='float:left'><input type='hidden' value='"+url+"' name='imgs'>";
                          var img_html="<img style='height:100px;width:100px'  src='"+url+"'  onclick='showOriginal(this)' original='"+url+"'>";
                          var a_html="<a href='javascript:void(0);' onclick='delespan1("+ids+")'>删除</a>";
                          var li_html="</li>";
                         $('#div_cover_imgs').append(a_hidden+img_html+"<br>"+a_html+li_html);
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



function delespan1(id){
   $('#P_Cover'+id).remove();
}
function delespan2(id){
	   $('#P_Detai'+id).remove();
	}



var table;
var tableInstance;
function reload(){
	tableInstance.reload({
		  where: {
              goods_name: $("#goods_name").val()
		  }
		  ,page: {
		    curr: 1
		  }
		});
}

function add(){
	layer.open({
		type:2,
		title:"新增",
		area:["450px","580px"],
		maxmin:true,
		offset: '20%',
		content:"../user/add.do"  
	});
}
function edit(id,url){
	var url = "../user/toReview.do";
	layer.open({
		type:2,
		title:"修改",
		area:["350px","350px"],
		maxmin:true,
		offset: '20%',
		content:url+"?id="+id
	});
}

function del(id) {
	$.ajax({
		url:"../goods/delete.do",
		type:"get",
		data:"id="+id,
		dataType:"json",
		success:function(data) {
			location.reload();
		}
	});
}
//Demo
layui.use('form', function(){
    var form = layui.form;

    //监听提交
    form.on('submit(formDemo)', function(data){
        layer.msg(JSON.stringify(data.field));
        return false;
    });
});
	layui.use([ 'table','layer'], function() {
		table = layui.table;
		layer = layui.layer;		
		
  		var oprateBtn = "<div><button class='layui-btn layui-btn-xs' id='user_update' onclick=edit('{{d.id}}')>修改</button>"
    			+"<button class='layui-btn layui-btn-xs'  id='user_delete' onclick=del('{{d.id}}')>删除</button>";
		tableInstance = table.render({
			limits:[10,20,50],
		    elem: '#demo'
		    ,height: $(window).height()*0.8
		    ,url: '../goods/queryData.do' //数据接口
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
		      ,{field: 'goods_name', title: '商品名称', width:"10%"}
		      ,{field: 'goods_original_price', title: '原始价格', width:"5%"}
		      ,{field: 'goods_discount_price', title: '打折价格', width:"5%"}
		      ,{field: 'goods_count', title: '商品总数', width:"5%"}
		      ,{field: 'goods_remain_count', title: '剩余个数', width:"5%"}
		      ,{field: 'create_user', title: '发布用户', width:"10%"}
		      ,{field: 'create_time', title: '发布时间', width:"10%"}
		      ,{ width:"10%",title:"操作",
		    	  templet: oprateBtn
		       }
		      ]]
			 ,even: false //开启隔行背景
			 ,done:function(){
			    }
		  });
	});
</script>
</html>