package main.core.mathematics.structure;

import main.core.algorithm.structure.Face;
import main.core.mathematics.element.Point3D;
import main.core.mathematics.factor.Affine3D;

public class BarycentricLocalDomain implements LocalDomain{
	protected Point3D up,vp,wp;
	
	public BarycentricLocalDomain(Face face){
		this.up=face.getVertex(0).getPoint();
		this.vp=face.getVertex(1).getPoint();
		this.wp=face.getVertex(2).getPoint();
	}
	
	public Point3D calculatePoint(double x,double y){
		return new Affine3D(x,y).combine(up,vp,wp);
	}
	
	public Point3D calculatePoint(Coordinate2D coordinate){
		return this.calculatePoint(coordinate.getX(),coordinate.getY());
	}
	
	public Coordinate2D calculateCoordinate(Point3D point){
		Affine3D affine=point.affine(up,vp,wp);
		return new Coordinate2D(affine.getU(),affine.getV());
	}
}
