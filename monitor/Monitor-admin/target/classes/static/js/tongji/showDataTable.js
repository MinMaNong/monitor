/**
 * 
 * 安全生产执法统计页面(扇面)showDataTable.html
 * 
 * **/
//创建函数对象
var showDataTable=new showDataTable();
function showDataTable(){
	this.titleR='';
	this.lftTstr=[];
	this.lftBstr=[];
	this.rghTstr=[];
	this.rghBstr=[];
	this.showStr=[];
	this.clsNameRB=[];
	this.appendDom=function(dom,data){
		var dataCount=data[0];
		$("#"+dom).siblings('ol').append(
			"<li>总数量<b>"+152+"</b></li>"+
//			"<li>总数量<b>"+dataCount+"</b></li>"+
			"<li>红外<b>"+6+"</b></li>"+
//			"<li>正常<b>"+data[1]+"</b></li>"+
			"<li>球机<b>"+67+"</b></li>"+
			"<li>全景<b>"+45+"</b></li>"+
			"<li>半球机<b>"+10+"</b></li>"
		);
	};
	this.showDataFansL=function(dom,loctn){
		var data01=this.lftTstr;
		var data02=this.lftBstr;
		var NumData=[];//展示数据
		var ttl='';//图表名用于区分模块
		if(loctn=='top'){
			NumData=data01;
			this.appendDom(dom,data01);
		}else{
			NumData=data02;
			this.appendDom(dom,data02);
		}
		var myChart = echarts.init(document.getElementById(dom));//扇面所在节点
		option = {
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        orient: 'vertical',
		        right: '5%',
		        top:'10%',
		        data: ['红外','球机','全景','半球机'],
		       	textStyle:{
		            fontSize: 12,
		            fontWeight: 'bolder',
		        	color: '#55c8fe'
		        }      
		    },
		    series : [
		        {
		            name: '访问来源',
		            type: 'pie',
		            radius : '86%',
		            center: ['45%', '50%'],
		            color:['#1977ff', '#2c498d','#14ff96','#16fefe'],
//		            data:[
//		                {value:NumData[1], name:'正常'},
//		                {value:NumData[2], name:'预警'},
//		                {value:NumData[3], name:'警告'},
//		                {value:NumData[4], name:'严重警告'},
//		            ],
					data:[
		                {value:6, name:'红外'},
		                {value:67, name:'球机'},
		                {value:45, name:'全景'},
		                {value:10, name:'半球机'},
		            ],
		            label: {
		                normal: {
		                    show: false,
		                }
		            },
		            itemStyle: {
		                emphasis: {
		                    shadowBlur: 10,
		                    shadowOffsetX: 0,
		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		                }
		            }
		        }
		    ]
		};
		// 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
	};
	
	/**
	 * 右面Ecarts
	 * 
	 * **/
	this.showDataRightEchart=function(dom,loctn){
		var data01=this.rghTstr;
		var data02=this.rghBstr;
		var showStr=this.showStr;
		var count01=data01[0]+data01[1]+data01[2]+data01[3];
		var count02=data02[0]+data02[1]+data02[2]+data02[3];
		var myChart = echarts.init(document.getElementById(dom));//扇面所在节点
		var ttlR=this.titleR;
		var that=this;
		optionTop ={
			title: {
		        text:ttlR,
		        left: '15%',
		        top:'5%',
		        textStyle:{
		             color:'#1bd2ff'
		        }
		    },
		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)"
		    },
		     legend: {
		        orient: 'vertical',
		        right: '15%',
		        top:'10%',
		        data:['正常视频','异常视频'],
		       	textStyle:{
		            color:'#1bd2ff',
		            fontSize:14,
		            fontWeight: 'bolder'
		        }
		    },
		    series: [
		        {
		            name:'访问来源',
		            type:'pie',
		            radius: ['70%', '85%'],
		            color:['#243e8e','#1bd2ff'],
		            avoidLabelOverlap: false,
		            label: {
		                normal: {
		                    show: false,
		                    position: 'center',
		                    color:['red','blue']
		                },
                emphasis: {
                    show: true,
                    textStyle: {
                        fontSize: '22',
                        fontWeight: 'bold',
                        color:'#ff9600'
                    }
                }
            },
            labelLine: {
                normal: {
                    show: false
                }
            },
            data:[
                {value:count01, name:'正常视频'},
                {value:count02, name:'异常视频'}
            ]
        }
    ]
};

		//柱状图
		optionBottom ={
		    color: ['#3398DB'],
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        }
		    },
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    xAxis : [
		        {
		            type : 'category',
		            data : that.clsNameRB,
		            axisLabel: {
                        show: true,
                        textStyle: {
                            color: '#55c8fe',
                            fontSize:14
                        }
                    },
                    splitLine:{show: false},//去除网格线
                	splitArea : {show : false},//保留网格区域
                    axisLine:{
                        lineStyle:{
                            color:'#74f8fd',
                            width:2,//这里是为了突出显示加上的
                        }
                    }  
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value',
		             axisLine:{
                        lineStyle:{
                            color:'#74f8fd',
                            width:2,//这里是为了突出显示加上的
                        }
                   },
                    splitLine:{show: false},//去除网格线
                	splitArea : {show : false},//保留网格区域
		            axisLabel : {
                        formatter: '{value}',
                        textStyle: {
                            color: '#55c8fe',
                            fontSize:14
                        }
                 }
		        }
		    ],
		    series : [
		        {
		            name:'直接访问',
		            type:'bar',
		            barWidth: '40%',
		             itemStyle: {
		                    normal: {
		                    	label: {
						            show: true,
						            position: 'top',
						            textStyle: {
						                color: '#fd9802',
						                fontSize:15,
						                fontWeight:800
						            }
					            },
		                        color: function(params) {
		                            // build a color map as your need.
		                            var colorList = [
		                                '#2868c2','#1964f1','#1be6ae','#16fefe','#27a2e5',
		                                '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
		                                '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
		                            ];
		                            return colorList[params.dataIndex]
		                        },
		                       
		                    }
		                },
		            data:[showStr[0],showStr[1],showStr[2],showStr[3]]
		        },
		        
		    ]
		};

		// 使用刚指定的配置项和数据显示图表。
		if(loctn=='top'){
			 myChart.setOption(optionTop);
			 $('#showDataRightEchartTop div').mouseover(function(){
			 	$('#showErro').hide();
			 }).mouseout(function(){
			 	$('#showErro').show();
			 });
		}else{
			 myChart.setOption(optionBottom);
		}
	}
}
////数据统计页面时间下拉菜单
//$("#showDataFanStart").jHsDate({
//	defDate: '',
//	defIsShow: true
//});
//$("#showDataFanEnd").jHsDate({
//	defDate: '',
//	defIsShow: true
//});