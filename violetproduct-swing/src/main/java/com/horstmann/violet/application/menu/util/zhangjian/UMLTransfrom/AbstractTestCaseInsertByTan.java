package com.horstmann.violet.application.menu.util.zhangjian.UMLTransfrom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2.Test_Id;
import com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2.Test_List;
import com.horstmann.violet.application.menu.util.zhangjian.Database.AbstractState;
import com.horstmann.violet.application.menu.util.zhangjian.Database.AbstractTestCase;
import com.horstmann.violet.application.menu.util.zhangjian.Database.DataBaseUtil;

//by tan
public class AbstractTestCaseInsertByTan {
	//zhangjian用于接收wqq的信息
	private List<AbstractTestCase> abstractTestCaseList =new ArrayList<AbstractTestCase>();
	String name="name";
	int num ;
	
	private AbstractTestCase testCase=null;
	
    //将 wqq的abstractTestCase--->zhangjian的abstractTestCase
	public void w2zAbstractTestCaseExchange(String xml){
		List<AbstractTestCase> list1=DataBaseUtil.getAllAbstractTestCase("select * from abstract_testcase order by id desc");
		if(list1==null||list1.isEmpty()){
			num=0;
		}
		else{
			num=list1.get(0).getId();
		}
		List<String>list=new Test_Id().exchangeTestCase(xml);//强xml转化为我们要的抽象测试的序列
		Iterator<String> iterator=list.iterator();
		while(iterator.hasNext()){
			num++;
			testCase=new AbstractTestCase();
			testCase.setId(num);
			testCase.setName(name+num);
			testCase.setTestRouter((String)iterator.next());
		    abstractTestCaseList.add(testCase);
		}
		//将zhangjian的abstractTestCase存储到数据库中
		DataBaseUtil.insert2AbstractTestCase(abstractTestCaseList, "insert into abstract_testcase(id,name,TestRoute) values(?,?,?)",list1);
	}
	
}
