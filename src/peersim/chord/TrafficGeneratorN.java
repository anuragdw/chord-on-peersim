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
public class TrafficGeneratorN implements Control {

	private static final String PAR_PROT = "protocol";
	private static final String PAR_RATE = "rate";
	private static final String PAR_MODEL = "model";


	private final int pid;
	private final int rate;
	private final int model;

	private int nextTime = -1;

	
	private boolean init = false;

	private boolean [] done = new boolean[1000]; 
	private int total = 0;
	

	
	public TrafficGeneratorN(String prefix) {
		pid = Configuration.getPid(prefix + "." + PAR_PROT);
		rate = Configuration.getInt(prefix + "." + PAR_RATE);
		model = Configuration.getInt(prefix + "." + PAR_MODEL);
		for (int i=0; i<1000; i++ ) {
			done[i] = false;
		}
		
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see peersim.core.Control#execute()
	 */
	public boolean execute() {

		if (init == false ) {
			Node sender = Network.get(0);
			try {  
				MD5Hash hash = new MD5Hash ("feed_set0");
				BigInteger targetId = hash.getHash();
				InitNMessage message = new InitNMessage (targetId);
				System.out.println(CommonState.getTime() + " InitNMessage Sent .. ");
				EDSimulator.add(10, message, sender, pid); 
				init = true; 
			} catch (NoSuchAlgorithmException e) {
				System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
			}	

		
			if (nextTime == -1 ) {
				if (model == 1 ) {
					nextTime = 5000; 
					//System.out.println(CommonState.getTime() + " nextTime = " + nextTime);
				} else if (model == 2 ) {

					double u = 1.0 - Math.random(); 
					double addOn = Math.log(u)/rate;
					nextTime = 5000 + (int)Math.ceil(-addOn*1000);
				}
			}
			
		}


	/*	if (CommonState.getTime() == 100 ) {
		//	System.out.println("Init..."); 
			Node sender = Network.get(0);
			if (sender == null || sender.isUp() == false ) {
				System.out.println("Simulation failed");
				System.exit(0);
			}
			int logSize = 2* (int)(Math.log(Network.size())/Math.log(2));
			
			try {  
				MD5Hash hash = new MD5Hash ("feed_num_of_sets");
			//	System.out.println("SEND");
				BigInteger targetId = hash.getHash();
		//		System.out.println(" hash   id : " + hash.getHash() );
				//sender = Network.get(10); // root node
				//UpdateRMessage message = new UpdateRMessage ( logSize , targetId, CommonState.getTime(), false);
				//EDSimulator.add(10, message, sender, pid);  
			//	cp.startTime = CommonState.getTime();
			} catch (NoSuchAlgorithmException e) {
				System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
			}	
			
			 
		}
		*/
		if ( (init == true) && (CommonState.getTime () >= 5000 ) && (total <= 950 )  )	 {		


				
			if (CommonState.getTime() == nextTime) { 

				if (model == 1 ) {
					nextTime += 1000/rate ; 
				} else if (model == 2 ) {

					double u = 1.0 - Math.random(); 
					double addOn = Math.log(u)/rate;
					nextTime += (int)Math.ceil(-addOn*1000);
				}

				Node sender, target;
				int tmp;

				do {
					sender = Network.get(CommonState.r.nextInt(Network.size()));
					tmp = (int)sender.getID();
					
				} while (sender == null || sender.isUp() == false || done[tmp] == true );
			
				try {  
					MD5Hash hash = new MD5Hash ("feed_num_of_sets");
					BigInteger targetId = hash.getHash();
					FindNMessage message = new FindNMessage (sender,sender,targetId,1,0); // ( set number, message type )
					EDSimulator.add(10, message, sender, pid); 
					done[tmp]=true;
					total++;
					//System.out.println(CommonState.getTime() + " FindNMessage Sent .. "); 
				} catch (NoSuchAlgorithmException e) {
						System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
			    	}
			}	
		      
		}
		
		return false;
	}
	

}
