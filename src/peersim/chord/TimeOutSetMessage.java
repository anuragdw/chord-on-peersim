package peersim.chord;

import java.math.*;
import peersim.core.*;

public class TimeOutSetMessage implements ChordMessage {


	private BigInteger targetId;
	private boolean isSource;


	public TimeOutSetMessage( BigInteger targetId, boolean b) {
		this.targetId = targetId;
		this.isSource = b;
		
	}

	public BigInteger getTargetId () {
		return targetId;
	}

	public boolean getIsSource() {
		return isSource;
	}

}
