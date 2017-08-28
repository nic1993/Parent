package com.horstmann.violet.application.gui.util.tanchao;

public class TanchaoMarkovTransitionEdge {
	private String id;
	private String name;
	private String content;
	private String start_reference;
	private String end_reference;
	private String startLocation_id;
	private String startLocation_x;
	private String startLocation_y;
	private String endLocation_id;
	private String endLocation_x;
	private String endLocation_y;
	private String underLocation_id;
	private String conditions;
	private String owned;
	private String assignValue;
	private String assignType;
	private String prob;

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public String getOwned() {
		return owned;
	}

	public void setOwned(String owned) {
		this.owned = owned;
	}

	public String getAssignValue() {
		return assignValue;
	}

	public void setAssignValue(String assignValue) {
		this.assignValue = assignValue;
	}

	public String getAssignType() {
		return assignType;
	}

	public void setAssignType(String assignType) {
		this.assignType = assignType;
	}

	public String getProb() {
		return prob;
	}

	public void setProb(String prob) {
		this.prob = prob;
	}

	public String getUnderLocation_id() {
		return underLocation_id;
	}

	public void setUnderLocation_id(String underLocation_id) {
		this.underLocation_id = underLocation_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStart_reference() {
		return start_reference;
	}

	public void setStart_reference(String start_reference) {
		this.start_reference = start_reference;
	}

	public String getEnd_reference() {
		return end_reference;
	}

	public void setEnd_reference(String end_reference) {
		this.end_reference = end_reference;
	}

	public String getStartLocation_id() {
		return startLocation_id;
	}

	public void setStartLocation_id(String startLocation_id) {
		this.startLocation_id = startLocation_id;
	}

	public String getStartLocation_x() {
		return startLocation_x;
	}

	public void setStartLocation_x(String startLocation_x) {
		this.startLocation_x = startLocation_x;
	}

	public String getStartLocation_y() {
		return startLocation_y;
	}

	public void setStartLocation_y(String startLocation_y) {
		this.startLocation_y = startLocation_y;
	}

	public String getEndLocation_id() {
		return endLocation_id;
	}

	public void setEndLocation_id(String endLocation_id) {
		this.endLocation_id = endLocation_id;
	}

	public String getEndLocation_x() {
		return endLocation_x;
	}

	public void setEndLocation_x(String endLocation_x) {
		this.endLocation_x = endLocation_x;
	}

	public String getEndLocation_y() {
		return endLocation_y;
	}

	public void setEndLocation_y(String endLocation_y) {
		this.endLocation_y = endLocation_y;
	}

	public String getLabelText() {
		return labelText;
	}

	public void setLabelText(String labelText) {
		this.labelText = labelText;
	}

	private String labelText;

}
