<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<c:if test="${empty header['container']}">
<es:contentHeader/>
</c:if>
<div class="breadcrumbs" id="breadcrumbs">
	<ul class="breadcrumb">
		<li>
			<i class="icon-home home-icon"></i>
			<a href="#">首页</a>
		</li>
		<li>
			<a href="#">用户管理</a>
		</li>
		<li class="active">在线用户列表</li>
	</ul><!-- .breadcrumb -->
</div>
<div class="page-content">
	<div class="row">
		<div class="col-sm-12">
			<div class="tabbable">
			<ul class="nav nav-tabs">
		        <li ${empty param['search.userId_eq'] and empty param['search.userId_gt'] ? 'class="active"' : ''}>
		            <a href="${ctx}/admin/sys/user/online">
		                <i class="icon-table"></i>
		                所有用户列表
		            </a>
		        </li>
		
		        <li ${not empty param['search.userId_gt'] ? 'class="active"' : ''}>
		            <a href="${ctx}/admin/sys/user/online?search.userId_gt=0">
		                <i class="icon-table"></i>
		                登录用户列表
		            </a>
		        </li>
		        <li ${not empty param['search.userId_eq'] ? 'class="active"' : ''}>
		            <a href="${ctx}/admin/sys/user/online?search.userId_eq=0">
		                <i class="icon-table"></i>
		                匿名游客列表
		            </a>
		        </li>
		        <li class="dropdown">
						<a data-toggle="dropdown" class="dropdown-toggle" href="#">
							Dropdown &nbsp;
							<i class="icon-caret-down bigger-110 width-auto"></i>
						</a>

						<ul class="dropdown-menu dropdown-info">
							<li>
								<a data-toggle="tab" href="#dropdown1">@fat</a>
							</li>

							<li>
								<a data-toggle="tab" href="#dropdown2">@mdo</a>
							</li>
						</ul>
					</li>
		    </ul>
		    <es:showMessage/>
				<div class="tab-content">
					<div id="home" class="tab-pane in active">
						<div class="row page-header">
							<span class="col-sm-7">
								<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">
									<button class="btn btn-sm btn-success">
										<i class="icon-file-alt bigger-120"></i> 新增
									</button>

									<button class="btn btn-sm btn-info">
										<i class="icon-edit bigger-120"></i> 编辑
									</button>

									<button class="btn btn-sm btn-danger">
										<i class="icon-trash bigger-120"></i> 删除
									</button>

									<button class="btn btn-sm btn-warning">
										<i class="icon-flag bigger-120"></i> 更多
									</button>
								</div>
							</span>
							<span class="col-sm-5">
								<%@include file="searchForm.jsp" %>
							</span>
								<div class="visible-xs visible-sm hidden-md hidden-lg">
									<div class="inline position-relative">
										<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown">
											<i class="icon-cog icon-only bigger-110"></i>
										</button>

										<ul class="dropdown-menu dropdown-only-icon dropdown-yellow pull-right dropdown-caret dropdown-close">
											<li>
												<a href="#" class="tooltip-info" data-rel="tooltip" title="View">
													<span class="blue">
														<i class="icon-zoom-in bigger-120"></i>
													</span>
												</a>
											</li>

											<li>
												<a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">
													<span class="green">
														<i class="icon-edit bigger-120"></i>
													</span>
												</a>
											</li>

											<li>
												<a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
													<span class="red">
														<i class="icon-trash bigger-120"></i>
													</span>
												</a>
											</li>
										</ul>
									</div>
								</div>
						</div><!-- /.page-header -->
					
					<%@include file="listTable.jsp"%>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<c:if test="${empty header['container']}">
<es:contentFooter/>
<%@include file="/WEB-INF/jsp/common/admin/import-sys-js.jspf"%>
<script type="text/javascript">
    function callback() {
        $(".scroll-pane").niceScroll({domfocus:true, hidecursordelay: 2000});
        $.sys.user.initOnlineListButton();
    }
    $(function() {
        callback();
    });
</script>
</c:if>
