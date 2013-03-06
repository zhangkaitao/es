<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<div id="container">
    <fieldset>
        <legend>
            类别参照列表 <a class="btn btn-primary" href="javascript:$.app.waitingOver();">关闭</a>
        </legend>
    </fieldset>
    <div>
       <%@include file="searchForm.jsp"%>
    </div>

    <div>
      <table class="sort-table table table-bordered table-hover table-striped" style="width: 93%"
              sort-url="${currentURL}" sort-async="true" sort-container-id="container">
          <thead>
            <tr>
                <th style="width: 8%">
                    选择
                </th>
                <th sort="id">编号</th>
                <th sort="name">名称</th>
            </tr>
          </thead>
          <tbody>
          <c:forEach items="${page.content}" var="m" varStatus="status">
            <tr id="${m.id}">
                <td class="check">
                    <c:choose>
                        <c:when test="${selectType eq 'multiple'}">
                            <input type="checkbox" name="ids" value="${m.id}">
                        </c:when>
                        <c:otherwise>
                            <input type="radio" name="ids" value="${m.id}">
                        </c:otherwise>
                    </c:choose>

                </td>
                <td>${m.id}</td>
                <td class="name">${m.name}</td>
            </tr>
          </c:forEach>
          </tbody>
      </table>
      <es:page page="${page}" async="true" containerId="container"/>
    </div>
</div>
<script type="text/javascript">
    $(function() {
        $.app.initTable($(".table"));
        $.app.asyncLoad(
                $("#searchForm").prop("action", "${noQueryStringCurrentURL}"),
                "container");

        $.app.asyncLoad(
                $("#searchForm .search-all").prop("href", $("#searchForm").prop("action")),
                "container");

        var multiple = ${selectType eq 'multiple'};
        var hasDomName = ${not empty domName};
        //参照窗口的dom
        var $openerId = $("#${domId}", top.currentDocument);
        var ids = $.array.trim($openerId.val().split(","));
        if(hasDomName) {
            var $openerName = $("#${domName}", top.currentDocument);
            var names = $.array.trim($openerName.val().split(","));
        }
        //当前窗口的checkbox / radion
        var $checkboxOrRadio = $(".table").find("td.check").find(":checkbox,:radio");
        //根据参照窗口的值 选中当前窗口的数据
        $checkboxOrRadio.each(function() {
            var $current = $(this);
            var $currentVal = $current.val();
            $.each(ids, function(index, id) {
                if(id == $currentVal) {
                    $current.prop("checked", true);
                }
            });
        });
        $checkboxOrRadio.parent().click(function() {
            var $current = $(this).find(":checkbox,:radio");
            //单选按钮仅有一个值
            if($current.is(":radio")) {
                $.array.clear(ids);
                if(hasDomName) {
                    $.array.clear(names);
                }
            }
            var id = $current.val();
            if(hasDomName) {
                var name = $current.closest("tr").find("td.name").text();
            }
            if ($current.is(":checked")) {
                if (!$.array.contains(ids, id)) {
                    ids.push(id);
                    if(hasDomName) {
                        names.push(name);
                    }
                }
            } else {
                var index = $.array.indexOf(ids, id);
                ids.splice(index, 1);
                if(hasDomName) {
                    names.splice(index, 1);
                }
            }

            $openerId.val(ids.join(","));

            if(hasDomName) {
                $openerName.val(names.join(","));
            }

        });


    });
</script>