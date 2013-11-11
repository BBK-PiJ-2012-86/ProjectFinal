package pcp;

import java.util.Random;

import structure.Problem;
import utilities.Rand;

public class Script {
	private static long time;

	public static void main(String[] args) {
		Problem prob = Problem.makeQuadeq(2, 2, new Rand(new Random()));
		Proof proof = Prover.constructProof(prob.getAss());
		log("doing");
		boolean result = new Verifier(new Random()).verify(proof, prob.getEqns());
		log("done");
		System.out.println(result);
		
		System.out.println(prob.isCorrect());
		log("n done");
	}
	
	public static void log(String message) {
		System.out.println(message+": "+(System.currentTimeMillis()-time));
		time = System.currentTimeMillis();
		
	}
}
