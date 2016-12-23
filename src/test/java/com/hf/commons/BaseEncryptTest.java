package com.hf.commons;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.util.FileCopyUtils;

import static org.junit.Assert.*;

public class BaseEncryptTest {
	
	@Test
	public void test() throws Exception {
		String inputStr = "简单加密";
		System.err.println("原文:\n" + inputStr);

		byte[] inputData = inputStr.getBytes();
		String code = BaseEncrypt.encryptBASE64(inputData);

		System.err.println("BASE64加密后:\n" + code);

		byte[] output = BaseEncrypt.decryptBASE64(code);

		String outputStr = new String(output);

		System.err.println("BASE64解密后:\n" + outputStr);

		// 验证BASE64加密解密一致性
		assertEquals(inputStr, outputStr);

		// 验证MD5对于同一内容加密是否一致
		assertArrayEquals(BaseEncrypt.encryptMD5(inputData), BaseEncrypt
				.encryptMD5(inputData));

		// 验证SHA对于同一内容加密是否一致
		assertArrayEquals(BaseEncrypt.encryptSHA(inputData), BaseEncrypt
				.encryptSHA(inputData));

		String key = BaseEncrypt.initMacKey();
		System.err.println("Mac密钥:\n" + key);

		// 验证HMAC对于同一内容，同一密钥加密是否一致
		assertArrayEquals(BaseEncrypt.encryptHMAC(inputData, key), BaseEncrypt.encryptHMAC(
				inputData, key));
		
		/**
		 * biginteger用于处理大数据
		 */
		BigInteger md5 = new BigInteger(BaseEncrypt.encryptMD5(inputData));
		System.err.println("MD5:\n" + md5.toString(16));//输出对应进制的字符串

		BigInteger sha = new BigInteger(BaseEncrypt.encryptSHA(inputData));
		System.err.println("SHA:\n" + sha.toString(32));

		BigInteger mac = new BigInteger(BaseEncrypt.encryptHMAC(inputData, key));
		System.err.println("HMAC:\n" + mac.toString(16));
	}
	
	@Test
	public void testMd5() throws NoSuchAlgorithmException, IOException{
		//使用jdk的md5
		byte[] data =FileCopyUtils.copyToByteArray(new FileInputStream("D:\\【实力VIP】8.0版.rar"));
		byte[] encryptMD5 = BaseEncrypt.encryptMD5(data);
		String string = new BigInteger(encryptMD5).toString(16);
		System.out.println(string);
		//System.out.println(hexString);
		
		byte[] md5 = DigestUtils.md5(new FileInputStream("D:\\【实力VIP】8.0版.rar"));
		
		//使用apache 的md5
		String fileMD5 = DigestUtils.md5Hex(new FileInputStream("D:\\configuration.yml"));
		System.out.println(fileMD5);//32位的十六进制
	}
}
