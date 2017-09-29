import java.util.ArrayList;;

public class Board {

	private int n;
	private int[][] tiles;

	private int blankRow;
	private int blankCol;

	// construct a board from an n-by-n array of tiles
	// (where tiles[i][j] = block in row i, column j)
	public Board(int[][] blocks) {

		this.n = blocks.length;
		this.tiles = new int[n][n];
		this.blankRow = 0;
		this.blankCol = 0;

		int temp = 1;

		for (int i = 0; i < this.n; i++) 
			for (int j = 0; j < this.n; j++) {
				this.tiles[i][j] = blocks[i][j];
				// this.goal[i][j] = temp;
				if (this.tiles[i][j] == 0) {
					blankRow = i;
					blankCol = j;
				}

				temp++;
			}
		// this.goal[n - 1][n - 1] = 0;

	}

	public int dimension() {
		return this.n;
	}

	public int hamming() {

		int distance = 0;
		int goal = 1;
		for (int i = 0; i < this.n; i++) 
			for (int j = 0; j < this.n; j++) {
				if ((this.tiles[i][j] != goal) && (goal != this.n * this.n))
					distance++;
				goal++;
			}
		return distance;
	}

	public int manhattan() {
		int distance = 0;
		int value = 0;

		for (int i = 0; i < this.n; i++)
			for (int j = 0; j < this.n; j++) {
				value = this.tiles[i][j];
				if (value == 0)
					continue;
				distance += Math.abs((value - 1) / this.n - i);
				distance += Math.abs((value - 1) % this.n - j);
			}

		return distance;
	}

	public boolean isGoal() {
		if (hamming() == 0)
			return true;
		else
			return false;
	}

	public Board twin() {
		for (int i = 0; i < n - 1; i++)
			for (int j = 0; j < n; j++)
				if (this.tiles[i][j] != 0 && this.tiles[i + 1][j] != 0)
					return new Board(swap(i, j, i + 1, j));
		
		throw new RuntimeException();
	}

	public boolean equals(Object y) {
		if (y == this) return true;
		if (y == null) return false;
		if (y.getClass() != this. getClass()) return false;

		Board that = (Board) y;
		if (that.dimension() != this.n) return false;

		for (int i = 0; i < this.n; i++) 
			for (int j = 0; j < this.n; j++)
				if(that.tiles[i][j] != this.tiles[i][j])
					return false;

		return true;
	}

	public Iterable<Board> neighbors() {
		
		ArrayList<Board> neighborsR = new ArrayList<Board>();

		if (this.blankRow > 0)
			neighborsR.add(new Board(swap(this.blankRow, this.blankCol, this.blankRow - 1, this.blankCol)));
		if (this.blankRow < this.n - 1)
			neighborsR.add(new Board(swap(this.blankRow, this.blankCol, this.blankRow + 1, this.blankCol)));
		if (this.blankCol > 0)
			neighborsR.add(new Board(swap(this.blankRow, this.blankCol, this.blankRow, this.blankCol - 1)));
		if (this.blankCol < this.n - 1)
			neighborsR.add(new Board(swap(this.blankRow, this.blankCol, this.blankRow, this.blankCol + 1)));


		return neighborsR;
	}

	public String toString() {
	    StringBuilder s = new StringBuilder();
	    s.append(n + "\n");
	    for (int i = 0; i < n; i++) {
	        for (int j = 0; j < n; j++) {
	            s.append(String.format("%2d ", tiles[i][j]));
	        }
	        s.append("\n");
	    }
	    return s.toString();
	}

	private int[][] arrayCopy(int[][] original) {

		int[][] copy = new int[this.n][this.n];
		for (int i = 0; i < this.n; i++) 
			for (int j = 0; j < this.n; j++)
				copy[i][j] = original[i][j];

		return copy;
	}

	private int[][] swap(int row1, int column1, int row2, int column2) {
		int[][] afterSwap = arrayCopy(this.tiles);
		int temp = afterSwap[row1][column1];
		afterSwap[row1][column1] = afterSwap[row2][column2];
		afterSwap[row2][column2] = temp;
		
		return afterSwap;
	}
	public static void main(String[] args) {

	}
}

