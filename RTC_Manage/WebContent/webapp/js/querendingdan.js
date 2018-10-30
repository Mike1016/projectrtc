new Vue({
	el:"#example",
	data:{
		ddid:'',
		mjly:'',
		ddlist:{
		/*"allPrice": 10000,
        "orderNo": "1537074923396",
        "allfreight": 0,
        "createTime": "2018-09-16 01:15:23",
        "orderGoods": [
            {
             	"count": 20,
                "myShoppingCoin": 5000,
                "price": 500,
                "freight": 0,
                "goodsAttachmentContent": "images/shangcheng/chanpin.png",
                "goodsName": "葡萄酒",
                "id": 53,
                "state": 0,
                "orderNo": "1537074923396",
                "createTime": "2018-09-16 01:15:23",
                "customerId": 1
            }
        ],
        "allCount": 20,
        "needToPay": 10000,
        "myShoppingCoin": 5000,
        "defaultAddress": {
            "id": 4,
            "state": 1,
            "receivingAddress": "西安",
            "addressee": "张三",
            "contactNo": "18710828311",
            "whichLocated": "雁塔区",
            "customerId": 1
		}*/
	}
	},
	created:function(){
		this.ddid = localStorage['ddid'];
		var data = {
			sessionId:sessionId,
			id:this.ddid
		};
		var that = this;
		//$.getJSON(base+'/app/order/toPayOrderShow.do',data,function(res){
			ajax({
				type:'get',
				url:'/app/order/toPayOrderShow.do',
				data:data,
				success:function(res){
					console.log(res);
			if(res.status == 1){
				that.ddlist = res.data;
			}else{
				mui.toast(res.msg);
			}
			}
		})
	},
	methods: {
		zhifu(){
			var data = {
				sessionId:sessionId,
				id:this.ddid,
				remark:this.mjly
			};
			console.log(data);
			//$.post(base+'/order/toPayOrder.do',data,function(res){
				ajax({
				type:'post',
				url:'/app/order/toPayOrder.do',
				data:data,
				success:function(res){
				console.log(res);
				if(res.status == 1){
					mui.toast(res.msg)
					location.href="zhifu.html";
				}else{mui.toast(res.msg)}
				}
			})
		},
		quxiao () {
			var data ={
				sessionId:sessionId,
				id:this.ddid
			};
			console.log(data);
			//$.post(base+'/order/orderPeration.do',data,function(res){
				ajax({
				type:'post',
				url:'/app/order/orderPeration.do',
				data:data,
				success:function(res){
				console.log(res);
				if(res.status == 1){
					mui.toast(res.msg);
					history.back(-1);
				}else{
					mui.toast(res.msg);
				}
				}
			})
		}
	}
})
