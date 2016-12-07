package com.horstmann.violet.application.gui.util.yangjie;

import java.io.FileOutputStream;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * 项目的主函数所在类，包括获取file文件对象和判断markov链对象是否还有未被遍历过的迁移。
 * 
 * @author YJ
 * @version 1.0
 * */

public class TheOne {

	/**
	 * 主函数:负责调用读取类中的读方法，产生测试用例类中的生成测试用例方法， 计算平稳分布类中的计算平稳分布方法，计算相似度类中的计算相似度的方法等。
	 * */
	public static void main(String[] args) throws Exception {

		// ReadMarkov rm = new ReadMarkov();
		ReadMarkov2 rm = new ReadMarkov2();
		Markov markov = rm.readMarkov();

		// System.out.println(markov.getNumberOfStates());
		Calculate.getAllTransValues(markov);

		// 创建一个document对象，用于存储测试用例
		Document dom = DocumentHelper.createDocument();
		Element root = dom.addElement("TCS");

		double[] PI = CalculateDistribution.stationaryDistribution(markov);// 计算markov链的平稳分布

		double similarity = 999991;
		boolean sufficiency = false;
		GenerateCases gc = new GenerateCases();
		boolean flag = true;

		do {
			int numberOfTestCases = gc.generate(markov, root);
			// System.out.println(numberOfTestCases);
			if (flag) {

				sufficiency = isSufficient(markov);
			}

			if (!sufficiency) {
				continue;
			}
			flag = false;
			// similarity = CalculateSimilarity.statistic_1(markov);
			similarity = CalculateSimilarity.statistic(markov, PI);

			System.out.println("\n当前使用链和测试链的相似度为:" + similarity);
			System.out.println("\n当前生成的测试用例和测试路径的个数:" + numberOfTestCases
					+ "\n\n");

		} while (similarity > 0.001);

		// WriteTestCases.writeCases(gc.getTestCases());

		for (double d : PI) {
			System.out.print(d + "  ");
		}
		// 将存储好测试用例的document对象写入XML文件
		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter writer = new XMLWriter(new FileOutputStream(
				"E:/Markov/bilibili/tcs.xml"), format);
		writer.write(dom);
		writer.close();

	}

	/**
	 * 判断markov链中是否有访问次数为0的迁移
	 * 
	 * @param markov
	 *            接受一个指定的markov链对象
	 * @return 返回指定markov链对象是否满足此充分性的布尔值
	 * */
	private static boolean isSufficient(Markov markov) {

		for (State state : markov.getStates()) {

			for (Transition outTransition : state.getOutTransitions()) {

				if (outTransition.getAccessTimes() == 0) {
					return false;
				}
			}
		}
		return true;
	}

}
