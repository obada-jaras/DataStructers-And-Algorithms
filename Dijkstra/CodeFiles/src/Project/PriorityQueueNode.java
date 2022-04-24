package Project;

public class PriorityQueueNode implements Comparable<PriorityQueueNode>{
	Vertex from;
	Vertex to;
	double cost;
	

	public PriorityQueueNode(Vertex from, Vertex to, double cost) {
		this.from = from;
		this.to = to;
		this.cost = cost;
	}


	
	
	@Override
	public int compareTo(PriorityQueueNode o) {
		if (this.cost - o.cost > 0) return 1;
		else return -1;
	}
	
	
}
