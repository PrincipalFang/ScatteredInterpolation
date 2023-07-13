package main.core.algorithm.structure;

import main.core.algorithm.domain.GeneralLocalDomain;
import main.core.data.output.OutputObject;
import main.core.data.output.OutputObjectHandler;

public class Edge extends Element implements OutputObjectHandler{
	protected Vertex headVertex;
	protected Vertex tailVertex;
	protected Face leftFace;
	protected Face rightFace;
	
	public Edge(Vertex headVertex,Vertex tailVertex){
		this.headVertex=headVertex;
		this.tailVertex=tailVertex;
		headVertex.linkEdge(tailVertex,this);
		tailVertex.linkEdge(headVertex,this);
	}
	
	public void linkFace(Face face){
		if(leftFace==null){
			leftFace=face;
		}else{
			rightFace=face;
		}
	}
	
	public Vertex getHeadVertex(){
		return headVertex;
	}
	
	public Vertex getTailVertex(){
		return tailVertex;
	}
	
	public Face getLeftFace(){
		return leftFace;
	}
	
	public Face getRightFace(){
		return rightFace;
	}
	
	public void createDomain(){
		switch(edgeDomainType){
			case General:
				domain=new GeneralLocalDomain(this);
				break;
			default:
				break;
		}
	}
	
	public void createSurface(){
		surface=this.createEdgeSurface(this);
	}
	
	public void output(OutputObject object){
		object.addSurface(surface);
	}
}
