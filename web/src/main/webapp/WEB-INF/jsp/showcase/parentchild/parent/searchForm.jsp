<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form id="searchForm" class="form-inline search-form" data-change-search="true">


    <esform:label path="search.id_in">编号</esform:label>
    <esform:input path="search.id_in" cssClass="input-small" placeholder="多个使用空格分隔"/>
    &nbsp;&nbsp;

    <esform:label path="search.name_like">名称</esform:label>
    <esform:input path="search.name_like" cssClass="input-small" placeholder="模糊匹配"/>

    &nbsp;&nbsp;

    <esform:label path="search.show_eq">是否显示</esform:label>
    <esform:select path="search.show_eq" cssClass="input-small">
        <esform:option label="全部" value=""/>
        <esform:options items="${booleanList}" itemLabel="info"/>
    </esform:select>


    <button type="submit" class="btn ">查询</button>
    <a class="btn btn-link accordion-toggle" data-toggle="collapse" href="#searchMore">高级查询</a>
    <a class="btn btn-link btn-clear-search">清空</a>


    <%--more--%>
    <div id="searchMore" class="accordion-body collapse">
        <div class="accordion-inner">

            <esform:label path="search.beginDate_gte">出生日期从</esform:label>

            <div class="input-append date">
                <esform:input path="search.beginDate_gte" cssClass="input-medium"
                              data-format="yyyy-MM-dd"
                              placeholder="大于等于"/>
                <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
            </div>
            <esform:label path="search.endDate_lte">到</esform:label>
            <div class="input-append date">
                <esform:input path="search.endDate_lte" cssClass="input-medium"
                              data-format="yyyy-MM-dd"
                              data-position="bottom-left"
                              placeholder="小于等于"/>
                <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
            </div>
        </div>
    </div>
</form>