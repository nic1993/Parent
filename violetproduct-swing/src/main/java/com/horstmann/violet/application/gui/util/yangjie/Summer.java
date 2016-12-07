package com.horstmann.violet.application.gui.util.yangjie;

public class Summer {

	public static void main(String[] args) {
		test_1();
	}

	public static void test_1() {
		String str1 = "jjjyjjj";
		String str2 = str1;
		System.out.println(str2);
		str2 = str2.replace("yj", "go");
		System.out.println(str2);
		System.out.println(str1);
	}
}
