<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="row">
	<div class="span12">
		<!--  div class="hero-unit"> -->
		<form:form commandName="groupEventInvite"
			action="saveGroupEventInvite" method="post" id="groupEventInvite">
			<fieldset>


				<h2>View And Update Payment Transactions</h2>
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
								onchange="loadDiv();loadGrid('grid',$('select#memberCategoryCode option').filter(':selected').val(),this.value,'Transactions List');">
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
							url : 'json/viewGroupEventPaymentTransactions/' + groupEventCode+'/'+groupMemCatCode,
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
										label : 'Transaction ID',
										name : 'transactionId',
										index : 'transactionId',
										hidden : true,
										editable : true,
										width: 130
									},
									{
										label : '#Tickets',
										name : 'totalNumberOfProducts',
										index : 'totalNumberOfProducts',
										hidden : false,
										editable : false,
										align: 'center',
										width : 70,
										summaryType : 'sum',
										summaryTpl : '<b>Total: {0}</b>'
									},	
									{
										label : 'Amt. Paid',
										name : 'transactionAmount',
										index : 'transactionAmount',
										hidden : false,
										editable : false,
										align: 'center',
										width: 80,
										formatter: 'double',
										summaryType : 'sum',
										summaryTpl : '<b>Total: {0}</b>'
									},
									{
										label : 'First Name',
										name : 'groupEventInvite.groupMember.firstName',
										index : 'groupEventInvite.groupMember.firstName',
										hidden : false,
										editable : false,
										width: 100
									},
									{
										label : 'Last Name',
										name : 'groupEventInvite.groupMember.lastName',
										index : 'groupEventInvite.groupMember.lastName',
										hidden : false,
										editable : false,
										width: 100
									},
									{
										label : 'Approved',
 										name : 'transactionApproved',
 										index : 'transactionApproved',
 										hidden : true,
 										editable : true,
 										align: 'center',
 										formatter: formatBoolean,
										edittype : "checkbox",
										editoptions : {
											value : "Yes:No"}, 										
										width: 60
 									},

								
									{
										label : 'Status',
										name : 'paymentStatus',
										index : 'paymentStatus',
										hidden : false,
										editable : true,
										align: 'center',
										width : 120,
										edittype:"select",
										editoptions:   
										   { value:"AWAITINGPMT:AWAITINGPMT;PROCESSINGPMT:PROCESSINGPMT;PMTRECEIVED:PMTRECEIVED;EXPIRED:EXPIRED;PMTREJECTED:PMTREJECTED;PROCESSED:PROCESSED;CANCELLED:CANCELLED;APPROVED:APPROVED", style: 'width: 120px'},
										   editrules:{required:true}
										   
									},
 									{
 										label : 'Ref Number.',
 										name : 'userReferenceNumber',
 										index : 'userReferenceNumber',
 										hidden : false,
 										editable : false,
 										width: 80
 									},
 										{
 											label : 'Transaction Dt.',
 											name : 'transactionDateTime',
 											index : 'transactionDateTime',
 											width : 100,
 											sortable : true,
 											editable : false,
 											formatter : formatDateTime,
 											editoptions : {
 												dataInit : datePick
 											}
 										},
 										{
 											label : 'Expiry Dt.',
 											name : 'transactionExpiryDateTime',
 											index : 'transactionExpiryDateTime',
 											width : 110,
 											sortable : true,
 											editable : true,
 											formatter : formatDateTime,
 											editoptions : {
 												dataInit : datetimePick,
 												 style: 'width: 100px'
 											}
 										},
 										{
 											label : 'Correspondence Dt.',
 											name : 'correspondenceDateTime',
 											index : 'correspondenceDateTime',
 											width : 100,
 											sortable : true,
 											editable : false,
 											formatter : formatDateTime,
 											editoptions : {
 												dataInit : datePick
 											}
 										},
									{
										label : 'Category',
										name : 'groupEventInvite.memberCategoryCode',
										index : 'groupEventInvite.memberCategoryCode',
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
								groupField : [ 'paymentStatus' ],
								groupColumnShow : [ true ],
								groupSummary : [true],
								groupText : [ '<b>{0} - {1} Transaction(s)</b>' ],
								groupCollapse : true
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
							editurl : "updateGroupEventPaymentTransaction",
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
