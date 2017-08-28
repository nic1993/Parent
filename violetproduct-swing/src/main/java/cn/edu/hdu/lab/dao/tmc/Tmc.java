package cn.edu.hdu.lab.dao.tmc;

import java.util.ArrayList;
import java.util.List;

public class Tmc {
	private String tmcType; //马尔科夫链的类型：SeqMarkov、UCMarkov、SoftMarkov;
	private String names;   //Markov链对应场景或用例的名字;
	private String owned;
	private State start;
	private List<State> states=new ArrayList<State>();
	private List<State> ends=new ArrayList<State>();
	private List<Transition> transitions=new ArrayList<Transition>();
	private String notation;//存放用例级Markov chain 的用例前置条件
	private double pro; //迁移概率
	private boolean isCombine=false;
	public Tmc(){}
	
	public Tmc(State start)
	{
		this.start=start;
		this.states.add(start);
	}
	public void printTmc()
	{
		System.out.println("对应用例的前置条件："+notation);
		System.out.println("执行概率："+pro);
		System.out.println("开始状态："+start.getName());
		System.out.println("状态集：");
		for (State state : states) 
		{
			state.print_state();
		}
		System.out.println("迁移集：");
		for (Transition transition : transitions) 
		{
			transition.print_transitition();
			//System.out.println(transition);
		}
		System.out.println("终止状态集：");
		for (State state : ends) 
		{
			state.print_state();
		}
		
	}
	public void statesAddState(State state)
	{
		this.states.add(state);
	}
	public void endsAddState(State endState)
	{
		this.ends.add(endState);
	}
	public void endsRemoveState(State endState)
	{
		this.ends.remove(endState);
	}
	public void statesAddStates(List<State> states)
	{
		this.states.addAll(states);
	}
	public void endsAddStates(List<State> states)
	{
		this.ends.addAll(states);
	}
	public void transitionsAddtransition(Transition trans)
	{
		this.transitions.add(trans);
	}
	public void transitionsAddtransitions(List<Transition> transitions)
	{
		this.transitions.addAll(transitions);
	}
	public State getStart() {
		return start;
	}
	public void setStart(State start) {
		this.start = start;
	}
	public List<State> getStates() {
		return states;
	}
	public void setStates(List<State> states) {
		this.states = states;
	}
	public List<State> getEnds() {
		return ends;
	}
	public void setEnds(List<State> ends) {
		this.ends = ends;
	}
	public List<Transition> getTransitions() {
		return transitions;
	}
	public void setTransitions(List<Transition> transitions) {
		this.transitions = transitions;
	}
	public String getNotation() {
		return notation;
	}
	public void setNotation(String notation) {
		this.notation = notation;
	}

	public double getPro() {
		return pro;
	}

	public void setPro(double pro) {
		this.pro = pro;
	}

	public boolean isCombine() {
		return isCombine;
	}

	public void setCombine(boolean isCombine) {
		this.isCombine = isCombine;
	}

	public String getTmcType() {
		return tmcType;
	}

	public void setTmcType(String tmcType) {
		this.tmcType = tmcType;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getOwned() {
		return owned;
	}

	public void setOwned(String owned) {
		this.owned = owned;
	}
	
}
