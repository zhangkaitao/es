<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div>
    <form:form id="editForm" method="post" commandName="parent" cssClass="form-horizontal">
        <fieldset>
            <legend class="no-margin">父管理[${op}] <a href="${ctx}/showcase/parentchild/parent" class="btn btn-link">返回</a></legend>
            <es:showGlobalError commandName="parent"/>
            <form:hidden path="id"/>

            <div class="prettyprint">
                <div class="control-group span6">
                    <form:label path="name" cssClass="control-label">名称</form:label>
                    <div class="controls">
                        <form:input path="name" cssClass="validate[required]" placeholder="请输入名称"/>
                    </div>
                </div>

                <div class="control-group span6">
                    <form:label path="type" cssClass="control-label">类型</form:label>
                    <div class="controls">
                        <form:select path="type" items="${typeList}" itemLabel="info"/>
                    </div>
                </div>
                <div class="clearfix"></div>

                <div class="control-group span6">
                    <form:label path="beginDate" cssClass="control-label">开始时间</form:label>
                    <div class="controls input-append date">
                        <form:input path="beginDate" data-format="yyyy-MM-dd" placeholder="例如2013-02-07" readonly="true"/>
                        <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
                    </div>
                </div>

                <div class="control-group span6">
                    <form:label path="endDate" cssClass="control-label">结束时间</form:label>
                    <div class="controls input-append date">
                        <form:input path="endDate" data-format="yyyy-MM-dd" placeholder="例如2013-02-07" readonly="true"/>
                        <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
                    </div>
                </div>
                <div class="clearfix"></div>

                <div class="control-group span6">
                    <form:label path="show" cssClass="control-label">是否显示</form:label>
                    <div class="controls inline-radio">
                        <form:radiobuttons path="show" items="${booleanList}" itemLabel="info" itemValue="value" cssClass="validate[required]"/>
                    </div>
                </div>
                <div class="clearfix"></div>
            </div>
            <fieldset>
                <legend class="no-margin">子列表<a id="createChild" class="btn btn-link">新增子</a></legend>
                <div>
                    <table id="childTable" class="table table-bordered table-hover table-striped">
                        <thead>
                            <tr>
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
                           <tr>
                               <td>1</td>
                               <td>1</td>
                               <td>1</td>
                               <td>1</td>
                               <td>1</td>
                               <td>1</td>
                               <td>1</td>
                           </tr>
                        </tbody>
                    </table>
                </div>
            </fieldset>

            <br/><br/>
            <div class="control-group">
                <div class="controls">
                    <input type="submit" class="btn btn-primary" value="${op}">
                    <a href="${ctx}/showcase/parentchild/parent" class="btn">返回</a>
                </div>
            </div>
        </fieldset>
    </form:form>
</div>
<es:contentFooter/>
<script type="text/javascript">
    $(function () {
        <c:choose>
            <c:when test="${op eq '删除'}">
                //删除时不验证 并把表单readonly
                $("#editForm :input").prop("readonly", true);
            </c:when>
            <c:otherwise>
                var validationEngine = $("#editForm").validationEngine();
                <es:showFieldError commandName="parent"/>
            </c:otherwise>
        </c:choose>


        $("#createChild").click(function() {
            $.app.modalEdit("${ctx}/showcase/parentchild/parent/child/create")
        });


    });
</script>