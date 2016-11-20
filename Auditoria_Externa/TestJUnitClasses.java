/*
 * @author Daniel Troyo
 * 
 * @version 0.1.0
 */
package unittests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * The Class TestJUnitClasses. Main connector of the . It does not belongs to
 * the public API of the project.
 */
@RunWith(Suite.class)

@Suite.SuiteClasses({ TestVideoRead.class, TestStdDesviation.class, TestCsvGroundTruth.class, TestAverage.class,
		TestServer.class, TestGenerateDistance.class })

public class TestJUnitClasses {
}
// File file = new File("C:\\Users\\fm010\\Desktop\\geckodriver.exe");
// System.setProperty("webdriver.gecko.driver", file.getAbsolutePath());