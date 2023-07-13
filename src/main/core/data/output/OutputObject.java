 package main.core.data.output;

import java.util.ArrayList;
import main.Definition;
import main.core.algorithm.surface.ElementSurface;
import main.core.data.file.FFileWriter;
import main.core.mathematics.element.Point3D;
import main.core.mathematics.element.Vector3D;

public class OutputObject extends Definition{
	private ArrayList<ElementSurface> surfaces;
	private FFileWriter writer;
	
	public OutputObject(){
		surfaces=new ArrayList<ElementSurface>();
	}
	
	public void addSurface(ElementSurface surface){
		surfaces.add(surface);
	}
	
	public void toPlyFileWithCurvature(String filePath,String fileName)throws Exception{
		writer=new FFileWriter(filePath,fileName);
		int numberOfVertices=0;
		int numberOfFaces=0;
		int shift=0;
		for(ElementSurface surface:surfaces){
			surface.tessellation();
			numberOfVertices=numberOfVertices+surface.getPoints().size();
			numberOfFaces=numberOfFaces+surface.getIndices().size();
		}
		for(String line:PLY_HEADERS_0){
			writer.write(line);
		}
		writer.write(PLY_HEADER_0+" "+numberOfVertices);
		for(String line:PLY_HEADERS_1){
			writer.write(line);
		}
		writer.write(PLY_HEADER_1+" "+numberOfFaces);
		for(String line:PLY_HEADERS_2){
			writer.write(line);
		}
		for(ElementSurface surface:surfaces){
			ArrayList<Point3D> points=surface.getPoints();
			ArrayList<Vector3D> normals=surface.getNormals();
			ArrayList<Double> curvatures=surface.getCurvatures();
			for(int i=0;i<points.size();i++){
				int ratio=(int)Math.round(256*curvatures.get(i)/curvatureBound);
				if(ratio>=0){
					ratio=Math.min(ratio,256);
					writer.write(points.get(i).toString()+" "+normals.get(i).toString()+" "+ratio+" "+(256-ratio)+" 0");
				}else{
					ratio=Math.min(-ratio,256);
					writer.write(points.get(i).toString()+" "+normals.get(i).toString()+" 0 "+(256-ratio)+" "+ratio);
				}
			}
		}
		for(ElementSurface surface:surfaces){
			ArrayList<Point3D> points=surface.getPoints();
			ArrayList<int[]> indices=surface.getIndices();
			for(int i=0;i<indices.size();i++){
				int[] faceIndices=indices.get(i);
				String line=Integer.toString(faceIndices.length);
				for(int j:faceIndices){
					line=line+" "+(j+shift);
				}
				writer.write(line);
			}
			shift=shift+points.size();
		}
		writer.close();
	}
	
	public void toS3dSurfaceFile(String filePath,String fileName)throws Exception{
		writer=new FFileWriter(filePath,fileName);
		for(ElementSurface surface:surfaces){
			surface.tessellation();
			ArrayList<Point3D> points=surface.getPoints();
			ArrayList<Vector3D> normals=surface.getNormals();
			ArrayList<int[]> indices=surface.getIndices();
			for(int i=0;i<indices.size();i++){
				int[] faceIndices=indices.get(i);
				writer.write("P "+faceIndices.length+" 0 0");
				for(int j=0;j<faceIndices.length;j++){
					int k=faceIndices[j];
					writer.write("n "+normals.get(k).toString());
					writer.write("v "+points.get(k).toString());
				}
				writer.write("E 0 0 0");
			}
		}
		writer.close();
	}
	
	public void toS3dCurvatureFile(String filePath,String fileName)throws Exception{
		writer=new FFileWriter(filePath,fileName);
		for(ElementSurface surface:surfaces){
			surface.tessellation();
			ArrayList<Point3D> points=surface.getPoints();
			ArrayList<Vector3D> normals=surface.getNormals();
			ArrayList<Double> curvatures=surface.getCurvatures();
			ArrayList<String> rgbs=new ArrayList<String>();
			for(double curvature:curvatures){
				if(curvature>=0){
					curvature=Math.min(curvature,curvatureBound);
					rgbs.add(FORMAT.format(curvature/curvatureBound)+" "+FORMAT.format(1-curvature/curvatureBound)+" 0");
				}else{
					curvature=Math.min(-curvature,curvatureBound);
					rgbs.add("0 "+FORMAT.format(1-curvature/curvatureBound)+" "+FORMAT.format(curvature/curvatureBound));
				}
			}
			ArrayList<int[]> indices=surface.getIndices();
			for(int i=0;i<indices.size();i++){
				int[] faceIndices=indices.get(i);
				writer.write("P "+faceIndices.length+" 0 0");
				for(int j=0;j<faceIndices.length;j++){
					int k=faceIndices[j];
					writer.write("n "+normals.get(k).toString());
					writer.write("d "+rgbs.get(k));
					writer.write("v "+points.get(k).toString());
				}
				writer.write("E 0 0 0");
			}
		}
		writer.close();
	}
}
