<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="row">
	<div class="span12">
		<!--  div class="hero-unit"> -->

		<div class="gridDiv">
			<table id="grid"></table>
			<div id="pgrid"></div>
		</div>
		<!-- /div>  -->
	</div>
</div>

<script type="text/javascript">
	var gridDiv = "";
    var displayError = function(rowid, response)
    {
   	 alert('Error in updating project. Please correct your inputs and try again');
    }
    
	function createLinksDropDown(currVal)
	{
		var userSelectedDropDown = currVal+':/'+currVal;
		var dropdowns = fetchContentTemplateList(true);
		if(dropdowns.indexOf(currVal)===-1)
		{
			dropdowns = ';'+userSelectedDropDown+dropdowns;
		}
		return ' :Empty URL'+dropdowns;
	}
	
	
	$(document).ready(
			function() {
				gridDiv = $("div.gridDiv").html();
				loadGrid('grid', 'Main Links');
			});
	function loadDiv() {

		$("div.gridDiv").html(gridDiv);
	}
	function loadGrid(gridId, captionVal) {
		
		jQuery("#" + gridId)
				.jqGrid(
						{
							url : 'json/viewGroupMainLinks',
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
																				editformbutton: true,
																				delOptions : {
																					url : 'handleGroupMainLink?operation=delete',
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
																			url : 'handleGroupMainLink?operation=update',
																			height : 270,
																			width : 440,
																			top : 200,
																			left : 470,
																			dataheight : 180,																					
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
										label : 'ID',
										name : 'id',
										index : 'id',
										hidden : true,
										editable : true,
										width : 20
									},								
									{
										label : 'Link Name',
										name : 'linkName',
										index : 'linkName',
										hidden : false,
										editable : true,
										width : 177,
										editrules : {
											required : true
										}
									},								
									{
										label : 'Link Description',
										name : 'linkDescription',
										index : 'linkDescription',
										hidden : false,
										editable : true,
										width : 300
									}, {
										label : 'Disabled',
										name : 'disabled',
										index : 'disabled',
										hidden : false,
										editable : true,
										align: 'center',
										formatter : formatBoolean,
										edittype : "checkbox",
										editoptions : {
											value : "Yes:No"
										},
										width : 70
									},
									{
										label : 'Order',
										name : 'linkOrder',
										index : 'linkOrder',
										hidden : false,
										align: 'center',
										editable : true,
										width: 50,
										formatter: 'double',
										editrules : {
											required : true
										}
									},
									{
										label : 'Override URL',
										name : 'overrideChildUrlorJs',
										index : 'overrideChildUrlorJs',
										hidden : false,
										editable : true,
										align: 'center',
										width : 100,
										formatter : formatBoolean,
										edittype : "checkbox",
										editoptions : {
											value : "Yes:No"
										}
									},
									{
										label : 'Updated Date',
										name : 'updatedAt',
										index : 'updatedAt',
										width : 100,
										sortable : true,
										editable : false,
										formatter : formatDate
									},
									{
										label : 'Created Date',
										name : 'createdAt',
										index : 'createdAt',
										width : 100,
										hidden: true,
										sortable : true,
										editable : true,
										formatter : formatDate
									},
									{
										label : 'Created By',
										name : 'createdBy',
										index : 'createdBy',
										width : 100,
										hidden: true,
										sortable : true,
										editable : true
									},
									{
										label : 'Updated By',
										name : 'updatedBy',
										index : 'updatedBy',
										width : 100,
										hidden: true,
										sortable : true,
										editable : true
									}],
							height : 'auto',
							rowNum : 20,
							rownumbers : true,
							rownumWidth : 25,
							width : 937,
							rowList : [ 5, 10, 15, 20 ],
							pager : '#pgrid',
							autoencode : true,
							shrinkToFit : false,
							viewrecords : true,
							//grouping : true,
							loadonce : false,
							subGrid : true,
							gridview: true,
			                rowattr: function (rda) {
			                    if (rda.disabled) {
			                    	return {"class": "ui-state-error"};
			                    }
			                },
// 							groupingView : {
// 								groupField : [ 'memberCategoryCode' ],
// 								groupColumnShow : [ false ],
// 								groupSummary : [true],
// 								groupText : [ '<b>{0} - {1} Members(s)</b>' ]
// 							},

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

								var groupMainLinkId = data.id;
								//var parentSerialNo = groupMemberData;
								pager_id = "p_" + subgrid_table_id;
								$("#" + subgrid_id)
										.html(
												"<table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div>");
								jQuery("#" + subgrid_table_id)
										.jqGrid(
												{
													url : "json/viewGroupSubLinks/"
															+ groupMainLinkId
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
																	editformbutton: true,
																	delOptions : {
																		url : 'handleGroupSubLink?operation=delete',
																		height : 290,
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
																url : "handleGroupSubLink?operation=update",
																height : 350,
																width : 440,
																top : 200,
																left : 470,
																dataheight : 260,
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
																label : 'ID',
																name : 'id',
																index : 'id',
																hidden : true,
																editable : true,
																width: 100
															},								
															{
																label : 'Link Name',
																name : 'linkName',
																index : 'linkName',
																hidden : false,
																editable : true,
																width : 150,
																editrules : {
																	required : true
																}
															},								
															{
																label : 'URL',
																name : 'linkHref',
																index : 'linkHref',
																hidden : false,
																editable : true,
																width : 200,
																editrules : {
																	required : true
																}
															},								
															{
																label : 'JS Function',
																name : 'linkJavaScript',
																index : 'linkJavaScript',
																hidden : false,
																editable : true,
																width : 150
															}, {
																label : 'Disabled',
																name : 'disabled',
																index : 'disabled',
																hidden : false,
																editable : true,
																align: 'center',
																formatter : formatBoolean,
																edittype : "checkbox",
																editoptions : {
																	value : "Yes:No"
																},
																width : 50
															},
															{
																label : 'Order',
																name : 'linkOrder',
																index : 'linkOrder',
																hidden : false,
																align: 'center',
																editable : true,
																width: 50,
																formatter: 'double',
																editrules : {
																	required : true
																}
															},								
															{
																label : 'Link Description',
																name : 'linkDescription',
																index : 'linkDescription',
																hidden : false,
																editable : true,
																width : 140
															}
// 															,
// 															{
// 																label : 'Group Label',
// 																name : 'groupEventCode',
// 																index : 'groupEventCode',
// 																hidden : true,
// 																editable : false,
// 																sortable : true,
// 																sorttype : 'string'
// 															}
															],
													rowNum : 20,
													pager : pager_id,
													//	sortname: 'num',
													sortorder : "asc",
													rownumbers : true,
													grouping : false,
													groupingView : {
														groupField : [ 'groupEventCode' ],
														groupColumnShow : [ false ],
														groupSummary : [true],
														groupText : [ '<b>{0} - {1} Passes(s)</b>' ]
													},
													rownumWidth : 25,
													height : '100%',
													editurl : "handleGroupSubLink?operation=update",
													caption : "Group Sub Links",
													gridview: true,
									                rowattr: function (rd) {
									                    if (rd.disabled) {
									                    	return {"class": "ui-state-error"};
									                    }
									                },
													subGrid : true,
//						 							groupingView : {
//					 								groupField : [ 'memberCategoryCode' ],
//					 								groupColumnShow : [ false ],
//					 								groupSummary : [true],
//					 								groupText : [ '<b>{0} - {1} Members(s)</b>' ]
//					 							},

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
													var indexer = subgrid_id.lastIndexOf("_"+row_id);
													var subSubGrid  = subgrid_id.substring(0, indexer);
													var subgrid_table_id, pager_id;
													subgrid_table_id = subgrid_id + "_u";
													var data = jQuery('#'+subSubGrid).jqGrid(
															'getRowData', row_id);
													var groupSubLinkId = data.id;
													var lastSelectedGLAId = null;
													//var parentSerialNo = groupMemberData;
													pager_id = "q_" + subgrid_table_id;
													$("#" + subgrid_id)
															.html(
																	"<table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div>");
													jQuery("#" + subgrid_table_id)
															.jqGrid(
																	{
																		url : "json/viewGroupLinkAccesses/"
																				+ groupSubLinkId
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
																						editbutton : false,
																						delOptions : {
																							url : 'handleGroupLinkAccess?operation=delete',
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
																						}
																					}
																				},
																				{
																					label : 'ID',
																					name : 'id',
																					index : 'id',
																					hidden : true,
																					editable : true,
																					width: 100
																				},								
																				{
																					label : 'URL',
																					name : 'linkHref',
																					index : 'linkHref',
																					hidden : false,
																					editable : true,
																					width : 230,
																					editrules : {
																						required : false
																					},
																					edittype:'select',
																					editoptions:{dataInit: function(elem){ }, value: createLinksDropDown('')}
																				},								
																				{
																					label : 'JS Function',
																					name : 'linkJavaScript',
																					index : 'linkJavaScript',
																					hidden : false,
																					editable : true,
																					width : 230
																				},
																				{
																					label : 'Start Date',
																					name : 'startDate',
																					index : 'startDate',
																					width : 74,
																					sortable : true,
																					editable : true,
																					formatter : formatDateTime,
																					editoptions : {
																						dataInit : datetimePick
																					}
																				},
																				{
																					label : 'Expiry Date',
																					name : 'expiryDate',
																					index : 'expiryDate',
																					width : 74,
																					sortable : true,
																					editable : true,
																					formatter : formatDateTime,
																					editoptions : {
																						dataInit : datetimePick
																					}
																				},
																				{
																					label : 'Updated Date',
																					name : 'updatedAt',
																					index : 'updatedAt',
																					width : 80,
																					sortable : true,
																					editable : false,
																					formatter : formatDate
																				}
//					 															,
//					 															{
//					 																label : 'Group Label',
//					 																name : 'groupEventCode',
//					 																index : 'groupEventCode',
//					 																hidden : true,
//					 																editable : false,
//					 																sortable : true,
//					 																sorttype : 'string'
//					 															}
																				],
																		beforeSelectRow: function(rowid) 
																		{
																			var rowData = jQuery('#'+subgrid_table_id).jqGrid ('getRowData', rowid);
																			var link = rowData.linkHref;
																			
																			var dropdowns = createLinksDropDown(link);
																			jQuery('#'+subgrid_table_id).setColProp('linkHref',{edittype:'select', editoptions:{dataInit: function(elem){$(elem).combify(); }, value: dropdowns}});
																			if(lastSelectedGLAId == null)
																			{

																				jQuery('#'+subgrid_table_id).jqGrid('editRow',rowid,{keys: true, extraparam: {linkHref: function () { 	var rowData = jQuery('#'+subgrid_table_id).jqGrid ('getRowData', rowid);
																				object = $(rowData.linkHref);
																				var c = (object.find('input[id="'+rowid+'_linkHref"]').val());
																	
																				link = c;
																				return c; 
																				}}, aftersavefunc: function(rowid, response, options) {
																					
																		            jQuery('#'+subgrid_table_id).jqGrid('setCell',rowid,'linkHref',link);
																		          
																				},errorfunc: displayError});
																				lastSelectedGLAId = rowid;
																				
																			}
																			else if(lastSelectedGLAId == rowid)
																			{
																				jQuery('#'+subgrid_table_id).jqGrid('editRow',rowid,{keys: true, extraparam: {linkHref: function () { 	var rowData = jQuery('#'+subgrid_table_id).jqGrid ('getRowData', rowid);
																				object = $(rowData.linkHref);

																				var c = (object.find('input[id="'+rowid+'_linkHref"]').val());

																				link = c;
																				return c; 
																				}}, aftersavefunc: function(rowid, response, options) {
																					
																		            jQuery('#'+subgrid_table_id).jqGrid('setCell',rowid,'linkHref',link);
																		          
																				},errorfunc: displayError});
																			}
																			else 
																			{
																				alert('Navigated Somewhere');
																				lastSelectedGLAId = rowid;
																			}
																		},
																		rowNum : 10,
																		pager : pager_id,
																		//	sortname: 'num',
																		sortorder : "asc",
																		rownumbers : true,
																		grouping : false,
																		groupingView : {
																			groupField : [ 'groupEventCode' ],
																			groupColumnShow : [ false ],
																			groupSummary : [true],
																			groupText : [ '<b>{0} - {1} Passes(s)</b>' ]
																		},
																		rownumWidth : 25,
																		height : '100%',
																		editurl : "handleGroupLinkAccess?operation=update",
																		caption : "Group Link Access",
																		subGrid : true,
																		gridview: true,
														                rowattr: function (rd) {
														                    if (!isValidDates(rd.startDate, rd.expiryDate)) {
														                    	return {"class": "ui-state-error"};
														                    }
														                },
//											 							groupingView : {
//										 								groupField : [ 'memberCategoryCode' ],
//										 								groupColumnShow : [ false ],
//										 								groupSummary : [true],
//										 								groupText : [ '<b>{0} - {1} Members(s)</b>' ]
//										 							},

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
																		var indexer = subgrid_id.lastIndexOf("_"+row_id);
																		var subSubGrid  = subgrid_id.substring(0, indexer);
																		var subgrid_table_id, pager_id;
																		subgrid_table_id = subgrid_id + "_v";
																		var data = jQuery('#'+subSubGrid).jqGrid(
																				'getRowData', row_id);
																		var groupLinkAccessId = data.id;
																		//var parentSerialNo = groupMemberData;
																		pager_id = "r_" + subgrid_table_id;
																		$("#" + subgrid_id)
																				.html(
																						"<table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div>");
																		jQuery("#" + subgrid_table_id)
																				.jqGrid(
																						{
																							url : "json/viewGroupLinkRoleAccesses/"
																									+ groupLinkAccessId
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
																												url : 'handleGroupLinkRoleAccess?operation=delete&groupLinkAccessId='
																													+ groupLinkAccessId,
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
																										url : "handleGroupLinkRoleAccess?operation=update&groupLinkAccessId="
																											+ groupLinkAccessId,
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
																										label : 'ID',
																										name : 'id',
																										index : 'id',
																										hidden : true,
																										editable : true,
																										width: 100
																									},								
																									{
																										label : 'Role',
																										name : 'role',
																										index : 'role',
																										hidden : false,
																										editable : true,
																										width : 100,
																										edittype : 'select',
																										editoptions : {
																											dataUrl : 'viewApplicationRoles',
																											buildSelect : function(response) {
																												var options = '<select>';
																												var j = $.parseJSON(response);
																												for (var i = 0; i < j.length; i++) {
																													options += '<option value="' + j[i] + '">'
																															+ j[i]
																															+ '</option>';
																												}
																												options += '</select>';
																												return options;
																											}
																										}
																									},
																									{
																										label : 'Start Date',
																										name : 'startDate',
																										index : 'startDate',
																										width : 80,
																										sortable : true,
																										editable : true,
																										formatter : formatDateTime,
																										editoptions : {
																											dataInit : datetimePick
																										}
																									},
																									{
																										label : 'Expiry Date',
																										name : 'expiryDate',
																										index : 'expiryDate',
																										width : 80,
																										sortable : true,
																										editable : true,
																										formatter : formatDateTime,
																										editoptions : {
																											dataInit : datetimePick
																										}
																									},
																									{
																										label : 'Updated Date',
																										name : 'updatedAt',
																										index : 'updatedAt',
																										width : 80,
																										sortable : true,
																										editable : false,
																										formatter : formatDate
																									},
																									{
																										label : 'Created Date',
																										name : 'createdAt',
																										index : 'createdAt',
																										width : 80,
																										sortable : true,
																										editable : false,
																										formatter : formatDate
																									}
//										 															,
//										 															{
//										 																label : 'Group Label',
//										 																name : 'groupEventCode',
//										 																index : 'groupEventCode',
//										 																hidden : true,
//										 																editable : false,
//										 																sortable : true,
//										 																sorttype : 'string'
//										 															}
																									],
																							rowNum : 10,
																							pager : pager_id,
																							//	sortname: 'num',
																							sortorder : "asc",
																							rownumbers : true,
																							grouping : false,
																							gridview: true,
																			                rowattr: function (rd) {
																			                    if (!isValidDates(rd.startDate, rd.expiryDate)) {
																			                    	return {"class": "ui-state-error"};
																			                    }
																			                },
																							groupingView : {
																								groupField : [ 'groupEventCode' ],
																								groupColumnShow : [ false ],
																								groupSummary : [true],
																								groupText : [ '<b>{0} - {1} Passes(s)</b>' ]
																							},
																							rownumWidth : 25,
																							height : '100%',
																							editurl : "handleGroupLinkRoleAccess?operation=update&groupLinkAccessId="
																								+ groupLinkAccessId,
																							caption : "Group Link Access Roles"
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
																							height : 270,
																							width : 440,
																							top : 200,
																							left : 470,
																							dataheight : 180,
																							reloadAfterSubmit : true,
																							url : "handleGroupLinkRoleAccess?operation=add&groupLinkAccessId="
																								+ groupLinkAccessId,
																				            beforeShowForm: function(form) {
//										 										                     $('#tr_noOfPeopleTagged', form).hide();
//										 										                     $('#tr_passPrice', form).hide();
//										 										                     $('#tr_soldBy', form).hide();
//										 										                     $('#tr_passStartDate', form).hide();
//										 										                     $('#tr_passExpiryDate', form).hide();
//										 										                     $('#tr_tableNumber', form).hide();
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
																	}
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
																		height : 270,
																		width : 440,
																		top : 200,
																		left : 470,
																		dataheight : 180,
																		reloadAfterSubmit : true,
																		processData: "Processing...",
																		//modal: true,
																		closeAfterAdd: true,
																		url : "handleGroupLinkAccess?operation=add&groupSubLinkId="
																			+ groupSubLinkId,
															            beforeShowForm: function(form) {
															            	
															            	//	 $('#tr_linkHref', form).hide();
//					 										                     $('#tr_noOfPeopleTagged', form).hide();
//					 										                     $('#tr_passPrice', form).hide();
//					 										                     $('#tr_soldBy', form).hide();
//					 										                     $('#tr_passStartDate', form).hide();
//					 										                     $('#tr_passExpiryDate', form).hide();
//					 										                     $('#tr_tableNumber', form).hide();
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
												}
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
													height : 350,
													width : 440,
													top : 200,
													left : 470,
													dataheight : 260,
													reloadAfterSubmit : true,
													url : "handleGroupSubLink?operation=add&groupMainLinkId="
														+ groupMainLinkId,
										            beforeShowForm: function(form) {
// 										                     $('#tr_noOfPeopleTagged', form).hide();
// 										                     $('#tr_passPrice', form).hide();
// 										                     $('#tr_soldBy', form).hide();
// 										                     $('#tr_passStartDate', form).hide();
// 										                     $('#tr_passExpiryDate', form).hide();
// 										                     $('#tr_tableNumber', form).hide();
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
							editurl : "handleGroupMainLink?operation=update",
							caption : captionVal
						});
		jQuery("#" + gridId).jqGrid('navGrid', "#pgrid", {
			edit : false,
			add : true,
			del : true
		} ,//options
		{}, // edit options
		{savekey: [true, 13],
			height : 270,
			width : 440,
			top : 200,
			left : 470,
			dataheight : 180,
			reloadAfterSubmit : true,
			url : "handleGroupMainLink?operation=add",
            beforeShowForm: function(form) {
//	                     $('#tr_noOfPeopleTagged', form).hide();
//	                     $('#tr_passPrice', form).hide();
//	                     $('#tr_soldBy', form).hide();
//	                     $('#tr_passStartDate', form).hide();
//	                     $('#tr_passExpiryDate', form).hide();
//	                     $('#tr_tableNumber', form).hide();
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
		{savekey: [true, 13],
			height : 240,
			width : 520,
			top : 200,
			left : 500,
			dataheight : 150,
			reloadAfterSubmit : true,
			url : "handleGroupMainLink?operation=delete",
            beforeShowForm: function(form) {
//	                     $('#tr_noOfPeopleTagged', form).hide();
//	                     $('#tr_passPrice', form).hide();
//	                     $('#tr_soldBy', form).hide();
//	                     $('#tr_passStartDate', form).hide();
//	                     $('#tr_passExpiryDate', form).hide();
//	                     $('#tr_tableNumber', form).hide();
                  },
			afterSubmit : function(
					response, postdata) {
				var status = false;
				if (response.responseText === "success")
					status = true
				return [ status,
				         response.responseText ];
			}
		}, // del options
		{	height : 200,
			width : 720,
			top : 300,
			left : 400,
			dataheight : 100} // search options
			);
		jQuery("#" + gridId).jqGrid('filterToolbar', {
			searchOperators : true
		});
	}

</script>
