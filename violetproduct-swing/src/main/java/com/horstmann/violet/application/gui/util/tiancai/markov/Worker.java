package com.horstmann.violet.application.gui.util.tiancai.markov;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Worker {
	private List<UseCase> useCases=new ArrayList<UseCase>();
	private List<Tmc> seqTmcs=new ArrayList<Tmc>();//场景级别Markov
	private List<Tmc> ucTmcs=new ArrayList<Tmc>(); //用例级别Markov链
	private Tmc f_Tmc;//软件级别的Markov chain
	public Worker(){}
	
	public List<UseCase> getUseCases() {
		return useCases;
	}

	public void setUseCases(List<UseCase> useCases) {
		this.useCases = useCases;
	}


	public Tmc getF_Tmc() {
		return f_Tmc;
	}


	public void setF_Tmc(Tmc f_Tmc) {
		this.f_Tmc = f_Tmc;
	}


	public void transInitial(String xmlFileName) throws InvalidTagException
	{
		InfoRetrieve api=new InfoRetrieve(xmlFileName);
		api.initialize();
		api.getUseCasesInfo(); //封装了所有信息
		System.out.println("useCases Informations:");
		api.print_useCases(); //输出信息
		this.useCases=api.getUseCases();
	}
	public void transVerify() throws InvalidTagException
	{
		ConWork work=new ConWork(this.useCases);
		work.Initialize();
		@SuppressWarnings("rawtypes")
		List verifyReList= work.ConsistencyCheck(); //包含两个量：T/F、验证结果的量
		if(verifyReList!=null)
		{
			if((Boolean)verifyReList.get(0)==true)
			{
				System.out.println("该UML模型满足一致性要求！可转化为对应的Markov链使用模型！");
			}
			else
			{
				System.out.println("UML模型不满足一致性要求！");
			}
			System.out.println((String)verifyReList.get(1));//输出验证结果提示
		}
	}
	
	public void transToMarckov()
	{
		Translation translation=new Translation();
		f_Tmc=translation.UMLTranslationToMarkovChain(useCases); //得到软件级Markov
		
		seqTmcs=translation.getSeqTmcList();//得到场景逐步叠加的Markov
		ucTmcs=translation.getTmcs();    //得到用例Markov
		
		f_Tmc.printTmc();
	}
	public void probabilityTest()
	{
		double proSum=0;
		for(State state:f_Tmc.getStates())
		{
			proSum=0;
			for(Transition tr:f_Tmc.getTransitions())
			{
				if(tr.getFrom().equals(state))
				{
					proSum+=tr.getTransFlag().getProb();
				}
			}
			if(proSum!=1.0)
			{
				System.out.println(state.getName());
			}
		}
	}
	public void writeMarkov(String mcXMLFileName) throws IOException
	{
		String McName="MarkovChainModel";
		int count=1;
		for(Tmc tmc:seqTmcs)
		{
			String fileName=mcXMLFileName+"Seq_"+McName+count+".xml";
			Write.writeMarkov2XML(tmc, fileName);
			count++; 
		}
		count=1;
		for(Tmc tmc:ucTmcs) //用例级别
		{
			String fileName=mcXMLFileName+"UC_"+McName+count+".xml";
			Write.writeMarkov2XML(tmc,fileName);
			count++;
		}
		
		Write.writeMarkov2XML(f_Tmc,(mcXMLFileName+"Sofeware_"+McName+".xml"));   
	}
	
	
}
