
var date = new Date(); //实例化日期对象
var year = date.getFullYear(); //获取年份
var month = date.getMonth()+1; //获取月份
var day = date.getDate(); //获取日份
var h = date.getHours(); //获取小时
var i = date.getMinutes(); //获取分钟
var s = date.getSeconds(); //获取秒
var end = getDateStr(1); //获取明天日期

/*
 * 小于10在前面补零
 * num  整数
 */
function zeroFill (num) {
	if(num < 10){
		num = "0"+num;
	}
	return num;
}

function getDateStr(dayCount){  
    if(null == dayCount){  
        dayCount = 0;  
    }  
	var dd = new Date();  
	dd.setDate(dd.getDate()+dayCount);//设置日期  
	var y = dd.getFullYear();  
	var m = dd.getMonth()+1;//获取当前月份的日期  
	var d = dd.getDate();
	m=zeroFill(m);
	d=zeroFill(d);
	return y+"-"+m+"-"+d;  
} 

//var  newDay = new Date(year,month,0);
//var lastdate = year + '-' + month + '-' + newDay.getDate(); //获取本月最后一天

/*
 * getDaySlot () 获取当天所在周的周一至当天的时间段
 * return (array)dayslot
 */
function getDaySlot () {
	
	var now = new Date(); 
	var nowTime = now.getTime() ; 
	var day = now.getDay();
	var oneDayLong = 24*60*60*1000 ; 
	var MondayTime = nowTime - (day-1)*oneDayLong; 
	var SundayTime =  nowTime + (7-day)*oneDayLong; 
	
	var monday = formatDate(new Date(MondayTime)); //本周周一日期
	//var sunday = new Date(SundayTime);  //本周周日日期
	
	var dayslot = new Array(monday, end);
	return dayslot;
}

/*
 * 把时间戳换成时间格式
 */
function formatDate(now) {
	var year=now.getFullYear();
	var month=now.getMonth()+1;
	var day=now.getDate();
	var hour=now.getHours();
	var minute=now.getMinutes();
	var second=now.getSeconds();
	month=zeroFill(month);
	day = zeroFill(day);
	return year+"-"+month+"-"+day;
}


/*
 * getMonthSlot () 获取当天所在月的1号至当天的时间段
 * return (array)dayslot
 */
function getMonthSlot () {
	month=zeroFill(month);
	var startdate = year + '-' + month + '-01'; //获取本月第一天
	var dayslot = new Array(startdate,end);
	return dayslot;
}

/*
 * getYearSlot () 获取当天所在年份的1月1号至当天的时间段
 * return (array)dayslot
 */
function getYearSlot () {
	var startdate = year + '-01-01'; //获取本年第一天
	var dayslot = new Array(startdate,end);
	return dayslot;
}


$(function () {
	var jupUrl = $('#jupUrl').val();
	
	$('#currDay').click(function () {
		var data = getDaySlot(),//本周时间段
			btnCurCode = $(this).attr('btnCurCode'),
			btnCur = 1;
		if(btnCurCode != 1){
			//调用添加动画方法
			loadAnmt.addDom();
			jump(jupUrl, data[0], data[1], btnCur);
			//移除动画元素
			loadAnmt.rmvLoad();
		}
	});
	
	$('#currMonth').click(function () {
		var data = getMonthSlot(), //本月时间段
			btnCurCode = $(this).attr('btnCurCode'),
			btnCur = 2;
		if(btnCurCode != 1){
			//调用添加动画方法
			loadAnmt.addDom();
			jump(jupUrl, data[0], data[1], btnCur);
			//移除动画元素
			loadAnmt.rmvLoad();
		}
	});
	
	$('#currYear').click(function () {
		var data = getYearSlot(), //本年时间段
			btnCurCode = $(this).attr('btnCurCode'),
			btnCur = 3;
		if(btnCurCode != 1){
			//调用添加动画方法
			loadAnmt.addDom();
			jump(jupUrl, data[0], data[1], btnCur);
			//移除动画元素
			loadAnmt.rmvLoad();
		}
	});
	
	//layui时间控件
	layui.use(['layer', 'laypage', 'laydate'], function(){ //独立版的layer无需执行这一句
		var laydate = layui.laydate,
			layer = layui.layer;
		
		//执行一个laydate实例
		laydate.render({
		    elem: '#timeStart' //指定元素
		    ,showBottom: false
		    
		});

		//执行一个laydate实例
		laydate.render({
		    elem: '#timeEnd' //指定元素
		    ,showBottom: false
		    
		});
		
	});
});

function jumpUrl (obj) {
	var startDate = $('#timeStart').val(), //开始时间
		startEnd = $('#timeEnd').val(),  //结束时间
		btnCur = 0,
		jupUrl = $('#jupUrl').val();
		
	if(startDate=='' || startEnd==''){
		layer.alert('开始或结束时间不能为空55!',{icon:2});
	}else{
		var result = Comparison (startDate, startEnd);
		
		if(result){
			//调用添加动画方法
			loadAnmt.addDom();
			jump(jupUrl, startDate, startEnd, btnCur);
			//移除动画元素
			loadAnmt.rmvLoad();
		}
	}
	
}

//按日期查询数据方法地址
function jump (jupUrl, startDate, startEnd, btnCur) {
	window.location.href=jupUrl+'&startDate='+startDate+'&endDate='+startEnd+'&btnCur='+btnCur;
}

/*
 * 将日期格式为2018-01-01转为数字20180101
 */
function strToNum (dateNum) {
	var data = dateNum.replace(/-/g, "");
	return parseInt(data);
}

/*
 * 比较大小
 */
function Comparison (start, end) {
	var  objRegExp= /^(?:19|20)[0-9][0-9]-(?:(?:0[1-9])|(?:1[0-2]))-(?:(?:[0-2][1-9])|(?:[1-3][0-1]))$/;
   	//是否为日期并且格式为：YYYY-MM-DD
	if(objRegExp.test(start) && objRegExp.test(end)){
		var num1 = strToNum(start);
		var num2 = strToNum(end);
		if(num1 > num2){
			layer.alert('结束日期必须大于开始日期!',{icon:2});
			return false;
		}else{
			return true;
		}
	}else{
		layer.alert('请输入正确的日期格式，如:2018-01-01',{icon:2});
		return false;
	}
}