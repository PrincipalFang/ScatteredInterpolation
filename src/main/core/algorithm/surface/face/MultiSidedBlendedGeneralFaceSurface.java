package main.core.algorithm.surface.face;

import main.core.algorithm.structure.Edge;
import main.core.algorithm.structure.Element;
import main.core.algorithm.structure.Face;
import main.core.mathematics.element.Point3D;
import main.core.mathematics.factor.Affine;

public class MultiSidedBlendedGeneralFaceSurface extends GeneralFaceSurface{
	protected int size;
	protected Point3D[] domainPoints;
	
	public MultiSidedBlendedGeneralFaceSurface(Face face){
		super(face);
		size=face.getSize();
		domainPoints=new Point3D[size];
		for(int i=0;i<size;i++){
			domainPoints[i]=face.getDomain().nProjectOnPlane(face.getVertex(i).getPoint());
		}
	}
	
	public Point3D calculateSurfacePoint(double x,double y,Element base){
		Point3D target=face.getDomain().calculatePoint(x,y);
		Point3D[] candidates=new Point3D[size];
		double[] distances=new double[size];
		for(int i=0;i<size;i++){
			Edge currentEdge=face.getEdge(i);
			candidates[i]=currentEdge.getSurface().calculateSurfacePoint(reparameterization.reparameterize(face,currentEdge,x,y),null);
			distances[i]=target.distance(domainPoints[i],domainPoints[(i+1)%size]);
			if(distances[i]==0){
				return candidates[i];
			}
		}
		double product=1;
		for(int i=0;i<size;i++){
			distances[i]=Math.pow(distances[i],continuityDegree+1);
			product=product*distances[i];
		}
		double[] weights=new double[size];
		for(int i=0;i<size;i++){
			double temp=distances[i];
			double facter=0;
			for(int j=0;j<size;j++){
				if(i!=j){
					facter=facter+1/(temp+distances[j]);
				}
			}
			weights[i]=product*facter/temp;
		}
		double sum=0;
		for(int i=0;i<size;i++){
			sum=sum+weights[i];
		}
		for(int i=0;i<size;i++){
			weights[i]=weights[i]/sum;
		}
		return new Affine(weights).combine(candidates);
	}
}
