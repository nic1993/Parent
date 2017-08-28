package cn.edu.hdu.lab.config;

/**
 * 静态变量配置类
 * @author Terence
 *
 */
public class StaticConfig {
	/**
	 * @umlPath UML模型用例图路径
	 * @umlPathPrefix UML模型路径前缀：所在包名
	 * @mcPathPrefix  Markov模型路径前缀：所在包名
	 * @initialCondition  uml模型匹配的初始条件
	 * @endCondition  uml模型匹配的结束条件
	 */
	//public static String umlPath="resources\\umlModel\\ResetMapXML\\useCases.xml";
	//public static String umlPathPrefix="resources\\umlModel\\ResetMapXML\\";
	//public static String mcPathPrefix="resources\\markovModel\\mcResetMapXML\\";

             
	
	//601转换路径信息
	//电梯 带时间约束
	public static String umlPathHDU="resources\\umlModel\\leverUMLHDU\\Primary Use Cases.ucase.violet.xml";  
	//public static String umlPathHDU="resources\\umlModel\\umlCYKHDU\\Parimary.ucase.violet.xml";  
	public static String umlPathPrefixHDU="resources\\umlModel\\leverUMLHDU\\";               
	public static String mcPathPrefixHDU="resources\\markovModel\\leverMCHDU\\"; 
	
	//不带时间约束
//	public static String umlPathHDU="resources\\umlModel\\umlCYKHDU7-22\\Primary Use Cases.ucase.violet.xml";  
//	//public static String umlPathHDU="resources\\umlModel\\umlCYKHDU\\Parimary.ucase.violet.xml";  
//	public static String umlPathPrefixHDU="resources\\umlModel\\umlCYKHDU7-22\\";               
//	public static String mcPathPrefixHDU="resources\\markovModel\\mcCYKXml7-22\\"; 
	
	
	//EA转换路径信息
	public static String umlPath="resources\\umlModel\\zcUML8-17\\Primary Use Cases.xml";  
	public static String umlPathPrefix="resources\\umlModel\\zcUML8-17\\";               
	public static String mcPathPrefix="resources\\markovModel\\zcMC8-17\\"; 
	
//	public static String umlPath="resources\\umlModel\\电梯模拟1\\Primary Use Cases.xml";
//	public static String umlPathPrefix="resources\\umlModel\\电梯模拟1\\";               
//	public static String mcPathPrefix="resources\\markovModel\\电梯模拟1\\";
	
	//
	public static String initialCondition="Correctiveness";                     
	public static String endCondition="SoftwareFinished";
	
	public static String[] fragmentsTypes={"loop","opt","alt","par"};//顺序不可改变 {"loop"}{"opt"}{"alt","par"}

	
}
