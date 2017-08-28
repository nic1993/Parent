package cn.edu.hdu.lab.service.consisFunc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.edu.hdu.lab.dao.condao.ConSD;
import cn.edu.hdu.lab.dao.condao.ConUseCase;
import cn.edu.hdu.lab.dao.uml.SD;
import cn.edu.hdu.lab.dao.uml.UseCase;

public class ConInfoParse {
	
	public List<ConUseCase> conInfoParse(List<UseCase> useCases)
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
			if(useCase.getSdSets()!=null)
			{
				for(Iterator<SD> newIt=useCase.getSdSets().iterator();newIt.hasNext();)
				{
					SD sdSet=newIt.next();
					conSD=new ConSD();
					conSD.setID(sdSet.getId());
					conSD.setName(sdSet.getName());
					conSD.setProb(sdSet.getProb());
					conSD.setPostCondition(sdSet.getPostSD());
					conUseCase.addconSD(conSD);
					
				}
			}

			conUseCase.setPreCondition(useCase.getPreCondition());
			conUseCases.add(conUseCase);				
		}
		return conUseCases;
	}
}
