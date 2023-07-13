package main;

import java.text.DecimalFormat;
import main.core.algorithm.reparameterization.BasicBarycentricReparameterization;
import main.core.algorithm.reparameterization.BasicGeneralReparameterization;
import main.core.algorithm.reparameterization.ParametricBarycentricReparameterization;
import main.core.algorithm.reparameterization.ParametricGeneralReparameterization;
import main.core.algorithm.reparameterization.Reparameterization;
import main.core.algorithm.structure.Edge;
import main.core.algorithm.structure.Face;
import main.core.algorithm.structure.Vertex;
import main.core.algorithm.surface.edge.BinaryBlendedEdgeSurface;
import main.core.algorithm.surface.edge.EdgeSurface;
import main.core.algorithm.surface.edge.EmptyEdgeSurface;
import main.core.algorithm.surface.edge.FlatEdgeSurface;
import main.core.algorithm.surface.face.EmptyFaceSurface;
import main.core.algorithm.surface.face.FaceSurface;
import main.core.algorithm.surface.face.FlatBarycentricFaceSurface;
import main.core.algorithm.surface.face.FlatGeneralFaceSurface;
import main.core.algorithm.surface.face.MultiSidedBlendedGeneralFaceSurface;
import main.core.algorithm.surface.face.TriangularBlendedBarycentricFaceSurface;
import main.core.algorithm.surface.vertex.EmptyVertexSurface;
import main.core.algorithm.surface.vertex.FlatVertexSurface;
import main.core.algorithm.surface.vertex.MultiplePolygonalLeastVertexSurface;
import main.core.algorithm.surface.vertex.VertexSurface;
import main.core.mathematics.factor.Affine2D;
import main.core.mathematics.factor.Affine3D;
import main.core.mathematics.factor.Affine4D;

public abstract class Definition{
	// Surface type
	public static enum SurfaceOutputType{Vertex,Edge,Face,Test};
	public static enum VertexSurfaceType{Null,Flat,MultiplePolygonalLeast};
	public static enum EdgeSurfaceType{Null,Flat,BinaryBlended};
	public static enum FaceSurfaceType{Null,FlatBarycentric,FlatGeneral,TriangularBlendedBarycentric,MultiSidedBlendedGeneral};
	public static enum SurfaceDomainType{Null,Barycentric,General};
	public static enum ReparameterizationType{BasicBarycentric,BasicGeneral,ParametricBarycentric,ParametricGeneral};
	
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
	
	// Surface parameter
	protected static SurfaceOutputType surfaceOutputType=SurfaceOutputType.Face;
	protected static VertexSurfaceType vertexSurfaceType=VertexSurfaceType.Null;
	protected static EdgeSurfaceType edgeSurfaceType=EdgeSurfaceType.Null;
	protected static FaceSurfaceType faceSurfaceType=FaceSurfaceType.Null;
	protected static SurfaceDomainType vertexDomainType=SurfaceDomainType.Null;
	protected static SurfaceDomainType edgeDomainType=SurfaceDomainType.General;
	protected static SurfaceDomainType faceDomainType=SurfaceDomainType.General;
	protected static Reparameterization reparameterization=new BasicGeneralReparameterization();
	protected static int continuityDegree=2;
	protected static int tessellationLevel=10;
	protected static int numberOfTriangularTessellationPoint=66;
	protected static double estimateEpsilon=0.0001;
	protected static double curvatureBound=1;
	
	public static void setSurfaceOutputType(SurfaceOutputType type){
		surfaceOutputType=type;
	}
	
	public static void setVertexSurfaceType(VertexSurfaceType type){
		vertexSurfaceType=type;
		switch(vertexSurfaceType){
			case Null:
				vertexDomainType=SurfaceDomainType.Null;
				break;
			case Flat:
				vertexDomainType=SurfaceDomainType.General;
				break;
			case MultiplePolygonalLeast:
				vertexDomainType=SurfaceDomainType.General;
				break;
			default:
				break;
		}
	}
	
	public static void setEdgeSurfaceType(EdgeSurfaceType type){
		edgeSurfaceType=type;
		switch(edgeSurfaceType){
			case Null:
				edgeDomainType=SurfaceDomainType.Null;
				break;
			case Flat:
				edgeDomainType=SurfaceDomainType.General;
				break;
			case BinaryBlended:
				edgeDomainType=SurfaceDomainType.General;
				break;
			default:
				break;
		}
	}
	
	public static void setFaceSurfaceType(FaceSurfaceType type){
		faceSurfaceType=type;
		switch(faceSurfaceType){
			case Null:
				faceDomainType=SurfaceDomainType.Null;
				break;
			case FlatBarycentric:
				faceDomainType=SurfaceDomainType.Barycentric;
				break;
			case FlatGeneral:
				faceDomainType=SurfaceDomainType.General;
				break;
			case TriangularBlendedBarycentric:
				faceDomainType=SurfaceDomainType.Barycentric;
				break;
			case MultiSidedBlendedGeneral:
				faceDomainType=SurfaceDomainType.General;
				break;
			default:
				break;
		}
	}
	
	public static void setReparameterizationType(ReparameterizationType type,double ratio){
		switch(type){
			case BasicBarycentric:
				reparameterization=new BasicBarycentricReparameterization();
				break;
			case BasicGeneral:
				reparameterization=new BasicGeneralReparameterization();
				break;
			case ParametricBarycentric:
				reparameterization=new ParametricBarycentricReparameterization();
				break;
			case ParametricGeneral:
				reparameterization=new ParametricGeneralReparameterization();
				break;
			default:
				break;
		}
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
	
	protected VertexSurface createVertexSurface(Vertex vertex){
		switch(vertexSurfaceType){
			case Null:
				return new EmptyVertexSurface(vertex);
			case Flat:
				return new FlatVertexSurface(vertex);
			case MultiplePolygonalLeast:
				return new MultiplePolygonalLeastVertexSurface(vertex);
			default:
				return null;
		}
	}
	
	protected EdgeSurface createEdgeSurface(Edge edge){
		switch(edgeSurfaceType){
			case Null:
				return new EmptyEdgeSurface(edge);
			case Flat:
				return new FlatEdgeSurface(edge);
			case BinaryBlended:
				return new BinaryBlendedEdgeSurface(edge);
			default:
				return null;
		}
	}
	
	protected FaceSurface createFaceSurface(Face face){
		switch(faceSurfaceType){
			case Null:
				return new EmptyFaceSurface(face);
			case FlatBarycentric:
				return new FlatBarycentricFaceSurface(face);
			case FlatGeneral:
				return new FlatGeneralFaceSurface(face);
			case TriangularBlendedBarycentric:
				return new TriangularBlendedBarycentricFaceSurface(face);
			case MultiSidedBlendedGeneral:
				return new MultiSidedBlendedGeneralFaceSurface(face);
			default:
				return null;
		}
	}
}
