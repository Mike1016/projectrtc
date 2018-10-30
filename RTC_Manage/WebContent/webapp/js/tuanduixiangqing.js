new Vue({
	el:'#example',
	data:{
		"level1": [
           /* {
                "state": 0,
                "phone": "54645343"
            },
            {
                "state": 1,
                "phone": "54645343"
            },
            {
                "state": 2,
                "phone": "54645343"
            }*/
        ],
        "level2": [
           /* {
                "state": 0,
                "phone": "1231231"
            }*/
        ],
        "level3": [
           /* {
                "state": 0,
                "phone": "43453136876"
            }*/
        ]

	},
	mounted:function(){
		var that = this;
		that.sj = localStorage['scb'];
		//$.post(base+'/customer/teamDetail.do',{sessionId:sessionId},function(res){
		ajax({
				type:'get',
				url:'/app/customer/teamDetail.do',
				data:{sessionId:sessionId},
				success:function(res){
				console.log(res);
			if(res.status == 1){
				that.level1 = res.data.level1;
				that.level2 = res.data.level2;
				that.level3 = res.data.level3;
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