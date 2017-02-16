package cn.edu.hdu.lab.service.sd2tmc;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import cn.edu.hdu.lab.config.StaticConfig;
import cn.edu.hdu.lab.dao.interfacedao.InterfaceIsogenySD;
import cn.edu.hdu.lab.dao.interfacedao.InterfaceUCRelation;
import cn.edu.hdu.lab.service.interfaces.Work;
import cn.edu.hdu.lab.service.parser.InvalidTagException;

//测试类
public class FuncTest {

	//测试：调用接口
	public static void main(String[] args) throws InvalidTagException, IOException {

		Work worker=new WorkImpl();
		
		worker.transInitial(StaticConfig.umlPath);	 //解析UML模型的XML文件
		Map<String, List<InterfaceUCRelation>> UCRMap=worker.provideUCRelation(); //获取用例执行顺序关系
		
		List<InterfaceIsogenySD> IISDList=worker.provideIsogencySD();//获取用例场景信息
		
		//根据界面提供的每个用例的用例执行顺序关系填写的矩阵集合或者每个用例的场景填写的矩阵集合，计算概率
		//List 第一个值为计算结果提示，第二个结果为计算概率
		//List list=worker.calculateProb(proMatrixList); //带入界面填写的矩阵数组集合，返回计算结果
		
		//一致性验证
		//List：第一个结果boolean：True通过，False不通过；第二个结果：具体的验证结果信息。
		List verList=worker.transVerify(); 
		
		//界面将计算的概率结果保存在相应的数据结构的概率成员变量中，调用下面方法可以赋场景的概率值
		worker.assignmentPro(IISDList);
		
		//模型转换；带入用例执行顺序关系作为参数    //里面有个小问题，17年春期来了再搞（需要查理论知识讨论、重新整理模型转换思路）
		worker.transToMarckov(UCRMap);
		
		//worker.probabilityTest();//概率查询，不要调用这个接口；
		
		//控制着数据结构写在同一个xml文件里面。
		//
		worker.writeMarkov(StaticConfig.mcPathPrefix);
	}
	
}
