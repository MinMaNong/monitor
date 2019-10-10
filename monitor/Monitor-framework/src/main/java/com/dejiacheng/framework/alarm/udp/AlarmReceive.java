package com.dejiacheng.framework.alarm.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.sun.jna.platform.unix.X11.XClientMessageEvent.Data;

/**
 * 
 * @author pcy
 * 报警监听服务端
 * @version 创建时间:2019年10月7日-下午4:30:55
 */
public class AlarmReceive implements Runnable {
	
	//定义一个套接字
	private DatagramSocket getSocket;
//	private static int port = 8015;
	
	//定义一个有参的构造函数
	public AlarmReceive(DatagramSocket getSocket) {
		this.getSocket = getSocket;
	}
	
	
	@Override
	public void run() {
		try {
			//定义接收方IP地址
//			InetAddress ip = InetAddress.getByName("192.168.2.60");
			InetAddress ip = InetAddress.getLocalHost();
			System.out.println("开始接收数据---监听端口地址为："+ip);
			//指定接收方地址和端口
			
			while(true) {
				
				//确定接收方数据包大小
				byte[] buf = new byte[1024];
				
				//创建接收类型的数据报，数据存储在buf中
				DatagramPacket getPacket = new DatagramPacket(buf, buf.length);
				
				//通过套接字接收数据
				getSocket.receive(getPacket);
				
				DatagramSocket dsSend = new DatagramSocket();
				AlarmSend alarmSend = new AlarmSend(dsSend,getPacket.getData(),getPacket.getLength());
				System.out.println(getPacket.getAddress());
				Thread sendThead = new Thread(alarmSend);
				sendThead.start();
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
