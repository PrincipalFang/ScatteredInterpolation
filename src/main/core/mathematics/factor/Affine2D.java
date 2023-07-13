package main.core.mathematics.factor;

import main.core.mathematics.element.Point3D;

public class Affine2D{
	private double u;
	private double v;

	public Affine2D(int u,int v){
		double sum=u+v;
		this.u=u/sum;
		this.v=v/sum;
	}
	
	public Affine2D(double u){
		this.u=u;
		this.v=1-u;
	}
	
	
	
	
	public Affine2D(double u,double v){
		double sum=u+v;
		this.u=u/sum;
		this.v=v/sum;
	}
	
	
	

	public Point3D combine(Point3D p1,Point3D p2){
		return new Point3D(u*p1.getX()+v*p2.getX(),
				u*p1.getY()+v*p2.getY(),
				u*p1.getZ()+v*p2.getZ());
	}
	
	
	
	
	
	
	public Point3D combine(Point3D p1,Point3D p2,double shift){
		double up=u+shift;
		
		if(up>1){
			up=1;
		}else if(up<0){
			up=0;
		}
		
		double vp=1-up;
		
		
		return new Point3D(up*p1.getX()+vp*p2.getX(),
				up*p1.getY()+vp*p2.getY(),
				up*p1.getZ()+vp*p2.getZ());
	}
	
	
	
	
	
	
	
	
	
	
	public double getU(){
		return u;
	}
	
	public double getV(){
		return v;
	}
}
