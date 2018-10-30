var sid = localStorage['sessionId'];






new Vue({
	el: '#example',
	data: {
		butlists: false, //筛选
		msg:[],
		goodsName:"",//搜索关键字
		screenType:'', //筛选 
		page:1,//页码
		goodsType:'',//分类数据
		isjzsj:true,
	},
	created:function(){
		var that = this;
		that.qingqsj();
	},
	mounted:function(){
	},
	methods: {
		qingqsj:function(){
			var that = this;
//			console.log(that.page);
			var data = {
				sessionId:sessionId,
				goodsName:this.goodsName,//搜索关键字
				screenType:this.screenType, //筛选 
				page:this.page,//页码
				goodsType:this.goodsType  //分类数据
			};
			ajax({
				type:"get",
				url:"/app/index/getIndexPic.do",
				data:{
					"sessionId":sid
				},
				success:function(res){
					console.log(res.data[0]);
					if(res.status==1){
						var zh = res.data.length-1;
						var h = '';
						h+=`<div class="mui-slider-item mui-slider-item-duplicate">
							<a href="#">
								<img src="${res.data[zh]}" />
							</a>
						</div>`;
						$.each(res.data, function(k,v) {
							h+=`
							<div class="mui-slider-item">
								<a href="#">
									<img src="${v}" />
								</a>
							</div>
			
							`;
						});
						h+=`
						<div class="mui-slider-item mui-slider-item-duplicate">
							<a href="#">
								<img src="${res.data[0]}" />
							</a>
						</div>
						`;
						$('.mui-slider-group').html(h);
					}
				}
			});
			ajax({
				type:'get',
				timeout : 1000, //超时时间设置，单位毫秒
				url:'/app/goods/listPageShow.do',
				data:data,
				success:function(res){
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
				},
				complete: function (XMLHttpRequest, status) { //当请求完成时调用函数
			        if (status == 'timeout') {//status == 'timeout'意为超时,status的可能取值：success,notmodified,nocontent,error,timeout,abort,parsererror 
			          ajaxTimeOut.abort(); //取消请求
			          mui.toast('网络请求超时，请刷新页面');
			        }
			     }
			});
		},
		tmpgwc:function(id){
			var data = {
				sessionId:sessionId,
				goodsIdStr:id,
				goodsCountStr:'1'
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
					location='querendingdan.html';
					}else{mui.toast(res.msg);}
				}
			});
		},
		/*cankaoshuju:function(){
			console.log(2);
			var that = this;
			for(var i=0; i<2; i++){
		                that.msg.push({
		                	id:1,
		                	goodsName:'阿斯兰的法律疯掉了几十个大立科技奥拉夫到家了卡士大夫就离开',
							goodsDiscountPrice:450,
							goodsAttachmentContent :'images/shangcheng/chanpin.png'
		                });  
	              	}
		},*/
  		sxbtn:function(){ //点筛选
  			this.butlists = true;
  		},
  		qxsx:function(){//取消筛选
  			this.butlists = false;
  		},
  		sousuoinp:function(){  //搜索
  			var that = this;
    			console.log(this.goodsName);
  			this.page = 1;
  			this.msg = [];
			this.qingqsj();
//			setTimeout(function(){
				//console.log(1);
//				that.cankaoshuju();
//			 },800);
			//console.log(this.msg);
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

$('#lists').on('tap','[data-spid]',function(){
	var id = $(this).attr('data-spid');
	console.log(id);
	localStorage['spid'] = id;
	location.href = 'shangpinxiangqing.html';
});