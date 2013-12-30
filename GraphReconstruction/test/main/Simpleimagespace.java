package main;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import javax.imageio.ImageIO;

/*
 * aeltere version zum einfachen testen, normale version wirft bei mir fehler
 * 
 * 
 */



/**
 * simple metric space from imagees.
 */
public class Simpleimagespace implements Set<Point2D>, MetricSpace<Point2D> {

	private LinkedList<Point2D> pixel;
	/**
	 * Creates a new ImageMetricSpace based on the black pixels in
	 * the specified image file.
	 * 
	 * @param filename the name of the image file
	 * @throws IOException if an error occurs while reading the image
	 */
	public Simpleimagespace(String filename) throws IOException 
	{   pixel = new LinkedList<Point2D>();
		BufferedImage image = ImageIO.read(new File(filename));
		extractPixels(image, Color.BLACK);
	}
	/**
	 * Create an empty space
	 * @throws IOException
	 */
	public Simpleimagespace() throws IOException 
	{   pixel = new LinkedList<Point2D>();
	}

	public double distance(Point2D a, Point2D b) {
		
		double x = a.getX()-b.getX();
		double y = a.getY()-b.getY();
		double res = Math.sqrt(x*x+y*y);
		return res;
	}

	private void extractPixels(BufferedImage image, Color colour) {
		Point topLeft = new Point(image.getMinX(), image.getMinY());
		Point bottomRight = new Point(topLeft.x + image.getWidth(), topLeft.y + image.getHeight());
		for (int y = topLeft.y; y < bottomRight.y; y++) {
			for (int x = topLeft.x; x < bottomRight.x; x++) {
				if (image.getRGB(x, y) == Color.BLACK.getRGB()){
					this.pixel.add(new Point(x, y));
				}
			}
		}
	}

	public boolean add(Point2D e) {
		return this.pixel.add(e);
	}

	public boolean addAll(Collection<? extends Point2D> c) {
		return this.pixel.addAll(c);
	}

	public void clear() {
		this.pixel.clear();
	}

	public boolean contains(Object o) {
		return this.pixel.contains(o);
	}
	public boolean containsAll(Collection<?> c) {
		return this.pixel.containsAll(c);
	}
	public boolean isEmpty() {
		return this.pixel.isEmpty();
	}
	public Iterator<Point2D> iterator() {
		return this.pixel.iterator();
	}
	public boolean remove(Object o) {
		return this.pixel.remove(o);
	}
	public boolean removeAll(Collection<?> c) {
		return this.pixel.removeAll(c);
	}
	public boolean retainAll(Collection<?> c) {
		return this.pixel.retainAll(c);
	}

	public int size() {
		return this.pixel.size();
	}

	public Object[] toArray() {
		return this.pixel.toArray();
	}
	
	public <T> T[] toArray(T[] a) {
		return this.pixel.toArray(a);
	}

}
