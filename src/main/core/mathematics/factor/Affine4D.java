package main.core.mathematics.factor;

import main.core.mathematics.element.Point3D;

public class Affine4D{
	private double a;
	private double b;
	private double c;
	private double d;
	
	public Affine4D(int a,int b,int c,int d){
		double sum=a+b+c+d;
		this.a=a/sum;
		this.b=b/sum;
		this.c=c/sum;
		this.d=d/sum;
	}
	
	public Affine4D(double a,double b,double c){
		this.a=a;
		this.b=b;
		this.c=c;
		this.d=1-a-b-c;
	}
	
	public Point3D combine(Point3D p1,Point3D p2,Point3D p3,Point3D p4){
		return new Point3D(a*p1.getX()+b*p2.getX()+c*p3.getX()+d*p4.getX(),
				a*p1.getY()+b*p2.getY()+c*p3.getY()+d*p4.getY(),
				a*p1.getZ()+b*p2.getZ()+c*p3.getZ()+d*p4.getZ());
	}
	
	public double getA(){
		return a;
	}
	
	public double getB(){
		return b;
	}
	
	public double getC(){
		return c;
	}
	
	public double getD(){
		return d;
	}
}
