<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="menu">
    <c:forEach items="${menus}" var="m">
        <div>
            <h3><a href="#">${m.name}</a></h3>
            <div class="submenu">
                <c:forEach items="${m.children}" var="c">
                    <ul>
                        <es:submenu menu="${c}"/>
                    </ul>
                </c:forEach>
            </div>
        </div>
    </c:forEach>
</div>
