import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
//import java.util.*;;

public class SAP {
	
	private final Digraph graph;
	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph G) {
		this.graph = new Digraph(G);
	}

	// length of shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w) {
		
		if (v > graph.V() - 1 || w > graph.V() -1 || v < 0 || w < 0) {
			throw new java.lang.IndexOutOfBoundsException();
		}
		
		BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(graph, v);
		BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(graph, w);
		
		int dist = Integer.MAX_VALUE;
		
		for (int i = 0; i < graph.V(); i++) { 
			int distv = bfsv.distTo(i);
			int distw = bfsw.distTo(i);
			
			if (bfsv.hasPathTo(i) && bfsw.hasPathTo(i)) {
				
				if (distv + distw < dist) {
					dist =  distv + distw;
				}
				
			}
		}
		
		return (dist == Integer.MAX_VALUE) ? -1 : dist;
	}
	
	// a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
	public int ancestor(int v, int w) {
		
		if (v > graph.V() - 1 || w > graph.V() -1 || v < 0 || w < 0) {
			throw new java.lang.IndexOutOfBoundsException();
		}
		
		BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(graph, v);
		BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(graph, w);
		
		int dist = Integer.MAX_VALUE;
		int distID = -1;
		
		for (int i = 0; i < graph.V(); i++) { 
			int distv = bfsv.distTo(i);
			int distw = bfsw.distTo(i);
			
			if (distv != Integer.MAX_VALUE && distw != Integer.MAX_VALUE) {
				if (distv + distw < dist) {
					dist =  distv + distw;
					distID = i;
				}
				
			}
		}
		
		return distID;
	}
	
	// length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		
		for (int vv : v) {
			if (vv < 0 || vv > graph.V() - 1) {
				throw new java.lang.IndexOutOfBoundsException();
			}
		}
		
		for (int ww : w) {
			if (ww < 0 || ww > graph.V() - 1) {
				throw new java.lang.IndexOutOfBoundsException();
			}
		}
		
		BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(this.graph, v);
		BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(this.graph, w);
		
		int dist = Integer.MAX_VALUE;
		
		for (int i = 0; i < graph.V(); i++) { 
			int distv = bfsv.distTo(i);
			int distw = bfsw.distTo(i);
			
			if (distv != Integer.MAX_VALUE && distw != Integer.MAX_VALUE) {
				if (distv + distw < dist) {
					dist =  distv + distw;
				}
				
			}
		}
		
		return (dist == Integer.MAX_VALUE) ? -1 : dist;
	}
	
	// a common ancestor that participates in shortest ancestral path; -1 if no such path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		
		for (int vv : v) {
			if (vv < 0 || vv > graph.V() - 1) {
				throw new java.lang.IndexOutOfBoundsException();
			}
		}
		
		for (int ww : w) {
			if (ww < 0 || ww > graph.V() - 1) {
				throw new java.lang.IndexOutOfBoundsException();
			}
		}
		
		
		BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(this.graph, v);
		BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(this.graph, w);
		
		int dist = Integer.MAX_VALUE;
		int distID = -1;
		
		for (int i = 0; i < graph.V(); i++) { 
			int distv = bfsv.distTo(i);
			int distw = bfsw.distTo(i);
			
			if (distv != Integer.MAX_VALUE && distw != Integer.MAX_VALUE) {
				if (distv + distw < dist) {
					dist =  distv + distw;
					distID = i;
				}
				
			}
		}
		
		return distID;
	}
	
	// do unit testing of this class
	public static void main(String[] args) {
		String folder = "wordnet/";
		
	    In in = new In(folder + "digraph2.txt"); 
	    Digraph G = new Digraph(in);
	    SAP sap = new SAP(G);
	    G.addEdge(0, 1);
        int v = 0;
        int w = 1;
        int length   = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        System.out.println("length = " + length + ", ancestor = " + ancestor + "\n");
	    
	   
//	    ArrayList<Integer> vList = new ArrayList<Integer>(Arrays.asList(-1));
//		ArrayList<Integer> wList = new ArrayList<Integer>(Arrays.asList(5));
//		    
//		StdOut.printf("length = %d, ancestor = %d\n", sap.length(vList, wList), sap.ancestor(vList, wList));
	}
}