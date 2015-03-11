<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf" %>
<!DOCTYPE html>
<!--[if lt IE 7 ]><html class="ie ie6" lang="en"> <![endif]-->
<!--[if IE 7 ]><html class="ie ie7" lang="en"> <![endif]-->
<!--[if IE 8 ]><html class="ie ie8" lang="en"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!-->
<html lang="en" xmlns="http://www.w3.org/1999/html"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">

    <meta http-equiv="Cache-Control" content="no-store" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="0" />

    <title>${title}</title>
    <link rel="icon" href="${ctx}/static/images/favicon.ico">
    <link rel="shortcut icon" href="${ctx}/static/images/favicon.ico">
    
    <link href="./static/vendor/bootstrap/css/bootstrap.css" type="text/css" media="screen" rel="stylesheet" />
  
	<!-- <link rel="stylesheet" type="text/css" id="site-theme" href="http://bootswatch.com/paper/bootstrap.min.css" /> -->
  
	<link href="${ctx}/static/vendor/font-awesome/css/font-awesome.css" type="text/css" media="screen" rel="stylesheet" />
	<link href="${ctx}/static/css/xadmin.main.css" type="text/css" media="screen" rel="stylesheet" />
	<link href="${ctx}/static/css/xadmin.plugins.css" type="text/css" media="screen" rel="stylesheet" />
	<link href="${ctx}/static/css/xadmin.responsive.css" type="text/css" media="screen" rel="stylesheet" />
    
    <style type="text/css">
	  #panel-login {
	    max-width: 350px;
	  }
	  #panel-login .controls{
	    padding: 0px 15px 15px !important;
	  }
	
	  #panel-login .alert-block{
	    padding: 0px;
	  }
	  #panel-login .alert-block ul{
	    margin: 10px 15px;
	    padding-left: 0px;
	  }
	  #panel-login .alert-block ul li{
	    list-style-type: none;
	  }
	</style>
	
	<style type="text/css">
	  .form-signin {
	      max-width: 320px;
	    -webkit-border-radius: 10px;
	       -moz-border-radius: 10px;
	            border-radius: 10px;
	    -webkit-box-shadow: 0 0px 40px rgba(0,0,0,.2);
	       -moz-box-shadow: 0 0px 40px rgba(0,0,0,.2);
	            box-shadow: 0 0px 40px rgba(0,0,0,.2);
	  }
	</style>

  	<script type="text/javascript" src="${ctx}/static/vendor/jquery/jquery.js"></script>
  
    <script type="text/javascript">
        var currentURL = "${requestScope.currentURL}";
    </script>
</head>
<body class="login">
  
<div class="container">

  <form id="loginForm" method="post" class="form-horizontal">
    <div class="panel panel-default panel-single" id="panel-login">
    
    <es:BackURL hiddenInput="true"/>
    
    <input type='hidden' name='csrfmiddlewaretoken' value='depy0qAotmsVOdoNJbNKvzmuBljvyVks' />
    
      <div class="panel-heading">
        
	<h2 class="form-signin-heading"><i class="icon-heart"></i> 开启体验之旅</h2>
	<p class="text-success" style="margin-bottom: 20px;">当前测试账号： <b>admin/admin</b></p>
      </div>
      <div class="panel-body">

      <div id="div_id_username" class="row">
        <div class="controls clearfix">
        <div class="input-group input-group-lg">
          <span class="input-group-addon"><i class="icon-user icon-fixed-width"></i></span>
          <input type="text" id="username" name="username" value="${param.username}" maxlength="254"
                            class="form-control input-lg validate[required]" placeholder="请输入用户名、邮箱或手机号">
        </div>
        </div>
      </div>

      <div id="div_id_password" class="row">
        <div class="controls clearfix">
        <div class="input-group input-group-lg">
          <span class="input-group-addon"><i class="icon-lock icon-fixed-width"></i></span>
          <input type="password" id="password" name="password" value="123456"
                            class="form-control input-lg validate[required]" placeholder="请输入密码">
        </div>
        
        </div>
      </div>
      
		<div class="row">
			<div class="controls clearfix">
	        	<div class="input-group">
	        		<label class="checkbox remember"><input type="checkbox" name="rememberMe" value="true">下次自动登录</label>
	        	</div>
	     	</div>
     	</div>
            
      <button class="btn btn-lg btn-primary btn-block" id="submitForm" type="submit">登录</button>

      <div class="text-info" style="margin-top:15px;"><a href="/xadmin/password_reset/"><i class="icon-question-sign"></i> 忘记了您的密码或用户名？</a></div>
      </div>
    
    </div>
  </form>

</div> <!-- /container -->
		<script src="${ctx}/static/js/application.js?11" type="text/javascript"></script>
		<script type="text/javascript">
			window.jQuery || document.write("<script src='${ctx}/static/vendor/jquery/jquery.min.js'>"+"<"+"script>");
		</script>
		<script src="${ctx}/static/vendor/bootstrap/js/bootstrap.js"></script>
		
		<script src="${ctx}/static/comp/jQuery-Validation-Engine/js/jquery.validationEngine.js?1" charset="utf-8" type="text/javascript"></script>
		<script src="${ctx}/static/comp/jQuery-Validation-Engine/js/languages/jquery.validationEngine-zh_CN.js?1" charset="utf-8" type="text/javascript"></script>
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
</body>
</html>
