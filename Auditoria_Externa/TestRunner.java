/*
 * @author Daniel Troyo
 * 
 * @version 0.1.0
 */
package unittests;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.opencv.core.Core;

// TODO: Auto-generated Javadoc
/**
 * The Class TestRunner. This class contains an interface for executing the
 * unitary tests. It does not belongs to the public API of the project.
 */
public class TestRunner {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // Carga la librer�a
		System.loadLibrary("opencv_java310");
		Result result = JUnitCore.runClasses(TestJUnitClasses.class);

		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}

		System.out.println(result.wasSuccessful());
	}
}
