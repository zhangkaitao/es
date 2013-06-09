<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf" %>
<es:contentHeader/>
<div data-table="table" class="panel">
    <%@include file="nav.jspf" %>

    <table class="table table-bordered">
        <tbody>
        <tr class="bold info">
            <td colspan="2">事务统计</td>
        </tr>
        <tr>
            <td style="width: 30%">执行的事务次数:</td>
            <td>${statistics.transactionCount}</td>
        </tr>
        <tr>
            <td>成功的事务次数:</td>
            <td>${statistics.successfulTransactionCount}</td>
        </tr>

        <tr class="bold info">
            <td colspan="2">连接统计</td>
        </tr>

        <tr>
            <td>数据库连接次数:</td>
            <td>
                ${statistics.connectCount}
                平均<fmt:formatNumber value="${statistics.connectCount/upSeconds}" maxFractionDigits="2"/>次/秒
            </td>
        </tr>
        <tr>
            <td>数据库prepareStatement获取次数:</td>
            <td>${statistics.prepareStatementCount}</td>
        </tr>
        <tr>
            <td>数据库prepareStatement关闭次数:</td>
            <td>${statistics.closeStatementCount} （Hibernate4 bug 没有记录）</td>
        </tr>
        <tr class="error">
            <td>optimistic lock失败次数:</td>
            <td>${statistics.optimisticFailureCount}</td>
        </tr>

        <tr class="bold info">
            <td colspan="2">Session统计</td>
        </tr>
        <tr>
            <td>Session打开的次数:</td>
            <td>
                ${statistics.sessionOpenCount}
                平均<fmt:formatNumber value="${statistics.sessionOpenCount/upSeconds}" maxFractionDigits="2"/>次/秒
            </td>
        </tr>
        <tr>
            <td>Session关闭的次数:</td>
            <td>
                ${statistics.sessionCloseCount}
                &nbsp;&nbsp;
                <span class="muted">因为OpenSessionInView，所以少一次</span>
            </td>
        </tr>
        <tr>
            <td>Session flush的次数:</td>
            <td>${statistics.flushCount}</td>
        </tr>

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


        <tr class="bold info">
            <td colspan="2">实体CRUD统计</td>
        </tr>
        <tr>
            <td>实体delete次数:</td>
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
            <td colspan="2">集合CRUD统计</td>
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


        <tr class="bold info">
            <td colspan="2">查询缓存统计</td>
        </tr>
        <tr>
            <td>查询总执行次数:</td>
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
            <td>被缓存的查询个数:</td>
            <td>
                ${statistics.queryCachePutCount}
            </td>
        </tr>

        <tr class="bold info">
            <td colspan="2">UpdateTimestamp缓存统计</td>
        </tr>
        <c:set var="updateTimestampsCacheCount"
               value="${statistics.updateTimestampsCacheHitCount + statistics.updateTimestampsCacheMissCount}"/>
        <c:set var="updateTimestampsCacheCount" value="${updateTimestampsCacheCount > 0 ? updateTimestampsCacheCount : 1}"/>
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
                平均:<fmt:formatNumber value="${statistics.updateTimestampsCacheHitCount/upSeconds}" maxFractionDigits="2"/>次/秒
            </td>
        </tr>
        <tr>
            <td>失效次数:</td>
            <td>
                ${statistics.updateTimestampsCacheMissCount}
                &nbsp;&nbsp;
                平均:<fmt:formatNumber value="${statistics.updateTimestampsCacheMissCount/upSeconds}" maxFractionDigits="2"/>次/秒
            </td>
        </tr>

        <tr class="bold info">
            <td colspan="2">jpa配置</td>
        </tr>
        <c:forEach items="${properties}" var="entry">
            <c:if test="${fn:startsWith(entry.key, 'hibernate') or fn:startsWith(entry.key, 'javax') or fn:startsWith(entry.key, 'net')}">
            <tr>
                <td>${entry.key}:</td>
                <td>
                    ${entry.value}
                </td>
            </tr>
            </c:if>
        </c:forEach>

        <tr class="bold info">
            <td colspan="2">加载的实体</td>
        </tr>
        <c:forEach items="${sessionFactory.allClassMetadata}" var="entry">

        <tr>
            <td>${entry.key}:</td>
            <td>
                实体名:${entry.value.entityName}<br/>
                标识符名：${entry.value.identifierPropertyName}
            </td>
        </tr>
        </c:forEach>

        </tbody>
    </table>
    <br/><br/>
</div>
<es:contentFooter/>