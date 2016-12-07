package com.horstmann.violet.application.menu.xiaole.SequenceTransfrom;
import java.awt.color.CMMException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.horstmann.violet.application.menu.xiaole.SequenceTransfrom.PackageInfo;
public class ReadVioletSequence {
   SAXReader reader = new SAXReader();
   private final String Diagram_id="EAID_D7EF9D40_325E_4007_B081_7B1019A7070F";
   PackageInfo packageInfo =new PackageInfo();
   List<LifeLineInfo> Lifelines=new ArrayList<LifeLineInfo>();//生命线
   List<MessageInfo> Messages=new ArrayList<MessageInfo>();//消息
   List<BaseFragmentInfo> Fragments =new ArrayList<BaseFragmentInfo>();//底层片段
   List<CombinedFragmentInfo> CombinedFragments=new ArrayList<CombinedFragmentInfo>();//组合片段
   List<FragmentPartInfo> FragmentParts=new ArrayList<FragmentPartInfo>();
   //获取到所有嵌套的组合片段ID，用来找出最高级父复合片段
   List<String> NestingCombinedFragmentsID=new ArrayList<String>();
   List<Double> MessageLocationY=new ArrayList<Double>();
   private int c=0;
   private int r=0;
   private int C=0;
   List<String> Activationid =new ArrayList<String>();
   List<String> calledgestartId =new ArrayList<String>();
   List<String> calledgeendId =new ArrayList<String>();
   List<String> returnedgestartId =new ArrayList<String>();
   List<String> returnedgeendId =new ArrayList<String>();
   List<String> Lifelineid = new ArrayList<String>();//lifeline id
   HashMap<String,String> FragmentLifelineid=new HashMap<String, String>();
   HashMap<String,String> CalledgeStartLifelineid=new HashMap<String, String>();
   HashMap<String,String> CalledgeEndLifelineid=new HashMap<String, String>();
   HashMap<String,String> ReturnedgeEndLifelineid=new HashMap<String, String>();
   //建立4个Hashmap用以映射
   HashMap<String,String> ReturnedgeStartLifelineid=new HashMap<String, String>(); 
   List<String> Text=new ArrayList<String>();//Text里面放置每个lifeline的name
   //List<List<String>> ActivationidTemp =new ArrayList<List<String>>();
   HashMap<String,String > LifelineConcludeActivation=new HashMap<String,String>();
   //建立hashMap.判断消息是在哪2条生命线发送和接受
   public void find() throws DocumentException{
	   Document dom=reader.read("ExampleVioletSD.seq.violet.xml");//读取Violet XML文件
	   Element root = dom.getRootElement();
	   Element nodes=root.element("nodes");
	   Element Eages=root.element("edges");
	   List<Element> lifelines = nodes.elements("LifelineNode");
	   List<Element> Calledges=Eages.elements("CallEdge");//发送消息
  	   List<Element> Returnedges=Eages.elements("ReturnEdge");//返回消息
  	   List<Element> combinedfragments=nodes.elements("CombinedFragment");
   	   int Calledgesize=Calledges.size();
  	   int Returnedgesize=Returnedges.size();
 	   for(Element combinedfragment : combinedfragments)
 	   {
 		   CombinedFragments.add(new CombinedFragmentInfo());//创建CombinedFragment对象
 		   
 	   }
 	   for(Element lifeline : lifelines)
 	   {
 		   Lifelines.add(new LifeLineInfo());//创建lifelineInfo对象
 		  
 	   }	
 	   for(int temp1=0;temp1<Calledgesize+Returnedgesize;temp1++)
 	   {
 		   Messages.add(new MessageInfo());//创建MsgFragment对象
 		   Fragments.add(new BaseFragmentInfo());
		   Fragments.add(new BaseFragmentInfo());
 	   }
 	   
 	 for(Element lifelineElement : lifelines)//处理生命线信息
	   {
 		 int lifelineIndex=lifelines.indexOf(lifelineElement);//lifeline下标
 		 Lifelines.get(lifelineIndex).setlifeLineId(transVioletIdToEAID(lifelineElement.attributeValue("id")));//设置ID
 		 Lifelines.get(lifelineIndex).setlifeLineName(lifelineElement.element("name").element("text").getText());//设置Name
 	
 	     String geometryString="Left="+lifelineElement.element("location").attributeValue("x")+";"
 	    		 +"Top="+"50"+";"//这里根据EA特性 默认Top为50
 	    		 +"Right="+String.valueOf(Double.parseDouble(lifelineElement.element("location").attributeValue("x"))
 	    				 +Double.parseDouble(lifelineElement.element("width").getText()))+";"
 	    		 +"Bottom="+"600";//这里先默认设置为600;
 	     Lifelines.get(lifelineIndex).setGeometry(geometryString);
 	     Lifelines.get(lifelineIndex).setType("uml:Lifeline");
 	         
 	  
 	   
 	 
 	   
 	  

 	   
 	  
	   Element lifelineschildren=lifelineElement.element("children"); 
	   List<Element> activations=lifelineschildren.elements("ActivationBarNode");//自发送消息
	   int SIZE =activations.size();
	   Lifelineid.add(Lifelines.get(lifelineIndex).getlifeLineId());//将所有的lifelineID放在Lifelineid结构中
	   Text.add(lifelineElement.element("name").element("text").getText());
	   //获取每个生命线中的子节点，即activationBarNode
       for(int m=0;m<SIZE;m++)//每个生命线上的activations
       {//遍历每个子节点中的ActivationBarNode,以获取每个生命线中所有的ActivationBarNode的id
//       	 Element element=activations.get(m).element("children");     	
//       	if(	element)
//       		 flag = 1;  	 
//       	 if(flag==1)
//            {
//         id.add(element.element("ActivationBarNode").attribute("id").getValue());
//         //如果有自发送消息的ActivationBarNode.获取其ID
//            }
//      	 else
       	 Activationid.add(transVioletIdToEAID(activations.get(m).attribute("id").getValue()));
       	 LifelineConcludeActivation.put(Activationid.get(m),Lifelineid.get(lifelineIndex));//将activationbarId和lifelineID相对应       	 	         	
       }         
       Activationid= new ArrayList<String>();
	   }
 	 
 	 
 		  
 		  
 	  
 	  for(Element calledgeElement :Calledges)//处理发送消息的边的信息
 	   {  
 		 
 		
 		  
 		 int calledgeElementIndex=Calledges.indexOf(calledgeElement);
 		 Messages.get(calledgeElementIndex).setID(calledgeElement.elementText("ID"));//SpecialID
 		 Messages.get(calledgeElementIndex).setIsNavigable(false);//给发送消息的边设置特征标识	
 		 Messages.get(calledgeElementIndex).setConnectorId(transVioletIdToEAID(calledgeElement.attributeValue("id")));//设置ID
 		 Messages.get(calledgeElementIndex).setName(calledgeElement.elementText("middleLabel"));//设置Name
 		 Messages.get(calledgeElementIndex).setMessageSort("synchCall");//
 		 Messages.get(calledgeElementIndex).setDiagram_id(Diagram_id);
 		 Messages.get(calledgeElementIndex).setEa_type("Sequence");
 		 Messages.get(calledgeElementIndex).setType("Sequence"); 
 		 String Sequence_points="PtStartX="+calledgeElement.element("startLocation").attributeValue("x")+";"
 				 +"PtStartY=-"+calledgeElement.element("startLocation").attributeValue("y")+";"
 				 +"PtEndX="+calledgeElement.element("endLocation").attributeValue("x")+";"
 				 +"PtEndY=-"+calledgeElement.element("endLocation").attributeValue("y")+";";
 		 Messages.get(calledgeElementIndex).setLocationY(calledgeElement.element("endLocation").attributeValue("y"));
 		 MessageLocationY.add(Double.parseDouble(calledgeElement.element("endLocation").attributeValue("y")));
 		 Messages.get(calledgeElementIndex).setSequence_points(Sequence_points);   
 		 Messages.get(calledgeElementIndex).setPrivatedata5("");//这里还没搞清楚
	   	 calledgestartId.add(transVioletIdToEAID(calledgeElement.element("start").attribute("reference").getValue()));
	   	 //获取ActionBarNodeId
 	   	 calledgeendId.add(transVioletIdToEAID(calledgeElement.element("end").attribute("reference").getValue()));
 	   	 //获取所有发送消息的边的start ID（相对于ActivationBarNode) 
 	   	 String StartID= LifelineConcludeActivation.get(calledgestartId.get(calledgeElementIndex));
 	   	//对应到lifeline的ID
 	   	 String EndID= LifelineConcludeActivation.get(calledgeendId.get(calledgeElementIndex));
 	   	
 	   	 Fragments.get(C).setId("EAID_"+UUID.randomUUID().toString().replace("-", "_")); 
 	   	 
 	   	 Fragments.get(C).setCovered(StartID);
 	   	 Fragments.get(C+1).setId("EAID_"+UUID.randomUUID().toString().replace("-", "_"));
 	   	 Fragments.get(C+1).setCovered(EndID);
 	   	 CalledgeStartLifelineid.put(transVioletIdToEAID(Calledges.get(c).attribute("id").getValue()),StartID);
 	   	 CalledgeEndLifelineid.put(transVioletIdToEAID(Calledges.get(c).attribute("id").getValue()), EndID);
 	   	 //MsgFragments.get(c).PtStartX=Calledges.get(c).element("startLocation").attribute("x").getValue();
         Messages.get(calledgeElementIndex).setSendEvent(Fragments.get(C).getId()); 
         Messages.get(calledgeElementIndex).setReceiveEvent(Fragments.get(C+1).getId());
         Messages.get(calledgeElementIndex).setSourceId(StartID);
         Messages.get(calledgeElementIndex).setTragetId(EndID);
 		 c++;
 		// System.out.println(Fragments.get(C).getId());
 		
   		C+=2;
 	   }
 	 
 	   while(r<Returnedgesize)
 	   {
 	   int temp3=c+r; 
      Messages.get(temp3).isNavigable=true;
      Messages.get(temp3).setID(Returnedges.get(r).elementText("ID"));
 	  Messages.get(temp3).connectorId=transVioletIdToEAID(Returnedges.get(r).attribute("id").getValue());
 	  Messages.get(temp3).name=Returnedges.get(r).element("middleLabel").getText();
 	  Messages.get(temp3).messageSort="reply";
 	  Messages.get(temp3).ea_type="Sequence";
 	  Messages.get(temp3).diagram_id="EAID_D7EF9D40_325E_4007_B081_7B1019A7070F";
 	  Messages.get(temp3).type="Sequence";
 	  String Sequence_points="PtStartX="+Returnedges.get(r).element("startLocation").attributeValue("x")+";"
				 +"PtStartY=-"+Returnedges.get(r).element("startLocation").attributeValue("y")+";"
				 +"PtEndX="+Returnedges.get(r).element("endLocation").attributeValue("x")+";"
				 +"PtEndY=-"+Returnedges.get(r).element("endLocation").attributeValue("y")+";";
 	   Messages.get(temp3).setLocationY(Returnedges.get(r).element("endLocation").attributeValue("y"));
	   MessageLocationY.add(Double.parseDouble(Returnedges.get(r).element("endLocation").attributeValue("y")));
 	   Messages.get(temp3).sequence_points=Sequence_points;
 	   Messages.get(temp3).privatedata5="";//暂时不处理
       returnedgestartId.add(transVioletIdToEAID(Returnedges.get(r).element("start").attribute("reference").getValue()));
       returnedgeendId.add(transVioletIdToEAID(Returnedges.get(r).element("end").attribute("reference").getValue()));
  	   	 //获取所有发送消息的边的start ID（相对于ActivationBarNode) 
  	   	 String id1= LifelineConcludeActivation.get(returnedgestartId.get(r));
  	   	 String id2= LifelineConcludeActivation.get(returnedgeendId.get(r));
  		 Fragments.get(C).setId("EAID_"+UUID.randomUUID().toString().replace("-", "_"));
 	   	 Fragments.get(C).setCovered(id1);
 	   	 Fragments.get(C+1).setId("EAID_"+UUID.randomUUID().toString().replace("-", "_"));
 	   	 Fragments.get(C+1).setCovered(id2);
  	   	 ReturnedgeStartLifelineid.put(transVioletIdToEAID(Returnedges.get(r).attribute("id").getValue()),id1);
  	   	 ReturnedgeEndLifelineid.put(transVioletIdToEAID(Returnedges.get(r).attribute("id").getValue()), id2);
  	     Messages.get(temp3).setSendEvent(Fragments.get(C).getId()); 
  	     Messages.get(temp3).setReceiveEvent(Fragments.get(C+1).getId());
  	     Messages.get(temp3).setSourceId(id1);
  	     Messages.get(temp3).setTragetId(id2);
  	    
  		 r++; 
  		 //System.out.println(Fragments.get(C).getId());
  		 C+=2;
 	   } 
 for(Element combinedfragmentElement : combinedfragments)
 		  
 	  {
 		  FragmentParts=new ArrayList<FragmentPartInfo>();
 		  int index=combinedfragments.indexOf(combinedfragmentElement);
 		  CombinedFragments.get(index).setId(transVioletIdToEAID(combinedfragmentElement.attributeValue("id")));		  
 		  CombinedFragments.get(index).setName("Xiao");
 		  CombinedFragments.get(index).setType("alt");//这里先全部设置为alt
 		  CombinedFragments.get(index).setID(combinedfragmentElement.elementText("ID"));
  	      String geometryString="Left="+combinedfragmentElement.element("location").attributeValue("x")+";"
  	    		 +"Top="+String.valueOf(Double.parseDouble(combinedfragmentElement.element("location").attributeValue("y"))+50)+";"//
  	    		 +"Right="+String.valueOf(Double.parseDouble(combinedfragmentElement.element("location").attributeValue("x"))
  	    				 +Double.parseDouble(combinedfragmentElement.element("width").getText()))+";"
  	    		 +"Bottom="+String.valueOf(Double.parseDouble(combinedfragmentElement.element("location").attributeValue("y"))
  	    				 +Double.parseDouble(combinedfragmentElement.element("height").getText())+50);//
 		  CombinedFragments.get(index).setGeometry(geometryString);
 		  List<Element> fragmentParts=combinedfragmentElement.element("fragmentParts").
 				  elements("com.horstmann.violet.product.diagram.abstracts.property.FragmentPart");
 		 
 		  for(Element fragmentpart : fragmentParts)
 		  {
 			  FragmentParts.add(new FragmentPartInfo());
 		  }
 
 		  for(Element fragmentpart : fragmentParts)
 		  {
 			 
 		  int Index=fragmentParts.indexOf(fragmentpart); 
 		  FragmentParts.get(Index).setConditionText(fragmentpart.elementText("conditionText"));
 		  Element coveredMessages=fragmentpart.element("coveredMessagesID");
 		  Element nestingChilds=fragmentpart.element("nestingChildNodesID");
 		  List<Element> EdgesID=coveredMessages.elements("string");		 
 		  List<Element> nestingChildNodesID=nestingChilds.elements("string");		 
 		  Element size=fragmentpart.element("size");
 		  FragmentParts.get(Index).setSize(size.getText());
 	
 		  for(Element EdgeID : EdgesID )
 		  {
 			  for(MessageInfo message : Messages)
 			  { 
 			  if(EdgeID.getText().equals(message.getID()))
 				  
 			   {
 			
 		     FragmentParts.get(Index).AddConcluedmessages(message);
 		     
 		       }
 			  }
 		  }
 		  for(Element nestingChildNodeID : nestingChildNodesID)
 		  {
 			 
 			  for(CombinedFragmentInfo combinedfragment : CombinedFragments)
 			  {
 				  if(nestingChildNodeID.getText().equals(combinedfragment.getID()))
 				  {
 					  FragmentParts.get(Index).AddNestingchilds(combinedfragment);
 				     NestingCombinedFragmentsID.add(combinedfragment.getID());
 				  }
 			  }
 		  }
 		}  
 		CombinedFragments.get(index).setFragmentParts(FragmentParts);  		  	     
 	  }
 		  
  } 
   public void writeTiming(String filename){
		  
		  Document doc = DocumentHelper.createDocument();
		    Element XMI = doc.addElement("xmi:XMI");
		    XMI.addAttribute("xmi:version", "2.1");
		    XMI.addNamespace("uml", "http://schema.omg.org/spec/UML/2.1");
		    XMI.addNamespace("xmi", "http://schema.omg.org/spec/XMI/2.1");
		    Element Documentation=XMI.addElement("xmi:Documentation");
		    Documentation.addAttribute("exporter", "Enterprise Architect");
		    Documentation.addAttribute("exporterVersion", "6.5");
		   //头
		    	    
		    Element Model=XMI.addElement("uml:Model");
		    Model.addAttribute("xmi:type","uml:Model");
		    Model.addAttribute("name","EA_Model");
		   //Model 标签 
		    
		    Element packagedElement=Model.addElement("packagedElement");
		    packagedElement.addAttribute("xmi:type","uml:Package");
		    packagedElement.addAttribute("xmi:id",packageInfo.id);//这里需要我们手动给出，在原始的文件中获取
		   // packagedElement.addAttribute("name", "");//同上
		    //packagedElement标签
	   
		     
		    Element ownedBehavior=packagedElement.addElement("ownedBehavior");
		    ownedBehavior.addAttribute("xmi:type","uml:Interaction");
		    ownedBehavior.addAttribute("xmi:id", "EAID_IN000000_C5A_D3F7_4321_B37B_01F132FAA48");//我们给出
		    ownedBehavior.addAttribute("name", "EA_Interaction1");
		    //ownedBehavior
		    
		    
		    //构造<lifeline>
		    for(LifeLineInfo temp:Lifelines){
		    	Element lifeline=ownedBehavior.addElement("lifeline");
		    	lifeline.addAttribute("xmi:type","uml:Lifeline");
		    	lifeline.addAttribute("xmi:id", temp.getlifeLineId());
		    	lifeline.addAttribute("name", temp.getlifeLineName()); 		       
		    }
		    //构造<fragment>	
		    //这里分2种情况，一种是没有组合片段的顺序图
		    //另一种是有组合片段的顺序图
		    //这里默认为有组合片段
		    for(CombinedFragmentInfo combinedfragment : CombinedFragments)
		    {
		    	//第一步先判断该组合片段是不是最高级组合片段；这里的最高级分2种情况
		    	//1.嵌套其他组合片段
		    	//2.单独的组合片段
		    //首先对嵌套其他组合片段的最高级组合片段进行处理
		    //如果所有的NestingCombinedFragmentsID当中都不包含当前的组合片段ID
		    //当前的组合片段即为最高级组合片段
		
		    if(!NestingCombinedFragmentsID.contains(combinedfragment.getID())){	 
		 
		       Element fragment=ownedBehavior.addElement("fragment");
		       fragment.addAttribute("xmi:type", "uml:CombinedFragment");
		       fragment.addAttribute("xmi:id", combinedfragment.getId());
		        
		       fragment.addAttribute("interactionOperator", combinedfragment.getType());
		       //遍历该组合片段的Part
		       for(FragmentPartInfo fragmentpart : combinedfragment.getFragmentParts())
		       {
		       //首先添加一个operand标签
		       Element operand=fragment.addElement("operand");
		       operand.addAttribute("xmi:type","uml:InteractionOperand");
		       operand.addAttribute("xmi:id", generateEAID());//我们给出。唯一即可		       
		       Element specification=operand.addElement("specification");
		       specification.addAttribute("xmi:type", "uml:OpaqueExpression");
		       specification.addAttribute("xmi:id",generateEAID());//我们给出，唯一即可
		       specification.addAttribute("body", fragmentpart.getConditionText());
		       //迭代处理part当中可能嵌套的组合片段
		       AddNestingCombinedFragements(fragmentpart,operand);		    	   
		       }
		    }	
		       //这里分2种情况，一种是组合片段嵌套另一个组合片段
		       //另一种是单一组合片段		    		       
		    }		       		       		             		       		        		   		    			
		    //构造<message>
		    for(MessageInfo message:Messages){
		    	Element messageElement=ownedBehavior.addElement("message");
		    	messageElement.addAttribute("xmi:type","uml:Message");
		    	messageElement.addAttribute("xmi:id", message.getConnectorId());
		    	messageElement.addAttribute("name", message.getName());
		    	messageElement.addAttribute("messageSort", message.getMessageSort());
		    	messageElement.addAttribute("sendEvent", message.getSendEvent());
		    	messageElement.addAttribute("receiveEvent", message.getReceiveEvent());
		    }
		    
		    //构造<Extension>
		    Element Extension=XMI.addElement("xmi:Extension");
		    //构造<elements>
		    Element elements=Extension.addElement("elements");
		    //构造elements下的<element>——包的信息
		    Element packageElement=elements.addElement("element");
		 
		    packageElement.addAttribute("xmi:idref", packageInfo.id);
		    packageElement.addAttribute("xmi:type", "uml:Package");
		    for(CombinedFragmentInfo combinedfragment : CombinedFragments)
		    {
		    	String SpeicalString="";
		    	String beforeString="$XREFPROP=$XID=";//开头的XID
		    	String XIDString = "{"+UUID.randomUUID().toString()+"}$XID"+";";
		        String middleString="$NAM=Partitions$NAM;$TYP=element property$TYP;$VIS=Public$VIS;$PAR=0$PAR;$DES=";
		        String ParString="";
		        List<FragmentPartInfo> ReverseParts=new ArrayList<FragmentPartInfo>();
		        for(FragmentPartInfo fragmentPartInfo : combinedfragment.getFragmentParts())
		        {
		        	
		        	int index=combinedfragment.getFragmentParts().indexOf(fragmentPartInfo);
		        	ReverseParts.add(index, combinedfragment.getFragmentParts().get(combinedfragment.getFragmentParts().size()-index-1));
		        }
		        for(FragmentPartInfo fragmentPart : ReverseParts)
		        {
//		        	int fragmentpartIndex=combinedfragment.getFragments().indexOf(fragmentPart);
		        	ParString+="@PAR;"+"Name="+fragmentPart.getConditionText()+";"
		        	+"Size="+fragmentPart.getSize().substring(0,fragmentPart.getSize().length()-2)+";"+"@ENDPAR;";		        			        			        		        	
		        }
		        String CLTString =combinedfragment.getId().substring(5, combinedfragment.getId().length()).replace("_", "-");
		        String afterString="$DES;$CLT={"+CLTString+"}$CLT;$SUP=&lt;none&gt;$SUP;$ENDXREF;";
		    	SpeicalString=beforeString+XIDString+middleString+ParString+afterString;
		    	Element element = elements.addElement("element");
		    	element.addAttribute("xmi:idref",combinedfragment.getId() );//我们给出。用来识别combinedfragment
		    	element.addAttribute("xmi:type", "uml:InteractionFragment");
		    	element.addAttribute("name", combinedfragment.getName());//我们给出
		    	Element xrefs=element.addElement("xrefs");
		    	xrefs.addAttribute("value", SpeicalString);
		    	
		    }
		 
		    //构造<connectors>
		    Element connectors=Extension.addElement("connectors");
		    Collections.sort(MessageLocationY);
		    for(MessageInfo message:Messages){
		    	Element connector=connectors.addElement("connector");
		    	connector.addAttribute("xmi:idref",message.getConnectorId());
		    	//构造<source>
		    	Element source=connector.addElement("source");
		    	source.addAttribute("xmi:idref", message.getSourceId());
		    	Element modifiers=source.addElement("modifiers");
		    	modifiers.addAttribute("isNavigable","false");//我们给出
		    	//构造<target>
		        
		    	Element target=connector.addElement("target");
		    	target.addAttribute("xmi:idref", message.getTragetId());
		    	Element modifiers1=target.addElement("modifiers");
		    	modifiers1.addAttribute("isNavigable", "true");//我们给出
		    	Element apperance=connector.addElement("appearance");
		    	
		    	apperance.addAttribute("seqno",String.valueOf(MessageLocationY.indexOf(Double.parseDouble(message.getLocationY()))+1));//按照Y坐标的大小进行排序
		    	//构造extendedProperties
		    	Element extendedProperties=connector.addElement("extendedProperties");
		    	if(message.isNavigable==true)
		    	{extendedProperties.addAttribute("diagram", message.getDiagram_id()).
		    	//addAttribute("privatedata5",message.getPrivatedata5()).
		    	addAttribute("privatedata4", "1").
		    	addAttribute("sequence_points",message.getSequence_points());
		    	
		    	}//我们給出
		    	else 
		    		{
	    		extendedProperties.addAttribute("diagram", message.getDiagram_id()).
		    	//addAttribute("privatedata5",message.getPrivatedata5()).
		    	addAttribute("privatedata4", "0").//我们給出
		    	addAttribute("sequence_points",message.getSequence_points());
	    		} 
		    }
		    
		    
		  //构造<diagrams>
		    Element diagrams=Extension.addElement("diagrams");  
		    Element diagram=diagrams.addElement("diagram");
		    diagram.addAttribute("xmi:id",Diagram_id);
		    Element model=diagram.addElement("model");
		    model.addAttribute("owner", packageInfo.id);
		    Element properties=diagram.addElement("properties");
		    properties.addAttribute("type", "Sequence");
		    diagram.addElement("style2");
		    Element dia_elements=diagram.addElement("elements");
		  
		    for(MessageInfo message :Messages)
		    {
		    	Element messageElement=dia_elements.addElement("element");
		    	messageElement.addAttribute("subject", message.getConnectorId());
		    }
		    
		    for(LifeLineInfo temp:Lifelines){
		    	Element dia_element=dia_elements.addElement("element");
		    	dia_element.addAttribute("geometry", temp.getGeometry());
		    	dia_element.addAttribute("subject", temp.getlifeLineId());		    			    	
		    }
		    for(CombinedFragmentInfo combinedfragment : CombinedFragments)
		    {
		    	Element combinedfragmentelement=dia_elements.addElement("element");
		    	combinedfragmentelement.addAttribute("geometry", combinedfragment.getGeometry());
		    	combinedfragmentelement.addAttribute("subject", combinedfragment.getId());
		    	
		    }
		  outputXml(doc, filename);
	}
    public String transVioletIdToEAID(String id)
    {
    	if(id.length()==1)
    	return "EAID_"+"9CE39211_2A09_4b09_B755_27EE1A169D7"+id;
    	else
    	return "EAID_"+"9CE39211_2A09_4b09_B755_27EE1A169D"+id;
    	
    }
    public String generateEAID()
    {
    	String EAID= "EAID_"+UUID.randomUUID().toString();
    	return EAID.replace("-", "_");
    }
    public void AddNestingCombinedFragements(FragmentPartInfo fragmentpart,Element operand)
    {
    if(fragmentpart.getNestingchilds().size()>0)//首先判断还有没有嵌套的组合片段
    {         		      		    			           
       for(CombinedFragmentInfo nestingcombinedfragment: fragmentpart.getNestingchilds())
     	  //如果有,遍历所有嵌套的组合片段
    	  //这是第一种情况，即为最高级组合片段中的含有嵌套组合片段的情况
      { 
     for(FragmentPartInfo fragmentPartInfo :nestingcombinedfragment.getFragmentParts())
    {  
     Element nestingfragment=operand.addElement("fragment");
 	  nestingfragment.addAttribute("xmi:type", "uml:CombinedFragment");
 	  nestingfragment.addAttribute("xmi:id", nestingcombinedfragment.getId());//我们给出，唯一即可
 	  nestingfragment.addAttribute("interactionOperator",nestingcombinedfragment.getType());	    	 
 	  Element nestingoperand=nestingfragment.addElement("operand");
 	  nestingoperand.addAttribute("xmi:id", generateEAID());//我们给出，唯一即可
 	  Element nestingspecification=nestingoperand.addElement("specification");
 	  nestingspecification.addAttribute("xmi:type", "uml:OpaqueExpression");
 	  nestingspecification.addAttribute("xmi:id", generateEAID());//我们给出，唯一即可
 	  nestingspecification.addAttribute("body",fragmentPartInfo.getConditionText());
 	
 	  AddNestingCombinedFragements(fragmentPartInfo, nestingoperand); 	  
 	  //迭代添加
      }	
      }
   } 		       
 if(fragmentpart.getConcluedmessages().size()>0)//直接添加最底层fragment	
    {
//移除嵌套的组合片段中已经含有的消息
	for(CombinedFragmentInfo combinedFragmentInfo : fragmentpart.getNestingchilds())
	{
		for(FragmentPartInfo fragmentPartInfo:combinedFragmentInfo.getFragmentParts())
		{
			for(MessageInfo messageInfo : fragmentPartInfo.getConcluedmessages())
			{
				fragmentpart.getConcluedmessages().remove(messageInfo);
			}
		}
	}
	
//	for(BaseFragmentInfo baseFragmentInfo :Fragments)
//	{
//		System.out.println(baseFragmentInfo.getId());
//	}
	 
 	  for(MessageInfo concluedmessage : fragmentpart.getConcluedmessages())
		    {			
 		
 			
 		       
		    	Element fragment1=operand.addElement("fragment");		    	
		        fragment1.addAttribute("xmi:type","uml:OccurrenceSpecification");
		        int index=Messages.indexOf(concluedmessage);
		    	fragment1.addAttribute("xmi:id", Fragments.get(2*index).getId());		    
		  //  System.out.println(concluedmessage.getSourceId());		    	
		    	fragment1.addAttribute("covered",concluedmessage.getSourceId());	
		    	Element fragment2=operand.addElement("fragment");			   
		        fragment2.addAttribute("xmi:type","uml:OccurrenceSpecification");
		    	fragment2.addAttribute("xmi:id", Fragments.get(2*index+1).getId());
		  //  	 System.out.println(concluedmessage.getTragetId());	//		    
		    	fragment2.addAttribute("covered",concluedmessage.getTragetId());	
		    	index+=2;
		    	//System.out.println(index);
		    }
 	
 	} 
 }	    
 	   
    
	public void outputXml(Document doc, String filename) {
	    try {
	      //定义输出流的目的地
	      FileWriter fw = new FileWriter(filename);
	       
	      //定义输出格式和字符集
	      OutputFormat format 
	        = OutputFormat.createPrettyPrint();
	      format.setEncoding("windows-1252");
	       
	      //定义用于输出xml文件的XMLWriter对象
	      XMLWriter xmlWriter 
	        = new XMLWriter(fw, format);
	      xmlWriter.write(doc);//*****
	      xmlWriter.close(); 
	    } catch (IOException e) {
	      e.printStackTrace();
	    }   
	  }

}

