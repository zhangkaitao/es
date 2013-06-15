<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<div id="container" data-table="childTable">
    <div class="row-fluid tool ui-toolbar">
        <%@include file="searchForm.jsp"%>
    </div>
    <div>
      <table id="childTable" class="sort-table table table-bordered table-hover"
              data-async="true"
              data-async-container="container">
          <thead>
            <tr>

                <th sort="id">编号</th>
                <th sort="name">名称</th>
                <th style="width: 80px">选择</th>
            </tr>
          </thead>
          <tbody>
          <c:forEach items="${page.content}" var="m" varStatus="status">
            <tr id="${m.id}">

                <td>${m.id}</td>
                <td class="name">${m.name}</td>
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
            </tr>
          </c:forEach>
          </tbody>
      </table>
      <es:page page="${page}"/>
    </div>
</div>
<script type="text/javascript">
    $(function() {
        var table = $("#childTable");
        $.table.initTable(table);

        var hasDomName = ${not empty domName};
        //参照窗口的dom
        var $openerId = $("#${domId}");
        var ids = $.array.trim($openerId.val().split(","));
        if(hasDomName) {
            var $openerName = $("#${domName}");
            var names = $.array.trim($openerName.val().split(","));
        }
        //当前窗口的checkbox / radion
        var $checkboxOrRadio = table.find("td.check").find(":checkbox,:radio");
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
        $checkboxOrRadio.closest("tr").andSelf().click(function() {
            var $current = $(this);
            if($current.is("tr")) {
                $current = $current.find(":checkbox,:radio");
            }
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