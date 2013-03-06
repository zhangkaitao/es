<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<es:showMessage/>
<c:set var="categoryId" value="${empty category ? 0 : category.id}"></c:set>
<fieldset>
    <legend class="no-margin">
        <c:if test="${not empty category}">[类别:<a href="${ctx}/showcase/product/product/category-${category.id}">${category.name}</a>] </c:if>产品列表
    </legend>
</fieldset>
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
      <a id="create" class="btn" href="${ctx}/showcase/product/product/category-${categoryId}/create">
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
      <a id="listAllProduct" class="btn" href="${ctx}/showcase/product/product">
          <span class="icon-list"></span>
          查看所有产品
      </a>
  </div>

  <%-- sortPrefix 指定排序参数的前缀（当多个表单/与实际数据冲突时使用）sortURL：排序时使用的URL（默认当前url）   --%>
  <%-- product/product-url-prefix 指定移动数据的URL前缀   --%>
  <table class="sort-table table table-bordered table-hover table-striped"
          sort-prefix="" sort-url="">
      <thead>
        <tr>
            <th style="width: 80px">
                <a class="check-all" href="javascript:;">全选</a>
                |
                <a class="reverse-all" href="javascript:;">反选</a>
            </th>
            <th sort="id">编号</th>
            <th sort="category">所属类别</th>
            <th sort="name">名称</th>
            <th sort="price">价格</th>
            <th>开始时间</th>
            <th>结束时间</th>
            <th>是否显示</th>
            <th style="width: 50px">操作</th>
        </tr>
      </thead>
      <tbody>
      <c:forEach items="${page.content}" var="m" varStatus="status">
        <tr id="${m.id}">
            <td class="check"><input type="checkbox" name="ids" value="${m.id}"></td>
            <td>${m.id}</td>
            <td>
                <a href="${ctx}/showcase/product/product/category-${m.category.id}">${m.category.name}</a>
            </td>
            <td>${m.name}</td>
            <td>${m.price}</td>
            <td><spring:eval expression="m.beginDate"/></td>
            <td><spring:eval expression="m.endDate"/></td>
            <td>${m.show}</td>
            <td>
                <a class="btn btn-link edit-btn" title="修改" href="${ctx}/showcase/product/product/category-${categoryId}/update/${m.id}">
                    <span class=" icon-edit"></span>
                </a>
                <a class="btn btn-link edit-btn" title="删除" href="${ctx}/showcase/product/product/category-${categoryId}/delete/${m.id}">
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
        $("#searchCategoryId").click(function() {
            $.app.modalDialog(
                    "${ctx}/showcase/product/category/select/multiple;domId=searchCategoryId",
                    null,
                    document
            );
        });
    });
</script>
