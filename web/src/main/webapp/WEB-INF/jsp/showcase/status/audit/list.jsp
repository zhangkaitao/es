<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>

<ul class="nav nav-pills">
    <li <c:if test="${empty param['search.status_eq'] and empty param['search.status_isNull']}">class="active"</c:if>>
        <a href="${ctx}/showcase/status/audit">所有数据列表</a>
    </li>
    <li <c:if test="${not empty param['search.status_isNull']}">class="active"</c:if>>
        <a href="${ctx}/showcase/status/audit?search.status_isNull=null">等待审核列表</a>
    </li>
    <li <c:if test="${param['search.status_eq'] eq 'fail'}">class="active"</c:if>>
        <a href="${ctx}/showcase/status/audit?search.status_eq=fail">审核拒绝列表</a>
    </li>
    <li <c:if test="${param['search.status_eq'] eq 'success'}">class="active"</c:if>>
        <a href="${ctx}/showcase/status/audit?search.status_eq=success">审核通过列表</a>
    </li>
</ul>


<es:showMessage/>
<div id="search" class="accordion-body collapse search">
    <div class="accordion-inner">
        <%@include file="searchForm.jsp"%>
    </div>
</div>

<div>
  <div class="btn-group">
      <a class="btn accordion-toggle" data-toggle="collapse" href="#search">
          <span class="icon-search"></span>
          查询
      </a>
      <a id="create" class="btn" href="${ctx}/showcase/status/audit/create">
          <span class="icon-file"></span>
          新增
      </a>
      <a id="update" class="btn">
          <span class="icon-edit"></span>
          修改
      </a>
      <a id="removeSelect" class="btn">
          <span class="icon-trash"></span>
          批量删除
      </a>
  </div>
  <%-- sortPrefix 指定排序参数的前缀（当多个表单/与实际数据冲突时使用）sortURL：排序时使用的URL（默认当前url）   --%>
  <table class="sort-table table table-bordered table-hover table-striped" style="margin-right: 50px">
      <thead>
        <tr>
            <th style="width: 80px;">
                <a class="check-all" href="javascript:;">全选</a>
                |
                <a class="reverse-all" href="javascript:;">反选</a>
            </th>
            <th sort="id">编号</th>
            <th sort="name">名称</th>
            <th>状态</th>
            <th>备注</th>
            <th style="width: 50px">操作</th>
        </tr>
      <tbody>
      <c:forEach items="${page.content}" var="m">
        <tr>
            <td class="check"><input type="checkbox" name="ids" value="${m.id}"></td>
            <td>
                <a href="${ctx}/showcase/status/audit/${m.id}">${m.id}</a>
            </td>
            <td>${m.name}</td>
            <td>${m.status.info}</td>
            <td>${m.comment}</td>
            <td>
                <a class="btn btn-link edit-btn" title="修改" href="${ctx}/showcase/status/audit/update/${m.id}">
                    <span class=" icon-edit"></span>
                </a>
                <a class="btn btn-link edit-btn" title="删除" href="${ctx}/showcase/status/audit/delete/${m.id}">
                    <span class=" icon-trash"></span>
                </a>
                <br/>
                <c:if test="${empty m.status}">
                <div class="btn-group">
                    <a class="btn btn-link btn-mini dropdown-toggle margin-1" data-toggle="dropdown" href="#">
                        审核
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu left">
                        <li>
                            <a id="auditSuccess" href="javscript:;" data-url="${ctx}/showcase/status/audit/${m.id}/success">通过</a>
                        </li>
                        <li>
                            <a id="auditFail" href="javscript:;" data-url="${ctx}/showcase/status/audit/${m.id}/fail">拒绝</a>
                        </li>
                    </ul>
                </div>
                </c:if>
            </td>
        </tr>
      </c:forEach>
      </tbody>
  </table>
  <es:page page="${page}" />
</div>
<es:contentFooter/>
<script type="text/javascript">
    $(function() {
        $("#auditSuccess,#auditFail").click(function() {
            var url = $(this).attr("data-url");
            $.app.confirm({
                message : "请输入审核结果：<br/><textarea id='comment' style='width: 300px;height: 50px;'></textarea>",
                ok : function() {
                    var comment = $("#comment", top.document).val();
                    window.location.href = url + "?comment=" + comment;
                }
            });

        });
    });
</script>
