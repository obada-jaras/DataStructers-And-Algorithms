package Project;

public class Adjacent {
	private Vertex vertex;
	private double distance;
	
	
	public Adjacent(Vertex vertex, double distance) {
		this.vertex = vertex;
		this.distance = distance;
	}


	public Vertex getVertex() {
		return vertex;
	}

	public double getDistance() {
		return distance;
	}
}
