package peersim.chord;

import java.math.*;
import peersim.core.*;

public class ReturnNMessage implements ChordMessage {

	private int n;
	private long expiryTime; 
	private int hopCount; 


	public ReturnNMessage(int n, long time, int count) {
		this.n = n;
		this.expiryTime = time;
		this.hopCount = count;
	}
	
	public int getN () {
		return n;
	}

	public long getExpiryTime () {
		return expiryTime;
	}

	public int getHC() {
		return hopCount; 
	}

}
