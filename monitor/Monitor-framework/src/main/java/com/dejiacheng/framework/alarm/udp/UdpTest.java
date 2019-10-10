package com.dejiacheng.framework.alarm.udp;

import java.net.DatagramSocket;

public class UdpTest {
	public static void main(String[] args) {
		try {
			DatagramSocket getSocket = new DatagramSocket(8015);
			AlarmReceive receive = new AlarmReceive(getSocket);
			Thread receiveThread = new Thread(receive);
			receiveThread.start();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
