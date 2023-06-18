package main.core.algorithm.surface.vertex;

import main.core.algorithm.structure.Vertex;
import main.core.algorithm.surface.ElementSurface;
import main.core.mathematics.structure.GeneralLocalDomain;

public abstract class VertexSurface extends ElementSurface{
	protected Vertex vertex;
	
	public VertexSurface(Vertex vertex){
		super();
		domain=new GeneralLocalDomain(vertex);
		this.vertex=vertex;
	}
	
	protected SurfaceDomainType surfaceDomaintype(){
		return SurfaceDomainType.General;
	}
	
	protected void tessellationProcess(){
		// TODO
		// For vertex surface output.
	}
}
