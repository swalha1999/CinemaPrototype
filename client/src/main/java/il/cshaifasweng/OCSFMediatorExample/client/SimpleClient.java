package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.client.events.*;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.*;
import org.greenrobot.eventbus.EventBus;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;

public class SimpleClient extends AbstractClient {
	
	private static SimpleClient client = null;
	public static String host="";
	public static int port6=0;

	private SimpleClient(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		Message message = (Message) msg;
		System.out.println("The message is: " + message);
		String messageContent = message.getMessage();

		if (message.getVersion() == MessageVersion.V1) {
			System.out.println("Received message of type: " + messageContent);
			System.out.println("Message Version: V1" );
			switch (messageContent) {
				case "add movies":
				case "get all movies":
					EventBus.getDefault().post(new AddMoviesEvent(message));
					break;
				case "update movies":
					EventBus.getDefault().post(new UpdateMoviesEvent(message));
					break;
				case "delete movies":
					EventBus.getDefault().post(new DeleteMoviesEvent(message));
					break;
				default:
					EventBus.getDefault().post(new MessageEvent(message));
					break;
			}
		}
		else if (message.getVersion() == MessageVersion.V2){
			System.out.println("Received message of type: " + message.getType() + " with data: " + message.getDataObject());
			System.out.println("Message Version: V2" );
			switch (message.getType()){
				case LOGIN_RESPONSE:
					EventBus.getDefault().post(new LoginEvent((LoginResponse) message.getDataObject()));
					break;
				case REGISTER_RESPONSE:
					EventBus.getDefault().post( new RegisterEvent((RegisterResponse) message.getDataObject()));
					break;
				case LOGOUT_RESPONSE:
					EventBus.getDefault().post( (LogoutResponse) message.getDataObject() );
					break;
				default:
					EventBus.getDefault().post(new MessageEvent(message));
					break;
			}
		}
	}

	public static SimpleClient getClient() {
		if (client == null) {
			client = new SimpleClient(host, port6);
		}
		return client;
	}

}
