package com.horstmann.violet.application.gui.util.chenzuo.Controller;

import com.horstmann.violet.application.gui.util.chenzuo.Bean.IPDeploy;
import com.horstmann.violet.application.gui.util.chenzuo.Bean.IPNode;
import com.horstmann.violet.application.gui.util.chenzuo.Bean.Pair;
import com.horstmann.violet.application.gui.util.chenzuo.Service.HandelService;
import com.horstmann.violet.application.gui.util.chenzuo.Service.PreConnService;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * MutliThread with Socket and Scp
 *
 * @author geek
 */
public class Controller {

    private static Logger logger = Logger.getLogger(Controller.class);

    private static long MAX_FILE_SIZE = 20 * 1024 * 1024;
    // deploy
    private static IPDeploy IP_TYPE_DEPLOY = new IPDeploy();
    // thread pool
    private static ExecutorService executorService = Executors.newCachedThreadPool();

    //connect by scp
    private static boolean preCon = true;

    // deploy and handle
    private static void handleMapping(Pair<String, File> data) {

        if (data == null) {
            logger.debug("please choose file to send!");
        }
        String type = data.getFirst();
        File[] files = {data.getSecond()};
        //big testcase deply to 2 servers
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
        return null;
    }

    public static void execute(String type, int num, File[] file) {

        //pre start
        List<IPNode> nodes;
        int i = 0;
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
                executorService.submit(new HandelService(node, file[i]));
                i++;
            }
        }
    }


    public static void Close() {
        // close
        executorService.shutdown();
    }

    public static void Run(Pair<String, File> data, boolean p) {
        preCon = p;
        Run(data);
    }

    public static void Run(Pair<String, File> data) {
        try {
            // deploy, distribute and accept
            handleMapping(data);

        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            Close();
        }
    }
}
