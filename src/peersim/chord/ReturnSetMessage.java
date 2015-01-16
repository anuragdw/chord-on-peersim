package peersim.chord;

import java.math.*;
import peersim.core.*;

public class ReturnSetMessage implements ChordMessage {

	private Set set;
	private long expiryTime; 


	public ReturnSetMessage(Set set, long expiryTime) {
		this.set = set;
		this.expiryTime = expiryTime;
	}
	
	public Set getSet () {
		return set;
	}

	public long getExpiryTime () {
		return expiryTime;
	}

	

}
