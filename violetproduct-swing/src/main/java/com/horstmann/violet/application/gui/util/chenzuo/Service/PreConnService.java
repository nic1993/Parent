package com.horstmann.violet.application.gui.util.chenzuo.Service;



import java.util.concurrent.Callable;

import com.horstmann.violet.application.gui.util.chenzuo.Bean.IPNode;
import com.horstmann.violet.application.gui.util.chenzuo.Util.ScpClientUtil;



/**
 * Created by geek on 2017/8/13.
 */
public class PreConnService implements Callable<String>{

    private ScpClientUtil scpclient ;

    public PreConnService(IPNode node){
        scpclient = new ScpClientUtil(node.getIp());
    }
    @Override
    public String call() throws Exception {
        return scpclient.preCon();
    }
}
