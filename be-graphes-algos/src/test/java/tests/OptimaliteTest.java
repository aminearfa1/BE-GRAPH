package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.shortestpath.AStarAlgorithm;
import org.insa.graphs.algorithm.shortestpath.BellmanFordAlgorithm;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.model.Path;
import org.junit.After;
import org.junit.Test;

public class OptimaliteTest extends algorithmTest {

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testShortest() {
		
		ArrayList<Long> tempsDijkstra = new ArrayList<>(),
						tempsAStar = new ArrayList<Long>();
		
		for (int i = 0; i < dataShortest.length; i++) {
			BellmanFordAlgorithm BF = new BellmanFordAlgorithm(dataShortest[i]);
			DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(dataShortest[i]);
			AStarAlgorithm aStar = new AStarAlgorithm(dataShortest[i]);
			
			ShortestPathSolution solutionBF = BF.run(),
								 solutionDijkstra = dijkstra.run(),
								 solutionAStar = aStar.run();
			
			
			Path cheminSolutionBF = solutionBF.getPath(),
				 cheminSolutionDijkstra = solutionDijkstra.getPath(),
				 cheminSolutionAStar = solutionAStar.getPath();
			
			if (cheminSolutionBF == null) {
				assertEquals(solutionBF.getStatus(), Status.INFEASIBLE);
				assertEquals(solutionDijkstra.getStatus(), Status.INFEASIBLE);
				assertEquals(solutionAStar.getStatus(), Status.INFEASIBLE);
			}else {
			
			
				tempsDijkstra.add(solutionDijkstra.getSolvingTime().toMillis());
				tempsAStar.add(solutionAStar.getSolvingTime().toMillis());
				
				assertEquals(cheminSolutionAStar.getArcs(), cheminSolutionBF.getArcs());
				assertEquals(cheminSolutionAStar.getLength(), cheminSolutionBF.getLength(), 1e-6);
				assertEquals(cheminSolutionAStar.getMinimumTravelTime(), cheminSolutionBF.getMinimumTravelTime(), 1e-6);
			
				assertEquals(cheminSolutionDijkstra.getArcs(), cheminSolutionBF.getArcs());
				assertEquals(cheminSolutionDijkstra.getLength(), cheminSolutionBF.getLength(), 1e-6);
				assertEquals(cheminSolutionDijkstra.getMinimumTravelTime(), cheminSolutionBF.getMinimumTravelTime(), 1e-6);
			}
		}
		
		System.out.println("Dijkstra : "+tempsDijkstra.toString());
		System.out.println("A* : "+tempsAStar.toString());
		
	}
	
	@Test
	public void testFastest() {
		
		ArrayList<Long> tempsDijkstra = new ArrayList<>(),
						tempsAStar = new ArrayList<Long>();
		
		for (int i = 0; i < dataFastest.length; i++) {
			BellmanFordAlgorithm BF = new BellmanFordAlgorithm(dataFastest[i]);
			DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(dataFastest[i]);
			AStarAlgorithm aStar = new AStarAlgorithm(dataFastest[i]);
			
			ShortestPathSolution solutionBF = BF.run(),
								 solutionDijkstra = dijkstra.run(),
								 solutionAStar = aStar.run();
			
			Path cheminSolutionBF = solutionBF.getPath(),
				 cheminSolutionDijkstra = solutionDijkstra.getPath(),
				 cheminSolutionAStar = solutionAStar.getPath();
			
			if (cheminSolutionBF == null) {
				assertEquals(solutionBF.getStatus(), Status.INFEASIBLE);
				assertEquals(solutionDijkstra.getStatus(), Status.INFEASIBLE);
				assertEquals(solutionAStar.getStatus(), Status.INFEASIBLE);
			}else {
				
				tempsDijkstra.add(solutionDijkstra.getSolvingTime().toMillis());
				tempsAStar.add(solutionAStar.getSolvingTime().toMillis());
				
				assertEquals(cheminSolutionAStar.getArcs(), cheminSolutionBF.getArcs());
				assertEquals(cheminSolutionAStar.getLength(), cheminSolutionBF.getLength(), 1e-6);
				assertEquals(cheminSolutionAStar.getMinimumTravelTime(), cheminSolutionBF.getMinimumTravelTime(), 1e-6);
			
				assertEquals(cheminSolutionDijkstra.getArcs(), cheminSolutionBF.getArcs());
				assertEquals(cheminSolutionDijkstra.getLength(), cheminSolutionBF.getLength(), 1e-6);
				assertEquals(cheminSolutionDijkstra.getMinimumTravelTime(), cheminSolutionBF.getMinimumTravelTime(), 1e-6);
			}
		}
		
		System.out.println("Dijkstra : "+tempsDijkstra.toString());
		System.out.println("A* : "+tempsAStar.toString());
	}

}
