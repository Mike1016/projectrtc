
new Vue({
	el: '#example',
	data: {
		butlists: false, //筛选
		msg:[],
		goodsName:"",//搜索关键字
		screenType:'', //综合 销量  价格
		shaixuan:'',
		page:1,//页码
		goodsType:'',//分类数据
		isrow:true,  //数据列表样式控制
		shaixuanbtns:[  //筛选数据
			{
				tit:'推荐商品',
				sxid:1003,
				isxuanze:false
			},
			{
				tit:'新品上市',
				sxid:1004,
				isxuanze:false
			},
			{
				tit:'热卖单品',
				sxid:1005,
				isxuanze:false
			},
			{
				tit:'促销商品',
				sxid:1006,
				isxuanze:false
			},
			{
				tit:'卖家包邮',
				sxid:1007,
				isxuanze:false
			},
			{
				tit:'限时抢购',
				sxid:1008,
				isxuanze:false
			}
		],
		isprice:true,
		isjzsj:true,
	},
	created:function(){
		var fenlei = localStorage['fenlei'];
		if(fenlei){
			this.goodsType = fenlei;
		}else{
			this.goodsType = '';
		}
		console.log(this.goodsType);
		
		var that = this;
		that.qingqsj();
	//that.cankaoshuju();
	},
	beforeDestroy:function(){
		console.log(1);
		localStorage['fenlei']='';
	},
	mounted:function(){
	},
	methods: {
		qingqsj:function(){
			var that = this;
			console.log(that.page);
			var data = {
				sessionId:sessionId,
				goodsName:this.goodsName,//搜索关键字
				screenType:this.screenType+this.shaixuan, //筛选 
				page:this.page,//页码
				goodsType:this.goodsType  //分类数据
			};
			console.log(data);
			ajax({
				type:'get',
				url:'/app/goods/listPageShow.do',
				data:data,
				success:function(res){
				console.log(res);
				if(res.status == 1){
					if(res.data.length ==0){
						that.isjzsj = false;
					}
					$.each(res.data, function(k,v) {
						that.msg.push(v);
					});
					that.page++;
				}else{
					mui.toast(res.msg);
				}
				}
			});
		},
		zhsx:function(){ //综合
			$.each(this.shaixuanbtns, function(k,v) {
  				v.isxuanze=false;
  			});
  			this.shaixuan = '';
			this.screenType = '';
			this.goodsName = '';
			this.goodsType = '';
  			var that = this;
  			this.page = 1;
  			this.msg = [];
			this.qingqsj();
//			setTimeout(function(){
				//console.log(1);
//				this.cankaoshuju();
//			 },800);
		},
		xlsx:function(){  //销量
			this.screenType = '1001';
			$.each(this.shaixuanbtns, function(k,v) {
  				v.isxuanze=false;
  			});
  			this.shaixuan = '';
  			var that = this;
  			this.page = 1;
  			this.msg = [];
//			this.qingqsj();
//			setTimeout(function(){
				//console.log(1);
				this.cankaoshuju();
//			 },800);
		},
		jgpx:function(){  //价格
			if(this.isprice){
				this.screenType = '1002';
				$.each(this.shaixuanbtns, function(k,v) {
  				v.isxuanze=false;
  			});
  			this.shaixuan = '';
  			var that = this;
  			this.page = 1;
  			this.msg = [];
			this.qingqsj();
//			setTimeout(function(){
				//console.log(1);
//				this.cankaoshuju();
//			 },800);
			this.isprice = false;
			}else{
			this.screenType = '1002 降序';
			$.each(this.shaixuanbtns, function(k,v) {
  				v.isxuanze=false;
  			});
  			this.shaixuan = '';
  			var that = this;
  			this.page = 1;
  			this.msg = [];
			this.qingqsj();
//			setTimeout(function(){
				//console.log(1);
//				this.cankaoshuju();
//			 },800);
			this.isprice = true;
			}
			console.log(this.screenType);
		},
		xuanxiamg:function(k){ //选择筛选数据
			this.screenType = '';
			if(this.shaixuanbtns[k].isxuanze){
				this.shaixuanbtns[k].isxuanze = false;
				var sp =this.shaixuan.replace(this.shaixuanbtns[k].sxid+',','');
				this.shaixuan=sp;
				
			}else{
				this.shaixuanbtns[k].isxuanze = true;
				this.shaixuan+=this.shaixuanbtns[k].sxid+',';
			}
			 console.log(this.screenType);
		},
		
		listqh:function(){ //数据列表样式
			if(this.isrow){
				this.isrow = false;
			}else{
				this.isrow = true;
			}
		},
		tmpddxq:function(id){ //跳转订单详情
			//console.log(id);
			var data = {
				sessionId:sessionId,
				goodsIdStr:id,
				goodsCountStr:1};
			ajax({
				type:'post',
				url:'/app/order/createOrder.do',
				data:data,
				success:function(res){
				console.log(res);
				if(res.status == 1){
					mui.toast(res.msg);
					localStorage['ddid'] = res.data.id;
					location='querendingdan.html';
					}else{mui.toast(res.msg);}
				}
			});
			},
//		cankaoshuju:function(){
//			console.log('加载数据···’');
//			console.log(this.screenType);
//			var that = this;
//			for(var i=0; i<2; i++){
//				//console.log(3);
//		                that.msg.push({
//		                	id:1,
//		                	goodsName:'阿斯兰的法律疯掉了几十个大立科技奥拉夫到家了卡士大夫就离开',
//							goodsDiscountPrice:450,
//							goodsAttachmentContent :'images/shangcheng/chanpin.png'
//		                });  
//	              	}
//		},
  		sxbtn:function(){ //点筛选
  			this.butlists = true;
  		},
  		qxsx:function(){//取消筛选
  			this.butlists = false;
  			$.each(this.shaixuanbtns, function(k,v) {
  				v.isxuanze=false;
  			});
  			this.shaixuan = '';
  		},
  		qrsx:function(){ //确认筛选
  			this.butlists = false;
  			var that = this;
  			//console.log(this.goodsName);
  			this.page = 1;
  			this.msg = [];
			this.qingqsj();
//			setTimeout(function(){
				//console.log(1);
//				that.cankaoshuju();
//			 },800);
			//console.log(this.msg);

  		},
  		sousuoinp:function(){  //搜索
  			 var that = this;
  			console.log(this.goodsName);
  			this.page = 1;
  			this.msg = [];
			this.qingqsj();
//			setTimeout(function(){
//				console.log(1);
//				that.cankaoshuju();
//			 },800);
			console.log(this.msg);
  		},
  		jzsj:function(){
  			this.qingqsj();
  		}
  		
	}
});


//排序选项
$('.sxb>div').click(function() {
//	console.log(this);
	$(this).addClass('active').siblings().removeClass('active');
});

$('#lists').on('tap','[data-spid]',function(){  //跳转详情
	var id = $(this).attr('data-spid');
	//console.log(id);
	localStorage['spid'] = id;
	location.href = 'shangpinxiangqing.html';
});