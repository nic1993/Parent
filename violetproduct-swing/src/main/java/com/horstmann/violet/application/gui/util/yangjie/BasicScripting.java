package com.horstmann.violet.application.gui.util.yangjie;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class BasicScripting {
	public void greet() throws ScriptException {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		// ScriptEngine engine = manager.getEngineByExtension("js");
		// ScriptEngine engine = manager.getEngineByMimeType("text/javascript");
		if (engine == null) {
			throw new RuntimeException("找不到JavaScript语言执行引擎。");
		}
		engine.eval("println('Hello!');");
	}

	public static void main(String[] args) {
		try {
			new BasicScripting().greet();
		} catch (ScriptException ex) {
			Logger.getLogger(BasicScripting.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}
}
