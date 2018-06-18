/*function buildGroupEventsOptionsByMemberCategory(memberCategoryCode,
		htmlSelectId) {
	$.getJSON("json/viewGroupEvents/" + memberCategoryCode, function(j) {
		var options = '<option value="">Select One</option>"';

		for (var i = 0; i < j.length; i++) {
			options += '<option value="' + j[i].value + '">' + j[i].label
					+ '</option>';
		}

		$("select#" + htmlSelectId).html(options);
	});
};*/

$(function() {
	$('.form_datetime').datetimepicker({
		format : "dd/mm/yyyy hh:ii",
		showMeridian : true,
		autoclose : true,
		todayBtn : true,
		todayHighlight : true,
		minuteStep : 5
	});
	$('[data-toggle="popover"]').popover();
	/*		 $('[data-toggle="popover"]').popover({
	 html: true,
	 trigger: 'click',
	 content: loadEmailActivity,
	 }).click(function(e) {
	 //$(this).popover('toggle');
	 });*/
	$('[data-toggle="tooltip"]').tooltip();

})

function loadGroupContent(domId) {
	var contentId = $('#' + domId).attr('data-contentId');
	populateContent(contentId, domId);
}

function populateContent(contentId, domId) {
	$
			.ajax({
				url : "json/loadContent?contentId=" + contentId,
				dataType : 'json',
				async : true,
				success : function(groupContentList) {
					$('#' + domId)
							.html(
									"Sorry! This content is not available at the moment.");
					$
							.each(
									groupContentList,
									function(index, groupContent) {

										if (groupContent.active) {
											$('#' + domId).html(
													groupContent.pageContent);
										}

										else {
											$('#' + domId)
													.html(
															"Sorry! This content is not available at the moment.");
										}
									});
				}
			});
}

function loadEmailActivity() {
	var start;
	$
			.ajax({
				url : "json/viewGroupEmailAcivities/"
						+ $(this).attr('data-emailid'),
				dataType : 'json',
				async : false,
				success : function(j) {
					start = "<div><table class='table table-striped table-bordered'><tr><td><b>No</td><td><b>Activity</b></td><td><b>Activity By</b></td><td><b>Timestamp</b></td></tr>"
					$
							.each(
									j,
									function(index, groupEmailActivity) {
										start = start
												+ "<tr><td>"
												+ (index + 1)
												+ "</td><td>"
												+ groupEmailActivity.emailActivity
												+ "</td><td>"
												+ groupEmailActivity.activityBy
												+ "</td><td>"
												+ formatDateTimeMillis(groupEmailActivity.activityTime)
												+ "</td></tr>";
									});
				}
			});
	return start + "</table></div>";
}

function loadSMSActivity() {
	var start;
	$.ajax({
		url : "json/viewGroupSMSReplies/" + $(this).attr('data-smsid'),
		dataType : 'json',
		async : false,
		success : function(j) {
			start = "<div><table class='table table-bordered'>"
			$.each(j, function(index, groupInboundSMS) {
				start = start + "<tr><td><div class='alert alert-success'>"
						+ groupInboundSMS.smsContent + "</div></td></tr>";
			});
		}
	});
	return start + "</table></div>";
}

function buildGroupEventsOptionsByMemberCategory(memberCategoryCode,
		htmlSelectId) {
	$("select#" + htmlSelectId).attr("disabled", "disabled");
	$.ajax({
		type : 'GET',
		url : "json/viewGroupEvents",
		data : {
			memberCategoryCode : memberCategoryCode
		},
		success : function(j) {
			var options = '<option value="">Select One</option>"';
			if (typeof j !== 'undefined' && typeof j[0] !== 'undefined') {

				for (var i = 0; i < j.length; i++) {
					options += '<option value="' + j[i].value + '">'
							+ j[i].label + '</option>';
				}
			}
			$("select#" + htmlSelectId).html(options);
			$("select#" + htmlSelectId).removeAttr("disabled");
		},
		dataType : 'json',
		async : true
	});
};

/*
 * Note: The below function should be used only to get all events including the
 * expired events for something like evaluating feedback etc. Do not use this
 * otherwise
 */
