<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<div class="lead header">
  <div class="pull-left" style="margin: 10px 0 0 20px;width: 680px;">
      <img src="${ctx}/static/images/favicon.png"/>
      &nbsp;&nbsp;Easy-Scaffold项目开发脚手架——基于主流Java开源技术构建
  </div>
  <div class="pull-right" style="margin: 15px 20px 0 0;">
      <div class="userinfo">
          <shiro:user>
              欢迎：<es:username/>！<a href="${ctx}/logout">退出</a>
          </shiro:user>
      </div>
  </div>

</div>
