new Vue({
	el:'#example',
	data:{
		dfh:[
			/*{
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

		],
		dfk:[
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
       		}
*/

		],
		dsh:[
					/*{
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
					if(v.state == 0){
						that.dfk.push(v);
					}else if(v.state == 2){
						that.dfh.push(v);
					}else if(v.state == 3){
						that.dsh.push(v);
					}
				});
			}else{mui.toast(res.msg);}
			}
		});
		console.log(that.dfk);
		console.log(that.dfh);
		console.log(that.dsh);
	},
	methods:{
		zfdd (id){
			
			localStorage['ddid'] = id;
			location.href = 'dingdanxiangqing.html';
		},
		qxdd (id,k){
			var that = this;
			var data = {
				sessionId:sessionId,
				id:id
			};
			//$.post(base+'/order/orderPeration.do',data,function(res){
				ajax({
				type:'post',
				url:'/app/order/orderPeration.do',
				data:data,
				success:function(res){
				console.log(res);
				if(res.status == 1){
					mui.toast(res.msg);
					that.dfk.splice(k,1);
				}else{
					mui.toast(res.msg);
				}
				}
			})
		},
		ckdd (id){
			localStorage['ddid'] = id;
			location.href = 'dingdanxiangqing2.html';
		},
		tkdd (id,k){
			var that = this;
			var data = {
				sessionId:sessionId,
				id:id
			};
			//$.post(base+'/order/refund.do',data,function(res){
				ajax({
				type:'post',
				url:'/app/order/refund.do',
				data:data,
				success:function(res){
				console.log(res);
				if(res.status == 1){
					mui.toast(res.msg);
					that.dfh.splice(k,1);
				}else{
					mui.toast(res.msg);
				}
				}
			})
		},
		qrsh (id,k){
			var that = this;
			var data = {
				sessionId:sessionId,
				orderId:id
			};
			//$.post(base+'/order/collectGoods.do',data,function(res){
				ajax({
				type:'post',
				url:'/app/order/collectGoods.do',
				data:data,
				success:function(res){
				console.log(res);
				if(res.status == 1){
					that.dsh.splice(k,1);
				}else{
					mui.toast(res.msg);
				}
				}
			})
		},
	}
})
