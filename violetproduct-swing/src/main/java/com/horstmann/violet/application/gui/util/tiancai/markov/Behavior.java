package com.horstmann.violet.application.gui.util.tiancai.markov;

public class Behavior {
	private String behaviorID;
	private String BehaviorName;
	public Behavior(){}
	
	@Override
	public String toString() {
		return "Behavior[behaviorID=" + behaviorID + ", BehaviorName="
				+ BehaviorName + "]";
	}

	public String getBehaviorID() {
		return behaviorID;
	}

	public void setBehaviorID(String behaviorID) {
		this.behaviorID = behaviorID;
	}

	public String getBehaviorName() {
		return BehaviorName;
	}

	public void setBehaviorName(String behaviorName) {
		BehaviorName = behaviorName;
	}
}
