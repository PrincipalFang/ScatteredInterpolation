package main.core.algorithm.surface.edge;

import main.core.algorithm.structure.Edge;
import main.core.algorithm.structure.Element;
import main.core.algorithm.structure.Vertex;
import main.core.mathematics.element.Point3D;
import main.core.mathematics.factor.Affine2D;

public class BinaryBlendedEdgeSurface extends EdgeSurface{
	protected Vertex v0,v1;
	
	public BinaryBlendedEdgeSurface(Edge edge){
		super(edge);
		v0=edge.getHeadVertex();
		v1=edge.getTailVertex();
	}
	
	public Point3D calculateSurfacePoint(double x,double y,Element base){
		double up=Math.pow(1-y,continuityDegree+1);
		double vp=Math.pow(y,continuityDegree+1);
		Point3D p0=v0.getSurface().calculateSurfacePoint(reparameterization.reparameterize(edge,v0,x,y),null);
		Point3D p1=v1.getSurface().calculateSurfacePoint(reparameterization.reparameterize(edge,v1,x,y),null);
		return new Affine2D(up/(up+vp)).combine(p0,p1);
	}
}
