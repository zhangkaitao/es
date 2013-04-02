<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf" %>
<es:contentHeader/>


<ul class="nav nav-tabs">
    <li class="active">
        <a>
            <i class="icon-ok"></i>
            维护树成功
        </a>
    </li>
</ul>

<es:showMessage/>

<es:contentFooter/>
<script type="text/javascript">
    parent.window.frames['treeFrame'].window.location.reload();
</script>
