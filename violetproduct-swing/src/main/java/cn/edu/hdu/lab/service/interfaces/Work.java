package cn.edu.hdu.lab.service.interfaces;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.horstmann.violet.application.gui.MainFrame;
import com.l2fprod.common.demo.Main;

import cn.edu.hdu.lab.dao.interfacedao.InterfaceIsogenySD;
import cn.edu.hdu.lab.dao.interfacedao.InterfaceUCRelation;
import cn.edu.hdu.lab.service.parserEA.InvalidTagException;

public interface Work {
	//初始化解析UML模型的XML文件
	public void transInitial(String xmlFileName) throws InvalidTagException, Exception;
   
	//初始化解读UML模型XML文件
	public void transInitialHDU(String xmlFileName) throws Throwable;
	
	//根据解析信息提供用例执行顺序关系
	public Map<String, List<InterfaceUCRelation>> provideUCRelation();
	
	//返回用例下的场景信息
	public List<InterfaceIsogenySD> provideIsogencySD();
	
	//根据界面提供的每个用例的用例执行顺序关系填写的矩阵集合或者每个用例的场景填写的矩阵集合，计算概率
	//List 第一个值为计算结果提示，第二个结果为计算概率
	public List<Object> calculateProb(List<double[][]> proMatrixList);
	
	//计算的场景概率 返回赋值
	public void assignmentPro(List<InterfaceIsogenySD> IISDList);
	
	//验证
	public List<Object> transVerify() throws InvalidTagException;
	public void transToMarckov(Map<String, List<InterfaceUCRelation>> UCRMap) throws Exception;
	public void probabilityAndReachableTest() throws Exception;
	public void writeMarkov(String mcXMLFileName,MainFrame mainFrame,List<String> seqNames,List<String> ucNames) throws IOException, Exception;

}
