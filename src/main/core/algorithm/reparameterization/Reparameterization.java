package main.core.algorithm.reparameterization;

import main.Definition;
import main.core.algorithm.structure.Edge;
import main.core.algorithm.structure.Face;
import main.core.algorithm.structure.Vertex;
import main.core.mathematics.structure.Coordinate2D;

public abstract class Reparameterization extends Definition{
	public abstract Coordinate2D reparameterize(Face face,Edge edge,double x,double y);
	public abstract Coordinate2D reparameterize(Edge edge,Face face,double x,double y);
	public abstract Coordinate2D reparameterize(Edge edge,Vertex vertex,double x,double y);
	public abstract Coordinate2D reparameterize(Vertex vertex,Edge edge,double x,double y);
	
	public Coordinate2D reparameterize(Face face,Edge edge,Coordinate2D coordinate){
		return this.reparameterize(face,edge,coordinate.getX(),coordinate.getY());
	}
	
	public Coordinate2D reparameterize(Edge edge,Face face,Coordinate2D coordinate){
		return this.reparameterize(edge,face,coordinate.getX(),coordinate.getY());
	}
	
	public Coordinate2D reparameterize(Edge edge,Vertex vertex,Coordinate2D coordinate){
		return this.reparameterize(edge,vertex,coordinate.getX(),coordinate.getY());
	}
	
	public Coordinate2D reparameterize(Vertex vertex,Edge edge,Coordinate2D coordinate){
		return this.reparameterize(vertex,edge,coordinate.getX(),coordinate.getY());
	}
}
