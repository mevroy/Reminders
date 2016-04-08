<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="row">
	<div class="span12">
		<!-- div class="hero-unit">  -->
		<form method="post" id="groupEventInvite" name="groupEventInvite">
			<fieldset>


				<h2>Create Group Event Invite List</h2>
				<br />



				<div class="span5">
					<div class="control-group" id="memberCategoryCodeCtl">
						<label class="control-label" for="memberCategoryCode">Member
							Category Code</label>

						<div class="controls">

							<select name="memberCategoryCode" class="input-xlarge"
								id="memberCategoryCode"
								onchange="loadDiv(); buildGroupEventsOptionsByMemberCategory(this.value, 'groupEventCode');">
								<option value="">Select One</option>
							</select>
						</div>
					</div>
					<div class="control-group" id="groupEventCodeCtl">
						<label class="control-label" for="groupEventCode">Group
							Event Code</label>

						<div class="controls">

							<select name="groupEventCode" class="input-xlarge"
								id="groupEventCode" onchange="loadDiv(); loadGrid('grid',$('select#memberCategoryCode option').filter(':selected').val() , this.value,'Un-Invited Group Members for '+$('select#groupEventCode option').filter(':selected').text());">
								<option value="">Select One</option>
							</select>
						</div>
					</div>
				</div>
				<div class="span5">


					<div class="control-group" id="inviteDateCtl">
						<label class="control-label" for="groupDates">Invite
							Validity Dates</label>
						<div class="controls">
							<div class="input-daterange input-group" id="datepicker">
								<input type="text" name="inviteStartDate"
									class="form-control input-small input-append"
									id="inviteStartDate" placeholder="Start Date" />&nbsp;<span
									class="input-prepend add-on"><i class="icon-calendar"></i></span>
								<span><i>&nbsp;&nbsp;&nbsp;TO</i></span> <input type="text"
									name="inviteExpiryDate"
									class="form-control input-small input-append"
									id="inviteExpiryDate"
									placeholder="Expiry Date after which invites expire" />&nbsp;<span
									class="input-prepend add-on"><i class="icon-calendar"></i></span>

							</div>
						</div>
					</div>
					<div class="control-group" id="inviteHeldCtl">
						<label class="control-label" for="inviteHeld">Invite Held</label>

						<div class="controls">
							<input type="radio" name="inviteHeld" class="input-xlarge"
								id="inviteHeld1" value="true" /> YES&nbsp;&nbsp; <input
								type="radio" name="inviteHeld" class="input-xlarge"
								id="inviteHeld2" value="false" checked/> NO
						</div>
					</div>

					<!-- input class="btn btn-primary btn-large" type="submit"
							value="SUBMIT" /> <a href="${pageContext.request.contextPath}/"
							class="btn btn-large">CANCEL</a>  -->

				</div>
			</fieldset>

		</form>

				<button class="btn btn-primary btn-small has-spinner"  type="submit" value="SUBMIT ALL" id="sendAll">SUBMIT ALL</button> 
				<button class="btn btn-primary btn-small has-spinner" type="submit" value="SUBMIT SELECTED" id="sendSelected">SUBMIT SELECTED</button>
		<div class="gridDiv">
			<table id="grid"></table>
			<div id="pgrid"></div>
		</div>
		<!-- /div>  -->
	</div>
</div>

