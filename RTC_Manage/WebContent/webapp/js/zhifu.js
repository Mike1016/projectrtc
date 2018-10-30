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
		var that = this;
		that.ddid = localStorage['ddid'];
		var data = {
			sessionId:sessionId,
			id:that.ddid
		};
		//$.post(base+'/order/toPayOrderShow',data,function(res){
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
			var that = this;
			console.log(this.ddlist.needToPay);
			mui.confirm('确定要支付吗？','支付',['取消','确定'],function(e){
				if(e.index == 1){
						var data = {
							sessionId:sessionId,
							id:that.ddid,
							myshoppingCoin:that.ddlist.myShoppingCoin,
							needToPay:that.ddlist.needToPay
						};
						console.log(data);
						if(that.ddlist.needToPay<that.ddlist.myShoppingCoin){
							//console.log(1);
							//$.post(base+'/order/toPay.do',data,function(res){
								ajax({
									type:'post',
									url:'/app/order/toPay.do',
									data:data,
									success:function(res){
									console.log(res);
								if(res.status == 1){
									mui.toast(res.msg);
									location.href='daifahuo.html';
								}
								}
							})
						}else{
							mui.toast('商城币不足！');
						}
				}
			},'div')
			
		}
	}
})
