package main.core.algorithm.reparameterization;

import main.core.algorithm.structure.Edge;
import main.core.algorithm.structure.Face;
import main.core.algorithm.structure.Vertex;
import main.core.mathematics.element.Point3D;
import main.core.mathematics.element.Vector3D;
import main.core.mathematics.structure.Coordinate2D;

public class BasicBarycentricReparameterization extends Reparameterization{
	public Coordinate2D reparameterize(Face face,Edge edge,double x,double y){
		Point3D point=face.getDomain().calculatePoint(x,y);
		return edge.getDomain().calculateCoordinate(point);
	}
	
	public Coordinate2D reparameterize(Edge edge,Face face,double x,double y){
		// TODO
		return null;
	}
	
	public Coordinate2D reparameterize(Edge edge,Vertex vertex,double x,double y){
		if(edge.getHeadVertex()==vertex){
			Vector3D yUnit=vertex.getPoint().toward(vertex.getPlane().nProjectOnPlane(edge.getTailVertex().getPoint()));
			Vector3D xUnit=yUnit.nRotate(vertex.getNormal(),-Math.PI/2);
			Point3D point=vertex.getPoint().nPlus(xUnit.nScale(x)).nPlus(yUnit.nScale(y));
			return vertex.getDomain().calculateCoordinate(point);
		}else{
			Vector3D yUnit=vertex.getPlane().nProjectOnPlane(edge.getHeadVertex().getPoint()).toward(vertex.getPoint());
			Vector3D xUnit=yUnit.nRotate(vertex.getNormal(),-Math.PI/2);
			Point3D point=edge.getHeadVertex().getPoint().nPlus(xUnit.nScale(x)).nPlus(yUnit.nScale(y));
			return vertex.getDomain().calculateCoordinate(point);
		}
	}
	
	public Coordinate2D reparameterize(Vertex vertex,Edge edge,double x,double y){
		// TODO
		return null;
	}
}
