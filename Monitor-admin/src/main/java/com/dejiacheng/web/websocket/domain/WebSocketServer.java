package com.dejiacheng.web.websocket.domain;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import com.dejiacheng.common.utils.Threads;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;


@ServerEndpoint("/websocket/{sid}")
@Component
public class WebSocketServer {
	
	static Log log=LogFactory.get(WebSocketServer.class);
	//静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();
    
 	//与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    
    //接收sid
    private String sid="";
    
    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session,@PathParam("sid") String sid) {
    	this.session = session;
    	webSocketSet.add(this);
    	addOnlineCount();
    	log.info("有新窗口开始监听："+sid+",当前在线人数为"+getOnlineCount());
    	this.sid = sid;
    	try {
			sendMessage("连接成功");
			int i = 1;
//			boolean flag = true;
			while(true) {
				Threads.sleep(2000);
				if( i%10 != 0 && i <= 100) {
					sendMessage("true");
				}else if( i%10 == 0 && i <= 100){
					sendMessage("false");
				}else {
					break;
				}
				i++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("websocketIO异常");
		}
    }
    
    /**
     * 连接关闭调用方法*/
    @OnClose
    public void onClose() {
    	webSocketSet.remove(this); //从set中删除
    	subOnlineCount();
    	log.info("有一连接关闭，当前在线人数为："+getOnlineCount());
    }
    
    /**
     * 收到客户端信息后回调处理*/
    @OnMessage
    public void onMessage(String message,Session session) {
    	log.info("收到来自客户端"+sid+"传来的消息："+message);
    	//群发消息
    	for (WebSocketServer item : webSocketSet) {
			try {
				item.sendMessage(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
    
    /**
     * 错误处理
     */
    @OnError
    public void onError(Session session,Throwable erro) {
    	log.error("发生错误");
    	erro.printStackTrace();
    } 
    
    /**
	 * 实现服务器主动推送
	 */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
    
    /**
     * 自定义群发消息
     */
    public static void sendInfo(String message,@PathParam("sid") String sid) {
    	log.info("推送消息到"+sid+"窗口,"+"推送内容："+message);
    	for (WebSocketServer item : webSocketSet) {
    		try {
    			//判断，若sid不为空，则只像该sid推送信息
    			if (sid == null) {
        			item.sendMessage(message);
    			} else if(item.sid.equals(sid)){
    				item.sendMessage(message);
    			}
			} catch (IOException e) {
				continue;
			}
		}
    }
    
    
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }
    
    public static synchronized void addOnlineCount () {
    	WebSocketServer.onlineCount++;
    }
    
    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
