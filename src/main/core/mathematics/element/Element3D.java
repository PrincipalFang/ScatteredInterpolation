package main.core.mathematics.element;

import java.text.DecimalFormat;

public abstract class Element3D{
	private static DecimalFormat FORMAT=new DecimalFormat("#.##################");
	
	protected double x;
	protected double y;
	protected double z;
	
	public Element3D(double x,double y,double z){
		this.x=x;
		this.y=y;
		this.z=z;
	}
	
	public Element3D(){
		this.x=0;
		this.y=0;
		this.z=0;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public double getZ(){
		return z;
	}
	
	public void assign(Element3D target){
		x=target.x;
		y=target.y;
		z=target.z;
	}
	
	public void setX(double x){
		this.x=x;
	}
	
	public void setY(double y){
		this.y=y;
	}
	
	public void setZ(double z){
		this.z=z;
	}
	
	public void mPlus(Element3D v){
		x=x+v.x;
		y=y+v.y;
		z=z+v.z;
	}
	
	public void mMinus(Element3D v){
		x=x-v.x;
		y=y-v.y;
		z=z-v.z;
	}
	
	public void mScale(double s){
		x=x*s;
		y=y*s;
		z=z*s;
	}
	
	public void mScale(double x,double y,double z){
		this.x=this.x*x;
		this.y=this.y*y;
		this.z=this.z*z;
	}
	
	public String toString(){
		return FORMAT.format(x)+" "+FORMAT.format(y)+" "+FORMAT.format(z);
	}
}
