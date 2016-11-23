package a2_p04_ar_vb;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultEdge;

import a1_p04_arn_vb.AttrVertex;

public class GraphSave {
	
	public static void GraphWriter(Graph<AttrVertex,DefaultEdge> graph,File datei){
		FileWriter writer;
		try {
			writer = new FileWriter(datei ,true);
			if(graph instanceof UndirectedGraph){
				writer.write("#ungerichtet\n");
			} else
			    writer.write("#gerichtet\n");
			if(graph instanceof WeightedGraph){
				writer.write("#attributiert,gewichtet\n");
			} else
				writer.write("#attributiert\n");
			
			for (DefaultEdge edge : graph.edgeSet()) {
				String sourceName = graph.getEdgeSource(edge).getName();
				int sourceValue = graph.getEdgeSource(edge).getValue();
				String targetName = graph.getEdgeTarget(edge).getName();
				int targetValue = graph.getEdgeTarget(edge).getValue();
				if(graph instanceof WeightedGraph){
				    double weight = graph.getEdgeWeight(edge);
				    writer.write(sourceName + "," + sourceValue + "," + targetName + "," + targetValue + "," + weight +"\n");
				} else {
				    writer.write(sourceName + "," + sourceValue + "," + targetName + "," + targetValue +"\n");
				}
			}
		       writer.flush();
		       writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
	}

}
