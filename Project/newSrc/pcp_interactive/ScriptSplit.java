package pcp_interactive;

import java.io.IOException;
import java.util.Random;

import structure.Problem;
import utilities.Rand;

public class ScriptSplit {
	private static long time;

	public static void main(String[] args) throws IOException {
		//System.in.read();
		log("starting");
		Problem prob = Problem.makeQuadeq(1300, 3900, new Rand(new Random()));
		log("made");
		VerifierSplit v = new VerifierSplit(new Rand(new Random()));
		boolean result1 = v.verifyFirstPartOfRequest(ProverSplit.proverInfoLinearityAndCorrespondence(prob.getAss(), v.generateFirstPartOfFerificationRequest(prob.getEqns())));
		System.out.println(result1);
		log("done first");
		boolean result2 = v.verifySeondPartOfRequest(ProverSplit.proverInfo2(prob.getAss(), v.generateSecondPartOfVerificationRequest(prob.getEqns())));
		System.out.println(result2);
		log("done");
		System.out.println(prob.isCorrect());
		log("naive");

	}
	
	public static void log(String message) {
		System.out.println(message+": "+(System.currentTimeMillis()-time));
		time = System.currentTimeMillis();
		
	}

}
