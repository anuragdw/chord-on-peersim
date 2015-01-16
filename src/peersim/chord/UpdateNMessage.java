package peersim.chord;

import java.math.*;
import peersim.core.*;

public class UpdateNMessage implements ChordMessage {

	
	private int n;
	private long eTime;  


	public UpdateNMessage( int n , long time) {
		this.n = n;
		this.eTime = time;
	}

	public int getN() {
		return n;
	}


	public long getETime() {
		return eTime;
	}



}
