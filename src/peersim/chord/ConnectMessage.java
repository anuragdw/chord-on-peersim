package peersim.chord;

import java.math.*;
import peersim.core.*;

public class ConnectMessage implements ChordMessage {

	//private Node source;

	private Node node;

	//private int k;

	private BigInteger id; 
	

	public ConnectMessage(Node node, BigInteger id) {
	//	this.source = source;
	//	this.from = from;
		this.node = node;
		this.id = id;
		
	}
	
	/*public Node getSource () {
		return source;
	}

	public Node getFrom () {
		return from;
	}
*/
	public Node getNode() {
		return node;
	}

	public BigInteger getId () {
		return id;
	}



}
