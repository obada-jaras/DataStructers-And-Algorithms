package Project;

import java.util.ArrayList;

public class Vertex {
	private City city;
	private ArrayList<Adjacent> adjacents;
	public boolean known;
	public Vertex pathPrev;
	
	
	public Vertex(City city) {
		this.city = city;
		this.adjacents = new ArrayList<Adjacent>();
		known = false;
		pathPrev = null;
	}
	
	

	public City getCity() {
		return city;
	}
	
	public ArrayList<Adjacent> getAdjacents() {
		return adjacents;
	}
	
	public double getAdjacentDistance(Vertex vertex) {
		for (Adjacent adjacent : adjacents) 
			if (adjacent.getVertex() == vertex) 
				return adjacent.getDistance();
			
		return -1;
	}

	public void addAdjacent(Vertex vertex) {
		adjacents.add(new Adjacent(vertex, getDistance(vertex)));
	}
	
	
	private double getDistance(Vertex vertex) {
		double x1 = this.city.xCoordinate;
		double y1 = this.city.yCoordinate;
		double x2 = vertex.getCity().xCoordinate;
		double y2 = vertex.getCity().yCoordinate;
		
		return Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
	}
}