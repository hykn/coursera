import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FordFulkerson;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class BaseballElimination {
	
	private Map<String, Integer> team;
	private Map<Integer, String> teamName;
	private int[] w;
	private int[] l;
	private int[] r;
	private int[][] g;
	private int teamNum;
	private int s;
	private int t;
	private int maxWin;
	private String leaderTeam;
	
	private void isTeamValid(String team) {
		if (!this.team.containsKey(team)) {
			throw new java.lang.IllegalArgumentException();
		}
	}
	
	private boolean isTrivialElimination(String team) {
		int teamID = this.team.get(team);
		for (int i = 0; i < this.teamNum; i++) {
			if (i != teamID && this.w[teamID] + this.r[teamID] < this.w[i]) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean isNonTrivialElimination(String team) {
		int teamID = this.team.get(team);
		FlowNetwork flow = this.constructFlow(teamID);
		FordFulkerson ff = new FordFulkerson(flow, s, t);

		for (FlowEdge edge : flow.adj(0)) {
			if (edge.flow() < edge.capacity()){
				return true;
			}
		}
		
		return false;
	}
	
	private FlowNetwork constructFlow(int teamID) {
		
		int V = 0;
		int gameVertics = (this.teamNum - 1) * (this.teamNum - 2) / 2;
		int teamVertics = this.teamNum - 1;
		V = 1 + gameVertics + teamVertics + 1;
		this.s = 0;
		this.t = V - 1;
		
		FlowNetwork flow = new FlowNetwork(V);
		
		// given teamID, for each game vertices and team vertices, determined their id
		int gameVerticesID = 1;
		int team1ID = 0;
		int team2ID = 0;
		
		for (int i = 0; i < this.teamNum; i++) {
			if (i == teamID) {
				continue;
			}
			
			for (int j = i + 1; j < this.teamNum; j++) {
				if (j == teamID || this.g[i][j] == 0) {
					continue;
				}

				team1ID = (i > teamID) ? gameVertics + i : gameVertics + 1 + i;
				team2ID = (j > teamID) ? gameVertics + j : gameVertics + 1 + j;
				
				FlowEdge sToGame = new FlowEdge(s, gameVerticesID, this.g[i][j]);
				FlowEdge gameToTeam1 = new FlowEdge(gameVerticesID, team1ID, Double.POSITIVE_INFINITY);
				FlowEdge gameToTeam2 = new FlowEdge(gameVerticesID, team2ID, Double.POSITIVE_INFINITY);
				flow.addEdge(sToGame);
				flow.addEdge(gameToTeam1);
				flow.addEdge(gameToTeam2);
				
				gameVerticesID += 1;
			}
						
			team1ID = (i > teamID) ? gameVertics + i : gameVertics + 1 + i;
			
			FlowEdge teamToT = new FlowEdge(team1ID, t, Math.max(this.w[teamID] + this.r[teamID] - this.w[i], 0));
			flow.addEdge(teamToT);
			
		}

		return flow;
	}
	
	
	// create a baseball division from given filename in format specified below
	public BaseballElimination(String filename) {
		String line;
		this.team = new HashMap<String, Integer>();
		this.teamName = new HashMap<Integer, String>();
		
		In teamIn = new In(filename);
		
		line = teamIn.readLine();
		this.teamNum = Integer.parseInt(line);
		
		
		this.w = new int[this.teamNum];
		this.l = new int[this.teamNum];
		this.r = new int[this.teamNum];
		this.g = new int[this.teamNum][this.teamNum];
		
		this.maxWin = 0;
		this.leaderTeam = "";
		
		int id = 0;
		while (!teamIn.isEmpty()) {
			line = teamIn.readLine();
			line = line.trim();
			String[] strArray = line.split("\\s+");
			this.team.put(strArray[0], id);
			this.teamName.put(id, strArray[0]);
			this.w[id] = Integer.parseInt(strArray[1]);
			this.l[id] = Integer.parseInt(strArray[2]);
			this.r[id] = Integer.parseInt(strArray[3]);
			
			for (int i = 0; i < this.teamNum; i++) {
				this.g[id][i] = Integer.parseInt(strArray[4 + i]);
			}
			
			if (this.w[id] > maxWin) {
				this.maxWin = this.w[id];
				this.leaderTeam = strArray[0];
			}
			
			id += 1;
		}
		teamIn.close();
		
	}
	
	// number of teams
	public int numberOfTeams() {
		return this.teamNum;
	}

	// all teams
	public Iterable<String> teams() {
		return this.team.keySet();
	}
	
	// number of wins for given team
	public int wins(String team) {
		this.isTeamValid(team);
		return this.w[this.team.get(team)];
	}
	
	// number of losses for given team
	public int losses(String team) {
		this.isTeamValid(team);
		return this.l[this.team.get(team)];
	}
	
	// number of remaining games for given team
	public int remaining(String team) {
		this.isTeamValid(team);
		return this.r[this.team.get(team)];
	}
	
	// number of remaining games between team1 and team2
	public int against(String team1, String team2) {
		this.isTeamValid(team1);
		this.isTeamValid(team2);
		return this.g[this.team.get(team1)][this.team.get(team2)];
	}
	
	// is given team eliminated?
	public boolean isEliminated(String team) {
		this.isTeamValid(team);
		if (isTrivialElimination(team)) {
			return true;
		}
		
		return isNonTrivialElimination(team);
	}
	
	// subset R of teams that eliminates given team; null if not eliminated
	public Iterable<String> certificateOfElimination(String team) {
		this.isTeamValid(team);
		int teamID = this.team.get(team);
		ArrayList<String> subset = new ArrayList<String>();
		
		if (this.isTrivialElimination(team)) {
			subset.add(this.leaderTeam);
			return subset;
		}
		
		FlowNetwork flow = this.constructFlow(teamID);
		FordFulkerson ff = new FordFulkerson(flow, s, t);
		
		// if the flow of any edge is not full, then the
		int gameVertics = (this.teamNum - 1) * (this.teamNum - 2) / 2;
		int team1ID = 0;
		
		for (int i = 0; i < this.teamNum; i++) {
			if (i == teamID) {
				continue;
			}
			
			team1ID = (i > teamID) ? gameVertics + i : gameVertics + 1 + i;
			
			if (ff.inCut(team1ID)) {
				subset.add(this.teamName.get(i));
			}
		}
		
		return (subset.isEmpty()) ? null : subset;
	}

	public static void main(String[] args) {
		String file = "baseball/teams4.txt";
		System.out.println(file);
		
	    BaseballElimination division = new BaseballElimination(file);
//	    division.constructFlow(4);
	    for (String team : division.teams()) {
	        if (division.isEliminated(team)) {
	            StdOut.print(team + " is eliminated by the subset R = { ");
	            for (String t : division.certificateOfElimination(team)) {
	                StdOut.print(t + " ");
	            }
	            StdOut.println("}");
	        }
	        else {
	            StdOut.println(team + " is not eliminated");
	        }
	    }
	}
}

