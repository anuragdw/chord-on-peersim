package peersim.chord;

import java.math.*;
import peersim.core.*;

public class CUPDRegisterMessage implements ChordMessage {

	
	private BigInteger id;


	public CUPDRegisterMessage( BigInteger key) {
		this.id = key;
		
	}


	public BigInteger getId () {
		return id;
	}

	
}
