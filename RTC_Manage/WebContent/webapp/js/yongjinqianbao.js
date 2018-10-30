new Vue({
	el:'#example',
	data:{
		sj:'',
		list:[
//		{
//          "account": 288,
//          "createTime": "2018-09-17 10:30:21",
//          "remark": "体验金激活到待分红",
//          "state": 1
//      }

		]
	},
	mounted:function(){
		var that = this;
		that.sj = localStorage['yjqb'];
		//$.post(base+'/capital/V2/record.do',{sessionId:sessionId},function(res){
			ajax({
				type:'get',
				url:'/app/capital/V2/record.do',
				data:{sessionId:sessionId},
				success:function(res){
				console.log(res);
			if(res.status == 1){
				that.list = res.data;
			}else{
				mui.toast(res.msg);
			}
			}
		})
	},
	methods:{
		tixian(){
			mui.prompt('','请输入充值金额','支付宝充值',['取消','确定'],function(e){
				if(e.index ==1){
					mui.toast('充值成功！');
				}
			},'div');
		}
	}
})