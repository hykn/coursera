import edu.princeton.cs.algs4.*;
import java.util.*;

public class KdTree {

	private Node root;
	private int size;
	
	private static class Node {
		private Point2D point;
		private boolean isVertical;
		private Node left;
		private Node right;

		public Node(Point2D pt, boolean vertical) {
			point = pt;
			isVertical = vertical;
		}

		public boolean interSectLeft(RectHV rect) {
			if (isVertical) {
				if (rect.xmin() > point.x())
					return false;
				else
					return true;

			}
			else {
				if (rect.ymin() > point.y())
					return false;
				else
					return true;
			}
		}

		public boolean interSectRight(RectHV rect) {
			if (isVertical) {
				if (rect.xmax() < point.x())
					return false;
				else
					return true;
			}
			else {
				if (rect.ymax() < point.y())
					return false;
				else
					return true;
			}
		}
	}

	public KdTree() {
		root = null;
		size = 0;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}
	
	public void insert(Point2D pt) {
		if (pt == null)
			throw new java.lang.NullPointerException();
		if (this.contains(pt))
			return;
		
		boolean VERTICAL = true;
		boolean HORIZONAL = false;

		size++;
		if (root == null)
			root = new Node(pt, VERTICAL);
		else {
			Node temp = root;
			while (temp != null) {
				if (temp.isVertical)
					if (pt.x() < temp.point.x()) {
						if (temp.left == null) {
							temp.left = new Node(pt, HORIZONAL);
							return;
						}
						else
							temp = temp.left;
					}
					else {
						if (temp.right == null) {
							temp.right = new Node(pt, HORIZONAL);
							return;
						}
						else
							temp = temp.right;
					}
				else {
					if (pt.y() < temp.point.y()) {
						if (temp.left == null) {
							temp.left = new Node(pt, VERTICAL);
							return;
						}
						else
							temp = temp.left;
					}
					else {
						if (temp.right == null) {
							temp.right = new Node(pt, VERTICAL);
							return;
						}
						else
							temp = temp.right;
					}
				}
			}

		}
	}
	
	public boolean contains(Point2D pt) {

		Node temp = root;
		while (temp != null) {
			if (temp.point.equals(pt))
				return true;

			if (temp.isVertical) {
				if (pt.x() < temp.point.x())
					temp = temp.left;
				else
					temp = temp.right;
			}
			else {
				if (pt.y() < temp.point.y())
					temp = temp.left;
				else 
					temp = temp.right;
			}
		}

		return false;
	}
	
	private void draw(Node next) {
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(0.02);

		StdDraw.point(next.point.x(), next.point.y());

		if (next.isVertical) {
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.setPenRadius(0.005);
			StdDraw.line(next.point.x(), 0, next.point.x(), 1);
		}
		else {
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.setPenRadius(0.005);
			StdDraw.line(0, next.point.y(), 1, next.point.y());
		}
		if (next.left != null)
			draw(next.left);
		if (next.right != null)
			draw(next.right);

	}
	
	public void draw() {
		if (isEmpty())
			return;
		
		StdDraw.rectangle(0.5, 0.5, 0.5, 0.5);
		draw(root);
	}

	private void searchRange(Node next, RectHV rect, ArrayList<Point2D> pointSet) {
		if (next == null)
			return;

		if (rect.contains(next.point))
			pointSet.add(next.point);

		if (next.left != null && next.interSectLeft(rect))
			searchRange(next.left, rect, pointSet);

		if (next.right != null && next.interSectRight(rect))
			searchRange(next.right, rect, pointSet);
	}	
	
	public Iterable<Point2D> range(RectHV rect) {
		ArrayList<Point2D> pointSet = new ArrayList<Point2D>();
		searchRange(root, rect, pointSet);
		return pointSet;
	}

	private Point2D searchNearest(Node next, Point2D pt, Point2D minPoint) {
		Point2D min = minPoint;

		if (next == null)
			return min;

		if (pt.distanceTo(next.point) < pt.distanceTo(min))
			min = next.point;

		if (next.isVertical) {
			if (pt.x() < next.point.x()) {
				min = searchNearest(next.left, pt, min);
				if (min.distanceTo(pt) > (next.point.x() - pt.x()))
					min = searchNearest(next.right, pt, min);
			}
			else {
				min = searchNearest(next.right, pt, min);
				if (min.distanceTo(pt) > pt.x() - next.point.x())
					min = searchNearest(next.left, pt, min);
			}

		}
		else {
			if (pt.y() < next.point.y()) {
				min = searchNearest(next.left, pt, min);
				if (min.distanceTo(pt) > next.point.y() - pt.y())
					min = searchNearest(next.right, pt, min);
			}
			else {
				min = searchNearest(next.right, pt, min);
				if (min.distanceTo(pt) > pt.y() - next.point.y())
					min = searchNearest(next.left, pt, min);
			}

		}

		return min;
	}
	
	public Point2D nearest(Point2D pt) {
		if (isEmpty())
			return null;
		return searchNearest(root, pt, root.point);
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		args = new String[]{"C://Users//Yukun//workspace//Algs Assignment_5//src//kdtree-testing//kdtree//input10K.txt"};
        for (String filename : args) {
        	KdTree set = new KdTree();
            // read in the board specified in the filename
            In in = new In(filename);
//            for (int i = 0; !in.isEmpty(); i++) {
//                double x = in.readDouble();
//                double y = in.readDouble();
//                set.insert(new Point2D(x, y));
//            }
//            kdtree.draw();
//            Point2D test = kdtree.nearest(new Point2D(0.81, 0.30));
//            RectHV rect = new RectHV(0, 0, 0, 1);
////            kdtree.draw();
//            StdDraw.setPenRadius(0.01);
//            for (Point2D temp : kdtree.range(rect)){
////            	StdDraw.setPenRadius(0.002);
////            	StdDraw.circle(temp.x(), temp.y(), 0.03);
//            	StdDraw.setPenRadius(0.01);
//            	StdDraw.point(temp.x(), temp.y());
//            }
//            StdDraw.setPenColor(StdDraw.BLACK);
//            StdDraw.setPenRadius(0.002);
//            StdDraw.rectangle((rect.xmax() + rect.xmin()) / 2, (rect.ymax() + rect.ymin()) / 2, (rect.xmax() - rect.xmin()) / 2, (rect.ymax() - rect.ymin()) / 2);
//            StdOut.println("size: " + kdtree);
           
            
            int N = 5;
            double rd = 0;
            for (int i = 0; i < N; i++) {
            	rd = StdRandom.uniform();
            	set.insert(new Point2D(rd, 0.5));
            	set.draw();
            }
            RectHV rect = new RectHV(0.01, 0.1, 0.99, 0.5);
            
            
            for (Point2D temp : set.range(rect)) {
            	StdDraw.setPenRadius(0.002);
            	StdDraw.circle(temp.x(), temp.y(), 0.03);
            	StdOut.println("(" + temp.x() + ", " + temp.y() + ")");
//        	StdDraw.setPenRadius(0.01);
//        	StdDraw.point(temp.x(), temp.y());
            }
            
            StdDraw.rectangle((rect.xmax() + rect.xmin()) / 2, (rect.ymax() + rect.ymin()) / 2, (rect.xmax() - rect.xmin()) / 2, (rect.ymax() - rect.ymin()) / 2);
            
        }
	}

}
