<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
	<es:contentHeader/>
					<div class="breadcrumbs" id="breadcrumbs">
						<ul class="breadcrumb">
							<li>
								<i class="icon-home home-icon"></i>
								<a href="#">首页</a>
							</li>
							<li class="active">控制台</li>
						</ul><!-- .breadcrumb -->

						<div class="nav-search" id="nav-search">
							<form class="form-search">
								<span class="input-icon">
									<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
									<i class="icon-search nav-search-icon"></i>
								</span>
							</form>
						</div><!-- #nav-search -->
					</div>
					<div class="page-content">
						<div class="page-header">
							<h1>
								控制台
								<small>
									<i class="icon-double-angle-right"></i>
									 查看
								</small>
							</h1>
						</div><!-- /.page-header -->
						
						<div class="row">
									<div class="col-xs-12">
										<div class="table-responsive">
											<div class="table-header">
												<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">
																<button class="btn btn-xs btn-success">
																	<i class="icon-ok bigger-120"></i>添加
																</button>

																<button class="btn btn-xs btn-info">
																	<i class="icon-edit bigger-120"></i>
																</button>

																<button class="btn btn-xs btn-danger">
																	<i class="icon-trash bigger-120"></i>
																</button>

																<button class="btn btn-xs btn-warning">
																	<i class="icon-flag bigger-120"></i>
																</button>
															</div>

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
											</div>
											<table id="sample-table-1" class="table table-striped table-bordered table-hover">
												<thead>
													<tr>
														<th class="center">
															<label>
																<input type="checkbox" class="ace" />
																<span class="lbl"></span>
															</label>
														</th>
														<th>Domain</th>
														<th>Price</th>
														<th class="hidden-480">Clicks</th>

														<th>
															<i class="icon-time bigger-110 hidden-480"></i>
															Update
														</th>
														<th class="hidden-480">Status</th>

														<th></th>
													</tr>
												</thead>

												<tbody>
													<tr>
														<td class="center">
															<label>
																<input type="checkbox" class="ace" />
																<span class="lbl"></span>
															</label>
														</td>

														<td>
															<a href="#">ace.com</a>
														</td>
														<td>$45</td>
														<td class="hidden-480">3,330</td>
														<td>Feb 12</td>

														<td class="hidden-480">
															<span class="label label-sm label-warning">Expiring</span>
														</td>

														<td>
															<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">
																<button class="btn btn-xs btn-success">
																	<i class="icon-ok bigger-120"></i>
																</button>

																<button class="btn btn-xs btn-info">
																	<i class="icon-edit bigger-120"></i>
																</button>

																<button class="btn btn-xs btn-danger">
																	<i class="icon-trash bigger-120"></i>
																</button>

																<button class="btn btn-xs btn-warning">
																	<i class="icon-flag bigger-120"></i>
																</button>
															</div>

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
														</td>
													</tr>

													<tr>
														<td class="center">
															<label>
																<input type="checkbox" class="ace" />
																<span class="lbl"></span>
															</label>
														</td>

														<td>
															<a href="#">base.com</a>
														</td>
														<td>$35</td>
														<td class="hidden-480">2,595</td>
														<td>Feb 18</td>

														<td class="hidden-480">
															<span class="label label-sm label-success">Registered</span>
														</td>

														<td>
															<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">
																<button class="btn btn-xs btn-success">
																	<i class="icon-ok bigger-120"></i>
																</button>

																<button class="btn btn-xs btn-info">
																	<i class="icon-edit bigger-120"></i>
																</button>

																<button class="btn btn-xs btn-danger">
																	<i class="icon-trash bigger-120"></i>
																</button>

																<button class="btn btn-xs btn-warning">
																	<i class="icon-flag bigger-120"></i>
																</button>
															</div>

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
														</td>
													</tr>

													<tr>
														<td class="center">
															<label>
																<input type="checkbox" class="ace" />
																<span class="lbl"></span>
															</label>
														</td>

														<td>
															<a href="#">max.com</a>
														</td>
														<td>$60</td>
														<td class="hidden-480">4,400</td>
														<td>Mar 11</td>

														<td class="hidden-480">
															<span class="label label-sm label-warning">Expiring</span>
														</td>

														<td>
															<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">
																<button class="btn btn-xs btn-success">
																	<i class="icon-ok bigger-120"></i>
																</button>

																<button class="btn btn-xs btn-info">
																	<i class="icon-edit bigger-120"></i>
																</button>

																<button class="btn btn-xs btn-danger">
																	<i class="icon-trash bigger-120"></i>
																</button>

																<button class="btn btn-xs btn-warning">
																	<i class="icon-flag bigger-120"></i>
																</button>
															</div>

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
														</td>
													</tr>

													<tr>
														<td class="center">
															<label>
																<input type="checkbox" class="ace" />
																<span class="lbl"></span>
															</label>
														</td>

														<td>
															<a href="#">best.com</a>
														</td>
														<td>$75</td>
														<td class="hidden-480">6,500</td>
														<td>Apr 03</td>

														<td class="hidden-480">
															<span class="label label-sm label-inverse arrowed-in">Flagged</span>
														</td>

														<td>
															<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">
																<button class="btn btn-xs btn-success">
																	<i class="icon-ok bigger-120"></i>
																</button>

																<button class="btn btn-xs btn-info">
																	<i class="icon-edit bigger-120"></i>
																</button>

																<button class="btn btn-xs btn-danger">
																	<i class="icon-trash bigger-120"></i>
																</button>

																<button class="btn btn-xs btn-warning">
																	<i class="icon-flag bigger-120"></i>
																</button>
															</div>

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
														</td>
													</tr>

													<tr>
														<td class="center">
															<label>
																<input type="checkbox" class="ace" />
																<span class="lbl"></span>
															</label>
														</td>

														<td>
															<a href="#">pro.com</a>
														</td>
														<td>$55</td>
														<td class="hidden-480">4,250</td>
														<td>Jan 21</td>

														<td class="hidden-480">
															<span class="label label-sm label-success">Registered</span>
														</td>

														<td>
															<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">
																<button class="btn btn-xs btn-success">
																	<i class="icon-ok bigger-120"></i>
																</button>

																<button class="btn btn-xs btn-info">
																	<i class="icon-edit bigger-120"></i>
																</button>

																<button class="btn btn-xs btn-danger">
																	<i class="icon-trash bigger-120"></i>
																</button>

																<button class="btn btn-xs btn-warning">
																	<i class="icon-flag bigger-120"></i>
																</button>
															</div>

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
														</td>
													</tr>
												</tbody>
											</table>
										</div><!-- /.table-responsive -->
									</div><!-- /span -->
								</div><!-- /row -->
								
								<div class="row">
									<div class="col-xs-12">
										<h3 class="header smaller lighter blue">jQuery dataTables</h3>
										<div class="table-responsive">
											<table id="sample-table-2" class="table table-striped table-bordered table-hover">
												<thead>
													<tr>
														<th class="center">
															<label>
																<input type="checkbox" class="ace" />
																<span class="lbl"></span>
															</label>
														</th>
														<th>Domain</th>
														<th>Price</th>
														<th class="hidden-480">Clicks</th>

														<th>
															<i class="icon-time bigger-110 hidden-480"></i>
															Update
														</th>
														<th class="hidden-480">Status</th>

														<th></th>
													</tr>
												</thead>

												<tbody>
													<tr>
														<td class="center">
															<label>
																<input type="checkbox" class="ace" />
																<span class="lbl"></span>
															</label>
														</td>

														<td>
															<a href="#">app.com</a>
														</td>
														<td>$45</td>
														<td class="hidden-480">3,330</td>
														<td>Feb 12</td>

														<td class="hidden-480">
															<span class="label label-sm label-warning">Expiring</span>
														</td>

														<td>
															<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
																<a class="blue" href="#">
																	<i class="icon-zoom-in bigger-130"></i>
																</a>

																<a class="green" href="#">
																	<i class="icon-pencil bigger-130"></i>
																</a>

																<a class="red" href="#">
																	<i class="icon-trash bigger-130"></i>
																</a>
															</div>

															<div class="visible-xs visible-sm hidden-md hidden-lg">
																<div class="inline position-relative">
																	<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown">
																		<i class="icon-caret-down icon-only bigger-120"></i>
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
														</td>
													</tr>

													<tr>
														<td class="center">
															<label>
																<input type="checkbox" class="ace" />
																<span class="lbl"></span>
															</label>
														</td>

														<td>
															<a href="#">base.com</a>
														</td>
														<td>$35</td>
														<td class="hidden-480">2,595</td>
														<td>Feb 18</td>

														<td class="hidden-480">
															<span class="label label-sm label-success">Registered</span>
														</td>

														<td>
															<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
																<a class="blue" href="#">
																	<i class="icon-zoom-in bigger-130"></i>
																</a>

																<a class="green" href="#">
																	<i class="icon-pencil bigger-130"></i>
																</a>

																<a class="red" href="#">
																	<i class="icon-trash bigger-130"></i>
																</a>
															</div>

															<div class="visible-xs visible-sm hidden-md hidden-lg">
																<div class="inline position-relative">
																	<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown">
																		<i class="icon-caret-down icon-only bigger-120"></i>
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
														</td>
													</tr>

													<tr>
														<td class="center">
															<label>
																<input type="checkbox" class="ace" />
																<span class="lbl"></span>
															</label>
														</td>

														<td>
															<a href="#">max.com</a>
														</td>
														<td>$60</td>
														<td class="hidden-480">4,400</td>
														<td>Mar 11</td>

														<td class="hidden-480">
															<span class="label label-sm label-warning">Expiring</span>
														</td>

														<td>
															<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
																<a class="blue" href="#">
																	<i class="icon-zoom-in bigger-130"></i>
																</a>

																<a class="green" href="#">
																	<i class="icon-pencil bigger-130"></i>
																</a>

																<a class="red" href="#">
																	<i class="icon-trash bigger-130"></i>
																</a>
															</div>

															<div class="visible-xs visible-sm hidden-md hidden-lg">
																<div class="inline position-relative">
																	<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown">
																		<i class="icon-caret-down icon-only bigger-120"></i>
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
														</td>
													</tr>

													<tr>
														<td class="center">
															<label>
																<input type="checkbox" class="ace" />
																<span class="lbl"></span>
															</label>
														</td>

														<td>
															<a href="#">best.com</a>
														</td>
														<td>$75</td>
														<td class="hidden-480">6,500</td>
														<td>Apr 03</td>

														<td class="hidden-480">
															<span class="label label-sm label-inverse arrowed-in">Flagged</span>
														</td>

														<td>
															<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
																<a class="blue" href="#">
																	<i class="icon-zoom-in bigger-130"></i>
																</a>

																<a class="green" href="#">
																	<i class="icon-pencil bigger-130"></i>
																</a>

																<a class="red" href="#">
																	<i class="icon-trash bigger-130"></i>
																</a>
															</div>

															<div class="visible-xs visible-sm hidden-md hidden-lg">
																<div class="inline position-relative">
																	<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown">
																		<i class="icon-caret-down icon-only bigger-120"></i>
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
														</td>
													</tr>

													<tr>
														<td class="center">
															<label>
																<input type="checkbox" class="ace" />
																<span class="lbl"></span>
															</label>
														</td>

														<td>
															<a href="#">pro.com</a>
														</td>
														<td>$55</td>
														<td class="hidden-480">4,250</td>
														<td>Jan 21</td>

														<td class="hidden-480">
															<span class="label label-sm label-success">Registered</span>
														</td>

														<td>
															<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
																<a class="blue" href="#">
																	<i class="icon-zoom-in bigger-130"></i>
																</a>

																<a class="green" href="#">
																	<i class="icon-pencil bigger-130"></i>
																</a>

																<a class="red" href="#">
																	<i class="icon-trash bigger-130"></i>
																</a>
															</div>

															<div class="visible-xs visible-sm hidden-md hidden-lg">
																<div class="inline position-relative">
																	<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown">
																		<i class="icon-caret-down icon-only bigger-120"></i>
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
														</td>
													</tr>

													<tr>
														<td class="center">
															<label>
																<input type="checkbox" class="ace" />
																<span class="lbl"></span>
															</label>
														</td>

														<td>
															<a href="#">team.com</a>
														</td>
														<td>$40</td>
														<td class="hidden-480">3,200</td>
														<td>Feb 09</td>

														<td class="hidden-480">
															<span class="label label-sm label-inverse arrowed-in">Flagged</span>
														</td>

														<td>
															<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
																<a class="blue" href="#">
																	<i class="icon-zoom-in bigger-130"></i>
																</a>

																<a class="green" href="#">
																	<i class="icon-pencil bigger-130"></i>
																</a>

																<a class="red" href="#">
																	<i class="icon-trash bigger-130"></i>
																</a>
															</div>

															<div class="visible-xs visible-sm hidden-md hidden-lg">
																<div class="inline position-relative">
																	<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown">
																		<i class="icon-caret-down icon-only bigger-120"></i>
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
														</td>
													</tr>

													<tr>
														<td class="center">
															<label>
																<input type="checkbox" class="ace" />
																<span class="lbl"></span>
															</label>
														</td>

														<td>
															<a href="#">up.com</a>
														</td>
														<td>$95</td>
														<td class="hidden-480">8,520</td>
														<td>Feb 22</td>

														<td class="hidden-480">
															<span class="label label-sm label-info arrowed arrowed-righ">Sold</span>
														</td>

														<td>
															<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
																<a class="blue" href="#">
																	<i class="icon-zoom-in bigger-130"></i>
																</a>

																<a class="green" href="#">
																	<i class="icon-pencil bigger-130"></i>
																</a>

																<a class="red" href="#">
																	<i class="icon-trash bigger-130"></i>
																</a>
															</div>

															<div class="visible-xs visible-sm hidden-md hidden-lg">
																<div class="inline position-relative">
																	<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown">
																		<i class="icon-caret-down icon-only bigger-120"></i>
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
														</td>
													</tr>

													<tr>
														<td class="center">
															<label>
																<input type="checkbox" class="ace" />
																<span class="lbl"></span>
															</label>
														</td>

														<td>
															<a href="#">view.com</a>
														</td>
														<td>$45</td>
														<td class="hidden-480">4,100</td>
														<td>Mar 12</td>

														<td class="hidden-480">
															<span class="label label-sm label-success">Registered</span>
														</td>

														<td>
															<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
																<a class="blue" href="#">
																	<i class="icon-zoom-in bigger-130"></i>
																</a>

																<a class="green" href="#">
																	<i class="icon-pencil bigger-130"></i>
																</a>

																<a class="red" href="#">
																	<i class="icon-trash bigger-130"></i>
																</a>
															</div>

															<div class="visible-xs visible-sm hidden-md hidden-lg">
																<div class="inline position-relative">
																	<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown">
																		<i class="icon-caret-down icon-only bigger-120"></i>
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
														</td>
													</tr>

													<tr>
														<td class="center">
															<label>
																<input type="checkbox" class="ace" />
																<span class="lbl"></span>
															</label>
														</td>

														<td>
															<a href="#">nice.com</a>
														</td>
														<td>$38</td>
														<td class="hidden-480">3,940</td>
														<td>Feb 12</td>

														<td class="hidden-480">
															<span class="label label-sm label-info arrowed arrowed-righ">Sold</span>
														</td>

														<td>
															<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
																<a class="blue" href="#">
																	<i class="icon-zoom-in bigger-130"></i>
																</a>

																<a class="green" href="#">
																	<i class="icon-pencil bigger-130"></i>
																</a>

																<a class="red" href="#">
																	<i class="icon-trash bigger-130"></i>
																</a>
															</div>

															<div class="visible-xs visible-sm hidden-md hidden-lg">
																<div class="inline position-relative">
																	<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown">
																		<i class="icon-caret-down icon-only bigger-120"></i>
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
														</td>
													</tr>

													<tr>
														<td class="center">
															<label>
																<input type="checkbox" class="ace" />
																<span class="lbl"></span>
															</label>
														</td>

														<td>
															<a href="#">fine.com</a>
														</td>
														<td>$25</td>
														<td class="hidden-480">2,983</td>
														<td>Apr 01</td>

														<td class="hidden-480">
															<span class="label label-sm label-warning">Expiring</span>
														</td>

														<td>
															<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
																<a class="blue" href="#">
																	<i class="icon-zoom-in bigger-130"></i>
																</a>

																<a class="green" href="#">
																	<i class="icon-pencil bigger-130"></i>
																</a>

																<a class="red" href="#">
																	<i class="icon-trash bigger-130"></i>
																</a>
															</div>

															<div class="visible-xs visible-sm hidden-md hidden-lg">
																<div class="inline position-relative">
																	<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown">
																		<i class="icon-caret-down icon-only bigger-120"></i>
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
														</td>
													</tr>

													<tr>
														<td class="center">
															<label>
																<input type="checkbox" class="ace" />
																<span class="lbl"></span>
															</label>
														</td>

														<td>
															<a href="#">good.com</a>
														</td>
														<td>$50</td>
														<td class="hidden-480">6,500</td>
														<td>Feb 02</td>

														<td class="hidden-480">
															<span class="label label-sm label-inverse arrowed-in">Flagged</span>
														</td>

														<td>
															<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
																<a class="blue" href="#">
																	<i class="icon-zoom-in bigger-130"></i>
																</a>

																<a class="green" href="#">
																	<i class="icon-pencil bigger-130"></i>
																</a>

																<a class="red" href="#">
																	<i class="icon-trash bigger-130"></i>
																</a>
															</div>

															<div class="visible-xs visible-sm hidden-md hidden-lg">
																<div class="inline position-relative">
																	<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown">
																		<i class="icon-caret-down icon-only bigger-120"></i>
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
														</td>
													</tr>

													<tr>
														<td class="center">
															<label>
																<input type="checkbox" class="ace" />
																<span class="lbl"></span>
															</label>
														</td>

														<td>
															<a href="#">great.com</a>
														</td>
														<td>$55</td>
														<td class="hidden-480">6,400</td>
														<td>Feb 24</td>

														<td class="hidden-480">
															<span class="label label-sm label-success">Registered</span>
														</td>

														<td>
															<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
																<a class="blue" href="#">
																	<i class="icon-zoom-in bigger-130"></i>
																</a>

																<a class="green" href="#">
																	<i class="icon-pencil bigger-130"></i>
																</a>

																<a class="red" href="#">
																	<i class="icon-trash bigger-130"></i>
																</a>
															</div>

															<div class="visible-xs visible-sm hidden-md hidden-lg">
																<div class="inline position-relative">
																	<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown">
																		<i class="icon-caret-down icon-only bigger-120"></i>
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
														</td>
													</tr>

													<tr>
														<td class="center">
															<label>
																<input type="checkbox" class="ace" />
																<span class="lbl"></span>
															</label>
														</td>

														<td>
															<a href="#">shine.com</a>
														</td>
														<td>$25</td>
														<td class="hidden-480">2,200</td>
														<td>Apr 01</td>

														<td class="hidden-480">
															<span class="label label-sm label-inverse arrowed-in">Flagged</span>
														</td>

														<td>
															<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
																<a class="blue" href="#">
																	<i class="icon-zoom-in bigger-130"></i>
																</a>

																<a class="green" href="#">
																	<i class="icon-pencil bigger-130"></i>
																</a>

																<a class="red" href="#">
																	<i class="icon-trash bigger-130"></i>
																</a>
															</div>

															<div class="visible-xs visible-sm hidden-md hidden-lg">
																<div class="inline position-relative">
																	<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown">
																		<i class="icon-caret-down icon-only bigger-120"></i>
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
														</td>
													</tr>

													<tr>
														<td class="center">
															<label>
																<input type="checkbox" class="ace" />
																<span class="lbl"></span>
															</label>
														</td>

														<td>
															<a href="#">rise.com</a>
														</td>
														<td>$42</td>
														<td class="hidden-480">3,900</td>
														<td>Feb 01</td>

														<td class="hidden-480">
															<span class="label label-sm label-info arrowed arrowed-righ">Sold</span>
														</td>

														<td>
															<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
																<a class="blue" href="#">
																	<i class="icon-zoom-in bigger-130"></i>
																</a>

																<a class="green" href="#">
																	<i class="icon-pencil bigger-130"></i>
																</a>

																<a class="red" href="#">
																	<i class="icon-trash bigger-130"></i>
																</a>
															</div>

															<div class="visible-xs visible-sm hidden-md hidden-lg">
																<div class="inline position-relative">
																	<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown">
																		<i class="icon-caret-down icon-only bigger-120"></i>
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
														</td>
													</tr>

													<tr>
														<td class="center">
															<label>
																<input type="checkbox" class="ace" />
																<span class="lbl"></span>
															</label>
														</td>

														<td>
															<a href="#">above.com</a>
														</td>
														<td>$35</td>
														<td class="hidden-480">3,420</td>
														<td>Mar 12</td>

														<td class="hidden-480">
															<span class="label label-sm label-warning">Expiring</span>
														</td>

														<td>
															<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
																<a class="blue" href="#">
																	<i class="icon-zoom-in bigger-130"></i>
																</a>

																<a class="green" href="#">
																	<i class="icon-pencil bigger-130"></i>
																</a>

																<a class="red" href="#">
																	<i class="icon-trash bigger-130"></i>
																</a>
															</div>

															<div class="visible-xs visible-sm hidden-md hidden-lg">
																<div class="inline position-relative">
																	<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown">
																		<i class="icon-caret-down icon-only bigger-120"></i>
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
														</td>
													</tr>

													<tr>
														<td class="center">
															<label>
																<input type="checkbox" class="ace" />
																<span class="lbl"></span>
															</label>
														</td>

														<td>
															<a href="#">share.com</a>
														</td>
														<td>$30</td>
														<td class="hidden-480">3,200</td>
														<td>Feb 11</td>

														<td class="hidden-480">
															<span class="label label-sm label-info arrowed arrowed-righ">Sold</span>
														</td>

														<td>
															<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
																<a class="blue" href="#">
																	<i class="icon-zoom-in bigger-130"></i>
																</a>

																<a class="green" href="#">
																	<i class="icon-pencil bigger-130"></i>
																</a>

																<a class="red" href="#">
																	<i class="icon-trash bigger-130"></i>
																</a>
															</div>

															<div class="visible-xs visible-sm hidden-md hidden-lg">
																<div class="inline position-relative">
																	<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown">
																		<i class="icon-caret-down icon-only bigger-120"></i>
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
														</td>
													</tr>

													<tr>
														<td class="center">
															<label>
																<input type="checkbox" class="ace" />
																<span class="lbl"></span>
															</label>
														</td>

														<td>
															<a href="#">fair.com</a>
														</td>
														<td>$35</td>
														<td class="hidden-480">3,900</td>
														<td>Mar 26</td>

														<td class="hidden-480">
															<span class="label label-sm label-inverse arrowed-in">Flagged</span>
														</td>

														<td>
															<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
																<a class="blue" href="#">
																	<i class="icon-zoom-in bigger-130"></i>
																</a>

																<a class="green" href="#">
																	<i class="icon-pencil bigger-130"></i>
																</a>

																<a class="red" href="#">
																	<i class="icon-trash bigger-130"></i>
																</a>
															</div>

															<div class="visible-xs visible-sm hidden-md hidden-lg">
																<div class="inline position-relative">
																	<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown">
																		<i class="icon-caret-down icon-only bigger-120"></i>
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
														</td>
													</tr>

													<tr>
														<td class="center">
															<label>
																<input type="checkbox" class="ace" />
																<span class="lbl"></span>
															</label>
														</td>

														<td>
															<a href="#">year.com</a>
														</td>
														<td>$48</td>
														<td class="hidden-480">3,990</td>
														<td>Feb 15</td>

														<td class="hidden-480">
															<span class="label label-sm label-warning">Expiring</span>
														</td>

														<td>
															<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
																<a class="blue" href="#">
																	<i class="icon-zoom-in bigger-130"></i>
																</a>

																<a class="green" href="#">
																	<i class="icon-pencil bigger-130"></i>
																</a>

																<a class="red" href="#">
																	<i class="icon-trash bigger-130"></i>
																</a>
															</div>

															<div class="visible-xs visible-sm hidden-md hidden-lg">
																<div class="inline position-relative">
																	<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown">
																		<i class="icon-caret-down icon-only bigger-120"></i>
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
														</td>
													</tr>

													<tr>
														<td class="center">
															<label>
																<input type="checkbox" class="ace" />
																<span class="lbl"></span>
															</label>
														</td>

														<td>
															<a href="#">day.com</a>
														</td>
														<td>$55</td>
														<td class="hidden-480">5,600</td>
														<td>Jan 29</td>

														<td class="hidden-480">
															<span class="label label-sm label-info arrowed arrowed-righ">Sold</span>
														</td>

														<td>
															<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
																<a class="blue" href="#">
																	<i class="icon-zoom-in bigger-130"></i>
																</a>

																<a class="green" href="#">
																	<i class="icon-pencil bigger-130"></i>
																</a>

																<a class="red" href="#">
																	<i class="icon-trash bigger-130"></i>
																</a>
															</div>

															<div class="visible-xs visible-sm hidden-md hidden-lg">
																<div class="inline position-relative">
																	<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown">
																		<i class="icon-caret-down icon-only bigger-120"></i>
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
														</td>
													</tr>

													<tr>
														<td class="center">
															<label>
																<input type="checkbox" class="ace" />
																<span class="lbl"></span>
															</label>
														</td>

														<td>
															<a href="#">light.com</a>
														</td>
														<td>$40</td>
														<td class="hidden-480">3,100</td>
														<td>Feb 17</td>

														<td class="hidden-480">
															<span class="label label-sm label-success">Registered</span>
														</td>

														<td>
															<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
																<a class="blue" href="#">
																	<i class="icon-zoom-in bigger-130"></i>
																</a>

																<a class="green" href="#">
																	<i class="icon-pencil bigger-130"></i>
																</a>

																<a class="red" href="#">
																	<i class="icon-trash bigger-130"></i>
																</a>
															</div>

															<div class="visible-xs visible-sm hidden-md hidden-lg">
																<div class="inline position-relative">
																	<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown">
																		<i class="icon-caret-down icon-only bigger-120"></i>
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
														</td>
													</tr>

													<tr>
														<td class="center">
															<label>
																<input type="checkbox" class="ace" />
																<span class="lbl"></span>
															</label>
														</td>

														<td>
															<a href="#">sight.com</a>
														</td>
														<td>$58</td>
														<td class="hidden-480">6,100</td>
														<td>Feb 19</td>

														<td class="hidden-480">
															<span class="label label-sm label-inverse arrowed-in">Flagged</span>
														</td>

														<td>
															<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
																<a class="blue" href="#">
																	<i class="icon-zoom-in bigger-130"></i>
																</a>

																<a class="green" href="#">
																	<i class="icon-pencil bigger-130"></i>
																</a>

																<a class="red" href="#">
																	<i class="icon-trash bigger-130"></i>
																</a>
															</div>

															<div class="visible-xs visible-sm hidden-md hidden-lg">
																<div class="inline position-relative">
																	<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown">
																		<i class="icon-caret-down icon-only bigger-120"></i>
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
														</td>
													</tr>

													<tr>
														<td class="center">
															<label>
																<input type="checkbox" class="ace" />
																<span class="lbl"></span>
															</label>
														</td>

														<td>
															<a href="#">right.com</a>
														</td>
														<td>$50</td>
														<td class="hidden-480">4,400</td>
														<td>Apr 01</td>

														<td class="hidden-480">
															<span class="label label-sm label-warning">Expiring</span>
														</td>

														<td>
															<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
																<a class="blue" href="#">
																	<i class="icon-zoom-in bigger-130"></i>
																</a>

																<a class="green" href="#">
																	<i class="icon-pencil bigger-130"></i>
																</a>

																<a class="red" href="#">
																	<i class="icon-trash bigger-130"></i>
																</a>
															</div>

															<div class="visible-xs visible-sm hidden-md hidden-lg">
																<div class="inline position-relative">
																	<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown">
																		<i class="icon-caret-down icon-only bigger-120"></i>
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
														</td>
													</tr>

													<tr>
														<td class="center">
															<label>
																<input type="checkbox" class="ace" />
																<span class="lbl"></span>
															</label>
														</td>

														<td>
															<a href="#">once.com</a>
														</td>
														<td>$20</td>
														<td class="hidden-480">1,400</td>
														<td>Apr 04</td>

														<td class="hidden-480">
															<span class="label label-sm label-info arrowed arrowed-righ">Sold</span>
														</td>

														<td>
															<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
																<a class="blue" href="#">
																	<i class="icon-zoom-in bigger-130"></i>
																</a>

																<a class="green" href="#">
																	<i class="icon-pencil bigger-130"></i>
																</a>

																<a class="red" href="#">
																	<i class="icon-trash bigger-130"></i>
																</a>
															</div>

															<div class="visible-xs visible-sm hidden-md hidden-lg">
																<div class="inline position-relative">
																	<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown">
																		<i class="icon-caret-down icon-only bigger-120"></i>
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
														</td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>

							

								<div class="row">
									<div class="col-sm-6">
										<div class="widget-box transparent" id="recent-box">
											<div class="widget-header">
												<h4 class="lighter smaller">
													<i class="icon-rss orange"></i>
													最近
												</h4>

												<div class="widget-toolbar no-border">
													<ul class="nav nav-tabs" id="recent-tab">
														<li class="active">
															<a data-toggle="tab" href="#task-tab">任务</a>
														</li>

														<li>
															<a data-toggle="tab" href="#member-tab">会员</a>
														</li>

														<li>
															<a data-toggle="tab" href="#comment-tab">评论</a>
														</li>
													</ul>
												</div>
											</div>

											<div class="widget-body">
												<div class="widget-main padding-4">
													<div class="tab-content padding-8 overflow-visible">
														<div id="task-tab" class="tab-pane active">
															<h4 class="smaller lighter green">
																<i class="icon-list"></i>
																可拖拽排序列表
															</h4>

															<ul id="tasks" class="item-list">
																<li class="item-orange clearfix">
																	<label class="inline">
																		<input type="checkbox" class="ace" />
																		<span class="lbl"> 问答</span>
																	</label>

																	<div class="pull-right easy-pie-chart percentage" data-size="30" data-color="#ECCB71" data-percent="42">
																		<span class="percent">42</span>%
																	</div>
																</li>

																<li class="item-red clearfix">
																	<label class="inline">
																		<input type="checkbox" class="ace" />
																		<span class="lbl"> BUG修复</span>
																	</label>

																	<div class="pull-right action-buttons">
																		<a href="#" class="blue">
																			<i class="icon-pencil bigger-130"></i>
																		</a>

																		<span class="vbar"></span>

																		<a href="#" class="red">
																			<i class="icon-trash bigger-130"></i>
																		</a>

																		<span class="vbar"></span>

																		<a href="#" class="green">
																			<i class="icon-flag bigger-130"></i>
																		</a>
																	</div>
																</li>

																<li class="item-default clearfix">
																	<label class="inline">
																		<input type="checkbox" class="ace" />
																		<span class="lbl">添加新的特征</span>
																	</label>

																	<div class="inline pull-right position-relative dropdown-hover">
																		<button class="btn btn-minier bigger btn-primary">
																			<i class="icon-cog icon-only bigger-120"></i>
																		</button>

																		<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-caret dropdown-close pull-right">
																			<li>
																				<a href="#" class="tooltip-success" data-rel="tooltip" title="Mark&nbsp;as&nbsp;done">
																					<span class="green">
																						<i class="icon-ok bigger-110"></i>
																					</span>
																				</a>
																			</li>

																			<li>
																				<a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
																					<span class="red">
																						<i class="icon-trash bigger-110"></i>
																					</span>
																				</a>
																			</li>
																		</ul>
																	</div>
																</li>

																<li class="item-blue clearfix">
																	<label class="inline">
																		<input type="checkbox" class="ace" />
																		<span class="lbl"> 更新模版脚本</span>
																	</label>
																</li>

																<li class="item-grey clearfix">
																	<label class="inline">
																		<input type="checkbox" class="ace" />
																		<span class="lbl"> 添加新皮肤</span>
																	</label>
																</li>

																<li class="item-green clearfix">
																	<label class="inline">
																		<input type="checkbox" class="ace" />
																		<span class="lbl"> 升级服务端</span>
																	</label>
																</li>

																<li class="item-pink clearfix">
																	<label class="inline">
																		<input type="checkbox" class="ace" />
																		<span class="lbl"> 清理垃圾</span>
																	</label>
																</li>
															</ul>
														</div>

														<div id="member-tab" class="tab-pane">
															<div class="clearfix">
																<div class="itemdiv memberdiv">
																	<div class="user">
																		<img alt="Bob Doe's avatar" src="assets/avatars/user.jpg" />
																	</div>

																	<div class="body">
																		<div class="name">
																			<a href="#">Bob Doe</a>
																		</div>

																		<div class="time">
																			<i class="icon-time"></i>
																			<span class="green">20 min</span>
																		</div>

																		<div>
																			<span class="label label-warning label-sm">pending</span>

																			<div class="inline position-relative">
																				<button class="btn btn-minier bigger btn-yellow btn-no-border dropdown-toggle" data-toggle="dropdown">
																					<i class="icon-angle-down icon-only bigger-120"></i>
																				</button>

																				<ul class="dropdown-menu dropdown-only-icon dropdown-yellow pull-right dropdown-caret dropdown-close">
																					<li>
																						<a href="#" class="tooltip-success" data-rel="tooltip" title="Approve">
																							<span class="green">
																								<i class="icon-ok bigger-110"></i>
																							</span>
																						</a>
																					</li>

																					<li>
																						<a href="#" class="tooltip-warning" data-rel="tooltip" title="Reject">
																							<span class="orange">
																								<i class="icon-remove bigger-110"></i>
																							</span>
																						</a>
																					</li>

																					<li>
																						<a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
																							<span class="red">
																								<i class="icon-trash bigger-110"></i>
																							</span>
																						</a>
																					</li>
																				</ul>
																			</div>
																		</div>
																	</div>
																</div>

																<div class="itemdiv memberdiv">
																	<div class="user">
																		<img alt="Joe Doe's avatar" src="assets/avatars/avatar2.png" />
																	</div>

																	<div class="body">
																		<div class="name">
																			<a href="#">Joe Doe</a>
																		</div>

																		<div class="time">
																			<i class="icon-time"></i>
																			<span class="green">1 hour</span>
																		</div>

																		<div>
																			<span class="label label-warning label-sm">pending</span>

																			<div class="inline position-relative">
																				<button class="btn btn-minier bigger btn-yellow btn-no-border dropdown-toggle" data-toggle="dropdown">
																					<i class="icon-angle-down icon-only bigger-120"></i>
																				</button>

																				<ul class="dropdown-menu dropdown-only-icon dropdown-yellow pull-right dropdown-caret dropdown-close">
																					<li>
																						<a href="#" class="tooltip-success" data-rel="tooltip" title="Approve">
																							<span class="green">
																								<i class="icon-ok bigger-110"></i>
																							</span>
																						</a>
																					</li>

																					<li>
																						<a href="#" class="tooltip-warning" data-rel="tooltip" title="Reject">
																							<span class="orange">
																								<i class="icon-remove bigger-110"></i>
																							</span>
																						</a>
																					</li>

																					<li>
																						<a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
																							<span class="red">
																								<i class="icon-trash bigger-110"></i>
																							</span>
																						</a>
																					</li>
																				</ul>
																			</div>
																		</div>
																	</div>
																</div>

																<div class="itemdiv memberdiv">
																	<div class="user">
																		<img alt="Jim Doe's avatar" src="assets/avatars/avatar.png" />
																	</div>

																	<div class="body">
																		<div class="name">
																			<a href="#">Jim Doe</a>
																		</div>

																		<div class="time">
																			<i class="icon-time"></i>
																			<span class="green">2 hour</span>
																		</div>

																		<div>
																			<span class="label label-warning label-sm">pending</span>

																			<div class="inline position-relative">
																				<button class="btn btn-minier bigger btn-yellow btn-no-border dropdown-toggle" data-toggle="dropdown">
																					<i class="icon-angle-down icon-only bigger-120"></i>
																				</button>

																				<ul class="dropdown-menu dropdown-only-icon dropdown-yellow pull-right dropdown-caret dropdown-close">
																					<li>
																						<a href="#" class="tooltip-success" data-rel="tooltip" title="Approve">
																							<span class="green">
																								<i class="icon-ok bigger-110"></i>
																							</span>
																						</a>
																					</li>

																					<li>
																						<a href="#" class="tooltip-warning" data-rel="tooltip" title="Reject">
																							<span class="orange">
																								<i class="icon-remove bigger-110"></i>
																							</span>
																						</a>
																					</li>

																					<li>
																						<a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
																							<span class="red">
																								<i class="icon-trash bigger-110"></i>
																							</span>
																						</a>
																					</li>
																				</ul>
																			</div>
																		</div>
																	</div>
																</div>

																<div class="itemdiv memberdiv">
																	<div class="user">
																		<img alt="Alex Doe's avatar" src="assets/avatars/avatar5.png" />
																	</div>

																	<div class="body">
																		<div class="name">
																			<a href="#">Alex Doe</a>
																		</div>

																		<div class="time">
																			<i class="icon-time"></i>
																			<span class="green">3 hour</span>
																		</div>

																		<div>
																			<span class="label label-danger label-sm">blocked</span>
																		</div>
																	</div>
																</div>

																<div class="itemdiv memberdiv">
																	<div class="user">
																		<img alt="Bob Doe's avatar" src="assets/avatars/avatar2.png" />
																	</div>

																	<div class="body">
																		<div class="name">
																			<a href="#">Bob Doe</a>
																		</div>

																		<div class="time">
																			<i class="icon-time"></i>
																			<span class="green">6 hour</span>
																		</div>

																		<div>
																			<span class="label label-success label-sm arrowed-in">approved</span>
																		</div>
																	</div>
																</div>

																<div class="itemdiv memberdiv">
																	<div class="user">
																		<img alt="Susan's avatar" src="assets/avatars/avatar3.png" />
																	</div>

																	<div class="body">
																		<div class="name">
																			<a href="#">Susan</a>
																		</div>

																		<div class="time">
																			<i class="icon-time"></i>
																			<span class="green">yesterday</span>
																		</div>

																		<div>
																			<span class="label label-success label-sm arrowed-in">approved</span>
																		</div>
																	</div>
																</div>

																<div class="itemdiv memberdiv">
																	<div class="user">
																		<img alt="Phil Doe's avatar" src="assets/avatars/avatar4.png" />
																	</div>

																	<div class="body">
																		<div class="name">
																			<a href="#">Phil Doe</a>
																		</div>

																		<div class="time">
																			<i class="icon-time"></i>
																			<span class="green">2 days ago</span>
																		</div>

																		<div>
																			<span class="label label-info label-sm arrowed-in arrowed-in-right">online</span>
																		</div>
																	</div>
																</div>

																<div class="itemdiv memberdiv">
																	<div class="user">
																		<img alt="Alexa Doe's avatar" src="assets/avatars/avatar1.png" />
																	</div>

																	<div class="body">
																		<div class="name">
																			<a href="#">Alexa Doe</a>
																		</div>

																		<div class="time">
																			<i class="icon-time"></i>
																			<span class="green">3天以前</span>
																		</div>

																		<div>
																			<span class="label label-success label-sm arrowed-in">approved</span>
																		</div>
																	</div>
																</div>
															</div>

															<div class="center">
																<i class="icon-group icon-2x green"></i>

																&nbsp;
																<a href="#">
																	查看所有会员 &nbsp;
																	<i class="icon-arrow-right"></i>
																</a>
															</div>

															<div class="hr hr-double hr8"></div>
														</div><!-- member-tab -->

														<div id="comment-tab" class="tab-pane">
															<div class="comments">
																<div class="itemdiv commentdiv">
																	<div class="user">
																		<img alt="Bob Doe's Avatar" src="assets/avatars/avatar.png" />
																	</div>

																	<div class="body">
																		<div class="name">
																			<a href="#">Bob Doe</a>
																		</div>

																		<div class="time">
																			<i class="icon-time"></i>
																			<span class="green">6 min</span>
																		</div>

																		<div class="text">
																			<i class="icon-quote-left"></i>
																			Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque commodo massa sed ipsum porttitor facilisis &hellip;
																		</div>
																	</div>

																	<div class="tools">
																		<div class="inline position-relative">
																			<button class="btn btn-minier bigger btn-yellow dropdown-toggle" data-toggle="dropdown">
																				<i class="icon-angle-down icon-only bigger-120"></i>
																			</button>

																			<ul class="dropdown-menu dropdown-only-icon dropdown-yellow pull-right dropdown-caret dropdown-close">
																				<li>
																					<a href="#" class="tooltip-success" data-rel="tooltip" title="Approve">
																						<span class="green">
																							<i class="icon-ok bigger-110"></i>
																						</span>
																					</a>
																				</li>

																				<li>
																					<a href="#" class="tooltip-warning" data-rel="tooltip" title="Reject">
																						<span class="orange">
																							<i class="icon-remove bigger-110"></i>
																						</span>
																					</a>
																				</li>

																				<li>
																					<a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
																						<span class="red">
																							<i class="icon-trash bigger-110"></i>
																						</span>
																					</a>
																				</li>
																			</ul>
																		</div>
																	</div>
																</div>

																<div class="itemdiv commentdiv">
																	<div class="user">
																		<img alt="Jennifer's Avatar" src="assets/avatars/avatar1.png" />
																	</div>

																	<div class="body">
																		<div class="name">
																			<a href="#">Jennifer</a>
																		</div>

																		<div class="time">
																			<i class="icon-time"></i>
																			<span class="blue">15 min</span>
																		</div>

																		<div class="text">
																			<i class="icon-quote-left"></i>
																			Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque commodo massa sed ipsum porttitor facilisis &hellip;
																		</div>
																	</div>

																	<div class="tools">
																		<div class="action-buttons bigger-125">
																			<a href="#">
																				<i class="icon-pencil blue"></i>
																			</a>

																			<a href="#">
																				<i class="icon-trash red"></i>
																			</a>
																		</div>
																	</div>
																</div>

																<div class="itemdiv commentdiv">
																	<div class="user">
																		<img alt="Joe's Avatar" src="assets/avatars/avatar2.png" />
																	</div>

																	<div class="body">
																		<div class="name">
																			<a href="#">Joe</a>
																		</div>

																		<div class="time">
																			<i class="icon-time"></i>
																			<span class="orange">22 min</span>
																		</div>

																		<div class="text">
																			<i class="icon-quote-left"></i>
																			Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque commodo massa sed ipsum porttitor facilisis &hellip;
																		</div>
																	</div>

																	<div class="tools">
																		<div class="action-buttons bigger-125">
																			<a href="#">
																				<i class="icon-pencil blue"></i>
																			</a>

																			<a href="#">
																				<i class="icon-trash red"></i>
																			</a>
																		</div>
																	</div>
																</div>

																<div class="itemdiv commentdiv">
																	<div class="user">
																		<img alt="Rita's Avatar" src="assets/avatars/avatar3.png" />
																	</div>

																	<div class="body">
																		<div class="name">
																			<a href="#">Rita</a>
																		</div>

																		<div class="time">
																			<i class="icon-time"></i>
																			<span class="red">50 min</span>
																		</div>

																		<div class="text">
																			<i class="icon-quote-left"></i>
																			Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque commodo massa sed ipsum porttitor facilisis &hellip;
																		</div>
																	</div>

																	<div class="tools">
																		<div class="action-buttons bigger-125">
																			<a href="#">
																				<i class="icon-pencil blue"></i>
																			</a>

																			<a href="#">
																				<i class="icon-trash red"></i>
																			</a>
																		</div>
																	</div>
																</div>
															</div>

															<div class="hr hr8"></div>

															<div class="center">
																<i class="icon-comments-alt icon-2x green"></i>

																&nbsp;
																<a href="#">
																	See all comments &nbsp;
																	<i class="icon-arrow-right"></i>
																</a>
															</div>

															<div class="hr hr-double hr8"></div>
														</div>
													</div>
												</div><!-- /widget-main -->
											</div><!-- /widget-body -->
										</div><!-- /widget-box -->
									</div><!-- /span -->

									<div class="col-sm-6">
										<div class="widget-box ">
											<div class="widget-header">
												<h4 class="lighter smaller">
													<i class="icon-comment blue"></i>
													会话
												</h4>
											</div>

											<div class="widget-body">
												<div class="widget-main no-padding">
													<div class="dialogs">
														<div class="itemdiv dialogdiv">
															<div class="user">
																<img alt="Alexa's Avatar" src="assets/avatars/avatar1.png" />
															</div>

															<div class="body">
																<div class="time">
																	<i class="icon-time"></i>
																	<span class="green">4秒钟前</span>
																</div>

																<div class="name">
																	<a href="#">Alexa</a>
																</div>
																<div class="text">大家好啊</div>

																<div class="tools">
																	<a href="#" class="btn btn-minier btn-info">
																		<i class="icon-only icon-share-alt"></i>
																	</a>
																</div>
															</div>
														</div>

														<div class="itemdiv dialogdiv">
															<div class="user">
																<img alt="John's Avatar" src="assets/avatars/avatar.png" />
															</div>

															<div class="body">
																<div class="time">
																	<i class="icon-time"></i>
																	<span class="blue">38秒以前</span>
																</div>

																<div class="name">
																	<a href="#">John</a>
																</div>
																<div class="text">框架很好用嘛</div>

																<div class="tools">
																	<a href="#" class="btn btn-minier btn-info">
																		<i class="icon-only icon-share-alt"></i>
																	</a>
																</div>
															</div>
														</div>

														<div class="itemdiv dialogdiv">
															<div class="user">
																<img alt="Bob's Avatar" src="assets/avatars/user.jpg" />
															</div>

															<div class="body">
																<div class="time">
																	<i class="icon-time"></i>
																	<span class="orange">2分钟以前</span>
																</div>

																<div class="name">
																	<a href="#">Bob</a>
																	<span class="label label-info arrowed arrowed-in-right">admin</span>
																</div>
																<div class="text">欢迎大家使用ACE后台管理系统.</div>

																<div class="tools">
																	<a href="#" class="btn btn-minier btn-info">
																		<i class="icon-only icon-share-alt"></i>
																	</a>
																</div>
															</div>
														</div>

														<div class="itemdiv dialogdiv">
															<div class="user">
																<img alt="Jim's Avatar" src="assets/avatars/avatar4.png" />
															</div>

															<div class="body">
																<div class="time">
																	<i class="icon-time"></i>
																	<span class="grey">3分钟以前</span>
																</div>

																<div class="name">
																	<a href="#">Jim</a>
																</div>
																<div class="text">大家多提提BUG</div>

																<div class="tools">
																	<a href="#" class="btn btn-minier btn-info">
																		<i class="icon-only icon-share-alt"></i>
																	</a>
																</div>
															</div>
														</div>

														<div class="itemdiv dialogdiv">
															<div class="user">
																<img alt="Alexa's Avatar" src="assets/avatars/avatar1.png" />
															</div>

															<div class="body">
																<div class="time">
																	<i class="icon-time"></i>
																	<span class="green">4分钟以前</span>
																</div>

																<div class="name">
																	<a href="#">Alexa</a>
																</div>
																<div class="text">继续支持ACE后台系统</div>

																<div class="tools">
																	<a href="#" class="btn btn-minier btn-info">
																		<i class="icon-only icon-share-alt"></i>
																	</a>
																</div>
															</div>
														</div>
													</div>

													<form>
														<div class="form-actions">
															<div class="input-group">
																<input placeholder="Type your message here ..." type="text" class="form-control" name="message" />
																<span class="input-group-btn">
																	<button class="btn btn-sm btn-info no-radius" type="button">
																		<i class="icon-share-alt"></i>
																		发送
																	</button>
																</span>
															</div>
														</div>
													</form>
												</div><!-- /widget-main -->
											</div><!-- /widget-body -->
										</div><!-- /widget-box -->
									</div><!-- /span -->
								</div><!-- /row -->

								<!-- PAGE CONTENT ENDS -->
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
	<script type="text/javascript">
			jQuery(function($) {
				var oTable1 = $('#sample-table-2').dataTable( {
				"aoColumns": [
			      { "bSortable": false },
			      null, null,null, null, null,
				  { "bSortable": false }
				] } );
				
				
				$('table th input:checkbox').on('click' , function(){
					var that = this;
					$(this).closest('table').find('tr > td:first-child input:checkbox')
					.each(function(){
						this.checked = that.checked;
						$(this).closest('tr').toggleClass('selected');
					});
						
				});
			
			
				$('[data-rel="tooltip"]').tooltip({placement: tooltip_placement});
				function tooltip_placement(context, source) {
					var $source = $(source);
					var $parent = $source.closest('table')
					var off1 = $parent.offset();
					var w1 = $parent.width();
			
					var off2 = $source.offset();
					var w2 = $source.width();
			
					if( parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2) ) return 'right';
					return 'left';
				}
			})
		</script>				
<es:contentFooter/>


