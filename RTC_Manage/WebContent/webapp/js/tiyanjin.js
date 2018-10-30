new Vue({
	el:'#example',
	data:{
		je:'',
		zt:'',
		list:[
		/*{
            "account": 288,
            "createTime": "2018-09-17 10:30:21",
            "remark": "体验金激活到待分红",
            "state": 1
        }*/

		]
	},
	mounted:function(){
		var that = this;
		that.je = localStorage['tyj'];
		that.zt = localStorage['tyjzt'];
		//$.post(base+'/capital/V1/record.do',{sessionId:sessionId},function(res){
			ajax({
				type:'get',
				url:'/app/capital/V1/record.do',
				data:{sessionId:sessionId},
				success:function(res){
				console.log(res);
			if(res.status == 1){
//				that.list = res.data;
			}else{
				mui.toast(res.msg);
			}
			}
		})
	},
	methods:{
		qujihuo(){
			location.href="jihuoma.html"
			/*mui.prompt('','请输入充值金额','支付宝充值',['取消','确定'],function(e){
				if(e.index ==1){
					mui.toast('充值成功！');
				}
			},'div');*/
		}
	}
})