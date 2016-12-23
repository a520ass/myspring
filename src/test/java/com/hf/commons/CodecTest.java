package com.hf.commons;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

import org.junit.Test;

import com.hf.utils.codec.Base58;


public class CodecTest {
	
	static char[] hex = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	
	@Test
	public void test1() throws UnsupportedEncodingException {
		String encode = Base58.encode("Hello world");
		System.out.println(encode);
	}
	
	@Test
	public void test2(){
		byte[] bytes=new byte[]{-1,-5};
		String byte2str = byte2str(bytes);
		System.out.println(byte2str);
	}
	
	@Test
	public void test3() throws UnsupportedEncodingException{
		String s = "何";  
        byte[] bs = s.getBytes("UTF-8");  
        /** 
         * 第一个参数要设置为1 
         * signum of the number (-1 for negative, 0 for zero, 1 for positive). 
         */  
        BigInteger bi = new BigInteger( bs);  
        String hex = bi.toString(16);  
        System.out.println(hex);  
          
        // 还原  
        bi = new BigInteger(hex, 16);  
        bs = bi.toByteArray();// 该数组包含此 BigInteger 的二进制补码表示形式。  （有一个符号位）
        byte[] tempBs = new byte[bs.length - 1];  
        byte[] originBs = bs;  
        if (bs[0] == 0) {  
           // System.out.println("去补码...");  
            System.arraycopy(bs, 1, tempBs, 0, tempBs.length); // 去掉第一个符号位
            originBs = tempBs;  
        }  
        s = new String(originBs, "UTF-8");  
        System.out.println(s);
	}
	
	
	 /**
     * 将字节数组转换成十六进制字符串
     * @param bytes
     * @return
     */
    private static String byte2str(byte []bytes){
        int len = bytes.length;   
        StringBuffer result = new StringBuffer();    
        for (int i = 0; i < len; i++) {   
            byte byte0 = bytes[i];   
            result.append(hex[byte0 >>> 4 & 0xf]);   //先向右无符号移位。然后自动转int，再进行与操作
            result.append(hex[byte0 & 0xf]);   
        }
        return result.toString();
    }

}
