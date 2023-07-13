package main.core.mathematics.least;

import java.util.ArrayList;

public class PolynomialLeastExpression{
	private ArrayList<PolynomialLeastEntry> entries;
	private ArrayList<Double> weights;
	private int size;
	
	public PolynomialLeastExpression(){
		entries=new ArrayList<PolynomialLeastEntry>();
		weights=new ArrayList<Double>();
		size=0;
	}
	
	public void add(PolynomialLeastEntry entry,double weight){
		entries.add(entry);
		weights.add(weight);
		size=size+1;
	}
	
	public double value(double x,double y){
		double result=0;
		for(int i=0;i<size;i++){
			result=result+entries.get(i).value(x,y)*weights.get(i);
		}
		return result;
	}
}
