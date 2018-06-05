package test.RequisitoFunzionale_11;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(value=Suite.class)
//@SuiteClasses(value={testClass.class, ...})
@Suite.SuiteClasses(value={CalcolaDistanzaSegConTest.class})
public class RF_11_TestSuite {
//	I test saranno avviati direttamente
//  dalle classi specificate in @SuiteClasses.
}