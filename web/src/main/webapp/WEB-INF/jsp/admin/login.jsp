<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf" %>
<es:contentHeader title="管理员登录 Easy-Scaffold脚手架"/>
<div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
        <a class="brand" href="#">&nbsp;&nbsp;es脚手架</a>
        <ul class="nav">
            <li><a href="http://sishuok.com" target="_blank">私塾在线</a></li>
            <li class="active"><a href="#">登录</a></li>
            <li><a href="http://jinnianshilongnian.iteye.com" target="_blank">我的博客</a></li>
            <li><a href="https://github.com/zhangkaitao/es" target="_blank">github</a></li>
        </ul>
        <a class="brand" style="float: right" href="mailto:zhangkaitao0503@gmail.com" target="_blank">&nbsp;&nbsp;问题反馈</a>
    </div>
</div>

<div class="container">
    <div class="login">
        <div class="title">用户登录</div>
        <div class="form">
        <form method="post" class="form-horizontal">
            <div class="control-group">
                <label for="username">用户名、邮箱或手机号</label>
                <div class="input-prepend">
                    <span class="add-on icon-user"></span>
                    <input type="text" id="username" name="username" class="input-xlarge" placeholder="请输入用户名、邮箱或手机号">
                </div>
            </div>
            <div class="control-group">
                <label for="password">密码</label>
                <div class="input-prepend">
                    <span class="add-on icon-key"></span>
                    <input type="password" id="password" name="password" class="input-xlarge" placeholder="请输入密码">
                </div>
            </div>

            <div class="control-group">
                <label class="checkbox remember"><input type="checkbox">下次自动登录</label>
                <input id="submit" type="submit" class="btn pull-left" value="登录">
            </div>

        </form>
        </div>
    </div>
</div>

<footer class="footer">
    <div class="container">
        <p><a href="http://sishuok.com" target="_blank">私塾在线学习网</a></p>
        <p>Code licensed <a href="http://www.apache.org/licenses/LICENSE-2.0" target="_blank">Apache License v2.0</a>, documentation under <a href="http://creativecommons.org/licenses/by/3.0/">CC BY 3.0</a>.</p>
        <ul class="footer-links">
            <li><a href="http://jinnianshilongnian.iteye.com">博客</a></li>
            <li class="muted">·</li>
            <li><a href="https://github.com/zhangkaitao/es/issues?state=open" target="_blank">问题反馈</a></li>
            <li class="muted">·</li>
            <li><a href="https://github.com/zhangkaitao/es" target="_blank">项目主页</a></li>
        </ul>
    </div>
</footer>
<es:contentFooter/>