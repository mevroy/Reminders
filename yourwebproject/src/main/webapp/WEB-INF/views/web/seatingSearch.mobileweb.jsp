<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:if test="${groupMember.serialNumber ne null}">
	<c:set var="warning" value="warning"></c:set>
</c:if>
<!-- -<div class="jumbotron">  -->
<div>
	<div style="padding-top: 18px;">
		<div class="alert alert-danger" style="padding: 5px;">
			<fieldset>
				<form action="seating" name="searchSeating" id="searchSeating"
					method="POST" 
					onsubmit="return validateFormAndToggleButton('searchSeating');">
					<input type="hidden" name="autoRegister" value="${autoRegister}"/>
					<div align="center">
						<div class="form-group" id="valueCtl">
							<label class="control-label" for="value"><b><h4>Enter
										Ticket Number (E.g. 2016-000)</h4></b></label>

							<div>
								<input type="text" class="form-control" name="value" id="value"
									autofocus="autofocus" placeholder="Enter your Ticket No."
									value="" />
							</div>
						</div>

						<div class="form-group">
							<button class="btn btn-danger btn-lg btn-block has-spinner"
								type="submit" id="submitButton" data-loading-text="<span class='spinner'><i class='icon-spin glyphicon glyphicon-repeat'></i></span> Loading.." autocomplete="off" >SEARCH</button>
						</div>


					</div>
				</form>
			</fieldset>
		</div>

	</div>
	<c:if test="${groupEventPasses ne null}">
		<!-- 	<div>
			<ul class="nav nav-pills nav-stacked">
				<li class="active alert-info"><a href="#tab1" data-toggle="tab">Seating
						for all tickets <span class="badge badge-error"><c:out
								value="${fn:length(groupEventPasses)}" /> Ticket</span>
				</a></li>
				<li class="alert-info"><a href="#tab2" data-toggle="tab">Member
						Info <span class="badge badge-error"><c:out
								value="${groupMember.firstName}" /> <c:out
								value="${groupMember.lastName}" /></span><span
						class="icon-chevron-down pull-right" title="Click to Expand"></span>
				</a></li>
				<li class="alert-info"><a href="#tab3" data-toggle="tab">Dinner
						Menu <span class="icon-chevron-down pull-right"
						title="Click to Expand"></span>
				</a></li>
			</ul>

			<div class="tab-content">
				<div class="tab-pane active" id="tab1">
					<div>Seating info for all your tickets</div>
					<div class="table-responsive">
						<table class="table table-bordered table-striped table-condensed">
							<thead>
								<tr style="font-weight: bold;" class="alert-error">
									<td>No.</td>
									<td>Ticket Number</td>
									<td>Table No.</td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${groupEventPasses}" var="groupEventPass"
									varStatus="count">
									<tr>
										<td><c:out value="${count.index+1}" /></td>
										<td><c:out value="${groupEventPass.passBarcode}" /></td>
										<td><c:out value="${groupEventPass.tableNumber}" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>

				<div class="tab-pane fade" id="tab2">
					Member Info
					<c:if test="${groupMember ne null }">
						<div class="table-responsive">
							<table class="table table-bordered table-condensed">
								<tbody>
									<tr style="font-weight: bold;">
										<td>Member Name</td>
										<td><c:out value="${groupMember.firstName}" /> <c:out
												value="${groupMember.lastName}" /></td>
									</tr>
									<tr style="font-weight: bold;">
										<td>No of Tickets</td>
										<td><c:out value="${fn:length(groupEventPasses)}" /></td>
									</tr>
								</tbody>
							</table>
						</div>
					</c:if>
				</div>
				<div class="tab-pane fade" id="tab3">Available Soon</div>
			</div>
		</div>
 -->

		<div class="panel-group" id="accordion2">
			<div class="panel panel-primary">
				<div class="panel-heading" href="#collapseOne" onclick="this.href='#collapseOne'" data-toggle="collapse"
						data-parent="#accordion2">
					<!--  <a class="btn btn-info" data-toggle="collapse"
						data-parent="#accordion2" href="#collapseOne">--> Seating for all
						your tickets <span class="badge badge-error"><c:out
								value="${fn:length(groupEventPasses)}" /> Ticket<c:if
								test="${fn:length(groupEventPasses) > 1}">s</c:if></span><span
						class="glyphicon glyphicon-menu-down pull-right" title="Click to Expand"></span>
					<!-- </a>  --> 
				</div>
				<div id="collapseOne" class="panel-collapse collapse in">
					<div class="panel-body">
						<div>Seating info for all your tickets</div>
						<div class="table-responsive">
							<table class="table table-bordered table-striped table-condensed">
								<thead>
									<tr style="font-weight: bold;" class="danger">
										<td>No.</td>
										<td>Ticket Number</td>
										<td>Table No.</td>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${groupEventPasses}" var="groupEventPass"
										varStatus="count">
										<tr>
											<td><c:out value="${count.index+1}" /></td>
											<td><c:out value="${groupEventPass.passBarcode}" /></td>
											<td><c:out value="${groupEventPass.tableNumber}" /></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
			<c:if test="${groupMember ne null }">
			<div class="panel panel-primary">
				<div class="panel-heading" href="#collapseTwo" data-toggle="collapse"
						data-parent="#accordion2"  onclick="this.href='#collapseTwo';">
					<!-- <a class="panel-toggle btn btn-info" data-toggle="collapse"
						data-parent="#accordion2" href="#collapseTwo"  --> Member Info <span
						class="badge badge-error"><c:out
								value="${groupMember.firstName}" /> <c:out
								value="${groupMember.lastName}" /></span><span
						class="glyphicon glyphicon-menu-down pull-right" title="Click to Expand"></span>
					<!-- </a>  -->
				</div>
				<div id="collapseTwo" class="panel-collapse collapse">
					<div class="panel-body">
						Member Info
						<c:if test="${groupMember ne null }">
							<div class="table-responsive">
								<table class="table table-bordered table-condensed">
									<tbody>
										<tr style="font-weight: bold;">
											<td>Member Name</td>
											<td><c:out value="${groupMember.firstName}" /> <c:out
													value="${groupMember.lastName}" /></td>
										</tr>
										<tr style="font-weight: bold;">
											<td>No of Tickets</td>
											<td><c:out value="${fn:length(groupEventPasses)}" /></td>
										</tr>
									</tbody>
								</table>
							</div>
						</c:if>
					</div>
				</div>
			</div>
			</c:if>

			<div class="panel panel-primary">
				<div class="panel-heading"  data-toggle="collapse"
						data-parent="#accordion2" href="#collapseThree" onclick="loadGroupContent('groupcontent1');">
				<!-- 	<a class="panel-toggle btn btn-info" data-toggle="collapse"
						data-parent="#accordion2" href="#collapseThree" onclick="loadGroupContent('groupcontent');"> --><span
						class="badge badge-error">Menu</span><span
						class="glyphicon glyphicon-menu-down pull-right" title="Click to Expand"></span>
				<!-- 	</a>  -->
				</div>
				<div id="collapseThree" class="panel-collapse collapse">
					<div class="panel-body"><div id = "groupcontent1" data-contentId="aae41932-3d7e-4601-bba3-170a74da748c"><div class="has-spinner active" style="cursor: inherit;"><span class="spinner"><i class="icon-spin glyphicon glyphicon-repeat"></i></span> Content loading...</div></div></div>
				</div>
			</div>
			<div class="panel panel-primary">
				<div class="panel-heading"  data-toggle="collapse"
						data-parent="#accordion2" href="#collapseFour" onclick="loadGroupContent('groupcontent2');">
				<!-- 	<a class="panel-toggle btn btn-info" data-toggle="collapse"
						data-parent="#accordion2" href="#collapseThree" onclick="loadGroupContent('groupcontent');"> --><span
						class="badge badge-error">Sponsors</span><span
						class="glyphicon glyphicon-menu-down pull-right" title="Click to Expand"></span>
				<!-- 	</a>  -->
				</div>
				<div id="collapseFour" class="panel-collapse collapse">
					<div class="panel-body"><div id = "groupcontent2" data-contentId="58823f7f-75ec-4109-9c96-db83e5f9df56"><div class="has-spinner active" style="cursor: inherit;"><span class="spinner"><i class="icon-spin glyphicon glyphicon-repeat"></i></span> Content loading...</div></div></div>
				</div>
			</div>
			<div class="panel panel-primary">
				<div class="panel-heading"  data-toggle="collapse"
						data-parent="#accordion2" href="#collapseFive" onclick="loadGroupContent('groupcontent3');">
				<!-- 	<a class="panel-toggle btn btn-info" data-toggle="collapse"
						data-parent="#accordion2" href="#collapseThree" onclick="loadGroupContent('groupcontent');"> --><span
						class="badge badge-error">Seating Map</span><span
						class="glyphicon glyphicon-menu-down pull-right" title="Click to Expand"></span>
				<!-- 	</a>  -->
				</div>
				<div id="collapseFive" class="panel-collapse collapse">
					<div class="panel-body"><div id = "groupcontent3" data-contentId="562538b3-6cc9-4b06-bb60-5e1c6e04474b"><div class="has-spinner active" style="cursor: inherit;"><span class="spinner"><i class="icon-spin glyphicon glyphicon-repeat"></i></span> Seating map loading...</div></div></div>
				</div>
			</div>	
			<div class="panel panel-primary">
				<div class="panel-heading"  data-toggle="collapse"
						data-parent="#accordion2" href="#collapseSix" onclick="loadGroupContent('groupcontent4');">
				<!-- 	<a class="panel-toggle btn btn-info" data-toggle="collapse"
						data-parent="#accordion2" href="#collapseThree" onclick="loadGroupContent('groupcontent');"> --><span
						class="badge badge-error">Feedback</span><span
						class="glyphicon glyphicon-menu-down pull-right" title="Click to Expand"></span>
				<!-- 	</a>  -->
				</div>
				<div id="collapseSix" class="panel-collapse collapse">
					<div class="panel-body"><div id = "groupcontent4" data-contentId="b207f443-0b5f-4865-9821-26ef78d69cce"><div class="has-spinner active" style="cursor: inherit;"><span class="spinner"><i class="icon-spin glyphicon glyphicon-repeat"></i></span> Content loading...</div></div></div>
				</div>
			</div>									
		</div>

	</c:if>

</div>

<script type="text/javascript">
	$(document).ready(
			function() {
				$("#searchSeating").validate(
						{
							rules : {
								value : {
									required : true
								}
							},
							errorClass : "control-group has-error",
							validClass : "control-group has-success",
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

			})
</script>
