<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="row">
	<div class="span12">
		<!--  div class="hero-unit"> -->
		<form:form commandName="groupEventInvite"
			action="saveGroupEventInvite" method="post" id="groupEventInvite">
			<fieldset>


				<h2>View Group Event Invite List</h2>
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
								onchange="loadDiv();loadGrid('grid',$('select#memberCategoryCode option').filter(':selected').val(),this.value,'Invite List'); loadGridRSVP('gridRSVP',$('select#memberCategoryCode option').filter(':selected').val(),this.value,'RSVP Status');">
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
			<br/>
			<table id="gridRSVP"></table>
			<div id="pgridRSVP"></div>
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
										label : 'Serial No',
										name : 'serialNumber',
										index : 'serialNumber',
										hidden : true,
										editable : false,
										width: 130
									},
									{
										label : 'First Name',
										name : 'groupMember.firstName',
										index : 'groupMember.firstName',
										hidden : false,
										editable : false,
										width: 150
									},
									{
										label : 'Last Name',
										name : 'groupMember.lastName',
										index : 'groupMember.lastName',
										hidden : false,
										editable : false,
										width: 130,
										summaryType:'count',
										summaryTpl : 'Total - '
									},
									{
										label : 'Adults',
										name : 'groupMember.adultCount',
										index : 'groupMember.adultCount',
										width : 40,
										sortable : false,
										align: 'center',
										editable : true,
										sorttype : "int",
										formatter : "integer",
										editrules : {
											integer : true
										},
										summaryType : 'sum',
										summaryTpl : '<b>{0}</b>'
									},
									{
										label : 'Kids',
										name : 'groupMember.kidCount',
										index : 'groupMember.kidCount',
										align: 'center',
										width : 40,
										sortable : false,
										editable : true,
										sorttype : "int",
										editrules : {
											integer : true
										},
										formatter : "integer",
										summaryType : 'sum',
										summaryTpl : '<b>{0}</b>'
									},
									{
										label : 'Membership End Date',
										name : 'groupMember.membershipEndDate',
										index : 'groupMember.membershipEndDate',
										width : 100,
										sortable : true,
										editable : true,
									//	sorttype : "date",
										formatter : formatDate
									//	,
									//			formatoptions: { srcformat: "m/d/Y", newformat: "d/m/Y" }

									},
									
									{
										label : 'Invite Code',
										name : 'groupEventInviteCode',
										index : 'groupEventInviteCode',
										hidden : false,
										align: 'center',
										editable : false,
										width: 60
									},
 									{
 										label : 'Transaction Ref.',
 										name : 'transactionReference',
 										index : 'transactionReference',
 										hidden : false,
 										align: 'center',
 										editable : false,
 										width: 100
 									},
 									{
 										label : 'Trans Appd.',
 											name : 'transactionApproved',
 											index : 'transactionApproved',
 											hidden : false,
 											align: 'center',
 											editable : false,
 											formatter: formatBoolean,
 										edittype : "checkbox",
 										editoptions : {
 											value : "Yes:No"}, 										
 										width: 70
 										},
 										{
 											label : 'Transaction Dt.',
 											name : 'transactionDateTime',
 											index : 'transactionDateTime',
 											width : 100,
 											sortable : true,
 											editable : false,
 											formatter : formatDate
 										},
								
									{
										label : 'Invite Emailed',
										name : 'inviteSent',
										index : 'inviteSent',
										hidden : false,
										align: 'center',
										editable : false,
										width : 80,
										formatter: formatBoolean,
										edittype : "checkbox",
										editoptions : {
											value : "Yes:No"}
									},
// 									{
// 										label : 'Invite Start Date',
// 										name : 'inviteStartDate',
// 										index : 'inviteStartDate',
// 										width : 100,
// 										sortable : true,
// 										editable : true,
// 										searchoptions:{sopt:['eq','bw','bn','cn','nc','ew','en']},
// 										sorttype : "date",
// 										formatter : function(cellValue, opts,
// 												rwd) {
// 											if (cellValue) {

