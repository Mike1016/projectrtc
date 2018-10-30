
//获取验证码
$('.dxyz').on('click',function(e){
	e.preventDefault();
	var tar = $(e.target);
	console.log(tar);
	var sj = $('.shouji').val();
	console.log(sj);
	if(sj != ''){
		if(sj.length == 11){
			//$.post(base+'/app/customer/AuthCode.do', {phone:sj},function(res){\
				ajax({
				type:'post',
				url:'/app/customer/AuthCode.do',
				data:{phone:sj},
				success:function(res){
				if(res.status == 1){
					var h=60;
					var tem = setInterval(function(){
						if(h>0){
							tar.attr("disabled",true);
							tar.text(h + ' s ');
							h--;
						}else{
							clearInterval(tem);
							tem=null;
							tar.attr('disabled',false);
								tar.text('重新获取');
							}
					},1000);
				}else{
					mui.toast(res.msg);
				}
				}
			})
		}else{
			mui.toast('请输入正确的手机号')
		}
	}else{
		mui.toast('请输入手机号');
	}
});

$('.content').off('click').on('click','[data-padid]',function(){
	console.log(2);
	$(this).addClass('active').siblings().removeClass('active');
	var id = $(this).attr('data-padid');
	$(id).addClass('active').siblings().removeClass('active');
});

$(document).ready(function(){
 $('.tanchuang').click(function(event){
  if(event.target==this){
  	$(this).css('display','none');
  }
  })
});



var sessionId = localStorage['sessionId'];
$('.gouwuche').on('click',function(){
	ajax({
		type:'post',
		url:'/app/shoppingCart/showInfo.do',
		data:{sessionId:sessionId},
		success:function(res){
			if(res.status == 1){
				if(res.status == 5000){
					mui.toast('请先登录··');
          			location = 'login.html';
				}else{
					window.location.href='gouwuge.html';
				}
				
			}else{
				mui.toast(res.msg);
			}
		}
	})
	 
});
$('.my').on('click',function(){
	ajax({
		type:'post',
		url:'/app/customer/memberCenter.do',
		data:{sessionId:sessionId},
		success:function(res){
			var dt = res.data
			if(res.status == 1){
				if(res.status == 5000){
					mui.toast('请先登录··');
          			location = 'login.html';
				}else{
					window.location.href='my.html';
				}
			}else{
				mui.toast(res.msg);
			}
		}
	})
})

