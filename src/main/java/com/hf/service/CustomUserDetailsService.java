package com.hf.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

import com.hf.entity.User;
import com.hf.repository.UserRepository;

public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	
	/*@Autowired 
	private UserCache userCache;*/
	 

	private static Logger log = LoggerFactory
			.getLogger(CustomUserDetailsService.class);

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		UserDetails user = null;//userCache.getUserFromCache(username);

		if (user == null) {
			//user = delegate.loadUserByUsername(username);
			User dbuser = userRepository.findByUsername(username);
			if (dbuser == null) {
				log.error("dbuser is null");
				throw new UsernameNotFoundException("dbuser not found");
			}
			//权限信息从这里获取，并添加到user中
			List<SimpleGrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority(dbuser.getRole().name()));

			log.info("username is " + username + ", role is "
					+ dbuser.getRole().name());

			 user = new org.springframework.security.core.userdetails.User(
					username, dbuser.getPassword(), authorities);
		}

		Assert.notNull(user, "UserDetailsService " 
				+ " returned null for username " + username + ". "
				+ "This is an interface contract violation");

		//userCache.putUserInCache(user);//session失效或退出登陆需要remove缓存

		return user;

	}

	/*
	 * @Test public void ps(){ Md5PasswordEncoder pwe=new Md5PasswordEncoder();
	 * String string = pwe.encodePassword("hefeng1995", "admin");
	 * System.err.print(string); }
	 */

}
