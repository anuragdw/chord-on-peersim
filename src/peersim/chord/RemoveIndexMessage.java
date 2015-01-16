package peersim.chord;

import java.math.*;
import peersim.core.*;

public class RemoveIndexMessage implements ChordMessage {

	//private Node source;

	//private Node from;



	private BigInteger targetId; 
	public Integer k;


	

	public RemoveIndexMessage(BigInteger targetId, Integer k) {
	
		this.targetId = targetId;
		this.k =k ;
		
	}
	
	

	public BigInteger getTargetId () {
		return targetId;
	}

	public Integer getK() {
		return k;
	}


}
