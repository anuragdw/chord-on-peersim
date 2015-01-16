package peersim.chord;

import java.math.*;
import peersim.core.*;

public class UpdateRMessage implements ChordMessage {

	
	private int n;
	private BigInteger target;
	private long eTime;  
	private boolean shortUpdate;


	public UpdateRMessage( int n , BigInteger target, long time, boolean su) {
		this.n = n;
		this.target = target ;
		this.eTime = time;
		this.shortUpdate = su;
	}

	public int getN() {
		return n;
	}

	public BigInteger getTarget() {
		return target;
	}

	public long getETime() {
		return eTime;
	}

	public boolean getSU () {
		return shortUpdate;
	}



}
