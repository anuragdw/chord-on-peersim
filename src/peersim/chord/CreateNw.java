/**
 * 
 */
package peersim.chord;

import peersim.core.*;
import peersim.config.Configuration;
import java.math.*;

/**
 * @author Andrea
 * 
 */
public class CreateNw implements Control {

	private int pid = 0;

	private static final String PAR_IDLENGTH = "idLength";

	private static final String PAR_PROT = "protocol";

	private static final String PAR_SUCCSIZE = "succListSize";

	public static int valueN = 0;

	int idLength = 0;

	int successorLsize = 0;

	int fingSize = 0;
	//campo x debug
	boolean verbose = false;

	/**
	 * 
	 */
	public CreateNw(String prefix) {
		pid = Configuration.getPid(prefix + "." + PAR_PROT);
		idLength = Configuration.getInt(prefix + "." + PAR_IDLENGTH); 
		successorLsize = Configuration.getInt(prefix + "." + PAR_SUCCSIZE); 
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see peersim.core.Control#execute()
	 */

	public boolean execute() {
		for (int i = 0; i < Network.size(); i++) {
			Node node = (Node) Network.get(i);
			ChordProtocol cp = (ChordProtocol) node.getProtocol(pid);
			cp.m = idLength;
			cp.succLSize = successorLsize;
			cp.varSuccList = 0;
			cp.chordId = new BigInteger(idLength, CommonState.r); 
			// CommonState.r is the source of randomness that can be used by all 
			cp.fingerTable = new Node[idLength];
			cp.successorList = new Node[successorLsize];
			cp.setNode(node); // TODO
		//	System.out.println("New Node created");

			//if ( i == 0 ) cp.rStream = true; 
		}
		NodeComparator nc = new NodeComparator(pid);
		Network.sort(nc);
		((ChordProtocol) Network.get(0).getProtocol(pid)).rStream = true;
		createFingerTable();
		//printSuccessorList();
		//printPredecessor();
	//	printFinger(999);
		//System.exit(0);
		//printFinger(0);
		//printFinger(2173);
		//printFinger(16);
		//printFinger(6);
		//printFinger(2);
		//printFinger(1);
		return false;
	}

	public Node findId(BigInteger id, int nodeOne, int nodeTwo) {
		if (nodeOne >= (nodeTwo - 1)) 
			return Network.get(nodeTwo);
		int middle = (nodeOne + nodeTwo) / 2;
		if (((middle) >= Network.size() - 1))
			System.out.print("ERROR: Middle is bigger than Network.size");
		if (((middle) <= 0))
			return Network.get(0);
		try {
			BigInteger middleId = ((ChordProtocol) ((Node) Network.get(middle))
					.getProtocol(pid)).chordId;
			
			BigInteger lowId;
			if (middle > 0)
				lowId = ((ChordProtocol) ((Node) Network.get(middle - 1))
						.getProtocol(pid)).chordId;
			else
				lowId = BigInteger.ZERO;
			
			if (id.compareTo(middleId) == 0
					|| ((id.compareTo(middleId) == -1) && (id.compareTo(lowId) == 1))) { // newId or lowId here ?? - newId
				return Network.get(middle);
			}
			
			
			if (id.compareTo(middleId) == -1) {
				if (middle > 0) return findId(id,nodeOne,middle-1); 
				return findId(id, nodeOne, middle);
			} else if (id.compareTo(middleId) == 1) {
				return findId(id, middle, nodeTwo);
			}
			return null;
		/*	BigInteger newId = ((ChordProtocol) ((Node) Network.get(middle))
					.getProtocol(pid)).chordId;
			BigInteger lowId;
			if (middle > 0)
				lowId = ((ChordProtocol) ((Node) Network.get(middle - 1))
						.getProtocol(pid)).chordId;
			else
				lowId = newId;
			BigInteger highId = ((ChordProtocol) ((Node) Network
					.get(middle + 1)).getProtocol(pid)).chordId;
			if (id.compareTo(newId) == 0
					|| ((id.compareTo(newId) == 1) && (id.compareTo(highId) == -1))) { // newId or lowId here ?? - newId
				return Network.get(middle);
			}
			if ((id.compareTo(newId) == -1) && (id.compareTo(lowId) == 1)) {
				if (middle > 0)
					return Network.get(middle - 1);
				else
					return Network.get(0);
			}
			if (id.compareTo(newId) == -1) {
				return findId(id, nodeOne, middle);
			} else if (id.compareTo(newId) == 1) {
				return findId(id, middle, nodeTwo);
			}
			return null;
			*/
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void printSuccessorList() {
		for (int i = 0; i < Network.size(); i++) { 
			Node node = (Node) Network.get(i);
			ChordProtocol cp = (ChordProtocol) node.getProtocol(pid); 
			System.out.println(" Successor list for ChordId " + cp.chordId + " (" + i + ")"); 
			for (int a = 0; a < successorLsize; a++) { // creating successor list
				if ( cp.successorList[a] != null ) 
					System.out.println( "     " + ((ChordProtocol) cp.successorList[a].getProtocol(pid)).chordId 
							+ " (" + cp.successorList[a].getIndex() + ")" ); 
				else System.out.println( " null " );	
		//		if (a + i < (Network.size() - 1))
		//			cp.successorList[a] = Network.get(a + i + 1);
		//		else
		//			cp.successorList[a] = Network.get(a); //some problem here, should be a + i + 1 - Network.size() 
			}
		}
	}

	public void printPredecessor() {
		for (int i = 0; i < Network.size(); i++) { 
			Node node = (Node) Network.get(i);
			ChordProtocol cp = (ChordProtocol) node.getProtocol(pid); 
			System.out.println(" Predecessor of ChordId " + cp.chordId + " (" + i + ")"); 
			
				if ( cp.predecessor != null ) 
					System.out.println( "     is " + ((ChordProtocol) cp.predecessor.getProtocol(pid)).chordId 
							+ " (" + cp.predecessor.getIndex() + ")" ); 
				else System.out.println( "     is null " );	
		//		if (a + i < (Network.size() - 1))
		//			cp.successorList[a] = Network.get(a + i + 1);
		//		else
		//			cp.successorList[a] = Network.get(a); //some problem here, should be a + i + 1 - Network.size() 
			
		}
	}

	private void printFingers() {
		for (int j = 0; j < Network.size(); j++) { 
			Node node = (Node) Network.get(j);
			ChordProtocol cp = (ChordProtocol) node.getProtocol(pid); 			
			System.out.println(" Finger Table list for ChordId " + cp.chordId + " (" + j + ")"); 
		
			for (int i = cp.fingerTable.length - 1; i > 0; i--) {
				if (cp.fingerTable[i] == null) {
					System.out.println("Finger " + i + " is null");
					continue;
				}
				if ((((ChordProtocol) cp.fingerTable[i].getProtocol(pid)).chordId)
						.compareTo(cp.chordId) == 0)
					break;
				System.out
						.println("Finger["
								+ i
								+ "] = "
								+ cp.fingerTable[i].getIndex()
								+ " chordId "
								+ ((ChordProtocol) cp.fingerTable[i]
										.getProtocol(pid)).chordId);
			}
		}
	}

	private void printFinger( int j) {
		//for (int j = 0; j < Network.size(); j++) { 
		
			Node node = (Node) Network.get(j);
			ChordProtocol cp = (ChordProtocol) node.getProtocol(pid); 			
			System.out.println(" Finger Table list for ChordId " + cp.chordId + " (" + j + ")"); 
		
			for (int i = cp.fingerTable.length - 1; i > 0; i--) {
				if (cp.fingerTable[i] == null) {
					System.out.println("Finger " + i + " is null");
					continue;
				}
				
					;
				System.out
						.println("Finger["
								+ i
								+ "] = "
								+ cp.fingerTable[i].getIndex()
								+ " chordId "
								+ ((ChordProtocol) cp.fingerTable[i]
										.getProtocol(pid)).chordId);
				if (cp.fingerTable[i] == cp.fingerTable[i-1])
					break;
			}
		//}
	}
		

	public void createFingerTable() {
		BigInteger idFirst = ((ChordProtocol) Network.get(0).getProtocol(pid)).chordId;
		BigInteger idLast = ((ChordProtocol) Network.get(Network.size() - 1)
				.getProtocol(pid)).chordId;
		for (int i = 0; i < Network.size(); i++) { // updating data for each node
			Node node = (Node) Network.get(i);
			ChordProtocol cp = (ChordProtocol) node.getProtocol(pid); 
			/* While creating the successor list, it must be noted that, we have to take special attention when the 
			   network is building up and the size of the network is less than successorLSize, in this case, the 
			   entries would be repeated 
			*/
			if (Network.size() == 1 ) {
				for (int a = 0; a < successorLsize; a++)
					cp.successorList[a] = null;
			}
			else {
				for (int a = 0; a < successorLsize; a++) { // creating successor list				
					if ( a >= Network.size() - 1 ) 
						cp.successorList[a] = cp.successorList[a-1] ; 				
					else if (a + i < (Network.size() - 1))
						cp.successorList[a] = Network.get(a + i + 1);
					else
						cp.successorList[a] = Network.get(a + i + 1 - Network.size()); 
					// changed from a to a + i + 1 - Network.size() ... working properly
				}
			}


			if (Network.size() == 1 ) 
				cp.predecessor = null ;
			else if (i > 0) 
				cp.predecessor = (Node) Network.get(i - 1);
			else
				cp.predecessor = (Node) Network.get(Network.size() - 1);

			int j = 0;
			for (j = 0; j < idLength; j++) {  // creating finger table
				BigInteger base;
				if (j == 0)
					base = BigInteger.ONE;
				else {
					base = BigInteger.valueOf(2);
					for (int exp = 1; exp < j; exp++) {
						base = base.multiply(BigInteger.valueOf(2));
					}
				}
				BigInteger pot = cp.chordId.add(base);
				if (pot.compareTo(idLast) == 1) { // if id exceeds last id
					pot = (pot.mod(idLast));  // take mod
					if (pot.compareTo(cp.chordId) != -1) { 
						break;
					}
					if (pot.compareTo(idFirst) == -1) {
						cp.fingerTable[j] = Network.get(0); // Network.get(Network.size() - 1); // pot lies b/w last and first node
						continue;
					}
				}
				cp.fingerTable[j] = findId(pot, 0, Network.size() - 1); // ##
			}
		}
	}
}
