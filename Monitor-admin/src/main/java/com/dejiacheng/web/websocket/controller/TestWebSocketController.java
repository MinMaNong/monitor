package com.dejiacheng.web.websocket.controller;

import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dejiacheng.web.websocket.domain.WebSocketServer;


@Controller
@RequestMapping("/checkcenter")
public class TestWebSocketController {
	
		//页面请求
		@GetMapping("/socket/{cid}")
		public void socket(@PathVariable String cid,Session session) {
			System.out.println(cid);
			WebSocketServer server = new WebSocketServer();
			System.out.println("ok");
			server.onOpen(session, cid);
		}
}
