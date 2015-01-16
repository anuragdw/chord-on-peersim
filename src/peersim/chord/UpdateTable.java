package peersim.chord;

import java.math.*;
import peersim.core.*;

public class UpdateTable {

	
	private Node requester;
	private BigInteger id;
	private boolean isExpired;
	private boolean isServed;

	public UpdateTable ( Node requester, BigInteger id, boolean isExpired, boolean isServed ) {
		this.requester = requester;
		this.id = id;
		this.isExpired = isExpired;
		this.isServed = isServed;
	}
	
	public Node getRequester() {
		return requester;
	}

	public BigInteger getId () {
		return id;
	}

	public boolean getIsExpired () {
		return isExpired; 
	}

	public boolean getIsServed () {
		return isServed; 
	}


}
