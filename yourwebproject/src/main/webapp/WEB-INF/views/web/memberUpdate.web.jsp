<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div id="0">
	<div class="jumbotron">
		<h2>Membership Summary</h2>
		<h5>Note: If you need to update your details (email,phone etc.)
			or need to add/update dependents, click the EDIT button below and
			follow the screens.</h5>
		<div class="row">
			<c:set var="looper" value="1" />
			<div>
				<div class="table-responsive">
					<table class='table table-striped table-bordered table-condensed'>
						<tr class="danger">
							<td><b>No</b></td>
							<td><b>Name</b></td>
							<td><b>Email</b></td>
							<td><b>Relationship</b></td>
						</tr>
						<tr>
							<td>${looper}</td>
							<td>${groupMember.firstName}</td>
							<td>${groupMember.primaryEmail}</td>
							<td>SELF</td>
						</tr>

						<c:forEach items="${groupMember.groupDependents}" var="deps"
							varStatus="ctr">
							<c:set var="looper" value="${looper +1}"></c:set>
							<tr>
								<td>${looper}</td>
								<td>${deps.firstName}</td>
								<td>${deps.email}</td>
								<td>${deps.relationship}</td>
							</tr>
						</c:forEach>
					</table>
				</div>

				<div class="col-lg-12">
					<div class="form-group">
						<button class="btn btn-primary btn-lg btn-block has-spinner"
							type="submit" onclick="hideById('0'); showById('1');"
							data-loading-text="<span class='spinner'><i class='icon-spin glyphicon glyphicon-repeat'></i></span> Loading..">EDIT</button>
					</div>
				</div>
			</div>

		</div>
	</div>
</div>
<div id="1" style="display: none;">
	<div class="jumbotron">
		<h2>Primary Member</h2>
		<div class="row">
			<div>

				<form:form commandName="groupMember" action="json/updateGroupMember"
					method="post" id="groupMember"
					onsubmit="event.preventDefault(); javascript:postFormAndToggleError('groupMember','json/updateGroupMember','1', '20');">

					<div class="col-lg-5">
						<div class="form-group" id="firstNameCtl">
							<label class="control-label" for="firstName">First Name</label>

							<div class="controls">
								<form:input path="firstName" cssClass="form-control"
									id="firstName" placeholder="Enter First Name" />
							</div>
						</div>
						<div class="form-group" id="lastNameCtl">
							<label class="control-label" for="lastName">Last Name</label>

							<div class="controls">
								<form:input path="lastName" cssClass="form-control"
									id="lastName" placeholder="Enter Last Name" />
							</div>
						</div>
						<div class="form-group" id="primaryEmailCtl">
							<label class="control-label" for="primaryEmail">Email</label>

							<div class="controls">
							<div class="input-group">
								<form:input path="primaryEmail" cssClass="form-control"
									id="primaryEmail" placeholder="Enter Email" />
									<div class="input-group-addon">
										<span><i class="glyphicon glyphicon-envelope"></i></span>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group" id="aliasNameCtl">
							<label class="control-label" for="aliasName">How would
								you like to be addressed? <a tabindex="0" role="button"
								data-toggle="popover" data-container="body"
								data-animation="true" data-trigger="hover"
								title="Do you have nick name?" data-placement="top"
								data-content="You will be addressed by this name in all correspondences from MKC. If you don't have one, no worries, we will address you by your first and last name"><span
									class="input-prepend add-on"><i
										class="glyphicon glyphicon-info-sign"></i></span></a>
							</label>

							<div class="controls">
								<form:input path="aliasName" cssClass="form-control"
									id="aliasName" placeholder="e.g Sam" />
							</div>
						</div>
					</div>
					<div class="col-lg-offset-2 col-lg-5">
						<div class="form-group" id="birthdayCtl">
							<label class="control-label" for="birthday">DOB <a
								tabindex="0" role="button" data-toggle="popover"
								data-container="body" data-animation="true" data-trigger="hover"
								title="Why do we need your DOB?" data-placement="top"
								data-content="Just to send you wishes. Its ok not to provide one!"><span
									class="input-prepend add-on"><i
										class="glyphicon glyphicon-info-sign"></i></span></a></label>
							<div class="controls">
								<div class="input-group date">
									<fmt:formatDate value='${groupMember.birthday}' var="bday"
										pattern='dd/MM/yyyy' />
									<form:input path="birthday" cssClass="form-control"
										value="${bday}" id="birthday"
										placeholder="Date of Birth (DD/MM/YYYY)" />
									<div class="input-group-addon">
										<span><i class='glyphicon glyphicon-calendar'></i></span>
									</div>
								</div>

							</div>
						</div>
						<div class="form-group" id="mobilephoneCtl">
							<label class="control-label" for="mobilephone">Mobile No.</label>

							<div class="controls">
