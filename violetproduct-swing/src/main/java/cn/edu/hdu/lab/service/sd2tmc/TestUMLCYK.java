package cn.edu.hdu.lab.service.sd2tmc;

import java.util.ArrayList;
import java.util.List;

import cn.edu.hdu.lab.config.StaticConfig;
import cn.edu.hdu.lab.dao.uml.UseCase;
import cn.edu.hdu.lab.service.parserHDU.XMLReaderHDU;

public class TestUMLCYK {

	public static void main(String[] args) throws Throwable {
		List<UseCase> useCases=new ArrayList<UseCase>();
		XMLReaderHDU reader=new XMLReaderHDU(StaticConfig.umlPathHDU);
		try {
			useCases=reader.parser();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{			
			for(UseCase uc:useCases)
			{
				uc.print_useCase();
			}
		}

	}

}
