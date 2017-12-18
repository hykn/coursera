import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.ST;
import java.util.ArrayList;

public class WordNet { 
	private final ArrayList<String> nounList;
	private final ST<String, Bag<Integer>> nounMap;
	private final SAP sapTool;
	private final Digraph nounGraph;
	
	
	private void checkValid(int id) {
		if (id < 0 || id > this.nounGraph.V())
			throw new java.lang.IllegalArgumentException();
	}
	
	private void storeNoun(String line, int id) {

		int firstComma = line.indexOf(',');
		int secondComma = line.indexOf(',', firstComma+1);
		String secondField = line.substring(firstComma+1, secondComma);

		this.nounList.add(secondField);
		String[] nounsArray = secondField.split(" ");
		for (int i = 0; i < nounsArray.length; i++) {

			if (!this.nounMap.contains(nounsArray[i])) {
				Bag<Integer> bag = new Bag<>();
				bag.add(id);
				this.nounMap.put(nounsArray[i], bag);
			}
			else {
				this.nounMap.get(nounsArray[i]).add(id);
			}
		}
	}
	
	// constructor takes the name of the two input files
	public WordNet(String synsets, String hypernyms) {
		
		
		String line;
		nounList = new ArrayList<String>();
		nounMap = new ST<String, Bag<Integer>>();
	   
		// java.lang.IllegalArgumentException
		// read synsets file
		In synsetsIn = new In(synsets);
		int id = 0;

		while (!synsetsIn.isEmpty()) {
			line = synsetsIn.readLine();
			this.storeNoun(line, id);
			id++;
		}
		synsetsIn.close();
		
		this.nounGraph = new Digraph(id);
		
		// read hypernyms file
		In hypernymsIn = new In(hypernyms);
		
		while (!hypernymsIn.isEmpty()) {
			line = hypernymsIn.readLine();
			String[] numbers = line.split(",");
			
			int first = Integer.parseInt(numbers[0]);
			
			checkValid(first);
			
			for (int i = 1; i < numbers.length; i++) {
				int vertex = Integer.parseInt(numbers[i]);
				checkValid(vertex);
				this.nounGraph.addEdge(first, vertex);
			}
		}
		hypernymsIn.close();
		
		// directed Cycle detected
		DirectedCycle dc = new DirectedCycle(this.nounGraph);
		if (new DirectedCycle(this.nounGraph).hasCycle()) {
			throw new java.lang.IllegalArgumentException();
		}
		
		int rootNum = 0;
		for (int i = 0; i < this.nounGraph.V(); i++) {
			if (!this.nounGraph.adj(i).iterator().hasNext()) {
				rootNum++;
			}
			if (rootNum > 1)
				throw new java.lang.IllegalArgumentException();
		}
		
		// create sapTool
		sapTool = new SAP(this.nounGraph);
		
		System.out.println("Finished");
		
	}
	
	// returns all WordNet nouns
	public Iterable<String> nouns() {
		return this.nounMap.keys();
	}

	// is the word a WordNet noun?
	public boolean isNoun(String word) {

		if (word == null) {
			throw new java.lang.IllegalArgumentException();
		}
		
		return this.nounMap.contains(word);
	}

	// distance between nounA and nounB (defined below)
	public int distance(String nounA, String nounB) {
		
		if (!this.isNoun(nounA) || !this.isNoun(nounB)) {
			throw new java.lang.IllegalArgumentException();
		}
		
		Iterable<Integer> nounAID = this.nounMap.get(nounA);
		Iterable<Integer> nounBID = this.nounMap.get(nounB);
		
		return sapTool.length(nounAID, nounBID);
	}

	// a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
	// in a shortest ancestral path (defined below)
	public String sap(String nounA, String nounB) {

//		 when nounA and nounB either is not in nounlist;
		if (!this.isNoun(nounA) || !this.isNoun(nounB)) {
			throw new java.lang.IllegalArgumentException();
		}
		
		Iterable<Integer> nounAID = this.nounMap.get(nounA);
		Iterable<Integer> nounBID = this.nounMap.get(nounB);
		
		int ancestor = this.sapTool.ancestor(nounAID, nounBID);
		
		return (ancestor != -1) ? this.nounList.get(ancestor) : "";
	}

	// do unit testing of this class
	public static void main(String[] args) {
//		String folder = "wordnet/";
//		WordNet wn = new WordNet(folder+"synsets.txt", folder+"hypernyms.txt");
//		System.out.println(wn.distance("taffy_apple", "dog_fennel"));
		
		String folder = "wordnet/";
		WordNet wn = new WordNet(folder+"synsets1000-subgraph.txt", folder+"hypernyms1000-subgraph.txt");
		System.out.println(wn.sap("upper_jawbone", "tract"));
		System.out.println(wn.distance("upper_jawbone", "tract"));
		System.out.println(wn.isNoun("animal_tissue"));
		
//		String folder = "wordnet/";
//		WordNet wn = new WordNet(folder+"synsets6.txt", folder+"hypernyms6InvalidCycle+Path.txt");
	}
}
