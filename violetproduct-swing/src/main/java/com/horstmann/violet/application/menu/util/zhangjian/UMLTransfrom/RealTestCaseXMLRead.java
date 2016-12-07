package com.horstmann.violet.application.menu.util.zhangjian.UMLTransfrom;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import com.horstmann.violet.application.menu.util.zhangjian.Database.AbstractTestCase;
import com.horstmann.violet.application.menu.util.zhangjian.Database.DataBaseUtil;
import com.horstmann.violet.application.menu.util.zhangjian.Database.RealProcess;
import com.horstmann.violet.application.menu.util.zhangjian.Database.RealTestCase;

public class RealTestCaseXMLRead {
	private   List<RealProcess> proList=new ArrayList<RealProcess>();

	private  List<RealTestCase> rtcList= new ArrayList<RealTestCase>();
	
	public RealTestCaseXMLRead(String url){//,int count){
		getInformationFormXML(url);//,count);
	}
	/**
	 * 解析实例化测试用例的XML文件
	 * @param url 实例化测试用例文件的URL
	 * @param count 数据库中存储的实例化测试用例的process的个数
	 */
	
	private  void getInformationFormXML(String url ) {//,int count
		int count;
		List<RealProcess> list1=DataBaseUtil.getAllProcess("select * from real_process order by pid desc");
		if(list1==null||list1.isEmpty()){
			count=0;
		}
		else{
			count=list1.get(0).getPid();
		}
		Document d =XMLUtils.load(url);
		Element root=d.getRootElement();
//		System.out.println(root.getName());
		//获取所有的测试用例序列
		List<Element> testCaseList =root.elements();	
//		System.out.println(testCaseList.size());
		int i=count+1;//该值设置为数据库测试用例中的count(testCase); i用于插入数据库real_process
        int j;
		List<RealTestCase> list2=DataBaseUtil.getAllRealTestCase("select * from real_testcase order by id desc");
		if(list2==null||list2.isEmpty()){
			j=0;
		}
		else{
			j=list1.get(0).getPid();
		}
		
		for(Element tc:testCaseList){
			//获取一个测试用例中的所有信息
			List<Element> pList=tc.elements();
			String router="";
			RealTestCase rtc =new RealTestCase();
			for(Element process:pList){
				RealProcess p =new RealProcess();
				String operation =process.elementText("operation");
				String input=process.elementText("input");
//				System.out.println(operation+"："+input);
				p.setPid(i);
			
				p.setOperation(operation);
				p.setInputInfo(input);
				if(pList.get(pList.size()-1)==process){
					router=router+i;
				}else{
					router=router+i+",";
				}
				i++;
				proList.add(p);
			}
			rtc.setName("realTestCase"+j++);
			rtc.setRealTestRouter(router);
			rtcList.add(rtc);
//			System.out.println(router);
		}
		DataBaseUtil.insert2RealProcess(proList, "insert into real_process(pid,pname,input_Info) values(?,?,?)");
		DataBaseUtil.insert2RealTestCase(rtcList, "insert into real_testcase(id,name,RealTestRoute) values(?,?,?)");
//		System.out.println("中间状态的长度"+proList.size());
//		System.out.println("测试用例个数："+rtcList.size());
	}

	public List<RealProcess> getProList() {
		return proList;
	}
	public List<RealTestCase> getRtcList() {
		return rtcList;
	}	
}
