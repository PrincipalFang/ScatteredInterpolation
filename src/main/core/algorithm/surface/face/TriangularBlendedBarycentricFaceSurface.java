package main.core.algorithm.surface.face;

import main.core.algorithm.structure.Edge;
import main.core.algorithm.structure.Element;
import main.core.algorithm.structure.Face;
import main.core.algorithm.structure.Vertex;
import main.core.mathematics.element.Point3D;
import main.core.mathematics.factor.Affine3D;

public class TriangularBlendedBarycentricFaceSurface extends BarycentricFaceSurface{
	protected Vertex v0,v1,v2;
	protected Edge e0,e1,e2;
	
	public TriangularBlendedBarycentricFaceSurface(Face face){
		super(face);
		v0=face.getVertex(0);
		v1=face.getVertex(1);
		v2=face.getVertex(2);
		e0=face.getEdge(0);
		e1=face.getEdge(1);
		e2=face.getEdge(2);
	}
	
	public Point3D calculateSurfacePoint(double x,double y,Element base){
		if(x==1){
			return v0.getPoint();
		}else if(y==1){
			return v1.getPoint();
		}else if(x+y==0){
			return v2.getPoint();
		}else{
			double up=Math.pow(x,continuityDegree+1);
			double vp=Math.pow(y,continuityDegree+1);
			double wp=Math.pow(1-x-y,continuityDegree+1);
			double fu=vp*wp*(1/(up+vp)+1/(up+wp))/(up+vp+wp);
			double fv=wp*up*(1/(vp+wp)+1/(vp+up))/(up+vp+wp);
			Point3D p0=e0.getSurface().calculateSurfacePoint(reparameterization.reparameterize(face,e0,x,y),null);
			Point3D p1=e1.getSurface().calculateSurfacePoint(reparameterization.reparameterize(face,e1,x,y),null);
			Point3D p2=e2.getSurface().calculateSurfacePoint(reparameterization.reparameterize(face,e2,x,y),null);
			return new Affine3D(fu,fv).combine(p1,p2,p0);
		}
	}
}
