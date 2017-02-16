package cn.edu.hdu.lab.service.probability;

import java.util.List;

import Jama.Matrix;

public class MergeAveMatrix {
	int m;//矩阵维数
	int np;//矩阵个数
	List<double[][]> Matrixs; //n个成员输入的n个矩阵
	public MergeAveMatrix(){}
	public MergeAveMatrix(List<double[][]> Matrixs){
		this.Matrixs=Matrixs;
		m=Matrixs.get(0).length;
		np=Matrixs.size();
	}
	
	public Matrix merge()//计算得到平均判断矩阵
	{	
		Matrix M;
		if(Matrixs.size()>1)
			{
				for(int i=1;i<Matrixs.size();i++)
				{
					for(int j=0;j<m;j++)
					{
						for(int k=0;k<m;k++)
						{
							Matrixs.get(0)[j][k]+=Matrixs.get(i)[j][k];
						}
					}
				}
				for(int j=0;j<m;j++)
				{
					for(int k=0;k<m;k++)
					{
						Matrixs.get(0)[j][k]/=np;
					}
				}
				M=new Matrix(Matrixs.get(0));
			}
			else  M=new Matrix(Matrixs.get(0));
		return M;
		
	}
}
