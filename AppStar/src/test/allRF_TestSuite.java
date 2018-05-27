package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import test.RequisitoFunzionale_11.RF_11_TestSuite;
import test.RequisitoFunzionale_12.RF_12_TestSuite;
import test.RequisitoFunzionale_3.RF_3_TestSuite;
import test.RequisitoFunzionale_5.RF_5_TestSuite;
import test.RequisitoFunzionale_6.RF_6_TestSuite;
import test.RequisitoFunzionale_7.RF_7_TestSuite;
import test.RequisitoFunzionale_9.RF_9_TestSuite;
import test.RequisitoFunzionale_1_e_2.RF_1_e_2_TestSuite;

@RunWith(value=Suite.class)
//@SuiteClasses(value={testClass.class, ...})
@SuiteClasses(value={RF_1_e_2_TestSuite.class, RF_3_TestSuite.class, RF_5_TestSuite.class, RF_6_TestSuite.class,
        RF_7_TestSuite.class, RF_9_TestSuite.class, RF_11_TestSuite.class, RF_12_TestSuite.class})
public class allRF_TestSuite {
//	I test saranno avviati direttamente
//  dalle classi specificate in @SuiteClasses.
}
