/**
 * 获表格中当前选中行的ID值
 */
function obtainDataGridSelectRowId(tableOfContainerName) {
	var selectGridDataId = $("#" + tableOfContainerName).jqGrid("getGridParam",
			'selrow');
	return selectGridDataId;
}
/**
 * 获取选中行的整行行数据
 * @param tableOfContainerName
 */
function obtainDataGridSelectRowData(tableOfContainerName){
	var selectGridDataId =obtainDataGridSelectRowId(tableOfContainerName);
	if (!selectGridDataId) {
		return;
	}
	var rowData = $("#" + tableOfContainerName).jqGrid("getRowData",
			selectGridDataId);
	return rowData;
}
/**
 * 获取选中行的整行行数据
 * 
 * @param tableOfContainerName
 * @param selectGridDataId
 * @returns
 */
function obtainDataGridSelectRowDataByGridDataId(tableOfContainerName, selectGridDataId) {
	if (!selectGridDataId) {
		return;
	}
	var rowData = $("#" + tableOfContainerName).jqGrid("getRowData",
			selectGridDataId);
	return rowData;
}
/**
 * 重新加载表格中的数据
 */
function reloadTableWorkData(tablegridName, purl, pparam) {
	// 获取表格参数
	$("#" + tablegridName).jqGrid('setGridParam', {
		url : purl,
		datatype : "json",// 初始化是为local，点击查询更改为json
		postData : pparam,// 传递需要的参数
		page : 1
	    // 点击查询时，需要设置当前页为第1页

	}).trigger('reloadGrid');
}
/**
 * 通过查询添加重新加载表格中的数据
 * 
 * @param tableGridName
 * @param querytableGridDataUrl
 */
function handleGridTableWorkQueryData(tableGridName, querytableGridDataUrl) {
	var paramData = obtainTermOfQueryDataGridData(tableGridName);
	queryDataGridDataThroughTerm(tableGridName, querytableGridDataUrl,
			paramData);
}
/**
 * 构造出查询条件
 * 
 * @param tableGridName
 * @returns {___anonymous1739_1740}
 */
function obtainTermOfQueryDataGridData(tableGridName) {
	var paramDataOfTable = {};
	var _paramOfSandboxJobNameQueryTableGrid = $(
			"#" + tableGridName + "QueryName").val();
	paramDataOfTable.querySandboxJobName = _paramOfSandboxJobNameQueryTableGrid;
	return paramDataOfTable;
}
/**
 * 根据查询条件重新加载出表格中的数据
 * 
 * @param tableGridName
 * @param querytableGridDataUrl
 * @param paramData
 */
function queryDataGridDataThroughTerm(tableGridName, querytableGridDataUrl,
		paramData) {
	reloadTableWorkData(tableGridName, querytableGridDataUrl, paramData);
}
/**
 * 为表格加载
 * 
 * @param tableOfContainerName
 * @param queryUrl
 * @param tablePagerOfContainerName
 * @param recordsNamespace
 */
function loadTableItemsData(tableOfContainerName, queryUrl,
		tablePagerOfContainerName, recordsNamespace,
		workEstablishedAtLevelColModel,selfOnSelectRow,selfOndblClickRow,paramId,tableWidth,tableHeight,_selfgridComplete) {
	$("#" + tableOfContainerName).jqGrid(
			{
				url : queryUrl,
				datatype : "json", // 数据来源，本地数据
				mtype : "POST",// 提交方式 GET,POST 可选
				height : tableHeight,// 高度，表格高度。可为数值、百分比或'auto'
				width : tableWidth,
				shrinkToFit : false,
				autoScroll : true,// 自动滚动条
				styleUI : "Bootstrap",
				colModel : workEstablishedAtLevelColModel,
				viewrecords : true,// 是否在浏览导航栏显示记录总数
				rowNum : 15,// 每页显示记录数,0:不返回数据，-1：返回全部数据
				rowList : [ 5, 15, 25, 50, 100 ],// 用于改变显示行数的下拉列表框的元素数组。
				prmNames : {
					page : "startPage",
					rows : "pageSize",
					search : "search",
					totalrows : "records" + recordsNamespace
				},
				jsonReader : {
					root : "gridData", // json中代表实际模型数据的入口
					page : "currPage", // json中代表当前页码的数据
					total : "totalPages", // json中代表页码总数的数据
					records : "totalRecords", // json中代表数据行总数的数据
					userdata : "userdata",
					id : "travelId",
					repeatitems : false
				// 如果设为false，则jqGrid在解析json时，会根据name来搜索对应的数据元素（即可以json中元素可以不按顺序）；而所使用的name是来自于colModel中的name设定。

				},
				pager : $('#' + tablePagerOfContainerName),
				beforeRequest : function() {

				},
				onSortCol : function(index, iCol, sortorder) {

				},
				ondblClickRow :function(rowid){
					if (selfOndblClickRow){
						selfOndblClickRow(rowid);
				    }
				},
				onSelectRow: function(id){ 
					 if (selfOnSelectRow){
						selfOnSelectRow(id);
				     }
				},
				loadBeforeSend : function(xhr, setting) {

				},
				serializeGridData : function(postData) {
					postData.records = $("#records" + recordsNamespace).val();
					return postData;
				},
				loadComplete : function(data) {
					if (data.success == "false") {
						bootbox.alert(data.info);
					} else {
						if (data.totalRecords != undefined) {
							$("#records" + recordsNamespace).val(
									data.totalRecords);
						}
					}
				},
				gridComplete : function() {
				   
				},
				loadError : function(xhr, status, error) {
					alert(error);
				},
				regional : 'cn'
			});
			$("#" + tableOfContainerName).navGrid('#' + tablePagerOfContainerName, {
				edit : false,
				add : false,
				del : false,
				search : false
			}).navButtonAdd(
			'#' + tablePagerOfContainerName,
			{
				caption : "查询SQL",
				buttonicon : "ui-icon-add",
				onClickButton : function() {
					var obj = $("#" + tableOfContainerName).jqGrid(
							'getGridParam', 'userData');
					if (obj.sql) {
						bootbox.alert(obj.sql);
					} else {
						
					}
				},
				title : "查询SQL",
				position : "last"
			});
}
/**
 * 设置表格宽度
 */
function setWidthPropertiesOfTheTable(tableGridName){
	$("#"+tableGridName).css('width', '100%');
	$("#gbox_"+tableGridName).css('width', '100%');
	$("#gview_"+tableGridName).css('width', '100%');
}
function widthPropertiesOfTheTable(tableGridName,changeWidth){
	$("#gbox_"+tableGridName).width(changeWidth);
	$("#gview_"+tableGridName).width(changeWidth);
	$("#"+tableGridName).width(changeWidth);
}