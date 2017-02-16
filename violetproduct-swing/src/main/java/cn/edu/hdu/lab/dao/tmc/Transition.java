package cn.edu.hdu.lab.dao.tmc;

public class Transition {
    private	State from;
    private State to;
    private TransFlag transFlag;
    
    private String label;        //时间型迁移or概率型迁移
    private Double prob;
    private double transTime;
    
    private String notation="";//边的标志：入边、出边、出入边；
    private String note="";//组合片段下的消息执行条件
    public Transition(){}
    public Transition(State from,State to,TransFlag transFlag)
    {
    	this.from=from;
    	this.to=to;
    	this.transFlag=transFlag;
    }
    public Transition(State from,State to,TransFlag transFlag,double transTime)
    {
    	this.from=from;
    	this.to=to;
    	this.transFlag=transFlag;
    	this.transTime=transTime;
    }
 
	public State getFrom() {
		return from;
	}
	public void setFrom(State from) {
		this.from = from;
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public State getTo() {
		return to;
	}
	public void setTo(State to) {
		this.to = to;
	}
	public TransFlag getTransFlag() {
		return transFlag;
	}
	public void setTransFlag(TransFlag transFlag) {
		this.transFlag = transFlag;
	}
	
	public Double getProb() {
		return prob;
	}
	public void setProb(Double prob) {
		this.prob = prob;
	}
	public double getTransTime() {
		return transTime;
	}
	public void setTransTime(double transTime) {
		this.transTime = transTime;
	}
	
	
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getNotation() {
		return notation;
	}
	public void setNotation(String notation) {
		this.notation = notation;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Transition [from=");
		builder.append(from);
		builder.append(", to=");
		builder.append(to);
		builder.append(", transFlag=");
		builder.append(transFlag);
		builder.append(", transTime=");
		builder.append(transTime);
		builder.append(", note=");
		builder.append(note);
		builder.append(", notation=");
		builder.append(notation);
		builder.append("]");
		return builder.toString();
	}
	public void print_transitition()
	{
		System.out.println("Transition [from=" + this.getFrom().getName() + ", to=" + this.getTo().getName()
				+"\tto---label="+this.to.getLabel()+"\tto---postSD="+this.to.getNotation()
				+", messageName="+ this.transFlag.getName()+"\t出入边标记="+this.getNotation()
				+ "\tsender=" + this.transFlag.getSender()  + "\treceiver=" + this.transFlag.getReceiver() 
				+ ", prob=" + this.transFlag.getProb() 	+ ", transTime=" + transTime+" "+ this.transFlag.getStimulate()+"]");
	}
    
	
}
