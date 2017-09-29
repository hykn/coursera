import java.util.*;
import java.util.*;
import java.util.Stack;
import java.util.Collections;

import edu.princeton.cs.algs4.*;

public class Solver {

	private class SearchNode implements Comparable<SearchNode> {
		private SearchNode previous;
		private Board board;
		private int moveNums = 0;

		public SearchNode(Board board) {	
			this.board = board;
		}
		
		public SearchNode(SearchNode that) {
			this.board = that.board;
			this.previous = that.previous;
			this.moveNums = that.moveNums;
		}

		public SearchNode(Board board, SearchNode previous) {
			this.board = board;
			this.previous = previous;
			this.moveNums = previous.moveNums + 1;
		}

		public int compareTo(SearchNode node) {
			return (this.board.manhattan() - node.board.manhattan()) + (this.moveNums - node.moveNums);
		}
	}

	private SearchNode lastMove;

	public Solver(Board initial) {

		if (initial == null) throw new java.lang.NullPointerException();

		// if (min < 0) throw new java.lang.NullPointerException();
		// if (max > )
		MinPQ<SearchNode> node = new MinPQ<SearchNode> ();
		node.insert(new SearchNode(initial));
		
		MinPQ<SearchNode> twinMoves = new MinPQ<SearchNode>();
        twinMoves.insert(new SearchNode(initial.twin()));

		while(true) {
			lastMove = Search(node);
			if (lastMove != null || Search(twinMoves) != null)
				return;
		}
	}

	private SearchNode Search(MinPQ<SearchNode> node) {
		if(node.isEmpty()) return null;
		SearchNode bestSol = node.delMin();

		if (bestSol.board.isGoal())
			return bestSol;

		for (Board neighbor : bestSol.board.neighbors()) {
			if (bestSol.previous == null || !neighbor.equals(bestSol.previous.board)) {
				node.insert(new SearchNode(neighbor, bestSol));
			}
		}

		return null;
	}

	public boolean isSolvable() {
		return (lastMove != null);
	}

	public int moves() {
		if (isSolvable())
			return lastMove.moveNums;
		else 
			return -1;
	}

	public Iterable<Board> solution() {

		if (!isSolvable()) return null;
		ArrayList<Board> moves = new ArrayList<Board>();
		SearchNode temp = new SearchNode(lastMove);

		while (temp != null) {
			moves.add(0, temp.board);
			temp = temp.previous;
		}

		return moves;
	}

	public static void main(String[] args) {

	    // create initial board from file
//	    In in = new In(args[0]);
//	    int n = in.readInt();
//	    int[][] blocks = new int[n][n];
//	    for (int i = 0; i < n; i++)
//	        for (int j = 0; j < n; j++)  
//	            blocks[i][j] = in.readInt();
		int[][] blocks = new int[][]{{1, 2, 3}, {4, 0, 5}, {7, 8, 6}};

	    Board initial = new Board(blocks);

	    // solve the puzzle
	    Solver solver = new Solver(initial);

	    // print solution to standard output
	    if (!solver.isSolvable())
	        StdOut.println("No solution possible");
	    else {
	        StdOut.println("Minimum number of moves = " + solver.moves());
	        for (Board board : solver.solution())
	            StdOut.println(board);
		}
	}
}