<div class="input-group">
								<form:input path="mobilephone" cssClass="form-control"
									id="mobilephone" placeholder="Enter Mobile Phone Number" />
									<div class="input-group-addon">
										<span><i class='glyphicon glyphicon-phone'></i></span>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group" id="otherPhoneCtl">
							<label class="control-label" for="otherPhone">Home Phone
								No</label>

							<div class="controls">
<div class="input-group">
								<form:input path="otherPhone" cssClass="form-control"
									id="otherPhone" placeholder="Enter Home Phone Number" />
									<div class="input-group-addon">
										<span><i class='glyphicon glyphicon-phone-alt'></i></span>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="col-lg-12">
						<div class="form-group">
							<form:hidden path="serialNumber" id="serialNumber" />
							<button class="btn btn-primary btn-lg btn-block has-spinner"
								type="submit" onclick="return $('#groupMember').valid();"
								data-loading-text="<span class='spinner'><i class='icon-spin glyphicon glyphicon-repeat'></i></span> Loading..">SAVE
								AND CONTINUE</button>
						</div>
					</div>




				</form:form>
			</div>
		</div>
	</div>
</div>



<c:set var="depCount" value="20"></c:set>
<c:set var="previous" value="1"></c:set>
<c:forEach items="${groupMember.groupDependents}" var="dependents"
	varStatus="counter">
	<c:set var="depCount" value="${depCount + 1}"></c:set>
	<div id="2${counter.index}" style="display: none;">
		<div class="jumbotron">
			<h2>
				Dependent
				<c:out value="${counter.index +1}"></c:out>
			</h2>
			<div class="row">
				<div>

					<form:form commandName="groupMember" action="json/updateDependent"
						method="post" id="dependent${counter.index}"
						onsubmit="event.preventDefault(); javascript:postFormAndToggleError('dependent${counter.index}','json/updateDependent','2${counter.index}','${depCount}');">
						<div class="col-lg-5">
							<div class="form-group"
								id="groupDependents[${counter.index}].firstNameCtl">
								<label class="control-label"
									for="groupDependents[${counter.index}].firstName">Dependent
									First Name</label>

								<div class="controls">
									<form:input path="groupDependents[${counter.index}].firstName"
										cssClass="form-control" id="firstName"
										placeholder="Enter First Name" />
								</div>
							</div>
							<div class="form-group"
								id="groupDependents[${counter.index}].lastNameCtl">
								<label class="control-label"
									for="groupDependents[${counter.index}].lastName">Dependent
									Last Name</label>

								<div class="controls">
									<form:input path="groupDependents[${counter.index}].lastName"
										cssClass="form-control" id="lastName"
										placeholder="Enter Last Name" />
								</div>
							</div>
							<div class="form-group"
								id="groupDependents[${counter.index}].emailCtl">
								<label class="control-label"
									for="groupDependents[${counter.index}].email">Email</label>

								<div class="controls">
								<div class="input-group">
									<form:input path="groupDependents[${counter.index}].email"
										cssClass="form-control" id="email" placeholder="Enter Email" />
										<div class="input-group-addon">
										<span><i class="glyphicon glyphicon-envelope"></i></span>
									</div>
								</div>
								</div>
							</div>
						</div>
						<div class="col-lg-offset-2 col-lg-5">
							<div class="form-group"
								id="groupDependents[${counter.index}].birthdayCtl">
								<label class="control-label"
									for="groupDependents[${counter.index}].birthday">DOB <a
									tabindex="0" role="button" data-toggle="popover"
									data-container="body" data-animation="true"
									data-trigger="hover" title="Why do we need your DOB?"
									data-placement="top"
									data-content="Just to send you wishes. Its ok not to provide one!"><span
										class="input-prepend add-on"><i
											class="glyphicon glyphicon-info-sign"></i></span></a></label>
								<div class="controls">
									<div class="input-group date">
										<fmt:formatDate
											value='${groupMember.groupDependents[counter.index].birthday}'
											var="bday" pattern='dd/MM/yyyy' />
										<form:input path="groupDependents[${counter.index}].birthday"
											cssClass="form-control" value="${bday}" id="birthday"
											placeholder="Date of Birth (DD/MM/YYYY)" />
										<div class="input-group-addon">
											<span><i class='glyphicon glyphicon-calendar'></i></span>
										</div>
									</div>

								</div>
							</div>
							<div class="form-group"
								id="groupDependents[${counter.index}].mobilephoneCtl">
								<label class="control-label"
									for="groupDependents[${counter.index}].mobilephone">Mobile
									No.</label>

								<div class="controls">
