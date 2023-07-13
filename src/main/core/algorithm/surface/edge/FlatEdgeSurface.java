package main.core.algorithm.surface.edge;

import main.core.algorithm.structure.Edge;
import main.core.algorithm.structure.Element;
import main.core.mathematics.element.Point3D;

public class FlatEdgeSurface extends EdgeSurface{
	public FlatEdgeSurface(Edge edge){
		super(edge);
	}
	
	public Point3D calculateSurfacePoint(double x,double y,Element base){
		return edge.getDomain().calculatePoint(x,y);
	}
}
