package main.core.algorithm.surface.face;

import main.core.algorithm.structure.Face;

public abstract class GeneralFaceSurface extends FaceSurface{
	public GeneralFaceSurface(Face face){
		super(face);
	}
	
	public SurfaceDomainType surfaceDomainType(){
		return SurfaceDomainType.General;
	}
}
