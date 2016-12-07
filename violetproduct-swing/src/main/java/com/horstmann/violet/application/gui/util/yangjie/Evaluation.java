package com.horstmann.violet.application.gui.util.yangjie;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * 此类专门用来求解激励序列中每个激励的约束集并得到每个参数的合法值 以此来生成测试用例
 * 
 * @author YJ
 * @version 1.0
 */

public class Evaluation {

	public static void getValue(List<Stimulate> oneCaseExtend) {

		// 遍历激励序列上的每个激励
		for (int i = 0; i < oneCaseExtend.size(); i++) {
			List<String> inequalities = new ArrayList<String>(); // 存储所有约束条件，包括变量定义域和变量间约束
			List<String> variables = new ArrayList<String>(); // 存储激励上所有的连续型取值范围的参数名称
			// 遍历当前激励中的所有参数，收集需要计算的约束条件和参数
			for (Parameter p : oneCaseExtend.get(i).getParameters()) {
				// 收集约束不等式集合inequalities
				if ((p.getParamType().equals("int") || p.getParamType().equals(
						"double"))
						&& p.getDomainType().equals("serial")) {
					inequalities.add(p.getDomain());
					variables.add(p.getName());
					// 如果当前参数的定义域是连续型，且参数类型是int型，则在约束集合中多加一个参数∈integers约束条件，以此来得到此参数的整形结果值
					if (p.getParamType().equals("int")) {
						inequalities.add(p.getName() + "∈Integers");
					}
				} else if (p.getDomainType().equals("discrete")) {// 处理离散型定义域的变量，随机在离散值中选一个值赋值给当前参数的value属性
					String[] values = p.getDomain().split(",");
					String value = values[new Random().nextInt(values.length)];
					p.setValue(value);
				}
			}
			// 把当前激励上的参数变量间约束条件也加入约束条件集合
			for (String constraint : oneCaseExtend.get(i).getConstraints()) {
				inequalities.add(constraint);
			}
			// 如果约束集合不为空，就先把约束条件集合和参数集合拼接成mathematica工具计算所需要的参数形式
			if (inequalities != null && inequalities.size() != 0) {
				// 拼接约束条件成参数形式
				String param1 = "{"
						+ inequalities.toString().substring(1,
								inequalities.toString().length() - 1) + "}";
				// 拼接参数变量成参数形式
				String param2 = "{"
						+ variables.toString().substring(1,
								variables.toString().length() - 1) + "}";
				String solution = Mathematica.getSolution(param1, param2);// 通过mathematica工具的调用接口计算不等式组并返回结果
				// 先去掉括号按逗号分割字符串类型的结果得到 变量->值 形式，再依次遍历按照->分割字符串就得到了 变量，值 字符串数组
				// 用这个变量去对比遍历当前激励上的参数集合，如果变量名相同，则把 值
				// 付给相应参数的value属性，结束后每个参数便都有了自己的值
				String[] results = solution.substring(1, solution.length() - 1)
						.split(",");
				for (String string : results) {
					String[] varAndVal = string.split("->");
					for (Parameter p : oneCaseExtend.get(i).getParameters()) {
						if (p.getName().equals(varAndVal[0].trim())) {
							if (p.getParamType().equals("int")) {// 如果此参数是int型，则将值先格式化为int值形式再赋值给此参数value属性
								p.setValue((int) Double
										.parseDouble(varAndVal[1].trim()) + "");
							} else {

								p.setValue(varAndVal[1].trim());
							}
							break;
						}
					}
				}
			}
			String stimul = oneCaseExtend.get(i).getName();
			String params = "";
			// 遍历当前激励的参数集合，把参数名和参数值拼接成 【参数名=参数值，参数名=参数值】的形式
			for (int i1 = 0; i1 < oneCaseExtend.get(i).getParameters().size(); i1++) {

				if (i1 != oneCaseExtend.get(i).getParameters().size() - 1) {
					params = params
							+ oneCaseExtend.get(i).getParameters().get(i1)
									.getName()
							+ "="
							+ oneCaseExtend.get(i).getParameters().get(i1)
									.getValue() + ",";
				} else {
					params = "["
							+ params
							+ oneCaseExtend.get(i).getParameters().get(i1)
									.getName()
							+ "="
							+ oneCaseExtend.get(i).getParameters().get(i1)
									.getValue() + "]";
				}
			}

			// 最后完整打印实例化后的当前激励
			if (i != oneCaseExtend.size() - 1) {

				System.out
						.print(oneCaseExtend.get(i).getName() + params + "→→");
			} else {
				System.out.println(oneCaseExtend.get(i).getName() + params);

			}

		}

	}
}
