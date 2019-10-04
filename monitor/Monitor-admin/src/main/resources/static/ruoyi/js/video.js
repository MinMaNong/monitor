var src = "http://192.168.2.254:7086/live/cameraid/1000078$0/substream/1.m3u8";
  var hls = new Hls();
  var video = document.getElementById("videoCell");
  video.src=src;
  hls.loadSource(src);
  hls.attachMedia(video);
  hls.on(Hls.Events.MANIFEST_PARSED, function(){
    video.play();
  });