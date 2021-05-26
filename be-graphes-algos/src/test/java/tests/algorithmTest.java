package tests;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.List;
import java.util.Random;

import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.junit.BeforeClass;

public class algorithmTest {

	protected static Graph graphs[];

	//3 cartes, chacune testée pour les mode "shortest path all roads" et "fastest path all roads"
	// /!\ Chemin des cartes à adapter /!\
	final static String hauteGaronne = "C:\\Users\\arfaa\\OneDrive\\Bureau\\ARFA\\INSA1\\graph_help\\maps\\haute-garonne.mapgr";
	final static String polynesie = "C:\\Users\\arfaa\\OneDrive\\Bureau\\ARFA\\INSA1\\graph_help\\maps\\french-polynesia.mapgr";
	final static String bretagne = "C:\\Users\\arfaa\\OneDrive\\Bureau\\ARFA\\INSA1\\graph_help\\maps\\bretagne.mapgr";

	final static String maps[] = { hauteGaronne, polynesie, bretagne };

	final static int nbRoutesTest = 5; //Variable utilisée pour déterminer le nombre d'itinéraires à tester par carte et par mode
									   //Dans l'état actuel, les tests seront effectués pour nbRoutesTest*3*2 itinéraires

	protected static ShortestPathData dataShortest[];
	protected static ShortestPathData dataFastest[];
	protected static ShortestPathData dataInfeasable, dataOneNode;
	protected static int tabOrigin[], tabDestination[];

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		List<ArcInspector> arcList = ArcInspectorFactory.getAllFilters();
		ArcInspector arcShortestAllRoad = arcList.get(0);
		ArcInspector arcFastestAllRoad = arcList.get(2);

		graphs = new Graph[3];
		
		//Lecture des différentes cartes
		for (int i = 0; i < 3; i++) {
			try (GraphReader reader = new BinaryGraphReader(
					new DataInputStream(new BufferedInputStream(new FileInputStream(maps[i]))))) {
				// TODO: Read the graph.
				graphs[i] = reader.read();
			}

		}

		dataShortest = new ShortestPathData[nbRoutesTest * 3];
		dataFastest = new ShortestPathData[nbRoutesTest * 3];
		tabOrigin = new int[nbRoutesTest * 3];
		tabDestination = new int[nbRoutesTest * 3];

		Random rand = new Random(100l);
		int origine, destination;

		// Pour chaque carte, génère un nombre nbRoutes*2 d'itinéraires (plus court et
		// plus rapide)
		for (int i = 0; i < 3; i++) {

			for (int j = 0; j < nbRoutesTest; j++) {

				origine = rand.nextInt(graphs[i].getNodes().size());
				destination = rand.nextInt(graphs[i].getNodes().size());
				dataShortest[i * nbRoutesTest + j] = new ShortestPathData(graphs[i], graphs[i].get(origine),
						graphs[i].get(destination), arcShortestAllRoad);
				dataFastest[i * nbRoutesTest + j] = new ShortestPathData(graphs[i], graphs[i].get(origine),
						graphs[i].get(destination), arcFastestAllRoad);

			}
		}

		dataInfeasable = new ShortestPathData(graphs[1], graphs[1].get(2984), graphs[1].get(10467), arcFastestAllRoad);
		dataOneNode = new ShortestPathData(graphs[0], graphs[0].get(535), graphs[0].get(535), arcShortestAllRoad);

	}

}
