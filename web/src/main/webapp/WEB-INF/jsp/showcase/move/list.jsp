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
      <a id="create" class="btn" href="${ctx}/showcase/move/create">
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
            <th style="width: 10%">
                <a class="check-all" href="javascript:;">全选</a>
                |
                <a class="reverse-all" href="javascript:;">反选</a>
            </th>
            <th sort="id">编号</th>
            <th sort="name">姓名</th>
            <th sort="weight">权重</th>
            <th>是否显示</th>
            <th style="width: 6%">操作</th>
        </tr>
      </thead>
      <tbody>
      <c:forEach items="${moves.content}" var="move" varStatus="status">
        <tr id="${move.id}">
            <td class="check"><input type="checkbox" name="ids" value="${move.id}"></td>
            <td>${move.id}</td>
            <td>${move.name}</td>
            <td>
                ${move.weight}
                <es:movable page="${moves}" first="${status.first}" last="${status.last}"/>
            </td>
            <td>${move.show}</td>
            <td>
                <a class="btn btn-link edit-btn" title="修改" href="${ctx}/showcase/move/update/${move.id}">
                    <span class=" icon-edit"></span>
                </a>
                <a class="btn btn-link edit-btn" title="删除" href="${ctx}/showcase/move/delete/${move.id}">
                    <span class=" icon-remove"></span>
                </a>
            </td>
        </tr>
      </c:forEach>
      </tbody>
  </table>
  <es:page page="${moves}" />
</div>
<es:contentFooter/>
<script type="text/javascript">
    $(function() {
        $("#removeSelect").click(function() {
            $.app.confirm({
                message: "确定删除选中的数据吗？",
                ok : function() {
                    window.location.href = "${ctx}/showcase/move/batch/delete?" + $(".check :checkbox").serialize();
                }
            });
        });

        $("#update").click(function() {
            var id = $(".check :checkbox:checked").val();
            if(!id) {
                $.app.alert({message : "请先选中要修改的数据"});
                return;
            }
            window.location.href = '${ctx}/showcase/move/update/' + id;
        });

        $("#reweight").click(function () {
            $.app.confirm({
                message: "确定优化权重吗？<br/><strong>注意：</strong>优化权重执行效率比较低，请在本系统使用人员较少时执行（如下班时间）",
                ok: function () {
                    $.app.waiting("优化权重执行中。。");
                    $.getJSON("${ctx}/showcase/move/reweight", function(data) {
                        $.app.waitingOver();
                        if(!data.success) {
                            $.app.alert({message : data.message});
                        }
                    });
                }
            });
        });

    });


</script>
