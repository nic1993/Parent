package com.horstmann.violet.application.gui.util.yangjie;

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Test {
	public static void main(String[] args) throws Exception {
		String str = "(a + b>= 0 && a +b<= 6&&a-b>0)";
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		engine.put("a", 3);
		engine.put("b", 1);
		Object result = engine.eval(str);

		System.out.println("结果类型:" + result.getClass().getName() + ",计算结果:"
				+ result);
		test();
	}

	public static void test1() {
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		System.out.println(list.toString());
	}

	public static void test() {
		String solution = "{{x -> 2.93, y -> 3.07}, {x -> 3.76, y -> 5.76}, {x -> 5.80, y -> 6.48}, {x -> 2.98, y -> 3.02}, {x -> 5.24, y -> 6.25}, {x -> 2.13, y -> 4.02}, {x -> 5.67, y -> 6.08}, {x -> 2.00, y -> 4.00}, {x -> 2.09, y -> 3.96}, {x -> 2.67, y -> 4.25}}";
		String str = solution.substring(2, solution.length() - 2);
		String[] results = str.split("\\}, \\{");
		for (String string : results) {

			System.out.println(string);
		}
	}
}