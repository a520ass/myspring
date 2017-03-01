package com.hf.utils.core;


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

    private static com.hf.entity.User sysuser;

    public static com.hf.entity.User getUser(){
        if(sysuser==null){
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();
            User user= (User) authentication.getPrincipal();
            String sql="select * from user where username = ?";
           Object[] args= {user.getUsername()};
            sysuser = SpringContextHolder.getBean(JdbcTemplate.class).queryForObject(sql, args,com.hf.entity.User.class);

        }
        return sysuser;
    }
}
