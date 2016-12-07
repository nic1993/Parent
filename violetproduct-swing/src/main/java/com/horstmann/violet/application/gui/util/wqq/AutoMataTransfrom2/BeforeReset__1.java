package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

import java.util.ArrayList;


public class BeforeReset__1 {
	public static void main(String[] args) {
		/*ArrayList<String> Clocks=new ArrayList<String>();
		Clocks.add("x");
		Clocks.add("y");
		ArrayList<String> ar1 =new ArrayList<String>();
		ar1.add("y-x<-1");
		ar1.add("x-y<2");
		ar1.add("y<=1");
		DBM_element DBM1[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element__1.stringToDBM_element(Clocks, ar1));
		DBM_element[][] fDBM1=Floyds.floyds(DBM1);		
		String clock="y";
		
		//测试setClock（）
		DBM_element[][] DBM1_reset=Reset_1.reset(DBM1, Clocks, clock);
		DBM_element[][] fDBM1_reset=Floyds.floyds(DBM1_reset);
		DBM_element[][] sety1=setClock(DBM1_reset, Clocks, clock);
		DBM_element[][] fsety1=Floyds.floyds(sety1);
		//测试beforeReset 复位前区域
		DBM_element[][] beforeReset=beforeReset(DBM1, Clocks, clock);
		
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				DBM_element cons=fsety1[i][j];
				//System.out.println("DBM_i:"+cons.getDBM_i());
				//System.out.println("DBM_j:"+cons.getDBM_j());
				System.out.println("value:"+cons.getValue());
				System.out.println("Strictness:"+cons.isStrictness());
				
								
			}
		}*/
		ArrayList<String> Clocks=new ArrayList<String>();
		Clocks.add("t");
		ArrayList<String> ar1 =new ArrayList<String>();
		ar1.add("t>=3");
		DBM_element DBM1[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element.stringToDBM_element(Clocks, ar1));
		DBM_element DBM2[][]=beforeReset(DBM1, Clocks, "t");
	}
	/**
	 * 求zone时钟复位前的区域
	 * @param zone
	 * @param Clocks
	 * @param clock
	 * @return
	 */
	public static DBM_element[][] beforeReset(DBM_element[][] zone,ArrayList<String> Clocks,String clock) {
		if(Clocks.size()==1){//一个时钟
			int len=zone.length;
			DBM_element[][] zero=new DBM_element[len][len];
			for(int i=0;i<len;i++){
				for(int j=0;j<len;j++){
					DBM_element ele=new DBM_element();
					ele.setValue(0);
					ele.setStrictness(true);
					ele.setDBM_i(i);
					ele.setDBM_j(j);
					zero[i][j]=ele;	
				}
			}
			
			if(IsEmpty.isEmpty(AndDBM.andDBM(Floyds.floyds(zone), Floyds.floyds(zero)))==1){//如果与零点相交为空
				return null;
			}
			else {//如果与零点相交不为空
				return NewDBM__1.newDBM(len);
			}
		}
		else {//两个时钟
			int len=zone.length;
			int k = 0;
			for(int i=0;i<Clocks.size();i++){
				if(Clocks.get(i).equals(clock)){
					k=i+1;//获取clock在矩阵中的位置
				}
			}
			DBM_element[][] DBM=NewDBM__1.newDBM(len);//将被复位的时钟设置为0
			DBM[0][k].setValue(0);
			DBM[0][k].setStrictness(true);
			DBM[k][0].setValue(0);
			DBM[k][0].setStrictness(true);
			
			if(IsEmpty.isEmpty(AndDBM.andDBM(Floyds.floyds(zone), Floyds.floyds(DBM)))==-1){//如果y=0与zone相交不为空
				DBM_element[][] zone_reset=Reset_1.reset(zone, Clocks, clock);//zone时钟复位
				DBM_element[][] befor=setClock(zone_reset, Clocks, clock);//将复位后的zone的复位时钟设置为大于等于0
				return befor;
			}
			else return null;
		}
	}
	
	/**
	 * 将zone中一个时钟设置为大于等于0,将时钟间的关系设置为88888 false
	 * @param zone
	 * @param Clocks
	 * @param clock
	 * @return
	 */
	public static DBM_element[][] setClock(DBM_element[][] zone,ArrayList<String> Clocks,String clock) {
		int n=zone.length;
		DBM_element[][] newDBM = new DBM_element[n][n];
		for(int i=0;i<n;i++){//复制zone，防止zone改变
			for(int j=0;j<n;j++){
				DBM_element ele=new DBM_element();
				ele.setValue(zone[i][j].getValue());
				ele.setStrictness(zone[i][j].isStrictness());
				ele.setDBM_i(i);
				ele.setDBM_j(j);
				newDBM[i][j]=ele;	
			}
		}
		
		int k = 0;
		for(int i=0;i<Clocks.size();i++){
			if(Clocks.get(i).equals(clock)){
				k=i+1;//获取clock在矩阵中的位置
			}
		}
		
		newDBM[0][k].setStrictness(true);
		newDBM[0][k].setValue(0);//将clock设置大于等于0
		newDBM[k][0].setStrictness(false);
		newDBM[k][0].setValue(88888);
		
		for(int i=0;i<n;i++){//将时钟间关系设为无关
			for(int j=0;j<n;j++){
				if(i!=0&&j!=0&&i!=j){
					newDBM[i][j].setStrictness(false);
					newDBM[i][j].setValue(88888);
				}
			}
		}
		
		return newDBM;
	}
}
