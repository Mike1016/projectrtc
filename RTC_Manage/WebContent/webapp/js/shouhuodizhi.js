var dz = new Vue({
	el: '#example',
	data: {
		list:[
//			{
//          "id": 3,
//          "state": 1,
//          "receivingAddress": "北京",
//          "addressee": "张三",
//          "contactNo": "18710828311",
//          "whichLocated": "xx街道",
//          "customerId": 1
//      },
//      {
//          "id": 4,
//          "state": 0,
//          "receivingAddress": "西安",
//          "addressee": "张三",
//          "contactNo": "18710828311",
//          "whichLocated": "xx街道",
//          "customerId": 1
//      },
//      {
//          "id": 5,
//          "state": 0,
//          "receivingAddress": "上海",
//          "addressee": "张三",
//          "contactNo": "18710828311",
//          "whichLocated": "xx街道",
//          "customerId": 1
//      },
//      {
//          "id": 6,
//          "state": 0,
//          "receivingAddress": "深圳",
//          "addressee": "张三",
//          "contactNo": "18710828311",
//          "whichLocated": "xx街道",
//          "customerId": 1
//      },

		]
	},
	created:function(){
		var that = this;
		//$.getJSON(base+'/app/address/queryAllAddress.do',{sessionId,sessionId},function(res){
			ajax({
				type:'get',
				url:'/app/address/queryAllAddress.do',
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
		tjdz (){
			localStorage['dzxx']="";
			location.href = 'xinjiandizhi.html';
		},
		bjdz (k) {
			var dzxx = JSON.stringify(this.list[k]);
			localStorage['dzxx'] = dzxx;
			location.href = 'xinjiandizhi.html';
		}
	},
});