<script type="text/javascript">
	var gridDiv = "";
	var lastsel;
	$(document).ready(
			function() {
				gridDiv = $("div.gridDiv").html();

				sendData = function(data, groupEventCode, memberCategoryCode, inviteStartDate, inviteExpiryDate, inviteHeld,selector) {
					toggleButtonWithId(selector);
					var dataToSend = JSON.stringify(data);

					$.ajax({
						type : "POST",
						url : "json/saveGroupEventInvite?groupEventCode="
								+ groupEventCode + "&memberCategoryCode="
								+ memberCategoryCode+"&inviteStartDate="+inviteStartDate+"&inviteExpiryDate="+inviteExpiryDate+"&inviteHeld="+inviteHeld,
						//dataType : "json",
						data : dataToSend,
						contentType : "application/json; charset=utf-8",
						success : function(response, textStatus, jqXHR) {
							// remove error div if exist
							loadMessageModal("Message", response);
							loadDiv();
							loadGrid('grid', memberCategoryCode, groupEventCode,
									'Updated Invite List');
							resetButtonWithId(selector);
						},
						error : function(jqXHR, textStatus, errorThrown) {
							loadMessageModal("Error", jqXHR.responseText+textStatus);
							resetButtonWithId(selector);
						}
					});
				};

				$("#groupEventInvite").validate(
						{
							rules : {
								groupEventCode : {
									required : true
								},
								inviteStartDate : {
									dateITA : true
								},
								inviteExpiryDate : {
									dateITA : true
								}

							},
							errorClass : "control-group error",
							validClass : "control-group success",
							errorElement : "span",
							highlight : function(element, errorClass,
									validClass) {
								if (element.type === 'radio') {
									this.findByName(element.name).parent("div")
											.parent("div").removeClass(
													validClass).addClass(
													errorClass);
								} else {
									$(element).parent("div").parent("div")
											.removeClass(validClass).addClass(
													errorClass);
								}
							},
							unhighlight : function(element, errorClass,
									validClass) {
								if (element.type === 'radio') {
									this.findByName(element.name).parent("div")
											.parent("div").removeClass(
													errorClass).addClass(
													validClass);
								} else {
									$(element).parent("div").parent("div")
											.removeClass(errorClass).addClass(
													validClass);
								}
							}
						});


			});

	function loadDiv() {

		$("div.gridDiv").html(gridDiv);
	}
	function loadGrid(gridId, groupMemCatCode, groupEventCode,captionVal) {
		//	alert("gridId-->"+gridId+" groupMemCatCode-->"+groupMemCatCode+" captionVal-->"+captionVal)
		jQuery("#" + gridId)
				.jqGrid(
						{
							url : 'json/viewUnInvitedGroupMembers/'+groupEventCode+'/' + groupMemCatCode,
							datatype : "json",
							colModel : [
									{
										label : 'Serial No',
										name : 'serialNumber',
										index : 'serialNumber',
										hidden : true,
										editable : false
									},
									{
										label : 'First Name',
										name : 'firstName',
										index : 'firstName',
										width : 100,
										sorttype : "string",
										search : true,
										editable : true,
										searchoptions:{sopt:['bw','eq','bn','cn','nc','ew','en']}
									},
									{
										label : 'Last Name',
										name : 'lastName',
										index : 'lastName',
										sorttype : "string",
										width : 100,
										editable : true,
										searchoptions:{sopt:['bw','eq','bn','cn','nc','ew','en']}

									},
									{
										label : 'Alias',
										name : 'aliasName',
										index : 'aliasName',
										width : 120,
										editable : true,
										sorttype : "string",
										searchoptions:{sopt:['bw','eq','bn','cn','nc','ew','en']}
									},
									{
										label : 'Email',
										name : 'primaryEmail',
										index : 'primaryEmail',
										sorttype : "string",
										width : 120,
										editable : true
									},
									{
										label : 'Phone Numbers',
										name : 'mobilephone',
										index : 'mobilephone',
										width : 100,
										sortable : false,
										sorttype : "string",
										editable : true
									},
									{
										label : 'Adults',
										name : 'adultCount',
										index : 'adultCount',
										width : 50,
										sortable : false,
										editable : true,
										sorttype : "int",
										formatter : "integer",
										summaryType : 'sum',
										summaryTpl : '<b>Total: {0}</b>'
									},
									{
										label : 'Kids',
										name : 'kidCount',
										index : 'kidCount',
										width : 50,
										sortable : false,
										editable : true,
										sorttype : "int",
										formatter : "integer",
										summaryType : 'sum',
										summaryTpl : '<b>Total: {0}</b>'
									},
									{
										label : 'Membership Start Date',
										name : 'membershipStartDate',
										index : 'membershipStartDate',
										width : 100,
										sortable : true,
										editable : true,
										searchoptions:{sopt:['bw','eq','bn','cn','nc','ew','en']},
										sorttype : "date",
										formatter : formatDate
									//	,
									//			formatoptions: { srcformat: "m/d/Y", newformat: "d/m/Y" }

									},
									{
										label : 'Membership End Date',
										name : 'membershipEndDate',
										index : 'membershipEndDate',
										width : 100,
										sortable : true,
										editable : true,
										sorttype : "date",
										searchoptions:{sopt:['bw','eq','bn','cn','nc','ew','en']},
										formatter : formatDate
									//	,
									//			formatoptions: { srcformat: "m/d/Y", newformat: "d/m/Y" }

									}
									,
									{
										label : 'Email verified',
										name : 'primaryEmailVerified',
										index : 'primaryEmailVerified',
										sorttype: "string",
										hidden : false,
										editable : true,
										edittype : "checkbox",
										formatter: formatBoolean,
										editoptions : {
											value : "Yes:No"
										}
									}
									, {
										label : 'Paid member',
										name : 'paidMember',
										index : 'paidMember',
										width : 90,
										sortable : true,
										editable : true,
										formatter: formatBoolean,
										edittype : "checkbox",
										editoptions : {
											value : "Yes:No"
										}
									} ],
							height : 'auto',
							rowNum : 500,
							rownumbers : true,
							rownumWidth : 25,
							width : 937,
							rowList : [ 300, 500, 1000 ],
							pager : '#pgrid',
							multiselect : true,
							//	sortname : 'firstName',
							autoencode : true,
							shrinkToFit : false,
							viewrecords : true,
							//grouping : true,
							loadonce : true,
							groupingView : {
								groupField : [ 'memberCategoryCode' ],
								groupColumnShow : [ false ],
								groupText : [ '<b>{0} - {1} Members(s)</b>' ]
							},
							onSelectRow : function(id) {
// 								if (id && id !== lastsel) {
// 									jQuery('#' + gridId).jqGrid('restoreRow',
// 											lastsel);
// 									jQuery('#' + gridId).jqGrid('editRow', id,
// 											true);
// 									lastsel = id;
// 								}
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

	$(function() {

		$("#sendAll")
				.click(
						function() {
							var localGridData = jQuery("#grid").jqGrid(
									'getGridParam', 'data');

							var selectedMemberCategoryCode = $('select#memberCategoryCode option').filter(':selected').val();
							var selectedgroupEventCode = $('select#groupEventCode option').filter(':selected').val();
							var inviteStartDate = $("#inviteStartDate").val();
							var inviteExpiryDate = $("#inviteExpiryDate").val();
							var inviteHeld = $(
									"input:radio[name=inviteHeld]:checked")
									.val();
							if (selectedgroupEventCode === '') {
								confirm("Please select a Event for which invites need to be created!");
								return false;
							}

							sendData(localGridData, selectedgroupEventCode,
									selectedMemberCategoryCode, inviteStartDate, inviteExpiryDate, inviteHeld,'sendAll');
						});
		$("#sendSelected")
				.click(
						function() {
							var selectedMemberCategoryCode = $('select#memberCategoryCode option').filter(':selected').val();
							var selectedgroupEventCode = $('select#groupEventCode option').filter(':selected').val();
							var inviteStartDate = $("#inviteStartDate").val();
							var inviteExpiryDate = $("#inviteExpiryDate").val();
							var inviteHeld = $(
									"input:radio[name=inviteHeld]:checked")
									.val();
							if (selectedgroupEventCode === '') {
								confirm("Please select a Event for which invites need to be created!");
								return false;
							}
							var localGridData = jQuery("#grid").jqGrid(
									'getGridParam', 'data'), idsToDataIndex = jQuery(
									"#grid").jqGrid('getGridParam', '_index'), selRows = jQuery(
									"#grid")
									.jqGrid('getGridParam', 'selarrrow'), dataToSend = [], i, l = selRows.length;
							for (i = 0; i < l; i++) {
								var x = localGridData[idsToDataIndex[selRows[i]]];
								//	delete x['_id_'];
								dataToSend.push(x);
							}
							if (dataToSend.length === 0) {
								confirm("Please select atleast one checkbox to proceed!");
								return false;
							}
						
							//alert(groupEventCode+" "+ memberCategoryCode+" "+ inviteStartDate+" "+ inviteExpiryDate+" "+ inviteHeld);
							sendData(dataToSend, selectedgroupEventCode,
									selectedMemberCategoryCode, inviteStartDate, inviteExpiryDate, inviteHeld,'sendSelected');
						});

		$('.input-group.date').datepicker({
			format : "dd/mm/yyyy"
		});
		$('.input-daterange').datepicker({
			format : "dd/mm/yyyy"
		});

		buildGroupMemberCategoriesOptions('memberCategoryCode');
		buildGroupEventsOptionsByMemberCategory("NULL", "groupEventCode");
	});
</script>
