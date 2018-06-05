package test.RequisitoFunzionale_3;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value=Suite.class)
// li esegue in ordine, bisogna mettere la classe estesa beforeAfter invece di quella padre!!!
@SuiteClasses(value={RegistraUtenteTest.class, NuovoLoginTest.class, BeforeAfterInserisciDatiStrumentoTest.class })
public class RF_3_TestSuite {
//	I test saranno avviati direttamente
//  dalle classi specificate in @SuiteClasses.
}