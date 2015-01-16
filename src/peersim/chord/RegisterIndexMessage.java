package peersim.chord;

import java.math.*;
import peersim.core.*;

public class RegisterIndexMessage implements ChordMessage {

	private Node source;

	//private Node node;

	//private int k;
	private FeedIndexEntry entry;

	private BigInteger id; 
	private int k;
	

	public RegisterIndexMessage(FeedIndexEntry entry, BigInteger id, Node source, int k) {
		this.source = source;
	//	this.from = from;
		this.entry = entry;
		this.id = id;
		this.k = k;
		
	}
	
	public Node getSource () {
		return source;
	}
/*
	public Node getFrom () {
		return from;
	}
*/
	public FeedIndexEntry getEntry() {
		return entry;
	}

	public int getK() {
		return k;
	}

	public BigInteger getId () {
		return id;
	}



}
