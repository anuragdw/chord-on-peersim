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
public class TrafficGenerator3 implements Control {

	private static final String PAR_PROT = "protocol";

	private final int pid;

	private int valueN;

	private boolean flag;

	private boolean flag2;

	/**
	 * 
	 */
	public TrafficGenerator3(String prefix) {
		pid = Configuration.getPid(prefix + "." + PAR_PROT);
		valueN = 999; 
		flag = false; 
		flag2 = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see peersim.core.Control#execute()
	 */
	public boolean execute() {

		if ( (CommonState.getTime() > 430000) && (CommonState.getTime() < 520000) ) flag = false ;

		if ( (CommonState.getTime() > 880000) && (CommonState.getTime() < 920000) ) flag = false ;

		if ( (CommonState.getTime() > 1380000) && (CommonState.getTime() < 1420000) ) flag = false ;

	
		if ( (CommonState.getTime() > 80000) && (CommonState.getTime() < 120000) ) flag2 = false ;
		

		if ( (CommonState.getTime() > 480000) && (CommonState.getTime() < 520000) ) flag2 = true ;

		int size = Network.size();
		Node sender, target;
		int i = 0;
		do {
			i++;
			sender = Network.get(CommonState.r.nextInt(size));
			target = Network.get(CommonState.r.nextInt(size));
		} while (sender == null || sender.isUp() == false );
		if ( flag == false ) {
	
			sender = Network.get(0); // root node
			StartQuery message = new StartQuery (valueN,2); // ( value of N, message type )
			EDSimulator.add(10, message, sender, pid);  
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
				EDSimulator.add(10, message, sender, pid);  
		//	}
		//	catch (NoSuchAlgorithmException e) {
				System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
	    	//	}	
			
			//flag2 = true; 
		
		} 
		//System.out.println("valueN:  " + valueN);
//System.out.println("");
		return false;
	}

}
