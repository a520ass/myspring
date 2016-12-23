package com.hf.web.test;

import javax.jms.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hf.jms.ProducerServiceImpl;

@Controller
@RequestMapping("/email")
public class EmailController {
	
	/*@Autowired
	private EmailService emailService;*/
	
	/*@RequestMapping("/send")
	public void sendEmail(){
		emailService.sendEmail("known.spammer@example.org", "测试邮件发送");
	}*/
	@Autowired
	private ProducerServiceImpl producerService;

	@Autowired
	@Qualifier("myDestination")
	private Destination myDestination;
	
	@RequestMapping("/jmstest1")
	@ResponseBody
	public void jmstest1(){
		for (int i=0; i<2; i++) {
			producerService.sendMessage(myDestination, "你好，生产者！这是消息：" + (i+1));
		}
	}
}
