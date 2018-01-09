package com.horstmann.violet.application.gui.util.yangjie;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import com.horstmann.violet.application.gui.util.chenzuo.Bean.TestCase;



public class HibernateUtils {

	private Configuration cfg;
	private  SessionFactory sf;
	private HibernateUtils hibernateUtils;
	public HibernateUtils()
	{
		sf = new Configuration().configure().buildSessionFactory();
	}

	
	public HibernateUtils(int type)
	{
		cfg = new Configuration().configure("com/horstmann/violet/application/gui/util/yangjie/hibernate.cfg.xml");
		cfg.addResource("com/horstmann/violet/application/gui/util/chenzuo/Bean/TestCase.hbm.xml");
		cfg.addResource("com/horstmann/violet/application/gui/util/chenzuo/Bean/myProcess.hbm.xml");
		sf = cfg.buildSessionFactory();
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

	
	public  void saveTCDetail(Target target) {
		// 对象

		// 根据session的工厂，创建session对象
		Session session = sf.openSession();
		// 开启事务
		Transaction tx = session.beginTransaction();
		// -----执行操作-----
		session.save(target);
		// 提交事务/ 关闭
		tx.commit();
		session.close();
	}
	
	public  void saveTestCase(TestCase testCase) {
		// 对象

		// 根据session的工厂，创建session对象
		Session session = sf.openSession();
		// 开启事务
		Transaction tx = session.beginTransaction();
		// -----执行操作-----
		session.save(testCase);
		// 提交事务/ 关闭
		tx.commit();
		session.close();
	}
	
//	@Test
//	public void save() throws Exception {
//		// 对象
////		TCDetail detail = TCDetail.getInstance();
////		detail.setStimulateSequence("stimulate");
////		detail.setTestCase("testcase");
////		detail.setTestSequence("testSequence");
//		
//		Target target = new Target();
//		target.setCoverageRate("1.000000");
//		target.setDeviationalue("0.00002345");
//		// 根据session的工厂，创建session对象
//		SessionFactory sf = new Configuration().configure()
//				.buildSessionFactory();
//		Session session = sf.openSession();
//		// 开启事务
//		Transaction tx = session.beginTransaction();
//		// -----执行操作-----
//		session.save(target);
//
//		// 提交事务/ 关闭
//		tx.commit();
//		session.close();
//	}
	
	public static void main(String[] args) {
		HibernateUtils hibernateUtils =  new HibernateUtils();
		Target target = new Target();
		TCDetail tcDetail = TCDetail.getInstance();
		tcDetail.setId(1);
		tcDetail.setStimulateSequence("===");
		tcDetail.setTestCase("   ");
		tcDetail.setTestSequence("++");
		target.setCoverageRate(1.000000);
		target.setDeviationalue(0.00002345);
		
		hibernateUtils.saveTCDetail(tcDetail);
	}
}
