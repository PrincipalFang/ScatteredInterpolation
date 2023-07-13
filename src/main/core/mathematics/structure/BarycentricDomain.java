package main.core.mathematics.structure;

import main.core.mathematics.element.Point3D;
import main.core.mathematics.factor.Affine3D;

public class BarycentricDomain extends Domain{
	protected Point3D p0,p1,p2;
	protected double area;
	
	public BarycentricDomain(Point3D p0,Point3D p1,Point3D p2){
		super(p0,p1,p2);
		this.p0=p0;
		this.p1=p1;
		this.p2=p2;
		area=p0.area(p1,p2);
	}
	
	public Point3D calculatePoint(double x,double y){
		return new Affine3D(x,y).combine(p0,p1,p2);
	}
	
	public double xCoordinate(Point3D point){
		double x=this.nProjectOnPlane(point).area(p1,p2)/area;
		if(this.normal.innerProduct(point.toward(p1).crossProduct(point.toward(p2)))<0){
			x=-x;
		}
		return x;
	}
	
	public double yCoordinate(Point3D point){
		double y=this.nProjectOnPlane(point).area(p2,p0)/area;
		if(this.normal.innerProduct(point.toward(p2).crossProduct(point.toward(p0)))<0){
			y=-y;
		}
		return y;
	}
	
	public double xLocalCoordinate(Point3D point){
		double x=point.area(p1,p2)/area;
		if(this.normal.innerProduct(point.toward(p1).crossProduct(point.toward(p2)))<0){
			x=-x;
		}
		return x;
	}
	
	public double yLocalCoordinate(Point3D point){
		double y=point.area(p2,p0)/area;
		if(this.normal.innerProduct(point.toward(p2).crossProduct(point.toward(p0)))<0){
			y=-y;
		}
		return y;
	}
}
