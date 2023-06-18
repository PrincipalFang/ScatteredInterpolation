package main.core.mathematics.element;

public class Vector3D extends Element3D{
	public Vector3D(double x,double y,double z){
		super(x,y,z);
	}
	
	public Vector3D(){
		super();
	}
	
	public Vector3D duplicate(){
		return new Vector3D(x,y,z);
	}
	
	public Vector3D nPlus(Vector3D v){
		return new Vector3D(x+v.x,y+v.y,z+v.z);
	}
	
	public Vector3D nMinus(Vector3D v){
		return new Vector3D(x-v.x,y-v.y,z-v.z);
	}
	
	public Vector3D nScale(double s){
		return new Vector3D(x*s,y*s,z*s);
	}
	
	public Vector3D nScale(double x,double y,double z){
		return new Vector3D(this.x*x,this.y*y,this.z*z);
	}
	
	public Vector3D crossProduct(Vector3D v){
		return new Vector3D(y*v.z-z*v.y,z*v.x-x*v.z,x*v.y-y*v.x);
	}
	
	public double innerProduct(Vector3D v){
		return x*v.x+y*v.y+z*v.z;
	}
	
	public double length(){
		return Math.sqrt(Math.pow(x,2)+Math.pow(y,2)+Math.pow(z,2));
	}
	
	public void mNormalize(){
		double length=this.length();
		if(length>0){
			x=x/length;
			y=y/length;
			z=z/length;
		}
	}
	
	public Vector3D nNormalize(){
		double length=this.length();
		if(length==0){
			return new Vector3D(0,0,0);
		}else{
			return new Vector3D(x/length,y/length,z/length);
		}
	}
	
	public void mFixLength(double l){
		double length=this.length();
		if(length>0){
			x=x*l/length;
			y=y*l/length;
			z=z*l/length;
		}
	}
	
	public Vector3D nFixLength(double l){
		double length=this.length();
		if(length==0){
			return new Vector3D(0,0,0);
		}else{
			return new Vector3D(x*l/length,y*l/length,z*l/length);
		}
	}
	
	public void mFixValueX(double t){
		if(x!=0){
			y=y*t/x;
			z=z*t/x;
			x=t;
		}
	}
	
	public Vector3D nFixValueX(double t){
		if(x==0){
			return new Vector3D(x,y,z);
		}else{
			return new Vector3D(t,y*t/x,z*t/x);
		}
	}
	
	public void mFixValueY(double t){
		if(y!=0){
			x=x*t/y;
			z=z*t/y;
			y=t;
		}
	}
	
	public Vector3D nFixValueY(double t){
		if(y==0){
			return new Vector3D(x,y,z);
		}else{
			return new Vector3D(x*t/y,t,z*t/y);
		}
	}
	
	public void mFixValueZ(double t){
		if(z!=0){
			x=x*t/z;
			y=y*t/z;
			z=t;
		}
	}
	
	public Vector3D nFixValueZ(double t){
		if(z==0){
			return new Vector3D(x,y,z);
		}else{
			return new Vector3D(x*t/z,y*t/z,t);
		}
	}
	
	public void mRotate(Vector3D axis,double r){
		Vector3D newAxis=axis.nNormalize();
		double sin=Math.sin(r);
		double cos=Math.cos(r);
		double newX=(cos+newAxis.x*newAxis.x*(1-cos))*x+(newAxis.x*newAxis.y*(1-cos)-newAxis.z*sin)*y+(newAxis.x*newAxis.z*(1-cos)+newAxis.y*sin)*z;
		double newY=(newAxis.y*newAxis.x*(1-cos)+newAxis.z*sin)*x+(cos+newAxis.y*newAxis.y*(1-cos))*y+(newAxis.y*newAxis.z*(1-cos)-newAxis.x*sin)*z;
		double newZ=(newAxis.z*newAxis.x*(1-cos)-newAxis.y*sin)*x+(newAxis.z*newAxis.y*(1-cos)+newAxis.x*sin)*y+(cos+newAxis.z*newAxis.z*(1-cos))*z;
		x=newX;
		y=newY;
		z=newZ;
	}
	
	public Vector3D nRotate(Vector3D axis,double r){
		Vector3D newAxis=axis.nNormalize();
		double sin=Math.sin(r);
		double cos=Math.cos(r);
		double newX=(cos+newAxis.x*newAxis.x*(1-cos))*x+(newAxis.x*newAxis.y*(1-cos)-newAxis.z*sin)*y+(newAxis.x*newAxis.z*(1-cos)+newAxis.y*sin)*z;
		double newY=(newAxis.y*newAxis.x*(1-cos)+newAxis.z*sin)*x+(cos+newAxis.y*newAxis.y*(1-cos))*y+(newAxis.y*newAxis.z*(1-cos)-newAxis.x*sin)*z;
		double newZ=(newAxis.z*newAxis.x*(1-cos)-newAxis.y*sin)*x+(newAxis.z*newAxis.y*(1-cos)+newAxis.x*sin)*y+(cos+newAxis.z*newAxis.z*(1-cos))*z;
		return new Vector3D(newX,newY,newZ);
	}
	
	public double rotateAngle(Vector3D axis,Vector3D v){
		double result=this.innerProduct(v)/this.length()/v.length();
		result=Math.min(result,1);
		result=Math.max(result,-1);
		double angle=Math.acos(result);
		if(this.crossProduct(v).innerProduct(axis)>0){
			return angle;
		}else{
			return -angle;
		}
	}
}
