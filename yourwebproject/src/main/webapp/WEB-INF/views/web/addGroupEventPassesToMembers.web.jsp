<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="row">
	<div class="span12">
		<!--  div class="hero-unit"> -->
		<form:form commandName="groupEventInvite"
			action="saveGroupEventInvite" method="post" id="groupEventInvite">
			<fieldset>


				<h2>View Event Passes by Group Members</h2>
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
			<br />
			<table id="gridRSVP"></table>
			<div id="pgridRSVP"></div>
		</div>
		<!-- /div>  -->
	</div>
</div>

<script type="text/javascript">
	var gridDiv = "";
	$(document).ready(
			function() {
				$("#groupEventInvite").validate(
						{
							rules : {
								memberCategoryCode : {
									required : true,
								},
								groupEventCode : {
									required : true
								}

							},
							errorClass : "control-group error",
							validClass : "control-group success",
							errorElement : "span",
							highlight : function(element, errorClass,
									validClass) {
								if (element.type === 'radio') {
									this.findByName(element.name).parent("div")
											.parent("div").removeClass(
													validClass).addClass(
													errorClass);
								} else {
									$(element).parent("div").parent("div")
											.removeClass(validClass).addClass(
													errorClass);
								}
							},
							unhighlight : function(element, errorClass,
									validClass) {
								if (element.type === 'radio') {
									this.findByName(element.name).parent("div")
											.parent("div").removeClass(
													errorClass).addClass(
													validClass);
								} else {
									$(element).parent("div").parent("div")
											.removeClass(errorClass).addClass(
													validClass);
								}
							}
						});
				gridDiv = $("div.gridDiv").html();

			});

	function loadDiv() {

		$("div.gridDiv").html(gridDiv);
	}
	function loadGrid(gridId, groupMemCatCode, groupEventCode, captionVal) {
		//	alert("gridId-->"+gridId+" groupMemCatCode-->"+groupMemCatCode+" groupEventCode-->"+groupEventCode+" captionVal-->"+captionVal)
		jQuery("#" + gridId)
				.jqGrid(
						{
							url : 'json/viewGroupEventInvites/'
									+ groupEventCode + '/' + groupMemCatCode,
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
																				keys : true,
																				delbutton : false
																			}
																		},
// 									{
// 										label : 'Serial No',
// 										name : 'groupMember',
// 										index : 'groupMember',
// 										hidden : true,
// 										editable : false,
// 										width : 130,
// 										formatter : function(cellValue,
// 												options, rawData) {
// 											var groupMember = cellValue;
// 											return groupMember.serialNumber;
// 										},
// 									},
									
									{
										label : 'Group Invite Id',
										name : 'groupEventInviteId',
										index : 'groupEventInviteId',
										hidden : true,
										editable : true,
										width : 20
									},								
									{
										label : 'First Name',
										name : 'groupMember.firstName',
										index : 'groupMember.firstName',
										hidden : false,
										editable : false,
										width : 100
									},
									{
										label : 'Last Name',
										name : 'groupMember.lastName',
										index : 'groupMember.lastName',
										hidden : false,
										editable : false,
										width : 100
									},
									{
										label : 'Membership End Date',
										name : 'groupMember.membershipEndDate',
										index : 'groupMember.membershipEndDate',
										width : 80,
										sortable : true,
										editable : false,
										//	sorttype : "date",
										formatter : formatDate
									//	,
									//			formatoptions: { srcformat: "m/d/Y", newformat: "d/m/Y" }

									},
								 {
										label : 'Transaction Ref.',
										name : 'transactionReference',
										index : 'transactionReference',
										hidden : false,
										editable : true,
										width : 100
									}, {
										label : 'Trans Appd.',
										name : 'transactionApproved',
										index : 'transactionApproved',
										hidden : false,
										editable : true,
										align: 'center',
										formatter : formatBoolean,
										edittype : "checkbox",
										editoptions : {
											value : "Yes:No"
										},
										width : 70
									}, {
										label : 'Transaction Dt.',
										name : 'transactionDateTime',
										index : 'transactionDateTime',
										width : 100,
										sortable : true,
										editable : true,
										formatter : formatDate,
										editoptions : {
											dataInit : datePick
										}
									},
									{
										label : 'Amt. Pd.',
										name : 'paidAmount',
										index : 'paidAmount',
										hidden : false,
										align: 'center',
										editable : true,
										width: 50,
										formatter: 'double',
										summaryType : 'sum',
										summaryTpl : '<b>Total: {0}</b>'
									},
									{
										label : 'Invite Code',
										name : 'groupEventInviteCode',
										index : 'groupEventInviteCode',
										hidden : false,
										editable : false,
										width : 100
									},
									{
										label : 'Invite Emailed',
										name : 'inviteSent',
										index : 'inviteSent',
										hidden : false,
										editable : false,
										align: 'center',
										width : 100,
										formatter : formatBoolean,
										edittype : "checkbox",
										editoptions : {
											value : "Yes:No"
										}
									}, {
										label : 'Invite Delivered',
										name : 'inviteDelivered',
										index : 'inviteDelivered',
										align: 'center',
										hidden : false,
										editable : false,
										formatter : formatBoolean,
										width : 100
									}, {
										label : 'Invite Held',
										name : 'inviteHeld',
										index : 'inviteHeld',
										align: 'center',
										hidden : false,
										editable : true,
										formatter : formatBoolean,
										width : 100,
										edittype : "checkbox",
										editoptions : {
											value : "true:false"
										}
									}, {
										label : 'RSVPd',
										name : 'rsvpd',
										index : 'rsvpd',
										align: 'center',
										hidden : false,
										editable : false,
										formatter : formatBoolean,
										width : 100
									}, {
										label : 'Mark Attended',
										name : 'markAttended',
										index : 'markAttended',
										hidden : true,
										editable : true,
										formatter : formatBoolean,
										edittype : "checkbox",
										editoptions : {
											value : "Yes:No"
										},
										width : 100
									}, {
										label : 'Group Label',
										name : 'memberCategoryCode',
										index : 'memberCategoryCode',
										align: 'center',
										hidden : false,
										width : 100,
										editable : false,
										sortable : true,
										sorttype : 'string'
									} ],
							height : 'auto',
							rowNum : 500,
							rownumbers : true,
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
							subGrid : true,
							groupingView : {
								groupField : [ 'memberCategoryCode' ],
								groupColumnShow : [ false ],
								groupSummary : [true],
								groupText : [ '<b>{0} - {1} Members(s)</b>' ]
							},

							subGridOptions : {
								"plusicon" : "ui-icon-triangle-1-e",
								"minusicon" : "ui-icon-triangle-1-s",
								"openicon" : "ui-icon-arrowreturn-1-e",
								// load the subgrid data only once
								// and the just show/hide
								"reloadOnExpand" : false,
								// select the row when the expand column is clicked
								"selectOnExpand" : true
							},
							subGridRowExpanded : function(subgrid_id, row_id) {
								var subgrid_table_id, pager_id;
								subgrid_table_id = subgrid_id + "_t";
								var data = jQuery('#' + gridId).jqGrid(
										'getRowData', row_id);
								//alert(data);

								var groupEventInviteId = data.groupEventInviteId;
								//var parentSerialNo = groupMemberData;
								pager_id = "p_" + subgrid_table_id;
								$("#" + subgrid_id)
										.html(
												"<table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div>");
								jQuery("#" + subgrid_table_id)
										.jqGrid(
												{
													url : "json/viewGroupEventPassesByGroupEventInviteId/"
															+ groupEventInviteId
															,
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
																	keys : true,
																	editbutton: true,
																	delOptions : {
																		url : 'deletePassesFromInvites',
																		height : 220,
																		width : 440,
																		top : 300,
																		left : 470,
																		dataheight : 120,
																		reloadAfterSubmit : true,
																		afterSubmit : function(
																				response, postdata) {
																			var status = false;
																			if (response.responseText === "success")
																				status = true
																			return [ status,
																			         response.responseText ];
																		}
																	},
															editOptions : {
																url : "updatePasses",
																reloadAfterSubmit : true,
																afterSubmit : function(
																		response, postdata) {
																	var status = false;
																	if (response.responseText === "success")
																		status = true
																	return [ status,
																	         response.responseText ];
																}}
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
																hidden : false,
																editable : true,
																align : 'center',
																width : 70,
																editrules : {
																	required : false
																//,email : true
																}
															},
															{
																label : 'Count',
																name : 'noOfPeopleTagged',
																index : 'noOfPeopleTagged',
																hidden : false,
																editable : true,
																align : 'center',
																width : 45,
																summaryType : 'sum',
																summaryTpl : '<b>Total: {0}</b>'
															},
															{
																label : 'Price',
																name : 'passPrice',
																index : 'passPrice',
																hidden : false,
																align : 'center',
																editable : true,
																width : 40,
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
																label : 'Sold',
																name : 'sold',
																index : 'sold',
																hidden : true,
																editable : true,
																formatter: formatBoolean,
																edittype : "checkbox",
																editoptions : {
																	value : "Yes:No"}, 
																width: 10
															},
															{
																label : 'Invalidated',
																name : 'passInvalidated',
																index : 'passInvalidated',
																formatter: formatBoolean,
																edittype : "checkbox",
																editoptions : {
																	value : "Yes:No"}, 
																hidden : true,
																editable : true,
																width: 10
															},
															{
																label : 'Start Date',
																name : 'passStartDate',
																index : 'passStartDate',
																hidden : false,
																editable : false,
																formatter : formatDateTime,
																width : 100,							
																editoptions : {
																	dataInit : datetimePick,
																}
															},
															{
																label : 'Exp Date',
																name : 'passExpiryDate',
																index : 'passExpiryDate',
																hidden : false,
																editable : false,
																formatter : formatDateTime,
																width : 100,
																editoptions : {
																	dataInit : datetimePick
																}
															},
															
															{
																label : 'Scan Date Time',
																name : 'trackingDate',
																index : 'trackingDate',
																hidden : false,
																editable : false,
																formatter : formatDateTime,
																width : 100
															},
															{
																label : 'Attndee FName',
																name : 'groupMember.firstName',
																index : 'groupMember.firstName',
																hidden : false,
																editable : false,
																width: 85
															},
															{
																label : 'Attndee LName',
																name : 'groupMember.lastName',
																index : 'groupMember.lastName',
																hidden : false,
																editable : false,
																width: 80
															},
															{
																label : 'Table',
																name : 'tableNumber',
																index : 'tableNumber',
																hidden : false,
																editable : true,
																width: 70,
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
																	}
																}
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
													rowNum : 30,
													pager : pager_id,
													//	sortname: 'num',
													sortorder : "asc",
													rownumbers : true,
													grouping : true,
													groupingView : {
														groupField : [ 'groupEventCode' ],
														groupColumnShow : [ false ],
														groupSummary : [true],
														groupText : [ '<b>{0} - {1} Passes(s)</b>' ]
													},
													rownumWidth : 25,
													height : '100%',
													editurl : "updatePasses",
													caption : "Bought Passes <a href='generatePasses?groupEventInviteIdOrEventCode="+groupEventInviteId+"' target='_top' class='btn btn-warning btn-mini' >PRINT PASSES</a>"
												});
								// 								jQuery("#" + subgrid_table_id).jqGrid(
								// 										'navGrid', "#" + pager_id, {
								// 											edit : false,
								// 											add : true,
								// 											del : false
								// 										})
								jQuery("#" + subgrid_table_id)
										.jqGrid(
												'navGrid',
												"#" + pager_id,
												{
													del : false ,edit:false
												}, //options
												{}, // edit options
												{savekey: [true, 13],
													height : 220,
													width : 440,
													top : 300,
													left : 470,
													dataheight : 120,
													reloadAfterSubmit : true,
													url : "updatePassesToInvites?groupEventInviteId="
														+ groupEventInviteId,
										            beforeShowForm: function(form) {
										                     $('#tr_noOfPeopleTagged', form).hide();
										                     $('#tr_passPrice', form).hide();
										                     $('#tr_soldBy', form).hide();
										                     $('#tr_passStartDate', form).hide();
										                     $('#tr_passExpiryDate', form).hide();
										                     $('#tr_tableNumber', form).hide();
										                  },
													afterSubmit : function(
															response, postdata) {
														var status = false;
														if (response.responseText === "success")
															status = true
														return [ status,
														         response.responseText ];
													}
												}, // add options
												{}, // del options
												{	height : 200,
													width : 720,
													top : 300,
													left : 400,
													dataheight : 100} // search options
										);
							},
							onSelectRow : function(id) {
//								if (id && id !== lastsel) {
//									jQuery('#' + gridId).jqGrid('restoreRow',
//											lastsel);
//									jQuery('#' + gridId).jqGrid('editRow', id,
//											true, pickdates);
//									lastsel = id;
//								}
							},
							editurl : "updateGroupEventInviteAttendance",
							caption : captionVal
						});
		jQuery("#" + gridId).jqGrid('navGrid', "#pgrid", {
			edit : false,
			add : false,
			del : false
		});
		jQuery("#" + gridId).jqGrid('filterToolbar', {
			searchOperators : true
		});
	}

	$(function() {
		$('.input-group.date').datepicker({
			format : "dd/mm/yyyy"
		});
		$('.input-daterange').datepicker({
			format : "dd/mm/yyyy"
		});

		buildGroupMemberCategoriesOptions('memberCategoryCode');
		buildGroupEventsOptionsByMemberCategory("NULL", "groupEventCode");
	});
</script>
