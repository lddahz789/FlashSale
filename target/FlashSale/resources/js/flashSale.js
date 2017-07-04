//存放交互逻辑
var flashSale = {
		
	// 封装秒杀相关的URL
	URL : {
		now: function () {
	           return "/miaosha/flashsale/time/now";
	       },
	       exposer: function (productId) {
	           return "/miaosha/flashsale/" + productId + "/exposure";
	       },
	       execution: function (productId, md5) {
	           return "/miaosha/flashsale/" + productId + "/" + md5 + "/execution";
	       }
	},
	// 处理秒杀逻辑
	handleSale: function(productId,node){
		// 处理秒杀逻辑
		node.hide()
			.html('<button class="btn btn-primary btn-lg" id="saleBtn">开始秒杀</button>');
		$.post(flashSale.URL.exposer(productId),{},function (result){
			
			// 回调函数逻辑
			if(result && result['succeeded']){
				var exposure = result['data'];
				if(exposure['exposed']){
					// 秒杀开启
					var md5 = exposure['md5'];
					var saleUrl = flashSale.URL.execution(productId,md5);
					console.log(saleUrl);
					// 绑定一次点击事件
					$('#saleBtn').one('click',function(){
						// 提交秒杀请求
						$(this).addClass('disabled');
						// 发送请求到服务器
						$.post(flashSale.URL.execution(productId,md5),{},function(result){
							if(result && result['succeeded']){
								var saleResult = result['data'];
								var statusInfo = saleResult['statusInfo'];
								// 显示秒杀结果
								node.html('<span class="text-success">' +　statusInfo + '</span>');
							}else{
								//未检测到手机号
								node.html('<span class="text-danger">非法用户,未注册!</span>');
							}
						});
					});
					node.show();
				}else {
					// 未开启秒杀
					console.log("秒杀未开启");
					var now = exposure['now'];
					var end = exposure['end'];
					var start = exposure['start'];
					// 重新进入计时逻辑
					flashSale.countdown(productId,now,start,end);
				}
			}else{
				console.log(result);
				console.log("服务器查询失败");
			}
		});
	},
	// 验证手机
	validatePhone: function (phone){
		if(phone && phone.length == 11 && !isNaN(phone)){
			return true;
		}else{
			return false;
		}
	},
	// 计时器逻辑
	countdown : function(productId,nowTime,startTime,endTime){
		var flashSaleBox = $('#flashsale-box');
		
		// 时间判断
		if(nowTime > endTime){
			// 秒杀结束
			flashSaleBox.html('秒杀结束!');
		}else if (nowTime < startTime){
			// 秒杀未开始,进入倒计时逻辑
			var saleTime = new Date(startTime-0 + 1000);
			flashSaleBox.countdown(saleTime,function(event){
				var format = event.strftime('秒杀倒计时: %D天 %H时 %M分 %S秒');
				flashSaleBox.html(format);
				// 时间完成后回调事件
			}).on('finish.countdown',function(){
				// 获取秒杀地址按钮
				flashSale.handleSale(productId,flashSaleBox);
			});
		}else{
			// 秒杀进行中TODO
			flashSale.handleSale(productId,flashSaleBox);
		}
	},
	// 详情页秒杀逻辑
	detail :{
		// 详情页初始化
		init: function (params){
			// 手机验证登录和计时
			// 规划交互流程
			// 在cookie中查找手机号
			var phone = $.cookie('phone');

			if (!flashSale.validatePhone(phone)){
				// 绑定手机号
				$('#phoneModal').modal({
					show : true,
					backdrop: false,
					keyboard: false
				});
				$('#phoneSubmitBtn').click(function (){
					var inputPhone = $('#phoneKey').val();
					if(flashSale.validatePhone(inputPhone)){
						$.cookie('phone',inputPhone,{expires: 7});
						// 刷新页面
						window.location.reload();
					}else{
						$('#phoneMessage').hide().html('<label class="label label-danger">手机号格式错误!</label>').show(300);
					}
				});
				
			}
			// 已经登录
			// 计时交互
			var productId = params['productId'];
			var startTime = params['startTime'];
			var endTime = params['endTime'];
			$.get(flashSale.URL.now(),{},function(result){
				if(result && result['succeeded']){
					var nowTime = result['data'];
					// 做时间判断
					flashSale.countdown(productId,nowTime,startTime,endTime);
				}else{
					console.log(result);
				}
			});
		}
		
	}
}