package main.core.algorithm.domain;

import main.core.algorithm.structure.Face;
import main.core.mathematics.structure.BarycentricDomain;

public class BarycentricLocalDomain extends BarycentricDomain implements LocalDomain{
	public BarycentricLocalDomain(Face face){
		super(face.getVertex(0).getPoint(),face.getVertex(1).getPoint(),face.getVertex(2).getPoint());
	}
}
