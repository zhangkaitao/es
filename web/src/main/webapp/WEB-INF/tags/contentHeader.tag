<%@tag pageEncoding="UTF-8"%>
<%@attribute name="title" type="java.lang.String" required="false" %>
<!DOCTYPE html>
<!--[if lt IE 7 ]><html class="ie ie6" lang="en"> <![endif]-->
<!--[if IE 7 ]><html class="ie ie7" lang="en"> <![endif]-->
<!--[if IE 8 ]><html class="ie ie8" lang="en"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!-->
<html lang="en" xmlns="http://www.w3.org/1999/html"> <!--<![endif]-->

<head>

    <meta charset="utf-8">
    <!-- Always force latest IE render
    ing engine (even in intranet) & Chrome Frame -->
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">

    <meta http-equiv="Cache-Control" content="no-store" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="0" />

    <title>${title}</title>
    <%@include file="/WEB-INF/jsp/common/import-css.jspf"%>
    <script type="text/javascript">
        var currentURL = "${requestScope.currentURL}";
    </script>
</head>
