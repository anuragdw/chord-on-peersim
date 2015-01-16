package peersim.chord;

import java.math.*;
import peersim.core.*;

public class CUPInitMessage implements ChordMessage {

	private int value;
	
	private BigInteger key; 



	public CUPInitMessage(int val, BigInteger key) {
		this.value = val;
		this.key = key;
		
	}


	public int getValue () {
		return value;
	}

	public BigInteger getKey () {
		return key;
	}

}
