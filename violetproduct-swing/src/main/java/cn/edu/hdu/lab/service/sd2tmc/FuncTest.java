 package cn.edu.hdu.lab.service.sd2tmc;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.edu.hdu.lab.config.StaticConfig;
import cn.edu.hdu.lab.dao.interfacedao.InterfaceIsogenySD;
import cn.edu.hdu.lab.dao.interfacedao.InterfaceSD;
import cn.edu.hdu.lab.dao.interfacedao.InterfaceUCRelation;
import cn.edu.hdu.lab.service.interfaces.Work;

//测试类
public class FuncTest {

	//测试：调用接口
	public static void main(String[] args) throws Throwable {

		Work worker=new WorkImpl();
		
		//第一步：解析EA画出的UML模型的XML文件
		//worker.transInitial(StaticConfig.umlPath);		
		//第一步：解析HDU601实验室平台画出UML模型的XML文件
		worker.transInitialHDU(StaticConfig.umlPathHDU);
		
		
		//第二步：获取用例执行顺序关系
		Map<String, List<InterfaceUCRelation>> UCRMap=worker.provideUCRelation(); 
		System.out.println("用例执行顺序关系;");
		for(Entry<String,List<InterfaceUCRelation>> en:UCRMap.entrySet())
		{
			//System.out.println(en.getKey());
			for(InterfaceUCRelation iur:en.getValue())
			{
				System.out.println(iur.getUCRelation());
			}
			
		}
	
		//第三步：获取用例场景信息
		List<InterfaceIsogenySD> IISDList=worker.provideIsogencySD();		
		
		//List 第一个值为计算结果提示，第二个结果为计算概率的一维数组
		/*
		 * 测试数据
		 */
		/*List<double[][]> proMatrixList=new ArrayList<double[][]>();
		double[][] a={{1,0.5,0.25},{2,1,1.25},{4,0.8,1}};
		double[][] b={{1,0.5,0.25},{2,1,1.25},{4,0.8,1}};
		proMatrixList.add(a);
		proMatrixList.add(b);*/
		
		//第四步：根据界面提供的每个用例的用例执行顺序关系填写的矩阵集合或者每个用例的场景填写的矩阵集合，计算概率
		//List<Object> list=worker.calculateProb(proMatrixList); //带入界面填写的矩阵数组集合，返回计算结果
		/*
		System.out.println(list.get(0));
		for(double pro:(double[])list.get(1))
		{
			System.out.println(pro);
		}*/
		
		for(InterfaceIsogenySD IISD:IISDList)
		{
			
			for(InterfaceSD ISD:IISD.getISDList())
			{
				ISD.setPro(1.0);
			}
		}
		
		//第五步：界面将计算的概率结果保存在相应的数据结构的概率成员变量中，调用下面方法可以赋场景的概率值
		worker.assignmentPro(IISDList);
		
		/* 第六步：
		 * 一致性验证
		 * List：第一个结果boolean：True通过，False不通过；
		 * 第二个结果：具体的验证结果信息。
		 * 第三个结果：用例执行关系邻接矩阵
		 * 第四个结果：初始状态到每个用例的路径
		 * 第五个结果：每个用例到结束状态的路径
		 */
		List<Object> verList=worker.transVerify(); 
		
		if((Boolean)verList.get(0)==true)
		{
			Map<?,?> prePath=(Map<?,?>) verList.get(3);
			Map<?,?> postPath=(Map<?, ?>) verList.get(4);
			
			for(Object en:prePath.keySet())
			{
				System.out.println(en);
				System.out.println(prePath.get(en).toString());
			}
			
			for(Object en:postPath.keySet())
			{
				System.out.println(en);
				System.out.println(postPath.get(en).toString());
			}
		}
		
		/*for(String str : UCRMap.keySet())
		{
			System.out.print(str+"*****");
			for(InterfaceUCRelation IUCR:UCRMap.get(str))
			{
				System.out.print("*****"+IUCR.getUCRelation());
				IUCR.setUCRelProb(0.8);
			}
			System.out.println();
		}*/
		
		
		//第七步：模型转换；带入用例执行顺序关系作为参数    		
		worker.transToMarckov(UCRMap);
		
		//第八步：验证Markov chain的概率归一性和可达性，可用可不用
		worker.probabilityAndReachableTest();//概率查询
		
		//第八步：控制着数据结构写在同一个xml文件里面。****************前缀名需要修改*********
//		worker.writeMarkov(StaticConfig.mcPathPrefixHDU);
	}
	
}
