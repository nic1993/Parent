package com.horstmann.violet.application.gui.util.yangjie;

/**
 * 通过单例设计模式构造的测试用例各信息的组合bean 方便每条测试用例各信息的存取
 * 
 * @author 夏沐
 *
 */
public class TCDetail {
	private int id;// 唯一标识
	private String testSequence;// 测试序列
	private String stimulateSequence;// 激励序列
	private String testCase;// 测试用例

	private static TCDetail tcDetail = null;

	public TCDetail() {
		// super();
	}

	public static TCDetail getInstance() {
		if (tcDetail == null) {
			tcDetail = new TCDetail();
		}
		return tcDetail;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTestSequence() {
		return testSequence;
	}

	public void setTestSequence(String testSequence) {
		this.testSequence = testSequence;
	}

	public String getStimulateSequence() {
		return stimulateSequence;
	}

	public void setStimulateSequence(String stimulateSequence) {
		this.stimulateSequence = stimulateSequence;
	}

	public String getTestCase() {
		return testCase;
	}

	public void setTestCase(String testCase) {
		this.testCase = testCase;
	}

}
