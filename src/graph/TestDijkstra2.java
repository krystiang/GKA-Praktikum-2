package a2_p04_ar_vb.test;


import static a1_p04_arn_vb.GkaGraphReaders.newUndirectedWeightedReader;
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
import a2_p04_ar_vb.impls.f3.DijkstraFinder;

public class TestDijkstra2 {

	UndirectedGraph<AttrVertex, DefaultEdge> undirected2;
	UndirectedGraph<AttrVertex, DefaultEdge> undirected3;
	UndirectedGraph<AttrVertex, DefaultEdge> undirected4;
	Map<String, Integer> undirectedcounts = new HashMap<>();
	
	{
		try {
			undirected2 =
					(UndirectedGraph<AttrVertex, DefaultEdge>) asCounting(newUndirectedWeightedReader().read(
							Root.pathToSource + "a2_p04_ar_vb/misc/graphDijk.gka"), undirectedcounts,
							UndirectedGraph.class,	WeightedGraph.class);
			undirected3 =
					(UndirectedGraph<AttrVertex, DefaultEdge>) asCounting(newUndirectedWeightedReader().read(
							Root.pathToSource + "a2_p04_ar_vb/misc/graphDijk2.gka"), undirectedcounts,
							UndirectedGraph.class,	WeightedGraph.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	@Test
	public void testDijkstra() throws IOException {
		//@formatter:off

		PerfCount undirectedCounter = new PerfCount();
		DijkstraFinder<Graph<AttrVertex, DefaultEdge>, AttrVertex, DefaultEdge> undirectedFinder =
				PathFinders.newDijkstraPathFinder(undirectedCounter);
		//@formatter:on

		GraphPath<AttrVertex, DefaultEdge> undirectedPath =
				undirectedFinder.apply(undirected2, new AttrVertex("1"), new AttrVertex("7"));
		
		System.out.println("tDijkstra undirect");
		System.out.println(undirectedCounter);
		System.out.println(undirectedcounts);
		undirectedcounts.clear();
		undirectedCounter.clear();
		
		System.out.println("undirectedPath: " + undirectedPath);
		System.out.println("undirectedPath.getWeight(): " + undirectedPath.getWeight());
		System.out.println("---------------------------");
		
		undirectedPath =
		undirectedFinder.apply(undirected3, new AttrVertex("A"), new AttrVertex("X"));
		
		
		System.out.println("Dijkstra undirect");
		System.out.println(undirectedCounter);
		System.out.println(undirectedcounts);
		undirectedcounts.clear();
		undirectedCounter.clear();
		
		System.out.println("undirectedPath: " + undirectedPath);
		System.out.println("undirectedPath.getWeight(): " + undirectedPath.getWeight());
		GraphVizPFinder.erzeugeDot("graphDijk2Path",undirected3, undirectedPath);
	}
}
