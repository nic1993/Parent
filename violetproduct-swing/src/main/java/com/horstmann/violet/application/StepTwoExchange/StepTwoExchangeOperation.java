package com.horstmann.violet.application.StepTwoExchange;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.dom4j.DocumentException;

import com.horstmann.violet.application.StepTwoModelExpand.ProgressUI;
import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.application.gui.util.tanchao.MarkovXML2GraphFile;
import com.horstmann.violet.application.gui.util.tanchao.TianWriteToVioletMarkov;
import com.horstmann.violet.application.menu.FileMenu;
import com.horstmann.violet.framework.file.GraphFile;
import com.horstmann.violet.workspace.IWorkspace;
import com.horstmann.violet.workspace.Workspace;

public class StepTwoExchangeOperation extends JPanel{
       private JLabel Exchangelabel;
       private JProgressBar ExchangeProgressBar;
       private JButton startExchange;
       private JButton restartExchange;
       private JPanel gapPanel;
       private MainFrame mainFrame;
       private FileMenu fileMenu;
       public StepTwoExchangeOperation(MainFrame mainFrame,FileMenu fileMenu)
       {
            this.mainFrame = mainFrame;
            this.fileMenu = fileMenu;
            init();
            this.setLayout(new GridBagLayout());
            this.add(Exchangelabel, new GBC(0, 0,  3, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 44, 0, 44));
            this.add(ExchangeProgressBar,new GBC(0, 1, 3, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 44, 0, 44));
            this.add(startExchange,new GBC(0, 2, 1, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 44, 10, 420));
            this.add(gapPanel, new GBC(1, 2, 1, 1).setFill(GBC.BOTH).setWeight(1, 0));
            this.add(restartExchange, new GBC(2, 2, 1, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 420, 10, 44));
       }
       public void init()
       {
    	   Exchangelabel = new JLabel("请在模型列表中选取需要转换的模型!");
    	   Exchangelabel.setFont(new Font("宋体",Font.PLAIN,16));
    	   
    	   ExchangeProgressBar = new JProgressBar();
    	   ExchangeProgressBar.setUI(new ProgressUI(ExchangeProgressBar, Color.green));
    	   ExchangeProgressBar.setPreferredSize(new Dimension(600, 30));
    	   
    	   startExchange = new JButton("开始转换");
//    	   startVerification.setFont(new Font("宋体",Font.PLAIN,10));
    	   restartExchange = new JButton("重新转换");
//    	   restartVerification.setFont(new Font("宋体",Font.PLAIN,10));
    	   gapPanel = new JPanel();
    	   buttonListen();
       }
       private void buttonListen()
       {
    	   startExchange.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				TianWriteToVioletMarkov tian=new TianWriteToVioletMarkov();
				try {
					tian.find("C:\\Users\\ccc\\Desktop\\markov\\Sofeware_MarkovChainModel.xml");
					tian.writeVioletMarkov("C:\\Users\\ccc\\Desktop\\markov\\Seq_MarkovChainModel2_(601).markov.violet.xml");
				    GraphFile fileGraphFile=MarkovXML2GraphFile.toGraphFile("Seq_MarkovChainModel2_(601).markov.violet.xml");
				    IWorkspace workspace=new Workspace(fileGraphFile);
				    mainFrame.addTabbedPane(workspace);
					mainFrame.repaint();
				} catch (DocumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
       }
       
}
