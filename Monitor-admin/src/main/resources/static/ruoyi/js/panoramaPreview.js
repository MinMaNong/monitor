/*
 js说明：该js用于panoramaPreview.html页面
 * */


/********************************************大华平台初始化***************************************************/
$(function(){
//	$.modal.loading("正在加载大华平台，请稍后...");
	var jiazai = layer.load(0, {
		  shade: [0.1,'#fff'] //0.1透明度的白色背景)
	});
	
	
	$.ajax({
		type:"post",
		contentType:"application/json;charset=UTF-8",
		url:ctx+"system/operate/initial",
		success:function(result){
//			$.modal.closeLoading();
			layer.close(jiazai);
			if(result.code == 0){
				console.log("成功");
			}else{
//				$.modal.closeLoading();
				$('.imgcode').click();
				$(".code").val("");
				$.modal.alertError(result.msg);
			}
		}
	})
	
	
	$.ajax({
		type:"post",
		contentType:"application/json;charset=UTF-8",
		url:ctx+"panoramaPreview/list",
		success:function(result){
			var channelname = result.data[5].channelname;
			var data = result.data[1].channelId;
			for (i = 0; i < result.data.length; i++) { 
			    if(result.data[i].status != 1){
//			    	$(result.data[i].channelId)).attr("src", "s-whole2.png");
			    	document.getElementById(result.data[i].channelId).src = "img/s-whole2.png";
			    }
			 }
//			$.each(data,function(index,domEle){
//				console.log(domEle);
//			})
//			data.forEach(function(element) {
//			    console.log(element);
//			});
//			$.modal.closeLoading();
//			layer.close(jiazai);
//			if(result.code == 0){
//				console.log("成功");
//			}else{
////				$.modal.closeLoading();
//				$('.imgcode').click();
//				$(".code").val("");
//				$.modal.alertError(result.msg);
//			}
		}
	})
})

/********************************************js获取本地时间***************************************************/
//说明：先用一个定时器在进入页面的时候就获取本地时间，然后销毁定时器，在销毁之前调用另一个一秒执行一次的定时器
var interval = setInterval(function(){
	var myDate = new Date();
	//时钟
	var hours = myDate.getHours();
	//分钟
	var minute = myDate.getMinutes();
	if(hours < 10){
		hours = "0"+hours;
	}
	if(minute < 10){
		minute = "0"+minute;
	}
	$(".time").text(hours+":"+minute);
	interval2;
	clearInterval(interval);
},0);
var interval2 = setInterval(function(){
	var myDate = new Date();
	//时钟
	var hours = myDate.getHours();
	//分钟
	var minute = myDate.getMinutes();
	if(hours < 10){
		hours = "0"+hours;
	}
	if(minute < 10){
		minute = "0"+minute;
	}
	$(".time").text(hours+":"+minute);
},1000);
/********************************************js获取本地时间-结束***************************************************/

/********************************************左边导航hover改变图片***************************************************/
//鼠标移入改变图片
$("#leftList div").mouseenter(function(){
	var LIndex = $(this).index();
	if (LIndex == 0 || LIndex == "0") {
		$(this).children("a").children("img").attr("src","img/l-qj.png");
	} else if(LIndex == 1 || LIndex == "1"){
		$(this).children("a").children("img").attr("src","img/l-sos.png");
	}
	else if(LIndex == 2 || LIndex == "2"){
		$(this).children("a").children("img").attr("src","img/l-tatistics.png");
	}
});
//鼠标移出恢复图片
$("#leftList div").mouseleave(function(){
	var LIndex = $(this).index();
	if (LIndex == 0 || LIndex == "0") {
		if($(this).is(".active")){
		}else{
			$(this).children("a").children("img").attr("src","../../img/l-qj2.png");
		}
	} else if(LIndex == 1 || LIndex == "1"){
		if($(this).is(".active")){
		}else{
			$(this).children("a").children("img").attr("src","../../img/l-sos2.png");
		}
	}
	else if(LIndex == 2 || LIndex == "2"){
		if($(this).is(".active")){
		}else{
			$(this).children("a").children("img").attr("src","../../img/l-tatistics2.png");
		}
	}
});
/********************************************左边导航hover改变图片-结束***************************************************/

