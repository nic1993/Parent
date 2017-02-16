package com.horstmann.violet.application.gui.util.tiancai.markov;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConInfoParse {
	
	public static List<ConUseCase> conInfoParse(List<UseCase> useCases)
	{
		List<ConUseCase> conUseCases=new ArrayList<ConUseCase>();
		for(Iterator<UseCase> it=useCases.iterator();it.hasNext();)
		{
			UseCase useCase=it.next();
			
			ConUseCase conUseCase=new ConUseCase();
			conUseCase.setID(useCase.getUseCaseID());
			conUseCase.setUseCaseName(useCase.getUseCaseName());
			conUseCase.setProb(useCase.getUseCasePro());
			List<ConSD> conSDs=new ArrayList<ConSD>();
			ConSD conSD;
			for(Iterator<SDSet> newIt=useCase.getSdSets().iterator();newIt.hasNext();)
			{
				SDSet sdSet=newIt.next();
				conSD=new ConSD();
				conSD.setID(sdSet.getId());
				conSD.setName(sdSet.getName());
				conSD.setProb(sdSet.getProb());
				conSD.setPostCondition(sdSet.getPostSD());
				conUseCase.addconSD(conSD);
				
			}
			conUseCase.setPreCondition(useCase.getPreCondition());
			conUseCases.add(conUseCase);				
		}
		return conUseCases;
	}
}
