package main;

import java.util.ArrayList;
import main.Definition.SurfaceOutputType;
import main.core.algorithm.structure.Grid;
import main.core.data.file.FFileReader;
import main.core.data.output.OutputObject;

public class Main{
	// Input and output
	private final static String TARGET_FILE="Mixed";
	private final static String TARGET_FOLDER=System.getProperty("user.home")+"/Desktop/Test/Input";
	private final static String DESTINATION_FOLDER=System.getProperty("user.home")+"/Desktop/Test/Output";
	
	// Output parameters
	private final static SurfaceOutputType SURFACE_TYPE=SurfaceOutputType.Face;
	private final static int CONTINUITY_DEGREE=2;
	private final static int TESSELLATION_LEVEL=25;
	private final static double ESTIMATE_EPSILON=0.0001;
	private final static double CURVATURE_BOUND=1;
	
	public static void main(String[] args){
		// Parameter Setting
		Definition.setOutputType(SURFACE_TYPE);
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
		
		// Create Grid
		Grid grid=new Grid(fileContent);
		
		// Write output files
		OutputObject object=new OutputObject();
		grid.output(object);
		try{
			object.toPlyFileWithCurvature(DESTINATION_FOLDER,TARGET_FILE+".ply");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
