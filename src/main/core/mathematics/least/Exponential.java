package main.core.mathematics.least;

import java.util.ArrayList;
import org.la4j.Matrix;
import org.la4j.Vector;
import org.la4j.inversion.GaussJordanInverter;

public class Exponential{
	private ArrayList<double[]> points;
	private ArrayList<double[]> derivatives;
	private Vector weights;
	
	public Exponential(){
		points=new ArrayList<double[]>();
		derivatives=new ArrayList<double[]>();
	}
	
	public void add(double x,double y,double z){
		points.add(new double[]{x,y,z});
	}
	
	public void add(double x,double y,double dx,double dy){
		derivatives.add(new double[]{x,y,dx,dy});
	}
	
	public int getPointSize(){
		return points.size();
	}
	
	public double[] getPoint(int i){
		return points.get(i);
	}
	
	public void process(){
		//int degree=5;
		int pointSize=points.size();
		int derivativeSize=derivatives.size();
		int size=pointSize+2*derivativeSize;
		
		
		
		Matrix invertible=Matrix.zero(size,size);
		Vector target=Vector.zero(size);
		
		
		for(int i=0;i<pointSize;i++){
			target.set(i,points.get(i)[2]);
			for(int j=0;j<size;j++){
				invertible.set(i,j,this.entry(i,j));
			}
		}
		
		
		/*
		for(int i=0;i<derivativeSize;i++){
			double[] derivative=derivatives.get(i);
			target.set(pointSize+2*i,derivative[2]);
			target.set(pointSize+2*i+1,derivative[3]);
			for(int j=0;j<size;j++){
				invertible.set(pointSize+2*i,j,bases[j].dxValue(derivative[0],derivative[1]));
				invertible.set(pointSize+2*i+1,j,bases[j].dyValue(derivative[0],derivative[1]));
			}
		}
		*/
		weights=new GaussJordanInverter(invertible).inverse().multiply(target);
	}
	
	public double value(double x,double y){
		double result=0;
		
		for(int i=0;i<points.size();i++){
			result=result+weights.get(i)*Math.exp(points.get(i)[0]*x+points.get(i)[1]*y);
		}
		
		return result;
	}
	
	private double entry(int i,int j){
		return Math.exp(points.get(i)[0]*points.get(j)[0]+points.get(i)[1]*points.get(j)[1]);
	}
}
