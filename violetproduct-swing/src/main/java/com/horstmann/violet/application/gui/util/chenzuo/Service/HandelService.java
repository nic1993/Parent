package com.horstmann.violet.application.gui.util.chenzuo.Service;

import com.horstmann.violet.application.gui.util.chenzuo.Bean.Constants;
import com.horstmann.violet.application.gui.util.chenzuo.Bean.IPNode;
import com.horstmann.violet.application.gui.util.chenzuo.Util.FileUtil;
import com.horstmann.violet.application.gui.util.chenzuo.Util.ScpClientUtil;
import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class HandelService implements Callable {

    private Logger logger = Logger.getLogger(this.getClass());

    // server's IP and Port
    protected IPNode node;

    private Socket socket = null;
    private ScpClientUtil scpclient;

    private ResultService resultService;

    //executor to deal with receive
    ExecutorService receiveService = Executors.newCachedThreadPool();

    // Stream based on socket
    DataOutputStream dos = null;
    DataInputStream dis = null;

    // testcases' Files
    File[] files = null;

    public HandelService(IPNode node, File files) {
        this(node, new File[]{files});
    }

    public HandelService(IPNode node, File[] files) {
        this.node = node;
        this.files = files;
        scpclient = new ScpClientUtil(node.getIp());
        resultService = new ResultService(node.getType());
    }

    // connect socket
    public boolean connection() {
        //connect socket
        try {
            socket = new Socket(node.getIp(), Constants.PORT);
            if (socket != null) {
                logger.debug("connection " + node.getIp() + " success");
                return true;
            }
        } catch (Exception e) {
            logger.error("fail to connect server");
        }
        return false;
    }

    // send xml files
    public void send() {
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            for (File f : files) {
                //send file
                scpclient.putFile(f.getAbsolutePath(), f.getName(), FileUtil.REMOTE_TC_PATH, null);
                //send filename
                dos.write(f.getName().getBytes());
                dos.flush();
                logger.debug("success send");
//                logger.debug("success send file:"+f.getName());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // receive result
    public void recv() {
        int bufferSize = 500;
        byte[] buf = new byte[bufferSize];
        String data = "";
        int fIndex =1;
        try {
            dis = new DataInputStream(socket.getInputStream());
            while ( dis.read(buf) != -1) {
                data = new String(buf, "UTF-8").trim();
                
                System.out.println("----"+data);
                
                Arrays.fill(buf, (byte) 0);
                logger.debug("@@@@@@receive data:" + data);
                //get index of result file and convert
                if (data.contains("index")) {
                    String index = data.split("#")[1].replace("exit", "");
                    fIndex++;
                    receiveService.submit(new RecvTransService(node,index));
//                  logger.debug(receiveService.take().get());
                }
                if ("exit".equals(data)) {
                    //finish work
                    Constants.ISFINISH.set(true);
                    logger.debug("success receive all files");
                    break;
                }
            }
        } catch (Exception e) {
            logger.debug("failed receive , cause by " + e.getCause());
        }
    }

    // close socket
    public void close() {
        try {
            node.setBusy(false);
            dos.close();
            dis.close();
            socket.close();
            scpclient.close();
            logger.debug("socket close");
        } catch (IOException e) {
            logger.error("close socket error ,cause by " + e.getMessage());
        }
    }

    public Object call() throws Exception {
        // 1.create connection
        boolean isCon = connection();
        if (isCon) {
            // 2.send data
            send();

            // 3.receive file
            recv();

            // 4.close socket
            close();
        }
        return null;
    }
}
