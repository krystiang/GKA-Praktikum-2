package a2_p04_ar_vb.test;

import static a1_p04_arn_vb.GkaGraphReaders.newUndirectedWeightedAttributedReader;
import static a1_p04_arn_vb.util.CountingProxys.asCounting;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Test;

import a1_p04_arn_vb.AttrVertex;
import a1_p04_arn_vb.PathFinders;
import a1_p04_arn_vb.Root;
import a1_p04_arn_vb.test.PerfCount;
import a2_p04_ar_vb.GraphVizPFinder;
import a2_p04_ar_vb.impls.f3.AStarFinder;
import a2_p04_ar_vb.impls.f3.DijkstraFinder;
/*
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.UnmodifiableDirectedGraph;
import org.jgrapht.graph.UnmodifiableUndirectedGraph;
*/

public class TestRandom {

	UndirectedGraph<AttrVertex, DefaultEdge> undirected;
	Map<String, Integer> undirectedcounts = new HashMap<>();
	
	{
		try {
			undirected =
					(UndirectedGraph<AttrVertex, DefaultEdge>) asCounting(newUndirectedWeightedAttributedReader().read(
							Root.pathToSource + "a2_p04_ar_vb/misc/UnitTestGraph.gka"), undirectedcounts,
							UndirectedGraph.class,	WeightedGraph.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	@Test
	public void testDijkstra() throws IOException {
		
		PerfCount undirectedCounter = new PerfCount();
		DijkstraFinder<Graph<AttrVertex, DefaultEdge>, AttrVertex, DefaultEdge> undirectedFinder =
				PathFinders.newDijkstraPathFinder(undirectedCounter);


		
		GraphPath<AttrVertex, DefaultEdge> undirectedPath =
				undirectedFinder.apply(undirected, new AttrVertex("v14"), new AttrVertex("v20"));
		
		System.out.println("Dijkstra undirect");
		System.out.println(undirectedCounter);
		System.out.println(undirectedcounts);
		undirectedcounts.clear();
		undirectedCounter.clear();
		
		System.out.println("undirectedPath: " + undirectedPath);
		System.out.println("undirectedPath.getWeight(): " + undirectedPath.getWeight());
		GraphVizPFinder.erzeugeDot("UnitTestGraphDijk",undirected, undirectedPath);
		System.out.println("---------------------------");
		
		
		AStarFinder<Graph<AttrVertex, DefaultEdge>, AttrVertex, DefaultEdge> astarundirectedFinder =
				PathFinders.newAStarPathFinder(undirectedCounter);


		
		 undirectedPath =
				 astarundirectedFinder.apply(undirected, new AttrVertex("v14"), new AttrVertex("v20"));
		
		System.out.println("AStern undirect");
		System.out.println(undirectedCounter);
		System.out.println(undirectedcounts);
		undirectedcounts.clear();
		undirectedCounter.clear();
		
		System.out.println("undirectedPath: " + undirectedPath);
		System.out.println("undirectedPath.getWeight(): " + undirectedPath.getWeight());
		GraphVizPFinder.erzeugeDot("UnitTestGraphAstar",undirected, undirectedPath);
		System.out.println("---------------------------");
	}
}