// 												var x = $.fn.fmatter.call(this,
// 														"date", new Date(
// 																cellValue),
// 														opts, rwd);
// 												var dfg = new Date(x);
// 												return dfg.getDate() + "/"
// 														+ (dfg.getMonth() + 1)
// 														+ "/"
// 														+ dfg.getFullYear();
// 											} else {
// 												return '';
// 											}
// 										}
// 										,
// 												formatoptions: { srcformat: "m/d/Y", newformat: "d/m/Y" }

// 									},
// 									{
// 										label : 'Invite Expiry Date',
// 										name : 'inviteExpiryDate',
// 										index : 'inviteExpiryDate',
// 										width : 100,
// 										sortable : true,
// 										editable : true,
// 										sorttype : "date",
// 										searchoptions:{sopt:['eq','bw','bn','cn','nc','ew','en']},
// 										formatter : function(cellValue, opts,
// 												rwd) {
// 											if (cellValue) {

// 												var x = $.fn.fmatter.call(this,
// 														"date", new Date(
// 																cellValue),
// 														opts, rwd);
// 												var dfg = new Date(x);
// 												return dfg.getDate() + "/"
// 														+ (dfg.getMonth() + 1)
// 														+ "/"
// 														+ dfg.getFullYear();
// 											} else {
// 												return '';
// 											}
// 										}
// 									//	,
// 									//			formatoptions: { srcformat: "m/d/Y", newformat: "d/m/Y" }

// 									},
									{
										label : 'Invite Delivered',
										name : 'inviteDelivered',
										index : 'inviteDelivered',
										hidden : false,
										align: 'center',
										editable : false,
										formatter: formatBoolean,
										width: 100
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
										label : 'Invite Held',
										name : 'inviteHeld',
										align: 'center',
										index : 'inviteHeld',
										hidden : false,
										editable : true,
										formatter: formatBoolean,
										width: 100,
										edittype : "checkbox",
										editoptions : {
											value : "true:false"}
									},
									{
										label : 'RSVPd',
 										name : 'rsvpd',
 										index : 'rsvpd',
 										align: 'center',
 										hidden : false,
 										editable : false,
 										formatter: formatBoolean,
										width: 100
 									},
									{
										label : 'Mark Attended',
 										name : 'markAttended',
 										index : 'markAttended',
 										hidden : false,
 										editable : false,
 										align: 'center',
 										formatter: formatBoolean,
										edittype : "checkbox",
										editoptions : {
											value : "Yes:No"}, 										
										width: 100
 									},	

