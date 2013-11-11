package demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import pcp.Proof;
import pcp.Verifier;
import pcp_interactive.ProverSplit;

import structure.Problem;
import trace.TraceAspect;
import utilities.Rand;

public class DemoRunner {
	
	private static final Rand rand = new Rand(new Random());
	private static final Problem small_1 = Problem.makeQuadeq(3, 3, rand);
	private static final Problem small_2 = Problem.makeQuadeq(3, 3, rand);
	private static final Problem small_3 = Problem.makeProblem(3, 3, rand);
	private static final Problem small_4 = Problem.makeProblem(3, 3, rand);
	
	private static final Problem large_1 = Problem.makeQuadeq(64, 64, rand);
	private static final Problem large_2 = Problem.makeProblem(64, 64, rand);
	
	private static Map<String, Problem> smallProblems = new HashMap<>(4);
	private static Map<String, Problem> largeProblems = new HashMap<>(2);
	
	static {
		smallProblems.put("1", small_1);
		smallProblems.put("2", small_2);
		smallProblems.put("3", small_3);
		smallProblems.put("4", small_4);
		
		largeProblems.put("1", large_1);
		largeProblems.put("2", large_2);
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		System.out.println("Choose from the following options:");
		System.out.println("1. Generate and print a random QUADEQ system (5 equations, 5 variables)");
		System.out.println("2. Verify one of the randomly renerated problems (1-4) using standard PCP method");
		System.out.println("3. Verify one of the large pre-defined problems (1-2) using interactive PCP method\n");
		
		TraceAspect.setTraceGenaration(true);
		
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		String line;
		while((line = reader.readLine()) != null) {
			String[] lineParts = line.split(" ");
			
			switch(lineParts[0]) {
			case "1" :
				System.out.println(Problem.makeQuadeq(5, 5, rand));
				break;
			case "2":
				if(lineParts.length < 2) {
					System.out.println("Not enough arguments: need problem number");
					continue;
				}
				Problem problem = smallProblems.get(lineParts[1]);
				if(problem != null) {
					verifyNonInteractive(problem);
				}
				else {
					System.out.println("Problem " + lineParts[1] + " does not exist");
				}
				break;
			case "3":
				if(lineParts.length < 2) {
					System.out.println("Not enough arguments: need problem number");
					continue;
				}
				Problem largeProblem = largeProblems.get(lineParts[1]);
				if(largeProblem != null) {
					verifyInteractive(largeProblem);
				}
				else {
					System.out.println("Problem " + lineParts[1] + " does not exist");
				}
				break;
			case "4":
			default:
				System.exit(0);
			}
			
			System.out.println("Choose from the following options:");
			System.out.println("1. Generate and print a random QUADEQ system (5 equations, 5 variables)");
			System.out.println("2. Verify one of the randomly renerated problems (1-4) using standard PCP method");
			System.out.println("3. Verify one of the large pre-defined problems (1-2) using interactive PCP method\n");
		}
	}
	
	private static void verifyNonInteractive(Problem problem) {
		Proof proof = pcp.Prover2.constructProof(problem.getAss());
		
		//Break linearity on 4
		if(problem == small_4) {
			proof.getAssEnc().set(0);
			proof.getAssEnc().set(1);
			proof.getAssEnc().set(2);
		}
		
		Verifier verifier = new Verifier(new Random());
		
		boolean result = verifier.verify(proof, problem.getEqns());
		
		if(result) {
			System.out.println("Verification: pass");
		}
		else {
			System.out.println("Verification: fail");
		}
	}
	
	private static void verifyInteractive(Problem problem) {
		pcp_interactive.VerifierSplit verifier = new pcp_interactive.VerifierSplit(rand); 
		
		boolean resultPart1 = verifier.verifyFirstPartOfRequest(ProverSplit.proverInfoLinearityAndCorrespondence(problem.getAss(), verifier.generateFirstPartOfFerificationRequest(problem.getEqns())));
		boolean resultPart2 = verifier.verifySeondPartOfRequest(ProverSplit.proverInfo2(problem.getAss(), verifier.generateSecondPartOfVerificationRequest(problem.getEqns())));
			
		if(resultPart1 && resultPart2) {
			System.out.println("Verification: pass");
		}
		else {
			System.out.println("Verification: fail");
		}
	}

	//private final Problem problem;
	//private final 
}
