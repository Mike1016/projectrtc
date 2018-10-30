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
		this.sj = localStorage['yeqb'];
		//$.post(base+'/capital/V3/record.do',{sessionId:sessionId},function(res){
		ajax({
				type:'post',
				url:'/app/capital/V3/record.do',
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
			mui.prompt('','提现最低100元','提现',['取消','确定'],function(e){
				if(e.index ==1){
					console.log(e.value);
					if(e.value <100){
						mui.toast('提现最低100元')
					}else{
						ajax({
							type:'post',
							url:'/app/alipay/transfer.do',
							data:{
								sessionId:sessionId,
								totalAmount:e.value,
								type:0
							},
							success:function(res){
								console.log(res);
							if(res.status == 1){
								
							}else{
								mui.toast(res.msg);
							}
						}
						})
					}
				}
			},'div');
		}
	}
})