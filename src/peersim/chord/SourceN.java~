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
public class SourceN implements Control {

	private static final String PAR_PROT = "protocol";

	private static int logSize = 0;
	private final int pid;

	private int valueN;

	private boolean flag;

	private boolean flag2;

	private boolean init = false;
	private boolean flagn ;
	
	private int traffic = 1000; 

	/**
	 * 
	 */
	public SourceN(String prefix) {
		pid = Configuration.getPid(prefix + "." + PAR_PROT);
		valueN = 999; 
		flag = false; 
		flag2 = true;
		flagn = false;
	}
	
	public int isStable() {
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
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see peersim.core.Control#execute()
	 */
	public boolean execute() {

		if (init == false ) {
			Node sender = Network.get(0);
		//	InitMessage message = new InitMessage (0, ((ChordProtocol) sender.getProtocol(pid)).chordId);
		//	EDSimulator.add(10, message, sender, pid);
			init = true;
		}
		
		
		if ( (init == true) )	 {		




		if ( (CommonState.getTime() > 430000) && (CommonState.getTime() < 520000) ) flag = false ;

		if ( (CommonState.getTime() > 880000) && (CommonState.getTime() < 920000) ) flag = false ;

		if ( (CommonState.getTime() > 1380000) && (CommonState.getTime() < 1420000) ) flag = false ;

	
		if ( (CommonState.getTime() > 80000) && (CommonState.getTime() < 120000) ) flag2 = false ;
		

		if ( (CommonState.getTime() > 780000) && (CommonState.getTime() < 820000) ) flag2 = true ;
		
	//	if ( (CommonState.getTime() > 8950) && (CommonState.getTime() < 9050) ) {  flagn = false ; }
		
		if (logSize == 0){
			logSize = 2* (int)(Math.log(Network.size())/Math.log(2));
		}
		
		int newSize = 2* (int)(Math.log(Network.size())/Math.log(2));
		if (newSize != logSize) {
			Node sender = Network.get(0);
			if (sender == null || sender.isUp() == false ) {
				System.out.println("Simulation failed");
				System.exit(0);
			}
			//int logSize = 2* (int)(Math.log(Network.size())/Math.log(2));
			
			try {  
				MD5Hash hash = new MD5Hash ("feed_num_of_sets");
			//	System.out.println("SEND");
				BigInteger targetId = hash.getHash();
		//		System.out.println(" hash   id : " + hash.getHash() );
				//sender = Network.get(10); // root node
				UpdateRMessage message = new UpdateRMessage ( newSize , targetId, CommonState.getTime(), false);
				EDSimulator.add(1, message, sender, pid);  
				logSize = newSize;
				//System.out.println("# "+ CommonState.getTime() +" n value updated ");
			//	cp.startTime = CommonState.getTime();
			} catch (NoSuchAlgorithmException e) {
				System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
			}	
		}

		

		int size = Network.size();
		Node sender, target;
		int i = 0;	
		if ( false ) {
		//	System.out.println("TEST");
		for ( int mm = 0; mm < traffic; mm++  ) {
			do {
				i++;
				sender = Network.get(CommonState.r.nextInt(size));
				target = Network.get(CommonState.r.nextInt(size));
			} while (sender == null || sender.isUp() == false );
			
			try {  
					MD5Hash hash = new MD5Hash ("feed_num_of_sets");
				//	System.out.println("SEND");
					BigInteger targetId = hash.getHash();
			//		System.out.println(" hash   id : " + hash.getHash() );
					//sender = Network.get(10); // root node
					FindNMessage message = new FindNMessage (sender,sender,targetId,1,0); // ( set number, message type )
					EDSimulator.add(10, message, sender, pid);  
			} catch (NoSuchAlgorithmException e) {
					System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
		    }	
			
		}
		 if (CommonState.getTime() == 1700) flagn = true;
		}
			
		if ( flag == false ) {
	
			sender = Network.get(0); // root node
			StartQuery message = new StartQuery (valueN,2); // ( value of N, message type )
			//EDSimulator.add(10, message, sender, pid);  
			valueN += 999;
		
			flag = true; 
		}

		if ( flag2 == false ) {

		//System.out.println(" Sender id : " + ((ChordProtocol) sender.getProtocol(pid)).chordId );
		//System.out.println(" Target id : " + ((ChordProtocol) target.getProtocol(pid)).chordId );
		//	try {  MD5Hash hash = new MD5Hash ("no_of_sets");
		//		System.out.println(" hash   id : " + hash.getHash() );
				sender = Network.get(10); // root node
				StartQuery message = new StartQuery (1,1); // ( set number, message type )
				//EDSimulator.add(10, message, sender, pid);  
		//	}
		//	catch (NoSuchAlgorithmException e) {
			//	System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
	    	//	}	
			
			//flag2 = true; 
		
		} 
		} // end of init 
		//System.out.println("valueN:  " + valueN);
//System.out.println("");
		return false;
	}

}
