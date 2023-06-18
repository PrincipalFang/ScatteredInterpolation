package main.core.algorithm.surface.vertex;

import main.core.algorithm.structure.Element;
import main.core.algorithm.structure.Vertex;
import main.core.mathematics.element.Point3D;

public class EmptyVertexSurface extends VertexSurface{
	public EmptyVertexSurface(Vertex vertex){
		super(vertex);
	}
	
	public Point3D calculateSurfacePoint(double x,double y,Element base){
		return null;
	}
}
