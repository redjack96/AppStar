package test.RequisitoFuzionale_1_e_2;

import org.junit.After;
import org.junit.Before;

public class BeforeAfterLoginTest extends LoginTest {

    @Before
    public void configureTheEnvironment(){ System.err.println("\n----------INIZIO TEST----------\n"); }

    @After
    public void releaseResources(){ System.err.println("\n----------FINE TEST-----------\n"); }
}

