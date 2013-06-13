<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div class="panel">


    <ul class="nav nav-tabs">
        <shiro:hasPermission name="showcase:parentchild:create">
        <c:if test="${op eq '新增'}">
            <li ${op eq '新增' ? 'class="active"' : ''}>
                <a href="${ctx}/showcase/parentchild/parent/create?BackURL=<es:BackURL/>">
                    <i class="icon-file-alt"></i>
                    新增
                </a>
            </li>
        </c:if>
        </shiro:hasPermission>

        <c:if test="${not empty m.id}">
            <li ${op eq '查看' ? 'class="active"' : ''}>
                <a href="${ctx}/showcase/parentchild/parent/${m.id}?BackURL=<es:BackURL/>">
                    <i class="icon-eye-open"></i>
                    查看
                </a>
            </li>
            <shiro:hasPermission name="showcase:parentchild:update">
            <li ${op eq '修改' ? 'class="active"' : ''}>
                <a href="${ctx}/showcase/parentchild/parent/${m.id}/update?BackURL=<es:BackURL/>">
                    <i class="icon-edit"></i>
                    修改
                </a>
            </li>
            </shiro:hasPermission>
            <shiro:hasPermission name="showcase:parentchild:delete">
            <li ${op eq '删除' ? 'class="active"' : ''}>
                <a href="${ctx}/showcase/parentchild/parent/${m.id}/delete?BackURL=<es:BackURL/>">
                    <i class="icon-trash"></i>
                    删除
                </a>
            </li>
            </shiro:hasPermission>
        </c:if>
        <li>
            <a href="<es:BackURL/>" class="btn btn-link">
                <i class="icon-reply"></i>
                返回
            </a>
        </li>
    </ul>


    <form:form id="editForm" method="post" commandName="m" cssClass="form-inline form-horizontal form-small">

            <es:showGlobalError commandName="m"/>
            <form:hidden path="id"/>
            <h4 class="hr">父数据</h4>
            <div>
                <div class="control-group span4">
                    <form:label path="name" cssClass="control-label">名称</form:label>
                    <div class="controls">
                        <form:input path="name" cssClass="validate[required]" placeholder="请输入名称"/>
                    </div>
                </div>

                <div class="control-group span4">
                    <form:label path="type" cssClass="control-label">类型</form:label>
                    <div class="controls">
                        <form:select path="type" items="${typeList}" itemLabel="info"/>
                    </div>
                </div>
                <div class="clearfix"></div>

                <div class="control-group span4">
                    <form:label path="beginDate" cssClass="control-label">开始时间</form:label>
                    <div class="controls input-append date">
                        <form:input path="beginDate"
                                      data-format="yyyy-MM-dd"
                                      placeholder="例如2013-02-07"/>
                        <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
                    </div>
                </div>

                <div class="control-group span4">
                    <form:label path="endDate" cssClass="control-label">结束时间</form:label>
                    <div class="controls input-append date">
                        <form:input path="endDate"
                                      data-format="yyyy-MM-dd"
                                      placeholder="例如2013-02-07"
                                      data-position="bottom-left"/>
                        <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
                    </div>
                </div>
                <div class="clearfix"></div>

                <div class="control-group span4">
                    <form:label path="show" cssClass="control-label">是否显示</form:label>
                    <div class="controls inline-radio">
                        <form:radiobuttons path="show" items="${booleanList}" itemLabel="info" itemValue="value" cssClass="validate[required]"/>
                    </div>
                </div>
                <div class="clearfix"></div>
            </div>
            <br/>
            <div class="panel">
                <h4 class="hr tool">
                    子数据&nbsp;&nbsp;&nbsp;&nbsp;
                    <div class="btn-group" style="font-weight: normal;">
                        <a class="btn btn-create-child no-disabled">
                            <i class="icon-edit"></i>
                            新增
                        </a>
                        <a class='btn btn-update-child' href='javascript:void(0);'>
                            <i class="icon-edit"></i>
                            修改
                        </a>
                        <a class="btn btn-delete-child">
                            <i class="icon-trash"></i>
                            删除
                        </a>
                        <a class='btn btn-copy-child'>
                            <i class="icon-copy"></i>
                            以此为模板复制一份
                        </a>
                    </div>
                </h4>
                <div class="span9">
                    <table id="childTable" class="table table-bordered table-hover">
                        <thead>
                            <tr>
                                <th style="width: 80px">
                                    <a class="check-all" href="javascript:;">全选</a>
                                    |
                                    <a class="reverse-all" href="javascript:;">反选</a>
                                </th>
                                <th>编号</th>
                                <th>名称</th>
                                <th>类型</th>
                                <th>开始时间</th>
                                <th>结束时间</th>
                                <th>是否显示</th>
                            </tr>
                        </thead>
                        <tbody>
                           <c:forEach items="${childList}" var="c">
                               <tr id="old_${c.id}">
                                   <td class="check"><input type="checkbox" name="ids" value="${c.id}"></td>
                                   <td>${c.id}</td>
                                   <td>${c.name}</td>
                                   <td>${c.type.info}</td>
                                   <td><spring:eval expression="c.beginTime"/></td>
                                   <td><spring:eval expression="c.endTime"/></td>
                                   <td>${c.show ? '是' : '否'}</td>
                               </tr>
                           </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <br/><br/>

            <c:if test="${op eq '新增'}">
                <c:set var="icon" value="icon-file-alt"/>
            </c:if>
            <c:if test="${op eq '修改'}">
                <c:set var="icon" value="icon-edit"/>
            </c:if>
            <c:if test="${op eq '删除'}">
                <c:set var="icon" value="icon-trash"/>
            </c:if>

            <div class="control-group left-group">
                <div>
                    <button type="submit" class="btn btn-primary">
                        <i class="${icon}"></i>
                            ${op}
                    </button>
                    <a href="<es:BackURL/>" class="btn">
                        <i class="icon-reply"></i>
                        返回
                    </a>
                </div>
            </div>


    </form:form>
</div>
<es:contentFooter/>
<script type="text/javascript">
    $(function () {
        <c:choose>
            <c:when test="${op eq '删除'}">
                //删除时不验证 并把表单readonly
                $.app.readonlyForm($("#editForm"), false);
            </c:when>
            <c:when test="${op eq '查看'}">
                $.app.readonlyForm($("#editForm"), true);
            </c:when>
            <c:otherwise>
                var validationEngine = $("#editForm").validationEngine();
                <es:showFieldError commandName="m"/>
            </c:otherwise>
        </c:choose>

        $.parentchild.initParentForm({
            form : $("#editForm"),
            tableId : "childTable",
            prefixParamName : "childList",
            modalSettings:{
                width:600,
                height:420,
                noTitle : true,
                buttons:{}
            },

            createUrl : "${ctx}/showcase/parentchild/parent/child/create",
            updateUrl : "${ctx}/showcase/parentchild/parent/child/{id}/update",
            deleteUrl : "${ctx}/showcase/parentchild/parent/child/{id}/delete",
            batchDeleteUrl : "${ctx}/showcase/parentchild/parent/child/batch/delete"

        });
    });



</script>