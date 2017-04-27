var map;
var operId;
var carId;
var deviceId;
var lushu;
var paramStartPoint={}
$(function(){
	carId=$("#_runningcarId").val();
	deviceId=$("#_runningdeviceId").val();
	bindingEvents();
	loadBaiduApiScript();
});
function bindingPageTitle(){
	
}
function handleNextStroke(){
    map.clearOverlays();
    _pictureCoordinatePoint(paramStartPoint);
	var param={}
	param.operId=operId;
	param.carId=carId;
	param.deviceId=deviceId;
	//查询当前operId对应的信息
	publicAjaxRequest(system_url.betweenTheTwoOperIdInformation, "", "", "", param,
		_showNextStroke, null);
}
function _showNextStroke(data){
    //下一个operId的信息
    var nextOperIdInfo=data.nextOperIdInfo;
    var adjacentNodes=data.adjacentNodes;
    var markPoints=adjacentNodes.markPoints;
    operId=nextOperIdInfo.operId;
    addCoordinatePoint(nextOperIdInfo);
    addluShuFunc(markPoints)
}
function bindingEvents(){
	$("#returnBackTripItem").unbind('click').click(function() {
	     backPage("pageIndex");
	});
}
//在地图上展示一个点并且将这个点做为中心
function addCoordinatePoint(data){
	paramStartPoint.longitude=data.longitude;
	paramStartPoint.latitude=data.latitude;
	paramStartPoint.title=data.jbbs+" "+data.weigthDiff;
	_pictureCoordinatePoint(paramStartPoint);
}
function addluShuFunc(data){
	//路书
	var points=[];
	  $.each(data, function (n, value) {
		 	points.push(new BMap.Point(value.longitudeShow, value.latitudeShow));
	  });
	  _startTempLuShu(points);
}
function _startTempLuShu(points){
	map.addOverlay(new BMap.Polyline(points, {strokeColor: '#CD0000'}));
	map.setViewport(points);
	lushu = new BMapLib.LuShu(map,points,{
		autoView:true,
		icon  : new BMap.Icon('http://developer.baidu.com/map/jsdemo/img/car.png', new BMap.Size(52,26),{anchor : new BMap.Size(27, 13)}),
		speed: 4000,
		enableRotation:true//是否设置marker随着道路的走向进行旋转
	}); 
	lushu.start();
}
//异步加载地图 start 
function loadBaiduApiScript(){
	var script = document.createElement("script");
	script.type = "text/javascript";
	script.src = "http://api.map.baidu.com/api?v=2.0&ak=G7MHOzoRhSGrTYNra76XcMd3&callback=initBaiduMap";
	document.body.appendChild(script);
}
function initBaiduMap(){
	map = new BMap.Map("allmap");
	map.setMapStyle({style:'bluish'});
	var point = new BMap.Point(116.331398,39.897445);
	map.centerAndZoom(point,12);
	//通过浏览器去进行定位
	var geolocation = new BMap.Geolocation();
	geolocation.getCurrentPosition(function(r){
		if(this.getStatus() == BMAP_STATUS_SUCCESS){
			var mk = new BMap.Marker(r.point);
			//map.panTo(r.point);
		}
		else {
		}        
	},{enableHighAccuracy: true});
	var myCity = new BMap.LocalCity();
	myCity.get(myFun);
}
function initBaiduMapEnd(){
    //如果operId和carId都不为空就去查看当前的operId所对应的这条记录
    operId=$("#_runningoperId").val();
    if(publicJudgmentObject(operId)==true&&publicJudgmentObject(carId)==true){
		var param={}
		param.operId=operId;
		param.carId=carId;
		//查询当前operId对应的信息
		_loadingOperPoint(param);
    }
   
}

function _loadingOperPoint(param){
    operId=param.operId;
    map.clearOverlays();
    publicAjaxRequest(system_url.currentOperIdInformation, "", "", "", param,
			addCoordinatePoint, null);
}
function _obtainTheNextOperIdInfo(){

}
// 定义一个控件类,即function
function ZoomControl(){
	// 默认停靠位置和偏移量
	this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;
	this.defaultOffset = new BMap.Size(10, 10);
}
// 通过JavaScript的prototype属性继承于BMap.Control
ZoomControl.prototype = new BMap.Control();
// 自定义控件必须实现自己的initialize方法,并且将控件的DOM元素返回
// 在本方法中创建个div元素作为控件的容器,并将其添加到地图容器中
ZoomControl.prototype.initialize = function(map){
	// 创建一个DOM元素
	var div = document.createElement("div");
	// 添加文字说明
	div.appendChild(document.createTextNode("查看下一段行程"));
	// 设置样式
	div.style.cursor = "pointer";
	 div.style.backgroundColor = "rgba(0, 0, 0, 0.5)";
  div.style.color = "white";
  div.style.padding = "2px";
  div.style.lineHeight = "30px";
  div.style.whiteSpace = "nowrap";
  div.style.MozUserSelect = "none";
  div.style.fontSize = "18px";
	// 绑定事件,点击一次放大两级
	div.onclick = function(e){
	   handleNextStroke();
	}
	//绑定手机端触碰div事件
    div.addEventListener("touchend", function(){
	    handleNextStroke();
	});
	// 添加DOM元素到地图中
	map.getContainer().appendChild(div);
	// 将DOM元素返回
	return div;
}
// 创建控件
function myFun(result){
	var cityName = result.name;
	// 创建控件
    var myZoomCtrl = new ZoomControl();
    // 添加到地图当中
    map.addControl(myZoomCtrl);
	map.setZoom(12);
	var top_left_navigation = new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_LEFT, type: BMAP_NAVIGATION_CONTROL_SMALL}); 
	map.addControl(top_left_navigation); 
	initBaiduMapEnd();
}

function _pictureCoordinatePoint(data){
	var point = new BMap.Point(data.longitude, data.latitude);
	var marker = new BMap.Marker(point);  // 创建标注
	map.addOverlay(marker);               // 将标注添加到地图中
	marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
	var opts = {
	  position : point,    // 指定文本标注所在的地理位置
	  offset   : new BMap.Size(30, -30)    //设置文本偏移量
	}
	var label = new BMap.Label(data.title, opts); 
	var bodyH=_screenSizefunctionC(); 
	var _fontSize=bodyH*0.03+"px";
	var _heigth=bodyH*0.035+"px";
	label.setStyle({
			 color : "black",
			 fontSize : _fontSize,
			 height : _heigth,
			 lineHeight : _heigth,
			 fontFamily:"Microsoft YaHei",
			 border: "1px solid #ddd",
			 background: "rgba(100,100,100,0.5)"
		 });
	marker.setLabel(label);
	setTimeout(function(){
		map.panTo(point);
	}, 10);
}