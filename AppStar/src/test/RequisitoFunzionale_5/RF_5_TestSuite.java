package test.RequisitoFunzionale_5;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(value=Suite.class)
//@SuiteClasses(value={testClass.class, ...})
@Suite.SuiteClasses(value={CalcolaCentroideTest.class, CalcolaEstensioneTest.class, CalcolaNumeroSegmentiTest.class})
public class RF_5_TestSuite {
//	I test saranno avviati direttamente
//  dalle classi specificate in @SuiteClasses.
}