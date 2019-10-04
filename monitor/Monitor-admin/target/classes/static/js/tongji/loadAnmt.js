//创建加载动画；
var loadAnmt=new LoadAmt();
function LoadAmt(){
	//获取HTML和body节点
	this.bdDom=document.getElementsByTagName("body")[0];
	this.htmDom=document.getElementsByTagName("html")[0];
	//动画样式；
	this.loadSty=".spinner{position:absolute;bottom:0;left:0;width:1742px;height:852px;background-color: rgba(0,0,0,0.6);}.loadShow{position:relative;margin:300px auto 0;width:100px;height:100px}.container1 > div,.container2 > div,.container3 > div{width:6px;height:6px;background-color:#fff;border-radius:100%;position:absolute;-webkit-animation:bouncedelay 1.2s infinite ease-in-out;animation:bouncedelay 1.2s infinite ease-in-out;-webkit-animation-fill-mode:both;animation-fill-mode:both}.spinner .spinner-container{position:absolute;width:100%;height:100%}.container2{-webkit-transform:rotateZ(45deg);transform:rotateZ(45deg)}.container3{-webkit-transform:rotateZ(90deg);transform:rotateZ(90deg)}.circle1{top:0;left:0}.circle2{top:0;right:0}.circle3{right:0;bottom:0}.circle4{left:0;bottom:0}.container2 .circle1{-webkit-animation-delay:-1.1s;animation-delay:-1.1s}.container3 .circle1{-webkit-animation-delay:-1.0s;animation-delay:-1.0s}.container1 .circle2{-webkit-animation-delay:-0.9s;animation-delay:-0.9s}.container2 .circle2{-webkit-animation-delay:-0.8s;animation-delay:-0.8s}.container3 .circle2{-webkit-animation-delay:-0.7s;animation-delay:-0.7s}.container1 .circle3{-webkit-animation-delay:-0.6s;animation-delay:-0.6s}.container2 .circle3{-webkit-animation-delay:-0.5s;animation-delay:-0.5s}.container3 .circle3{-webkit-animation-delay:-0.4s;animation-delay:-0.4s}.container1 .circle4{-webkit-animation-delay:-0.3s;animation-delay:-0.3s}.container2 .circle4{-webkit-animation-delay:-0.2s;animation-delay:-0.2s}.container3 .circle4{-webkit-animation-delay:-0.1s;animation-delay:-0.1s}@-webkit-keyframes bouncedelay{0%,100%,80%{-webkit-transform:scale(0.0)}40%{-webkit-transform:scale(5.0)}}@keyframes bouncedelay{0%,100%,80%{transform:scale(0.0);-webkit-transform:scale(0.0)}40%{transform:scale(5.0);-webkit-transform:scale(5.0)}}";
	//动画组件
	this.htmTxt="<div class='loadShow'><div class='spinner-container container1'>"
		+"<div class='circle1'></div>"
		+"<div class='circle2'></div>"
		+"<div class='circle3'></div>"
		+"<div class='circle4'></div>"
	+"</div>"
		+"<div class='spinner-container container2'>"
		+"<div class='circle1'></div>"
		+"<div class='circle2'></div>"
		+"<div class='circle3'></div>"
		+"<div class='circle4'></div>"
	+"</div>"
	+"<div class='spinner-container container3'>"
		+"<div class='circle1'></div>"
		+"<div class='circle2'></div>"
		+"<div class='circle3'></div>"
		+"<div class='circle4'></div>"
	+"</div></div>";
	
	//创建动画元素方法
	this.addDom=function(){
		//创建节点
		var spinner=document.createElement("div");
		var stl=document.createElement("style");
		//给spinner和stl添加类名
		spinner.classList.add('spinner');
		stl.classList.add('stlCls');
		//把创基的节点分别添加到body和HTML中；
		this.bdDom.appendChild(spinner);
		this.htmDom.appendChild(stl);
		
		//给spinner和stl分别添加动画组件和样式内容；
		stl.innerText=this.loadSty;
		spinner.innerHTML=this.htmTxt;
	};
	//移除动画元素效果
	this.rmvLoad=function(){
		var that=this;
		window.addEventListener("load", function() {
			that.bdDom.removeChild(document.getElementsByClassName('spinner')[0]);
			that.htmDom.removeChild(document.getElementsByClassName('stlCls')[0]);
		});
	};
	
	//移除动画元素效果
	this.ajaxLoad=function(){
		var that=this;
		
		that.bdDom.removeChild(document.getElementsByClassName('spinner')[0]);
		that.htmDom.removeChild(document.getElementsByClassName('stlCls')[0]);
		
	};
	
} 

////调用添加动画方法
//loadAnmt.addDom();
////移除动画元素
//loadAnmt.rmvLoad();

function addAnimation(){  
    //调用添加动画方法
	loadAnmt.addDom();  
	//移除动画元素
	loadAnmt.rmvLoad();
} 

