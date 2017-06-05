<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${groupMember.serialNumber ne null}" >
<c:set var="warning" value="warning"></c:set>
</c:if>
<div class="row">
	<div class="span12">
<div class="alert alert-warning" style="padding: 10px;">
		<fieldset>
			

			<form action="scanGroupMember" method="POST">
				<div class="span12">
					<div class="control-group" id="valueCtl">
						<label class="control-label" for="value"><b><h3>ID for "${groupEvent.eventName}"</h3></b></label>

						<div class="controls">
							<input type="text" class="input-xlarge" name="value" id="value" autofocus="autofocus"
								placeholder="Enter Member ID or Event Code " value="" /><input type="hidden" name="groupEventCode" id="groupEventCode" value="${groupEvent.eventCode}"> <input class="input-append btn btn-warning btn-large" type="submit"
					value="SEARCH" /> 
									<a	href="#" onclick="loadModal2(''); 	loadMemberGrid('gridALL', 'ALL', 'All Members');" class="input-append btn btn-success btn-large">MEMBERS LOOK UP</a>
								<c:if test="${groupMember.serialNumber ne null}">
				<a onclick="window.open('${pageContext.request.contextPath}/scan/MembershipPrintTemplate/${groupMember.serialNumber}','printwindow','toolbar=no , location=no , width=600, height=600, id=printer, top=100, left=400')" class="input-append btn btn-warning btn-large" >PRINT</a> 
				</c:if>
							<!-- 	<c:if test="${groupMember.membershipIdentifier ne null}">
			<img title="${groupMember.membershipIdentifier}" width="100" alt="${groupMember.membershipIdentifier}" src="https://chart.googleapis.com/chart?chs=150x150&cht=qr&chl=${groupMember.membershipIdentifier}">
		</c:if>  -->
						</div>
					</div>



				</div>
			</form>
			</fieldset>
</div>
		
		
<div class="tabbable">
    <ul class="nav nav-tabs">
        <li class="active"><a href="#option1" data-toggle="tab">Member Details <c:if test="${groupMember.membershipIdentifier ne null}"><b>(${groupMember.membershipIdentifier})</b></c:if> </a></li>
        <li><a href="#option2" data-toggle="tab" onclick="loadGrid('grid','Invite Passes List');">Invite Details</a></li>
        <li><a href="#option3" data-toggle="tab">RSVP</a></li> <li><a href="#option4" data-toggle="tab">Notifications</a></li>
    </ul>
    <div class="tab-content">
        <div class="tab-pane active" id="option1">
           				<div class="hero-unit" style="padding-top: 20px; padding-bottom: 10px;">
			<form:form commandName="groupMember" action="saveScannedGroupMember"
				method="post" id="groupMember">

				<fieldset class="control-group ${warning}">

					<div class="span3">
						<div class="control-group" id="firstNameCtl">
							<label class="control-label" for="firstName">First Name</label>

							<div class="controls">
								<form:input path="firstName" cssClass="input-medium"
									id="firstName" placeholder="Enter First Name" />
							</div>
						</div>
						<div class="control-group" id="lastNameCtl">
							<label class="control-label" for="lastName">Last Name</label>

							<div class="controls">
								<form:input path="lastName" cssClass="input-medium"
									id="lastName" placeholder="Enter Last Name" />
							</div>
						</div>
						<div class="control-group" id="primaryEmailCtl">
							<label class="control-label" for="primaryEmail">Email</label>

							<div class="controls">

								<form:input path="primaryEmail" cssClass="input-medium"
									id="primaryEmail" placeholder="Enter Email" />
							</div>
						</div>
						</div>
						<div class="span3">
							<div class="control-group" id="groupCodeCtl">
								<label class="control-label" for="groupCode">Group Code</label>

								<div class="controls">

									<form:input path="groupCode" cssClass="input-medium"
										id="groupCode" placeholder="Enter Group Code" readonly="true" />
								</div>
							</div>
						<div class="control-group" id="memberCategoryCodeCtl">
							<label class="control-label" for="memberCategoryCode">Member
								Category Code</label>

							<div class="controls">

								<form:select path="memberCategoryCode" cssClass="input-medium"
									id="memberCategoryCode">
									<option value="${groupMember.memberCategoryCode}">Select One</option>
								</form:select>
							</div>
						</div>


						<div class="control-group" id="paidMemberCtl">
							<label class="control-label" for="paidMember">Membership
								Fees Paid?</label>

							<div class="controls">
								<form:radiobutton path="paidMember" cssClass="input-medium"
									id="paidMember" value="true"
									onclick="$('#moreDetails').show();" />
								YES&nbsp;&nbsp;
								<form:radiobutton path="paidMember" cssClass="input-xlarge"
									id="paidMember" value="false"
									onclick="$('#moreDetails').hide();" />
								NO
							</div>
						</div>
					</div>

					<div class="span4">
						<div id="moreDetails">
							<!-- div class="control-group" id="membershipStartDateCtl">
							<label class="control-label" for="membershipStartDate">Membership
								Start date</label>


							<div class="controls">
								<div class="input-group date">
									<form:input path="membershipStartDate" cssClass="input-xlarge"
										id="membershipStartDateCtl"/>
								<span class="add-on"><i class="icon-calendar"></i></span>
								</div>
							</div>
						</div>   -->

							<div class="control-group" id="membershipStartDateCtl">
								<label class="control-label" for="membershipDates">Membership
									Dates</label>
								<div class="controls">
									<div class="input-daterange input-group" id="datepicker">
									<fmt:formatDate value='${groupMember.membershipStartDate}' var="stDate" pattern='dd/MM/yyyy'/>
									<fmt:formatDate value='${groupMember.membershipEndDate}' var="endDate" pattern='dd/MM/yyyy'/>
										<form:input path="membershipStartDate" value="${stDate}"
										cssClass="form-control input-small input-append"	id="membershipStartDate" placeholder="Start Date" />&nbsp;<span class="input-prepend add-on"><i	class="icon-calendar"></i></span> 
										<span><i>&nbsp;&nbsp;&nbsp;TO</i></span>
										<form:input path="membershipEndDate" value="${endDate}"
										cssClass="form-control input-small input-append" id="membershipEndDate"  placeholder="End Date" />&nbsp;<span class="input-prepend add-on"><i class="icon-calendar"></i></span>
									</div>
								</div>
							</div>
							<!-- div class="control-group" id="membershipEndDateCtl">
							<label class="control-label" for="membershipEndDate">Membership
								End date</label>


							<div class="controls">
								<div class="input-group date">
									<form:input path="membershipEndDate" data-provide="datepicker" data-date-format="dd/mm/yyyy" cssClass="input-xlarge"
										id="membershipEndDateCtl"/>
								<span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
								</div>
							</div>
						</div>  -->

							<div class="control-group" id="adultCountCtl">
								<label class="control-label" for="adultCount">Adult
									Count &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Kid Count</label>

								<div class="controls">

									<form:input path="adultCount" cssClass="input-small"
										id="adultCount" placeholder="No. of Adults" />
									<form:input path="kidCount" cssClass="input-small"
										id="kidCount" placeholder="No. of Kids" />
								</div>
							</div>
							<!-- div class="control-group" id="kidCountCtl">
								<label class="control-label" for="kidCount">Kid Count</label>
								<div class="controls">
									<form:input path="kidCount" cssClass="input-small"
										id="kidCount" placeholder="No. of Kids" />
								</div>
							</div>  -->
							<div class="control-group" id="paidMembershipAmountCtl">
								<label class="control-label" for="paidMembershipAmount">Membership
									Amount Paid</label>

								<div class="controls">
									<form:input path="paidMembershipAmount" cssClass="input-small"
										id="paidMembershipAmount" placeholder="Amount Paid" />
								</div>
							</div>
						</div>


					</div>
				</fieldset>
				<form:hidden path="serialNumber"/>
				<input type="hidden" name="groupEventCode" value="${groupEvent.eventCode}" id="groupEventCode"/>
				<input type="hidden" name="groupEventInviteId" value="${groupEventInvite.groupEventInviteId}" id="groupEventInviteId"/>
				<input class="btn btn-primary btn-medium" type="submit"
					value="SUBMIT" />
				<a
					href="${pageContext.request.contextPath}/${sessionScope.groupCode}/"
					class="btn btn-medium">CANCEL</a>
			</form:form>
		</div>
		<table id='dependentGrid' class='scroll'></table><div id='dependentPagerGrid' class='scroll'></div>
        </div>
        <div class="tab-pane" id="option2">
        <c:if test="${groupEvent ne null }"><h2>Event Name - ${groupEvent.eventName }</h2><br/></c:if>
		<table id="inviteGrid" class='scroll'></table><div id="invitePagerGrid" class='scroll'></div>
		<c:if test="${groupEventInvite.groupEventInviteId ne null}">
