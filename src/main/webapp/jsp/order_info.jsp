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
	</style>
	<script type="text/javascript">
		$(function() {
			$('#payOrder').click(function() {
				if ($('input[name="address"]').val() == "") {
					alert('请输入收货人地址');
					return;
				}
				if ($('input[name="name"]').val() == "") {
					alert('请输入收货人姓名');
					return;
				}
				var tel = $('input[name="telephone"]').val();
				if (tel == "") {
					alert('请输入收货人电话');
					return;
				}
				var isPhone = /^([0-9]{3,4}-)?[0-9]{7,8}$/; // 手机号码
			    var isMob= /^0?1[3|4|5|8][0-9]\d{8}$/; // 座机格式
			    if(!isMob.test(tel) && !isPhone.test(tel)){ // 电话格式不正确
			    	alert('请输入正确电话格式');
			    	return;
			    }
			    var pay_met = $('input[name="pay_met"]');
			    if (pay_met[1].checked == true) { // 目前不支持微信支付
			    	alert('暂不支持微信支付，请谅解');
			    	return;
			    }
			    
			    // 满足条件提交表单
			    $('#addresseeMessage').submit();
			})
		})
	</script>
</head>

	<body>
		<%@ include file="/jsp/header.jsp"%>
		<div class="container">
			<div class="row">

				<div style="margin:0 auto;margin-top:10px;width:950px;">
					<strong>订单详情</strong>
					<table class="table table-bordered">
						<tbody>
							<tr class="warning">
								<th colspan="5">
									订单编号:&nbsp;${requestScope.order.oid}&nbsp;&nbsp;
								</th>
							</tr>
							<tr class="warning">
								<th>图片</th>
								<th>商品</th>
								<th>价格</th>
								<th>数量</th>
								<th>小计</th>
							</tr>
							<c:forEach items="${requestScope.order.list}" var="item">
								<tr class="active">
									<td width="60" width="40%">
										<img src="${pageContext.request.contextPath}/${item.product.pimage}" width="70" height="60">
									</td>
									<td width="30%">
										<a href="${pageContext.request.contextPath}/product?method=getProductByPid&pid=${item.product.pid}"> ${item.product.pname}</a>
									</td>
									<td width="20%">
										￥${item.product.shop_price}
									</td>
									<td width="10%">
										${item.quantity}
									</td>
									<td width="15%">
										<span class="subtotal">￥${item.total}</span>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

				<div style="text-align:right;margin-right:120px;">
					商品金额: <strong style="color:#ff6600;">￥${requestScope.order.total}元</strong>
				</div>

			</div>

			<div>
				<hr/>
				<form id="addresseeMessage" action="${pageContext.request.contextPath}/order?method=payOrder" method="post" class="form-horizontal" style="margin-top:5px;margin-left:150px;">
					<input name="oid" value="${requestScope.order.oid}" type="hidden"/>
					<div class="form-group">
						<label for="username" class="col-sm-2 control-label">地址</label>
						<div class="col-sm-5">
							<input name="address" type="text" class="form-control" id="username" placeholder="请输入收货地址">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-sm-2 control-label">收货人</label>
						<div class="col-sm-5">
							<input name="name" type="password" class="form-control" id="inputPassword3" placeholder="请输收货人">
						</div>
					</div>
					<div class="form-group">
						<label for="confirmpwd" class="col-sm-2 control-label">电话</label>
						<div class="col-sm-5">
							<input name="telephone" type="text" class="form-control" id="confirmpwd" placeholder="请输入联系方式">
						</div>
					</div>
					
					<div class="form-group">
						<label for="confirmpwd" class="col-sm-2 control-label">支付方式</label>
						<div class="col-sm-5">
							<input type="radio" name="pay_met" value="0" checked="checked" />支付宝
							<img src="${pageContext.request.contextPath}/img/pay_img/alipay.png" align="middle"/>&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="pay_met" value="1"/>微信
							<img src="${pageContext.request.contextPath}/img/pay_img/wechat.png" align="middle"/>&nbsp;&nbsp;&nbsp;&nbsp;
						</div>
					</div>
				
					<hr/>
					<div style="margin-top:5px;margin-left:150px;">
						<p style="text-align:right;margin-right:100px;">
							<a href="javascript:void(0)" id="payOrder">
								<img src="${pageContext.request.contextPath}/img/finalbutton.gif" width="204" height="51" border="0" />
							</a>
						</p>
					</div>
				</form>
			</div>
		</div>
		<%@ include file="/jsp/footer.jsp"%>
	</body>

</html>