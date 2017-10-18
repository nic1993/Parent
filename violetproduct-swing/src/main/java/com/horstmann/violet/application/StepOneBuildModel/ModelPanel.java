package com.horstmann.violet.application.StepOneBuildModel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

import org.apache.commons.collections.functors.IfClosure;

import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.application.menu.FileMenu;
import com.horstmann.violet.workspace.IWorkspace;

public class ModelPanel extends JPanel{
      private SequenceTreePanel sequenceTreePanel;
      private UsecaseTreePanel usecaseTreePanel;
      private JPanel TreePanel;
      private JPanel gappanel;
      private JLabel title;
      
      private String temporaryUcaseFile;
      private String temporarySeqFile;
      private List<IWorkspace> UseCaseworkspaceList; //ÓÃÀýÍ¼    
      private List<IWorkspace> SequencespaceList;//Ë³ÐòÍ¼
      
      private MainFrame mainFrame;
      private FileMenu fileMenu;
      
      private JPopupMenu popupMenu;
      private JMenuItem deleteDiagram;
      private JMenuItem saveDiagram;
      
      private boolean isSave = false;
      
      private int index;
	  public ModelPanel(MainFrame mainFrame,FileMenu fileMenu) {
		this.mainFrame = mainFrame;
		this.fileMenu = fileMenu;
		init();
	  }
      private void init()
      {
    	sequenceTreePanel = new SequenceTreePanel(fileMenu, mainFrame,this);
    	usecaseTreePanel = new UsecaseTreePanel(fileMenu, mainFrame,this);
    	gappanel = new JPanel();
    	TreePanel = new JPanel();
    	Icon icon = new ImageIcon("resources/icons/16x16/smallDown.png");
    	title = new JLabel("",icon,JLabel.LEFT);
    	title.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
    	
    	UseCaseworkspaceList = new ArrayList<IWorkspace>(); //ÓÃÀýÍ¼ 
    	SequencespaceList=new ArrayList<IWorkspace>();//Ë³ÐòÍ¼
    	
    	TreePanel.setBackground(new Color(233, 233,233));
    	TreePanel.setLayout(new GridBagLayout());
    	TreePanel.add(usecaseTreePanel,new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0));
    	TreePanel.add(sequenceTreePanel,new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(5, 0, 0, 0));
    	
    	this.setBackground(new Color(233, 233,233));
    	this.setLayout(new GridBagLayout());
    	this.add(title,new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0));
    	this.add(TreePanel,new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(5, 16, 0, 0));
    	
    	listen();
      }
      
    private void listen()
    {
    	title.addMouseListener(new MouseAdapter() {
    		@Override
    		public void mouseClicked(MouseEvent e) {
    			// TODO Auto-generated method stub
    			if(e.getButton() == e.BUTTON1)
    			{
    				if(TreePanel.isVisible())
        			{
        				TreePanel.setVisible(false);
        			}
        			else {
    					TreePanel.setVisible(true);
    				}
    			}
    			else if (e.getButton() == e.BUTTON3) {
    				popupMenu = new JPopupMenu();
					deleteDiagram = new JMenuItem("É¾³ý     ",new ImageIcon("resources/icons/16x16/new.png"));
					saveDiagram = new JMenuItem("±£´æ    ",new ImageIcon("resources/icons/16x16/save.png"));
					deleteDiagram.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_MASK));
					saveDiagram.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_MASK));
					popupMenu.add(deleteDiagram);
					popupMenu.add(saveDiagram);
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
					
					deleteDiagram.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							ModelPanel selectmodel = null;
							for (ModelPanel modelPanel : mainFrame.getModelPanels()) {
								if(modelPanel.getTitle().getText().equals(title.getText()))
								{
									selectmodel = modelPanel;
								}
							}
							if(selectmodel != null)
							{
								if(!selectmodel.isSave())
								{
									int n = JOptionPane.showConfirmDialog(null, "¸ÃÄ£ÐÍ°ü»¹Î´±£´æ,ÊÇ·ñÉ¾³ý?", "±êÌâ",JOptionPane.YES_NO_OPTION,0,new ImageIcon("icons/64x64/save.png"));
								    if(n == 0)
								    {
								    	mainFrame.removeModelPanel(selectmodel);
								    	mainFrame.getModelPanelMap().remove(selectmodel);
								    }
								}
								else {
									mainFrame.removeModelPanel(selectmodel);
								}								
							}							
						}
					});
					
					saveDiagram.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							ModelPanel selectmodel = null;
							for (ModelPanel modelPanel : mainFrame.getModelPanels()) {
								if(modelPanel.getTitle().getText().equals(title.getText()))
								{
									selectmodel = modelPanel;
								}
							}
							if(selectmodel != null)
							{
								mainFrame.saveModelPanel(selectmodel);
								
							}
						}
					});
				}
    			
    			
    			mainFrame.renewPanel();
    		}
		});
    }
	public SequenceTreePanel getSequenceTreePanel() {
		return sequenceTreePanel;
	}
	public UsecaseTreePanel getUsecaseTreePanel() {
		return usecaseTreePanel;
	}
	public JLabel getTitle() {
		return title;
	}
	public List<IWorkspace> getUseCaseworkspaceList() {
		return UseCaseworkspaceList;
	}
	public List<IWorkspace> getSequencespaceList() {
		return SequencespaceList;
	}
	public boolean isSave() {
		return isSave;
	}
	public void setSave(boolean isSave) {
		this.isSave = isSave;
	}
	public String getTemporaryUcaseFile() {
		return temporaryUcaseFile;
	}
	public void setTemporaryUcaseFile(String temporaryUcaseFile) {
		this.temporaryUcaseFile = temporaryUcaseFile;
	}
	public String getTemporarySeqFile() {
		return temporarySeqFile;
	}
	public void setTemporarySeqFile(String temporarySeqFile) {
		this.temporarySeqFile = temporarySeqFile;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}      
     
}
