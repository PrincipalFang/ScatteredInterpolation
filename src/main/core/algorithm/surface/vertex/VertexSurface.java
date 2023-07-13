package main.core.algorithm.surface.vertex;

import main.core.algorithm.structure.Vertex;
import main.core.algorithm.surface.ElementSurface;

public abstract class VertexSurface extends ElementSurface{
	protected Vertex vertex;
	
	public VertexSurface(Vertex vertex){
		super();
		this.vertex=vertex;
	}
	
	public SurfaceDomainType surfaceDomainType(){
		return SurfaceDomainType.General;
	}
	
	protected void tessellationProcess(){
		// TODO
		// For vertex surface output.
	}
}
