/**
 * 
 */
package peersim.chord;
import peersim.core.Node;

import java.math.BigInteger;
import java.util.ArrayList;

import peersim.core.Control;
import peersim.core.Network;
import peersim.core.CommonState;
import peersim.config.Configuration;

/**
 * @author Andrea
 * 
 */
public class CUPObserver implements Control {

	private static final String PAR_PROT = "protocol";
	
//	private static int rValueNode = -1;
	
//	private static int pSize = 1000;

	private final String prefix;
	private final int pid;
	private int tc = 0;
	private int tnc = 0;
	private int tmsgc = 0;
	private int tdreg = 0;
//	private float avg;
//	private float cnt;
//
	/**
	 * 
	 */
	public CUPObserver(String prefix) {
		this.prefix = prefix;
		this.pid = Configuration.getPid(prefix + "." + PAR_PROT);
//		avg = 0;
//		cnt = 1;
//		System.out.println("# Time"  + " | " + "updated" +  
//				" | " + "total" + " | " + "diff"+ " | " + " value N" + " | " +  "avg time" + " | " + "hop count" + " | " 
//				+ "logSize*100" + " | " + "avg hop");
		
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
		int count = 0;
		int hc = 0;
		int hnc = 0;
		int msgc = 0;
		int dreg = 0;
		/*int totalStab = 0;
		int totFails = 0;
		
		long hopCount = 0 ;
		int hopCC = 0;
		float avgHop = 0;
		//int pSize = 1000;
		int hc = 0;
		int umc = 0;
		
		ArrayList hopCounters = new ArrayList(); // struttura dati che
													// memorizza gli hop di
													// tutti i mess mandati
		hopCounters.clear();
		int max = 0;
		
		long totalTime = 0;
		long totalMsg = 0;
		
		int min = Integer.MAX_VALUE;
		int latestValue = CreateNw.valueN; */
		//checkCorrectness (size); 
		/*if (rValueNode == -1 ) {
			for (int i = 0; i < size; i++) { 
				ChordProtocol cpT = (ChordProtocol) Network.get(i).getProtocol(pid);
				if (cpT.rValue == false) continue;
				rValueNode = i;
				latestValue = cpT.getUpdatedN();
				System.out.println("# " + CommonState.getTime() + " Observer at " + i);
				break;
			}
		} else {
			ChordProtocol cpTT = (ChordProtocol) Network.get(rValueNode).getProtocol(pid);
			if (cpTT.rValue == true ) {
				latestValue = cpTT.getUpdatedN();
			} else {
				for (int i = 0; i < size; i++) { 
					ChordProtocol cpT = (ChordProtocol) Network.get(i).getProtocol(pid);
					if (cpT.rValue == false) continue;
					rValueNode = i;
					latestValue = cpT.getUpdatedN();
					System.out.println("#Observer at " + i);
					break;
				}
			}
		}*/

		for (int i = 0; i < size; i++) {
				
			ChordProtocol cp = (ChordProtocol) Network.get(i).getProtocol(pid);
			
			/*totalStab = totalStab + cp.stabilizations;
			totFails = totFails + cp.fails;
			cp.stabilizations = 0;
			cp.fails = 0;*/
				
			/*if (CommonState.getTime() > 400) {
				hc += cp.msgCount;
				cp.msgCount = 0;
				umc += cp.uMsgCount++;
				cp.uMsgCount = 0;
			}
			cp.msgCount = 0;
			cp.uMsgCount = 0;
			*/
			//	if ( getReplica(cp.chordId) > 1 ) System.out.println(" Num Replica = " + getReplica(cp.chordId));  
			
			/* get number of nodes containing the latest value of n */ 
			//int valueN = cp.getN();  
			
			/*totalTime += cp.totalTime;
			totalMsg += cp.totalMsg; 
			cp.totalTime = 0;
			cp.totalMsg = 0;*/

			//if ((cp.getN() == latestValue) && (cp.expiryTime > CommonState.getTime())) count++ ;
			
			if (cp.cup_vet > CommonState.getTime() ) count ++;
			if (cp.cup_hop > 0 ) {
				hnc++;
				hc += cp.cup_hop;
				cp.cup_hop = 0;
			}

			msgc += cp.cup_up;
			cp.cup_up=0;

			dreg += cp.cup_dreg;
			cp.cup_dreg = 0;
			
			
			//if (cp.index != 0) {
			/*for (int j = 0; j < cp.hopCountNum; j++) {//hopCounters.add(counters[j]);
				hopCount += cp.getLookupMessage()[j];
				hopCC ++ ;
			}

			cp.emptyLookupMessage();*/
		
			
			/*if (CommonState.getTime() > 1000) {
				int dif = Network.size() - pSize;
				pSize = Network.size();
				avgHop = (float)hc/dif;
				//cp.msgCount = 0;
			}
			if (hopCC == 0) hopCC = 1; 
			double media = meanCalculator(hopCounters);
			int logSize = 2* (int)(Math.log(Network.size())/Math.log(2));*/
		/*	if (media > 0)
				System.out.println("Time: "+ CommonState.getTime() + " Count: "+ count + " Mean:  " + media + " Max Value: " + max
						+ " Min Value: " + min + " # Observations: "
						+ hopCounters.size());
			System.out.println("	 # Stabilizations: " + totalStab + " # Failures: "
					+ totFails);
			System.out.println("");*/
			//if (CommonState.getTime() <= 11900 ) System.out.println(CommonState.getTime() + " " + isStable() +
				//	" " + size); // (float)hopCount/hopCC)
		}
			tc += hc;
			tnc += hnc;
			tmsgc += msgc;
			tdreg += dreg;
			//if (hopCC == 0) hopCC = 1; 
//			if ( CommonState.getTime() >= 5000 ) 
			System.out.println(CommonState.getTime()/1000.0  + " " + size + " " + count + " " + hc + " " + hnc + " " + (float)hc/hnc + " " + msgc) ; 
// +  " " + size + " " + latestValue + " " + hopCC + " " + (float)hopCount/hopCC );
//" " + (size-count) +  " " + latestValue + " " +  (float)hopCount/hopCC + " " + 
//					hopCC + " " + logSize*100 + " " + avgHop + " " + hc + " " + umc); // (float)hopCount/hopCC)
		
		if ( CommonState.getTime() == 999000 ) {
			System.out.println(CommonState.getTime()/1000.0 + tc + " " + tnc + " " + (float)tc/tnc) ;
			System.out.println(CommonState.getTime()/1000.0 + tmsgc + " " + tnc + " " + (float)tmsgc/tnc) ; 
			System.out.println(CommonState.getTime()/1000.0 + tdreg + " " + tnc + " " + (float)tdreg/tnc) ; 
			/*System.out.println("digraph Index0 {"); // TODO			
			for (int i = 0; i < size; i++) {
				
				ChordProtocol cp = (ChordProtocol) Network.get(i).getProtocol(pid);
			//	if (cp.numConn > 0 ) {
					
					for (CUPListObject obj : cp.cup_list.values()) {
    						//if(obj.getIBit() == true) 
							System.out.println(Network.get(i).getID() + " -> " + obj.getNode().getID() + ";" );
					}
				
			//	}
				//if (cp.streamAvail == true) 
				//	System.out.println(Network.get(i).getID() + " [style=filled,color=\".7 .3 1.0\"]; ");
			}
			System.out.println("}"); */
		}
		/*if ( CommonState.getTime() == 99000 ) {

			System.out.println("digraph Stream {");			
			for (int i = 0; i < size; i++) {
				
				ChordProtocol cp = (ChordProtocol) Network.get(i).getProtocol(pid);
				if (cp.numConn > 0 ) {
					
					for (Node node : cp.connectionList.values()) {
    						System.out.println(Network.get(i).getID() + " -> " + node.getID() + ";" );
					}
				}
				if (cp.streamAvail == true) 
					System.out.println(Network.get(i).getID() + " [style=filled,color=\".7 .3 1.0\"]; ");
			}
			System.out.println("}");

			
			System.out.println("digraph Index0 {"); // TODO			
			for (int i = 0; i < size; i++) {
				
				ChordProtocol cp = (ChordProtocol) Network.get(i).getProtocol(pid);
			//	if (cp.numConn > 0 ) {
					
					for (RegListObject obj : cp.indexRegList.values()) {
    						if(obj.getK() == 0) 
							System.out.println(Network.get(i).getID() + " -> " + obj.getNode().getID() + ";" );
					}
				
			//	}
				if (cp.streamAvail == true) 
					System.out.println(Network.get(i).getID() + " [style=filled,color=\".7 .3 1.0\"]; ");
			}
			System.out.println("}");


			System.out.println("digraph Set {"); // TODO			
			for (int i = 0; i < size; i++) {
				
				ChordProtocol cp = (ChordProtocol) Network.get(i).getProtocol(pid);
			//	if (cp.numConn > 0 ) {
					
					for (RegListObject obj : cp.regList.values()) {
    						//if(obj.getK() == 0) 
							System.out.println(Network.get(i).getID() + " -> " + obj.getNode().getID() + ";" );
					}
			//	}
				if (cp.streamAvail == true) 
					System.out.println(Network.get(i).getID() + " [style=filled,color=\".7 .3 1.0\"]; ");
			}
			System.out.println("}");

			*/

		//}
			 
		return false;
	}

