<%@ tag pageEncoding="UTF-8"%>
<%@ tag import="com.sishuok.es.sys.organization.service.OrganizationService" %>
<%@ tag import="com.sishuok.es.common.utils.SpringUtils" %>
<%@ tag import="com.sishuok.es.sys.organization.entity.Organization" %>
<%@ tag import="com.google.common.collect.Lists" %>
<%@ tag import="java.util.List" %>
<%@ attribute name="id" type="java.lang.Long" required="true" description="当前要展示的组织机构的名字" %>
<%@ attribute name="showParents" type="java.lang.Boolean" required="false" description="是否显示父亲 默认包含" %>
<%@ attribute name="includeRoot" type="java.lang.Boolean" required="false" description="是否包含根 默认true" %>
<%!private OrganizationService organizationService;%>
<%

    if(showParents == null) {
        showParents = true;
    }
    if(includeRoot == null) {
        includeRoot = true;
    }

    if(organizationService == null) {
        organizationService = SpringUtils.getBean(OrganizationService.class);
    }

    Organization organization = organizationService.findOne(id);

    List<String> names = Lists.newArrayList();
    int index = 0;
    while (organization != null) {
        names.add(organization.getName());

        if(showParents == false) {
            break;
        }

        organization = organizationService.findOne(organization.getParentId());

        if(includeRoot == false && organization.isRoot()) {
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
