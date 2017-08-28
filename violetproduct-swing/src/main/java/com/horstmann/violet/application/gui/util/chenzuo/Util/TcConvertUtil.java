package com.horstmann.violet.application.gui.util.chenzuo.Util;

import com.horstmann.violet.application.gui.util.chenzuo.Bean.*;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 测试用例 操作工具类
 *
 * @author geek
 */
public class TcConvertUtil {

	/**
	 * 对字符串进行 正则匹配，获取结果
	 * 
	 * @param current
	 *            匹配串
	 * @param regEx
	 *            正则表达式
	 * @return
	 */
	public static List<String> stringRegEx(String current, String regEx) {
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(current);
		List<String> result = new ArrayList<String>();
		while (mat.find()) {
			result.add(mat.group(1).replace("[", " ").replace("]", " ").trim());
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map testCaseStatistics(List<TestCase> testCases) {
		// 存放总的统计结果
		Map finallStatisticsResult = new HashMap();

		// 比较器
		Comparator<String> comp = new Comparator<String>() {
			public int compare(String o1, String o2) {
				return Integer.parseInt(o1) - Integer.parseInt(o2);
			}
		};
		List<Pair> hs = new ArrayList(), ts = new ArrayList();
		Map<String, List<Pair>> hb = new TreeMap(comp), ht = new TreeMap(comp);
		List<Pair> tmp, tmp2;
		for (TestCase testCase : testCases) {
			String speed = testCase.getResult().getWind_speed(), high = testCase.getResult().getTakeoff_alt(),
					battery = testCase.getResult().getBattery_remaining(), time = testCase.getResult().getTime();
			if ("0%".equals(battery)) {
				hs.add(new Pair(speed, high));
				ts.add(new Pair(speed, time));
			}

			if (!hb.containsKey(speed) || !ht.containsKey(speed)) {
				tmp = new ArrayList();
				tmp.add(new Pair(high, battery));
				hb.put(speed, tmp);

				tmp2 = new ArrayList();
				tmp2.add(new Pair(high, time));
				ht.put(speed, tmp2);
			} else {
				tmp = hb.get(speed);
				tmp.add(new Pair(high, battery));

				tmp2 = ht.get(speed);
				tmp2.add(new Pair(high, time));
			}

		}

		// 1.高度、风速关系
		finallStatisticsResult.put("high-speed", hs);
		// 2.风速、时间关系
		finallStatisticsResult.put("time-speed", ts);
		// 3.电量、高度关系
		finallStatisticsResult.put("high-battery", hb);
		// 4.高度、时间关系
		finallStatisticsResult.put("high-time", ht);

		return finallStatisticsResult;

	}

	/**
	 * 功能测试统计工具
	 * 
	 * @param testCases
	 *            测试用例实体类
	 * @return Map 类型 说明 : 1.测试用例有误,无法对应到执行程序! 2.测试执行成功!耗时: 3.程序出现出现死循环或者抛出异常!
	 * 
	 */
	public static Map<String, Object> functionStatistics(List<TestCase> testCases) {
		// 存放总的统计结果
		Map<String, Object> finallStatisticsResult = new HashMap();
		// 成功、失败用例的ID
		List<Integer> caseSuccess = new ArrayList(), caseFailed = new ArrayList();
		Map<Integer, List<Integer>> tmpMap;
		List<Map<Integer, List<Integer>>> tmpList;
		/*
		 * 出错类型统计 Map格式: Map<出错类型,LIST< MAP< 用例号,出错激励ID统计 > > >
		 */
		Map<String, List<Map<Integer, List<Integer>>>> failedStatistics = new HashMap();

		for (TestCase testCase : testCases) {
			String tmpStr = testCase.getResult().getResultDetail();
			int id = Integer.parseInt(testCase.getTestCaseID());
			String keyTmp = null;
			// 出错用例 纪录并统计
			if (tmpStr.contains("测试用例有误") || tmpStr.contains("程序出现出现死循环或者抛出异常")) {

				// 出错纪录
				caseFailed.add(id);

				// 出错统计
				if (tmpStr.contains("测试用例有误")) {
					keyTmp = "测试用例有误";
				} else {
					keyTmp = "程序出现出现死循环或者抛出异常";
				}
				if (!failedStatistics.containsKey(keyTmp)) {
					tmpList = new ArrayList();
					failedStatistics.put(keyTmp, tmpList);
				}
				tmpList = failedStatistics.get(keyTmp);
				// 处理对应用例所出现问题的激励ID，封装成List
				tmpMap = new HashMap();
				List<Integer> value = new ArrayList();
				for (myProcess m : testCase.getProcessList()) {
					if (!m.isProcessExec())
						value.add(m.getProcessID());
				}
				tmpMap.put(id, value);
				tmpList.add(tmpMap);

			} else {
				// 成功用例纪录
				caseSuccess.add(id);
			}
		}
		// 成功用例
		finallStatisticsResult.put("caseSuccess", caseSuccess);
		// 失败用例
		finallStatisticsResult.put("caseFailed", caseFailed);
		// 出错情况统计
		finallStatisticsResult.put("failedStatistics", failedStatistics);
		return finallStatisticsResult;
	}

	/**
	 * 时间测试工具
	 * 
	 * @param testCases
	 * @return
	 */
	public static Time timeStatistics(String detail) {

		// 1.获取返回测试结果
		String result = detail;

		// 2.按格式 获取各个部分的数值
		String[] tmp = result.split("\\|");

		Time time = new Time();
		// 2.1 获取原始时间不等式
		time.setOriginal(tmp[0]);
		if (tmp.length > 2) {
			// 2.2 获取出错列表
			time.setError(tmp[2]);
		}
		// 2.3 封装映射表
		time.setMapping(tmp[1]);

		// 2.4封装结果
		time.setShowMap();
		return time;
	}

	/**
	 * 将激励链表字符串转换成激励链表实体
	 * 
	 * @param processList
	 *            激励链表字符串
	 * @return
	 */
	public static List<myProcess> string2ProcessList(String processList) {
		List<myProcess> list = new ArrayList<myProcess>();
		// 1.划分各个激励链表字符串
		String[] tmp = processList.split("\\)");
		// 2.对每个激励节点进行处理
		for (String process : tmp) {
			// 2.1.字符串格式处理
			process += ")";
			process = process.trim();
			// 2.2. 激励节点生成
			myProcess my = new myProcess();
			// 激励 是否成功执行 # 表示成功，& 表示失败
			if (process.contains("#")) {
				my.setProcessExec(true);
			} else {
				my.setProcessExec(false);
			}
			// 2.3.激励ID
			my.setProcessID(Integer.parseInt(stringRegEx(process, "ProcessID:([\\s|\\S]*?)ProcessName:").get(0)));
			// 2.4.激励Name
			my.setProcessName(stringRegEx(process, "ProcessName:([\\s|\\S]*?)\\(").get(0));
			// 2.5.激励参数
			my.setProcessParam(stringRegEx(process, "ProcessParameter:([\\s|\\S]*?)ProcessStatus:").get(0));
			// 2.6.激励状态
			my.setProcessStatus(stringRegEx(process, "ProcessStatus:([\\s|\\S]*?)\\)").get(0));
			// System.out.println(my);
			list.add(my);
		}
		return list;
	}

	/***
	 * 根据字符串 构造测试用例链表
	 * 
	 * @param str
	 *            从服务器获取的字符串
	 * @return
	 */
	public static List<TestCase> buildTestCaseList(String type, String fileName) {

		List<TestCase> list = new ArrayList<>();
		String str = readFileByLines(fileName);
		// 1.按*号将测试用例划分
		String[] tmp = str.split("\\*");
		// 2.对每个测试用例字符串进行解析封装
		for (String s : tmp) {
			TestCase testCase = new TestCase();
			// 2.1.字符串格式处理
			s = s.replace("\n", "");
			// 2.2.获取测试用例ID
			testCase.setTestCaseID(stringRegEx(s, "testcCaseID:([\\s|\\S]*?)-->processList:").get(0));
			// 2.3.构造激励链表
			String processList = stringRegEx(s, "processList:([\\s|\\S]*?)-->execStatus").get(0);
			testCase.setProcessList(string2ProcessList(processList));
			// 2.4.测试用例执行状态
			/*
			 * 功能性能 : 类型 说明 : 1.测试用例有误,无法对应到执行程序，且测试耗时:[不准确] 2.测试耗时:
			 * 3.程序执行过程中出现死循环或者抛出异常! 时间约束: [x:x] 第一个表示所用激励执行情况 1 有误，2 无误
			 * 第二个表示是否满足约束不等式 1 不满足 ，2 满足
			 */
			TestCaseResult testCaseResult = new TestCaseResult();
			String exeState = "", t = stringRegEx(s, "execStatus:([\\s|\\S]*?)-->resultStatus:").get(0);
			if (t.contains(":")) {
				String[] r = t.split(":");
				// 时间约束
				if (type == "Time") {
					String tStatus, eStatus;
					if ("1".equals(r[0])) {
						tStatus = "测试用例有误,无法对应到执行程序";
					} else {
						tStatus = "测试用例正确执行";
					}

					if ("1".equals(r[1])) {
						eStatus = "不满足时间约束";
					} else {
						eStatus = "满足时间约束";
					}
					exeState = tStatus + ",且" + eStatus;
				} else {
					// 鍔熻兘銆佹?ц兘
					switch (Integer.valueOf(r[0])) {
					case 1:
						exeState = "测试用例有误,无法对应到执行程序，且测试耗时:" + r[1] + "[不准确]";
						break;
					case 2:
						exeState = "测试耗时:" + r[1];
						break;
					}
					if (type != "Function") {
						testCaseResult.setExeTime(Double.valueOf(r[1]));
						testCaseResult.setTakeoff_alt(Double.valueOf(r[2].substring("takeoff_alt".length())));
						testCaseResult
								.setBattery_remaining(Double.valueOf(r[3].substring("battery_remaining".length())));
						testCaseResult.setTime(Double.valueOf(r[4].substring("time".length())));
						testCaseResult.setWind_speed(Double.valueOf(r[5].substring("wind_speed".length())));
					}

				}
			} else {
				exeState = "程序执行过程中出现死循环或者抛出异常!";
			}
			testCase.setState(exeState);
			// 2.5.测试用例结果
			/*
			 * 功能性能 : 类型 说明 : 1.测试用例有误,无法对应到执行程序! 2.测试执行成功!耗时:
			 * 3.程序出现出现死循环或者抛出异常! 时间约束: 1.出错时,错误的不等式 2.正确时,原约束不等式 3.出现死循环
			 */
			String result = "";
			// 时间约束
			if (type == "Time") {
				result = stringRegEx(s, "resultStatus:([\\s|\\S]*?)]").get(0).split(":")[1];
				if (result == "3") {
					result = "程序出现出现死循环或者抛出异常!";
				} else {
					testCaseResult.setTimeLimit(timeStatistics(result));
				}

			} else {
				// 功能性能
				t = stringRegEx(s, "resultStatus:([\\s|\\S]*?)]").get(0);
				if (!t.contains(":")) {
					switch (Integer.valueOf(t)) {
					case 1:
						result = "测试用例有误,无法对应到执行程序!";
						break;
					case 3:
						result = "程序出现出现死循环或者抛出异常!";
						break;
					}
				} else {
					result = "测试执行成功!耗时:" + t.split(":")[1];
				}
			}

			testCaseResult.setResultDetail(result);
			testCase.setResult(testCaseResult);
			// 2.6.测试用例表现格式
			testCase.setDetail(testCase.showTestCase());
			// 2.7.加入测试用例链表
			list.add(testCase);
		}

		// 性能测试除去多个0%
		if (type == "Performance") {
			// 处理多个0%
			for (int i = 0; i < list.size(); i++) {
				if (i + 1 < list.size()) {
					if (list.get(i).getResult().getBattery_remaining().equals("0%")
							&& list.get(i + 1).getResult().getBattery_remaining().equals("0%")) {
						list.remove(i);
					}
				}
			}
		}
		return list;
	}

	/**
	 * 字符串写入文件 由于测试数据太大需要通过保存在文件的形式查看
	 * 
	 * @param content
	 */
	public static void string2File(String content) {
		System.out.println(content);
		try {
			File file = new File("testFile.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fileWritter = new FileWriter(file.getName(), true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(content);
			bufferWritter.close();

			System.out.println("File Save Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 */
	public static String readFileByLines(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		StringBuilder sb = new StringBuilder();
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 0;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				line++;
				sb.append(tempString);
				sb.append("\n");
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		// System.out.println(TestCaseConvertUtil.class.getResource("/").getPath());//user.dir指定了当前的路径
		String str = readFileByLines(
				"E://项目//SVN//虚拟仿真平台进度//Lab603Projects//violetproduct-swing//src//main//java//com//horstmann//violet//application//gui//util//chengzuo//Util//result.txt");

		List<TestCase> list = new ArrayList();
		// buildTestCaseList("time",list, str);
		for (TestCase t : list) {
			System.out.println(t);
		}
		Map m = testCaseStatistics(list);
		Map<String, List<Pair>> hb = (Map<String, List<Pair>>) m.get("high-battery");
		for (Map.Entry<String, List<Pair>> entry : hb.entrySet()) {
			System.out.println(entry.getKey());
			for (Pair pair : entry.getValue()) {
				System.out.println("\t" + pair);
			}

		}
	}
}
