package com.horstmann.violet.application.StepTwoEvaluate;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import com.horstmann.violet.application.StepTwoModelExpand.ScenceTabelPanel;
import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;

public class RoutePanel extends JPanel{
	private JLabel route;
	private JPanel titlePanel;
	private JPanel gapPanel;
	private MainFrame mainFrame;
	public RoutePanel(MainFrame mainFram)
	{
		this.mainFrame = mainFram;
		init();
		this.setLayout(new GridBagLayout());
		this.add(titlePanel,new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0).setInsets(2));
//		this.add(gapPanel,new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 0));
		this.setBackground(Color.white);
	}
    private void init(){
    	route = new JLabel();
    	route.setFont(new Font("宋体", Font.PLAIN, 16));
    	
    	titlePanel = new JPanel(){
//    		public void paint(Graphics g) {
//	            super.paint(g);
//	            java.awt.Rectangle rect = this.getBounds();
//	            int width = (int) rect.getWidth() - 1;
//	            int height = (int) rect.getHeight() - 1;
//	            Graphics2D g2 = (Graphics2D)g;
//	            g2.setStroke(new BasicStroke(3f));
//	            g2.setColor(new Color(188,188,188));
//	            Stroke dash = new BasicStroke(2.5f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_ROUND,
//	            		3.5f,new float[]{15,10,},0f);
//	            		g2.setStroke(dash);
//	            g2.drawLine(0, height, width, height);
//	          }
    	};
    	titlePanel.setBackground(Color.white);

    	titlePanel.setLayout(new GridBagLayout());
		titlePanel.add(route,new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0));
//		titlePanel.add(panel,new GBC(1, 0).setFill(GBC.BOTH).setWeight(1, 0));

		listen();
		
    }
    public void listen()
    {
    	titlePanel.addMouseListener(new MouseAdapter() {
    		@Override
    		public void mousePressed(MouseEvent e) {
    			// TODO Auto-generated method stub
    			for(RoutePanel routePanel : mainFrame.getStepTwoEvaluateOperation().getRoutePanels())
    			{
    				routePanel.getRoute().setForeground(Color.BLACK);
    			}
    			getRoute().setForeground(Color.red);
    			String route = getRoute().getText();
    			String[] routes = route.split("-->");
    			ScenceTabelPanel reachableTabel = mainFrame.getStepTwoEvaluateOperation().getReachableTabel();
    			JTable table = reachableTabel.getTable();
    			int column = table.getColumnCount();
				int row = table.getRowCount();
    			
    			//初始化
    			for(int k = 0;k < row;k++)
				{
					for(int j = 1;j < column;j++)
    				{
						if(table.getValueAt(k, j).equals("true1"))
						{
							table.setValueAt("1", k, j);
						}
    				}
				}
    			table.repaint();
    			//获得路径并标红
    			for(int i = 1;i < routes.length;i++)
    			{
    				
    				String start = routes[i-1].trim();
    				String end = routes[i].trim();
    				
    				int rowValue = 0;
    				int columnValue = 0;
    				
    				for(int j = 0;j < row;j++)
    				{
    					if(table.getValueAt(j, 0).toString().equals(start))
    					{
    						rowValue = j;
    					}	
    				}
    				for(int j = 0;j < column;j++)
    				{
    					if(reachableTabel.getScenceTabelPanel().getColumnName(j).toString().trim().equals(end))
    					{
    						columnValue = j;
    					}	
    				}

    				table.setValueAt("true1", rowValue, columnValue);
    				table.repaint();
    				int max = mainFrame.getStepTwoEvaluateTabbedPane().getAccessibilityScroll().getVerticalScrollBar().getMaximum();
    				mainFrame.getStepTwoEvaluateTabbedPane().getAccessibilityScroll().getVerticalScrollBar().setValue(max);
    				mainFrame.renewPanel();
//    				mainFrame.getStepTwoEvaluateOperation().getReachableTabel().setSelectrow(rowValue);
//    				mainFrame.getStepTwoEvaluateOperation().getReachableTabel().setSelectcolumn(columnValue);
    				
//    				table.getCellEditor(rowValue, columnValue).getTableCellEditorComponent(table, 1, true, rowValue, columnValue).setForeground(Color.red);
//    				table.getCellEditor(rowValue, columnValue).getTableCellEditorComponent(table, 1, true, rowValue, columnValue).setFont(new Font("宋体", Font.PLAIN, 15));
    			}
    		}
		});
    }
	public JLabel getRoute() {
		return route;
	}
	public void setRoute(JLabel route) {
		this.route = route;
	}
	public JPanel getTitlePanel() {
		return titlePanel;
	}
    
}
