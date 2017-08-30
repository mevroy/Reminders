<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>



<div class="jumbotron">

<div id="timer"></div>
	<h2>Choose one of the below Payment Options</h2>
	<br />

	
	<div>
	<div class="row">

	<div class="panel-group" id="accordion2">
				<c:forEach items="${groupEvent.groupEventPaymentTypes}"
				var="groupEventPaymentType" varStatus="i">
			<div class="panel panel-primary">
				<div class="panel-heading"  data-toggle="collapse"
						data-parent="#accordion2" href="#collapse${i.index}" onclick="this.href='#collapse${i.index}'">
					
					<span
						class="badge">${groupEventPaymentType.paymentAlias}</span><button class="btn btn-primary btn-sm pull-right" type="button"><span
						class="glyphicon glyphicon-menu-down center-block" title="Click to Expand"></span></button>
				
				</div>
				<div id="collapse${i.index}" class="panel-collapse collapse">
					<div class="panel-body"><div id = "groupcontent${i.index}">
					<form:form commandName="groupEventPaymentTransaction"
										action="completePayment?eId=${groupEventPaymentTransaction.groupEventInvite.groupEventInviteId}"
										method="post" id="completePayment${i.index}" 
					onsubmit="return validateFormAndToggleButton('completePayment');">
					<form:hidden path="transactionId"/>
					<fmt:timeZone value="GMT+10"><fmt:formatDate var="formattedDate" pattern="yyyy/MM/dd HH:mm:ss "
											value="${groupEventPaymentTransaction.transactionExpiryDateTime}" /></fmt:timeZone>
					<input type="hidden" name="pmtId" value="${groupEventPaymentType.id}"/>
					${groupEventPaymentType.htmlContent}
					
												<c:choose>
							
													<c:when test="${groupEventPaymentType.paymentTypes eq 'EFT'}">
														<c:choose>
															<c:when test="${!disableButton}">
					
																<div class="form-group" id="userReferenceNumberCtl">
																	<label class="control-label" for="userReferenceNumber">Transaction
																		Reference No.</label>
					
																	<div class="controls">
					
																		<form:input path="userReferenceNumber" 
																			cssClass="form-control" id="userReferenceNumber" placeholder="Please enter your transaction reference number here" />
																	</div>
																</div>
																<button class="btn btn-primary btn-lg btn-block has-spinner"
																	type="submit"
																	data-loading-text="<span class='spinner'><i class='icon-spin glyphicon glyphicon-repeat'></i></span> Loading.."
																	autocomplete="off">UPDATE REFERENCE NUMBER</button>
															</c:when>
															<c:otherwise>
																<input class="btn btn-primary btn-lg btn-block has-spinner"
																	type="submit" disabled="disabled" value="UPDATE TRANSACTION" />
					
															</c:otherwise>
														</c:choose>
					
													</c:when>

													<c:otherwise></c:otherwise>
							</c:choose>
							</form:form>
					</div></div>
				</div>
			</div>
			</c:forEach>
</div>
	
	</div>
	</div>
</div>


<script type="text/javascript">
	$(document).ready(
			function() {
				$("#completePayment0").validate(
						{
							rules : {
								userReferenceNumber : {
									required : true,
									maxlength:20
									}
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
				
				  // 15 days from now!
				  function getDateToFormat() {
					  return new Date('${formattedDate}');
				    //return new Date(new Date().valueOf() + 1 * 1 * 1 * 10 * 1000);
				  }

				  var $clock = $('#timer');
				  $clock.countdown(getDateToFormat(), function(event) {
				    $(this).html(event.strftime('<h4 class="pull-right"><span  class="label label-danger">Transaction expires in %H hrs %M mins %S secs</span></h4>'));
				  }).on('finish.countdown', function(event) {
					  $(this).html('<h4 class="pull-right"><span  class="label label-danger">Transaction Expired</span></h4>')
					    .parent().addClass('disabled');
					  window.location.href = window.location.href;
					});

			});
</script>
