/**
 * 
 */
package peersim.chord;

import peersim.core.*;
import peersim.config.Configuration;
import peersim.edsim.EDSimulator;

/**
 * @author Andrea
 * 
 */
public class TrafficGenerator2 implements Control {

	private static final String PAR_PROT = "protocol";

	private final int pid;

	private int valueN;

	private boolean flag;

	/**
	 * 
	 */
	public TrafficGenerator2(String prefix) {
		pid = Configuration.getPid(prefix + "." + PAR_PROT);
		valueN = 0; 
		flag = false; 
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see peersim.core.Control#execute()
	 */
	public boolean execute() {
		
		int size = Network.size();
		Node sender, target;
		int i = 0;
		do {
			i++;
			sender = Network.get(CommonState.r.nextInt(size));
			target = Network.get(CommonState.r.nextInt(size));
		} while (sender == null || sender.isUp() == false );
		//if ( flag == false ) {
		valueN++ ; // = 5;		
		//UpdateRMessage message = new UpdateNMessage( valueN, ((ChordProtocol) sender.getProtocol(pid)).chordId, true, 0);
		//EDSimulator.add(10, message, sender, pid);
		//flag = true; 
		//}
		//System.out.println("valueN:  " + valueN);
//System.out.println("");
		return false;
	}

}
