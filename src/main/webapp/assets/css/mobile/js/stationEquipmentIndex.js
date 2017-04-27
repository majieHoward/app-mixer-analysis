var OFFSET = 15;
//每页的页数
var pageSize=10
//起始页数
var startPage=1
$(function() {
    $("#menu").mmenu({});
	loaded();
	bindingEvent();
});
function bindingEvent(){
	$('.gotoPageUrlBefore').unbind('click').on( 'click',function () {
		gotoPageUrlBeforeFunc(this);
	 });
	$('.gotoPageUrl').unbind('click').on( 'click',function () {
		gotoPageUrlFunc(this);
	 });
	 $('#scroller ul').unbind('click').on( 'click', 'li', function () {
		handleSelfShowDetailClick(this);
    });
}
function handleSelfShowDetailClick(itemOfLi){
     var carId = $(itemOfLi).attr('carId');
     var bssOrgId=$(itemOfLi).attr('bssOrgId');
     var servId=$(itemOfLi).attr('servId');
     window.location.href= baseContextPath+"/analysisadmin/analysis/mobile/travelShow/travelShow.html?carId="+carId+"&bssOrgId="+bssOrgId+"&servId="+servId;  
}
function gotoPageUrlFunc(urlOfPage){
	window.location.href=$(urlOfPage).attr("gotoPageUrl");
}
function gotoPageUrlBeforeFunc(urlOfPage){
	urlOfGotoPage=$(urlOfPage).attr("gotoPageUrl");
	changeScreeningShowPage("indexOrgSelect","indexOrgSelect","indexOrgSelect","indexOrgSelect.html");
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
					}
				});
				$("#thelist").empty();
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
		"json");
}
function structureDataOfItems(gridData){
    var _htmlOfList;
    var addr;
    var bssOrg;
    var bssOrgName;
    var carId;
    var carNbr;
    var serialId;
    var servId;
    $.each(gridData, function(key, value) {
     _htmlOfList="";
        addr=value.addr;
        bssOrg=value.bssOrg;
        bssOrgName=value.bssOrgName;
        carId=value.carId;
        carNbr=value.carNbr;
        serialId=value.serialId;
        servId=value.servId;
        _htmlOfList+="<li class='selfShowDetail' "
                      +"carId='"+carId+"' "
                      +"bssOrgId='"+bssOrg+"' "
                      +"servId='"+servId+"' "
                      +">"
                      +carId+"<br/>"
                      +carNbr+"<br/>"
                      +serialId
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