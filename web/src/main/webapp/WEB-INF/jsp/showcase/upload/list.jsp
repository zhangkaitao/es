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
      <a id="create" class="btn" href="${ctx}/showcase/upload/create">
          <span class="icon-file"></span>
          新增
      </a>
      <a id="ajaxCreate" class="btn" href="${ctx}/showcase/upload/ajax/create">
          <span class="icon-file"></span>
          ajax新增
      </a>
      <a id="ajaxBatchCreate" class="btn" href="${ctx}/ajaxUpload">
          <span class="icon-file"></span>
          ajax批量上传
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
  <table class="sort-table table table-bordered table-hover table-striped" >
      <thead>
        <tr>
            <th style="width: 80px">
                <a class="check-all" href="javascript:;">全选</a>
                |
                <a class="reverse-all" href="javascript:;">反选</a>
            </th>
            <th sort="id">编号</th>
            <th sort="name">名称</th>
            <th>附件</th>
            <th style="width: 50px">操作</th>
        </tr>
      </thead>
      <tbody>
      <c:forEach items="${page.content}" var="m">
        <tr>
            <td class="check"><input type="checkbox" name="ids" value="${m.id}"/></td>
            <td>${m.id}</td>
            <td>${m.name}</td>
            <td><es:showAttachment filename="${m.src}" showImage="true" width="auto" height="160"/></td>
            <td>
                <a class="btn btn-link edit-btn" title="修改" href="${ctx}/showcase/upload/update/${m.id}">
                    <span class="icon-edit"></span>
                </a>
                <a class="btn btn-link edit-btn" title="删除" href="${ctx}/showcase/upload/delete/${m.id}">
                    <span class="icon-trash"></span>
                </a>
            </td>
        </tr>
      </c:forEach>
      </tbody>
  </table>
  <es:page page="${page}" />
</div>
<es:contentFooter/>
