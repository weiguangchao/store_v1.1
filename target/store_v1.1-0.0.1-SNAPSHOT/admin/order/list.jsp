<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Language" content="zh-cn">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="${pageContext.request.contextPath}/css/Style1.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="${pageContext.request.contextPath}/js/public.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
		<script type="text/javascript">
			$(function() {
				$('.btn_detail').click(function() {
					// 保存当前对象
					var $btn = $(this);
					var $tb = $btn.next();
					if ($btn.val() == '订单详情') { // 订单详情
						var url = '${pageContext.request.contextPath}/adminOrder';
						var param = {'method': 'findOrderByOid', 'oid': $($btn).attr('id')};
						$.post(url, param, function(data) {
							var th = '<tr><td>商品</td><td>名称</td><td>单价</td><td>数量</td></tr>';
							
							$tb.append(th);
							
							// 循环遍历json数据
							$.each(data, function(i, obj) {
								var tr = '<tr><td><img src="${pageContext.request.contextPath}/'+obj.product.pimage+'" width="60px" height="60px"></td><td>'+obj.product.pname+'</td><td>'+obj.product.shop_price+'</td><td>'+obj.quantity+'</td></tr>'
								$tb.append(tr);
							})
						}, 'json')
						
						
						$btn.val('关闭');
					} else { // 关闭
						$tb.html('');
						$btn.val('订单详情');
					}
				})
			})
		</script>
	</HEAD>
	<body>
		<br>
		<form id="Form1" name="Form1" action="#" method="post">
			<table cellSpacing="1" cellPadding="0" width="100%" align="center" bgColor="#f5fafe" border="0">
				<TBODY>
					<tr>
						<td class="ta_01" align="center" bgColor="#afd1f3">
							<strong>订单列表</strong>
						</TD>
					</tr>
					
					<tr>
						<td class="ta_01" align="center" bgColor="#f5fafe">
							<table cellspacing="0" cellpadding="1" rules="all"
								bordercolor="gray" border="1" id="DataGrid1"
								style="BORDER-RIGHT: gray 1px solid; BORDER-TOP: gray 1px solid; BORDER-LEFT: gray 1px solid; WIDTH: 100%; WORD-BREAK: break-all; BORDER-BOTTOM: gray 1px solid; BORDER-COLLAPSE: collapse; BACKGROUND-COLOR: #f5fafe; WORD-WRAP: break-word">
								<tr style="FONT-WEIGHT: bold; FONT-SIZE: 12pt; HEIGHT: 25px; BACKGROUND-COLOR: #afd1f3">

									<td align="center" width="10%">
										序号
									</td>
									<td align="center" width="10%">
										订单编号
									</td>
									<td align="center" width="10%">
										订单金额
									</td>
									<td align="center" width="10%">
										收货人
									</td>
									<td align="center" width="10%">
										订单状态
									</td>
									<td align="center" width="50%">
										订单详情
									</td>
								</tr>
									<c:forEach items="${page.list}" var="order" varStatus="status">
										<tr onmouseover="this.style.backgroundColor = 'white'"
											onmouseout="this.style.backgroundColor = '#F5FAFE';">
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="5%">
												${status.count}
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="17%">
												${order.oid}
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="5%">
												${order.total}
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="5%">
												${order.name}
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="5%">
												<c:if test="${order.state==1}">未付款</c:if>
												<c:if test="${order.state==2}">
													<a href="${pageContext.request.contextPath}/adminOrder?method=updateOrderState&oid=${order.oid}">发货</a>
												</c:if>
												<c:if test="${order.state==3}">已发货</c:if>
												<c:if test="${order.state==4}">订单完成</c:if>
											</td>
											<td align="center" style="HEIGHT: 22px">
												<input type="button" value="订单详情" id="${order.oid}" class="btn_detail"/>
												<table id="orderItems" style="border-spacing: 0" border="1">
													
													
												</table>
											</td>
							
										</tr>
								</c:forEach>
							</table>
						</td>
					</tr>
					<tr align="center">
						<td colspan="7">
							<%@ include file="/jsp/pageFile.jsp"%>
						</td>
					</tr>
				</TBODY>
			</table>
		</form>
	</body>
</HTML>