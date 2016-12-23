package com.hf.web;

import java.util.Date;

import javax.servlet.http.Part;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;

import com.hf.utils.fastdfs.FastdfsClient;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@RequestMapping( method=RequestMethod.GET)
	public String home(Model model,@AuthenticationPrincipal User user) {
		//Authentication authentication =
				SecurityContextHolder.getContext().getAuthentication();
		//User user= (User) authentication.getPrincipal();
		model.addAttribute("user", user);
		model.addAttribute("date", new Date());
		model.addAttribute("title", "我的title");
		return "home";
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String processRegistration(
			@RequestPart("profilePicture") Part profilePicture) throws Exception {
		//String fileName = profilePicture.getSubmittedFileName();
		//profilePicture.write(UUID.randomUUID()+".jpg");
		FastdfsClient.getInstance().upload(FileCopyUtils.copyToByteArray(profilePicture.getInputStream()));
		return "123";
	}
}
