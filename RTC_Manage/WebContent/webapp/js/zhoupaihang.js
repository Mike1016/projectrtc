new Vue({
	el:'#example',
	data:{
		imgs:[
			"images/myer/jp0.png",
			"images/myer/jp1.png",
			"images/myer/jp2.png"
		],
		list:[
//		 {
//          "id": 1,
//          "state": 0,
//          "phone": "15588446699",
//          "account": 5500
//      },
//      {
//          "id": 9,
//          "state": 1,
//          "phone": "18899998888",
//          "account": 4000
//      },
//      {
//          "id": 2,
//          "state": 0,
//          "phone": "14456987845",
//          "account": 1000
//      },
//      {
//          "id": 3,
//          "state": 0,
//          "phone": "13265478945",
//          "account": 1000
//      },
//      {
//          "id": 4,
//          "state": 1,
//          "phone": "12569874512",
//          "account": 900
//      },
//      {
//          "id": 6,
//          "state": 0,
//          "phone": "15547894512",
//          "account": 500
//      },
//      {
//          "id": 7,
//          "state": 0,
//          "phone": "13256478945",
//          "account": 400
//      }

		]

	},
	mounted:function(){
		var that = this;
		that.sj = localStorage['scb'];
		//$.get(base+'/app/ranking/showRanking.do',{sessionId:sessionId},function(res){
			ajax({
				type:'get',
				url:'/app/ranking/showRanking.do',
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
	},
	filters: {
                hideMiddle(val) {
                    return `${val.substring(0,3)}****${val.substring(val.length-4)}`
                }
            }
})