function buildGroupEventsOptionsByMemberCategoryIncludingExpired(
		memberCategoryCode, htmlSelectId) {
	$("select#" + htmlSelectId).attr("disabled", "disabled");
	$.ajax({
		type : 'GET',
		url : "json/viewGroupEvents",
		data : {
			memberCategoryCode : memberCategoryCode,
			includeExpiredEvents : true
		},
		success : function(j) {
			var options = '<option value="">Select One</option>"';
			if (typeof j !== 'undefined' && typeof j[0] !== 'undefined') {

				for (var i = 0; i < j.length; i++) {
					options += '<option value="' + j[i].value + '">'
							+ j[i].label + '</option>';
				}
			}
			$("select#" + htmlSelectId).html(options);
			$("select#" + htmlSelectId).removeAttr("disabled");
		},
		dataType : 'json',
		async : true
	});
};

function buildGroupMemberCategoriesOptions(htmlSelectId) {
	var selectedDef = $("select#" + htmlSelectId).val();
	/*
	 * The above line get the value defined in the select tag E.g
	 * ${groupMember.memberCategoryCode} as assigns it to a temporary variable
	 * and then reset the value after creating drop down. Make sure in the
	 * respective jsp the value of "Select One" is assigned to
	 * ${groupMmeber.groupMemberCategory}
	 */
	$("select#" + htmlSelectId).attr("disabled", "disabled");
	$.getJSON("json/viewGroupMemberCategories", function(j) {
		var options = '<option value="">Select One</option>"';

		for (var i = 0; i < j.length; i++) {
			options += '<option value="' + j[i].memberCategoryCode + '">'
					+ j[i].memberCategoryName + '</option>';
		}

		$("select#" + htmlSelectId).html(options);
		$("select#" + htmlSelectId).val(selectedDef);
		$("select#" + htmlSelectId).removeAttr("disabled");

	})
};

/*
 * function buildGroupEmailTemplateOptionsByEventCode(groupEventCode,
 * htmlSelectId) { $.getJSON("json/viewGroupEmailTemplates/" + groupEventCode,
 * function(j) { var options = '<option value="">Select One</option>"';
 * 
 * for (var i = 0; i < j.length; i++) { options += '<option value="' +
 * j[i].value + '">' + j[i].label + '</option>'; }
 * 
 * $("select#" + htmlSelectId).html(options); }) }
 */
/*
 * function buildGroupEmailTemplateOptionsByEventCode(groupEventCode,
 * htmlSelectId) { $("select#"+htmlSelectId).attr("disabled","disabled");
 * $.ajax({ type : 'GET', url : "json/viewGroupEmailTemplates", data : {
 * groupEventCode : groupEventCode }, success : function(j) { var options = '<option
 * value="">Select One</option>"'; if (typeof j !== 'undefined' && typeof j[0]
 * !== 'undefined') { for (var i = 0; i < j.length; i++) { options += '<option
 * value="' + j[i].value + '">' + j[i].label + '</option>'; } } $("select#" +
 * htmlSelectId).html(options);
 * $("select#"+htmlSelectId).removeAttr("disabled"); }, dataType : 'json', async :
 * true }); }
 */

function buildGroupEmailTemplateOptionsByEventCode(groupEventCode, htmlSelectId) {
	var $select = $("select#" + htmlSelectId);
	$select.attr("disabled", "disabled");
	$.ajax({
		type : 'GET',
		url : "json/viewGroupEmailTemplates",
		data : {
			groupEventCode : groupEventCode
		},
		success : function(jsonData) {
			$select.html('');
			var $defaultOption = $("<option>", {
				text : 'Select One',
				value : ''
			});
			$defaultOption.appendTo($select);
			$.each(jsonData, function(groupName, options) {
				var $optgroup = $("<optgroup>", {
					label : groupName
				});
				$optgroup.appendTo($select);

				$.each(options, function(j, option) {
					var $option = $("<option>", {
						text : option.label,
						value : option.value
					});
					$option.appendTo($optgroup);
				});
			});
			$select.removeAttr("disabled");
		},
		dataType : 'json',
		async : true
	});
}

function buildGroupSMSTemplateOptionsByEventCode(groupEventCode, htmlSelectId) {
	var $select = $("select#" + htmlSelectId);
	$select.attr("disabled", "disabled");
	$.ajax({
		type : 'GET',
		url : "json/viewGroupSMSTemplates",
		data : {
			groupEventCode : groupEventCode
		},
		success : function(jsonData) {
			$select.html('');
			var $defaultOption = $("<option>", {
				text : 'Select One',
				value : ''
			});
			$defaultOption.appendTo($select);
			$.each(jsonData, function(groupName, options) {
				var $optgroup = $("<optgroup>", {
					label : groupName
				});
				$optgroup.appendTo($select);

				$.each(options, function(j, option) {
					var $option = $("<option>", {
						text : option.label,
						value : option.value
					});
					$option.appendTo($optgroup);
				});
			});
			$select.removeAttr("disabled");
		},
		dataType : 'json',
		async : true
	});
}




