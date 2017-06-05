<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="row">
	<div class="span12">
		<div class="hero-unit">
			<form:form commandName="groupEmailTemplate"
				action="saveGroupEmailTemplate" method="post"
				id="groupEmailTemplate">
				<fieldset>


					<h2>Add/Update Group Email Template</h2>
					<br />



					<div class="span5">
						<div class="control-group" id="templateNameCtl">
							<label class="control-label" for="templateName">Email
								Template Name</label>

							<div class="controls">
								<form:input path="templateName" cssClass="input-xlarge"
									id="templateName"
									placeholder="Enter Template Name (E.g GroupBirthdayTemplate)" />
							</div>
						</div>
						<div class="control-group" id="subjectCtl">
							<label class="control-label" for="subject">Email Subject</label>

							<div class="controls">


								<form:input path="subject" cssClass="input-xlarge" id="subject"
									placeholder="Enter Email Subject" />
							</div>
						</div>
						<div class="control-group" id="templateContentCtl">
							<label class="control-label" for="templateContent">Email
								Template Body&nbsp;&nbsp;<a href="#" title="Fill the template content and click this to view the HTML page" onclick="loadModal($('#templateContent').val());"><span class="input-prepend add-on">(<i
										class="icon-question-sign"></i>)</span></a></label>

							<div class="controls">
								<form:textarea path="templateContent" rows="10"
									cssClass="input-xlarge" id="templateContent"
									placeholder="Enter Template Content (E.g. <html><body><h2>Hi ${groupMember.firstName},</h2>On behalf of the Melbourne Konkan Community, We would like to wish you a very happy birthday and a year full of happiness ahead of you. We would also like to take this oppurtunity to thank you for being a valued member of the MKC and extending your support.<br><br>We believe that you have enjoyed various events conducted by us.<p> <br>  </a>.</p><p>Thanks,<br/><h3>Mevan Dsouza</h3></p></body></html>)" />
							</div>
						</div>

						<div class="control-group" id="fromAliasCtl">
							<label class="control-label" for="fromAlias">From Alias
								(Email)</label>

							<div class="controls">

								<form:input path="fromAlias" cssClass="input-xlarge"
									id="fromAlias" placeholder="Enter Alias" />

							</div>
						</div>
						<div class="control-group" id="fromAliasPersonalStringCtl">
							<label class="control-label" for="fromAliasPersonalString">From
								Alias (Personal)</label>

							<div class="controls">

								<form:input path="fromAliasPersonalString"
									cssClass="input-xlarge" id="fromAliasPersonalString"
									placeholder="Enter Alias (E.g. Mr. John Doe)" />

							</div>
						</div>
						<div class="control-group" id="expressCtl">
							<label class="control-label" for="html">Express Email</label>

							<div class="controls">
								<form:radiobutton path="expressEmail" cssClass="input-xlarge" id="expressEmail1"
									value="true"  />
								YES&nbsp;&nbsp;
								<form:radiobutton path="expressEmail" cssClass="input-xlarge" id="expressEmail2"
									value="false" />
								NO
							</div>
						</div>


					</div>



					<div class="span5">
						<div class="control-group" id="replyToEmailCtl">
							<label class="control-label" for="replyToEmail">Reply to
								Email</label>

							<div class="controls">

								<form:input path="replyToEmail" cssClass="input-xlarge"
									id="replyToEmail" placeholder="Enter ReplyTo Email" />
							</div>
						</div>
						<div class="control-group" id="htmlCtl">
							<label class="control-label" for="html">HTML format</label>

							<div class="controls">
								<form:radiobutton path="html" cssClass="input-xlarge" id="html1"
									value="true" />
								YES&nbsp;&nbsp;
								<form:radiobutton path="html" cssClass="input-xlarge" id="html2"
									value="false" />
								NO
							</div>
						</div>
						<div id="moreDetails">
							<div class="control-group" id="attachmentsCtl">
								<label class="control-label" for="attachments">Attachments</label>

								<div class="controls">

									<form:input path="attachments" cssClass="input-xlarge"
										id="attachments" placeholder="Enter attachment path" />
								</div>
							</div>
						<div class="control-group" id="prefillCtl">
							<label class="control-label" for="html">Pre-fill Attachment</label>

							<div class="controls">
								<form:radiobutton path="prefillAttachments" cssClass="input-xlarge" id="prefill1"
									value="true" />
								YES&nbsp;&nbsp;
								<form:radiobutton path="prefillAttachments" cssClass="input-xlarge" id="prefill2"
									value="false" />
								NO
							</div>
						</div>


							<div class="control-group" id="emailAccountCodeCtl">
								<label class="control-label" for="emailAccountCode">Email
									Account Code</label>

								<div class="controls">


									<form:select path="emailAccountCode" cssClass="input-xlarge"
										id="emailAccountCode">
										<option value="">Select One</option>
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

									<form:input path="groupCode" cssClass="input-xlarge"
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
				<h3 id="myModalLabel">Email Template</h3>
			</div>
			<div class="modal-body">
				<p id="eHTML">First populate the Email Template content and click on the icon again.</p>
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
				$("#groupEmailTemplate").validate(
						{
							rules : {
								templateName : {
									required : true
								},
								subject : {
									required : true
								},
								templateContent : {
									required : true
								},
								fromAlias : {
									required : true,
									email : true
								},
								replyToEmail : {
									email : true
								},
								jobCronName : {
									required : true
								},
								jobCronExpression : {
									required : true
								},
								templateName : {
									required : true
								},
								jobStatusContactEmail : {
									email : true
								},
								groupCode : {
									required : true
								},
								emailAccountCode : {
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
		buildGroupEmailAccountOptions('emailAccountCode');
	});
	
	function loadModal(html)
	{
		if(html)
		$('#eHTML').html(html);
		$('#myModal').modal('show').css({'width': '70%', 'margin-left':'auto', 'margin-right':'auto', 'left':'15%'});
	}
</script>
