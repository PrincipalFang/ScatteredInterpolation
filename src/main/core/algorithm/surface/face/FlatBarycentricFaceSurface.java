package main.core.algorithm.surface.face;

import main.core.algorithm.structure.Element;
import main.core.algorithm.structure.Face;
import main.core.mathematics.element.Point3D;

public class FlatBarycentricFaceSurface extends BarycentricFaceSurface{
	public FlatBarycentricFaceSurface(Face face){
		super(face);
	}
	
	public Point3D calculateSurfacePoint(double x,double y,Element base){
		return domain.calculatePoint(x,y);
	}
}
