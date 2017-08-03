<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="row">
	<div class="span12">
		<!--  div class="hero-unit"> -->
				<h2>Group Event Ticket Management</h2>
				<br />

<div class="tabbable">
	<ul class="nav nav-tabs">
		<li class="active"><a href="#optionALL" data-toggle="tab">All
				Events </a></li>
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
</div></div>
		<!-- /div>  -->
	</div>
</div>

<script type="text/javascript">

function loadTotalSoldForEvent(cellvalue, options, rowObject) {
	var cnt = 0
	//setTimeout("loadTotalSoldCountForEvent('count"+rowObject.eventCode+"', '"+rowObject.eventCode+"')", 2000);
	setTimeout("getTotalSoldCountForEvent('"+rowObject.eventCode+"', function(cnt) { decorateCount('count"+rowObject.eventCode+"',cnt); })", 2000);
	if(isValidDates(null, rowObject.eventDate)){
	//setInterval("loadTotalSoldCountForEvent('count"+rowObject.eventCode+"', '"+rowObject.eventCode+"')", 10000);
	setInterval("getTotalSoldCountForEvent('"+rowObject.eventCode+"', function(cnt) { decorateCount('count"+rowObject.eventCode+"',cnt); })", 10000);
	return "<span align='center' id='count"+rowObject.eventCode+"' class='label label-info'><div>"+cnt+"</div></span>";
	}
	return "<span align='center' id='count"+rowObject.eventCode+"' class='label label-error'><div>"+cnt+"</div></span>";
 	
}

