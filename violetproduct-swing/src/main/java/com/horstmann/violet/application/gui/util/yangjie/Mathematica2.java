package com.horstmann.violet.application.gui.util.yangjie;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.MathLinkFactory;

public class Mathematica2 {
	private static String parameter1;
	private static String parameter2;
	private static String result;

	private static Map<String, String> varToVoc;

	public static String getSolution(String param1, String param2) {

		KernelLink ml = null;

		String path = "-linkmode launch -linkname 'D:\\Mathematica\\10.2\\MathKernel.exe'";
		try {

			ml = MathLinkFactory.createKernelLink(path);
		} catch (MathLinkException e) {
			System.out.println("Fatal error opening link: " + e.getMessage());
			return null;
		}

		try {
			// Get rid of the initial InputNamePacket the kernel will send
			// when it is launched.
			ml.discardAnswer();

			ml.evaluate("<<MyPackage.m");
			ml.discardAnswer();

			ml.putFunction("FindInstance", 3);

			ml.endPacket();
			ml.waitForAnswer();
			String result1 = ml.getString();

			// If you want the result back as a string, use evaluateToInputForm
			// or evaluateToOutputForm. The second arg for either is the
			// requested page width for formatting the string. Pass 0 for
			// PageWidth->Infinity. These methods get the result in one
			// step--no need to call waitForAnswer.
			String strResult = ml.evaluateToOutputForm(
					"A = SetAccuracy[FindInstance[" + param1 + ", " + param2
							+ ", 50], 3]; A[[RandomInteger[{1, Length[A]}]]]",
					0);
			// System.out.println(strResult);
			return strResult;

		} catch (MathLinkException e) {
			System.out.println("MathLinkException occurred: " + e.getMessage());
		} finally {
			ml.close();

		}
		return null;

	}

	public static KernelLink ml;
	public static final String PATH = "-linkmode launch -linkname 'D:\\Mathematica\\10.2\\MathKernel.exe'";

	public static String getSolution2(String param1, String param2) {

		// KernelLink ml = null;

		// String path =
		// "-linkmode launch -linkname 'D:\\Program Files\\Wolfram Research\\Mathematica\\10.2\\MathKernel.exe'";
		try {
			// ml = MathLinkFactory.createKernelLink(PATH);
			if (ml == null) {

				ml = MathLinkFactory.createKernelLink(PATH);
				ml.discardAnswer();

				ml.evaluate("<<MyPackage.m");
				ml.discardAnswer();

				ml.putFunction("FindInstance", 3);

				ml.endPacket();
				ml.waitForAnswer();
				String result1 = ml.getString();
			}
		} catch (MathLinkException e) {
			System.out.println("Fatal error opening link: " + e.getMessage());
			return null;
		}

		try {
			// Get rid of the initial InputNamePacket the kernel will send
			// when it is launched.
			// ml.discardAnswer();
			//
			// ml.evaluate("<<MyPackage.m");
			// ml.discardAnswer();
			//
			// ml.putFunction("FindInstance", 3);
			//
			// ml.endPacket();
			// ml.waitForAnswer();
			// String result1 = ml.getString();

			// If you want the result back as a string, use evaluateToInputForm
			// or evaluateToOutputForm. The second arg for either is the
			// requested page width for formatting the string. Pass 0 for
			// PageWidth->Infinity. These methods get the result in one
			// step--no need to call waitForAnswer.

			// 将param1和param2中参数标识符按顺序全都替换为26个字母,防止变量命名不符合mathematica规则导致无法运算
			System.out.println("不等式组和参数集替换前：" + param1 + "======" + param2);
			replace(param1, param2);
			System.out.println("不等式组和参数集替换后：" + parameter1 + "======"
					+ parameter2);
			String strResult = ml.evaluateToOutputForm(
					"SetAccuracy[FindInstance[{" + parameter1 + "}, {"
							+ parameter2 + "}, 50], 3]", 0);
			// System.out.println(strResult);
			// 再将替换的参数标识符替换回原样
			System.out.println("变量名还原前计算结果：" + strResult);
			recovery(strResult);
			System.out.println("变量名还原后计算结果：" + result);
			return result;

		} catch (Exception e) {
			System.out.println("MathLinkException occurred: " + e.getMessage());
		} finally {
			// ml.close();
			// ml = null;
		}
		return null;
	}

	/**
	 * 将计算结果中的变量还原为本来的参数标识符
	 * 
	 * @param strResult
	 */
	private static void recovery(String strResult) {
		Set<String> keySet = varToVoc.keySet();
		for (String key : keySet) {
			strResult = strResult.replace(varToVoc.get(key) + " ", key.trim());
		}
		result = strResult;
	}

	/**
	 * 将参数标识符用字母表替换
	 * 
	 * @param param1
	 * @param param2
	 */
	private static void replace(String param1, String param2) {
		String[] vars = param2.split(",");
		varToVoc = new HashMap<String, String>();
		char vocabulary = 'a';
		for (String var : vars) {
			param2 = param2.replace(var.trim(), vocabulary + "" + vocabulary
					+ vocabulary);
			param1 = param1.replace(var.trim(), vocabulary + "" + vocabulary
					+ vocabulary);
			varToVoc.put(var.trim(), vocabulary + "" + vocabulary + vocabulary);
			vocabulary = (char) (vocabulary + 1);

		}

		parameter1 = param1;
		parameter2 = param2;
		// System.out.println(param1 + "---" + param2);

	}

}