<br/>
<a href="#grid" class="btn btn-warning btn-mini"  onclick="loadDiv(); loadGrid('grid','Invite Passes');">Reload</a>
				<div class="gridDivPasses">	<table id="grid"></table>
			<div id="pgrid"></div></div>
			</c:if>

        </div>
        <div class="tab-pane" id="option3">
           		<table id="gridRSVPId" class='scroll'></table><div id="pagergridRSVPId" class='scroll'></div>
        </div>
        
        <div class="tab-pane" id="option4">
        <div class="alert alert-info" style="padding: 10px;" >
        		<fieldset>
		<form id="groupEventInviteForm" name="groupEventInviteForm" onsubmit="event.preventDefault();">

			<div class="span3">
				<div class="control-group" id="groupEventCodeNotificationCtl">
					<label class="control-label" for="groupEventCodeNotification">Group
						Event Code</label>

					<div class="controls">

						<select name="groupEventCodeNotification" class="input-large"
							id="groupEventCodeNotification"
							onchange="loadDivNotification(); buildGroupEmailTemplateOptionsByEventCode(this.value,'templateNameNotification'); loadGridNotification('gridNotification','',this.value,'Invite Hah List');">
							<option value="">Select One</option>
						</select>
					</div>
				</div>

			</div>

			<div class="span3">
				<div class="control-group" id="templateNameNotificationCtl">
					<label class="control-label" for="templateNameNotification">Email
						Template Name</label>

					<div class="controls">


						<select id="templateNameNotification" name="templateNameNotification" class="input-large">
							<option value="">Select One</option>
						</select>
					</div>
				</div>
			</div>
			<div class="span3">
				<div class="control-group" id="submitNotificationCtl">
					<label class="control-label" for="submitNameNotificationCtl">&nbsp;</label>

					<div class="controls">
						<button class="btn btn-danger btn-small has-spinner"  type="submit" value="SUBMIT ALL" id="sendAll">SUBMIT</button>
					</div>
				</div>
			
			</div>

</form>
			</fieldset>	

			
				
				<!--  button class="btn btn-inverse btn-small has-spinner" type="submit" value="SUBMIT SELECTED" id="sendSelected">SUBMIT SELECTED</button> -->
				</div>
           		<div class="gridDivNotification"><table id="gridNotification" class='scroll'></table><div id="pagergridNotification" class='scroll'></div></div>
           		
           		
        </div>
    </div>
</div>


		<!-- Modal -->
		<div id="myModal" class="modal hide fade" data-backdrop="static" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true" align="center">
			<div class="modal-header alert alert-danger">
				<!-- button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>  -->
				<h3 id="myModalLabel">Select An Event</h3>
			</div>
			<div class="modal-body">
			<div  id="eHTML">
				<form action="loadScanGroupMember" name="setEvent" id="setEvent" method="GET">
				<input type="hidden" name="membershipIdentifier" id="membershipIdentifier" value="${groupMember.membershipIdentifier}">			
				<fieldset>

				<div class="span3">
					<div class="control-group" id="memberCategoryCodeCtl">
						<label class="control-label" for="memberCategoryCode">Member
							Category Code</label>

						<div class="controls">

							<select  name="memberCategoryCode" class="input-large"
								id="memberCategoryCode"
								onchange="loadDiv();buildGroupEventsOptionsByMemberCategory(this.value, 'groupEventCode');">
								<option>Select One</option>
							</select>
						</div>
					</div>

				</div>
				<div class="span3">

					<div class="control-group" id="groupEventCodeCtl">
						<label class="control-label" for="groupEventCode">Group
							Event Code</label>

						<div class="controls">

							<select name="groupEventCode" class="input-large"
								id="groupEventCode">
								<option>Select One</option>
							</select>
						</div>
					</div>

				</div>
				
				<div class="span3">

					<div class="control-group" id="groupEventSubmitCtl">
						<label class="control-label" for="groupEventSubmit">&nbsp; </label>

						<div class="controls">

							<input type="submit" class="btn btn-primary"  value="LOAD EVENT">
						</div>
					</div>

				</div>				
			</fieldset> 
			

		<!-- 	<div class="modal-footer">
			
				

			</div>  -->
			</form>
			</div>
			</div>
		</div>
		<!-- Modal End -->
		
		
		
				<!-- Modal 2 Start -->
		<div id="myModal2" class="modal hide fade" data-backdrop="static" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel2" aria-hidden="true" align="center">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h3 id="myModalLabel2">Search for existing member</h3>
			</div>
			<div class="modal-body">
			<div  id="eHTML2">
				<!-- form action="loadScanGroupMember" name="setEvent" id="setEvent" method="GET">	 -->		

					<table id="gridALL"></table>
					<div id="pgridALL"></div>			


			<!-- /form>  -->
			</div>
			</div>
		</div>
		<!-- Modal 2 End -->
		
	</div>
