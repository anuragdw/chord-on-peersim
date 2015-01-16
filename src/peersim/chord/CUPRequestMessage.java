package peersim.chord;

import java.math.*;
import peersim.core.*;

public class CUPRequestMessage implements ChordMessage {

	private Node source;

	private Node from;

	private BigInteger targetId; 
	

	public CUPRequestMessage(Node source, Node from, BigInteger targetId) {
		this.source = source;
		this.from = from;
		this.targetId = targetId;

		
	}
	
	public Node getSource () {
		return source;
	}

	public Node getFrom () {
		return from;
	}

	public BigInteger getTargetId () {
		return targetId;
	}

}
