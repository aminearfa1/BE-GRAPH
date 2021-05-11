package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class Label implements Comparable<Label>{
	private Node sommetCourant;
	private boolean marque;
	private double cout;
	private Arc pere;
	
	public Label(Node sommetCourant, double cout, Arc pere) {
		this.sommetCourant = sommetCourant;
		this.marque = false;
		this.cout = cout;
		this.pere = pere;
	}
	
	public Node getCurrent() {
		return this.sommetCourant;
	}
	public double getCost() {
		return this.cout;
	}
	public Arc getFather() {
		return this.pere;
	}
	
	public void setMark() {
		this.marque = true;
	}
	
	public void setFather(Arc father) {
		this.pere = father;
	}
	
	public void setCost(double cost) {
		this.cout = cost;
	}
	
	public boolean isMarked() {
		return this.marque;
	}
	
	@Override 
	public String toString() {
		return ""+this.getCost();
	}
	
	public double getTotalCost() {
		return this.cout;
	}
	
	@Override
	public int compareTo(Label o) {
		return Double.compare(this.getTotalCost(), o.getTotalCost()); 
	}
}
