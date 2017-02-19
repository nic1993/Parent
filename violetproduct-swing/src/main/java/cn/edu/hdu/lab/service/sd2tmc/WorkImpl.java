package cn.edu.hdu.lab.service.sd2tmc;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.hdu.lab.config.StaticConfig;
import cn.edu.hdu.lab.dao.interfacedao.InterfaceIsogenySD;
import cn.edu.hdu.lab.dao.interfacedao.InterfaceSD;
import cn.edu.hdu.lab.dao.interfacedao.InterfaceUC;
import cn.edu.hdu.lab.dao.interfacedao.InterfaceUCRelation;
import cn.edu.hdu.lab.dao.tmc.State;
import cn.edu.hdu.lab.dao.tmc.Tmc;
import cn.edu.hdu.lab.dao.tmc.Transition;
import cn.edu.hdu.lab.dao.uml.SDSet;
import cn.edu.hdu.lab.dao.uml.UseCase;
import cn.edu.hdu.lab.service.consisFunc.ConWork;
import cn.edu.hdu.lab.service.interfaces.Work;
import cn.edu.hdu.lab.service.parser.InfoRetrieve;
import cn.edu.hdu.lab.service.parser.InvalidTagException;
import cn.edu.hdu.lab.service.probability.MergeProbability;
import cn.edu.hdu.lab.service.write.Write;

public class WorkImpl implements Work  {
	private List<UseCase> useCases=new ArrayList<UseCase>();
	private List<Tmc> seqTmcs=new ArrayList<Tmc>();//场景级别Markov
	private List<Tmc> ucTmcs=new ArrayList<Tmc>(); //用例级别Markov链
	private Tmc f_Tmc;//软件级别的Markov chain
	public WorkImpl(){}
	
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

	//初始化，解析UML模型的XML文件信息
	public void transInitial(String xmlFileName) throws InvalidTagException
	{
		InfoRetrieve api=new InfoRetrieve(xmlFileName);
		api.initialize();
		api.getUseCasesInfo(); //封装了所有信息
		System.out.println("useCases Informations:");
		api.print_useCases(); //输出信息
		this.useCases=api.getUseCases();
	}
	
	//根据解析信息提供用例执行顺序关系(轻量级)
	@Override
	public Map<String, List<InterfaceUCRelation>> provideUCRelation() {
		
		Map<String,List<InterfaceUCRelation>> UCRelationMap=new HashMap<String,List<InterfaceUCRelation>>();
		
		//遍历起始状态的后置用例关系
		List<InterfaceUCRelation> initialIURList=new ArrayList<InterfaceUCRelation>();
		
		InterfaceUC initialStateUC=new InterfaceUC();
		initialStateUC.setID("0");
		initialStateUC.setName("InitialState");//初始状态	
		InterfaceUC endStateUC=new InterfaceUC();
		endStateUC.setID("-1");
		endStateUC.setName("EndState");//结束状态
		
		for(UseCase uc:useCases)
		{
			if(StaticConfig.initialCondition.contains(uc.getPreCondition()))
			{
				InterfaceUC interfaceUC=new InterfaceUC();//结束用例
				interfaceUC.setID(uc.getUseCaseID());
				interfaceUC.setName(uc.getUseCaseName());	
				
				InterfaceUCRelation IUR=new InterfaceUCRelation();//用例执行顺序关系
				IUR.setStartUC(initialStateUC);
				IUR.setEndUC(interfaceUC);
				IUR.setUCRelation(IUR.getStartUC().getName()+"-"+IUR.getEndUC().getName());
				IUR.setUCRelProb(0);
				initialIURList.add(IUR);
			}
		}
		UCRelationMap.put(initialIURList.get(0).getStartUC().getName(), initialIURList);
		
		System.out.println(UCRelationMap.size()+"**"+UCRelationMap.keySet().toString());
		
		//遍历用例集合下每个用例，根据场景后置条件和用例前置条件遍历出用例执行顺序关系
		System.out.println(useCases.size());
		
		for(UseCase uc:useCases)
		{
			List<InterfaceUCRelation> IURList=new ArrayList<InterfaceUCRelation>();
			
			InterfaceUC startUC=new InterfaceUC();//起始用例
			startUC.setID(uc.getUseCaseID());
			startUC.setName(uc.getUseCaseName());	
			
			for(SDSet sd:uc.getSdSets())
			{
				for(UseCase postUC:useCases)
				{
					if(sd.getPostSD().contains(postUC.getPreCondition()))
					{
						InterfaceUC endUC=new InterfaceUC();//结束用例
						endUC.setID(postUC.getUseCaseID());
						endUC.setName(postUC.getUseCaseName());
						
						InterfaceUCRelation IUR=new InterfaceUCRelation();//用例执行顺序关系
						IUR.setStartUC(startUC);
						IUR.setEndUC(endUC);
						IUR.setUCRelation(IUR.getStartUC().getName()+"-"+IUR.getEndUC().getName());
						IUR.setUCRelProb(0);
						
						IURList.add(IUR);
					}
					if(sd.getPostSD().contains(StaticConfig.endCondition))
					{
						
						InterfaceUCRelation IUR=new InterfaceUCRelation();//用例执行顺序关系
						IUR.setStartUC(startUC);
						IUR.setEndUC(endStateUC);
						
						IUR.setUCRelation(IUR.getStartUC().getName()+"-"+IUR.getEndUC().getName());
						IUR.setUCRelProb(0);
						
						IURList.add(IUR);						
					}
				}
			}
			if(IURList.size()>0)//过滤掉没有后置用例的空集合（size=0）
			{
				UCRelationMap.put(IURList.get(0).getStartUC().getName(), IURList);
			}
			
			System.out.println(UCRelationMap.size()+"**"+UCRelationMap.keySet().toString());
		}
		
		return UCRelationMap;
	}
	
