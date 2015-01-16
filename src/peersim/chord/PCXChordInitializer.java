package peersim.chord;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Random;

import peersim.config.Configuration;
import peersim.core.CommonState;
import peersim.core.Network;
import peersim.core.Node;
import peersim.dynamics.NodeInitializer;
import peersim.edsim.EDSimulator;

public class PCXChordInitializer implements NodeInitializer {

	private static final String PAR_PROT = "protocol";
	private static BigInteger lastId = BigInteger.ZERO;
	private static BigInteger firstId = BigInteger.ZERO;
	
	private BigInteger rValueId ;
	
	private static int count = 0;

	private int pid = 0;

	private ChordProtocol cp;

	public PCXChordInitializer(String prefix) {
		pid = Configuration.getPid(prefix + "." + PAR_PROT);
		
	}

	// this is guaranteed to run before a node is added to the network
	public void initialize(Node n) {
		cp = (ChordProtocol) n.getProtocol(pid);
		join(n);
	}
	
	public int getIndex( BigInteger id ) {
		System.out.println("nw size = " + Network.size()); 
		for ( int i=0; i<Network.size() ; i++ ) {
			if ( id.compareTo( ((ChordProtocol) Network.get(i).getProtocol(pid)).chordId ) == 0 )
				return i;
		}
		return -1;
	}

	public void join(Node myNode) { 
		try {  
			MD5Hash hash = new MD5Hash ("feed_num_of_sets");
			rValueId = hash.getHash();
  
		} catch (NoSuchAlgorithmException e) {
				System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
		}
		
		Random generator = new Random();
		//System.out.println("New Node created");

		cp.predecessor = null; // predeccessor not updated here
		// search a random node to join
		Node n;
		do {
			n = Network.get(generator.nextInt(Network.size()));
		} while (n == null || n.isUp() == false); // where is isUp() implemented ??
		cp.setNode(myNode); //  TODO ADM
		
		cp.m = ((ChordProtocol) n.getProtocol(pid)).m; // copying the nodeId length
		cp.chordId = new BigInteger(cp.m, CommonState.r); // give a random nodeId
		ChordProtocol cpRemote = (ChordProtocol) n.getProtocol(pid);


		Node successor = cpRemote.find_successor(cp.chordId);
		
		cp.fails = 0;
		cp.stabilizations = 0;
		cp.varSuccList = cpRemote.varSuccList;
		cp.varSuccList = 0;
		cp.succLSize = cpRemote.succLSize;
		cp.successorList = new Node[cp.succLSize];
		cp.successorList[0] = successor;
		cp.fingerTable = new Node[cp.m];

		long succId = 0;
		if (lastId == BigInteger.ZERO) { // if last id not set
			 lastId = ((ChordProtocol) Network.get(Network.size() - 1)
					.getProtocol(pid)).chordId;

		} 
		if(lastId.compareTo(cp.chordId) < 0){
				lastId = cp.chordId;

			
		}
		
		if (firstId == BigInteger.ZERO) {
			firstId = ((ChordProtocol) Network.get(0).getProtocol(pid)).chordId;

		} 
		if(firstId.compareTo(cp.chordId) > 0){
			firstId = cp.chordId;

			
		}
		
		BigInteger pId = BigInteger.ZERO; 
		BigInteger sId = BigInteger.ZERO ;
		
		int progress = 2* (int)(Math.log(Network.size())/Math.log(2));
		
		do {
			cp.stabilizations++;
			succId = cp.successorList[0].getID();


			cp.stabilize(myNode);



			if (cp.predecessor == null ) continue;
			 pId = ((ChordProtocol)cp.predecessor.getProtocol(pid)).chordId;
			 sId = ((ChordProtocol)cp.successorList[0].getProtocol(pid)).chordId;
			if ( idInabC (cp.chordId, pId,sId) == false) {
				//TODO failure check mechanism for last ID 
				System.out.println("lookup failed" + progress);

				cp.successorList[0] = ((ChordProtocol) cp.successorList[0]
						.getProtocol(pid)).find_successor(cp.chordId);
			}
			progress-- ; 
			if ( progress == 0 ) {
				System.out.println("New start");
				System.exit(0);
				do {
					n = Network.get(generator.nextInt(Network.size()));

				} while (n == null || n.isUp() == false); // where is isUp() implemented ??
				progress = 2* (int)(Math.log(Network.size())/Math.log(2));
				cp.successorList[0] = ((ChordProtocol) n.getProtocol(pid)).find_successor(cp.chordId);
			}
			//special cases
			// last id of the network

			if (cp.chordId.compareTo(lastId) == 0) { 

			}
			
			 pId = ((ChordProtocol)cp.predecessor.getProtocol(pid)).chordId;
			 sId = ((ChordProtocol)cp.successorList[0].getProtocol(pid)).chordId;
			
		} while ( (cp.successorList[0].getID() != succId) && ( idInabC (cp.chordId, pId,sId)) ); 
			
		Node tmp = ((ChordProtocol)myNode.getProtocol(pid)).predecessor;
		cp.createFingerTable();
		cp.fixFingersNew();

		count++;
		
		ChordProtocol cpSucc = ((ChordProtocol)cp.successorList[0].getProtocol(pid));
		
		if (cpSucc.pcx_stale == false) {

			if ( (idInabC(cp.chordId, rValueId, cpSucc.chordId)) || (rValueId.compareTo(cpSucc.chordId) == 0 ) ) {
				//System.out.println("# node : From " + cp.successorList[0].getIndex() + " to " + myNode.getIndex());
				cpSucc.pcx_stale = true;
				cp.pcx_stale = false;
				cp.pcx_val = cpSucc.pcx_val;
				cp.pcx_vet = cpSucc.pcx_vet;
					
			}
		}
		
		try {  
			MD5Hash hash = new MD5Hash ("feed_num_of_sets");
			BigInteger targetId = hash.getHash();

			PCXRequestMessage message = new PCXRequestMessage (myNode,myNode,targetId); 
			EDSimulator.add(10, message, myNode, pid);  

	} catch (NoSuchAlgorithmException e) {
			System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
    }	
	
		
	}
	
	private boolean idInabC(BigInteger id, BigInteger a, BigInteger b) { 
		// also takes care of the circular nature of id space										
		if ((b.compareTo(a) == 1)) {
			if ((a.compareTo(id) == -1) && (id.compareTo(b) == -1)) {
				return true;
			}
			return false;
		} else {
			if ((a.compareTo(id) == 1) && (id.compareTo(b) == 1)) {
				return false;
			}
			return true;
		}
	}
}
