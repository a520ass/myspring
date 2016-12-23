package com.hf.service;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hf.entity.User;
import com.hf.repository.UserRepository;
import com.hf.service.base.BaseService;

@Service
@Transactional
public class UserService extends BaseService<User, Integer>{
	
	/*@Autowired
	private UserRepository userRepository;*/
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private JmsTemplate jmsTemplate;
	
	@Autowired
	@Qualifier("myDestination")
	private Destination myDestination;
	
	public void saveEntity(User user){
		
		repository.save(user);
		
		jmsTemplate.send(myDestination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("你好，生产者发送了消息。。");
            }
        });
		
		String sql="INSERT INTO users (id, create_date, create_user, sort, update_date, update_user, email, name, password, role, username) VALUES (2, '2016-08-01 10:08:44', '1', 1, '2016-08-01 10:08:52', '1', '45@45', '陈玲', '45', 'admin', 'chengling')";
		jdbcTemplate.update(sql);
		
		
		//throw new RuntimeException("jdbc error");
	}
}
