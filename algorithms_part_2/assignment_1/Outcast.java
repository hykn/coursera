import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
	
	private final WordNet wordnet;
	
	// constructor takes a WordNet object
	public Outcast(WordNet wordnet) {
		this.wordnet = wordnet;
	}
   
	// given an array of WordNet nouns, return an outcast
	public String outcast(String[] nouns) {
	   
		int[][] distance = new int[nouns.length][nouns.length];
		for (int i = 0; i < nouns.length - 1; i++) {
			for (int j = i + 1; j < nouns.length; j++) {
				int dist = this.wordnet.distance(nouns[i], nouns[j]);
				distance[i][j] = dist;
				distance[j][i] = dist;
			}
		}
		
		int smallestDist = 0;
		int smallestID = -1;
		
		int[] sumDist = new int[nouns.length];
		for (int i = 0; i < nouns.length; i++) {
			for (int dist : distance[i]) {
				sumDist[i] += dist;
			}
		}
		for (int i = 0; i < nouns.length; i++) {
			if (sumDist[i] > smallestDist) {
				smallestDist = sumDist[i];
				smallestID = i;
			}
		}

		return (smallestID != -1) ? nouns[smallestID] : "";
	}
   
   // see test client below
   	public static void main(String[] args) {
	   	String folder = "wordnet/";
//	   	WordNet wordnet = new WordNet(folder+"synsets15.txt", folder+"hypernyms15Path.txt");
	   	WordNet wordnet = new WordNet(folder+"synsets.txt", folder+"hypernyms.txt");
	    Outcast outcast = new Outcast(wordnet);
	    
	    String outcastFile = "outcast11.txt";
	    In in = new In(folder + outcastFile);
	    String[] nouns = in.readAllStrings();
	    StdOut.println(outcastFile + ": " + outcast.outcast(nouns));
	}
}