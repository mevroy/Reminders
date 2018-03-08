<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<style>
.btn-coral, .btn-coral:active, .btn-coral:focus {
	background-color: #44d9e6;
	border-color: #44d9e6;
	color: #fff;
}
</style>
<div id="loader" class="has-spinner active">
	<!-- button type="button" class="btn btn-coral btn-lg btn-block disabled">
		<span class="spinner"><i
			class="glyphicon glyphicon-repeat icon-spin"></i></span> Invitation
		Loading...
	</button>  -->
		<a href="loadCustomInvitation?groupEventInviteId=${groupEventInvite.groupEventInviteId}" id="inviteButton" class="btn btn-coral btn-lg btn-block disabled has-spinner" data-loading-text="<span class='spinner'><i
			class='glyphicon glyphicon-repeat icon-spin'></i></span> Invitation Loading...">
		Reload Invitation
	</a>
</div>
<div id="invitation" style="display: none;">

	<div class="row">
		<div>
			<img class="col-xs-12 col-lg-offset-3 col-lg-6" id="imager"
				src="/app/res/custom/images/ErinsBday.png">
			<c:if test="${groupEventInvite.groupEventInviteId ne null}">
				<br/>
				<div class="col-xs-12 col-lg-offset-3 col-lg-6">
					<a class="btn btn-coral btn-lg btn-block"
						href="createRSVP?groupEventInviteId=${groupEventInvite.groupEventInviteId}">Click
						here to RSVP</a>
				</div>
			</c:if>
		</div>
	</div>
	<div class="row">
		<br />
	</div>
	<div class="row" style="display: none;">
		<div class="col-xs-12 col-lg-offset-3 col-lg-6">
			<div class="panel-group" id="accordion" role="tablist"
				aria-multiselectable="true">
				<div class="panel panel-default">
					<div class="panel-heading" style="background-color: #44d9e6;">
						<h3 class="panel-title" style="color: #fff;"
							data-toggle="collapse" data-parent="#accordion"
							href="#collapseOne" aria-expanded="true"
							onclick="this.href='#collapseOne'" aria-controls="collapseOne">
							Comments <span class="badge pull-right"><c:out
									value="${fn:length(groupEventInviteRSVPs)}" /></span>
						</h3>
					</div>
					<div id="collapseOne" class="panel-collapse collapse"
						role="tabpanel" aria-labelledby="headingOne">
						<c:if test="${fn:length(groupEventInviteRSVPs)<=0 }">
							<div class="panel-body">
								<p>No Comments</p>
							</div>
						</c:if>
						<ul class="list-group">
							<c:forEach items="${groupEventInviteRSVPs}"
								var="groupEventInviteRSVP">
								<c:if
									test="${groupEventInviteRSVP.rsvpComments ne null and groupEventInviteRSVP.rsvpComments ne '' }">
									<li class="list-group-item">
										<h5 class="list-group-item-heading">
											<c:choose>
												<c:when
													test="${!empty groupEventInviteRSVP.groupMember.aliasName}">${groupEventInviteRSVP.groupMember.aliasName}</c:when>
												<c:otherwise>${groupEventInviteRSVP.groupMember.firstName} ${groupEventInviteRSVP.groupMember.lastName}</c:otherwise>
											</c:choose>
											<small class="pull-right"><c:out
													value="${groupEventInviteRSVP.rsvpDateTime}" /></small>
										</h5>
										<p class="list-group-item-text">
											<i><c:out value="${groupEventInviteRSVP.rsvpComments}" /></i>
										</p>
									</li>
								</c:if>
							</c:forEach>
						</ul>
					</div>

				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	/* Hover the envelope to trigger animation. */
	$(document)
			.ready(
					function() {
						$("#inviteButton").button('loading');
						
						$("#imager")
								.load(
										'/app/res/custom/images/ErinsBday.png',
										function(responseText, textStatus, req) {
											if(textStatus== "error"){
												$("#inviteButton").button('reset');
												
											}
											else
											{
											$("#loader").slideUp(500, "linear");
											$("#invitation").slideDown(3000,
													"easeInQuint", function() {
														// Animation complete.
													});
											}
										})
					})
</script>
