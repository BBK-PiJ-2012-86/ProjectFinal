package pcp_interactive;

import java.util.Random;

import structure.Problem;
import utilities.Rand;

public class Script {
	private static long time;

	public static void main(String[] args) {
		log("starting");
		Problem prob = Problem.makeQuadeq(400, 800, new Rand(new Random()));
		log("made");
		Verifier v = new Verifier(new Rand(new Random()));
		boolean result = v.verify(Prover.proverInfo(prob.getAss(), v.request(prob.getEqns())));
		System.out.println(result);
		log("done");
		System.out.println(prob.isCorrect());
		log("naive");

	}
	
	public static void log(String message) {
		System.out.println(message+": "+(System.currentTimeMillis()-time));
		time = System.currentTimeMillis();
		
	}

}
