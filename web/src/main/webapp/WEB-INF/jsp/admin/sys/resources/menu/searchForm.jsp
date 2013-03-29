<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form id="searchForm" class="form-inline search-form" data-change-search="true">

    <label for="search.id_in">编号</label>
    <input type="text" id="search.id_in" name="search.id_in" value="${param['search.id_in']}"  class="input-medium" placeholder="多个使用空格分隔"/>
    &nbsp;
    <label for="search.age_gt">年龄从</label>
    <input type="text" id="search.age_gt" name="search.age_gt" value="${param['search.age_gt']}" class="input-small" placeholder="大于"/>

    <label for="search.age_lt">到</label>
    <input type="text" id="search.age_lt" name="search.age_lt" value="${param['search.age_lt']}" class="input-small" placeholder="小于"/>
    &nbsp;&nbsp;
    <input type="submit" class="btn " value="查询"/>
    <a class="btn btn-link accordion-toggle" data-toggle="collapse" href="#searchMore">高级查询</a>

    <%--more--%>
    <div id="searchMore" class="accordion-body collapse">
        <div class="accordion-inner">

            <label for="search.birthday_gte">出生日期从</label>
            <div id="dp_birthday_gte" class="input-append date">
                <input type="text"
                       id="search.birthday_gte"
                       name="search.birthday_gte"
                       value="${param['search.birthday_gte']}"
                       class="input-medium"
                       data-format="yyyy-MM-dd hh:mm:ss"
                       placeholder="大于等于"/>
                <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
            </div>
            <label for="search.birthday_lte">到</label>
            <div id="dp_birthday_lte" class="input-append date">
                <input type="text"
                       id="search.birthday_lte"
                       name="search.birthday_lte"
                       value="${param['search.birthday_lte']}"
                       class="input-medium"
                       data-format="yyyy-MM-dd hh:mm:ss"
                       data-position="bottom-left"
                       placeholder="小于等于"/>
                <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
            </div>
        </div>
    </div>
</form>
