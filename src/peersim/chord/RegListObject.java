/**
 * 
 */
package peersim.chord;

import peersim.core.*;
import peersim.config.Configuration;
import peersim.edsim.EDSimulator;

import java.sql.Timestamp;
import java.math.*;
import java.util.Comparator;

public class RegListObject {

	public BigInteger id;

	private long expiryTime;

	private Node node;

	private boolean reqStatus;
	
	private int k;

	public RegListObject ( BigInteger id, long expiryTime, Node node, boolean reqStatus, int k) {
		this.id = id;
		this.expiryTime = expiryTime;
		this.node = node;
		this.reqStatus = reqStatus;
		this.k = k;

	}
	
	

	public boolean getReqStatus () {
		return reqStatus;
	}

	public void setReqStatus (boolean status) {
		this.reqStatus = status;
	}

	public Node getNode () {
		return node;
	} 

	public long getExpiryTime () {
		return expiryTime;
	}

	public int getK() {
		return k;
	}

	 

}
