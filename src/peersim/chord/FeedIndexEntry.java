/**
 * 
 */
package peersim.chord;

import peersim.core.Node;

import peersim.core.*;
import peersim.config.Configuration;
import peersim.edsim.EDSimulator;

import java.sql.Timestamp;
import java.math.*;
import java.util.Comparator;
import java.util.Vector;

public class FeedIndexEntry {

	private BigInteger id;
	private Node node;
	private long expiryTime;

	public FeedIndexEntry ( BigInteger id, Node node, long eTime) {
		this.id = id;
		this.expiryTime = eTime;
		this.node = node;

	}

	public FeedIndexEntry ( FeedIndexEntry f ) {
		this.id = f.id;
		this.node = f.node;
		this.expiryTime = f.expiryTime;
	}

	public BigInteger getId () {
		return id;
	}

	public Node getNode () {
		return node;
	}

	public long getExpiryTime () {
		return expiryTime;
	}
	 

}
