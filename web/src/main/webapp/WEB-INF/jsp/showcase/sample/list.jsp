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
      <a id="create" class="btn" href="${ctx}/showcase/sample/create">
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
            <th sort="name">姓名</th>
            <th>年龄</th>
            <th>出生日期</th>
            <th>性别</th>
            <th>是否显示</th>
            <th style="width: 6%">操作</th>
        </tr>
      </thead>
      <tbody>
      <c:forEach items="${samples.content}" var="sample">
        <tr>
            <td class="check"><input type="checkbox" name="ids" value="${sample.id}"></td>
            <td>${sample.id}</td>
            <td>${sample.name}</td>
            <td>${sample.age}</td>
            <td>${sample.birthday}</td>
            <td>${sample.sex.info}</td>
            <td>${sample.show}</td>
            <td>
                <a class="btn btn-link edit-btn" title="修改" href="${ctx}/showcase/sample/update/${sample.id}">
                    <span class=" icon-edit"></span>
                </a>
                <a class="btn btn-link edit-btn" title="删除" href="${ctx}/showcase/sample/delete/${sample.id}">
                    <span class=" icon-remove"></span>
                </a>
            </td>
        </tr>
      </c:forEach>
      </tbody>
  </table>
  <es:page page="${samples}" />
</div>
<es:contentFooter/>
<script type="text/javascript">
    $(function() {
        $("#removeSelect").click(function() {
            $.app.confirm({
                message: "确定删除选中的数据吗？",
                ok : function() {
                    window.location.href = "${ctx}/showcase/sample/batch/delete?" + $(".check :checkbox").serialize();
                    //ajax删除
                    <%--$.ajax({--%>
                        <%--type : "post",--%>
                        <%--dataType: "json",--%>
                        <%--url : "${ctx}/showcase/sample/batch/delete",--%>
                        <%--data : $(".check :checkbox").serialize(),--%>
                        <%--success : function (data) {--%>
                            <%--if (!data.success) {--%>
                                <%--$.app.alert("删除时遇到问题，请重试或联系管理员");--%>
                            <%--} else {--%>
                                <%--location.reload();--%>
                            <%--}--%>
                        <%--}--%>
                    <%--});--%>
                }
            });
        });

        $("#update").click(function() {
            var id = $(".check :checkbox:checked").val();
            if(!id) {
                $.app.alert({message : "请先选中要修改的数据"});
                return;
            }
            window.location.href = '${ctx}/showcase/sample/update/' + id;
        });

    });
</script>
