package com.horstmann.violet.application.gui.util.tiancai.markov;
import Jama.Matrix; 
public class ValueOf {

	public static NormalMatrixDao mergeMatrixDao(int num)
	{
		return new NormalMatrixDao(num);
	}
	public static ProMatrix mergeJudgeMatrix(Matrix arr)
	{
		return new ProMatrix(arr);
	}
}
