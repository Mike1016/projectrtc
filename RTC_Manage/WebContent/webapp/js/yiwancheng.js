new Vue({
	el:'#example',
	data:{
		ywc:[
				/*	{
        "allPrice": 10000,
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
		}
       		}*/


		]
	},
	mounted:function(){
		var that = this;
//		$.getJSON(base+'/order/queryAllOrder.do',{sessionId:sessionId},function(res){
			ajax({
				type:'get',
				url:'/app/order/queryAllOrder.do',
				data:{sessionId:sessionId},
				success:function(res){
				console.log(res);
			if(res.status == 1){
				$.each(res.data, function(k,v) {
					if(v.state ==4){
						that.ywc.push(v);
					}
				});
			}else{mui.toast(res.msg);}
			}
		});
	},
	methods:{
	}
})
