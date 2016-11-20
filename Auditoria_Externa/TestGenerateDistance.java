package unittests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;
import org.opencv.videoio.Videoio;

import mainengine.BhattacharyyaHandler;
import mainengine.DistanceHistogramGenerator;
import mainengine.HistogramProcessor;
import mainengine.VideoSegmenter;

public class TestGenerateDistance {

	private static VideoSegmenter vidProcessor;
	private static HistogramProcessor histProcessor;
	private static DistanceHistogramGenerator distanceGenerator;

	@Before
	public void init() {
		vidProcessor = new VideoSegmenter();
		histProcessor = new HistogramProcessor();
		distanceGenerator = new BhattacharyyaHandler();
	}

	@Test
	public void test() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		String videoPath = "Dissolve1-15.mp4";
		try {
			LinkedList<Double> distances = executeDistanceGenerator(videoPath);
			VideoCapture videoCapture = new VideoCapture(videoPath);
			int amountFrames = (int) videoCapture.get(Videoio.CAP_PROP_FRAME_COUNT);
			boolean result = validateDistances(distances, amountFrames);
			assertTrue(result);
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void test2() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		String videoPath = "wrongURL";
		try {
			LinkedList<Double> distances = executeDistanceGenerator(videoPath);
			fail("La prueba deber�a fallar");
		} catch (IOException e) {
			assertTrue(true);
		}
	}

	private LinkedList<Double> executeDistanceGenerator(String videoPath) throws IOException {
		LinkedList<Mat> frames = vidProcessor.splitVideosToHSV(videoPath);
		LinkedList<Mat> histogramList = histProcessor.calculateHistoOfHueVideo(frames);
		LinkedList<Double> distanceArray = distanceGenerator.generateDistanceArray(histogramList);
		return distanceArray;
	}

	private boolean validateDistances(LinkedList<Double> distances, int amountFrames) {
		if (distances.size() < amountFrames - 1) {
			return false;
		}
		for (int counter = 0; counter < distances.size(); counter++) {
			double value = distances.get(counter);
			if (value < 0 && value > 1) {
				return false;
			}
		}
		return true;
	}
}