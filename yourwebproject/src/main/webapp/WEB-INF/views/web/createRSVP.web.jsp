<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

		<c:if test="${groupEventInviteRSVP ne null}">
<div class="jumbotron">
					<c:if test="${rsvpMessage ne null && rsvpMessage ne ''}"><h2><c:out value="${rsvpMessage}"/><c:if test="${groupEvent.eventName ne null || groupEvent.eventName ne ''}"> for event : <c:out
								value="${groupEvent.eventName}" />
						</c:if></h2>
					<br /></c:if>
	<div>
		<div class="row">

			<form:form commandName="groupEventInviteRSVP"
				action="saveGroupEventInviteRSVP" method="post"
				id="groupEventInviteRSVP" onsubmit="return validateFormAndToggleButton('groupEventInviteRSVP');">




					<div class="col-lg-5">
						<div class="form-group" id="firstNameCtl">
							<label class="control-label" for="firstName">First Name</label>

							<div class="controls">
								<form:input path="groupEventInvite.groupMember.firstName"
									readonly="true" cssClass="form-control" id="firstName" />
							</div>
						</div>
						<div class="form-group" id="lastNameCtl">
							<label class="control-label" for="lastName">Last Name</label>

							<div class="controls">
								<form:input path="groupEventInvite.groupMember.lastName"
									readonly="true" cssClass="form-control" id="firstName" />
							</div>
						</div>
						<div class="form-group" id="rsvpOutcomeCtl">
							<label class="control-label" for="rsvpOutcome">Your
								Status?</label>
							<div>
							<div class="radio">
								<label>
								<form:radiobutton path="rsvpOutcome" value="true" id="rsvpOutcome1"/>
								Yes, I will attend
								</label>
								</div><div class="radio">
								<label>
								<form:radiobutton path="rsvpOutcome" value="false" id="rsvpOutcome2" onclick="toggleCounts();"/>
								No, I can't attend
								</label></div>
							</div>
						</div>
						<div class="form-group" id="adultCountCtl">
							<label class="control-label" for="adultCount">Adults
								Count</label>

							<div class="controls">

								<form:input path="adultCount" cssClass="form-control"
									id="adultCount" placeholder="Please enter 0 or greater"/>
							</div>
						</div>
						<div class="form-group" id="kidsCountCtl">
							<label class="control-label" for="kidsCount">Kids Count</label>

							<div class="controls">

								<form:input path="kidsCount" cssClass="form-control"
									id="kidsCount" placeholder="Please enter 0 or greater"/>
							</div>
						</div>
						<c:if test="${groupEvent.paidEvent && groupEventInviteRSVP.groupEventInvite.rsvpd}">
						<div class="form-group" id="transactionReferenceCtl">
							<label class="control-label" for="transactionReference">Transaction Reference No.</label>

							<div class="controls">

								<form:input path="transactionReference" cssClass="form-control"
									id="transactionReference" />
							</div>
						</div>						
						</c:if>
						<div class="form-group" id="rsvpCommentsCtl">
							<label class="control-label" for="rsvpComments">Comments</label>

							<div class="controls">

								<form:textarea path="rsvpComments" cssClass="form-control"
									id="rsvpComments" placeholder="You may enter upto 250 characters" />
							</div>
						</div>
						<input type="hidden" name="rsvpMessage" value="${rsvpMessage}" id = "rsvpMessage"/>
						<form:hidden path="groupEventInvite.groupEventInviteId"/>
						<c:choose>
						<c:when test="${!disableButton}">
						<button class="btn btn-primary btn-lg btn-block has-spinner" type="submit" data-loading-text="<span class='spinner'><i class='icon-spin glyphicon glyphicon-repeat'></i></span> Loading.." autocomplete="off">SUBMIT</button>
						</c:when>
						<c:otherwise>
						<input class="btn btn-primary btn-lg btn-block has-spinner" type="submit" disabled="disabled"
							value="SUBMIT" />
							
						</c:otherwise>
						</c:choose>
					


					</div>
			</form:form>

		</div>
		
	</div>
</div>
			</c:if>

<script type="text/javascript">
    $(document).ready(function () {
            $("#groupEventInviteRSVP").validate({
            rules:{
                firstName:{
                    required:true
                },
                rsvpOutcome:{
                    required:true
                },
                rsvpComments:{
                    maxlength: 250
                },
                adultCount: {
                	number: true,
                	required:true,
                    rsvpCheck: 1
                },
                kidsCount :{
                	number: true,
                	required:true
                },
                transactionReference : {
                	maxlength:100
                }
                
                
            },
            errorClass:"control-group has-error text-danger",
            validClass:"control-group has-success",
            errorElement:"span",
            errorPlacement: function(error, element) {
                if (element.attr("type") == "radio") {
                	
                    error.insertBefore(element.parent('label').parent('div'));
                } else {
                    error.insertAfter(element);
                }
            },
            highlight:function (element, errorClass, validClass) {
                if (element.type === 'radio') {
					this.findByName(element.name).parent("div").parent("div").removeClass(validClass).addClass(errorClass);
                } else {
                    $(element).parent("div").parent("div").removeClass(validClass).addClass(errorClass);
                }
            },
            unhighlight:function (element, errorClass, validClass) {
                if (element.type === 'radio') {
                    this.findByName(element.name).parent("div").parent("div").removeClass(errorClass).addClass(validClass);
                } else {
                    $(element).parent("div").parent("div").removeClass(errorClass).addClass(validClass);
                }
            }
        });
        
        jQuery.validator.addMethod("rsvpCheck", function(value, element, params) {
        	  if($('input[name="rsvpOutcome"]:checked').val()==="true") {return params<=($("#adultCount").val()+$("#kidsCount").val());}
        	  else {return true;}
        	}, jQuery.validator.format("Since you are attending, please provide adults and/or kids count."));

    });
    function toggleCounts()
    {
    	$('#adultCount').val(0);
    	$('#kidsCount').val(0);
    	$('#transactionReference').val("");
    	
    }
    
</script>
