package com.hf.commons;

import static org.junit.Assert.*;

import org.junit.Test;

public class DESEncryptTest {
	
	@Test
	public void test() throws Exception {
		String inputStr = "DES";
		String key = DESEncrypt.initKey("hflovebeibei");//生成密钥key
		System.err.println("原文:\t" + inputStr);

		System.err.println("密钥:\t" + key);

		byte[] inputData = inputStr.getBytes();
		inputData = DESEncrypt.encrypt(inputData, key);

		System.err.println("加密后:\t" + DESEncrypt.encryptBASE64(inputData));

		byte[] outputData = DESEncrypt.decrypt(inputData, key);
		String outputStr = new String(outputData);

		System.err.println("解密后:\t" + outputStr);

		assertEquals(inputStr, outputStr);
	}
}
