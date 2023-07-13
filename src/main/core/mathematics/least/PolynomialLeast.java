package main.core.mathematics.least;

import java.util.ArrayList;
import org.la4j.Matrix;
import org.la4j.Vector;
import org.la4j.inversion.GaussJordanInverter;
import main.core.mathematics.least.PolynomialLeastEntry.PolynomialLeastEntryType;

public class PolynomialLeast{
	private ArrayList<double[]> points;
	private ArrayList<double[]> derivatives;
	private PolynomialLeastMatrix matrix;
	private PolynomialLeastExpression expression;
	
	public PolynomialLeast(){
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
	
	public double[] getPoint(int index){
		return points.get(index);
	}
	
	public int getDerivativeSize(){
		return derivatives.size();
	}
	
	public double[] getDerivative(int index){
		return derivatives.get(index);
	}
	
	public void process(){
		int pointSize=points.size();
		int derivativeSize=derivatives.size();
		int size=pointSize+2*derivativeSize;
		matrix=new PolynomialLeastMatrix(size,size);
		expression=new PolynomialLeastExpression();
		for(int i=0;i<pointSize;i++){
			double[] point=points.get(i);
			matrix.add(point[0],point[1],PolynomialLeastEntryType.POINT);
		}
		for(int i=0;i<derivativeSize;i++){
			double[] derivative=derivatives.get(i);
			matrix.add(derivative[0],derivative[1],PolynomialLeastEntryType.X_DERIVATIVE);
			matrix.add(derivative[0],derivative[1],PolynomialLeastEntryType.Y_DERIVATIVE);
		}
		matrix.leastProcess();
		PolynomialLeastEntry[] bases=matrix.bases();
		Matrix invertible=Matrix.zero(size,size);
		Vector target=Vector.zero(size);
		for(int i=0;i<pointSize;i++){
			double[] point=points.get(i);
			for(int j=0;j<size;j++){
				invertible.set(i,j,bases[j].value(point[0],point[1]));
			}
			target.set(i,point[2]);
		}
		for(int i=0;i<derivativeSize;i++){
			double[] derivative=derivatives.get(i);
			target.set(pointSize+2*i,derivative[2]);
			target.set(pointSize+2*i+1,derivative[3]);
			for(int j=0;j<size;j++){
				invertible.set(pointSize+2*i,j,bases[j].dxValue(derivative[0],derivative[1]));
				invertible.set(pointSize+2*i+1,j,bases[j].dyValue(derivative[0],derivative[1]));
			}
		}
		Vector weight=new GaussJordanInverter(invertible).inverse().multiply(target);
		for(int i=0;i<size;i++){
			expression.add(bases[i],weight.get(i));
		}
	}
	
	public double value(double x,double y){
		return expression.value(x,y);
	}
}
