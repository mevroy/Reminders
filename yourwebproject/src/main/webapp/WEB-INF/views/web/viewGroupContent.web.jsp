<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


		<h2>Group Content Publishing</h2>
		<br />

		<div class="tabbable">
			<ul class="nav nav-tabs">
				<li class="active"><a href="#optionALL" data-toggle="tab">
						Group Content</a></li>
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
		loadGrid('gridALL', 'Group Data Content');
	});

	function loadGrid(gridId, captionVal) {
		//	alert("gridId-->"+gridId+" groupMemCatCode-->"+groupMemCatCode+" groupEventCode-->"+groupEventCode+" captionVal-->"+captionVal)
		jQuery("#" + gridId)
				.jqGrid(
						{
							url : 'json/listAvailableContent?includeExpired=true',
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
										label : 'Template Name',
										name : 'contentName',
										index : 'contentName',
										hidden : false,
										editable : true,
										editoptions: {maxlength: 100},
										width : 150,
										editrules : {
											required : true
										}
									},
									{
										label : 'Title',
										name : 'contentTitle',
										index : 'contentTitle',
										hidden : false,
										editable : true,
										width : 100,
										editoptions: {maxlength: 100},
										editrules : {
											required : false
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
										width: 100
									},
									{
										label : 'Expiry Date',
										name : 'expiryDate',
										index : 'expiryDate',
										hidden : false,
										editable : true,
										formatter : formatDateTime,
										width : 100,
										editoptions : {
											dataInit : datetimePick
										}
									},
									{
										label : 'HTML Content',
										name : 'pageContent',
										index : 'pageContent',
										hidden : false,
										editable : true,
										edittype:"textarea", 
										editoptions:{rows:"15",cols:"90"},
										width : 400,
										editrules : {
											required : true
										}
									}, {
										label : 'Updated Date',
										name : 'updatedAt',
										index : 'updatedAt',
										hidden : false,
										editable : false,
										width : 80,
										formatter : formatDateTime
									}, {
										label : 'ID',
										name : 'contentId',
										index : 'contentId',
										hidden : true,
										editable : true
									} ],
							height : 'auto',
							rowNum : 50,
							rownumbers : true,
							rownumWidth : 25,
							width : 937,
							rowList : [50, 100, 150, 250 ],
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
							editurl : "handleGroupContent?operation=update",
							caption : captionVal
						});
		jQuery("#" + gridId).jqGrid('navGrid', "#p" + gridId, {
			del : false
		}, //options
		{
			height : 550,
			width : 880,
			top : 80,
			left : 200,
			dataheight : 450,
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
			url: 'handleGroupContent?operation=add',
			height : 550,
			width : 880,
			top : 80,
			left : 200,
			dataheight : 450,
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