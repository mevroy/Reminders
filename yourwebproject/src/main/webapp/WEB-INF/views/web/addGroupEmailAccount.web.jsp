<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="row">
	<div class="span12">
		<div class="hero-unit">
			<form:form commandName="groupEmailAccount" action="saveGroupEmailAccount"
				method="POST" id="groupEmailAccount">
				<fieldset>


					<h2>Add Group Email Account</h2>
					<br />



					<div class="span5">
						<div class="control-group" id="emailAccountCodeCtl">
							<label class="control-label" for="emailAccountCode">Email Account Code</label>

							<div class="controls">
								<form:input path="emailAccountCode" cssClass="input-xlarge"
									id="emailAccountCode" placeholder="Enter Email Account Code (Upto 7 chars)" />
							</div>
						</div>
						<div class="control-group" id="protocolCtl">
							<label class="control-label" for="protocol">Protocol</label>

							<div class="controls">
								<form:input path="protocol" cssClass="input-xlarge"
									id="protocol" placeholder="Enter Protocol (E.g. smtp 587, smtps 465)" />
							</div>
						</div>
						<div class="control-group" id="hostCtl">
							<label class="control-label" for="host">Host Name</label>

							<div class="controls">

								<form:input path="host" cssClass="input-xlarge"
									id="host" placeholder="Enter Host Name (E.g. smtp.gmail.com)" />
							</div>
						</div>
						<div class="control-group" id="portCtl">
							<label class="control-label" for="port">Port Number</label>

							<div class="controls">

								<form:input path="port" cssClass="input-xlarge"
									id="port" placeholder="Enter Port Number (E.g 587, 465 for GMAIL smtp)" />
							</div>
						</div>						
						<div class="control-group" id="usernameCtl">
							<label class="control-label" for="mobilephone">Account Username</label>

							<div class="controls">

								<form:input path="username" cssClass="input-xlarge"
									id="username" placeholder="Enter Account Username (E.g abc@gmail.com)" />
							</div>
						</div>
						<div class="control-group" id="passwordCtl">
							<label class="control-label" for="password">Account Password</label>

							<div class="controls">

								<form:password path="password" cssClass="input-xlarge"
									id="password" placeholder="Enter Account Password" />
							</div>
						</div>
					</div>

				</fieldset>
				<form:hidden path="groupCode" id="groupCode"/>
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
				$("#groupEmailAccount").validate(
						{
							rules : {
								emailAccountCode : {
									required : true,
									maxlength: 7
								},
								protocol : {
									required : false
								},
								username : {
									required : true
									//email : true
								},
								host : {
									required : true
								},
								port : {
									required : true,
									number : true
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
						})

			});

	

</script>