function buildGroupEventPassCategoryOptionsByEventCode(groupEventCode, htmlSelectId) {

	var $select = $("select#" + htmlSelectId);
	$select.attr("disabled", "disabled");
	$.ajax({
		type : 'GET',
		url : "json/viewGroupEventPassCategories",
		data : {
			groupEventCode : groupEventCode,
			includeNotAvailableForPurchase: true
		},
		success : function(jsonData) {
			$select.html('');
			var $defaultOption = $("<option>", {
				text : 'Select One',
				value : '0'
			});
			$defaultOption.appendTo($select);
				$.each(jsonData, function(j, option) {
					var $option = $("<option>", {
						text : option.passCategoryNameShort+" ($"+option.passPrice+")",
						value : option.groupEventPassCategoryId
					});
					$option.appendTo($select);
				});

			$select.removeAttr("disabled");
		},
		dataType : 'json',
		async : true
	});
}

function buildGroupEventPaymentTransactionsList(groupEventInviteId, htmlSelectId) {

	var $select = $("select#" + htmlSelectId);
	$select.attr("disabled", "disabled");
	$.ajax({
		type : 'GET',
		url : "json/viewGroupEventPaymentTransactions",
		data : {
			groupEventInviteId : groupEventInviteId,
			includeExpired: false
		},
		success : function(jsonData) {
			$select.html('');
			var $defaultOption = $("<option>", {
				text : 'Select One If Applicable',
				value : ''
			});
			$defaultOption.appendTo($select);
			var $newTransaction = $("<option>", {
				text : 'Create New Transaction',
				value : 'new'
			});
			$newTransaction.appendTo($select);
				$.each(jsonData, function(j, option) {
					var disabledOption = false;
					var clazz = "alert-info";
					if(option.paymentStatus==='CANCELLED' || option.paymentStatus==='EXPIRED')
					{
						disabledOption = true;
						clazz = "alert-danger";
					}
					var $option = $("<option>", {
						text : option.paymentStatus+" ("+formatDateTime(option.transactionDateTime)+")",
						value : option.transactionId,
						disabled: disabledOption,
						class: clazz
					});
					$option.appendTo($select);
				});

			$select.removeAttr("disabled");
		},
		dataType : 'json',
		async : true
	});
}


function buildGroupEmailAccountOptions(htmlSelectId) {
	$.ajax({
		type : 'GET',
		url : "json/viewGroupEmailAccounts",
		success : function(j) {
			var options = '<option value="">Select One</option>"';
			if (typeof j !== 'undefined' && typeof j[0] !== 'undefined') {
				for (var i = 0; i < j.length; i++) {
					options += '<option value="' + j[i].value + '">'
							+ j[i].label + '</option>';
				}

			}
			$("select#" + htmlSelectId).html(options);
		},
		dataType : 'json',
		async : true
	});
}

function buildGroupSMSAccountOptions(htmlSelectId) {
	$.ajax({
		type : 'GET',
		url : "json/viewGroupSMSAccounts",
		success : function(j) {
			var options = '<option value="">Select One</option>"';
			if (typeof j !== 'undefined' && typeof j[0] !== 'undefined') {
				for (var i = 0; i < j.length; i++) {
					options += '<option value="' + j[i].value + '">'
							+ j[i].label + '</option>';
				}

			}
			$("select#" + htmlSelectId).html(options);
		},
		dataType : 'json',
		async : true
	});
}


