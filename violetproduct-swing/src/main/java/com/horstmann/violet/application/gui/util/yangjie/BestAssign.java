package com.horstmann.violet.application.gui.util.yangjie;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;


public class BestAssign {
	int actualTcNumber = 0;

	public void assign(Markov markov, Element root) {

		List<Route> routeList = markov.getRouteList();
		RandomCase randomCase = new RandomCase();
		for (Route route : routeList) {
			actualTcNumber += route.getNumber();
			List<Transition> transitionList = route.getTransitionList();
			List<Stimulate> stimulateList = transform(transitionList,
					route.getNumber());
			String testSequence = getTestSequence(stimulateList);
			String stimulateSequence = getStimulateSequence(stimulateList);
			TCDetail.getInstance().setTestSequence(testSequence);
			TCDetail.getInstance().setStimulateSequence(stimulateSequence);
			for (int i = 0; i < route.getNumber(); i++) {
//				System.out.print("测试用例：");
				randomCase.getCase(stimulateList, root);
			}
		}
		markov.setActualNum(actualTcNumber);
		System.out.println("\n实际生成的总测试用例个数为：" + actualTcNumber);

		printBaseTestSequence(routeList);

		// System.out.println("\n利用欧氏距离计算出的最佳模型相似度："
		// + CalculateSimilarity.statistic_1(markov));
	}

	/**
	 * 从激励集合获取激励序列
	 * 
	 * @param stimulateList
	 * @return
	 */
	private String getStimulateSequence(List<Stimulate> stimulateList) {
		String stimulateSequence = "";
		for (int i = 0; i < stimulateList.size(); i++) {
			if (i != stimulateList.size() - 1) {
				stimulateSequence = stimulateSequence
						+ stimulateList.get(i).toString() + "-->>";
				// System.out.print(oneCaseExtend.get(i).toString() + "-->>");
			} else {
				stimulateSequence = stimulateSequence
						+ stimulateList.get(i).toString();
				// System.out.println(oneCaseExtend.get(i).toString());
			}
		}
		return stimulateSequence;
	}

	/**
	 * 从激励集合获取测试序列
	 * 
	 * @param stimulateList
	 * @return
	 */
	private String getTestSequence(List<Stimulate> stimulateList) {
		String testSequence = "";
		for (int i = 0; i < stimulateList.size(); i++) {
			if (i != stimulateList.size() - 1) {
				testSequence = testSequence + stimulateList.get(i).getName()
						+ "-->>";
				// System.out.print(oneCaseExtend.get(i).toString() + "-->>");
			} else {
				testSequence = testSequence + stimulateList.get(i).getName();
				// System.out.println(oneCaseExtend.get(i).toString());
			}
		}
		return testSequence;
	}

	/**
	 * 打印所有的路径测试序列
	 * 
	 * @param routeList
	 */
	private void printBaseTestSequence(List<Route> routeList
			 ) {
		System.out.println("\nMarkov链的基础测试序列集包括如下" + routeList.size() + "个：");

		for (Route route : routeList) {
			String testSequence = "";
			List<Transition> transitionList = route.getTransitionList();
			for (Transition transition : transitionList) {
				testSequence += transition.getName() + "→→";
			}
			testSequence = testSequence.substring(0, testSequence.length() - 2);
			route.setTcSequence(testSequence);
			route.setActualPercent(route.getNumber() * 1.0 / actualTcNumber);
			System.out.println("			测试序列：" + testSequence
					+ "	 路径概率(指标-可靠性测试用例生成比率" + route.getActualPercent()
					+ "):   " + route.getRouteProbability() + "	此类用例包含"
					+ route.getNumber() + "个");
		}
	}

	/**
	 * 将迁移集合转换成激励集合
	 * 
	 * @param transitionList
	 * @param routeNumber
	 * @return
	 */
	private List<Stimulate> transform(List<Transition> transitionList,
			int routeNumber) {
		List<Stimulate> stimulateList = new ArrayList<Stimulate>();
		for (Transition transition : transitionList) {
			stimulateList.add(transition.getStimulate());
			transition
					.setAccessTimes(transition.getAccessTimes() + routeNumber);
		}
		return stimulateList;
	}
}
