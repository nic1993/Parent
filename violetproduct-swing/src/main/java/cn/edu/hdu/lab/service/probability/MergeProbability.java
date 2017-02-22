package cn.edu.hdu.lab.service.probability;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import Jama.Matrix;
public class MergeProbability {

	private int number;
	
	List<double[][]> normalMatrixs;
	public MergeProbability(List<double[][]> proMatrixList)
	{
		number=proMatrixList.get(0).length;
		this.normalMatrixs=proMatrixList;
	}

	public  List<Object> mergeProbability()
	{
		List<Object> resultList=new ArrayList<>();
		double[] proArray=new double[number];
		
		DecimalFormat f=new DecimalFormat("0.000");
		//生成判断矩阵:平均矩阵
		MergeAveMatrix mam=new MergeAveMatrix(normalMatrixs);		
		Matrix m=mam.merge();
		
		//显示判断矩阵
		m.print(4, 3);
		System.out.println("特征值矩阵：");
		m.eig().getD().print(4, 3);
		System.out.println("对应特征向量矩阵：");
		m.eig().getV().print(4, 3);
		System.out.println("分量维数："+m.getColumnDimension());
		//验证矩阵一致性
		ProMatrix M=new ProMatrix(m);
		if(M.getCR()>0.1)//CR不达标则添加CR提示和概率为0的
		{
			resultList.add("判断矩阵一致性指标CR="+f.format(M.getCR())+">0.1,超标！");
			for(int i=0;i<proArray.length;i++)
			{
				proArray[i]=0;
			}
			resultList.add(proArray);
		}
		else//生成场景最大特征值对应的特征向量归一化后的结果；
		{
			proArray=M.getProList();
			resultList.add("计算结果正常,CR="+M.getCR()+",概率值为一维数组结果：");
			resultList.add(proArray);
		}
		return resultList;//计算结果提示+结算概率

	}
	
	
}
