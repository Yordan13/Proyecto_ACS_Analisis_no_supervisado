package unittests;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.LinkedList;

import org.junit.Test;

import javafx.util.Pair;
import mainengine.CsvGroundtruthreader;

public class TestCsvGroundTruth {

	@Test
	public void test() {
		LinkedList<Pair<Integer, Integer>> cuts;
		try {
			cuts = getCuts("groundtruth.csv");
			assertTrue(validateCuts(cuts));
		} catch (IllegalArgumentException | IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			assertTrue(false);
		}
	}

	private LinkedList<Pair<Integer, Integer>> getCuts(String path) throws IllegalArgumentException, IOException {
		CsvGroundtruthreader manager = new CsvGroundtruthreader();
		LinkedList<Pair<Integer, Integer>> result = manager.getAbsolutecuts(path);
		return result;
	}

	private boolean validateCuts(LinkedList<Pair<Integer, Integer>> data) {
		int previousValue = 0;
		int nextValue = 0;
		Pair<Integer, Integer> pair;
		boolean result = false;
		for (int counter = 0; counter < data.size(); counter++) {
			pair = data.get(counter);
			nextValue = pair.getKey() + pair.getValue();
			if (nextValue <= 0 || nextValue < previousValue) {
				return false;
			}
			previousValue = nextValue;
			result = true;
		}
		return result;
	}
}