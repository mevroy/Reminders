<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

		<c:if test="${groupEventInviteRSVP ne null}">
<div class="row">
	<div class="span12">
		<div class="hero-unit">

			<form:form commandName="groupEventInviteRSVP"
				action="saveGroupEventInviteRSVP" method="post"
				id="groupEventInviteRSVP" onsubmit="return validateFormAndToggleButton('groupEventInviteRSVP');">
				<fieldset>


					<c:if test="${rsvpMessage ne null && rsvpMessage ne ''}"><h2><c:out value="${rsvpMessage}"/><c:if test="${groupEvent.eventName ne null || groupEvent.eventName ne ''}"> for event : <c:out
								value="${groupEvent.eventName}" />
						</c:if></h2>
					<br /></c:if>



					<div class="span5">
						<div class="control-group" id="firstNameCtl">
							<label class="control-label" for="firstName">First Name</label>

							<div class="controls">
								<form:input path="groupEventInvite.groupMember.firstName"
									readonly="true" cssClass="input-xlarge" id="firstName" />
							</div>
						</div>
						<div class="control-group" id="lastNameCtl">
							<label class="control-label" for="lastName">Last Name</label>

							<div class="controls">
								<form:input path="groupEventInvite.groupMember.lastName"
									readonly="true" cssClass="input-xlarge" id="firstName" />
							</div>
						</div>
						<div class="control-group" id="rsvpOutcomeCtl">
							<label class="control-label" for="rsvpOutcome">Your
								Status?</label>
							<div class="controls">
							<div>
								<form:radiobutton path="rsvpOutcome" value="true" id="rsvpOutcome1"/>
								Yes, I will attend&nbsp;&nbsp;
								<form:radiobutton path="rsvpOutcome" value="false" id="rsvpOutcome2" onclick="toggleCounts();"/>
								No, I can't attend
								</div>
							</div>
						</div>
						<div class="control-group" id="adultCountCtl">
							<label class="control-label" for="adultCount">Adults
								Count</label>

							<div class="controls">

								<form:input path="adultCount" cssClass="input-xlarge"
									id="adultCount" placeholder="Please enter 0 or greater"/>
							</div>
						</div>
						<div class="control-group" id="kidsCountCtl">
							<label class="control-label" for="kidsCount">Kids Count</label>

							<div class="controls">

								<form:input path="kidsCount" cssClass="input-xlarge"
									id="kidsCount" placeholder="Please enter 0 or greater"/>
							</div>
						</div>
						<c:if test="${groupEvent.paidEvent && groupEventInviteRSVP.groupEventInvite.rsvpd}">
						<div class="control-group" id="transactionReferenceCtl">
							<label class="control-label" for="transactionReference">Transaction Reference No.</label>

							<div class="controls">

								<form:input path="transactionReference" cssClass="input-xlarge"
									id="transactionReference" />
							</div>
						</div>						
						</c:if>
						<div class="control-group" id="rsvpCommentsCtl">
							<label class="control-label" for="rsvpComments">Comments</label>

							<div class="controls">

								<form:textarea path="rsvpComments" cssClass="input-xlarge"
									id="rsvpComments" placeholder="You may enter upto 250 characters" />
							</div>
						</div>
						<input type="hidden" name="rsvpMessage" value="${rsvpMessage}" id = "rsvpMessage"/>
						<form:hidden path="groupEventInvite.groupEventInviteId"/>
						<c:choose>
						<c:when test="${!disableButton}">
						<button class="btn btn-primary btn-large has-spinner" type="submit">SUBMIT</button>
						</c:when>
						<c:otherwise>
						<input class="btn btn-primary btn-large has-spinner" type="submit" disabled="disabled"
							value="SUBMIT" />
							
						</c:otherwise>
						</c:choose>
					


					</div>
				</fieldset>
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
                    required:true,
                    rsvpCheck: 1
                },
                rsvpComments:{
                    maxlength: 250
                },
                adultCount: {
                	number: true,
                	required:true
                },
                kidsCount :{
                	number: true,
                	required:true
                },
                transactionReference : {
                	maxlength:100
                }
                
                
            },
            errorClass:"control-group error",
            validClass:"control-group success",
            errorElement:"span",
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
        	  if($(element).prop( "checked" )) {return params<=($("#adultCount").val()+$("#kidsCount").val());}
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
