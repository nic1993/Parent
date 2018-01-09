package cn.edu.hdu.lab.service.consisFunc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.edu.hdu.lab.dao.condao.ConGraph;
import cn.edu.hdu.lab.dao.condao.ConSD;
import cn.edu.hdu.lab.dao.condao.ConUseCase;
import cn.edu.hdu.lab.dao.condao.ConVertex;
import cn.edu.hdu.lab.dao.tmc.Tmc;
import cn.edu.hdu.lab.dao.uml.UseCase;
import cn.edu.hdu.lab.service.parserEA.InvalidTagException;
//import org.omg.PortableServer.POAManagerPackage.State;
public class ConWork {
	private List<ConUseCase> conUseCases;
	private ConSD conSD;
	private List<UseCase> useCases;
	private Tmc f_Tmc;
	//获取用例图消息;
	public ConWork(){}
	public ConWork(List<UseCase> useCases)
	{
		this.useCases=useCases;
	}
	public void Initialize() throws InvalidTagException
	{
		conUseCases=new ConInfoParse().conInfoParse(useCases);
	}
	public List<Object> ConsistencyCheck()
	{
		List<Object> verifyReList=new ArrayList<Object>();
		
	/*确定性;
		1、场景后置条件唯一;EAID_OP000000_7032_40ad_908E_AA82C86BFE7C
		2、软件必然执行结束;
		3.用例前置条件是用例下所有场景的前置条件
		 * */	
		//软件必然执行结束
		
		ConGraph tempGraph=new ConGraph(conUseCases);
		
		tempGraph.printGraph();
		boolean isOver=false;
		for(int i=0;i<tempGraph.getNum();i++)
		{
			if(tempGraph.getAdjoinMatrix()[i][tempGraph.getNum()-1]==1)
			{
				isOver=true;
				break;
			}
		}
		if(isOver==false)
		{
			verifyReList.add(false);
			verifyReList.add("'软件必定执行结束' 验证不通过！");
			return verifyReList;
		}
		
	//一、归一性；
		//1.序列图概率归一化验证
		for(Iterator<ConUseCase> it=conUseCases.iterator();it.hasNext();)
		{
			ConUseCase conUseCase=it.next();
			double Sprob=0;
			for(Iterator<ConSD> sdIt=conUseCase.getConSDset().iterator();sdIt.hasNext();)
			{
				ConSD conSD=sdIt.next();				
				Sprob+=conSD.getProb();
			}
			if(Sprob < 0.998 || Sprob > 1.002)
			{
				verifyReList.add(false);
				verifyReList.add("'序列图概率归一' 验证不通过！");
				return verifyReList;
			}
		}
		
	    /*
	     * 可达性
	     * 图的遍历算法
	     *---////弗洛伊德||迪杰斯特拉
	     */
		
		
		ConGraph graph=new ConGraph(conUseCases);
		//graph.printGraph();
		/*
		 * 对每个节点(除了初始节点和尾节点)
		 * 验证是否存在前置用例或从软件初始状态执行到该节点
		 * 验证是否存在后置用例节点或者执行到软件结束节点
		 */
		//验证1-5的用例节点是否有前置节点和后置节点，同时拥有则通过验证，返回True;
		for(int i=1;i<graph.getNum()-1;i++)
		{
			boolean isAccess=false;
			boolean accessPreAdjoinVertex=false;
			boolean accessPostAdjoinVertex=false;
			for(int j=0;j<graph.getNum();j++)
			{
				if(graph.getAdjoinMatrix()[j][i]==1)
				{
					accessPreAdjoinVertex=true;
					break;
				}
			}
			for(int j=0;j<graph.getNum();j++)
			{
				if(graph.getAdjoinMatrix()[i][j]==1)
				{
					accessPostAdjoinVertex=true;
					break;
				}
			}
			if(accessPreAdjoinVertex==true&&accessPostAdjoinVertex==true)
			{
				isAccess=true;
			}
			if(isAccess==false)
			{
				verifyReList.add(false);
				verifyReList.add("'UML模型可达性' 验证不通过！");
				return verifyReList;
			}
		}
		
		//判断从软件起始状态到每个节点都存在执行路径，能执行到每个节点;
		for(int i=1;i<graph.getNum();i++)
		{
			if(graph.isHavePath(0, i)==false) 
			{
				verifyReList.add(false);
				verifyReList.add("'UML模型可达性' 验证不通过！");
				return verifyReList;
			}
		}
		//判断从该节点开始到软件执行结束节点存在路径；
		for(int i=1;i<graph.getNum();i++)
		{
			if(graph.isHavePath(i, graph.getNum()-1)==false) 
			{
				verifyReList.add(false);
				verifyReList.add("'UML模型可达性' 验证不通过！");
				return verifyReList;
			}
		}
		
		
		/*
		 * 可达性验证通过的前三个结果
		 */
		verifyReList.add(true);
		verifyReList.add("一致性验证通过！");
		verifyReList.add(graph.getAdjoinMatrix());
		
		/*抽取路径 两个结果
		第一个：第每个用例的前置路径：从开始状态到用例的路径；
		第二个：每个用例的后置路径：从用例到结束状态的路径*/
		
		verifyReList.add(obtainPrePath(graph));
		verifyReList.add(obtainPostPath(graph));
		
		//添加用例途径
		return verifyReList;
		
	}
	public List<ConUseCase> getConUseCases() {
		return conUseCases;
	}
	
