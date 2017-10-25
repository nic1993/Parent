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
    /**
     * 閸掔娀娅庨弬鍥︽閿涘苯褰叉禒銉︽Ц閺傚洣娆㈤幋鏍ㄦ瀮娴犺泛锟�?
     *
     * @param fileName
     *            鐟曚礁鍨归梽銈囨畱閺傚洣娆㈤崥锟�?
     * @return 閸掔娀娅庨幋鎰鏉╂柨娲杢rue閿涘苯鎯侀崚娆掔箲閸ワ拷?锟絘lse
     */
    public static boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("閸掔娀娅庨弬鍥︽婢惰精锟�?:" + fileName + "娑撳秴鐡ㄩ崷顭掔磼");
            return false;
        } else {
            if (file.isFile())
                return deleteFile(fileName);
            else
                return deleteDirectory(fileName);
        }
    }

    /**
     * 閸掔娀娅庨崡鏇氶嚋閺傚洣锟�?
     *
     * @param fileName
     *            鐟曚礁鍨归梽銈囨畱閺傚洣娆㈤惃鍕瀮娴犺泛锟�?
     * @return 閸楁洑閲滈弬鍥︽閸掔娀娅庨幋鎰鏉╂柨娲杢rue閿涘苯鎯侀崚娆掔箲閸ワ拷?锟絘lse
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 婵″倹鐏夐弬鍥︽鐠侯垰绶為幍锟斤拷?锟界懓绨查惃鍕瀮娴犺泛鐡ㄩ崷顭掔礉楠炴湹绗栭弰顖欑娑擃亝鏋冩禒璁圭礉閸掓瑧娲块幒銉ュ灩闂勶拷
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("閸掔娀娅庨崡鏇氶嚋閺傚洣锟�?" + fileName + "閹存劕濮涢敍锟�?");
                return true;
            } else {
                System.out.println("閸掔娀娅庨崡鏇氶嚋閺傚洣锟�?" + fileName + "婢惰精瑙﹂敍锟�?");
                return false;
            }
        } else {
            System.out.println("閸掔娀娅庨崡鏇氶嚋閺傚洣娆㈡径杈Е閿涳拷" + fileName + "娑撳秴鐡ㄩ崷顭掔磼");
            return false;
        }
    }

    /**
     * 閸掔娀娅庨惄顔肩秿閸欏﹦娲拌ぐ鏇氱瑓閻ㄥ嫭鏋冩禒锟�?
     *
     * @param dir
     *            鐟曚礁鍨归梽銈囨畱閻╊喖缍嶉惃鍕瀮娴犳儼鐭惧锟�?
     * @return 閻╊喖缍嶉崚鐘绘珟閹存劕濮涙潻鏂挎礀true閿涘苯鎯侀崚娆掔箲閸ワ拷?锟絘lse
     */
    public static boolean deleteDirectory(String dir) {
        // 婵″倹鐏塪ir娑撳秳浜掗弬鍥︽閸掑棝娈х粭锔剧波鐏忔拝绱濋懛顏勫З濞ｈ濮為弬鍥︽閸掑棝娈х粭锟�?
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);
        // 婵″倹鐏塪ir鐎电懓绨查惃鍕瀮娴犳湹绗夛拷?锟芥ê婀敍灞惧灗閼板懍绗夐弰顖欑娑擃亞娲拌ぐ鏇礉閸掓瑩锟斤拷閸戯拷
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            System.out.println("閸掔娀娅庨惄顔肩秿婢惰精瑙﹂敍锟�?" + dir + "娑撳秴鐡ㄩ崷顭掔磼");
            return false;
        }
        boolean flag = true;
        // 閸掔娀娅庨弬鍥︽婢讹拷?锟借厬閻ㄥ嫭澧嶉張澶嬫瀮娴犺泛瀵橀幏锟�?鐡欓惄顔肩秿
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 閸掔娀娅庯拷?锟芥劖鏋冩禒锟�?
            if (files[i].isFile()) {
                flag = FileUtil.deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
            // 閸掔娀娅庯拷?锟芥劗娲拌ぐ锟�?
            else if (files[i].isDirectory()) {
                flag = FileUtil.deleteDirectory(files[i]
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            System.out.println("閸掔娀娅庨惄顔肩秿婢惰精瑙﹂敍锟�?");
            return false;
        }
        // 閸掔娀娅庤ぐ鎾冲閻╊喖锟�?
        if (dirFile.delete()) {
            System.out.println("閸掔娀娅庨惄顔肩秿" + dir + "閹存劕濮涢敍锟�?");
            return true;
        } else {
            return false;
        }
    }
}
