package com.hf.web;

import java.security.Principal;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.hf.config.custom.SpringContextHolder;
import com.hf.entity.ROLE;
import com.hf.entity.User;
import com.hf.service.UserService;
import com.sishuok.register.RegisterService;

@Controller
public class LoginController {
	
	@Value("${application.message:Hello World}")
	private String message = "Hello World";
	
	@Autowired(required=false)
	private RegisterService registerService;
	
	@Autowired
	private UserService userService;
	
	@Autowired TemplateEngine templateEngine;
	
	@RequestMapping("/login")
    public String login(Long id, HttpServletRequest request) {
		WebApplicationContext webApplicationContext = RequestContextUtils.findWebApplicationContext(request);
		if(webApplicationContext.getParent()==null){
			
		}else{
			System.out.println("测试是否相等。。"+(webApplicationContext.getParent()==SpringContextHolder.getApplicationContext()));
		}
		String name = request.getClass().getName();
		return "login";
    }
	
	@RequestMapping("/register")
	@ResponseBody
	public String register(Model model) {
		//registerService.register("hefeng", "admin");
		User user=new User();
		user.setId(3);
		user.setEmail("11");
		user.setName("he");
		user.setRole(ROLE.user.name());
		userService.save(user);
		return "/login";
	}
	
	@RequestMapping("/sendmail")
	@ResponseBody
	public String sendEmail(Principal principal){
		Context ctx=new Context();
		ctx.setVariable("date", new Date());
		ctx.setVariable("message", principal);
		String string = templateEngine.process("emailTemplate.html", ctx);
		
		return string;
	}
    
    @RequestMapping("/home")
	public String welcome(Map<String, Object> model) {
		model.put("title", "首页");
		model.put("message", this.message);
		model.put("date", new Date());
		return "home";
	}
    
    @RequestMapping(value = "/helloadmin", method=RequestMethod.GET)
    //@PreAuthorize("hasAnyRole('admin')")
    public String helloAdmin(){
        return "helloAdmin";
    }
    
    @RequestMapping("/error")
    public String forbidden(String errorcode,Model model){
    	model.addAttribute("errorcode", errorcode);
        return "error";
    }
}
