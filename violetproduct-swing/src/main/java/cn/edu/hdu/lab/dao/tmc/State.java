package cn.edu.hdu.lab.dao.tmc;

public class State {
	private String TmcID;
	private String name;
	private String label;
	
	private String notation;//存放场景后置条件
	//private String notion;   // 状态对应组合片段的信息 
	public State(){}
	public State(String name)
	{
		this.name=name;
	}
	public String getTmcID() {
		return TmcID;
	}
	public void setTmcID(String tmcID) {
		TmcID = tmcID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
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
		builder.append("State [TmcID=");
		builder.append(TmcID);
		builder.append(", name=");
		builder.append(name);
		builder.append(", label=");
		builder.append(label);
		builder.append(", notation=");
		builder.append(notation);
		builder.append("]");
		return builder.toString();
	}
	
	public void print_state() {
		System.out.println("State [TmcID=" + TmcID + ", name=" + name + ", "
				+"label="+label+",notation="+ notation  + "]");
	}
	
	
}
