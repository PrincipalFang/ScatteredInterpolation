package main.core.mathematics.structure;

import main.core.mathematics.element.Point3D;

public interface LocalDomain{
	public Point3D calculatePoint(double x,double y);
	public Point3D calculatePoint(Coordinate2D coordinate);
	public Coordinate2D calculateCoordinate(Point3D point);
}
