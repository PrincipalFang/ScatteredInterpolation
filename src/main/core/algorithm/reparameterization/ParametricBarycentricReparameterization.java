package main.core.algorithm.reparameterization;

import main.core.algorithm.structure.Edge;
import main.core.algorithm.structure.Face;
import main.core.algorithm.structure.Vertex;
import main.core.mathematics.element.Point3D;
import main.core.mathematics.structure.Coordinate2D;
import main.core.mathematics.structure.GeneralDomain;

public class ParametricBarycentricReparameterization extends BasicBarycentricReparameterization{
	public Coordinate2D reparameterize(Face face,Edge edge,double x,double y){
		Vertex head=edge.getHeadVertex();
		Vertex tail=edge.getTailVertex();
		Point3D point=face.getDomain().calculatePoint(x,y);
		GeneralDomain mappedHeadEdgeDomain=new GeneralDomain(head.getPoint(),head.getNormal());
		mappedHeadEdgeDomain.setOrigin(head.getPoint());
		mappedHeadEdgeDomain.setYUnit(tail.getPoint());
		mappedHeadEdgeDomain.generateXUnit();
		Coordinate2D coordiateHeadPoint=mappedHeadEdgeDomain.calculateCoordinate(point);
		GeneralDomain mappedTailEdgeDomain=new GeneralDomain(tail.getPoint(),tail.getNormal());
		mappedTailEdgeDomain.setOrigin(head.getPoint());
		mappedTailEdgeDomain.setYUnit(tail.getPoint());
		mappedTailEdgeDomain.generateXUnit();
		Coordinate2D coordiateTailPoint=mappedTailEdgeDomain.calculateCoordinate(point);
		int headIndex=this.triangularVertexIndex(face,head);
		int tailIndex=this.triangularVertexIndex(face,tail);
		double barycentrciHead,barycentrciTail;
		switch(headIndex){
			case 0:
				barycentrciHead=x;
				break;
			case 1:
				barycentrciHead=y;
				break;
			case 2:
				barycentrciHead=1-x-y;
				break;
			default:
				barycentrciHead=0;
				break;
		}
		switch(tailIndex){
			case 0:
				barycentrciTail=x;
				break;
			case 1:
				barycentrciTail=y;
				break;
			case 2:
				barycentrciTail=1-x-y;
				break;
			default:
				barycentrciTail=0;
				break;
		}
		double pHead=Math.pow(barycentrciHead/(barycentrciHead+barycentrciTail),continuityDegree+1);
		double pTail=Math.pow(barycentrciTail/(barycentrciHead+barycentrciTail),continuityDegree+1);
		double px=(coordiateHeadPoint.getX()*pHead+coordiateTailPoint.getX()*pTail)/(pHead+pTail);
		double py=(coordiateHeadPoint.getY()*pHead+coordiateTailPoint.getY()*pTail)/(pHead+pTail);
		return new Coordinate2D(px,py);
	}
	
	public Coordinate2D reparameterize(Edge edge,Face face,double x,double y){
		// TODO
		return null;
	}
	
	private int triangularVertexIndex(Face face,Vertex vertex){
		for(int i=0;i<3;i++){
			if(face.getVertex(i)==vertex){
				return i;
			}
		}
		return -1;
	}
}
