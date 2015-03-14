<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader title="新版本" index="true"/>
<%@include file="headermenu.jsp"%>
<!-- 内容开始 -->
  <div id="main" class="clearfix row">
      <div id="left-side" class="col-sm-1 col-md-2">
      		<!-- 左侧导航开始 -->
			<div class="panel-group hide-sm nav-sitemenu col-md-2" id="nav-accordion">
  
					  <div class="panel panel-default">
							    <div class="panel-heading">
							      <h6 class="panel-title">
							        <span class="badge badge-info">3</span>
							        <a class="accordion-toggle" data-toggle="collapse" data-parent="#nav-accordion" href="#nav-panel-1">
							          
							          <i class="icon-fixed-width icon-globe"></i>
							          
							          	用户管理
							          
							        </a>
							      </h6>
							    </div>
							    <div id="nav-panel-1" class="list-group panel-collapse collapse">
							      
							      <a href="/admin/sys/user/online"  target="iframe" class="list-group-item">
							        <i class="icon-fixed-width icon-globe"></i>
							        用户在线列表</span>
							      </a>
							    </div>
					  </div>
			 </div>
		</div>
		<!-- 左侧导航结束 -->
	<div id="content-block" class="col-sm-11 col-md-10">	
		<!-- 右侧内容开始 -->	
		<iframe id="iframe" name="iframe" src="index.jhtml" style="overflow:visible;" scrolling="yes" frameborder="no" width="100%" height="650"></iframe>
		<!-- 右侧内容结束 -->
	
	
	<script type="text/javascript"> 
		var leftWidth = 160; // 左侧窗口大小
		var tabTitleHeight = 33; // 页签的高度
		var htmlObj = $("html"), mainObj = $("#main");
		var headerObj = $("#header"), footerObj = $("#footer");
		var frameObj = $("#left, #openClose, #right, #right iframe");
		function wSize(){
			var minHeight = 500, minWidth = 980;
			var strs = getWindowSize().toString().split(",");
			htmlObj.css({"overflow-x":strs[1] < minWidth ? "auto" : "hidden", "overflow-y":strs[0] < minHeight ? "auto" : "hidden"});
			mainObj.css("width",strs[1] < minWidth ? minWidth - 10 : "auto");
			frameObj.height((strs[0] < minHeight ? minHeight : strs[0]) - headerObj.height() - footerObj.height() - (strs[1] < minWidth ? 42 : 28));
			$("#openClose").height($("#openClose").height() - 5);// <c:if test="${tabmode eq '1'}"> 
			$(".jericho_tab iframe").height($("#right").height() - tabTitleHeight); // </c:if>
			wSizeWidth();
		}
		function wSizeWidth(){
			if (!$("#openClose").is(":hidden")){
				var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
				$("#right").width($("#content").width()- leftWidth - $("#openClose").width() -5);
			}else{
				$("#right").width("100%");
			}
		}// <c:if test="${tabmode eq '1'}"> 
		function openCloseClickCallBack(b){
			$.fn.jerichoTab.resize();
		} // </c:if>
	</script>
		
<es:contentFooter/>
