package peersim.chord;

import java.math.*;
import peersim.core.*;

public class FindNMessage implements ChordMessage {

	private Node source;

	private Node from;

	private BigInteger destination; 
	
	private int numTry;
	
	private int hopCount;
	



	public FindNMessage(Node source, Node from, BigInteger destination, int numTry, int hopCount) {
		this.source = source;
		this.from = from;
		this.destination = destination;
		this.numTry = numTry;
		this.hopCount = hopCount;
		
	}
	
	public Node getSource () {
		return source;
	}

	public Node getFrom () {
		return from;
	}

	public BigInteger getDestination () {
		return destination;
	}

	public int getNumTry () {
		return numTry;
	}
	
	public int getHC () {
		return hopCount;
	}


}
