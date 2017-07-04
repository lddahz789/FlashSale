<%@page contentType="text/html; charset=UTF-8" language="java"%>
<%@include file="common/header.jsp"%>

<title>秒杀产品详情-${product.name}</title>
<body>
	<div class="container">
		<div class="panel panel-default text-center">
			<div class="panel-heading">
				<h1>${product.name}</h1>
			</div>
		</div>
		<div class="panel-body text-center">
			<h2>
				<span class="glyphicon glyphicon-time"></span> <span
					class="glyphicon" id="flashsale-box"></span>
			</h2>
		</div>
	</div>


	<!-- 弹出层 -->
	<div id="phoneModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title text-center">
						<span class="glyphicon glyphicon-phone"></span>秒杀电话:
					</h3>
				</div>
			</div>

			<div class="modal-body">
				<div class="row">
					<div class="col-xs-8 col-xs-offset-2">
						<input type="text" name="killPhone" id="phoneKey"
							placeholder="填写手机号码" class="form-control">
					</div>
				</div>
			</div>

			<div class="modal-footer">
				<span id="phoneMessage" class="glyphicon"></span>
				<button type="button" id="phoneSubmitBtn" class="btn btn-success">
					<span class="glyphicon glyphicon-phone"></span> 提交
				</button>
			</div>
		</div>
	</div>

</body>
<script
	src="https://cdn.bootcss.com/jquery.countdown/2.2.0/jquery.countdown.min.js"></script>
<script
	src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/flashSale.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(function(){
		flashSale.detail.init({
				productId : ${product.productId},
				startTime: ${product.startTime.time},
				endTime: ${product.endTime.time}
			});
	});
	
</script>
</html>