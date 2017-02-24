package com.hf.config.thrif.server;

import com.hf.utils.web.WebUtils;

import java.util.ArrayList;
import java.util.List;

/** 
 * 解析网卡Ip 
 * 
 */  
public class ThriftServerIpLocalNetworkResolve implements ThriftServerIpResolve {  

  
    //缓存  
    private List<String> serverIp=new ArrayList<>();  
      
    public void setServerIp(List<String> serverIp) {  
        this.serverIp = serverIp;  
    }  
  
    @Override  
    public List<String> getServerIp() {
        if (serverIp.size()==0){
            serverIp = WebUtils.getServerIp();
        }
        return serverIp;
    }  
  
    @Override  
    public void reset() {  
        serverIp = null;  
    }  
}  
