package com.horstmann.violet.application.gui.util.yangjie;

import java.util.ArrayList;
import java.util.List;

/**
 * 求解连续型参数变量定义域的边界值，返回边界值集合。
 * 
 * @author YJ
 * 
 */

public class Border {

	public List<String> getBorder(String domain) {

		List<String> border = new ArrayList<String>();
		if (domain.contains("≤")) {
			String[] strs = domain.split("≤");
			if (strs.length == 3) {
				border.add(strs[0]);
				border.add(strs[2]);
			} else if (domain.contains("<")) {
				int le = domain.indexOf("≤");
				int lt = domain.indexOf("<");
				if (le < lt) {
					border.add(strs[0]);
					border.add(Double.parseDouble(strs[1].split("<")[1]) - 1
							+ "");
				} else {
					border.add(strs[1]);
					border.add(Double.parseDouble(strs[0].split("<")[0]) + 1
							+ "");
				}
			} else {
				border.add(strs[1]);
			}
		} else if (domain.contains("≥")) {
			border.add(domain.split("≥")[1]);
		} else if (domain.contains("<")) {
			String[] strs = domain.split("<");
			if (strs.length == 3) {
				border.add(Double.parseDouble(strs[0]) + 1 + "");
				border.add(Double.parseDouble(strs[2]) - 1 + "");
			} else {
				border.add(Double.parseDouble(strs[1]) - 1 + "");
			}
		} else if (domain.contains(">")) {
			String[] strs = domain.split(">");
			border.add(Double.parseDouble(strs[1]) + 1 + "");
		}

		return border;
	}
}
