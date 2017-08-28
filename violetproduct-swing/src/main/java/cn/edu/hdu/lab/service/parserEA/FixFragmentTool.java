package cn.edu.hdu.lab.service.parserEA;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import cn.edu.hdu.lab.dao.uml.DiagramsData;
import cn.edu.hdu.lab.dao.uml.Fragment;
import cn.edu.hdu.lab.dao.uml.Message;
import cn.edu.hdu.lab.dao.uml.Operand;
import cn.edu.hdu.lab.dao.uml.REF;
import cn.edu.hdu.lab.dao.uml.SDRectangle;
public class FixFragmentTool {
	// <不同图元信息所属ID（组合片段ID，引用ID，对象ID，消息ID）、 消息（组合片段，引用，对象，消息）坐标>    成对存放
	//存放所有子顺序图中图元坐标消息
	public static HashMap<String, SDRectangle> rectangleById = new HashMap<>();	
	
	
	// <组合片段ID、所包含操作域坐标信息字符串> 成对存放
	//存放所有子顺序图组合片段下操作域的坐标信息字符串（该字符串待解析）
	public static HashMap<String, String> xrefValueById = new HashMap<>();
	
	/**
	 * 引用在消息之间的相对位置
	 * @param ref
	 * @param diagramsData
	 * @return
	 */
	public static int refIndexInDiagram(REF ref, DiagramsData diagramsData) {
		int index = 0;
		double refTop = ref.getRectangle().getTop();
		for(Message message : diagramsData.getMessageArray()) {
			if (refTop > message.getPointY()) {
				index ++;
			} else {
				break;
			}
		}
		return index;
	}
	
	/**
	 * 直系引用在组合片片段之间的想对位置
	 * @param ref
	 * @param diag
	 * @return
	 */
	public static int refIndexBetweenFragsInDiagram(REF ref, DiagramsData diag) {
		int Findex = 0;
		
		for(Fragment frag:diag.getFragmentArray())
		{
			if(ref.getRectangle().getTop()>frag.getRectangle().getTop())
			{
				Findex ++;	
			}
			else
			{
				break;
			}
		}
		return Findex;
	}
	
	public static double pointYFromValueString(String value) {
		if (value.contains("PtStartY=-")) {
			return Double.parseDouble(value.split("PtStartY=-")[1].split(";PtEndX=")[0]);
		}
		return 0;
	}
	public static String handleFromTimeConstraint(String str)
	{
		if(str!=null&&str.contains("DCBMGUID"))//含有起始状态的时间约束
		{
			//System.out.println("时间约束：：：：：：："+str);
			//System.out.println(str.split("DCBM=")[1].split(";DCBM")[0]);
			return str.split("DCBM=")[1].split(";DCBM")[0];
		}		
		return null;		
	}
	//获取一张顺序图下所有信息（组合片段，引用，对象，消息）的坐标
	public static SDRectangle rectangleFromValueString(String value) {
		double l = 0,t = 0,r = 0,b = 0;
		if (value.contains("Left")) {
			//解析字符串
			String[] strs = value.split(";");
			for(String str : strs) {
				if (str.contains("Left")) {
					l = Double.parseDouble(str.split("Left=")[1]);
					continue;
				} else  if (str.contains("Top")) {
					t = Double.parseDouble(str.split("Top=")[1]);
					continue;
				} else  if (str.contains("Right")) {
					r = Double.parseDouble(str.split("Right=")[1]);
					continue;
				} else  if (str.contains("Bottom")) {
					b = Double.parseDouble(str.split("Bottom=")[1]);
					continue;
				}
			}
		} 
		
		return new SDRectangle(l, t, r, b);
	}


	/*
	 * 通过组合片段的ID，找到该组合片段对应的坐标信息；
	 * 然后通过该组合片段ID找到对应的操作域坐标信息，对其进行解析计算。
	 */
	public static void operandRectangle(Fragment fragment) {
		SDRectangle fatherRectangle = FixFragmentTool.rectangleById.get(fragment.getId());
		if(fragment.getOperands().size()==1)
		{
			fragment.getOperands().get(0).setRectangle(fatherRectangle);
		}
		else //计算各个操作域的坐标，并将其进行赋值
		{
			String value = xrefValueById.get(fragment.getId());
			String[] strs = value.split(";Name=");
			
			ArrayList<String> nameList = new ArrayList<>();
			ArrayList<Double> sizeList = new ArrayList<>(); //存放的操作域高度是组合片段上下两条线的高度差
			for(String str: strs) {
				//条件;Size=100;~~~~~~~~
				if (str.contains(";Size=")) {
					nameList.add(str.split(";Size=")[0]);
					//System.out.println(str.split(";Size=")[1].split(";GUID=")[0]);
					sizeList.add(Double.parseDouble(str.split(";Size=")[1].split(";GUID=")[0].split(";")[0 ]));				
				}
			}
			
			for(Operand oper:fragment.getOperands())
			{
				int index = 0; //下标
				double sumH = 0;//高度和
				for(int i = 0; i < nameList.size(); i++) 
				{
					sumH += sizeList.get(i);
					if(nameList.get(i).equals(oper.getCondition())){
							index = i;
							break;
						}
					}
				
				//获取的是组合片段下操作域的上下的绝对高度
				SDRectangle rectangle = new SDRectangle(fatherRectangle); //继承了四条边的数据，修改上高下底
				
				if (index == 0) {//是最后一个 只需要修改top
					rectangle.setTop(rectangle.getBottom()-sizeList.get(0));
					
					
				} else if (index == nameList.size() - 1){//是第一个 只需要修改bottom
					rectangle.setBottom(rectangle.getTop()+sizeList.get(index));
					
				} else {//是中间的一个 
					rectangle.setTop(rectangle.getBottom()-sumH);
					rectangle.setBottom(rectangle.getTop()+sizeList.get(index));
				}
				oper.setRectangle(rectangle);
				
			}
		}
	} 
	

	
	
