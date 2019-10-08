package com.dejiacheng.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Locale;

/*
 * 服务器端，实现基于UDP的用户登陆
 */
public class UDPServer {
    public static void main(String[] args) throws IOException {
        /*
         * 接收客户端发送的数据
         */
        // 1.创建服务器端DatagramSocket，指定端口
        DatagramSocket socket = new DatagramSocket(8015);
        // 2.创建数据报，用于接收客户端发送的数据
        byte[] data = new byte[1024];// 创建字节数组，指定接收的数据包的大小
        DatagramPacket packet = new DatagramPacket(data, data.length);
        while(true) {
        	// 3.接收客户端发送的数据
        	System.out.println("****服务器端已经启动，等待客户端发送数据");
        	socket.receive(packet);// 此方法在接收到数据报之前会一直阻塞
        	// 4.读取数据
    		String info = byteArrayToHexString(data,packet.getLength());
    		if(info != null) {
    			System.out.println("我是服务器，客户端说：" + info);
    			break;
    		}
        }
        /*
         * 向客户端响应数据
         */
        // 1.定义客户端的地址、端口号、数据
//        InetAddress address = packet.getAddress();
//        int port = packet.getPort();
//        byte[] data2 = strTo16("0b 00 02 00 00 00 0f 41 01 00 5e").getBytes();
//        // 2.创建数据报，包含响应的数据信息
//        DatagramPacket packet2 = new DatagramPacket(data2, data2.length, address, port);
//        // 3.响应客户端
//        socket.send(packet2);
        // 4.关闭资源
//        socket.close();
 
    }
    
    /**
     * 字符串转化成为16进制字符串
     * @param s
     * @return
     */
    public static String strTo16(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4+" ";
        }
        return str;
    }
    
    public static String byteArrayToHexString(byte[] array,int num) {
  	  if (array == null) {
  	   return "";
  	  }
  	  StringBuffer buffer = new StringBuffer();
  	  for (int i = 0; i < num; i++) {
  	   buffer.append(byteToHex(array[i]));
  	  }
  	  return buffer.toString();
  	 }
  
  /**
   * byte转十六进制字符
   * 
   * @param b
   *            byte
   * @return 十六进制字符
   */
  public static String byteToHex(byte b) {
   String hex = Integer.toHexString(b & 0xFF);
   if (hex.length() == 1) {
    hex = '0' + hex;
   }
   return hex.toUpperCase(Locale.getDefault())+" ";
  }
}
