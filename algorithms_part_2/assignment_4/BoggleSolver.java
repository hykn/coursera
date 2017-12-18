import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;


public class BoggleSolver {
	
	private revisedTST<Integer> tst;
	private boolean[][] isVisited;
	private int row;
	private int col;
	private int[][][][] posAdj;
	
	private BoggleBoard board;
	private SET<String> valid;
	
	
	private int score(String word) {
		int len = word.length();
		if (len <= 2)
			return 0;
		else if (len <= 4)
			return 1;
		else if (len == 5)
			return 2;
		else if (len == 6)
			return 3;
		else if (len == 7)
			return 5;
		else 
			return 11;
	}
	
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
    	
    	this.tst = new revisedTST<Integer>();
    	for (String word : dictionary) {
    		this.tst.put(word, score(word));
    	}
    }
    
    
    private int[][] adjacent(int posI, int posJ) {
    	int[][] validAdj = new int[8][3];
    	if (posI > 0) {
    		validAdj[0][0] = posI - 1;
    		validAdj[0][1] = posJ;
    		validAdj[0][2] = 1;
    	}
    	if (posI > 0 && posJ < this.col - 1) {
    		validAdj[1][0] = posI - 1;
    		validAdj[1][1] = posJ + 1;
    		validAdj[1][2] = 1;
    	}
    	if (posJ < this.col - 1) {
    		validAdj[2][0] = posI;
    		validAdj[2][1] = posJ + 1;
    		validAdj[2][2] = 1;
    	}
    	if (posJ < this.col - 1 && posI < this.row - 1) {
    		validAdj[3][0] = posI + 1;
    		validAdj[3][1] = posJ + 1;
    		validAdj[3][2] = 1;
    	}
    	if (posI < this.row - 1) {
    		validAdj[4][0] = posI + 1;
    		validAdj[4][1] = posJ;
    		validAdj[4][2] = 1;
    	}
    	if (posI < this.row - 1 && posJ > 0) {
    		validAdj[5][0] = posI + 1;
    		validAdj[5][1] = posJ - 1;
    		validAdj[5][2] = 1;
    	}
    	if (posJ > 0) {
    		validAdj[6][0] = posI;
    		validAdj[6][1] = posJ - 1;
    		validAdj[6][2] = 1;
    	}
    	if (posI > 0 && posJ > 0) {
    		validAdj[7][0] = posI - 1;
    		validAdj[7][1] = posJ - 1;
    		validAdj[7][2] = 1;
    	}
    	
    	return validAdj;
    }
    
    private void preGraph() {
    	this.posAdj = new int[this.row][this.col][8][3];
    	for (int i = 0; i < this.row; i++) {
    		for (int j = 0; j < this.col; j++) {
    			this.posAdj[i][j] = this.adjacent(i, j);
    		}
    	}
    }
        
    private void dfsWords(int posI, int posJ, String curStr) {
    	this.isVisited[posI][posJ] = true;
    	char c = this.board.getLetter(posI, posJ);
    	
    	curStr = curStr + ((c == 'Q')? "QU" : c);
		if (this.tst.isPrefix(curStr)) {

			if (curStr.length() > 2 && this.tst.contains(curStr)) {
				this.valid.add(curStr);
			}
		}
		else {
			this.isVisited[posI][posJ] = false;
			return;
		}
		
		
    	for (int k = 0; k < 8; k++) {
    		
			if (this.posAdj[posI][posJ][k][2] != 1) {
				continue;
			}
			
			int curI = this.posAdj[posI][posJ][k][0];
			int curJ = this.posAdj[posI][posJ][k][1];
			
			if (isVisited[curI][curJ]) {
				continue;
			}
			
			dfsWords(curI, curJ, curStr);
		}
		this.isVisited[posI][posJ] = false;
			
    }
    
    public Iterable<String> getAllValidWords(BoggleBoard board) {
    	this.isVisited = new boolean[board.rows()][board.cols()];
    	
    	this.row = board.rows();
    	this.col = board.cols();
    	this.board = board;
    	
    	this.preGraph();
    	this.valid = new SET<String>();
    	String curStr = "";
    	
    	for (int i = 0; i < this.row; i++) {
    		for (int j = 0; j < this.col; j++) {
    			this.dfsWords(i, j, curStr);
    		}
    	}
    	return this.valid;
    }
    	
    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
    	if (!this.tst.contains(word))
    		return 0;
    	
    	return this.tst.get(word);
    }
    
    public static void main(String[] args) {
    	String dictionaryFile = "boggle/dictionary-algs4.txt";
    	String boggleFile = "boggle/board-q.txt";
    	
        In in = new In(dictionaryFile);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(boggleFile);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}
