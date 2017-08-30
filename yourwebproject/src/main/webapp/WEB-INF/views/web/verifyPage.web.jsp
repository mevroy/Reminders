<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="jumbotron">
	<div id="timer"></div>
	<h2>Your Transaction Summary</h2>
	<br />

	<div>
		<div class="row">

			<div class="col-lg-12">
				<table class='table table-condensed table-bordered table-striped'>
					<tr class="danger">
						<td colspan="2"><b>Primary Account Holder Information</b></td>
					</tr>
					<tr class="warning">
						<td><b>Account Holder</b></td>
						<td>${groupMember.firstName} ${groupMember.lastName}</td>
					</tr>
					<tr class="warning">
						<td><b>Primary Email</b></td>
						<td>${groupMember.primaryEmail}</td>
					</tr>
					<tr class="warning">
						<td><b>Mobile</b></td>
						<td>${groupMember.mobilephone}</td>
					</tr>

				</table>
			</div>




			<form:form commandName="groupEvent"
				action="buy?eId=${groupEventPaymentTransaction.groupEventInvite.groupEventInviteId}"
				method="post" id="buy"
				onsubmit="return validateFormAndToggleButton('buy');">
				<input type="hidden" name="transactionId"
					value="${groupEventPaymentTransaction.transactionId}" />
				<fmt:timeZone value="GMT+10">
					<fmt:formatDate var="formattedDate" pattern="yyyy/MM/dd HH:mm:ss "
						value="${groupEventPaymentTransaction.transactionExpiryDateTime}" />
				</fmt:timeZone>
				<div class="col-lg-12">
					<table class='table table-condensed table-bordered'>
						<tr class="info">
							<td><b>Ticket Type</b></td>
							<td><b>Ticket Count</b></td>
							<td><b>Amount</b></td>
						</tr>

						<c:forEach items="${groupEvent.groupEventPassCategories}"
							var="passCategory" varStatus="i">
							<c:if test="${passCategory.numberOfPasses gt 0}">
								<tr class="warning">
									<td>${passCategory.passCategoryNameShort}<span
										class="badge badge-warning">$${passCategory.passPrice}</span></td>
									<td>${passCategory.numberOfPasses}</td>
									<td>$${passCategory.numberOfPasses*
										passCategory.passPrice}</td>
								</tr>
							</c:if>
							<form:hidden
								path="groupEventPassCategories[${i.index}].groupEventPassCategoryId" />
							<form:hidden
								path="groupEventPassCategories[${i.index}].numberOfPasses" />
							<form:hidden
								path="groupEventPassCategories[${i.index}].passCategoryNameShort" />
							<form:hidden
								path="groupEventPassCategories[${i.index}].passPrice" />

						</c:forEach>
						<tr class="danger">
							<td><b>Total</b></td>
							<td><b>${groupEventPaymentTransaction.totalNumberOfProducts}</b></td>
							<td><b>$${groupEventPaymentTransaction.transactionAmount}</b></td>
						</tr>
					</table>
				</div>



				<div class="col-lg-12">
					<c:choose>
						<c:when test="${!disableButton and cancelButton}">
							<button class="btn btn-primary btn-lg btn-block has-spinner"
								type="submit"
								data-loading-text="<span class='spinner'><i class='icon-spin glyphicon glyphicon-repeat'></i></span> Loading.."
								autocomplete="off">PROCEED TO PAYMENT</button>
							<button class="btn btn-danger btn-lg btn-block" type="button"
								data-toggle="modal" data-target="#myModalForCancel">CANCEL
								TRANSACTION</button>
						</c:when>
						<c:when test="${!disableButton}">
							<button class="btn btn-primary btn-lg btn-block has-spinner"
								type="submit"
								data-loading-text="<span class='spinner'><i class='icon-spin glyphicon glyphicon-repeat'></i></span> Loading.."
								autocomplete="off">PROCEED TO PAYMENT</button>
							<a class="btn btn-default btn-lg btn-block"
								href="buy?eId=${groupEventPaymentTransaction.groupEventInvite.groupEventInviteId}">BACK</a>
						</c:when>
						<c:otherwise>
							<input class="btn btn-primary btn-lg btn-block has-spinner"
								type="submit" disabled="disabled" value="PROCEED TO PAYMENT" />

						</c:otherwise>
					</c:choose>
				</div>
			</form:form>


		</div>

	</div>
