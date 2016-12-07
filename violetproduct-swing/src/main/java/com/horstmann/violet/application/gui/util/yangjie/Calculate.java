package com.horstmann.violet.application.gui.util.yangjie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Calculate {

	public static void getAllTransValues(Markov markov) {

		for (State state : markov.getStates()) {
			// System.out.println(state.getOutTransitions().size());
			for (Transition t : state.getOutTransitions()) {

				Stimulate stimulate = t.getStimulate();
				List<String> inequalities = new ArrayList<String>(); // 存储所有约束条件，包括变量定义域和变量间约束
				List<String> variables = new ArrayList<String>(); // 存储激励上所有的连续型取值范围的参数名称
				// 遍历当前激励中的所有参数，收集需要计算的约束条件和参数
				List<String> allBorder = new ArrayList<String>();// 存放当前激励上所有参数的边界
				for (Parameter p : stimulate.getParameters()) {
					// 收集约束不等式集合inequalities
					if ((p.getParamType().equals("int") || p.getParamType()
							.equals("double"))
							&& p.getDomainType().equals("serial")) {
						inequalities.add(p.getDomain());
						for (String b : p.getBorderValue()) {
							allBorder.add(p.getName() + "==" + b);
						}
						variables.add(p.getName());
						// 如果当前参数的定义域是连续型，且参数类型是int型，则在约束集合中多加一个参数∈integers约束条件，以此来得到此参数的整形结果值
						if (p.getParamType().equals("int")) {
							inequalities.add(p.getName() + "∈Integers");
						}
					} /*
					 * else if (p.getDomainType().equals("discrete")) {//
					 * 处理离散型定义域的变量，随机在离散值中选一个值赋值给当前参数的value属性 String[] values =
					 * p.getDomain().split(","); String value = values[new
					 * Random() .nextInt(values.length)]; p.setValue(value); }
					 */
				}
				// 把当前激励上的参数变量间约束条件也加入约束条件集合
				for (String constraint : stimulate.getConstraints()) {
					inequalities.add(constraint);
				}

				Map<String, List<String>> map = null;

				// 如果约束集合不为空，就先把约束条件集合和参数集合拼接成mathematica工具计算所需要的参数形式
				if (inequalities != null && inequalities.size() != 0) {
					boolean flag = true;
					for (String s : allBorder) {
						if (s.contains("==0")) {
							continue;
						}
						inequalities.add(s);
						// 拼接约束条件成参数形式
						String param1 = "{"
								+ inequalities.toString().substring(1,
										inequalities.toString().length() - 1)
								+ "}";
						// 拼接参数变量成参数形式
						String param2 = "{"
								+ variables.toString().substring(1,
										variables.toString().length() - 1)
								+ "}";
						String solution = Mathematica.getSolution2(param1,
								param2);// 通过mathematica工具的调用接口计算不等式组并返回结果
						if (!solution.equals("{}")) {
							map = parse(solution);
							flag = false;
							break;
						}
						inequalities.remove(s);
					}

					if (flag) {
						// 拼接约束条件成参数形式
						String param1 = "{"
								+ inequalities.toString().substring(1,
										inequalities.toString().length() - 1)
								+ "}";
						// 拼接参数变量成参数形式
						String param2 = "{"
								+ variables.toString().substring(1,
										variables.toString().length() - 1)
								+ "}";
						String solution = Mathematica.getSolution2(param1,
								param2);// 通过mathematica工具的调用接口计算不等式组并返回结果
						map = parse(solution);
					}
				}

				for (Parameter p : stimulate.getParameters()) {

					if (!p.getDomainType().equals("discrete")) {
						List<String> values = map.get(p.getName());
						if (p.getParamType().equals("int")) {
							List<String> list = new ArrayList<String>();
							for (String string : values) {
								list.add((int) Double.parseDouble(string.trim())
										+ "");
							}
							values = list;
						}
						p.setValues(values);
						// System.out.println();
					} else {
						String[] values = p.getDomain().split(",");
						List<String> list = new ArrayList<String>();
						for (String str : values) {
							list.add(str);
						}
						p.setValues(list);

					}
				}

			}
		}

	}

	private static Map<String, List<String>> parse(String solution) {

		Map<String, List<String>> map = new HashMap<String, List<String>>();
		String[] results = solution.substring(2, solution.length() - 2).split(
				"\\}, \\{");
		for (String string : results) {
			if (string.contains(",")) {
				String[] strs = string.split(",");
				for (String str : strs) {
					String[] varAndVal = str.split("->");
					if (map.containsKey(varAndVal[0].trim())) {
						map.get(varAndVal[0].trim()).add(varAndVal[1].trim());
					} else {
						List<String> list = new ArrayList<String>();
						list.add(varAndVal[1].trim());
						map.put(varAndVal[0].trim(), list);
					}
				}
			} else {
				String[] varAndVal = string.split("->");
				if (map.containsKey(varAndVal[0].trim())) {
					map.get(varAndVal[0].trim()).add(varAndVal[1].trim());
				} else {
					List<String> list = new ArrayList<String>();
					list.add(varAndVal[1].trim());
					map.put(varAndVal[0].trim(), list);
				}
			}
		}

		return map;
	}

}
