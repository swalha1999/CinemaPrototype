package il.cshaifasweng.OCSFMediatorExample.client.data;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.MovieTicket;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Screening;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.User;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TicketView {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty userName;
    private final SimpleStringProperty screeningTitle;
    private final SimpleIntegerProperty seatNumber;
    private final SimpleBooleanProperty isUsed;
    private final SimpleBooleanProperty isRefunded;
    private final SimpleBooleanProperty isBundleTicket;

    public TicketView(int id, String userName, String screeningTitle, int seatNumber, boolean isUsed, boolean isRefunded, boolean isBundleTicket) {
        this.id = new SimpleIntegerProperty(id);
        this.userName = new SimpleStringProperty(userName);
        this.screeningTitle = new SimpleStringProperty(screeningTitle);
        this.seatNumber = new SimpleIntegerProperty(seatNumber);
        this.isUsed = new SimpleBooleanProperty(isUsed);
        this.isRefunded = new SimpleBooleanProperty(isRefunded);
        this.isBundleTicket = new SimpleBooleanProperty(isBundleTicket);
    }

    public TicketView(MovieTicket ticket) {
        this.id = new SimpleIntegerProperty(ticket.getId());
        User user = ticket.getUser();
        this.userName = new SimpleStringProperty(user != null ? user.getUsername() : "Unknown");
        Screening screening = ticket.getScreening();
        this.screeningTitle = new SimpleStringProperty(screening != null ? screening.getMovie().getTitle() : "Unknown");
        this.seatNumber = new SimpleIntegerProperty(ticket.getSeatNumber());
        this.isUsed = new SimpleBooleanProperty(ticket.getIsUsed());
        this.isRefunded = new SimpleBooleanProperty(ticket.isRefunded());
        this.isBundleTicket = new SimpleBooleanProperty(ticket.isBundleTicket());
    }

    // Getter methods for properties
    public int getId() { return id.get(); }
    public String getUserName() { return userName.get(); }
    public String getScreeningTitle() { return screeningTitle.get(); }
    public int getSeatNumber() { return seatNumber.get(); }
    public boolean getIsUsed() { return isUsed.get(); }
    public boolean getIsRefunded() { return isRefunded.get(); }
    public boolean getIsBundleTicket() { return isBundleTicket.get(); }

    // Property methods for JavaFX bindings
    public SimpleIntegerProperty idProperty() { return id; }
    public SimpleStringProperty userNameProperty() { return userName; }
    public SimpleStringProperty screeningTitleProperty() { return screeningTitle; }
    public SimpleIntegerProperty seatNumberProperty() { return seatNumber; }
    public SimpleBooleanProperty isUsedProperty() { return isUsed; }
    public SimpleBooleanProperty isRefundedProperty() { return isRefunded; }
    public SimpleBooleanProperty isBundleTicketProperty() { return isBundleTicket; }
}
