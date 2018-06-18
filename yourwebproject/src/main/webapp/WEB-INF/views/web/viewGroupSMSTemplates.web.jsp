<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="row">
	<div class="span12">

		<!--  div class="hero-unit"> -->
		<h2>Group Email Templates</h2>
		<br />

		<div class="tabbable">
			<ul class="nav nav-tabs">
				<li class="active"><a href="#optionALL" data-toggle="tab">All
						SMS Templates </a></li>
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
		<!-- /div>  -->
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		loadGrid('gridALL', 'All SMS Templates');
	});

	function loadGrid(gridId, captionVal) {
		//	alert("gridId-->"+gridId+" groupMemCatCode-->"+groupMemCatCode+" groupEventCode-->"+groupEventCode+" captionVal-->"+captionVal)
		jQuery("#" + gridId).jqGrid({
			url : 'json/viewAllGroupSMSTemplates',
			datatype : "json",
			colModel : [ {
				label : 'Template Name',
				name : 'templateName',
				index : 'templateName',
				hidden : false,
				editable : false,
				width : 300,
				formatter : editTemplateContent
			}, {
				label : 'ID',
				name : 'id',
				index : 'id',
				hidden : true,
				editable : true,
				width : 20
			}, {
				label : 'SMS Acct',
				name : 'smsAccountCode',
				index : 'smsAccountCode',
				hidden : false,
				editable : false,
				width : 80
			},  {
				label : 'Event Code',
				name : 'groupEventCode',
				index : 'groupEventCode',
				hidden : false,
				editable : false,
				sortable : true,
				sorttype : 'string',
				width : 100
			},  {
				label : 'Mem. Category',
				name : 'memberCategoryCode',
				index : 'memberCategoryCode',
				hidden : false,
				editable : false,
				sortable : true,
				sorttype : 'string',
				width : 100
			} ],
			height : 300,
			rowNum : 100,
			rownumbers : true,
			rownumWidth : 25,
			width : 937,
			rowList : [ 100, 250, 500 ],
			pager : '#p' + gridId,
			//	sortname : 'firstName',
			autoencode : true,
			shrinkToFit : false,
			viewrecords : true,
			grouping : true,
			loadonce : true,
			groupingView : {
				groupField : [ 'groupEventCode' ],
				groupColumnShow : [ true ],
				groupText : [ '<b>{0} - {1} Template(s)</b>' ],
				groupCollapse : true
			},
			onSelectRow : function(id) {
			},
			editurl : "server.php",
			caption : captionVal
		});
		jQuery("#" + gridId).jqGrid('navGrid', "#p" + gridId, {
			edit : false,
			add : false,
			del : false
		});
		jQuery("#" + gridId).jqGrid('filterToolbar', {
			searchOperators : true
		});
	}
	
	function editTemplateContent(cellValue, options, rowdata, action)
	{
		return "<a href='addGroupSMSTemplate?id="+rowdata.id+"' >"+rowdata.templateName+"</a>";
	}

</script>
