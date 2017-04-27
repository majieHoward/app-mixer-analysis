var carIdTripItem;
$(function(){
	carIdTripItem=$("#tripItemcarId").val();
	bindingEvents();
	loadBaiduApiScript();
});
function bindingEvents(){
	$("#returnBackTripItemLocation").unbind('click').click(function() {
	     backPage("pageIndex");
	});
}
//异步加载地图 start 
function loadBaiduApiScript(){
	var script = document.createElement("script");
	script.type = "text/javascript";
	script.src = "http://api.map.baidu.com/api?v=2.0&ak=G7MHOzoRhSGrTYNra76XcMd3&callback=initBaiduMap";
	document.body.appendChild(script);
}
function initBaiduMap(){
	map = new BMap.Map("allmap", {});
	var myCity = new BMap.LocalCity();
	myCity.get(myFun);
}
//异步加载地图 end 
function describe(){ 
	publicAjaxRequest(system_url.realTimePositionOfCarByCarId+"?carId="+carIdTripItem, "", "", "", "",
		_structureItemsOnMap, null);
}
function _structureItemsOnMap(data){
	var carRealTimePositioningItems=data.carRealTimePositioningItems;
	$.each(carRealTimePositioningItems, function(i, item) {
	    rs.push(item);
		showStars(item);
	});
}

function myFun(result){
    var point = new BMap.Point(result.center.lng,result.center.lat);
	map.centerAndZoom(point,12);
	map.enableScrollWheelZoom(true);
	var  mapStyle ={ 
		features: ["road", "building","water","land"],//隐藏地图上的poi
		style : "dark"  //设置地图风格为高端黑
	}
	map.setMapStyle(mapStyle);
	project = map.getMapType().getProjection();
	var bounds = map.getBounds();
	var sw = bounds.getSouthWest();
	var ne = bounds.getNorthEast();
	sw = project.lngLatToPoint(new BMap.Point(sw.lng, sw.lat));
	ne = project.lngLatToPoint(new BMap.Point(ne.lng, ne.lat));
	original = {
    x: sw.x,
    y: ne.y
    };
	var myCompOverlay = new ComplexCustomOverlay(new BMap.Point(result.center.lng,result.center.lat));
	map.addOverlay(myCompOverlay);
	map.setZoom(10);
	render();
	startCbk(nowTimeCbk);
	describe();
}
var BW = 0,
//canvas width
BH = 0,
//canvas height
ctx = null,
stars = [],
//存储所有星星对象的数组
timer = null,
//定时器
timeLine = null,
//时间轴对象
rs = [],
//最新的结果
isNowTimeData = false,
//是否显示当前时间的定位情况
py = null,
//偏移
gridWidth = 10000,
//网格的大小
isOverlay = false,
//是否叠加
//gridWidth   = 1,//网格的大小
canvas = null; //偏移
var original ;
var project;
function Star(options) {
    this.init(options);
}

Star.prototype.init = function(options) {
    this.x = ~~ (options.x);
    this.y = ~~ (options.y);
    this.initSize(options.size);
    if (~~ (0.5 + Math.random() * 10) == 1) {
        this.size = 0;
    } else {
        this.size = this.maxSize;
    }
}

Star.prototype.initSize = function(size) {
    var size = ~~ (size);
    this.maxSize = size > 10 ? 10 : size;
}

Star.prototype.render = function(i) {
    var p = this;
    console.log(p.x < 0 || p.y < 0 || p.x > BW || p.y > BH);
    if (p.x < 0 || p.y < 0 || p.x > BW || p.y > BH) {
        return;
    }

    ctx.beginPath();
    var gradient = ctx.createRadialGradient(p.x, p.y, 0, p.x, p.y, p.size);
    gradient.addColorStop(0, "rgba(7,120,249,1)");
    gradient.addColorStop(1, "rgba(7,120,249,0.3)");
    ctx.fillStyle = gradient;
    ctx.arc(p.x, p.y, p.size, Math.PI * 2, false);
    ctx.fill();
    if (~~ (0.5 + Math.random() * 7) == 1) {
        p.size = 0;
    } else {
        p.size = p.maxSize;
    }
}

function render() {
    renderAction();
    setTimeout(render, 180);
}

function renderAction() {
    ctx.clearRect(0, 0, BW, BH);
    ctx.globalCompositeOperation = "lighter";
    for (var i = 0,
    len = stars.length; i < len; ++i) {
        if (stars[i]) {
            stars[i].render(i);
        }
    }
}

// 复杂的自定义覆盖物
function ComplexCustomOverlay(point) {
    this._point = point;
}
ComplexCustomOverlay.prototype = new BMap.Overlay();
ComplexCustomOverlay.prototype.initialize = function(map) {
    this._map = map;
    canvas = this.canvas = document.createElement("canvas");
    canvas.style.cssText = "position:absolute;left:0;top:0;";
    ctx = canvas.getContext("2d");
    var size = map.getSize();
    canvas.width = BW = size.width;
    canvas.height = BH = size.height;
    map.getPanes().labelPane.appendChild(canvas);
    //map.getContainer().appendChild(canvas);
    return this.canvas;
}
ComplexCustomOverlay.prototype.draw = function() {
    var map = this._map;
    var bounds = map.getBounds();
    var sw = bounds.getSouthWest();
    var ne = bounds.getNorthEast();
    var pixel = map.pointToOverlayPixel(new BMap.Point(sw.lng, ne.lat));
    py = pixel;
    if (rs.length > 0) {
          $.each(rs,function(n,value) {  
              showStars(value);
          });  
		
	}
}



/**
 * 请求定位数据,并在地图上绘制出
 * @param 请求的时间
 * @param 成功后执行的回调函数
 * 
 */
var requestMgr = {
   
}

//显示星星
function showStars(data) {
stars.length = 0;
    var temp = {};
    var x = data.longitude;
    var y = data.latitude;
    var point = new BMap.Point(x, y);
    var px = map.pointToOverlayPixel(point);
    //create all stars
    var s = new Star({
        x: px.x - py.x,
        y: px.y - py.y,
        size: 10
    });
      console.log(s);
    stars.push(s);
    canvas.style.left = py.x + "px";
    canvas.style.top = py.y + "px";
    renderAction();
}


function nowTimeCbk(time) {
   
}
function startCbk(cbk) {
    var now = new Date();
    var time = {
        hour: now.getHours(),
        minute: now.getMinutes(),
        second: now.getSeconds()
    };
    if (cbk) {
        cbk(time);
    }
};
startCbk(nowTimeCbk);