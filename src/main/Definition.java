package main;

import java.text.DecimalFormat;
import main.core.algorithm.reparameterization.Reparameterization;
import main.core.algorithm.reparameterization.BasicBarycentricReparameterization;
import main.core.algorithm.structure.Edge;
import main.core.algorithm.structure.Face;
import main.core.algorithm.structure.Vertex;
import main.core.algorithm.surface.edge.BinaryBlendedEdgeSurface;
import main.core.algorithm.surface.edge.EdgeSurface;
import main.core.algorithm.surface.face.FaceSurface;
import main.core.algorithm.surface.face.TriangularBlendedBarycentricFaceSurface;
import main.core.algorithm.surface.vertex.MultiplePolygonalLeastVertexSurface;
import main.core.algorithm.surface.vertex.VertexSurface;
import main.core.mathematics.factor.Affine2D;
import main.core.mathematics.factor.Affine3D;
import main.core.mathematics.factor.Affine4D;

public abstract class Definition{
	// Surface type
	public static enum SurfaceDomainType{Barycentric,General,Test};
	public static enum SurfaceOutputType{Vertex,Edge,Face,Test};
	
	// Affine combination constant
	protected final static Affine4D A1111=new Affine4D(1,1,1,1);
	protected final static Affine3D A11n1=new Affine3D(1,1,-1);
	protected final static Affine3D A211=new Affine3D(2,1,1);
	protected final static Affine3D A111=new Affine3D(1,1,1);
	protected final static Affine2D A2n1=new Affine2D(2,-1);
	protected final static Affine2D A31=new Affine2D(3,1);
	protected final static Affine2D A21=new Affine2D(2,1);
	protected final static Affine2D A11=new Affine2D(1,1);
	
	// Output file format
	protected final static DecimalFormat FORMAT=new DecimalFormat("#.##################");
	protected final static String[] PLY_HEADERS_0={"ply","format ascii 1.0"};
	protected final static String[] PLY_HEADERS_1={"property float x","property float y","property float z","property float nx","property float ny","property float nz","property uchar red","property uchar green","property uchar blue"};
	protected final static String[] PLY_HEADERS_2={"property list int int vertex_index","end_header"};
	protected final static String PLY_HEADER_0="element vertex";
	protected final static String PLY_HEADER_1="element face";
	protected final static String S3D_ISOPHOTE_LINE="L 2 2 0";
	
	// Reparameterization type
	protected static Reparameterization reparameterization=new BasicBarycentricReparameterization();
	
	// Surface parameter
	protected static SurfaceOutputType outputSurfaceType=SurfaceOutputType.Face;
	protected static int continuityDegree=2;
	protected static int tessellationLevel=10;
	protected static int numberOfTriangularTessellationPoint=66;
	protected static double estimateEpsilon=0.0001;
	protected static double curvatureBound=1;
	
	public static void setOutputType(SurfaceOutputType type){
		outputSurfaceType=type;
	}
	
	public static void setContinuityDegree(int degree){
		continuityDegree=degree;
	}
	
	public static void setTessellationLevel(int level){
		tessellationLevel=level;
		numberOfTriangularTessellationPoint=(level+1)*(level+2)/2;
	}
	
	public static void setEstimateEpsilon(double epsilon){
		estimateEpsilon=epsilon;
	}
	
	public static void setCurvatureBound(double bound){
		curvatureBound=bound;
	}
	
	public VertexSurface createVertexSurface(Vertex vertex){
		return new MultiplePolygonalLeastVertexSurface(vertex);
	}
	
	public EdgeSurface createEdgeSurface(Edge edge){
		return new BinaryBlendedEdgeSurface(edge);
	}
	
	public FaceSurface createFaceSurface(Face face){
		return new TriangularBlendedBarycentricFaceSurface(face);
	}
}
