package com.horstmann.violet.application.gui.util.tiancai.markov;

import java.util.ArrayList;

/*
 * 信息获取类
 * 用文件名称和XML定义的解析器来读取UML模型
 * 首先，解读用例UseCase信息；
 * 根据用例下面的行为信息Behavior里面的标记信息来锁定Extension扩展下对应的Diagram片段
 * 然后根据Diagram里面的标记信息来确定顺序图对应的信息（类型，名称，ID）
 * 然后根据顺序图的信息寻找对应的顺序图文件
 * 
 *其次，找到顺序图文件后，获取顺序图里面的详细信息
 *……………………………………（有待续写）
 */
public class InfoRetrieve {
	private String xmlFile;
	private XMLReaderEA parser; //定义解读器，解读参数是文件名xmlFile;
	
	private ArrayList<UseCase> useCases;
	private ArrayList<Diagram> diagrams;
	private SDSet sdset=new SDSet();
	private ArrayList<LifeLine> lifeLines;
	private ArrayList<Node> nodes;
	

	public InfoRetrieve(String xmlFile)
	{
		this.xmlFile=xmlFile;
	}
	public void initialize() throws InvalidTagException
	{
		this.parser=new XMLReaderEA(this.xmlFile);
		
	}
	/*
	 * 寻找用例，根据自身的Behaviors找到对应的diagrams
	 * 根据Diagrams 获取 对应的顺序图SDSets
	 * 在每个用例中 封装Diagram对应的场景sdSets
	 */
	public void getUseCasesInfo() throws InvalidTagException
	{
		this.parser.retrieveuseCases();//获取用例
		this.setUseCases(this.parser.getUseCases());
		this.setDiagrams(this.parser.retrieveuseDiagrams());
		
		for(UseCase useCase:useCases)
		{
			if(useCase.getBehaviors()==null)
			{
				continue;
			}
			ArrayList<Diagram> ownDiagrams=new ArrayList<Diagram>();
			for(Behavior behavior: useCase.getBehaviors())
			{
				for(Diagram diagram:diagrams)
				{
					if(behavior.getBehaviorID().equals(diagram.getBehaviorID()))
					{
						ownDiagrams.add(diagram);
					}
				}
			}
			useCase.setDiagrams(ownDiagrams);
			
			ArrayList<SDSet> sdSets=new ArrayList<SDSet>();
			for(Diagram diagram:useCase.getDiagrams())
			{
				
				String sdFileName="ResetMapXML\\"+diagram.getDiagramName()+".xml";			
				XMLReaderEA sdParser=new XMLReaderEA(sdFileName);
				
				SDSet sd=new SDSet();
				//id,name,lifeLines,messages,fragments
				sd.setId(diagram.getDiagramID());
				sd.setName(diagram.getDiagramName());
				//此时已经获取了包含参与者在内的所有生命线
			    sd.setLifeLines(sdParser.retrieveLifeLines()); 
			   
			    //以下添加顺序不能变化，否则出错
			    
			    //位点和组合片段同时获取
			    sdParser.retrieveFragments();//		   
			    sd.setFragments(sdParser.getFragments());
			    sd.setNodes(sdParser.getNodes());
			    sd.setProb(diagram.getProb());
			    sd.setMessages(sdParser.retrieveMessages(sd.getProb()));
			    
			    sd.setProb(diagram.getProb());
			    sd.setPostSD(diagram.getNotation());

				sdSets.add(sd);
			}
			useCase.setSdSets(sdSets);
		}
		
	}
	
	//测试输出用例信息(用例name,id,behaviors,diagrams)
	public void print_useCases()
	{
		for(UseCase useCase:this.useCases)
		{
			useCase.print_useCase();
		}
	}
	
	public String getXmlFile() {
		return xmlFile;
	}

	public void setXmlFile(String xmlFile) {
		this.xmlFile = xmlFile;
	}

	public XMLReaderEA getParser() {
		return parser;
	}

	public void setParser(XMLReaderEA parser) {
		this.parser = parser;
	}

	public ArrayList<UseCase> getUseCases() {
		return useCases;
	}

	public void setUseCases(ArrayList<UseCase> useCases) {
		this.useCases = useCases;
	}

	public ArrayList<Diagram> getDiagrams() {
		return diagrams;
	}

	public void setDiagrams(ArrayList<Diagram> diagrams) {
		this.diagrams = diagrams;
	}

	public SDSet getSdset() {
		return sdset;
	}

	public void setSdset(SDSet sdset) {
		this.sdset = sdset;
	}

	public ArrayList<LifeLine> getLifeLines() {
		return lifeLines;
	}

	public void setLifeLines(ArrayList<LifeLine> lifeLines) {
		this.lifeLines = lifeLines;
	}

	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}
	

}
