<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="row">
	<div class="span12">
		<!--  div class="hero-unit"> -->
		<form:form commandName="groupEventInvite"
			action="saveGroupEventInvite" method="post" id="groupEventInvite">
			<fieldset>


				<h2>Group Event Feedback</h2>
				<br />



				<div class="span5">
					<div class="control-group" id="memberCategoryCodeCtl">
						<label class="control-label" for="memberCategoryCode">Member
							Category Code</label>

						<div class="controls">

							<form:select path="memberCategoryCode" cssClass="input-xlarge"
								id="memberCategoryCode"
								onchange="loadDiv();buildGroupEventsOptionsByMemberCategoryIncludingExpired(this.value, 'groupEventCode');">
								<option>Select One</option>
							</form:select>
						</div>
					</div>

				</div>
				<div class="span5">

					<div class="control-group" id="groupEventCodeCtl">
						<label class="control-label" for="groupEventCode">Group
							Event Code</label>

						<div class="controls">

							<form:select path="groupEventCode" cssClass="input-xlarge"
								id="groupEventCode"
								onchange="loadDiv();loadGrid('grid',$('select#memberCategoryCode option').filter(':selected').val(),this.value,'Feedback');">
								<option>Select One</option>
							</form:select>
						</div>
					</div>

				</div>
			</fieldset>
		</form:form>
		<div class="gridDiv">
			<table id="grid"></table>
			<div id="pgrid"></div>
		</div>
		<!-- /div>  -->
	</div>
</div>

<script type="text/javascript">
var gridDiv = "";
    $(document).ready(function () {
        $("#groupEventInvite").validate({
            rules:{
            	memberCategoryCode:{
                    required:true,
                },
                groupEventCode:{
                    required:true
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
         gridDiv = $("div.gridDiv").html();

    });
    
	function loadDiv()
	{

		$("div.gridDiv").html(gridDiv);
	}
	function loadGrid(gridId, groupMemCatCode, groupEventCode, captionVal) {
	//	alert("gridId-->"+gridId+" groupMemCatCode-->"+groupMemCatCode+" groupEventCode-->"+groupEventCode+" captionVal-->"+captionVal)
		jQuery("#" + gridId)
				.jqGrid(
						{
							url : 'json/viewGroupEventFeedbackList/' + groupEventCode+'/'+groupMemCatCode,
							datatype : "json",
							colModel : [
									{
										label : 'Name',
										name : 'name',
										index : 'name',
										hidden : false,
										editable : false,
										width: 100
									},
									{
										label : 'Compliments',
										name : 'compliments',
										index : 'compliments',
										hidden : false,
										editable : false,
										width: 300
									},
									{
										label : 'Improvement Areas',
 										name : 'improvementAreas',
 										index : 'improvementAreas',
 										hidden : false,
 										editable : false,
										width: 300
 									},
									{
										label : 'Comments',
										name : 'comments',
										index : 'comments',
										hidden : false,
										editable : false,
										width: 300
 									},

									{
										label : 'Group Label',
										name : 'memberCategoryCode',
										index : 'memberCategoryCode',
										hidden : true,
										editable : false,
										sortable : true,
										sorttype : 'string'
									} ],
							height : 'auto',
							rowNum : 500,
							rownumbers: true,
							rownumWidth : 25,
							width : 937,
							rowList : [ 250, 500, 1000, 2000 ],
							pager : '#pgrid',
						//	sortname : 'firstName',
							autoencode : true,
							shrinkToFit : false,
							viewrecords : true,
							//grouping : true,
							loadonce : true,
							groupingView : {
								groupField : [ 'memberCategoryCode' ],
								groupColumnShow : [ false ],
								groupText : [ '<b>{0} - {1} Members(s)</b>' ]
							},
							onSelectRow : function(id) {
							},
							editurl : "server.php",
							caption : captionVal
						});
		jQuery("#" + gridId).jqGrid('navGrid', "#pgrid", {
			edit : false,
			add : false,
			del : false
		});
		jQuery("#" + gridId).jqGrid('filterToolbar',{searchOperators : true});
	}

	$(function() {
		$('.input-group.date').datepicker({
			format : "dd/mm/yyyy"
		});
		$('.input-daterange').datepicker({
			format : "dd/mm/yyyy"
		});
		
		buildGroupMemberCategoriesOptions('memberCategoryCode');
		buildGroupEventsOptionsByMemberCategoryIncludingExpired("NULL","groupEventCode");
	});
	

</script>
