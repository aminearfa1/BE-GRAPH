package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.shortestpath.AStarAlgorithm;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.junit.After;
import org.junit.Test;

public class AStarAlgorithmTest extends algorithmTest{

	@After
	public void tearDown() throws Exception {
	}

	//Test pour vérifier que le chemin obtenu avec l'algorithme A* 
	//est le même que celui crée par la méthode createShortestPathFromNodes de la classe Path
	@Test
	public void testAStarShortest() {
		
		for(int i=0; i<dataShortest.length; i++) {
			AStarAlgorithm aStar = new AStarAlgorithm(dataShortest[i]);
			ShortestPathSolution solution = aStar.run();
			Path cheminSolution = solution.getPath();
			
			Graph graph = solution.getInputData().getGraph();
			 
			if (cheminSolution == null) {

				assertEquals(solution.getStatus(), Status.INFEASIBLE);

			} else {
				//Vérifie chemin valide
				assertTrue(cheminSolution.isValid());
//				assertEquals(cheminSolution.getOrigin(), graph.get(tabOrigin[i]));
//				assertEquals(cheminSolution.getDestination(), graph.get(tabDestination[i]));
				   
				 // List of nodes
				 ArrayList<Node> nodes = new ArrayList<Node>();
				 List<Arc> arcsSolution = cheminSolution.getArcs();
				 nodes.add(arcsSolution.get(0).getOrigin());
				 
				 for(int j=0;j<arcsSolution.size(); j++) {
			        nodes.add(arcsSolution.get(j).getDestination());	
			     }
				 
				 Path cheminShortest = Path.createShortestPathFromNodes(graph, nodes);
				 
				 //Vérifie que les chemins sont identiques (Arcs, taille, temps)
				 assertEquals(cheminSolution.getArcs(), cheminShortest.getArcs());
				 assertEquals(cheminSolution.getLength(), cheminShortest.getLength(), 1e-6);
				 assertEquals(cheminSolution.getMinimumTravelTime(), cheminShortest.getMinimumTravelTime(), 1e-6);
			}
		}
	}
	
	
	//Test pour vérifier que le chemin obtenu avec l'algorithme A* 
	//est le même que celui crée par la méthode createFastestPathFromNodes de la classe Path
	@Test
	public void testAStarFastest() {
		
		for(int i=0; i<dataShortest.length; i++) {
			AStarAlgorithm aStar = new AStarAlgorithm(dataFastest[i]);
			ShortestPathSolution solution = aStar.run();
			Path cheminSolution = solution.getPath();
			
			Graph graph = solution.getInputData().getGraph();
			 
			if (cheminSolution == null) {

				assertEquals(solution.getStatus(), Status.INFEASIBLE);

			} else {
				//Vérifie chemin valide
				assertTrue(cheminSolution.isValid());
//				assertEquals(cheminSolution.getOrigin(), graph.get(tabOrigin[i]));
//				assertEquals(cheminSolution.getDestination(), graph.get(tabDestination[i]));
				   
				 // List of nodes
				 ArrayList<Node> nodes = new ArrayList<Node>();
				 List<Arc> arcsSolution = cheminSolution.getArcs();
				 nodes.add(arcsSolution.get(0).getOrigin());
				 
				 for(int j=0;j<arcsSolution.size(); j++) {
			        nodes.add(arcsSolution.get(j).getDestination());	
			     }
				 
				 Path cheminFastest = Path.createFastestPathFromNodes(graph, nodes);
				 
				 //Vérifie que les chemins sont identiques (Arcs, taille, temps)
				 assertEquals(cheminSolution.getArcs(), cheminFastest.getArcs());
				 assertEquals(cheminSolution.getLength(), cheminFastest.getLength(), 1e-6);
				 assertEquals(cheminSolution.getMinimumTravelTime(), cheminFastest.getMinimumTravelTime(), 1e-6);
				
			}
			 
		}
	}
	
	
	//Test pour un chemin entre 2 îles 
	@Test
	public void testAStarInfeasable() {
		
		 AStarAlgorithm aStar = new AStarAlgorithm(dataInfeasable);
		 ShortestPathSolution solution =  aStar.run();
		 Path cheminSolution = solution.getPath();
		 
		 //Vérifie chemin valide
		 assertEquals(cheminSolution, null);
		 assertEquals(solution.getStatus(), Status.INFEASIBLE);
		
	}
	
	
	//Test pour un chemin d'un seul noeud
	@Test
	public void testAStarOneNode() {

		AStarAlgorithm aStar = new AStarAlgorithm(dataOneNode);
		ShortestPathSolution solution = aStar.run();
		Path cheminSolution = solution.getPath();

		// Vérifie que le chemin est de longueur nulle, de temps nul et n'a pas d'arc
		assertEquals(solution.getStatus(), Status.OPTIMAL);
		assertEquals(cheminSolution.getLength(), 0f, 0.1e-6);
		assertEquals(cheminSolution.getMinimumTravelTime(), 0d, 1e-6);
		assertEquals(cheminSolution.getArcs().size(), 0);

	}
	
	

}
