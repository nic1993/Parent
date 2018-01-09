package com.horstmann.violet.application.gui.util.chenzuo.Service;



import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.horstmann.violet.application.gui.util.chenzuo.Bean.Constants;
import com.horstmann.violet.application.gui.util.chenzuo.Bean.TestCase;
import com.horstmann.violet.application.gui.util.chenzuo.Controller.Controller;
import com.horstmann.violet.application.gui.util.chenzuo.Util.FileUtil;
import com.horstmann.violet.application.gui.util.chenzuo.Util.TcConvertUtil;

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
                	System.out.println(filelist[i]);
                }
     
                
                for (int i = 0; i < filelist.length; i++) {
                	
                	if(!filelist[i].contains("89")&&!filelist[i].contains("93")){
                		continue;
                	}
                	
                    String fileName = FileUtil.LOCAL_TARGET_PATH + filelist[i];
                        try {
                        	System.out.println("fileName: " + fileName);
                        	List<TestCase> testcaselist=TcConvertUtil.buildTestCaseList(type, fileName);
                        	
                        	//IDƫ��
                        	if(Controller.offsetIP!=null&&filelist[i].contains(Controller.offsetIP)){
                        		for(TestCase testCase:testcaselist){
                        			testCase.setTestCaseID(String.valueOf(Integer.parseInt(testCase.getTestCaseID())+Controller.offsetTestCaseId));
                        		}
                        	}
                        	
                            list.addAll(testcaselist);
                            FileUtil.delete(fileName);
                            logger.debug("list size:"+list.size());
//                            if(Constants.ISFINISH.get()){
//                                logger.debug("scheduledService close");
//                                scheduledService.shutdown();
//                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                }
                
            	if(Constants.ISFINISH.get()){
					logger.debug("scheduledService close");
					if(ResultService.list.size() == Controller.mainFrame.getStepFourOperation().length)
					{
						scheduledService.shutdown();
						Controller.isFinish = true;
					}
					
				}
            }

        }
    }

    public static List<Integer> getResults(List<TestCase> testcaselist)
    {
    	List<Integer> lists = new ArrayList<Integer>();
    	
    	int fail = 0;
    	int success = 0;
    	
    	for(int i = 0;i < testcaselist.size();i++)
    	{
    		if(testcaselist.get(i).getResult().contains("成功"))
    		{
    			success++;
    		}
    		else {
				fail++;
			}
    	}
    	
    	
    	lists.add(success);
    	lists.add(fail);
    	lists.add(testcaselist.size());
    	
    	
    	return lists;
    }
    
    
    public static List<Integer> getFailType(List<TestCase> testcaselist)
    {
    	List<Integer> lists = new ArrayList<Integer>();
    	
    	int fail = 0;
    	int failtype = 0;
    	for(int i = 0;i < testcaselist.size();i++)
    	{
    		if(!testcaselist.get(i).getResult().contains("成功"))
    		{
    			fail++;
    			if(testcaselist.get(i).getResult().contains("程序执行过程中出现死循环或者抛出异常!"))
    			{
    				failtype++;
    			}
    		}
    	}
    	
    	lists.add(failtype);
    	lists.add(fail);
    	return lists;
    }
    
    public static List<TestCase> getFail(List<TestCase> testcaselist)
    {
    	List<TestCase> lists = new ArrayList<TestCase>();
    	
    	for(int i = 0;i < testcaselist.size();i++)
    	{
    		if(testcaselist.get(i).getResult().contains("成功"))
    		{
    			
    		}
    		else {
    			lists.add(testcaselist.get(i));
			}
    	}
    	
    	return lists;
    }
    public static List<TestCase> getResult() {
        return list;
    }

	public static void main(String[] args) {
        PropertyConfigurator.configure("src/log4j.properties");
        ResultService s = new ResultService("Function");
    }

}
