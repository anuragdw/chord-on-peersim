package peersim.chord;

import java.math.*;
import peersim.core.*;

public class PurgeIndexMessage implements ChordMessage {

	//private Node source;

	//private Node from;

	private int k;

	private BigInteger targetId; 

	private long limit;
	

	public PurgeIndexMessage(int k, BigInteger targetId, long limit) {
	//	this.source = source;
	//	this.from = from;
		this.k = k;
		this.limit = limit;
		this.targetId = targetId;
		
	}
	
	/*public Node getSource () {
		return source;
	}

	public Node getFrom () {
		return from;
	}
*/
	public long getLimit() {
		return limit;
	}
	
	public int getK() {
		return k;
	}

	public BigInteger getTargetId () {
		return targetId;
	}



}
