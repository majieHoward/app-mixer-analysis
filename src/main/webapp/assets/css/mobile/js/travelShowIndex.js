var OFFSET = 5;
//每页的页数
var pageSize=10;
//起始页数
var startPage=1;
// browser window scroll (in pixels) after which the "back to top" link is shown
var offset_top = 300,
//browser window scroll (in pixels) after which the "back to top" link opacity is reduced
offset_opacity = 1200,
//duration of the top scrolling animation (in ms)
scroll_top_duration = 700,
//grab the "back to top" link
$back_to_top = $('.cd-top');
$(function() {
    $("#menu").mmenu({});
    bindingEvent();
	loaded();
});
function bindingEvent(){
	$('.gotoPageUrlBefore').unbind('click').on( 'click',function () {
		gotoPageUrlBeforeFunc(this);
	 });
	$('.gotoPageUrl').unbind('click').on( 'click',function () {
		gotoPageUrlFunc(this);
	 });
	 $('.selfShowDetail').unbind('click').on( 'click',function () {
		handleSelfShowDetailClick(this);
	 });
	$back_to_top.unbind('click').on('click', function(event){
	    myScroll.scrollTo(0, 0, scroll_top_duration);
	});
	$('#scroller ul').unbind('click').on( 'click', 'li', function () {
		travelShowHandleSelfShowDetail(this);
    });
    $('#selfLocation').unbind('click').on( 'click',function () {
		  handleSelfLocation();
	 });
	 $('#selfStatistics').unbind('click').on( 'click',function () {
		  handleSelfStatistics();
	 });
}
function handleSelfLocation(){
     var url="travelShow/travelShowTripItem.html?carId="+paramCarId;
     changeScreeningShowPage("pageLocation","travelShowTripItem","travelShowTripItem",url);
}
function handleSelfStatistics(){
     changeScreeningShowPage("pageStatistics","queryStatisticsLineCharts","queryStatisticsLineCharts","dataStatistic/queryStatisticsLineCharts.html");
}
function travelShowHandleSelfShowDetail(itemOfLi){
    var carId = $(itemOfLi).attr('carId');
    var deviceId = $(itemOfLi).attr('deviceId');
    var operId = $(itemOfLi).attr('operId');
    var url="travelShow/travelShowTripRunning.html?carId="+carId+"&deviceId="+deviceId+"&operId="+operId;
	changeScreeningShowPage("pageMap","travelShowTripRunning","travelShowTripRunning",url);
}
function handleSelfShowDetailClick(itemOfLi){
     var carId = itemOfLi.attr('carId');
    // window.location.href= baseContextPath+"/analysisadmin/analysis/mobile/travelShow/travelShow.html?carId="+carId;  
}
function gotoPageUrlFunc(urlOfPage){
	window.location.href=$(urlOfPage).attr("gotoPageUrl");
}
function gotoPageUrlBeforeFunc(urlOfPage){
	urlOfGotoPage=$(urlOfPage).attr("gotoPageUrl");
}
var myScroll,
	pullDownEl, pullDownOffset,
	pullUpEl, pullUpOffset,
	generatedCount = 0;
var maxScrollY = 0;
var hasMoreData = false;
document.addEventListener('touchmove', function(e) {
	e.preventDefault();
}, false);

