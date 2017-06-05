<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="row">
	<div class="span12">
		<div class="hero-unit">
			<form:form commandName="groupEvent" action="saveGroupEvent"
				method="post" id="groupEvent">
				<fieldset>


					<h2>Add a New Group Event</h2>
					<br />



					<div class="span5">
						<div class="control-group" id="eventCodeCtl">
							<label class="control-label" for="eventCode">Group Event
								Code</label>

							<div class="controls">
								<form:input path="eventCode" cssClass="input-xlarge"
									id="eventCode" placeholder="Enter Event Code" />
							</div>
						</div>
						<div class="control-group" id="eventNameCtl">
							<label class="control-label" for="eventName">Group Event
								Name</label>

							<div class="controls">
								<form:input path="eventName" cssClass="input-xlarge"
									id="eventName" placeholder="Enter Event Name" />
							</div>
						</div>
						<div class="control-group" id="descriptionCtl">
							<label class="control-label" for="description">Group
								Event Description</label>

							<div class="controls">
								<form:input path="description" cssClass="input-xlarge"
									id="description" placeholder="Enter Description" />
							</div>
						</div>
						<div class="control-group" id="groupCodeCtl">
							<label class="control-label" for="groupCode">Group Code</label>

							<div class="controls">

								<form:input path="groupCode" cssClass="input-xlarge"
									readonly="true" id="groupCode"
									placeholder="Enter 3 digit Group Code" />
							</div>
						</div>
						<div class="control-group" id="memberCategoryCodeCtl">
							<label class="control-label" for="memberCategoryCode">Member
								Category Code</label>

							<div class="controls">

								<form:select path="memberCategoryCode" cssClass="input-xlarge"
									id="memberCategoryCode">
									<option value="">Select One</option>
								</form:select>
							</div>
						</div>
						<div class="control-group" id="eventDateCtl">
							<label class="control-label" for="eventDate">Event Date
								Time</label>
							<div class="controls">
								<div class="input-group date" id="datepicker">
									<form:input path="eventDate"
										cssClass="input-xlarge input-append form_datetime"
										id="eventDate" placeholder="Event Date" />
									&nbsp;<span class="input-prepend add-on"><i
										class="icon-calendar"></i></span>
								</div>
							</div>
						</div>
						<div class="control-group" id="rsvpDeadlineDateCtl">
							<label class="control-label" for="rsvpDeadlineDate">RSVP
								Deadline Date Time</label>
							<div class="controls">
								<div class="input-group date" id="datepicker">
									<form:input path="rsvpDeadlineDate" readonly="readonly"
										cssClass="input-xlarge input-append form_datetime"
										id="rsvpDeadlineDate" placeholder="RSVP Deadline Date" />
									&nbsp;<span class="input-prepend add-on"><i
										class="icon-calendar"></i></span>
								</div>
							</div>
						</div>
						<div class="control-group" id="expiryDateCtl">
							<label class="control-label" for="expiryDate">Event
								Expiry Date</label>
							<div class="controls">
								<div class="input-group date" id="datepicker">
									<form:input path="expiryDate" readonly="readonly"
										cssClass="input-xlarge input-append form_datetime"
										id="expiryDate"
										placeholder="Date after which event is invisible on portal" />
									&nbsp;<span class="input-prepend add-on"><i
										class="icon-calendar"></i></span>
								</div>
							</div>
						</div>
						<div class="control-group" id="groupEventInviteCodeLengthCtl">
							<label class="control-label" for="groupEventInviteCodeLength">Event
								Code Length</label>

							<div class="controls">
								<form:input path="groupEventInviteCodeLength"
									cssClass="input-xlarge" id="groupEventInviteCodeLength"
									placeholder="Enter Length of the Event Code" />
							</div>
						</div>
						<div class="control-group" id="emailRsvpAllowedCtl">
							<label class="control-label" for="emailRsvpAllowed">Email
								RSVP Allowed?</label>

							<div class="controls">
								<form:radiobutton path="emailRsvpAllowed"
									cssClass="input-xlarge" id="emailRsvpAllowed1" value="true" />
								YES&nbsp;&nbsp;
								<form:radiobutton path="emailRsvpAllowed"
									cssClass="input-xlarge" id="emailRsvpAllowed2" value="false" />
								NO
							</div>
						</div>



					</div>

					<div class="span5">
						<div class="control-group" id="maxNumberOfPassesCtl">
							<label class="control-label" for="maxNumberOfPasses">No
								of Event Passes</label>

							<div class="controls">

								<form:input path="maxNumberOfPasses" cssClass="input-xlarge"
									id="maxNumberOfPasses"
									placeholder="Enter max number of event passes" />
							</div>
						</div>
						<div class="control-group" id="externalSurveyRedirectUrlCtl">
							<label class="control-label" for="externalSurveyRedirectUrl">External
								Feedback/Survey URL</label>

							<div class="controls">

								<form:input path="externalSurveyRedirectUrl"
									cssClass="input-xlarge" id="externalSurveyRedirectUrl"
									placeholder="Enter External Feedback/Survey URL" />
							</div>
						</div>
						<div class="control-group" id="eventOrganiserCtl">
							<label class="control-label" for="eventOrganiser">Event
								Organiser</label>

							<div class="controls">

								<form:input path="eventOrganiser" cssClass="input-xlarge"
									id="eventOrganiser" placeholder="Enter Event Organizer Name" />
							</div>
						</div>
						<div class="control-group" id="outsideParticipationAllowedCtl">
							<label class="control-label" for="outsideParticipationAllowed">Outside
								Participation Allowed?</label>

							<div class="controls">
								<form:radiobutton path="outsideParticipationAllowed"
									cssClass="input-xlarge" id="outsideParticipationAllowed1"
									value="true" />
								YES&nbsp;&nbsp;
								<form:radiobutton path="outsideParticipationAllowed"
									cssClass="input-xlarge" id="outsideParticipationAllowed2"
									value="false" />
								NO
							</div>
						</div>
						<div class="control-group" id="paidEventCtl">
							<label class="control-label" for="paidEvent">Paid Event?</label>

							<div class="controls">
								<form:radiobutton path="paidEvent" cssClass="input-xlarge"
									id="paidEvent1" value="true" />
								YES&nbsp;&nbsp;
								<form:radiobutton path="paidEvent" cssClass="input-xlarge"
									id="paidEvent2" value="false" />
								NO
							</div>
						</div>
						<div class="control-group" id="amountMemberCtl">
							<label class="control-label" for="amountMember">Member's
								Price</label>

							<div class="controls">
								<div class="input-prepend ">
									<div class="input-prepend">
										<span class="add-on">Adult $</span>

										<form:input path="amountPerAdulthead" cssClass="input-mini"
											id="amountPerAdulthead" placeholder="Enter Amount Per Head" />
									</div>
								</div>
								<div class="input-prepend">
									<div class="input-prepend">
										<span class="add-on">Child $</span>

										<form:input path="amountPerKidHead" cssClass="input-mini"
											id="amountPerKidHead" placeholder="Enter Amount Per Head" />
									</div>
								</div>
							</div>
						</div>
						<div class="control-group" id="amountNonMemberCtl">
							<label class="control-label" for="amountNonMember">Non
								Member's Price</label>

							<div class="controls">
								<div class="input-prepend">
									<div class="input-prepend">
										<span class="add-on">Adult $</span>

										<form:input path="amountPerNMAdulthead" cssClass="input-mini"
											id="amountPerNMAdulthead" placeholder="Enter Amount Per Head" />
									</div>

								</div>
								<div class="input-prepend">
									<div class="input-prepend">
										<span class="add-on">Child $</span>

										<form:input path="amountPerNMKidHead" cssClass="input-mini"
											id="amountPerNMKidHead" placeholder="Enter Amount Per Head" />
									</div>
								</div>
							</div>
						</div>
						<div class="control-group" id="amountPerFamilyCtl">
							<label class="control-label" for="amountPerFamily">Amount
								per Family</label>

							<div class="controls">

								<form:input path="amountPerFamily" cssClass="input-xlarge"
									id="amountPerFamily" placeholder="Enter Amount Per Family" />
							</div>
						</div>

						<div class="control-group" id="eventInviteSentImmediatelyCtl">
							<label class="control-label" for="eventInviteSentImmediately">Event
								Invite Sent Immediately?</label>

							<div class="controls">
								<form:radiobutton path="eventInviteSentImmediately"
									cssClass="input-xlarge" id="eventInviteSentImmediately1"
									value="true" />
								YES&nbsp;&nbsp;
								<form:radiobutton path="eventInviteSentImmediately"
									cssClass="input-xlarge" id="eventInviteSentImmediately2"
									value="false" />
								NO
							</div>
						</div>

						<div class="control-group" id="autoResponseForRSVPAllowedCtl">
							<label class="control-label" for="autoResponseForRSVPAllowed">Auto
								Response for RSVP?</label>

							<div class="controls">
								<form:radiobutton path="autoResponseForRSVPAllowed"
									cssClass="input-xlarge" id="autoResponseForRSVPAllowed1"
									value="true"
									onclick="$('#autoResponseRSVPTemplateCtl').show();" />
								YES&nbsp;&nbsp;
								<form:radiobutton path="autoResponseForRSVPAllowed"
									cssClass="input-xlarge" id="autoResponseForRSVPAllowed2"
									value="false"
									onclick="$('#autoResponseRSVPTemplateCtl').hide();" />
								NO
							</div>
						</div>
						<div class="control-group" id="autoResponseRSVPTemplateCtl">
							<label class="control-label" for="autoResponseRSVPTemplate">RSVP
								Email Template Name</label>

							<div class="controls">
								<form:select path="autoResponseRSVPTemplate"
									cssClass="input-xlarge" id="autoResponseRSVPTemplate">
									<option value="">Select One</option>
								</form:select>
							</div>
						</div>
						<div class="control-group" id="processCompletionEmailTemplateCtl">
							<label class="control-label" for="processCompletionTemplate">Process Completion
								Email Template Name</label>

							<div class="controls">
								<form:select path="processCompletionTemplate"
									cssClass="input-xlarge" id="processCompletionTemplate">
									<option value="">Select One</option>
								</form:select>
							</div>
						</div>						
					</div>
				</fieldset>
				<input class="btn btn-primary btn-large" type="submit"
					value="SUBMIT" />
				<a
					href="${pageContext.request.contextPath}/${sessionScope.groupCode}/"
					class="btn btn-large">CANCEL</a>
			</form:form>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(
			function() {
				$("#groupEvent").validate(
						{
							rules : {
								eventCode : {
									required : true,
									minlength : 7,
									maxlength : 7
								},
								eventName : {
									required : true,
									maxlength : 25
								},
								groupCode : {
									required : true,
									minlength : 3,
									maxlength : 3
								},
								eventDate : {
									required : true,
								//dateITA : true
								},
								rsvpDeadlineDate : {
								//dateITA : true
								},
								groupEventInviteCodeLength : {
									number : true
								},
								maxNumberOfPasses : {
									number : true
								},
								externalSurveyRedirectUrl : {
									url : true
								},
								amountPerAdulthead : {
									number : true
								},
								amountPerKidHead : {
									number : true
								},
								amountPerNMAdulthead : {
									number : true
								},
								amountPerNMKidHead : {
									number : true
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
						})

			});

	$(function() {
		$('#autoResponseRSVPTemplateCtl').hide();
		buildGroupMemberCategoriesOptions('memberCategoryCode');
		buildGroupEmailTemplateOptionsByEventCode("NULL",
				'autoResponseRSVPTemplate');
		buildGroupEmailTemplateOptionsByEventCode("NULL",
		'processCompletionTemplate');
	});
</script>
