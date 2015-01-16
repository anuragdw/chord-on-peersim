package peersim.chord;

import peersim.config.Configuration;
import peersim.core.Network;
import peersim.core.Node;
import peersim.core.CommonState;
import peersim.edsim.EDProtocol;
import peersim.transport.Transport;
import peersim.edsim.EDSimulator;

import java.math.*;
import java.security.*;
import java.util.*;
import java.lang.Integer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;	



/**
 * @author Andrea
 * 
 */
public class ChordProtocol implements EDProtocol {

	private static final String PAR_TRANSPORT = "transport"; // to get configuration parameter

	private Parameters p; // TODO
  
	private int[] lookupMessage; // array to store number of hops encountered by lookup messages originating at this node 

	public int index = 0;  // TODO

	public Node predecessor;  // predecessor node
	
	private Node node; // this node

	public Node[] fingerTable; // list of nodes in finger table, # = chorID length

	public Node[] successorList; // list of nodes in successor table, # = succLSize

	public BigInteger chordId; // id of node 

	public int m; // TODO

	public int succLSize;  // length of successor list

	public String prefix; // TODO 

	private int next = 0;  // TODO
	
	private BigInteger maxId;
	
	private BigInteger minId;
	
	

	
	
	// Added 
	//public int[] sequenceNums; 
	
	// Variables for general node	
	public int numOfSets;




	public int nValueET = 120000000;  //  TODO 
	public int cacheET = 500;  //  TODO  250000 // not used
	
	public boolean  reqActive = false;
public boolean  reqActive2 = false;
	
	private long [] hopCountArray ; // = 0;
	public int hopCountNum = 0; 
	 
	public int hopCount = 0;
	// Variables for stream root node
	public boolean rStream = false;

	// Variables for set root node
	public boolean rSet = false;
	
	// Variables for N root node
	public boolean rValue = false;
	private int initNumSets = -1;
	private long lastUpdateTime; 
	public double fracValue;
	private double nDecayRate = 0.000000000005; //  0.0005; == correct
	
	private long lastReqTime ;
	private long reqTimeOut = 50;
private long lastReqTime2 ;
	private long reqTimeOut2 = 50;
	
	public long startTime = 0;
	public long endTime = 0; 
	public long totalTime = 0;
	public int totalMsg = 0;
	
	public int lastUpdatedVal = 0;
	
	public int msgCount = 0;
	public int uMsgCount = 0;
	public int tm = 0;
	public int um = 0;
	public int cm = 0;
	public int dm = 0;
	public int pm = 0;
	public int rm =0;
	
	
	


// TOREM
	

	

	public boolean anchor = false; 
	private BigInteger anchorId; 

	 

	private UpdateTable[] table; 
	
	private int numEntries = 0;
	//tableNode;
	//private boolean[] tableResolved;
	//private BigInteger[] tableId ;
 // TOREM

	// campo x debug
	public int currentNode = 0;  // TODO

	public int varSuccList = 0;   // temp variable 

	public int stabilizations = 0; // count of number of stabilizations run on this node 

	public int fails = 0;  // count of number of failures on this node

	

	// Index Start 


	
	public Map<BigInteger, RegListObject> indexRegList;

	private Vector<FeedIndexEntry> activeIndexList;
	private Vector<Integer> reqSet;

	public boolean root; 
	


	// PCX Start

	public int pcx_val = -1;
	public boolean pcx_stale = true;
	private long pcx_et = 60000; //(600000/1);
	public long pcx_vet = 0;
	private boolean pcx_ra = false;
	private Map<BigInteger, RegListObject> pcx_list; 
	private long pcx_start = 0;
	private long pcx_end = 0;
	public int pcx_hop = 0;

	// Hash table to store key-value pairs 
	
	private HashMap<BigInteger, Integer> lIndexList;
	private HashMap<BigInteger, CacheObj> cIndexList;
	public int sc = 0;


	// CUP Start 

	public Map<BigInteger, CUPListObject> cup_list;
	private long cup_start = 0;
	public boolean cup_stale = true;
	public long cup_vet = 0;
	private long cup_et = 60000;
	private boolean cup_ra = false;
	private int cup_req = 0;
	public int cup_hop = 0;
	public int cup_val = 0;
	public int cup_up = 0;
	public Node cup_node ;
	private int cup_chance = 0;
	public int cup_dreg = 0;


	public int p2d2_up =0;
	public int p2d2_hop = 0;
	private long p2d2_start = 0;

	// NEW 

	private State state = State.IDLE;
	public Set cacheSet = new Set();
	
	public Map<BigInteger, RegListObject> regList; // TODO N
	public HashMap<Integer, Vector<FeedIndexEntry>> feedIndexPool;

	public Vector<FeedIndexEntry> localIndexList ;
	public Vector<FeedIndexEntry> tmpLocalIndexList;
	//public HashMap<BigInteger,Vector<FeedIndexEntry>> indexPool; 

	public long expiryTime = 0;
	public boolean STREAM = false;
	private int setET = 30000;
	private int timeOut = 10000;
	private int timeOut9 = 9000;
	private int listSize = 5;
	private int debugLevel = 15;
	public int regListET = 120000; // an entry in regList is removed after regListET time
	private int regiListET = 150000000; // an entry in regList is removed after regListET time
	private int indexListET = 20000;

	public boolean streamAvail = false;
	public int maxConn = 5;
	public int numConn = 0;
	public int numIndex = 0;

	boolean sReqActive = false;

	private int activeSet = -1;
	public Map<BigInteger, Node> connectionList; // TODO N

	public boolean numRoot = false;
	public boolean setRoot = false;
	public boolean streamRoot = false;
	public BigInteger nrootId = BigInteger.ZERO;
	public BigInteger srootId = BigInteger.ZERO;

	public Map<Integer, Long> waitList;
//	public Vector<Integer> setWL;
//	public Vector<Integer> indexWL;
	public long mCount = 0;
	private long glimit = 0;
	

	//PrintWriter writer; 
	FileWriter fw;
	BufferedWriter bw;

	public long pStart = 0;
	public long pEnd = 0; 
	public long joinTime = 0;
	public long connTime = 0;

	public boolean pint = false;

	public Integer mySet = -1;
	public Integer finalSet = -1;
	

	// NEW 

	public State getState() {
		return state;
	}

