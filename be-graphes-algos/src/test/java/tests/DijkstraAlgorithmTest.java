package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.BinaryPathReader;
import org.insa.graphs.model.io.GraphReader;
import org.insa.graphs.model.io.PathReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DijkstraAlgorithmTest extends algorithmTest {

	// Small graph use for tests

	@After
	public void tearDown() throws Exception {
	}

	
	//Test pour vérifier que le chemin obtenu avec l'algorithme de dijkstra 
	//est le même que celui crée par la méthode createShortestPathFromNodes de la classe Path
	@Test
	public void testDijkstraShortest() {

		for (int i = 0; i < dataShortest.length; i++) {

			DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(dataShortest[i]);
			ShortestPathSolution solution = dijkstra.run();
			Path cheminSolution = solution.getPath();

			Graph graph = solution.getInputData().getGraph();

			if (cheminSolution == null) {

				assertEquals(solution.getStatus(), Status.INFEASIBLE);

			} else {

				// Vérifie chemin valide
				assertTrue(cheminSolution.isValid());
//				assertEquals(cheminSolution.getOrigin(), graph.get(tabOrigin[i]));
//				assertEquals(cheminSolution.getDestination(), graph.get(tabDestination[i]));

				// List of nodes
				ArrayList<Node> nodes = new ArrayList<Node>();
				List<Arc> arcsSolution = cheminSolution.getArcs();
				nodes.add(arcsSolution.get(0).getOrigin());

				for (int j = 0; j < arcsSolution.size(); j++) {
					nodes.add(arcsSolution.get(j).getDestination());
				}

				Path cheminShortest = Path.createShortestPathFromNodes(graph, nodes);

				// Vérifie que les chemins sont identiques (Arcs, taille, temps)
				assertEquals(cheminSolution.getArcs(), cheminShortest.getArcs());
				assertEquals(cheminSolution.getLength(), cheminShortest.getLength(), 1e-6);
				 assertEquals(cheminSolution.getMinimumTravelTime(), cheminShortest.getMinimumTravelTime(), 1e-6);
			}

		}

	}

	//Test pour vérifier que le chemin obtenu avec l'algorithme de dijkstra 
	//est le même que celui crée par la méthode createFastestPathFromNodes de la classe Path
	@Test
	public void testDijkstraFastest() {

		for (int i = 0; i < dataShortest.length; i++) {
			DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(dataFastest[i]);
			ShortestPathSolution solution = dijkstra.run();
			Path cheminSolution = solution.getPath();

			Graph graph = solution.getInputData().getGraph();

			if (cheminSolution == null) {

				assertEquals(solution.getStatus(), Status.INFEASIBLE);

			} else {
				// Vérifie chemin valide
				assertTrue(cheminSolution.isValid());
//				assertEquals(cheminSolution.getOrigin(), graph.get(tabOrigin[i]));
//				assertEquals(cheminSolution.getDestination(), graph.get(tabDestination[i]));

				// List of nodes
				ArrayList<Node> nodes = new ArrayList<Node>();
				List<Arc> arcsSolution = cheminSolution.getArcs();
				nodes.add(arcsSolution.get(0).getOrigin());

				for (int j = 0; j < arcsSolution.size(); j++) {
					nodes.add(arcsSolution.get(j).getDestination());
				}

				Path cheminFastest = Path.createFastestPathFromNodes(graph, nodes);

				// Vérifie que les chemins sont identiques (Arcs, taille, temps)
				assertEquals(cheminSolution.getArcs(), cheminFastest.getArcs());
				assertEquals(cheminSolution.getLength(), cheminFastest.getLength(), 1e-6);
				assertEquals(cheminSolution.getMinimumTravelTime(), cheminFastest.getMinimumTravelTime(), 1e-6);
			}

		}

	}

	//Test pour un chemin entre 2 îles 
	@Test
	public void testDijkstraInfeasable() {

		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(dataInfeasable);
		ShortestPathSolution solution = dijkstra.run();
		Path cheminSolution = solution.getPath();

		// Vérifie qu'il n'y a pas de chemin
		assertEquals(cheminSolution, null);
		assertEquals(solution.getStatus(), Status.INFEASIBLE);

	}
	
	//Test pour un chemin d'un seul noeud
	@Test
	public void testDijkstraOneNode() {

		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(dataOneNode);
		ShortestPathSolution solution = dijkstra.run();
		Path cheminSolution = solution.getPath();

		// Vérifie que le chemin est de longueur nulle, de temps nul et n'a pas d'arc
		assertEquals(solution.getStatus(), Status.OPTIMAL);
		assertEquals(cheminSolution.getLength(), 0f, 1e-6);
		assertEquals(cheminSolution.getMinimumTravelTime(), 0d, 1e-6);
		assertEquals(cheminSolution.getArcs().size(), 0);

	}


}