/********************************************球机筛选按钮、控制球机图片***************************************************/
//whole：球机摄像头。half：半球机摄像头。myall：全景摄像头
//east：东边摄像头。south：南边摄像头。west：西边摄摄像头。north：北边摄像头。securityPost：保安亭摄像头。fireRoom:：消防室摄像头。monitor：监控室摄像头
$(".mold .mold_c").click(function(){//球机类型点击事件
	//删除兄弟级的class，为当前添加class=“active”
	$(this).siblings().removeClass("active");
	$(this).addClass("active");
	var arr =["../../img/s-whole2.png","../../img/s-whole.png","../../img/s-half2.png","../../img/s-half.png","../../img/s-qj2.png","../../img/s-qj.png","../../img/s-all2.png","../../img/s-all.png"];
	//把所有的图片还原成白色
	for(var i=0; i <= 3 ; i++){
		$(".mold .mold_c:eq(" + i +") img").attr("src",arr[i*2]);
	}
	//把点击的图片改成蓝色
	$(this).children("img").attr("src",arr[$(this).index()*2+1]);
	/****控制点击后摄像头的显示和隐藏****/
	var cInedx = $(this).index();
	if (cInedx == 0 || cInedx == "0") {
		$(".whole").show();
		$(".half,.myall").hide();
		//$(".myall").hide();
	} else if(cInedx == 1 || cInedx == "1"){
		$(".half").show();
		$(".whole,.myall").hide();
	} else if(cInedx == 2 || cInedx == "2"){
		$(".myall").show();
		$(".half,.whole").hide();
	} else if(cInedx == 3 || cInedx == "3"){
		$(".whole,.half,.myall").show(); 
	}
});
/******下拉框值改变，摄像头显示与隐藏******/
$("#uSelect").change(function() {//下拉框改变监听
	if ($(this).val() == "全部摄像头") {
		$("#camera .camera_img").show();
	} else if($(this).val() == "东边摄像头"){
		$("#camera .camera_img").hide();
		$("#camera .east").show();
	} else if($(this).val() == "南边摄像头"){
		$("#camera .camera_img").hide();
		$("#camera .south").show();
	} else if($(this).val() == "西边摄像头"){
		$("#camera .camera_img").hide();
		$("#camera .west").show();
	} else if($(this).val() == "北边摄像头"){
		$("#camera .camera_img").hide();
		$("#camera .north").show();
	} else if($(this).val() == "保安室岗亭摄像头"){
		$("#camera .camera_img").hide();
		$("#camera .securityPost").show();
	} else if($(this).val() == "消防室摄像头"){
		$("#camera .camera_img").hide();
		$("#camera .fireRoom").show();
	} else if($(this).val() == "监控室摄像头"){
		$("#camera .camera_img").hide();
		$("#camera .monitor").show();
	}
})
/********************************************球机筛选按钮、控制球机图-结束***************************************************/

/********************************************点击摄像头显示蒙层***************************************************/
//点击红外弹框-报警信息/实时视频
$("#camera .sensorRedGreen").click(function(){
	$(".eject").css({"display":"block"});
	$(".list").css({"display":"block"});
	$(".people").css({"display":"block"});
	//切换不同的报警类型时需要设置为 0
	clickNumber = 0;
	clickNumbers(clickNumber);
});
//点击摄像头弹框-实时视频
$("#camera .camera_img").click(function(){
	$(".eject").css({"display":"block"});
	$(".list").css({"display":"none"});
	$(".people").css({"display":"none"});
	//切换不同的报警类型时需要设置为 0
	clickNumber = 0;
	clickNumbers(clickNumber);
});
//点击实时视频制作右半边的×关闭蒙层
$(".ejectClose").click(function(){
	$(".eject").css({"display":"none"});
	clickNumber = 0;
	clickNumbers(clickNumber);
	console.log("切换不同的报警类型时需要设置为0："+clickNumber);
});
/********************************************点击摄像头显示蒙层-结束***************************************************/

/********************************************点击回放按钮显示实时视频制作模块***************************************************/
/****点击回放按钮显示实时视频制作模块****/
var state = false;
$(".error_return").bind("click","body",function() {
	$(".make").toggle(200);
	if (state == "true" || state == true) {
		$(".error_return").text("回放＞");
		state = false;
		console.log(state+'jingu')
	} else{
		$(".error_return").text("回放＜");
		state = true;
		console.log(state)
	}
})
/****点击回放按钮显示实时视频制作模块-结束****/
/****暂停和播放按钮****/

/****暂停和播放按钮-结束****/

/****人员入侵报警类型的任务派发人****/
$("#myselect_dropdown p").click(function() {
	var ul = $("#myselect_dropdown ul");
	if(ul.css("display") == "none") {
		ul.slideDown("fast");
	} else {
		ul.slideUp("fast");
	}
	$("#myselect_dropdown ul li a").click(function() {
		var txt = $(this).text();
		$("#myselect_dropdown p").html(txt);
		var value = $(this).attr("rel");
		$("#myselect_dropdown ul").hide();
		//值
		console.log("您选择了" + txt + "，值为：" + value);
	});
});
/****人员入侵报警类型的任务派发人-结束****/

/****轮播图左右按钮****/
//clickNumber为全局变量，当切换不同的报警类型时需要设置为 0
var clickNumber = 0; 
$(".bannerLeft").bind("click","body",function() {
	var liLength = $(".bContent ul li").length-4;
	if (clickNumber < liLength) {
		clickNumber ++;
		clickNumbers(clickNumber);
	}
});
$(".bannerRight").bind("click","body",function() {
	if (clickNumber >= 1) {
		clickNumber --;
		clickNumbers(clickNumber);
	}
});
function clickNumbers (value) {
	$(".bContent ul").css("margin-left","-"+value*75+"px");
}
/****轮播图左右按钮-结束****/

/****点击轮播图图片放大****/
$(".bContent ul li img").bind("click","body",function() {
	var thisSrc = $(this).attr("src");
	$(".ejectImgPlus").show();
	$(".ejectImgPlusM img").attr({"src":thisSrc});
	//通过改变z-index来改变左边导航的层次
	$("#leftList").css({"z-index":"10"});
});
/****点击轮播图图片放大-结束****/

/****点击轮播图中的关闭按钮****/
//关闭按钮
$(".ejectImgPlusClose").bind("click","body",function() {
	$(".ejectImgPlus").hide();
	//z-index该为20
	$("#leftList").css({"z-index":"20"});
});
/****点击轮播图中的关闭按钮-结束****/

/********************************************点击回放按钮显示实时视频制作模块-结束***************************************************/