</div>

<script type="text/javascript">

var gridDivPasses = "";
function loadDiv() {

	$("div.gridDivPasses").html(gridDivPasses);
}


function loadModal(html)
{
$('#myModal').modal('show').css({'width': '70%', 'margin-left':'auto', 'margin-right':'auto', 'left':'15%'});

}

function loadModal2(html)
{
	$('#myModal2').modal('show').css({'width': '70%', 'margin-left':'auto', 'margin-right':'auto', 'left':'15%'});

	}
	$(document).ready(
			function() {
				gridDivPasses = $("div.gridDivPasses").html();
				
				
				<c:if test="${groupEvent.eventCode eq null}">loadModal("");</c:if>
				buildGroupMemberCategoriesOptions('memberCategoryCode');
				buildGroupEventsOptionsByMemberCategory($("select#memberCategoryCode").val(), "groupEventCode");
			
				
				
				$("#setEvent").validate(
						{
							rules : {
								groupEventCode : {
									required : true
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
						})
						
						
						
				
				$("#groupMember").validate(
						{
							rules : {
								firstName : {
									required : true
								},
								lastName : {
									required : false
								},
								primaryEmail : {
									required : true,
									email : true
								},
								membershipStartDate : {
									dateITA : true
								},
								membershipEndDate : {
									dateITA : true
								},
								adultCount : {
									number : true
								},
								kidCount : {
									number : true
								},
								paidMembershipAmount : {
									number : true
								},
								memberCategoryCode : {
									required : true
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
						})

			});

	var pager_id= "dependentPagerGrid";
	var subgrid_table_id = "dependentGrid";
	var groupEventInviteId = '${groupEventInvite.groupEventInviteId}';
	var groupEventCodeForInvite = '${groupEventInvite.groupEventCode}';
	var parentSerialNo = '${groupMember.serialNumber}';
	var groupMemberCategoryCode = '${groupMember.memberCategoryCode}';
	var lastsel = null;
	jQuery("#" + subgrid_table_id)
	.jqGrid(
			{
				url:"json/viewGroupDependents?serialNumber="+parentSerialNo,
				//data : dependentsObject,
				datatype : "json",
				colNames : [ 'Action',
						'First Name',
						'Last Name',
						'Alias', 'Email',
						'Mobile',
						'Relationship',
						'Birthday',
						'Dep Serial No' ],
				colModel : [
						{
							name : 'myac',
							width : 50,
							fixed : true,
							sortable : false,
							resize : false,
							formatter : 'actions',
							formatoptions : {
								keys : false,
								delbutton : false
							}
						},
						{
							name : "firstName",
							index : "firstName",
							width : 100,
							editable : true,
							editrules : {
								required : true
							}
						},
						{
							name : "lastName",
							index : "lastName",
							width : 100,
							editable : true
						},
						{
							name : "aliasName",
							index : "aliasName",
							width : 100,
							editable : true
						},
						{
							name : "email",
							index : "email",
							width : 150,
							editable : true,
							editrules : {
								required : false,
								email : true
							}
						},
						{
							name : "mobilephone",
							index : "mobilephone",
							width : 100,
							editable : true
						},
						{
							name : "relationship",
							index : "relationship",
							width : 100,
							editable : true
						},
						{
							name : "birthday",
							index : "birthday",
							width : 100,
							editoptions : {
								dataInit : datePick
							},
							formatter : formatDate,
							editable : true
						},
						{
							name : "dependentserialNumber",
							index : "dependentserialNumber",
							width : 100,
							hidden : true,
							editable : true
						}

				],
				rowNum : 20,
				pager : pager_id,
				//	sortname: 'num',
				sortorder : "asc",
				rownumbers : true,
				rownumWidth : 25,
				width : 937,
				height : '100%',
				editurl : "updateGroupDependents?serialNumber="
						+ parentSerialNo,
				caption : "Dependent Records"
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
			{del:false}, //options
			{
				height : 400,
				width : 420,
				top : 250,
				left : 400,
				dataheight : 300,
				reloadAfterSubmit : true,
				afterSubmit : function(
						response, postdata) {
					var status = false;
					if (response.responseText === "success")
						status = true
					return [ status,
					         response.responseText ];
				}
			}, // edit options
			{
				height : 400,
				width : 420,
				top : 250,
				left : 400,
				dataheight : 300,
				reloadAfterSubmit : true,
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
				top : 250,
				left : 400,
				dataheight : 100} // search options
	);
	
	
	
	
	
	var gridId = "inviteGrid";
	var pgrid = "invitePagerGrid";
jQuery("#" + gridId).jqGrid(
		{
			url : "json/viewScannedGroupEventInvites?serialNumber="+parentSerialNo+"&groupEventInviteId="+groupEventInviteId,
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
							delbutton:false,
							keys : true
						}
					},
					{name: "event",
						label: "Event",
					    width: 65,
					    align: "center",
					    formatter: function(cellValue, options, rowObject) {

					    var eventInviteId = rowObject.groupEventInviteId;
					    var geventCode = rowObject.groupEventCode;
					    var url = "scanGroupMember?value="+eventInviteId+"&groupEventCode=${groupEvent.eventCode}" ;
					    var globalEventCode = "${groupEvent.eventCode}";
					    var disabled = "btn-warning";
					    if(geventCode!==globalEventCode)
					    {
					    	url = "#";
					    	disabled = "disabled";
					    }
					    return "<a href='"+url+"'  class='btn btn-mini "+disabled+"' >"+geventCode+"</a>"
					    }
					},
					{
						label : 'Event Invite ID',
						name : 'groupEventInviteId',
						index : 'groupEventInviteId',
						hidden : true,
						editable : true,
						width: 130
					},
					{
						label : 'Invite Code',
						name : 'groupEventInviteCode',
						index : 'groupEventInviteCode',
						hidden : false,
						align: 'center',
						editable : false,
						width: 80
					},
					{
						label : 'Mark Attended',
							name : 'markAttended',
							index : 'markAttended',
							hidden : false,
							editable : true,
							align: 'center',
							formatter: formatBoolean,
						edittype : "checkbox",
						editoptions : {
							value : "Yes:No"}, 										
						width: 100
						},	
					{
						label : 'Amt. Paid',
						name : 'paidAmount',
						index : 'paidAmount',
						hidden : false,
						editable : true,
						width: 60
					},
					{
						label : 'Transaction Ref.',
						name : 'transactionReference',
						index : 'transactionReference',
						hidden : false,
						editable : true,
						width: 100
					},
					{
						label : 'Trans Apprd.',
							name : 'transactionApproved',
							index : 'transactionApproved',
							hidden : false,
							align: 'center',
							editable : true,
							formatter: formatBoolean,
						edittype : "checkbox",
						editoptions : {
							value : "Yes:No"}, 										
						width: 100
						},
						{
							label : 'Transaction Dt.',
							name : 'transactionDateTime',
							index : 'transactionDateTime',
							width : 100,
							sortable : true,
							editable : true,
							formatter : formatDate,
							editoptions : {
								dataInit : datePick
							}
						},
						{
							label : 'First Name',
							name : 'groupMember.firstName',
							index : 'groupMember.firstName',
							hidden : false,
							editable : false,
							width: 100
						},
						{
							label : 'Last Name',
							name : 'groupMember.lastName',
							index : 'groupMember.lastName',
							hidden : false,
							editable : false,
							width: 100
						},
					{
						label : 'Invite Emailed',
						name : 'inviteSent',
						index : 'inviteSent',
						hidden : false,
						editable : false,
						width : 80,
						formatter: formatBoolean,
						edittype : "checkbox",
						editoptions : {
							value : "Yes:No"}
					},
					{
						label : 'Invite Delivered',
						name : 'inviteDelivered',
						index : 'inviteDelivered',
						hidden : false,
						editable : false,
						formatter: formatBoolean,
						width: 80
					},
//						{
//							label : 'Invite Cancelled',
//							name : 'inviteCancelled',
//							index : 'inviteCancelled',
//							hidden : false,
//							editable : true,
//							edittype : "checkbox",
//							editoptions : {
//								value : "true:false"}
//						},

					{
						label : 'RSVPd',
							name : 'rsvpd',
							index : 'rsvpd',
							hidden : false,
							editable : false,
							formatter: formatBoolean,
						width: 50
						},
					{
						label : 'Invite Held',
						name : 'inviteHeld',
						index : 'inviteHeld',
						hidden : false,
						editable : true,
						formatter: formatBoolean,
						width: 80,
						edittype : "checkbox",
						editoptions : {
							value : "Yes:No"}
					},
					{
						label : 'Email Count',
						name : 'inviteEmailCount',
						index : 'inviteEmailCount',
						width : 80,
						hidden : false,
						editable : false
					},
					{
						label : 'Event Code',
						name : 'groupEventCode',
						index : 'groupEventCode',
						hidden : true,
						editable : false,
						width: 100
					},
					{
						label : 'Category',
						name : 'memberCategoryCode',
						index : 'memberCategoryCode',
						hidden : false,
						editable : false,
						width: 80,
						sortable : true,
						sorttype : 'string'
					} ],
			height : 'auto',
			rowNum : 500,
			rownumbers: true,
			rownumWidth : 25,
			width : 937,
			rowList : [ 250, 500, 1000, 2000 ],
			pager : '#'+pgrid,
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
				if (id && id !== lastsel) {
					jQuery('#' + gridId).jqGrid('restoreRow',
							lastsel);
					jQuery('#' + gridId).jqGrid('editRow', id,
							true, datePick);
					lastsel = id;
				}
			},
			editurl : "updateGroupEventInviteAttendance",
			caption : "Invite Details"
		});
jQuery("#" + gridId).jqGrid('navGrid', '#'+pgrid, {
edit : false,
add : true,
del : false
});
jQuery("#" + gridId).jqGrid('filterToolbar',{searchOperators : true});


var gridRSVPId = "gridRSVPId";
var pagergridRSVPId = "pagergridRSVPId";
jQuery("#" + gridRSVPId)
.jqGrid(
		{
			url : 'json/viewLatestGroupEventInvitesRSVPsByGEIId/' + groupEventInviteId,
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
		keys : false,
		delbutton:false,
		editbutton: true,
		//editOptions : {
				url: 'json/updateRSVP?mode=update'
		//}
	}
},
					{
						label : 'First Name',
						name : 'groupMember.firstName',
						index : 'groupMember.firstName',
						hidden : false,
						editable : false,
						width: 100
					},
					{
						label : 'Last Name',
						name : 'groupMember.lastName',
						index : 'groupMember.lastName',
						hidden : false,
						editable : false,
						width: 100
					},
					{
						label : 'ID',
						name : 'groupEventInviteRSVPId',
						index : 'groupEventInviteRSVPId',
						hidden : true,
						editable : true,
						width: 100
					},
					{
						label : 'Adult Count',
						name : 'adultCount',
						index : 'adultCount',
						width: 75,
						hidden : false,
						editable : true,
						editrules : {
							integer : true
						}
						
					},
					{
						label : 'Kids Count',
						name : 'kidsCount',
						index : 'kidsCount',
						width: 75,
						hidden : false,
						editable : true,
						formatter: 'integer',
						editrules : {
							integer : true
						}
					},
//						{
//							label : 'Invite Held',
//							name : 'inviteHeld',
//							index : 'inviteHeld',
//							hidden : false,
//							editable : true,
//							edittype : "checkbox",
//							editoptions : {
//								value : "true:false"}
//						},
					{
						label : 'RSVP Status',
							name : 'rsvpOutcome',
							index : 'rsvpOutcome',
							width: 75,
							hidden : true,
						//formatter: formatBoolean,
							editable : false
						},
					{
						label : 'Attended',
							name : 'groupEventInvite.markAttended',
							index : 'groupEventInvite.markAttended',
							hidden : false,
							editable : false,
							formatter: formatBoolean,
						edittype : "checkbox",
						editoptions : {
							value : "Yes:No"}, 										
						width: 75
						},
						
							{
								label : 'Transaction Ref.',
								name : 'transactionReference',
								index : 'transactionReference',
								hidden : false,
								editable : false,
								width: 100
							},
						{
							label : 'Amt. Paid',
							name : 'groupEventInvite.paidAmount',
							index : 'groupEventInvite.paidAmount',
							hidden : false,
							editable : false,
							width: 60
						},
					{
						label : 'RSVP DateTime',
						name : 'rsvpDateTime',
						index : 'rsvpDateTime',
						width : 100,
						sortable : true,
						editable : false,
						formatter : formatDateTime
					},
					{
						label : 'Mem Category',
						name : 'memberCategoryCode',
						index : 'memberCategoryCode',
						hidden : false,
						editable : false,
						sortable : true,
						sorttype : 'string'
					},
						{
							label : 'Rsvp Comments',
							name : 'rsvpComments',
							index : 'rsvpComments',
							hidden : false,
							width : 300,
							editable : false
						} ],
			height : 'auto',
			rowNum : 500,
			rownumbers: true,
			rownumWidth : 25,
			width : 920,
			rowList : [ 250, 500, 1000, 2000 ],
			pager : '#'+pagergridRSVPId,
		//	sortname : 'firstName',
			autoencode : true,
			shrinkToFit : false,
			viewrecords : true,
			grouping : false,
			loadonce : false,
			groupingView : {
				groupField : [ 'rsvpOutcome' ],
				groupColumnShow : [ false ],
		 		groupSummary : [false],
				groupText : [ '<b>{1} Member(s)</b>' ]
			},
			onSelectRow : function(id) {

			},
			//editurl : "server.php",
			caption : "RSVPs"
		});
