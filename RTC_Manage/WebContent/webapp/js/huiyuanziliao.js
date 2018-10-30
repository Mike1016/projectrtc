//$.getJSON(base+'/app/customer/memberCenter.do',{sessionId:sessionId},function(res){
	ajax({
			type:'get',
			url:'/app/customer/memberCenter.do',
			data:{sessionId:sessionId},
			success:function(res){
				console.log(res);
			var dt = res.data
			if(res.status ==1){
				$('.shouji').val(dt.phone);
				$('.zsxm').val(dt.nickName);
				$('.wechat').val(dt.identityCard);
				$('.zfbzh').val(dt.alipay);
				$('#result').val(dt.birthday);
				$('#showCityPicker3').val(dt.city);
			}else{
				mui.toast(res.msg);
				location = 'my.html';
			}
		}
	})


function yzmzq(){
	var zsxm = $.trim($('.zsxm').val());
	var dxm = $.trim($('.dxm').val());
	var wechat = $.trim($('.wechat').val());
	var zfbzh = $.trim($('.zfbzh').val());
	var csrq = $.trim($('#result').val());
	var szcs = $.trim($('#showCityPicker3').val());
	var sfzreg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/; 
	if(dxm == ""){
				mui.toast('短信码不能为空！'); 
			        return false; 
			}
	/*if(!sfzreg.test(sfzhm)){
		mui.toast('身份证号码格式不正确！');
		return;
	}*/
	console.log(zsxm);console.log(dxm);console.log(sfzhm);
			console.log(zfbzh);console.log(csrq);console.log(sessionId);
			ajax({
					type:"POST",
					url:'/app/customer/PerfectingPersonal.do',
					data:{
						sessionId:sessionId,
						IdentityCard:wechat, 
						nickName:zsxm,
						alipay:zfbzh,
						birthday:csrq,
						city:szcs
					},
					success:function(res){
						console.log(res);
						if(res.status == 1){
							mui.toast(res.msg);
							location='my.html';
						}else{
							mui.toast(res.msg);
						}
					}
				});
}








mui.init();
				mui.ready(function() {
					/**
					 * 获取对象属性的值
					 * 主要用于过滤三级联动中，可能出现的最低级的数据不存在的情况，实际开发中需要注意这一点；
					 * @param {Object} obj 对象
					 * @param {String} param 属性名
					 */
					var _getParam = function(obj, param) {
						return obj[param] || '';
					};
					
					//-----------------------------------------
					//					//级联示例
					var cityPicker3 = new mui.PopPicker({
						layer: 3
					});
					cityPicker3.setData(cityData3);
					var showCityPickerButton = document.getElementById('showCityPicker3');
					//var cityResult3 = doc.getElementById('cityResult3');
					showCityPickerButton.addEventListener('tap', function(event) {
						cityPicker3.show(function(items) {
						var chengshi = _getParam(items[0], 'text') + " " + _getParam(items[1], 'text') + " " + _getParam(items[2], 'text');
						console.log(chengshi);
						//showCityPickerButton.value(chengshi);
							valzhi(showCityPickerButton,chengshi);
							//返回 false 可以阻止选择框的关闭
							//return false;
						});
					}, false);
				});
				
				
				var result = mui('#result')[0];
				var btns = mui('#result');
				btns.each(function(i, btn) {
					btn.addEventListener('tap', function() {
						var _self = this;
						if(_self.picker) {
							_self.picker.show(function (rs) {
								result.innerText = '选择结果: ' + rs.text;
								valzhi(result,rs.text);
								_self.picker.dispose();
								_self.picker = null;
							});
						} else {
							var optionsJson = this.getAttribute('data-options') || '{}';
							var options = JSON.parse(optionsJson);
							var id = this.getAttribute('id');
							/*
							 * 首次显示时实例化组件
							 * 示例为了简洁，将 options 放在了按钮的 dom 上
							 * 也可以直接通过代码声明 optinos 用于实例化 DtPicker
							 */
							_self.picker = new mui.DtPicker(options);
							_self.picker.show(function(rs) {
								/*
								 * rs.value 拼合后的 value
								 * rs.text 拼合后的 text
								 * rs.y 年，可以通过 rs.y.vaue 和 rs.y.text 获取值和文本
								 * rs.m 月，用法同年
								 * rs.d 日，用法同年
								 * rs.h 时，用法同年
								 * rs.i 分（minutes 的第二个字母），用法同年
								 */
								result.innerText = '选择结果: ' + rs.text;
								valzhi(result,rs.text);
								/* 
								 * 返回 false 可以阻止选择框的关闭
								 * return false;
								 */
								/*
								 * 释放组件资源，释放后将将不能再操作组件
								 * 通常情况下，不需要示放组件，new DtPicker(options) 后，可以一直使用。
								 * 当前示例，因为内容较多，如不进行资原释放，在某些设备上会较慢。
								 * 所以每次用完便立即调用 dispose 进行释放，下次用时再创建新实例。
								 */
								_self.picker.dispose();
								_self.picker = null;
							});
						}
						
					}, false);
				});
			
				
				
				function valzhi(doc,data){
					$(doc).val(data);
				}