	/**
	 * 获取从开始结点到用例的路径
	 * @param graph 图
	 * @return 所有用例的路径
	 */
	private Map<String,List<String>> obtainPrePath(ConGraph graph)
	{
		System.out.println("…………………………………………获取路径………………………………");
		
		for(ConVertex conVertex : graph.getVertexList())
		{
			System.out.println("++++++: " + conVertex.getName());
		}
		
		Map<String,List<String>> prePathMap=new HashMap<String,List<String>>();
		for(int i=1;i<graph.getNum()-1;i++)
		{
			List<String> prePath=new ArrayList<String>();
			boolean[] visited=new boolean[graph.getNum()];//默认为false 
			
			
			List<Integer> pathNums=new ArrayList<Integer>();
			pathNums.add(0);
			pathNums.addAll(searchPath(graph,visited,0,i));
			for(int k=0;k<pathNums.size();k++)
			{
				System.out.println("uc :  " + graph.getVertexList().get(pathNums.get(k)).getName());
				prePath.add(graph.getVertexList().get(pathNums.get(k)).getName());
			}
			prePathMap.put(graph.getVertexList().get(i).getName(),prePath);
		}
		
		/*System.out.println(prePathMap.size());
		for(String en:prePathMap.keySet())
		{
			System.out.println(prePathMap.get(en).toString());
		}*/
		
		
		return prePathMap;
	}
	
	/**
	 * 获取用例结点到结束状态的路径
	 * @param graph 图
	 * @return 所有用例的路径
	 */
	private Map<String,List<String>> obtainPostPath(ConGraph graph)
	{
		Map<String,List<String>> postPathMap=new HashMap<String,List<String>>();
		
		for(int i=1;i<graph.getNum()-1;i++)
		{
			List<String> Path=new ArrayList<String>();
		    boolean[] visited=new boolean[graph.getNum()];  //默认全部为False
		    for(int j=0;j<visited.length;j++)
			{
				visited[j]=false;
			}
			List<Integer> pathNums=new ArrayList<Integer>();
			pathNums.add(i);
			pathNums.addAll(searchPath(graph,visited,i,graph.getNum()-1));
			for(int k=0;k<pathNums.size();k++)
			{
				Path.add(graph.getVertexList().get(pathNums.get(k)).getName());
			}
			postPathMap.put(graph.getVertexList().get(i).getName(),Path);
		}
		/*System.out.println(postPathMap.size());
		for(String en:postPathMap.keySet())
		{
			System.out.println(postPathMap.get(en).toString());
		}*/
		return postPathMap;
	}
	/**
	 * 搜索路径
	 * @param graph 图
	 * @param i 起始结点
	 * @param j 结束结点
	 * @return 两个结点之间的路径
	 */
	private List<Integer> searchPath(ConGraph graph, boolean[] visited,int start ,int end)
	{
		
		List<Integer> pathNums=new ArrayList<Integer>();			
		for(int i=0;i<graph.getNum();i++)
		{
			if(graph.getAdjoinMatrix()[start][i]==1&&visited[i]!=true)
			{
				pathNums.add(i);
				visited[i]=true;
				
				List<Integer> a=searchPath(graph,visited,i,end);
				if(a.contains(end))
				{
					pathNums.addAll(a);
					return pathNums;
				}
			}			
		}		
		return pathNums;
	}
}
