<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


		<h2>Scanning Devices</h2>
		<br />

		<div class="tabbable">
			<ul class="nav nav-tabs">
				<li class="active"><a href="#optionALL" data-toggle="tab">
						Device List</a></li>
			</ul>
			<div class="tab-content">

				<div class="tab-pane active" id="optionALL">

							<table id="gridALL"></table>
							<div id="pgridALL"></div>

				</div>
			</div>
		</div>


<script type="text/javascript">
	$(document).ready(function() {
		loadGrid('gridALL', 'Group Scanner List');
	});

	function loadGrid(gridId, captionVal) {
		//	alert("gridId-->"+gridId+" groupMemCatCode-->"+groupMemCatCode+" groupEventCode-->"+groupEventCode+" captionVal-->"+captionVal)
		jQuery("#" + gridId)
				.jqGrid(
						{
							url : 'json/viewGroupScannerDevices',
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
// 											keys : false,
// 											editbutton : false,
// 											delbutton:false,
// 											delOptions : {
// 												url: 'handleGroupContent?operation=delete',
// 												height : 150,
// 												width : 400,
// 												top : 400,
// 												left : 250,
// 												dataheight : 70,
// 												reloadAfterSubmit : true,
// 												afterSubmit : function(response, postdata) {
// 													var status = false;
// 													if (response.responseText === "success")
// 														status = true
// 													return [ status, response.responseText ];
// 												}
// 												}
// 										}
// 									},
{
										label : 'ID',
										name : 'id',
										index : 'id',
										hidden : true,
										editable : true,
										width : 10
									},
									{
										label : 'Device UUID',
										name : 'deviceUuid',
										index : 'deviceUuid',
										hidden : false,
										editable : false,
										width : 245
									},
									{
										label : 'Device Name',
										name : 'deviceName',
										index : 'deviceName',
										hidden : false,
										editable : true,
										width : 100,
										editoptions: {maxlength: 100},
										editrules : {
											required : false
										}
									},
									{
										label : 'Access Start Date',
										name : 'accessStartDate',
										index : 'accessStartDate',
										hidden : false,
										editable : true,
										formatter : formatDateTime,
										editoptions : {
											dataInit : datetimePick
										},
										width: 100
									},
									{
										label : 'Access End Date',
										name : 'accessEndDate',
										index : 'accessEndDate',
										hidden : false,
										editable : true,
										formatter : formatDateTime,
										width : 100,
										editoptions : {
											dataInit : datetimePick
										}
									},									{
										label : 'Last Access Date',
										name : 'lastAccessDate',
										index : 'lastAccessDate',
										hidden : false,
										editable : false,
										formatter : formatDateTime,
										width : 100,
										editoptions : {
											dataInit : datetimePick
										}
									},
									{
										label : 'Device Owner',
										name : 'deviceOwner',
										index : 'deviceOwner',
										hidden : false,
										editable : true,
										width : 200
									}, {
										label : 'Updated Date',
										name : 'updatedAt',
										index : 'updatedAt',
										hidden : false,
										editable : false,
										width : 80,
										formatter : formatDateTime
									}, {
										label : 'Created Date',
										name : 'createdAt',
										index : 'createdAt',
										hidden : false,
										editable : false,
										width : 80,
										formatter : formatDateTime
									} ],
							height : 'auto',
							rowNum : 30,
							rownumbers : true,
							rownumWidth : 25,
							width : 918,
							rowList : [1, 10, 25, 50 ],
							pager : '#p' + gridId,
							//	sortname : 'firstName',
							autoencode : false,
							shrinkToFit : false,
							viewrecords : true,
							//grouping : false,
							gridview: true,
							loadonce : false,
							onSelectRow : function(id) {
							},
							editurl : "handleDeviceUpdates?operation=update",
							caption : captionVal
						});
		jQuery("#" + gridId).jqGrid('navGrid', "#p" + gridId, {
			del : false
		}, //options
		{
			height : 240,
			width : 440,
			top : 200,
			left : 470,
			dataheight : 150,
			reloadAfterSubmit : true,
			closeAfterEdit: true,
			afterSubmit : function(response, postdata) {
				var status = false;
				if (response.responseText === "success")
					status = true
				return [ status, response.responseText ];
			}
		}, // edit options
		{
			url: 'handleDeviceUpdates?operation=add',
			height : 240,
			width : 440,
			top : 200,
			left : 470,
			dataheight : 150,
			reloadAfterSubmit : true,
			closeAfterAdd: true,
			afterSubmit : function(response, postdata) {
				var status = false;
				if (response.responseText === "success")
					status = true
				return [ status, response.responseText ];
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
</script>