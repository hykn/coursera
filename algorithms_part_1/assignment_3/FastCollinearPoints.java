import java.util.*;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints {

	private List<LineSegment> segmentsSet = new ArrayList<LineSegment>();
	private HashMap<Double, ArrayList<Point>> lastPointsList = new HashMap<>();

	public FastCollinearPoints(Point[] points) {
		for (Point temp : points) 
			if (temp == null) 
				throw new java.lang.NullPointerException();
		for (int i = 0; i < points.length; i++)
			for (int j = i + 1; j < points.length; j++)
				if(points[i].compareTo(points[j]) == 0)
					throw new java.lang.IllegalArgumentException();
		
		Point[] pointsSet = Arrays.copyOf(points, points.length);
		Point[] compareSet = Arrays.copyOf(points, points.length);
		double slope = 0;
		
		for (Point original : pointsSet) {
			
			Arrays.sort(compareSet, original.slopeOrder());

			ArrayList<Point> collinearPoints = new ArrayList<>();
			double previousSlope = original.slopeTo(compareSet[0]);

			for (int i = 1; i < compareSet.length; i++) {
				slope = original.slopeTo(compareSet[i]);
				if (slope == previousSlope)
					collinearPoints.add(compareSet[i]);
				else {
					if (collinearPoints.size() >= 3) {
						collinearPoints.add(original);
						addNewSegment(collinearPoints);
					}
					collinearPoints.clear();
					collinearPoints.add(compareSet[i]);
				}
				previousSlope = slope;
			}
			
			if (collinearPoints.size() >= 3) {
				collinearPoints.add(original);
				addNewSegment(collinearPoints);
			}
		}
	}

	private void addNewSegment(ArrayList<Point> collinearPoints) {
		
		Collections.sort(collinearPoints);
		
		Point first = collinearPoints.get(0);
		Point last = collinearPoints.get(collinearPoints.size() - 1);
		
		double slope = first.slopeTo(last);
		ArrayList<Point> endPoints = lastPointsList.get(slope);

		if (endPoints == null) {
			endPoints = new ArrayList<>();
			endPoints.add(last);
			lastPointsList.put(slope, endPoints);
			segmentsSet.add(new LineSegment(first, last));
		}
		else {
			for (Point temp : endPoints) {
				if (temp.compareTo(last) == 0)
					return;
			}
			endPoints.add(last);
			segmentsSet.add(new LineSegment(first, last));
		}
	}

	public int numberOfSegments() {
		return segmentsSet.size();
	}
	
	public LineSegment[] segments() {
		return segmentsSet.toArray(new LineSegment[segmentsSet.size()]);
	}

	public static void main(String args[]) {
		args = new String[]{"C://Users//Yukun//workspace//Algs Assignment_3//src//collinear-testing//collinear//horizontal25.txt"};
        for (String filename : args) {

            // read in the board specified in the filename
            In in = new In(filename);
            int n = in.readInt();
            Point[] points = new Point[n];
            for (int i = 0; i < n; i++) {
                int x = in.readInt();
                int y = in.readInt();
                points[i] = new Point(x, y);
            }
            FastCollinearPoints collinear = new FastCollinearPoints(points);
            StdDraw.enableDoubleBuffering();
	    	StdDraw.setXscale(0, 32768);
	    	StdDraw.setYscale(0, 32768);
	    	
	        for (LineSegment temp : collinear.segments()) {
	        	StdOut.println(temp);
	        	temp.draw();
	        }
	        StdOut.println("The Number of Segments is " + collinear.numberOfSegments());
	        StdDraw.show();
        }
        
	}
}
