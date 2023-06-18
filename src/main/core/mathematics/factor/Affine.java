package main.core.mathematics.factor;

import main.core.mathematics.element.Point3D;

public class Affine{
	private double[] weights;
	
	public Affine(double[] weights){
		this.weights=new double[weights.length];
		double sum=0;
		for(double weight:weights){
			sum=sum+weight;
		}
		for(int i=0;i<weights.length;i++){
			this.weights[i]=weights[i]/sum;
		}
	}
	
	public Point3D combine(Point3D[] points){
		Point3D result=new Point3D();
		for(int i=0;i<weights.length;i++){
			result.mPlus(points[i].nScale(weights[i]));
		}
		return result;
	}
	
	public Affine shift(int index,double epsilon){
		double[] newWeights=new double[weights.length];
		for(int i=0;i<weights.length;i++){
			newWeights[i]=weights[i]-epsilon;
		}
		newWeights[index]=newWeights[index]+weights.length*epsilon;
		return new Affine(newWeights);
	}
	
	public double getWeight(int index){
		return weights[index];
	}
	
	public int getSize(){
		return weights.length;
	}
}
