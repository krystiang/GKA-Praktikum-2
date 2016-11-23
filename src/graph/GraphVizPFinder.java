package a2_p04_ar_vb;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jgrapht.GraphPath;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.ext.ComponentAttributeProvider;
import org.jgrapht.ext.DOTExporter;
import org.jgrapht.ext.EdgeNameProvider;
import org.jgrapht.ext.VertexNameProvider;
import org.jgrapht.graph.DefaultEdge;

import a1_p04_arn_vb.AttrVertex;
import a1_p04_arn_vb.Root;

public class GraphVizPFinder {

	private static String replaceName(String name) {
		name = name.replaceAll("ü", "ue");
		name = name.replaceAll("ö", "oe");
		name = name.replaceAll("ä", "ae");
		return name;
	}

	public static <V, E> void vizIt(String path,
			final UndirectedGraph<AttrVertex, DefaultEdge> graph,
			final GraphPath<AttrVertex, DefaultEdge> graphPath)
			throws IOException {
		DOTExporter<AttrVertex, DefaultEdge> export = new DOTExporter<>(
				new VertexNameProvider<AttrVertex>() {

					@Override
					public String getVertexName(AttrVertex vertex) {
						String name = vertex.toString();
						name = replaceName(name);
						return name + "_" + String.valueOf(vertex.getValue());
					}
				}, new VertexNameProvider<AttrVertex>() {

					@Override
					public String getVertexName(AttrVertex vertex) {
						String name = vertex.toString();
						name = replaceName(name);
						return name + "_" + String.valueOf(vertex.getValue());
					}
				}, new EdgeNameProvider<DefaultEdge>() {

					@Override
					public String getEdgeName(DefaultEdge edge) {
						if (graph instanceof WeightedGraph) {
							double weight = graph.getEdgeWeight(edge);
							return String.valueOf(weight);
						}
						return "";
					}
				}, new ComponentAttributeProvider<AttrVertex>() {
					@Override
					public Map<String, String> getComponentAttributes(
							AttrVertex component) {
						return null;
					}

				}, new ComponentAttributeProvider<DefaultEdge>() {

					@Override
					public Map<String, String> getComponentAttributes(
							DefaultEdge component) {
						Map<String, String> hm1 = new HashMap<String, String>();
						if (!(graphPath.getEdgeList().contains(component)))
							hm1.put("style", "dotted");
						return hm1;
					}

				}

		);

		export.export(new BufferedWriter(new FileWriter(path + ".dot")),
				graph);
	}

	public static <E> void erzeugeDot(String name,
			UndirectedGraph<AttrVertex, DefaultEdge> graph,
			GraphPath<AttrVertex, DefaultEdge> graphPath) throws IOException {
		vizIt(Root.pathToSource + "a2_p04_ar_vb/misc/" + name, graph, graphPath);
	}
	
	public static void main(String[] args){
		//vizIt(Root.pathToSource + "a2_p04_ar_vb/misc/", );
	}

}
