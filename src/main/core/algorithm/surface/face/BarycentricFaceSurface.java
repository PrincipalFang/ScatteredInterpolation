package main.core.algorithm.surface.face;

import main.core.algorithm.structure.Face;

public abstract class BarycentricFaceSurface extends FaceSurface{
	public BarycentricFaceSurface(Face face){
		super(face);
	}
	
	public SurfaceDomainType surfaceDomainType(){
		return SurfaceDomainType.Barycentric;
	}
}
