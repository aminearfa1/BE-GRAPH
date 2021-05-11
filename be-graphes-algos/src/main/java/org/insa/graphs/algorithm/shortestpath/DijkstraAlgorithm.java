package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

	public DijkstraAlgorithm(ShortestPathData data) {
		super(data);
	}

	@Override
	protected ShortestPathSolution doRun() {
		final ShortestPathData data = getInputData();
		Graph graph = data.getGraph();

		final int nbNodes = graph.size();

		// Create the heap and a static array to store the labels
		BinaryHeap<Label> tas = new BinaryHeap<Label>();
		Label labels[] = new Label[graph.getNodes().size()];
		Arrays.fill(labels, null);
		
		Label origine = createLabelOrigin(data);
		labels[data.getOrigin().getId()] = origine;
		tas.insert(origine);
		
		Label minimum, successeur;
		
		int cptErreurs = 0;
		
		//Tant que le tas n'est pas vide et que la destination n'est pas atteinte ou n'est pas marquée
		while(!tas.isEmpty() && 
			(labels[data.getDestination().getId()] == null || !labels[data.getDestination().getId()].isMarked() ) ) 
		{
			
			if(!tas.isValid()) {
				cptErreurs++;
			}
			
			//Récupère le minimum (= racine)
			minimum = tas.findMin();
			
			if(minimum.getCurrent() == data.getOrigin()) {
				notifyOriginProcessed(data.getOrigin());
			}
			
			if(minimum.getCurrent() == data.getDestination()) {
				notifyDestinationReached(data.getDestination());
			}
			
			//Supprime le minimum
			try {
				tas.remove(minimum);
			}catch(Exception e) {
				System.out.println("fatal error");
			}
			
			minimum.setMark();
			notifyNodeMarked(minimum.getCurrent());
			
//			System.out.println(minimum.getTotalCost());
			
			
//			System.out.println(minimum.getCurrent().getNumberOfSuccessors());
			
			//Pour chaque successeurs du noeud minimum
			for(Arc arc : minimum.getCurrent().getSuccessors()) {
				
				if(!data.isAllowed(arc)) {
					continue;
				}
				
				
				successeur = labels[arc.getDestination().getId()];
				
				//Si le successeur n'existe pas, on le crée
				if(successeur == null) {
					successeur = this.createLabel(data, arc, minimum);
					labels[arc.getDestination().getId()] = successeur;
					tas.insert(successeur);
					
					notifyNodeReached(successeur.getCurrent());
				
				//Sinon on regarde si on peut optimiser le coût du successeur
				}else{
					if(successeur.getCost() > minimum.getCost() + data.getCost(arc)) {
						successeur.setCost(minimum.getCost() + data.getCost(arc));
						successeur.setFather(arc);
						
						try {
							tas.miseAJour(successeur);
						}catch(Exception e) {
							
						}
					}
					
				}
				
			}
		}
		

		ShortestPathSolution solution = null;
		
		//Regarde si la destination a été atteinte et marquée
		if(labels[data.getDestination().getId()] != null && labels[data.getDestination().getId()].isMarked()) {
			
			//On récupère tous les arcs constituant le chemin
			//en partant de la destination et en remontant jusqu'à l'origine
			ArrayList<Arc> arcsSolution = new ArrayList<>();
			Arc arc = labels[data.getDestination().getId()].getFather();
			while(arc != null) {
				arcsSolution.add(arc);
				arc = labels[arc.getOrigin().getId()].getFather();
			}
			
			Collections.reverse(arcsSolution);
			
			solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcsSolution));
		
		//Sinon, il n'y a pas de solution
		}else {
		
			solution = new ShortestPathSolution(data, Status.INFEASIBLE);
		}
			
//		System.out.println("nb erreurs  = "+cptErreurs);
		return solution;
	}
	
	protected Label createLabel(ShortestPathData data, Arc pere, Label noeudPrecedent) {
			return new Label( pere.getDestination() , noeudPrecedent.getCost()+ data.getCost(pere), pere);	
	}
	
	protected Label createLabelOrigin(ShortestPathData data) {
		return new Label(data.getOrigin(), 0, null);
	}

}
