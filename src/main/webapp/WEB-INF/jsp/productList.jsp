<%@page contentType="text/html; charset=UTF-8" language="java"%>
<%@include file="common/header.jsp"%>

<title>秒杀产品列表</title>
<body>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading text-center">
				<h1>秒杀商品列表</h1>
			</div>
			<div class="panel-body">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>商品名称</th>
							<th>库存</th>
							<th>开始时间</th>
							<th>结束时间</th>
							<th>详情</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="p" items="${productList}">
							<tr>
								<td>${p.name}</td>
								<td>${p.stock}</td>
								<td><fmt:formatDate value="${p.startTime}"
										pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td><fmt:formatDate value="${p.endTime}"
										pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td><a class="btn btn-info" href="<%=request.getContextPath()%>/flashsale/${p.productId}/detail" target="_blank">详情</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>