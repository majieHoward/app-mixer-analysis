$(function(){
	bindingEvents();
	initChartInformation();
	handleQueryStatisticsCarId();
});
function bindingEvents(){
	$("#returnBackTripItemStatistics").unbind('click').click(function() {
	     backPage("pageIndex");
	});
	$("#queryStatisticsCarId").unbind('click').click(function() {
	     handleQueryStatisticsCarId();
	});
	$("#queryStatisticsOrg").unbind('click').click(function() {
	     handleQueryStatisticsOrg();
	});
	$("#filterStatistics").unbind('click').click(function() {
	     handleFilterStatistics();
	});
	$("#pageStatisticsDateSelectBorder").unbind('click').click(function() {
	     handlePageStatisticsDateSelectBorder();
	});
}
function handlePageStatisticsDateSelectBorder(){
	backPage("pageStatistics");
}
function handleFilterStatistics(){
	var $page = $("#pageStatisticsDateSelect");
	$page.css({'display':'block','visibility':'visible'});
	$("#pageStatisticsDateSelectBorder").css({'display':'block','visibility':'visible'});
}
function handleQueryStatisticsCarId(){

    $("#queryStatisticsCarId").css({color:"#d2d2d2"});
    $("#queryStatisticsOrg").css({color:"#ffffff"});
	loadChartInternalData();
}
function handleQueryStatisticsOrg(){
	 $("#queryStatisticsCarId").css({color:"#ffffff"});
    $("#queryStatisticsOrg").css({color:"#d2d2d2"});
    _queryMonthlyTrafficVolumeByBssOrg();
}
//初始化图表
function initChartInformation(){
	psLineChar = echarts.init(document.getElementById('psLine')); 
}
function _loadChartStart(){
 	 psLineChar.clear();  
     psLineChar.showLoading({text: '数据加载中'}); 
}
function _queryMonthlyTrafficVolumeByBssOrg(){
    _loadChartStart();
    var paramData={};
    paramData.bssOrgId=paramBssOrgId;
    publicAjaxRequest(system_url.monthlyTrafficVolumeByBssOrg, "", "", "", paramData,
		_showCharts, null);
}
//加载图表中数据
function loadChartInternalData(){
	 _loadChartStart();
     var paramData={};
     paramData.deviceId=paramServId;
     publicAjaxRequest(system_url.monthlyTrafficVolumeByCarId, "", "", "", paramData,
		_showCharts, null);
}
function _showCharts(data){
	psLineChar.setOption(data, true);  
    psLineChar.hideLoading(); 
}
