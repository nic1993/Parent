package com.horstmann.violet.application.gui.util.suna.TimeExtend;

public class Arc {
	private String name = null;            //迁移名
	private String label = null;           //标签，分辨是prob还是time
	private String time = null;            //迁移时间约束，迁移的属性label是time时，有time元素
	private String type = null;
	private String owned = null;
	private String conditions = null;

	private double prob;                   //迁移概率，迁移属性label为prob时，有prob元素
	private Stimulate stimulate;           //迁移上约束载体，内涵约束表达式的相关信息
	private String toStateName;            //目的节点名
	private String fromStateName;          //源节点名
	
	public Arc(){}
	
	//重载Arc构造方法，用于在模型扩展时新建Arc;
    public Arc(String name,String targetName,double prob) {
        this.name = name;
        this.toStateName = targetName;
        this.prob = prob;
    }
	
	@Override
	public String toString() {
		return "Arc [ name=" + name + ", label=" + label + ", type=" +type + ", prob=" +prob + ", time=" + time + ", owned=" + owned +", consitions=" + conditions +"]";
	} 
	public String getName() {
		return name;
	}
	
	public String getConditions() {
		return conditions;
	}
	public void setConditions(String conditions) {
		this.conditions = conditions;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOwned() {
		return owned;
	}
	public void setOwned(String owned) {
		this.owned = owned;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label; 
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public double getProb() {
		return prob;
	}
	public void setProb(double prob) {
		this.prob = prob;
	}
	public Stimulate getStimulate() {
		return stimulate;
	}
	public void setStimulate(Stimulate stimulate) {
		this.stimulate = stimulate;
	}
	public String getToStateName() {
		return toStateName;
	}
	public void setToStateName(String toStateName) {
		this.toStateName = toStateName;
	}

	public String getFromStateName() {
		return fromStateName;
	}

	public void setFromStateName(String fromStateName) {
		this.fromStateName = fromStateName;
	}
}
