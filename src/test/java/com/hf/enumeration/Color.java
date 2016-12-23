package com.hf.enumeration;

public enum Color {
	//RED, GREEN, BLANK, YELLOW 
	RED("红色", 1), GREEN("绿色", 2), BLANK("白色", 3), YELLO("黄色", 4);

	// 成员变量
	private String name;
	private int index;

	// 构造方法  enum的构造方法不能为public protected 
	//只能private（不写修饰符。编译后也会加上private）
	Color(String name, int index) {
		this.name = name;
		this.index = index;
	}

	// 普通方法
	public static String getName(int index) {
		for (Color c : Color.values()) {
			if (c.getIndex() == index) {
				return c.name;
			}
		}
		return null;
	}

	// get set 方法
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
