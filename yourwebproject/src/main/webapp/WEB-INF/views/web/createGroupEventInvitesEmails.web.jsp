<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="row">
	<div class="span12">
		<!--  div class="hero-unit"> -->

		<fieldset>
<form id="groupEventInvite" name="groupEventInvite">

			<h2>Create Group Event Invite Emails</h2>
			<br />


			<div class="span3">
				<div class="control-group" id="memberCategoryCodeCtl">
					<label class="control-label" for="memberCategoryCode">Member
						Category Code</label>

					<div class="controls">

						<select name="memberCategoryCode" class="input-large"
							id="memberCategoryCode"
							onchange="loadDiv(); buildGroupEventsOptionsByMemberCategory(this.value, 'groupEventCode'); buildGroupEmailTemplateOptionsByEventCode('NULL','templateName');">
							<option value="">Select One</option>
						</select>
					</div>
				</div>

			</div>
			<div class="span3">
				<div class="control-group" id="groupEventCodeCtl">
					<label class="control-label" for="groupEventCode">Group
						Event Code</label>

					<div class="controls">

						<select name="groupEventCode" class="input-large"
							id="groupEventCode"
							onchange="loadDiv(); buildGroupEmailTemplateOptionsByEventCode(this.value,'templateName'); loadGrid('grid',$('select#memberCategoryCode option').filter(':selected').val(),this.value,'Invite List');">
							<option value="">Select One</option>
						</select>
					</div>
				</div>

			</div>

			<div class="span3">
				<div class="control-group" id="templateNameCtl">
					<label class="control-label" for="templateName">Email
						Template Name</label>

					<div class="controls">


						<select id="templateName" name="templateName" class="input-large">
							<option value="">Select One</option>
						</select>
					</div>
				</div>
			</div>

