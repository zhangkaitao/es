<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form id="searchForm" class="form-inline search-form" data-change-search="true">

    <label for="search.id_in">编号</label>
    <input type="text" id="search.id_in" name="search.id_in" value="${param['search.id_in']}"  class="input-small" placeholder="多个使用空格分隔"/>
    &nbsp;&nbsp;
    <label for="search.username_like">用户名</label>
    <input type="text" id="search.username_like" name="search.username_like" value="${param['search.username_like']}" class="input-small" placeholder="模糊匹配"/>
    &nbsp;&nbsp;
    <label for="search.status_eq">状态</label>
    <select id="search.status_eq" name="search.status_eq" class="input-small">
        <option value="">所有</option>
        <c:forEach items="${statusList}" var="s">
            <option value="${s}" <c:if test="${param['search.status_eq'] eq s}">selected="selected"</c:if>>${s.info}</option>
        </c:forEach>
    </select>
    &nbsp;&nbsp;
    <input type="submit" class="btn" value="查询"/>
    <a class="btn btn-link accordion-toggle" data-toggle="collapse" href="#searchMore">高级查询</a>
    <a class="btn btn-link btn-search-all">查询所有</a>

    <%--more--%>
    <div id="searchMore" class="accordion-body collapse">
        <div class="accordion-inner">

            <label for="search.deleted_eq">已删除</label>
            <select id="search.deleted_eq" name="search.deleted_eq" class="input-medium">
                <option value="">全部</option>
                <option value="true" <c:if test="${param['search.deleted_eq'] eq 'true'}">selected="selected"</c:if>>是</option>
                <option value="false" <c:if test="${param['search.deleted_eq'] eq 'false'}">selected="selected"</c:if>>否</option>
            </select>
            &nbsp;&nbsp;
            <label for="search.createDate_gte">创建时间从</label>
            <div id="dp_createDate_gte" class="input-append date">
                <input type="text"
                       id="search.createDate_gte"
                       name="search.createDate_gte"
                       value="${param['search.createDate_gte']}"
                       class="input-medium"
                       data-format="yyyy-MM-dd hh:mm:ss"
                       readonly="true"
                       placeholder="大于等于"/>
                <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
            </div>
            <label for="search.createDate_lte">到</label>
            <div id="dp_createDate_lte" class="input-append date">
                <input type="text"
                       id="search.createDate_lte"
                       name="search.createDate_lte"
                       value="${param['search.createDate_lte']}"
                       class="input-medium"
                       data-format="yyyy-MM-dd hh:mm:ss"
                       readonly="true"
                       placeholder="小于等于"/>
                <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
            </div>

        </div>
    </div>

</form>
