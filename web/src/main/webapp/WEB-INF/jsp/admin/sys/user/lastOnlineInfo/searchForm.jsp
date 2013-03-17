<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form id="searchForm" class="form-inline search-form" data-change-search="true">

    <label for="search.user.username_like">用户名</label>
    <input type="text" id="search.user.username_like" name="search.user.username_like" value="${param['search.user.username_like']}" class="input-small" placeholder="模糊匹配"/>
    &nbsp;&nbsp;
    <input type="submit" class="btn" value="查询"/>
    <a class="btn btn-link accordion-toggle" data-toggle="collapse" href="#searchMore">高级查询</a>
    <a class="btn btn-link btn-search-all">查询所有</a>

    <%--more--%>
    <div id="searchMore" class="accordion-body collapse">
        <div class="accordion-inner">
            <label for="search.opDate_gte">最后登录时间从</label>
            <div id="dp_opDate_gte" class="input-append date">
                <input type="text"
                       id="search.opDate_gte"
                       name="search.opDate_gte"
                       value="${param['search.opDate_gte']}"
                       class="input-medium"
                       data-format="yyyy-MM-dd hh:mm:ss"
                       readonly="true"
                       placeholder="大于等于"/>
                <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
            </div>
            <label for="search.opDate_lte">到</label>
            <div id="dp_opDate_lte" class="input-append date">
                <input type="text"
                       id="search.opDate_lte"
                       name="search.opDate_lte"
                       value="${param['search.opDate_lte']}"
                       class="input-medium"
                       data-format="yyyy-MM-dd hh:mm:ss"
                       readonly="true"
                       placeholder="小于等于"/>
                <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
            </div>

        </div>
    </div>

</form>
