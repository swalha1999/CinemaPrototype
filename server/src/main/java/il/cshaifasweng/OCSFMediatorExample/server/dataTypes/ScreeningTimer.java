package il.cshaifasweng.OCSFMediatorExample.server.dataTypes;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Screening;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import il.cshaifasweng.OCSFMediatorExample.server.DAO.ScreeningDAO;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import org.hibernate.Session;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Set;

public class ScreeningTimer {

 private Timer timer;
 private ScreeningDAO screeningDAO;
 private Set<ConnectionToClient> clients; // List of connected clients

 public ScreeningTimer(ScreeningDAO screeningDAO, Set<ConnectionToClient> clients) {
  this.screeningDAO = screeningDAO;
  this.clients = clients;
  this.timer = new Timer(true); // Daemon thread
 }

 public void start() {
  // Schedule the task to check every minute
  timer.scheduleAtFixedRate(new CheckScreeningTask(), 0, 60 * 1000);
 }

 private class CheckScreeningTask extends TimerTask {
  @Override
  public void run() {
   // Fetch all screenings
   Message request = new Message(MessageType.GET_ALL_SCREENINGS_REQUEST);
   Message response = screeningDAO.getAllScreenings(request);

   if (response.isSuccess()) {
    List<Screening> screenings = (List<Screening>) response.getDataObject();
    LocalDateTime now = LocalDateTime.now();

    for (Screening screening : screenings) {
     LocalDateTime screeningTime = screening.getStartingAt();
     Duration duration = Duration.between(now, screeningTime);

     if (duration.toHours() == 1) {  // Check if there's 1 hour left
      Message notificationMessage = createNotificationMessage(screening);  // Create notification message
      sendNotificationsToClients(notificationMessage); // Notify all clients
     }
    }
   }
  }
 }

 private Message createNotificationMessage(Screening screening) {
  Message notification = new Message(MessageType.NOTIFY_CLIENTS);
  notification.setMessage("Movie " + screening.getMovie().getTitle() + " starts in 1 hour!");
  notification.setDataObject(screening);
  return notification;
 }

 private void sendNotificationsToClients(Message notificationMessage) {
  for (ConnectionToClient client : clients) {
   sendResponse(client, notificationMessage);
  }
 }

 private boolean sendResponse(ConnectionToClient client, Message response) {
  try {
   client.sendToClient(response);
   return true;
  } catch (IOException e) {
   System.out.println("Error sending message: " + e.getMessage());
   return false;
  }
 }

 public void stop() {
  timer.cancel();
 }

 public static void main(String[] args) {
  Session session = null; // Replace with actual session initialization
  ScreeningDAO screeningDAO = new ScreeningDAO(session);
  Set<ConnectionToClient> clients = null; // Replace with actual connected clients initialization
  ScreeningTimer screeningTimer = new ScreeningTimer(screeningDAO, clients);
  screeningTimer.start();

  // Add a shutdown hook to stop the timer on application exit
  Runtime.getRuntime().addShutdownHook(new Thread(screeningTimer::stop));
 }
}
