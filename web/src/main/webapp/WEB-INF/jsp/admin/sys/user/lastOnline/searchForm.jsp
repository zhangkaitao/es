<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form id="searchForm" class="form-inline search-form" data-change-search="true">

    <esform:label path="search.username_like">用户名</esform:label>
    <esform:input path="search.username_like" cssClass="input-small" placeholder="模糊匹配"/>
    &nbsp;&nbsp;

    <esform:label path="search.lastStopTimestamp_gte">最后退出时间从</esform:label>
    <div class="input-append date">
        <esform:input path="search.lastStopTimestamp_gte" cssClass="input-medium"
                      data-format="yyyy-MM-dd hh:mm:ss"
                      placeholder="大于等于"/>
        <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
    </div>
    <esform:label path="search.lastStopTimestamp_lte">到</esform:label>
    <div class="input-append date">
        <esform:input path="search.lastStopTimestamp_lte" cssClass="input-medium"
                      data-format="yyyy-MM-dd hh:mm:ss"
                      data-position="bottom-left"
                      placeholder="小于等于"/>
        <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
    </div>
    <input type="submit" class="btn" value="查询"/>
    <a class="btn btn-link btn-clear-search">清空</a>

</form>