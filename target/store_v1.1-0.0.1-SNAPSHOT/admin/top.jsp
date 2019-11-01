<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Language" content="zh-cn">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<style type="text/css">
		body,html {
			margin: 0px;
			padding: 0px;
		}
	
		body {
			background-color: #ffffff
		}
		
		body,td,th {
			font-size: 12px;
			color: #000000
		}
	</style>
	<link href="${pageContext.request.contextPath}/css/Style1.css" rel="stylesheet" type="text/css">
</head>
<body>
	<table border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<img src="${pageContext.request.contextPath}/img/admin/top_01.jpg">
			</td>
			<td>
				<img src="${pageContext.request.contextPath}/img/admin/top_100.jpg">
			</td>
		</tr>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td height="30" valign="bottom" background="${pageContext.request.contextPath}/img/admin/mis_01.jpg">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="85%" align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<font color="#000000">
								<script type="text/javascript">
									var tmpDate = new Date();
									var date = tmpDate.getDate();
									var month = tmpDate.getMonth() + 1 ;
									var year = tmpDate.getFullYear();
									document.write(year);
									document.write("年");
									document.write(month);
									document.write("月");
									document.write(date);
									document.write("日 ");
									
									var myArray = new Array(6);
									myArray[0] = "星期日"
									myArray[1] = "星期一"
									myArray[2] = "星期二"
									myArray[3] = "星期三"
									myArray[4] = "星期四"
									myArray[5] = "星期五"
									myArray[6] = "星期六"
									var weekday = tmpDate.getDay();
									if (weekday == 0 | weekday == 6) {
										document.write(myArray[weekday])
									}
									else {
										document.write(myArray[weekday])
									}
								</script>
							</font>
						</td>
						<td width="15%">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="16"
										background="${pageContext.request.contextPath}/img/admin/mis_05b.jpg">
										<img
											src="${pageContext.request.contextPath}/img/admin/mis_05a.jpg"
											width="6" height="18">
									</td>
									<td width="155" valign="bottom"
										background="${pageContext.request.contextPath}/img/admin/mis_05b.jpg">
										用户名：
										<font color="blue">zzz</font>
									</td>
									<td width="10" align="right"
										background="${pageContext.request.contextPath}/img/admin/mis_05b.jpg">
										<img src="${pageContext.request.contextPath}/img/admin/mis_05c.jpg" width="6" height="18">
									</td>
								</tr>
							</table>
						</td>
						<td align="right" width="5%"></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>