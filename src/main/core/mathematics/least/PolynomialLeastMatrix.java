package main.core.mathematics.least;

import main.core.mathematics.least.PolynomialLeastEntry.PolynomialLeastEntryType;

public class PolynomialLeastMatrix{
	private PolynomialLeastEntry[][] entries;
	private int rowSize,columnSize;
	private int rowFilled;
	
	public PolynomialLeastMatrix(int rowSize,int columnSize){
		entries=new PolynomialLeastEntry[rowSize][columnSize];
		this.rowSize=rowSize;
		this.columnSize=columnSize;
		rowFilled=0;
	}
	
	public void add(double x,double y,PolynomialLeastEntryType type){
		for(int j=0;j<columnSize;j++){
			entries[rowFilled][j]=new PolynomialLeastEntry(j,x,y,type);
		}
		rowFilled=rowFilled+1;
	}
	
	public void leastProcess(){
		int rowStep=0;
		int columnStep=0;
		while(rowStep<rowSize&&columnStep<columnSize){
			this.maximumPivoting(rowStep,columnStep);
			if(entries[rowStep][columnStep].zero()){
				columnStep=columnStep+1;
			}else{
				for(int i=rowStep+1;i<rowSize;i++){
					this.rowReduce(rowStep,i,columnStep);
				}
				rowStep=rowStep+1;
			}
		}
	}
	
	public PolynomialLeastEntry[] bases(){
		PolynomialLeastEntry[] result=new PolynomialLeastEntry[rowSize];
		int j=0;
		for(int i=0;i<rowSize;i++){
			while(entries[i][j].zero()){
				j=j+1;
			}
			result[i]=entries[i][j];
		}
		return result;
	}
	
	public String toString(){
		String result="[\n";
		for(int i=0;i<rowSize;i++){
			result=result+"|";
			for(int j=0;j<columnSize;j++){
				result=result+entries[i][j]+"|";
			}
			result=result+"\n";
		}
		result=result+"]";
		return result;
	}
	
	private void maximumPivoting(int base,int column){
		int pivot=base;
		double maxNorm=entries[base][column].norm();
		for(int i=base+1;i<rowSize;i++){
			double norm=entries[i][column].norm();
			if(maxNorm<norm){
				pivot=i;
				maxNorm=norm;
			}
		}
		PolynomialLeastEntry[] pivotRow=entries[pivot];
		for(int i=pivot;i>base;i--){
			entries[i]=entries[i-1];
		}
		entries[base]=pivotRow;
	}
	
	// Gram–Schmidt Orthogonalization
	private void rowReduce(int base,int target,int column){
		PolynomialLeastEntry baseEntry=entries[base][column];
		PolynomialLeastEntry targetEntry=entries[target][column];
		double scale=baseEntry.innerProduct(targetEntry)/baseEntry.innerProduct(baseEntry);
		for(int j=column;j<columnSize;j++){
			entries[target][j].mMinus(entries[base][j],scale);
		}
	}
}
