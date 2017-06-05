
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="row">
	<div class="span12">
		<!--  div class="hero-unit"> -->
		<h2>Group Interests</h2>
		<br />

		<div class="tabbable">
			<ul class="nav nav-tabs">
				<li class="active"><a href="#optionALL" data-toggle="tab">All
						Group Interests</a></li>
			</ul>
			<div class="tab-content">

				<div class="tab-pane active" id="optionALL">
					<!-- <div class="row">
						<div class="span12">  -->
							<!-- div class="hero-unit">  -->
							<table id="gridALL"></table>
							<div id="pgridALL"></div>
							<!-- /div>  -->
					<!-- 	</div>
					</div>  -->
				</div>
			</div>
		</div>
		<!-- /div>  -->
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		loadGrid('gridALL', 'Group Interests');
	});

	function loadGrid(gridId, captionVal) {
		//	alert("gridId-->"+gridId+" groupMemCatCode-->"+groupMemCatCode+" groupEventCode-->"+groupEventCode+" captionVal-->"+captionVal)
		jQuery("#" + gridId)
				.jqGrid(
						{
							url : 'json/viewGroupInterests?includeAll=true',
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
// 											keys : false
// 										}
// 									},
									{
										label : 'Interest Type',
										name : 'interestType',
										index : 'interestType',
										hidden : false,
										editable : true,
										width : 200,
										editrules : {
											required : true
										}
									},
									{
										label : 'Start Date',
										name : 'startDate',
										index : 'startDate',
										hidden : false,
										editable : true,
										formatter : formatDateTime,
										editoptions : {
											dataInit : datetimePick
										},
										width: 150
									},
									{
										label : 'Expiry Date',
										name : 'expiryDate',
										index : 'expiryDate',
										hidden : false,
										editable : true,
										formatter : formatDateTime,
										width : 150,
										editoptions : {
											dataInit : datetimePick
										}
									},
									{
										label : 'Description',
										name : 'interestDescription',
										index : 'interestDescription',
										hidden : false,
										editable : true,
										width : 200
									},
									{
										label : 'Auto Response',
										name : 'sendAutoResponse',
										index : 'sendAutoResponse',
										edittype : "checkbox",
										formatter : formatBoolean,
										editoptions : {
											value : "Yes:No"
										},
										hidden : false,
										editable : true,
										width : 100
									},
									{
										label : 'Event',
										name : 'eventCode',
										index : 'eventCode',
										hidden : false,
										edittype : 'select',
										editoptions : {
											dataUrl : 'json/viewGroupEvents?memberCategoryCode=NULL',
											buildSelect : function(response) {
												var options = '<select><option value="">Select One</option>"';
												

													$.each($.parseJSON(response), function(j, option) {
														options += '<option value="' + option.value + '">'
																+ option.label + '</option>';
																
													});
												
												options += '</select>';
												return options;
											}
										},
										editable : true,
										width : 130
									},
									{
										label : 'Event Resp. Template',
										name : 'autoResponseTemplate',
										index : 'autoResponseTemplate',
										hidden : false,
										edittype : 'select',
										editoptions : {
											dataUrl : 'json/viewGroupEmailTemplates?groupEventCode=ALL',
											buildSelect : function(response) {
												var fullSelect = '<select><option value="">Select One</option>';
												var optGroup = '';
									            $.each($.parseJSON(response), function(groupName, options) {
									                optGroup += '<optgroup label="'+groupName+'">';
									                var optionEach = '';
									                $.each(options, function(j, option) {
									                	optionEach +=  '<option value="' + option.value + '">'
														+ option.label
														+ '</option>';
									                });
									                optGroup += optionEach+'</optgroup>'
									            });
									            
									            fullSelect += optGroup+'</select>';
												return fullSelect;
											}
										},
										editable : true,
										width : 130
									},
									{
										label : 'Default Template',
										name : 'defaultResponseTemplate',
										index : 'defaultResponseTemplate',
										hidden : false,
										edittype : 'select',
										editoptions : {
											dataUrl : 'json/viewGroupEmailTemplates?groupEventCode=ALL',
											buildSelect : function(response) {
												var fullSelect = '<select><option value="">Select One</option>';
												var optGroup = '';
									            $.each($.parseJSON(response), function(groupName, options) {
									                optGroup += '<optgroup label="'+groupName+'">';
									                var optionEach = '';
									                $.each(options, function(j, option) {
									                	optionEach +=  '<option value="' + option.value + '">'
														+ option.label
														+ '</option>';
									                });
									                optGroup += optionEach+'</optgroup>'
									            });
									            
									            fullSelect += optGroup+'</select>';
												return fullSelect;
											}
										},
										editable : true,
										width : 130
									},
									{
										label : 'URL',
										name : 'url',
										index : 'url',
										hidden : false,
										editable : false,
										width : 130,
										formatter: viewLink
									},
									{
										label : 'Group Label',
										name : 'memberCategoryCode',
										index : 'memberCategoryCode',
										hidden : false,
										editable : true,
										sortable : true,
										edittype : 'select',
										editoptions : {
											dataUrl : 'json/viewGroupMemberCategories',
											buildSelect : function(response) {
												var options = '<select><option value="">Select One</option>';
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
									}, {
										label : 'Group Code',
										name : 'groupCode',
										index : 'groupCode',
										hidden : true,
										editable : true
									}, {
										label : 'ID',
										name : 'id',
										index : 'id',
										hidden : true,
										editable : true
									} ],
							height : 'auto',
							rowNum : 20,
							rownumbers : true,
							rownumWidth : 25,
							width : 937,
							rowList : [ 100, 250, 500 ],
							pager : '#p' + gridId,
							//	sortname : 'firstName',
							autoencode : true,
							shrinkToFit : false,
							viewrecords : true,
							grouping : false,
							loadonce : true,
							gridview: true,
							groupingView : {
								groupField : [ 'memberCategoryCode' ],
								groupColumnShow : [ true ],
								groupText : [ '<b>{0} - {1} Event(s)</b>' ]
							},
							onSelectRow : function(id) {
							},
							editurl : "json/updateGroupInterests",
							caption : captionVal
						});
		jQuery("#" + gridId).jqGrid('navGrid', "#p" + gridId, {
			del : false
		}, //options
		{
			height : 450,
			width : 420,
			top : 100,
			left : 400,
			dataheight : 300,
			reloadAfterSubmit : false,
			afterSubmit : function(response, postdata) {
				var status = false;
				if (response.responseText === "success")
					status = true
				return [ status, "Problem saving the record!" ];
			}
		}, // edit options
		{
			height : 450,
			width : 420,
			top : 100,
			left : 200,
			dataheight : 300,
			reloadAfterSubmit : false,
			afterSubmit : function(response, postdata) {
				var status = false;
				if (response.responseText === "success")
					status = true
				return [ status, "Problem saving the record!" ];
			}
		}, // add options
		{}, // del options
		{
			height : 200,
			width : 720,
			top : 100,
			left : 400,
			dataheight : 100
		} // search options
		);
		jQuery("#" + gridId).jqGrid('filterToolbar', {
			searchOperators : true
		});
	}
	function viewLink(cellValue, options, rowdata, action)
	{
		return "<a href='${pageContext.request.contextPath}/registerInterest?interestType="+rowdata.interestType+"&groupCode="+rowdata.groupCode+"&campaign=&serialNumber=' >"+rowdata.interestType+"</a>";
	}
</script>
