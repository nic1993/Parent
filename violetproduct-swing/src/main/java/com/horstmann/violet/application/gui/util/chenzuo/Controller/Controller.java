 package com.horstmann.violet.application.gui.util.chenzuo.Controller;

import java.io.File;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.horstmann.violet.application.gui.util.chenzuo.Bean.Constants;
import com.horstmann.violet.application.gui.util.chenzuo.Bean.IPDeploy;
import com.horstmann.violet.application.gui.util.chenzuo.Bean.IPNode;
import com.horstmann.violet.application.gui.util.chenzuo.Bean.Pair;
import com.horstmann.violet.application.gui.util.chenzuo.Service.HandelService;
import com.horstmann.violet.application.gui.util.chenzuo.Service.PreConnService;
import com.horstmann.violet.application.gui.util.chenzuo.Service.ResultService;


/**
 * MutliThread with Socket and Scp
 *
 * @author geek
 */
public class Controller {

    private static Logger logger = Logger.getLogger(Controller.class);

    private static long MAX_FILE_SIZE = 5 * 1024 * 1024;
    // deploy
    private static IPDeploy IP_TYPE_DEPLOY = new IPDeploy();
    // thread pool
    private static ExecutorService executorService = Executors.newCachedThreadPool();
    
    public static List<FutureTask<Integer>> handFutureList=new ArrayList<>();
    
    public static int executeNum=1;
    public static int offsetTestCaseId=0;
    public static String offsetIP=null;
    
    private static ResultService resultService;
    
//	private static ThreadPoolExecutor executorService ;

//    private static void printException(Runnable r, Throwable t){
//		if (t == null && r instanceof Future<?>) {
//			try {
//				Future<?> future = (Future<?>) r;
//				if (future.isDone())
//					future.get();
//			} catch (CancellationException ce) {
//				t = ce;
//			} catch (ExecutionException ee) {
//				t = ee.getCause();
//			} catch (InterruptedException ie) {
////				Thread.currentThread().interrupt(); // ignore/reset
//			}
//		}
//		if (t != null){
//			if(t.getMessage().contains("TestCaseException")){
//				TestCaseReportTabbedPanel.threadexceptionstate=1;
//			}
//			logger.error("123564"+t.getMessage(), t);
//		}
//    }
    
    //connect by scp
    private static boolean preCon = true;

    // deploy and handle
    private static void handleMapping(Pair<String, File> data){

        if (data == null) {
            logger.debug("please choose file to send!");
        }
        String type = data.getFirst();
        File[] files = {data.getSecond()};
        //big testcase deply to 2 servers
        System.out.println("files[0].length() > MAX_FILE_SIZE  "+(files[0].length() > MAX_FILE_SIZE));
        if (files[0].length() > MAX_FILE_SIZE) {
            //1.spilt file
            files = fileSpilt(data);
            //2.choose server
            execute(type, 2, files);
        } else {
            execute(type, 1, files);
        }
    }

    /////to do
    private static File[] fileSpilt(Pair<String, File> data) {
    	
    	TestFileSpilt spilt=new TestFileSpilt();
    	
        return spilt.FileSpilt(data.getSecond());
    }

    public static void execute(String type, int num, File[] file){

        //pre start
        List<IPNode> nodes;
        int i = 0;
        
        executeNum=num;
        
        handFutureList=new ArrayList<>();
        
        if ((nodes = IP_TYPE_DEPLOY.findNodeFree(num)) != null) {
            for (IPNode node : nodes) {
                node.setType(type);
                
                if (preCon) {
                    executorService.submit(new PreConnService(node));
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                
                Callable<Integer> handCallable=new HandelService(node, file[i]);
                
//                executorService.submit(handCallable);
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                handCallable.call();
                
                FutureTask<Integer> handFuture=new FutureTask<>(handCallable);
                handFutureList.add(handFuture);
                executorService.submit(handFuture);
                
//              handfuture.get();
                
                i++;
                if(i==1){
                	resultService = new ResultService(node.getType());
                }
                else if(i==2){
                	offsetIP=node.getIp().split("\\.")[3];
                }
            }
        }
    }


    public static void Close() {
        // close
//        executorService.shutdown();
    	preCon=true;
    }

//    public static void Run(Pair<String, File> data, boolean p) {
//        preCon = p;
//        Run(data);
//    }

    public static void Run(Pair<String, File> data){
    	ResultService.list.removeAll(ResultService.list);
    	Constants.ISFINISH.set(false);
    	
//    	executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
//                60L, TimeUnit.SECONDS,
//                new SynchronousQueue<Runnable>()) {
//
//    		protected void afterExecute(Runnable r, Throwable t) {
//    			super.afterExecute(r, t);
//    			printException(r, t);
//    		}
//    	};
    	
    	
        try {
            // deploy, distribute and accept
        	System.out.println("--------------------*******************");
            handleMapping(data);
            System.out.println("++++++++++++++++++++++++---------------");
        } catch (Exception e) {
            logger.error(e.getMessage());
		} finally {
            Close();
        }
    }
    
    public static boolean Ready(int needNodeNum){
    	
    	List<IPNode> nodes;
    	int successnum=0;
    	
    	logger.debug("Controller Ready Start");
		if ((nodes = IP_TYPE_DEPLOY.findNodeFree(needNodeNum)) != null) {
			for (IPNode node : nodes) {
				
				try {
					executorService.submit(new PreConnService(node));
					TimeUnit.SECONDS.sleep(1);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Socket socket = null;
				try {
		            socket = new Socket(node.getIp(), Constants.PORT);
		            if (socket != null) {
		                logger.debug("connection " + node.getIp() + " success");
		                successnum++;
		                socket.close();
		                logger.debug(node.getIp()+" socket close");
		            }
		        } catch (Exception e) {
		            logger.error(node.getIp()+" fail to connect server");
		        }
				
			}
			
			for (IPNode node : nodes) {
				node.setBusy(false);
			}
		} 
		logger.debug("Controller Ready End");
		
		return successnum==needNodeNum?true:false;
    }
}
