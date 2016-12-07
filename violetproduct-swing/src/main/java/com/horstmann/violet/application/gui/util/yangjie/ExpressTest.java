package com.horstmann.violet.application.gui.util.yangjie;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ExpressTest {

	public static void test1() throws ScriptException {
		String str = "(0 <= a && a <= 5)";
		// String str = "( 0 <= a <= 5)";
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		engine.put("a", 3);
		Object result = engine.eval(str);
		System.out.println("结果类型:" + result.getClass().getName() + ",计算结果:"
				+ result);
	}

	public static void test2() throws ScriptException {
		String str = "43*(2 + 1.4)+2*32/(3-2.1)";
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		Object result = engine.eval(str);
		System.out.println("结果类型:" + result.getClass().getName() + ",计算结果:"
				+ result);
	}

	public static void main(String[] args) throws ScriptException {
		test1();
		test2();
	}
}
