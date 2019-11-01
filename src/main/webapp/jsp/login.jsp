<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>会员登录</title>
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
		color: #666;
		font-size: 22px;
		font-weight: normal;
		padding-right: 17px;
	}
	</style>
	<script type="text/javascript">
		$(function() {
			// 给输入框添加失去焦点事件（可以使鼠标行为，也可以使Tab）
			$('#username').blur(function() {
				// 获取到输入框的内容
				var value = $(this).val();
				if (value == "") { // 输入框为空
					$('#hint').text('*用户名为空');
				} else { // 输入框不为空，数据提交到服务器进行校验
					$.post('${pageContext.request.contextPath}/user?method=checkUsername', 
							{'username': value}, 
							function(data) {
								if (data.checkUsername == 0) {
									$('#hint').text('*用户名不存在');
								} else if (data.checkUsername == -1) {
									$('#hint').text('*用户未激活');
								} else {
									$('#hint').text('');
								}
							}, 
							'json'
					)
				}
			})
		})
	</script>
</head>
<body>
	<%@ include file="/jsp/header.jsp"%>
	<div class="container"
		style="width:100%;height:460px;background:#FF2C4C url('${pageContext.request.contextPath}/img/loginbg.jpg') no-repeat;">
		<div class="row">
			<div class="col-md-7">
				<!--<img src="${pageContext.request.contextPath}/image/login.jpg" width="500" height="330" alt="会员登录" title="会员登录">-->
			</div>

			<div class="col-md-5">
				<div
					style="width: 440px; border: 1px solid #E7E7E7; padding: 20px 0 20px 30px; border-radius: 5px; margin-top: 60px; background: #fff;">
					<font>会员登录</font>USER LOGIN

					<div>
						<font style="color: red;font-size: 13px;">${msg}</font>
					</div>
					<c:remove var="msg"/>
					<form class="form-horizontal"
						action="${pageContext.request.contextPath}/user?method=login"
						method="post">

						<div class="form-group">
							<label for="username" class="col-sm-2 control-label">用户名</label>
							<div class="col-sm-6">
								<input type="text" name="username" class="form-control"
									id="username" placeholder="请输入用户名" value="${sessionScope.username}">
								<%-- 移除username --%>
								<c:remove var="username" scope="session"/>
							</div>
							<span id="hint" style="color:red;"></span>
						</div>
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label">密码</label>
							<div class="col-sm-6">
								<input type="password" name="password" class="form-control"
									id="inputPassword3" placeholder="请输入密码">
							</div>
						</div>
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label">验证码</label>
							<div class="col-sm-3">
								<input type="text" class="form-control" id="inputPassword3"
									placeholder="请输入验证码">
							</div>
							<div class="col-sm-3">
								<img src="${pageContext.request.contextPath}/img/captcha.jhtml" />
							</div>

						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<div class="checkbox">
									<label> <input type="checkbox" name="autoLogin"
										value="1"> 自动登录
									</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <label> <input
										type="checkbox" name="remember" value="1"> 记住用户名
									</label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<input type="submit" width="100" value="登录" name="submit"
									border="0"
									style="background: url('${pageContext.request.contextPath}/img/login.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
    height:35px;width:100px;color:white;">
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/jsp/footer.jsp"%>
</body>
</html>