package com.horstmann.violet.application.gui.util.yangjie;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;


public class BestAssign {

	public void assign(Markov markov, Element root) {
		int actualTcNumber = 0;
		List<Route> routeList = markov.getRouteList();
		for (Route route : routeList) {
			actualTcNumber += route.getNumber();
			List<Transition> transitionList = route.getTransitionList();
			List<Stimulate> stimulateList = transform(transitionList,
					route.getNumber());
			for (int i = 0; i < route.getNumber(); i++) {
				System.out.print("测试用例：");
				RandomCase.getCase(stimulateList, root);
			}
		}
		System.out.println("\n实际生成的总测试用例个数为：" + actualTcNumber);

		printBaseTestSequence(routeList);

		System.out.println("\n利用欧氏距离计算出的最佳模型相似度："
				+ CalculateSimilarity.statistic_1(markov));
	}

	/**
	 * 打印所有的路径测试序列
	 * 
	 * @param routeList
	 */
	private void printBaseTestSequence(List<Route> routeList) {
		System.out.println("\nMarkov链的基础测试序列集包括如下" + routeList.size() + "个：");
		for (Route route : routeList) {
			String testSequence = "";
			List<Transition> transitionList = route.getTransitionList();
			for (Transition transition : transitionList) {
				testSequence += transition.getName() + "→→";
			}
			testSequence = testSequence.substring(0, testSequence.length() - 2);
			System.out.println("			测试序列：" + testSequence + "	 路径概率:"
					+ route.getRouteProbability() + "	此类用例包含"
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
