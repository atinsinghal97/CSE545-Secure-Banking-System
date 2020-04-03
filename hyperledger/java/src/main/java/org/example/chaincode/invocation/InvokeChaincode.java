package org.example.chaincode.invocation;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.FileReader;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.example.client.CAClient;
import org.example.client.ChannelClient;
import org.example.client.FabricClient;
import org.example.config.Config;
import org.example.user.UserContext;
import org.example.util.Util;
import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.ChaincodeResponse.Status;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.EventHub;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.TransactionProposalRequest;
import java.sql.*;
import java.text.*;

public class InvokeChaincode {

	private static final byte[] EXPECTED_EVENT_DATA = "!".getBytes(UTF_8);
	private static final String EXPECTED_EVENT_NAME = "event";

	public static void main(String args[]) {
		
		try {
			String myDriver = "com.mysql.jdbc.Driver";
	        String myUrl = "jdbc:mysql://localhost:3306/secure_banking_system";
	        Class.forName(myDriver);
			java.sql.Connection conn = DriverManager.getConnection(myUrl, "admin", "xyz");
			Statement st = conn.createStatement();
		
			try {
	            Util.cleanUp();
				String caUrl = Config.CA_ORG1_URL;
				CAClient caClient = new CAClient(caUrl, null);
				// Enroll Admin to Org1MSP
				UserContext adminUserContext = new UserContext();
				adminUserContext.setName(Config.ADMIN);
				adminUserContext.setAffiliation(Config.ORG1);
				adminUserContext.setMspId(Config.ORG1_MSP);
				caClient.setAdminUserContext(adminUserContext);
				adminUserContext = caClient.enrollAdminUser(Config.ADMIN, Config.ADMIN_PASSWORD);
				
				FabricClient fabClient = new FabricClient(adminUserContext);
				
				ChannelClient channelClient = fabClient.createChannelClient(Config.CHANNEL_NAME);
				Channel channel = channelClient.getChannel();
				Peer peer = fabClient.getInstance().newPeer(Config.ORG1_PEER_0, Config.ORG1_PEER_0_URL);
				EventHub eventHub = fabClient.getInstance().newEventHub("eventhub01", "grpc://localhost:7053");
				Orderer orderer = fabClient.getInstance().newOrderer(Config.ORDERER_NAME, Config.ORDERER_URL);
				channel.addPeer(peer);
				channel.addEventHub(eventHub);
				channel.addOrderer(orderer);
				channel.initialize();
				
		        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        String query = "select id, decision_date, from_account, to_account, amount, transaction_type from transaction where TIMESTAMPDIFF(MINUTE,decision_date,NOW()) < 15 and approval_status = 1";
		        ResultSet rs = st.executeQuery(query);
		        if(rs!=null) {
		        	while(rs.next()) {
		        		TransactionProposalRequest request = fabClient.getInstance().newTransactionProposalRequest();
						ChaincodeID ccid = ChaincodeID.newBuilder().setName(Config.CHAINCODE_1_NAME).build();
						request.setChaincodeID(ccid);
						request.setFcn("createTransaction");
		        		String[] arr = new String[6];
		        		for (int i = 1; i <= 6; i++) {
		        			arr[i-1] = rs.getString(i);
		        	    }
		        		request.setArgs(arr);
						request.setProposalWaitTime(1000);
				
						Map<String, byte[]> tm2 = new HashMap<>();
						tm2.put("HyperLedgerFabric", "TransactionProposalRequest:JavaSDK".getBytes(UTF_8)); 																								
						tm2.put("method", "TransactionProposalRequest".getBytes(UTF_8)); 
						tm2.put("result", ":)".getBytes(UTF_8));
						tm2.put(EXPECTED_EVENT_NAME, EXPECTED_EVENT_DATA); 
						request.setTransientMap(tm2);
						Collection<ProposalResponse> responses = channelClient.sendTransactionProposal(request);
						for (ProposalResponse res: responses) {
							Status status = res.getStatus();
							Logger.getLogger(InvokeChaincode.class.getName()).log(Level.INFO,"Invoked createTransaction on "+Config.CHAINCODE_1_NAME + ". Status - " + status);
						}
					}
		        }
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				if (st != null) {
			        st.close();
			    }
			    if (conn != null) {
			        conn.close();
			    }
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