function postForm(formId, url) {
	var data = $('#' + formId).serialize();
	// $('input[type="submit"]').prop('disabled', true);
	// $('input[type="submit"]').prop('value', 'Please Wait...');
	toggleButton();
	$.ajax({
		type : "POST",
		url : url,
		data : data,
		// contentType: "application/json; charset=utf-8",
		success : function(response, textStatus, jqXHR) {
			loadMessageModal("Message",
					'<i class="glyphicon glyphicon-exclamation-sign"></i> '
							+ response);
			resetButton();
			// $('input[type="submit"]').button('reset');

		},
		error : function(jqXHR, textStatus, errorThrown) {
			loadMessageModal("Message", errorThrown);
			resetButton();
			// $('input[type="submit"]').button('loading');
		}
	});

}
function postFormToggleErrorModal(formId, url, hideElementOnSuccess, showElementOnSuccess ){

	hideById("modalalertBlock");
	var data = $('#' + formId).serialize();
	toggleButton();
	$.ajax({
		type : "POST",
		url : url,
		data : data,
		// contentType: "application/json; charset=utf-8",
		success : function(response, textStatus, jqXHR) {
			if ("success" !== response) {
				loadAlertMessageById(response, "modalalertMessage", "modalalertBlock");
			}
			else {
			//hideById(hideElementOnSuccess);
			//showById(showElementOnSuccess)
				}
			resetButton();
			// $('input[type="submit"]').button('reset');

		},
		error : function(jqXHR, textStatus, errorThrown) {
			loadAlertMessage(textStatus+":"+errorThrown, "modalalertMessage", "modalalertBlock");
			resetButton();
			// $('input[type="submit"]').button('loading');
		}
	});

	
}
function postFormAndToggleError(formId, url, hideElementOnSuccess, showElementOnSuccess ) {
	hideById("alertBlock");
	var data = $('#' + formId).serialize();
	toggleButton();
	$.ajax({
		type : "POST",
		url : url,
		data : data,
		// contentType: "application/json; charset=utf-8",
		success : function(response, textStatus, jqXHR) {
			if ("success" !== response) {
				loadAlertMessage(response);
			}
			else {
			hideById(hideElementOnSuccess);
			showById(showElementOnSuccess)}
			resetButton();
			// $('input[type="submit"]').button('reset');

		},
		error : function(jqXHR, textStatus, errorThrown) {
			loadAlertMessage(textStatus+":"+errorThrown);
			resetButton();
			// $('input[type="submit"]').button('loading');
		}
	});

}

function toggleButton() {
	toggleButtonWithId('');
}

function toggleButtonWithId(domId) {
	var selector = '#' + domId;
	if (domId === '') {
		selector = '';
	}

	$(selector + '.has-spinner').button('loading');
	$(selector + '.has-spinner').toggleClass('active');

}

function resetButton() {
	resetButtonWithId('');
}

function resetButtonWithId(domId) {
	var selector = '#' + domId;
	if (domId === '') {
		selector = '';
	}
	$(selector + '.has-spinner').removeClass('active');
	$(selector + '.has-spinner').button('reset');
}

function validateFormAndToggleButton(formName) {
	if (!$('#' + formName).valid()) {
		return false;
	}
	toggleButton();
	return true;
}

function loadMessageModal(title, body) {
	$("#myModalMessageTitle").html(title);
	$("#myModalMessageBody").html("<p>" + body + "</p>");
	loadModalMessage("");
}

function loadAlertMessage(message) {

	$("#alertMessage").html("<p>" + message + "</p>");
	$("#alertBlock").slideDown(400, "linear");
}

function loadAlertMessageById(message,messageId, htmlId) {

	$("#"+messageId).html("<p class='text-left'><i class='icon-exclamation-sign'> </i>" + message + "</p>");
	$("#"+htmlId).slideDown(400, "linear");
}

function fetchContentTemplateList(includeExpired) {
	var dropDown = '';
	$.ajax({
		type : 'GET',
		url : "json/listAvailableContent",
		data : {
			includeExpired : includeExpired
		},
		success : function(j) {
			var options = '';
			if (typeof j !== 'undefined' && typeof j[0] !== 'undefined') {
				for (var i = 0; i < j.length; i++) {
					options += ';' + j[i].contentURL + ':' + j[i].contentName
							+ ' (' + j[i].contentURL + ')';
				}
			}
			dropDown += dropDown + options;
		},
		dataType : 'json',
		async : false
	});
	return dropDown;
}

function datePick(element) {
	$(element).datepicker({
		format : "dd/mm/yyyy",
		autoclose : true,
		todayHighlight : true
	});
}


function datetimePick(element) {
	$(element).datetimepicker({
		format : "dd/mm/yyyy hh:ii:ss",
		showMeridian : true,
		autoclose : true,
		todayBtn : true,
		minuteStep : 5,
		pickerPosition : "bottom-left"
	});
}

function datePicker(id) {
	jQuery("#" + id + "_birthday").datepicker({
		format : "dd/mm/yyyy"
	});
}

function formatBoolean(cellValue, options, rowObject) {
	if (cellValue === true || cellValue === "Yes") {
		return 'Yes';
	} else {
		return 'No';
	}
}

function formatDate(cellValue, opts, rwd) {
	if (cellValue) {
		if (typeof cellValue.length != 'undefined') {
			return cellValue;
		}
		var x = $.fn.fmatter.call(this, "date", new Date(cellValue), opts, rwd);
		var dfg = new Date(x);
		return dfg.getDate() + "/" + (dfg.getMonth() + 1) + "/"
				+ dfg.getFullYear();
	} else {
		return '';
	}
}

