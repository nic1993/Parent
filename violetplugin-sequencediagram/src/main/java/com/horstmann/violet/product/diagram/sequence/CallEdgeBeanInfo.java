/*
 Violet - A program for editing UML diagrams.

 Copyright (C) 2007 Cay S. Horstmann (http://horstmann.com)
 Alexandre de Pellegrin (http://alexdp.free.fr);

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.horstmann.violet.product.diagram.sequence;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 * The bean info for the CallEdge type.
 */
public class CallEdgeBeanInfo extends SimpleBeanInfo
{
    public PropertyDescriptor[] getPropertyDescriptors()
    {
        try
        {
            
            		PropertyDescriptor messageDescriptor =new PropertyDescriptor("message", CallEdge.class);
            		messageDescriptor.setValue("priority", new Integer(1));
            		PropertyDescriptor parametersDescriptor = new PropertyDescriptor("parameters", CallEdge.class);
            		parametersDescriptor.setValue("priority", new Integer(2));
            		PropertyDescriptor argumentsDescriptor = new PropertyDescriptor("arguments", CallEdge.class);
            		argumentsDescriptor.setValue("priority", new Integer(3));
            		PropertyDescriptor returnvalueDescriptor = new PropertyDescriptor("returnvalue", CallEdge.class);
            		returnvalueDescriptor.setValue("priority", new Integer(4));
            		PropertyDescriptor assignDescriptor = new PropertyDescriptor("assign", CallEdge.class);
            		assignDescriptor.setValue("priority", new Integer(5));
            	    PropertyDescriptor aliasDescriptor = new PropertyDescriptor("alias", CallEdge.class);
            	    aliasDescriptor.setValue("priority", new Integer(6));
            		PropertyDescriptor conditionDescriptor = new PropertyDescriptor("condition", CallEdge.class);
            		conditionDescriptor.setValue("priority", new Integer(7));
            		PropertyDescriptor constraintDescriptor =new PropertyDescriptor("constraint", CallEdge.class);
            		constraintDescriptor.setValue("priority", new Integer(8));
            		PropertyDescriptor timeconstraintDescriptor =new PropertyDescriptor("timeconstraint", CallEdge.class);
            		constraintDescriptor.setValue("priority", new Integer(9));
            		return new PropertyDescriptor[]
            	            {
            				messageDescriptor,
            				parametersDescriptor,
            				argumentsDescriptor,
            				returnvalueDescriptor,
            				assignDescriptor,
            				aliasDescriptor,
            				conditionDescriptor,
            				constraintDescriptor,
            				timeconstraintDescriptor,
            				
            	            };
            
        }
        catch (IntrospectionException exception)
        {
            return null;
        }
    }
}
