<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="row">
	<div class="span12">
		<!--  div class="hero-unit"> -->
		<form:form commandName="groupEventInvite"
			action="saveGroupEventInvite" method="post" id="groupEventInvite">
			<fieldset>


				<h2>View Group Event Passes</h2>
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
								onchange="loadDiv();loadGrid('grid',$('select#memberCategoryCode option').filter(':selected').val(),this.value,'Invite Passes List');">
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
var lastsel = null;
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
							url : 'json/viewGroupEventPassesByGroupEventCode/' + groupEventCode,
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
											keys : true,delbutton:false
										}
									},
									{
										label : 'Serial No',
										name : 'id',
										index : 'id',
										hidden : true,
										editable : true,
										width: 100
									},
									{
										label : 'Barcode',
										name : 'passBarcode',
										index : 'passBarcode',
										align: 'center',
										hidden : false,
										editable : false,
										width: 75
									},
									{
										label : 'Start Date',
										name : 'passStartDate',
										index : 'passStartDate',
										hidden : false,
										editable : false,
										formatter : formatDateTime,
										editoptions : {
											dataInit : datetimePick
										},
										width: 95
									},	
									{
										label : 'Exp Date',
										name : 'passExpiryDate',
										index : 'passExpiryDate',
										hidden : false,
										editable : false,
										formatter : formatDateTime,
										editoptions : {
											dataInit : datetimePick
										},
										width: 95
									},	
									{
										label : 'Entry Cnt',
										name : 'noOfPeopleTagged',
										index : 'noOfPeopleTagged',
										hidden : false,
										align: 'center',
										editable : true,
										width: 60
									},
									{
										label : 'Price Tag',
										name : 'passPrice',
										index : 'passPrice',
										hidden : false,
										editable : true,
										align: 'center',
										width: 60,
										formatter : 'double',
										summaryType : 'sum',
										summaryTpl : '<b>Total: {0}</b>'
									},
									{
										label : 'Sold by',
										name : 'soldBy',
										index : 'soldBy',
										hidden : false,
										editable : true,
										width: 80
									},
									{
										label : 'Invalid',
										name : 'passInvalidated',
										index : 'passInvalidated',
										hidden : false,
										align: 'center',
										editable : true,
										edittype : "checkbox",
										formatter : formatBoolean,
										editoptions : {
											value : "Yes:No"},
										width: 60
									},
									{
										label : 'Sold',
										name : 'sold',
										index : 'sold',
										hidden : false,
										editable : true,
										align: 'center',
										edittype : "checkbox",
										formatter : formatBoolean,
										editoptions : {
											value : "Yes:No"},
										width: 50
									},
									{
										label : 'Table Number',
										name : 'tableNumber',
										index : 'tableNumber',
										hidden : false,
										editable : true,
										width: 80,
										edittype : 'select',
										editoptions : {
											dataUrl : '${pageContext.request.contextPath}/loadEmailTemplate/TableNumber',
											buildSelect : function(response) {
												var options = '<select>';
												var j = $.parseJSON(response);
												for (var i = 0; i < j.length; i++) {
													options += '<option value="' + j[i].TableName + '">'
															+ j[i].TableNumber
															+ '</option>';
												}
												options += '</select>';
												return options;
											},								        
											style: 'width: 80px'
										}
									},	
/* 									{
										label : 'Invite Code',
										name : 'groupEventInvite.groupEventInviteCode',
										index : 'groupEventInvite.groupEventInviteCode',
										hidden : false,
										editable : false,
										width: 60										
									}, */
									{
										label : 'Amt Approved',
										name : 'groupEventInvite.transactionApproved',
										index : 'groupEventInvite.transactionApproved',
										hidden : false,
										editable : false,
										align: 'center',
										width: 60,
										formatter: formatBoolean,
 										edittype : "checkbox",
 										editoptions : {
 											value : "Yes:No"}
									},
									{
										label : 'Scan Date',
										name : 'trackingDate',
										index : 'trackingDate',
										hidden : false,
										editable : false,
										formatter : formatDateTime,
										width: 90
									},											
									{
										label : 'Attndee FName',
										name : 'groupMember.firstName',
										index : 'groupMember.firstName',
										hidden : false,
										editable : false,
										width: 100
									},
									{
										label : 'Attndee LName',
										name : 'groupMember.lastName',
										index : 'groupMember.lastName',
										hidden : false,
										editable : false,
										width: 100
									},
									{
										label : 'Sold To FName',
										name : 'groupEventInvite.groupMember.firstName',
										index : 'groupEventInvite.groupMember.firstName',
										hidden : false,
										editable : false,
										width: 100
									},
									{
										label : 'Sold To LName',
										name : 'groupEventInvite.groupMember.lastName',
										index : 'groupEventInvite.groupMember.lastName',
										hidden : false,
										editable : false,
										width: 100
									},
 									{
 										label : 'Transaction Ref.',
 										name : 'groupEventInvite.transactionReference',
 										index : 'groupEventInvite.transactionReference',
 										hidden : false,
 										editable : false,
 										width: 100
 									},

									{
										label : 'Group Label',
										name : 'groupEventCode',
										index : 'groupEventCode',
										hidden : true,
										editable : false,
										sortable : true,
										sorttype : 'string'
									} ],
							height : 'auto',
							rowNum : 20,
							rownumbers: true,
							rownumWidth : 25,
							width : 937,
							rowList : [ 20, 50, 100, 250, 500, 1000, 2000 ],
							pager : '#pgrid',
						//	sortname : 'firstName',
							autoencode : true,
							shrinkToFit : false,
							viewrecords : true,
							grouping : true,
							loadonce : true,
							groupingView : {
								groupField : [ 'sold' ],
								groupColumnShow : [ true ],
								groupSummary : [true],
								groupText : [ '<b>Pass Status Sold - "{0}" - {1} Pass(es)</b>' ],
								groupCollapse : true,
								showSummaryOnHide : true
							},
							onSelectRow : function(id) {
								if (id && id !== lastsel) {
									jQuery('#' + gridId).jqGrid('restoreRow',
											lastsel);
									jQuery('#' + gridId).jqGrid('editRow', id,
											true, datetimePick);
									lastsel = id;
								}
							},
							editurl : "updatePasses",
							caption : captionVal
						});
	
		jQuery("#" + gridId)
		.jqGrid(
				'navGrid',
				"#pgrid",
				{del:false, edit: true, add:false}, //options
				{
					height : 400,
					width : 470,
					top : 100,
					left : 400,
					url: 'updatePasses',
					dataheight : 300,
					reloadAfterSubmit : false,
					afterSubmit : function(
							response, postdata) {
						var status = false;
						if (response.responseText === "success")
							status = true
						return [ status,
						         response.responseText ];
					}
				}, // edit options
				{}, // add options
				{}, // del options
				{													height : 200,
					width : 720,
					top : 100,
					left : 400,
					dataheight : 100} // search options
		);


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
