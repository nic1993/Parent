package com.horstmann.violet.application.gui.util.yangjie;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;



public class HibernateUtils {

	private  SessionFactory sf;
	public HibernateUtils()
	{
		sf = new Configuration().configure().buildSessionFactory();
	}

	// 创建Session对象
	public  Session getSession() {
		return sf.openSession();
	}

	public  void saveTCDetail(TCDetail detail) {
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
