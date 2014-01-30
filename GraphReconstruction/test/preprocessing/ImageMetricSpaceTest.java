package preprocessing;

import static org.junit.Assert.*;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import main.MetricSpace;

import org.junit.Test;

import preprocessing.ImageMetricSpace;

public class ImageMetricSpaceTest {

	@Test
	public void testExtractPixels() throws IOException {
		Set<Point> expectedSet = new HashSet<>();
		expectedSet.add(new Point(0, 0));
		expectedSet.add(new Point(2, 0));
		expectedSet.add(new Point(1, 1));

		MetricSpace<Point2D> space = new ImageMetricSpace("images/tinyTest.png", 0, 2);

		assertEquals(space, expectedSet);
	}

}