function loadSoldForEvent(cellvalue, options, rowObject) {
	var soldcnt = 0
	//setTimeout("loadSoldCountForCategory('"+rowObject.groupEvent.eventCode+"_"+cellvalue+"','"+rowObject.groupEvent.eventCode+"', '"+rowObject.groupEventPassCategoryId+"')", 2000);
	setTimeout("getSoldCountForCategory('"+rowObject.groupEvent.eventCode+"', '"+rowObject.groupEventPassCategoryId+"', function(cnt) { decorateCount('"+rowObject.groupEvent.eventCode+"_"+cellvalue+"',cnt); })", 2000);
	if(isValidDates(null, rowObject.groupEvent.eventDate) && isValidDates(rowObject.purchaseStartDateTime, rowObject.purchaseExpiryDateTime)){
	setInterval("getSoldCountForCategory('"+rowObject.groupEvent.eventCode+"', '"+rowObject.groupEventPassCategoryId+"', function(cnt) { decorateCount('"+rowObject.groupEvent.eventCode+"_"+cellvalue+"',cnt); })", 10000);	
	//setInterval("loadSoldCountForCategory('"+rowObject.groupEvent.eventCode+"_"+cellvalue+"','"+rowObject.groupEvent.eventCode+"', '"+rowObject.groupEventPassCategoryId+"')", 10000);
	return "<span align='center' id='"+rowObject.groupEvent.eventCode+"_"+cellvalue+"' class='label label-info'><div>"+soldcnt+"</div></span>";
	}
	return "<span align='center' id='"+rowObject.groupEvent.eventCode+"_"+cellvalue+"' class='label label-error'><div>"+soldcnt+"</div></span>";
 	
}



    $(document).ready(function () {
    	loadGrid('gridALL', 'All Events');
    });
    
	function loadGrid(gridId, captionVal) {
	//	alert("gridId-->"+gridId+" groupMemCatCode-->"+groupMemCatCode+" groupEventCode-->"+groupEventCode+" captionVal-->"+captionVal)
		jQuery("#" + gridId)
				.jqGrid(
						{
							url : 'json/viewAllGroupEvents',
							datatype : "json",
							colModel : [
									{
										label : 'Event',
										name : 'eventName',
										index : 'eventName',
										hidden : false,
										editable : false,
										width: 240
									},
									{
										label : "Total Passes",
										name : "maxNumberOfPasses",
										index : "maxNumberOfPasses",
										width : 70,
										editable : false,
										editrules:{integer:true}
									},
									{
										label : 'Event Date',
										name : 'eventDate',
										index : 'eventDate',
										hidden : false,
										editable : false,
										formatter: formatDateTime,
										width: 100
									},
									{
										label : 'RSVP Date',
 										name : 'rsvpDeadlineDate',
 										index : 'rsvpDeadlineDate',
 										hidden : false,
 										editable : false,
 										formatter: formatDateTime,
										width: 120
 									},
									{
										label : '# Sold',
										name : "totalSold",
										width : 40,
										align: 'center',
										editable : false,
										hidden: false,
										formatter: loadTotalSoldForEvent
									},
									{
										label : 'Paid Event',
										name : 'paidEvent',
										index : 'paidEvent',
										hidden : false,
										editable : false,
										formatter: formatBoolean,
										width: 80
 									},
									{
										label : 'RSVP Auto Response',
										name : 'autoResponseForRSVPAllowed',
										index : 'autoResponseForRSVPAllowed',
										formatter: formatBoolean,
										hidden : false,
										editable : false,
										width: 130
 									},{
										label : 'Code Length',
										name : 'groupEventInviteCodeLength',
										index : 'groupEventInviteCodeLength',
										hidden : false,
										editable : false,
										width: 80
									},
									{
										label : 'Group Label',
										name : 'memberCategoryCode',
										index : 'memberCategoryCode',
										hidden : true,
										editable : false,
										sortable : true,
										sorttype : 'string'
									},{
										label : 'Event Code',
										name : 'eventCode',
										index : 'eventCode',
										hidden : true,
										editable : false,
										width: 10
									} ],
							height : 'auto',
							rowNum : 20,
							rownumbers: true,
							rownumWidth : 25,
							width : 937,
							rowList : [ 100, 250, 500 ],
							pager : '#p'+gridId,
						//	sortname : 'firstName',
							autoencode : true,
							shrinkToFit : false,
							viewrecords : true,
							grouping : false,
							loadonce : true,
							subGrid : true,
							groupingView : {
								groupField : [ 'memberCategoryCode' ],
								groupColumnShow : [ false ],
								groupText : [ '<b>{0} - {1} Event(s)</b>' ]
							},
			                rowattr: function (rd) {
			                    if (!isValidDates(null, rd.eventDate)) {
			                    	return {"class": "ui-state-error"};
			                    }
		                    
			                    
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
								var groupEventCode = data.eventCode;
								//var dependetData = data.groupDependents;
								//var dependentObjects = JSON.parse(dependetData);
								pager_id = "p_" + subgrid_table_id;
								$("#" + subgrid_id)
										.html(
												"<table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div>");
								jQuery("#" + subgrid_table_id)
										.jqGrid(
												{
													url:"json/viewGroupEventPassCategories?groupEventCode="+groupEventCode+"&includeNotAvailableForPurchase=true",
													datatype : "json",													
													colNames : [ 'Action',
															'Ticket Display',
															'Purchase Name *',
															'Start Datetime', 
															'End Datetime',
															'# Sold',
															'Disable',
															'Display Price',
															'Pass Price',
															'Prefix',
															'Suffix',
															'Random Pass',
															'Total Passes',
															'Image URL',
															'Barcode URL',
															'Max Per Invite',
															'Purchase Group',
															'Display Order',
															'Header',
															'ID'],
													colModel : [
															 {
																name : 'myac',
																width : 60,
																fixed : true,
																sortable : false,
																resize : false,
																formatter : 'actions',
																formatoptions : {
																	keys : true,
																	editbutton: true,
																	delbutton:false,
																	editformbutton: true,
																	delOptions : {},
															editOptions : {
																savekey: [true, 13],
																url : "json/handlePassCategory?operation=update&groupEventCode="+groupEventCode,
																height : 550,
																width : 420,
																top : 50,
																left : 400,
																dataheight : 470,
																closeAfterEdit: true,
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
																name : "passCategoryName",
																index : "passCategoryName",
																width : 120,
																editable : true,
																editrules : {
																	required : false
																},
																editoptions:{maxlength:"23"}
															},
															{
																name : "passCategoryNameShort",
																index : "passCategoryNameShort",
																width : 140,
																editable : true,
																editrules : {
																	required : true
																},
																editoptions:{maxlength:"30"}
															},
															{
																name : "purchaseStartDateTime",
																index : "purchaseStartDateTime",
																width : 100,
																editable : true,
																editoptions : {
																	dataInit : datetimePick
																},
																formatter : formatDateTime,
																editable : true
															},
															{
																name : "purchaseExpiryDateTime",
																index : "purchaseExpiryDateTime",
																width : 100,
																editable : true,
																editoptions : {
																	dataInit : datetimePick
																},
																formatter : formatDateTime,
																editable : true
															},
															{
																label : '# Sold',
																name : "groupEventPassCategoryId",
																index: "groupEventPassCategoryId",
																width : 40,
																align: 'center',
																editable : false,
																hidden: false,
																formatter: loadSoldForEvent
															},
															{
																name : "disablePurchase",
																index : "disablePurchase",
																width : 50,
																editable : true,
																align: 'center',
																formatter : formatBoolean,
																edittype : "checkbox",
																editoptions : {
																	value : "Yes:No"
																}
															},
															{
																name : "displayPrice",
																index : "displayPrice",
																width : 50,
																editable : true,
																align: 'center',
																formatter : formatBoolean,
																edittype : "checkbox",
																editoptions : {
																	value : "Yes:No"
																}
															},
															{
																name : "passPrice",
																index : "passPrice",
																width : 60,
																editable : true,
																align: 'center',
																formatter: 'number',
																editrules : {
																	required : true,
																	number: true
																}
															},
															{
																name : "passPrefix",
																index : "passPrefix",
																width : 80,
																align: 'center',
																editable : true
															},
															{
																name : "passSuffix",
																index : "passSuffix",
																width : 80,
																align: 'center',
																editable : true
															},
															{
																name : "randomPassNumbers",
																index : "randomPassNumbers",
																width : 50,
																editable : true,
																align: 'center',
																formatter : formatBoolean,
																edittype : "checkbox",
																editoptions : {
																	value : "Yes:No"
																}
															},
															{
																name : "numberOfPasses",
																index : "numberOfPasses",
																width : 50,
																align: 'center',
																editable : true,
																editrules:{integer:true}
															},
															{
																name : "passImageURL",
																index : "passImageURL",
																width : 120,
																editable : true
															}
															,
															{
																name : "passBarocodeURL",
																index : "passBarocodeURL",
																width : 120,
																editable : true
															},
															{
																name : "maxPurchasePerInvite",
																index : "maxPurchasePerInvite",
																width : 50,
																editable : true,
																editrules:{integer:true},

																
															},
															{
																name : "memberOnlyPurchase",
																index : "memberOnlyPurchase",
																width : 80,
																editable : true,
																edittype : 'select',
																editoptions : {
																	dataUrl : 'json/viewGroupMemberCategories',
																	buildSelect : function(response) {
																		var options = '<select><optgroup label="Generic Categories">';
																		options+='<option value="">All Users</option>';
																		options+='<option value="ACTIVE">All Active Users</option>';
																		options+='<option value="INACTIVE">All In-Active Users</option></optgroup><optgroup label="Specific Categories">'
																		var j = $.parseJSON(response);
																		for (var i = 0; i < j.length; i++) {
																			options += '<option value="' + j[i].memberCategoryCode + '">'
																					+ j[i].memberCategoryName
																					+ '</option>';
																		}
																		options += '</optgroup></select>';
																		return options;
																	}
																}
																
															},
															{
																name : "displayOrder",
																index : "displayOrder",
																width : 50,
																editable : true,
																
																editrules : {
																	required : true,
																	number: true
																},
																formatter:'double'
															},
															{
																name : "passHeader",
																index : "passHeader",
																width : 100,
																editable : false,
																hidden: false
															},
															{
																name : "groupEventPassCategoryId",
																index : "groupEventPassCategoryId",
																width : 100,
																editable : true,
																hidden: true
															}

													],
													rowNum : 50,
													pager : pager_id,
													//	sortname: 'num',
													sortorder : "asc",
													rownumbers : true,
													rownumWidth : 25,
													height : '100%',
													editurl : "json/handlePassCategory?operation=update&groupEventCode="+groupEventCode,
													caption : "Pass Categories",
									                rowattr: function (rd) {
									                    if (rd.disablePurchase) {
									                    	return {"class": "ui-state-disabled"};
									                    }	
									                    if (!isValidDates(rd.purchaseStartDateTime, rd.purchaseExpiryDateTime)) {
									                    	return {"class": "ui-state-error"};
									                    }
								                    
									                    
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
												{del : false ,edit:false}, //options
												{}, // edit options
												{savekey: [true, 13],
													height : 550,
													width : 420,
													top : 50,
													left : 400,
													dataheight : 470,
													reloadAfterSubmit : true,
													url : "json/handlePassCategory?operation=add&groupEventCode="
														+ groupEventCode,
										            beforeShowForm: function(form) {
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
												{													height : 200,
													width : 720,
													top : 100,
													left : 400,
													dataheight : 100} // search options
										);
							},
							onSelectRow : function(id) {
							},
							editurl : "server.php",
							caption : captionVal
						});
		jQuery("#" + gridId).jqGrid('navGrid', "#p"+gridId, {
			edit : false,
			add : false,
			del : false
		});
		jQuery("#" + gridId).jqGrid('filterToolbar',{searchOperators : true});
	}


</script>
