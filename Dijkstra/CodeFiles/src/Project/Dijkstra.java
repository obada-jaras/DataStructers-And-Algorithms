package Project;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Dijkstra {
	private PriorityQueue<PriorityQueueNode> PQ = new PriorityQueue<>();
	private Vertex start;
	
	public Dijkstra(Vertex start) throws Exception {
		this.start = start;
		initializationQueue();
	}
	
	
	private void initializationQueue() {
		for (int i = 0; i < start.getAdjacents().size(); i++) {
			PQ.add(new PriorityQueueNode(start, start.getAdjacents().get(i).getVertex(), start.getAdjacents().get(i).getDistance()));
		}
		
		start.known = true;
	}
	
	
	public void dijkstra(Vertex des) {
		while (true) {
			PriorityQueueNode PQnode = PQ.poll();
			Vertex v = PQnode.to;
			
			if (v.known) continue;
			
			double distance = PQnode.cost;
 			v.known = true;
			v.pathPrev = PQnode.from;
			
			if (v == des) break;
			
			for (int j = 0; j < v.getAdjacents().size(); j++) {
				Adjacent adjacent = v.getAdjacents().get(j);
				PQ.add(new PriorityQueueNode(v, adjacent.getVertex(), distance + adjacent.getDistance()));
			}
		}
	}
	
	
	public ArrayList<Vertex> getShortestPathTo(Vertex v2) throws Exception {
		ArrayList<Vertex> path = new ArrayList<Vertex>();
		dijkstra(v2);
		
		Vertex pathPrev = v2;
		while (pathPrev != null) {
			path.add(0, pathPrev);
			pathPrev = pathPrev.pathPrev;
		}
		
		return path;
	}
}