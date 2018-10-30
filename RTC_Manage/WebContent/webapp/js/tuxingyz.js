
//图形验证码

$('.txyz').codeVerify({
	
		type : 1,
		width : '90px',
		height : '40px',
		fontSize : '18px',
		codeLength : 4,
		btnId : 'check-btn',
		ready : function() {
		},
		success : function() {
			yzmzq();
			//alert('验证匹配！');
		},
		error : function() {
			alert('验证码不匹配！');
			return;
		}
	});
