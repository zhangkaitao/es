<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf" %>
<es:contentHeader/>
<%@include file="/WEB-INF/jsp/common/import-calendar-css.jspf"%>

<style>
    body {
        margin-top: 40px;
        text-align: center;
        font-size: 14px;
        font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
    }

    .fc-button-add {
        margin-right: 10px!important;
    }

    #loading {
        position: absolute;
        top: 5px;
        right: 5px;
    }

    .ui-dialog {
        overflow: visible!important;
    }
    .ui-dialog-content {
        overflow: hidden!important;
        overflow: visible!important;
    }

    #calendar {
        width: 800px;
        margin: 0 auto;
    }
</style>

<div id='calendar'></div>

<es:contentFooter/>
<%@include file="/WEB-INF/jsp/common/import-calendar-js.jspf"%>
<script>
    $(document).ready(function() {

        var date = new Date();
        var d = date.getDate();
        var m = date.getMonth();
        var y = date.getFullYear();

        var calendar = $('#calendar').fullCalendar({
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            },
            events: "${ctx}/admin/personal/calendar/load",
            eventDrop: function(event, delta) {
                moveCalendar(event);
            },
            eventClick: function(event, delta) {
                viewCalendar(event);
            },
            loading: function(bool) {
                if (bool) $('#loading').show();
                else $('#loading').hide();
            },
            editable: true,
            selectable: true,
            selectHelper: true,
            select: function(start, end, allDay) {
                openNewCalendarForm(start, end);
                calendar.fullCalendar('unselect');
            }
        });

        $('span.fc-button-prev').before('<span class="fc-button fc-button-add fc-state-default fc-corner-left fc-corner-right">新增</span>');

        $(".fc-button-add").click(function() {
            openNewCalendarForm();
        });

        function openNewCalendarForm(start, end) {
            var url = "${ctx}/admin/personal/calendar/new";
            if(start) {
                start = $.fullCalendar.formatDate(start, "yyyy-MM-dd HH:mm:ss");
                end = $.fullCalendar.formatDate(end, "yyyy-MM-dd HH:mm:ss");
                url = url + "?start=" + start + "&end=" + end;
            }
            $.app.modalDialog("新增提醒事项", url, {
                width:370,
                height:430,
                ok : function(modal) {

                    var form = modal.find("#editForm");
                    if(!form.validationEngine('validate')) {
                        return false;
                    }
                    var url = "${ctx}/admin/personal/calendar/new";
                    $.post(url, form.serialize(), function() {
                        calendar.fullCalendar("refetchEvents");
                    });

                    return true;
                }
            });
        }

        function moveCalendar(event) {
            var url = "${ctx}/admin/personal/calendar/move";
            var id = event.id;
            var start = $.fullCalendar.formatDate(event.start, "yyyy-MM-dd HH:mm:ss");
            var end = $.fullCalendar.formatDate(event.end, "yyyy-MM-dd HH:mm:ss");
            url = url + "?id=" + id;
            url = url + "&start=" + start + "&end=" + end;

            $.post(url, function() {
                calendar.fullCalendar("refetchEvents");
            });
        }

        function viewCalendar(event) {
            var url = "${ctx}/admin/personal/calendar/view/" + event.id;
            $.app.modalDialog("查看提醒事项", url, {
                width:370,
                height:250,
                noTitle : false,
                okBtn : false,
                closeBtn : false
            });
        }
        $("body").on("click", ".btn-delete-calendar", function() {
            var $this = $(this);
            $.app.confirm({
                title : '确认删除提醒事项吗？',
                message : '确认删除提醒事项吗？',
                ok : function() {
                    var url = "${ctx}/admin/personal/calendar/delete?id=" + $this.data("id");
                    $.post(url, function() {
                        calendar.fullCalendar("refetchEvents");
                        $.app.closeModalDialog();
                    });
                }
            });

        });
    });

</script>
