package main.core.algorithm.surface.vertex;

import main.core.algorithm.structure.Element;
import main.core.algorithm.structure.Vertex;
import main.core.mathematics.element.Point3D;
import main.core.mathematics.least.PolynomialLeast;
import main.core.mathematics.structure.Coordinate2D;

public class MultiplePolygonalLeastVertexSurface extends VertexSurface{
	protected PolynomialLeast least;
	
	public MultiplePolygonalLeastVertexSurface(Vertex vertex){
		super(vertex);
		least=new PolynomialLeast();
		least.add(0,0,0);
		least.add(0,0,0,0);
		for(int i=0;i<vertex.getNeighbors().size();i++){
			Point3D point=vertex.getNeighbors().get(i).getPoint();
			Coordinate2D coordinate=vertex.getDomain().calculateCoordinate(point);
			least.add(coordinate.getX(),coordinate.getY(),vertex.getPlane().signedDistance(point));
		}
		least.process();
	}
	
	public Point3D calculateSurfacePoint(double x,double y,Element base){
		return vertex.getDomain().calculatePoint(x,y).nPlus(vertex.getNormal().nScale(least.value(x,y)));
	}
}
