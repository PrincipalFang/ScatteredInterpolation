package main.core.algorithm.surface.edge;

import java.util.ArrayList;
import main.core.algorithm.structure.Edge;
import main.core.algorithm.surface.ElementSurface;
import main.core.mathematics.element.Point3D;
import main.core.mathematics.element.Vector3D;
import main.core.mathematics.structure.GeneralLocalDomain;

public abstract class EdgeSurface extends ElementSurface{
	protected Edge edge;
	
	public EdgeSurface(Edge edge){
		super();
		domain=new GeneralLocalDomain(edge);
		this.edge=edge;
	}
	
	protected SurfaceDomainType surfaceDomaintype(){
		return SurfaceDomainType.General;
	}
	
	protected void tessellationProcess(){
		points=new ArrayList<Point3D>();
		normals=new ArrayList<Vector3D>();
		curvatures=new ArrayList<Double>();
		indices=new ArrayList<int[]>();
		for(int i=-tessellationLevel;i<=tessellationLevel;i++){
			double x=(double)i/tessellationLevel;
			for(int j=0;j<=tessellationLevel;j++){
				double y=(double)j/tessellationLevel;
				this.estimateGeneralPoint(x,y,null);
				points.add(this.getPoint());
				normals.add(this.getNormal());
				curvatures.add(this.getCurvature());
			}
		}
		for(int i=0;i<2*tessellationLevel;i++){
			for(int j=0;j<tessellationLevel;j++){
				int base=i*(tessellationLevel+1)+j;
				indices.add(new int[]{base,base+tessellationLevel+1,base+tessellationLevel+2,base+1});
			}
		}
	}
}
