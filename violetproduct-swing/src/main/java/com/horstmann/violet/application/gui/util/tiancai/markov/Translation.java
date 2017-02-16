package com.horstmann.violet.application.gui.util.tiancai.markov;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Translation {

	//private String startId=null,endId=null;
	private SDSet sdSet;
	private List<SDSet> sdSets=new ArrayList<SDSet>();
	private Tmc tmc;
	private List<Tmc> tmcs = new ArrayList<Tmc>();
	private List<Tmc> seqTmcList=new ArrayList<Tmc>();
	
	private State currentState=new State();
	private State start=new State("S0");
	private List<State> finalStates;
	
	private List<Message> messages;
	
	//private int k=0;
	private int count=0;
	
	Tmc F_Tmc=new Tmc();
	
	private static String MCName="MarkovChainModel";
	private static int seqCount=1;
	private static int ucCount=1;
	
	public Translation(){}
	@SuppressWarnings("rawtypes")
	public Tmc UMLTranslationToMarkovChain(List<UseCase> useCases)
	{
		/*
		 * 用例有前置条件和使用概率，根据每个用例的前置条件找到第一个用例
		 */
		for(UseCase useCase:useCases)
		{
			if(useCase.getSdSets()!=null)
			{	
				tmc=new Tmc();
				tmc.setTmcType("UC_Markov");
				tmc.setNames(useCase.getUseCaseName());
				tmc.setOwned("Software");
				finalStates=new ArrayList<State>();
				tmc.setNotation(useCase.getPreCondition());
				tmc.setPro(useCase.getUseCasePro());          //用例的使用概率
				if(useCase.equals(useCases.get(0)))
				{
					start.setTmcID("0");
					start.setName("S0");
					start.setLabel("start");
					tmc.setStart(this.start);
					tmc.statesAddState(start);
					count++;
					this.currentState=start;
				}
				else
				{
					State state=createNewState();
					tmc.setStart(state);
					start=state;//赋给全局变量，便于后续的应用；
					tmc.statesAddState(state);
					count++;
					this.currentState=state;
				}
				
				sdSets=useCase.getSdSets();
				sdSet=sdSets.get(0);
										
					messages=null;
					messages=sdSet.getMessages();
					for(Message message:messages)
					{
						if(message.isTranslationed()==true)
							continue;
						message.setProb(sdSet.getProb());
						if(message.isInFragment()==false)
						{
							State state=createNewState();
							if(message.isLast()==true)
							{
								state.setLabel("Final");
								state.setNotation(sdSet.getPostSD());
								finalStates.add(state);
							}
							TransFlag transFlag=new TransFlag(message);
							Transition transition=createTransition(state,transFlag); 
							transition.setNotation(null);
							transition.setNote(null);
							tmc.statesAddState(state);
							tmc.transitionsAddtransition(transition);
							currentState=state;//指针下移
							count++; 
							message.setTranslationed(true);
						}
						if(message.isInFragment()==true)
						{
							
							if(sdSet.getFragments()!=null)
							{
								
								for(Fragment frag:sdSet.getFragments())
								{
									if(frag.isTranslationed())
										continue;
									//消息可能在深层组合片段，所以需要寻找消息所在组合片段的路径
									List<String> fragmentIds=retrieveFragmentIds(frag,message);
									if(fragmentIds!=null)
									{
										//转化该组合片段
										State tempState = new State();
										tempState = currentState;
										//找到
										List sdList=fragmentTranslation(frag,tempState);
										message.setTranslationed(true);
										frag.setTranslationed(true);
										State tempLastState=(State)sdList.get(0);
										Message tempLastMessage=(Message)sdList.get(1);
										if(tempLastMessage.equals(messages.get(messages.size()-1)))
										{
											tempLastState.setLabel("LastFinal");
											tempLastState.setNotation(sdSet.getPostSD());
											finalStates.add(tempLastState);
										}
										break;
									}
								}
							}
						}
						//第一个用例的第一个顺序图执行完成
						
					}
					
					System.out.println("\n~~~~~~~~~MC~~~~~~~~~~~~~~");
					Tmc seqTmc=new Tmc();
					this.assignmentTmc(seqTmc, tmc);
					
					seqTmc.setTmcType("Seq_Markov");
					seqTmc.setNames(sdSet.getName());
					seqTmc.setOwned(useCase.getUseCaseName());

					//动态存取MC的XML文件
					writeSeqMC(seqTmc);
					seqTmcList.add(seqTmc);//添加到场景markov模型集合当中
					
					sdSetsMergeMarkov();//生成用例级别的Markov chain
					tmc.setEnds(finalStates);
					
					tmc.printTmc();
					 
					writeUcMC(tmc);
					tmcs.add(tmc);
					/*
					 * 对集成后的All用例级Markov链中的迁移做归一化处理
					 * 
					 *归一化原则：使链中每个状态的出边满足概率和为1.0
					 *
					 */
					normalizeOnProbability();
				}
		} //所有用例执行结束；
		SoftwareMergeMarkov(tmcs); //生成软件Markov链
		
		//System.out.println("………………………输出软件级Markov链使用模型……………………………………………");
		//F_Tmc.printTmc();//输出软件级Markov链使用模型
		
		//将Markov 链的终止状态归一
		unifyFinalStates();
		writeSoftwareMC(F_Tmc);
		return F_Tmc;		
	}
	
	public State createNewState()
	{
		State state=new State();
		String id=count+"";
		state.setTmcID(id);
		state.setName("S"+id);
		return state;
	}
	public Transition createTransition(State state,TransFlag transFlag)
	{
		Transition transition=new Transition();
		transition.setFrom(currentState);
		transition.setTo(state);
		transition.setTransFlag(transFlag);
		return transition;
	}
	//将剩下的顺序图添加到马尔科夫链上
	public void sdSetsMergeMarkov()
	{
		if(sdSets.size()<2)
			System.out.println("用例只有一个顺序图");
		else
		{
			for(int i=1;i<sdSets.size();i++)
			{
				sdSet=sdSets.get(i);
				currentState=tmc.getStart();//指针移动到链首；
				System.out.println();
				messages=null;
				messages=sdSet.getMessages();
				for (Message message : messages)
				{
					if(message.isTranslationed()==true)
						continue;
					message.setProb(sdSet.getProb());//场景执行概率赋给场景中消息的概率
					if (message.isInFragment()==false) 
					{ 
						notFragmentTranslationIntegate(message);
					}
					else 
					{
						//利用该信息获得组合所在组合片段消息
						if(sdSet.getFragments()!=null)
						{
							for(Fragment frag:sdSet.getFragments())
							{
								if(frag.isTranslationed())
									continue;
								//消息可能在深层组合片段，所有需要寻找消息所在组合片段的路径
								List<String> fragmentIds=retrieveFragmentIds(frag,message);
								if(fragmentIds!=null)
								{
									//转化该组合片段
									State tempState = new State();
									tempState = currentState;//指向当前结点
							
									@SuppressWarnings("rawtypes")
									List sdList=fragmentTranslationIntegate(frag,tempState);
									message.setTranslationed(true);
									frag.setTranslationed(true);
									State tempLastState=(State)sdList.get(0);
									Message tempLastMessage=(Message)sdList.get(1);
									if(tempLastMessage.equals(messages.get(messages.size()-1)))
									{
										tempLastState.setLabel("Final---Last");
										tempLastState.setNotation(sdSet.getPostSD());
										finalStates.add(tempLastState);
									}
									break;
								}
							}
						}
					}
				} 
				Tmc seqTmc=new Tmc();
				this.assignmentTmc(seqTmc, tmc);
				seqTmc.setTmcType("Seq_Markov");
				seqTmc.setOwned(tmc.getNames());
				seqTmc.setNames(seqTmcList.get(seqTmcList.size()-1).getNames()+"; "+sdSet.getName());
				
				seqTmcList.add(seqTmc);
				
				writeSeqMC(seqTmc);
			}
		}
	}
 
	//如果消息不在组合片段中
	public void notFragmentTranslationIntegate(Message message)
	{
		boolean issame = false;
		Iterator<Transition> transiterator = tmc.getTransitions().iterator();
		while (transiterator.hasNext()) 
		{
			Transition transition = transiterator.next();
			if (currentState.equals(transition.getFrom())
					&&message.getSender().equals(transition.getTransFlag().getSender())
					&& message.getReceiver().equals(transition.getTransFlag().getReceiver())
					&& message.getName().equals(transition.getTransFlag().getName())) 
			{
				transition.getTransFlag().setProb(transition.getTransFlag().getProb() + message.getProb());
				currentState = transition.getTo();
				issame = true;
				break;
			}
		}
		if (!issame) 
		{
			State state = createNewState();
			TransFlag transFlag=new TransFlag(message);
			Transition newTransition = createTransition(state,transFlag);
			newTransition.setNotation(null);
			if (message.isLast()) 
			{
				state.setLabel("Final");
				state.setNotation(sdSet.getPostSD());
				finalStates.add(state);
			}
			tmc.statesAddState(state);
			tmc.transitionsAddtransition(newTransition);
			currentState = newTransition.getTo();
			count++;      //全局的
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List fragmentTranslationIntegate(Fragment frag,State tempState)
	{
		Message lastMessage=null;
		State finalState = null;
		
		if (frag.getType().equals("alt")) 
		{
			List<Message> currentMessageList = retrieveCurrentMessages(frag);
			
			for (Operand oper : frag.getOperands()) 
			{
				List<Message> operMessages = new ArrayList<Message>();
				operMessages.addAll(retrieveOperandMessages(currentMessageList,	oper));
				
				for (Message mess : operMessages) 
				{
					if(mess.isTranslationed()==true)
						continue;
					
					// 该消息在当前操作中，直接转化
					if (mess.getOperandId().equals(oper.getId())==true) 
					{
						
						boolean issame = false;
						Iterator<Transition> transiterator = tmc.getTransitions().iterator();
						
						while (transiterator.hasNext())
						{
							Transition transition = transiterator.next();
							
							if (currentState.equals(transition.getFrom())
									&& mess.getSender().equals(transition.getTransFlag().getSender())
									&& mess.getReceiver().equals(transition.getTransFlag().getReceiver())
									&& mess.getName().equals(transition.getTransFlag().getName())) 
							{
								
								transition.getTransFlag().setProb(transition.getTransFlag().getProb() + mess.getProb());
								mess.setTranslationed(true);
								currentState = transition.getTo();
								issame = true;
								if(mess.equals(operMessages.get(operMessages.size()-1)))
								{
									
									lastMessage=mess;
									Transition nextTr=tmc.getTransitions().get(tmc.getTransitions().indexOf(transition)+1);
									finalState=nextTr.getTo();
									if(oper.equals(frag.getOperands().get(frag.getOperands().size()-1)))
										currentState=nextTr.getTo();
									else
										currentState=tempState;
									
								}
								break;
							}
						}
						if(issame==false)
						{	
							//仅仅是入边…
							if(mess.equals(operMessages.get(0))==true && mess.equals(operMessages.get(operMessages.size()-1))==false)
							{
								currentState=tempState;
								State state=createNewState();
								if(mess.isLast()==true)
								{
									state.setLabel("Final");
									state.setNotation(sdSet.getPostSD());
									finalStates.add(state);
								}
								TransFlag transFlag=new TransFlag(mess);
								Transition transition=createTransition(state,transFlag); 
								
								transition.setNotation(transition.getNotation()+"inAlt");//添加入边
							
								transition.setNote(oper.getCondition());//添加执行条件
								tmc.statesAddState(state);
								tmc.transitionsAddtransition(transition);
								currentState=state;//指针下移
								count++; 
							}
							//非入非出
							if(mess.equals(operMessages.get(0))==false && mess.equals(operMessages.get(operMessages.size()-1))==false)
							{
								State state=createNewState();
								if(mess.isLast()==true)
								{
									state.setLabel("Final");
									state.setNotation(sdSet.getPostSD());
									finalStates.add(state);
								}
								TransFlag transFlag=new TransFlag(mess);
								Transition transition=createTransition(state,transFlag); 
								transition.setNote(oper.getCondition());//添加执行条件
								tmc.statesAddState(state);
								tmc.transitionsAddtransition(transition);
								currentState=state;//指针下移
								count++; 
							}
							//是入边亦是出边
							if(mess.equals(operMessages.get(0))==true && mess.equals(operMessages.get(operMessages.size()-1))==true)
							{
								
								currentState=tempState;
								lastMessage=mess;
									State state=createNewState();
									if(mess.isLast()==true)
									{
										state.setLabel("Final");
										state.setNotation(sdSet.getPostSD());
										finalStates.add(state);
									}
									TransFlag transFlag=new TransFlag(mess);
									Transition transition=createTransition(state,transFlag); 
									
									transition.setNotation(transition.getNotation()+"inAlt"+"-outAlt");//添加入边和出边
									transition.setNote(oper.getCondition());//添加执行条件
									tmc.statesAddState(state);
									tmc.transitionsAddtransition(transition);
									count++; 
									currentState=state;
									
									//未状态为空是要建立未状态，便于后续归一操作末状态；
									if(finalState==null)
									{
										//加入末状态和空迁移，空迁移执行概率为1，自动滑落到末状态
										State outState=createNewState();
										TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
										
										Transition outTransition=createTransition(outState,outTransFlag); 
										outTransition.setNotation(null);//空边
										outTransition.setNote(null);//无条件执行
										
										tmc.statesAddState(outState);
										tmc.transitionsAddtransition(outTransition);
										count++;
										finalState=outState;
									}
									//末状态不为空表示执行到下面的操作，需要操作末状态归一处理，不用插入状态，直接建立迁移指向finalState，
									else
									{
										
										TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
										Transition outTransition=createTransition(finalState,outTransFlag); 
										outTransition.setNotation(null);//空边
										outTransition.setNote(null);//无条件执行
										tmc.transitionsAddtransition(outTransition);
									}
									
									if(oper.equals(frag.getOperands().get(frag.getOperands().size()-1)))
										{
										  currentState=finalState;
										}
									else
										{
										 currentState=tempState;
										}
									
							}
							//仅仅是出边
							if(mess.equals(operMessages.get(0))==false && mess.equals(operMessages.get(operMessages.size()-1))==true)
							{
								
								lastMessage=mess;
								State state=createNewState();
								if(mess.isLast()==true)
								{
									state.setLabel("Final");
									state.setNotation(sdSet.getPostSD());
									finalStates.add(state);
								}
								TransFlag transFlag=new TransFlag(mess);
								Transition transition=createTransition(state,transFlag); 
								
								transition.setNotation(transition.getNotation()+"outAlt");//添加入边和出边
								transition.setNote(oper.getCondition());//添加执行条件
								
								tmc.statesAddState(state);
								tmc.transitionsAddtransition(transition);
								count++; 
								currentState=state;
								
								//未状态为空是要建立未状态，便于后续归一操作末状态；
								if(finalState==null)
								{
									//加入末状态和空迁移，空迁移执行概率为1，自动滑落到末状态
									State outState=createNewState();
									TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
									
									Transition outTransition=createTransition(outState,outTransFlag); 
									outTransition.setNotation(null);//空边
									outTransition.setNote(null);//无条件执行
									
									tmc.statesAddState(outState);
									tmc.transitionsAddtransition(outTransition);
									count++;
									finalState=outState;
								}
								//末状态不为空表示执行到下面的操作，需要操作末状态归一处理，不用插入状态，直接建立迁移指向finalState，
								else
								{
									TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
									Transition outTransition=createTransition(finalState,outTransFlag); 
									outTransition.setNotation(null);//空边
									outTransition.setNote(null);//无条件执行
									tmc.transitionsAddtransition(outTransition);
								}
								
								if(oper.equals(frag.getOperands().get(frag.getOperands().size()-1)))
									currentState=finalState;
								else
									currentState=tempState;
								
							}
							mess.setTranslationed(true);
						}
					}
					 else // 不在当前组合片段den当前操作中，用该消息获取深层组合片段和对应消息
					{
							State newTempState=currentState;
							State newFinalState=new State();
							if(oper.getFragments()!=null)
							{
								for(Fragment newFrag:oper.getFragments())
								{
									//消息可能在深层组合片段，所有需要寻找消息所在组合片段的路径
									List<String> fragmentIds=retrieveFragmentIds(newFrag,mess);
									if(fragmentIds!=null)
									{
										if(newFrag.isTranslationed()==true)
											continue;
										//转化该组合片段
										List newList=fragmentTranslationIntegate(newFrag,newTempState);
										newFinalState=(State)newList.get(0);
										Message tempMessage=(Message)newList.get(1);
										newFrag.setTranslationed(true);
										//如果不是最后一条消息，则继续遍历
										//如果是最后一条消息(没有后继消息)，则做如下操作
										if(tempMessage.equals(operMessages.get(operMessages.size()-1)))
										{
											lastMessage=tempMessage;
											if(oper.equals(frag.getOperands().get(0)))
											{
												boolean tag=false;
												for(Transition tr:tmc.getTransitions())
												{
													String str=tr.getNotation();
													if(str==null)
														str="str";
													if(tr.getFrom().equals(newFinalState)
															&&tr.getTransFlag().getName()==null
															&&tr.getTransFlag().getProb()==1.0
															&&!str.equals("backLoop"))
													{
														finalState=tr.getTo();//找到原有的末状态//空迁移已经存在，不需要再建立了
														currentState=tempState;
														tag=true;
														break;
													}
												}	
												if(!tag)//如果是新生成的操作分支
												{
													State outState=createNewState();
													TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
													
													Transition outTransition=createTransition(outState,outTransFlag); 
													outTransition.setNotation(null);//空边
													outTransition.setNote(null);//无条件执行
													
													tmc.statesAddState(outState);
													tmc.transitionsAddtransition(outTransition);
													count++;
													finalState=outState;
												}
												
											}
											else
											{
												//如果是新生成的操作路径，则需要建立空迁移连接到finalState
												//
												//否则，空迁移已经存在,CS自动滑落到末状态
												//(最后一条操作路径CS移到finalState，中间操作路径CS移动到tempState)
												//
												boolean tag=false;
												for(Transition tr:tmc.getTransitions())
												{
													String str=tr.getNotation();
													if(str==null)
														str="str";
													if(tr.getFrom().equals(newFinalState)
															&&tr.getTransFlag().getName()==null
															&&tr.getTransFlag().getProb()==1.0
															&&!str.equals("backLoop"))
													{
														tag=true;
														break;
													}
													
												}
												if(!tag)
												{
													TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
													Transition outTransition=createTransition(finalState,outTransFlag); 
													outTransition.setNotation(null);//空边
													outTransition.setNote(null);//无条件执行
													tmc.transitionsAddtransition(outTransition);
													
												}
												if(oper.equals(frag.getOperands().get(frag.getOperands().size()-1)))
												{
													currentState=finalState;
												}
												else
												{
													currentState=tempState;
												}
													
											}
										}
										break;
									}
								}
							}
						}// 不在当前组合片段中
					}//消息遍历结束
			 }
		}
		if(frag.getType().equals("par"))
		{
			List<Message> currentMessageList = retrieveCurrentMessages(frag);
			for (Operand oper : frag.getOperands()) 
			{
				List<Message> operMessages = new ArrayList<Message>();
				operMessages.addAll(retrieveOperandMessages(currentMessageList,	oper));
				for (Message mess : operMessages) 
				{
					
					if(mess.isTranslationed()==true)
						continue;
					// 该消息在当前组合操作中，直接转化
					if (mess.getOperandId().equals(oper.getId())==true) 
					{
						boolean issame = false;
						Iterator<Transition> transiterator = tmc.getTransitions().iterator();
						while (transiterator.hasNext()) 
						{
							Transition transition = transiterator.next();
							if (currentState.equals(transition.getFrom())
									&&mess.getSender().equals(transition.getTransFlag().getSender())
									&& mess.getReceiver().equals(transition.getTransFlag().getReceiver())
									&& mess.getName().equals(transition.getTransFlag().getName())) 
							{
								transition.getTransFlag().setProb(transition.getTransFlag().getProb() + mess.getProb());
								mess.setTranslationed(true);
								currentState = transition.getTo();
								issame = true;
								if(mess.equals(operMessages.get(operMessages.size()-1)))
								{
									lastMessage=mess;
									Transition nextTr=tmc.getTransitions().get(tmc.getTransitions().indexOf(transition)+1);
									finalState=nextTr.getTo();
									if(oper.equals(frag.getOperands().get(frag.getOperands().size()-1)))
										currentState=nextTr.getTo();
									else
										currentState=tempState;
								}
								break;
							}
						}
					if(!issame)
					{
							//如果是当前操作的入边……
							if(mess.equals(operMessages.get(0))==true && mess.equals(operMessages.get(operMessages.size()-1))==false)
							{
								
								State state=createNewState();
								if(mess.isLast()==true)
								{
									state.setLabel("Final");
									state.setNotation(sdSet.getPostSD());
									finalStates.add(state);
								}
								TransFlag transFlag=new TransFlag(mess);
								Transition transition=createTransition(state,transFlag); 
								
								transition.setNotation(transition.getNotation()+"inPar");//添加入边
								transition.setNote(oper.getCondition());//添加执行条件
								tmc.statesAddState(state);
								tmc.transitionsAddtransition(transition);
								currentState=state;//指针下移
								count++; 
							}
							//非入非出
							if(mess.equals(operMessages.get(0))==false && mess.equals(operMessages.get(operMessages.size()-1))==false)
							{
								State state=createNewState();
								if(mess.isLast()==true)
								{
									state.setLabel("Final");
									state.setNotation(sdSet.getPostSD());
									finalStates.add(state);
								}
								TransFlag transFlag=new TransFlag(mess);
								Transition transition=createTransition(state,transFlag); 
								
								transition.setNote(oper.getCondition());//添加执行条件
								tmc.statesAddState(state);
								tmc.transitionsAddtransition(transition);
								currentState=state;//指针下移
								count++; 
								
							}
							//是入边亦是出边
							if(mess.equals(operMessages.get(0))==true && mess.equals(operMessages.get(operMessages.size()-1))==true)
							{
								lastMessage=mess;
									State state=createNewState();
									if(mess.isLast()==true)
									{
										state.setLabel("Final");
										state.setNotation(sdSet.getPostSD());
										finalStates.add(state);
									}
									TransFlag transFlag=new TransFlag(mess);
									Transition transition=createTransition(state,transFlag); 
									
									transition.setNotation(transition.getNotation()+"inPar"+"-outPar");//添加入边和出边
									transition.setNote(oper.getCondition());//添加执行条件
									tmc.statesAddState(state);
									tmc.transitionsAddtransition(transition);
									count++; 
									currentState=state;
									
									//未状态为空是要建立未状态，便于后续归一操作末状态；
									if(finalState==null)
									{
										//加入末状态和空迁移，空迁移执行概率为1，自动滑落到末状态
										State outState=createNewState();
										TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
										
										Transition outTransition=createTransition(outState,outTransFlag); 
										outTransition.setNotation(null);//空边
										outTransition.setNote(null);//无条件执行
										
										tmc.statesAddState(outState);
										tmc.transitionsAddtransition(outTransition);
										count++;
										finalState=outState;
									}
	
									//末状态不为空表示执行到下面的操作，需要操作末状态归一处理，不用插入状态，直接建立迁移指向finalState，
									else
									{
										
										TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
										Transition outTransition=createTransition(finalState,outTransFlag); 
										outTransition.setNotation(null);//空边
										outTransition.setNote(null);//无条件执行
										tmc.transitionsAddtransition(outTransition);
									}
									
									if(oper.equals(frag.getOperands().get(frag.getOperands().size()-1)))
										{
										  currentState=finalState;
										}
									else
										{
										 currentState=tempState;
										}
									
									//return finalState;
									
							}
							//仅仅是出边
							if(mess.equals(operMessages.get(0))==false && mess.equals(operMessages.get(operMessages.size()-1))==true)
							{
								lastMessage=mess;
								State state=createNewState();
								if(mess.isLast()==true)
								{
									state.setLabel("Final");
									state.setNotation(sdSet.getPostSD());
									finalStates.add(state);
								}
								TransFlag transFlag=new TransFlag(mess);
								Transition transition=createTransition(state,transFlag); 
								
								transition.setNotation(transition.getNotation()+"outPar");//添加入边和出边
								transition.setNote(oper.getCondition());//添加执行条件
								
								tmc.statesAddState(state);
								tmc.transitionsAddtransition(transition);
								count++; 
								currentState=state;
								
								//未状态为空是要建立未状态，便于后续归一操作末状态；
								if(finalState==null)
								{
									//加入末状态和空迁移，空迁移执行概率为1，自动滑落到末状态
									State outState=createNewState();
									TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
									
									Transition outTransition=createTransition(outState,outTransFlag); 
									outTransition.setNotation(null);//空边
									outTransition.setNote(null);//无条件执行
									
									tmc.statesAddState(outState);
									tmc.transitionsAddtransition(outTransition);
									count++;
									finalState=outState;
								}
	
								//末状态不为空表示执行到下面的操作，需要操作末状态归一处理，不用插入状态，直接建立迁移指向finalState，
								else
								{
									TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
									Transition outTransition=createTransition(finalState,outTransFlag); 
									outTransition.setNotation(null);//空边
									outTransition.setNote(null);//无条件执行
									tmc.transitionsAddtransition(outTransition);
								}
								
								if(oper.equals(frag.getOperands().get(frag.getOperands().size()-1)))
									currentState=finalState;
								else
									currentState=tempState;
							}
							mess.setTranslationed(true);
						}
					}
					else  // 不在当前操作中，用该消息获取深层组合片段和对应消息
					{
						
						State newTempState=currentState;
						State newFinalState=new State();
						if(oper.getFragments()!=null)
						{
							for(Fragment newFrag:oper.getFragments())
							{
								//消息可能在深层组合片段，所有需要寻找消息所在组合片段的路径
								List<String> fragmentIds=retrieveFragmentIds(newFrag,mess);
								if(fragmentIds!=null)
								{
									if(newFrag.isTranslationed()==true)
										continue;
									//转化该组合片段
									List newList=fragmentTranslationIntegate(newFrag,newTempState);
									newFinalState=(State)newList.get(0);
									Message tempMessage=(Message)newList.get(1);
									newFrag.setTranslationed(true);
									//如果不是最后一条消息，则继续遍历
									//如果是最后一条消息(没有后继消息)，则做如下操作
									if(tempMessage.equals(operMessages.get(operMessages.size()-1)))
									{
										lastMessage=tempMessage;
										if(oper.equals(frag.getOperands().get(0)))
										{
											boolean tag=false;
											for(Transition tr:tmc.getTransitions())
											{
												String str=tr.getNotation();
												if(str==null)
													str="str";
												if(tr.getFrom().equals(newFinalState)
														&&tr.getTransFlag().getName()==null
														&&tr.getTransFlag().getProb()==1.0
														&&!str.equals("backLoop"))
												{
													finalState=tr.getTo();//找到原有的末状态//空迁移已经存在，不需要再建立了
													currentState=tempState;
													tag=true;
													break;
												}
											}	
											if(!tag)//如果是新生成的操作分支
											{
												State outState=createNewState();
												TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
												
												Transition outTransition=createTransition(outState,outTransFlag); 
												outTransition.setNotation(null);//空边
												outTransition.setNote(null);//无条件执行
												
												tmc.statesAddState(outState);
												tmc.transitionsAddtransition(outTransition);
												count++;
												finalState=outState;
											}
											
										}
										else
										{
											//如果是新生成的操作路径，则需要建立空迁移连接到finalState
											//
											//否则，空迁移已经存在,CS自动滑落到末状态
											//(最后一条操作路径CS移到finalState，中间操作路径CS移动到tempState)
											//
											boolean tag=false;
											for(Transition tr:tmc.getTransitions())
											{
												String str=tr.getNotation();
												if(str==null)
													str="str";
												if(tr.getFrom().equals(newFinalState)
														&&tr.getTransFlag().getName()==null
														&&tr.getTransFlag().getProb()==1.0
														&&!str.equals("backLoop"))
												{
													tag=true;
													break;
												}
											}
											if(!tag)
											{
												TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
												Transition outTransition=createTransition(finalState,outTransFlag); 
												outTransition.setNotation(null);//空边
												outTransition.setNote(null);//无条件执行
												tmc.transitionsAddtransition(outTransition);
												
											}
											if(oper.equals(frag.getOperands().get(frag.getOperands().size()-1)))
											{
												currentState=finalState;
											}
											else
											{
												currentState=tempState;
											}
												
										}
									}
									break;
								}
							}
						}
					}// 不在当前组合片段中
				 
				}
			}
		}
		if(frag.getType().equals("loop"))
		{
			List<Message> currentMessageList = retrieveCurrentMessages(frag);
			
			for (Operand oper : frag.getOperands()) 
			{
				List<Message> operMessages = new ArrayList<Message>();
				operMessages.addAll(retrieveOperandMessages(currentMessageList,oper));
				
				for (Message mess : operMessages) 
				{
					if(mess.isTranslationed()==true)
						continue;
					// 该消息在当前组合片段中，直接转化
					if (mess.getOperandId().equals(oper.getId())==true) 
					{
						boolean issame = false;
						Iterator<Transition> transiterator = tmc.getTransitions().iterator();
						while (transiterator.hasNext()) 
						{
							Transition transition = transiterator.next();
							if (currentState.equals(transition.getFrom())
									&&mess.getSender().equals(transition.getTransFlag().getSender())
									&& mess.getReceiver().equals(transition.getTransFlag().getReceiver())
									&& mess.getName().equals(transition.getTransFlag().getName()))  
							{
								
								transition.getTransFlag().setProb(transition.getTransFlag().getProb() + mess.getProb());
								mess.setTranslationed(true);
								currentState = transition.getTo();
								issame = true;
								if(mess.equals(operMessages.get(operMessages.size()-1)))
								{
									lastMessage=mess;
									Transition nextTr=tmc.getTransitions().get(tmc.getTransitions().indexOf(transition)+1);
									finalState=nextTr.getTo();
									currentState=nextTr.getTo();
								}
								break;
							}
						}
					if(!issame)
					{
						//如果是当前操作的入边……
						if(mess.equals(operMessages.get(0))==true && mess.equals(operMessages.get(operMessages.size()-1))==false)
						{
							
							State state=createNewState();
							if(mess.isLast()==true)
							{
								state.setLabel("Final");
								state.setNotation(sdSet.getPostSD());
								finalStates.add(state);
							}
							TransFlag transFlag=new TransFlag(mess);
							Transition transition=createTransition(state,transFlag); 
							
							transition.setNotation(transition.getNotation()+"inLoop");//添加入边
							transition.setNote(oper.getCondition());//添加执行条件
							tmc.statesAddState(state);
							tmc.transitionsAddtransition(transition);
							currentState=state;//指针下移
							count++; 
						}
						//非入非出
						if(mess.equals(operMessages.get(0))==false && mess.equals(operMessages.get(operMessages.size()-1))==false)
						{
							State state=createNewState();
							if(mess.isLast()==true)
							{
								state.setLabel("Final");
								state.setNotation(sdSet.getPostSD());
								finalStates.add(state);
							}
							TransFlag transFlag=new TransFlag(mess);
							Transition transition=createTransition(state,transFlag); 
							
							transition.setNote(oper.getCondition());//添加执行条件
							tmc.statesAddState(state);
							tmc.transitionsAddtransition(transition);
							currentState=state;//指针下移
							count++; 
						}
						//是出边（入出，出）
						if( mess.equals(operMessages.get(operMessages.size()-1))==true)
						{
							lastMessage=mess;
							//未状态为空是要建立未状态,最后加入回边；
							//只有一条操作，末是出边，末状态肯定为空
							if(finalState==null)
							{	State state=createNewState();
								if(mess.isLast()==true)
								{
									state.setLabel("Final");
									state.setNotation(sdSet.getPostSD());
									finalStates.add(state);
								}
								TransFlag transFlag=new TransFlag(mess);
								Transition transition=createTransition(state,transFlag); 
								if(mess.equals(operMessages.get(0))==true)
								{
									
									transition.setNotation(transition.getNotation()+"inLoop"+"-outLoop");//添加入边和出边
								}
								if(mess.equals(operMessages.get(0))==false)
									transition.setNotation(transition.getNotation()+"outLoop");//添加出边
								transition.setNote(oper.getCondition());//添加执行条件
								
								tmc.statesAddState(state);
								tmc.transitionsAddtransition(transition);
								currentState=state;
								count++;

								State outState=createNewState();
								TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
								Transition outTransition=createTransition(outState,outTransFlag); 
								outTransition.setNotation(null);//空边
								outTransition.setNote(null);//无条件执行
								
								tmc.statesAddState(outState);
								tmc.transitionsAddtransition(outTransition);
								count++;
								currentState=outState;
								finalState=outState;
								
								//加入回边
								TransFlag loopBackFlag=new TransFlag(null,null,null,null,null,1,null);
								Transition loopBackTransition=createTransition(tempState,loopBackFlag);
								loopBackTransition.setNotation("backLoop");
								loopBackTransition.setNote(oper.getCondition());//添加执行条件
								tmc.transitionsAddtransition(loopBackTransition);
								}
							}			
						mess.setTranslationed(true);
					}
				}
					//不在当前组合片段中
					else
					{
						
						if(mess.isTranslationed()==true)
							continue;
						State newFinalState=new State();
						State newTempState=currentState;
						if(oper.getFragments()!=null)
						{
							for(Fragment newFrag:oper.getFragments())
							{
								if(newFrag.isTranslationed()==true)
									continue;
								//消息可能在深层组合片段，所有需要寻找消息所在组合片段的路径
								List<String> fragmentIds=retrieveFragmentIds(newFrag,mess);
								if(fragmentIds!=null)
								{
									//转化该组合片段
									List newList=fragmentTranslationIntegate(newFrag,newTempState);
									newFinalState=(State)newList.get(0);
									Message tempMessage=(Message)newList.get(1);
									newFrag.setTranslationed(true);									//如果不是最后一条消息，则继续遍历
									//如果是最后一条消息(没有后继消息)，则做如下操作
									if(tempMessage.equals(operMessages.get(operMessages.size()-1)))
									{
										
										lastMessage=tempMessage;
										//根据newFinalState确定后面已经有的空迁移，从而确定空迁移的目的状态
										boolean tag=false;
										for(Transition tr:tmc.getTransitions())
										{
											String str=tr.getNotation();
											if(str==null)
												str="str";
											if(tr.getFrom().equals(newFinalState)
													&&tr.getTransFlag().getName()==null
													&&tr.getTransFlag().getProb()==1.0
													&&str.equals("backLoop"))
											{
												finalState=tr.getTo();//找到原有的末状态
												tag=true;
												break;
											}
										}		
										
										if(!tag)
										{
											State outState=createNewState();
											TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
											Transition outTransition=createTransition(outState,outTransFlag); 
											outTransition.setNotation(null);//空边
											outTransition.setNote(null);//无条件执行
											
											tmc.statesAddState(outState);
											tmc.transitionsAddtransition(outTransition);
											count++;
											currentState=outState;
											finalState=outState;
											
											//加入回边
											TransFlag loopBackFlag=new TransFlag(null,null,null,null,null,1,null);
											Transition loopBackTransition=createTransition(tempState,loopBackFlag);
											loopBackTransition.setNotation("backLoop");
											loopBackTransition.setNote(oper.getCondition());//添加执行条件
											tmc.transitionsAddtransition(loopBackTransition);
										}
										currentState=finalState;
										
//										System.out.println("执行到这里"+mess.getName());
//										System.out.println("CS状态名："+currentState.getName());
//										System.out.println("tempState状态名："+tempState.getName());
//										if(finalState!=null)
//											System.out.println("finalState状态名："+finalState.getName());
									}
									break;
								}
							}
						}
					}
				  
				}
			}
		}
		List list=new ArrayList();
		list.add(finalState);
		list.add(lastMessage);
		return list;
	
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List fragmentTranslation(Fragment frag,State tempState)
	{
		/* * 先判断是否在顺序图下第一层组合片段（根据id判断）
		 * 	如果在第一层组合片段，则根据消息操作Id,确定所属操作;
		 * 		消息肯定是该第一条（在组合片段之前），否则会在深层操作中
		 * 		 获取当前组合片段所有消息（包括内层消息（根据顺序图中消息顺序添加）
					 * 利用顺序图中消息集合，截取本组合片段中所有消息（包括组合片段中消息））
					 *首先获取该组合片段下的信息（包括内层组合片段中的消息）（乱序放在组合集合中A中，然后用顺序图中消息一一遍历比较，如果在A中，则添加到B集合中）
		 */
		Message lastMessage=null;
		State finalState = null;
		
		if (frag.getType().equals("alt")) 
		{
			List<Message> currentMessageList = retrieveCurrentMessages(frag);
//			// 输出………………
//			System.out.println("\n**********组合片段下的信息********");
//			  for(Message mess:currentMessageList) 
//			  { mess.print_Message(); }
//			
//			  System.out.println("##########"+frag.getName()+"组合片段有"+frag.getOperands().size()+"条操作");
//			// 针对不同操作获取对应消息
			for (Operand oper : frag.getOperands()) 
			{
				List<Message> operMessages = new ArrayList<Message>();
				operMessages.addAll(retrieveOperandMessages(currentMessageList,	oper));
				for (Message mess : operMessages) 
				{
					if(mess.isTranslationed()==true)
						continue;
					// 该消息在当前组合片段中，直接转化
					if (mess.getOperandId().equals(oper.getId())==true) 
					{
						//如果是当前操作的入边……
						if(mess.equals(operMessages.get(0))==true && mess.equals(operMessages.get(operMessages.size()-1))==false)
						{
							
							State state=createNewState();
//							if(mess.isLast()==true)
//							{
//								state.setLabel("Final");
//								state.setNotation(sdSet.getPostSD());
//								finalStates.add(state);
//							}
							TransFlag transFlag=new TransFlag(mess);
							Transition transition=createTransition(state,transFlag); 
							
							transition.setNotation(transition.getNotation()+"inAlt");//添加入边
							transition.setNote(oper.getCondition());//添加执行条件
							tmc.statesAddState(state);
							tmc.transitionsAddtransition(transition);
							currentState=state;//指针下移
							count++; 
						}
						//非入非出
						if(mess.equals(operMessages.get(0))==false && mess.equals(operMessages.get(operMessages.size()-1))==false)
						{
							State state=createNewState();
//							if(mess.isLast()==true)
//							{
//								state.setLabel("Final");
//								state.setNotation(sdSet.getPostSD());
//								finalStates.add(state);
//							}
							TransFlag transFlag=new TransFlag(mess);
							Transition transition=createTransition(state,transFlag); 
							
							transition.setNote(oper.getCondition());//添加执行条件
							tmc.statesAddState(state);
							tmc.transitionsAddtransition(transition);
							currentState=state;//指针下移
							count++; 
							
						}
						//是入边亦是出边
						if(mess.equals(operMessages.get(0))==true && mess.equals(operMessages.get(operMessages.size()-1))==true)
						{
							lastMessage=mess;
								State state=createNewState();
//								if(mess.isLast()==true)
//								{
//									state.setLabel("Final");
//									state.setNotation(sdSet.getPostSD());
//									finalStates.add(state);
//								}
								TransFlag transFlag=new TransFlag(mess);
								Transition transition=createTransition(state,transFlag); 
								
								transition.setNotation(transition.getNotation()+"inAlt"+"-outAlt");//添加入边和出边
								transition.setNote(oper.getCondition());//添加执行条件
								tmc.statesAddState(state);
								tmc.transitionsAddtransition(transition);
								count++; 
								currentState=state;
								
								//未状态为空是要建立未状态，便于后续归一操作末状态；
								if(finalState==null)
								{
									//加入末状态和空迁移，空迁移执行概率为1，自动滑落到末状态
									State outState=createNewState();
									TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
									
									Transition outTransition=createTransition(outState,outTransFlag); 
									outTransition.setNotation(null);//空边
									outTransition.setNote(null);//无条件执行
									
									tmc.statesAddState(outState);
									tmc.transitionsAddtransition(outTransition);
									count++;
									finalState=outState;
								}

								//末状态不为空表示执行到下面的操作，需要操作末状态归一处理，不用插入状态，直接建立迁移指向finalState，
								else
								{
									
									TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
									Transition outTransition=createTransition(finalState,outTransFlag); 
									outTransition.setNotation(null);//空边
									outTransition.setNote(null);//无条件执行
									tmc.transitionsAddtransition(outTransition);
								}
								
								if(oper.equals(frag.getOperands().get(frag.getOperands().size()-1)))
									{
									  currentState=finalState;
									  
									}
								else
									{
									 currentState=tempState;
									}
								//return finalState;
						}
						//仅仅是出边
						if(mess.equals(operMessages.get(0))==false && mess.equals(operMessages.get(operMessages.size()-1))==true)
						{
							lastMessage=mess;
							State state=createNewState();
//							if(mess.isLast()==true)
//							{
//								state.setLabel("Final");
//								state.setNotation(sdSet.getPostSD());
//								finalStates.add(state);
//							}
							TransFlag transFlag=new TransFlag(mess);
							Transition transition=createTransition(state,transFlag); 
							
							transition.setNotation(transition.getNotation()+"outAlt");//添加入边和出边
							transition.setNote(oper.getCondition());//添加执行条件
							
							tmc.statesAddState(state);
							tmc.transitionsAddtransition(transition);
							count++; 
							currentState=state;
							
							//未状态为空是要建立未状态，便于后续归一操作末状态；
							if(finalState==null)
							{
								//加入末状态和空迁移，空迁移执行概率为1，自动滑落到末状态
								State outState=createNewState();
								TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
								
								Transition outTransition=createTransition(outState,outTransFlag); 
								outTransition.setNotation(null);//空边
								outTransition.setNote(null);//无条件执行
								
								tmc.statesAddState(outState);
								tmc.transitionsAddtransition(outTransition);
								count++;
								finalState=outState;
							}

							//末状态不为空表示执行到下面的操作，需要操作末状态归一处理，不用插入状态，直接建立迁移指向finalState，
							else
							{
								TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
								Transition outTransition=createTransition(finalState,outTransFlag); 
								outTransition.setNotation(null);//空边
								outTransition.setNote(null);//无条件执行
								tmc.transitionsAddtransition(outTransition);
							}
							//System.out.println(currentState);
							
							if(oper.equals(frag.getOperands().get(frag.getOperands().size()-1)))
								currentState=finalState;
							else
								currentState=tempState;
						}
						mess.setTranslationed(true);
					} 
					else  // 不在当前组合片段中，用该消息获取深层组合片段和对应消息
					{
						State newTempState=currentState;
						State newFinalState=new State();
						if(oper.getFragments()!=null)
						{
							for(Fragment newFrag:oper.getFragments())
							{
								//消息可能在深层组合片段，所有需要寻找消息所在组合片段的路径
								List<String> fragmentIds=retrieveFragmentIds(newFrag,mess);
								if(fragmentIds!=null)
								{
									if(newFrag.isTranslationed()==true)
										continue;
									//转化该组合片段
									List newList=fragmentTranslation(newFrag,newTempState);
									newFinalState=(State)newList.get(0);
									Message tempMessage=(Message)newList.get(1);
									newFrag.setTranslationed(true);
									//如果不是最后一条消息，则继续遍历
									//如果是最后一条消息(没有后继消息)，则做如下操作
									if(tempMessage.equals(operMessages.get(operMessages.size()-1)))
									{
										lastMessage=tempMessage;
										if(finalState==null)
										{
											finalState=newFinalState;
											//加入末状态和空迁移，空迁移执行概率为1，自动滑落到末状态
											State outState=createNewState();
											TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
											
											Transition outTransition=createTransition(outState,outTransFlag); 
											outTransition.setNotation(null);//空边
											outTransition.setNote(null);//无条件执行
											
											tmc.statesAddState(outState);
											tmc.transitionsAddtransition(outTransition);
											count++;
											finalState=outState;
											currentState=tempState;
										}
										else
										{

											
											if(oper.equals(frag.getOperands().get(frag.getOperands().size()-1)))
											{

												TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
												Transition outTransition=createTransition(finalState,outTransFlag); 
												outTransition.setNotation(null);//空边
												outTransition.setNote(null);//无条件执行
												tmc.transitionsAddtransition(outTransition);
												currentState=finalState;
											}
											else
												currentState=tempState;
										}
									}
									break;
								}
							}
						}
					}// 不在当前组合片段中
				}
			}
		}
		if(frag.getType().equals("par"))
		{
			List<Message> currentMessageList = retrieveCurrentMessages(frag);
			for (Operand oper : frag.getOperands()) 
			{
				List<Message> operMessages = new ArrayList<Message>();
				operMessages.addAll(retrieveOperandMessages(currentMessageList,	oper));
				
//				System.out.println("该操作下的消息");
//				for(Message mess:operMessages)
//				{
//					mess.print_Message();
//				}
				for (Message mess : operMessages) 
				{
					
					if(mess.isTranslationed()==true)
						continue;
					// 该消息在当前组合片段中，直接转化
					if (mess.getOperandId().equals(oper.getId())==true) 
					{
						//如果是当前操作的入边……
						if(mess.equals(operMessages.get(0))==true && mess.equals(operMessages.get(operMessages.size()-1))==false)
						{
							
							State state=createNewState();
//							if(mess.isLast()==true)
//							{
//								state.setLabel("Final");
//								state.setNotation(sdSet.getPostSD());
//								finalStates.add(state);
//							}
							TransFlag transFlag=new TransFlag(mess);
							Transition transition=createTransition(state,transFlag); 
							
							transition.setNotation(transition.getNotation()+"inPar");//添加入边
							transition.setNote(oper.getCondition());//添加执行条件
							tmc.statesAddState(state);
							tmc.transitionsAddtransition(transition);
							currentState=state;//指针下移
							count++; 
						}
						//非入非出
						if(mess.equals(operMessages.get(0))==false && mess.equals(operMessages.get(operMessages.size()-1))==false)
						{
							State state=createNewState();
//							if(mess.isLast()==true)
//							{
//								state.setLabel("Final");
//								state.setNotation(sdSet.getPostSD());
//								finalStates.add(state);
//							}
							TransFlag transFlag=new TransFlag(mess);
							Transition transition=createTransition(state,transFlag); 
							
							transition.setNote(oper.getCondition());//添加执行条件
							tmc.statesAddState(state);
							tmc.transitionsAddtransition(transition);
							currentState=state;//指针下移
							count++; 
							
						}
						//是入边亦是出边
						if(mess.equals(operMessages.get(0))==true && mess.equals(operMessages.get(operMessages.size()-1))==true)
						{
							lastMessage=mess;
								State state=createNewState();
//								if(mess.isLast()==true)
//								{
//									state.setLabel("Final");
//									state.setNotation(sdSet.getPostSD());
//									finalStates.add(state);
//								}
								TransFlag transFlag=new TransFlag(mess);
								Transition transition=createTransition(state,transFlag); 
								
								transition.setNotation(transition.getNotation()+"inPar"+"-outPar");//添加入边和出边
								transition.setNote(oper.getCondition());//添加执行条件
								tmc.statesAddState(state);
								tmc.transitionsAddtransition(transition);
								count++; 
								currentState=state;
								
								//未状态为空是要建立未状态，便于后续归一操作末状态；
								if(finalState==null)
								{
									//加入末状态和空迁移，空迁移执行概率为1，自动滑落到末状态
									State outState=createNewState();
									TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
									
									Transition outTransition=createTransition(outState,outTransFlag); 
									outTransition.setNotation(null);//空边
									outTransition.setNote(null);//无条件执行
									
									tmc.statesAddState(outState);
									tmc.transitionsAddtransition(outTransition);
									count++;
									finalState=outState;
								}
								//末状态不为空表示执行到下面的操作，需要操作末状态归一处理，不用插入状态，直接建立迁移指向finalState，
								else
								{
									
									TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
									Transition outTransition=createTransition(finalState,outTransFlag); 
									outTransition.setNotation(null);//空边
									outTransition.setNote(null);//无条件执行
									tmc.transitionsAddtransition(outTransition);
								}
								
								if(oper.equals(frag.getOperands().get(frag.getOperands().size()-1)))
									{
									  currentState=finalState;
									}
								else
									{
									 currentState=tempState;
									}
								
								//return finalState;
								
						}
						//仅仅是出边
						if(mess.equals(operMessages.get(0))==false && mess.equals(operMessages.get(operMessages.size()-1))==true)
						{
							lastMessage=mess;
							State state=createNewState();
//							if(mess.isLast()==true)
//							{
//								state.setLabel("Final");
//								state.setNotation(sdSet.getPostSD());
//								finalStates.add(state);
//							}
							TransFlag transFlag=new TransFlag(mess);
							Transition transition=createTransition(state,transFlag); 
							
							transition.setNotation(transition.getNotation()+"outPar");//添加入边和出边
							transition.setNote(oper.getCondition());//添加执行条件
							
							tmc.statesAddState(state);
							tmc.transitionsAddtransition(transition);
							count++; 
							currentState=state;
							
							//未状态为空是要建立未状态，便于后续归一操作末状态；
							if(finalState==null)
							{
								//加入末状态和空迁移，空迁移执行概率为1，自动滑落到末状态
								State outState=createNewState();
								TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
								
								Transition outTransition=createTransition(outState,outTransFlag); 
								outTransition.setNotation(null);//空边
								outTransition.setNote(null);//无条件执行
								
								tmc.statesAddState(outState);
								tmc.transitionsAddtransition(outTransition);
								count++;
								finalState=outState;
							}

							//末状态不为空表示执行到下面的操作，需要操作末状态归一处理，不用插入状态，直接建立迁移指向finalState，
							else
							{
								TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
								Transition outTransition=createTransition(finalState,outTransFlag); 
								outTransition.setNotation(null);//空边
								outTransition.setNote(null);//无条件执行
								tmc.transitionsAddtransition(outTransition);
							}
							
							
							if(oper.equals(frag.getOperands().get(frag.getOperands().size()-1)))
								currentState=finalState;
							else
								currentState=tempState;
						}
						mess.setTranslationed(true);
						

					} 
					else  // 不在当前组合片段中，用该消息获取深层组合片段和对应消息
					{
						State newTempState=currentState;
						State newFinalState=new State();
						if(oper.getFragments()!=null)
						{
							for(Fragment newFrag:oper.getFragments())
							{
								//消息可能在深层组合片段，所有需要寻找消息所在组合片段的路径
								List<String> fragmentIds=retrieveFragmentIds(newFrag,mess);
								if(fragmentIds!=null)
								{
									if(newFrag.isTranslationed()==true)
										continue;
									//转化该组合片段
									List newList=fragmentTranslation(newFrag,newTempState);
									newFinalState=(State)newList.get(0);
									Message tempMessage=(Message)newList.get(1);
									newFrag.setTranslationed(true);
									//如果不是最后一条消息，则继续遍历
									//如果是最后一条消息(没有后继消息)，则做如下操作
									if(tempMessage.equals(operMessages.get(operMessages.size()-1)))
									{
										lastMessage=tempMessage;
										if(finalState==null)
										{
											finalState=newFinalState;
											//加入末状态和空迁移，空迁移执行概率为1，自动滑落到末状态
											State outState=createNewState();
											TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
											
											Transition outTransition=createTransition(outState,outTransFlag); 
											outTransition.setNotation(null);//空边
											outTransition.setNote(null);//无条件执行
											
											tmc.statesAddState(outState);
											tmc.transitionsAddtransition(outTransition);
											count++;
											finalState=outState;
											currentState=tempState;
										}
										else
										{
											if(oper.equals(frag.getOperands().get(frag.getOperands().size()-1)))
											{
												TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
												Transition outTransition=createTransition(finalState,outTransFlag); 
												outTransition.setNotation(null);//空边
												outTransition.setNote(null);//无条件执行
												tmc.transitionsAddtransition(outTransition);
												currentState=finalState;
											}
											else
												currentState=tempState;
										}
									}
									break;
								}
							}
						}
					}// 不在当前组合片段中
				}
			}
		}
		if(frag.getType().equals("loop"))
		{
			List<Message> currentMessageList = retrieveCurrentMessages(frag);
			
			for (Operand oper : frag.getOperands()) 
			{
				List<Message> operMessages = new ArrayList<Message>();
				operMessages.addAll(retrieveOperandMessages(currentMessageList,oper));
				
				for (Message mess : operMessages) 
				{
					if(mess.isTranslationed()==true)
						continue;
					// 该消息在当前组合片段中，直接转化
					if (mess.getOperandId().equals(oper.getId())==true && mess.isTranslationed()==false) 
					{
						//如果是当前操作的入边……
						if(mess.equals(operMessages.get(0))==true && mess.equals(operMessages.get(operMessages.size()-1))==false)
						{
							
							State state=createNewState();
//							if(mess.isLast()==true)
//							{
//								state.setLabel("Final");
//								state.setNotation(sdSet.getPostSD());
//								finalStates.add(state);
//							}
							TransFlag transFlag=new TransFlag(mess);
							Transition transition=createTransition(state,transFlag); 
							
							transition.setNotation(transition.getNotation()+"inLoop");//添加入边
							transition.setNote(oper.getCondition());//添加执行条件
							tmc.statesAddState(state);
							tmc.transitionsAddtransition(transition);
							currentState=state;//指针下移
							count++; 
						}
						//非入非出
						if(mess.equals(operMessages.get(0))==false && mess.equals(operMessages.get(operMessages.size()-1))==false)
						{
							State state=createNewState();
//							if(mess.isLast()==true)
//							{
//								state.setLabel("Final");
//								state.setNotation(sdSet.getPostSD());
//								finalStates.add(state);
//							}
							TransFlag transFlag=new TransFlag(mess);
							Transition transition=createTransition(state,transFlag); 
							
							transition.setNote(oper.getCondition());//添加执行条件
							tmc.statesAddState(state);
							tmc.transitionsAddtransition(transition);
							currentState=state;//指针下移
							count++; 
						}
						//是出边（入出，出）
						if( mess.equals(operMessages.get(operMessages.size()-1))==true)
						{
							lastMessage=mess;
							//未状态为空是要建立未状态,最后加入回边；
							//只有一条操作，末是出边，末状态肯定为空
							if(finalState==null)
							{	State state=createNewState();
//								if(mess.isLast()==true)
//								{
//									state.setLabel("Final");
//									state.setNotation(sdSet.getPostSD());
//									finalStates.add(state);
//								}
								TransFlag transFlag=new TransFlag(mess);
								Transition transition=createTransition(state,transFlag); 
								if(mess.equals(operMessages.get(0))==true)
								{
									
									transition.setNotation(transition.getNotation()+"inLoop"+"-outLoop");//添加入边和出边
								}
								
								if(mess.equals(operMessages.get(0))==false)
								{
									transition.setNotation(transition.getNotation()+"outLoop");//添加出边
								}
								transition.setNote(oper.getCondition());//添加执行条件
								
								tmc.statesAddState(state);
								tmc.transitionsAddtransition(transition);
								currentState=state;
								count++;

								State outState=createNewState();
								TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
								
								Transition outTransition=createTransition(outState,outTransFlag); 
								transition.setNotation(null);//空边
								transition.setNote(null);//无条件执行
								
								tmc.statesAddState(outState);
								tmc.transitionsAddtransition(outTransition);
								count++;
								currentState=outState;
								finalState=outState;
								
								//加入回边
								TransFlag loopBackFlag=new TransFlag(null,null,null,null,null,1.0,null);
								Transition loopBackTransition=createTransition(tempState,loopBackFlag);
								loopBackTransition.setNotation("backLoop");
								loopBackTransition.setNote(oper.getCondition());//添加执行条件
								tmc.transitionsAddtransition(loopBackTransition);
								
							}
						}			
						mess.setTranslationed(true);
					}
					
					//不在当前组合片段中
					else
					{
						if(mess.isTranslationed()==true)
							continue;
						State newFinalState=new State();
						State newTempState=currentState;
						if(oper.getFragments()!=null)
						{
							for(Fragment newFrag:oper.getFragments())
							{
								if(newFrag.isTranslationed()==true)
									continue;
								//消息可能在深层组合片段，所有需要寻找消息所在组合片段的路径
								List<String> fragmentIds=retrieveFragmentIds(newFrag,mess);
								if(fragmentIds!=null)
								{
									
									//转化该组合片段
									List newList=fragmentTranslation(newFrag,newTempState);
									newFinalState=(State)newList.get(0);
									Message tempMessage=(Message)newList.get(1);
									newFrag.setTranslationed(true);									//如果不是最后一条消息，则继续遍历
									//如果是最后一条消息(没有后继消息)，则做如下操作
									if(tempMessage.equals(operMessages.get(operMessages.size()-1)))
									{
										//加入空迁移
										currentState=newFinalState;
										State outState=createNewState();
										TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
										Transition outTransition=createTransition(outState,outTransFlag); 
										outTransition.setNotation(null);//空边
										outTransition.setNote(null);//无条件执行
										
										tmc.statesAddState(outState);
										tmc.transitionsAddtransition(outTransition);
										count++;
										currentState=outState;
										finalState=outState;
										lastMessage=tempMessage;
										//加入回边
										TransFlag loopBackFlag=new TransFlag(null,null,null,null,null,1.0,null);
										
										Transition loopBackTransition=createTransition(tempState,loopBackFlag);
										loopBackTransition.setNotation("backLoop");
										loopBackTransition.setNote(oper.getCondition());//添加执行条件
										tmc.transitionsAddtransition(loopBackTransition);
									}
									break;
								}
							}
						}
					}
				}
			}
		}
		
		List list=new ArrayList();
		list.add(finalState);
		list.add(lastMessage);
		return list;
	}

	public void SoftwareMergeMarkov(List<Tmc> Tmcs)
	{
		F_Tmc.setTmcType("Software_Markov");
		F_Tmc.setOwned("Software_Itself");
		F_Tmc.setNames("Software_MarkovChainModel");
		//第一个用例的前置条件------"correctiveness"
		//第一步：
		F_Tmc.setNotation("correctiveness");//软件执行的 前置条件，即软件处于就绪状态，正确状态
 		//构造软件马尔科夫模型de 起始状态q0;
		State initialState=createNewState();
		initialState.setTmcID("-1");
		initialState.setName("Q0");
		initialState.setLabel("Initial");
		
		F_Tmc.setStart(initialState);
		F_Tmc.statesAddState(initialState);
		//第二步：链接起始用例链
		for(Tmc tmc:Tmcs)
		{
			//寻找第一个用例，构造迁移将起始状态Q0和S0连接起来，
			if(tmc.getNotation().equals("correctiveness")) //软件开始状态
			{
				State rearState=tmc.getStart();
				IntegateMarkovChain(initialState,rearState,tmc,Tmcs);
			}
			
		}
	}
	
	/*
	 * 对状态的概率归一
	 */
	public void normalizeOnProbability()
	{
		for(Tmc tempTmc:tmcs)  //接口定义，包装类实现
		{
			List<State> tempStates=tempTmc.getStates();
			List<Transition> tempTransitions=tempTmc.getTransitions();
			/*
			 *遍历每个状态，找该状态对应的所有出边，存储，然后做归一处理
			 */
			for(State tempState:tempStates)
			{
				List<Transition> temp2_Transitions=new ArrayList<Transition>();
				for(Transition tr:tempTransitions)
				{
					if(tr.getFrom().equals(tempState))
					{
						temp2_Transitions.add(tr);
					}
				}
				if(temp2_Transitions.size()>0)
				{
					double tempPro=0;
					for(Transition t:temp2_Transitions)
					{
						tempPro+=t.getTransFlag().getProb();
					}
					for(Transition t:temp2_Transitions)
					{
						t.getTransFlag().setProb(t.getTransFlag().getProb()/tempPro);
					}
				}
				
			}
		
		}
	}
	
	public void IntegateMarkovChain(State frontState,State rearState,Tmc rearTmc,List<Tmc> Tmcs)
	{
		TransFlag transFlag=new TransFlag(null,null,null,null,null,rearTmc.getPro(),null);
		Transition Tr=new Transition(frontState,rearState,transFlag);
		Tr.setNotation("connectUseCase");
		F_Tmc.transitionsAddtransition(Tr);
		
		if(rearTmc.isCombine()==false)
		{
			F_Tmc.statesAddStates(rearTmc.getStates());
			F_Tmc.endsAddStates(rearTmc.getEnds());
			F_Tmc.transitionsAddtransitions(rearTmc.getTransitions());
			
		}
		
		//寻找tmc的尾状态的后继用例，进行迭代----------------------------1.19从此处开始工作
		
		if(!rearTmc.isCombine())
		{
			for(State state:rearTmc.getEnds())
			{
				int flag=0;
				//state.print_state();
				for(Tmc tempTmc:Tmcs)
				{
					if(state.getNotation().equals(tempTmc.getNotation()))
					{
						//System.out.println(tempTmc.getStart().getName());
						flag=1;
						//System.out.println(state.getNotation()+"************"+tempTmc.getNotation());
						IntegateMarkovChain(state,tempTmc.getStart(),tempTmc,Tmcs);
						tempTmc.setCombine(true);
					}
					
				}
				if(flag==1)
				{
					if(!state.getName().equals("Q0"))//清除label和notation域
					{
						state.setLabel(null);
						state.setNotation(null);
						F_Tmc.endsRemoveState(state);//删除尾状态
					}
				}
		     } 
		}
	}
	
	public List<String> retrieveFragmentIds(Fragment frag,Message message)
	{
		
		List<String> fragmentIds=new ArrayList<String>();
		if(frag.getId().equals(message.getFragmentId()))
		{
			fragmentIds.add(frag.getId());
			return fragmentIds;
		}
		else
		{
			int tag=0;
			fragmentIds.add(frag.getId());
			
			List<String> newFragmentIds=null;			
			for(Operand oper:frag.getOperands())
			{
				int inTag=0;
				if(oper.isHasFragment()==true)
				{
					for(Fragment newFrag: oper.getFragments())
					{
						newFragmentIds=retrieveFragmentIds(newFrag,message);
						if(newFragmentIds!=null)
						{
							inTag=1;
							break;
						}
					}
				}
				if(inTag==1)
				{
					tag=1;
					break;
				}
			}
			if(tag==1)
			{
				fragmentIds.addAll(newFragmentIds);
				return fragmentIds;
			}
			else return null;
		}
		
	}
	
	//获取当前组合片段下的消息，根据顺序图中消息进行排序，
	public List<Message> retrieveCurrentMessages(Fragment frag)
	{
		List<Message> messageList1=retrieveChaosCurrentMessages(frag);//
		
		List<Message> messageList2=new ArrayList<Message>();
		messageList2.addAll(retrieveSortedCurrentMessages(messageList1));
		return messageList2;
	}
	public List<Message> retrieveChaosCurrentMessages(Fragment frag)
	{
		List<Message> messageList1=new ArrayList<Message>(); 
		for(Operand oper:frag.getOperands())
		{
			messageList1.addAll(oper.getMessages());
			if(oper.isHasFragment()==true && oper.getFragments()!=null)
			{
				for(Fragment currentFragment:oper.getFragments())
				{
					messageList1.addAll(retrieveChaosCurrentMessages(currentFragment));
				}
			}
		}
		
		return messageList1;
	}
	public List<Message> retrieveSortedCurrentMessages(List<Message> messageList1)
	{
		List<Message> messageList2=new ArrayList<Message>();
		for(Message message1:messages)
		{
			if(message1.isTranslationed()==false)
			{
				int Tab=0;
				for(Message message2:messageList1)
				{
					if(message1.equals(message2))
					{
						Tab=1;
						break;
					}
				}
				if(Tab==1)
					messageList2.add(message1);
			}
		}
		return messageList2;
	}
	
	//获取操作下的信息，然后根据当前组合片段中的消息集合来排序
	public List<Message> retrieveOperandMessages(List<Message> currentMessageList,Operand oper )
	{
		List<Message> messageList1=retrieveChaosOperandMessages(oper);
		List<Message> messageList2=retrieveSortedOperandMessages(currentMessageList,messageList1);
		return messageList2;
	}
	public List<Message> retrieveChaosOperandMessages(Operand oper)
	{
		List<Message> messageList1=new ArrayList<Message>(); 
		messageList1.addAll(oper.getMessages());
		if(oper.isHasFragment()==true && oper.getFragments()!=null)
		{
			for(Fragment currentFragment:oper.getFragments())
			{
				for(Operand newOper:currentFragment.getOperands())
				{
					messageList1.addAll(retrieveChaosOperandMessages(newOper));
				}
			}
		}
		return messageList1;
	}
	public List<Message> retrieveSortedOperandMessages(List<Message> currentMessageList, List<Message> messageList1)
	{
		List<Message> messageList2=new ArrayList<Message>();
		for(Message message1:currentMessageList)
		{
			if(message1.isTranslationed()==false)
			{
				int Tab=0;
				for(Message message2:messageList1)
				{
					if(message1.equals(message2))
					{
						Tab=1;
						break;
					}
				}
				if(Tab==1)
					messageList2.add(message1);
			}
		}
		return messageList2;
	}
	//生成最后的Markov模型，将终止状态归一
	public void unifyFinalStates()
	{
		System.out.println(count);
		State newState=createNewState();
		newState.setLabel("Exit");
		newState.setNotation("exitTheSoftware");
		F_Tmc.statesAddState(newState);
		
		//创建执行概率为1.0的空迁移 从currentState指向newState;
		for(State state: F_Tmc.getEnds() )
		{
			if(state.getLabel().equals("Final"))
			{
				currentState=state;
				TransFlag transFlag=new TransFlag(null,null,null,null,null,1.0,null);
				Transition transition=createTransition(newState,transFlag);
				transition.setNotation(null);
				F_Tmc.transitionsAddtransition(transition);
			}
			
		}
		F_Tmc.endsAddState(newState);
	}
	public void assignmentTmc(Tmc tmc1,Tmc tmc2)
	{
		tmc1.setTmcType(tmc2.getTmcType());
		tmc1.setNames(tmc2.getNames());
		tmc1.setStart(tmc2.getStart());
		tmc1.setStates(tmc2.getStates());
		tmc1.setEnds(tmc2.getEnds());
		tmc1.setTransitions(tmc2.getTransitions());
		tmc1.setNotation(tmc2.getNotation());
		tmc1.setPro(tmc2.getPro());
		tmc1.setCombine(tmc2.isCombine());
	}
	public void writeSeqMC(Tmc tmc)
	{
		String fileName="mcXmlDocuments\\Seq_MarkovChainModel"+seqCount+".xml";
		Write.writeMarkov2XML(tmc, fileName);
		seqCount++;
	}
	public void writeUcMC(Tmc tmc)
	{
		String fileName="mcXmlDocuments\\UC_MarkovChainModel"+ucCount+".xml";
		Write.writeMarkov2XML(tmc, fileName);
		ucCount++;
	}
	public void writeSoftwareMC(Tmc tmc)
	{
		String fileName="mcXmlDocuments\\Software_MarkovChainModel"+".xml";
		Write.writeMarkov2XML(tmc, fileName);
	}
	public List<Tmc> getTmcs() {
		return tmcs;
	}
	public List<Tmc> getSeqTmcList() {
		return seqTmcList;
	}
	
}//类括号