<div class="input-group">
									<form:input
										path="groupDependents[${counter.index}].mobilephone"
										cssClass="form-control" id="mobilephone"
										placeholder="Enter Mobile Phone Number" />
										<div class="input-group-addon">
										<span><i class='glyphicon glyphicon-phone'></i></span>
									</div>
								</div>
								</div>
							</div>
							<div class="form-group"
								id="groupDependents[${counter.index}].relationshipCtl">
								<label class="control-label"
									for="groupDependents[${counter.index}].relationship">Relationship</label>

								<div class="controls">
									<form:select
										path="groupDependents[${counter.index}].relationship"
										cssClass="form-control" id="relationship">
										<form:option value="">Select One</form:option>>
										<form:option value="CHILD">CHILD</form:option>
										<form:option value="SPOUSE">SPOUSE</form:option>
										<form:option value="PARENT">PARENT</form:option>
									</form:select>
								</div>
							</div>
						</div>

						<div class="col-lg-12">
							<div class="form-group">
								<form:hidden
									path="groupDependents[${counter.index}].dependentserialNumber"
									id="groupDependents[${counter.index}].dependentserialNumber" />
								<form:hidden path="serialNumber" id="serialNumber" />
								<button class="btn btn-primary btn-lg btn-block has-spinner"
									type="submit"
									onclick="return $('#dependent${counter.index}').valid();"
									data-loading-text="<span class='spinner'><i class='icon-spin glyphicon glyphicon-repeat'></i></span> Loading..">SAVE
									AND CONTINUE</button>
							</div>
						</div>


					</form:form>
					<div class="col-lg-12">
						<div class="form-group">
							<button class="btn btn-primary btn-lg btn-block has-spinner"
								type="submit"
								onclick="hideById('2${counter.index}'); showById('${previous}');"
								data-loading-text="<span class='spinner'><i class='icon-spin glyphicon glyphicon-repeat'></i></span> Loading..">BACK</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<c:set var="previous" value="2${counter.index}"></c:set>
</c:forEach>




