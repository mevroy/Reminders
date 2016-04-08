<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="row">
<div class="span12">
<div class="hero-unit">
    <form:form commandName="registerUserForm" id="registerUserForm" action="registerUser" method="post" >
        <fieldset>

           
           <h2>Sign Up as a user!</h2>
           <br/>
            

            <div class="span5">
                <div class="control-group" id="usernameCtl">
                    <label class="control-label" for="username">Username</label>

                    <div class="controls">
                        
                        <form:input path="username" id="username" cssClass="input-xlarge"/>
                        
                    </div>
                </div>

                <div class="control-group" id="passwordCtl">
                    <label class="control-label" for="password">Password</label>

                    <div class="controls">
                       <form:password path="password" cssClass="input-xlarge" id="password"/>
                    </div>
                </div>

                <div class="control-group" id="confirmCtl">
                    <label class="control-label" for="confirm">Confirm Password</label>

                    <div class="controls">
                       <form:password path="confirm" cssClass="input-xlarge" id="confirm"/>
                    </div>
                </div>

                <div class="control-group" id="emailCtl">
                    <label class="control-label" for="email">Email</label>

                    <div class="controls">
                      <form:input path="email" cssClass="input-xlarge" id="email"/>
                    </div>
                </div>

                <div class="control-group" id="mobileCtl">
                    <label class="control-label" for="mobile">Mobile</label>

                    <div class="controls">
                        <form:input path="mobile" cssClass="input-xlarge" id="mobile"/>
                    </div>
                </div>

            </div>
            <div class="span5">
                <div class="control-group" id="companyNameCtl">
                    <label class="control-label" for="companyName">Company</label>

                    <div class="controls">
                  <form:input path="companyName" cssClass="input-xlarge" id="companyName"/>
                    </div>
                </div>

                <div class="control-group" id="streetAddressCtl">
                    <label class="control-label" for="streetAddress">Street Address</label>

                    <div class="controls">
                   <form:input path="streetAddress" cssClass="input-xlarge" id="streetAddress"/>
                    </div>
                </div>

                <div class="control-group" id="cityCtl">
                    <label class="control-label" for="city">City</label>

                    <div class="controls">
                       <form:input path="city" cssClass="input-xlarge" id="city"/>
                    </div>
                </div>

                <div class="control-group" id="stateCtl">
                    <label class="control-label" for="state">State</label>

                    <div class="controls">
                     <form:input path="state" cssClass="input-xlarge" id="state"/>
                    </div>
                </div>

                <div class="control-group" id="countryCtl">
                    <label class="control-label" for="country">Country</label>

                    <div class="controls">
                        <form:input path="country" cssClass="input-xlarge" id="country"/>
                    </div>
                </div>

                <div class="control-group" id="postalCodeCtl">
                    <label class="control-label" for="postalCode">Postal Code</label>

                    <div class="controls">
                       <form:input path="postalCode" cssClass="input-xlarge" id="postalCode"/>
                    </div>
                </div>

                <input class="btn btn-primary btn-large" type="submit" value="Register"/>
                <a href="${pageContext.request.contextPath}/" class="btn btn-large">Cancel</a>

            </div>
        </fieldset>
    </form:form>
    </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        $("#registerUserForm").validate({
            rules:{
                username:{
                    required:true,
                    minlength:6,
                    maxlength:15
                },
                password:{
                    required:true,
                    minlength:6
                },
                confirm:{
                    equalTo:'#password'
                },
                email:{
                    required:true,
                    email:true,
                    maxlength:35
                },
                mobile:{
                    required:true,
                    digits:true,
                    maxlength:10
                },
                streetAddress:{
                    required:true,
                    maxlength:50
                },
                city:{
                    required:true
                },
                postalCode:{
                    required:true,
                    digits:true
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
    });
</script>
