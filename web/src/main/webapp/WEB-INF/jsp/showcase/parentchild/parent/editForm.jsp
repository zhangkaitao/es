<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div class="panel">
    <ul class="nav nav-tabs">
        <li class="active">
            <a>父管理[${op}]</a>
        </li>
        <li>
            <a href="<es:BackURL/>" class="btn btn-link">返回列表</a>
        </li>
    </ul>
    <form:form id="editForm" method="post" commandName="m" >

            <es:showGlobalError commandName="m"/>
            <form:hidden path="id"/>

            <div class="prettyprint">
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
                                      placeholder="例如2013-02-07"
                                      readonly="true"/>
                        <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
                    </div>
                </div>

                <div class="control-group span4">
                    <form:label path="endDate" cssClass="control-label">结束时间</form:label>
                    <div class="controls input-append date">
                        <form:input path="endDate"
                                      data-format="yyyy-MM-dd"
                                      placeholder="例如2013-02-07"
                                      data-position="bottom-left"
                                      readonly="true"/>
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
            <fieldset>
                <legend class="no-margin">
                    子列表
                    <div class="btn-group">
                        <a class="btn btn-create-child">
                            <span class="icon-edit"></span>
                            新增
                        </a>
                        <a class="btn btn-batch-delete-child">
                            <span class="icon-remove"></span>
                            批量删除
                        </a>
                    </div>
                </legend>
                <div>
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
                                <th>操作</th>
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
                                   <td>
                                       <c:choose>
                                           <c:when test="${op eq '删除'}">
                                               &nbsp;
                                           </c:when>
                                           <c:otherwise>
                                               <a class='btn btn-link icon-edit' href='javascript:void(0);' title='修改'></a>
                                               <a class='btn btn-link icon-trash' href='javascript:void(0);' title='删除'></a>
                                               <a class='btn btn-link icon-copy' href='javascript:void(0);' title='以此为模板复制一份'></a>
                                           </c:otherwise>
                                       </c:choose>
                                   </td>
                               </tr>
                           </c:forEach>
                        </tbody>
                    </table>
                </div>
            </fieldset>

            <br/><br/>
            <div class="control-group ui-toolbar">
                <div class="controls">
                    <input type="submit" class="btn btn-primary" value="${op}">
                    <a href="<es:BackURL/>" class="btn">返回</a>
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
                $("#editForm :input").not(":submit,:button").prop("readonly", true);
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
                height:400,
                buttons:{}
            },
            createUrl : "${ctx}/showcase/parentchild/parent/child/create",
            updateUrl : "${ctx}/showcase/parentchild/parent/child/update/{id}",
            deleteUrl : "${ctx}/showcase/parentchild/parent/child/delete/{id}",
            batchDeleteUrl : "${ctx}/showcase/parentchild/parent/child/batch/delete"

        });
    });



</script>