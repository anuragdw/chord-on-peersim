package peersim.chord;

import java.math.*;
import peersim.core.*;

public class CUPUpdateMessage implements ChordMessage {

	private int val;
	private long expiryTime; 
	private Node node;


	public CUPUpdateMessage(int val, long expiryTime, Node n) {
		this.val = val;
		this.expiryTime = expiryTime;
		this.node = n;
	}
	
	public int getVal () {
		return val;
	}

	public long getExpiryTime () {
		return expiryTime;
	}

	public Node getNode () {
		return node;
	}

	

}