	// 根据坐标修正 这张图的 fragment 之间的嵌套关系  +  fragment与ref之间的嵌套关系 
	//并将其嵌套保存起来
	@SuppressWarnings("unchecked")
	public static void fixFragmentsOfOneDiagram(DiagramsData diagramData,ArrayList<Fragment> allFrags) 
	{
		ArrayList<Fragment> fragments = diagramData.getFragmentArray();		
		Collections.sort(fragments, new SortByTopWithFrag()); //对其进行排序,高度由小到大，组合片段从上到下
		for(Fragment frag:fragments)
		{
			Collections.sort(frag.getOperands(), new SortByTopWithOper()); //对其进行排序,高度由小到大，组合片段从上到下
		}
		
		ArrayList<REF> refs = diagramData.getRefArray();
		Collections.sort(refs, new SortByTopWithRef());//对引用片段进行排序
	/*	System.out.println("排序后的引用：");
		for(REF r:refs)
		{
			System.out.println(r.toString());
		}*/
		
		//System.out.println("***********子图名称："+diagramData.getName());
		//System.out.println("排序后的组合片段：");
		/*if(fragments.size()>0)
		{
			for(Fragment f:fragments)
			{
				System.out.println(f.getName());
			}
			
		}
		System.out.println("排列后的引用片段：");
		if(refs.size()>0)
		{
			for(REF r:refs)
			{
				System.out.println(r.getRefName());
			}
			
		}*/
		
		//以下两种设置不可颠倒

//设置   引用片段ref 所属操作的ID***************************************************************
//并将其加入组合片段操作下
		
		for (int i = 0; i < refs.size(); i++) 
		{			
			REF ref = refs.get(i);			
			int flag=0;
			for (int j = fragments.size() - 1; j >= 0; j--) 
			{				
				Fragment fragmentJ = fragments.get(j);
				for(int k=fragmentJ.getOperands().size()-1;k>=0;k--)
				{
					Operand oper=fragmentJ.getOperands().get(k);
					if (rectangleI_in_rectangleJ(ref.getRectangle(), oper.getRectangle())) {						
						ref.setInFragFlag(true);
						ref.setInID(oper.getId());   
						ref.setInName(oper.getCondition());
						oper.setRef(ref);
						refs.remove(ref);
						i--;//修正变动的索引
						flag=1;
						break;
					}
				}
				if(flag==1)
				{
					break;
				}
			}
		}
	
//根据坐标修正组合片段的嵌套的情况
		for (int i = 1; i < fragments.size(); i++) {
			int flag=0;
			Fragment fragmentI = fragments.get(i);
			for (int j = i - 1; j >= 0; j--) {
				Fragment fragmentJ = fragments.get(j);
				for(int k=fragmentJ.getOperands().size()-1;k>=0;k--)
				{
					Operand oper=fragmentJ.getOperands().get(k);
					if (rectangleI_in_rectangleJ(fragmentI.getRectangle(), oper.getRectangle())) {
//设置组合片段所属操作的ID*********************************************************************
						fragmentI.setInID(oper.getId());
						oper.getFragmentIDs().add(fragmentI.getId());//加入所含最外层组合片段的id
						oper.getFragments().add(fragmentI);
						oper.setHasFragment(true);
						fragmentI.setEnFlag(true);
						flag=1;
						break;
					}
				}
				if(flag==1)
				{
					break;
				}			
			}
		}
		

		
		//去除嵌套的组合片段  
		//i--这样写是为了改变索引，避免报出并发修改异常错误)
		for(int i=0;i<fragments.size();i++)
		{
			if(fragments.get(i).isEnFlag())
			{
				allFrags.remove(fragments.get(i));
				fragments.remove(i);
				i--;
			}
		}
	}

	private static boolean rectangleI_in_rectangleJ(SDRectangle rectangleI, SDRectangle rectangleJ) {
		if (rectangleI.getTop() < rectangleJ.getTop()) {
			return false;
		}
		if (rectangleI.getLeft() < rectangleJ.getLeft()) {
			return false;
		}
		if (rectangleI.getBottom() > rectangleJ.getBottom()) {
			return false;
		}
		if (rectangleI.getRight() > rectangleJ.getRight()) {
			return false;
		}
		return true;
	}
	
	//实现一个组合片段的比较器
		@SuppressWarnings("rawtypes")
		static class SortByTopWithFrag implements Comparator {
			 public int compare(Object o1, Object o2) {
				 Fragment f1 = (Fragment) o1;
				 Fragment f2 = (Fragment) o2;
			  return f1.getRectangle().getTop() > f2.getRectangle().getTop() ? 1 : -1;
			 }
		}
		//实现一个操作区域的比较器
		@SuppressWarnings("rawtypes")
		static class SortByTopWithOper implements Comparator {
			 public int compare(Object o1, Object o2) {
				 Operand oper1 = (Operand) o1;
				 Operand oper2 = (Operand) o2;
			  return oper1.getRectangle().getTop() > oper2.getRectangle().getTop() ? 1 : -1;
			 }
		}
		//实现一个引用片段的比较器
		@SuppressWarnings("rawtypes")
		static class SortByTopWithRef implements Comparator {
			 public int compare(Object o1, Object o2) {
				 REF r1 = (REF) o1;
				 REF r2 = (REF) o1;
			  return r1.getRectangle().getTop() > r2.getRectangle().getTop() ? -1 : 1;
			 }
		}
}