</div>


<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$("#doBuy")
								.validate(
										{
											rules : {

											},
											errorClass : "control-group has-error text-danger",
											validClass : "control-group has-success",
											errorElement : "span",
											errorPlacement : function(error,
													element) {
												if (element.attr("type") == "radio") {

													error.insertBefore(element
															.parent('label')
															.parent('div'));
												} else {
													error.insertAfter(element);
												}
											},
											highlight : function(element,
													errorClass, validClass) {
												if (element.type === 'radio') {
													this.findByName(
															element.name)
															.parent("div")
															.parent("div")
															.removeClass(
																	validClass)
															.addClass(
																	errorClass);
												} else {
													$(element).parent("div")
															.parent("div")
															.removeClass(
																	validClass)
															.addClass(
																	errorClass);
												}
											},
											unhighlight : function(element,
													errorClass, validClass) {
												if (element.type === 'radio') {
													this.findByName(
															element.name)
															.parent("div")
															.parent("div")
															.removeClass(
																	errorClass)
															.addClass(
																	validClass);
												} else {
													$(element).parent("div")
															.parent("div")
															.removeClass(
																	errorClass)
															.addClass(
																	validClass);
												}
											}
										});

						function getDateToFormat() {
							return new Date('${formattedDate}');
						}

						var $clock = $('#timer');
						$clock
								.countdown(
										getDateToFormat(),
										function(event) {
											$(this)
													.html(
															event
																	.strftime('<h4 class="pull-right"><span  class="label label-danger">Transaction expires in %H hrs %M mins %S secs</span></h4>'));
										})
								.on(
										'finish.countdown',
										function(event) {
											$(this)
													.html(
															'<h4 class="pull-right"><span  class="label label-danger">Transaction Expired</span></h4>')
													.parent().addClass(
															'disabled');
											window.location.href = window.location.href;
										});

					});
</script>

<!-- Modal -->
<div class="modal fade" id="myModalForCancel" tabindex="0" role="dialog"
	aria-labelledby="myModalForCancelLabel" data-backdrop="static"
	align="center">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="btn btn-primary pull-right"
					data-dismiss="modal">X</button>
				<h3 class="modal-title" id="myModalLabel">Are you sure ?</h3>
			</div>
			<div class="modal-body">
				<div class="alert alert-danger">
					<div style="line-height: 27px; font-size: 18px;">
						<div id="myModalMessageBody">
							<i class="glyphicon glyphicon-exclamation-sign"></i> If you
							cancel this transaction all the tickets reserved for you, under
							this transaction, will be released and you will have to re-book,
							subject to tickets availability and within booking deadline.
							<c:if test="${not empty groupEvent.rsvpDeadlineDate}">The online booking deadline for this event is <fmt:timeZone
									value="GMT+10">
									<b><fmt:formatDate type="both" timeStyle="MEDIUM"
											value="${groupEvent.rsvpDeadlineDate}" /></b>
								</fmt:timeZone>, after which you will not be able to book tickets online. </c:if>
							Proceed with caution!
						</div>
					</div>
				</div>

			</div>
			<div class="modal-footer">
				<form:form commandName="groupEvent"
					action="cancelTransaction?eId=${groupEventPaymentTransaction.groupEventInvite.groupEventInviteId}"
					method="post" id="cancelTransaction">
					<input type="hidden" name="transactionId"
						value="${groupEventPaymentTransaction.transactionId}" />
					<button type="submit" class="btn btn-danger">PROCEED</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">CLOSE</button>
				</form:form>

			</div>
		</div>
	</div>
</div>