
new Vue({
	el: '#example',
	data: {
		bianwan:true,
		good_list:[
//			{
//				id: 1,  // (购物车商品编号     不显示)
//			    goodsAttachmentContent : "images/shangcheng/chanpin.png", // (商品图片)
//			    goodsName: "红酒是个斯蒂芬告诉对方感受到分公司多福多寿告诉对方",   // （商品名称）
//			    count:10 , // (商品数量) 
//			    goodsOriginalPrice: "890",   // （商品原价价格  单价）
//			    goodsDiscountPrice: "786",   //（商品折扣价格  单价）
//			    chosen:true  //(是否被选中  true 是     false 否)
//			},
//			{
//				id: 2,  // (购物车商品编号     不显示)
//			    goodsAttachmentContent : "images/shangcheng/chanpin.png", // (商品图片)
//			    goodsName: "红酒",   // （商品名称）
//			    count:9 , // (商品数量) 
//			    goodsOriginalPrice: "890",   // （商品原价价格  单价）
//			    goodsDiscountPrice: "786",   //（商品折扣价格  单价）
//			    chosen:true  //(是否被选中  true 是     false 否)
//			},
//			{
//				id: 3,  // (购物车商品编号     不显示)
//			    goodsAttachmentContent : "images/shangcheng/chanpin.png", // (商品图片)
//			    goodsName: "红酒",   // （商品名称）
//			    count:8 , // (商品数量) 
//			    goodsOriginalPrice: "890",   // （商品原价价格  单价）
//			    goodsDiscountPrice: "786",   //（商品折扣价格  单价）
//			    chosen:true  //(是否被选中  true 是     false 否)
//			},
			
		],
		totalPrice: 0,
		totalNum: 0,
		selected_all: false,
		spids:'',
		spnums:'',
	},
	created:function(){
		var that = this;
		//$.get(base+'/app/shoppingCart/showInfo.do',{sessionId:sessionId},function(res){
			ajax({
				type:'post',
				url:'/app/shoppingCart/showInfo.do',
				data:{sessionId:sessionId},
				success:function(res){
			if(res.status == 1){
				var len = res.data.length;
				var num = 0;
				for(var i=0;i<len;i++){
					if(res.data[i].chosen){
						num++;
					}
				}
				console.log(len);
				console.log(num);
				if(num == len){
					that.selected_all = true;
				}else{
					that.selected_all = false;
				}
				that.good_list = res.data;
				
				that.getTotal();
			}else{
				mui.toast(res.msg);
			}
			}
		})
		
	},
	mounted:function(){
		
	},
	watch: {
			'good_list': {
				handler: function (val, oldVal) {
					 //console.log(val);
					 //console.log(oldVal);
					return val;
				},
				deep: true
			}
	},
	methods: {
		shanchu () {
			this.spids = '';
			for(var i = 0; i < this.good_list.length; i++){
				if(this.good_list[i].chosen){
					this.spids += this.good_list[i].id+',';
					this.good_list.splice(i,1);
				}
			}
			console.log(this.spids);
			var data = {
				sessionId:sessionId,
				ids:this.spids
			}
			//$.post(base+'/shoppingCart/dels.do',data,function(res){
				ajax({
				type:'post',
				url:'/app/shoppingCart/dels.do',
				data:data,
				success:function(res){
			console.log(res);
				if(res.status == 1){
					location.reload();
				}else{
					mui.toast(res.msg);
				}
				}
			});
		},
		jiesuan () {
			var that = this;
			that.spids = '';
			that.spnums = '';
			for(var i = 0; i < that.good_list.length; i++){
				if(that.good_list[i].chosen){
					that.spids += that.good_list[i].goodsId+',';
					that.spnums += that.good_list[i].count+',';
				}
			}
			var data = {
				sessionId:sessionId,
				goodsIdStr:that.spids,
				goodsCountStr:that.spnums
			};
			console.log(data);
			ajax({
				type:'post',
				url:'/app/order/createOrder.do',
				data:data,
				success:function(res){
					console.log(res);
				if(res.status == 1){
					mui.toast(res.msg);
					localStorage['ddid'] = res.data.id;
					location.href='querendingdan.html';
				}else{mui.toast(res.msg);}
				
				}
			});
		},
		bianji () {
			if(this.bianwan){
				this.bianwan = false;
			}else{
				this.bianwan = true;
			}
		},
		getTotal () {
			
				this.totalPrice = 0
				this.totalNum = 0
				for (var i = 0; i < this.good_list.length; i++) {
					
					var _d = this.good_list[i]
					//console.log(_d);
					if(_d.chosen){
						this.totalPrice += _d['goodsDiscountPrice'] * _d['count']
						this.totalNum +=_d['count']
					}
				}
				
			},
			select_one (index) {
				if(this.good_list[index].chosen == true){
					this.good_list[index].chosen = false
				}else{
					this.good_list[index].chosen = true
				}
				this.getTotal()
			},
			slect_all () {
				if(this.selected_all){
					for (var i = 0; i < this.good_list.length; i++) {
						this.good_list[i].chosen = false;
					}
					this.selected_all = false						
				}else{
					for (var i = 0; i < this.good_list.length; i++) {
						this.good_list[i].chosen = true;
					}
					this.selected_all = true						
				}
				this.getTotal()
			},
			reduce (index) {
				if(this.good_list[index].count <= 1){
					return;
				} 
				this.good_list[index].count --
				this.getTotal()
			},
			add (index) {
				this.good_list[index].count ++
				this.getTotal()
			}
  		
	}
});
