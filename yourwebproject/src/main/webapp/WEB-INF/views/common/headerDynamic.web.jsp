<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="navbar navbar-fixed-top" role="navigation">
	<div class="navbar-inner">
		<div class="container">
			<!-- a class="brand active" href="${pageContext.request.contextPath}/${sessionScope.groupCode}/"><c:out value="${sessionScope.groupCode}"></c:out></a>  -->
			<a class="brand active" href="#"> <c:choose>
					<c:when test="${sessionScope.groupCode ne null }">
						<c:out value="${sessionScope.groupCode}"></c:out>
					</c:when>
					<c:otherwise>
			Portal
			</c:otherwise>
				</c:choose>
			</a>
			<c:if test="${sessionScope.group ne null}">
				<ul class="nav">
					<!-- li class="active"><a
					href="${pageContext.request.contextPath}/">Home</a></li>  -->
					<c:forEach items="${sessionScope.groupMainLinks}"
						var="groupMainLink">
						<c:if test="${groupMainLink.visibleToUser}">
							<c:set var="divided" value="true" />
							<c:set var="previousLinkDivider" value="false" />
							<li class="dropdown"><a href="#" class="dropdown-toggle"
								data-toggle="dropdown" role="button" aria-expanded="false">${groupMainLink.linkName}<span
									class="caret"></span></a>
								<ul class="dropdown-menu" role="menu">
									<c:forEach items="${groupMainLink.groupSubLinksForUI}"
										var="groupSubLink" varStatus="status">
										<c:if test="${groupSubLink.visibleToUser}">
											<c:choose>
												<c:when test="${groupSubLink.divider}">
												
													<c:if test="${divided eq false}">
														<c:set var="previousLinkDivider" value="true" />
														<c:set var="divided" value="true" />
														<!-- <li class="divider"></li>  -->
													</c:if>
												</c:when>
												<c:when
													test="${groupSubLink.javascript ne null and !empty groupSubLink.javascript}">
													<c:if test="${previousLinkDivider and divided eq true}"> <li class="divider"></li> </c:if>
													<li><a href="#"
														onclick="javascript:${groupSubLink.javascript}">${groupSubLink.linkName}</a></li>
													<c:set var="divided" value="false" />
													<c:set var="previousLinkDivider" value="false" />														
												</c:when>
												<c:when
													test="${groupSubLink.url ne null and groupSubLink.url ne '#'}">
													
													<c:if test="${previousLinkDivider and divided eq true}"> <li class="divider"></li> </c:if>
													<li><a href="${groupSubLink.url}">${groupSubLink.linkName}</a></li>
													<c:set var="divided" value="false" />	
													<c:set var="previousLinkDivider" value="false" />														
												</c:when>
												<c:otherwise>
												</c:otherwise>
											</c:choose>
										</c:if>
									</c:forEach>
								</ul></li>
						</c:if>
					</c:forEach>


				</ul>
			</c:if>
			<c:if test="${sessionScope.groupName ne null }">
				<ul class="nav pull-right">
					<li class="dropdown"><c:choose>
							<c:when
								test="${sessionScope.user ne null || not empty sessionScope.user}">
								<a href="#" class="dropdown-toggle" data-toggle="dropdown">Welcome,
									${user.userName}<b class="caret"></b>
								</a>
								<ul class="dropdown-menu">
									<li><a href="logoutUser">Logout</a></li>
								</ul>
							</c:when>
							<c:when test="${sessionScope.groupMember ne null }">
								<a href="#" class="dropdown-toggle" data-toggle="dropdown">Welcome,
									<c:choose>
										<c:when test="${!empty sessionScope.groupMember.aliasName}">${sessionScope.groupMember.aliasName}</c:when>
										<c:otherwise>${sessionScope.groupMember.firstName} ${sessionScope.groupMember.lastName}</c:otherwise>
									</c:choose>
								</a>
							</c:when>							
							<c:otherwise>
								<a href="#" class="dropdown-toggle" data-toggle="dropdown">User
									Sign In<b class="caret"></b>
								</a>

								<div class="dropdown-menu signin-pad">
									<form id="loginUserForm1" method="post" action="loginUser" onsubmit="return validateFormAndToggleButton('loginUserForm1');"
										name="loginUserForm1" class="pull-right">
										<div class="control-group" id="usernameS">
											<label class="control-label" for="username_lgn">Username</label>
											<div class="controls">
												<input class="span3" type="text" id="username_lgn"
													name="username" placeholder="Username" />
											</div>
										</div>
										<div class="control-group" id="passwordS">
											<label class="control-label" for="password_lgn">Password</label>
											<div class="controls">
												<input class="span3" type="password" id="password_lgn"
													name="password" placeholder="Password" />
											</div>
										</div>
										<button type="submit" data-loading-text="<span class='spinner'><i class='icon-spin icon-repeat'></i></span> Loading.." class="btn btn-small btn-primary has-spinner">Login</button><br /> <br /> <a href="#"
											style="margin-left: 3px;">Forgot your password?</a>
									</form>
								</div>
							</c:otherwise>
						</c:choose></li>
				</ul>
			</c:if>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(function() {
		// Setup drop down menu
		$('.dropdown-toggle').dropdown();

		// Fix input element click problem
		$('.dropdown input, .dropdown label, .dropdown button').click(function(e) {
			e.stopPropagation();
		});
	});

	$(document).ready(
			function() {
				$("#loginUserForm1").validate(
						{
							rules : {
								username : {
									required : true
								},
								password : {
									required : true
								}
							},
							errorClass : "control-group error",
							validClass : "control-group success",
							errorElement : "span",
							highlight : function(element, errorClass,
									validClass) {
								if (element.type === 'radio') {
									this.findByName(element.name).parent("div")
											.parent("div").removeClass(
													validClass).addClass(
													errorClass);
								} else {
									$(element).parent("div").parent("div")
											.removeClass(validClass).addClass(
													errorClass);
								}
							},
							unhighlight : function(element, errorClass,
									validClass) {
								if (element.type === 'radio') {
									this.findByName(element.name).parent("div")
											.parent("div").removeClass(
													errorClass).addClass(
													validClass);
								} else {
									$(element).parent("div").parent("div")
											.removeClass(errorClass).addClass(
													validClass);
								}
							}
						});
			});
</script>