<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div data-table="table" class="panel">


    <ul class="nav nav-tabs">
        <li <c:if test="${empty param['search.show_eq']}">class="active"</c:if>>
            <a href="${ctx}/showcase/product/category">
                <i class="icon-table"></i>
                所有类别列表
            </a>
        </li>
        <li <c:if test="${param['search.show_eq'] eq 'true'}">class="active"</c:if>>
            <a href="${ctx}/showcase/product/category?search.show_eq=true">
                <i class="icon-table"></i>
                可显示的类别列表
            </a>
        </li>
        <li <c:if test="${param['search.show_eq'] eq 'false'}">class="active"</c:if>>
            <a href="${ctx}/showcase/product/category?search.show_eq=false">
                <i class="icon-table"></i>
                隐藏的类别列表
            </a>
        </li>
    </ul>


    <es:showMessage/>

    <div class="row-fluid tool ui-toolbar">
        <div class="span6">
            <div class="btn-group">
                <a class="btn btn-create">
                    <span class="icon-file"></span>
                    新&nbsp;增
                </a>

                <a id="update" class="btn btn-update">
                    <span class="icon-edit"></span>
                    修改
                </a>
                <a class="btn btn-batch-delete">
                    <span class="icon-trash"></span>
                    删除
                </a>
                <div class="btn-group">
                    <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="icon-wrench"></i>
                        更多操作
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                            <a class="btn btn-link reweight">
                                <i class="icon-cog"></i>
                                优化权重
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a class="btn btn-link create-product">
                                <i class="icon-file"></i>
                                新增选中类别的产品
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a class="btn btn-link list-product">
                                <i class="icon-table"></i>
                                查看选中类别的产品
                            </a>
                        </li>
                        <li>
                            <a class="btn btn-link list-all-product">
                                <i class="icon-table"></i>
                                查看所有产品
                            </a>
                        </li>

                    </ul>
                </div>
            </div>
        </div>
        <div class="span6">
            <%@include file="searchForm.jsp"%>
        </div>
    </div>

    <%@include file="listTable.jsp"%>

</div>
<es:contentFooter/>

<script type="text/javascript">
    $(function() {

        $.movable.initMovableReweight($(".reweight"), "${ctx}/showcase/product/category/reweight");

        $(".list-all-product").click(function() {
            window.location.href=
                    "${ctx}/showcase/product/product?fromCategory=1&BackURL=" + $.table.tableURL($(".table"));
        });

        $(".create-product").click(function() {

            var checkbox = $.table.getFirstSelectedCheckbox($(".table"));
            if(checkbox.size() == 0) {
                return;
            }
            var id = checkbox.val();
            window.location.href=
                    "${ctx}/showcase/product/product/category-"+ id + "/create" +
                            "?BackURL=" + $.table.tableURL($(".table"));
        });

        $(".list-product").click(function() {

            var checkbox = $.table.getAllSelectedCheckbox($(".table"));
            if(checkbox.size() == 0) {
                return;
            }
            var ids = $.app.joinVar(checkbox);
            window.location.href=
                    "${ctx}/showcase/product/product?search.category.id_in=" + ids +
                            "&fromCategory=1&BackURL=" + $.table.tableURL($(".table"));
        });

    });
</script>
