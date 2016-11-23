package a2_p04_ar_vb;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.jgrapht.VertexFactory;
import org.jgrapht.WeightedGraph;
import org.jgrapht.generate.RandomGraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import a1_p04_arn_vb.AttrVertex;
import a1_p04_arn_vb.Root;

public class GraphCreator {

	public static void main(String[] args) {
		File datei = null;
		int countVertex;
		int countEdges;
		int maxEdges;
		boolean ok = true;
		WeightedGraph<AttrVertex, DefaultEdge> graph = new SimpleWeightedGraph<AttrVertex, DefaultEdge>(
				DefaultWeightedEdge.class);

		Scanner sc = new Scanner(System.in);
		while (ok) {
			System.out.print("Wie soll Datei Name heißen: ");
			datei = new File(Root.pathToSource + "a2_p04_ar_vb/misc/"
					+ sc.nextLine() + ".gka");
			if (datei.exists()) {
				System.out.println("Datei existiert schon");
			} else {
				try {
					datei.createNewFile();
					System.out.println("Datei erzeugt");
				} catch (IOException e) {
					e.printStackTrace();
				}
				ok = false;
			}
		}
		System.out.print("Wie viele Knoten soll es haben?: ");
		countVertex = sc.nextInt();
		maxEdges = (countVertex * (countVertex - 1)) / 2;
		System.out.print("Wie viele Kanten soll es haben?(maximal " + maxEdges
				+ ") : ");
		countEdges = sc.nextInt();
		buildGraph(graph, countVertex, countEdges);

		sc.close();
		GraphSave.GraphWriter(graph, datei);
		System.out.println("Datei Beschrieben");
		try {
			GraphVizwithAttr.vizIt(datei.toString().replace(".gka", ""), graph);
			System.out.println("Dot erzeugt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static WeightedGraph<AttrVertex, DefaultEdge> buildGraph(
			WeightedGraph<AttrVertex, DefaultEdge> graph,
			final int countVertex, int countEdges) {
		RandomGraphGenerator<AttrVertex, DefaultEdge> randomGenerator = new RandomGraphGenerator<AttrVertex, DefaultEdge>(
				countVertex, countEdges);
		VertexFactory<AttrVertex> vertexFactory = new VertexFactory<AttrVertex>() {
			int i = 0;
			int randomNumber;

			public AttrVertex createVertex() {
				i++;
				String iName = "v" + String.valueOf(i);
				if (i == countVertex)
					randomNumber = 0; // zielknoten
				else
					randomNumber = (int) (Math.random() * (100 - 5) + 5);
				return new AttrVertex(iName, randomNumber);
			}
		};
		randomGenerator.generateGraph(graph, vertexFactory, null);
		for (DefaultEdge edge : graph.edgeSet()) {
			int differenceValue = 0;
			int sourceValue = graph.getEdgeSource(edge).getValue();
			int targetValue = graph.getEdgeTarget(edge).getValue();
			differenceValue = Math.abs(sourceValue - targetValue)
					+ (int) (Math.random() * (25 - 5) + 5);
			;
			graph.setEdgeWeight(edge, differenceValue);
		}
		return graph;
	}
}
