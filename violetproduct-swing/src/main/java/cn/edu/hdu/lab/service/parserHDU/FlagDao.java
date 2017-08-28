package cn.edu.hdu.lab.service.parserHDU;

public class FlagDao {
	private String ID;             //对象唯一标识
	private boolean enFlag=false;  //是否被封装或嵌套
	private int count=0;           //出现次数
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public boolean isEnFlag() {
		return enFlag;
	}
	public void setEnFlag(boolean enFlag) {
		this.enFlag = enFlag;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	

}
