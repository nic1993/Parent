package com.horstmann.violet.application.gui.util.chenzuo.Util;

import java.io.File;

/**
 * Created by geek on 2017/8/15.
 */

public class FileUtil {

	public static String COMPILE_COP_PATH = "/home/KKXFINAL/Test/Coptermaster/obj";
	public static String COMPILE_TIME_PATH = "/home/KKXFINAL/Test/Time/obj";
    public static String REMOTE_TC_PATH ="/home/KKXFINAL/Test/testcase/";
    public static String REMOTE_RS_PATH ="/home/KKXFINAL/Test/result/";
    public static String LOCAL_TARGET_PATH =System.getProperty("user.dir")+"\\src\\main\\java\\com\\horstmann\\violet\\application\\gui\\util\\chenzuo\\File\\";

    public void SetLocalPath(String path){
        LOCAL_TARGET_PATH = path;
    }

    public static boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            return false;
        } else {
            if (file.isFile())
                return deleteFile(fileName);
            else
                return deleteDirectory(fileName);
        }
    }


    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
       
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
           
                return true;
            } else {

                return false;
            }
        } else {

            return false;
        }
    }


    public static boolean deleteDirectory(String dir) {
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            return false;
        }
        boolean flag = true;
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                flag = FileUtil.deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
            else if (files[i].isDirectory()) {
                flag = FileUtil.deleteDirectory(files[i]
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            return false;
        }
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }
}
