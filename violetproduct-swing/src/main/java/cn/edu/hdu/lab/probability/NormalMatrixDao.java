package cn.edu.hdu.lab.probability;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Jama.Matrix; 
public class NormalMatrixDao {

	Matrix M;
	int m;
	List<double[][]> Matrixs=new ArrayList<double[][]>();
	public NormalMatrixDao(int num){
		m=num;
		Initial();
	};
	public void Initial()
	{
		Scanner in=new Scanner(System.in);
		System.out.println("评分对象的数量："+m);
		//int m=in.nextInt();
		System.out.println("评分人数：");
		int np=in.nextInt();
		
		for(int i=0;i<np;i++)
		{
			double[][] tempM=new double[m][m];
			for(int j=0;j<m;j++)
			{
				for(int k=0;k<m;k++)
				{
					tempM[j][k]=in.nextDouble();
				}
			}
			Matrixs.add(tempM);
		}
		
		if(Matrixs!=null)
		{
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
			else
				if(Matrixs.size()==1)
				{
					M=new Matrix(Matrixs.get(0));
				}
		}
		else
		{
			System.out.println("评分矩阵输入错误！");
		}
		
	}
	public Matrix getM() {
		return M;
	}
}
