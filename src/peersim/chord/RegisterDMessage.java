package peersim.chord;

import java.math.*;
import peersim.core.*;

public class RegisterDMessage implements ChordMessage {


	//private BigInteger targetId;
	int k ;


	public RegisterDMessage(int k) {
		//this.targetId = targetId;
		this.k = k;
		
	}

	public int getK() {
		return k;
	}

	//public BigInteger getTargetId () {
	//	return targetId;
	//}

}
