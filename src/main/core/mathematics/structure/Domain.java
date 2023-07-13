package main.core.mathematics.structure;

import main.core.mathematics.element.Point3D;
import main.core.mathematics.element.Vector3D;

public abstract class Domain extends Plane{
	public abstract Point3D calculatePoint(double x,double y);
	public abstract double xCoordinate(Point3D point);
	public abstract double yCoordinate(Point3D point);
	public abstract double xLocalCoordinate(Point3D point);
	public abstract double yLocalCoordinate(Point3D point);
	
	public Domain(Point3D point,Vector3D normal){
		super(point,normal);
	}
	
	public Domain(Point3D p0,Point3D p1,Point3D p2){
		super(p0,p1,p2);
	}
	
	public Point3D calculatePoint(Coordinate2D coordinate){
		return this.calculatePoint(coordinate.getX(),coordinate.getY());
	}
	
	public Coordinate2D calculateCoordinate(Point3D point){
		return new Coordinate2D(this.xCoordinate(point),this.yCoordinate(point));
	}
}
