<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="row">
	<div class="span12">
		<div class="hero-unit">
			<form:form commandName="groupMemberCategory"
				action="saveGroupMemberCategory" method="post"
				id="groupMemberCategory">
				<fieldset>


					<h2>Add Group Member Category</h2>
					<br />



					<div class="span5">
						<div class="control-group" id="memberCategoryCodeCtl">
							<label class="control-label" for="memberCategoryCode">Member
								Category Code</label>

							<div class="controls">
								<form:input path="memberCategoryCode" cssClass="input-xlarge"
									id="memberCategoryCode" placeholder="Enter 7 characters Member Category Code" />
							</div>
						</div>

						<div class="control-group" id="memberCategoryNameCtl">
							<label class="control-label" for="memberCategoryName">Member
								Category Name</label>

							<div class="controls">

								<form:input path="memberCategoryName" cssClass="input-xlarge"
									id="memberCategoryName" placeholder="Enter Category Name"/>
							</div>
						</div>

						<div class="control-group" id="memberCategoryDescriptionCtl">
							<label class="control-label" for="memberCategoryDescription">Member
								Category Description</label>

							<div class="controls">

								<form:input path="memberCategoryDescription"
									cssClass="input-xlarge" id="memberCategoryDescription" placeholder="Enter Category Description" />
							</div>
						</div>

						<div class="control-group" id="memberCategoryDatesCtl">
							<label class="control-label" for="groupDates">Member
								Category Dates</label>
							<div class="controls">
								<div class="input-daterange input-group" id="datepicker">

									<form:input path="startDate"
										cssClass="form-control input-small input-append"
										id="startDate" placeholder="Start Date" />&nbsp;<span class="input-prepend add-on"><i class="icon-calendar"></i></span> <span><i>&nbsp;&nbsp;&nbsp;TO</i></span>
									<form:input path="expiryDate"
										cssClass="form-control input-small input-append"
										id="expiryDate" placeholder="End Date" />&nbsp;<span class="input-prepend add-on"><i class="icon-calendar"></i></span>
								</div>
							</div>
						</div>

						<div class="control-group" id="groupCodeCtl">
							<label class="control-label" for="groupCode">Group Code</label>

							<div class="controls">

								<form:select path="groupCode" cssClass="input-xlarge"
									id="groupCode">
									<option value="">Select One</option>
								</form:select>
							</div>
						</div>

						<input class="btn btn-primary btn-large" type="submit"
							value="SUBMIT" /> <a href="${pageContext.request.contextPath}/${sessionScope.groupCode}/" class="btn btn-large">CANCEL</a>

					</div>
				</fieldset>
			</form:form>
		</div>
	</div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        $("#groupMemberCategory").validate({
            rules:{
            	memberCategoryCode:{
                    required:true,
                    minlength: 7,
					maxlength: 7
                },
                memberCategoryName:{
                    required:true
                },
                startDate:{
                	//dateCompareToday: ['ge', 'today'],
                	dateITA:true
                },
                expiryDate:{
                //	dateCompareToday: ['ge', 'today'],
                //	dateCompareGreater: ['ge', '#startDate'],
                	dateITA:true
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
    
	$(function() {
		$('.input-group.date').datepicker({
			format : "dd/mm/yyyy"
		});
		$('.input-daterange').datepicker({
			format : "dd/mm/yyyy"
		});

	});
	
	$(function(){
	    $.getJSON("json/viewGroups", function(j){
	   
	      var options = '<option value="">Select One</option>"';
	     
	      for (var i = 0; i < j.length; i++) {
	        options += '<option value="' + j[i].groupCode + '">' + j[i].longName + '</option>';
	      }
	     
	      $("select#groupCode").html(options);
	    })
	  });
</script>
