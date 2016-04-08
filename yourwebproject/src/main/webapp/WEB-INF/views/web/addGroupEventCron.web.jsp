<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="row">
	<div class="span12">
		<div class="hero-unit">
			<form:form commandName="groupCronJob" action="saveGroupEventCron"
				method="post" id="groupCronJob">
				<fieldset>


					<h2>Add a new Cron Job</h2>
					<br />



					<div class="span5">
						<div class="control-group" id="jobCodeCtl">
							<label class="control-label" for="jobCode">Job Code</label>

							<div class="controls">
								<form:input path="jobCode" cssClass="input-xlarge" id="jobCode"
									placeholder="Enter Job Code (E.g. ABC_XYZ)" />
							</div>
						</div>
						<div class="control-group" id="jobNameCtl">
							<label class="control-label" for="jobName">Job Name</label>

							<div class="controls">
								<form:input path="jobName" cssClass="input-xlarge" id="jobName"
									placeholder="Enter Job Name (E.g. genericJob)" />
							</div>
						</div>
						<div class="control-group" id="jobDescriptionCtl">
							<label class="control-label" for="jobDescription">Job
								Description</label>

							<div class="controls">
								<form:input path="jobDescription" cssClass="input-xlarge"
									id="jobDescription" placeholder="Enter Job Description" />
							</div>
						</div>



						<div class="control-group" id="membershipStartDateCtl">
							<label class="control-label" for="groupDates">Job Dates</label>
							<div class="controls">
								<div class="input-daterange input-group" id="datepicker">

									<form:input path="startDate"
										cssClass="input-small input-append form_datetime"
										id="startDate" placeholder="Start Date" />&nbsp;<span class="input-prepend add-on"><i
										class="icon-calendar"></i></span> <span><i>&nbsp;&nbsp;&nbsp;TO</i></span>
									<form:input path="expiryDate"
										cssClass="input-small input-append form_datetime"
										id="expiryDate" placeholder="End Date" />&nbsp;<span class="input-prepend add-on"><i
										class="icon-calendar"></i></span>
								</div>
							</div>
						</div>


						<div class="control-group" id="jobQueryStringCtl">
							<label class="control-label" for="jobQueryString">Query
								String</label>

							<div class="controls">

								<form:textarea path="jobQueryString" cssClass="input-xlarge"
									rows="5" id="jobQueryString"
									placeholder="Query String (E.g. from GroupMember gm where gm.expiryDate=NOW())" />

							</div>
						</div>

						<div class="control-group" id="disabledCtl">
							<label class="control-label" for="disabled">Disable Group
								Cron?</label>

							<div class="controls">
								<form:radiobutton path="disabled" value="true" />
								YES&nbsp;&nbsp;
								<form:radiobutton path="disabled" value="false" />
								NO
							</div>
						</div>

					</div>



					<div class="span5">
						<div id="moreDetails">
							<div class="control-group" id="jobCronNameCtl">
								<label class="control-label" for="jobCronName">Cron Name</label>

								<div class="controls">

									<form:input path="jobCronName" cssClass="input-xlarge"
										id="jobCronName"
										placeholder="Enter Cron Name (E.g. CRON_BDAY)" />
								</div>
							</div>
							<div class="control-group" id="jobCronExpressionCtl">
								<label class="control-label" for="jobCronExpression">Cron
									Expression</label>

								<div class="controls">

									<form:input path="jobCronExpression" cssClass="input-xlarge"
										id="jobCronExpression"
										placeholder="Enter Cron expression (E.g. 0 0 12 1/1 * ? * for everyday 12:00 P.M.)" />
								</div>
							</div>


							<div class="control-group" id="jobStatusContactEmailCtl">
								<label class="control-label" for="jobStatusContactEmail">Job
									Status Contact Email</label>

								<div class="controls">


									<form:input path="jobStatusContactEmail"
										cssClass="input-xlarge" id="jobStatusContactEmail"
										placeholder="Enter Contact Email" />
								</div>
							</div>
							
							<div class="control-group" id="groupCodeCtl">
								<label class="control-label" for="groupCode">Group Code</label>

								<div class="controls">

									<form:input path="groupCode" cssClass="input-xlarge"
										id="groupCode" placeholder="Enter Group Code" readonly="true" />
								</div>
							</div>
							<div class="control-group" id="memberCategoryCodeCtl">
								<label class="control-label" for="memberCategoryCode">Member
									Category Code</label>

								<div class="controls">

									<form:select path="memberCategoryCode" cssClass="input-xlarge" onchange="buildGroupEventsOptionsByMemberCategory(this.value, 'groupEventCode'); buildGroupEmailTemplateOptionsByEventCode('NULL','templateName');"
										id="memberCategoryCode">
										<option value="">Select One</option>
									</form:select>
								</div>
							</div>							
							<div class="control-group" id="groupEventCodeCtl">
								<label class="control-label" for="groupEventCode">Group
									Event Code</label>
								<div class="controls">

									<form:select path="groupEventCode" cssClass="input-xlarge" onchange="buildGroupEmailTemplateOptionsByEventCode(this.value,'templateName')"
										id="groupEventCode">
										<option value="">Select One</option>
									</form:select>
								</div>
							</div>
							<div class="control-group" id="templateNameCtl">
								<label class="control-label" for="templateName">Email
									Template Name</label>

								<div class="controls">


									<form:select path="templateName" cssClass="input-xlarge"
										id="templateName">
										<option value="">Select One</option>
									</form:select>
								</div>
							</div>


							<div class="control-group" id="commentsCtl">
								<label class="control-label" for="comments">Comments</label>

								<div class="controls">

									<form:textarea path="comments" cssClass="input-xlarge"
										id="comments" placeholder="Enter Comments" />

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
				$("#groupCronJob").validate(
						{
							rules : {
								jobCode : {
									required : true
								},
								jobName : {
									required : true
								},
								jobQueryString : {
									required : true,
									onkeyup: false,
									validateQuery: true
								},
								startDate : {
									required: true,
									//dateITA : true
								},
								expiryDate : {
									//dateITA : true
								},
								jobCronName : {
									required : true
								},
								jobCronExpression : {
									required : false
								},
								templateName : {
									required : true
								},
								jobStatusContactEmail : {
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
		buildGroupMemberCategoriesOptions('memberCategoryCode');
		buildGroupEventsOptionsByMemberCategory("NULL","groupEventCode");
		buildGroupEmailTemplateOptionsByEventCode("NULL",'templateName');
	});



	$.validator.addMethod("validateQuery", function(value, element) {
		var success = false;
		$.ajax({type: 'POST', url: "json/executeGenericQuery", data: {query:value}, success: function(data) {
			if(typeof data[0]!== 'undefined' && data[0].key==='count')
			{success = true;
			return success;
			}
		},dataType: 'json',async: false});
		return success;
	}, "Query Syntax Incorrect.");
</script>
