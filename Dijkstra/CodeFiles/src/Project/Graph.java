package Project;

import java.util.ArrayList;

public class Graph {
	private ArrayList<Vertex> vertices;	
	
	public Graph() {
		this.vertices = new ArrayList<Vertex>();
	}
	
	
	
	public ArrayList<Vertex> getVertices() {
		return vertices;
	}

	public boolean addVertex(Vertex e) {
		return vertices.add(e);
	}
	
	public int getNumberOfVertices() {
		return vertices.size();
	}
	
	public boolean isVertexInGraph(Vertex v) {
		for (Vertex vertex : vertices) {
			if (v == vertex)
				return true;
		}
		
		return false;
	}
	
	
	public Vertex getVertex(String cityName) {
		for (Vertex vertex : vertices) 
			if (vertex.getCity().name.equals(cityName)) 
				return vertex;
			
		return null;
	}
	
	
	public void addAdjacentToCity(String city1, String city2) {
		int numberOfVertices = getNumberOfVertices();
		
		for (int i = 0; i < numberOfVertices; i++) {
			if (vertices.get(i).getCity().name.equals(city1)) {
				for (int j = 0; j < numberOfVertices; j++) {
					if (vertices.get(j).getCity().name.equals(city2)) {
						vertices.get(i).addAdjacent(vertices.get(j));
					}
				}
			}
		}
	}
}
