package main.core.algorithm.structure;

import java.util.ArrayList;
import main.Definition;
import main.core.data.output.OutputObject;
import main.core.data.output.OutputObjectHandler;
import main.core.mathematics.element.Point3D;
import main.core.mathematics.element.Vector3D;

public class Grid extends Definition implements OutputObjectHandler{
	protected Vertex[] vertices;
	protected Edge[] edges;
	protected Face[] faces;
	
	public Grid(ArrayList<String> objFileContent){
		ArrayList<Point3D> points=new ArrayList<Point3D>();
		ArrayList<Vector3D> normals=new ArrayList<Vector3D>();
		ArrayList<int[]> facePointIndices=new ArrayList<int[]>();
		ArrayList<int[]> faceNormalIndices=new ArrayList<int[]>();
		ArrayList<Edge> tempEdges=new ArrayList<Edge>();
		for(String line:objFileContent){
			String[] elements=line.split(" ");
			if(elements[0].equals("v")){
				points.add(new Point3D(Double.parseDouble(elements[1]),
						Double.parseDouble(elements[2]),
						Double.parseDouble(elements[3])));
			}else if(elements[0].equals("vn")){
				normals.add(new Vector3D(Double.parseDouble(elements[1]),
						Double.parseDouble(elements[2]),
						Double.parseDouble(elements[3])).nNormalize());
			}else if(elements[0].equals("f")){
				int size=elements.length-1;
				int[] facePointIndex=new int[size];
				int[] faceNormalIndex=new int[size];
				for(int i=0;i<size;i++){
					String[] token=elements[i+1].split("/");
					facePointIndex[i]=Integer.parseInt(token[0])-1;
					faceNormalIndex[i]=Integer.parseInt(token[2])-1;
				}
				facePointIndices.add(facePointIndex);
				faceNormalIndices.add(faceNormalIndex);
			}
		}
		vertices=new Vertex[points.size()];
		faces=new Face[facePointIndices.size()];
		for(int i=0;i<facePointIndices.size();i++){
			int[] facePointIndex=facePointIndices.get(i);
			int[] faceNormalIndex=faceNormalIndices.get(i);
			int size=facePointIndex.length;
			Vertex[] faceVertices=new Vertex[size];
			Edge[] faceEdges=new Edge[size];
			for(int j=0;j<size;j++){
				int pointIndex=facePointIndex[j];
				if(vertices[pointIndex]==null){
					vertices[pointIndex]=new Vertex(points.get(pointIndex),normals.get(faceNormalIndex[j]));
				}
				faceVertices[j]=vertices[pointIndex];
			}
			faceVertices[0].linkOrder(faceVertices[1],faceVertices[size-1]);
			for(int j=1;j<size-1;j++){
				faceVertices[j].linkOrder(faceVertices[j+1],faceVertices[j-1]);
			}
			faceVertices[size-1].linkOrder(faceVertices[0],faceVertices[size-2]);
			for(int j=0;j<size-1;j++){
				Vertex head=faceVertices[j];
				Vertex tail=faceVertices[j+1];
				if(head.existEdge(tail)){
					faceEdges[j]=head.getEdge(tail);
				}else{
					faceEdges[j]=new Edge(head,tail);
					tempEdges.add(faceEdges[j]);
				}
			}
			Vertex head=faceVertices[size-1];
			Vertex tail=faceVertices[0];
			if(head.existEdge(tail)){
				faceEdges[size-1]=head.getEdge(tail);
			}else{
				faceEdges[size-1]=new Edge(head,tail);
				tempEdges.add(faceEdges[size-1]);
			}
			faces[i]=new Face(faceVertices,faceEdges);
		}
		for(Vertex vertex:vertices){
			vertex.buildComponent();
		}
		edges=new Edge[tempEdges.size()];
		for(int i=0;i<tempEdges.size();i++){
			edges[i]=tempEdges.get(i);
		}
		for(Vertex vertex:vertices){
			vertex.createSurface();
		}
		for(Edge edge:edges){
			edge.createSurface();
		}
		for(Face face:faces){
			face.createSurface();
		}
	}
	
	public void output(OutputObject object){
		switch(outputSurfaceType){
			case Vertex:
				for(Vertex vertex:vertices){
					((Vertex)vertex).output(object);
				}
				break;
			case Edge:
				for(Edge edge:edges){
					((Edge)edge).output(object);
				}
				break;
			case Face:
				for(Face face:faces){
					((Face)face).output(object);
				}
				break;
			case Test:
				// TODO
				// Any possible testing code.
				break;
			default:
				break;
		}
	}
}
