package peersim.chord;

import java.math.*;
import peersim.core.*;

public class PCXUpdateMessage implements ChordMessage {

	
	private BigInteger targetId;
	private long et; 



	public PCXUpdateMessage( BigInteger key, long val) {
		this.et = val;
		this.targetId = key;
		
	}


	public long getET () {
		return et;
	}

	public BigInteger getTargetId () {
		return targetId;
	}

}
