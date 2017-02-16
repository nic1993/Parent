package cn.edu.hdu.lab.probability;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import Jama.Matrix;
public class MergeProbability {

	private int number;
	List<Double> proList=new ArrayList<Double>();
	public MergeProbability(int num)
	{
		number=num;
	}
	
	public List<Double> getProList() {
		return proList;
	}

	public  void mergeProbability()
	{
		 
		DecimalFormat f=new DecimalFormat("0.000");
		//生成判断矩阵
		NormalMatrixDao normalMatrixDao=(NormalMatrixDao)ValueOf.mergeMatrixDao(number);
		//Matrix m=normalMatrixDao.getM();
		double[][] array={{1.0,2.5,6.6667,8.6667},{0.3056,1.0,5.0,4.3333},
						       {0.1508,0.2,1.0,3.6667},{0.1157,0.2333,0.2833,1.0}};
		Matrix m=new Matrix(array);
		//显示判断矩阵
		m.print(4, 3);
		System.out.println("特征值矩阵：");
		m.eig().getD().print(4, 3);
		System.out.println("对应特征向量矩阵：");
		m.eig().getV().print(4, 3);
		System.out.println("分量维数："+m.getColumnDimension());
		//验证矩阵一致性
		ProMatrix M=(ProMatrix)ValueOf.mergeJudgeMatrix(m);
		
		if(M.getCR()>0.1)
		{
			System.out.println("判断矩阵一致性指标CR="+f.format(M.getCR())+">0.1,超标！");
		}
		else//生成场景最大特征值对应的特征向量归一化后的结果；
		{
			proList=M.getProList();
			System.out.println("判断矩阵一致性指标 CR="+f.format(M.getCR()));
			System.out.println("各个分量的概率为："+M.getProList());
		}
	}
	
	
}
