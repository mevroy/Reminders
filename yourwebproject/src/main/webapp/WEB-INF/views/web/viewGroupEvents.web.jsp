<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="row">
	<div class="span12">
		<!--  div class="hero-unit"> -->
				<h2>Group Events</h2>
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
										width: 100
									},
 									
									{
										label : 'Response Template',
										name : 'autoResponseRSVPTemplate',
										index : 'autoResponseRSVPTemplate',
										hidden : false,
										editable : false,
										width: 130
 									},
									{
										label : 'Group Label',
										name : 'memberCategoryCode',
										index : 'memberCategoryCode',
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
							rowList : [ 100, 250, 500 ],
							pager : '#p'+gridId,
						//	sortname : 'firstName',
							autoencode : true,
							shrinkToFit : false,
							viewrecords : true,
							grouping : true,
							loadonce : true,
							groupingView : {
								groupField : [ 'memberCategoryCode' ],
								groupColumnShow : [ false ],
								groupText : [ '<b>{0} - {1} Event(s)</b>' ]
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
