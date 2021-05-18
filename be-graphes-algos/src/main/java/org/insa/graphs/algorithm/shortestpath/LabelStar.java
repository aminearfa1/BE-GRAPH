package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class LabelStar extends Label implements Comparable<Label>{
	
	private Double coutEstime;

	public LabelStar(Node sommetCourant, double cout, double coutEstime, Arc pere) {
		super(sommetCourant, cout, pere);
		this.coutEstime = coutEstime;
	}
	
	@Override
	public double getTotalCost(){
		return this.getCost() + this.coutEstime;
	}
	
	@Override
	public int compareTo(Label o) {
		LabelStar other = (LabelStar) o;
		if(this.getTotalCost() == other.getTotalCost()) {
			return Double.compare(this.coutEstime, other.coutEstime);
		}
		return Double.compare(this.getTotalCost(), o.getTotalCost()); 
	}
}
