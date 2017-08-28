package com.horstmann.violet.application.gui.util.chenzuo.Bean;

import java.io.Serializable;
import java.text.DecimalFormat;

public class TestCaseResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1106050374724867452L;

	// 执行时间
	private double exeTime = 0d;

	// 高度
	private double takeoff_alt = 0d;

	// 电量
	private double battery_remaining = 0d;

	// 风速
	private double wind_speed = 0d;

	// 时间
	private double time = 0d;

	//result of executor
	private String resultDetail;

	//TimeLimit of TimeTest
	private Time timeLimit;

	DecimalFormat decimalFormat = new DecimalFormat("###");

	public TestCaseResult() {
	}

	public TestCaseResult(double exeTime, double wind_speed, double takeoff_alt, double battery_remaining,
						  double time) {
		this.exeTime = exeTime;
		this.takeoff_alt = takeoff_alt;
		this.battery_remaining = battery_remaining;
		this.time = time;
		this.wind_speed = wind_speed;
	}

	public String getWind_speed() {
		return decimalFormat.format(wind_speed);
	}

	public void setWind_speed(double wind_speed) {
		this.wind_speed = wind_speed;
	}

	public String getExeTime() {
		return decimalFormat.format(exeTime);
	}

	public void setExeTime(double exeTime) {
		this.exeTime = exeTime;
	}

	public String getTakeoff_alt() {
		return decimalFormat.format(takeoff_alt);
	}

	public void setTakeoff_alt(double akeoff_alt) {
		this.takeoff_alt = akeoff_alt;
	}

	public String getBattery_remaining() {
		String tmp = decimalFormat.format(battery_remaining);
		if (tmp.equals("-0"))
			tmp = "0";
		return tmp + "%";
	}

	public void setBattery_remaining(double battery_remaining) {
		this.battery_remaining = battery_remaining;
	}

	public String getTime() {
		return decimalFormat.format(time);
	}

	public void setTime(double time) {
		this.time = time;
	}

	public String getResultDetail() {
		return resultDetail;
	}

	public void setResultDetail(String resultDetail) {
		this.resultDetail = resultDetail;
	}

	public Time getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(Time timeLimit) {
		this.timeLimit = timeLimit;
	}

	@Override
	public String toString() {
		if(exeTime == 0d && wind_speed == 0d && takeoff_alt== 0d&&battery_remaining== 0d&&time == 0d)
			return resultDetail;
		else
			return "测试结果: [执行时间=" + decimalFormat.format(exeTime) 
				+ ", 风速=" + decimalFormat.format(wind_speed) 
				+ ", 起飞高度=" + decimalFormat.format(takeoff_alt) 
				+ ", 剩余电量="+ decimalFormat.format(battery_remaining) 
				+ "%, 所用时间 =" + decimalFormat.format(time) + "]";
	}

}
