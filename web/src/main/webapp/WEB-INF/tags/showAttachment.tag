<%@ tag import="com.sishuok.es.common.utils.ImagesUtils" %>
<%@ tag import="org.apache.commons.io.FilenameUtils" %>
<%@ tag import="org.apache.commons.lang3.StringUtils" %>
<%@ tag import="java.net.URLEncoder" %>
<%@ tag pageEncoding="UTF-8" description="显示附件" %>
<%@ attribute name="filename" type="java.lang.String" required="true" description="附件地址" %>
<%@ attribute name="noAttachmentTitle" type="java.lang.String" required="false" description="没有附件时显示的标题" %>
<%@ attribute name="isDownload" type="java.lang.Boolean" required="false" description="是否直接下载，默认false" %>
<%@ attribute name="showImage" type="java.lang.Boolean" required="false" description="如果是图片是否显示" %>
<%@ attribute name="width" type="java.lang.String" required="false" description="图片的高度，默认160" %>
<%@ attribute name="height" type="java.lang.String" required="false" description="图片的宽度，默认120" %>

<%@ attribute name="left" type="java.lang.String" required="false" description="css sprite 距离左边" %>
<%@ attribute name="top" type="java.lang.String" required="false" description="css sprite 距离上边" %>

<%@ attribute name="style" type="java.lang.String" required="false" description="css样式" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:choose>
    <c:when test="${not empty filename}">
        <%

            String displayFileName = FilenameUtils.getName(filename);
            displayFileName = displayFileName.substring(displayFileName.indexOf("_") + 1);

            if(Boolean.TRUE.equals(isDownload)) {
                filename = URLEncoder.encode(filename, "UTF-8");
            }

            if(showImage == null) {
                showImage = Boolean.FALSE;
            }

            String ctx = request.getContextPath();

            if(showImage && ImagesUtils.isImage(filename)) {
                if(StringUtils.isEmpty(width)) {
                    width = "160";
                }
                if(StringUtils.isEmpty(height)) {
                    height = "120";
                }
                boolean isCssSprite = StringUtils.isNotEmpty(left) && StringUtils.isNotEmpty(top);

                String templatePrefix = "<a href='%1$s/%2$s' target='_blank'>";
                if(Boolean.TRUE.equals(isDownload)) {
                    templatePrefix = "<a href='%1$s/download?filename=%2$s' target='_blank'>";
                }
                String templateSuffix = "</a>";

                String template = templatePrefix + "<img src='%1$s/%2$s' style='display:inline-block;vertical-align: middle;height:%3$spx;width:%4$spx;%5$s'/>" + templateSuffix;
                if(isCssSprite) {
                    template = "<a href='%1$s/%2$s' target='_blank'><i style='display:inline-block;vertical-align: middle;background:url(%1$s/%2$s) -%3$spx -%4$spx no-repeat;height:%5$spx;width:%6$spx;%7$s'></i></a>";
                }
                if(isCssSprite) {
                    out.write(String.format(
                            template,
                            ctx, filename,
                            left, top,
                            height, width,
                            style));
                } else {
                    out.write(String.format(
                            template,
                            ctx, filename,
                            height, width,
                            style));
                }

            } else {
                String template = "<a href='%s/%s' target='_blank'>%s</a>";
                if (Boolean.TRUE.equals(isDownload)) {
                    template = "<a href='%s/download?filename=%s' target='_blank'>%s</a>";
                }
                out.write(String.format(template, ctx, filename, displayFileName));
            }
        %>
    </c:when>
    <c:otherwise>
        ${noAttachmentTitle}
    </c:otherwise>

</c:choose>
