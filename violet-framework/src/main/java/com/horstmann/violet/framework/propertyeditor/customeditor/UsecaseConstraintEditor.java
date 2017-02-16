package com.horstmann.violet.framework.propertyeditor.customeditor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyEditorSupport;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;

import com.horstmann.violet.product.diagram.abstracts.property.UseConstraint;
import com.horstmann.violet.product.diagram.abstracts.property.Usecaseconstraint;

public class UsecaseConstraintEditor extends PropertyEditorSupport{
	public boolean supportsCustomEditor()
    {
        return true;
    } 

    public Component getCustomEditor()
    {     
        final JPanel panel = new JPanel();
        panel.add(getOtherComponent());
        return panel;
    }

	private Component getOtherComponent() {
		// TODO Auto-generated method stub
		useConstraint = (UseConstraint)getValue();
		constrainttable = new UsecaseJtabel(constraintTableModel);
		JPanel panel = new JPanel();
        constrainttable.getTableHeader().setVisible(true);
        constrainttable.getTableHeader().setReorderingAllowed(false);
        constrainttable.getTableHeader().setResizingAllowed(false);        
        constrainttable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        initconstraint();
        constraintAddButton.setText("�½�");
        constraintAddButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent evt)
        	{ 
        		constrainttable.addcolumn();        
        		// firePropertyChange();
        	}
        });
        constraintSaveButton.setText("����");
        constraintSaveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
                saveConstraint();
			}
		});
        constraintDeleteButton.setText("ɾ��");
        constraintDeleteButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent evt)
        	{
        		if(constrainttable.getRowCount() != 0)
        		constrainttable.deletecolumn();
        	}
        });
        jScrollPane.setBorder(BorderFactory.createEtchedBorder());
        jScrollPane.setViewportView(constrainttable);    
        jScrollPane.setPreferredSize(new Dimension(200, 200));
        GroupLayout panelLayout=new GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
        	panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
        	.addGroup(panelLayout.createSequentialGroup()       	    	    
        	       .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
        		       .addGroup(panelLayout.createSequentialGroup()
        		           .addContainerGap()
        		           .addComponent(constraintAddButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
        		           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        		           .addComponent(constraintDeleteButton, GroupLayout.PREFERRED_SIZE, 60,GroupLayout.PREFERRED_SIZE)
        		           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        		           .addComponent(constraintSaveButton,GroupLayout.PREFERRED_SIZE,60,GroupLayout.PREFERRED_SIZE))
        		           .addComponent(jScrollPane, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)))
        		                		                 		          
        		);
       panelLayout.setVerticalGroup(
        	      panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
        	      .addGroup(panelLayout.createSequentialGroup()	  
        	        .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
        	          .addGroup(panelLayout.createSequentialGroup()          
        	          .addComponent(jScrollPane,GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)))       	         
        	          .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        	          .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
        	          .addComponent(constraintDeleteButton)
        	          .addComponent(constraintAddButton)
        	          .addComponent(constraintSaveButton))
        	        .addContainerGap())
        	    );
        return panel;
	    }
	    public void initconstraint()
	    {
	    	int count = useConstraint.getConstraints().size();
	    	List<Usecaseconstraint> constraints = useConstraint.getConstraints();
	    	for(int i = 0; i < count; i++)
	    	{
	    		constrainttable.addcolumn();
	    		constrainttable.getModel().setValueAt(constraints.get(i).getName(), i, 0);
	    		constrainttable.getModel().setValueAt(constraints.get(i).getContent(), i,  1);
	    		constrainttable.getModel().setValueAt(constraints.get(i).getType(), i,  2);
	    	}
//	    	int count = allConstraint.getNameList().size();
//	    	for(int i = 0; i < count; i++)
//	    	{
//	    		constrainttable.addcolumn();
//	    		constrainttable.getModel().setValueAt(allConstraint.getNameList().get(i), i, 0);
//	    		constrainttable.getModel().setValueAt(allConstraint.getContentList().get(i), i,  1);
//	    		constrainttable.getModel().setValueAt(allConstraint.getTypeList().get(i), i,  2);
//	    	}
	    }
	    public void saveConstraint()
	    {
	    	int count = constrainttable.getRowCount();
	    	useConstraint.getConstraints().clear();
	    	if(count != 0)
	    	{
	    		for(int i = 0;i < count ;i++)
	    		{
	    			Usecaseconstraint constraint = new Usecaseconstraint();
	    			constraint.setName((String) constrainttable.getModel().getValueAt(i, 0));
	    			constraint.setContent((String) constrainttable.getModel().getValueAt(i, 1));
	    			constraint.setType((String) constrainttable.getModel().getValueAt(i, 2));
	    			useConstraint.getConstraints().add(constraint);
	    		}
	    	}
//	    	allConstraint.getNameList().clear();
//	    	allConstraint.getContentList().clear();
//	    	allConstraint.getTypeList().clear();
//	    	for(int i = 0;i < count ;i++)
//	    	{
//	    		allConstraint.getNameList().add((String) constrainttable.getModel().getValueAt(i, 0));
//	    		allConstraint.getContentList().add((String) constrainttable.getModel().getValueAt(i, 1));
//	    		allConstraint.getTypeList().add((String) constrainttable.getModel().getValueAt(i, 2));
//	    	}
	    }
	    private UseConstraint useConstraint;
	    private UsecaseJtabel constrainttable;
	    private JButton constraintAddButton=new JButton();
	    private JButton constraintDeleteButton=new JButton();
	    private JButton constraintSaveButton=new JButton();
	    private JScrollPane jScrollPane=new JScrollPane(); 
	    private UsecaseTableMode constraintTableModel = new UsecaseTableMode(); 

}