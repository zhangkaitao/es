<%@ tag import="com.sun.imageio.plugins.common.ImageUtil" %>
<%@ tag import="com.sishuok.es.common.utils.ImagesUtils" %>
<%@ tag import="org.apache.commons.lang3.StringUtils" %>
<%@ tag import="org.apache.commons.io.FilenameUtils" %>
<%@ tag import="java.net.URLEncoder" %>
<%@ tag pageEncoding="UTF-8" description="显示附件" %>
<%@ attribute name="filename" type="java.lang.String" required="true" description="附件地址" %>
<%@ attribute name="noAttachmentTitle" type="java.lang.String" required="false" description="没有附件时显示的标题" %>
<%@ attribute name="isDownload" type="java.lang.Boolean" required="false" description="是否直接下载，默认false" %>
<%@ attribute name="showImage" type="java.lang.Boolean" required="false" description="如果是图片是否显示" %>
<%@ attribute name="width" type="java.lang.String" required="false" description="图片的高度，默认160" %>
<%@ attribute name="height" type="java.lang.String" required="false" description="图片的宽度，默认120" %>

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

                String template = "<a href='%1$s/%2$s' target='_blank'><img src='%1$s/%2$s' style='height:%3$spx;width:%4$spx;'/></a>";
                if(Boolean.TRUE.equals(isDownload)) {
                    template = "<a href='%1$s/download?filename=%2$s' target='_blank'><img src='%1$s/download?filename=%2$s' style='height:%3$spx;width:%4$spx;'/></a>";
                }

                out.write(String.format(
                        template,
                        ctx, filename, height, width));
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
