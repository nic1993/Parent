package com.horstmann.violet.application.gui.util.yangjie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchConditions {

	private static int count = 0;

	public static int findConditionNum() throws Exception {
		count = 0;
		File directory = new File("E:/Markov/飞控源码/ardupilot-master");
		search(directory);
		return count;
	}

	public static void search(File directory) throws Exception {
		File[] childFiles = directory.listFiles();
		for (File file : childFiles) {
			if (file.isDirectory()) {
				if (!file.getName().equals("examples")) {// 将飞控源码里examples文件夹给过滤掉
					// System.out.println(file.getName());
					search(file);
				}
			} else {

				String extension = file.getName().substring(
						file.getName().indexOf(".") + 1);
				if (extension.equals("cpp")) {
					// System.out.println(file.getName());
					readFile(file);
				}
			}

		}
	}

	/**
	 * 精确版本的源代码条件统计算法
	 * 
	 * @param file
	 * @throws Exception
	 */
	public static void readFile(File file) throws Exception {
		BufferedReader bufReader = new BufferedReader(new FileReader(file));
		String line = null;

		StringBuilder sb = new StringBuilder();
		while ((line = bufReader.readLine()) != null) {
			// 在读取文件的时候过滤“//"和”///“这两种注释
			if (line.trim().contains("//")) {
				continue;
			}
			sb.append(line + "\n");

		}
		// System.out.println(sb.toString());
		String srcCode = sb.toString();
		bufReader.close();

		// 过滤”/* */”和“/** */”两种注释

		/**
		 * 做/* 注释的正则匹配
		 * 
		 * 
		 * 通过渐进法做注释的正则匹配，因为/*注释总是成对出现 当匹配到一个/*时总会在接下来的内容中会首先匹配到"*\\/",
		 * 因此在获取对应的"*\\/"注释时只需要从当前匹配的/*开始即可， 下一次匹配时只需要从上一次匹配的结尾开始即可
		 * （这样对于大文本可以节省匹配效率）—— 这就是渐进匹配法
		 * 
		 * 
		 * */
		Pattern leftpattern = Pattern.compile("/\\*");
		Matcher leftmatcher = leftpattern.matcher(srcCode);
		Pattern rightpattern = Pattern.compile("\\*/");
		Matcher rightmatcher = rightpattern.matcher(srcCode);
		/**
		 * begin 变量用来做渐进匹配的游标初始值为文件开头
		 * **/
		String src = srcCode;
		int begin = 0;
		while (leftmatcher.find(begin)) {
			rightmatcher.find(leftmatcher.start());
			src = src.replace(
					srcCode.substring(leftmatcher.start(), rightmatcher.end()),
					"");

			begin = rightmatcher.end();
		}
		// System.out.println(src);

		// 匹配所有的if、while、for语句并统计其中的条件数
		Pattern leftpattern2 = Pattern.compile("(if|while|for) *\\(");
		Matcher leftmatcher2 = leftpattern2.matcher(src);
		Pattern rightpattern2 = Pattern.compile("\\) *\\{*;*\n");
		Matcher rightmatcher2 = rightpattern2.matcher(src);
		Pattern rightpattern3 = Pattern.compile("\\) *\\{");
		Matcher rightmatcher3 = rightpattern3.matcher(src);
		/**
		 * begin2 变量用来做渐进匹配的游标初始值为src开头
		 * **/
		int begin2 = 0;
		while (leftmatcher2.find(begin2)) {
			String decision = null;
			int matcher2start = -1;
			int matcher3start = -1;
			if (rightmatcher2.find(leftmatcher2.start())) {
				matcher2start = rightmatcher2.start();

			}
			if (rightmatcher3.find(leftmatcher2.start())) {
				matcher3start = rightmatcher3.start();

			}
			if (matcher2start != -1) {
				if (matcher3start != -1) {
					if (matcher2start <= matcher3start) {
						decision = src.substring(leftmatcher2.end(),
								rightmatcher2.start());
						begin2 = rightmatcher2.end();
					} else {
						decision = src.substring(leftmatcher2.end(),
								rightmatcher3.start());
						begin2 = rightmatcher3.end();
					}
				} else {
					decision = src.substring(leftmatcher2.end(),
							rightmatcher2.start());
					begin2 = rightmatcher2.end();
				}
			} else {
				decision = src.substring(leftmatcher2.end(),
						rightmatcher3.start());
				begin2 = rightmatcher3.end();
			}
			int length = decision.split("(\\&\\&|\\|\\|)").length;
			count += length;
		}

		// System.out.println(count);

	}

	/**
	 * 模糊版本的源代码条件统计算法
	 * 
	 * @param file
	 * @throws Exception
	 */

	public static void read(File file) throws Exception {
		BufferedReader bufReader = new BufferedReader(new FileReader(file));
		String line = null;

		String regex = "(if|while|for) *\\((.+)\\)";
		Pattern pattern = Pattern.compile(regex);
		while ((line = bufReader.readLine()) != null) {
			Matcher matcher = pattern.matcher(line);
			while (matcher.find()) {
				String decision = matcher.group(2);
				// System.out.println(decision);
				int length = decision.split("(\\&\\&|\\|\\|)").length;
				count += length;
			}
		}
		// System.out.println(count);
		bufReader.close();
	}

}
