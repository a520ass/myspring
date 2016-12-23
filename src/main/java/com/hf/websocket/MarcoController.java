package com.hf.websocket;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.hf.entity.Shout;

@Controller
public class MarcoController {
	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	private static Logger log=LoggerFactory.getLogger(MarcoController.class);
	
	@MessageMapping("/marco")
	//@SendTo("/topic/shout")
	@SendToUser(value="/queue/notifications",broadcast=false)
	public Shout handleShout(Principal principal, Shout incoming){
		log.info("Received message: "+incoming.getMessage());
		Shout outgoing=new Shout();
		outgoing.setMessage("Polo!  发送者"+principal.getName());
		return outgoing;
	}
	
	@SubscribeMapping("/marco")
	public Shout handleSubscription() {
		Shout outgoing = new Shout();
		outgoing.setMessage("Polo!");
		return outgoing;
	}
	
	@Scheduled(cron = "*/5 * * * * ?")
	public void sendShout(){
		Shout outgoing=new Shout();
		outgoing.setMessage("Polo!");
		messagingTemplate.convertAndSend("/topic/shout", outgoing);
		//messagingTemplate.c
	}
}
