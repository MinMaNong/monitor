package com.dejiacheng.framework.alarm.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.dejiacheng.framework.alarm.utils.DecimalUtils;

public class AlarmSend implements Runnable{
	
	private DatagramSocket getSocket;
	private byte[] getData;
	private int length;
	
	public AlarmSend(DatagramSocket getSocket,byte[] getData,int length) {
		this.getSocket = getSocket;
		this.getData = getData;
		this.length = length;
	}
	
	
	@Override
	public void run() {
		try {
			byte[] senData = DecimalUtils.InstructionConversion(getData,length);
			while(senData != null) {
				System.err.println("准备发送数据");
//				DatagramPacket getPacket = new DatagramPacket(getData,getData.length,InetAddress.getByName("192.168.2.59"),4015);
				DatagramPacket getPacket = new DatagramPacket(senData,length,InetAddress.getByName("127.0.0.1"),4015);
				getSocket.send(getPacket);
				senData = null;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		getSocket.close();
	}

}
