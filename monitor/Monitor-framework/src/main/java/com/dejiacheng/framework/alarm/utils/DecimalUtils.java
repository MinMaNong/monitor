package com.dejiacheng.framework.alarm.utils;

import java.util.Locale;

import com.dejiacheng.common.core.domain.AjaxResult;
import com.dejiacheng.framework.alarm.websocket.domain.WebSocketServer;

public class DecimalUtils {
	private static byte[] online = {8, 0, -128, 0, 0, 0, -127, 9};
	private static final byte zone1 = 1;
	private static final byte zone2 = 2;
	private static final byte zone3 = 4;
	private static final byte zone4 = 8;
	/**
	 * 指令转换
	 */
	public static byte[] InstructionConversion(byte[] getByte,int length) {
		int num = 0;
		online[6] = -127;
		online[7] = 9;
//		System.out.println(getByte.length);
		if(getByte[6] == 1) {
			return online;
		}else if(length == 11 || length == 12){
			online[2] = getByte[2];
			online[6] = -126;
			online[7] = 0;
			StringBuffer buf = new StringBuffer();
			buf.append(0);
			if(getByte[2] < 10) {
				buf.append(0);
				buf.append(getByte[2]);
			}else {
				buf.append(getByte[2]);
			}
//			AjaxResult.success();
			switch (getByte[7]) {
			case zone1:
				buf.append(1);
				break;
			case zone2:
				buf.append(2);
				break;
			case zone3:
				buf.append(3);
				break;
			case zone4:
				buf.append(4);
				break;
			default:
				break;
			}
			WebSocketServer.sendInfo(buf.toString(),"20");
//			for (byte b : online) {
//				if(num == 0) {
//					num += Integer.parseInt(byteToHex(b),16);
//				}else {
//					break;
//				}
//			}
//			for(int i = 0; i < 8; i++) {
//				num += Integer.parseInt(byteToHex(online[i]),16);;
//			}
			online[7] = PositiveCheck(online,length);
			return online;
		}else {
			return null;
		}
	}
	
	
	/**
	 * 正校验
	 */
	public static byte PositiveCheck(byte[] buf,int length) {
		int num = 0;
		for (byte b : buf) {
			num += Integer.parseInt(byteToHex(b).trim(),16);
		}
		return (byte) num;
	}
	
	
	/**
	 * byte[] 转 String类型
	 * 
	 * @param array 字节数组
	 * @param num
	 * @return 转义后字符串
	 */
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
	  
//	  public static void main(String[] args) {
////		byte[] buf = {8, 0, 1, 0, 0, 0, -126, -117};
////		byte[] buf = {8, 0, 1, 0, 0, 0, -126, 0};
////		System.out.println(byteToHex(PositiveCheck(buf,buf.length)));
//		byte b = -126;
//		System.out.println(Integer.parseInt(byteToHex(b).trim(),16));
//	}
}
