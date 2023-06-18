package main.core.algorithm.surface.face;

import java.util.ArrayList;
import main.core.algorithm.structure.Face;
import main.core.algorithm.surface.ElementSurface;
import main.core.mathematics.element.Point3D;
import main.core.mathematics.element.Vector3D;
import main.core.mathematics.structure.BarycentricLocalDomain;
import main.core.mathematics.structure.GeneralLocalDomain;

public abstract class FaceSurface extends ElementSurface{
	protected Face face;
	
	public FaceSurface(Face face){
		super();
		switch(this.surfaceDomaintype()){
			case Barycentric:
				domain=new BarycentricLocalDomain(face);
				break;
			case General:
				domain=new GeneralLocalDomain(face);
				break;
			default:
				break;
		}
		this.face=face;
	}
	
	protected void tessellationProcess(){
		switch(this.surfaceDomaintype()){
			case Barycentric:
				this.BarycentricTessellationProcess();
				break;
			case General:
				this.GeneralTessellationProcess();
				break;
			default:
				break;
		}
	}
	
	private void BarycentricTessellationProcess(){
		points=new ArrayList<Point3D>();
		normals=new ArrayList<Vector3D>();
		curvatures=new ArrayList<Double>();
		indices=new ArrayList<int[]>();
		for(int i=tessellationLevel;i>=0;i--){
			double u=(double)i/tessellationLevel;
			for(int j=tessellationLevel-i;j>=0;j--){
				double v=(double)j/tessellationLevel;
				this.estimateBarycentricPoint(u,v,null);
				points.add(this.getPoint());
				normals.add(this.getNormal());
				curvatures.add(this.getCurvature());
			}
		}
		for(int i=tessellationLevel;i>0;i--){
			for(int j=tessellationLevel-i;j>=0;j--){
				indices.add(new int[]{this.getTriangularTessellationIndex(i,j),this.getTriangularTessellationIndex(i-1,j+1),this.getTriangularTessellationIndex(i-1,j)});
			}
		}
		for(int i=tessellationLevel;i>1;i--){
			for(int j=tessellationLevel-i;j>=0;j--){
				indices.add(new int[]{this.getTriangularTessellationIndex(i-2,j+1),this.getTriangularTessellationIndex(i-1,j),this.getTriangularTessellationIndex(i-1,j+1)});
			}
		}
	}
	
	private void GeneralTessellationProcess(){
		points=new ArrayList<Point3D>();
		normals=new ArrayList<Vector3D>();
		curvatures=new ArrayList<Double>();
		indices=new ArrayList<int[]>();
		int shift=0;
		int size=face.getSize();
		Point3D[] polygonPoints=new Point3D[size];
		double[] domainX=new double[size];
		double[] domainY=new double[size];
		for(int i=0;i<size;i++){
			polygonPoints[i]=face.getVertex(i).getPoint();
			domainX[i]=((GeneralLocalDomain)domain).xCoordinate(polygonPoints[i]);
			domainY[i]=((GeneralLocalDomain)domain).yCoordinate(polygonPoints[i]);
		}
		for(int i=0;i<size;i++){
			double xv=domainX[i];
			double xw=domainX[(i+1)%size];
			double yv=domainY[i];
			double yw=domainY[(i+1)%size];
			for(int j=tessellationLevel;j>=0;j--){
				double u=(double)j/tessellationLevel;
				for(int k=tessellationLevel-j;k>=0;k--){
					double v=(double)k/tessellationLevel;
					double w=1-u-v;
					this.estimateGeneralPoint(v*xv+w*xw,v*yv+w*yw,null);
					points.add(this.getPoint());
					normals.add(this.getNormal());
					curvatures.add(this.getCurvature());
				}
			}
			for(int j=tessellationLevel;j>0;j--){
				for(int k=tessellationLevel-j;k>=0;k--){
					indices.add(new int[]{this.getTriangularTessellationIndex(j,k)+shift,this.getTriangularTessellationIndex(j-1,k+1)+shift,this.getTriangularTessellationIndex(j-1,k)+shift});
				}
			}
			for(int j=tessellationLevel;j>1;j--){
				for(int k=tessellationLevel-j;k>=0;k--){
					indices.add(new int[]{this.getTriangularTessellationIndex(j-2,k+1)+shift,this.getTriangularTessellationIndex(j-1,k)+shift,this.getTriangularTessellationIndex(j-1,k+1)+shift});
				}
			}
			shift=shift+numberOfTriangularTessellationPoint;
		}
	}
}
