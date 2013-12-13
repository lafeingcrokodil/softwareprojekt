package main;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import preprocessing.EuclideanMetricSpace;

public class MetricImplTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Point newPoint1 = new Point(1,0);
		Point newPoint2 = new Point(0,0);
		Point newPoint3 = new Point(2,0);
		Point newPoint4 = new Point(0,1);
		Point newPoint5 = new Point(3,4);
		Point newPoint6 = new Point(2,2);
		Point newPoint7 = new Point(1,1);
		
		//Test pointsInRadius
		EuclideanMetricSpace euc = new EuclideanMetricSpace();
		MetricSpaceImplemented<Point> space = new MetricSpaceImplemented(euc);
		space.add(newPoint1);
		space.add(newPoint2);
		space.add(newPoint3);
		space.add(newPoint4);
		space.add(newPoint5);
		space.add(newPoint6);
		space.add(newPoint7);
		//System.out.println(space.distance(newPoint7,newPoint2));
		/*for(Point p : space){
			System.out.println(p.x+", "+p.y);
			System.out.println(space.distance(newPoint2,p));
		}*/
		MetricSpaceImplemented<Point> inR = space.pointsInRadius(newPoint2, 3);
		for(Point p : inR){
			System.out.println(p.x+", "+p.y);
		}
		
		
		/*
		//Test labelAs
		space.labelAs(newPoint1, 1);
		space.labelAs(newPoint2, 2);
		space.labelAs(newPoint3, 3);
		space.labelAs(newPoint4, 1);
		space.labelAs(newPoint5, 2);
		space.labelAs(newPoint6, 3);
		space.labelAs(newPoint7, 1);
		System.out.println(space.prelBranch.isEmpty());
		for(Point each: space.branch){
			System.out.println("a: " + each.x + ", b: " + each.y);
		}
		System.out.println(space.branch.isEmpty());
		for(Point each: space.prelBranch){
			System.out.println("a: " + each.x + ", b: " + each.y);
		}	
		System.out.println(space.edge.isEmpty());
		for(Point each: space.edge){
			System.out.println("a: " + each.x + ", b: " + each.y);
		}
		
		//Test getLabledAs()
		MetricSpaceImplemented<Point> edges = space.getLabeledAs(2);
		MetricSpaceImplemented<Point> branches = space.getLabeledAs(3);
		MetricSpaceImplemented<Point> prelbranches = space.getLabeledAs(1);
		
		System.out.println("edges");
		for(Point each: edges){
			System.out.println("a: " + each.x + ", b: " + each.y);
		}
		System.out.println("branches");
		for(Point each: branches){
			System.out.println("a: " + each.x + ", b: " + each.y);
		}	
		System.out.println("prels");
		for(Point each: prelbranches){
			System.out.println("a: " + each.x + ", b: " + each.y);
		}
		
		
		
		//Test differenceSet
		MetricSpaceImplemented<Point> biggerSpace = new MetricSpaceImplemented();
		MetricSpaceImplemented<Point> smallerSpace = new MetricSpaceImplemented();
		biggerSpace.add(newPoint1);
		biggerSpace.add(newPoint2);
		biggerSpace.add(newPoint3);
		biggerSpace.add(newPoint4);
		smallerSpace.add(newPoint1);
		smallerSpace.add(newPoint2);
		smallerSpace.add(newPoint7);
		MetricSpaceImplemented<Point> differenceSpace = biggerSpace.differenceSet(biggerSpace, smallerSpace);
		for(Point each: differenceSpace){
			System.out.println("a: " + each.x + ", b: " + each.y);
		}
		
		MetricSpaceImplemented<Point> constructorTestSpace = new MetricSpaceImplemented(biggerSpace);
		*/
	}

}
