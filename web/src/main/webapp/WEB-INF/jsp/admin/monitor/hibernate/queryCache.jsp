<%@ page import="org.hibernate.stat.QueryStatistics" %>
<%@ page import="org.hibernate.stat.Statistics" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.Comparator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf" %>
<es:contentHeader/>

<div data-table="table" class="panel">
    <c:set var="type" value="query"/>
    <%@include file="nav.jspf" %>
    <h3>总体</h3>
    <table class="table table-bordered">
        <tbody>
        <tr class="bold info">
            <td colspan="2">查询缓存统计</td>
        </tr>
        <tr>
            <td style="width: 30%">查询总执行次数:</td>
            <td>${statistics.queryExecutionCount}</td>
        </tr>
        <tr>
            <td>最慢查询执行时间:</td>
            <td>${statistics.queryExecutionMaxTime}毫秒</td>
        </tr>
        <tr>
            <td>最慢查询:</td>
            <td>${statistics.queryExecutionMaxTimeQueryString}</td>
        </tr>
        <c:set var="queryCacheCount" value="${statistics.queryCacheHitCount + statistics.queryCacheMissCount}"/>
        <c:set var="queryCacheCount" value="${queryCacheCount > 0 ? queryCacheCount : 1}"/>
        <c:set var="queryCacheHitPercent" value="${statistics.queryCacheHitCount * 1.0 / (queryCacheCount)}"/>
        <tr>
            <td>总命中率:</td>
            <td><fmt:formatNumber value="${queryCacheHitPercent}" maxFractionDigits="2"/></td>
        </tr>
        <tr>
            <td>命中次数:</td>
            <td>
                ${statistics.queryCacheHitCount}
                &nbsp;&nbsp;
                平均:<fmt:formatNumber value="${statistics.queryCacheHitCount/upSeconds}" maxFractionDigits="2"/>次/秒
            </td>
        </tr>
        <tr>
            <td>失效次数:</td>
            <td>
                ${statistics.queryCacheMissCount}
                &nbsp;&nbsp;
                平均:<fmt:formatNumber value="${statistics.queryCacheMissCount/upSeconds}" maxFractionDigits="2"/>次/秒
            </td>
        </tr>
        <tr>
            <td>放入次数:</td>
            <td>
                ${statistics.queryCachePutCount}
            </td>
        </tr>

        <tr class="bold info">
            <td colspan="2">UpdateTimestamp缓存统计</td>
        </tr>
        <c:set var="updateTimestampsCacheCount"
               value="${statistics.updateTimestampsCacheHitCount + statistics.updateTimestampsCacheMissCount}"/>
        <c:set var="updateTimestampsCacheCount"
               value="${updateTimestampsCacheCount > 0 ? updateTimestampsCacheCount : 1}"/>
        <c:set var="updateTimestampsCacheHitPercent"
               value="${statistics.updateTimestampsCacheHitCount * 1.0 / (queryCacheCount)}"/>
        <tr>
            <td>总命中率:</td>
            <td><fmt:formatNumber value="${updateTimestampsCacheHitPercent}" maxFractionDigits="2"/></td>
        </tr>
        <tr>
            <td>命中次数:</td>
            <td>
                ${statistics.updateTimestampsCacheHitCount}
                &nbsp;&nbsp;
                平均:<fmt:formatNumber value="${statistics.updateTimestampsCacheHitCount/upSeconds}"
                                     maxFractionDigits="2"/>次/秒
            </td>
        </tr>
        <tr>
            <td>失效次数:</td>
            <td>
                ${statistics.updateTimestampsCacheMissCount}
                &nbsp;&nbsp;
                平均:<fmt:formatNumber value="${statistics.updateTimestampsCacheMissCount/upSeconds}"
                                     maxFractionDigits="2"/>次/秒
            </td>
        </tr>
        </tbody>
    </table>
    <br/>
    <h3>详细</h3>

    <ul class="nav nav-pills">
        <li ${empty param.sort ? "class='active'" : ""}><a href="">默认排序</a></li>
        <li ${param.sort eq 'hitPercent' ? "class='active'" : ""}><a href="?sort=hitPercent">总命中率</a></li>
        <li ${param.sort eq 'executionCount' ? "class='active'" : ""}><a href="?sort=executionCount">执行次数</a></li>
        <li ${param.sort eq 'executionAvgRowCount' ? "class='active'" : ""}><a href="?sort=executionAvgRowCount">执行返回的平均行数</a></li>
        <li ${param.sort eq 'executionAvgTime' ? "class='active'" : ""}><a href="?sort=executionAvgTime">平均执行时间</a></li>
        <li ${param.sort eq 'executionMaxTime' ? "class='active'" : ""}><a href="?sort=executionMaxTime">最长执行时间</a></li>
    </ul>
    <table class="table table-bordered">
        <tbody>
        <%
            Statistics statistics = (Statistics) request.getAttribute("statistics");
            String[] queries = statistics.getQueries();

            sort(queries, statistics, request.getParameter("sort"));

            for(String query : queries) {
                QueryStatistics queryStatistics = statistics.getQueryStatistics(query);
                pageContext.setAttribute("query", query);
                pageContext.setAttribute("queryStatistics", queryStatistics);

        %>

        <c:set var="queryCacheCount"
               value="${queryStatistics.cacheHitCount + queryStatistics.cacheMissCount}"/>
        <c:set var="queryCacheCount" value="${queryCacheCount > 0 ? queryCacheCount : 1}"/>
        <c:set var="queryCacheHitPercent" value="${queryStatistics.cacheHitCount * 1.0 / (queryCacheCount)}"/>

        <tr class="bold info">
            <td colspan="2">${query}</td>
        </tr>
        <tr>
            <td style="width: 30%">总命中率:</td>
            <td><fmt:formatNumber value="${queryCacheHitPercent}" maxFractionDigits="2"/></td>
        </tr>
        <tr>
            <td>命中次数:</td>
            <td>
                ${queryStatistics.cacheHitCount}
                &nbsp;&nbsp;
                平均:<fmt:formatNumber value="${queryStatistics.cacheHitCount/upSeconds}" maxFractionDigits="2"/>次/秒
            </td>
        </tr>
        <tr>
            <td>失效次数:</td>
            <td>
                ${queryStatistics.cacheMissCount}
                &nbsp;&nbsp;
                平均:<fmt:formatNumber value="${queryStatistics.cacheMissCount/upSeconds}" maxFractionDigits="2"/>次/秒
            </td>
        </tr>
        <tr>
            <td>往缓存中Put总次数:</td>
            <td>
                ${queryStatistics.cachePutCount}
            </td>
        </tr>
        <tr>
            <td style="width: 30%">执行次数（实际查数据库）:</td>
            <td>
                ${queryStatistics.executionCount}
            </td>
        </tr>
        <tr>
            <td>执行返回行数:</td>
            <td>
                总行数:${queryStatistics.executionRowCount}
                <c:set var="executionAvgRowCount" value="${queryStatistics.executionRowCount/queryStatistics.executionCount}"/>
                <c:set var="executionAvgRowCount" value="${executionAvgRowCount > 0 ? executionAvgRowCount : 0}"/>
                平均行数():${executionAvgRowCount}

            </td>
        </tr>
        <tr>
            <td>执行时间:</td>
            <td>
                平均:${queryStatistics.executionAvgTime}毫秒
                最长:${queryStatistics.executionMaxTime}毫秒
                最短:${queryStatistics.executionMinTime}毫秒
            </td>
        </tr>

        <%
            }
        %>

        </tbody>
    </table>
    <br/><br/>
