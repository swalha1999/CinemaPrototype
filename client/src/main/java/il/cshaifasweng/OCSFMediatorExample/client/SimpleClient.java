package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.client.events.*;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.*;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.patchs.NewUserAddedPatch;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.patchs.RemoveUserPatch;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.responses.*;
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
			switch (messageContent) {
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
			switch (message.getType()){
				case LOGIN_RESPONSE:
					EventBus.getDefault().post(new LoginEvent((LoginResponse) message.getDataObject()));
					break;
				case REGISTER_RESPONSE:
					EventBus.getDefault().post( new RegisterEvent((RegisterResponse) message.getDataObject()));
					break;
				case LOGOUT_RESPONSE:
					EventBus.getDefault().post( new LogoutEvent((LogoutResponse) message.getDataObject()));
					break;
				case GET_ALL_USERS_RESPONSE:
					EventBus.getDefault().post( new GetAllUsersEvent((GetAllUsersResponse) message.getDataObject()));
					break;
				case GET_ALL_MOVIES_RESPONSE:
					EventBus.getDefault().post( new GetAllMoviesEvent((GetAllMoviesResponse) message.getDataObject()));
					break;
				case NEW_USER_ADDED_PATCH:
					EventBus.getDefault().post( new NewUserAddedEvent((NewUserAddedPatch) message.getDataObject()));
					break;
				case REMOVE_USER_PATCH:
					EventBus.getDefault().post( new RemoveUserEvent((RemoveUserPatch) message.getDataObject()));
					break;
				case GET_MOVIE_RESPONSE:
					EventBus.getDefault().post( new GetMovieEvent((GetMovieResponse) message.getDataObject()));
					break;
				case ADD_MOVIE_PATCH:
					EventBus.getDefault().post( new AddMoviesEvent((AddMovieResponse) message.getDataObject()));

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
