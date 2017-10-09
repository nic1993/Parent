package com.horstmann.violet.application.gui.util.chenzuo.Service;

import com.horstmann.violet.application.gui.util.chenzuo.Bean.Constants;
import com.horstmann.violet.application.gui.util.chenzuo.Bean.TestCase;
import com.horstmann.violet.application.gui.util.chenzuo.Util.FileUtil;
import com.horstmann.violet.application.gui.util.chenzuo.Util.TcConvertUtil;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Created by geek on 2017/8/14.
 */
public class ResultService {

    private Logger logger = Logger.getLogger(this.getClass());

    private ScheduledThreadPoolExecutor scheduledService = new ScheduledThreadPoolExecutor(1);

    public static List<TestCase> list = Collections.synchronizedList(new ArrayList());

    public ResultService(String type) {
        scheduledService.scheduleAtFixedRate(
                new GetResult(type),
                0,
                Constants.PEROID,
                Constants.TIME_TYPE);
    }

    class GetResult implements Runnable {

        private String type;

        @Override
        public void run() {
            readfile();
        }

        public GetResult(String type) {
            this.type = type;
        }

        public void readfile() {
            File file = new File(FileUtil.LOCAL_TARGET_PATH);
            if (file.isDirectory()) {
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    String fileName = FileUtil.LOCAL_TARGET_PATH + filelist[i];
                        try {
                            list.addAll(TcConvertUtil.buildTestCaseList(type, fileName));
                            FileUtil.delete(fileName);
                            logger.debug("list size:"+list.size());
                            if(Constants.ISFINISH.get()){
                                logger.debug("scheduledService close");
                                scheduledService.shutdown();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                }
            }

        }
    }
    /**
	 * 统计测试用例执行情况
	 */
	public static List<Integer> getResults(List<TestCase> list) {
		// 用于记录成功和失败
		List<Integer> totallist = new ArrayList<Integer>();
		int i = 0;
		int j = 0;
		int size = list.size();
		// 遍历看看成功或者失败的个数统计
		for(TestCase info:list){
			  String str=info.getResult().getResultDetail();
			  if(str.contains("成功")){
				  i++;
			  }
			  else{
				  j++;
			  }
		  }
		totallist.add(i);
		totallist.add(j);
		totallist.add(size);
		return totallist;
	}
	public static List<TestCase> getFail(List<TestCase> list)
	{
		List<TestCase> failelist = new ArrayList<TestCase>();
		// 遍历看看成功或者失败的个数统计
		for(TestCase info:list){
			 String str=info.getResult().getResultDetail();
			  System.out.println(str);
			  if(str.contains("成功")){
			  }
			  else{
				  failelist.add(info);
			  }
		  }
		return failelist;
	}
	public  static List<Integer> getFailType(List<TestCase> list) {
		List<TestCase> faillist = getFail(list);
		List<Integer> countList = new ArrayList<Integer>();
		if(faillist.size() > 0)
		{
			int i = faillist.size();
			int count = 0;
			for(int j = 0;j < i;j++)
			{
				if(faillist.get(j).getResult().getResultDetail().contains("程序执行过程中出现死循环或者抛出异常!"))
				{
					count++;
				}
			}
			countList.add(count);
			countList.add(i);
			return countList;
		}
		else {
			countList.add(0);
			countList.add(0);
			return countList;
		}
		
	}

    public static List<TestCase> getResult() {
        return list;
    }

    public static void main(String[] args) {
        PropertyConfigurator.configure("src/log4j.properties");
        ResultService s = new ResultService("Function");
    }

}