function loaded() {
    $("#pullDown").hide();
    $("#pullUp").hide();
	pullDownEl = document.getElementById('pullDown');
	//offsetHeight = 内容可视区域的高度+ 滚动条 + 边框
	pullDownOffset = pullDownEl.offsetHeight;
	pullUpEl = document.getElementById('pullUp');
	//offsetHeight = 内容可视区域的高度+ 滚动条 + 边框
	pullUpOffset = pullUpEl.offsetHeight;
	hasMoreData = false;
	$("#pullUp").hide();
	pullDownEl.className = 'loading';
	pullDownEl.querySelector('.pullDownLabel').innerHTML = 'Loading...';
	startPage = 1;
	$.post(
		workTravelCarSelectShowUrl, {
			"startPage": startPage,
			"pageSize": pageSize
		},
		function(response, status) {
			if (status == "success") {
				$("#thelist").show();
				if (response.gridData.length < pageSize) {
					hasMoreData = false;
					$("#pullUp").hide();
				} else {
					hasMoreData = true;
					//$(".pullUpLabel").html(startPage+"/"+response.totalPages);
					//$("#pullUp").show();
				}

				myScroll = new iScroll('wrapper', {
					useTransition: true,
					topOffset: pullDownOffset,
					onRefresh: function() {
						if (pullDownEl.className.match('loading')) {
							pullDownEl.className = 'idle';
							pullDownEl.querySelector('.pullDownLabel').innerHTML = 'Pull down to refresh...';
							this.minScrollY = -pullDownOffset;
						}
						if (pullUpEl.className.match('loading')) {
							pullUpEl.className = 'idle';
							pullUpEl.querySelector('.pullUpLabel').innerHTML = 'Pull up to load more...';
						}
					},
					onScrollMove: function() {
											console.log("this.y:"+this.y+"    "+"this.minScrollY:"+this.maxScrollY+"    "+"pullDownOffset:"+pullDownOffset );
					
						if (this.y > OFFSET && !pullDownEl.className.match('flip')) {
							pullDownEl.className = 'flip';
							pullDownEl.querySelector('.pullDownLabel').innerHTML = 'Release to refresh...';
							this.minScrollY = 0;
						} else if (this.y < OFFSET && pullDownEl.className.match('flip')) {
							pullDownEl.className = 'idle';
							pullDownEl.querySelector('.pullDownLabel').innerHTML = 'Pull down to refresh...';
							this.minScrollY = -pullDownOffset;
						} 
						if (this.y < (maxScrollY - pullUpOffset - OFFSET) && !pullUpEl.className.match('flip')) {
							if (hasMoreData) {
								this.maxScrollY = this.maxScrollY - pullUpOffset;
								pullUpEl.className = 'flip';
								pullUpEl.querySelector('.pullUpLabel').innerHTML = 'Release to refresh...';
							}
						} else if (this.y > (maxScrollY - pullUpOffset - OFFSET) && pullUpEl.className.match('flip')) {
							if (hasMoreData) {
								this.maxScrollY = maxScrollY;
								pullUpEl.className = 'idle';
								pullUpEl.querySelector('.pullUpLabel').innerHTML = 'Pull up to load more...';
							}
						}
						console.log(this.minScrollY);
					},
					onScrollEnd: function() {
						if (pullDownEl.className.match('flip')) {
							pullDownEl.className = '数据加载中';
							pullDownEl.querySelector('.pullDownLabel').innerHTML = 'Loading...';
							refresh();
						}
						if (hasMoreData && pullUpEl.className.match('flip')) {
							pullUpEl.className = '数据加载中';
							pullUpEl.querySelector('.pullUpLabel').innerHTML = 'Loading...';
							nextPage();
						}
						//scrollTop() 方法返回或设置匹配元素的滚动条的垂直位置
						( Math.abs(this.y) < offset_top ) ? $back_to_top.removeClass('cd-is-visible cd-fade-out'):$back_to_top.addClass('cd-is-visible');
						if( Math.abs(this.y) > offset_opacity ) { 
							$back_to_top.addClass('cd-fade-out');
						}
					}
				});
				$("#thelist").empty();
				structureDataOfItems(response.gridData);
				myScroll.refresh();
				alert(myScroll.maxScrollY);
				if (hasMoreData) {
					myScroll.maxScrollY = myScroll.maxScrollY + pullUpOffset;
				} else {
					myScroll.maxScrollY = myScroll.maxScrollY;
				}
				maxScrollY = myScroll.maxScrollY;
				alert(maxScrollY);
			};
		},
		"json");
}
function structureDataOfItems(gridData){
    var _htmlOfList;
    var carId;
    var dateOne;
    var dateTwo;
    var deviceId;
    var jbbs;
    var operId;
    var weightOne;
    var weigthDiff;
    $.each(gridData, function(key, value) {
     _htmlOfList="";
        carId=value.carId;
        dateOne=value.dateOne;
        dateTwo=value.dateTwo;
        deviceId=value.deviceId;
        jbbs=value.jbbs;
        operId=value.operId;
        weightOne=value.weightOne;
        weigthDiff=value.weigthDiff;
        
        _htmlOfList+="<li class='selfShowDetail'"
                      +"carId='"+carId+"' "
                      +"deviceId='"+deviceId+"' "
                      +"operId='"+operId+"' "
                      +">"
                      +carId+"<br/>"
                      +deviceId+"<br/>"
                      +dateOne+"<br/>"
                      +dateTwo+"<br/>"
                      +jbbs+"<br/>"
                      +weigthDiff+"<br/>"
                      +"</li>";
		$("#thelist").append(_htmlOfList);
	});
}
function refresh() {
    $("#pullDown").hide();
    /*
	startPage = 1;
	$.post(
		workTravelCarSelectShowUrl, {
			"startPage": startPage,
			"pageSize": pageSize
		},
		function(response, status) {
			if (status == "success") {
				$("#thelist").empty();

				myScroll.refresh();

				if (response.gridData.length < pageSize) {
					hasMoreData = false;
					$("#pullUp").hide();
				} else {
					hasMoreData = true;
					//$("#pullUp").show();
				}
				structureDataOfItems(response.gridData);
				myScroll.refresh();

				if (hasMoreData) {
					myScroll.maxScrollY = myScroll.maxScrollY + pullUpOffset;
				} else {
					myScroll.maxScrollY = myScroll.maxScrollY;
				}
				maxScrollY = myScroll.maxScrollY;
			};
		},
		"json");*/
}

function nextPage() {
	startPage++;
	$.post(
		workTravelCarSelectShowUrl, {
			"startPage": startPage,
			"pageSize": pageSize
		},
		function(response, status) {
			if (status == "success") {
				if (response.gridData.length < pageSize) {
					hasMoreData = false;
					$("#pullUp").hide();
				} else {
					hasMoreData = true;
					$(".pullUpLabel").html(startPage+"/"+response.totalPages);
					$("#pullUp").show();
				}
                structureDataOfItems(response.gridData);
				myScroll.refresh(); // Remember to refresh when contents are loaded (ie: on ajax completion)
				if (hasMoreData) {
					myScroll.maxScrollY = myScroll.maxScrollY + pullUpOffset;
				} else {
					myScroll.maxScrollY = myScroll.maxScrollY;
				}
				maxScrollY = myScroll.maxScrollY;
			};
		},
		"json");
}