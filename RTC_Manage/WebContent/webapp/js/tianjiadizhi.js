var dz = new Vue({
	el: '#example',
	data: {
		dzid:'',
		sjr:'',
		pone:'',
		diq:'',
		xxdz:'',
		moren:''
	},
	created:function(){
		var ddzx = localStorage['dzxx'];
		if(ddzx){
			var dz = JSON.parse(ddzx);
			this.dzid = dz.id;
		this.sjr = dz.addressee;
		this.pone = dz.contactNo;
		this.diq = dz.receivingAddress;
		this.xxdz = dz.whichLocated;
		if(dz.state ==1){
			$('.mui-switch').addClass('mui-active');
            this.moren = true;
		}else{
			this.moren = false;
		}
		
		}
	},
	methods:{
		bcdz (){
			var that = this;
			console.log($('.mui-switch').hasClass('mui-active'));
			 that.moren = $('.mui-switch').hasClass('mui-active');
			var data = {
				sessionId:sessionId,
				id:this.dzid,
				receivingAddress:this.diq,
				addressee:this.sjr,
				contactNo:this.pone,
				whichLocated:this.xxdz,
				judge:this.moren
			};
			console.log(data);
			//$.post(base+'/app/address/saveAddress.do',data,function(res){
				ajax({
				type:'post',
				url:'/app/address/saveAddress.do',
				data:data,
				success:function(res){
				console.log(res);
				if(res.status == 1){
					mui.toast(res.msg);
					self.location=document.referrer;
				}else{
					mui.toast(res.msg);
				}
				}
			})
		},
		scdz () {
			var data = {
				sessionId:sessionId,
				id:this.dzid
			};
			console.log(data);
			//$.post(base+'/app/address/delAddress.do',data,function(res){
				ajax({
				type:'post',
				url:'/app/address/delAddress.do',
				data:data,
				success:function(res){
				console.log(res);
				if(res.status == 1){
					mui.toast(res.msg);
					self.location=document.referrer;
				}else{
					mui.toast(res.msg);
				}
				}
			})
		}
	},
	beforeDestory (){
		console.log(1);
	},
});