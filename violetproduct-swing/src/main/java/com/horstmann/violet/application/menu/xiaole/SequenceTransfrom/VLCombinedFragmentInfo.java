package com.horstmann.violet.application.menu.xiaole.SequenceTransfrom;

import java.util.ArrayList;
import java.util.List;

public class VLCombinedFragmentInfo {
   private String Id;
   private String LocationX;
   private String LocationY;
   private String type;
   private String height;
   private String width;
   private List<String> conditions;
   private List<VLFragmentPartInfo> fragmentParts;
   
public String getHeight() {
	return height;
}
public void setHeight(String height) {
	this.height = height;
}
public String getWidth() {
	return width;
}
public void setWidth(String width) {
	this.width = width;
}
public String getId() {
	return Id;
}
public void setId(String id) {
	Id = id;
}
public String getLocationX() {
	return LocationX;
}
public void setLocationX(String locationX) {
	LocationX = locationX;
}
public String getLocationY() {
	return LocationY;
}
public void setLocationY(String locationY) {
	LocationY = locationY;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public List<String> getConditions() {
	return conditions;
}
public void setConditions(List<String> conditions) {
	this.conditions = conditions;
}
public List<VLFragmentPartInfo> getFragmentParts() {
	return fragmentParts;
}
public void setFragmentParts(List<VLFragmentPartInfo> fragmentParts) {
	this.fragmentParts = fragmentParts;
}
   
}
