<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
	var myData = [];
	var lastsel;
</script>
<div class="tabbable">
	<ul class="nav nav-tabs">
		<li class="active"><a href="#optionALL" data-toggle="tab">All
				Members </a></li>
		<c:forEach items="${groupMemberCategories}" var="groupMemberCategory">
			<li><a
				href="#option<c:out value="${groupMemberCategory.memberCategoryCode}"/>"
				onclick="loadGrid('grid<c:out value="${groupMemberCategory.memberCategoryCode}"/>','<c:out value="${groupMemberCategory.memberCategoryCode}"/>','<c:out value="${groupMemberCategory.memberCategoryName}"/>');"
				data-toggle="tab"><c:out
						value="${groupMemberCategory.memberCategoryName}" /></a></li>

		</c:forEach>
	</ul>
	<div class="tab-content">

		<div class="tab-pane active" id="optionALL">
			<div class="row">
				<div class="span12">
					<!-- div class="hero-unit">  -->
					<table id="gridALL"></table>
					<div id="pgridALL"></div>
					<!-- /div>  -->
				</div>
			</div>
		</div>

		<c:forEach items="${groupMemberCategories}" var="groupMemberCategory">

			<div class="tab-pane"
				id="option<c:out value="${groupMemberCategory.memberCategoryCode}"/>">

				<div class="row">
					<div class="span12">
						<!-- div class="hero-unit">  -->
						<table
							id="grid<c:out value="${groupMemberCategory.memberCategoryCode}"/>"></table>
						<div
							id="pgrid<c:out value="${groupMemberCategory.memberCategoryCode}"/>"></div>
						<!-- /div>  -->
					</div>
				</div>

			</div>
		</c:forEach>

	</div>
</div>



