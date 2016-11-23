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

public class TestDijkstra {

	UndirectedGraph<AttrVertex, DefaultEdge> undirected;
	Map<String, Integer> undirectedcounts = new HashMap<>();
	
	{
		try {
			undirected =
					(UndirectedGraph<AttrVertex, DefaultEdge>) asCounting(newUndirectedWeightedAttributedReader().read(
							Root.pathToSource + "a2_p04_ar_vb/misc/graph3.gka"), undirectedcounts,
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
				undirectedFinder.apply(undirected, new AttrVertex("Husum"), new AttrVertex("Hamburg"));
		
		System.out.println("Dijkstra undirect");
		System.out.println(undirectedCounter);
		System.out.println(undirectedcounts);
		undirectedcounts.clear();
		undirectedCounter.clear();
		
		System.out.println("undirectedPath: " + undirectedPath);
		System.out.println("undirectedPath.getWeight(): " + undirectedPath.getWeight());
		GraphVizPFinder.erzeugeDot("HusumHamburg",undirected, undirectedPath);
		System.out.println("---------------------------");
		
		
		undirectedPath =
				undirectedFinder.apply(undirected, new AttrVertex("Minden"), new AttrVertex("Hamburg"));
		
		System.out.println("Dijkstra undirect");
		System.out.println(undirectedCounter);
		System.out.println(undirectedcounts);
		undirectedcounts.clear();
		undirectedCounter.clear();
		
		System.out.println("undirectedPath: " + undirectedPath);
		System.out.println("undirectedPath.getWeight(): " + undirectedPath.getWeight());
		System.out.println("---------------------------");
		
		undirectedPath =
				undirectedFinder.apply(undirected, new AttrVertex("Münster"), new AttrVertex("Hamburg"));
		
		System.out.println("Dijkstra undirect");
		System.out.println(undirectedCounter);
		System.out.println(undirectedcounts);
		undirectedcounts.clear();
		undirectedCounter.clear();
		
		System.out.println("undirectedPath: " + undirectedPath);
		System.out.println("undirectedPath.getWeight(): " + undirectedPath.getWeight());
	}
}
