package com.horstmann.violet.application.gui.util.chenzuo.Service;


import org.apache.log4j.Logger;

import com.horstmann.violet.application.gui.util.chenzuo.Bean.IPNode;
import com.horstmann.violet.application.gui.util.chenzuo.Controller.Controller;
import com.horstmann.violet.application.gui.util.chenzuo.Util.FileUtil;
import com.horstmann.violet.application.gui.util.chenzuo.Util.ScpClientUtil;

import java.io.File;
import java.util.Date;
import java.util.concurrent.Callable;

/**
 * Created by geek on 2017/8/13.
 */
public class RecvTransService implements Callable {

    private static Logger logger = Logger.getLogger(Controller.class);

    private static ScpClientUtil scpclient;
    private IPNode node;
    private String id;

    public RecvTransService(IPNode node, String id) {
        scpclient = new ScpClientUtil(node.getIp());
        this.node = node;
        this.id = id;
    }

    private void recvRSFile() {
    	
    	synchronized(RecvTransService.class){
    		String fileName = "result_" + node.getType() + "_" + id + ".txt";
            String fileName1 = "result_"+node.getIp().split("\\.")[3]+"_" + node.getType() + "_" + id + ".txt";
            
            long l = System.currentTimeMillis();
            int flag = 0;
            for(;;){
            	try {
            		scpclient.getFile(FileUtil.REMOTE_RS_PATH + fileName, FileUtil.LOCAL_TARGET_PATH);
				} catch (Exception e) {
					// TODO: handle exception
					flag = 1;
				}
            	if (flag == 0) {
					break;
				}
            }
            
            System.out.println("before "+ node.getIp().split("\\.")[3] + " " + fileName);
            
            for(;;){
            	File file=new File(FileUtil.LOCAL_TARGET_PATH+fileName);
                file.renameTo(new File(FileUtil.LOCAL_TARGET_PATH+fileName1));
                
                File newfile=new File(FileUtil.LOCAL_TARGET_PATH+fileName1);
                if(newfile.exists()){
                	break;
                }
//                System.out.println(FileUtil.LOCAL_TARGET_PATH+fileName+" - - "+FileUtil.LOCAL_TARGET_PATH+fileName1);
            }
            
            System.out.println("after "+ node.getIp().split("\\.")[3] + " " + new File(FileUtil.LOCAL_TARGET_PATH+fileName1).getName());
            
            logger.debug(node.getIp()+" file " + id + " get ok,cost time is:" + (System.currentTimeMillis() - l) + " ms");

            //delete all files
//            while(true){
//            	File file1=new File(FileUtil.LOCAL_TARGET_PATH+fileName1);
//            	if(file1.exists()){
//            		scpclient.execute("rm -rf "+FileUtil.REMOTE_RS_PATH +"*");
//            		break;
//            	}
//            }
            
    	}
    	
    }

    public static void close(){
        scpclient.close();
    }

    @Override
    public Object call() {
    	try {
    		recvRSFile();
		} catch (Exception e) {
			// TODO: handle exception
		}
        
        return null;
    }
}
