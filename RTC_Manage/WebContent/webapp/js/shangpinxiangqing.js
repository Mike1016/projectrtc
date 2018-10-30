
new Vue({
	el: '#example',
	data: {
		butlists:false,
		goodid:'',
		shuju:{},
		goodimgs:[ //图片
//			{goodsAttachmentContent : 'images/shangcheng/chanpin.png'},
//			{goodsAttachmentContent:'images/shangcheng/chanpin.png'},
//			{goodsAttachmentContent:'images/shangcheng/chanpin.png'},
//			{goodsAttachmentContent:'images/shangcheng/chanpin.png'},
//			{goodsAttachmentContent:'images/shangcheng/chanpin.png'}
		],
		goodyj:762,//原价
		goodxj:866,//现价
		goodname:'使得法国发生的发生的发发',//商品名
		goodxqimgs:[
//			{goodsAttachmentContent :'images/shangcheng/xiangqing.png'}
		],//详情图片
		goodcanshu:[
			{
                "id": 1,
                "attrValue": "葡萄酒",
                "goodsId": 1,
                "attrName": "产品类别"
            },
            {
                "id": 2,
                "attrValue": "是",
                "goodsId": 1,
                "attrName": "是否进口"
            },

		],//参数
		goodxps:10,//新品数
		goodcxs:10,//促销数
		goodzs:20,//总数
		goodimg:'images/shangcheng/chanpin.png',//商品图片
		goodxl:12,//销量
		shuliang:1, //数量
		istijiao:false,  //是否提交
		jiagou:'',//加入购物车还是立即购买
		diyiimg:'',
		zuihouimg:'',
	},
	mounted:function(){
		//console.log(this.goodimgs[this.goodimgs.length-1].goodsAttachmentContent);
		var spid = localStorage['spid'];
		this.goodid = spid;
		var data = {
			sessionId:sessionId,
			id:spid
		};
		var that = this;
		console.log(data);
		//$.get(base+'/app/goods/goodsInfo.do',dt,function(res){
		ajax({
				type:'get',
				url:'/app/goods/goodsInfo.do',
				data:data,
				success:function(res){
			console.log(res);
			if(res.status == 1){
				console.log(1);
				that.shuju = res.data;
				var dt = res.data;
				that.goodimgs = that.shuju.good.goodsImgs; //图片集合
				that.diyiimg = that.goodimgs[0].goodsAttachmentContent; //图片集合
				that.zuihouimg = that.goodimgs[that.goodimgs.length-1].goodsAttachmentContent; //图片集合
				that.goodyj = that.shuju.good.goodsOriginalPrice;//原价
				that.goodxj = that.shuju.good.goodsDiscountPrice;//现价
				that.goodname = that.shuju.good.goodsName;//商品名
				that.goodxqimgs = that.shuju.details;//详情图片
				that.goodcanshu = that.shuju.parameter;//参数
				that.goodxps = that.shuju.business.newGoodsCount;//新品数
				that.goodcxs = that.shuju.business.promotionGoodsCount;//促销数
				that.goodzs = that.shuju.business.allGoodsCount;//总数
				that.goodimg = that.shuju.good.goodsImgs.goodsAttachmentContent;//商品图片
				that.goodxl = that.shuju.good.salesVolume;//销量
				console.log(that.goodimgs[that.goodimgs.length-1].goodsAttachmentContent);
			}else{
				mui.toast(res.msg);
			}
			}
		})
	},
	/*mounted:function(){

	},*/

	methods:{
		tijiao:function(){
			var that = this;
			var data = {
				sessionId:sessionId,
				id:this.goodid,
				count:this.shuliang
			};
			var data2 = {
				sessionId:sessionId,
				goodsIdStr:this.goodid,
				goodsCountStr:this.shuliang
			};
			if(this.istijiao){
				if(this.jiagou){//加入购物车
					console.log(data);
			//$.post(base+'/goods/ShopCat.do',data,function(res){
				ajax({
				type:'post',
				url:'/app/goods/ShopCat.do',
				data:data,
				success:function(res){
					console.log(res);
					if(res.status == 1){
						mui.toast(res.msg);
						that.butlists = false;
					}else{
						mui.toast(res.msg);
					}
				}
			});
			}else{ //立即购物
				//console.log("立即购买")
			//$.post(base+'/order/createOrder.do',data,function(res){
				
				ajax({
				type:'post',
				url:'/app/order/createOrder.do',
				data:data2,
				success:function(res){
					console.log(data2);
					console.log(res);
				if(res.status == 1){
					mui.toast(res.msg);
					localStorage['ddid'] = res.data.id;
					location='querendingdan.html';
				}else{mui.toast(res.msg);}
				}
			});
	
				}
			}else{
				this.butlists = false;
			}
		},
		jiarugouwuche:function(){   //加入购物车
			this.butlists = true;
			this.istijiao = true;
			this.jiagou = true;
			
		},
		lijigoumai:function(id){  //立即购买
			this.butlists = true;
			this.istijiao = true;
			this.jiagou = false;
			
			
		},
		xzsl:function(){ 
			this.istijiao = false;
			if(this.butlists){
				this.butlists = false;
			}else{this.butlists = true;};
		},
		jian:function(){ //减
			if(this.shuliang<=1){
				return;
			}else{
				this.shuliang--;
			}
		},
		jia:function(){  //加
			this.shuliang++;
		},
	}

});
