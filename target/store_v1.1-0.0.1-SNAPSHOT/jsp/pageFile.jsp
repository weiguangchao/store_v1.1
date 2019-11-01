<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		$('#skip').click(function() {
			// 获取到最大页数
			var maxPage = ${requestScope.page.maxPage};
			// 获取用户输入框输入的页数
			var skipPage = $('#pageNum').val();
			// 数字正则
			var reg = /^[1-9][0-9]*$/;
			if (!reg.test(skipPage)) { // 不是数字则提示
				alert('输入不合法！');
				return;
			}
			if (parseInt(skipPage) > parseInt(maxPage)) { // 输入过大
				alert('输入过大！');
				return;
			}
			window.location.href = '${pageContext.request.contextPath}/${requestScope.page.url}&page=' + skipPage;
		})
	})
</script>
<!-- 分页开始 -->
<div style="text-align: center">
	共${requestScope.page.maxPage}页&nbsp;/&nbsp;第${requestScope.page.currentPage}页
	<a href="${pageContext.request.contextPath}/${requestScope.page.url}&page=1">首页</a>
	
	<%-- 判断上一页 --%>
	<c:choose>
		<c:when test="${requestScope.page.currentPage==1}">
			<a href="javascript:void(0)">上一页</a>
		</c:when>
		<c:otherwise>
			<a href="${pageContext.request.contextPath}/${requestScope.page.url}&page=${requestScope.page.currentPage-1}">上一页</a>
		</c:otherwise>
	</c:choose>
	
	<%-- 打印中间页 --%>
	<c:forEach begin="1" end="${requestScope.page.maxPage}" var="p" varStatus="status">
		<c:if test="${status.current==requestScope.page.currentPage}">
			<a href="${pageContext.request.contextPath}/${requestScope.page.url}&page=${p}"
				style="color: red">${p}</a>
		</c:if>
		<c:if test="${status.current!=requestScope.page.currentPage}">
			<a href="${pageContext.request.contextPath}/${requestScope.page.url}&page=${p}">${p}</a>
		</c:if>
	</c:forEach>
	
	<%-- 判断下一页 --%>
	<c:choose>
		<c:when test="${requestScope.page.currentPage==requestScope.page.maxPage}">
			<a href="javascript:void(0)">下一页</a>
		</c:when>
		<c:otherwise>
			<a href="${pageContext.request.contextPath}/${requestScope.page.url}&page=${requestScope.page.currentPage+1}">下一页</a>
		</c:otherwise>
	</c:choose>
	
	<a href="${pageContext.request.contextPath}/${requestScope.page.url}&page=${requestScope.page.maxPage}">末页</a>
	<input type="text" id="pageNum" size="1"/>
	<input type="button" value="前往" id="skip"/>
</div>