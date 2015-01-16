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

public class CacheObj {

	private int value;
	private boolean pRF;
	private long eTime;
	public Vector<Node> iVector ;

	public CacheObj ( int val, long eTime, boolean penRF, int capacity) {
		this.value = val;
		this.eTime = eTime;
		this.pRF = penRF;
		this.iVector = new Vector<Node>(capacity); 
		

	}
	
	


	public boolean getPRF () {
		return pRF;
	}

	public void setPRF (boolean status) {
		this.pRF = status;
	}

	public int getValue () {
		return value;
	}

	public long getETime () {
		return eTime;
	}

	public void setETime ( long t ) {
		this.eTime = t;
	}
	 

}
