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
	        format: "dd/mm/yyyy hh:ii",
	        showMeridian: true,
	        autoclose: true,
	        todayBtn: true,
	        todayHighlight: true,
	        minuteStep: 5
		});
		 $('[data-toggle="popover"]').popover();
		 $('[data-toggle="tooltip"]').tooltip();
		
	})
	
function buildGroupEventsOptionsByMemberCategory(memberCategoryCode,
		htmlSelectId) {
	$("select#"+htmlSelectId).attr("disabled","disabled");
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
			$("select#"+htmlSelectId).removeAttr("disabled");
		},
		dataType : 'json',
		async : true
	});
};

/* Note: The below function should be used only to get all events including the expired events for something like evaluating feedback etc. Do not use this otherwise*/
function buildGroupEventsOptionsByMemberCategoryIncludingExpired(
		memberCategoryCode, htmlSelectId) {
	$("select#"+htmlSelectId).attr("disabled","disabled");
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
			$("select#"+htmlSelectId).removeAttr("disabled");
		},
		dataType : 'json',
		async : true
	});
};

function buildGroupMemberCategoriesOptions(htmlSelectId) {
	var selectedDef = $("select#"+htmlSelectId).val();
	/*The above line get the value defined in the select tag E.g ${groupMember.memberCategoryCode} as assigns it to a temporary variable and then reset the value after creating drop down. Make sure in the respective jsp the value of "Select One" is assigned to ${groupMmeber.groupMemberCategory}*/
	$("select#"+htmlSelectId).attr("disabled","disabled");
	$.getJSON("json/viewGroupMemberCategories", function(j) {
		var options = '<option value="">Select One</option>"';

		for (var i = 0; i < j.length; i++) {
			options += '<option value="' + j[i].memberCategoryCode + '">'
					+ j[i].memberCategoryName + '</option>';
		}

		$("select#" + htmlSelectId).html(options);
		$("select#"+htmlSelectId).val(selectedDef);
		$("select#"+htmlSelectId).removeAttr("disabled");
		
	})
};

/*function buildGroupEmailTemplateOptionsByEventCode(groupEventCode, htmlSelectId) {
 $.getJSON("json/viewGroupEmailTemplates/" + groupEventCode, function(j) {
 var options = '<option value="">Select One</option>"';

 for (var i = 0; i < j.length; i++) {
 options += '<option value="' + j[i].value + '">' + j[i].label
 + '</option>';
 }

 $("select#" + htmlSelectId).html(options);
 })

 }
 */
/*function buildGroupEmailTemplateOptionsByEventCode(groupEventCode, htmlSelectId) {
	$("select#"+htmlSelectId).attr("disabled","disabled");
	$.ajax({
		type : 'GET',
		url : "json/viewGroupEmailTemplates",
		data : {
			groupEventCode : groupEventCode
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
			$("select#"+htmlSelectId).removeAttr("disabled");
		},
		dataType : 'json',
		async : true
	});
}*/

function buildGroupEmailTemplateOptionsByEventCode(groupEventCode, htmlSelectId) {
var $select = $("select#"+htmlSelectId);
	$select.attr("disabled","disabled");
	$.ajax({
		type : 'GET',
		url : "json/viewGroupEmailTemplates",
		data : {
			groupEventCode : groupEventCode
		},
		success : function(jsonData) {
			$select.html('');
			var $defaultOption = $("<option>", {text: 'Select One', value: ''});
			$defaultOption.appendTo($select);
	            $.each(jsonData, function(groupName, options) {
	                var $optgroup = $("<optgroup>", {label: groupName});
	                $optgroup.appendTo($select);

	                $.each(options, function(j, option) {
	                	var $option = $("<option>", {text: option.label, value: option.value});
	                    $option.appendTo($optgroup);
	                });
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

function postForm(formId, url)
{
	var data = $('#'+formId).serialize();
//	$('input[type="submit"]').prop('disabled', true);
//	$('input[type="submit"]').prop('value', 'Please Wait...');
	toggleButton();
    $.ajax({
        type: "POST",
        url: url,
        data: data,
        //contentType: "application/json; charset=utf-8",
        success: function(response, textStatus, jqXHR) {
        	loadMessageModal("Message", response);
        	resetButton();
        	//$('input[type="submit"]').button('reset');
            
        },
        error: function(jqXHR, textStatus, errorThrown) {
        	loadMessageModal("Message", jqXHR);
        	resetButton();
        //	$('input[type="submit"]').button('loading');
        }
    });

}


function toggleButton()
{
	toggleButtonWithId('');
}

function toggleButtonWithId(domId)
{
	var selector = '#'+domId;
	if(domId === '')
	{
		selector = '';
	}
	$(selector+'.has-spinner').button('loading');
	$(selector+'.has-spinner').toggleClass('active');
}

function resetButton()
{
	resetButtonWithId('');
}

function resetButtonWithId(domId)
{
	var selector = '#'+domId;
	if(domId === '')
	{
		selector = '';
	}
	$(selector+'.has-spinner').removeClass('active');
	$(selector+'.has-spinner').button('reset');
}

function validateFormAndToggleButton(formName)
{
	if(! $('#'+formName).valid())
	{
		return false;
	}
	toggleButton();
	return true;
}

function loadMessageModal(title, body)
{
    $("#myModalMessageTitle").html(title);
    $("#myModalMessageBody").html("<p>"+body+"</p>");
    loadModalMessage("");	
}

function fetchContentTemplateList(includeExpired)
{
	var dropDown = '';
	$.ajax({
		type : 'GET',
		url : "json/listAvailableContent",
		data : {
			includeExpired : true
		},
		success : function(j) {
			var options = '';
			if (typeof j !== 'undefined' && typeof j[0] !== 'undefined') {
				for (var i = 0; i < j.length; i++) {
					options += ';' + j[i].contentURL + ':'
							+ j[i].contentName + ' ('+j[i].contentURL+')';
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
		format : "dd/mm/yyyy"
	});
}

function datetimePick(element) {
	$(element).datetimepicker({
        format: "dd/mm/yyyy hh:ii",
        showMeridian: true,
        autoclose: true,
        todayBtn: true,
        minuteStep: 5,
        pickerPosition: "bottom-left"
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
		return dfg.getDate() + "/" + (dfg.getMonth() + 1) + "/"
				+ dfg.getFullYear() + " " + dfg.getHours() + ":"
				+ dfg.getMinutes();
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
		return dfg.getDate() + "/" + (dfg.getMonth() + 1) + "/"
				+ dfg.getFullYear() + " " + dfg.getHours() + ":"
				+ dfg.getMinutes() + ":" +dfg.getMilliseconds() ;
	} else {
		return '';
	}
}

function isValidDates(startDate, expiryDate)
{
	if(startDate!=null && startDate > new Date())
	{
		return false;
	}
	
	if(expiryDate !=null && expiryDate < new Date())
	{
		return false;
	}
	
	return true;
}