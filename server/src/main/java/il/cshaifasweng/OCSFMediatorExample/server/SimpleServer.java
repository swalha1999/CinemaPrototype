package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.SubscribedClient;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SimpleServer extends AbstractServer {
	private static ArrayList<SubscribedClient> SubscribersList = new ArrayList<>();

	public SimpleServer(int port) {
		super(port);
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		Message message = (Message) msg;
		String request = message.getMessage();
		try {
			if (request.isBlank()){
				message.setMessage("Error! we got an empty message");
				client.sendToClient(message);
			}
			else if(request.equals("Movies1")){

				message.setMessage("update submitters IDs");
				sendToAllClients(message);
			}
			else if (request.equals("add client")){
				SubscribedClient connection = new SubscribedClient(client);
				SubscribersList.add(connection);
				message.setMessage("client added successfully");
				client.sendToClient(message);
			}
			else if(request.startsWith("echo Hello")){
				message.setMessage("Hello World!");
				client.sendToClient(message);
			}
			else if(request.startsWith("send Submitters IDs")){
				//add code here to send submitters IDs to client
				message.setMessage("322283755, 212371421, 314932211");
				client.sendToClient(message);
			}
			else if (request.startsWith("send Submitters")){
				//add code here to send submitters names to client
				message.setMessage("Omar, Abdulrahman, Mohammad");
				client.sendToClient(message);
			}
			else if (request.equals("what’s the time?")) {
				//add code here to send the time to client
				Date date = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				String time= formatter.format(date);
				message.setMessage("Time is " + time);
				client.sendToClient(message);
			}
			else if (request.startsWith("multiply")){
				String subString = request.substring(9);
				String[] parts = subString.split("\\*");
				int num1 = Integer.parseInt(parts[0]);
				int num2 = Integer.parseInt(parts[1]);
				message.setMessage("result is " + num1 * num2);
				client.sendToClient(message);

			}else{
				message.setMessage(request);
				sendToAllClients(message);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void sendToAllClients(Message message) {
		try {
			for (SubscribedClient SubscribedClient : SubscribersList) {
				SubscribedClient.getClient().sendToClient(message);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
