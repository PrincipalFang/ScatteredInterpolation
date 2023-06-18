package main.core.algorithm.structure;

import main.Definition;
import main.core.algorithm.surface.ElementSurface;

public abstract class Element extends Definition{
	protected ElementSurface surface;
	
	public abstract void createSurface();
	
	public ElementSurface getSurface(){
		return surface;
	}
}
