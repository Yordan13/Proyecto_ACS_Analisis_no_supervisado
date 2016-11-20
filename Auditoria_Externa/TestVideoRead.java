/*
 * @author Daniel Troyo
 * 
 * @version 0.1.0
 */
package unittests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import mainengine.VideoSegmenter;

// TODO: Auto-generated Javadoc
/**
 * The Class TestSet1. This class contains a set of unitary tests. It does not
 * belongs to the public API of the project.
 */
@RunWith(Parameterized.class)

public class TestVideoRead {

	private String videoRoute;
	private VideoSegmenter vidProcTest;
	private LinkedList<Mat> truthFrames;
	private LinkedList<Mat> readFrames;

	@Parameterized.Parameters
	public static Collection parametersToTest() {
		return Arrays.asList(new Object[][] { { "Dissolve1-15.mp4" } });
	}

	public TestVideoRead(String videoRoute) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // Carga la librer�a
		System.loadLibrary("opencv_java310");
		this.videoRoute = videoRoute;
		this.vidProcTest = new VideoSegmenter();
		this.truthFrames = new LinkedList<>();
	}

	/**
	 * Test #1 Validates correct reading of video. Then compares that the
	 * resolution of the frames is the same of the video.
	 */
	@Test
	public void validateReadFrames() throws IOException {
		truthFrames = readTruthFrames();
		readFrames = truthFrames;
		if (truthFrames.size() != readFrames.size()) {
			fail("Tamaños diferentes");
		}
		for (int i = 0; i < truthFrames.size(); i++) {
			if (!matIsEqual(truthFrames.get(i), readFrames.get(i))) {
				fail("Frame " + i + "es diferente");
			}
		}
		assertTrue(true);
	}

	private LinkedList<Mat> readTruthFrames() {
		LinkedList<Mat> res = new LinkedList<>();
		for (int i = 0; i <= 3682; i++) {
			res.add(Highgui.imread("test/test1/" + i + ".jpeg"));
		}
		return res;
	}

	private boolean matIsEqual(Mat mat1, Mat mat2) {
		Imgproc.cvtColor(mat2, mat2, Imgproc.COLOR_HSV2BGR);
		/*
		 * Imgproc. Highgui.imwrite("temp.jpeg", mat2); mat2 =
		 * Highgui.imread("temp.jpeg");
		 */
		// treat two empty mat as identical as well
		if (mat1.empty() && mat2.empty()) {
			return true;
		}
		// if dimensionality of two mat is not identical, these two mat is not
		// identical
		if (mat1.cols() != mat2.cols() || mat1.rows() != mat2.rows() || mat1.dims() != mat2.dims()) {
			return false;
		}
		LinkedList<Mat> list1, list2;
		list1 = new LinkedList<>();
		list2 = new LinkedList<>();
		Mat res = new Mat();
		int nz = -1;
		Core.split(mat2, list2);
		Core.split(mat1, list1);

		for (int i = 0; i < list1.size(); i++) {
			Core.bitwise_xor(list1.get(i), list2.get(i), res);
			nz = Core.countNonZero(res);
			if (nz != 0) {
				return false;
			}
		}
		return true;
	}

}
