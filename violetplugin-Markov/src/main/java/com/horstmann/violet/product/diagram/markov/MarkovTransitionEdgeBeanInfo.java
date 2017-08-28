package com.horstmann.violet.product.diagram.markov;


import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 * The bean info for the ClassNode type.
 */
public class MarkovTransitionEdgeBeanInfo extends SimpleBeanInfo
{
    /*
     * (non-Javadoc)
     * 
     * @see java.beans.BeanInfo#getPropertyDescriptors()
     */
    public PropertyDescriptor[] getPropertyDescriptors()
    {
        try
        {
        	PropertyDescriptor nameDescriptor = new PropertyDescriptor("name", MarkovTransitionEdge.class);
        	nameDescriptor.setValue("priority", new Integer(1));
            PropertyDescriptor conditionDescriptor = new PropertyDescriptor("conditions", MarkovTransitionEdge.class);
            conditionDescriptor.setValue("priority", new Integer(2));
            PropertyDescriptor ownedDescriptor = new PropertyDescriptor("owned", MarkovTransitionEdge.class);
            ownedDescriptor.setValue("priority", new Integer(3));
            PropertyDescriptor proDescriptor = new PropertyDescriptor("pro", MarkovTransitionEdge.class);
            proDescriptor.setValue("priority", new Integer(4));
            PropertyDescriptor valueDescriptor = new PropertyDescriptor("assignValue", MarkovTransitionEdge.class);
            valueDescriptor.setValue("priority", new Integer(5));
            PropertyDescriptor typeDescriptor = new PropertyDescriptor("assignType", MarkovTransitionEdge.class);
            typeDescriptor.setValue("priority", new Integer(6));
            return new PropertyDescriptor[]
            {
            	nameDescriptor,
            	conditionDescriptor,
            	ownedDescriptor,
            	proDescriptor,
            	valueDescriptor,
            	typeDescriptor,
                
            };
        }
        catch (IntrospectionException exception)
        {
            return null;
        }
    }
}
