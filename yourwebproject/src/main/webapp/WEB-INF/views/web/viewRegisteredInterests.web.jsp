<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="tabbable">
	<ul class="nav nav-tabs">
		<li class="active"><a href="#optionALL" data-toggle="tab">Registered Interests </a></li>
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

	</div>
</div>



<script type="text/javascript">
	$(document).ready(function() {
		loadGrid('gridALL','Registered Interests');

	});

	function loadGrid(gridId, captionVal) {
		jQuery("#" + gridId)
				.jqGrid(
						{
							url : 'json/viewRegisteredInterests',
							datatype : "json",
							colModel : [
										{
											label : 'Action',
											name : 'myac',
											width : 50,
											sortable : false,
											resize : false,
											formatter : 'actions',
											formatoptions : {
												keys : false
											}
										},
									{
										label : 'ID',
										name : 'id',
										index : 'id',
										sorttype: "string",
										hidden : true,
										editable : true
									},
									{
										label : 'First Name',
										name : 'firstName',
										index : 'firstName',
										width : 100,
										sorttype: "string",
										search: true,
										editable : false
									},
									{
										label : 'Last Name',
										name : 'lastName',
										index : 'lastName',
										sorttype: "string",
										//frozen : true,
										width : 100
										
									},
									{
										label : 'Email',
										name : 'primaryEmail',
										index : 'primaryEmail',
										sorttype: "string",
										width : 120,
										editable : false,
										editrules: {email: true}
									},
									{
										label : 'Phone Numbers',
										name : 'mobilephone',
										index : 'mobilephone',
										width : 90,
										sortable : false,
										sorttype: "string",
										editable : false
									},
									{
										label : 'Interest Type',
										name : 'interestType',
										index : 'interestType',
										width : 130,
										sortable : true,
										sorttype: "string",
										editable : false
									},
									{
										label : 'Completed',
										name : 'completed',
										index : 'completed',
										width : 70,
										//sorttype: "string",
										editable : true,
 										formatter: formatBoolean,
										edittype : "checkbox",
										editoptions : {
											value : "Yes:No"}, 		
									},
									{
										label : 'Campaign',
										name : 'campaign',
										index : 'campaign',
										width : 70,
										sortable : true,
										sorttype: "string",
										editable : false
									},
									{
										label : 'IP',
										name : 'requestIp',
										index : 'requestIp',
										width : 80,
										sortable : true,
										sorttype: "string",
										editable : false
									},
									{
										label : 'Created At',
										name : 'createdAt',
										index : 'createdAt',
										width : 100,
										//sorttype: "date",
										formatter: formatDateTimeMillis,
										hidden : false,
										editable : false
									},
									{
										label : 'Emailed',
										name : 'autoResponseEmailSent',
										index : 'autoResponseEmailSent',
										width : 60,
										editable : false,
										hidden: false,
 										formatter: formatBoolean
	
									}
									],
							height : 'auto',
							rowNum : 500,
							rownumbers: true,
							rownumWidth : 25,
							width : 937,
							rowList : [ 250, 500, 1000, 2000 ],
							pager : '#pgridALL',
							//sortname : 'createdAt',
							autoencode : true,
							shrinkToFit : false,
							viewrecords : true,
							//grouping : true,
							loadonce : true,
							onSelectRow : function(id) {
// 								if (id && id !== lastsel) {
// 									jQuery('#' + gridId).jqGrid('restoreRow',
// 											lastsel);
// 									jQuery('#' + gridId).jqGrid('editRow', id,
// 											true, pickdates);
// 									lastsel = id;
//								}
							},
							editurl : "json/updateRegisteredInterests",
							caption : captionVal
						});
		jQuery("#" + gridId).jqGrid('navGrid', "#pgridALL", {
			edit : false,
			add : false,
			del : false
		});
		jQuery("#" + gridId).jqGrid('filterToolbar',{searchOperators : false});
		//jQuery("#" + gridId).jqGrid('setFrozenColumns');
	}


</script>
