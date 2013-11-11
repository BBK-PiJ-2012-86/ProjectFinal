package trace;

import java.util.BitSet;
import java.util.Random;

import org.aspectj.lang.ProceedingJoinPoint;

import pcp_interactive.ProverSplit;

import structure.Eqn;
import structure.Eqns;
import structure.Problem;
import utilities.Rand;

public privileged aspect TraceAspect {
	private pointcut myPackages() : within(pcp_interactive..*) || within(pcp_non_interactive..*) || within(structure..*) || within(utilities..*);
	private pointcut ignores() : execution(* get*(..)) || call(* get*(..)) || call(* set*(..)) || call(* put(..)) || execution(* leftPad(..)) || execution(* setRandom(..)) || execution(* encodeBit(..)) || execution(* reverse(..));
	private pointcut traceCalls() : ((execution(* *.*(..)) && (myPackages()) || call(* java.util.*.*(..))) && !ignores()) && !within(trace.*);
	
	private pointcut otherPrints() : (call(* print(..) ) || call(* println(..))) && myPackages();
	
	//private int depth = 0;
	private static boolean traceGeneration = false;
	
	public static void setTraceGenaration(boolean value) {
		traceGeneration = value;
	}
	
	/*before() : traceCalls() {
		String out = "";
		for(int i = 0; i < 2*depth; i++)
			out += " ";
		out += thisJoinPoint.toString();// + " with param values: " + thisJoinPoint.getArgs();
		System.out.println(out);
		depth++;
	}
	
	after() : traceCalls() {
		depth--;
	}*/
	
	void around(Eqns eqns, BitSet random, utilities.Pair<BitSet, Boolean> testEqns):call(private void pcp.Verifier.computeCompositeEquationForAssignmentTest(..)) && args(eqns,random,testEqns) {
		System.out.println("...Testing assignment on the following equations: " + BitSetUtils.printBitSet(random, eqns.size()));
		proceed(eqns, random, testEqns);
		System.out.println("...Chosen composite equation: " + new Eqn(eqns.size(), testEqns.left, testEqns.right).toString());
	}
	
	boolean around():call(boolean pcp.Verifier.testAss(..)) {
		boolean result = proceed();
		
		if(result) {
			System.out.println("...Assignment test: pass");
		}
		else {
			System.out.println("...Assignment test: fail");
		}
		
		return result;
	}
	
	boolean around(BitSet assEnc, BitSet crossEnc, int x,
			int y, int xCrossy):call(private boolean pcp.Verifier.testCrossCheck(..)) && args(assEnc, crossEnc, x, y, xCrossy) {
		System.out.print("...Checking tensor product x=" + x + "[=" + assEnc.get(x) + "],y=" + y + "[=" + assEnc.get(y) + "],cross=" + xCrossy + "[=" + assEnc.get(xCrossy) + "]: ");
		boolean result = proceed(assEnc, crossEnc, x, y, xCrossy);
		
		if(result) {
			System.out.println("...Tensor test: pass");
		}
		else {
			System.out.println("...Tensor test: fail");
		}
		
		return result;
	}
	
	boolean around(BitSet assEnc, int x, int y):call(private boolean pcp.Verifier.testLinearityCheck(..)) && args(assEnc, x, y) {
		System.out.print("...Linearity check over bits " + x + "[=" + assEnc.get(x) + "]" + " and " + y + "[=" + assEnc.get(y) + "]" + ", bit at x^y=" + assEnc.get(x^y) +  " : ");
		boolean result = proceed(assEnc, x, y);
		
		if(result) {
			System.out.println("...Linearity test: pass");
		}
		else {
			System.out.println("...Linearity test: fail");
		}
		
		return result;
	}
	
	Eqn around(int numVars, BitSet coeffs, boolean rhs) : call( Eqn.new(..)) && args(numVars, coeffs, rhs) {
		Eqn eqn = proceed(numVars, coeffs, rhs);
		
		if(traceGeneration) {
			System.out.println("...Making equation with the coefficients: " + BitSetUtils.printBitSet(eqn.getCoeffs(), numVars*numVars) + " (" + eqn.toString() + ")");
		}
		
		return eqn;
	}
	
	public static void main(String[] args) {
		Random r = new Random(12435670);
		Rand rand = new Rand(r);
		
		/*System.out.println("***Creating system of 2 equations over 2 variables***");
		System.out.println();
		
		Problem prob22 = Problem.makeQuadeq(2,2, rand);
		
		System.out.println();
		System.out.println("***Creating system of 5 equations over 5 variables***");
		System.out.println();
		
		Problem prob55 = Problem.makeQuadeq(5,5, rand);
		
		System.out.println();
		System.out.println("***Creating system of 10 equations over 32 variables***");
		System.out.println();
		
		Problem prob3210 = Problem.makeQuadeq(32,10, rand);*/
		
		Problem prob1000 = Problem.makeQuadeq(100, 25, rand);
		
		/*=====================================================================================*/
		
		/*System.out.println();
		System.out.println("***Veirfy two variable problem using non-interactive PCP***");
		System.out.println();
		
		Proof proof22 = pcp.Prover2.constructProof(prob22.getAss());
		
		pcp.Verifier verNI = new pcp.Verifier(r); 
		
		verNI.verify(proof22, prob22.getEqns());
		
		System.out.println();
		System.out.println("***Verify two variable problem using interactive PCP***");
		System.out.println();
		
		pcp_interactive.VerifierSplit verI = new pcp_interactive.VerifierSplit(rand); 
		
		boolean prob22v1 = verI.verifyFirstPartOfRequest(ProverSplit.proverInfoLinearityAndCorrespondence(prob22.getAss(), verI.generateFirstPartOfFerificationRequest(prob22.getEqns())));
		System.out.println("Interactive PCP stage 1: " + (prob22v1 ? "pass" : "fail"));
		boolean prob22v2 = verI.verifySeondPartOfRequest(ProverSplit.proverInfo2(prob22.getAss(), verI.generateSecondPartOfVerificationRequest(prob22.getEqns())));
		System.out.println("Interactive PCP stage 2: " + (prob22v2 ? "pass" : "fail"));
		
		System.out.println();
		System.out.println("***Verify two variable problem using naive assignment checking***");
		System.out.println();
		
		System.out.println("***Naive assignment check (two variables): " + (prob22.isCorrect() ? "pass***" : "fail***"));*/
		
		/*=====================================================================================*/
		
		/*System.out.println();
		System.out.println("***Veirfy five variable problem using non-interactive PCP***");
		System.out.println();
		
		Proof proof55 = pcp.Prover2.constructProof(prob22.getAss());
		
		verNI.verify(proof55, prob55.getEqns());
		
		System.out.println();
		System.out.println("***Verify five variable problem using interactive PCP***");
		System.out.println();
		
		boolean prob55v1 = verI.verifyFirstPartOfRequest(ProverSplit.proverInfoLinearityAndCorrespondence(prob55.getAss(), verI.generateFirstPartOfFerificationRequest(prob55.getEqns())));
		System.out.println("Interactive PCP stage 1: " + (prob55v1 ? "pass" : "fail"));
		boolean prob55v2 = verI.verifySeondPartOfRequest(ProverSplit.proverInfo2(prob55.getAss(), verI.generateSecondPartOfVerificationRequest(prob55.getEqns())));
		System.out.println("Interactive PCP stage 2: " + (prob55v2 ? "pass" : "fail"));
		
		System.out.println();
		System.out.println("***Verify five variable problem using naive assignment checking***");
		System.out.println();
		
		System.out.println("***Naive assignment check (five variables): " + (prob55.isCorrect() ? "pass***" : "fail***"));*/
		
		/*=====================================================================================*/
		
		System.out.println();
		System.out.println("***Verify 1000 variable problem using interactive PCP***");
		System.out.println();
		
		pcp_interactive.VerifierSplit verI = new pcp_interactive.VerifierSplit(rand); 
		
		boolean prob3210v1 = verI.verifyFirstPartOfRequest(ProverSplit.proverInfoLinearityAndCorrespondence(prob1000.getAss(), verI.generateFirstPartOfFerificationRequest(prob1000.getEqns())));
		System.out.println("Interactive PCP stage 1: " + (prob3210v1 ? "pass" : "fail"));
		boolean prob3210v2 = verI.verifySeondPartOfRequest(ProverSplit.proverInfo2(prob1000.getAss(), verI.generateSecondPartOfVerificationRequest(prob1000.getEqns())));
		System.out.println("Interactive PCP stage 2: " + (prob3210v2 ? "pass" : "fail"));
		
		System.out.println();
		System.out.println("***Verify 32 variable problem using naive assignment checking***");
		System.out.println();
		
		System.out.println("***Naive assignment check (32 variables): " + (prob1000.isCorrect() ? "pass***" : "fail***"));
	}
}
