package peersim.chord;

import java.math.*;
import peersim.core.*;

public class GetIndexMessage implements ChordMessage {

	private Node from;
	private Node source;

	private BigInteger destination; 

	private int k;
	
	
	public GetIndexMessage(Node source, Node from, BigInteger destination, int k) {
		this.from = from;
		this.source = source;
		this.destination = destination;	
		this.k = k;
	}

	public Node getFrom () {
		return from;
	}
	
public Node getSource () {
		return source;
	}

	public BigInteger getDestination () {
		return destination;
	}

	public int getK() {
		return k;
	}


}
