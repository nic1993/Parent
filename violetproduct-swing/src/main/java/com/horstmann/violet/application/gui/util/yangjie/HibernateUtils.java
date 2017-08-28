package com.horstmann.violet.application.gui.util.yangjie;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;


public class HibernateUtils {

	private static SessionFactory sf;
	static {
		// 加载主配置文件, 并创建Session的工厂
		sf = new Configuration().configure().buildSessionFactory();
		// sf = new Configuration().configure(new
		// File("hibernate.cfg.xml")).buildSessionFactory();
	}

	// 创建Session对象
	public static Session getSession() {
		return sf.openSession();
	}

	public static void saveTCDetail(TCDetail detail) {
		// 对象

		// 根据session的工厂，创建session对象
		Session session = sf.openSession();
		// 开启事务
		Transaction tx = session.beginTransaction();
		// -----执行操作-----
		session.save(detail);

		// 提交事务/ 关闭
		tx.commit();
		session.close();
	}
	public static void saveTCDetails(List<TCDetail> details) {
		// 对象

		// 根据session的工厂，创建session对象
		Session session = sf.openSession();
		// 开启事务
		Transaction tx = session.beginTransaction();
		// -----执行操作-----
		for(int i = 0;i < details.size();i++)
		{
			session.save(details.get(i));
			if(i % 100 == 0 )
			{
				session.flush();
				session.clear(); 
			}
		}
		// 提交事务/ 关闭
		tx.commit();
		session.close();
	}

	@Test
	public void save() throws Exception {
		// 对象
		TCDetail detail = TCDetail.getInstance();
		detail.setStimulateSequence("stimulate");
		detail.setTestCase("testcase");
		detail.setTestSequence("testSequence");
		// 根据session的工厂，创建session对象
		SessionFactory sf = new Configuration().configure()
				.buildSessionFactory();
		Session session = sf.openSession();
		// 开启事务
		Transaction tx = session.beginTransaction();
		// -----执行操作-----
		session.save(detail);

		// 提交事务/ 关闭
		tx.commit();
		session.close();
	}
}