jQuery("#" + gridRSVPId)
.jqGrid(
		'navGrid',
		"#"+pagergridRSVPId,
		{del:false, edit:false
			}, //options
		{}, // edit options
		{savekey: [true, 13],
			height : 220,
			width : 440,
			top : 300,
			left : 470,
			url : "json/updateRSVP?mode=add&groupEventInviteId="+groupEventInviteId,
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
		}, // add options
		{}, // del options
		{} // search options
);
//jQuery("#" + gridRSVPId).jqGrid('filterToolbar',{searchOperators : true});
	
	
	
	
	
function loadGrid(gridId, captionVal) {	
jQuery("#" + gridId)
.jqGrid(
		{
			url : "json/viewGroupEventPassesBySerialNumberAndGroupEventCode/"
				+ parentSerialNo
				+ "/"
				+ groupEventCodeForInvite,
			datatype : "json",
			colModel : [
						{
							label : 'Action',
							name : 'myac',
							width : 40,
							fixed : true,
							sortable : false,
							resize : false,
							formatter : 'actions',
							formatoptions : {
								keys : false,
								editbutton:false,
								delOptions : {
										url: 'detachPassesFromRegistrations'
								}
							}
						},
					{
						label : 'Barcode',
						name : 'passBarcode',
						index : 'passBarcode',
						hidden : false,
						editable : true,
						align: 'center',
						width: 80,
						editrules : {
							required : false
							//,email : true
						}
					},
					{
						label : 'Scan Date Time',
						name : 'trackingDate',
						index : 'trackingDate',
						hidden : false,
						editable : false,
						formatter : formatDateTime,
						width: 110
					},
					{
						label : 'Start Date',
						name : 'passStartDate',
						index : 'passStartDate',
						hidden : false,
						editable : false,
						formatter : formatDateTime,
						width: 110
					},	
					{
						label : 'Exp Date',
						name : 'passExpiryDate',
						index : 'passExpiryDate',
						hidden : false,
						editable : false,
						formatter : formatDateTime,
						width: 110
					},	
					{
						label : 'Entry Cnt',
						name : 'noOfPeopleTagged',
						index : 'noOfPeopleTagged',
						hidden : false,
						editable : false,
						align: 'center',
						width: 60,
						summaryType : 'sum',
						summaryTpl : '<b>Total: {0}</b>'
					},
					{
						label : 'Price Tag',
						name : 'passPrice',
						index : 'passPrice',
						hidden : false,
						align: 'center',
						editable : false,
						width: 80,
						formatter: 'double',
						summaryType : 'sum',
						summaryTpl : '<b>Total: {0}</b>'
					},	
					{
						label : 'Amt Approved',
						name : 'groupEventInvite.transactionApproved',
						index : 'groupEventInvite.transactionApproved',
						hidden : false,
						align: 'center',
						editable : false,
						width: 90,
						formatter: formatBoolean,
							edittype : "checkbox",
							editoptions : {
								value : "Yes:No"}
					},
					{
							label : 'Transaction Ref.',
							name : 'groupEventInvite.transactionReference',
							index : 'groupEventInvite.transactionReference',
							hidden : false,
							editable : false,
							width: 100
						},
						{
							label : 'Bought By',
							name : 'groupEventInvite.groupMember.firstName',
							index : 'groupEventInvite.groupMember.firstName',
							hidden : false,
							editable : false,
							width: 100
						},
					{
						label : 'Group Label',
						name : 'groupEventCode',
						index : 'groupEventCode',
						hidden : true,
						editable : false,
						sortable : true,
						sorttype : 'string'
					} ],
			height : 'auto',
			rowNum : 500,
			rownumbers: true,
			rownumWidth : 25,
			width : 937,
			rowList : [ 250, 500, 1000, 2000 ],
			pager : '#pgrid',
		//	sortname : 'firstName',
			autoencode : true,
			shrinkToFit : false,
			viewrecords : true,
			grouping : true,
			loadonce : false,
			groupingView : {
				groupField : [ 'groupEventCode' ],
				groupColumnShow : [ false ],
				groupSummary : [true],
				groupText : [ '<b>{0} - {1} Passes(s)</b>' ]
			},
			onSelectRow : function(id) {
				if (id && id !== lastsel) {
					jQuery('#' + gridId).jqGrid('restoreRow',
							lastsel);
					jQuery('#' + gridId).jqGrid('editRow', id,
							true, pickdates);
					lastsel = id;
				}
			},
			editurl : "registerPassesToAttendees?groupEventInviteId="+groupEventInviteId+"&serialNumber="+parentSerialNo,
			caption : captionVal
		});
		
		
jQuery("#" + gridId)
.jqGrid(
		'navGrid',
		"#pgrid",
		{del:false, edit:false
			}, //options
		{}, // edit options
		{savekey: [true, 13],
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
		}, // add options
		{}, // del options
		{													height : 200,
			width : 720,
			top : 300,
			left : 400,
			dataheight : 100} // search options
);
// jQuery("#" + gridId).jqGrid('navGrid', "#pgrid", {
// edit : false,
// add : false,
// del : false
// });
jQuery("#" + gridId).jqGrid('filterToolbar',{searchOperators : true});
}

	
	
	
	
