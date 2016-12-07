package com.horstmann.violet.application.menu.xiaole.SequenceTransfrom;

import java.util.ArrayList;
import java.util.List;

public class FragmentPartInfo {

	
	private String conditionText;
	private String size;//这里的size指的是分块区域高度
	private List<CombinedFragmentInfo> nestingchilds=new ArrayList<CombinedFragmentInfo>();//嵌套的组合片段
	private List<MessageInfo> concluedmessages=new ArrayList<MessageInfo>();//覆盖的消息
	public String getConditionText() {
		return conditionText;
	}
	public void setConditionText(String conditionText) {
		this.conditionText = conditionText;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public List<CombinedFragmentInfo> getNestingchilds() {
		return nestingchilds;
	}
	public void AddNestingchilds(CombinedFragmentInfo nestingchilds) {
		this.nestingchilds.add(nestingchilds);
	}
	public List<MessageInfo> getConcluedmessages() {
		return concluedmessages;
	}
	public void AddConcluedmessages(MessageInfo concluedmessages) {
		this.concluedmessages.add(concluedmessages);
	}
	
	
	
	
}