	/*private void checkCorrectness(int s){
		Node node = Network.get(0);
		ChordProtocol cp = (ChordProtocol)node.getProtocol(pid);
		BigInteger tmp = cp.chordId;
		BigInteger fin ;
		int cnt=0;
		//for (int i=0; i<s; i++) {
			do{
				node = cp.successorList[0];
				cp = (ChordProtocol)node.getProtocol(pid);
				fin = cp.chordId;
				cnt++;
			//	node.updateSuccessor();
			}while ((fin.compareTo(tmp) != 0 ) && (cnt < 2*s));
	//	}
	//	cp = (ChordProtocol)node.getProtocol(pid);
		//BigInteger fin = cp.chordId;
	if (cnt >= 2*s) System.out.println("#" + CommonState.getTime() + " Verdict = " + "failed");
	else System.out.println(CommonState.getTime() + " Size = "+ s +" Verdict = " + (cnt - s));
		
	}
	
	private double meanCalculator(ArrayList list) {
		int lenght = list.size();
		if (lenght == 0)
			return 0;
		int sum = 0;
		for (int i = 0; i < lenght; i++) {
			sum = sum + ((Integer) list.get(i)).intValue();
		}
		double mean = sum / lenght;
		return mean;
	}

	private int maxArray(int[] array, int dim) {
		int max = 0;
		for (int j = 0; j < dim; j++) {
			if (array[j] > max)
				max = array[j];
		}
		return max;
	}
	
	private int getReplica( BigInteger id ) {
		int ret =0;
		for ( int i=0; i<Network.size() ; i++ ) {
			if ( id.compareTo( ((ChordProtocol) Network.get(i).getProtocol(pid)).chordId ) == 0 )
				ret ++ ;
		}
		return ret;
	}

	private int minArray(int[] array, int dim) {
		int min = 0;
		for (int j = 0; j < dim; j++) {
			if (array[j] < min)
				min = array[j];
		}
		return min;
	}*/
}
