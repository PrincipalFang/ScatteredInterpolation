package main.core.algorithm.structure;

import java.util.HashMap;
import main.core.data.output.OutputObject;
import main.core.data.output.OutputObjectHandler;
import main.core.mathematics.element.Point3D;
import main.core.mathematics.element.Vector3D;

public class Face extends Element implements OutputObjectHandler{
	protected HashMap<Vertex,Vertex> nextVertex;
	protected HashMap<Vertex,Vertex> lastVertex;
	protected Vertex[] vertices;
	protected Edge[] edges;
	protected int size;
	protected Point3D center;
	protected Vector3D normal;
	
	public Face(Vertex[] vertices,Edge[] edges){
		this.vertices=vertices;
		this.edges=edges;
		nextVertex=new HashMap<Vertex,Vertex>();
		lastVertex=new HashMap<Vertex,Vertex>();
		size=vertices.length;
		for(int i=0;i<size-1;i++){
			nextVertex.put(vertices[i],vertices[i+1]);
			lastVertex.put(vertices[i+1],vertices[i]);
		}
		nextVertex.put(vertices[size-1],vertices[0]);
		lastVertex.put(vertices[0],vertices[size-1]);
		center=new Point3D();
		for(Vertex vertex:vertices){
			center.mPlus(vertex.getPoint());
		}
		center.mScale(1.0/size);
		normal=new Vector3D();
		for(int i=0;i<size-1;i++){
			normal.mPlus(center.toward(vertices[i].getPoint()).crossProduct(center.toward(vertices[i+1].getPoint())).nNormalize());
		}
		normal.mPlus(center.toward(vertices[size-1].getPoint()).crossProduct(center.toward(vertices[0].getPoint())).nNormalize());
		normal.mNormalize();
		for(Edge edge:edges){
			edge.linkFace(this);
		}
	}
	
	public Vertex[] getVertices(){
		return vertices;
	}
	
	public Vertex getVertex(int index){
		return vertices[index];
	}
	
	public Vertex getNextVertex(Vertex vertex){
		return nextVertex.get(vertex);
	}
	
	public Vertex getLastVertex(Vertex vertex){
		return lastVertex.get(vertex);
	}
	
	public Edge[] getEdges(){
		return edges;
	}
	
	public Edge getEdge(int index){
		return edges[index];
	}
	
	public int getSize(){
		return size;
	}
	
	public Point3D getCenter(){
		return center;
	}
	
	public Vector3D getNormal(){
		return normal;
	}
	
	public void createSurface(){
		surface=this.createFaceSurface(this);
	}
	
	public void output(OutputObject object){
		object.addSurface(surface);
	}
}
