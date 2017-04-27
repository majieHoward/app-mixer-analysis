var markerArr;
var timedTask;
$(function(){
	bindingPageTitle();
	bindingEvents();
	loadBaiduApiScript();
});
function bindingPageTitle(){
	$("#titleOfItemPage").html(itemPageTitle);
}
function bindingEvents(){
	$("#returnBackIndex").unbind('click').click(function() {
		 clearInterval(timedTask);
		 changeScreeningShowPage("pageIndex","stationEquipmentIndex","stationEquipmentIndex","stationEquipmentIndex.html");
	});
}
//异步加载地图 start 
function loadBaiduApiScript(){
	var script = document.createElement("script");
	script.type = "text/javascript";
	script.src = "http://api.map.baidu.com/api?v=2.0&ak=G7MHOzoRhSGrTYNra76XcMd3&callback=initBaiduMap";
	document.body.appendChild(script);
}
var map;
function initBaiduMap(){
	map = new BMap.Map("allmap");
	var point = new BMap.Point(116.331398,39.897445);
	map.centerAndZoom(point,12);
	var geolocation = new BMap.Geolocation();
	geolocation.getCurrentPosition(function(r){
		if(this.getStatus() == BMAP_STATUS_SUCCESS){
			var mk = new BMap.Marker(r.point);
			map.panTo(r.point);
		}
		else {
		}        
	},{enableHighAccuracy: true});
	var myCity = new BMap.LocalCity();
	myCity.get(myFun);
}
function myFun(result){
	var cityName = result.name;
	map.setCenter(cityName);
	//alert("当前定位城市:"+cityName);
	var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
	var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
	var top_right_navigation = new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_RIGHT, type: BMAP_NAVIGATION_CONTROL_SMALL}); //右上角，仅包含平移和缩放按钮
	/*缩放控件type有四种类型:
	BMAP_NAVIGATION_CONTROL_SMALL：仅包含平移和缩放按钮；BMAP_NAVIGATION_CONTROL_PAN:仅包含平移按钮；BMAP_NAVIGATION_CONTROL_ZOOM：仅包含缩放按钮*/
	//添加控件和比例尺
	var overView = new BMap.OverviewMapControl();
	var overViewOpen = new BMap.OverviewMapControl({isOpen:true, anchor: BMAP_ANCHOR_BOTTOM_RIGHT});
	//启用滚轮放大缩小
	map.enableScrollWheelZoom(true);
	map.addControl(top_left_control);        
	map.addControl(top_left_navigation);     
	map.addControl(top_right_navigation); 
	map.addControl(overView);          //添加默认缩略地图控件
	map.addControl(overViewOpen); 
	map.setZoom(10); 
	//右下角，打开
	verifyTheLoginInformation();
	LoopLocation(); //指定1秒刷新一次 
}
function LoopLocation(){
	timedTask=setInterval(verifyTheLoginInformation,100000); //指定1秒刷新一次 
}

function verifyTheLoginInformation(){
	$.ajax({
		type: "GET",
		url: baseContextPath+"/analysisadmin/analysis/analysisRealtimeInformation/analysisRealtimeInformation.html?bssOrgId="+parameters.bssOrgId+"&carId="+parameters.carId,
		dataType: "json",
		success: function(data){
			markerArr=data.aaData;
			addMarker();
		}
	});
}
//创建marker
function addMarker(){
	 map.clearOverlays();
	 //markerArr = [{"title":"川A97568","content":"川A97568当前位置","point":"104.026257|30.763964","isOpen":0,"icon":{"w":21,"h":21,"l":0,"t":0,"x":6,"lb":5}}];
for(var i=0;i<markerArr.length;i++){
    var json = markerArr[i];
    var p0 = json.longitude;
    var p1 = json.latitude;
    var point = new BMap.Point(p0,p1);
		var iconImg = createIcon(json.icon);
    var marker = new BMap.Marker(point,{icon:iconImg});
		var iw = createInfoWindow(i);
		var label = new BMap.Label(json.carnbr,{"offset":new BMap.Size(json.icon.lb-json.icon.x+10,-20)});
		 label.setStyle({
           borderColor:"#808080",
           position : "absolute",
           color:"#333"
		});
		marker.setLabel(label);
        map.addOverlay(marker);
		marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
		(function(){
			var index = i;
			var _iw = createInfoWindow(i);
			var _marker = marker;
			_marker.addEventListener("click",function(){
			    this.openInfoWindow(_iw);
		    });
			label.addEventListener("click",function(){
			    _marker.openInfoWindow(_iw);
		    })
			if(!!json.isOpen){
				label.hide();
				_marker.openInfoWindow(_iw);
			}
		})()
}
}
//创建InfoWindow
function createInfoWindow(i){
var json = markerArr[i];
var iw = new BMap.InfoWindow("<b class='iw_poi_title' title='" + json.carnbr + "'>" + json.carnbr + "</b><div class='iw_poi_content'>"+json.carnbr+"</div>");
return iw;
}
//创建一个Icon
function createIcon(json){
var icon = new BMap.Icon("http://app.baidu.com/map/images/us_mk_icon.png", new BMap.Size(json.w,json.h),{imageOffset: new BMap.Size(-json.l,-json.t),infoWindowOffset:new BMap.Size(json.lb+10,1),offset:new BMap.Size(json.x,json.h)})
return icon;
}