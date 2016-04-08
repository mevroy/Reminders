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
			<c:if test="${sessionScope.user ne null}">
				<ul class="nav">
					<!-- li class="active"><a
					href="${pageContext.request.contextPath}/">Home</a></li>  -->
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-expanded="false">Groups<span
							class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="addGroup">Add New Group</a></li>
							<li><a href="addGroupMemberCategory">Add New Member
									Category</a></li>
							<li><a href="addGroupEmailAccount">Add New Email Account</a></li>
							<li><a href="viewGroupInterests">View Group Interests</a></li>
						</ul></li>

					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-expanded="false">Members<span
							class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="addGroupMember">Add New Members</a></li>
							<li><a href="viewAllGroupMember">View Members</a></li>
						</ul></li>

					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-expanded="false">
							Events<span class="caret"></span>
					</a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="addGroupEvent">Add New Group Event</a></li>
							<li><a href="viewAllGroupEvents">View Group Events</a></li>
							<li class="divider"></li>
							<li><a href="addGroupEventCron">Add New Email Job</a></li>
							<li><a href="#">View Email Jobs</a></li>
							<li class="divider"></li>
							<li><a href="addGroupEmailTemplate">Add New Email
									Template</a></li>
							<li><a href="viewAllGroupEmailTemplates">View Email
									Templates</a></li>
						</ul></li>

					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-expanded="false">
							Invites<span class="caret"></span>
					</a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="createGroupEventInvite">Create Invite List</a></li>
							<li><a href="viewGroupEventInvites">View Invite List</a></li>
							<li><a href="registerGroupEventInvites">Update/Register
									Invites</a></li>
							<li class="divider"></li>
							<li><a href="viewGroupEventPasses">View Event Passes</a></li>
							<li><a href="addGroupEventPassesToGroupMembers">Add
									Event Passes to Members</a></li>
							<li class="divider"></li>
							<li><a href="loadScanGroupMember">Scan Invitations </a></li>

						</ul></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-expanded="false">
							Emails<span class="caret"></span>
					</a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="createGroupEventInviteEmails">Create Invite
									Emails</a></li>
							<li><a href="viewGroupEmails">View Invite Emails</a></li>
						</ul></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-expanded="false">Feedback<span
							class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="viewGroupEventFeedback">Feedback By Event</a></li>
						</ul></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-expanded="false">Other<span
							class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#">General Feedback</a></li>
							<li><a href="viewRegisteredInterests">Registered
									Interests</a></li>
							<li><a href="viewAuditLogs">Audit Logs</a></li>									

						</ul></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-expanded="false">URLs<span
							class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#"
								onclick="window.open('${pageContext.request.contextPath}/00622239-f2b3-48e7-a55a-d7c1f65d05ad/batch/','_blank','toolbar=no, location=no')">URL
									for Batch Job</a></li>
							<li><a href="${pageContext.request.contextPath}/newFeedback">URL
									for New Feedback</a></li>
							<li><a
								href="${pageContext.request.contextPath}/groupEventFeedback/{groupEventInviteId}">URL
									for Event Feedback</a></li>
							<li><a
								href="${pageContext.request.contextPath}/createRSVP/{groupEventInviteId}">URL
									for RSVP</a></li>
							<li><a
								href="${pageContext.request.contextPath}/registerInterest?groupCode=MKC&interestType=Interest%20Type&campaign=Email">URL
									for Register Interest</a></li>
							<li><a
								href="${pageContext.request.contextPath}/loadEmailWebversion/{groupEmailId}">URL
									for Email Web version</a></li>
							<li><a
								href="${pageContext.request.contextPath}/viewEmail/{groupEmailId}">URL
									for Marking Email as viewed</a></li>
							<li><a
								href="${pageContext.request.contextPath}/groupEventFeedback/{groupEventInviteId}">URL
									for Event Feedback</a></li>
							<li><a
								href="${pageContext.request.contextPath}/scan/{templateName}/{value}">URL
									for Membership Scans</a></li>
							<li><a
								href="${pageContext.request.contextPath}/scanGroupMemberRemotely?templateName=MKCAppleScanMember&amp;autoRegister=">URL
									for Remotely scanning Member IDs</a></li>
							<li><a
								href="${pageContext.request.contextPath}/scanAndRegisterPasses?templateName=MKCAppleScanPass&amp;autoRegister=">URL
									for Remotely scanning Event Passes</a></li>


						</ul></li>

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
							<c:otherwise>
								<a href="#" class="dropdown-toggle" data-toggle="dropdown">User
									Sign In<b class="caret"></b>
								</a>

								<div class="dropdown-menu signin-pad">
									<form id="loginUserForm1" method="post" action="loginUser"
										name="loginUserForm1" class="pull-right">
										<div class="control-group" id="usernameS">
										<label class="control-label" for="username_lgn">Username</label><div class="controls"> <input
											class="span3" type="text" id="username_lgn" name="username"
											placeholder="Username" /></div></div> <div class="control-group" id="passwordS"><label class="control-label" for="password_lgn">Password</label>
										<div class="controls"><input class="span3" type="password" id="password_lgn"
											name="password" placeholder="Password" /> </div></div><input
											type="submit" name="Login" class="btn small" value="Login" />
										<br /> <br /> <a href="#" style="margin-left: -14px;">Forgot
											your password?</a>
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
		$('.dropdown input, .dropdown label').click(function(e) {
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