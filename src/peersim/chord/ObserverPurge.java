/**
 * 
 */
package peersim.chord;
import peersim.core.*;

import java.math.BigInteger;
import java.util.*;

import peersim.core.Control;
import peersim.core.Network;
import peersim.core.CommonState;
import peersim.config.Configuration;
import peersim.edsim.EDSimulator;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;	
import java.security.*;
/**
 * @author Andrea
 * 
 */
public class ObserverPurge implements Control {

	private static final String PAR_PROT = "protocol";
	
//	private static int rValueNode = -1;
	
//	private static int pSize = 1000;

	private final String prefix;
	private final int pid;
	//	private final int tid;
	private BigInteger id ;
	
	
//	private float avg;
//	private float cnt;
//
	/**
	 * 
	 */
	public ObserverPurge(String prefix) {
		this.prefix = prefix;
		this.pid = Configuration.getPid(prefix + "." + PAR_PROT);
		//this.tid = Configuration.getPid(prefix + "." + PAR_TRANSPORT);
		
		try {  
				MD5Hash hash = new MD5Hash ("feed_set0");
				this.id = hash.getHash();

		
		} catch (NoSuchAlgorithmException e) {
				System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
			}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see peersim.core.Control#execute()
	 */
	/*public int isStable() {
		// returns number of nodes with rvalue = true 
		//- stable
		// 1 - not yet stable
		// -1 - error - 2+ nodes with rvalue = true 
		int size = Network.size();
		boolean tmp;
		int ret = 0;  
		for ( int i=0; i<size; i++) {
			tmp = ((ChordProtocol) Network.get(i).getProtocol(pid)).rValue;
			if (tmp) ret++;
		}
		return ret;
		
	}*/
	
	public boolean execute() {
		int size = Network.size();
		
		
		
		

		for (int i = 0; i < size; i++) {
				
			ChordProtocol cp = (ChordProtocol) Network.get(i).getProtocol(pid);

			if (cp.setRoot == true ) {
				for ( Map.Entry<Integer, Vector<FeedIndexEntry>> en : cp.feedIndexPool.entrySet()) {
					if (en.getValue().isEmpty() == false ) {
						
						int numIndex = en.getValue().size();
						for (int ii=0; ii < numIndex; ii++ ){
							FeedIndexEntry tmp = en.getValue().get(ii);
							if (tmp.getExpiryTime() <= (CommonState.getTime() + 1000) ) {
								en.getValue().remove(ii);
								ii -= 1;
								numIndex -= 1;
							}
						}
						if ((en.getKey() != 0 ) && ( en.getValue().isEmpty() == true)) {
							try {  
								MD5Hash hash = new MD5Hash ("feed_num_of_sets");
								BigInteger targetId = hash.getHash();
								ReduceSetMessage msg = new ReduceSetMessage (targetId, en.getKey());
							//	debug(5,"ReduceSetMessage Sent");
								EDSimulator.add(1, msg, Network.get(i), pid); 
				
							} catch (NoSuchAlgorithmException e) {
									System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
							}
		
						}
									
					}
				}
			}
			
		}	

		
			 
		return false;
	}
	

}
