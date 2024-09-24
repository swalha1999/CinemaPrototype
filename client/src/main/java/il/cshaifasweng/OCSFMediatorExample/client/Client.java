package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.client.controllers.AddScreening;
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
				case GET_MY_TICKETS_RESPONSE:
					EventBus.getDefault().post(new GetMyTicketsEvent(message));
					break;
				case GET_ALL_USERS_RESPONSE:
					EventBus.getDefault().post(new GetAllUsersEvent(message));
					break;
				case UPDATE_CINEMA_PATCH:
					EventBus.getDefault().post(new UpdateCinemaEvent(message));
					break;
				case REMOVE_CINEMA_PATCH:
					EventBus.getDefault().post(new RemoveCinemaEvent(message));
					break;
				case GET_CINEMA_HALLS_RESPONSE:
					EventBus.getDefault().post(new GetCinemaHallsEvent(message));
					break;
				case ADD_CINEMA_PATCH:
					EventBus.getDefault().post(new AddCinemaEvent(message));
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
				case GET_SCREENING_FOR_HALL_RESPONSE:
					EventBus.getDefault().post( new GetScreeningForHallEvent(message));
					break;
				case ADD_HALL_PATCH:
					EventBus.getDefault().post( new AddHallEvent(message));
					break;
				case ADD_SCREENING_PATCH:
					EventBus.getDefault().post( new AddScreeningEvent(message));
					break;
				case UPDATE_SCREENING_PATCH:
					EventBus.getDefault().post( new UpdateScreeningEvent(message));
					break;
				case GET_SEATS_FOR_SCREENING_RESPONSE:
					EventBus.getDefault().post( new GetSeatsForScreeningEvent(message));
					break;
				case PURCHASE_TICKETS_RESPONSE:
					EventBus.getDefault().post(new PurchaseScreeningEvent(message));
					break;
				case NOTIFICATION:
					EventBus.getDefault().post(new HourTillMovieEvent(message));
					break;
				case GET_MY_SCREENINGS_RESPONSE:
					EventBus.getDefault().post( new GetMyScreeningsEvent(message));
					break;
				case SHOW_CINEMA_INFO_RESPONSE:
					EventBus.getDefault().post(new GetCinemaTicketsEvent(message));
					break;
				case USER_TICKET_REMOVED_PATCH:
					EventBus.getDefault().post(new RemovedTicketEvent(message));
					break;
				case GET_ALL_SUPPORT_TICKETS_RESPONSE:
					EventBus.getDefault().post(new GetAllSupportTicketsEvent(message));
					break;
				case CINEMA_SUPPORT_TICKETS_INFO_RESPONSE:
					EventBus.getDefault().post(new GetCinemaSupportTicketsEvent(message));
					break;
				case CINEMA_TICKETS_INFO_RESPONSE:
					EventBus.getDefault().post(new GetCinemaTicketsEvent(message));
					break;
				case GET_USER_INFO_RESPONSE:
					EventBus.getDefault().post(new UserInfoReceivedEvent(message));
					break;
				case GET_ALL_TICKETS_RESPONSE:
					EventBus.getDefault().post(new GetCinemaTicketsEvent(message));
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
