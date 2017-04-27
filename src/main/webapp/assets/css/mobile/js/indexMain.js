$(function() {
	$("#menu").mmenu({});
	bindingEvent();
	loadBaiduApiScript();
});
function bindingEvent(){
	$('.gotoPageUrlBefore').unbind('click').on( 'click',function () {
		gotoPageUrlBeforeFunc(this);
	 });
	$('.gotoPageUrl').unbind('click').on( 'click',function () {
		gotoPageUrlFunc(this);
	 });
}
function gotoPageUrlFunc(urlOfPage){
	window.location.href=$(urlOfPage).attr("gotoPageUrl");
}
function gotoPageUrlBeforeFunc(urlOfPage){
	urlOfGotoPage=$(urlOfPage).attr("gotoPageUrl");
	changeScreeningShowPage("indexOrgSelect","indexOrgSelect","indexOrgSelect","indexOrgSelect.html");
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
	describe();
}
//异步加载地图 end 

function describe(){ 
    publicAjaxRequest(system_url.stationLocationInformationDisplay, "", "", "", "",
		_structureItemsOnMap, null);
}
function _structureItemsOnMap(data){
	$.each(data.stationLocationBean, function(i, item) {
				var bssOrgId =item.bssOrgId;
				var latitude =item.latitude;
				var longitude =item.longitude;
				var bssOrgName=item.bssOrgName;
				var streetOfLocation=item.streetOfLocation;
				map.addOverlay(new ComplexCustomOverlay(new BMap.Point(latitude,longitude), bssOrgName,streetOfLocation,bssOrgId));
	});
}
function myFun(result){
	var cityName = result.name;
	map.setCenter(cityName);
	var top_right_navigation = new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_RIGHT, type: BMAP_NAVIGATION_CONTROL_SMALL}); //右上角，仅包含平移和缩放按钮
	map.setZoom(10);    
	map.addControl(top_right_navigation); 
}
//复杂的自定义覆盖物
function ComplexCustomOverlay(point, text, mouseoverText,station){
  this._point = point;
  this._text = text;
  this._overText = mouseoverText;
  this._station=station;
}
ComplexCustomOverlay.prototype = new BMap.Overlay();
ComplexCustomOverlay.prototype.initialize = function(map){
  this._map = map;
  var div = this._div = document.createElement("div");
  div.style.position = "absolute";
  div.style.zIndex = BMap.Overlay.getZIndex(this._point.lat);
  div.style.backgroundColor = "#1a2226";
  div.style.border = "1px solid #1e282c";
  div.style.color = "white";
  div.style.padding = "2px";
  div.style.lineHeight = "18px";
  div.style.whiteSpace = "nowrap";
  div.style.MozUserSelect = "none";
  div.style.fontSize = "16px";
  div.station=this._station;
  var span = this._span = document.createElement("span");
  div.appendChild(span);
  span.appendChild(document.createTextNode(this._text));      
  var that = this;

  var arrow = this._arrow = document.createElement("div");
  arrow.style.background = "url(${contextPath}/assets/images/index/label.png) no-repeat";
  arrow.style.position = "absolute";
  arrow.style.width = "11px";
  arrow.style.height = "10px";
  arrow.style.top = "22px";
  arrow.style.left = "10px";
  arrow.style.overflow = "hidden";
  div.appendChild(arrow);
 
  div.onmouseover = function(){
    this.style.backgroundColor = "#6BADCA";
    this.style.borderColor = "#0000ff";
    this.getElementsByTagName("span")[0].innerHTML = that._overText;
    arrow.style.backgroundPosition = "0px -20px";
  }
  div.onmouseout = function(){
    this.style.backgroundColor = "#1a2226";
    this.style.borderColor = "#1e282c";
    this.getElementsByTagName("span")[0].innerHTML = that._text;
    arrow.style.backgroundPosition = "0px 0px";
  }
  //绑定手机端触碰div事件
  div.addEventListener("touchend", function(){
	   window.location.href= baseContextPath+"/analysisadmin/analysis/mobile/stationEquipment/stationEquipment.html?bssOrgId="+this.station; 
  });
  map.getPanes().labelPane.appendChild(div);
  return div;
}
ComplexCustomOverlay.prototype.draw = function(){
  var map = this._map;
  var pixel = map.pointToOverlayPixel(this._point);
  this._div.style.left = pixel.x - parseInt(this._arrow.style.left) + "px";
  this._div.style.top  = pixel.y - 30 + "px";
}
