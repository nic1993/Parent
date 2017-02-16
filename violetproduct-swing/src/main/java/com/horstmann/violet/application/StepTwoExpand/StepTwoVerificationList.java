package com.horstmann.violet.application.StepTwoExpand;

import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.horstmann.violet.application.gui.GBC;

public class StepTwoVerificationList extends JPanel{
	   private List<String> nameList;
	   private ButtonGroup buttonGroup;
	   private List<JRadioButton> jRadioButtons;
	   public StepTwoVerificationList(List<String> nameList)
	   {
		   buttonGroup = new ButtonGroup();
		   jRadioButtons = new ArrayList<JRadioButton>();
		   this.nameList = nameList;
		   initdata(nameList);
		   this.setLayout(new GridBagLayout());
		   if(nameList.size() != 0)
		   {
			   for(int i = 0;i < nameList.size();i++)
			   {
				   this.add(jRadioButtons.get(i), new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0));
			   }
		   }
	   }
       private void initdata(List<String> nameList)
       {
    	   for(int i = 0;i < nameList.size();i++)
    	   {
    		   JRadioButton jRadioButton = new JRadioButton(nameList.get(i));
    		   jRadioButtons.add(jRadioButton);
    		   buttonGroup.add(jRadioButton);
    	   }
       }
}