</form>
			</fieldset>	

			
				<button class="btn btn-primary btn-small has-spinner"  type="submit" value="SUBMIT ALL" id="sendAll">SUBMIT ALL</button> 
				<button class="btn btn-primary btn-small has-spinner" type="submit" value="SUBMIT SELECTED" id="sendSelected">SUBMIT SELECTED</button>


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
    	
        decodeErrorMessage = function(jqXHR, textStatus, errorThrown) {
            var html, errorInfo, i, errorText = textStatus + '\n<br />' + errorThrown;
            if (jqXHR.responseText.charAt(0) === '[') {
                try {
                    errorInfo = $.parseJSON(jqXHR.responseText);
                    errorText = "";
                    for (i=0; i<errorInfo.length; i++) {
                       if (errorText.length !== 0) {
                           errorText += "<hr/>";
                       }
                       errorText += errorInfo[i].Source + ": " + errorInfo[i].Message;
                    }
                }
                catch (e) { }
            } else {
                html = /<body.*?>([\s\S]*)<\/body>/i.exec(jqXHR.responseText);
                if (html !== null && html.length > 1) {
                    errorText = html[1];
                }
            }
            return errorText;
        };
        
        
        sendData = function(data,templateName,groupEventCode,memberCategoryCode,selector) {
            var dataToSend = JSON.stringify(data);
            toggleButtonWithId(selector);
            $.ajax({
                type: "POST",
                url: "json/saveGroupEventInviteEmails?templateName="+templateName+"&groupEventCode="+groupEventCode+"&memberCategoryCode="+memberCategoryCode,
                //dataType:"json",
                data: dataToSend,
                contentType: "application/json; charset=utf-8",
                success: function(response, textStatus, jqXHR) {
                    // remove error div if exist
                    loadMessageModal("Message", response);
                  	loadDiv();
                    loadGrid('grid',memberCategoryCode,groupEventCode,'Updated Invite List');
                    resetButtonWithId(selector);
                },
                error: function(jqXHR, textStatus, errorThrown) {
                	loadMessageModal("Error", jqXHR.responseText+textStatus);
                	resetButtonWithId(selector);
                }
            });
        };


    	
    	
    	
    	
    	
    	
    	
    	
    	
        $("#groupEventInvite").validate({
            rules:{
                groupEventCode:{
                    required:true
                },
                templateName: {
                	required: true
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
							url : 'json/viewGroupEventInvites/' + groupEventCode+'/'+groupMemCatCode,
							datatype : "json",
							colModel : [
// 									{
// 										label : 'Action',
// 										name : 'myac',
// 										width : 80,
// 										fixed : true,
// 										sortable : false,
// 										resize : false,
// 										formatter : 'actions',
// 										formatoptions : {
// 											keys : true
// 										}
// 									},
									{
										label : 'Event Serial No',
										name : 'groupEventInviteId',
										index : 'groupEventInviteId',
										hidden : true,
										editable : false
									},
									{
										label : 'First Name',
										name : 'groupMember.firstName',
										index : 'groupMember.firstName',
										width : 120,
										hidden : false,
										editable : false
									},
									{
										label : 'Last Name',
										name : 'groupMember.lastName',
										index : 'groupMember.lastName',
										width : 100,
										hidden : false,
										editable : false
									},
									{
										label : 'Email Verified',
										name : 'groupMember.primaryEmailVerified',
										index : 'groupMember.primaryEmailVerified',
										hidden : false,
										editable : true,
										width : 100,
										formatter: formatBoolean,
										edittype : "checkbox",
										editoptions : {
											value : "Yes:No"}
									},
									{
										label : 'Invite Held',
										name : 'inviteHeld',
										index : 'inviteHeld',
										hidden : false,
										editable : true,
										width : 80,
										formatter: formatBoolean,
										edittype : "checkbox",
										editoptions : {
											value : "Yes:No"}
									},
									{
										label : 'Invite Emailed',
										name : 'inviteSent',
										index : 'inviteSent',
										hidden : false,
										editable : false,
										width : 100,
										formatter: formatBoolean,
										edittype : "checkbox",
										editoptions : {
											value : "Yes:No"}
									},
									{
										label : 'RSVPd',
										name : 'rsvpd',
										index : 'rsvpd',
										hidden : false,
										editable : false,
										width : 60,
										formatter: formatBoolean,
										edittype : "checkbox",
										editoptions : {
											value : "Yes:No"}
									},
									{
										label : 'Invite Delivered',
										name : 'inviteDelivered',
										index : 'inviteDelivered',
										hidden : false,
										editable : false,
										width : 100,
										formatter: formatBoolean,
										edittype : "checkbox",
										editoptions : {
											value : "Yes:No"}
									},
									{
										label : 'Email Count',
										name : 'inviteEmailCount',
										index : 'inviteEmailCount',
										width : 80,
										hidden : false,
										editable : false
									},
									{
										label : 'Invite Cancelled',
										name : 'inviteCancelled',
										index : 'inviteCancelled',
										hidden : false,
										editable : true,
										width : 100,
										formatter: formatBoolean,
										edittype : "checkbox",
										editoptions : {
											value : "Yes:No"}
									},
									{
										label : 'Invite Expiry Date',
										name : 'inviteExpiryDate',
										index : 'inviteExpiryDate',
										width : 130,
										sortable : true,
										editable : true,
										sorttype : "date",
										searchoptions:{sopt:['eq','bw','bn','cn','nc','ew','en']},
										formatter : formatDate
									//	,
									//			formatoptions: { srcformat: "m/d/Y", newformat: "d/m/Y" }

									},
									{
										label : 'Mark Attended',
 										name : 'markAttended',
 										index : 'markAttended',
 										hidden : false,
 										editable : false,
 										formatter: formatBoolean,
										edittype : "checkbox",
										editoptions : {
											value : "true:false"}, 										
										width: 100
 									},									
									{
										label : 'Group Label',
										name : 'memberCategoryCode',
										index : 'memberCategoryCode',
										hidden : true,
										editable : false,
										sortable : true,
										sorttype : 'string'
									},
									{
										label : 'Serial No',
										name : 'groupMember.serialNumber',
										index : 'groupMember.serialNumber',
										hidden : true,
										editable : false
									} ],
							height : 'auto',
							rowNum : 500,
							rownumbers: true,
							rownumWidth : 25,
							width : 937,
							rowList : [ 250, 500, 1000, 2000 ],
							pager : '#pgrid',
							multiselect: true,
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

        $("#sendAll").click(function(){
            var localGridData = jQuery("#grid").jqGrid('getGridParam','data');
           
           var selectedEmailTemplate = $('select#templateName option').filter(':selected').val();
           var selectedMemberCategoryCode = $('select#memberCategoryCode option').filter(':selected').val();
           var selectedgroupEventCode = $('select#groupEventCode option').filter(':selected').val();
           if(selectedgroupEventCode === '')
           {
        	   confirm("Please select a Event from the drop down");
        	   return false;  
           }
           if(selectedEmailTemplate==='')
           {
        	   confirm("Please select a template from the drop down");
        	   return false;
           }
           
            sendData(localGridData,selectedEmailTemplate,selectedgroupEventCode,selectedMemberCategoryCode,'sendAll');
         
        });
        $("#sendSelected").click(function(){
            var selectedEmailTemplate = $('select#templateName option').filter(':selected').val();
            var selectedMemberCategoryCode = $('select#memberCategoryCode option').filter(':selected').val();
            var selectedgroupEventCode = $('select#groupEventCode option').filter(':selected').val();
            if(selectedEmailTemplate==='')
            {
         	   confirm("Please select a template from the drop down!");
         	   return false;
            }
            var localGridData = jQuery("#grid").jqGrid('getGridParam','data'),
                idsToDataIndex = jQuery("#grid").jqGrid('getGridParam','_index'),
                selRows = jQuery("#grid").jqGrid('getGridParam','selarrrow'),
                dataToSend = [], i, l=selRows.length;
            for (i=0; i<l; i++) {
            	var x = localGridData[idsToDataIndex[selRows[i]]];
            //	delete x['_id_'];
                dataToSend.push(x);
            }
            if(dataToSend.length===0){
          	   confirm("Please select atleast one checkbox to proceed!");
         	   return false;
            }
            sendData(dataToSend,selectedEmailTemplate,selectedgroupEventCode,selectedMemberCategoryCode,'sendSelected');

        });
        
		$('.input-group.date').datepicker({
			format : "dd/mm/yyyy"
		});
		$('.input-daterange').datepicker({
			format : "dd/mm/yyyy"
		});
		
		buildGroupMemberCategoriesOptions('memberCategoryCode');
		buildGroupEventsOptionsByMemberCategory("NULL","groupEventCode");
		buildGroupEmailTemplateOptionsByEventCode("NULL",'templateName');
	});
	

</script>