</div>
<es:contentFooter/>
<%!
    //排序：总命中率 命中次数  失效次数  被缓存的个数  执行次数 执行返回的总行数   平均执行时间  最长执行时间  最短执行时间
    private void sort(final String[] queries, final Statistics statistics, final String sortBy) {
        Arrays.sort(queries, new Comparator<String>() {
            public int compare(String q1, String q2) {
                QueryStatistics s1 = statistics.getQueryStatistics(q1);
                QueryStatistics s2 = statistics.getQueryStatistics(q2);

                if ("hitPercent".equals(sortBy)) {
                    double s1HitPercent = 1.0 * s1.getCacheHitCount() / Math.max(s1.getCacheMissCount() + s1.getCacheHitCount(), 1);
                    double s2HitPercent = 1.0 * s2.getCacheHitCount() / Math.max(s2.getCacheMissCount() + s2.getCacheHitCount(), 1);
                    return -Double.compare(s1HitPercent, s2HitPercent);
                }
                if ("hitCount".equals(sortBy)) {
                    return -Long.valueOf(s1.getCacheHitCount()).compareTo(Long.valueOf(s2.getCacheHitCount()));
                }
                if ("missCount".equals(sortBy)) {
                    return -Long.valueOf(s1.getCacheMissCount()).compareTo(Long.valueOf(s2.getCacheMissCount()));
                }
                if ("putCount".equals(sortBy)) {
                    return -Long.valueOf(s1.getCachePutCount()).compareTo(Long.valueOf(s2.getCachePutCount()));
                }
                if ("executionCount".equals(sortBy)) {
                    return -Long.valueOf(s1.getExecutionCount()).compareTo(Long.valueOf(s2.getExecutionCount()));
                }
                if ("executionRowCount".equals(sortBy)) {
                    return -Long.valueOf(s1.getExecutionRowCount()).compareTo(Long.valueOf(s2.getExecutionRowCount()));
                }
                if ("executionAvgRowCount".equals(sortBy)) {
                    return -Long.valueOf(s1.getExecutionRowCount() / s1.getExecutionRowCount())
                            .compareTo(Long.valueOf(s2.getExecutionRowCount() / s1.getExecutionRowCount()));
                }
                if ("executionAvgTime".equals(sortBy)) {
                    return -Long.valueOf(s1.getExecutionAvgTime())
                            .compareTo(Long.valueOf(s2.getExecutionAvgTime()));
                }
                if ("executionMaxTime".equals(sortBy)) {
                    return -Long.valueOf(s1.getExecutionMaxTime())
                            .compareTo(Long.valueOf(s2.getExecutionMaxTime()));
                }
                if ("executionMinTime".equals(sortBy)) {
                    return -Long.valueOf(s1.getExecutionMinTime())
                            .compareTo(Long.valueOf(s2.getExecutionMinTime()));
                }

                return -q1.compareTo(q2);
            }
        });
    }
%>