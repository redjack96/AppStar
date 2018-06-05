package test.RequisitoFunzionale_4;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import test.RequisitoFunzionale_3.RegistraUtenteTest;
import test.RequisitoFunzionale_3.NuovoLoginTest;

@RunWith(value=Suite.class)
//@SuiteClasses(value={testClass.class, ...})
@SuiteClasses(value={RegistraUtenteTest.class, NuovoLoginTest.class, BeforeImportazioneTest.class})
public class RF_4_TestSuite {
//	I test saranno avviati direttamente
//  dalle classi specificate in @SuiteClasses.
}