	//返回用例下的同源场景信息(轻量级信息)
	@Override
	public List<InterfaceIsogenySD> provideIsogencySD()
	{
		List<InterfaceIsogenySD> IIDList=new ArrayList<InterfaceIsogenySD>();
		for(UseCase uc:useCases)
		{
			InterfaceIsogenySD IID=new InterfaceIsogenySD();
			IID.setUcID(uc.getUseCaseID());
			IID.setUcName(uc.getUseCaseName());
			List<InterfaceSD> ISDList=new ArrayList<InterfaceSD>();
			for(SDSet sd:uc.getSdSets())
			{
				InterfaceSD ISD=new InterfaceSD();
				ISD.setID(sd.getId());
				ISD.setName(sd.getName());
				ISDList.add(ISD);
			}
			IID.setISDList(ISDList);
			IIDList.add(IID);
		}
		return IIDList;
	}
	@SuppressWarnings({ "null", "unchecked" })
	@Override
	public List<Object> calculateProb(List<double[][]> proMatrixList) {
		List<Object> resultList=new ArrayList<Object>();
		if(proMatrixList.size()>0)
		{
			if(proMatrixList.get(0).length==1)
			{
				resultList.add("size=1");
				double[] proArray={0};				
				resultList.add(proArray);
				return resultList;
			}
			else
				if(proMatrixList.get(0).length==2)
				{
					DecimalFormat f=new DecimalFormat("0.000");
					resultList.add("size=2");
					double[] proArray=new double[2];
					for(int k=1;k<proMatrixList.size();k++)
					{
						for(int i=0;i<2;i++)
						{
							for(int j=0;j<2;j++)
							{
								proMatrixList.get(0)[i][j]=proMatrixList.get(k)[i][j];
							}
						}
						
					}
					for(int i=0;i<2;i++)
					{
						for(int j=0;j<2;j++)
						{
							proMatrixList.get(0)[i][j]/=2;
						}
					}
					proArray[0]=Double.valueOf(f.format(1-1/(1+proMatrixList.get(0)[0][1])));
					proArray[1]=Double.valueOf(f.format(1/(1+proMatrixList.get(0)[0][1])));
					resultList.add(proArray);
					return resultList;
				}
				else
				{
					MergeProbability mp=new MergeProbability(proMatrixList);
					resultList=mp.mergeProbability();
					return resultList;
				}
		}
		else
		{
			resultList.add("概率矩阵输入有误，请重新输入");
			return resultList;
		}
		
	}
	
	@Override
	public void assignmentPro(List<InterfaceIsogenySD> IISDList)
	{
		for(InterfaceIsogenySD IISD:IISDList)
		{
			for(UseCase uc:useCases)
			{
				if(IISD.getUcID().equals(uc.getUseCaseID()))
				{
					for(InterfaceSD ISD:IISD.getISDList())
					{
						for(SDSet sd:uc.getSdSets())
						{
							if(ISD.getID().equals(sd.getId()))
							{
								sd.setProb(ISD.getPro());
							}
						}
					}
				}
			}
		}
	}
	
	public List transVerify() throws InvalidTagException
	{
		ConWork work=new ConWork(this.useCases);
		work.Initialize();
		@SuppressWarnings("rawtypes")
		List verifyReList= work.ConsistencyCheck(); //包含两个量：T/F、验证结果的量
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
			System.out.println((String)verifyReList.get(1));//输出验证结果提示
		}
		return verifyReList;
	}
	@Override
	public void transToMarckov(Map<String, List<InterfaceUCRelation>> UCRMap)
	{
		
		Translation translation=new Translation();
		f_Tmc=translation.UMLTranslationToMarkovChain(useCases,UCRMap); //得到软件级Markov
		
		seqTmcs=translation.getSeqTmcList();//得到场景逐步叠加的Markov
		ucTmcs=translation.getTmcs();    //得到用例Markov
		
		f_Tmc.printTmc();
	}
	
	//查看最终模型所有结点出迁移概率之和是否为一
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
