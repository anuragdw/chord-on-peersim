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
public class P2D2TrafficGenerator implements Control {

	private static final String PAR_PROT = "protocol";
	//private static final String PAR_RATE = "rate";
	//private static final String PAR_MODEL = "model";



	private final int pid;
	//private final int rate;
	//private final int model;

	private int nextTime = -1;
	private int logSize;
	private long lastUpdate = 0;
	private int upCount = 10;
	
	private boolean init = false;

	private boolean [] done = new boolean[1000]; 
	private int total = 0;
	

	
	public P2D2TrafficGenerator(String prefix) {
		pid = Configuration.getPid(prefix + "." + PAR_PROT);
		//rate = Configuration.getInt(prefix + "." + PAR_RATE);
		//model = Configuration.getInt(prefix + "." + PAR_MODEL);
		for (int i=0; i<100; i++ ) {
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
			logSize = Network.size()/1000; //2* (int)(Math.log(Network.size())/Math.log(2));
			lastUpdate = 10000;
			Node sender = Network.get(0);
			try {  
				MD5Hash hash = new MD5Hash ("feed_num_of_sets");
				BigInteger targetId = hash.getHash();
			//	CUPInitMessage message = new CUPInitMessage (10, targetId);
			//	EDSimulator.add(10, message, sender, pid); 
				//System.out.println("#" + CommonState.getTime() + " PCXInitMessage Sent .. "); 
			} catch (NoSuchAlgorithmException e) {
					System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
		    	}


			init = true;
		}
	/*	if (init == false ) {
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
*/

		

		
	/*	if ((CommonState.getTime() > 1000 ) && (CommonState.getTime () < 10000 ) ){
			total = 0;
			int size = Network.size();
			for (int i = 0; i < size; i++) {
				ChordProtocol cp = (ChordProtocol) Network.get(i).getProtocol(pid);
				if (cp.pcx_vet > CommonState.getTime() ) total ++;
			}

			if (total < 80 ) {
			

				Node sender, target;
				int tmp;

				do {
					sender = Network.get(CommonState.r.nextInt(Network.size()));
					tmp = (int)sender.getIndex();
					
				} while (sender == null || sender.isUp() == false || done[tmp] == true );
			
				try {  
					MD5Hash hash = new MD5Hash ("feed_num_of_sets");
					BigInteger targetId = hash.getHash();
					PCXRequestMessage message = new PCXRequestMessage (sender,sender,targetId);
					EDSimulator.add(10, message, sender, pid); 
					done[tmp]=true;
					//System.out.println(CommonState.getTime() + " FindNMessage Sent .. "); 
				} catch (NoSuchAlgorithmException e) {
						System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
			    	}
			}	
		      
		} */
		//long min =  CommonState.getTime() - 10000 ;
		if( (CommonState.getTime() != 0) && (CommonState.getTime()%60000 == 0) ) {
			System.out.println(CommonState.getTime() + " CUPupdateMessage Sent " ); //with et = " + ((float)(CommonState.getTime()) - lastUpdate)/1000 + " secs" ); 	
			Node sender = Network.get(0);
			try {  
					MD5Hash hash = new MD5Hash ("feed_num_of_sets");
					BigInteger targetId = hash.getHash();
					UpMessage message = new UpMessage (targetId, CommonState.getTime() + 70000);
					EDSimulator.add(10, message, sender, pid); 
					//System.out.println(CommonState.getTime() + " PCXUPdateMessage Sent with et = " + ((float)(CommonState.getTime()) - lastUpdate)/1000 + " secs" ); 
				} catch (NoSuchAlgorithmException e) {
						System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
			    	}
	
		}

		/*else  {
			int nsize = Network.size()/1000; // 2* (int)(Math.log(Network.size())/Math.log(2));
Node sender = Network.get(0);
			if (nsize > logSize) {
				try {  
					MD5Hash hash = new MD5Hash ("feed_num_of_sets");
					BigInteger targetId = hash.getHash();
					//PCXUpdateMessage message = new PCXUpdateMessage (targetId, CommonState.getTime() + CommonState.getTime() - lastUpdate);
					//EDSimulator.add(10, message, sender, pid); 
					//System.out.println(CommonState.getTime() + " PCXUPdateMessage Sent with et = " + ((float)(CommonState.getTime()) - lastUpdate)/1000 + " secs" ); 
				} catch (NoSuchAlgorithmException e) {
						System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
			    	}
			logSize = nsize;
			 lastUpdate = CommonState.getTime();
			}
		}*/

				
		
		return false;
	}
	

}
