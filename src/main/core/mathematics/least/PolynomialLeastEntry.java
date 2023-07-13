package main.core.mathematics.least;

public class PolynomialLeastEntry{
	public enum PolynomialLeastEntryType{POINT,X_DERIVATIVE,Y_DERIVATIVE};
	
	private double[] entries;
	private int degree;
	
	public PolynomialLeastEntry(int degree,double x,double y,PolynomialLeastEntryType entryType){
		entries=new double[degree+1];
		this.degree=degree;
		switch(entryType){
			case POINT:
				for(int i=0;i<=degree;i++){
					entries[i]=this.power(x,degree-i)*this.power(y,i)/(this.factorial(degree-i)*this.factorial(i));
				}
				break;
			case X_DERIVATIVE:
				for(int i=0;i<=degree;i++){
					entries[i]=(degree-i)*this.power(x,degree-i-1)*this.power(y,i)/(this.factorial(degree-i)*this.factorial(i));
				}
				break;
			case Y_DERIVATIVE:
				for(int i=0;i<=degree;i++){
					entries[i]=i*this.power(x,degree-i)*this.power(y,i-1)/(this.factorial(degree-i)*this.factorial(i));
				}
				break;
			default:
				break;
		}
	}
	
	public double get(int i){
		return entries[i];
	}
	
	public double[] getEntries(){
		return entries;
	}
	
	public int getDegree(){
		return degree;
	}
	
	public double norm(){
		double result=0;
		for(int i=0;i<=degree;i++){
			result=result+this.power(entries[i],2);
		}
		result=Math.sqrt(result);
		return result;
	}
	
	public boolean zero(){
		for(int i=0;i<=degree;i++){
			if(Math.abs(entries[i])>0.00001){
				return false;
			}
		}
		return true;
	}
	
	public void mPlus(PolynomialLeastEntry target,double scale){
		for(int i=0;i<=degree;i++){
			entries[i]=entries[i]+target.entries[i]*scale;
		}
	}
	
	public void mMinus(PolynomialLeastEntry target,double scale){
		for(int i=0;i<=degree;i++){
			entries[i]=entries[i]-target.entries[i]*scale;
		}
	}
	
	public double innerProduct(PolynomialLeastEntry target){
		double result=0;
		for(int i=0;i<=degree;i++){
			result=result+entries[i]*target.entries[i];
		}
		return result;
	}
	
	public double value(double x,double y){
		double result=0;
		for(int i=0;i<=degree;i++){
			result=result+this.power(x,degree-i)*this.power(y,i)*entries[i];
		}
		return result;
	}
	
	public double dxValue(double x,double y){
		double result=0;
		for(int i=0;i<degree;i++){
			result=result+(degree-i)*this.power(x,degree-i-1)*this.power(y,i)*entries[i];
		}
		return result;
	}
	
	public double dyValue(double x,double y){
		double result=0;
		for(int i=1;i<=degree;i++){
			result=result+i*this.power(x,degree-i)*this.power(y,i-1)*entries[i];
		}
		return result;
	}
	
	public String toString(){
		String result=""+entries[0];
		for(int i=1;i<=degree;i++){
			result=result+" "+entries[i];
		}
		return result;
	}
	
	private double power(double base,double power){
		if(base!=0){
			return Math.pow(base,power);
		}else if(power==0){ 
			return 1;
		}else{
			return 0;
		}
	}
	
	private int factorial(int n){
		int result=1;
		int i=1;
		while(i<n){
			i=i+1;
			result=result*i;
		}
		return result;
	}
}
