<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div class="main-content">
	<div class="breadcrumbs" id="breadcrumbs">
		<ul class="breadcrumb">
			<li>
				<i class="icon-home home-icon"></i>
				<a href="#">后台首页</a>
			</li>
			<li>
				<a href="#">产品列表</a>
			</li>
			<li class="active">新增</li>
		</ul><!-- .breadcrumb -->
	</div>
	<div class="page-content">
		<div class="page-header">
			<h1>
				新增产品
			<small>
				<i class="icon-double-angle-right"></i>
					基本信息
			</small>
			</h1>
	</div><!-- /.page-header -->
	<div class="row">
		<div class="col-xs-12">
		<!-- PAGE CONTENT BEGINS -->
    	<form:form id="editForm" method="post" commandName="m" cssClass="form-horizontal" role="form">
          	<es:showGlobalError commandName="m"/>

            <form:hidden path="id"/>

<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> Text Field </label>

										<div class="col-sm-9">
											<input type="text" id="form-field-1" placeholder="Username" class="col-xs-10 col-sm-5" />
										</div>
									</div>
            <div class="control-group">
                <form:label path="category.name" cssClass="control-label">所属类别</form:label>
                <div class="controls input-append">
                    <form:input id="categoryName" path="category.name" cssClass="validate[required]" readonly="true"/>
                    <span class="add-on"><i class="icon-chevron-down"></i></span>
                    <form:hidden id="categoryId" path="category.id"/>
                </div>
                
                <div class="input-group">
					<input class="form-control input-mask-product" type="text" id="form-field-mask-3" />
					<span class="input-group-addon"><i class="icon-search"></i></span>
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
                    <form:input path="beginDate" data-format="yyyy-MM-dd hh:mm:ss" placeholder="例如2013-02-07 11:11:11"/>
                    <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
                </div>
            </div>

            <div class="control-group">
                <form:label path="endDate" cssClass="control-label">结束时间</form:label>
                <div class="controls input-append date">
                    <form:input path="endDate" data-format="yyyy-MM-dd hh:mm:ss" placeholder="例如2013-02-07 11:11:11"/>
                    <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
                </div>
            </div>

            <div class="control-group">
                <form:label path="show" cssClass="control-label">是否显示</form:label>
                <div class="controls inline-radio">
                    <form:radiobuttons path="show" items="${booleanList}" itemLabel="info" itemValue="value" cssClass="validate[required]"/>
                </div>
            </div>


            <c:if test="${op eq '新增'}">
                <c:set var="icon" value="icon-file-alt"/>
            </c:if>
            <c:if test="${op eq '修改'}">
                <c:set var="icon" value="icon-edit"/>
            </c:if>
            <c:if test="${op eq '删除'}">
                <c:set var="icon" value="icon-trash"/>
            </c:if>

            <div class="control-group">
                <div class="controls">
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
                        width:600,
                        height:450,
                        callback : function() {
                            $("[name='category.name']").validationEngine('validate');
                            return true;
                        }
                    }
             );
        });
    });
</script>