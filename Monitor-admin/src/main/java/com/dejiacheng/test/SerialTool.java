package com.dejiacheng.test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

//import javax.comm.CommPortIdentifier;
//import javax.comm.SerialPort;
import javax.servlet.http.HttpServletRequest;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

/**
 * 串口服务类，提供打开、关闭串口，读取、发送串口数据等服务（采用单例设计模式
 * 
 */
public class SerialTool implements Runnable {
    private static CommPortIdentifier portId;
    private static SerialPort serialPort;
    private static OutputStream outputStream;
    private static InputStream inputStream;
    private static Object obj = new Object();
    private static Object obj1 = new Object();
    Thread readThread;
    /**
     * 16进制数字字符集
     */
    private static String hexString = "0123456789ABCDEF";

    /*
     * 将字符串编码成16进制数字,适用于所有字符（包括中文）
     */
    public static String encode(byte[] str) {
        // 根据默认编码获取字节数组
        byte[] bytes = str;
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        // 将字节数组中每个字节拆解成2位16进制整数
        for (int i = 0; i < bytes.length; i++) {
            sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
            sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
        }
        return sb.toString();
    }

    /**
     * @方法名称： ReadData()
     * @return encode(readBuffer) 十六进制显示的字符串型数据
     * @throws IOException
     * @功能描述：对端口通信发送来的数据进行读取
     * @返回值类型：String
     */
    public static String ReadData() throws IOException {
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        synchronized (obj) {
            while (true) {
                byte[] readBuffer = new byte[23];
                int length = 0;
                try {
                    if (bis.available() > 0) {
                        while ((length = bis.read(readBuffer)) == 23) {
                            for (int i = 0; i < length; i++) {
                                return encode(readBuffer);
                            }
                        }
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    inputStream.close();
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // TinODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将ReadData()方法中字符串以空格切割加入String数组中,获取命令符(通道)
     * 
     * @param int
     *            i=10或11
     * @return 第i个数据
     * @throws IOException
     */
    public static List<Object> DataProcess() throws IOException {
        synchronized (obj) {
        String dataHex = ReadData();
        String head = dataHex.substring(6, 8);
        String channel1 = dataHex.substring(20, 22);
        String frequency1 = dataHex.substring(22, 24);
        String Plush11 = dataHex.substring(24, 26);
        String Plush12 = dataHex.substring(26, 28);
        String Plush1 = Plush12 + Plush11;
        String voltage11 = dataHex.substring(28, 30);
        String voltage12 = dataHex.substring(30, 32);
        String voltage1 = voltage12 + voltage11;

        String frequency2 = dataHex.substring(32, 34);
        String Plush21 = dataHex.substring(34, 36);
        String Plush22 = dataHex.substring(36, 38);
        String Plush2 = Plush22 + Plush21;
        String voltage21 = dataHex.substring(38, 40);
        String voltage22 = dataHex.substring(40, 42);
        String voltage2 = voltage22 + voltage21;
        //Float curr = Float.parseFloat(SerialReadCurr.ReadCurrData().substring(
                //1, 4)) / 10;
        // 通道值
        int chs = Integer.parseInt(channel1, 16);
        // 频率
        int freq1 = Integer.parseInt(frequency1, 16);
        // 脉宽
        int plush1 = Integer.parseInt(Plush1, 16);
        // 强度
        float volt1 = Integer.parseInt(voltage1, 16);
        int freq2 = Integer.parseInt(frequency2, 16);
        // 脉宽
        int plush2 = Integer.parseInt(Plush2, 16);
        // 强度
        float volt2 = Integer.parseInt(voltage2, 16);
        String ch1 = "";
        String ch2 = "";
        if (chs == 2) {
            ch1 = "CH1";
            ch2 = "CH2";
        } else if (chs == 1) {
            ch1 = "NG";
            ch2 = "NG";
        }
        List<Object> list = new ArrayList<Object>();
        list.add(ch1);
        list.add(volt1 / 10);
        list.add(plush1);
        list.add(freq1);
        list.add(ch2);
        list.add(volt2 / 10);
        list.add(plush2);
        list.add(freq2);
        //list.add(curr);
        //HttpServletRequest request = null;
        //request.setAttribute("list",list);
        return list;
        }
    }

    // 初始化串口对象，端口等待时间2000ms，数据位8，停止位1，校验位none；
    public static void initComm(String comm) {
        try {
            portId = CommPortIdentifier.getPortIdentifier(comm);
            serialPort = (SerialPort) portId.open(comm, 2000);
            outputStream = serialPort.getOutputStream();
            inputStream = serialPort.getInputStream();
            // 设置初始化参数；
            serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            /*
             * serialPort.addEventListener(this);
             * serialPort.notifyOnDataAvailable(true);//设置监听模式为当有数据到达时唤醒监听线程。
             */} catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭串口
     * 
     * @param serialport
     *            待关闭的串口对象
     */
    public static void closePort() {
        if (serialPort != null) {
            serialPort.close();
            serialPort = null;
        }
    }

    /**
     * @方法描述：向所连接的端口发送数据
     * @param：cmd
     */
    public static byte[] writeComm(byte[] cmd) {
        try {
            outputStream.write(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cmd;
    }

    // 字符串转成十六进制byte[] 类型数组；
    public static byte[] HexString2Bytes(String src) {
        if (null == src || 0 == src.length()) {
            return null;
        }
        byte[] ret = new byte[src.length() / 2];
        byte[] tmp = src.getBytes();
        for (int i = 0; i < (tmp.length / 2); i++) {
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
        }
        return ret;
    }

    // byte类型数据，转成十六进制形式；
    public static byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
                .byteValue();
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
                .byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }

    public void run() {

    }
/*  public static void main(String arg[]){

    }*/
}
