<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="jumbotron">
	<c:if test="${rsvpMessage ne null && rsvpMessage ne ''}">
		<h2>
			<c:out value="${rsvpMessage}" />
			<c:if
				test="${groupEvent.eventName ne null || groupEvent.eventName ne ''}"> for <c:out
					value="${groupEvent.eventName}" />
			</c:if>
		</h2>
		<br />
	</c:if>
	<div>
		<div class="row">
			<div>
				<form:form commandName="groupEvent"
					action="verifyPurchase?eId=${groupEventInvite.groupEventInviteId}"
					method="post" id="verifyPurchase"
					onsubmit="return validateFormAndToggleButton('verifyPurchase');">






					<c:forEach items="${groupEvent.groupEventPassCategories}"
						var="passCategory" varStatus="i">
						<div class="col-sm-4">
							<div class="form-group" id="${i.index}">

								<label class="control-label"
									for="groupEventPassCategoryId${i.index}">${passCategory.passCategoryNameShort}
									<span class="badge badge-warning">$${passCategory.passPrice}</span>
								<c:choose>
										<c:when test="${passCategory.disablePurchase}">
											<a tabindex="0" role="button" data-toggle="popover"
												data-container="body" data-animation="true"
												data-trigger="focus" title="SOLD OUT" data-placement="top"
												data-content="This ticket category is sold out or unavailable at the moment. Please contact <c:choose><c:when test="${not empty groupEvent.eventOrganiser}">${groupEvent.eventOrganiser}</c:when><c:otherwise>us</c:otherwise></c:choose> if you need help!"><span
												class="input-prepend add-on"><i
													class="glyphicon glyphicon-remove-sign text-danger"></i></span></a>
										</c:when>
										<c:when test="${passCategory.maxPurchasePerInvite eq 0}">
											<a tabindex="0" role="button" data-toggle="popover"
												data-container="body" data-animation="true"
												data-trigger="focus" title="Why is this selection disabled?"
												data-placement="top"
												data-content="You have already purchased tickets upto a max limit for this ticket category. Please contact <c:choose><c:when test="${not empty groupEvent.eventOrganiser}">${groupEvent.eventOrganiser}</c:when><c:otherwise>us</c:otherwise></c:choose> if you need help!"><span
												class="input-prepend add-on"><i
													class="glyphicon glyphicon-exclamation-sign text-danger"></i></span></a>
										</c:when>

										<c:when test="${passCategory.maxPurchasePerInvite eq -1}">
											<a tabindex="0" role="button" data-toggle="popover"
												data-container="body" data-animation="true"
												data-trigger="focus"
												title="Why is this selection not available?"
												data-placement="top"
												data-content="You are not eligible to purchase this ticket category at this time. Possible reasons include, this category is only available for Active members. Please contact <c:choose><c:when test="${not empty groupEvent.eventOrganiser}">${groupEvent.eventOrganiser}</c:when><c:otherwise>us</c:otherwise></c:choose> if you think, this should not be the case!"><span
												class="input-prepend add-on"><i
													class="glyphicon glyphicon-exclamation-sign text-danger"></i></span></a>
										</c:when>
										<c:otherwise>
											<a tabindex="0" role="button" data-toggle="popover"
												data-container="body" data-animation="true"
												data-trigger="focus"
												title="Are there any limits on my ticket purchase?"
												data-placement="top"
												data-content="Yes, there is a maximum limit for each ticket category that you can purchase for a particular event. The dropdown values are automatically populated based on the pre-set limit. If you require more tickets than you are allowed to buy, please contact <c:choose><c:when test="${not empty groupEvent.eventOrganiser}">${groupEvent.eventOrganiser}.</c:when><c:otherwise>us!</c:otherwise></c:choose>"><span
												class="input-prepend add-on"><i
													class="glyphicon glyphicon-question-sign"></i></span></a>
										</c:otherwise>
									</c:choose> ${passCategory.passHeader}
								</label>

								<c:choose>
									<c:when test="${passCategory.disablePurchase}">
										<div class="controls">
											<form:select
												path="groupEventPassCategories[${i.index}].numberOfPasses"
												class="form-control" id="numberOfPasses${i.index}"
												disabled="true">
												<option value="0">Sold Out</option>
												<c:forEach begin="1"
													end="${passCategory.maxPurchasePerInvite}" varStatus="loop">
													<option value="${loop.index}">${loop.index}</option>
												</c:forEach>

											</form:select>
										</div>
									</c:when>
									<c:when test="${passCategory.maxPurchasePerInvite eq 0 }">
										<div class="controls">
											<form:select
												path="groupEventPassCategories[${i.index}].numberOfPasses"
												class="form-control" id="numberOfPasses${i.index}"
												disabled="true">
												<option value="0">Please Select</option>
												<c:forEach begin="1"
													end="${passCategory.maxPurchasePerInvite}" varStatus="loop">
													<option value="${loop.index}">${loop.index}</option>
												</c:forEach>

											</form:select>
										</div>
									</c:when>
									<c:when test="${passCategory.maxPurchasePerInvite eq -1 }">
										<div class="controls">
											<form:select
												path="groupEventPassCategories[${i.index}].numberOfPasses"
												class="form-control" id="numberOfPasses${i.index}"
												disabled="true">
												<option value="0">Not Available</option>
											</form:select>
										</div>
									</c:when>
									<c:otherwise>
										<div class="controls has-success">
											<form:select
												path="groupEventPassCategories[${i.index}].numberOfPasses"
												class="form-control" id="numberOfPasses${i.index}">
												<option value="0">Please Select</option>
												<c:forEach begin="1"
													end="${passCategory.maxPurchasePerInvite}" varStatus="loop">
													<option value="${loop.index}">${loop.index}</option>
												</c:forEach>

											</form:select>
										</div>
									</c:otherwise>
								</c:choose>



							</div>

						</div>
						<form:hidden
							path="groupEventPassCategories[${i.index}].passCategoryNameShort" />
						<form:hidden path="groupEventPassCategories[${i.index}].passPrice" />
						<form:hidden
							path="groupEventPassCategories[${i.index}].groupEventPassCategoryId" />
					</c:forEach>
					<div class="col-lg-12">
						<div class="form-group">
							<c:choose>
								<c:when test="${!disableButton}">
									<button class="btn btn-primary btn-lg btn-block has-spinner"
										type="submit"
										data-loading-text="<span class='spinner'><i class='icon-spin glyphicon glyphicon-repeat'></i></span> Loading.."
										autocomplete="off">CONTINUE</button>
								</c:when>
								<c:otherwise>
									<input class="btn btn-primary btn-lg btn-block has-spinner"
										type="submit" disabled="disabled" value="BOOKINGS CLOSED" />

								</c:otherwise>
							</c:choose>
						</div>
					</div>



				</form:form>
			</div>

			<div>
				<c:if test="${fn:length(paymentTransactions) gt 0}">


					<div class="col-lg-10"></div>

					<table class="table table-condensed">
						<tbody>
							<tr>
								<td class="frame_tl" style="border: none;"></td>
								<td class="frame_top" style="border: none;"></td>
								<td class="frame_tr" style="border: none;"></td>
							</tr>
							<tr>
								<td class="frame_left" style="border: none;"></td>
								<td align="center" style="border: none; padding: 0px;">


									<table
										class='table table-striped table-bordered table-condensed'
										style="margin-bottom: 0px;">
										<thead>
											<tr>
												<td colspan="4"><h4>Your Previous Transactions</h4></td>
											</tr>
										</thead>
										<tr class="danger">
											<td id="tickets"><b>#Tickets </b></td>
											<td><b>Total Amt</b></td>
											<td><b>Status</b></td>
											<td><b>Transaction Date</b></td>

										</tr>

										<c:forEach items="${paymentTransactions}"
											var="paymentTransaction" varStatus="p">

											<tr>
												<td>${paymentTransaction.totalNumberOfProducts}</td>
												<td>$${paymentTransaction.transactionAmount}</td>
												<td>${paymentTransaction.paymentStatus}<c:choose>
														<c:when
															test="${not empty paymentTransaction.transactionExpiryDateTime and paymentTransaction.paymentStatus == 'EXPIRED'}">
															<a tabindex="0" role="button" data-toggle="popover"
																data-container="body" data-animation="true"
																data-trigger="focus"
																title="When did the transaction Expire?"
																data-placement="right"
																data-content="Since we did not receive a payment from you, this transaction automatically expired on <fmt:timeZone value="GMT+10"><fmt:formatDate type="both" timeStyle="MEDIUM"
											value="${paymentTransaction.transactionExpiryDateTime}" /></fmt:timeZone>"><span
																class="input-prepend add-on"><i
																	class="glyphicon glyphicon-info-sign"></i></span></a>
														</c:when>
														<c:when
															test="${paymentTransaction.paymentStatus == 'PMTRECEIVED' }">
															<a tabindex="0" role="button" data-toggle="popover"
																data-container="body" data-animation="true"
																data-trigger="focus" title="Payment Received"
																data-placement="right"
																data-content="We have received your payment and have applied it to your transaction. You will receive your tickets soon!"><span
																class="input-prepend add-on"><i
																	class="glyphicon glyphicon-info-sign"></i></span></a>
														</c:when>
														<c:when
															test="${paymentTransaction.paymentStatus == 'PROCESSED' }">
															<a tabindex="0" role="button" data-toggle="popover"
																data-container="body" data-animation="true"
																data-trigger="focus" title="Transaction Processed"
																data-placement="right"
																data-content="We have processed your transaction and sent you an email/sms. If this was for online tickets purchase, you should now have the event tickets in your inbox. If you do not see an email with instructions to download your tickets, after 24 hours, after seeing this status, please contact <c:choose><c:when test="${not empty groupEvent.eventOrganiser}">${groupEvent.eventOrganiser}.</c:when><c:otherwise>us!</c:otherwise></c:choose>"><span
																class="input-prepend add-on"><i
																	class="glyphicon glyphicon-info-sign"></i></span></a>
														</c:when>
														<c:otherwise></c:otherwise>
													</c:choose></td>
												<td><fmt:timeZone value="GMT+10">
														<fmt:formatDate type="both" timeStyle="MEDIUM"
															value="${paymentTransaction.transactionDateTime}" />
													</fmt:timeZone></td>

											</tr>

										</c:forEach>
									</table>


								</td>
								<td class="frame_right" style="border: none;"></td>
							</tr>
							<tr>
								<td class="frame_bl" style="border: none;"></td>
								<td class="frame_bottom" style="border: none;"></td>
								<td class="frame_br" style="border: none;"></td>
							</tr>
						</tbody>
					</table>

				</c:if>
			</div>


		</div>

	</div>
</div>

<script type="text/javascript">
	$(document).ready(
			function() {
				$("#verifyPurchase").validate(
						{
							rules : {

							},
							errorClass : "control-group has-error text-danger",
							validClass : "control-group has-success",
							errorElement : "span",
							errorPlacement : function(error, element) {
								if (element.attr("type") == "radio") {

									error.insertBefore(element.parent('label')
											.parent('div'));
								} else {
									error.insertAfter(element);
								}
							},
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
