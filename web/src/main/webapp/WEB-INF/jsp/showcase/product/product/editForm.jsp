<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div>
    <form:form id="editForm" method="post" commandName="m" cssClass="form-horizontal">
        <fieldset>
            <legend>产品管理[${op}] <a href="<es:BackURL/>" class="btn btn-link">返回</a></legend>

            <es:showGlobalError commandName="m"/>

            <form:hidden path="id"/>

            <div class="control-group">
                <form:label path="category.name" cssClass="control-label">所属类别</form:label>
                <div class="controls input-append">
                    <form:input id="categoryName" path="category.name" cssClass="validate[required]" readonly="true"/>
                    <span class="add-on"><i class="icon-chevron-down"></i></span>
                    <form:hidden id="categoryId" path="category.id"/>
                </div>
            </div>

            <div class="control-group">
                <form:label path="name" cssClass="control-label">名称</form:label>
                <div class="controls">
                    <form:input path="name" cssClass="validate[required]" placeholder="5到10个字母、数字、下划线"/>
                </div>
            </div>

            <div class="control-group">
                <form:label path="price" cssClass="control-label">价格</form:label>
                <div class="controls input-prepend input-append">
                    <form:input path="price" cssClass="validate[required,custom[integer]]" placeholder="以角为单位，请输入整数"/>
                    <span class="add-on" title="角（人民币）">￥</span>
                </div>
            </div>

            <div class="control-group">
                <form:label path="number" cssClass="control-label">数量</form:label>
                <div class="controls">
                    <form:input path="number" cssClass="validate[required,custom[integer]]" placeholder="请输入整数且大于等于0的数字"/>
                </div>
            </div>


            <div class="control-group">
                <form:label path="beginDate" cssClass="control-label">开始时间</form:label>
                <div class="controls input-append date">
                    <form:input path="beginDate" data-format="yyyy-MM-dd hh:mm:ss" placeholder="例如2013-02-07 11:11:11" readonly="true"/>
                    <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
                </div>
            </div>

            <div class="control-group">
                <form:label path="endDate" cssClass="control-label">结束时间</form:label>
                <div class="controls input-append date">
                    <form:input path="endDate" data-format="yyyy-MM-dd hh:mm:ss" placeholder="例如2013-02-07 11:11:11" readonly="true"/>
                    <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
                </div>
            </div>

            <div class="control-group">
                <form:label path="show" cssClass="control-label">是否显示</form:label>
                <div class="controls inline-radio">
                    <form:radiobuttons path="show" items="${booleanList}" itemLabel="info" itemValue="value" cssClass="validate[required]"/>
                </div>
            </div>

            <div class="control-group">
                <div class="controls">
                    <input type="submit" class="btn btn-primary" value="${op}">
                    <a href="<es:BackURL/>" class="btn">返回</a>
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
                $("#editForm :input").not(":button,:submit").prop("readonly", true);
            </c:when>
            <c:otherwise>
                var validationEngine = $("#editForm").validationEngine();
                <es:showFieldError commandName="m"/>
            </c:otherwise>
        </c:choose>

        $( "#number" ).spinner({
            step: 1,
            min : 0,
            numberFormat: "n"
        });

        $("[name='category.name']").siblings(".add-on").andSelf().click(function() {
            $.app.modalDialog(
                    "参照",
                    "${ctx}/showcase/product/category/select/single;domId=categoryId;domName=categoryName",
                    {
                        width:800,
                        height:450
                    }
             );
        });
    });
</script>