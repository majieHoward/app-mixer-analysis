$(function(){
	bindingEvents();
	tripItem_structureTableData();
});

function bindingEvents(){
	$("#returnBackIndex").unbind('click').click(function() {
		changeScreeningShowPage("travelShowIndex","travelShowIndex","travelShowIndex","travelShowIndex.html");
	});
	$('#scroller ul').unbind('click').on( 'click', 'li', function () {
		tripItem_loadShowRealTimePosition(this);
    });
}
function tripItem_loadShowRealTimePosition(selectedObjectItem){
	var bssOrgId=$(selectedObjectItem).attr("select_bssOrg_id");
	window.location.href=urlOfGotoPage+"?bssOrgId="+bssOrgId;
}
function tripItem_structureTableData(){
	
	$("#thelist_tripItem").html(sessionStorage.getItem("htmlOfSelectOrg"));
}