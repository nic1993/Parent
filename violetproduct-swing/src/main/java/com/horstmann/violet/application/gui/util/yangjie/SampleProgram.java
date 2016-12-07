package com.horstmann.violet.application.gui.util.yangjie;

import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.MathLinkFactory;

public class SampleProgram {

	public static void main(String[] argv) {

		KernelLink ml = null;

		String path = "-linkmode launch -linkname 'D:\\Program Files\\Wolfram Research\\Mathematica\\10.2\\MathKernel.exe'";
		try {

			ml = MathLinkFactory.createKernelLink(path);
		} catch (MathLinkException e) {
			System.out.println("Fatal error opening link: " + e.getMessage());
			return;
		}

		try {
			// Get rid of the initial InputNamePacket the kernel will send
			// when it is launched.
			ml.discardAnswer();

			ml.evaluate("<<MyPackage.m");
			ml.discardAnswer();

			// ml.evaluate("2+2");
			// ml.evaluate("FindInstance[{0¡Üx+y¡Ü6,1<x<3,0<y<7},{x,y},1]");//
			// ml.evaluate("Plus[2,2]");
			// ml.waitForAnswer();

			// int result = ml.getInteger();
			// System.out.println("2 + 2 = " + result);

			// Here's how to send the same input, but not as a string:
			// ml.putFunction("EvaluatePacket", 1);//
			// FindInstance[{0¡Üx+y¡Ü6,1<x<3,0<y<7},{x,y},1]
			// ml.putFunction("Plus", 2);
			//
			// ml.put(3);
			// ml.put(3);
			// ml.endPacket();
			// ml.waitForAnswer();
			// result = ml.getInteger();
			// System.out.println("3 + 3 = " + result);

			ml.putFunction("FindInstance", 3);

			// ml.putSymbol("{0 <= x + y <= 6, 1 < x < 3, 0 < y < 7}");
			// ml.putSymbol("{x, y}");
			// ml.putSymbol("1");
			ml.endPacket();
			ml.waitForAnswer();
			String result1 = ml.getString();
			// System.out.println("3 + 3 = " + result);

			// If you want the result back as a string, use evaluateToInputForm
			// or evaluateToOutputForm. The second arg for either is the
			// requested page width for formatting the string. Pass 0 for
			// PageWidth->Infinity. These methods get the result in one
			// step--no need to call waitForAnswer.
			String strResult = ml
					.evaluateToOutputForm(
							"A = SetAccuracy[FindInstance[{1 <= x <= 6, 3 < y <= 9, 6 <= x + y < 13, -2 <= x - y < 3}, {x, y}, 10], 3]; A[[RandomInteger[{1, Length[A]}]]]",
							0);
			System.out.println(strResult);

		} catch (MathLinkException e) {
			System.out.println("MathLinkException occurred: " + e.getMessage());
		} finally {
			ml.close();
		}
	}
}
