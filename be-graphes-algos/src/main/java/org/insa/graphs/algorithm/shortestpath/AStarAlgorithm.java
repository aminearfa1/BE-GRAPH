package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.insa.graphs.algorithm.AbstractInputData.Mode;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.Point;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    
   @Override
    protected Label createLabel(ShortestPathData data, Arc pere, Label noeudPrecedent) {
    
    	double coutEstime = 0;
    	
    	
    	//Cas chemin le plus court
    	if(data.getMode() == Mode.LENGTH) {
    		coutEstime = pere.getDestination().getPoint().distanceTo(data.getDestination().getPoint() );
		//Cas chemin le plus rapide
    	}else if(data.getMode() == Mode.TIME) {
    	
    		int vitesse, 
    		vitesseGraph = data.getGraph().getGraphInformation().getMaximumSpeed() ,
    		vitesseArc = data.getMaximumSpeed() ; 
    		
    		//On récupère la vitesse		
    		if(data.getGraph().getGraphInformation().hasMaximumSpeed() && vitesseArc !=-1 ) {
    			vitesse = Math.min(vitesseGraph, vitesseArc);
    		}else {
    			if(data.getGraph().getGraphInformation().hasMaximumSpeed() && vitesseArc == -1) {
    				vitesse = vitesseGraph;
    			}else if( !data.getGraph().getGraphInformation().hasMaximumSpeed() && vitesseArc !=-1) {
    				vitesse = vitesseArc;
    			}else{
    				vitesse = 1;
    			}
    		}
    		
    		coutEstime = pere.getDestination().getPoint().distanceTo(data.getDestination().getPoint()) * 3.6f / vitesse;	
    	}else {
    		return null;
    	}
    	
    	
    	
    	return new LabelStar(pere.getDestination(),
				noeudPrecedent.getCost() + data.getCost(pere),
				coutEstime,
				pere);
	}
   
   @Override
   protected Label createLabelOrigin(ShortestPathData data) {
	  
	   return new LabelStar(data.getOrigin(),
				0,
				Point.distance(data.getOrigin().getPoint(), data.getDestination().getPoint() ) ,
				null);
	   
   }
}
