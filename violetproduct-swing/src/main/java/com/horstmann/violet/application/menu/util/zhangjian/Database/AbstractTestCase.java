package com.horstmann.violet.application.menu.util.zhangjian.Database;

import java.io.Serializable;
/**
 * 抽象测试序列的数据结构
 * @author ZhangJian
 *
 */
public class AbstractTestCase implements Serializable {
	private int id;
	private String name;
	private String testRouter;
	public int getId() {
		return id;
	}
	public void setId(int tid) {
		this.id = tid;
	}
	public String getName() {
		return name;
	}
	public void setName(String tname) {
		this.name = tname;
	}
	public String getTestRouter() {
		return testRouter;
	}
	public void setTestRouter(String testRouter) {
		this.testRouter = testRouter;
	}
	
}
