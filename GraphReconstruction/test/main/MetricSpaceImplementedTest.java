package main;
import java.awt.Point;
import java.util.*;

import org.junit.Test;

import preprocessing.EuclideanMetricSpace;

import static org.junit.Assert.*;

public class MetricSpaceImplementedTest {
	/*
	 * Create test points
	 */
	Point newPoint1 = new Point(1,0);
	Point newPoint2 = new Point(0,0);
	Point newPoint3 = new Point(2,0);
	Point newPoint4 = new Point(0,1);
	Point newPoint5 = new Point(3,4);
	Point newPoint6 = new Point(2,2);
	Point newPoint7 = new Point(1,1);
	
	@Test
	public void testPointsInRadius(){
		EuclideanMetricSpace euc = new EuclideanMetricSpace();
		MetricSpaceImplemented<Point> space = new MetricSpaceImplemented(euc);
		space.add(newPoint1);
		space.add(newPoint2);
		space.add(newPoint3);
		space.add(newPoint4);
		space.add(newPoint5);
		space.add(newPoint6);
		space.add(newPoint7);
//		MetricSpaceImplemented<Point> expectedSpace = new MetricSpaceImplemented();
//		expectedSpace.add(newPoint1);
//		expectedSpace.add(newPoint4);
//		expectedSpace.add(newPoint2);
//		MetricSpaceImplemented<Point> inR = space.pointsInRadius(newPoint2, 1);
//		assertEquals(expectedSpace,inR);
	}
	
	@Test
	public void testLabelPointsInRadius(){
		EuclideanMetricSpace euc = new EuclideanMetricSpace();
		MetricSpaceImplemented<Point> space = new MetricSpaceImplemented(euc);
		space.add(newPoint1);
		space.add(newPoint2);
		space.add(newPoint3);
		space.add(newPoint4);
		space.add(newPoint5);
		space.add(newPoint6);
		space.add(newPoint7);
//		MetricSpaceImplemented<Point> expectedSpace = new MetricSpaceImplemented();
//		expectedSpace.add(newPoint1);
//		expectedSpace.add(newPoint4);
//		expectedSpace.add(newPoint2);
//		MetricSpaceImplemented<Point> inR = space.pointsInRadius(newPoint2, 1);
//		space.labelInRadius(newPoint2,1);
//		assertEquals(space.branch,inR);
	}
	
	@Test
	public void testLabelAsAndGetLabeledAs(){
//		MetricSpaceImplemented<Point> space = new MetricSpaceImplemented();
//		space.labelAs(newPoint1, 1);
//		space.labelAs(newPoint2, 2);
//		space.labelAs(newPoint3, 3);
//		space.labelAs(newPoint4, 1);
//		space.labelAs(newPoint5, 2);
//		space.labelAs(newPoint6, 3);
//		space.labelAs(newPoint7, 1);
//		MetricSpaceImplemented<Point> expectedSpace1 = new MetricSpaceImplemented();
//		MetricSpaceImplemented<Point> expectedSpace2 = new MetricSpaceImplemented();
//		MetricSpaceImplemented<Point> expectedSpace3 = new MetricSpaceImplemented();
//		expectedSpace1.add(newPoint1);
//		expectedSpace1.add(newPoint4);
//		expectedSpace1.add(newPoint7);
//		expectedSpace2.add(newPoint2);
//		expectedSpace2.add(newPoint5);
//		expectedSpace3.add(newPoint3);
//		expectedSpace3.add(newPoint6);
//		//Test labelAs()
//		assertEquals(expectedSpace1, space.prelBranch);
//		assertEquals(expectedSpace2, space.edge);
//		assertEquals(expectedSpace3, space.branch);
//		//Test getLabeledAs()
//		assertEquals(expectedSpace1, space.getLabeledAs(1));
//		assertEquals(expectedSpace2, space.getLabeledAs(2));
//		assertEquals(expectedSpace3, space.getLabeledAs(3));
	}
	
	@Test
	public void testDifferenceSet(){
//		MetricSpaceImplemented<Point> biggerSpace = new MetricSpaceImplemented();
//		MetricSpaceImplemented<Point> smallerSpace = new MetricSpaceImplemented();
//		biggerSpace.add(newPoint1);
//		biggerSpace.add(newPoint2);
//		biggerSpace.add(newPoint3);
//		biggerSpace.add(newPoint4);
//		biggerSpace.add(newPoint7);
//		smallerSpace.add(newPoint1);
//		smallerSpace.add(newPoint2);
//		smallerSpace.add(newPoint6);
//		MetricSpaceImplemented<Point> differenceSpace = biggerSpace.differenceSet(biggerSpace, smallerSpace);
//		MetricSpaceImplemented<Point> expectedSpace = new MetricSpaceImplemented();
//		expectedSpace.add(newPoint3);
//		expectedSpace.add(newPoint4);
//		expectedSpace.add(newPoint7);
//		assertEquals(expectedSpace, differenceSpace);
	}
	@Test
	public void testConstructor(){
		
//		MetricSpaceImplemented<Point> inputSpace = new MetricSpaceImplemented();
//		inputSpace.add(newPoint1);
//		inputSpace.add(newPoint2);
//		inputSpace.add(newPoint3);
//		inputSpace.add(newPoint4);
//		inputSpace.add(newPoint7);
//		MetricSpaceImplemented<Point> constructorTestSpace = new MetricSpaceImplemented(inputSpace);
//		assertEquals(constructorTestSpace, inputSpace);	
	}
	
	public void testLabelInRadius(){
		//TODO
	}
}
