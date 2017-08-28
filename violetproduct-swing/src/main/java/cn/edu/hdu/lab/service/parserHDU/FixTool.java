package cn.edu.hdu.lab.service.parserHDU;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import cn.edu.hdu.lab.dao.uml.DiagramsData;
import cn.edu.hdu.lab.dao.uml.Fragment;
import cn.edu.hdu.lab.dao.uml.Message;
import cn.edu.hdu.lab.dao.uml.Operand;
import cn.edu.hdu.lab.dao.uml.REF;
import cn.edu.hdu.lab.dao.uml.SDRectangle;

public class FixTool {
	@SuppressWarnings("unchecked")
	public static void fixFragmentsOfOneDiagram(ArrayList<Fragment> allFrags,DiagramsData dd) 
	{
		ArrayList<Fragment> fragments = allFrags;		
		Collections.sort(fragments, new SortByTopWithFrag()); //对其进行排序,高度由小到大，组合片段从上到下
		for(Fragment frag:fragments)
		{
			Collections.sort(frag.getOperands(), new SortByTopWithOper()); //对其进行排序,高度由小到大，组合片段从上到下
						
		}
		//将引用片段封装进操作域		
		encapsulateOperWithRef(allFrags,dd.getRefArray());		
		//对组合片段的操作域封装消息
		fixMessageInOperand(allFrags,dd.getMessageArray());
		//**********根据坐标修正组合片段的嵌套的情况
		fixFragments(fragments);
		
	}
	@SuppressWarnings("unchecked")
	public static void sortMesses(ArrayList<Message> messageList) {
			Collections.sort(messageList, new SortByYWithMess());		
	}
	@SuppressWarnings("unchecked")
	private static void  encapsulateOperWithRef(ArrayList<Fragment> fragments,ArrayList<REF> refs)
	{
		Collections.sort(refs, new SortByTopWithRef());//对引用片段进行排序
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
		
	}
	/**
	 * 按照坐标，将信息嵌套进对应的操作域
	 * @param allFrags
	 * @param messes
	 */
	private static void fixMessageInOperand(ArrayList<Fragment> allFrags,ArrayList<Message> messes)
	{
		
		for (Message m:messes) {			
			for(int i=allFrags.size()-1;i>=0;i--)
			{
				int f1=0;
				if(m.getPointY()>=allFrags.get(i).getRectangle().getTop()
						&&m.getPointY()<=allFrags.get(i).getRectangle().getBottom())
				{
					for(int j=allFrags.get(i).getOperands().size()-1;j>=0;j--)
					{
						if(m.getPointY()>=allFrags.get(i).getOperands().get(j).getRectangle().getTop()
								&& m.getPointY()<=allFrags.get(i).getOperands().get(j).getRectangle().getBottom())
						{
							
							m.setInFragment(true);
							m.setFragmentId(allFrags.get(i).getId());
							m.setFragType(allFrags.get(i).getType());
							m.setOperandId(allFrags.get(i).getOperands().get(j).getId());		
							allFrags.get(i).getOperands().get(j).getMessages().add(m);
							f1=1;
							break;
						}
					}
				}
				if(f1==1)
				{
					break;
				}
			}
		}
	}
	/**
	 * 根据坐标修正组合片段的嵌套情况
	 * @param fragments
	 */
	private static void fixFragments(ArrayList<Fragment> fragments)
	{
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
			
			//实现一个消息的比较器
			@SuppressWarnings("rawtypes")
			static class SortByYWithMess implements Comparator {
				 public int compare(Object o1, Object o2) {
					 Message m1 = (Message) o1;
					 Message m2 = (Message) o2;
				  return m1.getPointY()> m2.getPointY() ? 1 : -1;
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
