package peersim.chord;

import java.math.*;
import peersim.core.*;

public class CUPUpMessage implements ChordMessage {

	
	private BigInteger targetId;
	private long et; 
	//private int tag;



	public CUPUpMessage( BigInteger key, long val) {
		this.et = val;
		this.targetId = key;
		//this.tag = tag;
		
	}


	public long getET () {
		return et;
	}

	public BigInteger getTargetId () {
		return targetId;
	}

	
}
