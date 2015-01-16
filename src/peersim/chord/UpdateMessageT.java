package peersim.chord;

import java.math.*;
import peersim.core.*;

public class UpdateMessageT implements ChordMessage {

	private Node sender;
	
	private int n; 

	private BigInteger targetId;

	private BigInteger limit;

	private boolean type; 

	private int level;

//	private int hopCounter = -1;

	public UpdateMessageT(int n, BigInteger limit, boolean type, int level ) {
	//	this.sender = sender;
		this.n = n;
	//	this.targetId = targetId;
		this.limit = limit;
		this.type = type;
		this.level = level;
	}

//	public void increaseHopCounter() {
//		hopCounter++;
//	}

	/**
	 * @return the senderId
	 */
	public Node getSender() {
		return sender;
	}

	/**
	 * @return the target
	 */
	public BigInteger getTarget() {
		return targetId;
	}

	public BigInteger getLimit() {
		return limit;
	}

	public int getN() {
		return n;
	}

	public int getLevel() {
		return level;
	}

	public boolean getType() {
		return type;
	}

	/**
	 * @return the hopCounter
	 */
//	public int getHopCounter() {
//		return hopCounter;
//	}

}
