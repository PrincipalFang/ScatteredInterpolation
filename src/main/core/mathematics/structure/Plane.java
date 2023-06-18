package main.core.mathematics.structure;

import main.core.mathematics.element.Point3D;
import main.core.mathematics.element.Vector3D;

public class Plane{
	protected Point3D point;
	protected Vector3D normal;
	
	public Plane(Point3D point,Vector3D normal){
		this.point=point;
		this.normal=normal.nNormalize();
	}
	
	public Plane(Point3D p1,Point3D p2,Point3D p3){
		this.point=p1;
		normal=p1.toward(p2).crossProduct(p1.toward(p3));
		normal.mNormalize();
	}
	
	public double signedDistance(Point3D target){
		return point.toward(target).innerProduct(normal);
	}
	
	public Point3D nProjectOnPlane(Point3D target){
		return target.nPlus(normal.nScale(-1*this.signedDistance(target)));
	}
	
	public void pProjectOnPlane(Point3D target){
		Point3D p=this.nProjectOnPlane(target);
		target.assign(p);
	}
	
	public Point3D nShiftToPlane(Point3D target,Vector3D direction){
		double r=-this.signedDistance(target)/direction.innerProduct(normal);
		return target.nPlus(direction.nScale(r));
	}
	
	public void pShiftToPlane(Point3D target,Vector3D direction){
		double r=-this.signedDistance(target)/direction.innerProduct(normal);
		target.mPlus(direction.nScale(r));
	}
	
	public void setNormal(Vector3D normal){
		this.normal=normal;
	}
	
	public Vector3D getNormal(){
		return normal;
	}
}
