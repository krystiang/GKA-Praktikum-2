package a2_p04_ar_vb;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.ext.DOTExporter;
import org.jgrapht.ext.EdgeNameProvider;
import org.jgrapht.ext.VertexNameProvider;

import a1_p04_arn_vb.AttrVertex;
import a1_p04_arn_vb.GkaGraphReaders;

public class GraphVizwithAttr {
	
	private static String replaceName(String name){
		name=name.replaceAll("ü", "ue");
		name=name.replaceAll("ö", "oe");
		name=name.replaceAll("ä", "ae");
		return name;
	}

	public static <V, E> void vizIt(String path, final Graph<AttrVertex, E> graph)
			throws IOException {
		DOTExporter<AttrVertex, E> export =
				new DOTExporter<>(new VertexNameProvider<AttrVertex>() {

					@Override
					public String getVertexName(AttrVertex vertex) {
						String name = vertex.toString();
                        name = replaceName(name);
						return name + "_" + String.valueOf(vertex.getValue());
						//return vertex.toString();
					}
				}, new VertexNameProvider<AttrVertex>() {

					@Override
					public String getVertexName(AttrVertex vertex) {
						String name = vertex.toString();
						name = replaceName(name);
						return name + "_" + String.valueOf(vertex.getValue());
						//return vertex.toString();
					}
				}, 
						  new EdgeNameProvider<E>() {
						  
						  @Override 
						  public String getEdgeName(E edge) { 
						 if(graph instanceof WeightedGraph){
						  double weight = graph.getEdgeWeight(edge);
						  return String.valueOf(weight);
						 }
						 return "";
						  //return edge.toString(); 
							  } }

						 );

		export.export(new BufferedWriter(new FileWriter(path + ".dot")), graph);
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
//		vizIt("src/a1_p04_arn_vb/misc/graph1",
//				GkaGraphReaders.newDirectedReader().read("src/a1_p04_arn_vb/misc/graph1.gka"));
//		vizIt("src/a1_p04_arn_vb/misc/graph2",
//				GkaGraphReaders.newUndirectedReader().read("src/a1_p04_arn_vb/misc/graph2.gka"));
//		vizIt("src/a1_p04_arn_vb/misc/graph3",
//				GkaGraphReaders.newUndirectedReader().read("src/a1_p04_arn_vb/misc/graph3.gka"));
//		vizIt("src/a1_p04_arn_vb/misc/graph4",
//				GkaGraphReaders.newDirectedReader().read("src/a1_p04_arn_vb/misc/graph4.gka"));
//		vizIt("src/a1_p04_arn_vb/misc/k5g",
//				GkaGraphReaders.newDirectedReader().read("src/a1_p04_arn_vb/misc/k5g.gka"));
//		vizIt("src/a1_p04_arn_vb/misc/k5u",
//				GkaGraphReaders.newUndirectedReader().read("src/a1_p04_arn_vb/misc/k5u.gka"));
		vizIt("src/a1_p04_arn_vb/misc/graph3",
				GkaGraphReaders.newUndirectedWeightedAttributedReader().read("src/a1_p04_arn_vb/misc/graph3.gka"));
	}

}
