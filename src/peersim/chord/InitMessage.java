package peersim.chord;

import java.math.*;
import peersim.core.*;

public class InitMessage implements ChordMessage {


	private BigInteger target;
	private Node node;

	public InitMessage( BigInteger target, Node node) {
		this.target = target;
		this.node = node;
	}

	public BigInteger getTarget() {
		return target;
	}
	
	public Node getNode() {
		return node;
	}


}
