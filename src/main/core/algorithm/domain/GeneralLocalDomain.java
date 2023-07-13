package main.core.algorithm.domain;

import main.core.algorithm.structure.Edge;
import main.core.algorithm.structure.Face;
import main.core.algorithm.structure.Vertex;
import main.core.mathematics.element.Vector3D;
import main.core.mathematics.structure.GeneralDomain;

public class GeneralLocalDomain extends GeneralDomain implements LocalDomain{
	public GeneralLocalDomain(Vertex vertex){
		super(vertex.getPoint(),vertex.getNormal());
		this.setOrigin(vertex.getPoint());
		this.setYUnit(vertex.getNeighbors().get(0).getPoint());
		this.generateXUnit();
	}
	
	public GeneralLocalDomain(Edge edge){
		super(edge.getHeadVertex().getPoint(),edge.getHeadVertex().getNormal());
		Vector3D normal=edge.getHeadVertex().getNormal().nPlus(edge.getTailVertex().getNormal()).nNormalize();
		Vector3D direction=edge.getHeadVertex().getPoint().toward(edge.getTailVertex().getPoint());
		normal=direction.crossProduct(normal).crossProduct(direction).nNormalize();
		this.setNormal(normal);
		this.setOrigin(edge.getHeadVertex().getPoint());
		this.setYUnit(edge.getTailVertex().getPoint());
		this.generateXUnit();
	}
	
	public GeneralLocalDomain(Face face){
		super(face.getCenter(),face.getNormal());
		this.setOrigin(face.getCenter());
		this.setXUnit(face.getVertex(0).getPoint());
		this.generateYUnit();
	}
}
