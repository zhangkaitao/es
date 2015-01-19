<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf" %>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">

    <meta http-equiv="Cache-Control" content="no-store" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="0" />

    <title>es-shop 后台管理</title>
    <link rel="icon" href="${ctx}/static/images/favicon.ico">
    <link rel="shortcut icon" href="${ctx}/static/images/favicon.ico">
    <%@include file="/WEB-INF/jsp/common/import-css.jspf"%>
    <script type="text/javascript">
        var currentURL = "${requestScope.currentURL}";
    </script>
</head>
<body class="login-layout">
	<div class="main-container">
			<div class="main-content">
				<div class="row">
					<div class="col-sm-10 col-sm-offset-1">
						<div class="login-container">
							<div class="center">
								<h1>
									<i class="icon-leaf green"></i>
									<span class="red">ES-SHOP</span>
									<span class="white">后台登陆</span>
								</h1>
								<h4 class="blue">&copy; 小小士工作室</h4>
							</div>

							<div class="space-6"></div>

							<div class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header blue lighter bigger">
												<i class="icon-coffee green"></i>
												登陆信息
											</h4>

											<div class="space-6"></div>
								 			<div style="margin-right: 30px;">
								                <es:showMessage></es:showMessage>
								            </div>
											<form id="loginForm" method="post" class="form-horizontal">
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" id="username" name="username" value="${param.username}" class="form-control input-xlarge validate[required]" placeholder="用户名、邮箱或手机号" />
															<i class="icon-user"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" id="password" name="password" class="form-control input-xlarge validate[required]" placeholder="请输入密码" />
															<i class="icon-lock"></i>
														</span>
													</label>
													
													
													<%-- jcaptchaEbabled 在JCaptchaValidateFilter设置 --%>
										            <!-- <c:if test="${jcaptchaEbabled}"> -->
										                <div class="control-group">
										                    <label for="jcaptchaCode">验证码</label>
										                    <div class="input-prepend">
										                        <span class="add-on icon-circle-blank"></span>
										                        <input type="text" id="jcaptchaCode" name="jcaptchaCode"
										                                class="input-medium validate[required,ajax[ajaxJcaptchaCall]]" placeholder="请输入验证码">
										                    </div>
										                     <img class="jcaptcha-btn jcaptcha-img" style="margin-left: 10px;" src="${ctx}/jcaptcha.jpg" title="点击更换验证码">
										                     <a class="jcaptcha-btn btn btn-link">换一张</a>
										                </div>
										            <!-- </c:if> -->

													<div class="space"></div>
													<div class="clearfix">
														<label class="inline">
															<input type="checkbox" class="ace" name="rememberMe" value="true" />
															<span class="lbl"> 下次自动登录</span>
														</label>

														<button id="submitForm" type="submit" class="width-35 pull-right btn btn-sm btn-primary">
															<i class="icon-key"></i>
															登陆
														</button>
													</div>

													<div class="space-4"></div>
												</fieldset>
											</form>
										</div><!-- /widget-main -->
									</div><!-- /widget-body -->
								</div><!-- /login-box -->
							</div><!-- /position-relative -->
						</div>
					</div><!-- /.col -->
				</div><!-- /.row -->
			</div>
		</div><!-- /.main-container -->
		
		
		
<div class="container">
    <div class="login">
        <div class="title">用户登录</div>
        <div class="form">
            <div style="margin-right: 30px;">
                <es:showMessage></es:showMessage>
            </div>
            <form id="loginForm" method="post" class="form-horizontal">
            <es:BackURL hiddenInput="true"/>
            <div class="control-group">
                <label for="username">用户名、邮箱或手机号</label>
                <div class="input-prepend">
                    <span class="add-on icon-user"></span>
                    <input type="text" id="username" name="username" value="${param.username}"
                            class="input-xlarge validate[required]" placeholder="请输入用户名、邮箱或手机号">
                </div>
            </div>
            <div class="control-group">
                <label for="password">密码</label>
                <div class="input-prepend">
                    <span class="add-on icon-key"></span>
                    <input type="password" id="password" name="password"
                            class="input-xlarge validate[required]" placeholder="请输入密码">
                </div>
            </div>
            <%-- jcaptchaEbabled 在JCaptchaValidateFilter设置 --%>
            <c:if test="${jcaptchaEbabled}">
                <div class="control-group">
                    <label for="jcaptchaCode">验证码</label>
                    <div class="input-prepend">
                        <span class="add-on icon-circle-blank"></span>
                        <input type="text" id="jcaptchaCode" name="jcaptchaCode"
                                class="input-medium validate[required,ajax[ajaxJcaptchaCall]]" placeholder="请输入验证码">
                    </div>
                     <img class="jcaptcha-btn jcaptcha-img" style="margin-left: 10px;" src="${ctx}/jcaptcha.jpg" title="点击更换验证码">
                     <a class="jcaptcha-btn btn btn-link">换一张</a>
                </div>
            </c:if>

            <div class="control-group">
                <label class="checkbox remember"><input type="checkbox" name="rememberMe" value="true">下次自动登录</label>
                <input id="submitForm" type="submit" class="btn btn-login pull-left" value="登录">
            </div>

        </form>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    $(function() {
        $("#username").focus();
        $(".jcaptcha-btn").click(function() {
            var img = $(".jcaptcha-img");
            var imageSrc = img.attr("src");
            if(imageSrc.indexOf("?") > 0) {
                imageSrc = imageSrc.substr(0, imageSrc.indexOf("?"));
            }
            imageSrc = imageSrc + "?" + new Date().getTime();
            img.attr("src", imageSrc);
        });
        $.validationEngineLanguage.allRules.ajaxJcaptchaCall={
            "url": "${ctx}/jcaptcha-validate",
            "alertTextLoad": "* 正在验证，请稍等。。。"
        };
        $("#loginForm").validationEngine({scroll:false});
    });
</script>