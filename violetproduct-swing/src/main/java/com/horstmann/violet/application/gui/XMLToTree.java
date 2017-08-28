package com.horstmann.violet.application.gui;

import java.awt.GridLayout;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
//xml转换为树
public class XMLToTree {
    static Document doc;
    static Element root;
    static DefaultMutableTreeNode rootTree ;
    static DefaultMutableTreeNode root1;
    static DefaultMutableTreeNode root2;
    static DefaultTreeCellRenderer demoRenderer;
    private static JScrollPane jScrollPane;
    public static JScrollPane  getTree(String path) {
    	JPanel jp=new JPanel();
        try {
        	//获得SAXReader对象
            SAXReader saxReader=new SAXReader();
            //获得domcument的对象
            doc=saxReader.read(path);     
            //获得根节点
            root = doc.getRootElement();//使用dom4j提供的API获得XML的根节点
            //创建Jtree数据模型的根节点
            root1 =new DefaultMutableTreeNode(getAttriString(root));
            getTreenodeNames(root,root1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //对于</TimingDiagramGraph>的处理
        String ss1=getAttriString(root).split(" ")[0];
        String ss2=ss1.replaceAll("<", "</ ")+">";
        root2=new DefaultMutableTreeNode(ss2);
        rootTree =new DefaultMutableTreeNode("message");
        rootTree.add(root1);
        rootTree.add(root2);
        //生成树(包含了一个容器)
        JTree jTree=new JTree(rootTree);
        //将树中默认操作时显示的图片改成我们想要的图片
        demoRenderer = new DefaultTreeCellRenderer();
        demoRenderer.setClosedIcon(new ImageIcon("resources/icons/22x22/collapsed.png"));
        demoRenderer.setOpenIcon(new ImageIcon("resources/icons/22x22/expanded.png"));
        demoRenderer.setLeafIcon(null);
        demoRenderer.setClosedIcon(null);
        demoRenderer.setOpenIcon(null);
        jTree.setCellRenderer(demoRenderer);
        jTree.putClientProperty("JTree.lineStyle","None"); 
        jp.setLayout(new GridLayout());
        jp.add(jTree);
        
        jScrollPane = new JScrollPane(jp);
       return jScrollPane;
    }
    
    
    //获得当前Element 建立当前树node的子节点
    public static void getTreenodeNames(Element e,DefaultMutableTreeNode node) {
        //获得当前element的子元素的迭代器
        Iterator iter = e.elementIterator();
        while (iter.hasNext()) {
        	//获得子元素
            Element childEle = (Element) iter.next();
            //取出子节点的集合
            List<Element> listEle=childEle.elements();
            //如果存在孩子元素
            DefaultMutableTreeNode child=null;
//            if(listEle.size()!=0){
            	
            	//取出子元素的信息，生成当前树node新的子节点
               child = new DefaultMutableTreeNode(getAttriString(childEle));
            	//将生成的节点添加到树模型中
            	node.add(child);
            	
            	//定义一个相对应的</>的节点  对这种节点单独处理
            	DefaultMutableTreeNode child1=null;
            	if(listEle.size()!=0){
            		String s1=getAttriString(childEle).split(" ")[0];
            		String s2=s1.replace("<", "/ ");
            		String s3="<"+s2+" >";
            		child1 = new DefaultMutableTreeNode(s3);
            		node.add(child1);
            	}
            //若果当前的元素没有子元素，跳到下一子元素
            if (childEle.nodeCount()==0) {
                continue;
            }
            //如果当前的元素有子元素，递归子元素
            else{
            	//只递归第一个元素的子元素
                getTreenodeNames(childEle,child);//遍历
            }

        }
    }
    //获得所有的属性---值的结合, 例如 <startPoint class="Point2D.Double" id="8" x="150.0" y="412.0"/>  
    //如果没有属性
    public static String getAttriString(Element element){
    	List<Attribute> list=element.attributes();
    	String s1=null;
    	String s2=null;
    	//当属性为空的时候  <startPointTime>13</startPointTime>
    	if(list.size()==0){
    		String sName="< "+element.getName()+" >"+element.getText()+"</ "+element.getName()+" >";
    		return sName;
    	}
    	//取出当前Element的孩子element
    	List<Element> listEle=element.elements();
    	//如果孩子为空，并且没有文本
    	if(listEle.size()==0 ){
    		String sName1="<"+element.getName();
        	for(Attribute attri:list){
        		s1=attri.getName();
        		s2=attri.getValue();
        		sName1+=" "+s1+"="+s2;
    	}
        	sName1+=" />";
        	return sName1;
    	}
    	
    	
    	String s="<"+element.getName();
    	for(Attribute attri:list){
    		s1=attri.getName();
    		s2=attri.getValue();
    		s+=" "+s1+"="+s2;
    	}
    	s+=">";
    	return s;
    } 

//    public static void main(String[] args) {
//
//        JFrame frame = new JFrame("test");
//        frame.setVisible(true);
//        frame.setBounds(200, 200, 400, 300);
//        JPanel jp=XMLToTree.getTree("C:\\Users\\Admin\\Desktop\\111.timing.violet.xml");
//        frame.add(jp);
//        frame.pack();
//    }

}