package peersim.chord;

import java.math.*;
import peersim.core.*;

public class InitNMessage implements ChordMessage {


	private BigInteger target;

	public InitNMessage( BigInteger target) {
		this.target = target;
	}

	public BigInteger getTarget() {
		return target;
	}


}
