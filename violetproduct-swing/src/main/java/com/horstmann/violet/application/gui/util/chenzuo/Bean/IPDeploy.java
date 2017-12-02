package com.horstmann.violet.application.gui.util.chenzuo.Bean;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class IPDeploy {

	//
	private static BlockingQueue waitQueue = new LinkedBlockingQueue(4);

	//list of ip
	private List<IPNode> ips = new ArrayList<>();
	
	public IPDeploy(){
		buildFromProperties();
	}

    public List<IPNode> findNodeFree(int index){
		List<IPNode> nodes = new ArrayList<>(index);
		int i=1;
		for(IPNode node:ips){
			if(i<=index && !node.isBusy()){
				node.setBusy(true);
				nodes.add(node);
				i++;
			}else{
				break;
			}
		}
		return nodes;
	}

	//read properties
	private void buildFromProperties() {
		Properties prop = new Properties();
		Pair<String,String> rPair = new Pair<String,String>();
		try {
            // read .properties
			InputStream in = new BufferedInputStream(new FileInputStream("src/ip.properties"));
            // load list
			prop.load(in);
			Iterator<String> it = prop.stringPropertyNames().iterator();
			while (it.hasNext()) {
				String key = it.next();
				String value = prop.getProperty(key);
				//read ips
				if(key.equals("ip")) {
					String[] tmpL = value.split(",");
					for(String r:tmpL) {
						IPNode node = new IPNode(r);
						ips.add(node);
					}
				}
			}
			in.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public List<IPNode> getIps() {
		return ips;
	}

	//test 
	public static void main(String[] args) {
		IPDeploy p = new IPDeploy();
	}
}
