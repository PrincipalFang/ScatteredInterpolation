package main.core.algorithm.structure;

import main.Definition;
import main.core.algorithm.domain.LocalDomain;
import main.core.algorithm.surface.ElementSurface;

public abstract class Element extends Definition{
	protected LocalDomain domain;
	protected ElementSurface surface;
	
	public abstract void createDomain();
	public abstract void createSurface();
	
	public LocalDomain getDomain(){
		return domain;
	}
	
	public ElementSurface getSurface(){
		return surface;
	}
}
