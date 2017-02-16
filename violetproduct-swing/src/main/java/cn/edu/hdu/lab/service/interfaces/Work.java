package cn.edu.hdu.lab.service.interfaces;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import cn.edu.hdu.lab.dao.interfacedao.InterfaceIsogenySD;
import cn.edu.hdu.lab.dao.interfacedao.InterfaceUCRelation;
import cn.edu.hdu.lab.service.parser.InvalidTagException;

public interface Work {
	//初始化解析UML模型的XML文件
	public void transInitial(String xmlFileName) throws InvalidTagException;
   
	//根据解析信息提供用例执行顺序关系
	public Map<String, List<InterfaceUCRelation>> provideUCRelation();
	
	//返回用例下的场景信息
	public List<InterfaceIsogenySD> provideIsogencySD();
	
	//根据界面提供的每个用例的用例执行顺序关系填写的矩阵集合或者每个用例的场景填写的矩阵集合，计算概率
	//List 第一个值为计算结果提示，第二个结果为计算概率
	public List calculateProb(List<double[][]> proMatrixList);
	
	//计算的场景概率 返回赋值
	public void assignmentPro(List<InterfaceIsogenySD> IISDList);
	
	//验证
	public List transVerify() throws InvalidTagException;
	public void transToMarckov(Map<String, List<InterfaceUCRelation>> UCRMap);
	public void probabilityTest();
	public void writeMarkov(String mcXMLFileName) throws IOException;

}
