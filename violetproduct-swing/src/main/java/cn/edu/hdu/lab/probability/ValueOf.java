package cn.edu.hdu.lab.probability;
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
