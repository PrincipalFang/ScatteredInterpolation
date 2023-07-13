package main.core.algorithm.domain;

import main.core.mathematics.element.Point3D;
import main.core.mathematics.element.Vector3D;
import main.core.mathematics.structure.Coordinate2D;

public interface LocalDomain{
	public double signedDistance(Point3D target);
	public Point3D nProjectOnPlane(Point3D target);
	public void pProjectOnPlane(Point3D target);
	public Point3D nShiftToPlane(Point3D target,Vector3D direction);
	public void pShiftToPlane(Point3D target,Vector3D direction);
	public Point3D calculatePoint(double x,double y);
	public Point3D calculatePoint(Coordinate2D coordinate);
	public Coordinate2D calculateCoordinate(Point3D point);
	public double xCoordinate(Point3D point);
	public double yCoordinate(Point3D point);
	public double xLocalCoordinate(Point3D point);
	public double yLocalCoordinate(Point3D point);
}
