package com.horstmann.violet.application.gui.util.tiancai.markov;

import java.util.ArrayList;
import java.util.List;

public class ConFunc {

	/**
	 * @param args
	 * @throws InvalidTagException 
	 */
	public static void main(String[] args) throws InvalidTagException {
		ConWork work=new ConWork();
		work.Initialize();
		
		//List verifyReList= new ArrayList(); //包含两个量：T/F、验证结果的量
		//verifyReList=work.ConsistencyCheck();
		
		/*boolean result=work.ConsistencyCheck();
		if(result)
		{
			System.out.println("该UML模型满足一致性要求！可转化为对应的Markov链使用模型！");
		}
		else
		{
			System.out.println("UML模型不满足一致性要求！");
		}
		
		
		if(verifyReList!=null)
		{
			if((boolean)verifyReList.get(0)==true)
			{
				System.out.println("该UML模型满足一致性要求！可转化为对应的Markov链使用模型！");
			}
			else
			{
				System.out.println("UML模型不满足一致性要求！");
			}
			
			System.out.println((String)verifyReList.get(1));
		}
		*/
		
	}

}
