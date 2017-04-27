function _screenSizefunctionA(){
	console.log($(window).width()); //浏览器当前窗口可视区域宽度
	return $(window).width();
}
function _screenSizefunctionB(){
	console.log($(document).width());//浏览器当前窗口文档对象宽度
	return $(document).width();
}
function _screenSizefunctionC(){
	console.log($(window).height()); //浏览器当前窗口可视区域高度
	return $(window).height();
}
function _screenSizefunctionD(){
	console.log($(document).height()); //浏览器当前窗口文档的高度
	return $(document).height();
}
function _screenSizefunctionE(){
	console.log($(document.body).height());//浏览器当前窗口文档body的高度
	return $(document.body).height();
}
function _screenSizefunctionF(){
	console.log($(document.body).outerHeight(true));//浏览器当前窗口文档body的总高度 包括border padding margin
    return $(document.body).outerHeight(true);
}
function _screenSizefunctionG(){
	console.log($(document.body).width());//浏览器当前窗口文档body的宽度
	return $(document.body).width();
}
function _screenSizefunctionH(){
	console.log($(document.body).outerWidth(true));//浏览器当前窗口文档body的总宽度 包括border padding margin
    return $(document.body).outerWidth(true);
}
function _getElementHeightA(parameter){
	// 获得的是该div本身的高度, (不包含padding,margin,border)
	return $(parameter).height();
}
function _getElementHeightB(parameter){
	 // 包含该div本身的高度, padding上下的高度, 以及border上下的高度(不包含margin的高度)
	return $(parameter).outerHeight();
}
function _getElementHeightC(parameter){
	// 包含该div本身的高度, 以及padding,border,margin上下的总高度
	return $(parameter).outerHeight(true);
}
function _getElementWidthA(parameter){
	// 获得的是该div本身的高度, (不包含padding,margin,border)
	return $(parameter).width();
}
function _getElementWidthB(parameter){
	 // 包含该div本身的高度, padding上下的高度, 以及border上下的高度(不包含margin的高度)
	return $(parameter).outerWidth();
}
function _getElementWidthC(parameter){
	// 包含该div本身的高度, 以及padding,border,margin上下的总高度
	return $(parameter).outerWidth(true);
}
//获得元素左边框的宽度

//获得元素右边框的宽度








