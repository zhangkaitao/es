<%@ page import="org.hibernate.stat.CollectionStatistics" %>
<%@ page import="org.hibernate.stat.EntityStatistics" %>
<%@ page import="org.hibernate.stat.Statistics" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf" %>
<es:contentHeader/>

<div data-table="table" class="panel">
    <c:set var="type" value="entityAndCollectionCRUDCount"/>
    <%@include file="nav.jspf" %>
    <h3>总体</h3>
    <table class="table table-bordered">
        <tbody>
        <tr class="bold info">
            <td colspan="2">实体缓存统计</td>
        </tr>
        <tr>
            <td style="width: 30%">实体delete次数:</td>
            <td>${statistics.entityDeleteCount}</td>
        </tr>
        <tr>
            <td>实体insert次数:</td>
            <td>${statistics.entityInsertCount}</td>
        </tr>
        <tr>
            <td>实体update次数:</td>
            <td>${statistics.entityUpdateCount}</td>
        </tr>
        <tr>
            <td>实体load次数:</td>
            <td>${statistics.entityLoadCount}</td>
        </tr>
        <tr>
            <td>实体fetch次数:</td>
            <td>${statistics.entityFetchCount}</td>
        </tr>

        <tr class="bold info">
            <td colspan="2">集合缓存统计</td>
        </tr>
        <tr>
            <td>集合remove次数:</td>
            <td>${statistics.collectionRemoveCount}（当inverse="true"）</td>
        </tr>
        <tr>
            <td>集合insert次数:</td>
            <td>${statistics.entityInsertCount}</td>
        </tr>
        <tr>
            <td>集合update次数:</td>
            <td>${statistics.collectionUpdateCount}</td>
        </tr>
        <tr>
            <td>集合load次数:</td>
            <td>${statistics.collectionLoadCount}</td>
        </tr>
        <tr>
            <td>集合fetch次数:</td>
            <td>${statistics.collectionFetchCount}</td>
        </tr>
        <tr>
            <td>集合recreated次数:</td>
            <td>${statistics.collectionRecreateCount}</td>
        </tr>
        </tbody>
    </table>
    <br/>

    <%
        Statistics statistics = (Statistics) request.getAttribute("statistics");
    %>

    <h3>实体CRUD详细</h3>
    <table class="table table-bordered">
        <tbody>
        <%
            String[] entityNames = statistics.getEntityNames();
            for(String entityName : entityNames) {
                EntityStatistics entityStatistics = statistics.getEntityStatistics(entityName);
                pageContext.setAttribute("entityName", entityName);
                pageContext.setAttribute("entityStatistics", entityStatistics);
        %>

        <tr class="bold info">
            <td colspan="2">${entityName}</td>
        </tr>
        <tr>
            <td style="width: 30%">实体delete次数:</td>
            <td>${entityStatistics.deleteCount}</td>
        </tr>
        <tr>
            <td>实体insert次数:</td>
            <td>${entityStatistics.insertCount}</td>
        </tr>
        <tr>
            <td>实体update次数:</td>
            <td>${entityStatistics.updateCount}</td>
        </tr>
        <tr>
            <td>实体load次数:</td>
            <td>${entityStatistics.loadCount}</td>
        </tr>
        <tr>
            <td>实体fetch次数:</td>
            <td>${entityStatistics.fetchCount}</td>
        </tr>
        <tr>
            <td>optimistic lock失败次数:</td>
            <td>${entityStatistics.optimisticFailureCount}</td>
        </tr>

        <%
            }
        %>

        </tbody>
    </table>

    <h3>集合CRUD详细</h3>
    <table class="table table-bordered">
        <tbody>
        <%
            String[] collectionRoleNames = statistics.getCollectionRoleNames();
            for(String collectionRoleName : collectionRoleNames) {
                CollectionStatistics collectionStatistics = statistics.getCollectionStatistics(collectionRoleName);
                pageContext.setAttribute("collectionRoleName", collectionRoleName);
                pageContext.setAttribute("collectionStatistics", collectionStatistics);

        %>

        <tr class="bold info">
            <td colspan="2">${collectionRoleName}</td>
        </tr>
        <tr>
            <td style="width: 30%">集合remove次数:</td>
            <td>${collectionStatistics.removeCount}</td>
        </tr>
        <tr>
            <td>集合update次数:</td>
            <td>${collectionStatistics.updateCount}</td>
        </tr>
        <tr>
            <td>集合load次数:</td>
            <td>${collectionStatistics.loadCount}</td>
        </tr>
        <tr>
            <td>集合fetch次数:</td>
            <td>${collectionStatistics.fetchCount}</td>
        </tr>
        <tr>
            <td>集合recreated次数:</td>
            <td>${collectionStatistics.recreateCount}</td>
        </tr>
        <%
            }
        %>

        </tbody>
    </table>
    <br/><br/>
</div>
<es:contentFooter/>