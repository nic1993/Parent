package com.horstmann.violet.application.gui.util.yangjie;

import java.util.List;
import java.util.Random;

import org.dom4j.Element;


public class RandomCase {

	public static void getCase(List<Stimulate> oneCaseExtend, Element root) {

		Element tc = root.addElement("testcase");

		String testCase = "";
		for (int i = 0; i < oneCaseExtend.size(); i++) {
			Stimulate stimulate = oneCaseExtend.get(i);
			// 生成测试用例时需要去掉测试序列中的空迁移null
			if ("null".equals(stimulate.getName())) {
				continue;
			}

			int random = -1;
			String str = "";
			for (int j = 0; j < stimulate.getParameters().size(); j++) {

				Parameter parameter = stimulate.getParameters().get(j);
				String value = null;
				if (parameter.getDomainType().equals("serial")) {
					List<String> values = parameter.getValues();
					if (random == -1) {

						random = new Random().nextInt(values.size());
					}
					value = values.get(random);
				} else {

					value = parameter.getValues().get(
							new Random().nextInt(parameter.getValues().size()));
				}

				if (j != stimulate.getParameters().size() - 1) {
					str = str + parameter.getName() + "=" + value + ",";
				} else {
					str = "[" + str + parameter.getName() + "=" + value + "]";
				}
			}

			if (i != oneCaseExtend.size() - 1) {
				testCase += stimulate.getName() + str + "→→";

				Element process = tc.addElement("process");
				process.addElement("operation").setText(stimulate.getName());
				if (str.equals("")) { // 没有输入参数的情况
					process.addElement("input").setText("null");
				} else {
					process.addElement("input").setText(
							str.substring(1, str.length() - 1));
				}

			} else {
				testCase += stimulate.getName() + str;

				Element process = tc.addElement("process");
				process.addElement("operation").setText(stimulate.getName());
				if (str.equals("")) {
					process.addElement("input").setText("null");
				} else {
					process.addElement("input").setText(
							str.substring(1, str.length() - 1));
				}

			}
		}

		System.out.println(testCase);

	}
}
