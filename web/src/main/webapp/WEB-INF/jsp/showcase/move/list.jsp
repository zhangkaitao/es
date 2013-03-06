<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
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
      <a id="create" class="btn" href="${ctx}/showcase/move/create">
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
      <a id="reweight" class="btn">
          <span class="icon-cog"></span>
          优化权重
      </a>
  </div>

  <%-- sortPrefix 指定排序参数的前缀（当多个表单/与实际数据冲突时使用）sortURL：排序时使用的URL（默认当前url）   --%>
  <%-- move-url-prefix 指定移动数据的URL前缀   --%>
  <table class="sort-table move-table table table-bordered table-hover table-striped"
          sort-prefix="" sort-url="" move-url-prefix="${ctx}/showcase/move">
      <thead>
        <tr>
            <th style="width: 80px">
                <a class="check-all" href="javascript:;">全选</a>
                |
                <a class="reverse-all" href="javascript:;">反选</a>
            </th>
            <th sort="id">编号</th>
            <th sort="name">姓名</th>
            <th sort="weight">权重</th>
            <th>是否显示</th>
            <th style="width: 50px">操作</th>
        </tr>
      </thead>
      <tbody>
      <c:forEach items="${page.content}" var="m" varStatus="status">
        <tr id="${m.id}">
            <td class="check"><input type="checkbox" name="ids" value="${m.id}"></td>
            <td>${m.id}</td>
            <td>${m.name}</td>
            <td>
                ${m.weight}
                <es:movable page="${page}" first="${status.first}" last="${status.last}"/>
            </td>
            <td>${m.show}</td>
            <td>
                <a class="btn btn-link edit-btn" title="修改" href="${ctx}/showcase/move/update/${m.id}">
                    <span class=" icon-edit"></span>
                </a>
                <a class="btn btn-link edit-btn" title="删除" href="${ctx}/showcase/move/delete/${m.id}">
                    <span class=" icon-trash"></span>
                </a>
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
        $.movable.initMovableReweight($("#reweight"), "${ctx}/showcase/move/reweight");
    });
</script>
