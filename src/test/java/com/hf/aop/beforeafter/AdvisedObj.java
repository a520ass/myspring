package com.hf.aop.beforeafter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//需要被切入其他通知的类，也就是说，其他方法要插入到此类方法执行的前面或后面
public class AdvisedObj {
	private static final Logger logger = LoggerFactory.getLogger(AdvisedObj.class);  
    
    public void perform(){  
        logger.info("AdvisedObj perform().");  
    } 
}
