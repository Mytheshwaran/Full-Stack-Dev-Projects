package com.dhyan.snmp;

import java.io.FileWriter;
import java.util.Arrays;
import java.util.Date;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.TimeTicks;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import au.com.bytecode.opencsv.CSVWriter;

public class SNMPManager {

	int port = 161;
	String Oid = ".1.3.6.1.2.1.1.3.0";

	@SuppressWarnings({ "rawtypes", "unchecked", "resource" })
	public static void main(String[] args) throws Exception {

		SNMPManager client = new SNMPManager();

		//Used to send snmp request to agent and manager
		TransportMapping transport = new DefaultUdpTransportMapping();
		//Listen to response
		transport.listen();
		// Create Snmp object for sending data to Agent
		Snmp snmp = new Snmp(transport);

		//used to set snmp method & OID of device 
		//Exchange data using network protocols 
		PDU pdu = new PDU();
		pdu.add(new VariableBinding(new OID(client.Oid)));
		pdu.setType(PDU.GET);
		
		CSVWriter csvfile = new CSVWriter(new FileWriter("result.csv"));

		for (int ip = 201; ip <= 255; ip++) {
			
			String ipAddress="192.168.56."+ip;
			String Agent = ipAddress + "/" + client.port;
			
			ResponseEvent response = snmp.send(pdu, client.getTarget(Agent));
			if (response == null) {
				throw new RuntimeException("Time out occured");
			}

			//Receive response as PDU from the manager
			PDU PDUValue = response.getResponse();
			
			if (PDUValue != null) {
				for (VariableBinding PUDOutput : PDUValue.getVariableBindings()) {
					
					TimeTicks upTime=(TimeTicks) PUDOutput.getVariable();
					
					Date currentTime=new Date();
					System.out.println(currentTime);
					
					long machineStartTime=currentTime.getTime()-upTime.toMilliseconds();
					
					Date IST=new Date(machineStartTime);
					
					String[] tableData = { ipAddress,IST.toString()};
					System.out.println(Arrays.toString(tableData));
					csvfile.writeNext(tableData);
				}
			} else {
				String[] tableData = { ipAddress, "Machine Down" };
				System.out.println(Arrays.toString(tableData));
				csvfile.writeNext(tableData);
			}
			csvfile.flush();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Target getTarget(String Agent) {
		CommunityTarget target = new CommunityTarget();
		target.setCommunity(new OctetString("public"));
		target.setAddress(new UdpAddress(Agent));
		target.setTimeout(1000);
		target.setVersion(SnmpConstants.version2c);
		return target;
	}

}