<div id="${depCount}" style="display: none;">
	<div class="jumbotron">
		<div class="row">


			<div id="newDependent" style="display: none;">

				<form:form commandName="groupDependents"
					action="updateGroupDependents" method="post" id="dependentx"
					onsubmit="event.preventDefault(); javascript:postFormAndToggleError('dependentx','updateGroupDependents?serialNumber=${groupMember.serialNumber}','newDependent','addDepButton'); ">
					<div class="col-lg-5">
						<div class="form-group" id="firstNameCtl">
							<label class="control-label" for="firstName">Dependent
								First Name</label>

							<div class="controls">
								<form:input path="firstName" cssClass="form-control"
									id="firstName" placeholder="Enter First Name" />
							</div>
						</div>
						<div class="form-group" id="lastNameCtl">
							<label class="control-label" for="lastName">Dependent
								Last Name</label>

							<div class="controls">
								<form:input path="lastName" cssClass="form-control"
									id="lastName" placeholder="Enter Last Name" />
							</div>
						</div>

						<div class="form-group" id="emailCtl">
							<label class="control-label" for="email">Email</label>
							<div class="controls">
								<div class="input-group">
									<form:input path="email" cssClass="form-control" id="email"
										placeholder="Enter Email" />
									<div class="input-group-addon"><span><i class="glyphicon glyphicon-envelope"></i></span></div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-lg-offset-2 col-lg-5">
						<div class="form-group" id="birthdayCtl">
							<label class="control-label" for="birthday">DOB <a
								tabindex="0" role="button" data-toggle="popover"
								data-container="body" data-animation="true" data-trigger="hover"
								title="Why do we need your DOB?" data-placement="top"
								data-content="Just to send you wishes. Its ok not to provide one!"><span
									class="input-prepend add-on"><i
										class="glyphicon glyphicon-info-sign"></i></span></a></label>
							<div class="controls">
								<div class="input-group date">
									<form:input path="birthday" cssClass="form-control"
										id="birthday" placeholder="Date of Birth (DD/MM/YYYY)" />
									<div class="input-group-addon">
										<span><i class='glyphicon glyphicon-calendar'></i></span>
									</div>
								</div>

							</div>
						</div>
						<div class="form-group" id="mobilephoneCtl">
							<label class="control-label" for="mobilephone">Mobile No.</label>

							<div class="controls">
								<div class="input-group">
									<form:input path="mobilephone" cssClass="form-control"
										id="mobilephone" placeholder="Enter Mobile Phone Number" />
									<div class="input-group-addon">
										<span><i class='glyphicon glyphicon-phone'></i></span>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group" id="relationshipCtl">
							<label class="control-label" for="relationship">Relationship</label>

							<div class="controls">
								<form:select path="relationship" cssClass="form-control"
									id="relationship">
									<option value="">Select One</option>
									<option value="CHILD">CHILD</option>
									<option value="SPOUSE">SPOUSE</option>
									<option value="PARENT">PARENT</option>
								</form:select>
							</div>
						</div>
					</div>

					<div class="col-lg-12">
						<div class="form-group">
							<button class="btn btn-primary btn-lg btn-block has-spinner"
								type="submit" onclick="return $('#dependentx').valid();"
								data-loading-text="<span class='spinner'><i class='icon-spin glyphicon glyphicon-repeat'></i></span> Loading..">SAVE
								AND CONTINUE</button>
						</div>
					</div>
				</form:form>
				<div class="col-lg-12">
					<div class="form-group">
						<button class="btn btn-default btn-lg btn-block has-spinner"
							type="submit"
							onclick=" hideById('${depCount}'); showById('previewScreen'); return false;"
							data-loading-text="<span class='spinner'><i class='icon-spin glyphicon glyphicon-repeat'></i></span> Loading..">SKIP</button>
					</div>
				</div>
			</div>

			<div id="addDepButton">
				<div class="col-lg-12">
					<div class="form-group">
						<button class="btn btn-primary btn-lg btn-block" type="submit"
							onclick="hideById('addDepButton'); showById('newDependent'); $('#dependentx')[0].reset(); ">ADD
							MORE DEPENDENTS</button>
					</div>
				</div>
				<div class="col-lg-12">
					<div class="form-group">
						<button class="btn btn-default btn-lg btn-block has-spinner"
							type="submit"
							onclick="hideById('${depCount}'); showById('previewScreen');"
							data-loading-text="<span class='spinner'><i class='icon-spin glyphicon glyphicon-repeat'></i></span> Loading..">DONE</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="previewScreen" style="display: none;">
	<div class="jumbotron">
		<div class="row">
			<div class="col-lg-12">
				<div class="alert alert-info">
					<i class="icon-exclamation-sign"></i> All changes to your account
					are now complete. Thanks for your time!
				</div>
			</div>
			<div class="col-lg-12">
				<div class="form-group">
					<a href="loadGroupMember?serialNumber=${groupMember.serialNumber}"
						class="btn btn-primary btn-lg btn-block has-spinner"
						data-loading-text="<span class='spinner'><i class='icon-spin glyphicon glyphicon-repeat'></i></span> Loading..">VIEW
						SUMMARY</a>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(
			function() {
				$('.input-group.date').datepicker({
					format : "dd/mm/yyyy"
				});

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
								mobilephone : {
									required : true
								}
							},
							errorPlacement : function(error, element) {
								error.appendTo(element.parent("div").parent(
										"div"));
							},
							errorClass : "control-group has-error text-danger",
							validClass : "control-group has-success",
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

	$("#dependentx").validate(
			{
				rules : {
					firstName : {
						required : true
					},
					lastName : {
						required : false
					},
					primaryEmail : {
						required : false,
						email : true
					},
					mobilephone : {
						required : false
					},
					relationship : {
						required : true
					}
				},
				errorClass : "control-group has-error text-danger",
				validClass : "control-group has-success",
				errorElement : "span",
				highlight : function(element, errorClass, validClass) {
					if (element.type === 'radio') {
						this.findByName(element.name).parent("div").parent(
								"div").removeClass(validClass).addClass(
								errorClass);
					} else {
						$(element).parent("div").parent("div").removeClass(
								validClass).addClass(errorClass);
					}
				},
				unhighlight : function(element, errorClass, validClass) {
					if (element.type === 'radio') {
						this.findByName(element.name).parent("div").parent(
								"div").removeClass(errorClass).addClass(
								validClass);
					} else {
						$(element).parent("div").parent("div").removeClass(
								errorClass).addClass(validClass);
					}
				}
			});

	$("#dependent0").validate(
			{
				rules : {
					firstName : {
						required : true
					},
					lastName : {
						required : false
					},
					primaryEmail : {
						required : false,
						email : true
					},
					mobilephone : {
						required : false
					},
					relationship : {
						required : true
					}
				},
				errorClass : "control-group has-error text-danger",
				validClass : "control-group has-success",
				errorElement : "span",
				highlight : function(element, errorClass, validClass) {
					if (element.type === 'radio') {
						this.findByName(element.name).parent("div").parent(
								"div").removeClass(validClass).addClass(
								errorClass);
					} else {
						$(element).parent("div").parent("div").removeClass(
								validClass).addClass(errorClass);
					}
				},
				unhighlight : function(element, errorClass, validClass) {
					if (element.type === 'radio') {
						this.findByName(element.name).parent("div").parent(
								"div").removeClass(errorClass).addClass(
								validClass);
					} else {
						$(element).parent("div").parent("div").removeClass(
								errorClass).addClass(validClass);
					}
				}
			});
</script>
