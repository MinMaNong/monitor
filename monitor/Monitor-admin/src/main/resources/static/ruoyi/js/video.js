var src = "http://192.168.2.254:7086/live/cameraid/1000078$0/substream/1.m3u8";
  var hls = new Hls();
  var video = document.getElementById("videoCell");
  video.src=src;
  hls.loadSource(src);
  hls.attachMedia(video);
  hls.on(Hls.Events.MANIFEST_PARSED, function(){
    video.play();
  });
  


//云台控制上
function getup(){
	alert("上");
}

//云台控制下
function getwn(){
	alert("下");
}

//云台控制左
function getleft(){
	alert("左");
}

//云台控制右
function getright(){
	alert("右");
}

//ajax获取视频URL、通道号、云台控制
function getvideo(){
//	$.ajax({
//          //请求方式
//          type : "POST",
//          //请求的媒体类型
//          contentType: "application/json;charset=UTF-8"
//          data : "channelCode(通道号)": username
//       		 "nStep(步长)": password,
//            "nDirect(方向)" : nDirect,
//          success : function(result) {
//            		 if (result.code == 0) {
//              			//TODO成功后处理代码
//
//         			 } else {
//          				 $.modal.closeLoading();
//        	  			 $('.imgcode').click();
//            			 $(".code").val("");
//		 $.modal.msg(result.msg);
//		            }
//		        });
//}
}