<script type="text/javascript">
	$(document).ready(function() {
		loadGrid('gridALL', 'ALL', 'All Members');
		var lastsel = "";
	});

	function loadGrid(gridId, groupMemCatCode, captionVal) {
		jQuery("#" + gridId)
				.jqGrid(
						{
							url : 'json/viewAllGroupMember/' + groupMemCatCode,
							datatype : "json",
							colModel : [
									{
										label : 'Action',
										name : 'myac',
										width : 35,
										fixed : true,
										sortable : false,
										resize : false,
										formatter : 'actions',
										formatoptions : {
											keys : true,
											editbutton: true,
											delbutton : false,
										editformbutton: true,
//  											delOptions : {
// 												ajaxDelOptions : {
// 													// url: "deleteGroupMember",
// 													contentType : 'application/json;charset=utf-8',
// 													type : 'POST',
// 													datatype : 'json'
// 												}
// 											},
											editOptions : {
												url : 'updateGroupMember',
												height : 580,
												width : 540,
												top : 44,
												left : 470,
												dataheight : 490,
												closeAfterEdit: true,
												//modal : true,
												//reloadAfterSubmit : true,
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
										name : 'serialNumber',
										index : 'serialNumber',
										sorttype : "string",
										hidden : true,
										editable : true
									},
									{
										label : 'First Name',
										name : 'firstName',
										index : 'firstName',
										width : 100,
										sorttype : "string",
										search : true,
										editable : true,
										//frozen : true,
										searchoptions : {
											sopt : [ 'bw', 'eq', 'bn', 'cn',
													'nc', 'ew', 'en' ]
										}
									},
									{
										label : 'Last Name',
										name : 'lastName',
										index : 'lastName',
										sorttype : "string",
										//frozen : true,
										width : 100,
										editable : true,
										searchoptions : {
											sopt : [ 'bw', 'eq', 'bn', 'cn',
													'nc', 'ew', 'en' ]
										}

									},
									{
										label : 'Alias',
										name : 'aliasName',
										index : 'aliasName',
										width : 80,
										editable : true,
										sorttype : "string",
										//	frozen : true,
										searchoptions : {
											sopt : [ 'bw', 'eq', 'bn', 'cn',
													'nc', 'ew', 'en' ]
										}
									},									
									{
										label : 'Member ID',
										name : 'membershipIdentifier',
										index : 'membershipIdentifier',
										sorttype : "string",
										width : 80,
										hidden : false,
										editable : false,
										formatter: function(cellValue, options, rowObject) {
								
										    var membershipIdentifier = rowObject.membershipIdentifier;
										    var url = "scanGroupMember?value="+membershipIdentifier ;
										    return "<a href='"+url+"'  class='btn btn-warning btn-mini' >"+membershipIdentifier+"</a>"
										    }
									},
									{
										label : 'Active',
										name : 'activeMember',
										index : 'activeMember',
										width : 50,
										sortable : true,
										align: 'center',
										editable : false,
										formatter : formatBoolean,
										formatter: function(cellValue, options, rowObject) {
											
										    var membershipIdentifier = rowObject.serialNumber;
										    var url = "generateMembershipFormPDF?serialNumber="+membershipIdentifier ;
										    return "<a href='#' onclick='window.open(\""+url+"\",\"printwindow\",\"toolbar=no , location=no , width=200, height=200, id=printer, top=100, left=400\");'  class='btn btn-warning btn-mini' >"+formatBoolean(rowObject.activeMember)+"</a>"
										    }
									},									
									{
										label : 'Birth Day',
										name : 'birthday',
										index : 'birthday',
										width : 70,
										sortable : true,
										editable : true,
										//sorttype : "string",
										searchoptions : {
											sopt : [ 'bw', 'eq', 'bn', 'cn',
													'nc', 'ew', 'en' ]
										},
										editoptions : {
											dataInit : datePick,
											reformatAfterEdit : false
										},
										formatter : formatDate,
									//reformatAfterEdit : false

									//	,
									//			formatoptions: { srcformat: "m/d/Y", newformat: "d/m/Y" }

									},
									{
										label : 'Suburb',
										name : 'address.city',
										index : 'address.city',
										sorttype : "string",
										width : 100,
										hidden : false,
										editable : false
									},
									//{
									//	name : 'groupCode',
									//	index : 'groupCode',
									//	width : 40,
									//	editable : false,
									//	sorttype : "String"
									//},
									{
										label : 'Primary Email',
										name : 'primaryEmail',
										index : 'primaryEmail',
										sorttype : "string",
										width : 140,
										editable : true,
										editrules : {
											email : true
										}
									},
									{
										label : 'Other Email',
										name : 'otherEmail',
										index : 'otherEmail',
										sorttype : "string",
										width : 140,
										hidden : false,
										editrules : {
											required : false
											//,email : true
										},
										editable : true
									},
									{
										label : 'Phone Numbers',
										name : 'mobilephone',
										index : 'mobilephone',
										width : 80,
										sortable : false,
										sorttype : "string",
										editable : true
									},
									{
										label : 'Adults',
										name : 'adultCount',
										index : 'adultCount',
										width : 40,
										sortable : false,
										editable : true,
										sorttype : "int",
										formatter : "integer",
										editrules : {
											integer : true
										},
										summaryType : 'sum',
										summaryTpl : '<b>Total: {0}</b>'
									},
									{
										label : 'Kids',
										name : 'kidCount',
										index : 'kidCount',
										width : 40,
										sortable : false,
										editable : true,
										sorttype : "int",
										editrules : {
											integer : true
										},
										formatter : "integer",
										summaryType : 'sum',
										summaryTpl : '<b>Total: {0}</b>'
									},
									{
										label : 'Mem. Start Dt.',
										name : 'membershipStartDate',
										index : 'membershipStartDate',
										width : 90,
										sortable : true,
										editable : true,
										searchoptions : {
											sopt : [ 'bw', 'eq', 'bn', 'cn',
													'nc', 'ew', 'en' ]
										},
										//sorttype : "date",
										formatter : formatDate,
										editoptions : {
											dataInit : datePick
										}
									//	,
									//			formatoptions: { srcformat: "m/d/Y", newformat: "d/m/Y" }

									},
									{
										label : 'Mem. End Dt.',
										name : 'membershipEndDate',
										index : 'membershipEndDate',
										width : 90,
										sortable : true,
										editable : true,
										//sorttype : "date",
										editoptions : {
											dataInit : datePick
										},
										searchoptions : {
											sopt : [ 'bw', 'eq', 'bn', 'cn',
													'nc', 'ew', 'en' ]
										},
										formatter : formatDate
									//	,
									//			formatoptions: { srcformat: "m/d/Y", newformat: "d/m/Y" }

									},
									{
										label : 'Paid Mem',
										name : 'paidMember',
										index : 'paidMember',
										width : 50,
										sortable : true,
										editable : true,
										edittype : "checkbox",
										formatter : formatBoolean,
										editoptions : {
											value : "Yes:No"
										}
									},
									{
										label : 'Mem. Amt. Paid',
										name : 'paidMembershipAmount',
										index : 'paidMembershipAmount',
										width : 80,
										sortable : true,
										editable : true,
										sorttype : 'float',
										formatter : 'currency',

									},
									// 									{
									// 										label : 'Group Label',
									// 										name : 'memberCategoryCode',
									// 										index : 'memberCategoryCode',
									// 										hidden : true,
									// 										editable : false,
									// 										sortable : true,
									// 										sorttype : 'string'
									// 									},
									{
										label : 'Email verified',
										name : 'primaryEmailVerified',
										index : 'primaryEmailVerified',
										//sorttype : "string",
										hidden : false,
										editable : true,
										width : 80,
										edittype : "checkbox",
										formatter : formatBoolean,
										editoptions : {
											value : "Yes:No"
										}
									},
									{
										label : 'Other Phone',
										name : 'otherPhone',
										index : 'otherPhone',
										sorttype : "string",
										hidden : true,
										editable : true
									},
									{
										label : 'No. of Dependents',
										name : 'numberOfDependants',
										index : 'numberOfDependants',
										sorttype : "int",
										hidden : true,
										editable : true
									},
									{
										label : 'Last Trans Date',
										name : 'lastTransactionDate',
										index : 'lastTransactionDate',
										formatter : formatDate,
										//sorttype : "date",
										hidden : false,
										editable : true,
										editoptions : {
											dataInit : datePick
										}
									},
									
									{
										label : 'External ID',
										name : 'externalMembershipIdentifier',
										index : 'externalMembershipIdentifier',
										sorttype : "string",
										hidden : false,
										editable : true
									},
									{
										label : 'Membership Category',
										name : 'memberCategoryCode',
										index : 'memberCategoryCode',
										sorttype : "string",
										hidden : false,
										width : 100,
										editable : true,
										edittype : 'select',
										editoptions : {
											dataUrl : 'json/viewGroupMemberCategories',
											buildSelect : function(response) {
												var options = '<select>';
												var j = $.parseJSON(response);
												for (var i = 0; i < j.length; i++) {
													options += '<option value="' + j[i].memberCategoryCode + '">'
															+ j[i].memberCategoryName
															+ '</option>';
												}
												options += '</select>';
												return options;
											}
										}
									},
									{
										label : 'Security Code',
										name : 'securityCode',
										index : 'securityCode',
										sorttype : "string",
										hidden : true,
										editable : true
									},
									
									{
										label : 'Created At',
										name : 'createdAt',
										index : 'createdAt',
										formatter : formatDateTime,
										//sorttype : "date",
										hidden : false,
										editable : false
									},
									{
										label : 'Created By',
										name : 'createdBy',
										index : 'createdBy',
										sorttype : "string",
										hidden : true,
										editable : true
									},
									],
							height : 'auto',
							rowNum : 500,
							rownumbers : true,
							rownumWidth : 25,
							width : 937,
							rowList : [ 250, 500, 1000, 2000 ],
							pager : '#pgrid' + groupMemCatCode,
							//sortname : 'firstName',
							autoencode : true,
							shrinkToFit : false,
							viewrecords : true,
							//grouping : true,
							subGrid : true,
							loadonce : true,
							groupingView : {
								groupField : [ 'memberCategoryCode' ],
								groupColumnShow : [ false ],
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
								var parentSerialNo = data.serialNumber;
								//var dependetData = data.groupDependents;
								//var dependentObjects = JSON.parse(dependetData);
								pager_id = "p_" + subgrid_table_id;
								$("#" + subgrid_id)
										.html(
												"<table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div>");
								jQuery("#" + subgrid_table_id)
										.jqGrid(
												{
													url:"json/viewGroupDependents?serialNumber="+parentSerialNo,
													datatype : "json",													
													colNames : [ 'Action',
															'First Name',
															'Last Name',
															'Alias', 'Email',
															'Mobile',
															'Relationship',
															'Birthday',
															'Dep Serial No' ],
													colModel : [
															{
																name : 'myac',
																width : 80,
																fixed : true,
																sortable : false,
																resize : false,
																formatter : 'actions',
																formatoptions : {
																	keys : false
																}
															},
															{
																name : "firstName",
																index : "firstName",
																width : 100,
																editable : true,
																editrules : {
																	required : true
																}
															},
															{
																name : "lastName",
																index : "lastName",
																width : 100,
																editable : true
															},
															{
																name : "aliasName",
																index : "aliasName",
																width : 100,
																editable : true
															},
															{
																name : "email",
																index : "email",
																width : 150,
																editable : true,
																editrules : {
																	required : false,
																	email : true
																}
															},
															{
																name : "mobilephone",
																index : "mobilephone",
																width : 100,
																editable : true
															},
															{
																name : "relationship",
																index : "relationship",
																width : 100,
																editable : true
															},
															{
																name : "birthday",
																index : "birthday",
																width : 100,
																editoptions : {
																	dataInit : datePick
																},
																formatter : formatDate,
																editable : true
															},
															{
																name : "dependentserialNumber",
																index : "dependentserialNumber",
																width : 100,
																hidden : true,
																editable : true
															}

													],
													rowNum : 20,
													pager : pager_id,
													//	sortname: 'num',
													sortorder : "asc",
													rownumbers : true,
													rownumWidth : 25,
													height : '100%',
													editurl : "updateGroupDependents?serialNumber="
															+ parentSerialNo,
													caption : "Dependent Records"
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
												{del:false}, //options
												{
													height : 400,
													width : 420,
													top : 100,
													left : 400,
													dataheight : 300,
													reloadAfterSubmit : true,
													afterSubmit : function(
															response, postdata) {
														var status = false;
														if (response.responseText === "success")
															status = true
														return [ status,
														         response.responseText ];
													}
												}, // edit options
												{
													height : 400,
													width : 420,
													top : 100,
													left : 200,
													dataheight : 300,
													reloadAfterSubmit : true,
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
												{													height : 200,
													width : 720,
													top : 100,
													left : 400,
													dataheight : 100} // search options
										);
							},
							onSelectRow : function(id) {
								//  								if (id && id !== lastsel) {
								//  									jQuery('#' + gridId).jqGrid('restoreRow',
								//  											lastsel);
								//  									jQuery('#' + gridId).jqGrid('editRow', id,
								//  											true, datePicker);
								//  									lastsel = id;
								// 								}
							},
							editurl : "updateGroupMember",
							caption : captionVal
						});
		jQuery("#" + gridId).jqGrid('navGrid', "#pgrid" + groupMemCatCode, {
			edit : false,
			add : false,
			del : false
		});
		jQuery("#" + gridId).jqGrid('filterToolbar', {
			searchOperators : true
		});
		//jQuery("#" + gridId).jqGrid('setFrozenColumns');
	}
</script>
