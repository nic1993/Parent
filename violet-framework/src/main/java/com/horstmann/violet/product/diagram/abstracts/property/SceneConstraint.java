package com.horstmann.violet.product.diagram.abstracts.property;

import java.util.ArrayList;
import java.util.List;

public class SceneConstraint {
	private List<SequenceConstraint> constraints = new ArrayList<SequenceConstraint>();
	private List<String> sequenceName = new ArrayList<String>();
	
	public List<String> getSequenceName() {
		return sequenceName;
	}
	public void setSequenceName(List<String> sequenceName) {
		this.sequenceName = sequenceName;
	}
	public List<SequenceConstraint> getConstraints() {
		return constraints;
	}
	public void setConstraints(List<SequenceConstraint> constraints) {
		this.constraints = constraints;
	}
	public SceneConstraint clone() {
		SceneConstraint cloned = new SceneConstraint();
		return cloned;
	}
}
