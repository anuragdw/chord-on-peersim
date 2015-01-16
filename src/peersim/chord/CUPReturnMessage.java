package peersim.chord;

import java.math.*;
import peersim.core.*;

public class CUPReturnMessage implements ChordMessage {

	private int val;
	private long expiryTime; 


	public CUPReturnMessage(int val, long expiryTime) {
		this.val = val;
		this.expiryTime = expiryTime;
	}
	
	public int getVal () {
		return val;
	}

	public long getExpiryTime () {
		return expiryTime;
	}

}
