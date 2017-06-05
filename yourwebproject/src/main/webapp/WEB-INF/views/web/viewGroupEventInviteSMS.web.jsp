<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="row">
	<div class="span12">
		<!--  div class="hero-unit"> -->
		<form:form commandName="groupEventInvite" action="" method="post"
			id="groupEventInvite">
			<fieldset>


				<h2>Group Event SMS</h2>
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
								id="groupEventCode" onclick="addNullSelectOption('groupEventCode');"
								onchange="loadDiv();loadGrid('grid',$('select#memberCategoryCode option').filter(':selected').val(),this.value,'Invite SMS List');">
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
		</div>
		<!-- /div>  -->
	</div>
</div>

<script type="text/javascript">
	var gridDiv = "";

	function loadDiv() {

		$("div.gridDiv").html(gridDiv);
	}
	function loadGrid(gridId, groupMemCatCode, groupEventCode, captionVal) {

		//	alert("gridId-->"+gridId+" groupMemCatCode-->"+groupMemCatCode+" groupEventCode-->"+groupEventCode+" captionVal-->"+captionVal)
		jQuery("#" + gridId).jqGrid(
				{
					url : 'json/viewGroupSMS/' + groupEventCode + '/'
							+ groupMemCatCode,
					datatype : "json",
					colModel : [{
						label : 'SMS ID',
						name : 'providerMessageId',
						index : 'providerMessageId',
						hidden : true,
						editable : true,
						width : 1
					}, {
						label : 'First Name',
						name : 'groupMember.firstName',
						index : 'groupMember.firstName',
						hidden : false,
						editable : false,
						width : 120
					}, {
						label : 'Last Name',
						name : 'groupMember.lastName',
						index : 'groupMember.lastName',
						hidden : false,
						editable : false,
						width : 120
					}, {
						label : 'Message',
						name : 'body',
						index : 'body',
						hidden : false,
						editable : false,
						searchoptions:{sopt:['cn','eq','bn','bw','nc','ew','en']},
						width : 200
					}, {
						label : 'SMS Held',
						name : 'smsHeld',
						index : 'smsHeld',
						width : 70,
						align: 'center',
						sortable : true,
						editable : false,
						hidden : false,
						searchoptions:{sopt:['bw','eq','bn','cn','nc','ew','en']},
						formatter: function(cellValue, options, rowObject) {
							//If the rowObject picks a different value other than the current 'name' then it will not work. for e.g here you are fetching groupEmailId under emailHeld. It will work if you defined a hidden attrbute of groupEmailId.
						    var smsId = rowObject.providerMessageId;
							var template = "<div class='popover' style='max-width: 800px;'  role='tooltip'><div class='arrow'></div><h3 class='popover-title'></h3><div class='popover-content'><span class='spinner active'><i class='icon-spin icon-repeat icon-play-circle'></i></span> Loading..</div></div>";
							var linkattr = '<a tabindex="0" data-template="'+template+'" data-container = "body" data-animation="true" role="button" class="btn btn-mini btn-warning" popover-placement="top"  data-toggle="popover" data-trigger="focus" title="SMS Replies"  data-smsid="'+smsId+'">'+formatBoolean(rowObject.smsHeld)+'</a>';
							return linkattr;
						    }	

					},  {
						label : 'SMS Date',
						name : 'smsedDate',
						index : 'smsedDate',
						width : 130,
						sortable : true,
						editable : true,
						searchoptions:{sopt:['bw','eq','bn','cn','nc','ew','en']},
						//sorttype : "date",
						formatter : formatDateTime,


					}, {
						label : 'SMS Scheduled Date',
						name : 'smsingDate',
						index : 'smsingDate',
						width : 100,
						sortable : true,
						editable : true,
						searchoptions:{sopt:['bw','eq','bn','cn','nc','ew','en']},
						//sorttype : "date",
						formatter : formatDateTime

					},
					{
						label : 'Errors',
						name : 'smsError',
						index : 'smsError',
						hidden : false,
						editable : false,
						width : 220
					},
					
					// 									{
					// 										label : 'Invite Cancelled',
					// 										name : 'inviteCancelled',
					// 										index : 'inviteCancelled',
					// 										hidden : false,
					// 										editable : true,
					// 										edittype : "checkbox",
					// 										editoptions : {
					// 											value : "true:false"}
					// 									},
					{
						label : 'Group Label',
						name : 'groupMember.serialNumber',
						index : 'groupMember.serialNumber',
						hidden : true,
						editable : false,
						sortable : true,
						sorttype : 'string'
					}
					],
					height : 'auto',
					rowNum : 2000,
					rownumbers : true,
					rownumWidth : 25,
					width : 937,
					rowList : [ 1000, 2000, 5000, 10000 ],
					pager : '#pgrid',
					sortname : 'groupMember.firstName',
					autoencode : true,
					shrinkToFit : false,
					viewrecords : true,
					//grouping : true,
					loadonce : true,
					groupingView : {
						groupField : [ 'groupMember.firstName' ],
						groupColumnShow : [ false ],
						groupText : [ '<b>{0} - {1} SMS(es)</b>' ],
						groupDataSorted: true
					},
					onSelectRow : function(id) {
						// 								if (id && id !== lastsel) {
						// 									jQuery('#' + gridId).jqGrid('restoreRow',
						// 											lastsel);
						// 									jQuery('#' + gridId).jqGrid('editRow', id,
						// 											true, pickdates);
						// 									lastsel = id;
						// 								}
					},
					loadComplete : function () {
						 $('[data-toggle="popover"]').popover({
							    html: true,
							    trigger: 'focus',
							  content: loadSMSActivity,
							  }).click(function(e) {
							  // $(this).popover('toggle');
							  });
					},
					editurl : "server.php",
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

	var appendedNullValue = false;
	function addNullSelectOption(htmlSelectID)
	{
		if(!appendedNullValue)
		{
			$("select#"+htmlSelectID).append('<option value="NULL">Show Unassigned</option>');
		appendedNullValue = true;}
		
	}
	$(function() {
		buildGroupMemberCategoriesOptions('memberCategoryCode');
		buildGroupEventsOptionsByMemberCategory("NULL", "groupEventCode");
		//addNullSelectOption("groupEventCode");
		gridDiv = $("div.gridDiv").html();
	});
</script>
