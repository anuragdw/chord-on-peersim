package peersim.chord;

import java.math.*;
import peersim.core.*;

public class ReduceSetMessage implements ChordMessage {


	private BigInteger targetId;
	int k;


	public ReduceSetMessage(BigInteger targetId, int k) {
		this.targetId = targetId;
		this.k = k;
		
	}

	public BigInteger getTargetId () {
		return targetId;
	}

	public int getK() {
		return k;
	}
		

}
