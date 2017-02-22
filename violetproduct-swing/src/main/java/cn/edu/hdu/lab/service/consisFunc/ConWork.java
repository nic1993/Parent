package cn.edu.hdu.lab.service.consisFunc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.edu.hdu.lab.dao.condao.ConGraph;
import cn.edu.hdu.lab.dao.condao.ConSD;
import cn.edu.hdu.lab.dao.condao.ConUseCase;
import cn.edu.hdu.lab.dao.tmc.Tmc;
import cn.edu.hdu.lab.dao.uml.UseCase;
import cn.edu.hdu.lab.service.parser.InvalidTagException;
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
		conUseCases=(new ConInfoParse()).conInfoParse(useCases);
	}
	public List<Object> ConsistencyCheck()
	{
		List<Object> verifyReList=new ArrayList<Object>();
		
	/*确定性;
		1、场景后置条件唯一;
		2、软件必然执行结束;
		3.用例前置条件是用例下所有场景的前置条件
		 * */	
		//软件必然执行结束
		ConGraph tempGraph=new ConGraph(conUseCases);
		System.out.println("邻接矩阵节点数："+tempGraph.getNum());
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
			if(Sprob!=1.0)
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
		
		//判断从软件起始状态到每个节点都存在执行路径，能执行到每个节点；-
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
		
		verifyReList.add(true);
		verifyReList.add("一致性验证通过！");
		verifyReList.add(graph.getAdjoinMatrix());
		return verifyReList;
		
	}
	public List<ConUseCase> getConUseCases() {
		return conUseCases;
	}

}
