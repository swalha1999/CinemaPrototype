package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.client.events.AddMoviesEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.DeleteMoviesEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.MessageEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.UpdateMoviesEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.Messages.Message;
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
		String messageContent = message.getMessage();
		System.out.println(messageContent);

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

	public static SimpleClient getClient() {
		if (client == null) {
			client = new SimpleClient(host, port6);
		}
		return client;
	}

}
