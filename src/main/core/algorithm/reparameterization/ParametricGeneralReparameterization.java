package main.core.algorithm.reparameterization;

import main.core.algorithm.structure.Edge;
import main.core.algorithm.structure.Face;
import main.core.algorithm.structure.Vertex;
import main.core.mathematics.element.Point3D;
import main.core.mathematics.factor.Affine3D;
import main.core.mathematics.structure.Coordinate2D;
import main.core.mathematics.structure.GeneralDomain;

public class ParametricGeneralReparameterization extends BasicBarycentricReparameterization{
	public Coordinate2D reparameterize(Face face,Edge edge,double x,double y){
		Point3D point=face.getDomain().calculatePoint(x,y);
		Vertex head=edge.getHeadVertex();
		Vertex tail=edge.getTailVertex();
		Point3D faceDomainHead=face.getDomain().nProjectOnPlane(head.getPoint());
		Point3D faceDomainTail=face.getDomain().nProjectOnPlane(tail.getPoint());
		GeneralDomain mappedHeadEdgeDomain=new GeneralDomain(head.getPoint(),head.getNormal());
		mappedHeadEdgeDomain.setOrigin(head.getPoint());
		mappedHeadEdgeDomain.setYUnit(tail.getPoint());
		mappedHeadEdgeDomain.generateXUnit();
		GeneralDomain mappedTailEdgeDomain=new GeneralDomain(tail.getPoint(),tail.getNormal());
		mappedTailEdgeDomain.setOrigin(head.getPoint());
		mappedTailEdgeDomain.setYUnit(tail.getPoint());
		mappedTailEdgeDomain.generateXUnit();
		Affine3D affineHead,affineTail;
		double coordinateHeadPointX,coordinateHeadPointY,coordinateTailPointX,coordinateTailPointY;
		if(face.getNextVertex(head)==tail){
			Point3D faceDomainHeadLast=face.getDomain().nProjectOnPlane(face.getLastVertex(head).getPoint());
			Point3D faceDomainTailNext=face.getDomain().nProjectOnPlane(face.getNextVertex(tail).getPoint());
			affineHead=point.affine(faceDomainHead,faceDomainTail,faceDomainHeadLast);
			affineTail=point.affine(faceDomainHead,faceDomainTail,faceDomainTailNext);
			Coordinate2D coordinateHeadLast=mappedHeadEdgeDomain.calculateCoordinate(face.getLastVertex(head).getPoint());
			Coordinate2D coordinateTailNext=mappedTailEdgeDomain.calculateCoordinate(face.getNextVertex(tail).getPoint());
			coordinateHeadPointX=affineHead.getW()*coordinateHeadLast.getX();
			coordinateHeadPointY=affineHead.getV()+affineHead.getW()*coordinateHeadLast.getY();
			coordinateTailPointX=affineTail.getW()*coordinateTailNext.getX();
			coordinateTailPointY=affineTail.getV()+affineTail.getW()*coordinateTailNext.getY();
		}else{
			Point3D faceDomainHeadNext=face.getDomain().nProjectOnPlane(face.getNextVertex(head).getPoint());
			Point3D faceDomainTailLast=face.getDomain().nProjectOnPlane(face.getLastVertex(tail).getPoint());
			affineHead=point.affine(faceDomainHead,faceDomainHeadNext,faceDomainTail);
			affineTail=point.affine(faceDomainHead,faceDomainTailLast,faceDomainTail);
			Coordinate2D coordinateHeadNext=mappedHeadEdgeDomain.calculateCoordinate(face.getNextVertex(head).getPoint());
			Coordinate2D coordinateTailLast=mappedTailEdgeDomain.calculateCoordinate(face.getLastVertex(tail).getPoint());
			coordinateHeadPointX=affineHead.getV()*coordinateHeadNext.getX();
			coordinateHeadPointY=affineHead.getV()*coordinateHeadNext.getY()+affineHead.getW();
			coordinateTailPointX=affineTail.getV()*coordinateTailLast.getX();
			coordinateTailPointY=affineTail.getV()*coordinateTailLast.getY()+affineTail.getW();
		}
		GeneralDomain mappedEdgeDomain=new GeneralDomain(face.getCenter(),face.getNormal());
		mappedEdgeDomain.setOrigin(head.getPoint());
		mappedEdgeDomain.setYUnit(tail.getPoint());
		mappedEdgeDomain.generateXUnit();
		Coordinate2D coordiatePoint=mappedEdgeDomain.calculateCoordinate(point);
		double cy=coordiatePoint.getY();
		double pHead=Math.pow(1-cy,continuityDegree+1);
		double pTail=Math.pow(cy,continuityDegree+1);
		double px=(coordinateHeadPointX*pHead+coordinateTailPointX*pTail)/(pHead+pTail);
		double py=(coordinateHeadPointY*pHead+coordinateTailPointY*pTail)/(pHead+pTail);
		return new Coordinate2D(px,py);
	}
	
	public Coordinate2D reparameterize(Edge edge,Face face,double x,double y){
		// TODO
		return null;
	}
}
