package main.core.algorithm.surface;

import java.util.ArrayList;
import main.Definition;
import main.core.algorithm.structure.Element;
import main.core.mathematics.element.Point3D;
import main.core.mathematics.element.Vector3D;
import main.core.mathematics.structure.Coordinate2D;
import main.core.mathematics.structure.LocalDomain;
import main.core.mathematics.structure.Plane;

public abstract class ElementSurface extends Definition{
	protected LocalDomain domain;
	protected boolean tessellated;
	protected ArrayList<Point3D> points;
	protected ArrayList<Vector3D> normals;
	protected ArrayList<Double> curvatures;
	protected ArrayList<Double> isophoteLevels;
	protected ArrayList<int[]> indices;
	protected Point3D point;
	protected Vector3D normal;
	protected double curvature;
	protected double isophoteLevel;
	
	public abstract Point3D calculateSurfacePoint(double x,double y,Element base);
	
	protected abstract SurfaceDomainType surfaceDomaintype();
	protected abstract void tessellationProcess();
	
	public ElementSurface(){
		tessellated=false;
	}
	
	public Point3D calculateSurfacePoint(Coordinate2D coordinate,Element base){
		return this.calculateSurfacePoint(coordinate.getX(),coordinate.getY(),base);
	}
	
	public LocalDomain getDomain(){
		return domain;
	}
	
	public Point3D calculateLocalDomainPoint(double x,double y,Element base){
		return domain.calculatePoint(x,y);
	}
	
	public Point3D calculateLocalDomainPoint(Coordinate2D coordinate){
		return domain.calculatePoint(coordinate);
	}
	
	public void tessellation(){
		if(tessellated){
			return;
		}
		this.tessellationProcess();
		tessellated=true;
	}
	
	public ArrayList<Point3D> getPoints(){
		return points;
	}
	
	public ArrayList<Vector3D> getNormals(){
		return normals;
	}
	
	public ArrayList<Double> getCurvatures(){
		return curvatures;
	}
	
	public ArrayList<Double> getIsophoteLevels(){
		return isophoteLevels;
	}
	
	public ArrayList<int[]> getIndices(){
		return indices;
	}
	
	public void estimateBarycentricPoint(double u,double v,Element base){
		point=this.calculateSurfacePoint(u,v,base);
		// There is an unknown bug when estimating the points on the corner, so shift it a little bit.
		if(u==1){
			u=0.99;
			v=0.005;
		}else if(v==1){
			u=0.005;
			v=0.99;
		}else if(u+v==0){
			u=0.005;
			v=0.005;
		}
		Point3D point2=this.calculateSurfacePoint(u,v,base);
		Point3D t1=this.calculateSurfacePoint(u-2*estimateEpsilon,v+estimateEpsilon,base);
		Point3D t2=this.calculateSurfacePoint(u-estimateEpsilon,v-estimateEpsilon,base);
		Point3D t3=this.calculateSurfacePoint(u+estimateEpsilon,v-2*estimateEpsilon,base);
		Point3D t4=this.calculateSurfacePoint(u+2*estimateEpsilon,v-estimateEpsilon,base);
		Point3D t5=this.calculateSurfacePoint(u+estimateEpsilon,v+estimateEpsilon,base);
		Point3D t6=this.calculateSurfacePoint(u-estimateEpsilon,v+2*estimateEpsilon,base);
		normal=point2.toward(t1).crossProduct(point2.toward(t2)).nNormalize();
		curvature=2*Math.PI-point2.angle(t1,t2)-point2.angle(t2,t3)-point2.angle(t3,t4)-point2.angle(t4,t5)-point2.angle(t5,t6)-point2.angle(t6,t1);
		double area=point2.area(t1,t2)+point2.area(t2,t3)+point2.area(t3,t4)+point2.area(t4,t5)+point2.area(t5,t6)+point2.area(t6,t1);
		curvature=3*curvature/area;
	}
	
	public void estimateGeneralPoint(double x,double y,Element base){
		Point3D t1=this.calculateSurfacePoint(x+estimateEpsilon,y,base);
		Point3D t2=this.calculateSurfacePoint(x,y+estimateEpsilon,base);
		Point3D t3=this.calculateSurfacePoint(x-estimateEpsilon,y,base);
		Point3D t4=this.calculateSurfacePoint(x,y-estimateEpsilon,base);
		point=this.calculateSurfacePoint(x,y,base);
		normal=point.toward(t1).crossProduct(point.toward(t2)).nNormalize();
		curvature=2*Math.PI-point.angle(t1,t2)-point.angle(t2,t3)-point.angle(t3,t4)-point.angle(t4,t1);
		double area=point.area(t1,t2)+point.area(t2,t3)+point.area(t3,t4)+point.area(t4,t1);
		curvature=3*curvature/area;
	}
	
	protected Point3D getPoint(){
		return point;
	}
	
	protected Vector3D getNormal(){
		return normal;
	}
	
	protected double getCurvature(){
		return curvature;
	}
	
	protected double getIsophoteLevel(){
		return isophoteLevel;
	}
	
	protected ArrayList<Double> getIsophoteLevels(ArrayList<Point3D> points,ArrayList<Vector3D> normals){
		ArrayList<Double> levels=new ArrayList<Double>();
		for(int i=0;i<points.size();i++){
			Point3D point=points.get(i);
			Vector3D normal=normals.get(i);
			Plane plane=new Plane(point,normal);
			Point3D target=point.nPlus(new Vector3D(1,0.2,0.2));
			target.mPlus(normal.nScale(-2*plane.signedDistance(target)));
			Vector3D reflected=point.toward(target).nNormalize();
			levels.add(3*normal.innerProduct(reflected));
		}
		return levels;
	}
	
	protected int getTriangularTessellationIndex(int i,int j){
		return i*(i-2*tessellationLevel-3)/2-j+numberOfTriangularTessellationPoint-1;
	}
}
