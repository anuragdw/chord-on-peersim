package peersim.chord;

import java.math.*;
import peersim.core.*;
import java.util.*;

public class ReturnIndexMessage implements ChordMessage {

	private Vector<FeedIndexEntry> list;
	private int k;



	public ReturnIndexMessage(Vector<FeedIndexEntry> list, int k ) {
		this.list = list;
		this.k = k;
	}
	
	public Vector<FeedIndexEntry> getList () {
		return list;
	}

	public int getK() {
		return k;
	}


}
