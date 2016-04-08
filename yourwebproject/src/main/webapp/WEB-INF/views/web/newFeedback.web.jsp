<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row">
	<div class="span12">
		<div class="hero-unit">
			<form:form commandName="feedback"
				action="saveFeedback"
				method="post" id="feedback">
				<fieldset>


					<h2>
						Your Feedback/Suggestions/Questions
						<c:if test="${eventName ne null && !empty eventName}"> for event : <c:out
								value="${eventName}" />
						</c:if>
					</h2>
					<br />



					<div class="span5">
						<div class="control-group" id="nameCtl">
							<label class="control-label" for="name">Name</label>

							<div class="controls">
								<c:choose>
									<c:when test="${feedback.name ne null}">
										<form:input path="name" cssClass="input-xlarge" id="name"
											readonly="true" />

									</c:when>
									<c:otherwise>
										<form:input path="name" cssClass="input-xlarge" id="name" />

									</c:otherwise>


								</c:choose>
							</div>
						</div>

						<div class="control-group" id="emailCtl">
							<label class="control-label" for="email">Email</label>

							<div class="controls">
								<c:choose>
									<c:when test="${feedback.email ne null}">
										<form:input path="email" cssClass="input-xlarge" id="email"
											readonly="true" />
									</c:when>
									<c:otherwise>
										<form:input path="email" cssClass="input-xlarge" id="email" />
									</c:otherwise>
								</c:choose>


							</div>
						</div>
						<div class="control-group" id="complimentsCtl">
							<label class="control-label" for="compliments">What went
								well?</label>

							<div class="controls">

								<form:textarea path="compliments" cssClass="input-xlarge"
									id="compliments" rows="5" />
							</div>
						</div>

					</div>

					<div class="span5">

						<div class="control-group" id="improvementAreasCtl">
							<label class="control-label" for="improvementAreas">What
								could be improved?</label>

							<div class="controls">
								<form:textarea path="improvementAreas" cssClass="input-xlarge"
									id="improvementAreas" rows="5" />
							</div>
						</div>
						<div class="control-group" id="commentsCtl">
							<label class="control-label" for="comments">Other General
								Comments</label>

							<div class="controls">

								<form:textarea path="comments" cssClass="input-xlarge"
									id="comments" />
							</div>
						</div>

						<input class="btn btn-primary btn-large" type="submit"
							value="SUBMIT" /> 
					</div>
				</fieldset>
				<form:hidden path="serialNumber" />
				<form:hidden path="groupEventCode" />
				<form:hidden path="groupCode" />
				<form:hidden path="id" />
				<form:hidden path="groupEventInviteId" />
			</form:form>
			<!-- form id="registerFileForm" action="${pageContext.request.contextPath}/files" method="POST" enctype="multipart/form-data" encoding="multipart/form-data">

		<ol>
			<li><label for="filePath">Server Path</label><input id="path" type="text" name="path" />
			<li><label for="fileXml">Input File</label><input id="file" type="file" name="file" /></li>
		</ol>
		<input id="uploadFile" type="submit" value="Upload" name="uploadFile" />  
	</form>  -->
		</div>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(
			function() {
				$("#feedback").validate(
						{
							rules : {
								name : {
									required : true
								},
								email : {
									required : true,
									email : true
								},
								comments : {
									required : true,
									maxlength : 2000
								},
								compliments : {
									maxlength : 2000
								},
								improvementAreas : {
									maxlength: 1000
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
						});
			});
</script>
