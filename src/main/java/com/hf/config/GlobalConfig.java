package com.hf.config;

public abstract class GlobalConfig {
	
	/**
	 * 基本包名
	 */
	public static final String BASEPACKAGES="com.hf";
	/**
	 * 基本web包名
	 */
	public static final String BASEWEBPACKAGES="com.hf.web";

	/**
	 * 基本web包名
	 */
	public static final String BASEWEBSOCKETPACKAGES="com.hf.websocket";
	
	/**
	 * 项目访问根路径（内部）
	 */
	public static final String CTXLOCAL="http://127.0.0.1:8081/myspring";
	
	/**
	 * rmi
	 */
	public static final String RMICTXLOCAL="rmi://127.0.0.1:1099";

	public static final String[] parsePatterns = {
			"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
			"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
			"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};
}
