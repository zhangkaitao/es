<%@ tag import="com.sishuok.es.common.Constants" %>
<%@ tag import="com.sishuok.es.sys.organization.service.OrganizationService" %>
<%@ tag import="com.sishuok.es.common.utils.SpringUtils" %>
<%@ tag import="com.sishuok.es.sys.organization.entity.Organization" %>
<%@ tag import="com.sishuok.es.sys.organization.service.JobService" %>
<%@ tag import="com.sishuok.es.sys.organization.entity.Job" %>
<%@ tag import="java.util.List" %>
<%@ tag import="com.google.common.collect.Lists" %>
<%@ tag pageEncoding="UTF-8"%>
<%@ attribute name="id" type="java.lang.Long" required="true" description="当前要展示的组织机构的名字" %>
<%@ attribute name="showParents" type="java.lang.Boolean" required="false" description="是否显示父亲" %>
<%@ attribute name="includeRoot" type="java.lang.Boolean" required="false" description="是否包含根" %>
<%!private JobService jobService;%>
<%
    if(showParents == null) {
        showParents = true;
    }
    if(includeRoot == null) {
        includeRoot = true;
    }

    if(jobService == null) {
        jobService = SpringUtils.getBean(JobService.class);
    }

    Job job = jobService.findOne(id);

    List<String> names = Lists.newArrayList();
    int index = 0;
    while (job != null) {
        names.add(job.getName());

        if(showParents == false) {
            break;
        }

        job = jobService.findOne(job.getParentId());

        if(includeRoot == false && job.isRoot()) {
            break;
        }
    }

    for(int l = names.size() - 1, i = l; i >= 0; i--) {
        if(i != l) {
            out.write(" &gt; ");
        }
        out.write(names.get(i));
    }
%>
