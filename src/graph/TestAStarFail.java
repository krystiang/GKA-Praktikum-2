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
import a2_p04_ar_vb.impls.f3.AStarFinder;

public class TestAStarFail {

	UndirectedGraph<AttrVertex, DefaultEdge> undirected;
	Map<String, Integer> undirectedcounts = new HashMap<>();
	
	{
		try {
			undirected =
					(UndirectedGraph<AttrVertex, DefaultEdge>) asCounting(newUndirectedWeightedAttributedReader().read(
							Root.pathToSource + "a2_p04_ar_vb/misc/keinziel.gka"), undirectedcounts,
							UndirectedGraph.class,	WeightedGraph.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	@Test
	public void testAStar() throws IOException {

		PerfCount undirectedCounter = new PerfCount();
		AStarFinder<Graph<AttrVertex, DefaultEdge>, AttrVertex, DefaultEdge> undirectedFinder =
				PathFinders.newAStarPathFinder(undirectedCounter);


		
		GraphPath<AttrVertex, DefaultEdge> undirectedPath =
				undirectedFinder.apply(undirected, new AttrVertex("v1"), new AttrVertex("v11"));
		
		System.out.println("AStar undirect");
		System.out.println(undirectedCounter);
		System.out.println(undirectedcounts);
		undirectedcounts.clear();
		
		System.out.println("undirectedPath: " + undirectedPath);
		System.out.println("undirectedPath.getWeight(): " + undirectedPath.getWeight());
		System.out.println("---------------------------");

	}
}
