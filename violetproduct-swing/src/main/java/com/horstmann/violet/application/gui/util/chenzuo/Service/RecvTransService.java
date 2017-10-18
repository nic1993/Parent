package com.horstmann.violet.application.gui.util.chenzuo.Service;


import org.apache.log4j.Logger;

import com.horstmann.violet.application.gui.util.chenzuo.Bean.IPNode;
import com.horstmann.violet.application.gui.util.chenzuo.Controller.Controller;
import com.horstmann.violet.application.gui.util.chenzuo.Util.FileUtil;
import com.horstmann.violet.application.gui.util.chenzuo.Util.ScpClientUtil;

import java.io.File;
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
            
            System.out.println(fileName+" - "+fileName1);
            
            long l = System.currentTimeMillis();
            scpclient.getFile(FileUtil.REMOTE_RS_PATH + fileName, FileUtil.LOCAL_TARGET_PATH);
            
            File file=new File(FileUtil.LOCAL_TARGET_PATH+fileName);
            file.renameTo(new File(FileUtil.LOCAL_TARGET_PATH+fileName1));
            
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
    public Object call() throws Exception {
        recvRSFile();
        return null;
    }
}
