
import edu.princeton.cs.algs4.*;
import java.util.*;

public class PointSET {

	private SET<Point2D> set;

	public PointSET() {
		set = new SET<Point2D>();
	}

	public boolean isEmpty() {
		return set.size() == 0;
	}
	
	public int size() {
		return set.size();
	}
	
	public void insert(Point2D p) {
		if (p == null)
			throw new java.lang.NullPointerException();
		set.add(p);
	}
	
	public boolean contains(Point2D p) {
		if (p == null)
			throw new java.lang.NullPointerException();
		return set.contains(p);
	}

	public void draw() {
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(0.01);
		
		for (Point2D temp : set) 
			StdDraw.point(temp.x(), temp.y());
		StdDraw.show();
	}

	public Iterable<Point2D> range(RectHV rect) {
		ArrayList<Point2D> inPoints = new ArrayList<Point2D>();

		for (Point2D temp : set)
			if (rect.contains(temp))
				inPoints.add(temp);

		return inPoints;
	}

	public Point2D nearest(Point2D p) {
		if (isEmpty())
			return null;

		double dis = 0;
		double minDis = Double.MAX_VALUE;
		Point2D nearestPoint = null;
		
		for (Point2D temp : set) {
			dis = p.distanceTo(temp);
			if (dis < minDis) {
				minDis = dis;
				nearestPoint = temp;
			}
		}
		
		return nearestPoint;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		args = new String[]{"C://Users//Yukun//workspace//Algs Assignment_5//src//kdtree-testing//kdtree//circle10.txt"};
        for (String filename : args) {
        	PointSET set = new PointSET();
            // read in the board specified in the filename
            In in = new In(filename);
            for (int i = 0; !in.isEmpty(); i++) {
                double x = in.readDouble();
                double y = in.readDouble();
                set.insert(new Point2D(x, y));
            }
            set.draw();
        }
	}

}
