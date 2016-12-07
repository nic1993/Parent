package com.horstmann.violet.application.gui.util.yangjie;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ListTest {
	private User user = new User("no1", 100);

	@Test
	public void testName() throws Exception {
		List<User> list1 = new ArrayList<User>();
		list1.add(new User("yj", 100));
		list1.add(new User("jj", 100));
		System.out.println(list1.toString());
		List<User> list2 = (List<User>) ((ArrayList<User>) list1).clone();
		System.out.println(list2.toString());
		list1.add(new User("yy", 100));
		System.out.println(list1);
		System.out.println(list2);
	}

	@Test
	public void test2() throws Exception {
		List<User> list1 = new ArrayList<User>();
		list1.add(new User("yj", 100));
		list1.add(new User("jj", 100));
		System.out.println(list1.toString());
		List<User> list2 = new ArrayList<User>();
		list2.addAll(list1);
		System.out.println(list2.toString());
		list1.add(new User("yy", 100));
		System.out.println(list1);
		System.out.println(list2);
	}

	@Test
	public void test3() throws Exception {
		List<User> list1 = new ArrayList<User>();
		list1.add(new User("yj", 100));
		list1.add(new User("jj", 100));
		System.out.println(list1.toString());
		user.setList(list1);
		System.out.println(user.getList().toString());
		list1.add(new User("c", 100));
		System.out.println(list1);
		System.out.println(user.getList().toString());
	}
}
