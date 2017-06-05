<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${error}">
	<div class="alert alert-danger alert-dismissible">
		<a class="close" data-dismiss="alert">x</a><i
			class="glyphicon glyphicon-exclamation-sign"></i> ${errorMessage}
	</div>
</c:if>

<c:if test="${alert}">
	<div class="alert alert-warning alert-dismissible">
		<a class="close" data-dismiss="alert">x</a><i
			class="glyphicon glyphicon-exclamation-sign"></i> ${alertMessage}
	</div>
</c:if>

<c:if test="${success}">
	<div class="alert alert-success alert-dismissible">
		<a class="close" data-dismiss="alert">x</a><i
			class="glyphicon glyphicon-ok-sign"></i> ${successMessage}
	</div>
</c:if>

<c:if test="${info}">
	<div class="alert alert-info alert-dismissible">
		<a class="close" data-dismiss="alert">x</a><i
			class="glyphicon glyphicon-info-sign"></i> ${infoMessage}
	</div>
</c:if>

<c:if test="${infoWithAction}">
	<div class="alert alert-block alert-info fade in">
		<button class="close" data-dismiss="alert">&times;</button>
		<h4 class="alert-heading">${infoWithActionHeading}</h4>
		<p>
			<i class="glyphicon glyphicon-exclamation-sign"></i>
			${infoWithActionContent}
		</p>
		<p>
			<a class="btn btn-info" href="${infoWithActionPrimaryActionLink}">${infoWithActionPrimaryAction}</a>
			<a class="btn" href="${infoWithActionSecActionLink}">${infoWithActionSecAction}</a>
		</p>
	</div>
</c:if>


<div id="alertBlock" style="display: none;">
	<div class="alert alert-danger">
		<i class="icon-exclamation-sign"></i>
		<div id="alertMessage"></div>
	</div>
</div>



<!-- Modal Window Start -->
<div id="myModalMessage" class="modal fade" data-backdrop="static"
	tabindex="-1" role="dialog" aria-labelledby="myModalMessageLabel1"
	aria-hidden="true" align="center">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="btn btn-primary pull-right" data-dismiss="modal">
					X
				</button>
				<c:if test="${popupTitle ne ''}">
					<h3 class="modal-title" id="myModalMessageTitle">${popupTitle}</h3>
				</c:if>
			</div>
			<div class="modal-body">
				<div id="eHTMLMessageModal">
					<div id="modalMessageGrid">
						<div class="alert alert-danger">
							<div style="line-height: 27px; font-size: 18px;">
								<div id="myModalMessageBody">
									<i class="glyphicon glyphicon-exclamation-sign"></i>
									${popupMessage}
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" data-dismiss="modal">OK</button>
			</div>
		</div>
	</div>
</div>
<!-- Modal Window End-->


<script type="text/javascript">
	function loadModalMessage(html) {
		$('#myModalMessage').modal('show');

	}

	$(document).ready(function() {
		<c:if test="${popupModal}">loadModalMessage("");
		</c:if>
	});
</script>