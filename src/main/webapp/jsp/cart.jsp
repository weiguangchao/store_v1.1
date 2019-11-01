<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html> 
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>XXX网上商城</title>
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" />
		<style>
			body {
				margin-top: 20px;
				margin: 0 auto;
			}
			
			.carousel-inner .item img {
				width: 100%;
				height: 300px;
			}
			
			.container .row div {
				/* position:relative;
	 float:left; */
			}
			
			font {
				color: #3164af;
				font-size: 18px;
				font-weight: normal;
				padding: 0 10px;
			}
		</style>
		<script type="text/javascript">
			$(function() {
				// 从购物车中移除某件商品
				$('.delete').click(function() {
					if (window.confirm('确定要移除该商品？') == true) {
						window.location.href = '${pageContext.request.contextPath}/cart?method=removeCartItemFromCart&pid=' 
								+ $(this).attr('id');
					}
				})
				// 清空购物车
				$('a#clear').click(function() {
					if (window.confirm('确定要清除购物车？') == true) {
						window.location.href = '${pageContext.request.contextPath}/cart?method=removeCart';
					}
				})
				// 提交订单
				$('#submitOrder').click(function() {
					if (window.confirm('提交订单？') == true) {
						window.location.href = '${pageContext.request.contextPath}/order?method=saveOrder';
					}
				})
			})
		</script>
	</head>

	<body>
		<%@ include file="/jsp/header.jsp"%>
		<c:if test="${empty sessionScope.cart}">
			<h3 style="text-align: center;">购物车为空，赶紧消费吧</h3>
		</c:if>
		<c:if test="${not empty sessionScope.cart}">
			<div class="container">
				<div class="row">
					<div style="margin:0 auto; margin-top:10px;width:950px;">
						<strong style="font-size:16px;margin:5px 0;">订单详情</strong>
						<table class="table table-bordered">
							<tbody>
								<tr class="warning">
									<th>图片</th>
									<th>商品</th>
									<th>价格</th>
									<th>数量</th>
									<th>小计</th>
									<th>操作</th>
								</tr>
								<c:forEach items="${sessionScope.cart.cartItems}" var="item">
									<tr class="active">
										<td width="60" width="40%">
											<input type="hidden" name="id" value="22">
											<img src="${pageContext.request.contextPath}/${item.value.product.pimage}" width="70" height="60">
										</td>
										<td width="30%">
											<a target="_blank" href="${pageContext.request.contextPath}/product?method=getProductByPid&pid=${item.value.product.pid}">${item.value.product.pname}</a>
										</td>
										<td width="20%">
											￥${item.value.product.shop_price}
										</td>
										<td width="10%">
											<input type="text" name="quantity" value="${item.value.quantity}" maxlength="4" size="10">
										</td>
										<td width="15%">
											<span class="subtotal">￥${item.value.total}</span>
										</td>
										<td>
											<a href="javascript:void(0)" class="delete" id="${item.value.product.pid}">删除</a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
	
				<div style="margin-right:130px;">
					<div style="text-align:right;">
						<em style="color:#ff6600;">
							<c:if test="${empty sessionScope.user}">
								登录后确认是否享有优惠&nbsp;&nbsp;
							</c:if>
				</em> 赠送积分: <em style="color:#ff6600;">${sessionScope.cart.integral}</em>&nbsp; 商品金额: <strong style="color:#ff6600;">￥${sessionScope.cart.total}元</strong>
					</div>
					<div style="text-align:right;margin-top:10px;margin-bottom:10px;">
						<a href="javascript:void(0)" id="clear" class="clear">清空购物车</a>
						<a href="javascript:void(0)" id="submitOrder">
							<input type="submit" width="100" value="提交订单" name="submit" border="0" style="background: url('${pageContext.request.contextPath}/img/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
							height:35px;width:100px;color:white;">
						</a>
					</div>
				</div>
			</div>
		</c:if>
		<%@ include file="/jsp/footer.jsp"%>
	</body>

</html>