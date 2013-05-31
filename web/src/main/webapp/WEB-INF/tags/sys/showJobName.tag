<%@ tag import="com.google.common.collect.Lists" %>
<%@ tag import="com.sishuok.es.common.utils.SpringUtils" %>
<%@ tag import="com.sishuok.es.sys.organization.entity.Job" %>
<%@ tag import="com.sishuok.es.sys.organization.service.JobService" %>
<%@ tag import="java.util.List" %>
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

    if(job == null) {
        out.write("<span class='label label-important'>删除的数据，请修改</span>");
        return;
    }

    List<String> names = Lists.newArrayList();

    names.add(job.getName());

    if(showParents == true) {
        List<Job> parents = jobService.findAncestor(job.getParentIds());
        for(Job o : parents) {
            if(includeRoot == false && o.isRoot()) {
                continue;
            }
            names.add(o.getName());
        }
    }


    StringBuilder s = new StringBuilder();
    s.append(String.format("<a class='btn btn-link no-padding' href='%s/admin/sys/organization/job/%d'>", request.getContextPath(), id));

    for(int l = names.size() - 1, i = l; i >= 0; i--) {
        if(i != l) {
            s.append(" &gt; ");
        }
        s.append(names.get(i));
    }

    s.append("</a>");
    out.write(s.toString());
%>
