package main.core.mathematics.factor;

import main.core.mathematics.element.Point3D;

public class Affine3D{
	private double u;
	private double v;
	private double w;
	
	public Affine3D(int u,int v,int w){
		double sum=u+v+w;
		this.u=u/sum;
		this.v=v/sum;
		this.w=w/sum;
	}
	
	public Affine3D(double u,double v){
		this.u=u;
		this.v=v;
		this.w=1-u-v;
	}
	
	public Point3D combine(Point3D p1,Point3D p2,Point3D p3){
		return new Point3D(u*p1.getX()+v*p2.getX()+w*p3.getX(),
				u*p1.getY()+v*p2.getY()+w*p3.getY(),
				u*p1.getZ()+v*p2.getZ()+w*p3.getZ());
	}
	
	public double getU(){
		return u;
	}
	
	public double getV(){
		return v;
	}
	
	public double getW(){
		return w;
	}
}
