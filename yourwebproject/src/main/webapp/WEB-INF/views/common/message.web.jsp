<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${error}">
	<div class="alert alert-danger">
		<a class="close" data-dismiss="alert">x</a><i
			class="icon-exclamation-sign"></i> ${errorMessage}
	</div>
</c:if>

<c:if test="${alert}">
	<div class="alert">
		<a class="close" data-dismiss="alert">x</a><i
			class="icon-exclamation-sign"></i> ${alertMessage}
	</div>
</c:if>

<c:if test="${success}">
	<div class="alert alert-success">
		<a class="close" data-dismiss="alert">x</a><i
			class="icon-exclamation-sign"></i> ${successMessage}
	</div>
</c:if>

<c:if test="${info}">
	<div class="alert alert-info">
		<a class="close" data-dismiss="alert">x</a><i
			class="icon-exclamation-sign"></i> ${infoMessage}
	</div>
</c:if>

<c:if test="${infoWithAction}">
	<div class="alert alert-block alert-info fade in">
		<button class="close" data-dismiss="alert">&times;</button>
		<h4 class="alert-heading">${infoWithActionHeading}</h4>
		<p>
			<i class="icon-exclamation-sign"></i> ${infoWithActionContent}
		</p>
		<p>
			<a class="btn btn-info" href="${infoWithActionPrimaryActionLink}">${infoWithActionPrimaryAction}</a>
			<a class="btn" href="${infoWithActionSecActionLink}">${infoWithActionSecAction}</a>
		</p>
	</div>
</c:if>



<!-- Modal Window Start -->
<div id="myModalMessage" class="modal hide fade" data-backdrop="static"
	tabindex="-1" role="dialog" aria-labelledby="myModalMessageLabel1"
	aria-hidden="true" align="center">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">&times;</button>
		<c:if test="${popupTitle ne ''}">
			<h3 id="myModalMessageTitle">${popupTitle}</h3>
		</c:if>
	</div>
	<div class="modal-body">
		<div id="eHTMLMessageModal">
			<div id="modalMessageGrid">
				<div class="alert alert-error">
					<div style="line-height: 27px; font-size: 18px;">
						<div id="myModalMessageBody">
							<i class="icon-exclamation-sign"></i> ${popupMessage}
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- Modal Window End-->


<script type="text/javascript">
	function loadModalMessage(html) {
		$('#myModalMessage').modal('show').css({
			'width' : '50%',
			'margin-left' : 'auto',
			'margin-right' : 'auto',
			'left' : '25%'
		});

	}

	$(document).ready(function() {
		<c:if test="${popupModal}">loadModalMessage("");
		</c:if>
	});
</script>