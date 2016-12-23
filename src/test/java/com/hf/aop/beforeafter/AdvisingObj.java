package com.hf.aop.beforeafter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//aop中的通知类
public class AdvisingObj {
	
	private static final Logger logger = LoggerFactory.getLogger(AdvisingObj.class);  
    
    public void beforeExecute(){  
        logger.info("AdvisingObj beforeExcecute(). 前置通知");  
    }  
      
    public void afterExecute(){  
        logger.info("AdvisingObj afterExecute(). 后置通知");  
    }  
}