function loadMemberGrid(gridId, groupMemCatCode, captionVal) {
	jQuery("#" + gridId)
			.jqGrid(
					{
						url : 'json/viewAllGroupMember/' + groupMemCatCode,
						datatype : "json",
						colModel : [
								{
									label : 'Serial No',
									name : 'serialNumber',
									index : 'serialNumber',
									sorttype : "string",
									hidden : true,
									editable : true
								},
								{
									label : 'First Name',
									name : 'firstName',
									index : 'firstName',
									width : 100,
									sorttype : "string",
									search : true,
									editable : true,
									//frozen : true,
									searchoptions : {
										sopt : [ 'bw', 'eq', 'bn', 'cn',
												'nc', 'ew', 'en' ]
									}
								},
								{
									label : 'Last Name',
									name : 'lastName',
									index : 'lastName',
									sorttype : "string",
									//frozen : true,
									width : 100,
									editable : true,
									searchoptions : {
										sopt : [ 'bw', 'eq', 'bn', 'cn',
												'nc', 'ew', 'en' ]
									}

								},									
								{
									label : 'Member ID',
									name : 'membershipIdentifier',
									index : 'membershipIdentifier',
									sorttype : "string",
									width : 88,
									hidden : false,
									editable : false,
									formatter: function(cellValue, options, rowObject) {

									    var membershipIdentifier = rowObject.membershipIdentifier;
									    var url = "scanGroupMember?value="+membershipIdentifier+"&groupEventCode=${groupEvent.eventCode}" ;
									    return "<a href='"+url+"'  class='btn btn-warning btn-mini' >"+membershipIdentifier+"</a>"
									    }
								},
								{
									label : 'Active',
									name : 'activeMember',
									index : 'activeMember',
									width : 50,
									sortable : true,
									editable : false,
									formatter : formatBoolean,
								},
								{
									label : 'Primary Email',
									name : 'primaryEmail',
									index : 'primaryEmail',
									sorttype : "string",
									width : 140,
									editable : true,
									editrules : {
										email : true
									}
								},
								{
									label : 'Mem. End Dt.',
									name : 'membershipEndDate',
									index : 'membershipEndDate',
									width : 94,
									sortable : true,
									editable : true,
									//sorttype : "date",
									editoptions : {
										dataInit : datePick
									},
									searchoptions : {
										sopt : [ 'bw', 'eq', 'bn', 'cn',
												'nc', 'ew', 'en' ]
									},
									formatter : formatDate
								//	,
								//			formatoptions: { srcformat: "m/d/Y", newformat: "d/m/Y" }

								},
								{
									label : 'Membership Category',
									name : 'memberCategoryCode',
									index : 'memberCategoryCode',
									sorttype : "string",
									hidden : false,
									width : 80,
									editable : true,
									edittype : 'select',
									editoptions : {
										dataUrl : 'json/viewGroupMemberCategories',
										buildSelect : function(response) {
											var options = '<select>';
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
								},
								],
						height : 300,
						rowNum : 13,
						rownumbers : true,
						rownumWidth : 25,
						width : 744,
						rowList : [ 250, 500, 1000, 2000 ],
						pager : '#pgrid' + groupMemCatCode,
						sortname : 'firstName',
						autoencode : true,
						shrinkToFit : false,
						viewrecords : true,
						//grouping : true,
						subGrid : true,
						loadonce : true,
						groupingView : {
							groupField : [ 'memberCategoryCode' ],
							groupColumnShow : [ false ],
							groupText : [ '<b>{0} - {1} Members(s)</b>' ]
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
							var parentSerialNo = data.serialNumber;
							//var dependetData = data.groupDependents;
							//var dependentObjects = JSON.parse(dependetData);
							pager_id = "p_" + subgrid_table_id;
							$("#" + subgrid_id)
									.html(
											"<table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div>");
							jQuery("#" + subgrid_table_id)
									.jqGrid(
											{
												url:"json/viewGroupDependents?serialNumber="+parentSerialNo,
												datatype : "json",													
												colNames : [ 'Action',
														'First Name',
														'Last Name',
														'Alias', 'Email',
														'Mobile',
														'Relationship',
														'Birthday',
														'Dep Serial No' ],
												colModel : [
														{
															name : 'myac',
															width : 80,
															fixed : true,
															sortable : false,
															resize : false,
															formatter : 'actions',
															formatoptions : {
																keys : false
															}
														},
														{
															name : "firstName",
															index : "firstName",
															width : 100,
															editable : true,
															editrules : {
																required : true
															}
														},
														{
															name : "lastName",
															index : "lastName",
															width : 100,
															editable : true
														},
														{
															name : "aliasName",
															index : "aliasName",
															width : 100,
															editable : true
														},
														{
															name : "email",
															index : "email",
															width : 150,
															editable : true,
															editrules : {
																required : false,
																email : true
															}
														},
														{
															name : "mobilephone",
															index : "mobilephone",
															width : 100,
															editable : true
														},
														{
															name : "relationship",
															index : "relationship",
															width : 100,
															editable : true
														},
														{
															name : "birthday",
															index : "birthday",
															width : 100,
															editoptions : {
																dataInit : datePick
															},
															formatter : formatDate,
															editable : true
														},
														{
															name : "dependentserialNumber",
															index : "dependentserialNumber",
															width : 100,
															hidden : true,
															editable : true
														}

												],
												rowNum : 20,
												pager : pager_id,
												//	sortname: 'num',
												sortorder : "asc",
												rownumbers : true,
												rownumWidth : 25,
												height : '100%',
												editurl : "updateGroupDependents?serialNumber="
														+ parentSerialNo,
												caption : "Dependent Records"
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
											{del:false}, //options
											{
												height : 400,
												width : 420,
												top : 100,
												left : 400,
												dataheight : 300,
												reloadAfterSubmit : true,
												afterSubmit : function(
														response, postdata) {
													var status = false;
													if (response.responseText === "success")
														status = true
													return [ status,
													         response.responseText ];
												}
											}, // edit options
											{
												height : 400,
												width : 420,
												top : 100,
												left : 200,
												dataheight : 300,
												reloadAfterSubmit : true,
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
							//  								if (id && id !== lastsel) {
							//  									jQuery('#' + gridId).jqGrid('restoreRow',
							//  											lastsel);
							//  									jQuery('#' + gridId).jqGrid('editRow', id,
							//  											true, datePicker);
							//  									lastsel = id;
							// 								}
						},
						editurl : "updateGroupMember",
						caption : captionVal
					});
	jQuery("#" + gridId).jqGrid('navGrid', "#pgrid" + groupMemCatCode, {
		edit : false,
		add : false,
		del : false
	});
	jQuery("#" + gridId).jqGrid('filterToolbar', {
		searchOperators : true
	});
	//jQuery("#" + gridId).jqGrid('setFrozenColumns');
}	
	



var pagergridNotification = "#pagergridNotification";
var gridNotification = "#gridNotification";
var gridDivNotification = "";
$(document).ready(function () {
	
    decodeErrorMessage = function(jqXHR, textStatus, errorThrown) {
        var html, errorInfo, i, errorText = textStatus + '\n<br />' + errorThrown;
        if (jqXHR.responseText.charAt(0) === '[') {
            try {
                errorInfo = $.parseJSON(jqXHR.responseText);
                errorText = "";
                for (i=0; i<errorInfo.length; i++) {
                   if (errorText.length !== 0) {
                       errorText += "<hr/>";
                   }
                   errorText += errorInfo[i].Source + ": " + errorInfo[i].Message;
                }
            }
            catch (e) { }
        } else {
            html = /<body.*?>([\s\S]*)<\/body>/i.exec(jqXHR.responseText);
            if (html !== null && html.length > 1) {
                errorText = html[1];
            }
        }
        return errorText;
    };
    
    
    sendData = function(data,templateName,groupEventCode,memberCategoryCode,selector) {
        var dataToSend = JSON.stringify(data);
        toggleButtonWithId(selector);
        $.ajax({
            type: "POST",
            url: "json/saveGroupEventInviteEmails?templateName="+templateName+"&groupEventCode="+groupEventCode+"&memberCategoryCode="+memberCategoryCode,
            //dataType:"json",
            data: dataToSend,
            contentType: "application/json; charset=utf-8",
            success: function(response, textStatus, jqXHR) {
                // remove error div if exist
                loadMessageModal("Message", response);
                loadDivNotification();
                loadGridNotification('gridNotification',memberCategoryCode,groupEventCode,'Invite List');
                resetButtonWithId(selector);
            },
            error: function(jqXHR, textStatus, errorThrown) {
            	loadMessageModal("Error", jqXHR.responseText+textStatus);
            	resetButtonWithId(selector);
            }
        });
    };


	
	
	
	
	
	
	
	
	
    $("#groupEventInviteForm").validate({
        rules:{
            groupEventCodeNotification:{
                required:true
            },
            templateNameNotification: {
            	required: true
            	}
            
        },
        errorClass:"control-group error",
        validClass:"control-group success",
        errorElement:"span",
        highlight:function (element, errorClass, validClass) {
            if (element.type === 'radio') {
                this.findByName(element.name).parent("div").parent("div").removeClass(validClass).addClass(errorClass);
            } else {
                $(element).parent("div").parent("div").removeClass(validClass).addClass(errorClass);
            }
        },
        unhighlight:function (element, errorClass, validClass) {
            if (element.type === 'radio') {
                this.findByName(element.name).parent("div").parent("div").removeClass(errorClass).addClass(validClass);
            } else {
                $(element).parent("div").parent("div").removeClass(errorClass).addClass(validClass);
            }
        }
    });
    gridDivNotification = $("div.gridDivNotification").html();

});

function loadDivNotification()
{

	$("div.gridDivNotification").html(gridDivNotification);
}
function loadGridNotification(gridId, groupMemCatCode, groupEventCode, captionVal) {
//	alert("gridId-->"+gridId+" groupMemCatCode-->"+groupMemCatCode+" groupEventCode-->"+groupEventCode+" captionVal-->"+captionVal)
	jQuery("#" + gridId)
			.jqGrid(
					{
						url : "json/viewGroupEventInvitesEventCodeAndSerialNumber?serialNumber="+parentSerialNo+"&groupEventCode="+groupEventCode,
						datatype : "json",
						colModel : [
//									{
//										label : 'Action',
//										name : 'myac',
//										width : 80,
//										fixed : true,
//										sortable : false,
//										resize : false,
//										formatter : 'actions',
//										formatoptions : {
//											keys : true
//										}
//									},
								{
									label : 'Event Serial No',
									name : 'groupEventInviteId',
									index : 'groupEventInviteId',
									hidden : true,
									editable : false
								},
								{
									label : 'First Name',
									name : 'groupMember.firstName',
									index : 'groupMember.firstName',
									width : 120,
									hidden : false,
									editable : false
								},
								{
									label : 'Last Name',
									name : 'groupMember.lastName',
									index : 'groupMember.lastName',
									width : 100,
									hidden : false,
									editable : false
								},
								{
									label : 'Email Verified',
									name : 'groupMember.primaryEmailVerified',
									index : 'groupMember.primaryEmailVerified',
									hidden : false,
									editable : true,
									width : 100,
									formatter: formatBoolean,
									edittype : "checkbox",
									editoptions : {
										value : "Yes:No"}
								},
								{
									label : 'Invite Held',
									name : 'inviteHeld',
									index : 'inviteHeld',
									hidden : false,
									editable : true,
									width : 80,
									formatter: formatBoolean,
									edittype : "checkbox",
									editoptions : {
										value : "Yes:No"}
								},
								{
									label : 'Invite Emailed',
									name : 'inviteSent',
									index : 'inviteSent',
									hidden : false,
									editable : false,
									width : 100,
									formatter: formatBoolean,
									edittype : "checkbox",
									editoptions : {
										value : "Yes:No"}
								},
								{
									label : 'RSVPd',
									name : 'rsvpd',
									index : 'rsvpd',
									hidden : false,
									editable : false,
									width : 60,
									formatter: formatBoolean,
									edittype : "checkbox",
									editoptions : {
										value : "Yes:No"}
								},
								{
									label : 'Invite Delivered',
									name : 'inviteDelivered',
									index : 'inviteDelivered',
									hidden : false,
									editable : false,
									width : 100,
									formatter: formatBoolean,
									edittype : "checkbox",
									editoptions : {
										value : "Yes:No"}
								},
								{
									label : 'Email Count',
									name : 'inviteEmailCount',
									index : 'inviteEmailCount',
									width : 80,
									hidden : false,
									editable : false
								},
								{
									label : 'Invite Cancelled',
									name : 'inviteCancelled',
									index : 'inviteCancelled',
									hidden : false,
									editable : true,
									width : 100,
									formatter: formatBoolean,
									edittype : "checkbox",
									editoptions : {
										value : "Yes:No"}
								},
								{
									label : 'Invite Expiry Date',
									name : 'inviteExpiryDate',
									index : 'inviteExpiryDate',
									width : 130,
									sortable : true,
									editable : true,
									sorttype : "date",
									searchoptions:{sopt:['eq','bw','bn','cn','nc','ew','en']},
									formatter : formatDate
								//	,
								//			formatoptions: { srcformat: "m/d/Y", newformat: "d/m/Y" }

								},
								{
									label : 'Mark Attended',
										name : 'markAttended',
										index : 'markAttended',
										hidden : false,
										editable : false,
										formatter: formatBoolean,
									edittype : "checkbox",
									editoptions : {
										value : "true:false"}, 										
									width: 100
									},									
								{
									label : 'Group Label',
									name : 'memberCategoryCode',
									index : 'memberCategoryCode',
									hidden : true,
									editable : false,
									sortable : true,
									sorttype : 'string'
								},
								{
									label : 'Serial No',
									name : 'groupMember.serialNumber',
									index : 'groupMember.serialNumber',
									hidden : true,
									editable : false
								} ],
						height : 'auto',
						rowNum : 500,
						rownumbers: true,
						rownumWidth : 25,
						width : 937,
						rowList : [ 250, 500, 1000, 2000 ],
						pager : pagergridNotification,
						multiselect: true,
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

						},
						editurl : "server.php",
						caption : captionVal
					});
	jQuery("#" + gridId).jqGrid('navGrid', pagergridNotification, {
		edit : false,
		add : false,
		del : false
	});
	jQuery("#" + gridId).jqGrid('filterToolbar',{searchOperators : true});
	
	

}

$(function() {

    $("#sendAll").click(function(){
        var localGridData = jQuery(gridNotification).jqGrid('getGridParam','data');
       
       var selectedEmailTemplate = $('select#templateNameNotification option').filter(':selected').val();
       var selectedMemberCategoryCode = '';
       var selectedgroupEventCode = $('select#groupEventCodeNotification option').filter(':selected').val();
       if(selectedgroupEventCode === '')
       {
    	   confirm("Please select a Event from the drop down");
    	   return false;  
       }
       if(selectedEmailTemplate==='')
       {
    	   confirm("Please select a template from the drop down");
    	   return false;
       }
        sendData(localGridData,selectedEmailTemplate,selectedgroupEventCode,selectedMemberCategoryCode,'sendAll');
     
    });
    $("#sendSelected").click(function(){
        var selectedEmailTemplate = $('select#templateNameNotification option').filter(':selected').val();
        var selectedMemberCategoryCode = '';
        var selectedgroupEventCode = $('select#groupEventCodeNotification option').filter(':selected').val();
        if(selectedEmailTemplate==='')
        {
     	   confirm("Please select a template from the drop down!");
     	   return false;
        }
        var localGridData = jQuery(gridNotification).jqGrid('getGridParam','data'),
            idsToDataIndex = jQuery(gridNotification).jqGrid('getGridParam','_index'),
            selRows = jQuery(gridNotification).jqGrid('getGridParam','selarrrow'),
            dataToSend = [], i, l=selRows.length;
        for (i=0; i<l; i++) {
        	var x = localGridData[idsToDataIndex[selRows[i]]];
        //	delete x['_id_'];
            dataToSend.push(x);
        }
        if(dataToSend.length===0){
      	   confirm("Please select atleast one checkbox to proceed!");
     	   return false;
        }
        sendData(dataToSend,selectedEmailTemplate,selectedgroupEventCode,selectedMemberCategoryCode,'sendSelected');

    });
    
//	$('.input-group.date').datepicker({
//		format : "dd/mm/yyyy"
//	});
//	$('.input-daterange').datepicker({
//		format : "dd/mm/yyyy"
//	});
	
//	buildGroupMemberCategoriesOptions('memberCategoryCode');
	buildGroupEventsOptionsByMemberCategory(groupMemberCategoryCode,"groupEventCodeNotification");
	buildGroupEmailTemplateOptionsByEventCode("NULL",'templateNameNotification');
});










	$(function() {
		$('.input-group.date').datepicker({
			format : "dd/mm/yyyy"
		});
		$('.input-daterange').datepicker({
			format : "dd/mm/yyyy"
		});
		//$('#moreDetails').hide();
		buildGroupMemberCategoriesOptions('memberCategoryCode');
	});
</script>
