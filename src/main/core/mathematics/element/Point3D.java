package main.core.mathematics.element;

import main.core.mathematics.factor.Affine3D;

public class Point3D extends Element3D{
	public Point3D(double x,double y,double z){
		super(x,y,z);
	}
	
	public Point3D(){
		super();
	}
	
	public Point3D duplicate(){
		return new Point3D(x,y,z);
	}
	
	public Point3D nPlus(Element3D v){
		return new Point3D(x+v.x,y+v.y,z+v.z);
	}
	
	public Point3D nMinus(Element3D v){
		return new Point3D(x-v.x,y-v.y,z-v.z);
	}
	
	public Point3D nScale(double s){
		return new Point3D(x*s,y*s,z*s);
	}
	
	public Point3D nScale(double x,double y,double z){
		return new Point3D(this.x*x,this.y*y,this.z*z);
	}
	
	public Vector3D toward(Point3D p){
		return new Vector3D(p.x-x,p.y-y,p.z-z);
	}
	
	public Affine3D affine(Point3D p0,Point3D p1,Point3D p2){
		double area0=p0.area(p1,p2);
		double area1=this.area(p1,p2);
		double area2=this.area(p2,p0);
		double u=area1/area0;
		double v=area2/area0;
		if(p0.toward(p1).crossProduct(p0.toward(p2)).innerProduct(this.toward(p1).crossProduct(this.toward(p2)))<0){
			u=-u;
		}
		if(p1.toward(p2).crossProduct(p1.toward(p0)).innerProduct(this.toward(p2).crossProduct(this.toward(p0)))<0){
			v=-v;
		}
		return new Affine3D(u,v);
	}
	
	public double distance(Point3D p){
		return Math.sqrt(Math.pow(x-p.x,2)+Math.pow(y-p.y,2)+Math.pow(z-p.z,2));
	}
	
	public double distance(Point3D p0,Point3D p1){
		return 2*this.area(p0,p1)/p0.distance(p1);
	}
	
	public double angle(Point3D p0,Point3D p1){
		double a=this.distance(p0);
		double b=this.distance(p1);
		double c=p0.distance(p1);
		return Math.acos((a*a+b*b-c*c)/(2*a*b));
	}
	
	public double area(Point3D p0,Point3D p1){
		double a=this.toward(p0).length();
		double b=p0.toward(p1).length();
		double c=p1.toward(this).length();
		double s=(a+b+c)/2;
		return Math.sqrt(Math.max(s*(s-a)*(s-b)*(s-c),0));
	}
}
