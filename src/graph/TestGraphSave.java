package a2_p04_ar_vb.test;

import static a1_p04_arn_vb.GkaGraphReaders.newUndirectedAttributedReader;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.IOException;

import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.VertexFactory;
import org.jgrapht.generate.CompleteGraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.junit.Test;

import a1_p04_arn_vb.AttrVertex;
import a1_p04_arn_vb.Root;
import a1_p04_arn_vb.util.GkaGraphReader;
import a2_p04_ar_vb.GraphSave;

public class TestGraphSave {
	
	UndirectedGraph<AttrVertex, DefaultEdge> g1 = 
			new SimpleGraph<AttrVertex, DefaultEdge>(DefaultEdge.class);
	


	@Test
	public void testSave() throws IOException {
		buildGraph(g1, 5);
		File datei = new File(Root.pathToSource + "a2_p04_ar_vb/misc/testSave.gka");
		GraphSave.GraphWriter(g1, datei);
		
		GkaGraphReader<UndirectedGraph<AttrVertex, DefaultEdge>> reader =
				newUndirectedAttributedReader();
		UndirectedGraph<AttrVertex, DefaultEdge> graph =
				reader.read(Root.pathToSource + "a2_p04_ar_vb/misc/testSave.gka");
		for(DefaultEdge edge : graph.edgeSet() ){
			assertFalse("Fehler bei Kante " + edge.toString(), g1.containsEdge(edge));	
		}
		
	}

	

	private void buildGraph(Graph<AttrVertex,DefaultEdge> graph, int n) {
		CompleteGraphGenerator<AttrVertex, DefaultEdge> completeGenerator = 
			new CompleteGraphGenerator<AttrVertex, DefaultEdge>(n);
		VertexFactory<AttrVertex> vertexFactory = new VertexFactory<AttrVertex>() {
			private int i = 0;
			public AttrVertex createVertex() {
				i++;
				String iName = String.valueOf(i);
				return new AttrVertex(iName);
			}
		};
		completeGenerator.generateGraph(graph, vertexFactory, null);
	}
	

}
