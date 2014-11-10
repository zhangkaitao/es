<%@ page import="org.hibernate.stat.SecondLevelCacheStatistics" %>
<%@ page import="org.hibernate.stat.Statistics" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.Comparator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf" %>
<es:contentHeader/>

<div data-table="table" class="panel">
    <c:set var="type" value="secondLevelCache"/>
    <%@include file="nav.jspf" %>

    <h3>总体</h3>
    <table class="table table-bordered">
        <tbody>

        <tr class="bold info">
            <td colspan="2">二级缓存统计（实体&集合缓存，不包括查询）</td>
        </tr>
        <c:set var="secondLevelCacheCount"
               value="${statistics.secondLevelCacheHitCount + statistics.secondLevelCacheMissCount}"/>
        <c:set var="secondLevelCacheCount" value="${secondLevelCacheCount > 0 ? secondLevelCacheCount : 1}"/>
        <c:set var="secondLevelCacheHitPercent" value="${statistics.secondLevelCacheHitCount * 1.0 / (secondLevelCacheCount)}"/>
        <tr>
            <td>总命中率:</td>
            <td><fmt:formatNumber value="${secondLevelCacheHitPercent}" maxFractionDigits="2"/></td>
        </tr>
        <tr>
            <td>命中次数:</td>
            <td>
                ${statistics.secondLevelCacheHitCount}
                &nbsp;&nbsp;
                平均:<fmt:formatNumber value="${statistics.secondLevelCacheHitCount/upSeconds}" maxFractionDigits="2"/>次/秒
            </td>
        </tr>
        <tr>
            <td>失效次数:</td>
            <td>
                ${statistics.secondLevelCacheMissCount}
                &nbsp;&nbsp;
                平均:<fmt:formatNumber value="${statistics.secondLevelCacheMissCount/upSeconds}" maxFractionDigits="2"/>次/秒
            </td>
        </tr>
        <tr>
            <td>被缓存的个数:</td>
            <td>${statistics.secondLevelCachePutCount}</td>
        </tr>

        <tr class="bold info">
            <td colspan="2">内存情况</td>
        </tr>
        <tr>
            <td style="width: 30%">当前系统使用内存:</td>
            <td>
                <pretty:prettyMemory byteSize="${usedSystemMemory}"/>
            </td>
        </tr>
        <tr>
            <td style="width: 30%">系统总内存:</td>
            <td>
                <pretty:prettyMemory byteSize="${maxSystemMemory}"/>
            </td>
        </tr>
        <tr>
            <td style="width: 30%">二级缓存使用内存:</td>
            <td>
                <pretty:prettyMemory byteSize="${totalMemorySize}"/>
            </td>
        </tr>
        <tr>
            <td style="width: 30%">内存中的总实体数:</td>
            <td>
                ${totalMemoryCount}
            </td>
        </tr>
        <tr>
            <td style="width: 30%">磁盘中的总实体数:</td>
            <td>
                ${totalDiskCount}
            </td>
        </tr>

        </tbody>
    </table>
    <br/>
    <h3>详细</h3>

    <ul class="nav nav-pills">
        <li ${empty param.sort ? "class='active'" : ""}><a href="">默认排序</a></li>
        <li ${param.sort eq 'hitPercent' ? "class='active'" : ""}><a href="?sort=hitPercent">总命中率</a></li>
        <li ${param.sort eq 'sizeInMemory' ? "class='active'" : ""}><a href="?sort=sizeInMemory">所占内存大小</a></li>
        <li ${param.sort eq 'elementCountInMemory' ? "class='active'" : ""}><a href="?sort=elementCountInMemory">内存中的实体数</a></li>
    </ul>

    <table class="table table-bordered">
        <tbody>
        <%
            Statistics statistics = (Statistics) request.getAttribute("statistics");
            String[] secondLevelCacheRegionNames = statistics.getSecondLevelCacheRegionNames();

            sort(secondLevelCacheRegionNames, statistics, request.getParameter("sort"));

            for(String secondLevelCacheRegionName : secondLevelCacheRegionNames) {
                SecondLevelCacheStatistics secondLevelCacheStatistics =
                        statistics.getSecondLevelCacheStatistics(secondLevelCacheRegionName);
                pageContext.setAttribute("secondLevelCacheRegionName", secondLevelCacheRegionName);
                pageContext.setAttribute("secondLevelCacheStatistics", secondLevelCacheStatistics);

        %>

        <c:set var="totalCount"
               value="${secondLevelCacheStatistics.hitCount + secondLevelCacheStatistics.missCount}"/>
        <c:set var="totalCount" value="${totalCount > 0 ? totalCount : 1}"/>
        <c:set var="cacheHitPercent" value="${secondLevelCacheStatistics.hitCount * 1.0 / (totalCount)}"/>

        <tr class="bold info">
            <td colspan="2">${secondLevelCacheRegionName}</td>
        </tr>
        <tr>
            <td style="width: 30%">总命中率:</td>
            <td><fmt:formatNumber value="${cacheHitPercent}" maxFractionDigits="2"/></td>
        </tr>
        <tr>
            <td>命中次数:</td>
            <td>
                ${secondLevelCacheStatistics.hitCount}
                &nbsp;&nbsp;
                平均:<fmt:formatNumber value="${secondLevelCacheStatistics.hitCount/upSeconds}" maxFractionDigits="2"/>次/秒
            </td>
        </tr>
        <tr>
            <td>失效次数:</td>
            <td>
                ${secondLevelCacheStatistics.missCount}
                &nbsp;&nbsp;
                平均:<fmt:formatNumber value="${secondLevelCacheStatistics.missCount/upSeconds}" maxFractionDigits="2"/>次/秒
            </td>
        </tr>
        <tr>
            <td>往缓存中Put总次数:</td>
            <td>
                ${secondLevelCacheStatistics.putCount}
            </td>
        </tr>
        <tr>
            <td style="width: 30%">所占内存大小:</td>
            <td>
                <pretty:prettyMemory byteSize="${secondLevelCacheStatistics.sizeInMemory}"/>
            </td>
        </tr>
        <tr>
            <td style="width: 30%">内存中的实体数:</td>
            <td>
                ${secondLevelCacheStatistics.elementCountInMemory}
            </td>
        </tr>
        <tr>
            <td style="width: 30%">磁盘中的实体数:</td>
            <td>
                ${secondLevelCacheStatistics.elementCountOnDisk}
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
    //总命中率  命中次数  失效次数  被缓存的个数    所占内存大小   内存中的实体数
    private void sort(final String[] secondLevelCacheRegionNames, final Statistics statistics, final String sortBy) {
        Arrays.sort(secondLevelCacheRegionNames, new Comparator<String>() {
            public int compare(String region1, String region2) {
                SecondLevelCacheStatistics s1 = statistics.getSecondLevelCacheStatistics(region1);
                SecondLevelCacheStatistics s2 = statistics.getSecondLevelCacheStatistics(region2);

                if("hitPercent".equals(sortBy)) {
                    double s1HitPercent = 1.0 * s1.getHitCount() / Math.max(s1.getMissCount() + s1.getHitCount(), 1);
                    double s2HitPercent = 1.0 * s2.getHitCount() / Math.max(s2.getMissCount() + s2.getHitCount(), 1);
                    return -Double.compare(s1HitPercent, s2HitPercent);
                }
                if("hitCount".equals(sortBy)) {
                    return -Long.valueOf(s1.getHitCount()).compareTo(Long.valueOf(s2.getHitCount()));
                }
                if("missCount".equals(sortBy)) {
                    return -Long.valueOf(s1.getMissCount()).compareTo(Long.valueOf(s2.getMissCount()));
                }
                if("putCount".equals(sortBy)) {
                    return -Long.valueOf(s1.getPutCount()).compareTo(Long.valueOf(s2.getPutCount()));
                }
                if("sizeInMemory".equals(sortBy)) {
                    return -Long.valueOf(s1.getSizeInMemory()).compareTo(Long.valueOf(s2.getSizeInMemory()));
                }
                if("elementCountInMemory".equals(sortBy)) {
                    return -Long.valueOf(s1.getElementCountInMemory()).compareTo(Long.valueOf(s2.getElementCountInMemory()));
                }

                return -region1.compareTo(region2);
            }
        });
    }
%>