// 									{
// 										label : 'Resend Invite',
// 										name : 'resendInvite',
// 										index : 'resendInvite',
// 										hidden : false,
// 										editable : true,
// 										edittype : "checkbox",
// 										editoptions : {
// 											value : "true:false"}
// 									},

									{
										label : 'Group Label',
										name : 'memberCategoryCode',
										index : 'memberCategoryCode',
										hidden : false,
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
							grouping : true,
							loadonce : true,
							groupingView : {
								groupField : [ 'memberCategoryCode' ],
								groupSummary : [true],
								groupColumnShow : [ false ],
								groupText : [ '<b>{0} - {1} Members(s)</b>' ]
							},
							onSelectRow : function(id) {
								if (id && id !== lastsel) {
									jQuery('#' + gridId).jqGrid('restoreRow',
											lastsel);
									jQuery('#' + gridId).jqGrid('editRow', id,
											true, pickdates);
									lastsel = id;
								}
							},
							editurl : "server.php",
							caption : captionVal,
							 footerrow: true, // the footer will be used for Grand Total
				                userDataOnFooter: true,
							loadComplete: function () {

							    var sumOfKids = jQuery("#"+gridId).jqGrid('getCol', 'groupMember.kidCount', false, 'sum');
							    var sumOfAdults = jQuery("#"+gridId).jqGrid('getCol', 'groupMember.adultCount', false, 'sum');

							    jQuery("#"+gridId).jqGrid('footerData', 'set', 
							    { 
							    	'groupMember.firstName': 'Total Expected:',
							    	'groupMember.kidCount': sumOfKids, 
							    	'groupMember.adultCount': sumOfAdults
							    });
							}
						});
		jQuery("#" + gridId).jqGrid('navGrid', "#pgrid", {
			edit : false,
			add : false,
			del : false
		});
		jQuery("#" + gridId).jqGrid('filterToolbar',{searchOperators : true});
	}

	
	
	
	
	
	function loadGridRSVP(gridId, groupMemCatCode, groupEventCode, captionVal) {
		//	alert("gridId-->"+gridId+" groupMemCatCode-->"+groupMemCatCode+" groupEventCode-->"+groupEventCode+" captionVal-->"+captionVal)
			jQuery("#" + gridId)
					.jqGrid(
							{
								url : 'json/viewLatestGroupEventInvitesRSVPs/' + groupEventCode+'/'+groupMemCatCode,
								datatype : "json",
								colModel : [
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
											width: 100,
											summaryType:'count',
											summaryTpl : 'Total - '
										},
										{
											label : 'Adult Count',
											name : 'adultCount',
											index : 'adultCount',
											align: 'center',
											width: 75,
											hidden : false,
											editable : false,
											formatter: 'integer',
											summaryType : 'sum',
											summaryTpl : '<b>{0}</b>'
											
										},
										{
											label : 'Kids Count',
											name : 'kidsCount',
											index : 'kidsCount',
											width: 75,
											align: 'center',
											hidden : false,
											editable : false,
											formatter: 'integer',
											summaryType : 'sum',
											summaryTpl : '<b>{0}</b>'
										},
//	 									{
//	 										label : 'Invite Held',
//	 										name : 'inviteHeld',
//	 										index : 'inviteHeld',
//	 										hidden : false,
//	 										editable : true,
//	 										edittype : "checkbox",
//	 										editoptions : {
//	 											value : "true:false"}
//	 									},

										{
											label : 'Attended',
	 										name : 'groupEventInvite.markAttended',
	 										index : 'groupEventInvite.markAttended',
	 										hidden : false,
	 										editable : false,
	 										formatter: formatBoolean,
											edittype : "checkbox",
											editoptions : {
												value : "true:false"}, 										
											width: 75
	 									},
	 									{
	 										label : 'Transaction Ref.',
	 										name : 'transactionReference',
	 										index : 'transactionReference',
	 										hidden : false,
	 										editable : false,
	 										width: 100
	 									},
										{
											label : 'Amt. Paid',
											name : 'groupEventInvite.paidAmount',
											index : 'groupEventInvite.paidAmount',
											hidden : false,
											editable : true,
											width: 60
										}, 						
										{
											label : 'RSVP DateTime',
											name : 'rsvpDateTime',
											index : 'rsvpDateTime',
											width : 100,
											sortable : true,
											editable : true,
											formatter : formatDateTime
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
	 										label : 'Rsvp Comments',
	 										name : 'rsvpComments',
	 										index : 'rsvpComments',
	 										hidden : false,
	 										editable : false
	 									},										{
											label : 'RSVP Status',
	 										name : 'rsvpOutcome',
	 										index : 'rsvpOutcome',
	 										width: 75,
	 										hidden : true,
											//formatter: formatBoolean,
	 										editable : false
	 									} ],
								height : 'auto',
								rowNum : 500,
								rownumbers: true,
								rownumWidth : 25,
								width : 937,
								rowList : [ 250, 500, 1000, 2000 ],
								pager : '#pgridRSVP',
							//	sortname : 'firstName',
								autoencode : true,
								shrinkToFit : false,
								viewrecords : true,
								grouping : true,
								loadonce : true,
								groupingView : {
									groupField : [ 'rsvpOutcome' ],
									groupColumnShow : [ false ],
							 		groupSummary : [true],
									groupText : [ '<b>RSVP Status "{0}" - {1} Member(s)</b>' ],
									groupCollapse : true,
									showSummaryOnHide : true
								},
								onSelectRow : function(id) {

								},
								editurl : "server.php",
								caption : captionVal
							});
			jQuery("#" + gridId).jqGrid('navGrid', "#pgridRSVP", {
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
		buildGroupEventsOptionsByMemberCategory("NULL","groupEventCode");
	});
	

</script>
