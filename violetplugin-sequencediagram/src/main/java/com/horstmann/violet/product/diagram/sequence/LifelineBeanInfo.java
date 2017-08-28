package com.horstmann.violet.product.diagram.sequence;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class LifelineBeanInfo extends SimpleBeanInfo {
	public PropertyDescriptor[] getPropertyDescriptors() {
		try {
			PropertyDescriptor nameDescriptor = new PropertyDescriptor("name", LifelineNode.class);
			nameDescriptor.setValue("priority", new Integer(1));
			PropertyDescriptor widthDescriptor = new PropertyDescriptor("width", LifelineNode.class);
			return new PropertyDescriptor[] { 
					nameDescriptor,
			};
		} catch (IntrospectionException exception) {
			return null;
		}
	}
}
