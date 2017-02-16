package com.horstmann.violet.application.gui.util.tiancai.markov;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import Jama.Matrix; 
public class ProMatrix {
	
	Matrix m;
	double CR=0.0;
	List<Double> proList=new ArrayList<Double>();
	private static double[] RI={0.00,0.00,0.58,0.90,1.12,1.24,1.32,1.41,1.45};
	public ProMatrix(Matrix m)
	{
		this.m=m;
		computeCR();
	}
	//计算CR;
	public void computeCR()
	{
		DecimalFormat f=new DecimalFormat("0.000");
		double maxLamta=0.0;
		int location=0;
		List<Vertex> lamtaList=new ArrayList<Vertex>();
		
		Matrix lamtaMatrix=m.eig().getD();
		for(int i=0;i<lamtaMatrix.getRowDimension();i++)
		{
			int tag=0;
			for(int j=0;j<lamtaMatrix.getColumnDimension();j++)
			{
				if(i!=j)
				{
					if(lamtaMatrix.get(i, j)!=0.0)
					{
						tag=1;
					}
				}
			}
			if(tag==0)
			{
				Vertex v=new Vertex(i,lamtaMatrix.get(i, i));
				lamtaList.add(v);
			}
		}
		if(lamtaList!=null)
		{
			
			maxLamta=lamtaList.get(0).getValue();
			for(Vertex v:lamtaList)
			{
				if(maxLamta<v.getValue())
				{
					maxLamta=v.getValue();
					location=v.getLocation();
				}
			}
			if(RI[m.getColumnDimension()-1]!=0.0)
			{
				CR=(maxLamta-m.getColumnDimension())
						/((m.getColumnDimension()-1)*RI[m.getColumnDimension()-1]);
				
			}
			//System.out.println(m.getColumnDimension());
			double[] tempPro=new double[m.getColumnDimension()];
			for(int i=0;i<m.eig().getD().getColumnDimension();i++)
			{
				if(location==i)
				{
					
					for(int j=0;j<m.eig().getD().getRowDimension();j++)
					{
						tempPro[j]=m.eig().getV().get(j, i);//遍历列值;
					}
				}
			}
			double sum=0.0;
			for(int k=0;k<tempPro.length;k++)
			{
				sum+=tempPro[k];
			}
			if(sum!=0.0)
			{
				for(int k=0;k<tempPro.length;k++)
				{
					proList.add(tempPro[k]/sum);
				}
			}
			else
			{
				System.out.println("归一化分母为0,失败！");
			}
		}		
		
	}
	public List<Double> getProList() {
		return proList;
	}
	public double getCR() {
		return CR;
	}
	
}
