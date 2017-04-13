package com.hf.commons;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 基础加密组件
 * 
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("restriction")
public abstract class BaseEncrypt {
	/**
	 * 它包括 SHA-1，SHA-224，SHA-256，SHA-384，和 SHA-512 等几种算法。
	 * 其中，SHA-1，SHA-224 和 SHA-256 适用于长度不超过 2^64 二进制位的消息。
	 * SHA-384 和 SHA-512 适用于长度不超过 2^128 二进制位的消息。
	 */
	public static final String KEY_SHA = "SHA";
	public static final String KEY_MD5 = "MD5";

	/**
	 * MAC算法可选以下多种算法
	 * 
	 * <pre>
	 * HmacMD5 
	 * HmacSHA1 
	 * HmacSHA256 
	 * HmacSHA384 
	 * HmacSHA512
	 * </pre>
	 */
	public static final String KEY_MAC = "HmacMD5";

	/**
	 * BASE64解码
	 *
	 * @return	字节数组
	 * @throws IOException 
	 */
	public static byte[] decryptBASE64(String base64String) throws IOException {
		return Base64.decodeBase64(base64String);
	}

	/**
	 * BASE64编码
	 * @return	编码后的字符串
	 */
	public static String encryptBASE64(byte[] binaryData) {
		return Base64.encodeBase64String(binaryData);
	}

	/**
	 * MD5加密（信息摘要，无法还原）
	 * Message-Digest Algorithm 5
	 * @return	MD5字节数组
	 * @throws NoSuchAlgorithmException 
	 */
	public static byte[] encryptMD5(byte[] data) throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
		md5.update(data);
		return md5.digest();

	}

	/**
	 * SHA加密
	 *
	 * @return 加密后的字节数组
	 * @throws NoSuchAlgorithmException 
	 */
	public static byte[] encryptSHA(byte[] data) throws NoSuchAlgorithmException  {
		MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
		sha.update(data);
		return sha.digest();

	}

	/**
	 * 初始化HMAC密钥
	 * 
	 * @return	MAC密钥（经过base64编码后的）
	 * @throws NoSuchAlgorithmException 
	 */
	public static String initMacKey() throws NoSuchAlgorithmException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);
		SecretKey secretKey = keyGenerator.generateKey();
		return encryptBASE64(secretKey.getEncoded());
	}

	/**
	 * HMAC加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws IOException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
	public static byte[] encryptHMAC(byte[] data, String key) throws IOException, NoSuchAlgorithmException, InvalidKeyException{
		SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC);
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);
		return mac.doFinal(data);
	}
}
