<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="row">
	<div class="span12">
		<!--  div class="hero-unit"> -->
		<form:form commandName="groupEventInvite"
			action="saveGroupEventInvite" method="post" id="groupEventInvite">
			<fieldset>


				<h2>Update Invites/Attendance</h2>
				<br />



				<div class="span5">
					<div class="control-group" id="memberCategoryCodeCtl">
						<label class="control-label" for="memberCategoryCode">Member
							Category Code</label>

						<div class="controls">

							<form:select path="memberCategoryCode" cssClass="input-xlarge"
								id="memberCategoryCode"
								onchange="loadDiv();buildGroupEventsOptionsByMemberCategory(this.value, 'groupEventCode');">
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
								onchange="loadDiv();loadGrid('grid',$('select#memberCategoryCode option').filter(':selected').val(),this.value,'Invite List');">
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
var lastsel = "";
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
							url : 'json/viewGroupEventInvites/' + groupEventCode+'/'+groupMemCatCode,
							datatype : "json",
							colModel : [
									{
										label : 'Action',
										name : 'myac',
										width : 50,
										fixed : true,
										sortable : false,
										resize : false,
										formatter : 'actions',
										formatoptions : {
											keys : true
										}
									},
									{
										label : 'Event Invite ID',
										name : 'groupEventInviteId',
										index : 'groupEventInviteId',
										hidden : true,
										editable : true,
										width: 130
									},
									{
										label : 'Invite Code',
										name : 'groupEventInviteCode',
										index : 'groupEventInviteCode',
										hidden : false,
										editable : false,
										width: 80,
										align: 'center',
										formatter: function(cellValue, options, rowObject) {

										    var groupEventInviteCode = rowObject.groupEventInviteCode;
										    if(groupEventInviteCode){
										    	var txt = '<div id = "image" style="height: 150;"> <img src="https://chart.googleapis.com/chart?cht=qr&chs=150x150&chl='+groupEventInviteCode+'" width="120" height="120" border="0" alt="" style="height: auto;" /></div>';
										    	var html = " <button id='"+groupEventInviteCode+"' onfocus='$(this).popover();' onclick='$(this).popover();' type='button' data-trigger='focus hover click' data-html='true' class='btn btn-mini btn-info' data-toggle='popover' data-container='body' title='Code: "+groupEventInviteCode+"' data-content='"+txt+"'>"+groupEventInviteCode+"</button>";
										   return html;
										   }
										    else
										    return "";
										    }
									},
									{
										label : 'First Name',
										name : 'groupMember.firstName',
										index : 'groupMember.firstName',
										hidden : false,
										editable : false,
										width: 100
									},
									{
										label : 'Last Name',
										name : 'groupMember.lastName',
										index : 'groupMember.lastName',
										hidden : false,
										editable : false,
										width: 100
									},
									{
										label : 'Mark Attended',
 										name : 'markAttended',
 										index : 'markAttended',
 										hidden : false,
 										editable : true,
 										align: 'center',
 										formatter: formatBoolean,
										edittype : "checkbox",
										editoptions : {
											value : "Yes:No"}, 										
										width: 80
 									},
 									{
 										label : 'Transaction Ref.',
 										name : 'transactionReference',
 										index : 'transactionReference',
 										hidden : false,
 										editable : true,
 										width: 80
 									},
 									{
 										label : 'Transaction Approved',
 											name : 'transactionApproved',
 											index : 'transactionApproved',
 											hidden : false,
 											align: 'center',
 											editable : true,
 											formatter: formatBoolean,
 										edittype : "checkbox",
 										editoptions : {
 											value : "Yes:No"}, 										
 										width: 60
 										},
 										{
 											label : 'Transaction Dt.',
 											name : 'transactionDateTime',
 											index : 'transactionDateTime',
 											width : 80,
 											sortable : true,
 											editable : true,
 											formatter : formatDate,
 											editoptions : {
 												dataInit : datePick
 											}
 										},	
									{
										label : 'Amt. Paid',
										name : 'paidAmount',
										index : 'paidAmount',
										hidden : false,
										editable : true,
										align: 'center',
										width: 60,
										formatter: 'double',
										summaryType : 'sum',
										summaryTpl : '<b>Total: {0}</b>'
									},

								
									{
										label : 'Invite Emailed',
										name : 'inviteSent',
										index : 'inviteSent',
										hidden : false,
										editable : false,
										align: 'center',
										width : 80,
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
										align: 'center',
										editable : false,
										formatter: formatBoolean,
										width: 80
									},
// 									{
// 										label : 'Invite Cancelled',
// 										name : 'inviteCancelled',
// 										index : 'inviteCancelled',
// 										hidden : false,
// 										editable : true,
// 										edittype : "checkbox",
// 										editoptions : {
// 											value : "true:false"}
// 									},

									{
										label : 'RSVPd',
 										name : 'rsvpd',
 										index : 'rsvpd',
 										hidden : false,
 										align: 'center',
 										editable : false,
 										formatter: formatBoolean,
										width: 50
 									},
									{
										label : 'Invite Held',
										name : 'inviteHeld',
										index : 'inviteHeld',
										hidden : false,
										editable : true,
										align: 'center',
										formatter: formatBoolean,
										width: 80,
										edittype : "checkbox",
										editoptions : {
											value : "Yes:No"}
									},
									{
										label : 'Email Count',
										name : 'inviteEmailCount',
										index : 'inviteEmailCount',
										width : 80,
										align: 'center',
										hidden : false,
										editable : false
									},
									{
										label : 'Category',
										name : 'memberCategoryCode',
										index : 'memberCategoryCode',
										hidden : true,
										editable : false,
										width: 80,
										sortable : true,
										sorttype : 'string'
									},
									{
										label : 'GrpCode',
										name : 'groupCode',
										index : 'groupCode',
										width : 30,
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
						//	sortname : 'firstName',
							autoencode : true,
							shrinkToFit : false,
							viewrecords : true,
							grouping : true,
							loadonce : true,
							groupingView : {
								groupField : [ 'groupCode' ],
								groupColumnShow : [ false ],
								groupSummary : [true],
								groupText : [ '<b>{0} - {1} Members(s)</b>' ]
							},
							onSelectRow : function(id) {
								if (id && id !== lastsel) {
							//		jQuery('#' + gridId).jqGrid('restoreRow',
							//				lastsel);
							//		jQuery('#' + gridId).jqGrid('editRow', id,
							//				true, datePick);
							//		lastsel = id;
								}
							},
							editurl : "updateGroupEventInviteAttendance",
							caption : captionVal
						});
		jQuery("#" + gridId).jqGrid('navGrid', "#pgrid", {
			edit : false,
			add : true,
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
		buildGroupEventsOptionsByMemberCategory("NULL","groupEventCode");
	});
	

</script>
