<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="row">
	<div class="span12">
		<div class="hero-unit">
			<form:form commandName="groupSMSTemplate"
				action="saveGroupSMSTemplate" method="post"
				id="groupSMSTemplate">
				<fieldset>


					<h2>Add/Update Group SMS Template</h2>
					<br />



					<div class="span5">
						<div class="control-group" id="templateNameCtl">
							<label class="control-label" for="templateName">SMS
								Template Name</label>

							<div class="controls">
								<form:input path="templateName" cssClass="input-xlarge"
									id="templateName"
									placeholder="Enter Template Name (E.g GroupBirthdayTemplate)" />
							</div>
						</div>
						<div class="control-group" id="templateContentCtl">
							<label class="control-label" for="templateContent">SMS
								Template Body&nbsp;&nbsp;<a href="#" title="Fill the template content and click this to view the HTML page" onclick="loadModal($('#templateContent').val());"><span class="input-prepend add-on">(<i
										class="icon-question-sign"></i>)</span></a></label>

							<div class="controls">
								<form:textarea path="templateContent" rows="10"
									cssClass="input-xlarge" id="templateContent"
									placeholder="Enter Template Content (E.g. <html><body><h2>Hi ${groupMember.firstName},</h2>On behalf of the Melbourne Konkan Community, We would like to wish you a very happy birthday and a year full of happiness ahead of you. We would also like to take this oppurtunity to thank you for being a valued member of the MKC and extending your support.<br><br>We believe that you have enjoyed various events conducted by us.<p> <br>  </a>.</p><p>Thanks,<br/><h3>Mevan Dsouza</h3></p></body></html>)" />
							</div>
						</div>

					</div>



					<div class="span5">
						<div id="moreDetails">
							<div class="control-group" id="smsAccountCodeCtl">
								<label class="control-label" for="smsAccountCode">SMS
									Account Code</label>

								<div class="controls">
									<form:select path="smsAccountCode" cssClass="input-xlarge"
										id="smsAccountCode">
										<option value="">Select One</option>
										<option value="${sessionScope.groupCode}">Default (${sessionScope.groupCode})</option>
									</form:select>
								</div>
							</div>
							<div class="control-group" id="memberCategoryCodeCtl">
								<label class="control-label" for="memberCategoryCode">Member
									Category Code</label>

								<div class="controls">

									<form:select path="memberCategoryCode" cssClass="input-xlarge"
										id="memberCategoryCode"
										onchange="buildGroupEventsOptionsByMemberCategory(this.value, 'groupEventCode');">
										<option value="">Select One</option>
									</form:select>
								</div>
							</div>
							<div class="control-group" id="groupEventCodeCtl">
								<label class="control-label" for="groupEventCode">Group
									Event Code</label>

								<div class="controls">

									<form:select path="groupEventCode" cssClass="input-xlarge"
										id="groupEventCode">
										<option value="">Select One</option>
									</form:select>
								</div>
							</div>

							<div class="control-group" id="groupCodeCtl">
								<label class="control-label" for="groupCode">Group Code</label>

								<div class="controls">

									<form:input path="group.groupCode" cssClass="input-xlarge"
										id="groupCode" placeholder="Enter Group Code" readonly="true" />
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
				<form:hidden path="id" id="id"/>
				<input class="btn btn-primary btn-large" type="submit"
					value="SUBMIT" />
				<a
					href="${pageContext.request.contextPath}/${sessionScope.groupCode}/"
					class="btn btn-large">CANCEL</a>
			</form:form>
		</div>
		<!-- Modal -->
		<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true" align="center">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h3 id="myModalLabel">SMS Template</h3>
			</div>
			<div class="modal-body">
				<p id="eHTML">First populate the SMS Template content and click on the icon again.</p>
			</div>
			<div class="modal-footer">
				<button class="btn btn-primary" data-dismiss="modal" aria-hidden="true">OK</button>

			</div>
		</div>
		<!-- Modal End -->
	</div>
</div>

<script type="text/javascript">
	$(document).ready(
			function() {
				$("#groupSMSTemplate").validate(
						{
							rules : {
								templateName : {
									required : true
								},
								templateContent : {
									required : true
								},
								groupCode : {
									required : true
								},
								smsAccountCode : {
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
		$('.input-group.date').datepicker({
			format : "dd/mm/yyyy"
		});
		$('.input-daterange').datepicker({
			format : "dd/mm/yyyy"
		});
		buildGroupMemberCategoriesOptions('memberCategoryCode');
		buildGroupEventsOptionsByMemberCategory("NULL", "groupEventCode");

	});
	
	function loadModal(html)
	{
		if(html)
		$('#eHTML').html(html);
		$('#myModal').modal('show').css({'width': '70%', 'margin-left':'auto', 'margin-right':'auto', 'left':'15%'});
	}
</script>
