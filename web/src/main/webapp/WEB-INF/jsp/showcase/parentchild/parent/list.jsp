<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<es:showMessage/>
<div>
    <div id="search" class="accordion-body collapse">
        <div class="accordion-inner">
            <%@include file="searchForm.jsp"%>
        </div>
    </div>
</div>

<div>
  <div class="btn-group">
      <a class="btn accordion-toggle" data-toggle="collapse" href="#search">
          <span class="icon-search"></span>
          查询
      </a>
      <a id="create" class="btn" href="${ctx}/showcase/parentchild/parent/create">
          <span class="icon-edit"></span>
          新增
      </a>
      <a id="update" class="btn">
          <span class="icon-edit"></span>
          修改
      </a>
      <a id="removeSelect" class="btn">
          <span class="icon-remove"></span>
          批量删除
      </a>
  </div>
  <%-- sortPrefix 指定排序参数的前缀（当多个表单/与实际数据冲突时使用）sortURL：排序时使用的URL（默认当前url）   --%>
  <table class="sort-table table table-bordered table-hover table-striped" sort-prefix="" sort-url="">
      <thead>
        <tr>
            <th style="width: 10%">
                <a class="check-all" href="javascript:;">全选</a>
                |
                <a class="reverse-all" href="javascript:;">反选</a>
            </th>
            <th sort="id">编号</th>
            <th sort="name">名称</th>
            <th>类型</th>
            <th>开始时间</th>
            <th>结束时间</th>
            <th>是否显示</th>
            <th style="width: 6%">操作</th>
        </tr>
      </thead>
      <tbody>
      <c:forEach items="${parents.content}" var="parent">
        <tr>
            <td class="check"><input type="checkbox" name="ids" value="${parent.id}"></td>
            <td>${parent.id}</td>
            <td>${parent.name}</td>
            <td>${parent.type.info}</td>
            <td><spring:eval expression="parent.beginDate"/></td>
            <td><spring:eval expression="parent.endDate"/></td>
            <td>${parent.show}</td>
            <td>
                <a class="btn btn-link edit-btn" title="修改" href="${ctx}/showcase/parentchild/parent/update/${parent.id}">
                    <span class=" icon-edit"></span>
                </a>
                <a class="btn btn-link edit-btn" title="删除" href="${ctx}/showcase/parentchild/parent/delete/${parent.id}">
                    <span class=" icon-remove"></span>
                </a>
            </td>
        </tr>
      </c:forEach>
      </tbody>
  </table>
  <es:page page="${parents}" />
</div>
<es:contentFooter/>
<script type="text/javascript">
    $(function() {
        $("#removeSelect").click(function() {
            $.app.confirm({
                message: "确定删除选中的数据吗？",
                ok : function() {
                    window.location.href = "${ctx}/showcase/parentchild/parent/batch/delete?" + $(".check :checkbox").serialize();
                }
            });
        });

        $("#update").click(function() {
            var id = $(".check :checkbox:checked").val();
            if(!id) {
                $.app.alert({message : "请先选中要修改的数据"});
                return;
            }
            window.location.href = '${ctx}/showcase/parentchild/parent/update/' + id;
        });

    });
</script>
