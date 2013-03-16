<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form id="searchForm" class="form-inline search-form" data-change-search="true">

    <label for="search.id_in">编号</label>
    <input type="text" id="search.id_in" name="search.id_in" value="${param['search.id_in']}"  class="input-medium" placeholder="多个使用空格分隔">
    &nbsp;&nbsp;
    <label for="search.name_like">名称</label>
    <input type="text" id="search.name_like" name="search.name_like" value="${param['search.name_like']}" class="input-small" placeholder="模糊查询">

    &nbsp;&nbsp;
    <label for="search.show_eq">是否显示</label>
    <select id="search.show_eq" name="search.show_eq" class="input-small" placeholder="模糊查询">
        <option value="">全部</option>
        <c:forEach items="${booleanList}" var="b">
            <option
                value="${b.value}"
                <c:if test="${param['search.show_eq'] eq b.value}">selected="selected"</c:if>
            >
                ${b.info}
            </option>
        </c:forEach>
    </select>

    <button type="submit" class="btn ">查询</button>
    <a class="btn btn-link accordion-toggle" data-toggle="collapse" href="#searchMore">高级查询</a>
    <a class="btn btn-link btn-search-all">查询所有</a>

    <%--more--%>
    <div id="searchMore" class="accordion-body collapse">
        <div class="accordion-inner">
            <label for="search.beginDate_gte">开始时间从</label>
            <div id="dp_birthday_gte" class="input-append date">
                <input type="text"
                        id="search.beginDate_gte"
                        name="search.beginDate_gte"
                        value="${param['search.beginDate_gte']}"
                        class="input-medium"
                        readonly="true"
                        data-format="yyyy-MM-dd"
                        placeholder="大于等于"/>
                <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
            </div>
            <label for="search.endDate_lte">到</label>
            <div id="dp_birthday_lte" class="input-append date">
                <input type="text"
                        id="search.endDate_lte"
                        name="search.endDate_lte"
                        value="${param['search.endDate_lte']}"
                        class="input-medium"
                        readonly="true"
                        data-format="yyyy-MM-dd"
                        placeholder="小于等于"/>
                <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
            </div>
        </div>
    </div>
</form>
