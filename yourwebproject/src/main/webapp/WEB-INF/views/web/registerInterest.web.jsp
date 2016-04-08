<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row">
	<div class="span12">
		<div class="hero-unit">
			<form:form commandName="registerInterest" action="saveInterest"
				method="post" id="registerInterest" onsubmit="return validateFormAndToggleButton('registerInterest');">
				<fieldset>


					<h2>Register Interest</h2>
					<br />



					<div class="span5">
						<div class="control-group" id="firstNameCtl">
							<label class="control-label" for="firstName">First Name</label>

							<div class="controls">
								<form:input path="firstName" cssClass="input-xlarge"
									id="firstName" placeholder="Enter First Name" />
							</div>
						</div>
						<div class="control-group" id="lastNameCtl">
							<label class="control-label" for="lastName">Last Name</label>

							<div class="controls">
								<form:input path="lastName" cssClass="input-xlarge"
									id="lastName" placeholder="Enter Last Name" />
							</div>
						</div>
						<div class="control-group" id="primaryEmailCtl">
							<label class="control-label" for="primaryEmail">Email</label>

							<div class="controls">
								<form:input path="primaryEmail" cssClass="input-xlarge"
									id="primaryEmail" placeholder="Enter Email" />
							</div>
						</div>
						<div class="control-group" id="mobilephoneCtl">
							<label class="control-label" for="mobilephone">Contact
								No.</label>

							<div class="controls">

								<form:input path="mobilephone" cssClass="input-xlarge"
									id="mobilephone" placeholder="Enter Phone Number" />
							</div>
						</div>
						<div class="control-group" id="interestTypeCtl">
							<label class="control-label" for="interestType">Interest
								Type</label>

							<div class="controls">

								<form:select path="interestType" cssClass="input-xlarge"
									id="interestType">
									<c:forEach items="${groupInterests}" var="groupInterest">
										<form:option value="${groupInterest.interestType}"
											label="${groupInterest.interestType}" />
									</c:forEach>

								</form:select>
							</div>
						</div>
					</div>

				</fieldset>

				<form:hidden path="id" id="id" />
				<form:hidden path="groupCode" id="groupCode" />
				<form:hidden path="campaign" id="campaign" />
				<form:hidden path="autoResponseEmailSent" id="autoResponseEmailSent"/>
				<input type="hidden" name="serialNumber" id="serialNumber" value="${serialNumber}">
				<button class="btn btn-primary btn-large has-spinner" type="submit" >SUBMIT</button>
			</form:form>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(
			function() {
				$("#registerInterest").validate(
						{
							rules : {
								firstName : {
									required : true
								},
								lastName : {
									required : false
								},
								primaryEmail : {
									required : true,
									email : true
								},
								interestType : {
									required : true
								},
								mobilephone : {
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
						})

			});

	

</script>
