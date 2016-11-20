package unittests;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.LinkedList;

import org.junit.Test;

import mainengine.NumericalDataCalculator;

public class TestStdDesviation {

	@Test
	public void test() {
		LinkedList<Double> set = new LinkedList<>(
				Arrays.asList(123.4554, 1234.45, 455.3, 789.09876, 231.23, 3453.98, 1234.2345, 13.09));
		double value = 1047.57;
		double result = calculateStdDesviation(set);
		result = (Math.floor(result * 100)) / 100;
		assertTrue(value == result);
	}

	private double calculateStdDesviation(LinkedList<Double> set) {
		NumericalDataCalculator calculator = new NumericalDataCalculator();
		return calculator.calculateStdDeviation(set);
	}
}