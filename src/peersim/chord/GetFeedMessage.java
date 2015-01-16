package peersim.chord;

import java.math.*;
import peersim.core.*;

public class GetFeedMessage implements ChordMessage {

	private Node from;

	private BigInteger fromId; 


	public GetFeedMessage(Node from, BigInteger destination) {
		this.from = from;
		this.fromId = destination;	
	}

	public Node getFrom () {
		return from;
	}
	

	public BigInteger getId () {
		return fromId;
	}



}
