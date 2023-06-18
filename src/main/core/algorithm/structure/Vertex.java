package main.core.algorithm.structure;

import java.util.ArrayList;
import java.util.HashMap;
import main.core.data.output.OutputObject;
import main.core.data.output.OutputObjectHandler;
import main.core.mathematics.element.Point3D;
import main.core.mathematics.element.Vector3D;
import main.core.mathematics.structure.Plane;

public class Vertex extends Element implements OutputObjectHandler{
	protected ArrayList<Vertex> neighbors;
	protected HashMap<Vertex,Vertex> nextVertex;
	protected HashMap<Vertex,Vertex> lastVertex;
	protected HashMap<Vertex,Edge> edges;
	protected Plane plane;
	protected Point3D point;
	protected Vector3D normal;
	
	public Vertex(Point3D point,Vector3D normal){
		nextVertex=new HashMap<Vertex,Vertex>();
		lastVertex=new HashMap<Vertex,Vertex>();
		edges=new HashMap<Vertex,Edge>();
		plane=new Plane(point,normal);
		this.point=point;
		this.normal=normal;
	}
	
	public void linkOrder(Vertex v1,Vertex v2){
		nextVertex.put(v1,v2);
		lastVertex.put(v2,v1);
	}
	
	public void buildComponent(){
		neighbors=new ArrayList<Vertex>();
		Vertex start=nextVertex.keySet().iterator().next();
		Vertex current=nextVertex.get(start);
		neighbors.add(start);
		while(current!=start){
			neighbors.add(current);
			current=nextVertex.get(current);
		}
	}
	
	public ArrayList<Vertex> getNeighbors(){
		return neighbors;
	}
	
	public Vertex getNextVertex(Vertex vertex){
		return nextVertex.get(vertex);
	}
	
	public Vertex getLastVertex(Vertex vertex){
		return lastVertex.get(vertex);
	}
	
	public boolean existEdge(Vertex v){
		return edges.containsKey(v);
	}
	
	public void linkEdge(Vertex v,Edge e){
		edges.put(v,e);
	}
	
	public Edge getEdge(Vertex v){
		return edges.get(v);
	}
	
	public Plane getPlane(){
		return plane;
	}
	
	public Point3D getPoint(){
		return point;
	}
	
	public Vector3D getNormal(){
		return normal;
	}
	
	public void createSurface(){
		surface=this.createVertexSurface(this);
	}
	
	public void output(OutputObject object){
		object.addSurface(surface);
	}
}
