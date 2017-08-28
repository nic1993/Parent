package cn.edu.hdu.lab.service.parserEA;

import java.util.ArrayList;

import cn.edu.hdu.lab.config.StaticConfig;
import cn.edu.hdu.lab.dao.uml.Behavior;
import cn.edu.hdu.lab.dao.uml.Diagram;
import cn.edu.hdu.lab.dao.uml.LifeLine;
import cn.edu.hdu.lab.dao.uml.Node;
import cn.edu.hdu.lab.dao.uml.SD;
import cn.edu.hdu.lab.dao.uml.UseCase;

/*
 * 信息获取类
 * 用文件名称和XML定义的解析器来读取UML模型
 * 首先，解读用例UseCase信息；
 * 根据用例下面的行为信息Behavior里面的标记信息来锁定Extension扩展下对应的Diagram片段
 * 然后根据Diagram里面的标记信息来确定顺序图对应的信息（类型，名称，ID）
 * 然后根据顺序图的信息寻找对应的顺序图文件
 * 
 *其次，找到顺序图文件后，获取顺序图里面的详细信息
 */
public class InfoRetrieve {
	private String xmlFile;
	private UMLReader parser; //定义解读器，解读参数是文件名xmlFile;
	
	private ArrayList<UseCase> useCases;
	private ArrayList<Diagram> diagrams;
	private SD sdset=new SD();
	private ArrayList<LifeLine> lifeLines;
	private ArrayList<Node> nodes;
	

	public InfoRetrieve(String xmlFile)
	{
		this.xmlFile=xmlFile;
	}
	public void initialize() throws InvalidTagException
	{
		this.parser=new UMLReader(this.xmlFile);
		
	}
	/*
	 * 寻找用例，根据自身的Behaviors找到对应的diagrams
	 * 根据Diagrams 获取 对应的顺序图SDSets
	 * 在每个用例中 封装Diagram对应的场景sdSets
	 */
	public void getUseCasesInfo() throws Exception
	{
		//获取用例
		this.parser.retrieveuseCases();
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
			
			ArrayList<SD> sdSets=new ArrayList<SD>();
			/*for(Diagram diagram:useCase.getDiagrams())
			{
				
				String sdFileName=StaticConfig.umlPathPrefix+diagram.getDiagramName()+".xml";			
				XMLReaderEA sdParser=new XMLReaderEA(sdFileName);
				
				SD sd=new SD();
				
				//id,name,lifeLines,messages,fragments
				sd.setId(diagram.getDiagramID());
				sd.setName(diagram.getDiagramName());
				//此时已经获取了包含参与者在内的所有生命线
				
			    sd.setLifeLines(sdParser.retrieveLifeLines()); 
			   
			    //以下添加顺序不能变化，否则出错
			    
			    //位点和组合片段同时获取
			    sdParser.retrieveFragments();	   
			    sd.setFragments(sdParser.getFragments());
			    sd.setNodes(sdParser.getNodes());
			    sd.setProb(diagram.getProb());
			    sd.setMessages(sdParser.retrieveMessages(sd.getProb()));
			    
			    sd.setProb(diagram.getProb());
			    sd.setPostSD(diagram.getNotation());

				sdSets.add(sd);
			}*/
			
			for(Diagram diagram:useCase.getDiagrams())
			{
				
				String sdFileName=StaticConfig.umlPathPrefix+diagram.getDiagramName()+".xml";			
				UMLReader sdParser=new UMLReader(sdFileName,diagram.getDiagramName());
				
				SD sd=new SD();
				sdSets.add(sd);
				
				//id,name,lifeLines,messages,fragments
				sd.setId(diagram.getDiagramID());
				sd.setName(diagram.getDiagramName());
				
				/*System.out.println(sd.getId());
				System.out.println(sd.getName());*/
				
				//准备阶段：获取顺序图下所有子顺序图信息  以及  子顺序图中所有图元（组合片段，引用，对象，消息）的ID和坐标信息
				sdParser.retrieveAllDiagramElements();
				/*
				{
					System.out.println("\n所有子图的消息:");
					for(DiagramsData d:sdParser.umlAllDiagramData)
					{
						System.out.println(d.getName()+"----"+d.getDiagramID());
						System.out.println(d.toString());
					}
					System.out.println("*********************************************");
				}*/
				
				//此时已经获取了包含参与者在内的所有生命线
			    sd.setLifeLines(sdParser.retrieveLifeLines());
			   /* System.out.println("\n生命线信息：");
			    for(LifeLine l:sd.getLifeLines())
			    {
			    	System.out.println(l.toString());
			    }
			    System.out.println("*********************************************");*/
			    
			   /* System.out.println("\n所有信息坐标数量："+FixFragmentTool.rectangleById.size());
				Iterator iter = FixFragmentTool.rectangleById.entrySet().iterator();
				while (iter.hasNext()) 
				{
					Map.Entry entry = (Map.Entry) iter.next();
					Object key = entry.getKey();
					Object val = entry.getValue();
					System.out.println(key+"---"+val);
				}
				System.out.println("*********************************************");*/
			    //以下添加顺序不能变化，否则出错
			    
			    //位点和组合片段同时获取
			    //暂时没有赋给sd
			    sdParser.retrieveFragments();	   
			   
			    //sd.setFragments(sdParser.getFragments());//此步组合片段是并列存在的
			    
			    sd.setNodes(sdParser.getNodes());
			    /*
			    System.out.println("\n节点信息：");
			    System.out.println(sd.getNodes().size());*/
			    
			    
			    sd.setProb(diagram.getProb());			    
			    sdParser.retrieveMessages(sd.getProb());
			    //sd.setMessages(sdParser.retrieveMessages(sd.getProb()));			    
			    //组合所有信息			    
			    sdParser.assembleInfo2DiffDiagram();
			    sd.setMessages(sdParser.getCompleteSD().getMessageArray());
			    sd.getMessages().get(sd.getMessages().size()-1).setLast(true);
			    sd.setId(sdParser.getCompleteSD().getDiagramID());
			    sd.setLifeLines(sdParser.getCompleteSD().getLifelineArray());
			    sd.setFragments(sdParser.getCompleteSD().getFragmentArray());
				//归纳
				/* System.out.println("\n组合片段信息：");	
				for(Fragment frag:sdParser.getFragments())
				{
					System.out.println("类型："+frag.getType()+"---"+frag.getName()+"----"+frag.getId());
					System.out.println("组合片段坐标"+frag.getRectangle().toString());
					System.out.println("操作域坐标：");
					for(Operand oper:frag.getOperands())
					{
						System.out.println(oper.getRectangle().toString());
					}
				}
				System.out.println("*********************************************");*/
			    
			    sd.setProb(diagram.getProb());
			    sd.setPostSD(diagram.getNotation());			    
			}
			useCase.setSdSets(sdSets);
		}
		for(UseCase useCase:useCases)
		{
			if(useCase.getDiagrams().size()==0)
			{
				useCases.remove(useCase);
			}
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

	public UMLReader getParser() {
		return parser;
	}

	public void UMLReader(UMLReader parser) {
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

	public SD getSdset() {
		return sdset;
	}

	public void setSdset(SD sdset) {
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
