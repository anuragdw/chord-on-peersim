package peersim.chord;

import java.math.*;
import peersim.core.*;

public class StartQuery implements ChordMessage {

//	private BigInteger target;
	
	private int value ;
	private int type;

	public StartQuery(int n, int type) {
		this.value  = n;
		this.type = type; 

	}


	/**
	 * @return the target
	 */
	public int getNumReplica() {
		return value;
	}

	public int getValue () {
		return value;
	}

	public int getType () {
		return type;
	}

	

}
