<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="row">
	<div class="span12">
		<div class="hero-unit">
			<form:form commandName="groupMember" action="saveGroupMember"
				method="post" id="groupMember">
				<fieldset>


					<h2>Add a new Group Member</h2>
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
							<label class="control-label" for="mobilephone">Mobile Phone</label>

							<div class="controls">

								<form:input path="mobilephone" cssClass="input-xlarge"
									id="mobilephone" placeholder="Enter Mobile Phone" />
							</div>
						</div>						
						<fieldset>
						<div class="control-group" id="groupCodeCtl">
							<label class="control-label" for="groupCode">Group Code</label>

							<div class="controls" >

								<form:input path="groupCode" cssClass="input-xlarge"
									id="groupCode" placeholder="Enter Group Code"  readonly="true"/>
							</div>
						</div>
						</fieldset>

					</div>

					<div class="span5">
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
						<div class="control-group" id="paidMemberCtl">
							<label class="control-label" for="paidMember">Membership
								Fees Paid?</label>

							<div class="controls">
								<form:radiobutton path="paidMember" cssClass="input-xlarge"
									id="paidMember" value="true"
									onclick="$('#moreDetails').show();" />
								YES&nbsp;&nbsp;
								<form:radiobutton path="paidMember" cssClass="input-xlarge"
									id="paidMember" value="false"
									onclick="$('#moreDetails').hide();" />
								NO
							</div>
						</div>
						<div id="moreDetails">
							<!-- div class="control-group" id="membershipStartDateCtl">
							<label class="control-label" for="membershipStartDate">Membership
								Start date</label>


							<div class="controls">
								<div class="input-group date">
									<form:input path="membershipStartDate" cssClass="input-xlarge"
										id="membershipStartDateCtl"/>
								<span class="add-on"><i class="icon-calendar"></i></span>
								</div>
							</div>
						</div>   -->

							<div class="control-group" id="membershipStartDateCtl">
								<label class="control-label" for="membershipDates">Membership
									Dates</label>
								<div class="controls">
								<div class="input-daterange input-group" id="datepicker">
									<form:input path="membershipStartDate"
										cssClass="form-control input-small input-append" id="membershipStartDate" placeholder = "Start Date" />&nbsp;<span class="input-prepend add-on"><i class="icon-calendar"></i></span>
									<span><i>&nbsp;&nbsp;&nbsp;TO</i></span>
									<form:input path="membershipEndDate"
										cssClass="form-control input-small input-append" id="membershipEndDate" placeholder="End Date" />&nbsp;<span class="input-prepend add-on"><i class="icon-calendar"></i></span>
								</div>
								</div>
							</div>
							<!-- div class="control-group" id="membershipEndDateCtl">
							<label class="control-label" for="membershipEndDate">Membership
								End date</label>


							<div class="controls">
								<div class="input-group date">
									<form:input path="membershipEndDate" data-provide="datepicker" data-date-format="dd/mm/yyyy" cssClass="input-xlarge"
										id="membershipEndDateCtl"/>
								<span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
								</div>
							</div>
						</div>  -->

							<div class="control-group" id="adultCountCtl">
								<label class="control-label" for="adultCount">Adult
									Count</label>

								<div class="controls">

									<form:input path="adultCount" cssClass="input-small"
										id="adultCount" placeholder="No. of Adults" />

								</div>
							</div>
							<div class="control-group" id="kidCountCtl">
								<label class="control-label" for="kidCount">Kid Count</label>

								<div class="controls">


									<form:input path="kidCount" cssClass="input-small"
										id="kidCount" placeholder="No. of Kids" />
								</div>
							</div>
							<div class="control-group" id="paidMembershipAmountCtl">
								<label class="control-label" for="paidMembershipAmount">Membership
									Amount Paid</label>

								<div class="controls">


									<form:input path="paidMembershipAmount" cssClass="input-small"
										id="paidMembershipAmount" placeholder="Amount Paid" />
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
				$("#groupMember").validate(
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
								membershipStartDate : {
									dateITA : true
								},
								membershipEndDate : {
									dateITA : true
								},
								adultCount : {
									number : true
								},
								kidCount : {
									number : true
								},
								mobilephone : {
									maxlength : 13
								},
								paidMembershipAmount : {
									number : true
								},
								memberCategoryCode : {
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

	$(function() {

		$('.input-daterange').datepicker({
			format : "dd/mm/yyyy",
	        autoclose: true,
	        todayHighlight: true
		});
		$('#moreDetails').hide();
		buildGroupMemberCategoriesOptions('memberCategoryCode');
	});
	

</script>
