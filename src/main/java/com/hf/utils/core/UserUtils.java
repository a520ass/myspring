package com.hf.utils.core;


import com.google.common.collect.Maps;
import com.hf.config.custom.SpringContextHolder;
import com.hf.repository.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Map;

/**
 * Created by krt on 2017/2/24.
 */
public class UserUtils {

    private static final Map<String,com.hf.entity.User> sysuserMap= Maps.newHashMap();

    public static com.hf.entity.User getUser(){
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        User user= (User) authentication.getPrincipal();
        com.hf.entity.User userI = sysuserMap.get(user.getUsername());
        if(userI==null){
            String sql="select * from user where username = ?";
            Object[] args= {user.getUsername()};
            userI=SpringContextHolder.getBean(JdbcTemplate.class).queryForObject(sql, args,com.hf.entity.User.class);
            sysuserMap.put(user.getUsername(),userI);
        }
        return userI;
    }
}
