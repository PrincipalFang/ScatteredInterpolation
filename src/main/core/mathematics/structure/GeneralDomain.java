package main.core.mathematics.structure;

import main.core.mathematics.element.Point3D;
import main.core.mathematics.element.Vector3D;

public class GeneralDomain extends Plane{
	protected Point3D origin;
	protected Point3D xUnit;
	protected Point3D yUnit;
	protected double unit;
	
	public GeneralDomain(Point3D point,Vector3D normal){
		super(point,normal);
	}
	
	public GeneralDomain(Point3D p1,Point3D p2,Point3D p3){
		super(p1,p2,p3);
	}
	
	public void setOrigin(Point3D point){
		origin=this.nProjectOnPlane(point);
	}
	
	public void setXUnit(Point3D point){
		xUnit=this.nProjectOnPlane(point);
		unit=origin.distance(xUnit);
	}
	
	public void setYUnit(Point3D point){
		yUnit=this.nProjectOnPlane(point);
		unit=origin.distance(yUnit);
	}
	
	public void generateXUnit(){
		xUnit=origin.nPlus(origin.toward(yUnit).nRotate(normal,1.5*Math.PI));
	}
	
	public void generateYUnit(){
		yUnit=origin.nPlus(origin.toward(xUnit).nRotate(normal,0.5*Math.PI));
	}
	
	public Point3D getPoint(double x,double y){
		return origin.nPlus(origin.toward(xUnit).nScale(x)).nPlus(origin.toward(yUnit).nScale(y));
	}
	
	public double xCoordinate(Point3D point){
		double value=this.nProjectOnPlane(point).distance(origin,yUnit)/unit;
		if(origin.toward(xUnit).innerProduct(origin.toward(point))>0){
			return value;
		}else{
			return -value;
		}
	}
	
	public double yCoordinate(Point3D point){
		double value=this.nProjectOnPlane(point).distance(origin,xUnit)/unit;
		if(origin.toward(yUnit).innerProduct(origin.toward(point))>0){
			return value;
		}else{
			return -value;
		}
	}
	
	public double xLocalCoordinate(Point3D point){
		double value=point.distance(origin,yUnit)/unit;
		if(origin.toward(xUnit).innerProduct(origin.toward(point))>0){
			return value;
		}else{
			return -value;
		}
	}
	
	public double yLocalCoordinate(Point3D point){
		double value=point.distance(origin,xUnit)/unit;
		if(origin.toward(yUnit).innerProduct(origin.toward(point))>0){
			return value;
		}else{
			return -value;
		}
	}
}
