<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form id="searchForm" class="form-inline search-form" data-change-search="true">

    <label for="search.username_like">用户名</label>
    <input type="text" id="search.username_like" name="search.username_like" value="${param['search.username_like']}" class="input-small" placeholder="模糊匹配"/>
    &nbsp;&nbsp;
    <label for="search.lastStopTimestamp_gte">最后退出时间从</label>
    <div id="dp_lastStopTimestamp_gte" class="input-append date">
        <input type="text"
               id="search.lastStopTimestamp_gte"
               name="search.lastStopTimestamp_gte"
               value="${param['search.lastStopTimestamp_gte']}"
               class="input-medium"
               data-format="yyyy-MM-dd hh:mm:ss"
               placeholder  ="大于等于"/>
        <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
    </div>
    <label for="search.lastStopTimestamp_lte">到</label>
    <div id="dp_lastStopTimestamp_lte" class="input-append date">
        <input type="text"
               id="search.lastStopTimestamp_lte"
               name="search.lastStopTimestamp_lte"
               value="${param['search.lastStopTimestamp_lte']}"
               class="input-medium"
               data-format="yyyy-MM-dd hh:mm:ss"
               placeholder="小于等于"/>
        <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
    </div>
    &nbsp;&nbsp;
    <input type="submit" class="btn" value="查询"/>
    <a class="btn btn-link accordion-toggle" data-toggle="collapse" href="#searchMore">高级查询</a>
    <a class="btn btn-link btn-search-all">查询所有</a>


</form>