	public void debugf (int level, String str) {
		if(pint == true ) {
			try {
				bw.write(CommonState.getTime() + ": " + str + "\n");
				bw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void debug (int level, String text, Object... arguments) {
		//if(CommonState.getTime() > 60000) {			
		if (level >= debugLevel ) {
			System.out.println();
			System.out.format("#" + CommonState.getTime() + ": (" + node.getID() + ") " + " [" + node.getIndex() + "] " + text + "\n", arguments);
		}
		//}
	}

	private void printSet() {
		//System.out.print("# Set -> [ ");
		for (int i = 0; i< cacheSet.getCount() ; i++) {
		//	System.out.print( cacheSet.get(i) + ", ");
		}
		//System.out.println(" ] ");
	}

	public void printIndexList(Vector<FeedIndexEntry> list) {
		//System.out.println("Node Index | Expiry Time | Node Id ( numIndex = " + numIndex +" ) ");
		for (int i = 0; i<list.size() ; i++) {
		//	System.out.println( "" + list.get(i).getNode().getID() + "       | " + list.get(i).getExpiryTime() + "       | " + list.get(i).getId() );
		}
		//System.out.println("____________________________________________________________");
	}
	
	public void printTable () {

		if(numEntries != 0) {
			System.out.println("Printing for " + chordId + "(" + getIndex(chordId) + ")" );
			for (int i=0; i< numEntries ; i++ ) {
				//System.out.println(" id : " + table[i].getId() + "(" + getIndex(table[i].getId()) + ") 
				//isExpired : " + table[i].getIsExpired() + " isServed : " + table[i].getIsServed() );
			}
		}
	}   

	/**
	 * 
	 */
	public ChordProtocol(String prefix)  {
		this.prefix = prefix;
		lookupMessage = new int[1];
		lookupMessage[0] = 0; 
		p = new Parameters();
		p.tid = Configuration.getPid(prefix + "." + PAR_TRANSPORT);
		numOfSets = -1; // ABM 
		lastUpdateTime = 0;
		table = new UpdateTable[100] ; // ABM
	//	System.out.println("New Node created");
		
		minId = BigInteger.ZERO;
		maxId = BigInteger.valueOf(2);
		for (int exp = 1; exp < m; exp++) {
			maxId = maxId.multiply(BigInteger.valueOf(2));
		}
		

		activeIndexList = new Vector<FeedIndexEntry>(5);

		root = false;
		
		

		indexRegList = new HashMap<BigInteger, RegListObject> ();

		lIndexList = new HashMap<BigInteger, Integer> ();
		cIndexList = new HashMap<BigInteger, CacheObj> (); 


		//PCX
		pcx_list = new HashMap<BigInteger, RegListObject> ();

		cup_list = new HashMap<BigInteger, CUPListObject> ();
		// New

		regList = new HashMap<BigInteger, RegListObject> ();
		feedIndexPool = new HashMap<Integer, Vector<FeedIndexEntry> >();
		connectionList = new HashMap<BigInteger, Node> ();
		//localIndexList = new Vector<FeedIndexEntry> (listSize);
		//indexPool = new HashMap<BigInteger,Vector<FeedIndexEntry>> ();
		tmpLocalIndexList = new Vector<FeedIndexEntry> (listSize);
		reqSet = new Vector<Integer> (100);
		waitList = new HashMap<Integer, Long>();

		
	}

	// ADM TODO 
	public void setNode(Node n) {
		this.node = n;
	}
	
	public int getN(){
		///if (expiryTime < CommonState.getTime())
			return numOfSets;
	//	else 
		//	return -1;
		
	}
	
	public int getUpdatedN() { //  to be used only on rValue nodes
		if (numOfSets == -1) return -1 ; 
		fracValue -= (CommonState.getTime() - lastUpdateTime)*nDecayRate;
		if (fracValue < 1 )
			fracValue = 1; 
		numOfSets = (int)Math.ceil(fracValue);
		lastUpdateTime = CommonState.getTime() ;
		return numOfSets;
		
	}
	
	public void updateN( int num) {
		if (num == 0 ) {
			fracValue += 1;
			getUpdatedN();
		} else {
			fracValue = num;
			numOfSets = num; 
			getUpdatedN();
		}
			
	}

	public void log (String text, Object... arguments) {
		//System.out.format(CommonState.getTime() + ": " + text + "\n", arguments);
	}
	
	public void logg (String text, Object... arguments) {
		System.out.format("#" + CommonState.getTime() + ": " + text + "\n", arguments);
	}

	public void fail (String text, Object... arguments) {
		System.err.format("#" + CommonState.getTime() + ": " + text + "\n", arguments);
	}

	public long getIndex( BigInteger id ) {
		for ( int i=0; i<Network.size() ; i++ ) {
			if ( id.compareTo( ((ChordProtocol) Network.get(i).getProtocol(p.pid)).chordId ) == 0 )
				return Network.get(i).getIndex();
		}
		return -1;
	}
	
	public void printRegList() {
		
		logg ("regList for "+ this.node.getIndex());
	
		for (Map.Entry<BigInteger, RegListObject> entry : regList.entrySet()) {
			   // System.out.println(entry.getKey() + "/" + entry.getValue());
				RegListObject obj = entry.getValue();
				logg("index = "+ obj.getNode().getIndex() + " eTime = " + obj.getExpiryTime());
				if ( obj.getExpiryTime() > CommonState.getTime() ) {
					
				}				
		}
		
	}

public void printCUPList() {
		
		logg ("cup_list for "+ this.node.getIndex());
	
		for (Map.Entry<BigInteger, CUPListObject> entry : cup_list.entrySet()) {
			   // System.out.println(entry.getKey() + "/" + entry.getValue());
				CUPListObject obj = entry.getValue();
				System.out.println(" # id = "+ obj.getNode().getID() + " " + obj.getIBit() );
								
		}
		System.out.println(" ############################");
		
	}

        //

	public int getLILSize () {
		return lIndexList.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see peersim.edsim.EDProtocol#processEvent(peersim.core.Node, int,
	 *      java.lang.Object)
	 */

	/* this is to be run when a the event in the event queue is scheduled.
	   the event is added by the function 
		send(Node src, Node dest, Object msg, int pid) 
	   of the UniformRandomTransport Class	 
	*/ 
	public void processEvent(Node node, int pid, Object event) {
		// process requests according to the routing table of the node
		p.pid = pid;
		currentNode = node.getIndex();
		mCount ++;

		// InitMessage( BigInteger target, Node node) 
		
		if (event.getClass() == InitMessage.class) {

			InitMessage message = (InitMessage) event;
			BigInteger target = message.getTarget();  

			Transport t = (Transport) node.getProtocol(p.tid);	

			if (target == ((ChordProtocol) node.getProtocol(pid)).chordId) {
				
				debug(1,"InitMessage Received");
				if (pint == true ) {
					try {
 
						File filet = new File("ghar/logdump/setRoot" );
		 
						// if file doesnt exists, then create it
						if (!filet.exists()) {
							filet.createNewFile();
						}
		 
						FileWriter fwt = new FileWriter(filet.getAbsoluteFile(), true);
						BufferedWriter bwt = new BufferedWriter(fwt);
						bwt.write(CommonState.getTime() + ": feed_set0 @ " + this.node.getID() + "\n");
						//bwt.flush();
						bwt.close();
		 
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				BigInteger tmpId = ((ChordProtocol) message.getNode().getProtocol(pid)).chordId;

				FeedIndexEntry sourceEntry = new FeedIndexEntry(tmpId, message.getNode(), 10000 + indexListET);
				Vector <FeedIndexEntry> tmpList = new Vector <FeedIndexEntry> (listSize);
				tmpList.add(sourceEntry);
				feedIndexPool.put(0,tmpList);
				// jan6
				//localIndexList.add(sourceEntry);
				setRoot = true;
				srootId = target;
				//feedIndexPool.put(0,tmpList);
				//printIndexList(localIndexList);
				numIndex = 1;
				
			}
			
			
			
			if (target != ((ChordProtocol) node.getProtocol(pid)).chordId) {
				
				Node tmPre = ((ChordProtocol)node.getProtocol(pid)).predecessor;
				if (tmPre == null ) tmPre = find_predecessor(((ChordProtocol)node.getProtocol(pid)).chordId.subtract(BigInteger.ONE));
				// TODO quick fix for now
				if ( idInabC(target,((ChordProtocol)tmPre.getProtocol(pid)).chordId,chordId) ) {
					
					
					debug(5,"InitMessage Received added first setRoot");
					if (pint == true ) {
						try {
	 
							File filet = new File("ghar/logdump/setRoot" );
			 
							// if file doesnt exists, then create it
							if (!filet.exists()) {
								filet.createNewFile();
							}
			 
							FileWriter fwt = new FileWriter(filet.getAbsoluteFile(), true);
							BufferedWriter bwt = new BufferedWriter(fwt);
							bwt.write(CommonState.getTime() + ": feed_set0 @ " + this.node.getID() + "\n");
							//bwt.flush();
							bwt.close();
			 
						} catch (IOException e) {
							e.printStackTrace();
						}	
					}
					BigInteger tmpId = ((ChordProtocol) message.getNode().getProtocol(pid)).chordId;

					FeedIndexEntry sourceEntry = new FeedIndexEntry(tmpId, message.getNode(),10000 + indexListET);
					Vector <FeedIndexEntry> tmpList = new Vector <FeedIndexEntry> (listSize);
					tmpList.add(sourceEntry);
					feedIndexPool.put(0,tmpList);
					//localIndexList.add(sourceEntry);
					// jan6
					numIndex = 1;
					setRoot = true;
					srootId = target;
					//printIndexList(localIndexList);

				} else {
					Node dest = find_nearest_neighbour(target,node);
					t.send(node, dest, message, pid);
				}	
			}
		
		}

		// StreamMessage ()

		if (event.getClass() == StreamMessage.class) { 
		
			STREAM = true;
			pStart = CommonState.getTime(); 
			Transport t = (Transport) node.getProtocol(p.tid);
			if (pint == true ){	
				try {
	 
					File file = new File("ghar/logdump/node" + this.node.getID() );
	 
					// if file doesnt exists, then create it
					if (!file.exists()) {
						file.createNewFile();
					}
	 
					fw = new FileWriter(file.getAbsoluteFile(), true);
					bw = new BufferedWriter(fw);
					bw.write(CommonState.getTime() + ": New file created \n");
					bw.flush();
					//bw.close();
	 
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			debug(3,"StreamMessage Received");
			debugf(3,"StreamMessage Received");
			
			
			try {  
				MD5Hash hash = new MD5Hash ("feed_num_of_sets");
				BigInteger targetId = hash.getHash();
				FindSetMessage message = new FindSetMessage (node,node,targetId);

				//state = State.WAIT4S;
				//Node dest = find_nearest_neighbour(targetId,node);
				//t.send(node, dest, message, pid);
				//reqActive = true; // TODO
				//TimeOutSetMessage nMsg = new TimeOutSetMessage(targetId, true);
				//EDSimulator.add(timeOut, nMsg, node, pid); 

				debug(1,"FindSetMessage Sent");
				debugf(1,"FindSetMessage Sent");
				EDSimulator.add(1, message, node, pid); 
				activeSet = -1;
				/* message send to self internally */ 
				
				//System.out.println(CommonState.getTime() + " FindNMessage Sent .. "); 
			} catch (NoSuchAlgorithmException e) {
					System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
		    	}	
			
		}
		

		// FindSetMessage(Node source, Node from, BigInteger targetId)

		if (event.getClass() == FindSetMessage.class) {
			
			//debug(1,"FindSetMessage Received");
			cm++;			
			tm++;

			FindSetMessage message = (FindSetMessage) event;	
			if(message.getSource() == this.node ) { 
				p2d2_start = CommonState.getTime();  
			
			}
		
			BigInteger target = message.getTargetId();			
			Transport t = (Transport) node.getProtocol(p.tid);

			if (target == ((ChordProtocol) node.getProtocol(pid)).chordId) {

				
				if (message.getSource() != this.node) { // to guard against those messages 
									// that may have originated at the node itself
					// make entry in table 
					BigInteger tmpId = ((ChordProtocol) message.getFrom().getProtocol(pid)).chordId;
					RegListObject tmpEntry = new RegListObject( tmpId, CommonState.getTime() + regListET, message.getFrom(), true, -1 ); 
					regList.put( tmpId, tmpEntry);	
				} 

				p2d2_start =0;
				if (numRoot == false) {
					
					numRoot = true;
					nrootId = target;
					
					//System.exit(0);
					
					if ( this.cacheSet.getCount() <= 0 ) {
						cacheSet.add(0);
						debug(5, " cache count = " + this.cacheSet.getCount() );
						expiryTime = 10000 + setET;
						//System.exit(0);
					}
					if (pint == true ) {
						try {
	 
							File filet = new File("ghar/logdump/numRoot" );
			 
							// if file doesnt exists, then create it
							if (!filet.exists()) {
								filet.createNewFile();
							}
			 
							FileWriter fwt = new FileWriter(filet.getAbsoluteFile(), true);
							BufferedWriter bwt = new BufferedWriter(fwt);
							bwt.write(CommonState.getTime() + ": num_of_sets @ " + this.node.getID() + "\n");
							bwt.write(" Set ( " + expiryTime + " ) -> [ ");
							for (int i = 0; i< cacheSet.getCount() ; i++) {
								bwt.write( cacheSet.get(i) + ", ");
							}
							bwt.write(" ] ");
							//bwt.flush();
							bwt.close();
			 
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

				if ( this.cacheSet.getCount() <= 0 ) {
					System.out.println("ERROR! Root node does not has cache list. ABORTING!");
					System.exit(0);
				}
				
				if (expiryTime < CommonState.getTime()) 
					expiryTime = CommonState.getTime() + setET;

				ReturnSetMessage newMsg = new ReturnSetMessage ( new Set(this.cacheSet), expiryTime ); //CommonState.getTime() + setET);
				if (message.getSource() != this.node) {
					t.send(node, message.getFrom(), newMsg, pid);
				} else {
					EDSimulator.add(1, newMsg, message.getFrom(), pid);		
				} 
				
				debug(1,"ReturnSetMessage Sent");
				//printSet();

				
			}
			
			if (target != ((ChordProtocol) node.getProtocol(pid)).chordId) {
				
				Node tmPre = ((ChordProtocol)node.getProtocol(pid)).predecessor;
				if (tmPre == null ) tmPre = find_predecessor(((ChordProtocol)node.getProtocol(pid)).chordId.subtract(BigInteger.ONE));
				// TODO quick fix for now
				if ( idInabC(target,((ChordProtocol)tmPre.getProtocol(pid)).chordId,chordId) ) {

					if (message.getSource() != this.node) { // to guard against those messages 
										// that may have originated at the node itself
						// make entry in table 
						BigInteger tmpId = ((ChordProtocol) message.getFrom().getProtocol(pid)).chordId;
						RegListObject tmpEntry = new RegListObject( tmpId, CommonState.getTime() + regListET, message.getFrom(), true, -1 ); 
						regList.put( tmpId, tmpEntry);	
					} 
					p2d2_start = 0;
					if (numRoot == false) {
					
						numRoot = true;
						nrootId = target;
					
						if ( this.cacheSet.getCount() <= 0 ) {
							cacheSet.add(0);
							debug(5, " cache count = " + this.cacheSet.getCount() );
							expiryTime = 10000 + setET;
							debug(50, "from scratch");
							//System.exit(0);
						} else {	
							debug(50, "from cache count = " + this.cacheSet.getCount());
							System.exit(0);
						}
							
						if (pint == true ) {
							try {
	 
								File filet = new File("ghar/logdump/numRoot" );
				 
								// if file doesnt exists, then create it
								if (!filet.exists()) {
									filet.createNewFile();
								}
				 
								FileWriter fwt = new FileWriter(filet.getAbsoluteFile(), true);
								BufferedWriter bwt = new BufferedWriter(fwt);
								bwt.write(CommonState.getTime() + ": num_of_sets @ " + this.node.getID() + "\n");
								bwt.write(" Set ( " + expiryTime + " ) -> [ ");
								for (int i = 0; i< cacheSet.getCount() ; i++) {
									bwt.write( cacheSet.get(i) + ", ");
								}
								bwt.write(" ] \n ");
								//bwt.flush();
								bwt.close();
				 
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}

					if ( this.cacheSet.getCount() <= 0 ) {
						System.out.println("ERROR! Root node does not has cache list. ABORTING!");
						System.exit(0);
					}

					if (expiryTime < CommonState.getTime()) 
					expiryTime = CommonState.getTime() + setET;

					ReturnSetMessage newMsg = new ReturnSetMessage ( new Set(this.cacheSet), expiryTime ) ; // CommonState.getTime() + setET);
					if (message.getSource() != this.node) {
						t.send(node, message.getFrom(), newMsg, pid);
					} else {
						EDSimulator.add(1, newMsg, message.getFrom(), pid);		
					} 
					//debug(1,"ReturnSetMessage Sent");
					//printSet();
					
				} else { 					
					
					if ( this.expiryTime > CommonState.getTime() ) {
					
						
						if (message.getSource() != this.node) { // to guard against those messages 
											// that may have originated at the node itself
							// make entry in table 
							BigInteger tmpId = ((ChordProtocol) message.getFrom().getProtocol(pid)).chordId;
							RegListObject tmpEntry = new RegListObject( tmpId, CommonState.getTime() + regListET, message.getFrom(), true, -1 ); 
							regList.put( tmpId, tmpEntry);	
						}
							
							ReturnSetMessage newMsg = new ReturnSetMessage ( new Set(this.cacheSet),  expiryTime ); //CommonState.getTime() + setET);
							if (message.getSource() != this.node) {
								t.send(node, message.getFrom(), newMsg, pid);
							} else {
								EDSimulator.add(1, newMsg, message.getFrom(), pid);		
							} 
							//debug(1,"ReturnSetMessage Sent");
							//printSet();
	

					} else {

							if(reqActive == false) { 
								// if a previous request has not been made
								FindSetMessage newMsg = new FindSetMessage ( message.getSource(), node, target); 				
								state = State.WAIT4S;
								Node dest = find_nearest_neighbour(target,node);
								t.send(node, dest, newMsg, pid);
								//debug(1,"FindSetMessage Forwarded");
								reqActive = true; // TODO
								
								//lastReqTime = CommonState.getTime();
								
								//startTime = CommonState.getTime() ; // not considering failures right now
								if(message.getSource() == this.node) {
									TimeOutSetMessage nMsg = new TimeOutSetMessage(target, true);
									EDSimulator.add(timeOut, nMsg, node, pid);
								} else {
									TimeOutSetMessage nMsg = new TimeOutSetMessage(target, false);
									EDSimulator.add(timeOut9, nMsg, node, pid);
								}

							/*	try {
 
									File filet = new File("ghar/logdump/node" + this.node.getID() );
					 
									// if file doesnt exists, then create it
									if (!filet.exists()) {
										filet.createNewFile();
									}
					 
									FileWriter fwt = new FileWriter(filet.getAbsoluteFile(), true);
									BufferedWriter bwt = new BufferedWriter(fwt);
									bwt.write(CommonState.getTime() + ": GetSetMessage forwarded from " + message.getSource().getID() + " \n");
									bwt.flush();
									bwt.close();
					 
								} catch (IOException e) {
									e.printStackTrace();
								}*/	
								
							}
							
							if (message.getSource() != this.node) { // to guard against those messages 
												// that may have originated at the node itself
								// make entry in table 
								BigInteger tmpId = ((ChordProtocol) message.getFrom().getProtocol(pid)).chordId;
								RegListObject tmpEntry = new RegListObject( tmpId, CommonState.getTime() + regListET, message.getFrom(), false, -1 ); 
								regList.put( tmpId, tmpEntry);	
								
							}
					}
				}

			}

		}
	

		// TimeOutSetMessage( BigInteger targetId)

		if (event.getClass() == TimeOutSetMessage.class) {
			mCount -- ;
			TimeOutSetMessage message = (TimeOutSetMessage) event;
			if ( message.getIsSource() == false ) {
				if ( ( (CommonState.getTime() - timeOut9 ) > this.expiryTime ) && (state == State.WAIT4S)) {
					debug(5,"TOS9 for " + this.expiryTime);
					for (Map.Entry<BigInteger, RegListObject> entry : regList.entrySet()) {

						RegListObject obj = entry.getValue();
						if ( obj.getReqStatus() == false ) {
							obj.setReqStatus(true);
							//t.send(node, obj.getNode(), message, pid );
							regList.put(entry.getKey(), obj );
						}				
					}
					reqActive = false;
				}
			}

			else if ( ( (CommonState.getTime() - timeOut ) > this.expiryTime ) && (state == State.WAIT4S)) {
				debug(5,"TOS for " + this.expiryTime);
				

				BigInteger target = message.getTargetId();
				Transport t = (Transport) node.getProtocol(p.tid);

				FindSetMessage newMsg = new FindSetMessage ( node, node, target); 
				EDSimulator.add(1, newMsg, node, pid); 				
				//state = State.WAIT4S;
				//Node dest = find_nearest_neighbour(target,node);
				//t.send(node, dest, newMsg, pid);
				
				reqActive = false; // TODO
				//TimeOutSetMessage nMsg = new TimeOutSetMessage(target, true);
				//EDSimulator.add(timeOut, nMsg, node, pid); 
			}
		}


		// ReturnSetMessage(Set set, long expiryTime) 

		if (event.getClass() == ReturnSetMessage.class) {
			cm++;			
			tm++;
			
			if (node.getID() == 69 ) debug(6,"ReturnSetMessage Received");
			if (state == State.WAIT4S) state = State.IDLE ; 
			ReturnSetMessage message = (ReturnSetMessage) event;
			cacheSet = new Set(message.getSet()); 
			expiryTime = message.getExpiryTime();

			
			
			if(p2d2_start > 0 ) {
				p2d2_hop = (int)(CommonState.getTime() - p2d2_start)/50;
				p2d2_start = 0; 
			}
			
			
			Transport t = (Transport) node.getProtocol(p.tid);
			reqActive = false;
			
			// send return Set Message to all the unsatisfied entries in the list

			for (Map.Entry<BigInteger, RegListObject> entry : regList.entrySet()) {

				RegListObject obj = entry.getValue();
				if ( obj.getReqStatus() == false ) {
					obj.setReqStatus(true);
					t.send(node, obj.getNode(), message, pid );
					regList.put(entry.getKey(), obj );
				}				
			}

			// Query for index list

			if ( STREAM == true ) {
				
				int tn = cacheSet.getCount(); 
				int k,l;
				if (tn <= 0) { System.out.println("Cache has no elements. Abort!"); /* System.exit(0);*/  l= 0; k = 0; }
				else { l = CommonState.r.nextInt(tn); k = cacheSet.get(l);}
				debug(3, " tn = " + tn + " l = " + l + " k = " + k ); 
				String str = new String();
				str = str.concat(" Set ( " + expiryTime + " ) -> [ ");
					for (int i = 0; i< cacheSet.getCount() ; i++) {
						str = str.concat( cacheSet.get(i) + ", ");
					}
					str = str.concat(" ] ");

				debugf(5,str);	
				debugf(5,"ReturnSetMessage Received and selected "+ k);	
				mySet = k;	
							
				//printSet();
				//activeSet = k;
				try {  
					MD5Hash hash = new MD5Hash ("feed_set" + k );
					BigInteger targetId = hash.getHash();
					GetIndexMessage newMessage = new GetIndexMessage (node, node, targetId, k);
					Node dest = find_nearest_neighbour(targetId,node);
					t.send(node, dest, newMessage, pid ); 
					debug(1,"GetIndexMessage Sent"); 
					debugf(1,"GetIndexMessage Sent"); 
					//if (this.node.getID() == 186 ) { debug(10,"GetIndexMessage Sent for " + k + " to " +  dest.getID()); System.exit(0); } 
					waitList.put(new Integer(k), CommonState.getTime() + timeOut);
					state = State.WAIT4L;
					TimeOutIndexMessage nMsg = new TimeOutIndexMessage(k, true);
					EDSimulator.add(timeOut, nMsg, node, pid); 
				} catch (NoSuchAlgorithmException e) {
					System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
				}
			}

		}

		// UpdateSetMessage(Set set, long expiryTime) 

		if (event.getClass() == UpdateSetMessage.class) {
			um++;			
			tm++;
			
			//debug(1,"UpdateSetMessage Received");

			UpdateSetMessage message = (UpdateSetMessage) event;
			

			//if (node.getID() == 69) { debug(6, " upmsg cache count = " + cacheSet.getCount() + " max el = " + cacheSet.getMax() ); } 
			p2d2_up++;
			
			
			Transport t = (Transport) node.getProtocol(p.tid);
			if (node.getID() == 69) { 
				//debug(6, " org  cache count = " + cacheSet.getCount() + " max el = " + cacheSet.getMax() );
				//debug(6, " msg  cache count = " + message.getSet().getCount() + " max el = " + message.getSet().getMax() );
 } 

			if ((numRoot == false ) || ( (cacheSet.getMax() <= message.getSet().getMax() ))) {

				cacheSet = new Set(message.getSet()); 
				expiryTime = message.getExpiryTime();

			
				// send update Set Message to all the unexpired entries in the list

				for (Map.Entry<BigInteger, RegListObject> entry : regList.entrySet()) {

					RegListObject obj = entry.getValue();
					if ( obj.getExpiryTime() > CommonState.getTime() ) {
						t.send(node, obj.getNode(), message, pid );
						regList.put(entry.getKey(), obj );
					}				
				}
			}


		}


		if (event.getClass() == UpMessage.class) {
			
			
			UpMessage message = (UpMessage) event;			
			
			BigInteger target = message.getTargetId();
			//int val = message.getValue();
						
			Transport t = (Transport) node.getProtocol(p.tid);

			if (target == ((ChordProtocol) node.getProtocol(pid)).chordId) {
				
				
					cacheSet.add(cacheSet.getMax()+1);
					expiryTime = message.getET(); 
					UpdateSetMessage newMsg = new UpdateSetMessage ( new Set( this.cacheSet), expiryTime);
					
					
				
					for (Map.Entry<BigInteger, RegListObject> entry : regList.entrySet()) {

						RegListObject obj = entry.getValue();
						if ( obj.getExpiryTime() > CommonState.getTime() ) {
							t.send(node, obj.getNode(), newMsg, pid );
							regList.put(entry.getKey(), obj );
						}				
					}
					
					
				
				
			}
			
			
			if (target != ((ChordProtocol) node.getProtocol(pid)).chordId) {
				
				Node tmPre = ((ChordProtocol)node.getProtocol(pid)).predecessor;
				if (tmPre == null ) tmPre = find_predecessor(((ChordProtocol)node.getProtocol(pid)).chordId.subtract(BigInteger.ONE));
				// TODO quick fix for now
				if ( idInabC(target,((ChordProtocol)tmPre.getProtocol(pid)).chordId,chordId) ) {
					
					cacheSet.add(cacheSet.getMax()+1);
					expiryTime = message.getET(); 
					UpdateSetMessage newMsg = new UpdateSetMessage ( new Set( this.cacheSet), expiryTime);
				
					for (Map.Entry<BigInteger, RegListObject> entry : regList.entrySet()) {

						RegListObject obj = entry.getValue();
						if ( obj.getExpiryTime() > CommonState.getTime() ) {
							t.send(node, obj.getNode(), newMsg, pid );
							regList.put(entry.getKey(), obj );
						}				
					}
					
					
				} else {
					
					//if (lIndexList.containsKey(target) ) lIndexList.remove(target);
					
					Node dest = find_nearest_neighbour(target,node);
					t.send(node, dest, message, pid);
				}				

			}
		}


		// TimeOutIndexMessage( int k)

		if (event.getClass() == TimeOutIndexMessage.class) {
			mCount --;
			TimeOutIndexMessage message = (TimeOutIndexMessage) event;			
			int k = message.getK();

			
			if (message.getIsSource() == false ) {
				if ( (waitList.containsKey(new Integer(k)) == true ) && (waitList.get(new Integer(k)) == CommonState.getTime() )) 	{
					waitList.remove(k);
					if(waitList.size() == 0 ) reqActive2 = false;	
					debug(5,"TOI9 for " + k);
					Iterator<Map.Entry<BigInteger, RegListObject>> it = indexRegList.entrySet().iterator();
					while (it.hasNext()) { //for (Map.Entry<BigInteger, RegListObject> entry : indexRegList.entrySet()) {

						RegListObject obj = it.next().getValue();
						if ( (obj.getReqStatus() == false) && (obj.getK() == k ) ) {
							//obj.setReqStatus(true);
					
							//FeedIndexEntry tmpEntry = tmpList.remove(0); 
							//tmpList.add(tmpEntry); 


							//ReturnIndexMessage newMessage = new ReturnIndexMessage ( tmpList, k); 
							//t.send(node, obj.getNode(), message, pid );

							it.remove(); //, obj );
						}				
					}
				}
			}	

			else if ( (streamAvail == false) && (waitList.containsKey(new Integer(message.getK())) == true ) && (waitList.get(new Integer(message.getK())) == CommonState.getTime() )) {
				debug(5,"TOI for " + k);
				//debugf(5,"TOI for " + k);
				if (pint == true ){
					try {
	 
						File filet = new File("ghar/logdump/node" + this.node.getID() );
		 
						// if file doesnt exists, then create it
						if (!filet.exists()) {
							filet.createNewFile();
						}
		 
						FileWriter fwt = new FileWriter(filet.getAbsoluteFile(), true);
						BufferedWriter bwt = new BufferedWriter(fwt);
						bwt.write(CommonState.getTime() + ": TOI for " + k + "\n");
						bwt.write(" Wait list contains -> ");
						for (Map.Entry<Integer, Long> entry : waitList.entrySet()) {

							bwt.write( " " + entry.getKey() + " (" + entry.getValue()+ "), " );
						}
						bwt.write("\n");
						bwt.write("GetIndexMessage Sent after TOI\n");
						bwt.flush();
						bwt.close();
		 
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				Transport t = (Transport) node.getProtocol(p.tid);
				//activeSet = 0;
				Iterator<Map.Entry<BigInteger, RegListObject>> it = indexRegList.entrySet().iterator();
				while (it.hasNext()) { //for (Map.Entry<BigInteger, RegListObject> entry : indexRegList.entrySet()) {

					RegListObject obj = it.next().getValue();
					if ( (obj.getReqStatus() == false) && (obj.getK() == k ) ) {
						//obj.setReqStatus(true);
				
						//FeedIndexEntry tmpEntry = tmpList.remove(0); 
						//tmpList.add(tmpEntry); 


						//ReturnIndexMessage newMessage = new ReturnIndexMessage ( tmpList, k); 
						//t.send(node, obj.getNode(), message, pid );

						it.remove(); //, obj );
					}				
				}

				try {  
					MD5Hash hash = new MD5Hash ("feed_set0");
					BigInteger targetId = hash.getHash();
					GetIndexMessage newMessage = new GetIndexMessage (node, node, targetId, 0);
					Node dest = find_nearest_neighbour(targetId,node);
					t.send(node, dest, newMessage, pid ); 
					debug(1,"GetIndexMessage Sent after TOI"); 
					//debugf(1,"GetIndexMessage Sent after TOI");   
					state = State.WAIT4L;
					sReqActive = false;
					TimeOutIndexMessage nMsg = new TimeOutIndexMessage(0, true);
					waitList.remove(k);
					waitList.put(new Integer(0), CommonState.getTime() + timeOut);
					EDSimulator.add(timeOut, nMsg, node, pid); 
				} catch (NoSuchAlgorithmException e) {
					System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
				} 
			}
		}


		// GetIndexMessage(Node source, Node from, BigInteger destination, int k)

		if (event.getClass() == GetIndexMessage.class) {
			cm++;			
			tm++;
			GetIndexMessage message = (GetIndexMessage) event;
			
			
			BigInteger target = message.getDestination();
			Transport t = (Transport) node.getProtocol(p.tid);

			if (target == ((ChordProtocol) node.getProtocol(pid)).chordId) {
				
				Integer k = new Integer(message.getK());
			
				if ( k == 0) {
					//Vector<FeedIndexEntry> tmpList = feedIndexPool.get(k);
					ReturnIndexMessage tmpMessage = new ReturnIndexMessage( new Vector<FeedIndexEntry> (feedIndexPool.get(k)) , message.getK());
					t.send(node, message.getFrom(), tmpMessage, pid );
					debug(1,"ReturnIndexMessage Sent");
					if (pint == true ) {
						try {
	 
							File filet = new File("ghar/logdump/feed_set" + k );
			 
							// if file doesnt exists, then create it
							if (!filet.exists()) {
								filet.createNewFile();
							}
			 
							FileWriter fwt = new FileWriter(filet.getAbsoluteFile(), true);
							BufferedWriter bwt = new BufferedWriter(fwt);
							bwt.write(CommonState.getTime() + ": ReturnIndexMessage Sent  @ " + this.node.getID() + "  \n");
							//bwt.write("Node Index | Expiry Time | Node Id ( numIndex = " + numIndex +" ) \n");
						//	for (int i = 0; i<localIndexList.size() ; i++) {
							//	bwt.write( "" + localIndexList.get(i).getNode().getID() + "       | " + localIndexList.get(i).getExpiryTime() + "       | " + localIndexList.get(i).getId() + "\n" );
						//	}
							//bwt.write("____________________________________________________________\n");
							//bwt.flush();
							bwt.close();
			 
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					//printIndexList(localIndexList); 


					//FeedIndexEntry tmpEntry = tmpList.remove(0); 
					//tmpList.add(tmpEntry); 
					//feedIndexPool.put(k, tmpList);
					// to cycle the entries 

					if (message.getSource() != this.node) {

						BigInteger tmpId = ((ChordProtocol) message.getFrom().getProtocol(pid)).chordId;
						RegListObject tmp = new RegListObject( tmpId, CommonState.getTime() + regiListET, message.getFrom(), true, message.getK() ); 
						indexRegList.put( tmpId, tmp);
						
					}


				} else {

					//Vector<FeedIndexEntry> tmpList = feedIndexPool.get(k);
					localIndexList = feedIndexPool.get(k);
				// jan6
					if ((localIndexList.isEmpty() == false)  ) {

						ReturnIndexMessage tmpMessage = new ReturnIndexMessage(new Vector<FeedIndexEntry> (localIndexList), message.getK());
						t.send(node, message.getFrom(), tmpMessage, pid ); 
						debug(1,"ReturnIndexMessage Sent");
					//	printIndexList(localIndexList);
						if (pint == true ) {
							try {
	 
								File filet = new File("ghar/logdump/feed_set" + k );
				 
								// if file doesnt exists, then create it
								if (!filet.exists()) {
									filet.createNewFile();
								}
				 
								FileWriter fwt = new FileWriter(filet.getAbsoluteFile(), true);
								BufferedWriter bwt = new BufferedWriter(fwt);
								bwt.write(CommonState.getTime() + ": ReturnIndexMessage Sent  @ " + this.node.getID() + "  \n");
								//bwt.write("Node Index | Expiry Time | Node Id ( numIndex = " + numIndex +" ) \n");
								for (int i = 0; i<localIndexList.size() ; i++) {
								//	bwt.write( "" + localIndexList.get(i).getNode().getID() + "       | " + localIndexList.get(i).getExpiryTime() + "       | " + localIndexList.get(i).getId() + "\n" );
								}
								//bwt.write("____________________________________________________________\n");
								//bwt.flush();
								bwt.close();
				 
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						//FeedIndexEntry tmpEntry = tmpList.remove(0); 
						//tmpList.add(tmpEntry); 
						//feedIndexPool.put(k, tmpList);
						// to cycle the entries 
			
						if (message.getSource() != this.node) {

							BigInteger tmpId = ((ChordProtocol) message.getFrom().getProtocol(pid)).chordId;
							RegListObject tmp = new RegListObject( tmpId, CommonState.getTime() + regiListET, message.getFrom(), true, message.getK() ); 
							indexRegList.put( tmpId, tmp);
						}
	
					} else {
						fails++;
						//debug(5,"Send PurgeMessage to req");
						debug(5, "Empty ReturnIndexMessage Sent");
						ReturnIndexMessage tmpMessage = new ReturnIndexMessage(new Vector<FeedIndexEntry> (), message.getK());
						t.send(node, message.getFrom(), tmpMessage, pid ); 
						//debug(1,"ReturnIndexMessage Sent");						
						if (pint == true ) {
							try {
	 
								File filet = new File("ghar/logdump/feed_set" + k );
				 
								// if file doesnt exists, then create it
								if (!filet.exists()) {
									filet.createNewFile();
								}
				 
								FileWriter fwt = new FileWriter(filet.getAbsoluteFile(), true);
								BufferedWriter bwt = new BufferedWriter(fwt);
								bwt.write(CommonState.getTime() + ": No Index List found @ " + this.node.getID() + ". Sending ReduceSetMessage \n");
								bwt.close();
				 
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						try {  
							MD5Hash hash = new MD5Hash ("feed_num_of_sets");
							BigInteger targetId = hash.getHash();
							ReduceSetMessage msg = new ReduceSetMessage (targetId, message.getK());
							debug(5,"ReduceSetMessage Sent");
							EDSimulator.add(1, msg, node, pid); 
				
						} catch (NoSuchAlgorithmException e) {
								System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
						}

					}
				}		
				
			}
			
			if (target != ((ChordProtocol) node.getProtocol(pid)).chordId) {
				
				Node tmPre = ((ChordProtocol)node.getProtocol(pid)).predecessor;
				if (tmPre == null ) tmPre = find_predecessor(((ChordProtocol)node.getProtocol(pid)).chordId.subtract(BigInteger.ONE));
				if (node.getID() == 103) debug(10, "pre node is " + tmPre.getID() );
				// TODO quick fix for now
				if ( idInabC(target,((ChordProtocol)tmPre.getProtocol(pid)).chordId,chordId) ) {
				
					Integer k = new Integer(message.getK());
			
					if ( k == 0) {
						//Vector<FeedIndexEntry> tmpList = feedIndexPool.get(k);
						ReturnIndexMessage tmpMessage; 
						if (feedIndexPool.get(k) == null ) {		
							tmpMessage = new ReturnIndexMessage(new Vector<FeedIndexEntry> (), message.getK());
						} else {							
							tmpMessage = new ReturnIndexMessage(new Vector<FeedIndexEntry> (feedIndexPool.get(k)), message.getK());
						}
						t.send(node, message.getFrom(), tmpMessage, pid ); 
						debug(1,"ReturnIndexMessage Sent");
						//printIndexList(localIndexList);
						if (pint == true ) {
							try {
	 
								File filet = new File("ghar/logdump/feed_set" + k );
				 
								// if file doesnt exists, then create it
								if (!filet.exists()) {
									filet.createNewFile();
								}
				 
								FileWriter fwt = new FileWriter(filet.getAbsoluteFile(), true);
								BufferedWriter bwt = new BufferedWriter(fwt);
								bwt.write(CommonState.getTime() + ": ReturnIndexMessage Sent  @ " + this.node.getID() + "  \n");
								//bwt.write("Node Index | Expiry Time | Node Id ( numIndex = " + numIndex +" ) \n");
							//	for (int i = 0; i<localIndexList.size() ; i++) {
								//	bwt.write( "" + localIndexList.get(i).getNode().getID() + "       | " + localIndexList.get(i).getExpiryTime() + "       | " + localIndexList.get(i).getId() + "\n" );
							//	}
								//bwt.write("____________________________________________________________\n");
								//bwt.flush();
								bwt.close();
				 
							} catch (IOException e) {
								e.printStackTrace();
							}
						}

						//FeedIndexEntry tmpEntry = tmpList.remove(0); 
						//tmpList.add(tmpEntry); 
						//feedIndexPool.put(k, tmpList);
	
						if (message.getSource() != this.node) {

							BigInteger tmpId = ((ChordProtocol) message.getFrom().getProtocol(pid)).chordId;
							RegListObject tmp = new RegListObject( tmpId, CommonState.getTime() + regiListET, message.getFrom(), true, message.getK() ); 
							indexRegList.put( tmpId, tmp);
						
						}
					} else {

						//Vector<FeedIndexEntry> tmpList = feedIndexPool.get(k);
						//jan6
						localIndexList = feedIndexPool.get(k);
						if ((feedIndexPool.containsKey(k) == true ) && (localIndexList.isEmpty() == false) ) {

							ReturnIndexMessage tmpMessage = new ReturnIndexMessage(new Vector<FeedIndexEntry> (localIndexList), message.getK());
							t.send(node, message.getFrom(), tmpMessage, pid ); 
							debug(1,"ReturnIndexMessage Sent");
							if (pint == true ) {
								try {
	 
									File filet = new File("ghar/logdump/feed_set" + k );
					 
									// if file doesnt exists, then create it
									if (!filet.exists()) {
										filet.createNewFile();
									}
					 
									FileWriter fwt = new FileWriter(filet.getAbsoluteFile(), true);
									BufferedWriter bwt = new BufferedWriter(fwt);
									bwt.write(CommonState.getTime() + ": ReturnIndexMessage Sent  @ " + this.node.getID() + " \n");
									//bwt.write("Node Index | Expiry Time | Node Id ( numIndex = " + numIndex +" ) \n");
									for (int i = 0; i<localIndexList.size() ; i++) {
									//	bwt.write( "" + localIndexList.get(i).getNode().getID() + "       | " + localIndexList.get(i).getExpiryTime() + "       | " + localIndexList.get(i).getId() + "\n" );
									}
									//bwt.write("____________________________________________________________\n");
									//bwt.flush();
									bwt.close();
					 
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						//	printIndexList(localIndexList);

							//FeedIndexEntry tmpEntry = tmpList.remove(0); 
							//tmpList.add(tmpEntry); 
							//feedIndexPool.put(k, tmpList);
							// to cycle the entries 
			
							if (message.getSource() != this.node) {

								BigInteger tmpId = ((ChordProtocol) message.getFrom().getProtocol(pid)).chordId;
								RegListObject tmp = new RegListObject( tmpId, CommonState.getTime() + regiListET, message.getFrom(), true, message.getK() ); 
								indexRegList.put( tmpId, tmp);
							}
	
						} else {
							fails++;
							//debug(5,"Send PurgeMessage to req");
							//debug(5, "Wait for time out at req");
							debug(5, "Empty ReturnIndexMessage Sent");
							ReturnIndexMessage tmpMessage = new ReturnIndexMessage(new Vector<FeedIndexEntry> (), message.getK());
							t.send(node, message.getFrom(), tmpMessage, pid ); 		
							if (pint == true ){
								try {
		 
									File filet = new File("ghar/logdump/feed_set" + k );
					 
									// if file doesnt exists, then create it
									if (!filet.exists()) {
										filet.createNewFile();
									}
					 
									FileWriter fwt = new FileWriter(filet.getAbsoluteFile(), true);
									BufferedWriter bwt = new BufferedWriter(fwt);
									bwt.write(CommonState.getTime() + ": No Index List found @ " + this.node.getID() + ". Sending ReduceSetMessage \n");
									bwt.close();
					 
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							try {  
								MD5Hash hash = new MD5Hash ("feed_num_of_sets");
								BigInteger targetId = hash.getHash();
								ReduceSetMessage msg = new ReduceSetMessage (targetId, message.getK());
								debug(5,"ReduceSetMessage Sent");
								EDSimulator.add(1, msg, node, pid); 
				
							} catch (NoSuchAlgorithmException e) {
									System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
							}
						}
					}
					
				} else { 					
					
					// Don't look in temporary cache for index. just forward the query. 

					if((reqActive2 == false) || (waitList.containsKey(new Integer(message.getK())) == false ) ){ 

						// if a previous request has not been made

						GetIndexMessage newMessage = new GetIndexMessage (message.getSource(), node, target, message.getK());
						

						Node dest = find_nearest_neighbour(target,node);
						t.send(node, dest, newMessage, pid ); 
						if ((CommonState.getTime() <= 20000) && (message.getK() == 14))  { debug(10,"GetIndexMessage from " + message.getFrom().getID() + " to " +  dest.getID());  } 

						//debug(1,"GetIndexMessage Forwarded"); 
						reqActive2 = true; // TODO
						state = State.WAIT4L;
						TimeOutIndexMessage nMsg = new TimeOutIndexMessage(message.getK(), false);
						waitList.put(new Integer(message.getK()), CommonState.getTime() + timeOut9);
						EDSimulator.add(timeOut9, nMsg, node, pid);

						// 

						//if(node.getID() == 166 ) debug(3,"reqset added " + message.getK());


									
					} 

					


					if (message.getSource() != this.node) {
	
						BigInteger tmpid = ((ChordProtocol) message.getFrom().getProtocol(pid)).chordId;

						RegListObject tmpentry = new RegListObject( tmpid, CommonState.getTime() + regListET, message.getFrom(), false, message.getK() ); 
						//if ((message.getFrom().getID() == 638) && (node.getID() == 519) ) { debugf(10, " h" ); //"GetIndexMessage from " + message.getFrom().getID() + " to " +  dest.getID());
// System.exit(0); } 
						while (indexRegList.containsKey(tmpid) == true ) {
							tmpid = tmpid.add(BigInteger.ONE) ;
						}
						 indexRegList.put( tmpid, tmpentry);
					}
					
				/*	Integer k =  new Integer(message.getK());				
					Vector<FeedIndexEntry> tmpList = feedIndexPool.get(k);
			
					if (tmpList!= null ) {

						ReturnIndexMessage tmpMessage = new ReturnIndexMessage(tmpList,message.getK() );
						t.send(node, message.getFrom(), tmpMessage, pid ); 
						debug(1,"ReturnIndexMessage Sent");

						FeedIndexEntry tmpEntry = tmpList.remove(0); 
						tmpList.add(tmpEntry); 
						feedIndexPool.put(k, tmpList);
						// to cycle the entries 
			
						if (message.getSource() != this.node) {
		
							BigInteger tmpid = ((ChordProtocol) message.getFrom().getProtocol(pid)).chordId;
							RegListObject tmpentry = new RegListObject( tmpid, CommonState.getTime() + regiListET, message.getFrom(), true, message.getK() ); 
							indexRegList.put( tmpid, tmpentry);
						}

					} else {
						
						if(reqActive2 == false) { 
							// if a previous request has not been made

							GetIndexMessage newMessage = new GetIndexMessage (node, node, target, message.getK());
							Node dest = find_nearest_neighbour(target,node);
							t.send(node, dest, newMessage, pid ); 
							debug(1,"GetIndexMessage Sent"); 
							reqActive2 = true; // TODO
							state = State.WAIT4L;
							TimeOutIndexMessage nMsg = new TimeOutIndexMessage(k);
							EDSimulator.add(timeOut, nMsg, node, pid);
										
						} 

						if (message.getSource() != this.node) {
		
							BigInteger tmpid = ((ChordProtocol) message.getFrom().getProtocol(pid)).chordId;
							RegListObject tmpentry = new RegListObject( tmpid, CommonState.getTime() + regListET, message.getFrom(), false, message.getK() ); 
							indexRegList.put( tmpid, tmpentry);
						}

					} */


				}

			}
		}


		// ReturnIndexMessage(Vector<FeedIndexEntry> list, int k )

		if (event.getClass() == ReturnIndexMessage.class) {
			cm++;			
			tm++;
			
			//debug(1,"ReturnIndexMessage Received");
			ReturnIndexMessage message = (ReturnIndexMessage) event;
			
			
			

			//if (message.getList() == null) System.exit(0);
			Integer k = new Integer(message.getK()); 
			waitList.remove(k);
			if(waitList.size() == 0 ) reqActive2 = false;

			Transport t = (Transport) node.getProtocol(p.tid);
			
			// send return Index Message to all the unsatisfied entries in the list

			Iterator<Map.Entry<BigInteger, RegListObject>> it = indexRegList.entrySet().iterator();

			while (it.hasNext()) { //for (Map.Entry<BigInteger, RegListObject> entry : indexRegList.entrySet()) {

				RegListObject obj = it.next().getValue();
				//if (node.getID() == 353 ) debugf(5, " Entry : id = " + obj.getNode().getID() + " k = " + obj.getK() );
				if ( (obj.getReqStatus() == false) && (obj.getK() == k ) ) {
					//obj.setReqStatus(true);
					
					//FeedIndexEntry tmpEntry = tmpList.remove(0); 
					//tmpList.add(tmpEntry); 
				//	if (node.getID() == 353 ) debugf(5, " -> Sent \n" );

					//ReturnIndexMessage newMessage = new ReturnIndexMessage ( tmpList, k); 
					t.send(node, obj.getNode(), message, pid );

					it.remove(); //indexRegList.remove(entry.getKey());
				}else {
					//if (node.getID() == 353 ) debugf(5, " -> Not Sent \n" );
				}				
			}



			if ((message.getList().isEmpty() == true) && (STREAM == true) && (message.getK() == mySet)) { 
				int tn = cacheSet.getCount(); 
				int k2,l;
				if (tn <= 0) { l= 0; k2 = 0; }
				else { l = CommonState.r.nextInt(tn); k2 = cacheSet.get(l);}
				if ( (k2 != 0 ) && ( k2 == message.getK() ) ) k2 = 0 ;
				debugf(5,"Empty ReturnIndexMessage Received from feed_set" + message.getK()); 
				debug(5,"Empty ReturnIndexMessage Received from feed_set" + message.getK()); 
				
				try {  
					MD5Hash hash = new MD5Hash ("feed_set" + k2 );
					mySet = k2;

					BigInteger targetId = hash.getHash();
					GetIndexMessage newMessage = new GetIndexMessage (node, node, targetId, k2);
					Node dest = find_nearest_neighbour(targetId,node);
					t.send(node, dest, newMessage, pid ); 
					debug(5,"Re::: GetIndexMessage Sent to feed_set" + k2); 
					debugf(5,"Re::: GetIndexMessage Sent to feed_set" + k2); 
					//System.exit(0);
					state = State.WAIT4L;
					sReqActive = false;
					TimeOutIndexMessage nMsg = new TimeOutIndexMessage(k2, true);
					waitList.put(new Integer(k2), CommonState.getTime() + timeOut);
					//if(node.getID() == 112 ) debug(3,"reqset added " + 0);
					EDSimulator.add(timeOut, nMsg, node, pid); 
				} catch (NoSuchAlgorithmException e) {
					System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
				}
				
			} else { 
				
	
			
				tmpLocalIndexList = new Vector<FeedIndexEntry> (message.getList());			
				if (state == State.WAIT4L) state = State.IDLE ; 
			
				if ((STREAM == true) && ( sReqActive == false) && (message.getK() == mySet )) {

					// connect to the nodes..

					boolean conn = false;
					debug(3,"ReturnIndexMessage Received");
					debugf(3,"ReturnIndexMessage Received for " + message.getK() + " with ");
					String str = new String();
					for (int i = 0; i<tmpLocalIndexList.size() ; i++) {
						str = str.concat(tmpLocalIndexList.get(i).getNode().getID() + "       | " + tmpLocalIndexList.get(i).getExpiryTime() + "       | " + tmpLocalIndexList.get(i).getId() + "\n" );
					}
					debugf(3,str);

					glimit = 0;
					activeSet = message.getK();
					//Vector<FeedIndexEntry> tmpList = localIndexList;
					printIndexList(tmpLocalIndexList);
					FeedIndexEntry tmpEntry ;
					while ( tmpLocalIndexList.isEmpty() == false  ) {
						int kk = CommonState.r.nextInt(tmpLocalIndexList.size());
						tmpEntry = tmpLocalIndexList.remove(kk); 
						if (tmpEntry.getExpiryTime() < CommonState.getTime()) {
							debug(10, "hello");
							//System.exit(0);
						}
						if ( (message.getK() == 0) || (tmpEntry.getExpiryTime() > CommonState.getTime() )) {
							ConnectMessage nMessage = new ConnectMessage (node, chordId);
							t.send(node, tmpEntry.getNode(), nMessage, pid );
							debug(3, "ConnectMessage send to "+ tmpEntry.getNode().getID() );
							debugf(3, "ConnectMessage send to "+ tmpEntry.getNode().getID() );
							sReqActive = true;
							conn = true;
							state = State.WAIT4C;
							if (glimit < tmpEntry.getExpiryTime() ) glimit = tmpEntry.getExpiryTime() ;  
							//tmpLocalIndexList.add(tmpEntry);
							//feedIndexPool.put(k,tmpList);
							break;
						}
						//debug(5, "here");
						if (glimit < tmpEntry.getExpiryTime() ) glimit = tmpEntry.getExpiryTime() ;  
					
					} 

				//	if ( tmpList == null ) {
					// all entries expired
					// send req to root node.,, 
	
					if ( conn == false ) {
						try {  
							MD5Hash hash = new MD5Hash ("feed_set" + activeSet);
							BigInteger targetId = hash.getHash();
							PurgeIndexMessage newMessage = new PurgeIndexMessage (activeSet, targetId, glimit);//TODO N
							debug(3, "PurgeIndexMessage send");
							debugf(3, "Connection Not possible. PurgeIndexMessage send to feed_set" + activeSet);
							Node dest = find_nearest_neighbour(targetId,node);
							t.send(node, dest, newMessage, pid );  
						} catch (NoSuchAlgorithmException e) {
							System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
						}

						int tn = cacheSet.getCount(); 
						int k2,l;
						if (tn <= 0) { l= 0; k2 = 0; }
						else { l = CommonState.r.nextInt(tn); k2 = cacheSet.get(l);}
					
						try {  
							MD5Hash hash = new MD5Hash ("feed_set" + k2 );
							mySet = k2;
							BigInteger targetId = hash.getHash();
							GetIndexMessage newMessage = new GetIndexMessage (node, node, targetId, k2);
							Node dest = find_nearest_neighbour(targetId,node);
							t.send(node, dest, newMessage, pid ); 
							debug(5,"Re:: GetIndexMessage Sent to feed_set" + k2); 
							debugf(5,"Re:: GetIndexMessage Sent to feed_set" + k2); 
							//System.exit(0);
							state = State.WAIT4L;
							sReqActive = false;
							TimeOutIndexMessage nMsg = new TimeOutIndexMessage(k2, true);
							waitList.put(new Integer(k2), CommonState.getTime() + timeOut);
							//if(node.getID() == 112 ) debug(3,"reqset added " + 0);
							EDSimulator.add(timeOut, nMsg, node, pid); 
						} catch (NoSuchAlgorithmException e) {
							System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
						}	

					}	

				}	
			}

		}


		
		

		// PurgeMessage(int k, BigInteger targetId


		if (event.getClass() == PurgeIndexMessage.class) {
			pm++;			
			tm++;
			
			debug(1,"PurgeIndexMessage Received");
			PurgeIndexMessage message = (PurgeIndexMessage) event;
			Integer k = new Integer(message.getK()); 
			BigInteger target = message.getTargetId();
			long limit = message.getLimit();
			// can purge the hard way that is remove all the entries from the set ..
			// can purge the soft way, that is keep the latest entries 
			Transport t = (Transport) node.getProtocol(p.tid);

			if (target == ((ChordProtocol) node.getProtocol(pid)).chordId) {
				
				if (feedIndexPool.containsKey(k) == true ) {

					if ( k ==0 ) {
						numIndex = feedIndexPool.get(k).size();
						debug(1,"set 0 purged" );
						for (int i=0; i<numIndex; i++) {
							// jan6
							FeedIndexEntry tmp = feedIndexPool.get(k).get(i);
							if (tmp.getExpiryTime() <= limit ) {
								feedIndexPool.get(k).remove(i);
								i -= 1;
								numIndex -= 1;
							}
						}
				
					} else {					
				
						//localIndexList.clear();
						feedIndexPool.get(k).clear();
						numIndex = 0;
						if (feedIndexPool.size() == 1 ) setRoot = false;
						else { 
							setRoot = false;
							for ( Map.Entry<Integer, Vector<FeedIndexEntry>> en : feedIndexPool.entrySet()) {
								if (en.getValue().isEmpty() == false ) {
									setRoot = true;
									break;
								}
							}
						}
						//if (indexPool.isEmpty() == true) setRoot = false;
						//if (node.getID() == 45 ) debug(3,"setroot made true");
					
					
						debug(6,"setroot removed -> " + k);
						//printIndexList(localIndexList);

						try {  
							MD5Hash hash = new MD5Hash ("feed_num_of_sets");
							BigInteger targetId = hash.getHash();
							ReduceSetMessage msg = new ReduceSetMessage (targetId, message.getK());
						//	debug(5,"ReduceSetMessage Sent");
							EDSimulator.add(1, msg, node, pid); 
				
						} catch (NoSuchAlgorithmException e) {
								System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
						}
					}
					if (pint == true ) {
						try {
		 
							File filet = new File("ghar/logdump/feed_set" + k );
			 
							// if file doesnt exists, then create it
							if (!filet.exists()) {
								filet.createNewFile();
							}
			 
							FileWriter fwt = new FileWriter(filet.getAbsoluteFile(), true);
							BufferedWriter bwt = new BufferedWriter(fwt);
							bwt.write(CommonState.getTime() + ": PurgeIndexMessage Received @ " + this.node.getID() + ". Sending ReduceSetMessage \n");
							bwt.close();
			 
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					
				}
				/*for (int i=0; i<numIndex; i++) {
					FeedIndexEntry tmp = localIndexList.get(i);
					if (tmp.getExpiryTime() <= limit ) {
						localIndexList.remove(i);
						i -= 1;
						numIndex -= 1;
					}
				}
				debug(5,"After Purge");
				setRoot = false;
				//printIndexList(localIndexList);

				try {  
					MD5Hash hash = new MD5Hash ("feed_num_of_sets");
					BigInteger targetId = hash.getHash();
					ReduceSetMessage msg = new ReduceSetMessage (targetId, message.getK());
					debug(5,"ReduceSetMessage Sent");
					EDSimulator.add(1, msg, node, pid); 
				
				} catch (NoSuchAlgorithmException e) {
						System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
				} */
			} 	
			
				 		
			
			if (target != ((ChordProtocol) node.getProtocol(pid)).chordId) {
				
				Node tmPre = ((ChordProtocol)node.getProtocol(pid)).predecessor;
				if (tmPre == null ) tmPre = find_predecessor(((ChordProtocol)node.getProtocol(pid)).chordId.subtract(BigInteger.ONE));
				// TODO quick fix for now
				if ( idInabC(target,((ChordProtocol)tmPre.getProtocol(pid)).chordId,chordId) ) {

					if (feedIndexPool.containsKey(0) == true ) {
		
						if ( k ==0 ) {
							debug(1,"set 0 purged" );
							numIndex = feedIndexPool.get(0).size();
							for (int i=0; i<numIndex; i++) {
								// jan6
								FeedIndexEntry tmp = feedIndexPool.get(0).get(i);
								if (tmp.getExpiryTime() <= limit ) {
									feedIndexPool.get(0).remove(i);
									i -= 1;
									numIndex -= 1;
								}
							}
				
						} else {
							//localIndexList.clear();
							feedIndexPool.get(0).clear();
							numIndex = 0;
							debug(6,"setroot removed -> " + feedIndexPool.size() + " "  + k );
							if (feedIndexPool.size() == 1 ) setRoot = false;
							else { 
								setRoot = false;
								for ( Map.Entry<Integer, Vector<FeedIndexEntry>> en : feedIndexPool.entrySet()) {
									if (en.getValue().isEmpty() == false ) {
										setRoot = true;
										break;
									}
								}
							}
							//indexPool.remove(target);
							numIndex = 0;
						//	if(indexPool.isEmpty() == true) setRoot = false;

							//if (node.getID() == 45 ) debug(3,"setroot made true");
				
						//	debug(5,"After Purge");
							//printIndexList(localIndexList);

							try {  
								MD5Hash hash = new MD5Hash ("feed_num_of_sets");
								BigInteger targetId = hash.getHash();
								ReduceSetMessage msg = new ReduceSetMessage (targetId, message.getK());
								//debug(5,"ReduceSetMessage Sent");
								EDSimulator.add(1, msg, node, pid); 
				
							} catch (NoSuchAlgorithmException e) {
									System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
							}
						}
						if (pint == true ) {
							try {
		 
								File filet = new File("ghar/logdump/feed_set" + k );
				 
								// if file doesnt exists, then create it
								if (!filet.exists()) {
									filet.createNewFile();
								}
				 
								FileWriter fwt = new FileWriter(filet.getAbsoluteFile(), true);
								BufferedWriter bwt = new BufferedWriter(fwt);
								bwt.write(CommonState.getTime() + ": PurgeIndexMessage Received @ " + this.node.getID() + ". Sending ReduceSetMessage \n");
								bwt.close();
				 
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					
					}
				/*	int tn = localIndexList.size();
					for (int i=0; i<tn; i++) {
						//debug(1,"in loop" + listSize);
						FeedIndexEntry tmp = localIndexList.get(i);
						if (tmp.getExpiryTime() <= limit ) {
							localIndexList.remove(i);
							i -= 1;
							numIndex -= 1;
							tn -= 1;
						}
					}
					debug(5,"After Purge");
				//	printIndexList(localIndexList);
					setRoot = false;

					try {  
						MD5Hash hash = new MD5Hash ("feed_num_of_sets");
						BigInteger targetId = hash.getHash();
						ReduceSetMessage msg = new ReduceSetMessage (targetId, message.getK());
						debug(5,"ReduceSetMessage Sent");
						EDSimulator.add(1, msg, node, pid); 
				
					} catch (NoSuchAlgorithmException e) {
							System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
					}*/
					
					
				} else { 					
					
					//  just forward the msg. 

					Node dest = find_nearest_neighbour(target,node);
					t.send(node, dest, message, pid ); 
					//debug(1,"PurgeIndexMessage Forwarded"); 
									
				} 

					

			}

		}


		// ConnectMessage(Node node, BigInteger id )


		if (event.getClass() == ConnectMessage.class) {
			dm++;			
			tm++;
			//debug(1,"ConnectMessage Received");
			ConnectMessage message = (ConnectMessage)event;
			Transport t = (Transport) node.getProtocol(p.tid);

			if ((streamAvail == true) && (numConn < maxConn )) {
				
				numConn ++ ;
				connectionList.put(message.getId(), message.getNode());
				ConnectAMessage newMessage = new ConnectAMessage (node,chordId); 
				t.send(node, message.getNode(), newMessage, pid );
				debug(1,"ConnectAMessage Send");
				//debugf(1,"ConnectAMessage Send to " + message.getNode().getID());

				if (numConn == maxConn ) {
					try {  
						MD5Hash hash = new MD5Hash ("feed_set" + finalSet );
						BigInteger targetId = hash.getHash();
						RemoveIndexMessage nMessage = new RemoveIndexMessage(chordId, finalSet);
						Node dest = find_nearest_neighbour(targetId,node);
						t.send(node, dest, nMessage, pid ); 
						//debug(3,"RegisterIndexMessage Sent to feed_set" + activeSet); 
						//debugf(3,"RegisterIndexMessage Sent to feed_set " + activeSet); 
					} catch (NoSuchAlgorithmException e) {
						System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");

					}
				}	


			} else {
				ConnectDMessage newMessage = new ConnectDMessage (); 
				t.send(node, message.getNode(), newMessage, pid );
				debug(1,"ConnectDMessage Send");
				//debugf(1,"ConnectDMessage Send to " + message.getNode().getID());
			}  

			
		/*	if (state == State.WAIT4L) state = State.IDLE ; 
			ReturnIndexMessage message = (ReturnIndexMessage) event;
			Integer k = new Integer(message.getK()); 
			Vector<FeedIndexEntry> tmpList = message.getList();
			feedIndexPool.put(k,tmpList);
			
			Transport t = (Transport) node.getProtocol(p.tid);
			reqActive2 = false;
			
			// send return Index Message to all the unsatisfied entries in the list


			for (Map.Entry<BigInteger, RegListObject> entry : indexRegList.entrySet()) {

				RegListObject obj = entry.getValue();
				if ( (obj.getReqStatus() == false) && (obj.getK() == k ) ) {
					obj.setReqStatus(true);
					
					FeedIndexEntry tmpEntry = tmpList.remove(0); 

					tmpList.add(tmpEntry); 



					ReturnIndexMessage newMessage = new ReturnIndexMessage ( tmpList, k); 

					t.send(node, obj.getNode(), newMessage, pid );


					indexRegList.put(entry.getKey(), obj );

				}				

			}


			
			if (STREAM == true ) {


				// connect to the nodes..
				boolean conn = false;

				tmpList = feedIndexPool.get(k); 	

				FeedIndexEntry tmpEntry ;

				while ( tmpList != null  ) {

					tmpEntry = tmpList.remove(0); 

					if ( tmpEntry.getExpiryTime() > CommonState.getTime() ) {
						//ConnectMessage nMessage = new ConnectMessage (node, chordId);

						//t.send(node, tmpEntry.getNode(), nMessage, pid );
						conn = true;

						state = State.WAIT4C;

						tmpList.add(tmpEntry);
						feedIndexPool.put(k,tmpList);

						break;

					}
				} 



			//	if ( tmpList == null ) {

				// all entries expired

				// send req to root node.,, 

	

				if ( conn == false ) {

					try {  

						MD5Hash hash = new MD5Hash ("feed_set" + k);

						BigInteger targetId = hash.getHash();

						PurgeIndexMessage newMessage = new PurgeIndexMessage (k, targetId);

						Node dest = find_nearest_neighbour(targetId,node);

						t.send(node, dest, newMessage, pid );  

					} catch (NoSuchAlgorithmException e) {

						System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");

					}



					try {  

						MD5Hash hash = new MD5Hash ("feed_set0" );



						BigInteger targetId = hash.getHash();

						GetIndexMessage newMessage = new GetIndexMessage (node, node, targetId, k);

						Node dest = find_nearest_neighbour(targetId,node);

						t.send(node, dest, newMessage, pid ); 

						debug(1,"GetIndexMessage Sent"); 

						state = State.WAIT4L;
						TimeOutIndexMessage nMsg = new TimeOutIndexMessage(k);

						EDSimulator.add(timeOut, nMsg, node, pid); 

					} catch (NoSuchAlgorithmException e) {
						System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
					}	


				}	



			}	*/
			

		}


		// ConnectAMessage()


		if (event.getClass() == ConnectAMessage.class) {
			dm++;			
			tm++;
			
			debug(3,"ConnectAMessage Received");
			debugf(3,"ConnectAMessage Received");
			streamAvail = true;
			STREAM = false;
			pEnd = CommonState.getTime();
			connTime = pEnd - pStart - 1;
			connTime = connTime/10;
			sReqActive = false;
			Transport t = (Transport) node.getProtocol(p.tid);	
			//state = State.WAIT4R; 

			try {  
				MD5Hash hash = new MD5Hash ("feed_set" + activeSet );
				finalSet = activeSet;
				BigInteger targetId = hash.getHash();
				FeedIndexEntry tmp = new FeedIndexEntry( chordId, node, CommonState.getTime() + indexListET ); 
				RegisterIndexMessage newMessage = new RegisterIndexMessage (tmp, targetId,node,activeSet);
				Node dest = find_nearest_neighbour(targetId,node);
				t.send(node, dest, newMessage, pid ); 
				debug(3,"RegisterIndexMessage Sent to feed_set" + activeSet); 
				debugf(3,"RegisterIndexMessage Sent to feed_set " + activeSet); 
				state = State.WAIT4R;
				TimeOutRegisterMessage nMsg = new TimeOutRegisterMessage();
				EDSimulator.add(timeOut, nMsg, node, pid); 
			} catch (NoSuchAlgorithmException e) {
				System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");

			}

			

		}

		// RegisterIndexMessage(FeedIndexEntry entry, BigInteger id)


		if (event.getClass() == RegisterIndexMessage.class) {

			cm++;			
			tm++;
			
			//debug(1,"RegisterIndexMessage Received");
			RegisterIndexMessage message = (RegisterIndexMessage) event;
			//Integer k = new Integer(message.getK()); 
			BigInteger target = message.getId();
			//long limit = message.getLimit();
			int k = message.getK();
if(k==0) {debug(1, "set 0 queried"  );}

			Transport t = (Transport) node.getProtocol(p.tid);

			if (target == ((ChordProtocol) node.getProtocol(pid)).chordId) {

			String str = new String();

			if(feedIndexPool.containsKey(k) == false ) {
				 setRoot = true;
				srootId = target;
				//indexPool.remove(target);			
// jan6				localIndexList.clear();
				feedIndexPool.put(k, new Vector<FeedIndexEntry> () ) ;
				str = str.concat (" LocalIndexList cleared @ " + this.node.getID() + "\n");
				debug(3, "cleared here .. ");
				debug(6, "setroot added -> " + k   );
				numIndex = 0;
				if (pint == true ) {
					try {

						File filet = new File("ghar/logdump/setRoot" );
		 
						// if file doesnt exists, then create it
						if (!filet.exists()) {
							filet.createNewFile();
						}
		 
						FileWriter fwt = new FileWriter(filet.getAbsoluteFile(), true);
						BufferedWriter bwt = new BufferedWriter(fwt);
						bwt.write( CommonState.getTime() + ": feed_set" + k + " @ " + this.node.getID() + "\n");
						bwt.close();
		 
					} catch (IOException e) {
						e.printStackTrace();
					}
				}	
			}
				 
				
				boolean done = false;
				//jan6
				localIndexList = feedIndexPool.get(k); 
				numIndex = (localIndexList == null ) ? 0: localIndexList.size();
				for (int i=0; i< numIndex; i++) {
					if (localIndexList.get(i).getId().compareTo(message.getEntry().getId()) == 0) {
						localIndexList.remove(i);
						localIndexList.add(i,message.getEntry());
						done = true;
						RegisterAMessage newMessage = new RegisterAMessage ();
						t.send(node, message.getSource(), newMessage, pid );
						debug(3,"RegisterAMessage Sent");
						str = str.concat(CommonState.getTime() + ": RegisterAMessage 1 Sent. New IndexList is \n");
						str = str.concat("Node Index | Expiry Time | Node Id ( numIndex = " + numIndex +" ) \n");
						for (int ii = 0; ii<numIndex ; ii++) {
							str = str.concat( "" + localIndexList.get(ii).getNode().getID() + "       | " + localIndexList.get(ii).getExpiryTime() + "       | " + localIndexList.get(ii).getId() + "\n" );
						}
						str = str.concat("____________________________________________________________\n"); 
						printIndexList(localIndexList);
						break;
					}
				} 				

				if ( done == false ) {
					if (numIndex < listSize ) {
						FeedIndexEntry tmp = message.getEntry();
						localIndexList.add(tmp);
						numIndex += 1;
						RegisterAMessage newMessage = new RegisterAMessage ();
						t.send(node, message.getSource(), newMessage, pid );
						debug(3,"RegisterAMessage Sent"); 
						str = str.concat(CommonState.getTime() + ": RegisterAMessage 2 Sent. New IndexList is \n");
						str = str.concat("Node Index | Expiry Time | Node Id ( numIndex = " + numIndex +" ) \n");
						for (int i = 0; i<localIndexList.size() ; i++) {
							str = str.concat( "" + localIndexList.get(i).getNode().getID() + "       | " + localIndexList.get(i).getExpiryTime() + "       | " + localIndexList.get(i).getId() + "\n" );
						}
						str = str.concat("____________________________________________________________\n"); 
						
						printIndexList(localIndexList);	
					}
					else {
						int i=0;
						for (i=0; i<numIndex; i++) {
							if (localIndexList.get(i).getExpiryTime() < CommonState.getTime() ) {
								 	localIndexList.remove(i);
									localIndexList.add(i,message.getEntry());
									RegisterAMessage newMessage = new RegisterAMessage ();
									t.send(node, message.getSource(), newMessage, pid );
									debug(3,"RegisterAMessage Sent");
									str = str.concat(CommonState.getTime() + ": RegisterAMessage 3 Sent. New IndexList is \n");
									str = str.concat("Node Index | Expiry Time | Node Id ( numIndex = " + numIndex +" ) \n");
									for (int ii = 0; ii<localIndexList.size() ; ii++) {
										str = str.concat( "" + localIndexList.get(ii).getNode().getID() + "       | " + localIndexList.get(ii).getExpiryTime() + "       | " + localIndexList.get(ii).getId() + "\n" );
									}
									str = str.concat("____________________________________________________________\n"); 
						
									printIndexList(localIndexList);
									break;
							} 
						}

						if (i== numIndex) {
							if (k==0) {
								try {  
									MD5Hash hash = new MD5Hash ("feed_num_of_sets");
									BigInteger targetId = hash.getHash();
									ExpandSetMessage msg = new ExpandSetMessage (targetId, message.getSource());
									debug(3,"ExpandSetMessage Sent");
									str = str.concat(CommonState.getTime() + ": Not able to Register. ExpandSetMessage Sent.\n");
						
									EDSimulator.add(1, msg, node, pid); 
								
								} catch (NoSuchAlgorithmException e) {
										System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
							    	}
							} else {
								RegisterDMessage newMessage = new RegisterDMessage (0);
								t.send(node, message.getSource(), newMessage, pid );
								debug(3,"RegisterDMessage Sent");
								str = str.concat(CommonState.getTime() + ": Not able to Register. RegisterDMessage Sent.\n");
							}
						
						}
					}
				}
				if (pint == true ) {
					try {

						File filet = new File("ghar/logdump/feed_set" + k );
		 
						// if file doesnt exists, then create it
						if (!filet.exists()) {
							filet.createNewFile();
						}
		 
						FileWriter fwt = new FileWriter(filet.getAbsoluteFile(), true);
						BufferedWriter bwt = new BufferedWriter(fwt);
						bwt.write( str);
						bwt.close();
		 
					} catch (IOException e) {
						e.printStackTrace();
					}
				}	
				
				
			} 	
			
				 		
			
			if (target != ((ChordProtocol) node.getProtocol(pid)).chordId) {
				
				Node tmPre = ((ChordProtocol)node.getProtocol(pid)).predecessor;
				if (tmPre == null ) tmPre = find_predecessor(((ChordProtocol)node.getProtocol(pid)).chordId.subtract(BigInteger.ONE));	
				// TODO quick fix for now

				if ( idInabC(target,((ChordProtocol)tmPre.getProtocol(pid)).chordId,chordId) ) {
								//int tn = indexPool.get(target).size();
				String str = new String();
					
				if(feedIndexPool.containsKey(k) == false ) {
				//if (node.getID() == 45 ) debug(3,"setroot made true");
				feedIndexPool.put(k, new Vector<FeedIndexEntry> () ) ;
				str = str.concat (" LocalIndexList cleared @ " + this.node.getID() + "\n");
				 setRoot = true;
								debug(6, "setroot added -> " + k  );
				srootId = target;
				//localIndexList.clear();
				//indexPool.remove(target);
debug(3, "cleared here .. ");
				if (pint == true ) {
					try {

						File filet = new File("ghar/logdump/setRoot" );
		 
						// if file doesnt exists, then create it
						if (!filet.exists()) {
							filet.createNewFile();
						}
		 
						FileWriter fwt = new FileWriter(filet.getAbsoluteFile(), true);
						BufferedWriter bwt = new BufferedWriter(fwt);
						bwt.write( CommonState.getTime() + ": feed_set" + k + " @ " + this.node.getID() + "\n");
						bwt.close();
		 
					} catch (IOException e) {
						e.printStackTrace();
					}	
				}
				numIndex = 0;
			}
					//debug(5," test");
					boolean done = false;
					localIndexList = feedIndexPool.get(k);
					numIndex = (localIndexList == null ) ? 0: localIndexList.size();
					//debug(5,"tets" + tn);
					//if (k == 1) System.exit(0);
					
					for (int i=0; i<numIndex ; i++) {
						if (localIndexList.get(i).getId().compareTo(message.getEntry().getId()) == 0) {
							localIndexList.remove(i);
							localIndexList.add(i,message.getEntry());
							done = true;
							RegisterAMessage newMessage = new RegisterAMessage ();
							t.send(node, message.getSource(), newMessage, pid );
							debug(3,"RegisterAMessage Sent for set_" + k);
							str = str.concat(CommonState.getTime() + ": RegisterAMessage 1 Sent. New IndexList is \n");
							str = str.concat("Node Index | Expiry Time | Node Id ( numIndex = " + numIndex +" ) \n");
							for (int ii = 0; ii<localIndexList.size() ; ii++) {
								str = str.concat( "" + localIndexList.get(ii).getNode().getID() + "       | " + localIndexList.get(ii).getExpiryTime() + "       | " + localIndexList.get(ii).getId() + "\n" );
							}
							str = str.concat("____________________________________________________________\n"); 
							printIndexList(localIndexList);
							break;
						}
					} 				

					if ( done == false ) {
						if (numIndex < listSize) {
							FeedIndexEntry tmp = message.getEntry();
							localIndexList.add(tmp);
							numIndex += 1;
							RegisterAMessage newMessage = new RegisterAMessage ();
							t.send(node, message.getSource(), newMessage, pid );
							debug(3,"RegisterAMessage Sent for set_" + k); 
							str = str.concat(CommonState.getTime() + ": RegisterAMessage 2 Sent. New IndexList is \n");
							str = str.concat("Node Index | Expiry Time | Node Id ( numIndex = " + numIndex +" ) \n");
							for (int ii = 0; ii<localIndexList.size() ; ii++) {
								str = str.concat( "" + localIndexList.get(ii).getNode().getID() + "       | " + localIndexList.get(ii).getExpiryTime() + "       | " + localIndexList.get(ii).getId() + "\n" );
							}
							str = str.concat("____________________________________________________________\n"); 
							printIndexList(localIndexList);	
						}
						else {
							int i=0;
							
							for (i=0; i<numIndex; i++) {
								if (localIndexList.get(i).getExpiryTime() < CommonState.getTime() ) {
									 	localIndexList.remove(i);
										localIndexList.add(i,message.getEntry());
										RegisterAMessage newMessage = new RegisterAMessage ();
										t.send(node, message.getSource(), newMessage, pid );
										debug(3,"RegisterAMessage  Sent for set_" + k);
										str = str.concat(CommonState.getTime() + ": RegisterAMessage 3 Sent. New IndexList is \n");
										str = str.concat("Node Index | Expiry Time | Node Id ( numIndex = " + numIndex +" ) \n");
										for (int ii = 0; ii<localIndexList.size() ; ii++) {
											str = str.concat( "" + localIndexList.get(ii).getNode().getID() + "       | " + localIndexList.get(ii).getExpiryTime() + "       | " + localIndexList.get(ii).getId() + "\n" );
										}
										str = str.concat("____________________________________________________________\n"); 
										printIndexList(localIndexList);
										break;
								} 
							}

							if (i== numIndex) {
								if (k==0) {
									try {  
										MD5Hash hash = new MD5Hash ("feed_num_of_sets");
										BigInteger targetId = hash.getHash();
										ExpandSetMessage msg = new ExpandSetMessage (targetId, message.getSource());
										debug(3,"ExpandSetMessage Sent");
										str = str.concat(CommonState.getTime() + ": Not able to Register. ExpandSetMessage Sent.\n");
										EDSimulator.add(1, msg, node, pid); 
									
									} catch (NoSuchAlgorithmException e) {
											System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
								    	}
								}
			
								else {
									RegisterDMessage newMessage = new RegisterDMessage (0);
									t.send(node, message.getSource(), newMessage, pid );
									debug(3,"RegisterDMessage Sent");
									str = str.concat(CommonState.getTime() + ": Not able to Register. RegisterDMessage Sent.\n");
								}
							}
						}
					}
					if(pint == true ) {
						try {

							File filet = new File("ghar/logdump/feed_set" + k );
			 
							// if file doesnt exists, then create it
							if (!filet.exists()) {
								filet.createNewFile();
							}
			 
							FileWriter fwt = new FileWriter(filet.getAbsoluteFile(), true);
							BufferedWriter bwt = new BufferedWriter(fwt);
							bwt.write( str);
							bwt.close();
			 
						} catch (IOException e) {
							e.printStackTrace();
						}
					}	
					
				} else { 					
					
					//  just forward the msg. 

					Node dest = find_nearest_neighbour(target,node);
					t.send(node, dest, message, pid ); 
					//debug(1,"RegisterIndexMessage Forwarded"); 
									
				} 

					

			}

		}


		// TimeOutRegisterMessage()

		if (event.getClass() == TimeOutRegisterMessage.class) {
			
			TimeOutRegisterMessage message = (TimeOutRegisterMessage) event;			
			//int k = message.getK();

			if (state == State.WAIT4R ) {
				debug(1,"TOR");
				debugf(1,"TOR");
				
				Transport t = (Transport) node.getProtocol(p.tid);
				//activeSet = 0;

				try {  
					MD5Hash hash = new MD5Hash ("feed_set0"  );

					BigInteger targetId = hash.getHash();
					FeedIndexEntry tmp = new FeedIndexEntry( chordId, node, CommonState.getTime() + indexListET ); 
					RegisterIndexMessage newMessage = new RegisterIndexMessage (tmp, targetId,node,0);
					Node dest = find_nearest_neighbour(targetId,node);
					t.send(node, dest, newMessage, pid ); 
					debug(1,"RegisterIndexMessage Sent"); 
					debugf(1,"RegisterIndexMessage Sent to feed_set0"); 
					state = State.WAIT4R;
					TimeOutRegisterMessage nMsg = new TimeOutRegisterMessage();
					EDSimulator.add(timeOut, nMsg, node, pid); 
				} catch (NoSuchAlgorithmException e) {
					System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
				} 
			}
		}

		// RegisterAMessage()


		if (event.getClass() == RegisterAMessage.class) {
			dm++;			
			tm++;
			debug(3,"RegisterAMessage Received");
			debugf(3,"RegisterAMessage Received. Process Completed.");
			
			state = State.IDLE;
			pEnd = CommonState.getTime();
			joinTime = pEnd - pStart - 1;
			joinTime = joinTime/10;
		}

		
		// RegisterDMessage()

		if (event.getClass() == RegisterDMessage.class) {
			dm++;			
			tm++;
			debug(3,"RegisterDMessage Received");
			debugf(3,"RegisterDMessage Received");
			Transport t = (Transport) node.getProtocol(p.tid);
			RegisterDMessage message = (RegisterDMessage)event;			
			int k = message.getK();
			
			try {  
				MD5Hash hash = new MD5Hash ("feed_set" + k );
				finalSet = k;
				BigInteger targetId = hash.getHash();
				FeedIndexEntry tmp = new FeedIndexEntry( chordId, node, CommonState.getTime() + indexListET ); 
				RegisterIndexMessage newMessage = new RegisterIndexMessage (tmp, targetId,node,k);
				Node dest = find_nearest_neighbour(targetId,node);
				t.send(node, dest, newMessage, pid ); 
				debug(3,"RegisterIndexMessage Sent to feed_set" + k); 
				debugf(3,"RegisterIndexMessage Sent to feed_set" + k); 
				state = State.WAIT4R;
				TimeOutRegisterMessage nMsg = new TimeOutRegisterMessage();
				EDSimulator.add(timeOut, nMsg, node, pid); 
			} catch (NoSuchAlgorithmException e) {
				System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
			}
		}		
		

		// RemoveIndexMessage(BigInteger targetId))


		if (event.getClass() == RemoveIndexMessage.class) {
			rm++;			
			tm++;
			//debug(1,"ExpandSetMessage Received");
			RemoveIndexMessage message = (RemoveIndexMessage) event;

			BigInteger target = message.getTargetId();
			Integer k = message.getK();

			Transport t = (Transport) node.getProtocol(p.tid);

			if (target == ((ChordProtocol) node.getProtocol(pid)).chordId) {
				

				Vector<FeedIndexEntry> tmpList = feedIndexPool.get(k);
				int numIn = tmpList.size();
				for (int i =0; i<numIn; i++ ) {
					if (tmpList.get(i).getId() == target) {
						tmpList.remove(i);
						break;
					}
				}
				
				if ((k != 0 ) && ( tmpList.isEmpty() == true)) {
					try {  
						MD5Hash hash = new MD5Hash ("feed_num_of_sets");
						BigInteger targetId = hash.getHash();
						ReduceSetMessage msg = new ReduceSetMessage (targetId, k);
					//	debug(5,"ReduceSetMessage Sent");
						EDSimulator.add(1, msg, node, pid); 
		
					} catch (NoSuchAlgorithmException e) {
							System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
					}

				}

				
				
			} 	
			
				 		
			
			if (target != ((ChordProtocol) node.getProtocol(pid)).chordId) {
				
				Node tmPre = ((ChordProtocol)node.getProtocol(pid)).predecessor;
				if (tmPre == null ) tmPre = find_predecessor(((ChordProtocol)node.getProtocol(pid)).chordId.subtract(BigInteger.ONE));
				// TODO quick fix for now
				if ( idInabC(target,((ChordProtocol)tmPre.getProtocol(pid)).chordId,chordId) ) {
									
						Vector<FeedIndexEntry> tmpList = feedIndexPool.get(k);
						int numIn = tmpList.size();
						for (int i =0; i<numIn; i++ ) {
							if (tmpList.get(i).getId() == target) {
								tmpList.remove(i);
								break;
							}
						}
				
						if ((k != 0 ) && ( tmpList.isEmpty() == true)) {
							try {  
								MD5Hash hash = new MD5Hash ("feed_num_of_sets");
								BigInteger targetId = hash.getHash();
								ReduceSetMessage msg = new ReduceSetMessage (targetId, k);
							//	debug(5,"ReduceSetMessage Sent");
								EDSimulator.add(1, msg, node, pid); 
		
							} catch (NoSuchAlgorithmException e) {
									System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
							}

						}
					}
					
					
				} else { 					
					
					//  just forward the msg. 

					Node dest = find_nearest_neighbour(target,node);
					t.send(node, dest, message, pid ); 
					//debug(1,"ExpandSetMessage Forwarded"); 
									
				} 

					

			

		}	

		
		// ExpandSetMessage(BigInteger targetId, Node requester )


		if (event.getClass() == ExpandSetMessage.class) {
			um++;			
			tm++;
			//debug(1,"ExpandSetMessage Received");
			ExpandSetMessage message = (ExpandSetMessage) event;

			BigInteger target = message.getTargetId();


			Transport t = (Transport) node.getProtocol(p.tid);

			if (target == ((ChordProtocol) node.getProtocol(pid)).chordId) {
				debug(6, " added in cache -> " + (this.cacheSet.getMax()  +1) );
				cacheSet.add(cacheSet.getMax()+1);
				
				expiryTime = CommonState.getTime() + setET;
				UpdateSetMessage newMsg = new UpdateSetMessage ( new Set( this.cacheSet), expiryTime);
				EDSimulator.add(1, newMsg, node, pid);		

				//debug(5,"After Update");
				printSet(); 

				RegisterDMessage newMs = new RegisterDMessage(cacheSet.getMax());
				t.send(node,message.getRequester(),newMs, pid);
				debug(1,"RegisterDMessage Sent ");
				if(pint == true ) {
					try {
	 
						File filet = new File("ghar/logdump/numRoot" );
		 
						// if file doesnt exists, then create it
						if (!filet.exists()) {
							filet.createNewFile();
						}
		 
						FileWriter fwt = new FileWriter(filet.getAbsoluteFile(), true);
						BufferedWriter bwt = new BufferedWriter(fwt);
						bwt.write(CommonState.getTime() + ": ExpandSetMessage @ " + this.node.getID() + "\n");
						bwt.write(" Set ( " + expiryTime + " ) -> [ ");
						for (int i = 0; i< cacheSet.getCount() ; i++) {
							bwt.write( cacheSet.get(i) + ", ");
						}
						bwt.write(" ] \n");
						//bwt.flush();
						bwt.close();
		 
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} 	
			
				 		
			
			if (target != ((ChordProtocol) node.getProtocol(pid)).chordId) {
				
				Node tmPre = ((ChordProtocol)node.getProtocol(pid)).predecessor;
				if (tmPre == null ) tmPre = find_predecessor(((ChordProtocol)node.getProtocol(pid)).chordId.subtract(BigInteger.ONE));
				// TODO quick fix for now
				if ( idInabC(target,((ChordProtocol)tmPre.getProtocol(pid)).chordId,chordId) ) {
									debug(6, " added in cache -> " + (cacheSet.getMax()  +1) );
				//	debug(6, " pre cache count = " + this.cacheSet.getCount() + " max el = " + cacheSet.getMax() );					
cacheSet.add(cacheSet.getMax()+1);
				//	debug(6, " cache count = " + cacheSet.getCount() + " max el = " + cacheSet.getMax() );
					expiryTime = CommonState.getTime() + setET;
					UpdateSetMessage newMsg = new UpdateSetMessage ( new Set(cacheSet), expiryTime);
					EDSimulator.add(1, newMsg, node, pid);		

					//debug(5,"After Update");
					printSet();
					RegisterDMessage newMs = new RegisterDMessage(cacheSet.getMax());
					t.send(node,message.getRequester(),newMs, pid);
					debug(1,"RegisterDMessage Sent ");
					if (pint == true ) {
						try {
	 
							File filet = new File("ghar/logdump/numRoot" );
			 
							// if file doesnt exists, then create it
							if (!filet.exists()) {
								filet.createNewFile();
							}
			 
							FileWriter fwt = new FileWriter(filet.getAbsoluteFile(), true);
							BufferedWriter bwt = new BufferedWriter(fwt);
							bwt.write(CommonState.getTime() + ": ExpandSetMessage @ " + this.node.getID() + "\n");
							bwt.write(" Set ( " + expiryTime + " ) -> [ ");
							for (int i = 0; i< cacheSet.getCount() ; i++) {
								bwt.write( cacheSet.get(i) + ", ");
							}
							bwt.write(" ] \n");
							//bwt.flush();
							bwt.close();
			 
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					
					
				} else { 					
					
					//  just forward the msg. 

					Node dest = find_nearest_neighbour(target,node);
					t.send(node, dest, message, pid ); 
					//debug(1,"ExpandSetMessage Forwarded"); 
									
				} 

					

			}

		}	


		// ReduceSetMessage(BigInteger targetId, int k )


		if (event.getClass() == ReduceSetMessage.class) {
			um++;			
			tm++;
			//debug(1,"ReduceSetMessage Received");
			ReduceSetMessage message = (ReduceSetMessage) event;

			BigInteger target = message.getTargetId();


			Transport t = (Transport) node.getProtocol(p.tid);

			if (target == ((ChordProtocol) node.getProtocol(pid)).chordId) {
				
				if(message.getK() != 0 ) {
				cacheSet.remove(message.getK());
				debug(6, " removed from cache -> " + message.getK() );  
				expiryTime = CommonState.getTime() + setET;
				UpdateSetMessage newMsg = new UpdateSetMessage ( new Set(this.cacheSet), expiryTime);
				EDSimulator.add(1, newMsg, node, pid);		

				//debug(1,"After Update");
				//debug(5,"After reuction" );
				printSet();
				if (pint == true ) {
					try {
	 
							File filet = new File("ghar/logdump/numRoot" );
			 
							// if file doesnt exists, then create it
							if (!filet.exists()) {
								filet.createNewFile();
							}
			 
							FileWriter fwt = new FileWriter(filet.getAbsoluteFile(), true);
							BufferedWriter bwt = new BufferedWriter(fwt);
							bwt.write(CommonState.getTime() + ": ReduceSetMessage for " + message.getK() + " @ " + this.node.getID() + "\n");
							bwt.write(" Set ( " + expiryTime + " ) -> [ ");
							for (int i = 0; i< cacheSet.getCount() ; i++) {
								bwt.write( cacheSet.get(i) + ", ");
							}
							bwt.write(" ] \n");
							//bwt.flush();
							bwt.close();
			 
						} catch (IOException e) {
							e.printStackTrace();
						}
					 }
				}
			} 	
			
				 		
			
			if (target != ((ChordProtocol) node.getProtocol(pid)).chordId) {
				
				Node tmPre = ((ChordProtocol)node.getProtocol(pid)).predecessor;
				if (tmPre == null ) tmPre = find_predecessor(((ChordProtocol)node.getProtocol(pid)).chordId.subtract(BigInteger.ONE));
				// TODO quick fix for now
				if ( idInabC(target,((ChordProtocol)tmPre.getProtocol(pid)).chordId,chordId) ) {
					
					if(message.getK() != 0) {
					cacheSet.remove(message.getK());
				debug(6, " removed from cache -> " + message.getK() );  
					expiryTime = CommonState.getTime() + setET;
					UpdateSetMessage newMsg = new UpdateSetMessage ( new Set(this.cacheSet), expiryTime);		
					EDSimulator.add(1, newMsg, node, pid);		

					//debug(1,"After Update");
					//debug(5," after reduction " + cacheSet.getCount());
					printSet(); 
					if (pint == true ) {
						try {
	 
							File filet = new File("ghar/logdump/numRoot" );
			 
							// if file doesnt exists, then create it
							if (!filet.exists()) {
								filet.createNewFile();
							}
			 
							FileWriter fwt = new FileWriter(filet.getAbsoluteFile(), true);
							BufferedWriter bwt = new BufferedWriter(fwt);
							bwt.write(CommonState.getTime() + ": ReduceSetMessage for " + message.getK() + " @ " + this.node.getID() + "\n");
							bwt.write(" Set ( " + expiryTime + " ) -> [ ");
							for (int i = 0; i< cacheSet.getCount() ; i++) {
								bwt.write( cacheSet.get(i) + ", ");
							}
							bwt.write(" ] \n");
							//bwt.flush();
							bwt.close();
			 
						} catch (IOException e) {
							e.printStackTrace();
						}
					}			
}					
	
					
				} else { 					
					
					//  just forward the msg. 

					Node dest = find_nearest_neighbour(target,node);
					t.send(node, dest, message, pid ); 
					//debug(1,"ReduceSetMessage Forwarded"); 
									
				} 

					

			}

		}		



		// ConnectDMessage()


		if (event.getClass() == ConnectDMessage.class) {
			dm++;			
			tm++;
			debug(5,"ConnectDMessage Received");
			debugf(5,"ConnectDMessage Received");
			Transport t = (Transport) node.getProtocol(p.tid);
				boolean conn = false;
//long limit = 0;
			FeedIndexEntry tmpEntry ;
			while ( tmpLocalIndexList.isEmpty() == false  ) {
				//debug (5, "passed");
				int kk = CommonState.r.nextInt(tmpLocalIndexList.size());

				tmpEntry = tmpLocalIndexList.remove(kk); 
				
				if ( (activeSet ==0 ) ||  ( tmpEntry.getExpiryTime() > CommonState.getTime() )) {
					
					ConnectMessage nMessage = new ConnectMessage (node, chordId);
					t.send(node, tmpEntry.getNode(), nMessage, pid );
					debug(5, "Re ConnectMessage send to "+ tmpEntry.getNode().getID() );
					debugf(5, "Re ConnectMessage send to "+ tmpEntry.getNode().getID() );
					sReqActive = true;
					conn = true;
					state = State.WAIT4C;
					if (glimit < tmpEntry.getExpiryTime() ) glimit = tmpEntry.getExpiryTime() ;  
					//tmpLocalIndexList.add(tmpEntry);
					//feedIndexPool.put(k,tmpList);
					break;
				}
				if (glimit < tmpEntry.getExpiryTime() ) glimit = tmpEntry.getExpiryTime() ;  
			} 

			
			//	if ( tmpList == null ) {
				// all entries expired
				// send req to root node.,, 
	
			if ( conn == false ) {

				debug(5, "Can't connect to active set " + activeSet + " limit is " + glimit );
				//System.exit(0);
				try {  
					MD5Hash hash = new MD5Hash ("feed_set" + activeSet);
					BigInteger targetId = hash.getHash();
					PurgeIndexMessage newMessage = new PurgeIndexMessage (activeSet, targetId, glimit);//TODO N
					debug(3, "PurgeIndexMessage send");
					debugf(3, "Connection Not possible. PurgeIndexMessage send to feed_set" + activeSet);
					Node dest = find_nearest_neighbour(targetId,node);
					t.send(node, dest, newMessage, pid );  
				} catch (NoSuchAlgorithmException e) {
					System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
				}
				int tn = cacheSet.getCount(); 
				int k,l;
				if (tn <= 0) { l= 0; k = 0; }
				else { l = CommonState.r.nextInt(tn); k = cacheSet.get(l);}

				try {  
					MD5Hash hash = new MD5Hash ("feed_set"+k );

					BigInteger targetId = hash.getHash();
					GetIndexMessage newMessage = new GetIndexMessage (node, node, targetId, k);
					Node dest = find_nearest_neighbour(targetId,node);
					t.send(node, dest, newMessage, pid ); 
					debug(5,"Re: GetIndexMessage Sent to feed_set"+k); 
					debugf(5,"Re: GetIndexMessage Sent to feed_set"+k); 
					state = State.WAIT4L;
					sReqActive = false;
					TimeOutIndexMessage nMsg = new TimeOutIndexMessage(k, true);
					waitList.put(new Integer(k), CommonState.getTime() + timeOut);
				//	if(node.getID() == 112 ) debug(3,"reqset added " + 0);
					EDSimulator.add(timeOut, nMsg, node, pid); 
				} catch (NoSuchAlgorithmException e) {
					System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
				}	

			}	
		}


		
	
		// PCX starts here



			
		if (event.getClass() == PCXInitMessage.class) {
			
			PCXInitMessage message = (PCXInitMessage) event;			
			
			BigInteger target = message.getKey();
			int val = message.getValue();
						
			Transport t = (Transport) node.getProtocol(p.tid);

			if (target == ((ChordProtocol) node.getProtocol(pid)).chordId) {
				
				if(this.pcx_stale == true ) {
					this.pcx_stale = false;
					pcx_val = val;
					pcx_vet = 10000 + pcx_et ; // 60 secs default 
				} else {
					System.out.println("ABORT!!!");
					System.exit(0);
				}
				
			}
			
			
			if (target != ((ChordProtocol) node.getProtocol(pid)).chordId) {
				
				Node tmPre = ((ChordProtocol)node.getProtocol(pid)).predecessor;
				if (tmPre == null ) tmPre = find_predecessor(((ChordProtocol)node.getProtocol(pid)).chordId.subtract(BigInteger.ONE));
				// TODO quick fix for now
				if ( idInabC(target,((ChordProtocol)tmPre.getProtocol(pid)).chordId,chordId) ) {
					
					if(this.pcx_stale == true ) {
						this.pcx_stale = false;
						pcx_val = val;
						pcx_vet = 10000 + pcx_et ; // 60 secs default 
					} else {
						System.out.println("ABORT!!!");
						System.exit(0);
					}
					
				} else {
					
					//if (lIndexList.containsKey(target) ) lIndexList.remove(target);
					
					Node dest = find_nearest_neighbour(target,node);
					t.send(node, dest, message, pid);
				}				

			}
		}


		if (event.getClass() == PCXUpdateMessage.class) {
			
			PCXUpdateMessage message = (PCXUpdateMessage) event;			
			
			BigInteger target = message.getTargetId();
			//int val = message.getValue();
						
			Transport t = (Transport) node.getProtocol(p.tid);

			if (target == ((ChordProtocol) node.getProtocol(pid)).chordId) {
				
				if(this.pcx_stale == true ) {
					System.out.println("ERROR! ABORT!!!");
					System.exit(0);
					//this.pcx_stale = false;
					//pcx_val = val;
					//pcx_vet = 10000 + 60000 ; // 60 secs default 
				} else {
					pcx_val++;
					pcx_vet = message.getET(); 
					System.out.println("updated to " + pcx_vet);
//System.out.println("ABORT!!!");
//					System.exit(0);
				}
				
			}
			
			
			if (target != ((ChordProtocol) node.getProtocol(pid)).chordId) {
				
				Node tmPre = ((ChordProtocol)node.getProtocol(pid)).predecessor;
				if (tmPre == null ) tmPre = find_predecessor(((ChordProtocol)node.getProtocol(pid)).chordId.subtract(BigInteger.ONE));
				// TODO quick fix for now
				if ( idInabC(target,((ChordProtocol)tmPre.getProtocol(pid)).chordId,chordId) ) {
					
					if(this.pcx_stale == true ) {
						System.out.println("ERROR! ABORT!!!");
						System.exit(0);
					} else {
						pcx_val++;
						pcx_vet = message.getET();
						System.out.println("updated to " + pcx_vet);
					}
					
				} else {
					
					//if (lIndexList.containsKey(target) ) lIndexList.remove(target);
					
					Node dest = find_nearest_neighbour(target,node);
					t.send(node, dest, message, pid);
				}				

			}
		}

		// PCXRequestMessage (Node source, Node from, BigInteger targetId)

		if (event.getClass() == PCXRequestMessage.class) {
			
			//debug(1,"PCXRequestMessage Received");

			//pcx_start = CommonState.getTime(); 
			PCXRequestMessage message = (PCXRequestMessage) event;	
			if (message.getSource() == this.node) { pcx_start = CommonState.getTime(); }		
			BigInteger target = message.getTargetId();			
			Transport t = (Transport) node.getProtocol(p.tid);

			if (target == ((ChordProtocol) node.getProtocol(pid)).chordId) {

				
				if (message.getSource() != this.node) { // to guard against those messages 
									// that may have originated at the node itself
					// make entry in table 
					//BigInteger tmpId = ((ChordProtocol) message.getFrom().getProtocol(pid)).chordId;
					//RegListObject tmpEntry = new RegListObject( tmpId, 0 , message.getFrom(), true, -1 ); 
					//pcx_listist.put( tmpId, tmpEntry);	
				} 
				pcx_start = 0; // no need to take note of num hops on root node. it would always be zero

				if ( this.pcx_stale == true ) {
					System.out.println(" ERROR! ROOT NOTE NOT FOUND!! ABORT!!! ");
					System.exit(0);
					//pcx_stale = false;
					//pcx_val = 10;
				}

				if(pcx_vet < CommonState.getTime() ) pcx_vet += pcx_et;
				PCXReturnMessage newMsg = new PCXReturnMessage ( pcx_val, pcx_vet);
				if (message.getSource() != this.node) {
					t.send(node, message.getFrom(), newMsg, pid);
				} else {
					EDSimulator.add(1, newMsg, message.getFrom(), pid);		
				} 
				
			//	debug(1,"ATW");
			//	System.out.println("");

				
			}
			
			if (target != ((ChordProtocol) node.getProtocol(pid)).chordId) {
				
				Node tmPre = ((ChordProtocol)node.getProtocol(pid)).predecessor;
				if (tmPre == null ) tmPre = find_predecessor(((ChordProtocol)node.getProtocol(pid)).chordId.subtract(BigInteger.ONE));
				// TODO quick fix for now
				if ( idInabC(target,((ChordProtocol)tmPre.getProtocol(pid)).chordId,chordId) ) {

					if (message.getSource() != this.node) { // to guard against those messages 
										// that may have originated at the node itself
						// make entry in table 
						//BigInteger tmpId = ((ChordProtocol) message.getFrom().getProtocol(pid)).chordId;
						//RegListObject tmpEntry = new RegListObject( tmpId, 0 , message.getFrom(), true, -1 ); 
						//pcx_listist.put( tmpId, tmpEntry);	
					} 

					pcx_start = 0;
					if ( this.pcx_stale == true ) {
						System.out.println(" ERROR! ROOT NOTE NOT FOUND!! ABORT!!! ");
						System.exit(0);
						//pcx_stale = false;
						//pcx_val = 10;
					}

					if(pcx_vet < CommonState.getTime() ) pcx_vet += pcx_et;
					PCXReturnMessage newMsg = new PCXReturnMessage ( pcx_val, pcx_vet);
					if (message.getSource() != this.node) {
						t.send(node, message.getFrom(), newMsg, pid);
					} else {
						EDSimulator.add(1, newMsg, message.getFrom(), pid);		
					} 
				
					//debug(1,"ATW");
					//System.out.println("");
					
				} else { 					
					
					if ( this.pcx_vet > CommonState.getTime() ) {
					

						pcx_start = 0;
						PCXReturnMessage newMsg = new PCXReturnMessage ( pcx_val, pcx_vet);
						if (message.getSource() != this.node) {
							t.send(node, message.getFrom(), newMsg, pid);
						} else {
							EDSimulator.add(1, newMsg, message.getFrom(), pid);
							//System.out.println("# NEVER GONNA HAPPEN");		
						} 
				
						//debug(1,"PCXReturnMessage Sent");
	

					} else {

							if(pcx_ra == false) { 
								// if a previous request has not been made
								PCXRequestMessage newMsg = new PCXRequestMessage ( message.getSource(), node, target); 				
								Node dest = find_nearest_neighbour(target,node);
								t.send(node, dest, newMsg, pid);
								//debug(1,"PCXRequestMessage Forwarded");
								pcx_ra = true; // TODO
																
							}
							
							if (message.getSource() != this.node) { // to guard against those messages 
												// that may have originated at the node itself
								// make entry in table 
								BigInteger tmpId = ((ChordProtocol) message.getFrom().getProtocol(pid)).chordId;
								RegListObject tmpEntry = new RegListObject( tmpId, 0, message.getFrom(), false, -1 ); 
								pcx_list.put( tmpId, tmpEntry);	
							}
					}
				}

			}

		}
	

		
		// PCXReturnMessage(int val, long expiryTime) 

		if (event.getClass() == PCXReturnMessage.class) {
			
		//	debug(1,"PCXReturnMessage Received with ET = " + message.getExpiryTime());

			if(pcx_start != 0 ) {
				//debug(1," " +pcx_start); 
				pcx_hop = (int)(CommonState.getTime() - pcx_start)/50;
				pcx_start = 0;
			}


			PCXReturnMessage message = (PCXReturnMessage) event;
			pcx_val = message.getVal(); 
			pcx_vet = message.getExpiryTime();
			
			//if (node.getID()%100 == 0 ) debug(1,"PCXReturnMessage Received with ET = " + message.getExpiryTime());
			
			Transport t = (Transport) node.getProtocol(p.tid);
			pcx_ra = false;
			
			// send return Message to all the unsatisfied entries in the list

			for (Map.Entry<BigInteger, RegListObject> entry : pcx_list.entrySet()) {

				RegListObject obj = entry.getValue();
				if ( obj.getReqStatus() == false ) {
					obj.setReqStatus(true);
					t.send(node, obj.getNode(), message, pid );
					pcx_list.put(entry.getKey(), obj );
				}				
			}

		}
		
		// CUP starts here

		if (event.getClass() == CUPInitMessage.class) {
			
			CUPInitMessage message = (CUPInitMessage) event;			
			
			BigInteger target = message.getKey();
			int val = message.getValue();
						
			Transport t = (Transport) node.getProtocol(p.tid);

			if (target == ((ChordProtocol) node.getProtocol(pid)).chordId) {
				
				if(this.cup_stale == true ) {
					this.cup_stale = false;
					cup_val = val;
					cup_vet = 10000 + cup_et ; // 60 secs default 
				} else {
					System.out.println("ABORT!!!");
					System.exit(0);
				}
				
			}
			
			
			if (target != ((ChordProtocol) node.getProtocol(pid)).chordId) {
				
				Node tmPre = ((ChordProtocol)node.getProtocol(pid)).predecessor;
				if (tmPre == null ) tmPre = find_predecessor(((ChordProtocol)node.getProtocol(pid)).chordId.subtract(BigInteger.ONE));
				// TODO quick fix for now
				if ( idInabC(target,((ChordProtocol)tmPre.getProtocol(pid)).chordId,chordId) ) {
					
					if(this.cup_stale == true ) {
						this.cup_stale = false;
						cup_val = val;
						cup_vet = 10000 + cup_et ; // 60 secs default 
					} else {
						System.out.println("ABORT!!!");
						System.exit(0);
					}
					
				} else {
					
					//if (lIndexList.containsKey(target) ) lIndexList.remove(target);
					
					Node dest = find_nearest_neighbour(target,node);
					t.send(node, dest, message, pid);
				}				

			}
		}


		
		// CUPRequestMessage (Node source, Node from, BigInteger targetId)

		if (event.getClass() == CUPRequestMessage.class) {
			
			//debug(1,"CUPRequestMessage Received");

			CUPRequestMessage message = (CUPRequestMessage) event;	
			if (message.getSource() == this.node) { cup_start = CommonState.getTime(); }		
			BigInteger target = message.getTargetId();			
			Transport t = (Transport) node.getProtocol(p.tid);

			if (target == ((ChordProtocol) node.getProtocol(pid)).chordId) {

				
				if (message.getSource() != this.node) { // to guard against those messages 
									// that may have originated at the node itself
					// make entry in table 
					BigInteger tmpId = ((ChordProtocol) message.getFrom().getProtocol(pid)).chordId;
					CUPListObject tmpEntry = new CUPListObject( tmpId, true, message.getFrom(), true); 
					cup_list.put( tmpId, tmpEntry);	
				} 
				cup_start = 0; // no need to take note of num hops on root node. it would always be zero

				if ( this.cup_stale == true ) {
					System.out.println(" ERROR! ROOT NOTE NOT FOUND!! ABORT!!! ");
					System.exit(0);
					
				}

				if(cup_vet < CommonState.getTime() ) cup_vet += cup_et;
				CUPReturnMessage newMsg = new CUPReturnMessage ( cup_val, cup_vet);
				if (message.getSource() != this.node) {
					t.send(node, message.getFrom(), newMsg, pid);
				} else {
					EDSimulator.add(1, newMsg, message.getFrom(), pid);		
				} 
				
			//	debug(1,"ATW");
			//	System.out.println("");

				
			}
			
			if (target != ((ChordProtocol) node.getProtocol(pid)).chordId) {
				
				Node tmPre = ((ChordProtocol)node.getProtocol(pid)).predecessor;
				if (tmPre == null ) tmPre = find_predecessor(((ChordProtocol)node.getProtocol(pid)).chordId.subtract(BigInteger.ONE));
				// TODO quick fix for now
				if ( idInabC(target,((ChordProtocol)tmPre.getProtocol(pid)).chordId,chordId) ) {

					if (message.getSource() != this.node) { // to guard against those messages 
										// that may have originated at the node itself
						// make entry in table 
						BigInteger tmpId = ((ChordProtocol) message.getFrom().getProtocol(pid)).chordId;
						CUPListObject tmpEntry = new CUPListObject( tmpId, true, message.getFrom(), true); 
						cup_list.put( tmpId, tmpEntry);	
					} 

					cup_start = 0;
					if ( this.cup_stale == true ) {
						System.out.println(" ERROR! ROOT NOTE NOT FOUND!! ABORT!!! ");
						System.exit(0);
						//pcx_stale = false;
						//pcx_val = 10;
					}

					if(cup_vet < CommonState.getTime() ) cup_vet += cup_et;
					CUPReturnMessage newMsg = new CUPReturnMessage ( cup_val, cup_vet);
					if (message.getSource() != this.node) {
						t.send(node, message.getFrom(), newMsg, pid);
					} else {
						EDSimulator.add(1, newMsg, message.getFrom(), pid);		
					} 
				
					//debug(1,"ATW");
					//System.out.println("");
					
				} else { 					
					
					if ( this.cup_vet > CommonState.getTime() ) {
					

						cup_start = 0;
						cup_req++;
						CUPReturnMessage newMsg = new CUPReturnMessage ( cup_val, cup_vet);
						if (message.getSource() != this.node) {
							t.send(node, message.getFrom(), newMsg, pid);
						} else {
							EDSimulator.add(1, newMsg, message.getFrom(), pid);
							//System.out.println("# NEVER GONNA HAPPEN");		
						} 

						if (message.getSource() != this.node) { // to guard against those messages 
										// that may have originated at the node itself
							// make entry in table 
							BigInteger tmpId = ((ChordProtocol) message.getFrom().getProtocol(pid)).chordId;
							CUPListObject tmpEntry = new CUPListObject( tmpId, true, message.getFrom(), true); 
							cup_list.put( tmpId, tmpEntry);	
						} 
				
						//debug(1,"PCXReturnMessage Sent");
	

					} else {
							//cup_req++;
							if(cup_ra == false) { 
								// if a previous request has not been made
								CUPRequestMessage newMsg = new CUPRequestMessage ( message.getSource(), node, target); 				
								Node dest = find_nearest_neighbour(target,node);
								t.send(node, dest, newMsg, pid);
								//debug(1,"PCXRequestMessage Forwarded");
								cup_ra = true; // TODO
																
							}
							
							if (message.getSource() != this.node) { // to guard against those messages 
												// that may have originated at the node itself
								// make entry in table 
								BigInteger tmpId = ((ChordProtocol) message.getFrom().getProtocol(pid)).chordId;
								CUPListObject tmpEntry = new CUPListObject( tmpId, true, message.getFrom(), false); 
								cup_list.put( tmpId, tmpEntry);	
							} 
					}
				}

			}

		}
	

		
		// CUPReturnMessage(int val, long expiryTime) 

		if (event.getClass() == CUPReturnMessage.class) {
			
		//	debug(1,"CUPReturnMessage Received with ET = " + message.getExpiryTime());

			if(cup_start != 0 ) {
				//debug(1," " +pcx_start); 
				cup_hop = (int)(CommonState.getTime() - cup_start)/50;
				cup_start = 0;
			}


			CUPReturnMessage message = (CUPReturnMessage) event;
			cup_val = message.getVal(); 
			cup_vet = message.getExpiryTime();
			
			//if (node.getID()%100 == 0 ) debug(1,"PCXReturnMessage Received with ET = " + message.getExpiryTime());
			
			Transport t = (Transport) node.getProtocol(p.tid);
			cup_ra = false;
			
			// send return Message to all the unsatisfied entries in the list

			for (Map.Entry<BigInteger, CUPListObject> entry : cup_list.entrySet()) {

				CUPListObject obj = entry.getValue();
				if ( obj.getReqStatus() == false ) {
					obj.setReqStatus(true);
					t.send(node, obj.getNode(), message, pid );
					cup_list.put(entry.getKey(), obj );
				}				
			}

		}
		
		
		// CUPUpdateMessage(Set set, long expiryTime) 

		if (event.getClass() == CUPUpdateMessage.class) {
			
			//debug(1,"CUPUpdateMessage Received");
			cup_up++;
			Transport t = (Transport) node.getProtocol(p.tid);
			CUPUpdateMessage message = (CUPUpdateMessage) event;
//if (node.getID() == 54 ) debug(1, " got update with et = " + cup_vet + " from " + message.getNode().getID()); 
			//if ( ( message.getExpiryTime() == 420000) && (node.getID() == 93 )) printCUPList();//debug (1," reached");
//if ( ( message.getExpiryTime() == 480000) && (node.getID() == 93 )) { printCUPList(); debug (1," " + cup_vet); }
			if (cup_vet != message.getExpiryTime() ) {
				cup_val = message.getVal(); 
				cup_vet = message.getExpiryTime();
				//if (cup_up > 0 ) { debug(1,"end"); printCUPList(); System.exit(0); }
			//	cup_up++;
				//if (node.getID() == 86 ) debug(1, "YES");
				boolean interested = false;	
				//if (node.getID() == 38 ) debug(1, " got UPDATE with et = " + cup_vet + " from " + message.getNode().getID()); 
				

			
				// send update Set Message to all the registered entries in the list

				for (Map.Entry<BigInteger, CUPListObject> entry : cup_list.entrySet()) {

					CUPListObject obj = entry.getValue();
					if ( obj.getIBit() == true )  {
						CUPUpdateMessage nmsg = new CUPUpdateMessage (message.getVal(), message.getExpiryTime(), node);
						t.send(node, obj.getNode(), nmsg, pid );
						cup_list.put(entry.getKey(), obj );
						interested = true;
						//if(CommonState.getTime() > 240000 ) System.out.println("from "+ node.getID() + " to " + obj.getNode().getID());
					}				
				}

				if ((interested == false) && (cup_req == 0) ){
					//if(cup_chance == 0) 
					//	cup_chance = 1;
					//else {
						CUPDRegisterMessage msg = new CUPDRegisterMessage (chordId);
						t.send(node, message.getNode(), msg, pid);
						cup_chance = 0;
						//cup_interested = false;
					//}
				} else {
					cup_req = 0;
					cup_chance = 0;
					//cup_interested = true;
				}
				

			} else {
				 //if (cup_interested == false) {
					CUPDRegisterMessage msg = new CUPDRegisterMessage (chordId);
					t.send(node, message.getNode(), msg, pid);
					//System.exit(0);
				//}
			}



		}


		if (event.getClass() == CUPUpMessage.class) {
			
			CUPUpMessage message = (CUPUpMessage) event;			
			
			BigInteger target = message.getTargetId();
			//int val = message.getValue();
						
			Transport t = (Transport) node.getProtocol(p.tid);

			if (target == ((ChordProtocol) node.getProtocol(pid)).chordId) {
				
				if(this.cup_stale == true ) {
					System.out.println("ERROR! ABORT!!!");
					System.exit(0);
					//this.pcx_stale = false;
					//pcx_val = val;
					//pcx_vet = 10000 + 60000 ; // 60 secs default 
				} else {
					//pcx_val++;
					debug(1,"cuup");
					cup_vet = message.getET(); 
					CUPUpdateMessage nmsg = new CUPUpdateMessage(cup_val,cup_vet,node);
					// send update  Message to all the registered entries in the list

					for (Map.Entry<BigInteger, CUPListObject> entry : cup_list.entrySet()) {

						CUPListObject obj = entry.getValue();
						if ( obj.getIBit() == true )  {
							t.send(node, obj.getNode(), nmsg, pid );
							cup_list.put(entry.getKey(), obj );
							
						}				
					}
					
					//System.out.println("updated to " + pcx_vet);
//System.out.println("ABORT!!!");
//					System.exit(0);
				}
				
			}
			
			
			if (target != ((ChordProtocol) node.getProtocol(pid)).chordId) {
				
				Node tmPre = ((ChordProtocol)node.getProtocol(pid)).predecessor;
				if (tmPre == null ) tmPre = find_predecessor(((ChordProtocol)node.getProtocol(pid)).chordId.subtract(BigInteger.ONE));
				// TODO quick fix for now
				if ( idInabC(target,((ChordProtocol)tmPre.getProtocol(pid)).chordId,chordId) ) {
					
					if(this.cup_stale == true ) {
						System.out.println("ERROR! ABORT!!!");
						System.exit(0);
					} else {
						//pcx_val++;
						//debug(1,"cuup");
						cup_vet = message.getET();
						CUPUpdateMessage nmsg = new CUPUpdateMessage(cup_val,cup_vet,node);
						// send update  Message to all the registered entries in the list

						for (Map.Entry<BigInteger, CUPListObject> entry : cup_list.entrySet()) {

							CUPListObject obj = entry.getValue();
							if ( obj.getIBit() == true )  {
								t.send(node, obj.getNode(), nmsg, pid );
								cup_list.put(entry.getKey(), obj );
								//debug(1,"cuuuuuup");
							}				
						}
						//System.out.println("updated to " + pcx_vet);
					}
					
				} else {
					
					//if (lIndexList.containsKey(target) ) lIndexList.remove(target);
					
					Node dest = find_nearest_neighbour(target,node);
					t.send(node, dest, message, pid);
				}				

			}
		}


		if (event.getClass() == CUPDRegisterMessage.class) {
			
			CUPDRegisterMessage message = (CUPDRegisterMessage) event;	
			cup_dreg ++;		
			
			BigInteger id = message.getId();
			
			cup_list.remove(id);
			
						
		}


	
		
		// Added 
		
		if (event.getClass() == UpdateRMessage.class) {

			UpdateRMessage message = (UpdateRMessage) event;
			BigInteger target = message.getTarget();  

			Transport t = (Transport) node.getProtocol(p.tid);	

			if (target == ((ChordProtocol) node.getProtocol(pid)).chordId) {
				
				if ( this.getUpdatedN() > 0 ) {
					
					this.updateN(0) ; 
					expiryTime = CommonState.getTime() + nValueET;
					System.out.println(CommonState.getTime() + " UpdateRMessage Received .. "); 
					
					for (Map.Entry<BigInteger, RegListObject> entry : regList.entrySet()) {

						RegListObject obj = entry.getValue();
						if ( obj.getExpiryTime() > CommonState.getTime() ) {
							UpdateNMessage nMsg = new UpdateNMessage(this.getUpdatedN(), expiryTime);  
							t.send(node, obj.getNode(), nMsg, pid );

						}				
					}
					
					
				}
				else {
					this.updateN(message.getN());
					expiryTime = CommonState.getTime() + nValueET;
					System.out.println(CommonState.getTime() + " 1st UpdateRMessage Received .. "); 
					//fails++;
				}
				
			}
			
			
			
			if (target != ((ChordProtocol) node.getProtocol(pid)).chordId) {
				
				Node tmPre = ((ChordProtocol)node.getProtocol(pid)).predecessor;
				if (tmPre == null ) tmPre = find_predecessor(((ChordProtocol)node.getProtocol(pid)).chordId.subtract(BigInteger.ONE));
				// TODO quick fix for now
				if ( idInabC(target,((ChordProtocol)tmPre.getProtocol(pid)).chordId,chordId) ) {
					
					
					if ( this.getUpdatedN() > 0 ) {
						this.updateN(0) ; 
						expiryTime = CommonState.getTime() + nValueET; 
						//System.out.println(CommonState.getTime() + " UpdateRMessage Received @ NN .. "); 
						
						for (Map.Entry<BigInteger, RegListObject> entry : regList.entrySet()) {
							RegListObject obj = entry.getValue();
							if ( obj.getExpiryTime() > CommonState.getTime() ) {
								UpdateNMessage nMsg = new UpdateNMessage(this.getUpdatedN(), expiryTime);  
								t.send(node, obj.getNode(), nMsg, pid );
							}				
						}
					
					}
					else {
						
						this.updateN(message.getN());
						expiryTime = CommonState.getTime() + nValueET;
						//System.out.println(CommonState.getTime() + " 1st UpdateRMessage Received @ NN .. "); 
						//logg("Value of N initialized");
						//fails++;
					}
					
				} else {
					Node dest = find_nearest_neighbour(target,node);
					t.send(node, dest, message, pid);
				}	
			}
		
		}

		// Added 
		
		if (event.getClass() == InitNMessage.class) {

			InitNMessage message = (InitNMessage) event;
			BigInteger target = message.getTarget();  

			Transport t = (Transport) node.getProtocol(p.tid);	

			if (target == ((ChordProtocol) node.getProtocol(pid)).chordId) {
				
				this.root = true;
				CreateNw.valueN = 1; 
				System.out.println(CommonState.getTime() + " InitNMessage Received .. ");

				try {  
					MD5Hash hash = new MD5Hash ("feed_num_of_sets");
					BigInteger targetId = hash.getHash();
					UpdateRMessage tmpMessage = new UpdateRMessage ( CreateNw.valueN, targetId, CommonState.getTime() + nValueET, false ); 
					t.send(node, node, tmpMessage, pid );

				} catch (NoSuchAlgorithmException e) {
					System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
				}
	

				
			}
			
			
			
			if (target != ((ChordProtocol) node.getProtocol(pid)).chordId) {
				
				Node tmPre = ((ChordProtocol)node.getProtocol(pid)).predecessor;
				if (tmPre == null ) tmPre = find_predecessor(((ChordProtocol)node.getProtocol(pid)).chordId.subtract(BigInteger.ONE));
				// TODO quick fix for now
				if ( idInabC(target,((ChordProtocol)tmPre.getProtocol(pid)).chordId,chordId) ) {
					
					
					this.root = true;
					CreateNw.valueN = 1; 
					//System.out.println(CommonState.getTime() + " InitNMessage Received @ NN .. ");

					try {  
						MD5Hash hash = new MD5Hash ("feed_num_of_sets");
						BigInteger targetId = hash.getHash();
						UpdateRMessage tmpMessage = new UpdateRMessage ( CreateNw.valueN, targetId, CommonState.getTime() + nValueET, false ); 
						t.send(node, node, tmpMessage, pid );

					} catch (NoSuchAlgorithmException e) {
						System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
				}
					
				} else {
					this.root = false;					
					Node dest = find_nearest_neighbour(target,node);
					t.send(node, dest, message, pid);
				}	
			}
		
		}


		// Added 
		
		if (event.getClass() == FindNMessage.class) {
			FindNMessage message = (FindNMessage) event;
			
			BigInteger target = message.getDestination();			
			Transport t = (Transport) node.getProtocol(p.tid);

			if (target == ((ChordProtocol) node.getProtocol(pid)).chordId) {

				if ( this.getUpdatedN() > 0 ) {

					if (message.getSource() != this.node) { // to guard against those messages 
										// that may have originated at the node itself

						ReturnNMessage newMsg = new ReturnNMessage ( this.getUpdatedN(), CommonState.getTime() + 
								nValueET,message.getHC()); 
						t.send(node, message.getFrom(), newMsg, pid);
						msgCount ++ ;
					
						// make entry in table 
						BigInteger tmpid = ((ChordProtocol) message.getFrom().getProtocol(pid)).chordId;
						RegListObject tmpentry = new RegListObject( tmpid, CommonState.getTime() + regListET, message.getFrom(), true, -1 ); 
						regList.put( tmpid, tmpentry);	
					} else {
						hopCountArray = new long[hopCountNum + 1];
						hopCountArray[hopCountNum] = 0;
						hopCountNum ++ ;
					}	
					
				} else {

					fail("Value of N not ready at root node"); 
					fails++;
				}
				
			}
			
			if (target != ((ChordProtocol) node.getProtocol(pid)).chordId) {
				
				Node tmPre = ((ChordProtocol)node.getProtocol(pid)).predecessor;
				if (tmPre == null ) tmPre = find_predecessor(((ChordProtocol)node.getProtocol(pid)).chordId.subtract(BigInteger.ONE));
				// TODO quick fix for now
				if ( idInabC(target,((ChordProtocol)tmPre.getProtocol(pid)).chordId,chordId) ) {

					if ( this.getUpdatedN() > 0 ) {
				
						if (message.getSource() != this.node) {	
							ReturnNMessage newMsg = new ReturnNMessage ( this.getUpdatedN(), CommonState.getTime() + nValueET,message.getHC()); 
							msgCount ++ ;
							t.send(node, message.getFrom(), newMsg, pid);
						
							// make entry in table 
							BigInteger tmpid = ((ChordProtocol) message.getFrom().getProtocol(pid)).chordId;
							RegListObject tmpentry = new RegListObject( tmpid, CommonState.getTime() + regListET, message.getFrom(), true, -1); 
							regList.put( tmpid, tmpentry);
						} else {
							hopCountArray = new long[hopCountNum + 1];
							hopCountArray[hopCountNum] = 0;
							hopCountNum ++ ;
							System.out.println("# " + CommonState.getTime() + " from cache @ NN .. ");
						}
					}
					else {
						fail("Value of N not ready at root node NN");
						fails++;
					}
					
					
				} else { 					
					
					if ( this.expiryTime > CommonState.getTime() ) {
					
						if (message.getSource() != this.node) { 
							ReturnNMessage newMsg = new ReturnNMessage ( this.getN(), this.expiryTime,
									message.getHC()); 
							msgCount ++ ;
							t.send(node, message.getFrom(), newMsg, pid);
	
							BigInteger tmpid = ((ChordProtocol) message.getFrom().getProtocol(pid)).chordId;
							RegListObject tmpentry = new RegListObject( tmpid, CommonState.getTime() + regListET, message.getFrom(), true, -1 ); 
							regList.put( tmpid, tmpentry);
						} else {
							hopCountArray = new long[hopCountNum + 1];
							hopCountArray[hopCountNum] = 0;
							hopCountNum ++ ;
							System.out.println("# " + CommonState.getTime() + " from cache .. ");
						}

					} else {

							if((reqActive == false) || (lastReqTime + reqTimeOut < CommonState.getTime() )) { 
								// if a previous request has not been made
								FindNMessage newMsg = new FindNMessage ( message.getSource(), node, target, message.getNumTry(),
										message.getHC() + 1); 
								msgCount ++ ;
								Node dest = find_nearest_neighbour(target,node);
								t.send(node, dest, newMsg, pid);
								reqActive = true; // TODO
								lastReqTime = CommonState.getTime();
								
								startTime = CommonState.getTime() ; // not considering failures right now
								
							}
							
							if (message.getSource() != this.node) {
								BigInteger tmpid = ((ChordProtocol) message.getFrom().getProtocol(pid)).chordId;
								RegListObject tmpentry = new RegListObject( tmpid, CommonState.getTime() + regListET, message.getFrom(), false, -1 ); // value has to be returned
								regList.put( tmpid, tmpentry);
							}
					}
				}

			}
		}

		if (event.getClass() == ReturnNMessage.class) {
		
			ReturnNMessage message = (ReturnNMessage) event;
			numOfSets = message.getN(); 
			expiryTime = message.getExpiryTime();
			
			
			this.endTime = CommonState.getTime();
			
			{
				long delay = endTime - startTime ;
				if ((delay != 0) && (startTime != 0 )) {
					hopCountArray = new long[hopCountNum + 1];
					hopCountArray[hopCountNum] = delay;
					hopCountNum ++ ;	
				}
				
				startTime = 0;
				endTime = 0;
				
			}

			Transport t = (Transport) node.getProtocol(p.tid);
			reqActive = false;
			
			
			

			// send return N Message to all the unsatisfied entries in the list

			for (Map.Entry<BigInteger, RegListObject> entry : regList.entrySet()) {

				RegListObject obj = entry.getValue();
				if ( obj.getReqStatus() == false ) {
					obj.setReqStatus(true);
					ReturnNMessage msg = new ReturnNMessage ( message.getN(), message.getExpiryTime(), message.getHC() + 1);
					msgCount ++ ;
					t.send(node, obj.getNode(), msg, pid );

					regList.put(entry.getKey(), obj );
				}				
			}

			// Query for index list

			int setNum = CommonState.r.nextInt(numOfSets);
			try {  
				MD5Hash hash = new MD5Hash ("feed_set" + setNum);
				BigInteger targetId = hash.getHash();
				//GetIndexMessage newMessage = new GetIndexMessage (node, node, targetId, setNum);
				Node dest = find_nearest_neighbour(targetId,node);
				//t.send(node, dest, newMessage, pid );  
			} catch (NoSuchAlgorithmException e) {
				System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
			}	

			

		}

		
		if (event.getClass() == UpdateNMessage.class) {

			uMsgCount++;
			
			UpdateNMessage message = (UpdateNMessage) event;
			numOfSets = message.getN(); 
			expiryTime = message.getETime();
			Transport t = (Transport) node.getProtocol(p.tid);
			
			if (lastUpdatedVal != numOfSets ) {
				lastUpdatedVal = numOfSets;
				for (Map.Entry<BigInteger, RegListObject> entry : regList.entrySet()) {
						RegListObject obj = entry.getValue();
						if ( obj.getExpiryTime() > CommonState.getTime() ) {
							t.send(node, obj.getNode(), message, pid );
						}
				}
			}

		}
		
		
			
	}

	/* TODO */	
	public Object clone() {
		ChordProtocol cp = new ChordProtocol(prefix);
		String val = BigInteger.ZERO.toString();
		cp.chordId = new BigInteger(val);
		cp.fingerTable = new Node[m];
		cp.successorList = new Node[succLSize];
		cp.currentNode = 0;
		return cp;
	}

	/* TODO */
	public long[] getLookupMessage() {
		return hopCountArray;
	}

	/* Add myNode in the network at the current place by connecting to its appropriate successor */
	public void stabilize(Node myNode) { 
		//this.node = myNode;
	//	logg("Stabilize start;");
		try {
			//updateSuccessor();
			Node node = ((ChordProtocol) successorList[0].getProtocol(p.pid)).predecessor;
		//	logg ( ((ChordProtocol) successorList[0].getProtocol(p.pid)).chordId + " succ = " + successorList[0].getIndex());
		//	logg ( chordId + "");
		//	logg ( ((ChordProtocol) node.getProtocol(p.pid)).chordId + " pre = " + node.getIndex());
			if ((node != null) && (node.isUp() != false)) {
				if (this.chordId == ((ChordProtocol) node.getProtocol(p.pid)).chordId)
					return; // if currentNode is already a predecessor, no need to stabilize
				BigInteger remoteID = ((ChordProtocol) node.getProtocol(p.pid)).chordId;
				if (idInabC(remoteID, chordId, ((ChordProtocol) successorList[0]
						.getProtocol(p.pid)).chordId))
					{successorList[0] = node; // here stabilized needs to be called again but we will call it from outside
				//	logg("Succ changed to " + node.getIndex());
					}						// of this function, that is from the join function 
				// no need to change values for node ( predecessor ) here, it may be correctly placed 
				((ChordProtocol) successorList[0].getProtocol(p.pid))
						.notify(myNode); // notify the new successor to change its predecessor on some conditions ... 
			} else {
		    //	System.out.println("No pre found");
				((ChordProtocol) successorList[0].getProtocol(p.pid)).predecessor = myNode;
			//	System.exit(0);
			}
	//		System.out.println("AD");
			 node = ((ChordProtocol) successorList[0].getProtocol(p.pid)).predecessor;
			log ( "pre = " + node.getID());
		//	printSuccList();
			updateSuccessorList(myNode); 
		//	printSuccList();
	//		logg("Stabalize over");
		} catch (Exception e1) {
			e1.printStackTrace();
			updateSuccessor();
		}
	}

	public void updateSuccessorList(Node myNode) throws Exception {
		this.node = myNode;
		try {
		//	System.out.println("USL s");
		/*	while (successorList[0] == null || successorList[0].isUp() == false) {
				updateSuccessor(); // finds next entry in the successor list
			}*/
			if (successorList[0] == null || successorList[0].isUp() == false) {
			//	System.out.println("incorrect updateSuccessorList() call"); // finds next entry in the successor list
				updateSuccessor();
				//return;
			}
		//	System.out.println("USL over");
			// just copies the successor list of the neighbour ( first successor )
			System.arraycopy(((ChordProtocol) successorList[0]
					.getProtocol(p.pid)).successorList, 0, successorList, 1,
					succLSize - 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void notify(Node node) throws Exception {
		BigInteger nodeId = ((ChordProtocol) node.getProtocol(p.pid)).chordId;
		if (predecessor == null) {
			predecessor = node;
		//	System.out.println("test");
		}
		
		else if (idInabC(nodeId, ((ChordProtocol) predecessor
						.getProtocol(p.pid)).chordId, this.chordId)) {
			Node tmp = predecessor;
		//	System.out.println("test2");
			((ChordProtocol) predecessor.getProtocol(p.pid)).successorList[0] = node;
			((ChordProtocol) node.getProtocol(p.pid)).predecessor = predecessor;
			predecessor = node;
			//((ChordProtocol) tmp.getProtocol(p.pid)).stabilize(tmp); // not necessary
		} else {
		//	System.out.println("testn");
		}
		// TODO : change here 
		
	}
	
	public void notifys(Node node)  {
		BigInteger nodeId = ((ChordProtocol) node.getProtocol(p.pid)).chordId;
		if (predecessor == null) {
			predecessor = node;
			System.out.println("test");
		}
		
		else if (idInab(((ChordProtocol) predecessor
						.getProtocol(p.pid)).chordId, this.chordId, nodeId)) {
			Node tmp = predecessor;
		//	System.out.println("test3");
			((ChordProtocol) predecessor.getProtocol(p.pid)).successorList[0] = node;
			((ChordProtocol) node.getProtocol(p.pid)).predecessor = predecessor;
			predecessor = node;
			((ChordProtocol) tmp.getProtocol(p.pid)).stabilize(tmp);
		}
		// TODO : change here 
		
	}

	public void updateSuccessor() {
		// find the first successor alive
		boolean searching = true;
		varSuccList = 0 ; // ABM
		Node myNode = this.node;
		while (searching) {
			try { 
				Node node = successorList[varSuccList]; // varSuccList = just a tmp variable used 
				varSuccList++;
				successorList[0] = node;
				//System.out.println("US s");
				/*if (successorList[0] == null
						|| successorList[0].isUp() == false) {
					if (varSuccList >= succLSize - 1) { // >= or > to be used ??
						searching = false;
						varSuccList = 0;
					} else
						updateSuccessor();  // in a sense this searches further
						// it can be implemented in a while loop without involving recursion ( which is causing stack overflow )
				}
				updateSuccessorList();
				searching = false; */
				if (successorList[0] == null || successorList[0].isUp() == false) {
					if (varSuccList >= succLSize - 1) { // >= or > to be used ??
						searching = false;
						varSuccList = 0;
						//System.err.println("Node is disconnected from network. Start over.");
						rejoin(myNode);
						
					} else {
						//System.out.println("Value = " + varSuccList);
						continue;
					}
				} else {
					//System.out.println("US else");
					updateSuccessorList(myNode);
					searching = false;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // end of while
	}
	
	public void rejoin ( Node node) {
		   // System.out.println("Trying to join");
			Random generator = new Random();
			//System.out.println("New Node created");
			if ( node == null) return ;
			ChordProtocol cp = (ChordProtocol) node.getProtocol(p.pid);
		
			// search a random node to join
			Node n;
			do {
				n = Network.get(generator.nextInt(Network.size()));
			} while (n == null || n.isUp() == false); // where is isUp() implemented ??
			
			//System.out.println("While over");
			
			ChordProtocol cpRemote = (ChordProtocol) n.getProtocol(p.pid);

			Node successor = cpRemote.find_successor(cp.chordId);
			cp.successorList[0] = successor;
			
			
			long succId = 0;
			BigInteger lastId = ((ChordProtocol) Network.get(Network.size() - 1)
					.getProtocol(p.pid)).chordId; // TODO 
			int progress = 10; 
			do {
				cp.stabilizations++;
				succId = cp.successorList[0].getID();
				cp.stabilize(node);
				if (((ChordProtocol) cp.successorList[0].getProtocol(p.pid)).chordId
						.compareTo(cp.chordId) < 0) {
				//	System.out.println("TEST");
					progress = 10; 
					cp.successorList[0] = ((ChordProtocol) cp.successorList[0]
							.getProtocol(p.pid)).find_successor(cp.chordId);
				}
				progress-- ; 
				if ( progress == 0 ) {
					//System.out.println("New start");
					do {
						n = Network.get(generator.nextInt(Network.size()));
					} while (n == null || n.isUp() == false); // where is isUp() implemented ??
					progress = 10;
					cp.successorList[0] = ((ChordProtocol) n.getProtocol(p.pid)).find_successor(cp.chordId);
				}
				// control was not the last element of the network
				if (cp.chordId.compareTo(lastId) > 0) {
					cp.successorList[0] = Network.get(0);
					break;
				}
			} while (cp.successorList[0].getID() != succId
					|| ((ChordProtocol) cp.successorList[0].getProtocol(p.pid)).chordId
							.compareTo(cp.chordId) < 0);
			cp.fixFingers();
		
	}

	private boolean idInab(BigInteger id, BigInteger a, BigInteger b) {
		if ((a.compareTo(id) == -1) && (id.compareTo(b) == -1)) {
			return true;
		}
		return false;
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

	public Node find_successor(BigInteger id) { // FROM PAPER : find the node responsible for this id, that is, 
		//the live node at or immediately after this id.
		log ( "in fs "+ this.node.getIndex());
	//	logg ( id + " to find " + getIndex(id));
	//	logg (chordId + " at " + getIndex(chordId));
		//if ( getIndex(chordId) == 727)  printSuccList();
	//	log("test " + getIndex(chordId)); // printSuccList();
		try {
			
			if ( this.chordId == id ) return this.node; // TODO modify it.. this.node has been artificially created 
			
		/*	if (this.node.getIndex() == 835) {
			//	System.out.println( "succ = " + successorList[0].getIndex()) ;
				log ( ""+chordId);
				log ("" + id);
				log ("" + ((ChordProtocol) successorList[0].getProtocol(p.pid)).chordId );
			} */
		
			if (successorList[0] == null || successorList[0].isUp() == false) { // if successor list is invalid, //
				//validate it first
			//	logg ("tag1");
				updateSuccessor();
			}
			
			//if ( this.chordId == id ) return successorList[0]
			if (idInabC(id, this.chordId, ((ChordProtocol) successorList[0] // idInab -> idInabC
					.getProtocol(p.pid)).chordId)) {
			//	logg("tag2");
				return successorList[0];
				
			} else {
				
				if( ((ChordProtocol) successorList[0].getProtocol(p.pid)).chordId.compareTo(id) == 0)
					return successorList[0];
				
				Node tmp = closest_preceding_node(id); // what if successorList[0].id == id ?? 
			//	logg (((ChordProtocol) tmp.getProtocol(p.pid)).chordId + " cpp gives " + tmp.getIndex()  );
				BigInteger tmpId = ((ChordProtocol)tmp.getProtocol(p.pid)).chordId;
				if ( tmpId == chordId ) {  
					//((ChordProtocol) tmp.getProtocol(p.pid)).printSuccList();
				//((ChordProtocol) tmp.getProtocol(p.pid)).printFingers(); 
				return tmp; }  // TODO 
				log("node = " + tmp.getIndex() + " " + id.subtract(tmpId).bitCount());
				
				//if ( this.node == tmp) return tmp; // TODO : improve this solution
				//else 
					return ((ChordProtocol) tmp.getProtocol(p.pid)).find_successor(id);
				//return tmp;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return successorList[0];
	} 
	
	public Node find_successor_once(BigInteger id) { // FROM PAPER : find the node responsible for this id, that is, 
		//the live node at or immediately after this id.
		//log ( "in fso");
		//if ( getIndex(chordId) == 727)  printSuccList();
	//	log("test " + getIndex(chordId)); // printSuccList();
		try {
			
			//if ( this.chordId == id ) return this.node; // TODO modify it.. this.node has been artificially created 
		
			if (successorList[0] == null || successorList[0].isUp() == false) { // if successor list is invalid, //
				//validate it first
			//	log ("tag1");
				updateSuccessor();
			}
			
			//if ( this.chordId == id ) return successorList[0]
			if (idInabC(id, this.chordId, ((ChordProtocol) successorList[0] // idInab -> idInabC
					.getProtocol(p.pid)).chordId)) {
			//	log("tag2");
				return successorList[0];
				
			} else {
				
				if( ((ChordProtocol) successorList[0].getProtocol(p.pid)).chordId.compareTo(id) == 0)
					return successorList[0];
				
				Node tmp = closest_preceding_node(id); // what if successorList[0].id == id ?? 
				BigInteger tmpId = ((ChordProtocol)tmp.getProtocol(p.pid)).chordId;
				//log("node = " + tmp.getIndex() + " " + id.subtract(tmpId).bitCount());
				
				//if ( this.node == tmp) return tmp; // TODO : improve this solution
				//else 
					//return ((ChordProtocol) tmp.getProtocol(p.pid)).find_successor(id);
				return tmp;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return successorList[0];
	} 

/*	private Node closest_preceding_node(BigInteger id) {
		for (int i = m; i > 0; i--) {
			try {
				if (fingerTable[i - 1] == null
						|| fingerTable[i - 1].isUp() == false) {
					continue;
				}
				BigInteger fingerId = ((ChordProtocol) (fingerTable[i - 1]
						.getProtocol(p.pid))).chordId;
				if ((idInab(fingerId, this.chordId, id))
						|| (id.compareTo(fingerId) == 0)) {
					return fingerTable[i - 1];
				}
				if (fingerId.compareTo(this.chordId) == -1) {
					// sono nel caso in cui ho fatto un giro della rete
					// circolare
					if (idInab(id, fingerId, this.chordId)) {
						return fingerTable[i - 1];
					}
				}  
				if ((id.compareTo(fingerId) == -1)
						&& (id.compareTo(this.chordId) == -1)) {
					if (i == 1)
						return successorList[0];
					BigInteger lowId = ((ChordProtocol) fingerTable[i - 2]
							.getProtocol(p.pid)).chordId;
					if (idInab(id, lowId, fingerId))
						return fingerTable[i - 2];
					else if (fingerId.compareTo(this.chordId) == -1)
						continue;
					else if (fingerId.compareTo(this.chordId) == 1)
						return fingerTable[i - 1];
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (fingerTable[m - 1] == null)
			return successorList[0];
		return successorList[0];
	} */
	
	public Node closest_preceding_node(BigInteger id) {
	//	if (this.node.getIndex() == 7303)  { printSuccList(); printFingers(); }
		//BigInteger preId = ((ChordProtocol) (predecessor.getProtocol(p.pid))).chordId;
		//BigInteger ppreId = ((ChordProtocol)(((ChordProtocol) (predecessor.getProtocol(p.pid))).predecessor.getProtocol(p.pid))).chordId; 
		//if ( idInabC(id,ppreId,preId)) return predecessor;
		boolean t = true;
		for (int i = m; i > 0; i--) {
			try {
				if (fingerTable[i - 1] == null
						|| fingerTable[i - 1].isUp() == false) {
					//fixFingers();  
					if ((this.node.getIndex() == 7303) && false) {
		//				logg("fix fingers called");
						printFingers(); 
						t = false;
					}
					continue;
				}
				BigInteger fingerId = ((ChordProtocol) (fingerTable[i - 1]
						.getProtocol(p.pid))).chordId;
				if ((idInabC(fingerId, this.chordId, id))
						|| (id.compareTo(fingerId) == 0)) {
					//if (id.compareTo(BigInteger. .valueOf(232798229045287842454616700156363484)) == 0) 
				//	if (this.node.getIndex() == 999 )	System.out.println ("took " + getIndex(fingerId));
					return fingerTable[i - 1];
				}
			//	if (this.node.getIndex() == 999 )	 {  System.out.println("left " + getIndex(fingerId)); }
				
			//	if ( i == 1) 
				//	return successorList[0]; 
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	//	if (fingerTable[m - 1] == null)
		//	return successorList[0];
		//log(this.node);
		//log("test");
		updateSuccessor();
		BigInteger ts = ((ChordProtocol) (successorList[0].getProtocol(p.pid))).chordId;
		if ( idInabC(ts,chordId,id)) return successorList[0];
		return this.node;
	}
	

	// debug function
	public void printFingers() {
		log("start");
		for (int i = fingerTable.length - 1; i >= 0; i--) {
			if (fingerTable[i] == null) {
				System.out.println("Finger " + i + " is null");
				continue;
			}
			if ((((ChordProtocol) fingerTable[i].getProtocol(p.pid)).chordId)
					.compareTo(this.chordId) == 0)
				break;
			System.out
					.println("Finger["
							+ i
							+ "] = "
							+ fingerTable[i].getIndex()
							+ " chordId "
							+ ((ChordProtocol) fingerTable[i]
									.getProtocol(p.pid)).chordId);
		}
		log("done");
	}

	public void fixFingers() {
		//logg("in ff m = " + next);
		if (next >= m )
			next = 0;
		//logg("in ff m == " + next);
		if (fingerTable[next] != null && fingerTable[next].isUp()) {
			next++;
			return;
		}
		BigInteger base;
		if (next == 0)
			base = BigInteger.ONE;
		else {
			base = BigInteger.valueOf(2);
			for (int exp = 1; exp < next; exp++) {
				base = base.multiply(BigInteger.valueOf(2));
			}
		}
		BigInteger pot = this.chordId.add(base);
	//	logg(pot + " = pot");
	//	BigInteger idFirst = ((ChordProtocol) Network.get(0).getProtocol(p.pid)).chordId;
//		BigInteger idLast = ((ChordProtocol) Network.get(Network.size() - 1)
	//			.getProtocol(p.pid)).chordId; // TODO
		
		maxId = BigInteger.valueOf(2);
		for (int exp = 1; exp < m; exp++) {
			maxId = maxId.multiply(BigInteger.valueOf(2));
		}
		if (pot.compareTo(maxId) == 1) {
			pot = (pot.mod(maxId));
			if (pot.compareTo(this.chordId) != -1) {
				next++;
				return;
			}
		//	if (pot.compareTo(idFirst) == -1) {
			//	this.fingerTable[next] = Network.get(Network.size() - 1);
			//	next++;
			//	return;
		//	}
		}
		do {
		//	logg(pot + " = pot");
			fingerTable[next] = find_successor(pot);
		//	pot = pot.subtract(BigInteger.ONE);
		//	((ChordProtocol) successorList[0].getProtocol(p.pid)).fixFingers();
		} while (fingerTable[next] == null || fingerTable[next].isUp() == false);
		next++;
	}
	
	public void fixFingersNew() {
		
		BigInteger base;
		for (int j=0; j<m; j++) {
		//	System.out.println(j + "");
			if (j == 0)
			base = BigInteger.ONE;
			else {
				base = BigInteger.valueOf(2);
				for (int exp = 1; exp < j; exp++) {
				base = base.multiply(BigInteger.valueOf(2));
				}
			}
			
			BigInteger pot = this.chordId.subtract(base);
			if (j==119) {
		//		System.out.println(j + " this" + successorList[0].getIndex());
		//		System.out.println(chordId + "");
		//		System.out.println(base + "");
		//		System.out.println(pot + "");
			}
	//	BigInteger idFirst = ((ChordProtocol) Network.get(0).getProtocol(p.pid)).chordId;
	//	BigInteger idLast = ((ChordProtocol) Network.get(Network.size() - 1)
	//			.getProtocol(p.pid)).chordId; // TODO
			if (pot.compareTo(BigInteger.ZERO) == -1) {
				pot = (pot.add(maxId));
			//	if (j==120) System.out.println("hello");
			}
			
			Node pn = find_predecessor(pot);
			if (j==119) {
		//		System.out.println(pn.getIndex());
			}
			((ChordProtocol)pn.getProtocol(p.pid)).update_finger_table(this.node, j);
			
		}
			
	}
	
	public Node find_predecessor (BigInteger id) {
		
	//	logg ("find prede of " + id );
		Node n = this.node;
		if (chordId.compareTo(id) == 0)
			return n;
		
		updateSuccessor(); 
		BigInteger succId = ((ChordProtocol)successorList[0].getProtocol(p.pid)).chordId;
		if  ( (id.compareTo(succId) == 0) ) 
			return successorList[0];
		
		BigInteger nodeId = this.chordId;
		
		//System.out.println("tst");
		int count = 0;
		while ( idInabC (id,nodeId,succId) == false){
		//	System.out.println("tst " + n.getIndex());
			if (count == 15){
		//		System.out.println(id + " " + getIndex(id));
		//		System.out.println(nodeId + " "+ getIndex(nodeId) );
		//		System.out.println(succId + " " + getIndex(succId));
		//		if ( ((ChordProtocol)n.getProtocol(p.pid)).successorList[0] == null) System.out.println("null");
		//		if ( ((ChordProtocol)n.getProtocol(p.pid)).successorList[0].isUp() == false) System.out.println("not up");
				//Node succ = ((ChordProtocol)n.getProtocol(p.pid)).successorList[0];
			//	System.out.println("      " + n.getIndex());
			}
			
			((ChordProtocol)n.getProtocol(p.pid)).fixFingers();
			
			n = ((ChordProtocol)n.getProtocol(p.pid)).closest_preceding_node(id);
	//		if (count == 15 ) System.exit(0);
			count ++ ;
			nodeId = ((ChordProtocol)n.getProtocol(p.pid)).chordId;
			Node tmpSucc = ((ChordProtocol)n.getProtocol(p.pid)).successorList[0];
			if ( ( tmpSucc == null ) || (tmpSucc.isUp() == false )) {
				((ChordProtocol)n.getProtocol(p.pid)).updateSuccessor();
			//	logg("check");
				tmpSucc = ((ChordProtocol)n.getProtocol(p.pid)).successorList[0];
			}
			succId =   ((ChordProtocol)tmpSucc.getProtocol(p.pid)).chordId;

		}
		
		return n;
		
	}
	
	public void update_finger_table(Node n, int j) {
		BigInteger nodeId = ((ChordProtocol)n.getProtocol(p.pid)).chordId;
		BigInteger fingerId = ((ChordProtocol)fingerTable[j].getProtocol(p.pid)).chordId;
		if ( idInabC(nodeId,chordId,fingerId)) {
			fingerTable[j] = n;
		//	System.out.println("FT " + j + " updated for node " + this.node.getIndex());
			if (predecessor == null ) { 
			//	logg(((ChordProtocol)predecessor.getProtocol(p.pid)).chordId + ""); 
				predecessor =  find_predecessor(chordId.subtract(BigInteger.ONE)); 
			//	logg(((ChordProtocol)nn.getProtocol(p.pid)).chordId + ""); 
				//logg(chordId+ "");
				//logg("exit"); System.exit(0); 
				}
			((ChordProtocol)predecessor.getProtocol(p.pid)).update_finger_table(n, j);
		}
			
	}
	
	public void createFingerTable () {
		
	//	BigInteger idFirst = ((ChordProtocol) Network.get(0).getProtocol(pid)).chordId;
//		BigInteger idLast = ((ChordProtocol) Network.get(Network.size() - 1)
	//			.getProtocol(pid)).chordId;
	//	for (int i = 0; i < Network.size(); i++) { // updating data for each node
		//	Node node = (Node) Network.get(i);
	//		ChordProtocol cp = (ChordProtocol) node.getProtocol(pid); 
			/* While creating the successor list, it must be noted that, we have to take special attention when the 
			   network is building up and the size of the network is less than successorLSize, in this case, the 
			   entries would be repeated 
			*/
	//		if (Network.size() == 1 ) {
		//		for (int a = 0; a < successorLsize; a++)
			//		cp.successorList[a] = null;
		//	}
		//	else {
			//	for (int a = 0; a < successorLsize; a++) { // creating successor list				
				//	if ( a >= Network.size() - 1 ) 
					//	cp.successorList[a] = cp.successorList[a-1] ; 				
				//	else if (a + i < (Network.size() - 1))
					//	cp.successorList[a] = Network.get(a + i + 1);
			//		else
				//		cp.successorList[a] = Network.get(a + i + 1 - Network.size()); 
					// changed from a to a + i + 1 - Network.size() ... working properly
			//	}
		//	}


	//		if (Network.size() == 1 ) 
	//			cp.predecessor = null ;
	//		else if (i > 0) 
		//		cp.predecessor = (Node) Network.get(i - 1);
		//	else
		//		cp.predecessor = (Node) Network.get(Network.size() - 1);

			log("in cft");
			
			fingerTable[0] = successorList[0];
			BigInteger fingeId = ((ChordProtocol)fingerTable[0].getProtocol(p.pid)).chordId;
			maxId = BigInteger.valueOf(2);
			for (int exp = 1; exp < m; exp++) {
				maxId = maxId.multiply(BigInteger.valueOf(2));
			}
		//	log (" " + fingerTable[0].getIndex() + " " + finge ); 
			int j;
			for (j = 1; j < m; j++) {  // creating finger table
				BigInteger base;
			    base = BigInteger.valueOf(2);
					for (int exp = 1; exp < j; exp++) {
						base = base.multiply(BigInteger.valueOf(2));
					}
				
				BigInteger pot = chordId.add(base);
			//	log(fingeId + "");
			//	log(pot + "");
				
				
				//log(maxId + ""); 
				
				
				if (pot.compareTo(maxId) == 1) { // if id exceeds last id
					pot = (pot.mod(maxId));  // take mod
					if (pot.compareTo(chordId) != -1) { 
						continue;
					}
				}
				
			//	log(pot + "");
				BigInteger fingerId = ((ChordProtocol)fingerTable[j-1].getProtocol(p.pid)).chordId;
				if ( idInabC(pot,chordId,fingerId)) {
					fingerTable[j] = fingerTable[j-1];
				}
				else {
					fingerTable[j] = ((ChordProtocol)fingerTable[0].getProtocol(p.pid)).find_successor(pot);
				}
					
			}
	//	}
	}

	/**
	 */
	public void emptyLookupMessage() {
		hopCountNum = 0; // index = 0;
		this.hopCountArray = new long[0];//this.lookupMessage = new int[0];
	}

	private BigInteger minBI( BigInteger a, BigInteger b ) {
		if ( a.compareTo(b) == 1 ) return b;
		return a ; 
	}

	public Node find_nearest_neighbour ( BigInteger target, Node node ) {
	//	log("in fnn");

		
		Node tmPre = ((ChordProtocol)node.getProtocol(p.pid)).predecessor;
		if (tmPre == null ) tmPre = find_predecessor(((ChordProtocol)node.getProtocol(p.pid)).chordId.subtract(BigInteger.ONE));
		BigInteger preId = ((ChordProtocol) (tmPre.getProtocol(p.pid))).chordId;

		Node tmPPre = ((ChordProtocol)tmPre.getProtocol(p.pid)).predecessor;
		if (tmPPre == null ) tmPPre = find_predecessor(((ChordProtocol)tmPre.getProtocol(p.pid)).chordId.subtract(BigInteger.ONE));
		BigInteger ppreId = ((ChordProtocol) (tmPPre.getProtocol(p.pid))).chordId; 
		if ( idInabC(target,ppreId,preId)) return tmPre;
		
		//if ( idInabC(target, ((ChordProtocol)predecessor.getProtocol(p.pid)).chordId, chordId ) == true ) return node;
		Node dest = find_successor_once(target);		
		
		if (dest.isUp() == false) {
			do {
			//	log("in fnn in");
				varSuccList = 0;
				stabilize(node); 
				stabilizations++;
				fixFingers();  
				dest = find_successor_once(target); // try again 
			} while (dest.isUp() == false);
		} // next hop found. If next hop is the first entry in the 
		
		return dest; 
	}
	
	public void printSuccList () {
		System.out.println("SuccessorList for " + getIndex(chordId)); 
		for ( int c = 0; c< succLSize; c++ ){
			if ( (successorList[c] != null ) && (successorList[c].isUp() == true) ) {
				System.out.println("Successor "+ c + " = " + successorList[c].getIndex() + " " 
							+ ((ChordProtocol)successorList[c].getProtocol(p.pid)).chordId  );
			}
		}
	}

}


