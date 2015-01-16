/**
 * 
 */
package peersim.chord;

import peersim.core.*;
import peersim.config.Configuration;
import peersim.edsim.EDSimulator;

import java.security.*;
import java.math.*; 


/**
 * @author Andrea
 * 
 */
public class TrafficGeneratorTest implements Control {

	private static final String PAR_PROT = "protocol";
	
	private final int pid;
	//private final int rate;
	//private final int model;

	private int nextTime = -1;

	
	private boolean init = false;

	private boolean [] done = new boolean[1000]; 
	private int total = 0;
	private int total2 = 0;
	

	
	public TrafficGeneratorTest(String prefix) {
		pid = Configuration.getPid(prefix + "." + PAR_PROT);
		
		
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see peersim.core.Control#execute()
	 */
	public boolean execute() {

		Node sender;

		if ( init == false ) {

			do {
				sender = Network.get(CommonState.r.nextInt(Network.size()));
				
			} while (sender == null || sender.isUp() == false);
			
			try {  
				MD5Hash hash = new MD5Hash ("feed_set0");
				BigInteger targetId = hash.getHash();
				InitMessage message = new InitMessage (targetId, sender);
				//System.out.println("#" + CommonState.getTime() + " InitNMessage Sent from " + sender.getID());
				((ChordProtocol)sender.getProtocol(pid)).streamAvail = true;
				//((ChordProtocol)sender.getProtocol(pid)).maxConn = 50;
				EDSimulator.add(10, message, sender, pid); 
				init = true; 
			} catch (NoSuchAlgorithmException e) {
				System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
			}
		}

		//System.out.println("****");
		ChordProtocol cp = ((ChordProtocol)Network.get(66).getProtocol(pid));
		//if(( CommonState.getTime () >= 1400  ) && (cp.cacheSet.getCount() == 0) ) {
		//		System.out.println(CommonState.getTime());
		//		System.exit(0);
		//}
	
		if (( CommonState.getTime () >= 1000  )	&& ( total != 0 ) && ( CommonState.getTime() % 1000 == 0  )) {		

				total--;
				boolean pass = false; 

				do {
					sender = Network.get(CommonState.r.nextInt(Network.size()));
					pass = ((ChordProtocol)sender.getProtocol(pid)).streamAvail; 
					
				} while (sender == null || sender.isUp() == false || pass == true);
			
				//try {  
				//	MD5Hash hash = new MD5Hash ("feed_num_of_sets");
				//	BigInteger targetId = hash.getHash();
					StreamMessage message = new StreamMessage ();
					EDSimulator.add(10, message, sender, pid); 
					
					//System.out.println(CommonState.getTime() + " FindNMessage Sent .. "); 
			//	} catch (NoSuchAlgorithmException e) {
			//			System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
			  //  	}
				
		      
		}

		if (( CommonState.getTime () >= 20000  )	&& ( total2 != 0 ) && ( CommonState.getTime() % 1000 == 0  )) {		

				total2--;
				boolean pass = false; 

				do {
					sender = Network.get(CommonState.r.nextInt(Network.size()));
					pass = ((ChordProtocol)sender.getProtocol(pid)).streamAvail; 
					
				} while (sender == null || sender.isUp() == false || pass == true);
			
				//try {  
				//	MD5Hash hash = new MD5Hash ("feed_num_of_sets");
				//	BigInteger targetId = hash.getHash();
					StreamMessage message = new StreamMessage ();
					EDSimulator.add(10, message, sender, pid); 
					
					//System.out.println(CommonState.getTime() + " FindNMessage Sent .. "); 
			//	} catch (NoSuchAlgorithmException e) {
			//			System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
			  //  	}
				
		      
		}
		
		return false;
	}
	

}
