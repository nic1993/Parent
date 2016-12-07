package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;
///////最终的提取不等式和提取参数
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;



public class Get_str {
/**
* 最终的取不等式，用于mma
* @param tran
* @return
*/
public static String get_bds(String tran){
	String out1=null;
	String s1=new String();
	String ss=null;
	String str = tran.replace("[", "").replace("]", "").replace("&&", ";")/*.replace("||", ";")*/;
	//System.out.println(str.toString()+"***********str*************");
	if(str.equals("")){
		//System.out.println("null"+"&&&&&&&&&");
		//ss="null";
		return null;
	}
	else{
		//String str = tran.replace("[", "").replace("]", "").replace("&&", ";");
		if(str.length()>0){
			Queue<String> queue=new LinkedList<String>();
			String[] strs=str.split(";");
			for(int i=0;i<strs.length;i++){
				queue.add(strs[i]);
			}
			String a=queue.poll();
			List<String> list1=new ArrayList<String>(); //存放参数
			//List<String> list2=new ArrayList<String>(); //存放不等式
			//List<String> list3=new ArrayList<String>(); //存放参数
			//List<String> list4=new ArrayList<String>(); //存放不等式
			while(a!=null){
				String out=a;
				if(!(a.contains("cycle")||a.contains("True")||a.contains("False"))){
				//if(!a.contains("cycle")){   //去除cycle
					if(a.contains("in:")){   //去除in：
						int index=a.indexOf(":");
						out=a.substring(index+1, a.length());
					}
					else{
						if(a.contains("condition:")){   //去除condition：
							int index=a.indexOf(":");
							out=a.substring(index+1, a.length());
						}
					}	
					/*if(out.contains("!=")){//把不等于化为大于或者小于
						out="("+out.replace("!=", ">")+")"+"||"+"("+out.replace("!=", "<")+")";
					}*/
					if(out.contains("<")||out.contains(">")||out.contains("=")||out.contains("==")){
						if(out.contains("=")&&!out.contains("<")&&!out.contains(">")&&!out.contains("==")&&!out.contains("!=")){
							 out = out.replace("=", "==");
						}
						list1.add(out);///放不等式
						//System.out.println(list1+"*****list1******");
						//System.out.println(list1+"#######");
						/*if(out.contains("<=.<=")){
							int index=a.indexOf("=");
							out=a.substring(index+1,a.length());/////
							int index1=out.indexOf("<");
							out=out.substring(0,index1);/////
							list2.add(out);
						}*/
						/*else{
							if(out.contains(">")){
								
							}
							else{
								
							}
						}*/
					}
					/*else{
						return null;
					}*/
				}			
				a=queue.poll();
				//System.out.println(a+"****");
			}
			if(list1.size()>1){
				if(!list1.get(0).contains("==0")){
					ss=list1.get(0);
				}
				
				for(int j=1;j<list1.size();j++){
					//System.out.println(ss+"^^^^^^^^^^^");
					s1=list1.get(j);
					if(!(s1.contains("==0"))&&(ss!=null)){
						ss=ss+","+s1;
					}
					if(!(s1.contains("==0"))&&(ss==null)){
						ss=s1;
					}
				}
			}
			if(list1.size()==1){
				if(!(list1.get(0).contains("==0"))){
					ss=list1.get(0);
				}	
			}	
			if(list1.size()<=0){
				return null;
			}
			}
		}
	    if(ss!=null&&ss.contains(" ")){
	    	ss=ss.replace(" ", "");
	    }
	    
		return ss;
	}
/**
* 返回数字参数，用于mma
* @param bds
* @return
*/
public static String get_cs(String bds){
	String c1=new String();
	String cs=new String(); //用于返回参数组
	String s1=new String();//获取第一个不等式首字符
	String s2=new String();
	String s3=new String();
	String s4=new String();
	//System.out.println("******bds1*******"+bds1);
	//String bds=bds1.replace("|", ",");
	//System.out.println("^^^^^^^^^^^^"+bds);
	if(bds==null){
		return null;
	}
	else{
		Queue<String> queue = new LinkedList<String>();//存放不等式
		//Queue<String> queue1 = new LinkedList<String>();//存放布尔型不等式
		String[] strs=bds.replace("(", "").replace(")", "").split(",");  //拆开不等式，放入队列中
		for(int i=0;i<strs.length;i++){
			if(!(strs[i].contains("True")||strs[i].contains("False"))){
				/*if(strs[i].contains("||")){
					int index=strs[i].indexOf("|");
					s1=strs[i].substring(0, index);
					queue1.add(s1);
					s2=strs[i].substring(index+2, strs[i].length());
					if(s2.contains("||")){
						int index2=s2.indexOf("|");
						s3=s2.substring(0,index2);
						s4=s2.substring(index2+2,s2.length());
						queue1.add(s3);
						queue1.add(s4);
					}
					else{
						queue1.add(s2);
					}
					//queue1.add(s1);
					//queue1.add(s2);
				}
				else{
					queue1.add(strs[i]);
				}
				
			}
			else{*/
				if(strs[i].contains("||")&&!(strs[i].contains("True")||strs[i].contains("False"))){
					int index=strs[i].indexOf("|");
					s1=strs[i].substring(0, index);
					s2=strs[i].substring(index+2, strs[i].length());
					queue.add(s1);
					queue.add(s2);
				}
				else{
					queue.add(strs[i]);
				}
			}	 
		 }
		 String a=queue.poll();  //第一个不等式
		 //String b=queue1.poll();
		 //System.out.println("%%%%%%%%%1111111111111%%%%%%%%%%%"+a);
		 List<String> list=new ArrayList<String>();   //存放参数
		 /*if(a.contains("||")){
			 int index=a.indexOf("|");
			 s1=a.substring(beginIndex)
		 }*/
		 while(a!=null){
			 //int ss1=Integer.parseInt(a.substring(0, 1));
			 //System.out.println("****a*****"+a);
			 //System.out.println("*****第一个字符******"+a.substring(0,1));
			 int ss1=a.substring(0, 1).toCharArray()[0];
			 //System.out.println("*****第一个字符的数字******"+ss1);
			 if(!((ss1>=48&&ss1<=57)||ss1==45)){//第一个为参数
				 if(a.contains("<=")){  ///////处理两个<=
					 //System.out.println("2222222222"+a+"222222222");
					 int index1=a.indexOf("<");
					 s1=a.substring(0,index1);//第一个<=前面的
					 list.add(s1);
					 s2=a.substring(index1+2,a.length());//第一个<=后面的
					 if(s2.contains("<=")){
						 int index2=s2.indexOf("<");
						 s3=s2.substring(0,index2);//两个<=之间的
						 s4=s2.substring(index2+2,s2.length());//第二个<=后面的
						 //int ss2=Integer.valueOf(s3.substring(0, 1));
						// int ss3=Integer.valueOf(s4.substring(0, 1));
						 int ss2=s3.substring(0, 1).toCharArray()[0];
						 int ss3=s4.substring(0, 1).toCharArray()[0];
						// System.out.println("33333333333"+ss2+"333333333");
						 //System.out.println("4444444444"+ss3+"444444444");
						 if(!((ss2>=48&&ss2<=57)||ss2==45)){
							 //System.out.println("***********"+s3);
							 list.add(s3);
						 }
						 if(!((ss3>=48&&ss3<=57)||ss3==45)){
							 //System.out.println("***********&&&&&&&&&"+s4);
							 list.add(s4);
						 }
					 }	
					 else{
						 int ss2=s2.substring(0, 1).toCharArray()[0];
						 if(!((ss2>=48&&ss2<=57)||ss2==45)){
							 list.add(s2);
						 }
					 }
				 }             //////////<=.<=
				 /*else{
					 if(a.contains("<=")){
						 //System.out.println("555555555"+a);
						 int index1=a.indexOf("<");
						 s1=a.substring(0,index1);//<=前面的
						 list.add(s1);
						 s2=a.substring(index1+2,a.length());//<=后面的
						 //int ss2=Integer.valueOf(s2.substring(0, 1));
						 int ss2=s2.substring(0, 1).toCharArray()[0];
						 //System.out.println("666666666666"+ss2);
						 if(!((ss2>=48&&ss2<=57)||ss2==45)){
							 list.add(s2);
						 }
					 }       */    ///////////    <=
					 else{
						 if(a.contains("<")){
							 int index1=a.indexOf("<");
							 s1=a.substring(0,index1);//<=前面的
							 list.add(s1);
							 s2=a.substring(index1+1,a.length());//<=后面的
							 //int ss2=Integer.valueOf(s2.substring(0, 1));
							 int ss2=s2.substring(0, 1).toCharArray()[0];
							 if(!((ss2>=48&&ss2<=57)||ss2==45)){
								 list.add(s2);
							 }
						 }
						 else{
							 if(a.contains(">=")){  ///////处理两个>=
								 int index1=a.indexOf(">");
								 s1=a.substring(0,index1);//第一个>=前面的
								 list.add(s1);
								 s2=a.substring(index1+2,a.length());//第一个>=后面的
								 if(s2.contains(">=")){
									 int index2=s2.indexOf(">");
									 s3=s2.substring(0,index2);//两个>=之间的
									 s4=s2.substring(index2+2,s2.length());//第二个>=后面的
									 //int ss2=Integer.valueOf(s3.substring(0, 1));
									 //int ss3=Integer.valueOf(s4.substring(0, 1));
									 int ss2=s3.substring(0, 1).toCharArray()[0];
									 int ss3=s4.substring(0, 1).toCharArray()[0];
									 if(!((ss2>=48&&ss2<=58)||ss2==45)){
										 list.add(s3);
									 }
									 if(!((ss3>=48&&ss3<=57)||ss3==45)){
										 list.add(s4);
									 }
								 }
								 else{
									 int ss2=s2.substring(0, 1).toCharArray()[0];
									 if(!((ss2>=48&&ss2<=57)||ss2==45)){
										 list.add(s2);
									 }
								 }
							 }             //////////>=.>=
							 /*else{
								 if(a.contains(">=")){
									 int index1=a.indexOf(">");
									 s1=a.substring(0,index1);//<=前面的
									 list.add(s1);
									 s2=a.substring(index1+2,a.length());//<=后面的
									 //int ss2=Integer.valueOf(s2.substring(0, 1));
									 int ss2=s2.substring(0, 1).toCharArray()[0];
									 if(!((ss2>=48&&ss2<=57)||ss2==45)){
										 list.add(s2);
									 }
								 }           ///////////    >=
*/									 else{
									 if(a.contains(">")){
										 int index1=a.indexOf(">");
										 s1=a.substring(0,index1);//>=前面的
										 list.add(s1);
										 s2=a.substring(index1+1,a.length());//>=后面的
										 //int ss2=Integer.valueOf(s2.substring(0, 1));
										 int ss2=s2.substring(0, 1).toCharArray()[0];
										 if(!((ss2>=48&&ss2<=57)||ss2==45)){
											 list.add(s2);
										 }
									 }
									 else{
										 if(a.contains("==")){
											 int index1=a.indexOf("=");
											 s1=a.substring(0,index1);//==前面的
											 list.add(s1);
											 s2=a.substring(index1+2,a.length());//==后面的
											 //int ss2=Integer.valueOf(s2.substring(0, 1));
											 int ss2=s2.substring(0, 1).toCharArray()[0];
											 if(!((ss2>=48&&ss2<=57)||ss2==45)){
												 list.add(s2);
											 }
										 }
										 else{
											 if(a.contains("!=")){
												 int index1=a.indexOf("!");
												 s1=a.substring(0,index1);//!=前面的
												 list.add(s1);
												 s2=a.substring(index1+2,a.length());//!=后面的
												 //int ss2=Integer.valueOf(s2.substring(0, 1));
												 int ss2=s2.substring(0, 1).toCharArray()[0];
												 if(!((ss2>=48&&ss2<=57)||ss2==45)){
													 list.add(s2);
												 } 
											 }
										 }
									 }
							//	 }
							// }
						 }
					 }	 
				 }//不为>=.>=				 
			 }
			 if((ss1>=48&&ss1<=57)||ss1==45){//第一个为自然数
				 if(a.contains("<=")){  ///////处理两个<=
					 int index1=a.indexOf("<");
					 s2=a.substring(index1+2,a.length());//第一个<=后面的
					 if(s2.contains("<=")){
						 int index2=s2.indexOf("<");
						 s3=s2.substring(0,index2);//两个<=之间的
						 s4=s2.substring(index2+2,s2.length());//第二个<=后面的
						 //int ss2=Integer.valueOf(s3.substring(0, 1));
						// int ss3=Integer.valueOf(s4.substring(0, 1));
						 int ss2=s3.substring(0, 1).toCharArray()[0];
						 int ss3=s4.substring(0, 1).toCharArray()[0];
						 if(!((ss2>=48&&ss2<=57)||ss2==45)){
							 list.add(s3);
						 }
						 if(!((ss3>=48&&ss3<=57)||ss3==45)){
							 list.add(s4);
						 }
					 } 
					 else{
						 int ss2=s2.substring(0, 1).toCharArray()[0];
						 if(!((ss2>=48&&ss2<=57)||ss2==45)){
							 list.add(s2);
						 }
					 }
				 }             //////////<=.<=
				 /*else{
					 if(a.contains("<=")){
						 int index1=a.indexOf("<");
						 s2=a.substring(index1+2,a.length());//<=后面的
						 //int ss2=Integer.valueOf(s2.substring(0, 1));
						 int ss2=s2.substring(0, 1).toCharArray()[0];
						 if(!((ss2>=48&&ss2<=57)||ss2==45)){
							 list.add(s2);
						 }
					 }           ///////////    <=
*/						 else{
						 if(a.contains("<")){
							 int index1=a.indexOf("<");
							 s2=a.substring(index1+1,a.length());//<=后面的
							 //int ss2=Integer.valueOf(s2.substring(0, 1));
							 int ss2=s2.substring(0, 1).toCharArray()[0];
							 if(!((ss2>=48&&ss2<=57)||ss2==45)){
								 list.add(s2);
							 }
						 }
						 else{
							 if(a.contains(">=")){  ///////处理两个>=
								 int index1=a.indexOf(">");
								 s2=a.substring(index1+2,a.length());//第一个>=后面的
								 if(s2.contains(">=")){
									 int index2=s2.indexOf(">");
									 s3=s2.substring(0,index2);//两个>=之间的
									 s4=s2.substring(index2+2,s2.length());//第二个>=后面的
									 //int ss2=Integer.valueOf(s3.substring(0, 1));
									 //int ss3=Integer.valueOf(s4.substring(0, 1));
									 int ss2=s3.substring(0, 1).toCharArray()[0];
									 int ss3=s4.substring(0, 1).toCharArray()[0];
									 if(!((ss2>=48&&ss2<=57)||ss2==45)){
										 list.add(s3);
									 }
									 if(!((ss3>=48&&ss3<=57)||ss3==45)){
										 list.add(s4);
									 }
								 }
								 else{
									 int ss2=s2.substring(0, 1).toCharArray()[0];
									 if(!((ss2>=48&&ss2<=57)||ss2==45)){
										 list.add(s2);
									 }
								 }
								 
							 }             //////////>=.>=
							 /*else{
								 if(a.contains(">=")){
									 int index1=a.indexOf(">");
									 s2=a.substring(index1+2,a.length());//<=后面的
									 //int ss2=Integer.valueOf(s2.substring(0, 1));
									 int ss2=s2.substring(0, 1).toCharArray()[0];
									 if(!((ss2>=48&&ss2<=57)||ss2==45)){
										 list.add(s2);
									 }
								 }     */      ///////////    >=
								 else{
									 if(a.contains(">")){
										 int index1=a.indexOf(">");
										 s2=a.substring(index1+1,a.length());//>=后面的
										 //int ss2=Integer.valueOf(s2.substring(0, 1));
										 int ss2=s2.substring(0, 1).toCharArray()[0];
										 if(!((ss2>=48&&ss2<=57)||ss2==45)){
											 list.add(s2);
										 }
									 }
									 else{
										 if(a.contains("==")){
											 int index1=a.indexOf("=");
											 s2=a.substring(index1+2,a.length());//==后面的
											 //int ss2=Integer.valueOf(s2.substring(0, 1));
											 int ss2=s2.substring(0, 1).toCharArray()[0];
											 if(!((ss2>=48&&ss2<=57)||ss2==45)){
												 list.add(s2);
											 }
										 }
										 else{
											 if(a.contains("!=")){
												 int index1=a.indexOf("!");
												 s2=a.substring(index1+2,a.length());//!=后面的
												 //int ss2=Integer.valueOf(s2.substring(0, 1));
												 int ss2=s2.substring(0, 1).toCharArray()[0];
												 if(!((ss2>=48&&ss2<=57)||ss2==45)){
													 list.add(s2);
												 } 
											 }
										 }
									 }
								// }
							// }
						 }
					 }	 
				 }				 
			 }
			 a=queue.poll();		 
		 }//while
		 if(list.size()>1){
				cs=list.get(0);
				for(int j=1;j<list.size();j++){
					//System.out.println(ss+"^^^^^^^^^^^");
					c1=list.get(j);
					cs=cs+","+c1;
				}
			}
			if(list.size()==1){
				cs=list.get(0);
			}				
	}	
return cs;
}//cs


/**
 * 返回布尔型不等式
 * @param tran
 * @return
 */
public static String get_bool_bds(String tran){
	String out1=null;
	String s1=new String();
	String ss=new String();
	String str = tran.replace("[", "").replace("]", "").replace("&&", ";")/*.replace("||", ";")*/;
	if(str.equals("")){
		return null;
	}
	else{
		if(str.length()>0){
			Queue<String> queue=new LinkedList<String>();
			String[] strs=str.split(";");
			for(int i=0;i<strs.length;i++){
				queue.add(strs[i]);
			}
			String a=queue.poll();
			List<String> list1=new ArrayList<String>(); //存放参数
			List<String> list2=new ArrayList<String>(); //存放布尔型不等式
			//List<String> list3=new ArrayList<String>(); //存放参数
			//List<String> list4=new ArrayList<String>(); //存放不等式
			while(a!=null){
				String out=a;
				if(!(a.contains("cycle")||a.contains("{"))){
				//if(!a.contains("cycle")){   //去除cycle
					if(a.contains("in:")){   //去除in：
						int index=a.indexOf(":");
						out=a.substring(index+1, a.length());
					}
					else{
						if(a.contains("condition:")){   //去除condition：
							int index=a.indexOf(":");
							out=a.substring(index+1, a.length());
						}
					}	
					if(out.contains("<")||out.contains(">")||out.contains("=")||out.contains("==")){
						if(out.contains("=")&&!out.contains("<")&&!out.contains(">")&&!out.contains("==")&&!out.contains("!=")){
							 out = out.replace("=", "==");
						}
						list1.add(out);///放不等式
					}
				}			
				a=queue.poll();
			}
			if(list1.size()>0){
				for(int j=0;j<list1.size();j++){
					if((list1.get(j).contains("True")||list1.get(j).contains("False"))){
						list2.add(list1.get(j));
					}
				}
			}
			if(list2.size()>1){
				ss=list2.get(0);
				for(int j=1;j<list2.size();j++){
					//System.out.println(ss+"^^^^^^^^^^^");
					s1=list2.get(j);
					ss=ss+","+s1;
				}
			}
			if(list2.size()==1){
				ss=list2.get(0);
			}	
			if(list2.size()<=0){
				return null;
			}
			}
		}
	    ss=ss.replace(" ", "");
		return ss;
}

/**
 * 返回布尔型参数
 * @param bds
 * @return
 */
public static String get_bool_cs(String bds){
	String c1=new String();
	String cs=new String(); //用于返回参数组
	String s1=new String();//获取第一个不等式首字符
	String s2=new String();
	String s3=new String();
	String s4=new String();
	if(bds==null){
		return null;
	}
	else{
		Queue<String> queue1 = new LinkedList<String>();//存放布尔型不等式
		String[] strs=bds.replace("(", "").replace(")", "").split(",");  //拆开不等式，放入队列中
		for(int i=0;i<strs.length;i++){//布尔型不等式拆开放进队列中去
			if(strs[i].contains("True")||strs[i].contains("False")){
				if(strs[i].contains("||")){
					int index=strs[i].indexOf("|");
					s1=strs[i].substring(0, index);
					queue1.add(s1);
					s2=strs[i].substring(index+2, strs[i].length());
					if(s2.contains("||")){
						int index2=s2.indexOf("|");
						s3=s2.substring(0,index2);
						s4=s2.substring(index2+2,s2.length());
						queue1.add(s3);
						queue1.add(s4);
					}
					else{
						queue1.add(s2);
					}
					//queue1.add(s1);
					//queue1.add(s2);
				}
				else{
					queue1.add(strs[i]);
				}
			}
		}
		 String a=queue1.poll();  //第一个布尔不等式
		 List<String> list=new ArrayList<String>();   //存放布尔型参数
		 while(a!=null){
			 if(a.contains("==")){
				 int index1=a.indexOf("=");
				 s1=a.substring(0,index1);//==前面的
				 if(!(s1.contains("True")||s1.contains("False"))){
					 list.add(s1);
				 }
				 s2=a.substring(index1+2,a.length());//==后面的
				 if(!(s2.contains("True")||s2.contains("False"))){
					 list.add(s2);
				 }
			 }
			 a=queue1.poll();	 
		 }//while
		 if(list.size()>1){
				cs=list.get(0);
				for(int j=1;j<list.size();j++){
					//System.out.println(ss+"^^^^^^^^^^^");
					c1=list.get(j);
					cs=cs+","+c1;
				}
			}
			if(list.size()==1){
				cs=list.get(0);
			}				
	}	
return cs;
}


/**
* 最终的取有==0不等式，用于mma
* @param tran
* @return
*/
public static String get_bds_0(String tran){
	String out1=null;
	String s1=new String();
	String ss=new String();
	String str = tran.replace("[", "").replace("]", "").replace("&&", ";")/*.replace("||", ";")*/;
	//System.out.println(str.toString()+"***********str*************");
	if(str.equals("")){
		//System.out.println("null"+"&&&&&&&&&");
		//ss="null";
		return null;
	}
	else{
		//String str = tran.replace("[", "").replace("]", "").replace("&&", ";");
		if(str.length()>0){
			Queue<String> queue=new LinkedList<String>();
			String[] strs=str.split(";");
			for(int i=0;i<strs.length;i++){
				queue.add(strs[i]);
			}
			String a=queue.poll();
			List<String> list1=new ArrayList<String>(); //存放参数
			List<String> list2=new ArrayList<String>(); //存放==0的不等式
			//List<String> list3=new ArrayList<String>(); //存放参数
			//List<String> list4=new ArrayList<String>(); //存放不等式
			while(a!=null){
				String out=a;
				if(!(a.contains("cycle")||a.contains("{"))){
				//if(!a.contains("cycle")){   //去除cycle
					if(a.contains("in:")){   //去除in：
						int index=a.indexOf(":");
						out=a.substring(index+1, a.length());
					}
					else{
						if(a.contains("condition:")){   //去除condition：
							int index=a.indexOf(":");
							out=a.substring(index+1, a.length());
						}
					}	
					/*if(out.contains("!=")){//把不等于化为大于或者小于
						out="("+out.replace("!=", ">")+")"+"||"+"("+out.replace("!=", "<")+")";
					}*/
					if(out.contains("<")||out.contains(">")||out.contains("=")||out.contains("==")){
						if(out.contains("=")&&!out.contains("<")&&!out.contains(">")&&!out.contains("==")&&!out.contains("!=")){
							 out = out.replace("=", "==");
						}
						list1.add(out);///放不等式
					}
				}			
				a=queue.poll();
			}
			
			if(list1.size()>0){
				for(int j=0;j<list1.size();j++){
					if(list1.get(j).contains("==0")){
						list2.add(list1.get(j));
					}
				}
			}
			if(list2.size()>1){
				ss=list2.get(0);
				for(int j=1;j<list2.size();j++){
					//System.out.println(ss+"^^^^^^^^^^^");
					s1=list2.get(j);
					ss=ss+","+s1;
				}
			}
			if(list2.size()==1){
				ss=list2.get(0);
			}	
			if(list2.size()<=0){
				return null;
			}
			}
		}
	    ss=ss.replace(" ", "").replace("==", "=");
		return ss;
			
	}

}//T012






