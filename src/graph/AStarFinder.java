package a2_p04_ar_vb.impls.f3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.graph.GraphPathImpl;

import a1_p04_arn_vb.AttrVertex;

/**
 * 
 * @author
 * 
 * @param <G>
 *            der Typ des Graph
 * @param <V>
 *            der Typ der Vertexe
 * @param <E>
 *            der Typ der Edges
 */
public class AStarFinder<G extends Graph<AttrVertex, E>, V, E> {

	/**
	 * listener ist ein TraversalListener der nach den Events horcht die von
	 * einem GraphIterator emitiert werden
	 */
	private final TraversalListener<V, E> listener;

	public AStarFinder(TraversalListener<V, E> listener) {
		this.listener = listener;
	}

	/**
	 * 
	 * @param graph
	 *            der Graph
	 * @param start
	 *            startvertex
	 * @param end
	 *            zielvertex
	 * 
	 * @return ein Graphpath
	 */
	public GraphPath<AttrVertex, E> apply(G graph, AttrVertex start,
			AttrVertex end) {

		if (!(graph.containsVertex(start) && graph.containsVertex(end))) {
			throw new IllegalArgumentException(
					"start or end ist not contain im graph");
		}

		if (start.equals(end)) {
			throw new IllegalArgumentException(
					"start and end should not be equal");
		}

		List<AttrVertex> openlist = new ArrayList<AttrVertex>();
		Map<AttrVertex, Double> gValue = new HashMap<AttrVertex, Double>();
		Map<AttrVertex, Double> fValue = new HashMap<AttrVertex, Double>();
		Map<AttrVertex, AttrVertex> predecessor = new HashMap<AttrVertex, AttrVertex>();

		// openlist initialisieren
		for (AttrVertex vertex : graph.vertexSet()) {
			listener.vertexTraversed(null);
			openlist.add(vertex);
			gValue.put(vertex, start.equals(vertex) ? 0.0
					: Double.POSITIVE_INFINITY);
			fValue.put(vertex, start.equals(vertex) ? vertex.getValue()
					: Double.POSITIVE_INFINITY);
			predecessor.put(vertex, start.equals(vertex) ? vertex : null);

		}

		AttrVertex index = start;

		// while (!openlist.isEmpty() && (predecessor.get(end) == null)) {
		while (!openlist.isEmpty() && !(index.equals(end))) {
			// while (!openlist.isEmpty() && openlist.contains(end)) {
			// kleinsten holen
			double smallf = Double.POSITIVE_INFINITY;

			AttrVertex currentVertex = openlist.get(openlist.indexOf(index));
			double currentDistance = gValue.get(currentVertex);

			// alle Kanten durchgehen und die Kanten raussuchen für
			// ungerichteten Graph
			for (E edge : graph.edgeSet()) {

				listener.edgeTraversed(null);

				if (graph instanceof DirectedGraph) {
					if (graph.getEdgeSource(edge).equals(currentVertex)) {
						double targetDistance = currentDistance
								+ graph.getEdgeWeight(edge);
						AttrVertex targetVertex = graph.getEdgeTarget(edge);

						if (targetVertex.equals(currentVertex))
							targetVertex = graph.getEdgeSource(edge);

						// neue g-Wert und f-wert speichern, falls die
						// gespeicherte
						// Distanz größer war
						// if ((fValue.get(targetVertex)) > (targetDistance +
						// currentHeuristik)) {
						if (openlist.contains(targetVertex)
								&& gValue.get(targetVertex) > targetDistance) {
							gValue.put(targetVertex, targetDistance);
							fValue.put(targetVertex, targetDistance
									+ targetVertex.getValue());
							predecessor.put(targetVertex, currentVertex);
						}

					}
				} else {
					if (graph.getEdgeSource(edge).equals(currentVertex)
							|| graph.getEdgeTarget(edge).equals(currentVertex)) {
						double targetDistance = currentDistance
								+ graph.getEdgeWeight(edge);
						AttrVertex targetVertex = graph.getEdgeTarget(edge);

						if (targetVertex.equals(currentVertex))
							targetVertex = graph.getEdgeSource(edge);

						// neue g-Wert und f-wert speichern, falls die
						// gespeicherte
						// Distanz größer war
						// if ((fValue.get(targetVertex)) > (targetDistance +
						// currentHeuristik)) {
						if (openlist.contains(targetVertex)
								&& gValue.get(targetVertex) > targetDistance) {
							gValue.put(targetVertex, targetDistance);
							fValue.put(targetVertex, targetDistance
									+ targetVertex.getValue());
							predecessor.put(targetVertex, currentVertex);
						}

					}
				}
			}
			// "Knoten als bearbeitet markieren"
			openlist.remove(currentVertex);
			// Kleinsten f-wert suchen
			for (Entry<AttrVertex, Double> wert : fValue.entrySet()) {
				if (wert.getValue() < smallf
						&& openlist.contains(wert.getKey())) {
					index = wert.getKey();
					smallf = wert.getValue();
				}
			}
			// System.out.println("currentVertex " + currentVertex);
			// System.out.println("index " + index);
			// falls keiner weitere nachbarn übrig ist
			if (index.equals(currentVertex)) {
				predecessor.clear();
				openlist.clear();
			}
		}

		// Pfad bauen

		AttrVertex nextCurrentVertex = end;
		AttrVertex currentVertex = nextCurrentVertex;
		double weight = 0;
		List<E> path = new ArrayList<E>();

		// predecessor.get(currentVertex);
		// falls ziel knoten nie erreicht wurde
		// wird eine leere liste zurück gegeben.
		if (predecessor.get(currentVertex) != null) {
			while (nextCurrentVertex != null
					&& !nextCurrentVertex.equals(start)) {
				currentVertex = nextCurrentVertex;
				nextCurrentVertex = predecessor.get(currentVertex);
				path.add(graph.getEdge(nextCurrentVertex, currentVertex));
			}

			Collections.reverse(path);
			weight = fValue.get(end);
		}

		return new GraphPathImpl<AttrVertex, E>(graph, start, end, path, weight);
	}
}
