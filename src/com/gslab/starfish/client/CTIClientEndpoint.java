package com.gslab.starfish.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

import javax.json.Json;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.glassfish.tyrus.client.ClientManager;




import com.gslab.starfish.json.messages.ControlR;
import com.gslab.starfish.json.messages.Header;
import com.gslab.starfish.json.messages.Register_Received;
import com.gslab.starfish.json.messages.Register_Send;
import com.gslab.starfish.json.messages.decoders.Register_Received_Decoder;
import com.gslab.starfish.json.messages.decoders.Register_Send_Decoder;
import com.gslab.starfish.json.messages.encoders.Register_Received_Encoder;
import com.gslab.starfish.json.messages.encoders.Register_Send_Encoder;
import com.gslab.starfish.json.messages.interfaces.Message;


 
@ClientEndpoint (encoders={Register_Send_Encoder.class,Register_Received_Encoder.class}, decoders={Register_Send_Decoder.class,Register_Received_Decoder.class})
public class CTIClientEndpoint {
 	   
	Set<Session> userSessions = Collections.synchronizedSet(new HashSet<Session>());

    private Logger logger = Logger.getLogger(this.getClass().getName());
 
    @OnOpen
    public void onOpen(Session session) {
        logger.info("Connected ... " + session.getId());
        try{  		

        	
        	Register_Send joinMessage = new Register_Send();
                    
                        ControlR control = new ControlR(); //control
                        
                            control.setType( "request");
                            control.setPackage_type("registerFromStarfish");
                            control.setSequence (1);
                            control.setVersion ( "1.0-000abc");
                            control.setSession_id(session.getId());
                           
                       Header header = new Header();
                       
                            header.setInitiator("initiator001");
                            header.setTarget ("target");
                            header.setAction( "connect");
                            
                            joinMessage.setControl(control);
                            joinMessage.setHeader(header);
                            
                            
                           ArrayList<String> payload = new ArrayList<String>();
                           payload.add("This is from client");
                            joinMessage.setPayload(payload);
                   session.getAsyncRemote().sendObject(joinMessage);
	
	}catch(Exception io){
		System.out.println(io.getMessage());
	}
       




    }
   
 
//    @OnMessage
//    public void onMessage(Register_Send joinMessage, Session session) {
//       // BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
//        try {
//            logger.info("This is from Register_Send method:"+joinMessage.getControl().getSession_id());
//
//            
//        } catch (Exception e) {
//
//           e.printStackTrace();
//        }
//    }
    
    @OnMessage
    public void onMessage(Message message, Session session) {
       // BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
    	
    	if(message instanceof Register_Received ){
    		logger.info("getting Register_Received message");
    	}
       
           logger.info("This is from Register_Received method:"+message.toString());// +"::"+((Register_Received)message).getControl().getSession_id());
      
   
        
    }
 
    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info(String.format("Session %s close because of %s", session.getId(), closeReason));
    }
 
    public static void main(String[] args) {


               ClientManager client = ClientManager.createClient();
        try {
            Session srvSession = client.connectToServer(CTIClientEndpoint.class, new URI("ws://localhost:9090/websockets/cti"));
            
            while(0==0){
    			Thread.sleep(1000);
    			if(srvSession!=null){
    			
//    				
//    				Message clientMessage = null;
//        	        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//	        	    try{
//	        	    	System.out.println("Enter a message to send to server: " );
//	        	        String message  = reader.readLine();
//	        	        String[] messages = message.split("::");Str
//	        	        clientMessage.setMessage(messages[1]);
//	        	        clientMessage.setSender(messages[0]);
//	        	        clientMessage.setReceived(new Date());
	        	    	
//	        	    	String message  = reader.readLine();
//	        	        String[] messages = message.split("::");
//	        	        clientMessage = new Message(Json.createObjectBuilder()
//	        	                .add("event", "login")
//	        	                .add("userID", "client001")
//	        	                .build());
//	        	    }catch(Exception ex){
//	        	    	System.out.println(ex.getMessage());	        	    	
//	        	    }	        	    
//        	    	System.out.println("Sending a message to server: " + clientMessage);
//        	    	srvSession.getAsyncRemote().sendObject(clientMessage);
//        	    	System.out.println("message sent");
        	    }
            }   
       } catch (Exception e) {



    	   	e.printStackTrace();
        }
    }
 
}