package test.RequisitoFunzionale_3;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import test.RequisitoFuzionale_1_e_2.BeforeAfterLoginTest;

@RunWith(value=Suite.class)
//@SuiteClasses(value={testClass.class, ...})
@SuiteClasses(value={RegistraUtenteTest.class, NuovoLoginTest.class, InserimentoDatiStrumentoTest.class})
public class RF_3_TestSuite {
//	I test saranno avviati direttamente
//  dalle classi specificate in @SuiteClasses.
}