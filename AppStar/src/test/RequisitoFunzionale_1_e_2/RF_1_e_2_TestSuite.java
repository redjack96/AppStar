package test.RequisitoFunzionale_1_e_2;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value=Suite.class)
//@SuiteClasses(value={testClass.class, ...})
@SuiteClasses(value={LoginTest.class})
public class RF_1_e_2_TestSuite {
//	I test saranno avviati direttamente
//  dalle classi specificate in @SuiteClasses.
}