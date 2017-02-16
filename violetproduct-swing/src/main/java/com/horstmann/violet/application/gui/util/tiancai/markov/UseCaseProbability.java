package com.horstmann.violet.application.gui.util.tiancai.markov;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UseCaseProbability {
	/*
	 * 获取所有用例,确定用例执行关系的概率，然后存储在数据结构中（概率矩阵），
	 * 在转化Markov链集成用例级Markov链的时候赋给对应的迁移
	 * (需要从数据结构中按照用例的ID遍历查询对应的用例执行顺序关系对应的执行概率)
	 *
	 */
	private double[][] ucRelaProMatrix;  //概率矩阵，存储用例之间的执行关系概率
	private ConGraph graph;
	public UseCaseProbability() throws InvalidTagException
	{
		Initialize();
	}
	
	/*
	 * 初始化，求取:
	 * 邻接矩阵(表示了用例之间的执行关系)
	 * 和
	 * 用例节点(VertexList中含有:用例ID、用例名称、用例节点的别称ID)
	 */
	public void Initialize() throws InvalidTagException 
	{
		ConWork work=new ConWork();
		work.Initialize();
		graph=new ConGraph(work.getConUseCases());
		//按照邻接矩阵遍历用例节点
		ucRelaProMatrix=new double[graph.getNum()][graph.getNum()];
		
		
	}
	public void MergeProMatrix()
	{
		//概率矩阵ucRelaProMatrix
		//用例关系矩阵
		int num=graph.getNum();
		int[][] relaMatrix=graph.getAdjoinMatrix();
		
		
		for(int i=0;i<num-1;i++)
		{
			List<Integer> rearNodeList=new ArrayList<Integer>();
			for(int j=1;j<num;j++)
			{
				if(relaMatrix[i][j]==1)
				{
					 rearNodeList.add(j);
				}
			}
			graph.getVertexList().get(i).setRearNodeList(rearNodeList);
			//获取起始、结束用例打分;
			String currentUcName="Default Uc Name";
			for(ConVertex v:graph.getVertexList())
			{
				if(v.getNickID()==i)
				{
					currentUcName=v.getName();
				}
			}
			
			if(rearNodeList.size()==0)
			{
				System.out.println(currentUcName+"用例无后继用例！");
			}
			else if(rearNodeList.size()==1) 
			{	
				String tempUcName="Default rearUc Name";
				for(ConVertex v:graph.getVertexList())
				{
					if(v.getNickID()==rearNodeList.get(0))
					{
						tempUcName=v.getName();
					}
				}
				System.out.println(currentUcName+"用例有 1个 后继用例"+tempUcName+" 其执行概率为1.0");
				ucRelaProMatrix[i][rearNodeList.get(0)]=1.0;
			}
			else if(rearNodeList.size()==2)
			{
				List<String> tempUcNameList=new ArrayList<String>();
				for(int k=0;k<=1;k++)
				{
					for(ConVertex v:graph.getVertexList())
					{
						if(v.getNickID()==rearNodeList.get(k))
						{
							tempUcNameList.add(v.getName());
						}
					}
				}
				System.out.println(currentUcName+"用例有 2个 后继用例"+tempUcNameList.get(0)
						+"和"+tempUcNameList.get(1)+"  其执行概率可根据专家直接打分得到：");
				Scanner cin=new Scanner(System.in);
				double x=cin.nextDouble();
				double y=cin.nextDouble();
				cin.close();
				if(x+y==1.0)
				{
					System.out.println("两者概率满足归一化");
					ucRelaProMatrix[i][rearNodeList.get(0)]=x;
					ucRelaProMatrix[i][rearNodeList.get(1)]=y;
					
				}
				else
				{
					System.out.println("两者概率不满足归一化");
				}
				
			}
			else if(rearNodeList.size()>=3)
			{
				List<String> tempUcNameList=new ArrayList<String>();
				for(int k=0;k<=1;k++)
				{
					for(ConVertex v:graph.getVertexList())
					{
						if(v.getNickID()==rearNodeList.get(k))
						{
							tempUcNameList.add(v.getName());
						}
					}
				}
				System.out.println(currentUcName+"用例有 "+rearNodeList.size()+"个 后继用例");
				
				for(int k=0;k<tempUcNameList.size();k++)
				{
					System.out.print(currentUcName+" ---> "+tempUcNameList.get(k)
							+"("+rearNodeList.get(k)+")、 ");
				}
				
				//打分
				MergeProbability mp=new MergeProbability(rearNodeList.size());
				mp.mergeProbability();
				List<Double> proList=mp.getProList();
				
				//向概率矩阵对应位置赋值
				for(int j=0;j<rearNodeList.size();j++)
				{
					ucRelaProMatrix[i][rearNodeList.get(j)]=proList.get(j);
				}
				
			}
		}
	}
	
	
	
}
