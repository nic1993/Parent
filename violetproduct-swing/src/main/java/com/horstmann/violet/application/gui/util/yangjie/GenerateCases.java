package com.horstmann.violet.application.gui.util.yangjie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;


/**
 * 建立一个专门用来产生测试用例和测试路径的类，其中包括生成测试用例和路径的方法、 选择下一步迁移的方法、和输出测试用例和路径的方法等。
 * 
 * @author YJ
 * @version 1.0
 * */

public class GenerateCases {

	private static final int COEFFICIENT = 8; // 设置一个常量系数
	private int oneBatchSize; // 生成的每一批的测试用例个数
	private List<List<Integer>> testPaths = new ArrayList<List<Integer>>(); // 测试路径集合
	private List<List<String>> testCases = new ArrayList<List<String>>(); // 测试用例集合
	private List<List<Stimulate>> testCasesExtend = new ArrayList<List<Stimulate>>();

	/**
	 * 产生测试用例和路径并打印至控制台，同时将用例写入文件。
	 * 
	 * @param markov
	 *            接受一个markov链对象
	 * @param bufw
	 *            接受一个文件输出流对象
	 * @return 返回当前产生的测试用例和测试路径的个数
	 * */
	public int generate(Markov markov, Element root) throws IOException {

		oneBatchSize = (int) (markov.getStates().size()
				* markov.getStates().size() * 0.5); // 设置每一批生成N*50个测试用例
		int startSize = testCases.size();

		while (testCases.size() - startSize < oneBatchSize) {

			List<Integer> onePath = new ArrayList<Integer>(); // 存储当前生成的一个测试路径
			List<String> oneCase = new ArrayList<String>(); // 存储当前生成的一个测试用例
			List<Stimulate> oneCaseExtend = new ArrayList<Stimulate>();// 存储当前生成的一个测试用例扩展版

			onePath.add(0);
			oneCase.add(markov.getStates().get(0).getStateName());

			State currentState = markov.getInitialState();
			// System.out.println(currentState.getStateName()+currentState.getLabel());
			currentState
					.setStateAccessTimes(currentState.getStateAccessTimes() + 1);

			while (!currentState.getLabel().equals("final")) {

				// System.out.println(currentState.getOutTransitions().size());
				Transition nextTransition = rouletteWheels(currentState
						.getOutTransitions()); // 通过赌轮算法获取当前状态节点的下一个出迁移

				nextTransition
						.setAccessTimes(nextTransition.getAccessTimes() + 1);

				currentState = nextTransition.getNextState();
				onePath.add(currentState.getStateNum());
				oneCase.add(nextTransition.getName());
				// 把当前迁移上面的激励对象存储到集合
				oneCaseExtend.add(nextTransition.getStimulate());

				currentState.setStateAccessTimes(currentState
						.getStateAccessTimes() + 1);

			}

			testPaths.add(onePath);
			testCases.add(oneCase);
			testCasesExtend.add(oneCaseExtend);

			// bufw.write(oneCase.toString());
			// bufw.newLine();
			// bufw.flush();

			// printCaseAndPath(oneCase, onePath);
			printCaseAndPath(oneCase, oneCaseExtend, onePath, root);
		}

		return testCases.size();
	}

	private void printCaseAndPath(List<String> oneCase,
			List<Stimulate> oneCaseExtend, List<Integer> onePath, Element root) {

		System.out.print("测试序列:");
		for (int i = 0; i < oneCase.size(); i++) {
			if (i != oneCase.size() - 1) {
				System.out.print(oneCase.get(i) + "-->>");
			} else {
				System.out.println(oneCase.get(i));
			}
		}

		System.out.print("激励序列:");
		for (int i = 0; i < oneCaseExtend.size(); i++) {
			if (i != oneCaseExtend.size() - 1) {
				System.out.print(oneCaseExtend.get(i).toString() + "-->>");
			} else {
				System.out.println(oneCaseExtend.get(i).toString());
			}
		}

		System.out.print("测试用例:");
		// Evaluation.getValue(oneCaseExtend);

		RandomCase.getCase(oneCaseExtend, root);

		System.out.print("测试路径:");
		for (int i = 0; i < onePath.size(); i++) {
			if (i != onePath.size() - 1) {
				System.out.print(onePath.get(i) + "-->>");
			} else {
				System.out.println(onePath.get(i));
				System.out.println();
			}
		}

	}

	/**
	 * 将产生的测试用例和测试路径输出至控制台
	 * 
	 * @param oneCase
	 *            接受一个测试用例
	 * @param onePath
	 *            接受一个测试路径
	 * */
	private void printCaseAndPath(List<String> oneCase, List<Integer> onePath) {

		System.out.print("测试用例:");
		for (int i = 0; i < oneCase.size(); i++) {
			if (i != oneCase.size() - 1) {
				System.out.print(oneCase.get(i) + "-->>");
			} else {
				System.out.println(oneCase.get(i));
			}
		}

		System.out.print("测试路径:");
		for (int i = 0; i < onePath.size(); i++) {
			if (i != onePath.size() - 1) {
				System.out.print(onePath.get(i) + "-->>");
			} else {
				System.out.println(onePath.get(i));
				System.out.println();
			}
		}
	}

	/**
	 * 赌轮算法: 用于选择后续迁移
	 * 
	 * @param outTransitions
	 *            接受一个状态节点的出迁移集合
	 * @return 返回利用赌轮算法从出迁移集合中选择出的一个迁移
	 * */
	private Transition rouletteWheels(List<Transition> outTransitions) {

		int i = 0;
		double[] probs = new double[outTransitions.size()];
		for (Transition t : outTransitions) {
			probs[i++] = t.getProbability();
		}

		double sum = 0.0;
		for (int j = 0; j < probs.length; j++) {
			sum += probs[j];
			probs[j] = sum;
		}
		for (int j = 0; j < probs.length; j++) {
			probs[j] /= sum;
		}

		double random = Math.random();
		for (int j = 0; j < probs.length; j++) {
			if (random <= probs[j]) {
				return outTransitions.get(j);
			}
		}

		System.out.println("选择后续迁移失败!");
		return null;
	}

	public int getOneBatchSize() {
		return oneBatchSize;
	}

	public void setOneBatchSize(int oneBatchSize) {
		this.oneBatchSize = oneBatchSize;
	}

	public List<List<Integer>> getTestPaths() {
		return testPaths;
	}

	public void setTestPaths(List<List<Integer>> testPaths) {
		this.testPaths = testPaths;
	}

	public List<List<String>> getTestCases() {
		return testCases;
	}

	public void setTestCases(List<List<String>> testCases) {
		this.testCases = testCases;
	}

	public static int getCoefficient() {
		return COEFFICIENT;
	}

}
