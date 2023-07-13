package main.core.algorithm.surface.face;

import main.core.algorithm.structure.Element;
import main.core.algorithm.structure.Face;
import main.core.mathematics.element.Point3D;

public class EmptyFaceSurface extends FaceSurface{
	public EmptyFaceSurface(Face face){
		super(face);
	}
	
	public Point3D calculateSurfacePoint(double x,double y,Element base){
		return null;
	}
	
	public SurfaceDomainType surfaceDomainType(){
		return null;
	}
}
