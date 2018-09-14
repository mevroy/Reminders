<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="row">
	<div class="span12">
		<div class="hero-unit">
			<form:form commandName="group" action="saveGroup" method="post"
				id="group">
				<fieldset>


					<h2>Add a new Group</h2>
					<br />



					<div class="span5">
						<div class="control-group" id="groupLongNameCtl">
							<label class="control-label" for="groupLongName">Group
								Name</label>

							<div class="controls">
								<form:input path="groupLongName" cssClass="input-xlarge"
									id="groupLongName" placeholder="Enter Group Name" />
							</div>
						</div>
						<div class="control-group" id="descriptionCtl">
							<label class="control-label" for="description">Group Description</label>

							<div class="controls">
								<form:input path="description" cssClass="input-xlarge"
									id="description" placeholder="Enter Description" />
							</div>
						</div>
						<div class="control-group" id="groupCodeCtl">
							<label class="control-label" for="groupCode">Group Code</label>

							<div class="controls">

								<form:input path="groupCode" cssClass="input-xlarge" 
									id="groupCode" placeholder="Enter 3 digit Group Code" />
							</div>
						</div>

						<div class="control-group" id="membershipStartDateCtl">
							<label class="control-label" for="groupDates">Group Dates</label>
							<div class="controls">
								<div class="input-daterange input-group" id="datepicker">

									<form:input path="startDate"
										cssClass="form-control input-small input-append" id="startDate" placeholder = "Start Date" />&nbsp;<span class="input-prepend add-on"><i class="icon-calendar"></i></span>
									<span><i>&nbsp;&nbsp;&nbsp;TO</i></span>
									<form:input path="expiryDate"
										cssClass="form-control input-small input-append" id="expiryDate" placeholder="End Date" />&nbsp;<span class="input-prepend add-on"><i class="icon-calendar"></i></span>
								</div>
							</div>
						</div>


						<div class="control-group" id="avgMembersCountCtl">
							<label class="control-label" for="avgMembersCount">Approximate
								Member Count</label>

							<div class="controls">

								<form:input path="avgMembersCount" cssClass="input-xlarge"
									id="avgMembersCount" placeholder="No. of Members" />

							</div>
						</div>

					</div>



					<div class="span5">
						<div id="moreDetails">

							<div class="control-group" id="contactNameCtl">
								<label class="control-label" for="contactName">Group
									Contact Name</label>

								<div class="controls">


									<form:input path="contactName" cssClass="input-xlarge"
										id="contactName" placeholder="Group Contact Name" />
								</div>
							</div>

							<div class="control-group" id="contactEmailCtl">
								<label class="control-label" for="contactEmail">Group
									Contact Email</label>

								<div class="controls">


									<form:input path="contactEmail" cssClass="input-xlarge"
										id="contactEmail" placeholder="Group Contact Email" />
								</div>
							</div>
							<div class="control-group" id="contactNumberCtl">
								<label class="control-label" for="contactNumber">Group
									Contact Phone</label>

								<div class="controls">


									<form:input path="contactNumber" cssClass="input-xlarge"
										id="contactNumber" placeholder="Group Contact Phone" />
								</div>
							</div>


							<div class="control-group" id="groupAdminCtl">
								<label class="control-label" for="groupAdmin">Group
									Admin</label>

								<div class="controls">

									<form:input path="groupAdmin" cssClass="input-xlarge"
										id="groupAdmin" placeholder="Group Admin" />

								</div>
							</div>
							
							
														<div class="control-group" id="registrationEmailSendCtl">
								<label class="control-label" for="registrationEmailSend">Send
									Registration Email?</label>

								<div class="controls">
									<form:radiobutton path="registrationEmailSend"
										value="true" />
									YES&nbsp;&nbsp;
									<form:radiobutton path="registrationEmailSend"
										 value="false" />
									NO
								</div>
							</div>


							<div class="control-group" id="groupEmailSendCtl">
								<label class="control-label" for="groupEmailSend">Send
									Group Email?</label>

								<div class="controls">
									<form:radiobutton path="groupEmailSend"
										value="true" />
									YES&nbsp;&nbsp;
									<form:radiobutton path="groupEmailSend"
										value="false" />
									NO
								</div>
							</div>

							
							
						</div>


					</div>
				</fieldset>
				<input class="btn btn-primary btn-large" type="submit"
					value="SUBMIT" />
				<a href="${pageContext.request.contextPath}/${sessionScope.groupCode}/" class="btn btn-large">CANCEL</a>
			</form:form>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(
			function() {
				$("#group").validate(
						{
							rules : {
								groupLongName : {
									required : true
								},
								description : {
									required : true
								},
								groupCode : {
									required : true
								},
								startDate : {
									required: true,
									dateITA : true
								},
								expiryDate : {
									required: true,
									dateITA : true
								},
								avgMembersCount : {
									number : true
								},
								contactName : {
									required : true
								},
								contactNumber : {
									required : true
								},
								contactEmail : {
									required : true,
									email : true
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

		$('.input-daterange').datepicker({
			format : "dd/mm/yyyy",
	        autoclose: true,
	        todayHighlight: true
		});

	});
</script>
