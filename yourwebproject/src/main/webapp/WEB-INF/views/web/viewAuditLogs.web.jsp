<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="tabbable">
	<ul class="nav nav-tabs">
		<li class="active"><a href="#optionALL" data-toggle="tab">Audit Logs</a></li>
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
		loadGrid('gridALL','Audit Logs');

	});

	function loadGrid(gridId, captionVal) {
		jQuery("#" + gridId)
				.jqGrid(
						{
							url : 'json/auditLog',
							datatype : "json",
							colModel : [
										
									{
										label : 'ID',
										name : 'id',
										index : 'id',
										sorttype: "string",
										hidden : true,
										editable : true
									},
									{
										label : 'Request URI',
										name : 'requestURI',
										index : 'requestURI',
										width : 240,
										sorttype: "string",
										search: true,
										editable : false,
										//frozen : true,
										searchoptions:{sopt:['eq','bw','bn','cn','nc','ew','en']}
									},
									{
										label : 'Request URL',
										name : 'requestURL',
										index : 'requestURL',
										sorttype: "string",
										//frozen : true,
										width : 250,
										hidden: true,
										editable : false,searchoptions:{sopt:['eq','bw','bn','cn','nc','ew','en']}
										
									},
									{
										label : 'Access Date',
										name : 'accessDate',
										index : 'accessDate',
										width : 120,
										//sorttype: "date",
										formatter: formatDateTimeMillis,
										hidden : false,
										editable : false
									},
								
									{
										label : 'Error',
										name : 'error',
										index : 'error',
										hidden : false,
										align: 'center',
										editable : false,
										width : 40,
										formatter: formatBoolean
									},
									{
										label : 'Session ID',
										name : 'sessionId',
										index : 'sessionId',
										width : 260,
										sortable : true,
										sorttype: "string",
										editable : false
									},
									{
										label : 'Method',
										name : 'method',
										index : 'method',
										width : 50,
										sortable : true,
										sorttype: "string",
										editable : false
									},
									{
										label : 'Time MS',
										name : 'executionTimeMillis',
										index : 'executionTimeMillis',
										align: 'center',
										width : 50,
										sortable : true,
										editable : false
									},
									{
										label : 'User',
										name : 'user.userName',
										index : 'user.userName',
										width : 70,
										sortable : true,
										sorttype: "string",
										editable : false
									},
									{
										label : 'Class',
										name : 'clazz',
										index : 'clazz',
										width : 120,
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
										label : 'Params',
										name : 'requestParams',
										index : 'requestParams',
										width : 330,
										sortable : true,
										sorttype: "string",
										editable : false
									},
									{
										label : 'User Agent',
										name : 'userAgent',
										index : 'userAgent',
										width : 500,
										sortable : true,
										sorttype: "string",
										editable : false
									},
									{
										label : 'Trace',
										name : 'errorTrace',
										index : 'errorTrace',
										width : 500,
										sortable : true,
										sorttype: "string",
										editable : false
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
							editurl : "",
							caption : captionVal
						});
		jQuery("#" + gridId).jqGrid('navGrid', "#pgridALL", {
			edit : false,
			add : false,
			del : false
		});
		jQuery("#" + gridId).jqGrid('filterToolbar',{searchOperators : true});
		//jQuery("#" + gridId).jqGrid('setFrozenColumns');
	}


</script>
