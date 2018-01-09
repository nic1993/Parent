package com.horstmann.violet.application.StepOneBuildModel;

import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.horstmann.violet.application.StepTwoModelExpand.ScenceTabelPanel;

public class Radio extends JRadioButton{

	private String quota;
	private JPanel scenceTabelPanel = null;

	public JPanel getScenceTabelPanel() {
		return scenceTabelPanel;
	}

	public void setScenceTabelPanel(JPanel scenceTabelPanel) {
		this.scenceTabelPanel = scenceTabelPanel;
	}
	public String getQuota() {
		return quota;
	}
	public void setQuota(String quota) {
		this.quota = quota;
	}

}
