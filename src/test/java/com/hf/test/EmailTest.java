package com.hf.test;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hf.config.email.EmailConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmailConfig.class})
@TestPropertySource("classpath:app.conf")
public class EmailTest {
	
	@Autowired JavaMailSenderImpl mailSender;

	@Test
	public void test1() throws InterruptedException, MessagingException{
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
		helper.setFrom("18518911270@163.com");
		helper.setTo("a520ass@hotmail.com");
		helper.setSubject("关于海航");
		helper.setText("这个项目由于一些问题。");
		mailSender.send(mimeMessage);
	}

}

