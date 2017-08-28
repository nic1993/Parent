package com.horstmann.violet.application.menu.xiaole.SequenceTransfrom;

import java.util.ArrayList;
import java.util.List;

public class EADiagram {
   private String name;
   private String type;
   private String ID;  //packageID
   private String DiagramID;
   private List<String> elementid = new ArrayList<String>();
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}

public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}

public String getID() {
	return ID;
}
public void setID(String iD) {
	ID = iD;
}
public List<String> getElementid() {
	return elementid;
}
public void setElementid(List<String> elementid) {
	this.elementid = elementid;
}
public String getDiagramID() {
	return DiagramID;
}
public void setDiagramID(String diagramID) {
	DiagramID = diagramID;
}

}
