<%@ page import="net.sf.ehcache.CacheManager" %>
<%@ page import="net.sf.ehcache.Statistics" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.Comparator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf" %>
<es:contentHeader/>
<div data-table="table" class="panel">
    <%@include file="nav.jspf" %>

    <ul class="nav nav-pills">
        <li ${empty param.sort ? "class='active'" : ""}><a href="?">默认排序</a></li>
        <li ${param.sort eq 'hitPercent' ? "class='active'" : ""}><a href="?sort=hitPercent">总命中率</a></li>
        <li ${param.sort eq 'objectCount' ? "class='active'" : ""}><a href="?sort=objectCount">内存中对象数</a></li>
    </ul>

    <table class="table table-bordered">
        <tbody>
        <%
            CacheManager cacheManager = (CacheManager) request.getAttribute("cacheManager");
            String[] cacheNames = cacheManager.getCacheNames();
            int length = cacheNames.length;

            sort(cacheNames, cacheManager, request.getParameter("sort"));

            for (int i = 0; i < length; i++) {
                String cacheName = cacheNames[i];
                pageContext.setAttribute("cacheName", cacheName);
                pageContext.setAttribute("cache", cacheManager.getCache(cacheName));
        %>

        <c:set var="totalCount"
               value="${cache.statistics.cacheHits + cache.statistics.cacheMisses}"/>
        <c:set var="totalCount" value="${totalCount > 0 ? totalCount : 1}"/>
        <c:set var="cacheHitPercent" value="${cache.statistics.cacheHits * 1.0 / (totalCount)}"/>


        <tr class="bold info">
            <td colspan="2">
            ${cacheName}
            <a href="${ctx}/admin/monitor/ehcache/${cacheName}/details" class="btn btn-link no-padding pull-right">详细</a>
            </td>
        </tr>

        <tr>
            <td>总命中率:</td>
            <td>${cacheHitPercent}</td>
        </tr>
        <tr>
            <td>命中次数:</td>
            <td>${cache.statistics.cacheHits}</td>
        </tr>
        <tr>
            <td>失效次数:</td>
            <td>${cache.statistics.cacheMisses}</td>
        </tr>
        <tr>
            <td>缓存总对象数:</td>
            <td>${cache.statistics.objectCount}</td>
        </tr>
        <tr>
            <td>最后一秒查询完成的执行数:</td>
            <td>${cache.statistics.searchesPerSecond}</td>
        </tr>
        <tr>
            <td>最后一次采样的平均执行时间:</td>
            <td>${cache.statistics.averageSearchTime}毫秒</td>
        </tr>
        <tr>
            <td>平均获取时间:</td>
            <td>${cache.statistics.averageGetTime}毫秒</td>
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
        private void sort(final String[] cacheNames, final CacheManager cacheManager, final String sort) {
            Arrays.sort(cacheNames, new Comparator<String>() {
                public int compare(String n1, String n2) {
                    Statistics s1 = cacheManager.getCache(n1).getStatistics();
                    Statistics s2 = cacheManager.getCache(n2).getStatistics();
                    if("hitCount".equals(sort)) {
                        double n1HitPercent = 1.0 * s1.getCacheHits() / Math.max(s1.getCacheHits() + s1.getCacheMisses(), 1);
                        double n2HitPercent = 1.0 * s2.getCacheHits() / Math.max(s2.getCacheHits() + s2.getCacheMisses(), 1);
                        return -Double.compare(n1HitPercent, n2HitPercent);
                    } else if("objectCount".equals(sort)) {
                        return -Long.valueOf(s1.getObjectCount()).compareTo(Long.valueOf(s2.getObjectCount()));
                    }
                    return -n1.compareTo(n2);
                }

            });
        }
%>