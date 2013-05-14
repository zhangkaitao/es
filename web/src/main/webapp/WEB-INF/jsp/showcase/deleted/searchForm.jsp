<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form id="searchForm" class="form-inline search-form" data-change-search="true">


    <esform:label path="search.id_in">编号</esform:label>
    <esform:input path="search.id_in" cssClass="input-medium" placeholder="多个使用空格分隔"/>
    &nbsp;
    <esform:label path="search.deleted_eq">已删除</esform:label>
    <esform:select path="search.deleted_eq" cssClass="input-small">
        <esform:option label="全部" value=""/>
        <esform:options items="${booleanList}" itemLabel="info"/>
    </esform:select>

    &nbsp;
    <button type="submit" class="btn ">查询</button>
    <a class="btn btn-link accordion-toggle" data-toggle="collapse" href="#searchMore">高级查询</a>
    <a class="btn btn-link btn-clear-search">清空</a>

    <%--more--%>
    <div id="searchMore" class="accordion-body collapse">
        <div class="accordion-inner">

            <esform:label path="search.birthday_gte">出生日期从</esform:label>

            <div class="input-append date">
                <esform:input path="search.birthday_gte" cssClass="input-medium"
                              data-format="yyyy-MM-dd hh:mm:ss"
                              placeholder="大于等于"/>
                <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
            </div>
            <esform:label path="search.birthday_lte">到</esform:label>
            <div class="input-append date">
                <esform:input path="search.birthday_lte" cssClass="input-medium"
                              data-format="yyyy-MM-dd hh:mm:ss"
                              data-position="bottom-left"
                              placeholder="小于等于"/>
                <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
            </div>
        </div>
    </div>
</form>
