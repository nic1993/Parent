package com.horstmann.violet.product.diagram.usecase;


import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 * The bean info for the ClassNode type.
 */
public class UseCaseNodeBeanInfo extends SimpleBeanInfo
{
    /*
     * (non-Javadoc)
     * 
     * @see java.beans.BeanInfo#getPropertyDescriptors()
     */
     @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
    	        try
    	        {
    	            PropertyDescriptor nameDescriptor = new PropertyDescriptor("name", UseCaseNode.class);
    	            nameDescriptor.setValue("priority", new Integer(1));
    	            PropertyDescriptor useConstraintDescriptor = new PropertyDescriptor("useConstraint", UseCaseNode.class);
    	            useConstraintDescriptor.setValue("priority", new Integer(2));
    	            PropertyDescriptor sceneConstraintDescriptor = new PropertyDescriptor("sceneConstraint", UseCaseNode.class);
    	            sceneConstraintDescriptor.setValue("priority", new Integer(3));
    	            return new PropertyDescriptor[]
    	            {
    	                    nameDescriptor,
    	                    useConstraintDescriptor,
    	                    sceneConstraintDescriptor,
    	            };
    	        }
    	        catch (IntrospectionException exception)
    	        {
    	            return null;
    	        }
    	    }
    }

