package peersim.chord;

import java.math.*;
import peersim.core.*;

public class PurgeMessage implements ChordMessage {

	//private Node source;

	//private Node from;



	private BigInteger targetId; 


	

	public PurgeMessage(BigInteger targetId) {
	
		this.targetId = targetId;
		
	}
	
	

	public BigInteger getTargetId () {
		return targetId;
	}



}
