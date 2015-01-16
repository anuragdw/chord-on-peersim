package peersim.chord;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.Random;

import peersim.config.Configuration;
import peersim.core.CommonState;
import peersim.core.Network;
import peersim.core.Node;
import peersim.dynamics.NodeInitializer;
import peersim.edsim.EDSimulator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;	


public class ChordInitializer implements NodeInitializer {

	private static final String PAR_PROT = "protocol";
	private static BigInteger lastId = BigInteger.ZERO;
	private static BigInteger firstId = BigInteger.ZERO;
	
	private BigInteger rValueId ;
	
	private static int count = 0;

	private int pid = 0;

	private ChordProtocol cp;

	public ChordInitializer(String prefix) {
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
		//try {  
		//	MD5Hash hash = new MD5Hash ("feed_num_of_sets");
		//	rValueId = hash.getHash();
  
		//} catch (NoSuchAlgorithmException e) {
		//		System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
		//}
		
	//	if(CommonState.getTime() >= 1700 ) System.out.println("mark");
		Random generator = new Random();
		//System.out.println("New Node created");

		cp.predecessor = null; // predeccessor not updated here
		// search a random node to join
		Node n;
		do {
			n = Network.get(generator.nextInt(Network.size()));
		} while (n == null || n.isUp() == false); // where is isUp() implemented ??
	//	System.out.println("Got n = " + n.getID());
		cp.setNode(myNode); //  TODO ADM
//		System.out.println(myNode.getID() + "");
		
		cp.m = ((ChordProtocol) n.getProtocol(pid)).m; // copying the nodeId length
		cp.chordId = new BigInteger(cp.m, CommonState.r); // give a random nodeId
		ChordProtocol cpRemote = (ChordProtocol) n.getProtocol(pid);

		//System.out.println("Call 1");
		Node successor = cpRemote.find_successor(cp.chordId);
		//System.out.println("Call 1 Over");
	//	System.out.println(successor.getIndex() + " = successor ");
		
		//System.out.println("Got s = " + successor.getID());
		
		cp.fails = 0;
		cp.stabilizations = 0;
		cp.varSuccList = cpRemote.varSuccList;
		cp.varSuccList = 0;
		cp.succLSize = cpRemote.succLSize;
		cp.successorList = new Node[cp.succLSize];
		cp.successorList[0] = successor;
		cp.fingerTable = new Node[cp.m];
		//cp.sequenceNums = new int[cp.m];
		long succId = 0;
		if (lastId == BigInteger.ZERO) { // if last id not set
			 lastId = ((ChordProtocol) Network.get(Network.size() - 1)
					.getProtocol(pid)).chordId;
		//	 System.out.println("dodo");
		} 
		if(lastId.compareTo(cp.chordId) < 0){
				lastId = cp.chordId;
			//	System.out.println("do");
			
		}
		
		if (firstId == BigInteger.ZERO) {
			firstId = ((ChordProtocol) Network.get(0).getProtocol(pid)).chordId;
			// System.out.println("dooddood");
		} 
		if(firstId.compareTo(cp.chordId) > 0){
			firstId = cp.chordId;
			//System.out.println("dood");
			
		}
		
		BigInteger pId = BigInteger.ZERO; 
		BigInteger sId = BigInteger.ZERO ;
		
		int progress = 2* (int)(Math.log(Network.size())/Math.log(2));
		
		do {
			cp.stabilizations++;
			succId = cp.successorList[0].getID();
		//	System.out.println("Got s = " + ((ChordProtocol)myNode.getProtocol(pid)).successorList[0].getID());
		//	System.out.println("Got node " + succId + " for " + getIndex (cp.chordId));
			cp.stabilize(myNode);
		//	System.out.println("Stablize done");
		/*	if ( ( ((ChordProtocol) cp.successorList[0].getProtocol(pid)).chordId.compareTo(cp.chordId) < 0 ) 
					 && (cp.chordId.compareTo(lastId) != 0) ) { // lookup has failed, try again*/
			if (cp.predecessor == null ) continue;
			 pId = ((ChordProtocol)cp.predecessor.getProtocol(pid)).chordId;
			 sId = ((ChordProtocol)cp.successorList[0].getProtocol(pid)).chordId;
			if ( idInabC (cp.chordId, pId,sId) == false) {
				//TODO failure check mechanism for last ID 
				System.out.println("lookup failed" + progress);
		//		System.out.println(cp.chordId + " = " + myNode.getIndex() );
			//	System.out.println(getIndex(lastId));
				//System.out.println(getIndex(firstId));
		//		System.out.println(((ChordProtocol)cp.predecessor.getProtocol(pid)).chordId);
			//	System.out.println(cp.chordId);
				//System.out.println(((ChordProtocol)cp.successorList[0].getProtocol(pid)).chordId);
			//	progress = 10; 
				cp.successorList[0] = ((ChordProtocol) cp.successorList[0]
						.getProtocol(pid)).find_successor(cp.chordId);
			}
			progress-- ; 
			if ( progress == 0 ) {
				System.out.println("New start");
				System.exit(0);
				do {
					n = Network.get(generator.nextInt(Network.size()));
					//System.out.println(n);
					//System.out.println(n.getID());
				} while (n == null || n.isUp() == false); // where is isUp() implemented ??
				progress = 2* (int)(Math.log(Network.size())/Math.log(2));
				cp.successorList[0] = ((ChordProtocol) n.getProtocol(pid)).find_successor(cp.chordId);
			}
			//special cases
			// last id of the network
		//	System.out.println("midway over");
			if (cp.chordId.compareTo(lastId) == 0) { 
		//		System.out.println("SuccId = "+ cp.successorList[0].getIndex());
		//		System.out.println( ((ChordProtocol)cp.successorList[0].getProtocol(pid)).chordId + "Succ chordId =" );
		//		System.out.println( firstId + "");
			//	cp.successorList[0] = Network.get(0); // not always true
				// TODO : Added by me
				//System.out.println("RNP = " + ((ChordProtocol) Network.get(0).getProtocol(pid)).predecessor.getID());
				//System.out.println("LN = " + (Network.get(Network.size()-1).getID()));

//				((ChordProtocol) Network.get(0).getProtocol(pid)).notifys(myNode);
	//			System.out.println(Network.size() + " TE");
		//		break;
			}
			
			 pId = ((ChordProtocol)cp.predecessor.getProtocol(pid)).chordId;
			 sId = ((ChordProtocol)cp.successorList[0].getProtocol(pid)).chordId;
			
		} while ( (cp.successorList[0].getID() != succId) && ( idInabC (cp.chordId, pId,sId)) ); 
			//	|| ( (cp.chordId.compareTo(lastId) != 0) && ((ChordProtocol) cp.successorList[0].getProtocol(pid)).chordId
				//		.compareTo(cp.chordId) < 0)) ; // TODO
	//	System.out.println("Second while pver");
		Node tmp = ((ChordProtocol)myNode.getProtocol(pid)).predecessor;
	//	System.out.println("Pre = " + tmp.getID());
	//	System.out.println("Pre Suc = " + ((ChordProtocol)tmp.getProtocol(pid)).successorList[0].getID());
//		
	//	cp.printFingers();
		cp.createFingerTable();
	//	System.out.println("cft");
	//	if (count == 40 ) cp.printFingers();
		cp.fixFingersNew();
	//	System.out.println("ff");
		count++;
		
		ChordProtocol cpSucc = ((ChordProtocol)cp.successorList[0].getProtocol(pid));
		
		if (cpSucc.rValue == true) {
			System.out.println("#" + CommonState.getTime() + " hel " + myNode.getID());
			if ( (idInabC(cp.chordId, rValueId, cpSucc.chordId)) || (rValueId.compareTo(cpSucc.chordId) == 0 ) ) {
				System.out.println("# rValue : From " + cp.successorList[0].getID() + " to " + myNode.getID());
				cpSucc.rValue = false;
				cp.rValue = true;
				cp.fracValue = cpSucc.fracValue;
				cp.numOfSets = cpSucc.numOfSets;
				// copy reglist
				/*for (Map.Entry<BigInteger, RegListObj> entry : cpSucc.regList.entrySet()) {
					   // System.out.println(entry.getKey() + "/" + entry.getValue());
						RegListObj obj = entry.getValue();
						
							cp.regList.put(entry.getKey(), obj );
							//System.out.println("Send..."); 
										
					}*/
				
				//BigInteger tmpid = cpSucc.chordId;
				//RegListObject tmpentry = new RegListObject( tmpid, CommonState.getTime() + 60000,cp.successorList[0] , 
							//true, -1 ); 
				//cp.regList.put( tmpid, tmpentry);
				//cp.expiryTime = cpSucc.expiryTime;	
			}
		}


		if (cpSucc.setRoot == true) {
			Iterator<Map.Entry<Integer, Vector<FeedIndexEntry>>> it = cpSucc.feedIndexPool.entrySet().iterator();
			while(it.hasNext()) {
					Map.Entry<Integer, Vector<FeedIndexEntry>> tmpp = it.next();
					try {  
							MD5Hash hash = new MD5Hash ("feed_set" + tmpp.getKey());
							rValueId = hash.getHash();

		
					} catch (NoSuchAlgorithmException e) {
				System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
			}
				//	rValueId = tmpp.getKey(); // cpSucc.srootId;
					//System.out.println("#" + CommonState.getTime() + " hel " + myNode.getID());
					if ( (idInabC(cp.chordId, rValueId, cpSucc.chordId)) || (rValueId.compareTo(cp.chordId) == 0 ) ) {
						System.out.println("# setRoot : From " + cp.successorList[0].getID() + " to " + myNode.getID());
						if (cp.pint == true ) {
							try {
			 
								File filet = new File("ghar/logdump/setRoot" );
				 
								// if file doesnt exists, then create it
								if (!filet.exists()) {
									filet.createNewFile();
								}
				 
								FileWriter fwt = new FileWriter(filet.getAbsoluteFile(), true);
								BufferedWriter bwt = new BufferedWriter(fwt);
								bwt.write(CommonState.getTime() + ": feed_setX : from " + cp.successorList[0].getID() + " to " + myNode.getID()+ "\n");
								//bwt.flush();
								bwt.close();
				 
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						//it.remove();//cpSucc.setRoot = false;
						cp.setRoot = true;
						cp.srootId = rValueId;

						cp.numIndex = cpSucc.numIndex;

						// copy indexList
						Vector <FeedIndexEntry> tmpList = new Vector<FeedIndexEntry> (5);
						for (int iii=0; iii<tmpp.getValue().size(); iii++) {
							FeedIndexEntry sourceEntry = new FeedIndexEntry(tmpp.getValue().get(iii));
							tmpList.add(sourceEntry);
						}
						cp.feedIndexPool.put(tmpp.getKey(), tmpList);
						tmpp.getValue().clear();
					
					}
			}
			//if ( cpSucc.indexPool.isEmpty() == true ) cpSucc.setRoot = false;

				cpSucc.setRoot = false;
				for ( Map.Entry<Integer, Vector<FeedIndexEntry>> en : cpSucc.feedIndexPool.entrySet()) {
					if (en.getValue().isEmpty() == false ) {
						cpSucc.setRoot = true;
						break;
					}
				}
			



		}

		if (cpSucc.numRoot == true) {
			rValueId = cpSucc.nrootId;
			//System.out.println("#" + CommonState.getTime() + " hel " + myNode.getID());
			if ( (idInabC(cp.chordId, rValueId, cpSucc.chordId)) || (rValueId.compareTo(cp.chordId) == 0 ) ) {
				//System.out.println("# numRoot : From " + cp.successorList[0].getID() + " to " + myNode.getID());
				//System.out.println ( rValueId + " - original " );
				//System.out.println ( cp.chordId + " - new ");
				//System.out.println ( cpSucc.chordId + " - old ");
				cpSucc.numRoot = false;
				cp.numRoot = true;
				cp.nrootId = rValueId;

				cp.expiryTime = cpSucc.expiryTime;

				cp.cacheSet = new Set(cpSucc.cacheSet);
				if (cp.pint == true ) {
					try {
	 
						File filet = new File("ghar/logdump/numRoot" );
		 
						// if file doesnt exists, then create it
						if (!filet.exists()) {
							filet.createNewFile();
						}
		 
						FileWriter fwt = new FileWriter(filet.getAbsoluteFile(), true);
						BufferedWriter bwt = new BufferedWriter(fwt);
						bwt.write(CommonState.getTime() + ": numRoot - from " + cp.successorList[0].getID() + " to " + myNode.getID()+ "\n");
						//bwt.flush();
						bwt.close();
		 
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				BigInteger tmpid = cpSucc.chordId;
				RegListObject tmpentry = new RegListObject( tmpid, CommonState.getTime() + cp.regListET ,cp.successorList[0] , true, -1 ); 
				cp.regList.put( tmpid, tmpentry);
				//cp.expiryTime = cpSucc.expiryTime;	
			}
		}
		
		//try {  
				//MD5Hash hash = new MD5Hash ("feed_num_of_sets");
				//BigInteger targetId = hash.getHash();
				//FindSetMessage message = new FindSetMessage (myNode,myNode,targetId);
				//debug(1,"FindSetMessage Sent");
				//EDSimulator.add(1, message, myNode, pid); 
				//activeSet = -1;
				/* message send to self internally */ 
				StreamMessage message = new StreamMessage ();
				EDSimulator.add(10, message, myNode, pid); 
			
				//System.out.println(CommonState.getTime() + " FindNMessage Sent .. "); 
		//	} catch (NoSuchAlgorithmException e) {
		//			System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
		  //  	}	
		
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
