<%--
 分页格式
   首页 <<   1   2   3   4   5   6   7   8   9   10  11>  >> 尾页
   首页 <<   1   2   3   4   5   6   7   8   9   ... 11  12 >  >> 尾页
   首页 <<   1   2  ...  4   5   6   7   8   9   10 ... 12  13 >  >> 尾页
   首页 <<   1   2  ...  5   6   7   8   9   10  11  12  13 >  >> 尾页
   首页 <<   1   2  ...  5   6   7   8   9   10  11  ... 13  14 >  >> 尾页
   首页 <<   1   2  ...  5   6   7   8   9   10  11  ...   21  22 >  >> 尾页

--%>
<%@tag pageEncoding="UTF-8" description="分页"%>
<%@ attribute name="page" type="org.springframework.data.domain.Page"
	required="true" description="分页"%>
<%@ attribute name="pageSize" type="java.lang.Integer" required="false"
	description="每页大小"%>
<%@ attribute name="simple" type="java.lang.Boolean" required="false"
	description="是否简单风格"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="es" tagdir="/WEB-INF/tags"%>

<c:if test="${empty pageSize}">
	<c:set var="pageSize" value="${page.size}" />
</c:if>

<c:set var="displaySize" value="2" />
<c:set var="current" value="${page.number + 1}" />

<c:set var="begin" value="${current - displaySize}" />
<c:if test="${begin <= displaySize}">
	<c:set var="begin" value="${1}" />
</c:if>
<c:set var="end" value="${current + displaySize}" />
<c:if test="${end > page.totalPages - displaySize}">
	<c:set var="end" value="${page.totalPages - displaySize}" />
</c:if>
<c:if test="${end < 0 or page.totalPages < displaySize * 4}">
	<c:set var="end" value="${page.totalPages}" />
</c:if>
<!--  两种样式，此处采用统一的排版样式，方便页面显示均匀
     <ul  class="pagination">
     -->
<ul class="pagination pagination-sm pagination-left pagination-inline">
	<c:choose>
		<c:when test="${page.firstPage}">
			<li class="disabled"><a title="首页">首页</a></li>
			<li class="disabled"><a title="上一页">&lt;&lt;</a></li>
		</c:when>
		<c:otherwise>
			<li><a href="#"
				onclick="$.table.turnPage('${pageSize}', 1, this);" title="首页">首页</a></li>
			<li><a href="#"
				onclick="$.table.turnPage('${pageSize}', ${current - 1}, this);"
				title="上一页">&lt;&lt;</a></li>
		</c:otherwise>
	</c:choose>

	<c:forEach begin="1" end="${begin == 1 ? 0 : 2}" var="i">
		<li <c:if test="${current == i}"> class="active"</c:if>><a
			href="#" onclick="$.table.turnPage('${pageSize}', ${i}, this);"
			title="第${i}页">${i}</a></li>
	</c:forEach>

	<c:if test="${begin > displaySize + 1}">
		<li><a>...</a></li>
	</c:if>

	<c:forEach begin="${begin}" end="${end}" var="i">
		<li <c:if test="${current == i}"> class="active"</c:if>><a
			href="#" onclick="$.table.turnPage('${pageSize}', ${i}, this);"
			title="第${i}页">${i}</a></li>
	</c:forEach>


	<c:if test="${end < page.totalPages - displaySize}">
		<li><a>...</a></li>
	</c:if>

	<c:forEach
		begin="${end < page.totalPages ? page.totalPages - 1 : page.totalPages + 1}"
		end="${page.totalPages}" var="i">
		<li <c:if test="${current == i}"> class="active"</c:if>><a
			href="#" onclick="$.table.turnPage('${pageSize}', ${i}, this);"
			title="第${i}页">${i}</a></li>
	</c:forEach>

	<c:choose>
		<c:when test="${page.lastPage}">
			<li class="disabled"><a title="下一页">&gt;&gt;</a></li>
			<li class="disabled"><a title="尾页"><span>尾页</span></a></li>
		</c:when>
		<c:otherwise>
			<li><a href="#"
				onclick="$.table.turnPage('${pageSize}', ${current + 1}, this);"
				title="下一页">&gt;&gt;</a></li>
			<li><a href="#"
				onclick="$.table.turnPage('${pageSize}', ${page.totalPages}, this);"
				title="尾页"><span>尾页</span></a></li>
		</c:otherwise>
	</c:choose>
	<li><span>共计<span class="text-success">${page.totalElements}</span>
			条记录
	</span></li>
	<li><span class="page-info">[共${page.totalPages}页]</span></li>
</ul>

<div class="form-actions well well-sm">
	<input type="hidden" id="action" name="action" value="" /> <input
		type="hidden" id="select-across" name="select_across" value="" />
	<div class="btn-group clearfix dropup">
		<a class="dropdown-toggle btn btn-success" data-toggle="dropdown" href="#">
			<i class="icon-wrench icon-white"></i> 
			<span class="action-counter">${page.totalElements} 个中  
			<span class="checknum">0</span> 个被选</span> 
			<span class="all" style="display: none;">选中了 ${page.totalElements} 个</span>
			<span class="caret"></span></a>
		<ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
			<li><a onclick="" class="disabled"><i class="icon-remove"></i>
					删除</a></li>
			<li><a onclick="" id="check-all"><i class="icon-remove"></i>
					全选</a></li>
			<li><a onclick="" id="reverse-all" ><i class="icon-remove"></i>
					反选</a></li>
		</ul>
	</div>
	<a class="question btn btn-default" href="javascript:;"
		style="display: none;" title="点击此处选择所有页面中包含的对象。">选中所有的
		${page.totalElements} 数据</a> <a class="clear btn btn-default"
		href="javascript:;" style="display: none;">清除选中</a>
</div>
