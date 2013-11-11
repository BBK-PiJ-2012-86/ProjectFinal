package utilities;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import structure.EqnTestDeterministic;

@RunWith(Suite.class)
@SuiteClasses({ ManipTest.class, RandTest.class, WHTest.class, EqnTestDeterministic.class })
public class DeterministicTests {

}