function formatDateTime(cellValue, opts, rwd) {
	if (cellValue) {
		if (typeof cellValue.length != 'undefined') {
			return cellValue;
		}
		var x = $.fn.fmatter.call(this, "dateTime", new Date(cellValue), opts,
				rwd);
		var dfg = new Date(x);
		return addZero(dfg.getDate(), 2) + "/"
				+ addZero((dfg.getMonth() + 1), 2) + "/" + dfg.getFullYear()
				+ " " + addZero(dfg.getHours(), 2) + ":"
				+ addZero(dfg.getMinutes(), 2) + ":"
				+ addZero(dfg.getSeconds(), 2);
	} else {
		return '';
	}
}

function formatDateTimeMillis(cellValue, opts, rwd) {
	if (cellValue) {
		if (typeof cellValue.length != 'undefined') {
			return cellValue;
		}
		var x = $.fn.fmatter.call(this, "dateTime", new Date(cellValue), opts,
				rwd);
		var dfg = new Date(x);
		return addZero(dfg.getDate(), 2) + "/"
				+ addZero((dfg.getMonth() + 1), 2) + "/" + dfg.getFullYear()
				+ " " + addZero(dfg.getHours(), 2) + ":"
				+ addZero(dfg.getMinutes(), 2) + ":"
				+ addZero(dfg.getSeconds(), 2) + ":" + dfg.getMilliseconds();
	} else {
		return '';
	}
}

function isValidDates(startDate, expiryDate) {
	if (startDate != null && startDate > new Date()) {
		return false;
	}

	if (expiryDate != null && expiryDate < new Date()) {
		return false;
	}

	return true;
}

function addZero(x, n) {
	while (x.toString().length < n) {
		x = "0" + x;
	}
	return x;
}

function showById(id) {
	try {
		$('#' + id).show();
	} catch (Exception) {

	}
}

function hideById(id) {
	try {
		$('#' + id).hide();
	} catch (Exception) {

	}
}


function loadTotalSoldCountForEvent(cssSelector, groupEventCode) {
 	$.ajax({
 		type : 'GET',
 		url : "json/viewSoldTickets",
 		data : {
			groupEventCode : groupEventCode,
		},
 		success : function(j) {
 			var cnt = j.length;
			$("#" + cssSelector).fadeOut(500, function() {$("#" + cssSelector).html("<div>"+cnt+"</div>").fadeIn(1000)});
 			
 		},
 		async : true
 	});	
}

function loadSoldCountForCategory(cssSelector, groupEventCode, groupEventPassCategoryId) {
 	$.ajax({
 		type : 'GET',
 		url : "json/viewSoldTicketsForCategory",
 		data : {
			groupEventCode : groupEventCode,
			groupEventPassCategoryId : groupEventPassCategoryId
		},
 		success : function(j) {
 			var cnt = j.length;
			$("#" + cssSelector).fadeOut(500, function() {$("#" + cssSelector).html("<div>"+cnt+"</div>").fadeIn(1000)});
 			
 		},
 		async : true
 	});	
}


function getTotalSoldCountForEvent(groupEventCode, callback){
 	$.ajax({
 		type : 'GET',
 		url : "json/viewSoldTickets",
 		data : {
			groupEventCode : groupEventCode,
		},
 		success : function(j) {
 			var cnt = j.length;
 			callback(cnt);
 		},
 		async : true
 	});	
	}

function getSoldCountForCategory(groupEventCode, groupEventPassCategoryId, callback) {
 	$.ajax({
 		type : 'GET',
 		url : "json/viewSoldTicketsForCategory",
 		data : {
			groupEventCode : groupEventCode,
			groupEventPassCategoryId : groupEventPassCategoryId
		},
 		success : function(j) {
 			var cnt = j.length;
 			callback(cnt);
 			
 		},
 		async : true
 	});	
}


function decorateCount(cssSelector, cnt) {
	if(cssSelector!='')
	$("#" + cssSelector).fadeOut(500, function() {$("#" + cssSelector).html("<div>"+cnt+"</div>").fadeIn(1000)});
}

function getCount(cnt)
{
	alert(cnt);
	return cnt;
}



function getAuditLogs(requestURI, method ,callback) {
 	$.ajax({
 		type : 'GET',
 		url : "json/checkAuditLogs",
 		data : {
 			requestURI : requestURI,
			method : method
		},
 		success : function(j) {
 			var cnt = j.length;
 			callback(cnt);
 			
 		},
 		async : true
 	});	
}

