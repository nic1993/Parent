package com.horstmann.violet.application.gui.util.yangjie;

import java.util.ArrayList;
import java.util.List;


/**
 * 此类用来封装收集Markov链路径等的相关功能
 * 
 * @author 夏沐
 * 
 */
public class CollectRoute {

	private List<Route> routeList;// 存放Markov链上的路径集合
	private List<Transition> transitionList;// 将集合当做栈来用 收集每一个路径
	private double routeProbability = 1.0;
	private int tcNumber;

	public void collect(Markov markov) {
		routeList = new ArrayList<Route>();
		transitionList = new ArrayList<Transition>();
		tcNumber = markov.getTcNumber();
		State initialState = markov.getInitialState();
		initialState.setStateAccessTimes(1);
		dfs(initialState);
		markov.setRouteList(routeList);
		int actualTcNum = getActualTcNum(routeList);
		// 当测试用例个数差距超过100个并且绕环次数没超过10环
		if (tcNumber - actualTcNum > 10 && Constant.maxCircle < 11) {
			Constant.maxCircle++;
			collect(markov);
		}
		double dbCoverage = 0;
		for (Route route : routeList) {
			dbCoverage += route.getRouteProbability();
		}
		markov.setDbCoverage(dbCoverage);
	}

	private int getActualTcNum(List<Route> rl) {
		int actualTcNum = 0;
		for (Route route : rl) {
			actualTcNum += route.getNumber();
		}
		return actualTcNum;
	}

	/**
	 * 对Markov链进行一次深度优先搜索即可收集所有路径
	 * 
	 * @param initialState
	 *            初始状态
	 */
	private void dfs(State initialState) {
		List<Transition> outTransitions = initialState.getOutTransitions();
		if (outTransitions != null && outTransitions.size() != 0) {
			for (Transition transition : outTransitions) {
				// if (transition.isVisited() == false) {
				// transition.setVisited(true);
				// 遇到节点已经访问过两次就换迁移，以此保证环路只走一次
				if (transition.getNextState().getStateAccessTimes() == Constant.maxCircle) {
					continue;
				}
				// 每访问下一个状态节点，使其节点访问次数+1
				transition.getNextState().setStateAccessTimes(
						transition.getNextState().getStateAccessTimes() + 1);
				transitionList.add(transition);
				routeProbability = routeProbability
						* transition.getProbability();
				State nextState = transition.getNextState();
				if (nextState.getLabel().equals("final")) {
					List<Transition> oneRoute = new ArrayList<Transition>();
					oneRoute.addAll(transitionList);
					Route route = new Route();
					route.setTransitionList(oneRoute);
					route.setRouteProbability(routeProbability);

					route.setNumber((int) Math.round(tcNumber
							* routeProbability));

					// route.setNumber((int) (tcNumber * routeProbability));
					routeList.add(route);
				}
				dfs(nextState);
				transitionList.remove(transitionList.size() - 1);
				routeProbability = routeProbability
						/ transition.getProbability();
				// 回溯的时候记得要把节点的访问次数减一
				nextState
						.setStateAccessTimes(nextState.getStateAccessTimes() - 1);
				// }
			}
		}
	}
}
