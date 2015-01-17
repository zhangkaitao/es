<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<table id="sample-table-1" class="table table-striped table-bordered table-hover">
												<thead>
													<tr>
														<th class="center" style="width: 100px;">
															<a class="check-all" href="javascript:;">全选</a>
											                |
											                <a class="reverse-all" href="javascript:;">反选</a>
														</th>
														<th>用户</th>
														<th>用户主机IP</th>
														<th>系统主机IP</th>
														<th class="hidden-480" >登录时间</th>
														<th class="hidden-580">最后访问时间</th>

														<th>状态	</th>
											            <th>用户会话ID</th>
														
													</tr>
												</thead>

												<tbody>
												<c:forEach items="${page.content}" var="m">
										            <tr>
										                <td class="check center">
										                    <input type="checkbox" name="ids"  class="ace" value="${m.id}"/>
										                    <span class="lbl"></span>
										                </td>
										                <td>
										                    <c:if test="${m.userId eq 0}">游客</c:if>
										                    <a href="${ctx}/admin/sys/user/${m.userId}">${m.username}</a>
										                </td>
										                <td>${m.host}</td>
										                <td>${m.systemHost}</td>
										                <td><pretty:prettyTime date="${m.startTimestamp}"/></td>
										                <td><pretty:prettyTime date="${m.lastAccessTime}"/></td>
										                <td>${m.id}</td>
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
										        </c:forEach>
												</tbody>
											</table>
<es:page page="${page}"/>
