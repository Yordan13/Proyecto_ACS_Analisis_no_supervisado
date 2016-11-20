package unittests;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.LinkedList;

import org.junit.Test;

import mainengine.NumericalDataCalculator;

public class TestAverage {

	@Test
	public void test() {
		LinkedList<Double> set = new LinkedList<Double>(
				Arrays.asList(23.5, 45.0, 345.5, 56.7, 5443.0, 1323.0, 444.4, 564.0, 908.0, 1020.0));
		double value = 1017.31;
		double result = calculateAverage(set);
		result = Math.ceil(result * 1000) / 1000;
		assertTrue(value == result);
	}

	private double calculateAverage(LinkedList<Double> set) {
		NumericalDataCalculator calculator = new NumericalDataCalculator();
		return calculator.calculateAverage(set);
	}
}