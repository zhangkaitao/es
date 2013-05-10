<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form id="searchForm" class="form-inline search-form" data-change-search="true">

    <esform:label path="search.id_in">编号</esform:label>
    <esform:input path="search.id_in" cssClass="input-small" placeholder="多个使用空格分隔"/>
    <%--<input type="text" id="search.id_in" name="search.id_in" value="${param['search.id_in']}"  />--%>
    &nbsp;
    <esform:label path="search.age_gt">年龄</esform:label>
    <esform:input path="search.age_gt" cssClass="input-small" placeholder="大于"/>

    <esform:label path="search.age_lt">到</esform:label>
    <esform:input path="search.age_lt" cssClass="input-small" placeholder="小于"/>
    &nbsp;&nbsp;
    <input type="submit" class="btn " value="查询"/>
    <a class="btn btn-link accordion-toggle" data-toggle="collapse" href="#searchMore">高级查询</a>
    <a class="btn btn-link btn-clear-search">清空</a>

    <%--more--%>
    <div id="searchMore" class="accordion-body collapse">
        <div class="accordion-inner">

            <esform:label path="search.birthday_gte">出生日期从</esform:label>

            <div class="input-append date">
                <esform:input path="search.birthday_gte" cssClass="input-medium"
                                data-format="yyyy-MM-dd hh:mm:ss"
                                data-position="bottom-left"
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
