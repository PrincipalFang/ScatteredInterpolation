package main.core.algorithm.surface.face;

import main.core.algorithm.structure.Face;

public abstract class GeneralFaceSurface extends FaceSurface{
	public GeneralFaceSurface(Face face){
		super(face);
	}
	
	protected SurfaceDomainType surfaceDomaintype(){
		return SurfaceDomainType.General;
	}
}
