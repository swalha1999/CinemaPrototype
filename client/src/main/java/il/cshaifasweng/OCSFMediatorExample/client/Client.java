package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.client.events.*;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.*;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.responses.*;
import org.greenrobot.eventbus.EventBus;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;

public class Client extends AbstractClient {
	
	private static Client client = null;
	public static String host="";
	public static int port6=0;

	private Client(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		Message message = (Message) msg;
		System.out.println("The message is: " + message);

		if (message.getVersion() == MessageVersion.V3) {
			switch (message.getType()){

				case GET_ALL_USERS_RESPONSE:
					EventBus.getDefault().post(new GetAllUsersEvent(message));
					break;
				case GET_ALL_MOVIES_RESPONSE:
					EventBus.getDefault().post( new GetAllMoviesEvent(message));
					break;
				case GET_MOVIE_RESPONSE:
					EventBus.getDefault().post( new GetMovieEvent(message));
					break;
				case NEW_USER_ADDED_PATCH:
					EventBus.getDefault().post( new NewUserAddedEvent(message));
					break;
				case REMOVE_USER_PATCH:
					EventBus.getDefault().post( new RemoveUserEvent(message));
					break;
				case ADD_MOVIE_PATCH:
					EventBus.getDefault().post( new AddMoviesEvent(message));
					break;
				case REMOVE_MOVIE_PATCH:
					EventBus.getDefault().post( new RemoveMovieEvent(message));
					break;
				case UPDATE_MOVIE_PATCH:
					EventBus.getDefault().post( new UpdateMovieEvent(message));
					break;
				case GET_ALL_SCREENINGS_RESPONSE:
					EventBus.getDefault().post( new GetAllScreeningsEvent(message));
					break;
				case GET_SCREENING_FOR_MOVIE_RESPONSE:
					EventBus.getDefault().post( new GetScreeningForMovieEvent(message));
					break;
				case GET_ALL_CINEMAS_RESPONSE:
					EventBus.getDefault().post( new GetAllCinemasEvent(message));
					break;
				case UPDATED_USER_PATCH:
					EventBus.getDefault().post( new UpdatedUserEvent(message));
					break;

				default:
					EventBus.getDefault().post(new MessageEvent(message));
					break;
			}
		}
		else if (message.getVersion() == MessageVersion.V2 ) {
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

				default:
					EventBus.getDefault().post(new MessageEvent(message));
					break;
			}
		}
		else {
			System.out.println("Unsupported message version received");
		}
	}

	public static Client getClient() {
		if (client == null) {
			client = new Client(host, port6);
		}
		return client;
	}

}
