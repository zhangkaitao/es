<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div class="panel">

    <ul class="nav nav-tabs">
        <li class="active">
            <a href="${ctx}/admin/sys/group/${group.id}/batch/append?BackURL=<es:BackURL/>">
                <i class="icon-file-alt"></i>
                ${group.type.info}-批量新增
            </a>
        </li>

        <li>
            <a href="<es:BackURL/>" class="btn btn-link">
                <i class="icon-reply"></i>
                返回
            </a>
        </li>
    </ul>

    <form id="editForm" method="post" class="form-horizontal">

        <es:BackURL hiddenInput="true"/>

        <div class="control-group">
            <label for="startIds" class="control-label">用户编号(区间)</label>
            <div class="controls">
                <input type="text" id="startIds" placeholder="起始编号"/>
                到
                <input type="text" id="endIds" placeholder="结束编号"/>
                &nbsp;
                <a class="btn btn-success btn-append-range">添加</a>
                <div id="selectedRange" style="line-height: 30px;">

                </div>
            </div>
        </div>


        <div class="control-group">
            <label for="ids" class="control-label">
                用户编号(批量)<br/>
                <span class="muted">多个逗号分隔</span>
            </label>
            <div class="controls">
                <div style="float: left;">
                    <textarea id="ids" name="ids" rows="3" cols="20" placeholder="请输入用户编号，多个逗号分隔"></textarea>
                </div>
                <div id="search-username" class="accordion-body collapse" style="float: left;margin-left: 20px">
                    <input type="text" id="username" class="input-medium" placeholder="输入用户名 检索用户编码">
                </div>

                <span style="margin-left: 20px;display: inline-block"
                      data-toggle="tooltip" data-placement="bottom" data-html="true"
                      title="根据用户名检索用户编号！<br/>检索到的用户编号会自动添加到【用户编号(批量)】中">
                    <a class="btn btn-link" data-toggle="collapse" href="#search-username">
                        <i class="icon-search"></i>
                    </a>
                </span>
            </div>
        </div>


        <div class="control-group">
            <label class="control-label"></label>
            <div class="controls">
                <button type="submit" class="btn btn-primary">
                    <i class="icon-file-alt"></i>
                    批量新增
                </button>
                <a href="<es:BackURL/>" class="btn">
                    <i class="icon-reply"></i>
                    返回
                </a>
            </div>
        </div>


    </form>
</div>
<es:contentFooter/>

<script type="text/javascript">
    $(function() {
        var numberPattern = /^[0-9]+$/
        $(".btn-append-range").click(function() {
            var startIds = $("#startIds");
            var endIds = $("#endIds");
            if(!numberPattern.test(startIds.val()) || !numberPattern.test(endIds.val())) {
                $.app.alert({
                    message : "用户编号(区间)，开始编号和结束编号必须填写，且必须是正整数！"
                });
                return;
            }
            if(parseInt(startIds.val()) > parseInt(endIds.val())) {
                $.app.alert({
                    message : "用户编号(区间)，开始编号必须小于等于结束编号！"
                });
                return;
            }

            $("#selectedRange").append(startIds.clone().attr("name", startIds.attr("id")).removeAttr("id").attr("readonly", true));
            $("#selectedRange").append(" 到 ");
            $("#selectedRange").append(endIds.clone().attr("name", endIds.attr("id")).removeAttr("id").attr("readonly", true));
        });


        var $username = $("#username");
        $.app.initAutocomplete({
            input : $username,
            source : "${ctx}/admin/sys/user/ajax/autocomplete",
            select : function(event, ui) {
                var $ids = $("#ids");
                var ids = $ids.val();
                ids = (ids ? ids : "") + (ids ? "," : "") + ui.item.value;
                $ids.val(ids);
                $username.val("");
                return false;
            }
        });
    });


</script>
