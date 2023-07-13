package main;

import java.util.ArrayList;
import main.Definition.EdgeSurfaceType;
import main.Definition.FaceSurfaceType;
import main.Definition.ReparameterizationType;
import main.Definition.SurfaceOutputType;
import main.Definition.VertexSurfaceType;
import main.core.algorithm.structure.Mesh;
import main.core.data.file.FFileReader;
import main.core.data.output.OutputObject;

public class Main{
	// Input and output
	private final static String TARGET_FILE="Ellipsoid";
	private final static String TARGET_FOLDER=System.getProperty("user.home")+"/Desktop/Test/Input";
	private final static String DESTINATION_FOLDER=System.getProperty("user.home")+"/Desktop/Test/Output";
	// Surface parameter
	private final static SurfaceOutputType SURFACE_OUTPUT_TYPE=SurfaceOutputType.Face;
	private final static VertexSurfaceType VERTEX_SURFACE_TYPE=VertexSurfaceType.MultiplePolygonalLeast;
	private final static EdgeSurfaceType EDGE_SURFACE_TYPE=EdgeSurfaceType.BinaryBlended;
	private final static FaceSurfaceType FACE_SURFACE_TYPE=FaceSurfaceType.TriangularBlendedBarycentric;
	private final static ReparameterizationType REPARAMETERIZATION_TYPE=ReparameterizationType.ParametricBarycentric;
	private final static double REPARAMETERIZATION_RATIO=0;
	private final static int CONTINUITY_DEGREE=2;
	private final static int TESSELLATION_LEVEL=25;
	private final static double ESTIMATE_EPSILON=0.0001;
	private final static double CURVATURE_BOUND=1;
	
	public static void main(String[] args){
		// Parameter Setting
		Definition.setSurfaceOutputType(SURFACE_OUTPUT_TYPE);
		Definition.setVertexSurfaceType(VERTEX_SURFACE_TYPE);
		Definition.setEdgeSurfaceType(EDGE_SURFACE_TYPE);
		Definition.setFaceSurfaceType(FACE_SURFACE_TYPE);
		Definition.setReparameterizationType(REPARAMETERIZATION_TYPE,REPARAMETERIZATION_RATIO);
		Definition.setContinuityDegree(CONTINUITY_DEGREE);
		Definition.setTessellationLevel(TESSELLATION_LEVEL);
		Definition.setEstimateEpsilon(ESTIMATE_EPSILON);
		Definition.setCurvatureBound(CURVATURE_BOUND);
		// Read input file
		ArrayList<String> fileContent=null;
		try{
			fileContent=new FFileReader(TARGET_FOLDER,TARGET_FILE+".obj").read();
		}catch(Exception e){
			e.printStackTrace();
		}
		// Create mesh
		Mesh mesh=new Mesh(fileContent);
		// Write output files
		OutputObject object=new OutputObject();
		mesh.output(object);
		try{
			object.toPlyFileWithCurvature(DESTINATION_FOLDER,TARGET_FILE+".ply");
			object.toS3dSurfaceFile(DESTINATION_FOLDER,TARGET_FILE+"_surface.s3d");
			object.toS3dCurvatureFile(DESTINATION_FOLDER,TARGET_FILE+"_curvature.s3d");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
