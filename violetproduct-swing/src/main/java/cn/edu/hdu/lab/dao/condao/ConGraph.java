package cn.edu.hdu.lab.dao.condao;

import java.util.ArrayList;
import java.util.List;

import cn.edu.hdu.lab.config.StaticConfig;

public class ConGraph {
	private static int num; //节点数;
	private static List<ConVertex> vertexList=new ArrayList<ConVertex>(); //顶点表,存储节点（对应着用例）信息
	private static int adjoinMatrix[][]; //邻接矩阵，表示用例之间的关系
	
	public ConGraph(List<ConUseCase> conUseCases)
	{
		graphInitial(conUseCases);
	}
	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public List<ConVertex> getVertexList() {
		return vertexList;
	}

	public void setVertexList(List<ConVertex> vertexList) {
		this.vertexList = vertexList;
	}

	public int[][] getAdjoinMatrix() {
		return adjoinMatrix;
	}

	public void setAdjoinMatrix(int[][] adjoinMatrix) {
		this.adjoinMatrix = adjoinMatrix;
	}

	public void graphInitial(List<ConUseCase> conUseCases)
	{
		num=1;
		//根据前置条件 初始化一个首状态结点;
		ConVertex initVertex=new ConVertex("000000","Initial",0);
		vertexList.add(initVertex);
		
		for(ConUseCase uc:conUseCases)
		{
			ConVertex vertex=new ConVertex(uc.getID(),uc.getUseCaseName(),num);
			num++;
			vertexList.add(vertex);
		}
		//初始化一个尾节点；表示软件执行结束;
		ConVertex exitVertex=new ConVertex("999999","Exit",num++);
		
		vertexList.add(exitVertex);
		adjoinMatrix=new int[num][num];
		for(int i=0;i<num;i++)
		{
			for(int j=0;j<num;j++)
			{
					adjoinMatrix[i][j]=0;//初始化节点之间没有联系；
			}
		}
		//根据用例前置条件和顺序图的后置条件来给邻接矩阵建立联系；
		//节点别称nickID对应邻接矩阵的坐标序号
//		for(ConVertex vertex:vertexList)
//		{
//			System.out.println(vertex.getID());
//		}
		for(int i=0;i<num;i++)
		{
			//首元节点，软件初始执行寻找后置用例
			if(i==0)
			{
				for(ConUseCase uc:conUseCases)
				{
					if(uc.getPreCondition()!=null&&uc.getPreCondition().contains(StaticConfig.initialCondition))
					{
						for(ConVertex vertex:vertexList)
						{
							if(vertex.getID()!=null&&vertex.getID().equals(uc.getID()))
							{
								adjoinMatrix[0][vertex.getNickID()]=1;
							}
						}
						
					}
				}
			}
			else if(i!=(num-1))
			{
				//其他用例之间建立关系;
				for(ConUseCase uc:conUseCases)
				{
					int tempX = 0;
					for(ConVertex vertex:vertexList)
					{
						
						if(vertex.getID()!=null&&vertex.getID().equals(uc.getID()))
						{
							tempX=vertex.getNickID();
							break;
						}
					}
					
					for(ConSD sd:uc.getConSDset())
					{
						for(ConUseCase tempUc:conUseCases)
						{
							if(tempUc.getPreCondition()!=null&&sd.getPostCondition().contains(tempUc.getPreCondition()))
							{
								for(ConVertex vertex:vertexList)
								{
									if(vertex.getID()!=null&&tempUc.getID().equals(vertex.getID()))
									{
										adjoinMatrix[tempX][vertex.getNickID()]=1;
									}
								}
								
							}
						}
					}
				}
			}
			else//寻找结束节点的前置用例，建立关系
			{
				for(ConUseCase uc:conUseCases)
				{
					for(ConSD sd:uc.getConSDset())
					{
						if(sd.getPostCondition().contains("SoftwareFinish"))  //结束判断*************根据XML信息修改*****************
						{
							for(ConVertex vertex:vertexList)
							{
								if(vertex.getID()!=null&&uc.getID().equals(vertex.getID()))
								{
									adjoinMatrix[vertex.getNickID()][num-1]=1;
								}
							}
							
						}
					}
				}
			}
		}
		
	}

	public void printGraph()
	{
		System.out.println("用例执行关系用邻接矩阵表示如下：");
		System.out.print(" ");
		for(int i=0;i<num;i++)
		{
			System.out.print(" "+i);
		}
		System.out.println(); 
		for(int i=0;i<num;i++)
		{
			System.out.print(i+" ");
			for(int j=0;j<num;j++)
			{
				System.out.print(adjoinMatrix[i][j]+" ");
			}
			System.out.println();
		}
		
	}

	//判断两个节点之间是否存在连通路径
	public Boolean isHavePath(int start,int end)
	{
		List<Integer> queue=new ArrayList<Integer>();
		List<Integer> visited=new ArrayList<Integer>();
		queue.add(start);
		while(!queue.isEmpty())
		{
			for(int i=0;i<num;i++)
			{
				if(adjoinMatrix[start][i]==1&&!visited.contains(i))
				{
					queue.add(i);
				}
			}
			if(queue.contains(end))
			{
				return true;
			}
			else
			{
				visited.add(queue.get(0));
				queue.remove(0);
				if(!queue.isEmpty())
				{
					start=queue.get(0);
				}
			}
		}		
		return false;
	}
}
